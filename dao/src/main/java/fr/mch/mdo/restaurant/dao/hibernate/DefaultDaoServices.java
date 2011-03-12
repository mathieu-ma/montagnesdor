package fr.mch.mdo.restaurant.dao.hibernate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Restrictions;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.MdoDaoBean;
import fr.mch.mdo.restaurant.beans.MdoEntry;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;

public abstract class DefaultDaoServices extends MdoDaoBase implements IDaoServices 
{
	protected enum PropertiesRestrictions 
	{
		EQUALS() {
			public Criteria add(Criteria criteria, String property, Object value) {
				return criteria.add(Restrictions.eq(property, value));
			}
		},
		LIKE() {
			public Criteria add(Criteria criteria, String property, Object value) {
				return criteria.add(Restrictions.like(property, value));
			}
		},
		IS_NULL() {
			public Criteria add(Criteria criteria, String property, Object value) {
				return criteria.add(Restrictions.isNull(property));
			}
		},
		ORDER() {
			public Criteria add(Criteria criteria, String property, Object value) {
				return criteria.addOrder(Order.asc(property));
			}
		},
		PROJECTION() {
			public Criteria add(Criteria criteria, String property, Object value) {
				if (value instanceof Projection) {
					return criteria.setProjection((Projection) value);
				}
				// Maybe throw IllegalArgumentException or create MdoRuntimeException
				throw new IllegalArgumentException();
			}
		};
		public Criteria add(Criteria criteria, String property, Object value) {
			return null;
		}
	}

	protected DefaultDaoServices(boolean loadSessionFactory) {
		super(loadSessionFactory);
	}

	protected DefaultDaoServices() {

	}

	public IMdoBean delete(IMdoBean daoBean, boolean... isLazy) throws MdoDataBeanException {
		IMdoBean result = daoBean;
		try {
			TransactionSession transactionSession = super.beginTransaction();

			Session session = transactionSession.getSession();
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
	@SuppressWarnings("unchecked")
	public List<IMdoBean> findAll(boolean... isLazy) throws MdoDataBeanException {
		List<IMdoBean> result = null;
		try {
			TransactionSession transactionSession = super.beginTransaction();

			Session session = transactionSession.getSession();
			Criteria criteria = session.createCriteria(super.getBean().getClass());
			result = criteria.list();

			super.endTransaction(transactionSession, result, isLazy);
		} catch (HibernateException e) {
			super.getLogger().error("message.error.dao.list", new Object[] { super.getBean().getClass().getName(), super.getBean().toString() }, e);
			throw new MdoDataBeanException(e);
		} catch (Exception e) {
			super.getLogger().error("message.error.dao.list", new Object[] { super.getBean().getClass().getName(), super.getBean().toString() }, e);
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
	public void load(IMdoBean daoBean, boolean... isLazy) throws MdoDataBeanException {
		try {
			TransactionSession transactionSession = super.beginTransaction();

			Session session = transactionSession.getSession();
			MdoDaoBean daoBeanX = (MdoDaoBean) daoBean;
			session.load(daoBeanX, (Serializable) daoBeanX.getId());

			super.endTransaction(transactionSession, daoBeanX, isLazy);
		} catch (HibernateException e) {
			super.getLogger().error("message.error.dao.key", new Object[] { super.getBean().getClass().getName(), super.getBean().toString() }, e);
			throw new MdoDataBeanException(e);
		} catch (Exception e) {
			super.getLogger().error("message.error.dao.key", new Object[] { super.getBean().getClass().getName(), super.getBean().toString() }, e);
			throw new MdoDataBeanException(e);
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
	public IMdoBean findByPrimaryKey(Object key, boolean... isLazy) throws MdoDataBeanException {
		IMdoBean result = null;
		try {
			TransactionSession transactionSession = super.beginTransaction();
			
			Session session = transactionSession.getSession();
			result = (IMdoBean) session.get(super.getBean().getClass(), (Serializable) key);

			super.endTransaction(transactionSession, result, isLazy);
		} catch (HibernateException e) {
			super.getLogger().error("message.error.dao.key", new Object[] { super.getBean().getClass().getName(), super.getBean().toString() }, e);
			throw new MdoDataBeanException(e);
		} catch (Exception e) {
			super.getLogger().error("message.error.dao.key", new Object[] { super.getBean().getClass().getName(), super.getBean().toString() }, e);
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
	public IMdoBean update(IMdoBean daoBean, boolean... isLazy) throws MdoDataBeanException {
		IMdoBean result = daoBean;
		try {
			TransactionSession transactionSession = super.beginTransaction();
			
			Session session = transactionSession.getSession();
			// Merge is like update but not take into the result into cache
			//session.merge(result);
			session.saveOrUpdate(result);

			super.endTransaction(transactionSession, result, isLazy);
		} catch (HibernateException e) {
			super.getLogger().error("message.error.dao.save", new Object[] { super.getBean().getClass().getName(), result }, e);
			throw new MdoDataBeanException(e);
		} catch (Exception e) {
			super.getLogger().error("message.error.dao.save", new Object[] { super.getBean().getClass().getName(), result }, e);
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
	public IMdoBean insert(IMdoBean daoBean, boolean... isLazy) throws MdoDataBeanException {
		IMdoBean result = daoBean;
		try {
			TransactionSession transactionSession = super.beginTransaction();

			Session session = transactionSession.getSession();
			session.save(result);

			super.endTransaction(transactionSession, result, isLazy);

		} catch (HibernateException e) {
			super.getLogger().error("message.error.dao.save", new Object[] { super.getBean().getClass().getName(), result }, e);
			throw new MdoDataBeanException(e);
		} catch (Exception e) {
			super.getLogger().error("message.error.dao.save", new Object[] { super.getBean().getClass().getName(), result }, e);
			throw new MdoDataBeanException(e);
		} finally {
			try {
				
//try {
//	DefaultSessionFactory.getInstance().currentSession().connection().close();
//} catch (SQLException e) {
//	// TODO Auto-generated catch block
//	e.printStackTrace();
//}				
				super.closeSession();
			} catch (HibernateException e) {
				super.getLogger().error("message.error.dao.session.close", e);
				throw new MdoDataBeanException("message.error.dao.session.close", e);
			}
		}
		return result;
	}

	@Override
	public IMdoBean findByUniqueKey(Object propertyValue, boolean... isLazy) throws MdoDataBeanException {
		if (propertyValue == null) {
			super.getLogger().error("message.error.dao.unique.fields.null");
			throw new MdoDataBeanException("message.error.dao.unique.fields.null");
		}
		return this.findByUniqueKey(new Object[] { propertyValue }, isLazy);
	}

	@Override
	public IMdoBean findByUniqueKey(Object[] propertyValues, boolean... isLazy) throws MdoDataBeanException {
		if (propertyValues == null || propertyValues.length == 0) {
			super.getLogger().error("message.error.dao.unique.fields.null");
			throw new MdoDataBeanException("message.error.dao.unique.fields.null");
		}
		return null;
	}

	protected Object findByUniqueKey(Map<String, Object> propertyValueMap, boolean... isLazy) throws MdoDataBeanException {
		return this.findByUniqueKey(super.getBean().getClass(), propertyValueMap, isLazy);
	}

	@SuppressWarnings("unchecked")
	protected Object findByUniqueKey(Class<? extends IMdoBean> clazz, Map<String, Object> propertyValueMap, boolean... isLazy) throws MdoDataBeanException {
		Object result = null;

		List list = this.findByProperties(clazz, propertyValueMap, isLazy);
		result = uniqueResult(list);

		return result;
	}

	@SuppressWarnings("unchecked")
	protected Object uniqueResult(List list) throws MdoDataBeanException {
		Object result = null;

		if (list != null) {
			int size = list.size();
			if (size > 1) {
				super.getLogger().error("message.error.dao.unique.result");
				throw new MdoDataBeanException("message.error.dao.unique.result");
			} else if (size == 1) {
				result = list.get(0);
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected List findByProperties(Map<String, Object> propertyValueMap, boolean... isLazy) throws MdoDataBeanException {
		return this.findByProperties(super.getBean().getClass(), propertyValueMap, isLazy);
	}

	@SuppressWarnings("unchecked")
	protected List findByProperties(Class<? extends IMdoBean> clazz, Map<String, Object> propertyValueMap, boolean... isLazy) throws MdoDataBeanException {

		if (propertyValueMap == null) {
			super.getLogger().error("message.error.dao.properties.null");
			throw new MdoDataBeanException("message.error.dao.properties.null");
		}

		Map<String, Entry<PropertiesRestrictions, Object>> propertyValueRestrictionMap = new HashMap<String, Entry<PropertiesRestrictions, Object>>();
		for (String property : propertyValueMap.keySet()) {
			Entry<PropertiesRestrictions, Object> value = new MdoEntry<PropertiesRestrictions, Object>(PropertiesRestrictions.EQUALS, propertyValueMap.get(property));
			propertyValueRestrictionMap.put(property, value);
		}

		List result = this.findByPropertiesRestrictions(clazz, propertyValueRestrictionMap, isLazy);
		return result;
	}

	@SuppressWarnings("unchecked")
	protected List findByPropertiesRestrictions(Map<String, Entry<PropertiesRestrictions, Object>> propertyValueMap, boolean... isLazy) throws MdoDataBeanException {

		return this.findByPropertiesRestrictions(super.getBean().getClass(), propertyValueMap, isLazy);
	}

	@SuppressWarnings("unchecked")
	protected List findByPropertiesRestrictions(Class<? extends IMdoBean> clazz, Map<String, Entry<PropertiesRestrictions, Object>> propertyValueMap, boolean... isLazy)
			throws MdoDataBeanException {

		if (propertyValueMap == null) {
			super.getLogger().error("message.error.dao.properties.null");
			throw new MdoDataBeanException("message.error.dao.properties.null");
		}

		List result = this.findByCriteria(clazz, propertyValueMap, isLazy);

		return result;
	}

	@SuppressWarnings("unchecked")
	protected List findByCriteria(Class<? extends IMdoBean> clazz, Map<String, Entry<PropertiesRestrictions, Object>> propertyValueMap, boolean... isLazy)
			throws MdoDataBeanException {

		if (propertyValueMap == null) {
			super.getLogger().error("message.error.dao.properties.null");
			throw new MdoDataBeanException("message.error.dao.properties.null");
		}

		List result = null;
		try {
			TransactionSession transactionSession = super.beginTransaction();

			Session session = transactionSession.getSession();
			Criteria criteria = session.createCriteria(clazz);
			Map<String,String> alreadyCreatedAlias = new HashMap<String, String>();
			for (String property : propertyValueMap.keySet()) {
//				int indexOfAliasInProperty = property.indexOf(".");
//				if (indexOfAliasInProperty > 0) {
//					String alias = property.substring(0, indexOfAliasInProperty);
//					if (!alreadyCreatedAlias.containsKey(alias)) {
//						alreadyCreatedAlias.put(alias, alias);
//						criteria.createAlias(alias, alias);
//					}
//				}
//				propertyValueMap.get(property).getKey().add(criteria, property, propertyValueMap.get(property).getValue());
				String aliasProperty = this.createAlias(alreadyCreatedAlias, criteria, property);
				propertyValueMap.get(property).getKey().add(criteria, aliasProperty, propertyValueMap.get(property).getValue());
			}

			criteria.add(Restrictions.eq("deleted", Boolean.FALSE));
			result = criteria.list();

			super.endTransaction(transactionSession, result, isLazy);
		} catch (HibernateException e) {
			super.getLogger().error("message.error.dao.list.properties", new Object[] { clazz.getName(), propertyValueMap.toString() }, e);
			throw new MdoDataBeanException(e);
		} catch (Exception e) {
			super.getLogger().error("message.error.dao.list.properties", new Object[] { clazz.getName(), propertyValueMap.toString() }, e);
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

	private String createAlias(Map<String, String> alreadyCreatedAlias, Criteria criteria, String property) {
		String result = property;
//System.out.println("property=" + property);		
		if (property != null) {
			String aliasProperty = "";
			String alias = "";
			String[] properties = property.split("\\.");
			int maxSize = properties.length - 1;
			for (int i = 0; i < maxSize; i++) {
				if (i == 0) {
					aliasProperty = properties[i];
				} else {
					aliasProperty = aliasProperty + "." + properties[i];
				}
				alias += properties[i] + "_"; 
				if (!alreadyCreatedAlias.containsKey(aliasProperty)) {
					alreadyCreatedAlias.put(aliasProperty, alias);
					criteria.createAlias(aliasProperty, alias);
				}
			}
			if (maxSize>0) {
				// aliasProperty the last one
				result = alreadyCreatedAlias.get(aliasProperty) + property.substring(property.lastIndexOf("."));
			}
		}

		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List findAllByQuery(String queryName, Object[] values, boolean... isLazy) throws MdoDataBeanException {
		List result = null;
		try {
			TransactionSession transactionSession = super.beginTransaction();

			Session session = transactionSession.getSession();
			Query query = session.getNamedQuery(queryName);
			String[] namedParameters = query.getNamedParameters();
			if (namedParameters != null && values != null && namedParameters.length == values.length) {
				for (int i = 0; i < namedParameters.length; i++) {
					query.setParameter(namedParameters[i], values[i]);
				}
			}
			result = query.list();

			super.endTransaction(transactionSession, result, isLazy);
		} catch (HibernateException e) {
			logger.error("message.error.dao.query", values, e);
			throw new MdoDataBeanException("message.error.dao.query", values, e);
		} catch (Exception e) {
			logger.error("message.error.dao.query", values, e);
			throw new MdoDataBeanException("message.error.dao.query", values, e);
		} finally {
			try {
				super.closeSession();
			} catch (HibernateException e) {
				logger.error("message.error.dao.session.close", e);
				throw new MdoDataBeanException("message.error.dao.session.close", e);
			}
		}
		return result;
	}

}
