package fr.mch.mdo.restaurant.services.business.managers.tables;

import java.math.BigDecimal;
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
import fr.mch.mdo.restaurant.dao.beans.ValueAddedTax;
import fr.mch.mdo.restaurant.dao.orders.IOrderLinesDao;
import fr.mch.mdo.restaurant.dao.orders.hibernate.DefaultOrderLinesDao;
import fr.mch.mdo.restaurant.dao.products.IProductSpecialCodesDao;
import fr.mch.mdo.restaurant.dao.products.IProductsDao;
import fr.mch.mdo.restaurant.dao.products.hibernate.DefaultProductSpecialCodesDao;
import fr.mch.mdo.restaurant.dao.products.hibernate.DefaultProductsDao;
import fr.mch.mdo.restaurant.dao.tables.IDinnerTablesDao;
import fr.mch.mdo.restaurant.dao.tables.hibernate.DefaultDinnerTablesDao;
import fr.mch.mdo.restaurant.dto.beans.DinnerTableDto;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.OrderLineDto;
import fr.mch.mdo.restaurant.dto.beans.ProductSpecialCodeDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantPrefixTableDto;
import fr.mch.mdo.restaurant.dto.beans.TableTypeDto;
import fr.mch.mdo.restaurant.exception.MdoBusinessException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.AbstractAdministrationManager;
import fr.mch.mdo.restaurant.services.business.managers.assembler.DefaultDinnerTablesAssembler;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IDinnerTablesManagerAssembler;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultDinnerTablesManager extends AbstractAdministrationManager implements IDinnerTablesManager
{
	private IProductsDao productsDao;
	private IProductSpecialCodesDao productSpecialCodeDao;
	private IOrderLinesDao orderLinesDao;
	
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
		this.orderLinesDao = DefaultOrderLinesDao.getInstance();
	}

	/**
	 * This constructor is used by ioc
	 */
	public DefaultDinnerTablesManager() {
	}

	public static IDinnerTablesManager getInstance() {
		return LazyHolder.instance;
	}

	/**
	 * @return the productsDao
	 */
	public IProductsDao getProductsDao() {
		return productsDao;
	}

	/**
	 * @param productsDao the productsDao to set
	 */
	public void setProductsDao(IProductsDao productsDao) {
		this.productsDao = productsDao;
	}

	/**
	 * @return the productSpecialCodeDao
	 */
	public IProductSpecialCodesDao getProductSpecialCodeDao() {
		return productSpecialCodeDao;
	}

	/**
	 * @param productSpecialCodeDao the productSpecialCodeDao to set
	 */
	public void setProductSpecialCodeDao(
			IProductSpecialCodesDao productSpecialCodeDao) {
		this.productSpecialCodeDao = productSpecialCodeDao;
	}

	/**
	 * @return the orderLinesDao
	 */
	public IOrderLinesDao getOrderLinesDao() {
		return orderLinesDao;
	}

	/**
	 * @param orderLinesDao the orderLinesDao to set
	 */
	public void setOrderLinesDao(IOrderLinesDao orderLinesDao) {
		this.orderLinesDao = orderLinesDao;
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
	 * @param code the code.
	 * @return the 
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
						ManagedProductSpecialCode resultX = ManagedProductSpecialCode.valueOf(productSpecialCode.getCode().getName());
						if (resultX.checkCode(code)) {
							result = resultX;
							result.setProductSpecialCode(productSpecialCode);
						}
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
	public void processOrderLineByCode(MdoUserContext userContext, OrderLineDto orderLine, Long deletedId) throws MdoBusinessException {
		String computedCode = orderLine.getCode();
		if (computedCode != null) {
			String productCode = computedCode;
long deltaTime = System.currentTimeMillis();
			// Try to know if the code begins by the a Product Special Code
			ManagedProductSpecialCode productSpecialCode = this.getProductSpecialCode(userContext.getUserAuthentication().getRestaurant(), computedCode);
			if (productSpecialCode != null) {
				// Remove the prefix Product Special Code
				productCode = computedCode.substring(1);
			} else {
				productSpecialCode = ManagedProductSpecialCode.DEFAULT;
			}
deltaTime = System.currentTimeMillis() - deltaTime;
System.out.println("1) processOrderLineByCode Delta Time = " + deltaTime);
deltaTime = System.currentTimeMillis();
			
			try {
				Long restaurantId = userContext.getUserAuthentication().getRestaurant().getId();
				// Product Special Code Id
				Long pscId = productSpecialCodeDao.getIdByCodeName(restaurantId, productSpecialCode.name());
				if (pscId == null) {
					logger.error("message.error.business.DefaultDinnerTablesManager.processOrderLineByCode.no.psc", new Object[]{orderLine});
					throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.processOrderLineByCode.no.psc", new Object[]{orderLine});
				}
				ProductSpecialCodeDto pscDto = new ProductSpecialCodeDto();
				pscDto.setId(pscId);
				MdoTableAsEnumDto code = new MdoTableAsEnumDto();
				code.setName(productSpecialCode.name());
				pscDto.setCode(code);
				orderLine.setProductSpecialCode(pscDto);
deltaTime = System.currentTimeMillis() - deltaTime;
System.out.println("2) processOrderLineByCode Delta Time = " + deltaTime);
deltaTime = System.currentTimeMillis();
				
				// Try to get the Product
				Product product = productSpecialCode.getProductByCode(productsDao, restaurantId, userContext.getCurrentLocale().getId(), productCode);
				productSpecialCode.fillOrderLine((MdoUserContext) userContext, product, orderLine);
deltaTime = System.currentTimeMillis() - deltaTime;
System.out.println("3) processOrderLineByCode Delta Time = " + deltaTime);
deltaTime = System.currentTimeMillis();
				if (orderLine.getUnitPrice() != null) {
					this.addOrderLine(userContext, orderLine);
				}
				productSpecialCode.postProcessCode(orderLine);
				if (deletedId != null) {
					OrderLineDto deletedOrderLine = new OrderLineDto();
					deletedOrderLine.setId(deletedId);
					deletedOrderLine.setDinnerTable(orderLine.getDinnerTable());
					this.removeOrderLine(userContext, deletedOrderLine);
					if (deletedOrderLine.getDinnerTable() == null) {
						// Set Dinner Table to null means that the dinner table is deleted. 
						orderLine.setDinnerTable(null);
					}
				}
				// Process sum quantities and sum amounts.
//				this.processDinnerTable((DinnerTableDto) userContext.getCurrentTable(), orderLine, false);
deltaTime = System.currentTimeMillis() - deltaTime;
System.out.println("4) processOrderLineByCode Delta Time = " + deltaTime);
deltaTime = System.currentTimeMillis();
			} catch (MdoException e) {
				logger.error("message.error.business.DefaultDinnerTablesManager.processOrderLineByCode", new Object[]{orderLine}, e);
				throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.processOrderLineByCode", new Object[]{orderLine}, e);
			}
		}
	}
	
	@Override
	public void addOrderLine(MdoUserContext userContext, OrderLineDto orderLine) throws MdoBusinessException {
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
				dinnerTableId = this.createFromUserContext(castedUserContext, orderLine.getDinnerTable().getNumber());
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
					orderLineToBeSaved.setProduct(product);
				}

				// Fill order line
				orderLineToBeSaved.setId(orderLine.getId());
				orderLineToBeSaved.setQuantity(orderLine.getQuantity());
				orderLineToBeSaved.setLabel(orderLine.getLabel());
				orderLineToBeSaved.setUnitPrice(orderLine.getUnitPrice());
				// Amount == quantity * unit price
				orderLine.setAmount(orderLine.getQuantity().multiply(orderLine.getUnitPrice()));
				// Maybe don't have to store in database
				orderLineToBeSaved.setAmount(orderLine.getAmount());
				// Fill VAT depends on product in catalog, or new/old order line.
				ValueAddedTax vat = new ValueAddedTax();
				Long vatId = orderLine.getVat().getId();
				
				if (orderLine.getId() == null) {
					if (vatId == null) {
						// Set default VAT from restaurant.
						vatId = userContext.getUserAuthentication().getRestaurant().getVat().getId();
					}
					vat.setId(vatId);
					orderLineToBeSaved.setVat(vat);
					dao.insert(orderLineToBeSaved);
				} else {
					// Load the order line from data base in order to subtract in sums
//					OrderLine oldOrderLine = new OrderLine();
//					oldOrderLine.setId(orderLineToBeSaved.getId());
					//dao.load(oldOrderLine);
					OrderLine oldOrderLine = orderLinesDao.getOrderLine(orderLineToBeSaved.getId());

					if (vatId == null) {
						// Set default VAT the last entry.
						vatId = oldOrderLine.getVat().getId();
					}
					vat.setId(vatId);
					orderLineToBeSaved.setVat(vat);
					
					// Subtract because this will be added later
					if (tableToBeSaved.getAmountsSum() == null) {
						tableToBeSaved.setAmountsSum(BigDecimal.ZERO);
					}
					if (tableToBeSaved.getQuantitiesSum() == null) {
						tableToBeSaved.setQuantitiesSum(BigDecimal.ZERO);
					}
					tableToBeSaved.setAmountsSum(tableToBeSaved.getAmountsSum().subtract(oldOrderLine.getAmount()));
					tableToBeSaved.setQuantitiesSum(tableToBeSaved.getQuantitiesSum().subtract(oldOrderLine.getQuantity()));
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
			dinnerTable = daoCasted.displayTableByNumber(userContext.getUserAuthentication().getRestaurant().getId(), number);
		} catch (MdoException e) {
			logger.error("message.error.business.DefaultDinnerTablesManager.findTableByNumber", new Object[]{number}, e);
			throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.findTableByNumber", new Object[]{number}, e);
		}

		result = (DinnerTableDto)((IDinnerTablesManagerAssembler) assembler).marshal(dinnerTable, userContext);
		
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
	public Long createFromUserContext(MdoUserContext userContext, String dinnerTableNumber) throws MdoBusinessException {
		Long result = null;
		if (dinnerTableNumber == null) {
			logger.error("message.error.business.DefaultDinnerTablesManager.createFromUserContext.number.null");
			throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.createFromUserContext.number.null");
		}
		DinnerTableDto myDinnerTable = userContext.getMyDinnerTable(dinnerTableNumber);
		if (myDinnerTable == null) {
			logger.error("message.error.business.DefaultDinnerTablesManager.createFromUserContext.myDinnerTable.null");
			throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.createFromUserContext.myDinnerTable.null");
		}
		boolean isDinnerTableFree = this.isDinnerTableFree(userContext, dinnerTableNumber);
		if (!isDinnerTableFree) {
			logger.error("message.error.business.DefaultDinnerTablesManager.createFromUserContext.table.occupied");
			throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.createFromUserContext.table.occupied", new Object[] {dinnerTableNumber});
		}
		
		RestaurantDto userContextRestaurant = userContext.getUserAuthentication().getRestaurant();
		
		DinnerTable dinnerTableToBeSaved = new DinnerTable();
		dinnerTableToBeSaved.setNumber(dinnerTableNumber);
		dinnerTableToBeSaved.setCustomersNumber(myDinnerTable.getCustomersNumber());
		//dtb_reduction_ratio, dtb_amount_pay, dtb_reduction_ratio_changed, 
		Restaurant restaurant = new Restaurant();
		restaurant.setId(userContextRestaurant.getId());
		dinnerTableToBeSaved.setRestaurant(restaurant);
		UserAuthentication user = new UserAuthentication();
		user.setId(userContext.getUserAuthentication().getId());
		dinnerTableToBeSaved.setUser(user);
		Date registrationDate = new Date();
		dinnerTableToBeSaved.setRegistrationDate(registrationDate);
		TableTypeDto typeDto = findTableType(userContextRestaurant.getPrefixTableNames(), dinnerTableNumber, userContextRestaurant.getDefaultTableType());
		TableType type = new TableType();
		type.setId(typeDto.getId());
		dinnerTableToBeSaved.setType(type);
		
		try {
			dao.insert(dinnerTableToBeSaved);
		} catch (MdoException e) {
			logger.error("message.error.business.DefaultDinnerTablesManager.createFromUserContext.insert.table", new Object[]{dinnerTableNumber}, e);
			throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.createFromUserContext.insert.table", new Object[]{dinnerTableNumber}, e);
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
	public List<IMdoDtoBean> findAllFreeTables(MdoUserContext userContext) throws MdoBusinessException {
		List<IMdoDtoBean> result = new ArrayList<IMdoDtoBean>();
			
		Long restaurantId = ((MdoUserContext) userContext).getUserAuthentication().getRestaurant().getId();
		// Get list of non cashing dinner tables
		try {
			result = ((IDinnerTablesManagerAssembler) assembler).marshalFreeTables(((IDinnerTablesDao) dao).findAllFreeTables(restaurantId), userContext);
		} catch (MdoException e) {
			logger.error("message.error.business.DefaultDinnerTablesManager.findAllFreeTables", e);
			throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.findAllFreeTables", e);
		}

		return result;
	}

	@Override
	public void updateCustomersNumber(Long dinnerTableId, Integer customersNumber) throws MdoBusinessException {
		try {
			((IDinnerTablesDao) dao).updateCustomersNumber(dinnerTableId, customersNumber);
		} catch (MdoException e) {
			logger.error("message.error.business.DefaultDinnerTablesManager.updateCustomersNumber", e);
			throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.updateCustomersNumber", e);
		}
	}

	@Override
	public void removeOrderLine(MdoUserContext userContext, OrderLineDto orderLine) throws MdoBusinessException {
		try {
			OrderLine deletedOrderLine = new OrderLine();
			deletedOrderLine.setId(orderLine.getId());
			((IDinnerTablesDao) dao).removeOrderLine(deletedOrderLine);
			// Check if the table order line list is empty
			int size = ((IDinnerTablesDao) dao).getOrderLinesSize(orderLine.getDinnerTable().getId());
			if (size == 0) {
				DinnerTable dinnerTable = new DinnerTable();
				dinnerTable.setId(orderLine.getDinnerTable().getId());
				((IDinnerTablesDao) dao).delete(dinnerTable);
				// Set Dinner Table to null means that the dinner table is deleted. 
				orderLine.setDinnerTable(null);
			}
			// Process sum quantities and sum amounts. Be aware when the table is deleted
			//this.processCurrentDinnerTable((DinnerTableDto) userContext.getCurrentTable(), orderLine, true);

		} catch (MdoException e) {
			logger.error("message.error.business.DefaultDinnerTablesManager.removeOrderLine", e);
			throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.removeOrderLine", e);
		}
	}
	
	/**
	 * Process sum quantity and sum unit price.
	 * @param dinnerTable the dinner table fields to be changed
	 */
	private void processCurrentDinnerTable(DinnerTableDto dinnerTable, OrderLineDto orderLine, boolean orderLineToBeRemoved) {
		BigDecimal quantitiesSum = dinnerTable.getQuantitiesSum();
		BigDecimal amountsSum = dinnerTable.getAmountsSum();
		if (quantitiesSum != null) {
			if (orderLineToBeRemoved) {
				quantitiesSum = quantitiesSum.subtract(orderLine.getQuantity());
			} else {
				quantitiesSum = quantitiesSum.add(orderLine.getQuantity());
			}
		}
		if (amountsSum != null) {
			if (orderLineToBeRemoved) {
				amountsSum = amountsSum.subtract(orderLine.getAmount());
			} else {
				amountsSum = amountsSum.add(orderLine.getAmount());
			}
		}
		dinnerTable.setQuantitiesSum(quantitiesSum);
		dinnerTable.setAmountsSum(amountsSum);
	}
	
}
