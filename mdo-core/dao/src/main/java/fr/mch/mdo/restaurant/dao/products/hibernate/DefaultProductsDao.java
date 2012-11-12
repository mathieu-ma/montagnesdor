package fr.mch.mdo.restaurant.dao.products.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.dao.beans.Product;
import fr.mch.mdo.restaurant.dao.beans.ProductCategory;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.hibernate.TransactionSession;
import fr.mch.mdo.restaurant.dao.products.IProductsDao;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultProductsDao extends DefaultDaoServices implements IProductsDao 
{
	private static class LazyHolder {
		private static IProductsDao instance = new DefaultProductsDao(LoggerServiceImpl.getInstance().getLogger(DefaultProductsDao.class.getName()), new Product());
	}

	private DefaultProductsDao(ILogger logger, IMdoDaoBean bean) {
		super(true);
		this.setLogger(logger);
		this.setBean(bean);
	}

	public static IProductsDao getInstance() {
		return LazyHolder.instance;
	}

	public DefaultProductsDao() {
	}

	@Override
	public IMdoBean findByUniqueKey(Object[] propertyValues, boolean... isLazy) throws MdoDataBeanException {
		super.findByUniqueKey(propertyValues, isLazy);
		if (propertyValues.length != 2) {
			super.getLogger().error("message.error.dao.unique.fields.2");
			throw new MdoDataBeanException("message.error.dao.unique.fields.2");
		}
		Map<String, Object> propertyValueMap = new HashMap<String, Object>();
		propertyValueMap.put("restaurant.id", propertyValues[0]);
		propertyValueMap.put("code", propertyValues[1]);
		return (IMdoBean) super.findByUniqueKey(propertyValueMap, isLazy);
	}

	@Override
	public IMdoBean find(Long restaurantId, String code) throws MdoDataBeanException {
		Map<String, Object> propertyValueMap = new HashMap<String, Object>();
		propertyValueMap.put("restaurant.id", restaurantId);
		propertyValueMap.put("code", code);
		return (IMdoBean) super.findByUniqueKey(propertyValueMap, false);
	}

	@Override
	public IMdoBean find(String restaurantReference, String code) throws MdoDataBeanException {
		Map<String, Object> propertyValueMap = new HashMap<String, Object>();
		propertyValueMap.put("restaurant.reference", restaurantReference);
		propertyValueMap.put("code", code);
		return (IMdoBean) super.findByUniqueKey(propertyValueMap, false);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<IMdoBean> findAllByPrefixCode(Long restaurantId, String prefixCode) throws MdoException {
		List<IMdoBean> result = new ArrayList<IMdoBean>();

		List<MdoCriteria> criterias = new ArrayList<MdoCriteria>();
		criterias.add(new MdoCriteria("restaurant.id", PropertiesRestrictions.EQUALS, restaurantId));
		criterias.add(new MdoCriteria("code", PropertiesRestrictions.LIKE, prefixCode));

//		Map<String, Entry<PropertiesRestrictions, Object>> propertyValueMap = new HashMap<String, Entry<PropertiesRestrictions, Object>>();
//		String property = "restaurant.id";
//		Entry<PropertiesRestrictions, Object> value = new MdoEntry<PropertiesRestrictions, Object>(PropertiesRestrictions.EQUALS, restaurantId);
//		propertyValueMap.put(property, value);
//		property = "code";
//		value = new MdoEntry<PropertiesRestrictions, Object>(PropertiesRestrictions.LIKE, prefixCode);
//		propertyValueMap.put(property, value);
//		result = super.findByPropertiesRestrictions(propertyValueMap, false);

		result = super.findByPropertiesRestrictions(criterias, false);
		return result;
	}

	@Override
	public Map<Long, String> findCodesByPrefixCode(Long restaurantId, String prefixProductCode) throws MdoDataBeanException {
		Map<Long, String> result = new HashMap<Long, String>();
		try {
			TransactionSession transactionSession = super.beginTransaction();
			Session session = transactionSession.getSession();
	
			Criteria criteria = session.createCriteria(super.getBean().getClass());
			criteria.add(Restrictions.ilike("code", prefixProductCode + "%"));
			criteria.createAlias("restaurant", "restaurant");
			criteria.add(Restrictions.eq("restaurant.id", restaurantId));
			// Only select id and number fields
			criteria.setProjection(Projections.projectionList().add(Projections.property("id")).add(Projections.property("code")));
	
			@SuppressWarnings("unchecked")
			List<Object[]> list = criteria.list();
			for (Iterator<Object[]> iterator = list.iterator(); iterator.hasNext();) {
				Object[] objects = iterator.next();
				Long id = (Long) objects[0];
				String number = (String) objects[1];
				result.put(id, number);
			}
			super.endTransaction(transactionSession, result, true);
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
	@SuppressWarnings("unchecked")
	public List<IMdoBean> findAllByRestaurant(Long restaurantId) throws MdoDataBeanException {
		List<IMdoBean> result = new ArrayList<IMdoBean>();
		
		List<MdoCriteria> criterias = new ArrayList<MdoCriteria>();
		criterias.add(new MdoCriteria("restaurant.id", PropertiesRestrictions.EQUALS, restaurantId));

//		Map<String, Entry<PropertiesRestrictions, Object>> propertyValueMap = new HashMap<String, Entry<PropertiesRestrictions, Object>>();
//		String property = "restaurant.id";
//		Entry<PropertiesRestrictions, Object> value = new MdoEntry<PropertiesRestrictions, Object>(PropertiesRestrictions.EQUALS, restaurantId);
//		propertyValueMap.put(property, value);
//		result = super.findByPropertiesRestrictions(propertyValueMap, false);

		result = super.findByPropertiesRestrictions(criterias, false);
		return result;
	}
	
	@Override
	public IMdoBean delete(IMdoBean daoBean, boolean... isLazy) throws MdoDataBeanException {
		// Delete first all categories.
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("product.id", ((IMdoDaoBean) daoBean).getId());
		this.deleteByKeys(ProductCategory.class, keys);

		return super.delete(daoBean, isLazy);
	}
}
