package fr.mch.mdo.restaurant.dao.hibernate;

import java.beans.PropertyDescriptor;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.expression.Resolver;
import org.hibernate.HibernateException;
import org.hibernate.transform.ResultTransformer;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.beans.OrderLine;

/**
 * Result transformer that allows to transform a result to
 * a user specified class which will be populated via setter
 * methods or fields matching the alias name.
 * The resultBeanTemplate bean is used for being sure that nested property bean is not null.
 * So when alias has nested property, do not forget to instantiate the nested beans. 
 * 
 * @author mathieu
 *
 */
public class MdoAliasToBean implements ResultTransformer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Class<? extends IMdoBean> resultTemplateClass;

	private String[] suppliedAliases;
	
	public MdoAliasToBean(Class<? extends IMdoBean> resultTemplateClass) {
		if (resultTemplateClass == null) {
			throw new IllegalArgumentException("resultTemplateClass cannot be null");
		}
		this.resultTemplateClass = resultTemplateClass;
	}

	public MdoAliasToBean(Class<? extends IMdoBean> resultTemplateClass, String[] suppliedAliases) {
		if (resultTemplateClass == null) {
			throw new IllegalArgumentException("resultTemplateClass cannot be null");
		}
		this.resultTemplateClass = resultTemplateClass;
		this.suppliedAliases = suppliedAliases;
	}

	public Object transformTuple(Object[] tuple, String[] aliases) {
		Object result;

		String[] selectedAliases = this.suppliedAliases;
		if (selectedAliases == null) {
			selectedAliases = aliases;
		}
		try {
			result = resultTemplateClass.newInstance();
			for (int i = 0; i < selectedAliases.length; i++) {
				String alias = selectedAliases[i];
				Object value = tuple[i];
				if (value != null) {
					// When alias has nested property, do not forget to instantiate the nested beans.
					newInstanceForNullNestedProperty(result, alias);
					PropertyUtils.setNestedProperty(result, alias, value);
				}
			}
		} catch (Exception e) {
			throw new HibernateException("Could not instantiate resultclass: "
					+ resultTemplateClass.getName());
		}

		return result;
	}
	
	private void newInstanceForNullNestedProperty(Object bean, String property) throws Exception {
		Resolver resolver = BeanUtilsBean.getInstance().getPropertyUtils().getResolver();

        while (resolver.hasNested(property)) {
            String nestedProperty = resolver.next(property);
            // Get the nested bean
            Object nestedBean = PropertyUtils.getNestedProperty(bean, nestedProperty);
			if (nestedBean == null) {
				PropertyDescriptor nestedPropertyDescriptor = PropertyUtils.getPropertyDescriptor(bean, nestedProperty);
				// Instance nested bean because it is null
				nestedBean = nestedPropertyDescriptor.getPropertyType().newInstance();
				// Set the new instance to the current bean
				PropertyUtils.setNestedProperty(bean, nestedProperty, nestedBean);
			}
			// Switch the current bean
            bean = nestedBean;
            // Remove the current property 
            property = resolver.remove(property);
        }
	}
	
	public static void main(String[] args) throws Exception {
		Object bean = new OrderLine();
		String property = "productSpecialCode.code.name";
		
		MdoAliasToBean alias = new MdoAliasToBean(OrderLine.class);
		alias.newInstanceForNullNestedProperty(bean, property);

		Resolver resolver = BeanUtilsBean.getInstance().getPropertyUtils().getResolver();
		System.out.println(resolver.hasNested("property"));
		
//		Object aliasValue = PropertyUtils.getNestedProperty(result, alias);
//		BeanUtilsBean.getInstance().getPropertyUtils().getResolver().hasNested(alias);
//		if (aliasValue != null) {
//			PropertyUtils.setNestedProperty(
//		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List transformList(List collection) {
		return collection;
	}
}
