package fr.mch.mdo.restaurant.web.struts;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.ioc.spring.WebAdministractionBeanFactory;

/**
 * @author Mathieu MA
 * 
 *         To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class MdoStrutsDispatcher extends StrutsPrepareAndExecuteFilter 
{
	ILogger logger = WebAdministractionBeanFactory.getInstance().getLogger(MdoStrutsDispatcher.class.getName());

	protected FilterConfig filterConfig;

	private static String defaultEntryURI = null;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		logger.debug("** START Request = " + System.currentTimeMillis() + "**");

		// ConvertUtils.register(new DateLocaleConverter(Locale.getDefault(),
		// "yyyyMMdd"), java.util.Date.class);

		// This is required for UTF8 user setter parameters:
		// Example:
		// 1) User enters "tété" in HTML input type=text field named
		// "bean.name".
		// 2) Struts2 fills the name of bean with "t?t?" if we don't perform
		// request.getParameter("foo");
		request.setCharacterEncoding("UTF-8");
		request.getParameter("foo");
		// This is default value equals to the instruction <%@page
		// contentType="text/html; charset=UTF-8"%>
		// Could override in JSP page
		response.setContentType("text/html; charset=UTF-8");

		// if(((Log4JLogger)logger).isDebugEnabled())
		{
			logger.debug("List of Request Parameters: ");
			for (Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();) {
				String s = e.nextElement();
				String[] sArray = request.getParameterValues(s);
				for (int i = 0; i < sArray.length; i++) {
					logger.debug("**" + s + "[" + i + "]==" + sArray[i] + "**");
				}
			}
		}

		super.doFilter(request, response, chain);

		logger.debug("** END Request = " + System.currentTimeMillis() + "**");
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		defaultEntryURI = filterConfig.getServletContext().getContextPath() + filterConfig.getServletContext().getInitParameter(Constants.DEFAULT_ENTRY_URI_KEY);
		this.filterConfig = filterConfig;

		super.init(filterConfig);
	}

	public static void initSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(Constants.USER_SESSION_PREFIX_KEY);
		initI18nSession(request.getSession(), request.getLocale());
	}

	public static void initI18nSession(HttpSession session, Locale locale) {
		// Struts Configuration For I18n=WW_TRANS_I18N_LOCALE this is will be done in the class MdoProcessLanguageInterceptor extends I18nInterceptor 
//		session.setAttribute(I18nInterceptor.DEFAULT_SESSION_ATTRIBUTE, locale);
		// JSTL Configuration For I18n
		Config.set(session, Config.FMT_LOCALE, locale);
	}

	public static String getDefaultEnrtryURI() {
		return defaultEntryURI;
	}
}
