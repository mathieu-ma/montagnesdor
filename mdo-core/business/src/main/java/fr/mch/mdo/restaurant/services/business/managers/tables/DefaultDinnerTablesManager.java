package fr.mch.mdo.restaurant.services.business.managers.tables;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.DinnerTable;
import fr.mch.mdo.restaurant.dao.beans.OrderLine;
import fr.mch.mdo.restaurant.dao.beans.Product;
import fr.mch.mdo.restaurant.dao.beans.ProductSpecialCode;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.beans.TableBill;
import fr.mch.mdo.restaurant.dao.beans.TableCredit;
import fr.mch.mdo.restaurant.dao.beans.TableType;
import fr.mch.mdo.restaurant.dao.beans.TableVat;
import fr.mch.mdo.restaurant.dao.beans.UserAuthentication;
import fr.mch.mdo.restaurant.dao.products.IProductSpecialCodesDao;
import fr.mch.mdo.restaurant.dao.products.IProductsDao;
import fr.mch.mdo.restaurant.dao.products.hibernate.DefaultProductSpecialCodesDao;
import fr.mch.mdo.restaurant.dao.products.hibernate.DefaultProductsDao;
import fr.mch.mdo.restaurant.dao.tables.IDinnerTablesDao;
import fr.mch.mdo.restaurant.dao.tables.hibernate.DefaultDinnerTablesDao;
import fr.mch.mdo.restaurant.dto.beans.DinnerTableDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.OrderLineDto;
import fr.mch.mdo.restaurant.dto.beans.ProductDto;
import fr.mch.mdo.restaurant.dto.beans.ProductSpecialCodeDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantPrefixTableDto;
import fr.mch.mdo.restaurant.dto.beans.TableTypeDto;
import fr.mch.mdo.restaurant.exception.MdoBusinessException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.AbstractAdministrationManager;
import fr.mch.mdo.restaurant.services.business.managers.assembler.DefaultDinnerTablesAssembler;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;



public class DefaultDinnerTablesManager extends AbstractAdministrationManager implements IDinnerTablesManager
{
	private IProductsDao productsDao;
	private IProductSpecialCodesDao productSpecialCodeDao;
	
	private static class LazyHolder {
		private static IDinnerTablesManager instance = new DefaultDinnerTablesManager(
				LoggerServiceImpl.getInstance().getLogger(DefaultDinnerTablesManager.class.getName()),
				DefaultDinnerTablesDao.getInstance(), DefaultDinnerTablesAssembler.getInstance());
	}

	private DefaultDinnerTablesManager(ILogger logger, IDaoServices dao, IManagerAssembler assembler) {
		super.logger = logger;
		super.dao = dao;
		super.assembler = assembler;
		this.productsDao = DefaultProductsDao.getInstance();
		this.productSpecialCodeDao = DefaultProductSpecialCodesDao.getInstance();
	}

	/**
	 * This constructor is used by ioc
	 */
	public DefaultDinnerTablesManager() {
	}

	public static IDinnerTablesManager getInstance() {
		return LazyHolder.instance;
	}

	private enum ManagedProductSpecialCode {
		DEFAULT(""), OFFERED_PRODUCT("#"), DISCOUNT_ORDER("-"), USER_ORDER("/") {
			public void fillFields(MdoUserContext userContext, OrderLineDto orderLineDto, OrderLine orderLine) {
				orderLineDto.setCode(this.getCode());
			}
		}, CREDIT("@");
		private String code = "";

		ManagedProductSpecialCode(String code) {
			this.code = code;
		}

		public String getCode() {
			return this.code;
		}
	}

	@Override
	public IMdoDtoBean update(IMdoDtoBean dtoBean, MdoUserContext userContext) throws MdoBusinessException {
		DinnerTable daoBean = (DinnerTable) assembler.unmarshal(dtoBean);
		try {
			// Deleting daoBean.getBills(), daoBean.getCashings(), daoBean.getCredits(), daoBean.getOrders(), daoBean.getVats() before inserting new ones
			Set<TableBill> backupBills = new HashSet<TableBill>(daoBean.getBills());
			// TODO
//			Set<TableCashing> backupCashings = new HashSet<TableCashing>(daoBean.getCashings());
			Set<TableCredit> backupCredits = new HashSet<TableCredit>(daoBean.getCredits());
			Set<OrderLine> backupOrders = new HashSet<OrderLine>(daoBean.getOrders());
			Set<TableVat> backupVats = new HashSet<TableVat>(daoBean.getVats());
			// Removing
			daoBean.getBills().clear();
			// TODO
//			daoBean.getCashings().clear();
			daoBean.getCredits().clear();
			daoBean.getOrders().clear();
			daoBean.getVats().clear();
			dao.update(daoBean);
			// Restoring
			daoBean.getBills().addAll(backupBills);
			// TODO
//			daoBean.getCashings().addAll(backupCashings);
			daoBean.getCredits().addAll(backupCredits);
			daoBean.getOrders().addAll(backupOrders);
			daoBean.getVats().addAll(backupVats);
			
			return assembler.marshal((IMdoDaoBean) dao.update(daoBean), userContext);
		} catch (MdoException e) {
			logger.error("message.error.administration.business.save", e);
			throw new MdoBusinessException("message.error.administration.business.save", e);
		}
	}

	
	/**
	 * Check that the first character of the code is in the table
	 * t_product_special_code. If this is the case then return the
	 * ManagedProductSpecialCode object
	 * 
	 * @param restaurantId
	 * @param code
	 * @return
	 */
	private ManagedProductSpecialCode getProductSpecialCode(RestaurantDto restaurant, String code) {
		ManagedProductSpecialCode result = null;
		if (code.length() > 0) {
			// Check if the First character belongs to a product special code
			try {
				String shortCode = code.substring(0, 1);
				// Maybe get ProductSpecialCode from collection restaurant.getProductSpecialCodes() instead of requesting into database
				Set<ProductSpecialCodeDto> productSpecialCodes = restaurant.getProductSpecialCodes();
				for (Iterator<ProductSpecialCodeDto> iterator = productSpecialCodes.iterator(); iterator.hasNext();) {
					ProductSpecialCodeDto productSpecialCode = iterator.next();
					if (shortCode.equals(productSpecialCode.getShortCode())) {
						result = ManagedProductSpecialCode.valueOf(productSpecialCode.getCode().getName());
						break;
					}
				}
//				ProductSpecialCode productSpecialCode = (ProductSpecialCode) productSpecialCodeDao.findByShortCode(restaurant.getId(), shortCode);
//				if (productSpecialCode != null) {
//					result = ManagedProductSpecialCode.valueOf(productSpecialCode.getCode().getName());
//				}
			} catch (Exception e) {
				logger.error("message.error.business.find.productSpecialCode", e);
			}
		}
		return result;
	}

	@Override
	public void processOrderLineByCode(MdoUserContext userContext, OrderLineDto orderLine) throws MdoBusinessException {
		String computedCode = orderLine.getCode();
		if (computedCode != null) {
			String productCode = computedCode;
			// Try to know if the code begins by the a Product Special Code
			ManagedProductSpecialCode productSpecialCode = this.getProductSpecialCode(userContext.getUserAuthentication().getRestaurant(), computedCode);
			if (productSpecialCode != null) {
				// Remove the prefix Product Special Code
				productCode = computedCode.substring(1);
			} else {
				productSpecialCode = ManagedProductSpecialCode.DEFAULT;
			}
			try {
				Long restaurantId = userContext.getUserAuthentication().getRestaurant().getId();
				// Product Special Code
				ProductSpecialCode psc = (ProductSpecialCode) productSpecialCodeDao.findByCodeName(restaurantId, productSpecialCode.name());
				if (psc == null) {
					logger.error("message.error.business.DefaultDinnerTablesManager.processOrderLineByCode.no.psc", new Object[]{orderLine});
					throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.processOrderLineByCode.no.psc", new Object[]{orderLine});
				}
				ProductSpecialCodeDto pscDto = new ProductSpecialCodeDto();
				pscDto.setId(psc.getId());
				orderLine.setProductSpecialCode(pscDto);
				
				// Try to get the Product
				Product product = (Product) productsDao.findByCode(restaurantId, productCode);
				if (product != null) {
					ProductDto productDto = new ProductDto();
					productDto.setId(product.getId());
					productDto.setColorRGB(product.getColorRGB());
					orderLine.setProduct(productDto);
					// Update unit price and label...
					orderLine.setLabel(this.getLabel(product.getLabels(), userContext.getCurrentLocale().getId()));
					orderLine.setUnitPrice(product.getPrice());
				}
				if (orderLine.getUnitPrice() != null) {
					this.addOrderLine(userContext, orderLine);
				}
			} catch (MdoException e) {
				logger.error("message.error.business.DefaultDinnerTablesManager.processOrderLineByCode", new Object[]{orderLine}, e);
				throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.processOrderLineByCode", new Object[]{orderLine}, e);
			}
		}
	}
	
	private String getLabel(Map<Long, String> labels, Long localeId) {
		String result = null;
		if (labels != null && !labels.isEmpty()) {
			result = labels.get(localeId);
			if (result == null) {
				result = labels.values().iterator().next();
			}
		}
		return result;
	}
	
	@Override
	public void addOrderLine(IMdoBean userContext, OrderLineDto orderLine) throws MdoBusinessException {
		if (orderLine.getQuantity() == null) {
			logger.error("message.error.business.DefaultDinnerTablesManager.addOrderLine.quantity.null", new Object[]{orderLine});
			throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.addOrderLine.no.quantity", new Object[]{orderLine});
		}
		if (orderLine.getCode() == null) {
			logger.error("message.error.business.DefaultDinnerTablesManager.addOrderLine.code.null", new Object[]{orderLine});
			throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.addOrderLine.no.code", new Object[]{orderLine});
		}
		if (orderLine.getProductSpecialCode() == null || orderLine.getProductSpecialCode().getId() == null) {
			logger.error("message.error.business.DefaultDinnerTablesManager.addOrderLine.psc.null", new Object[]{orderLine});
			throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.addOrderLine.no.psc", new Object[]{orderLine});
		}
		if (orderLine.getLabel() == null) {
			logger.error("message.error.business.DefaultDinnerTablesManager.addOrderLine.label.null", new Object[]{orderLine});
			throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.addOrderLine.no.label", new Object[]{orderLine});
		}
		if (orderLine.getUnitPrice() == null) {
			logger.error("message.error.business.DefaultDinnerTablesManager.addOrderLine.unit.price.null", new Object[]{orderLine});
			throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.addOrderLine.no.unit.price", new Object[]{orderLine});
		}
		MdoUserContext castedUserContext = (MdoUserContext) userContext;
		try {
			if (orderLine.getDinnerTable() == null) {
				// Try to find in database
				logger.error("message.error.business.DefaultDinnerTablesManager.addOrderLine.no.current.table");
				throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.addOrderLine.no.current.table");
			}
			Long dinnerTableId = orderLine.getDinnerTable().getId();
			if (dinnerTableId == null) {
				// Create new table
				dinnerTableId = this.createFromUserContext(orderLine.getDinnerTable(), castedUserContext);
				orderLine.getDinnerTable().setId(dinnerTableId);
			} 
			if (dinnerTableId != null) {
				OrderLine orderLineToBeSaved = new OrderLine();

				// Fill table
				DinnerTable tableToBeSaved = new DinnerTable();
				tableToBeSaved.setId(dinnerTableId);
				orderLineToBeSaved.setDinnerTable(tableToBeSaved);

				// Fill ProductSpecialCode
				ProductSpecialCode psc = new ProductSpecialCode();
				psc.setId(orderLine.getProductSpecialCode().getId());
				orderLineToBeSaved.setProductSpecialCode(psc);
				// Fill Product
				Product product = new Product();
				if (orderLine.getProduct() != null) {
					product.setId(orderLine.getProduct().getId());
				}
				orderLineToBeSaved.setProduct(product);

				// Fill order line
				orderLineToBeSaved.setId(orderLine.getId());
				orderLineToBeSaved.setQuantity(orderLine.getQuantity());
				orderLineToBeSaved.setUnitPrice(orderLine.getUnitPrice());
				// Amount == quantity * unit price
				orderLine.setAmount(orderLine.getQuantity().multiply(orderLine.getUnitPrice()));
				// Maybe don't have to store in database
				orderLineToBeSaved.setAmount(orderLine.getAmount());
				orderLineToBeSaved.setLabel(orderLine.getLabel());
				if (orderLine.getId() == null) {
					dao.insert(orderLineToBeSaved);
				} else {
//					// Load the order line from data base in order to subtract in sums
//					OrderLine oldOrderLine = new OrderLine();
//					oldOrderLine.setId(orderLineToBeSaved.getId());
//					dao.load(oldOrderLine);
//					// Subtract because this will be added later
//					tableToBeSaved.setAmountsSum(tableToBeSaved.getAmountsSum().subtract(oldOrderLine.getAmount()));
//					tableToBeSaved.setQuantitiesSum(tableToBeSaved.getQuantitiesSum().subtract(oldOrderLine.getQuantity()));
					// Save new values
					dao.update(orderLineToBeSaved);
				}
				// Store the id get from database
				orderLine.setId(orderLineToBeSaved.getId());
//				// Update the table amount/quantity after order line saved...==> This is will be done only after cashing table.
//				tableToBeSaved.setQuantitiesSum(orderLine.getQuantity().add(tableToBeSaved.getQuantitiesSum()));
//				tableToBeSaved.setAmountsSum(orderLine.getAmount().add(tableToBeSaved.getAmountsSum()));
//				tableToBeSaved.setAmountPay(tableToBeSaved.getAmountsSum().multiply(BigDecimal.ONE.subtract(tableToBeSaved.getReductionRatio().divide(new BigDecimal(100)))));
//				dao.update(tableToBeSaved);
			} else {
				// Try to find in database
				logger.error("message.error.business.DefaultDinnerTablesManager.addOrderLine.no.current.table");
				throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.addOrderLine.no.current.table");
			}
		} catch (MdoException e) {
			logger.error("message.error.business.DefaultDinnerTablesManager.addOrderLine", new Object[]{orderLine}, e);
			throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.addOrderLine", new Object[]{orderLine}, e);
		}
	}

	@Override
	public Map<Long, String> findAllTableNamesByPrefix(IMdoBean userContext, String prefixTableNumber) throws MdoBusinessException {
		Map<Long, String> result = new HashMap<Long, String>();
		MdoUserContext userContextX = (MdoUserContext) userContext;
		IDinnerTablesDao daoX = (IDinnerTablesDao) dao;
		try {
			result = daoX.findAllTableNamesByPrefix(userContextX.getUserAuthentication().getId(), prefixTableNumber);
		} catch (MdoException e) {
			logger.error("message.error.business.DefaultDinnerTablesManager.findAllTableNamesByPrefix", new Object[]{prefixTableNumber}, e);
			throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.findAllTableNamesByPrefix", new Object[]{prefixTableNumber}, e);
		}

		return result;
	}

	@Override
	public DinnerTableDto findTableByNumber(MdoUserContext userContext, String number) throws MdoBusinessException {
		DinnerTableDto result = null;
		IDinnerTablesDao daoCasted = (IDinnerTablesDao) dao;
		// Get dinner table
		DinnerTable dinnerTable = null;
		try {
			dinnerTable = daoCasted.findByNumber(userContext.getUserAuthentication().getRestaurant().getId(), number);
		} catch (MdoException e) {
			logger.error("message.error.business.DefaultDinnerTablesManager.findTableByNumber", new Object[]{number}, e);
			throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.findTableByNumber", new Object[]{number}, e);
		}

		result = (DinnerTableDto) assembler.marshal(dinnerTable, userContext);

		return result;
	}

	@Override
	public Map<Long, String> findAllProductCodesByPrefix(IMdoBean userContext, String prefixProductCode) throws MdoBusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getCustomersNumberByNumber(IMdoBean userContext, String tableNumber) throws MdoBusinessException {
		Integer result = null;
		Long restaurantId = ((MdoUserContext) userContext).getUserAuthentication().getRestaurant().getId();
		// Get customers number the non cashing dinner table
		try {
			result = ((IDinnerTablesDao) dao).findCustomersNumberByNumber(restaurantId, tableNumber);
		} catch (MdoException e) {
			logger.error("message.error.business.DefaultDinnerTablesManager.getCustomersNumberByNumber", e);
			throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.getCustomersNumberByNumber", e);
		}
		return result;
	}

	@Override
	public Long createFromUserContext(DinnerTableDto dinnerTable, MdoUserContext userContext) throws MdoBusinessException {
		Long result = null;
		if (dinnerTable.getNumber() == null) {
			logger.error("message.error.business.DefaultDinnerTablesManager.createFromUserContext.number.null");
			throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.createFromUserContext.number.null");
		}
		boolean isDinnerTableFree = this.isDinnerTableFree(userContext, dinnerTable.getNumber());
		if (!isDinnerTableFree) {
			logger.error("message.error.business.DefaultDinnerTablesManager.createFromUserContext.table.occupied");
			throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.createFromUserContext.table.occupied", new Object[] {dinnerTable.getNumber()});
		}
		
		RestaurantDto userContextRestaurant = userContext.getUserAuthentication().getRestaurant();
		
		DinnerTable dinnerTableToBeSaved = new DinnerTable();
		dinnerTableToBeSaved.setNumber(dinnerTable.getNumber());
		dinnerTableToBeSaved.setCustomersNumber(dinnerTable.getCustomersNumber());
		//dtb_reduction_ratio, dtb_amount_pay, dtb_reduction_ratio_changed, 
		Restaurant restaurant = new Restaurant();
		restaurant.setId(userContextRestaurant.getId());
		dinnerTableToBeSaved.setRestaurant(restaurant);
		UserAuthentication user = new UserAuthentication();
		user.setId(userContext.getUserAuthentication().getId());
		dinnerTableToBeSaved.setUser(user);
		Date registrationDate = new Date();
		dinnerTableToBeSaved.setRegistrationDate(registrationDate);
		TableTypeDto typeDto = findTableType(userContextRestaurant.getPrefixTableNames(), dinnerTable.getNumber(), userContextRestaurant.getDefaultTableType());
		TableType type = new TableType();
		type.setId(typeDto.getId());
		dinnerTableToBeSaved.setType(type);
		
		try {
			dao.insert(dinnerTableToBeSaved);
		} catch (MdoException e) {
			logger.error("message.error.business.DefaultDinnerTablesManager.createFromUserContext.insert.table", new Object[]{dinnerTable.getNumber()}, e);
			throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.createFromUserContext.insert.table", new Object[]{dinnerTable.getNumber()}, e);
		}
		
		result = dinnerTableToBeSaved.getId();
		
		return result;
	}
	
	private TableTypeDto findTableType(Set<RestaurantPrefixTableDto> prefixes, String number, TableTypeDto defaultType) {
		TableTypeDto result = defaultType;
		if (prefixes != null && !prefixes.isEmpty()) {
			for (Iterator<RestaurantPrefixTableDto> iterator = prefixes.iterator(); iterator.hasNext();) {
				RestaurantPrefixTableDto restaurantPrefixTableDto = iterator.next();
				if (number.startsWith(restaurantPrefixTableDto.getPrefix().getName())) {
					result = restaurantPrefixTableDto.getType();
					break;
				}
			}
		}
		return result;
	}

	@Override
	public boolean isDinnerTableFree(MdoUserContext userContext, String number) throws MdoBusinessException {
		boolean result = true;
		Long restaurantId = ((MdoUserContext) userContext).getUserAuthentication().getRestaurant().getId();
		// Get customers number of non cashing dinner table
		try {
			result = ((IDinnerTablesDao) dao).isDinnerTableFree(restaurantId, number);
		} catch (MdoException e) {
			logger.error("message.error.business.DefaultDinnerTablesManager.isDinnerTableFree", e);
			throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.isDinnerTableFree", e);
		}
		return result;
	}

	@Override
	public List<IMdoDtoBean> findAllFreeTables(MdoUserContext userContext) throws MdoException {
		List<IMdoDtoBean> result = new ArrayList<IMdoDtoBean>();
			
		Long restaurantId = ((MdoUserContext) userContext).getUserAuthentication().getRestaurant().getId();
		// Get list of non cashing dinner tables
		try {
			// TODO not use assembler.marshal but get only required fields to be displayed
			result = assembler.marshal(((IDinnerTablesDao) dao).findAllFreeTables(restaurantId), userContext);
		} catch (MdoException e) {
			logger.error("message.error.business.DefaultDinnerTablesManager.findAllFreeTables", e);
			throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.findAllFreeTables", e);
		}

		return result;
	}
}
