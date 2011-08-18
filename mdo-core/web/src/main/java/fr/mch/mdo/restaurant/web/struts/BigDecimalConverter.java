package fr.mch.mdo.restaurant.web.struts;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.ActionContext;

import fr.mch.mdo.restaurant.beans.IMdoBean;

public class BigDecimalConverter extends StrutsTypeConverter
{
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (values.length != 1) {
			super.performFallbackConversion(context, values, toClass);
		}

		BigDecimal result = BigDecimal.ZERO;
		String stringValue = values[0];
		Number number = this.doConvertToNumber(context, stringValue);
		if (number != null) {
			result = new BigDecimal(number.toString());
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String convertToString(Map context, Object o) {
		IMdoBean bean = (IMdoBean) o;
		// return bean.getId().toString();
		return bean.toString();
	}

	private Number doConvertToNumber(Map<String, Object> context, Object value) {
		Number result = BigDecimal.ZERO;
		if (value instanceof String) {
			String stringValue = (String) value;
			NumberFormat numFormat = NumberFormat.getInstance(getLocale(context));
			ParsePosition parsePos = new ParsePosition(0);
			numFormat.setGroupingUsed(true);
			result = numFormat.parse(stringValue, parsePos);
		}
		return result;
	}

	private Locale getLocale(Map<String, Object> context) {
		if (context == null) {
			return Locale.getDefault();
		}

		Locale locale = (Locale) context.get(ActionContext.LOCALE);

		if (locale == null) {
			locale = Locale.getDefault();
		}

		return locale;
	}
}
