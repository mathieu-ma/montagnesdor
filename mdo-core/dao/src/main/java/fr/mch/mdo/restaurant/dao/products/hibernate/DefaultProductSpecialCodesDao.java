package fr.mch.mdo.restaurant.dao.products.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Projections;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.dao.MdoTableAsEnumTypeDao;
import fr.mch.mdo.restaurant.dao.beans.ProductSpecialCode;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.products.IProductSpecialCodesDao;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultProductSpecialCodesDao extends DefaultDaoServices implements IProductSpecialCodesDao 
{
	private static class LazyHolder {
		private static IProductSpecialCodesDao instance = new DefaultProductSpecialCodesDao(LoggerServiceImpl.getInstance()
				.getLogger(DefaultProductSpecialCodesDao.class.getName()), new ProductSpecialCode());
	}

	private DefaultProductSpecialCodesDao(ILogger logger, IMdoDaoBean bean) {
		super(true);
		this.setLogger(logger);
		this.setBean(bean);
	}

	public static IProductSpecialCodesDao getInstance() {
		return LazyHolder.instance;
	}

	public DefaultProductSpecialCodesDao() {
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<IMdoBean> findProductSpecialCodesByRestaurant(Long restaurantId) throws MdoDataBeanException {

		Map<String, Object> propertyValueMap = new HashMap<String, Object>();
		propertyValueMap.put("restaurant.id", restaurantId);

		return super.findByProperties(propertyValueMap);
	}

	@Override
	public IMdoBean findByShortCode(Long restaurantId, String shortCode) throws MdoDataBeanException {

		Map<String, Object> propertyValueMap = new HashMap<String, Object>();
		propertyValueMap.put("restaurant.id", restaurantId);
		propertyValueMap.put("shortCode", shortCode);

		return (IMdoBean) super.findByUniqueKey(propertyValueMap);
	}

	@Override
	public IMdoBean findByCodeName(Long restaurantId, String codeName) throws MdoDataBeanException {

		Map<String, Object> propertyValueMap = new HashMap<String, Object>();
		propertyValueMap.put("restaurant.id", restaurantId);
		propertyValueMap.put("code.name", codeName);
		propertyValueMap.put("code.type", MdoTableAsEnumTypeDao.PRODUCT_SPECIAL_CODE.name());
		propertyValueMap.put("code.deleted", Boolean.FALSE);

		return (IMdoBean) super.findByUniqueKey(propertyValueMap);
	}

	@Override
	public Long getIdByCodeName(Long restaurantId, String codeName) throws MdoDataBeanException {
		Long result = null;

		List<MdoCriteria> criterias = new ArrayList<MdoCriteria>();
		criterias.add(new MdoCriteria("restaurant.id", PropertiesRestrictions.EQUALS, restaurantId));
		criterias.add(new MdoCriteria("code.name", PropertiesRestrictions.EQUALS, codeName));
		criterias.add(new MdoCriteria("code.type", PropertiesRestrictions.EQUALS, MdoTableAsEnumTypeDao.PRODUCT_SPECIAL_CODE.name()));
		criterias.add(new MdoCriteria("code.deleted", PropertiesRestrictions.EQUALS, Boolean.FALSE));
		criterias.add(new MdoCriteria("id", PropertiesRestrictions.PROJECTION, Projections.property("id")));
		Object object = super.uniqueResult(super.findByCriteria(this.getBean().getClass(), criterias));
		if (object != null) {
			result = new Long(object.toString());
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IMdoBean> findAllByRestaurant(Long restaurantId) throws MdoDataBeanException {
		List<IMdoBean> result = new ArrayList<IMdoBean>();
		
		List<MdoCriteria> criterias = new ArrayList<MdoCriteria>();
		criterias.add(new MdoCriteria("restaurant.id", PropertiesRestrictions.EQUALS, restaurantId));
		criterias.add(new MdoCriteria("code.order", PropertiesRestrictions.ORDER, Boolean.FALSE));
		result = super.findByPropertiesRestrictions(criterias, false);
		return result;
	}

}
