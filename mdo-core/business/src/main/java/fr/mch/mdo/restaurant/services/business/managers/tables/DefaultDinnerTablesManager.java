package fr.mch.mdo.restaurant.services.business.managers.tables;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.DinnerTable;
import fr.mch.mdo.restaurant.dao.beans.OrderLine;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.beans.TableBill;
import fr.mch.mdo.restaurant.dao.beans.TableCredit;
import fr.mch.mdo.restaurant.dao.beans.TableType;
import fr.mch.mdo.restaurant.dao.beans.TableVat;
import fr.mch.mdo.restaurant.dao.beans.UserAuthentication;
import fr.mch.mdo.restaurant.dao.orders.IOrderLinesDao;
import fr.mch.mdo.restaurant.dao.orders.hibernate.DefaultOrderLinesDao;
import fr.mch.mdo.restaurant.dao.products.IProductSpecialCodesDao;
import fr.mch.mdo.restaurant.dao.products.IProductsDao;
import fr.mch.mdo.restaurant.dao.products.hibernate.DefaultProductSpecialCodesDao;
import fr.mch.mdo.restaurant.dao.products.hibernate.DefaultProductsDao;
import fr.mch.mdo.restaurant.dao.tables.IDinnerTablesDao;
import fr.mch.mdo.restaurant.dao.tables.hibernate.DefaultDinnerTablesDao;
import fr.mch.mdo.restaurant.dto.beans.DinnerTableDto;
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
	public IMdoDtoBean update(IMdoDtoBean dtoBean) throws MdoBusinessException {
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
			
			return assembler.marshal((IMdoDaoBean) dao.update(daoBean));
		} catch (MdoException e) {
			logger.error("message.error.administration.business.save", e);
			throw new MdoBusinessException("message.error.administration.business.save", e);
		}
	}

	@Override
	public Map<Long, String> findAllTableNamesByPrefix(Long restaurantId, String prefixTableNumber) throws MdoBusinessException {
		Map<Long, String> result = new HashMap<Long, String>();
		IDinnerTablesDao daoX = (IDinnerTablesDao) dao;
		try {
			result = daoX.findAllNumberByPrefixNumber(restaurantId, prefixTableNumber);
		} catch (MdoException e) {
			logger.error("message.error.business.DefaultDinnerTablesManager.findAllTableNamesByPrefix", new Object[]{prefixTableNumber}, e);
			throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.findAllTableNamesByPrefix", new Object[]{prefixTableNumber}, e);
		}

		return result;
	}

	@Override
	public DinnerTableDto findTableByNumber(Long restaurantId, String number) throws MdoBusinessException {
		DinnerTableDto result = null;
		IDinnerTablesDao daoCasted = (IDinnerTablesDao) dao;
		// Get dinner table
		DinnerTable dinnerTable = null;
		try {
			dinnerTable = daoCasted.findTable(1L);
		} catch (MdoException e) {
			logger.error("message.error.business.DefaultDinnerTablesManager.findTableByNumber", new Object[]{number}, e);
			throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.findTableByNumber", new Object[]{number}, e);
		}

		result = (DinnerTableDto)((IDinnerTablesManagerAssembler) assembler).marshal(dinnerTable);
		
		return result;
	}

	@Override
	public Map<Long, String> findAllProductCodesByPrefix(Long restaurantId, String prefixProductCode) throws MdoBusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	private Long processDinnerTable(Long userAuthenticationId, Long restaurantId, String[] prefixTakeawayNames, 
			BigDecimal takeawayMinAmountReduction, BigDecimal takeawayBasicReduction, Long tableTypeId,
			String dinnerTableNumber, Integer customersNumber) throws MdoBusinessException {
		BigDecimal reductionRatio = null;
		// Amount + current order line amount
		BigDecimal amount = null;
		if (this.startsWith(dinnerTableNumber, prefixTakeawayNames) && takeawayMinAmountReduction.compareTo(amount)<0) {
			// Take away + amount > specific value
			reductionRatio = takeawayBasicReduction;
		}
		Long dinnerTableId = this.createFromUserContext(userAuthenticationId, restaurantId, prefixTakeawayNames, tableTypeId, dinnerTableNumber, 
				customersNumber, reductionRatio);
		return dinnerTableId;
	}
	
	@Override
	public Long createFromUserContext(Long userAuthenticationId, Long restaurantId, String[] prefixTakeawayNames, Long tableTypeId,
			String dinnerTableNumber, Integer customersNumber, BigDecimal reductionRatio) throws MdoBusinessException {
		Long result = null;
		if (dinnerTableNumber == null) {
			logger.error("message.error.business.DefaultDinnerTablesManager.createFromUserContext.number.null");
			throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.createFromUserContext.number.null");
		}
		if (customersNumber == null) {
			logger.error("message.error.business.DefaultDinnerTablesManager.createFromUserContext.customerNumber.null");
			throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.createFromUserContext.customerNumber.null");
		}
		boolean isDinnerTableFree = this.isDinnerTableFree(restaurantId, dinnerTableNumber);
		if (!isDinnerTableFree) {
			logger.error("message.error.business.DefaultDinnerTablesManager.createFromUserContext.table.occupied");
			throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.createFromUserContext.table.occupied", new Object[] {dinnerTableNumber});
		}
		
		DinnerTable dinnerTableToBeSaved = new DinnerTable();
		dinnerTableToBeSaved.setNumber(dinnerTableNumber);
		dinnerTableToBeSaved.setCustomersNumber(customersNumber);
		dinnerTableToBeSaved.setReductionRatio(reductionRatio);
		Restaurant restaurant = new Restaurant();
		restaurant.setId(restaurantId);
		dinnerTableToBeSaved.setRestaurant(restaurant);
		UserAuthentication user = new UserAuthentication();
		user.setId(userAuthenticationId);
		dinnerTableToBeSaved.setUser(user);
		Date registrationDate = new Date();
		dinnerTableToBeSaved.setRegistrationDate(registrationDate);
		TableType type = new TableType();
		type.setId(tableTypeId);
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
	
	private boolean startsWith(String data, String[] prefixes) {
		boolean result = false;
		for (String prefix : prefixes) {
			if (data.startsWith(prefix)) {
				result = true;
				break;
			}
		}
		
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
	public boolean isDinnerTableFree(Long restaurantId, String number) throws MdoBusinessException {
		boolean result = true;
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
	public void updateCustomersNumber(Long dinnerTableId, Integer customersNumber) throws MdoBusinessException {
		try {
			((IDinnerTablesDao) dao).updateCustomersNumber(dinnerTableId, customersNumber);
		} catch (MdoException e) {
			logger.error("message.error.business.DefaultDinnerTablesManager.updateCustomersNumber", e);
			throw new MdoBusinessException("message.error.business.DefaultDinnerTablesManager.updateCustomersNumber", e);
		}
	}
}
