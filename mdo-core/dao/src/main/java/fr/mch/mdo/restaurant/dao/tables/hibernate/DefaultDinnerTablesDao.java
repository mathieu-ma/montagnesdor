package fr.mch.mdo.restaurant.dao.tables.hibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.sql.JoinType;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.dao.beans.DinnerTable;
import fr.mch.mdo.restaurant.dao.beans.TableCashing;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.hibernate.TransactionSession;
import fr.mch.mdo.restaurant.dao.tables.IDinnerTablesDao;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;
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

	private List<MdoCriteria> findAllByPrefixNumberCriteria(Long restaurantId, Long userAuthenticationId, String prefixTableNumber) {
		List<MdoCriteria> result = new ArrayList<MdoCriteria>();
		result.add(new MdoCriteria("number", PropertiesRestrictions.LIKE, prefixTableNumber + "%"));
		result.add(new MdoCriteria("restaurant.id", PropertiesRestrictions.EQUALS, restaurantId));
		if (userAuthenticationId != null) {
			result.add(new MdoCriteria("user.id", PropertiesRestrictions.EQUALS, userAuthenticationId));
		}
		result.add(new MdoCriteria("cashing.id", JoinType.LEFT_OUTER_JOIN.getJoinTypeValue(), PropertiesRestrictions.IS_NULL));
		
		return result;
	}

	@Override
	public List<DinnerTable> findAllByPrefixNumber(Long restaurantId, String prefixTableNumber, boolean... isLazy) throws MdoDataBeanException {
		return this.findAllByPrefixNumber(restaurantId, null, prefixTableNumber);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DinnerTable> findAllByPrefixNumber(Long restaurantId, Long userAuthenticationId, String prefixTableNumber, boolean... isLazy) throws MdoDataBeanException {
		List<DinnerTable> result = new ArrayList<DinnerTable>();

		List<MdoCriteria> criterias = this.findAllByPrefixNumberCriteria(restaurantId, userAuthenticationId, prefixTableNumber);
		result = super.findByPropertiesRestrictions(criterias, false);

		return result;
	}

	@Override
	public Map<Long, String> findAllNumberByPrefixNumber(Long restaurantId, String prefixTableNumber) throws MdoDataBeanException {
		return this.findAllNumberByPrefixNumber(restaurantId, null, prefixTableNumber);
	}

	@Override
	public Map<Long, String> findAllNumberByPrefixNumber(Long restaurantId, Long userAuthenticationId, String prefixTableNumber) throws MdoDataBeanException {
		Map<Long, String> result = new HashMap<Long, String>();
		
		List<MdoCriteria> criterias = findAllByPrefixNumberCriteria(restaurantId, userAuthenticationId, prefixTableNumber);
		// Only select id and number fields
		criterias.add(new MdoCriteria("id", PropertiesRestrictions.PROJECTION));
		criterias.add(new MdoCriteria("number", PropertiesRestrictions.PROJECTION));
		
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
	public DinnerTable findTable(Long id) throws MdoDataBeanException {
		DinnerTable result = null;

		// Only the one who cashing date is null: in SQL Standard, 
		// if unique constraint is on 2 fields then the values of the 2 fields could be (1, null), (1, null)

		List<MdoCriteria> criterias = new ArrayList<MdoCriteria>();
		criterias.add(new MdoCriteria("id", PropertiesRestrictions.EQUALS, id));
		// Only the one who cashing date is null: in SQL Standard, 
		// if unique constraint is on 2 fields then the values of the 2 fields could be (1, null), (1, null)
		criterias.add(new MdoCriteria("cashing.id", JoinType.LEFT_OUTER_JOIN.getJoinTypeValue(), PropertiesRestrictions.IS_NULL));

		// Only select needed fields
		criterias.add(new MdoCriteria("id", PropertiesRestrictions.PROJECTION));
		criterias.add(new MdoCriteria("number", PropertiesRestrictions.PROJECTION));
		criterias.add(new MdoCriteria("customersNumber", PropertiesRestrictions.PROJECTION));
		criterias.add(new MdoCriteria("registrationDate", PropertiesRestrictions.PROJECTION));
		criterias.add(new MdoCriteria("printingDate", PropertiesRestrictions.PROJECTION));
		criterias.add(new MdoCriteria("reductionRatio", PropertiesRestrictions.PROJECTION));
		criterias.add(new MdoCriteria("reductionRatioChanged", PropertiesRestrictions.PROJECTION));
		criterias.add(new MdoCriteria("quantitiesSum", PropertiesRestrictions.PROJECTION));
		criterias.add(new MdoCriteria("amountsSum", PropertiesRestrictions.PROJECTION));
		
		result = (DinnerTable) super.uniqueResult(super.findByCriteria(super.getBean().getClass(), DinnerTable.class, criterias, true));

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
	public DinnerTable findIdAndCustomersNumber(Long restaurantId, String number) throws MdoDataBeanException {
		DinnerTable result = this.findIdAndCustomersNumber(restaurantId, null, number);

		return result;
	}

	@Override
	public DinnerTable findIdAndCustomersNumber(Long restaurantId, Long userAuthenticationId, String number) throws MdoDataBeanException {
		DinnerTable result = null;

		/**
		 * TODO
		 * 
		 * SELECT count(orl.orl_id), dtb.dtb_code 
FROM t_order_line orl right join t_dinner_table dtb on orl.dtb_id = dtb.dtb_id
WHERE dtb.dtb_id=2
GROUP BY dtb.dtb_code

		 * 
		 */
		
		List<MdoCriteria> criterias = new ArrayList<MdoCriteria>();
		criterias.add(new MdoCriteria("number", PropertiesRestrictions.EQUALS, number));
		criterias.add(new MdoCriteria("restaurant.id", PropertiesRestrictions.EQUALS, restaurantId));
		criterias.add(new MdoCriteria("cashing.id", JoinType.LEFT_OUTER_JOIN.getJoinTypeValue(), PropertiesRestrictions.IS_NULL));
		if (userAuthenticationId != null) {
			criterias.add(new MdoCriteria("user.id", PropertiesRestrictions.EQUALS, userAuthenticationId));
		}

		// Only select needed fields
		criterias.add(new MdoCriteria("id", PropertiesRestrictions.PROJECTION));
		criterias.add(new MdoCriteria("number", PropertiesRestrictions.PROJECTION));
		criterias.add(new MdoCriteria("customersNumber", PropertiesRestrictions.PROJECTION));
		
		result = (DinnerTable) super.uniqueResult(super.findByCriteria(super.getBean().getClass(), DinnerTable.class, criterias));
		
		return result;
	}

	@Override
	public boolean isDinnerTableFree(Long restaurantId, String number) throws MdoDataBeanException {
		boolean result = false;

		List<MdoCriteria> criterias = new ArrayList<MdoCriteria>();
		criterias.add(new MdoCriteria("number", PropertiesRestrictions.EQUALS, number));
		criterias.add(new MdoCriteria("restaurant.id", PropertiesRestrictions.EQUALS, restaurantId));
		criterias.add(new MdoCriteria("cashing.id", JoinType.LEFT_OUTER_JOIN.getJoinTypeValue(), PropertiesRestrictions.IS_NULL));
		criterias.add(new MdoCriteria("id", PropertiesRestrictions.PROJECTION, Projections.property("id")));
		Object object = super.uniqueResult(super.findByCriteria(super.getBean().getClass(), criterias));
		// if object is not null this means that there is at least one table found.
		result = (object == null);
		
		return result;
	}

	@Override
	public List<DinnerTable> findAllCashed(Long restaurantId, boolean... isLazy) throws MdoDataBeanException {
		return findAllByCriterias(restaurantId, 
				null, 
				null, 
				new MdoCriteria("cashing.id", JoinType.INNER_JOIN.getJoinTypeValue(), PropertiesRestrictions.IS_NOT_NULL), isLazy);
	}

	@Override
	public List<DinnerTable> findAllCashed(Long restaurantId, Long userAuthenticationId, boolean... isLazy) throws MdoDataBeanException {
		return findAllByCriterias(restaurantId, 
				userAuthenticationId, 
				null, 
				new MdoCriteria("cashing.id", JoinType.INNER_JOIN.getJoinTypeValue(), PropertiesRestrictions.IS_NOT_NULL), isLazy);
	}

	@Override
	public List<DinnerTable> findAllPrinted(Long restaurantId, boolean... isLazy) throws MdoDataBeanException {
		return findAllByCriterias(restaurantId, 
				null, 
				new MdoCriteria("printingDate", PropertiesRestrictions.IS_NOT_NULL), 
				new MdoCriteria("cashing.id", JoinType.LEFT_OUTER_JOIN.getJoinTypeValue(), PropertiesRestrictions.IS_NULL), isLazy);
	}

	@Override
	public List<DinnerTable> findAllPrinted(Long restaurantId, Long userAuthenticationId, boolean... isLazy) throws MdoDataBeanException {
		return findAllByCriterias(restaurantId, 
				userAuthenticationId, 
				new MdoCriteria("printingDate", PropertiesRestrictions.IS_NOT_NULL), 
				new MdoCriteria("cashing.id", JoinType.LEFT_OUTER_JOIN.getJoinTypeValue(), PropertiesRestrictions.IS_NULL), isLazy);
	}

	@Override
	public List<DinnerTable> findAllNotPrinted(Long restaurantId, boolean... isLazy) throws MdoDataBeanException {
		return findAllByCriterias(restaurantId, 
				null, 
				new MdoCriteria("printingDate", PropertiesRestrictions.IS_NULL), 
				new MdoCriteria("cashing.id", JoinType.LEFT_OUTER_JOIN.getJoinTypeValue(), PropertiesRestrictions.IS_NULL), isLazy);
	}

	@Override
	public List<DinnerTable> findAllNotPrinted(Long restaurantId, Long userAuthenticationId, boolean... isLazy) throws MdoDataBeanException {
		return findAllByCriterias(restaurantId, 
				userAuthenticationId, 
				new MdoCriteria("printingDate", PropertiesRestrictions.IS_NULL), 
				new MdoCriteria("cashing.id", JoinType.LEFT_OUTER_JOIN.getJoinTypeValue(), PropertiesRestrictions.IS_NULL), isLazy);
	}

	@Override
	public List<DinnerTable> findAllAlterable(Long restaurantId, boolean... isLazy) throws MdoDataBeanException {
		return findAllByCriterias(restaurantId, 
				null, 
				null, 
				new MdoCriteria("cashing.id", JoinType.LEFT_OUTER_JOIN.getJoinTypeValue(), PropertiesRestrictions.IS_NULL), isLazy);
	}

	@Override
	public List<DinnerTable> findAllAlterable(Long restaurantId, Long userAuthenticationId, boolean... isLazy) throws MdoDataBeanException {
		return findAllByCriterias(restaurantId, 
				userAuthenticationId, 
				null, 
				new MdoCriteria("cashing.id", JoinType.LEFT_OUTER_JOIN.getJoinTypeValue(), PropertiesRestrictions.IS_NULL), isLazy);
	}

	@SuppressWarnings("unchecked")
	private List<DinnerTable> findAllByCriterias(Long restaurantId, Long userAuthenticationId, 
			MdoCriteria printingDateCriteria, MdoCriteria cashingCriteria, boolean... isLazy)
					throws MdoDataBeanException {
		List<DinnerTable> result = new ArrayList<DinnerTable>();
		
		List<MdoCriteria> criterias = new ArrayList<MdoCriteria>();
		criterias.add(new MdoCriteria("restaurant.id", PropertiesRestrictions.EQUALS, restaurantId));
		if (userAuthenticationId != null) {
			criterias.add(new MdoCriteria("user.id", PropertiesRestrictions.EQUALS, userAuthenticationId));
		}
		if (printingDateCriteria != null) {
			criterias.add(printingDateCriteria);
		}
		if (cashingCriteria != null) {
			criterias.add(cashingCriteria);
		}

		// Only select needed fields
		criterias.add(new MdoCriteria("id", PropertiesRestrictions.PROJECTION));
		criterias.add(new MdoCriteria("amountPay", PropertiesRestrictions.PROJECTION));
		criterias.add(new MdoCriteria("cashing.cashingDate", PropertiesRestrictions.PROJECTION));
		criterias.add(new MdoCriteria("customersNumber", PropertiesRestrictions.PROJECTION));
		criterias.add(new MdoCriteria("printingDate", PropertiesRestrictions.PROJECTION));
		criterias.add(new MdoCriteria("quantitiesSum", PropertiesRestrictions.PROJECTION));
		criterias.add(new MdoCriteria("amountsSum", PropertiesRestrictions.PROJECTION));
		criterias.add(new MdoCriteria("number", PropertiesRestrictions.PROJECTION));
		criterias.add(new MdoCriteria("reductionRatio", PropertiesRestrictions.PROJECTION));
		criterias.add(new MdoCriteria("registrationDate", PropertiesRestrictions.PROJECTION));
		criterias.add(new MdoCriteria("reductionRatioChanged", PropertiesRestrictions.PROJECTION));
		criterias.add(new MdoCriteria("type.code.name", PropertiesRestrictions.PROJECTION));
		
		result = super.findByCriteria(super.getBean().getClass(), DinnerTable.class, criterias, isLazy);
		
		return result;
	}

	@Override
	public void updateCustomersNumber(Long dinnerTableId, Integer customersNumber) throws MdoDataBeanException {
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("customersNumber", customersNumber);
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("id", dinnerTableId);
		super.updateFieldsByKeys(fields, keys);
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

	@Override
	public void updateDerivedFieldsFromOrderLine(Long dinnerTableId, BigDecimal quantitiesSum, BigDecimal amountsSum, BigDecimal amountPay) throws MdoDataBeanException {
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("quantitiesSum", quantitiesSum);
		fields.put("amountsSum", amountsSum);
		fields.put("amountPay", amountPay);
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("id", dinnerTableId);
		super.updateFieldsByKeys(fields, keys);
	}

	@Override
	public BigDecimal getReductionRatio(Long dinnerTableId) throws MdoDataBeanException {
		 BigDecimal result = BigDecimal.ZERO;
		 
		List<MdoCriteria> criterias = new ArrayList<MdoCriteria>();
		criterias.add(new MdoCriteria("id", PropertiesRestrictions.EQUALS, dinnerTableId));
		criterias.add(new MdoCriteria("reductionRatio", PropertiesRestrictions.PROJECTION));

		Object object = super.uniqueResult(super.findByCriteria(DinnerTable.class, criterias));
		if (object != null) {
			result = new BigDecimal(object.toString());
		}
		
		return result;
	}
}
