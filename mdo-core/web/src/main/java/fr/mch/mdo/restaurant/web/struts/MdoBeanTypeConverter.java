package fr.mch.mdo.restaurant.web.struts;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import fr.mch.mdo.restaurant.beans.IMdoBean;

public class MdoBeanTypeConverter extends StrutsTypeConverter
{
	@SuppressWarnings("rawtypes")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (values.length != 1) {
			super.performFallbackConversion(context, values, toClass);
		}

		IMdoBean bean = null;
		String id = values[0];
		if (id != null) {
			try {
				bean = (IMdoBean) toClass.newInstance();
				// bean.setId(new Long(id.toString()));
			} catch (Exception e) {
				bean = null;
			}
		}
		return bean;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String convertToString(Map context, Object o) {
		IMdoBean bean = (IMdoBean) o;
		// return bean.getId().toString();
		return bean.toString();
	}
}
