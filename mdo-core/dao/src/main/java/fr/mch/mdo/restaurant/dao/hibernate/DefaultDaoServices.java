package fr.mch.mdo.restaurant.dao.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.ResultTransformer;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.MdoDaoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;

public abstract class DefaultDaoServices extends MdoDaoBase implements IDaoServices 
{
	protected enum PropertiesRestrictions 
	{
		DEFAULT(),
		EQUALS() {
			public Criteria add(Criteria criteria, String property, String alias, Object value, ProjectionList projectionList) {
				return criteria.add(Restrictions.eq(property, value));
			}
		},
		LIKE() {
			public Criteria add(Criteria criteria, String property, String alias, Object value, ProjectionList projectionList) {
				return criteria.add(Restrictions.like(property, value));
			}
		},
		IS_NULL() {
			public Criteria add(Criteria criteria, String property, String alias, Object value, ProjectionList projectionList) {
				return criteria.add(Restrictions.isNull(property));
			}
		},
		IS_NOT_NULL() {
			public Criteria add(Criteria criteria, String property, String alias, Object value, ProjectionList projectionList) {
				return criteria.add(Restrictions.isNotNull(property));
			}
		},
		ORDER() {
			public Criteria add(Criteria criteria, String property, String alias, Object value, ProjectionList projectionList) {
				Order order = Order.asc(property);
				if (Boolean.FALSE.equals(value)) {
					order = Order.desc(property);
				}
				return criteria.addOrder(order);
			}
		},
		PROJECTION_SUM() {
			public Criteria add(Criteria criteria, String property, String alias, Object value, ProjectionList projectionList) {
				projectionList.add(Projections.sum(property), alias);
				return criteria;
			}
		},
		PROJECTION() {
			public Criteria add(Criteria criteria, String property, String alias, Object value, ProjectionList projectionList) {
				projectionList.add(Projections.property(property), alias);
				return criteria;
			}
		},
		PROJECTION_ROW_COUNT() {
			public Criteria add(Criteria criteria, String property, String alias, Object value, ProjectionList projectionList) {
				projectionList.add(Projections.rowCount(), alias);
				return criteria;
			}
		},
		SQL() {
			public Criteria add(Criteria criteria, String property, String alias, Object value, ProjectionList projectionList) {
				return criteria.add(Restrictions.sqlRestriction(value.toString()));
			}
		};
		public Criteria add(Criteria criteria, String property, String alias, Object value, ProjectionList projectionList) {
			return criteria;
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
			session.merge(result);
//			session.saveOrUpdate(result);
//			session.update(result);
			
			// Flush here because of AOP and collection mapping.
			// In AOP, the commit is done in a global transaction between business method.
			session.flush();
			
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

	@SuppressWarnings("rawtypes")
	protected Object findByUniqueKey(Class<? extends IMdoBean> clazz, Map<String, Object> propertyValueMap, boolean... isLazy) throws MdoDataBeanException {
		Object result = null;

		List list = this.findByProperties(clazz, propertyValueMap, isLazy);
		result = uniqueResult(list);

		return result;
	}

	@SuppressWarnings("rawtypes")
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

	@SuppressWarnings("rawtypes")
	protected List findByProperties(Map<String, Object> propertyValueMap, boolean... isLazy) throws MdoDataBeanException {
		return this.findByProperties(super.getBean().getClass(), propertyValueMap, isLazy);
	}

	protected class MdoCriteria {
		private String property;
		private String propertyAlias;
		private int aliasJoinType;
		private PropertiesRestrictions restriction;
		private Object restrictionValue;
		
		public MdoCriteria(String property, String propertyAlias, int aliasJoinType, PropertiesRestrictions restriction, Object restrictionValue) {
			this.property = property;
			this.propertyAlias = propertyAlias;
			this.aliasJoinType = aliasJoinType;
			this.restriction = restriction;
			this.restrictionValue = restrictionValue;
		}

		public MdoCriteria(String property, int aliasJoinType, PropertiesRestrictions restriction) {
			this(property, property, aliasJoinType, restriction, null);
		}

		public MdoCriteria(String property, PropertiesRestrictions restriction, Object restrictionValue) {
			this(property, property, JoinType.INNER_JOIN.getJoinTypeValue(), restriction, restrictionValue);
		}

		public MdoCriteria(String property, String propertyAlias, PropertiesRestrictions restriction) {
			this(property, propertyAlias, JoinType.INNER_JOIN.getJoinTypeValue(), restriction, null);
		}

		public MdoCriteria(String property, PropertiesRestrictions restriction) {
			this(property, property, JoinType.INNER_JOIN.getJoinTypeValue(), restriction, null);
		}

		/**
		 * @return the property
		 */
		public String getProperty() {
			return property;
		}
		/**
		 * @param property the property to set
		 */
		public void setProperty(String property) {
			this.property = property;
		}

		/**
		 * @return the propertyAlias
		 */
		public String getPropertyAlias() {
			return propertyAlias;
		}
		/**
		 * @param propertyAlias the propertyAlias to set
		 */
		public void setPropertyAlias(String propertyAlias) {
			this.propertyAlias = propertyAlias;
		}

		/**
		 * @return the aliasJoinType
		 */
		public int getAliasJoinType() {
			return aliasJoinType;
		}
		/**
		 * @param aliasJoinType the aliasJoinType to set
		 */
		public void setAliasJoinType(int aliasJoinType) {
			this.aliasJoinType = aliasJoinType;
		}
		/**
		 * @return the restriction
		 */
		public PropertiesRestrictions getRestriction() {
			return restriction;
		}
		/**
		 * @param restriction the restriction to set
		 */
		public void setRestriction(PropertiesRestrictions restriction) {
			this.restriction = restriction;
		}
		/**
		 * @return the restrictionValue
		 */
		public Object getRestrictionValue() {
			return restrictionValue;
		}
		/**
		 * @param restrictionValue the restrictionValue to set
		 */
		public void setRestrictionValue(Object restrictionValue) {
			this.restrictionValue = restrictionValue;
		}
		
		@Override
		public String toString() {
			return "MdoCriteria [property=" + property + ", propertyAlias=" + propertyAlias + ", aliasJoinType=" + aliasJoinType + ", restriction=" + restriction + ", restrictionValue=" + restrictionValue + "]";
		}
	}

	@SuppressWarnings("rawtypes")
	protected List findByProperties(Class<? extends IMdoBean> clazz, Map<String, Object> propertyValueMap, boolean... isLazy) throws MdoDataBeanException {

		if (propertyValueMap == null) {
			super.getLogger().error("message.error.dao.properties.null");
			throw new MdoDataBeanException("message.error.dao.properties.null");
		}

		List<MdoCriteria> criterias = new ArrayList<DefaultDaoServices.MdoCriteria>();
		for (String property : propertyValueMap.keySet()) {
			criterias.add(new MdoCriteria(property, PropertiesRestrictions.EQUALS, propertyValueMap.get(property)));
		}

		List result = this.findByPropertiesRestrictions(clazz, criterias, isLazy);
		return result;
	}

	@SuppressWarnings("rawtypes")
	protected List findByPropertiesRestrictions(List<MdoCriteria> criterias, boolean... isLazy) throws MdoDataBeanException {

		return this.findByPropertiesRestrictions(super.getBean().getClass(), criterias, isLazy);
	}

	@SuppressWarnings("rawtypes")
	protected List findByPropertiesRestrictions(Class<? extends IMdoBean> clazz, List<MdoCriteria> criterias, boolean... isLazy)
			throws MdoDataBeanException {

		if (criterias == null) {
			super.getLogger().error("message.error.dao.properties.null");
			throw new MdoDataBeanException("message.error.dao.properties.null");
		}

		List result = this.findByCriteria(clazz, criterias, isLazy);

		return result;
	}

	@SuppressWarnings("rawtypes")
	protected List findByCriteria(Class<? extends IMdoBean> clazz, ResultTransformer resultTransformer, List<MdoCriteria> criterias, boolean... isLazy) throws MdoDataBeanException {

		if (criterias == null) {
			super.getLogger().error("message.error.dao.properties.null");
			throw new MdoDataBeanException("message.error.dao.properties.null");
		}

		List result = null;
		try {
			TransactionSession transactionSession = super.beginTransaction();

			Session session = transactionSession.getSession();
			Criteria criteria = session.createCriteria(clazz);
			Map<String,String> alreadyCreatedAlias = new HashMap<String, String>();
			ProjectionList projectionList = Projections.projectionList(); 
			for (MdoCriteria mdoCriteria : criterias) {
				String propertyAlias = this.createAlias(alreadyCreatedAlias, criteria, mdoCriteria);
				mdoCriteria.getRestriction().add(criteria, propertyAlias, mdoCriteria.getPropertyAlias(), mdoCriteria.getRestrictionValue(), projectionList);
			}
			if (projectionList.getLength() != 0) {
				criteria.setProjection(projectionList);
			}

			criteria.add(Restrictions.eq("deleted", Boolean.FALSE));
			
			if (resultTransformer != null) {
				// Always last change on criteria to be taken into account.
				criteria.setResultTransformer(resultTransformer);	
			}
			
			result = criteria.list();

			super.endTransaction(transactionSession, result, isLazy);
		} catch (HibernateException e) {
			super.getLogger().error("message.error.dao.list.properties", new Object[] { clazz.getName(), criterias.toString() }, e);
			throw new MdoDataBeanException(e);
		} catch (Exception e) {
			super.getLogger().error("message.error.dao.list.properties", new Object[] { clazz.getName(), criterias.toString() }, e);
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

	@SuppressWarnings("rawtypes")
	protected List findByCriteria(Class<? extends IMdoBean> clazz, Class<? extends IMdoBean> transformerAliasToBeanClazz, List<MdoCriteria> criterias, boolean... isLazy) throws MdoDataBeanException {
		//return this.findByCriteria(clazz, Transformers.aliasToBean(transformerAliasToBeanClazz), criterias, isLazy);
		
		// List of map with alias as key
		//return this.findByCriteria(clazz, Transformers.ALIAS_TO_ENTITY_MAP, criterias, isLazy);

		return this.findByCriteria(clazz, new MdoAliasToBean(transformerAliasToBeanClazz), criterias, isLazy);
	}

	@SuppressWarnings("rawtypes")
	protected List findByCriteria(Class<? extends IMdoBean> clazz, List<MdoCriteria> criterias, boolean... isLazy) throws MdoDataBeanException {
		return this.findByCriteria(clazz, (ResultTransformer) null, criterias, isLazy);
	}

	private String createAlias(Map<String, String> alreadyCreatedAlias, Criteria criteria, MdoCriteria mdoCriteria) {
		String result = mdoCriteria.getProperty();
		if (result != null) {
			String aliasProperty = "";
			String alias = "";
			String[] properties = result.split("\\.");
			int maxSize = properties.length - 1;
			for (int i = 0; i < maxSize; i++) {
				if (i == 0) {
					aliasProperty = properties[i];
//					alias = aliasProperty; 
				} else {
					aliasProperty = aliasProperty + "." + properties[i];
//					alias += properties[i].substring(0, 1).toUpperCase() + properties[i].substring(1); 
				}
				alias += properties[i] + "_"; 
				if (!alreadyCreatedAlias.containsKey(aliasProperty)) {
					alreadyCreatedAlias.put(aliasProperty, alias);
					criteria.createAlias(aliasProperty, alias, JoinType.parse(mdoCriteria.aliasJoinType));
				}
			}
			if (maxSize>0) {
				// aliasProperty the last one
				result = alreadyCreatedAlias.get(aliasProperty) + result.substring(result.lastIndexOf("."));
//				if (mdoCriteria.getProperty().equals(mdoCriteria.getPropertyAlias())) {
//					int lastIndexOfDot = result.lastIndexOf(".");
//					if (lastIndexOfDot > 0) {
//						result = alreadyCreatedAlias.get(aliasProperty) + result.substring(lastIndexOfDot + 1, lastIndexOfDot + 2).toUpperCase() + result.substring(lastIndexOfDot + 2);
//					}
//				}
			}
		}

		return result;
	}

	@SuppressWarnings("rawtypes")
	public List findAllByQuery(String queryName, Map<String, Object> values, boolean... isLazy) throws MdoDataBeanException {
		return this.findAllByQuery(queryName, values, null, isLazy);
	}
	
	@SuppressWarnings("rawtypes")
	public List findAllByQuery(String queryName, Map<String, Object> values, ResultTransformer resultTransformer, boolean... isLazy) throws MdoDataBeanException {
		List result = null;
		try {
			TransactionSession transactionSession = super.beginTransaction();

			Session session = transactionSession.getSession();
			Query query = session.getNamedQuery(queryName);
			String[] namedParameters = query.getNamedParameters();
			if (namedParameters != null && values != null && namedParameters.length == values.size()) {
				for (int i = 0; i < namedParameters.length; i++) {
					query.setParameter(namedParameters[i], values.get(namedParameters[i]));
				}
			}
			if (resultTransformer != null) {
				// Always last change on criteria to be taken into account.
				query.setResultTransformer(resultTransformer);	
			}
			result = query.list();

			super.endTransaction(transactionSession, result, isLazy);
		} catch (HibernateException e) {
			logger.error("message.error.dao.query", new Object[] {queryName}, e);
			throw new MdoDataBeanException("message.error.dao.query", new Object[] {queryName}, e);
		} catch (Exception e) {
			logger.error("message.error.dao.query", new Object[] {queryName}, e);
			throw new MdoDataBeanException("message.error.dao.query", new Object[] {queryName}, e);
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

	@Override
	public void updateFieldsByKeys(Map<String, Object> fields, Map<String, Object> keys) throws MdoDataBeanException {
		this.updateFieldsByKeys(super.getBean().getClass(), fields, keys);
	}
	
	@Override
	public void updateFieldsByKeys(Class<? extends IMdoBean> clazz, Map<String, Object> fields, Map<String, Object> keys) throws MdoDataBeanException {
		TransactionSession transactionSession = null;
		if (fields != null && !fields.isEmpty() && keys != null && !keys.isEmpty()) {
			try {
				transactionSession = super.beginTransaction();
	
				Session session = transactionSession.getSession();
				StringBuilder query = new StringBuilder("UPDATE " + clazz.getName() + " bean ");
				query.append(" SET ");
				Map<String, Object> renamedFields = new HashMap<String, Object>();
				for (String key : fields.keySet()) {
					String renamedKey = key.replace(".", "_");
					query.append(" bean.").append(key).append("=:").append(renamedKey).append(",");
					renamedFields.put(renamedKey, fields.get(key));
				}
				query.deleteCharAt(query.lastIndexOf(","));
				query.append(" WHERE 1=1 ");
				Map<String, Object> renamedKeys = new HashMap<String, Object>();
				for (String key : keys.keySet()) {
					String renamedKey = key.replace(".", "_");
					query.append(" AND bean.").append(key).append("=:").append(renamedKey);
					renamedKeys.put(renamedKey, keys.get(key));
				}

				Query hQuery = session.createQuery(query.toString());
				hQuery.setProperties(renamedFields);
				hQuery.setProperties(renamedKeys);
				
				hQuery.executeUpdate();
	
				super.endTransaction(transactionSession, null);
			} catch (Throwable e) {
				logger.error("message.error.dao.update.fields.keys", new Object[] {clazz, fields, keys}, e);
				transactionSession.getTransaction().rollback();
				throw new MdoDataBeanException("message.error.dao.update.fields.keys", new Object[] {clazz, fields, keys}, e);
			} finally {
				try {
					super.closeSession();
				} catch (HibernateException e) {
					logger.error("message.error.dao.session.close", e);
					throw new MdoDataBeanException("message.error.dao.session.close", e);
				}
			}
		} else {
			logger.error("message.error.dao.update.fields.keys.empty", new Object[] {clazz, fields, keys});
			throw new MdoDataBeanException("message.error.dao.update.fields.keys.empty", new Object[] {clazz, fields, keys});
		}
	}

	@Override
	public void delete(Long id) throws MdoDataBeanException {
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("id", id);
		this.deleteByKeys(keys);
	}

	@Override
	public void deleteByKeys(Map<String, Object> keys) throws MdoDataBeanException {
		this.deleteByKeys(super.getBean().getClass(), keys);
	}
	
	@Override
	public void deleteByKeys(Class<? extends IMdoBean> clazz, Map<String, Object> keys) throws MdoDataBeanException {
		TransactionSession transactionSession = null;
		if (keys != null && !keys.isEmpty()) {
			try {
				transactionSession = super.beginTransaction();
	
				Session session = transactionSession.getSession();
				
				StringBuilder query = new StringBuilder("DELETE " + clazz.getName() + " bean ");
				query.append(" WHERE 1=1 ");
				Map<String, Object> renamedKeys = new HashMap<String, Object>();
				for (String key : keys.keySet()) {
					String renamedKey = key.replace(".", "_");
					query.append(" AND bean.").append(key).append("=:").append(renamedKey);
					renamedKeys.put(renamedKey, keys.get(key));
				}

				Query hQuery = session.createQuery(query.toString());
				hQuery.setProperties(renamedKeys);
				
				hQuery.executeUpdate();
	
				super.endTransaction(transactionSession, null);
			} catch (Throwable e) {
				logger.error("message.error.dao.delete.keys", new Object[] {clazz, keys}, e);
				transactionSession.getTransaction().rollback();
				throw new MdoDataBeanException("message.error.dao.delete.keys", new Object[] {clazz, keys}, e);
			} finally {
				try {
					super.closeSession();
				} catch (HibernateException e) {
					logger.error("message.error.dao.session.close", e);
					throw new MdoDataBeanException("message.error.dao.session.close", e);
				}
			}
		} else {
			logger.error("message.error.dao.delete.fields.keys.empty", new Object[] {clazz, keys});
			throw new MdoDataBeanException("message.error.dao.delete.keys.empty", new Object[] {clazz, keys});
		}
	}
}
