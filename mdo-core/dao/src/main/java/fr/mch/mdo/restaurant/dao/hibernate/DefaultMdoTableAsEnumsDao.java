package fr.mch.mdo.restaurant.dao.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.MdoEntry;
import fr.mch.mdo.restaurant.dao.IMdoTableAsEnumsDao;
import fr.mch.mdo.restaurant.dao.MdoTableAsEnumTypeDao;
import fr.mch.mdo.restaurant.dao.beans.MdoString;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices.MdoCriteria;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices.PropertiesRestrictions;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultMdoTableAsEnumsDao extends DefaultDaoServices implements IMdoTableAsEnumsDao 
{
	private static class LazyHolder {
		private static IMdoTableAsEnumsDao instance = new DefaultMdoTableAsEnumsDao(LoggerServiceImpl.getInstance().getLogger(DefaultMdoTableAsEnumsDao.class.getName()),
				new MdoTableAsEnum());
	}

	private DefaultMdoTableAsEnumsDao(ILogger logger, IMdoDaoBean bean) {
		super(true);
		this.setLogger(logger);
		this.setBean(bean);
	}

	public static IMdoTableAsEnumsDao getInstance() {
		return LazyHolder.instance;
	}

	public DefaultMdoTableAsEnumsDao() {
	}

	@Override
	public IMdoBean findByUniqueKey(Object[] propertyValues, boolean... isLazy) throws MdoDataBeanException {
		super.findByUniqueKey(propertyValues, isLazy);
		if (propertyValues.length != 2) {
			super.getLogger().error("message.error.dao.unique.fields.2");
			throw new MdoDataBeanException("message.error.dao.unique.fields.2");
		}
		Map<String, Object> propertyValueMap = new HashMap<String, Object>();
//		propertyValueMap.put("type", new MdoString((String) propertyValues[0]));
		propertyValueMap.put("type", propertyValues[0]);
		propertyValueMap.put("name", propertyValues[1]);
		return (IMdoBean) super.findByUniqueKey(propertyValueMap, isLazy);
	}

	@Override
	public List<MdoTableAsEnum> getBeans(String type) throws MdoDataBeanException {
		return getMdoTableAsEnumByType(type);
	}

	@Override
	public List<MdoTableAsEnum> getSpecificRounds() throws MdoDataBeanException {
		return getMdoTableAsEnumByType(MdoTableAsEnumTypeDao.SPECIFIC_ROUND);
	}

	@Override
	public List<MdoTableAsEnum> getTableTypes() throws MdoDataBeanException {
		return getMdoTableAsEnumByType(MdoTableAsEnumTypeDao.TABLE_TYPE);
	}

	@Override
	public List<MdoTableAsEnum> getRestaurantPrefixTakeawayNames() throws MdoDataBeanException {
		return getMdoTableAsEnumByType(MdoTableAsEnumTypeDao.PREFIX_TABLE_NAME);
	}

	@Override
	public List<MdoTableAsEnum> getPrintingInformationAlignments() throws MdoDataBeanException {
		return getMdoTableAsEnumByType(MdoTableAsEnumTypeDao.PRINTING_INFORMATION_ALIGNMENT);
	}

	@Override
	public List<MdoTableAsEnum> getPrintingInformationSizes() throws MdoDataBeanException {
		return getMdoTableAsEnumByType(MdoTableAsEnumTypeDao.PRINTING_INFORMATION_SIZE);
	}

	@Override
	public List<MdoTableAsEnum> getPrintingInformationParts() throws MdoDataBeanException {
		return getMdoTableAsEnumByType(MdoTableAsEnumTypeDao.PRINTING_INFORMATION_PART);
	}

	@Override
	public List<MdoTableAsEnum> getUserRoles() throws MdoDataBeanException {
		return getMdoTableAsEnumByType(MdoTableAsEnumTypeDao.USER_ROLE);
	}

	@Override
	public List<MdoTableAsEnum> getUserTitles() throws MdoDataBeanException {
		return getMdoTableAsEnumByType(MdoTableAsEnumTypeDao.USER_TITLE);
	}

	@Override
	public List<MdoTableAsEnum> getCategories() throws MdoDataBeanException {
		return getMdoTableAsEnumByType(MdoTableAsEnumTypeDao.CATEGORY);
	}

	@Override
	public List<MdoTableAsEnum> getProductSpecialCodes() throws MdoDataBeanException {
		return getMdoTableAsEnumByType(MdoTableAsEnumTypeDao.PRODUCT_SPECIAL_CODE);
	}

	@Override
	public List<MdoTableAsEnum> getProductParts() throws MdoDataBeanException {
		return getMdoTableAsEnumByType(MdoTableAsEnumTypeDao.PRODUCT_PART);
	}

	@Override
	public List<MdoTableAsEnum> getValueAddedTaxes() throws MdoDataBeanException {
		return getMdoTableAsEnumByType(MdoTableAsEnumTypeDao.VALUE_ADDED_TAX);
	}

	@Override
	public List<MdoTableAsEnum> getCashings() throws MdoDataBeanException {
		return getMdoTableAsEnumByType(MdoTableAsEnumTypeDao.CASHING_TYPE);
	}

	private List<MdoTableAsEnum> getMdoTableAsEnumByType(MdoTableAsEnumTypeDao type) throws MdoDataBeanException {
//		return this.getMdoTableAsEnumByType(new MdoString(type.name()));
		return this.getMdoTableAsEnumByType(type.name());
	}

	@SuppressWarnings("unchecked")
	private List<MdoTableAsEnum> getMdoTableAsEnumByType(String type) throws MdoDataBeanException {
		List<? super MdoTableAsEnum> result = null;

		List<MdoCriteria> criterias = new ArrayList<MdoCriteria>();
		criterias.add(new MdoCriteria("type", PropertiesRestrictions.EQUALS, type));
		criterias.add(new MdoCriteria("order", PropertiesRestrictions.ORDER, null));

//		Map<String, Entry<PropertiesRestrictions, Object>> propertyValueRestrictionMap = new HashMap<String, Entry<PropertiesRestrictions, Object>>();
//		String property = "type";
//		Entry<PropertiesRestrictions, Object> value = new MdoEntry<PropertiesRestrictions, Object>(PropertiesRestrictions.EQUALS, type);
//		propertyValueRestrictionMap.put(property, value);
//		property = "deleted";
//		value = new MdoEntry<PropertiesRestrictions, Object>(PropertiesRestrictions.EQUALS, Boolean.FALSE);
//		propertyValueRestrictionMap.put(property, value);
//		property = "order";
//		value = new MdoEntry<PropertiesRestrictions, Object>(PropertiesRestrictions.ORDER, null);
//		propertyValueRestrictionMap.put(property, value);

		result = super.findByPropertiesRestrictions(criterias);
		return (List<MdoTableAsEnum>) result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<MdoString> findAllTypes() throws MdoException {
		List<MdoString> result = new ArrayList<MdoString>();
		//result = super.findAllByQuery(Constants.HQL_MDO_TABLE_AS_ENUM_SELECT_ALL_TYPES, null, true);

		List<MdoCriteria> criterias = new ArrayList<MdoCriteria>();
		String property = "type";
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property(property));
		criterias.add(new MdoCriteria(property, PropertiesRestrictions.PROJECTION, Projections.distinct(projectionList)));

//		Map<String, Entry<PropertiesRestrictions, Object>> propertyValueMap = new HashMap<String, Entry<PropertiesRestrictions,Object>>();
//		String property = "type";
//		ProjectionList projectionList = Projections.projectionList();
//		projectionList.add(Projections.property(property));
//		Entry<PropertiesRestrictions, Object> value = new MdoEntry<PropertiesRestrictions, Object>(PropertiesRestrictions.PROJECTION, Projections.distinct(projectionList));
//		propertyValueMap.put(property, value);
//		result = super.findByPropertiesRestrictions(propertyValueMap, true);

		result = super.findByPropertiesRestrictions(criterias, true);
		return result;
	}
}
