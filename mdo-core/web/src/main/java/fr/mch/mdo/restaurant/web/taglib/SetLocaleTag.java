package fr.mch.mdo.restaurant.web.taglib;

import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.jstl.core.Config;

import com.opensymphony.xwork2.ActionContext;

public class SetLocaleTag extends org.apache.taglibs.standard.tag.el.fmt.SetLocaleTag
{
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public int doEndTag() throws JspException {

		Locale locale = null;

		if (value == null) {
		    locale = Locale.getDefault();
		} else if (value instanceof String) {
		    if (((String) value).trim().equals("")) {
			locale = Locale.getDefault();
		    } else {
			locale = parseLocale((String) value, variant);
		    }
		} else {
		    locale = (Locale) value;
		}

		// Used for Struts2
		pageContext.getSession().setAttribute(Config.FMT_LOCALE, locale);
		ActionContext.getContext().setLocale(locale);

		return super.doEndTag();
	}
	
}
