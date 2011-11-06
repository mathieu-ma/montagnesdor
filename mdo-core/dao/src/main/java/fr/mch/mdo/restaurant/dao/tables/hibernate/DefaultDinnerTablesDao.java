package fr.mch.mdo.restaurant.dao.tables.hibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import fr.mch.mdo.restaurant.dao.beans.OrderLine;
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
		criterias.add(new MdoCriteria("id", PropertiesRestrictions.PROJECTION, Projections.property("number")));
		criterias.add(new MdoCriteria("number", PropertiesRestrictions.PROJECTION, Projections.property("number")));
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
		criterias.add(new MdoCriteria("cashing.id", CriteriaSpecification.LEFT_JOIN, PropertiesRestrictions.IS_NULL, null));
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
		criterias.add(new MdoCriteria("cashing.id", CriteriaSpecification.LEFT_JOIN, PropertiesRestrictions.IS_NULL, null));

		return (DinnerTable) super.uniqueResult(super.findByCriteria(super.getBean().getClass(), criterias, isLazy));
	}

	@Override
	public void updateReductionRatio(Long dinnerTableId, BigDecimal reductionRatio, Boolean reductionRatioChanged, BigDecimal amountPay) throws MdoDataBeanException {
		try {
			TransactionSession transactionSession = super.beginTransaction();

			Session session = transactionSession.getSession();
			session.createQuery("Update DinnerTable dinnerTable set " +
					" dinnerTable.reductionRatio = :reductionRatio, " +
					" dinnerTable.reductionRatioChanged = :reductionRatioChanged, " +
					" dinnerTable.amountPay = :amountPay " +
					" WHERE dinnerTable.id="+dinnerTableId).setBigDecimal("reductionRatio", reductionRatio)
					.setBoolean("reductionRatioChanged", reductionRatioChanged).setBigDecimal("amountPay", amountPay).executeUpdate();
			

			super.endTransaction(transactionSession, null, true);
		} catch (HibernateException e) {
			super.getLogger().error("message.error.dao.save", new Object[] { getBean().getClass().getName(), "DinnerTable" }, e);
			throw new MdoDataBeanException("message.error.dao.save", new Object[] { getBean().getClass().getName(), "DinnerTable" }, e);
		} catch (Exception e) {
			super.getLogger().error("message.error.dao.save", new Object[] { getBean().getClass().getName(), "DinnerTable" }, e);
			throw new MdoDataBeanException("message.error.dao.save", new Object[] { getBean().getClass().getName(), "DinnerTable" }, e);
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
	public void addOrderLine(OrderLine orderLine) throws MdoDataBeanException {
		super.insert(orderLine);
	}

	@Override
	public void updateOrderLine(OrderLine orderLine) throws MdoDataBeanException {
		super.update(orderLine);		
	}

	@Override
	public void removeOrderLine(OrderLine orderLine) throws MdoDataBeanException {
		try {
			TransactionSession transactionSession = super.beginTransaction();

			Session session = transactionSession.getSession();
			session.createQuery("DELETE OrderLine orderLine WHERE orderLine.id="+orderLine.getId()).executeUpdate();
			

			super.endTransaction(transactionSession, orderLine, true);
		} catch (HibernateException e) {
			super.getLogger().error("message.error.dao.delete", new Object[] { getBean().getClass().getName(), orderLine }, e);
			throw new MdoDataBeanException("message.error.dao.delete", new Object[] { getBean().getClass().getName(), orderLine }, e);
		} catch (Exception e) {
			super.getLogger().error("message.error.dao.delete", new Object[] { getBean().getClass().getName(), orderLine }, e);
			throw new MdoDataBeanException("message.error.dao.delete", new Object[] { getBean().getClass().getName(), orderLine }, e);
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

		result = super.findByPropertiesRestrictions(criterias, false);
		
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
}
