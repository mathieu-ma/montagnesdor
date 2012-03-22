package fr.mch.mdo.restaurant.dao.tables.hibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.dao.beans.DinnerTable;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.dao.beans.OrderLine;
import fr.mch.mdo.restaurant.dao.beans.Product;
import fr.mch.mdo.restaurant.dao.beans.ProductSpecialCode;
import fr.mch.mdo.restaurant.dao.beans.TableCashing;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.hibernate.TransactionSession;
import fr.mch.mdo.restaurant.dao.tables.IDinnerTablesDao;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultDinnerTablesDao extends DefaultDaoServices implements IDinnerTablesDao 
{
	private static class LazyHolder {
		private static IDinnerTablesDao instance = new DefaultDinnerTablesDao(LoggerServiceImpl.getInstance().getLogger(DefaultDinnerTablesDao.class.getName()), new DinnerTable());
	}

	private DefaultDinnerTablesDao(ILogger logger, IMdoDaoBean bean) {
		super(true);
		this.setLogger(logger);
		this.setBean(bean);
	}

	public static IDinnerTablesDao getInstance() {
		return LazyHolder.instance;
	}

	public DefaultDinnerTablesDao() {
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<IMdoBean> findAllByPrefixNumber(Long restaurantId, String prefixTableNumber, boolean... isLazy) throws MdoDataBeanException {
		List<IMdoBean> result = new ArrayList<IMdoBean>();

//		Map<String, Entry<PropertiesRestrictions, Object>> propertyValueMap = new HashMap<String, Entry<PropertiesRestrictions, Object>>();
//		String property = "restaurant.id";
//		Entry<PropertiesRestrictions, Object> value = new MdoEntry<PropertiesRestrictions, Object>(PropertiesRestrictions.EQUALS, restaurantId);
//		propertyValueMap.put(property, value);

//		property = "number";
//		value = new MdoEntry<PropertiesRestrictions, Object>(PropertiesRestrictions.LIKE, prefixTableNumber);
//		propertyValueMap.put(property, value);

//		// Only the ones who cashing date is null
//		property = "cashingDate";
//		value = new MdoEntry<PropertiesRestrictions, Object>(PropertiesRestrictions.IS_NULL, null);
//		propertyValueMap.put(property, value);
//		result = super.findByPropertiesRestrictions(propertyValueMap, false);

		List<MdoCriteria> criterias = new ArrayList<MdoCriteria>();
		criterias.add(new MdoCriteria("number", PropertiesRestrictions.LIKE, prefixTableNumber + "%"));
		criterias.add(new MdoCriteria("restaurant.id", PropertiesRestrictions.EQUALS, restaurantId));
		criterias.add(new MdoCriteria("cashing.id", CriteriaSpecification.LEFT_JOIN, PropertiesRestrictions.IS_NULL, null));

		result = super.findByPropertiesRestrictions(criterias, false);

		return result;
	}

	@Override
	public Map<Long, String> findAllTableNamesByPrefix(Long restaurantId, String prefixTableNumber) throws MdoDataBeanException {
		Map<Long, String> result = new HashMap<Long, String>();
		
		List<MdoCriteria> criterias = new ArrayList<MdoCriteria>();
		criterias.add(new MdoCriteria("number", PropertiesRestrictions.LIKE, prefixTableNumber + "%"));
		criterias.add(new MdoCriteria("restaurant.id", PropertiesRestrictions.EQUALS, restaurantId));
		criterias.add(new MdoCriteria("cashing.id", CriteriaSpecification.LEFT_JOIN, PropertiesRestrictions.IS_NULL, null));
		// Only select id and number fields
		criterias.add(new MdoCriteria("id", PropertiesRestrictions.PROJECTION, null));
		criterias.add(new MdoCriteria("number", PropertiesRestrictions.PROJECTION, null));
		
		@SuppressWarnings("unchecked")
		List<Object[]> list = super.findByPropertiesRestrictions(criterias, false);
		for (Iterator<Object[]> iterator = list.iterator(); iterator.hasNext();) {
			Object[] objects = iterator.next();
			Long id = (Long) objects[0];
			String number = (String) objects[1];
			result.put(id, number);
		}

		return result;
	}

	@Override
	public Integer findCustomersNumberByNumber(Long restaurantId, String number) throws MdoDataBeanException {
		Integer result = null;

		List<MdoCriteria> criterias = new ArrayList<MdoCriteria>();
		criterias.add(new MdoCriteria("number", PropertiesRestrictions.EQUALS, number));
		criterias.add(new MdoCriteria("restaurant.id", PropertiesRestrictions.EQUALS, restaurantId));
		criterias.add(new MdoCriteria("cashing.id", CriteriaSpecification.LEFT_JOIN, PropertiesRestrictions.IS_NULL));
		criterias.add(new MdoCriteria("customersNumber", PropertiesRestrictions.PROJECTION, Projections.property("customersNumber")));
		Object object = super.uniqueResult(super.findByCriteria(super.getBean().getClass(), criterias));
		if (object != null) {
			result = new Integer(object.toString());
		}
		return result;
	}

	@Override
	public DinnerTable findByNumber(Long restaurantId, String number, boolean... isLazy) throws MdoDataBeanException {
		// Only the one who cashing date is null: in SQL Standard, 
		// if unique constraint is on 2 fields then the values of the 2 fields could be (1, null), (1, null)

		List<MdoCriteria> criterias = new ArrayList<MdoCriteria>();
		criterias.add(new MdoCriteria("number", PropertiesRestrictions.EQUALS, number));
		criterias.add(new MdoCriteria("restaurant.id", PropertiesRestrictions.EQUALS, restaurantId));
		criterias.add(new MdoCriteria("cashing.id", CriteriaSpecification.LEFT_JOIN, PropertiesRestrictions.IS_NULL));

		return (DinnerTable) super.uniqueResult(super.findByCriteria(super.getBean().getClass(), criterias, isLazy));
	}

	@Override
	public DinnerTable displayTableByNumber(Long restaurantId, String number) throws MdoDataBeanException {
		DinnerTable result = null;

		// Dinner Table part
		List<MdoCriteria> criterias = new ArrayList<MdoCriteria>();
		criterias.add(new MdoCriteria("number", PropertiesRestrictions.EQUALS, number));
		criterias.add(new MdoCriteria("restaurant.id", PropertiesRestrictions.EQUALS, restaurantId));
		// Only the one who cashing date is null: in SQL Standard, 
		// if unique constraint is on 2 fields then the values of the 2 fields could be (1, null), (1, null)
		criterias.add(new MdoCriteria("cashing.id", CriteriaSpecification.LEFT_JOIN, PropertiesRestrictions.IS_NULL));

		// Only select needed fields
		criterias.add(new MdoCriteria("id", PropertiesRestrictions.PROJECTION));
		criterias.add(new MdoCriteria("customersNumber", PropertiesRestrictions.PROJECTION));
		criterias.add(new MdoCriteria("registrationDate", PropertiesRestrictions.PROJECTION));
		criterias.add(new MdoCriteria("printingDate", PropertiesRestrictions.PROJECTION));
		criterias.add(new MdoCriteria("reductionRatio", PropertiesRestrictions.PROJECTION));
		criterias.add(new MdoCriteria("reductionRatioChanged", PropertiesRestrictions.PROJECTION));
		
		Object[] dinnerTable = (Object[]) super.uniqueResult(super.findByCriteria(super.getBean().getClass(), criterias, true));
		
		if (dinnerTable != null) {
			result = new DinnerTable();
			result.setNumber(number);
			
			int index = 0;
			Long id = (Long) dinnerTable[index++];
			Integer customersNumber = (Integer) dinnerTable[index++];
			Date registrationDate = (Date) dinnerTable[index++];
			Date printingDate = (Date) dinnerTable[index++];
			BigDecimal reductionRatio = (BigDecimal) dinnerTable[index++];
			Boolean reductionRatioChanged = (Boolean) dinnerTable[index++];
			result.setId(id);
			result.setCustomersNumber(customersNumber);
			result.setRegistrationDate(registrationDate);
			result.setPrintingDate(printingDate);
			if (reductionRatio == null) {
				reductionRatio = BigDecimal.ZERO;	
			}
			result.setReductionRatio(reductionRatio);
			result.setReductionRatioChanged(reductionRatioChanged);

			// Orders Part
			criterias.clear();
			criterias.add(new MdoCriteria("dinnerTable.id", PropertiesRestrictions.EQUALS, result.getId()));
			// We must use the 2 following lines because we want left outer join.
			// If we comment the 2 following lines, we will have inner join so order lines without product id will not get.
			criterias.add(new MdoCriteria("productSpecialCode.id", CriteriaSpecification.LEFT_JOIN, PropertiesRestrictions.DEFAULT));
			criterias.add(new MdoCriteria("product.id", CriteriaSpecification.LEFT_JOIN, PropertiesRestrictions.DEFAULT));

			// Only select needed fields
			criterias.add(new MdoCriteria("id", PropertiesRestrictions.PROJECTION));
			criterias.add(new MdoCriteria("quantity", PropertiesRestrictions.PROJECTION));
			criterias.add(new MdoCriteria("label", PropertiesRestrictions.PROJECTION));
			criterias.add(new MdoCriteria("unitPrice", PropertiesRestrictions.PROJECTION));
			criterias.add(new MdoCriteria("amount", PropertiesRestrictions.PROJECTION));

//				criterias.add(new MdoCriteria("product", PropertiesRestrictions.PROJECTION));
//				criterias.add(new MdoCriteria("productSpecialCode", PropertiesRestrictions.PROJECTION));
			// These 3 lines is used to build the code
			criterias.add(new MdoCriteria("product.code", PropertiesRestrictions.PROJECTION));
			criterias.add(new MdoCriteria("productSpecialCode.shortCode", PropertiesRestrictions.PROJECTION));
			criterias.add(new MdoCriteria("productSpecialCode.code.name", PropertiesRestrictions.PROJECTION));

			criterias.add(new MdoCriteria("label", PropertiesRestrictions.ORDER, Boolean.FALSE));

			List<?> orders = super.findByCriteria(OrderLine.class, criterias, true);
			BigDecimal quantitiesSum = BigDecimal.ZERO;
			BigDecimal amountsSum = BigDecimal.ZERO;
			if (orders != null) {
				LinkedHashSet<OrderLine> buildOrders = new LinkedHashSet<OrderLine>();
				for (Object object : orders) {
					Object[] arrays = (Object[]) object;
					OrderLine orderLine = new OrderLine();
					index = 0;
					id = (Long) arrays[index++];
					BigDecimal quantity = (BigDecimal) arrays[index++];
					String label = (String) arrays[index++];
					BigDecimal unitPrice = (BigDecimal) arrays[index++];
					BigDecimal amount = (BigDecimal) arrays[index++];
					String productCode = (String) arrays[index++];
					String productSpecialCodeShortCode = (String) arrays[index++];
					String productSpecialCodeCodeName = (String) arrays[index++];
					orderLine.setId(id);
					orderLine.setQuantity(quantity);
					orderLine.setLabel(label);
					orderLine.setUnitPrice(unitPrice);
					orderLine.setAmount(amount);
					if (productCode != null) {
						Product product = new Product();
						product.setCode(productCode);
						orderLine.setProduct(product);
					}
					if (productSpecialCodeShortCode != null) {
						ProductSpecialCode productSpecialCode = new ProductSpecialCode();
						productSpecialCode.setShortCode(productSpecialCodeShortCode);
						MdoTableAsEnum code = new MdoTableAsEnum();
						code.setName(productSpecialCodeCodeName);
						productSpecialCode.setCode(code);
						orderLine.setProductSpecialCode(productSpecialCode);
					}
					buildOrders.add(orderLine);
					// Assume that the quantity is never null; 
					quantitiesSum = quantitiesSum.add(quantity);
					// Assume that the amount is never null; 
					amountsSum = amountsSum.add(amount);
				}
				result.setOrders(buildOrders);
				
				result.setQuantitiesSum(quantitiesSum);
				result.setAmountsSum(amountsSum);
				// Assume that the amountsSum and reductionRatio are never null; 
				BigDecimal amountPay = result.getReductionRatio().multiply(amountsSum).divide(new BigDecimal(100));
				result.setAmountPay(amountPay);
			}
		}
		
		return result;
	}

	@Override
	public void updateReductionRatio(Long dinnerTableId, BigDecimal reductionRatio, Boolean reductionRatioChanged, BigDecimal amountPay) throws MdoDataBeanException {
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("reductionRatio", reductionRatio);
		fields.put("reductionRatioChanged", reductionRatioChanged);
		fields.put("amountPay", amountPay);
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("id", dinnerTableId);
		super.updateFieldsByKeys(fields, keys);
	}

	@Override
	public void addOrderLine(OrderLine orderLine) throws MdoDataBeanException {
		super.insert(orderLine);
	}

	@Override
	public void updateOrderLine(OrderLine orderLine) throws MdoDataBeanException {
		super.update(orderLine);		
	}

	@Override
	public void removeOrderLine(OrderLine orderLine) throws MdoDataBeanException {
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("id", orderLine.getId());
		super.deleteByKeys(OrderLine.class, keys);
	}

	@Override
	public OrderLine findOrderLine(Long orderLineId) throws MdoDataBeanException {
		OrderLine result = new OrderLine();
		result.setId(orderLineId);
		
		super.load(result, true);
		
		return result;
	}

	@Override
	public boolean isDinnerTableFree(Long restaurantId, String number) throws MdoException {
		Integer customersNumber = this.findCustomersNumberByNumber(restaurantId, number);
		
		return customersNumber == null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IMdoBean> findAllFreeTables(Long restaurantId) throws MdoException {
		List<IMdoBean> result = new ArrayList<IMdoBean>();
		List<MdoCriteria> criterias = new ArrayList<MdoCriteria>();
		criterias.add(new MdoCriteria("restaurant.id", PropertiesRestrictions.EQUALS, restaurantId));
		criterias.add(new MdoCriteria("cashing.id", CriteriaSpecification.LEFT_JOIN, PropertiesRestrictions.IS_NULL, null));

		result = super.findByPropertiesRestrictions(criterias, true);
		
		return result;
	}

	@Override
	public void updateCustomersNumber(Long dinnerTableId, Integer customersNumber) throws MdoDataBeanException {
		try {
			TransactionSession transactionSession = super.beginTransaction();
	
			Session session = transactionSession.getSession();
			session.createQuery("Update DinnerTable dinnerTable set " +
					" dinnerTable.customersNumber = :customersNumber " +
					" WHERE dinnerTable.id=" + dinnerTableId).setInteger("customersNumber", customersNumber).executeUpdate();
			super.endTransaction(transactionSession, null, true);
		} catch (HibernateException e) {
			super.getLogger().error("message.error.dao.DefaultDinnerTablesDao.updateCustomersNumber", e);
			throw new MdoDataBeanException("message.error.dao.DefaultDinnerTablesDao.updateCustomersNumber", e);
		} catch (Exception e) {
			super.getLogger().error("message.error.dao.DefaultDinnerTablesDao.updateCustomersNumber", e);
			throw new MdoDataBeanException("message.error.dao.DefaultDinnerTablesDao.updateCustomersNumber", e);
		} finally {
			try {
				super.closeSession();
			} catch (HibernateException e) {
				super.getLogger().error("message.error.dao.session.close", e);
				throw new MdoDataBeanException("message.error.dao.session.close", e);
			}
		}
	}

	@Override
	public int getOrderLinesSize(Long dinnerTableId) throws MdoException {
		int result = 0;

		List<MdoCriteria> criterias = new ArrayList<MdoCriteria>();
		criterias.add(new MdoCriteria("dinnerTable.id", PropertiesRestrictions.EQUALS, dinnerTableId));
		criterias.add(new MdoCriteria(null, PropertiesRestrictions.PROJECTION_ROW_COUNT, Projections.rowCount()));
//		criterias.add(new MdoCriteria("quantity", PropertiesRestrictions.PROJECTION_SUM, Projections.sum("quantity")));
//		criterias.add(new MdoCriteria("amount", PropertiesRestrictions.PROJECTION_SUM, Projections.sum("amount")));
		Object object = super.uniqueResult(super.findByCriteria(OrderLine.class, criterias));
		if (object != null) {
			result = new Integer(object.toString());
		}

		return result;
	}

	/**
	 * This method delete the dinner table.
	 * In order to do this, it has to delete by cascade in the following order:
	 * 1) Delete the SQL Table t_order_line
	 * 2) Delete the SQL Table t_table_bill
	 * 3) Delete the SQL Table t_table_cashing
	 * 4) Delete the SQL Table t_cashing_type
	 * 5) Delete the SQL Table t_table_credit
	 * 6) Delete the SQL Table t_table_vat
	 * 
	 */
	@Override
	public IMdoBean delete(IMdoBean daoBean, boolean... isLazy) throws MdoDataBeanException {
		DinnerTable result = (DinnerTable) daoBean;
		try {
			TransactionSession transactionSession = super.beginTransaction();
			
			Session session = transactionSession.getSession();
			session.load(result, result.getId());
			result.getOrders().clear();
			result.getBills().clear();
			
			TableCashing cashing = result.getCashing();
			if (cashing != null) {
				cashing.getCashingTypes().clear();
				result.setCashing(null);
			}
			
			result.getCredits().clear();
			result.getVats().clear();
			session.delete(result);

			super.endTransaction(transactionSession, result, isLazy);
		} catch (HibernateException e) {
			super.getLogger().error("message.error.dao.delete", new Object[] { getBean().getClass().getName(), result }, e);
			throw new MdoDataBeanException(e);
		} catch (Exception e) {
			super.getLogger().error("message.error.dao.delete", new Object[] { getBean().getClass().getName(), result }, e);
			throw new MdoDataBeanException(e);
		} finally {
			try {
				super.closeSession();
			} catch (HibernateException e) {
				super.getLogger().error("message.error.dao.session.close", e);
				throw new MdoDataBeanException("message.error.dao.session.close", e);
			}
		}
		return result;
	}
}
