package fr.mch.mdo.restaurant.web.struts;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.I18nInterceptor;

import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.ioc.spring.WebAdministractionBeanFactory;

/**
 * @author Mathieu MA
 * 
 *         To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class MdoProcessLanguageInterceptor extends I18nInterceptor 
{
	/**
     * Default Serial Version UID
     */
	private static final long serialVersionUID = 1L;
	
	public void init() {
	}

	public void destroy() {
	}

	@Override
	/**
	 * This method will be used to check the user language.
	 * In cas of user not choose any language then the browser language is taken into account.
	 */
	public String intercept(ActionInvocation invocation) throws Exception {
		// Get the action context from the invocation so we can access the
		// HttpServletRequest and HttpSession objects.
		final ActionContext context = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) context.get(StrutsStatics.HTTP_REQUEST);

		HttpSession session = request.getSession();
		MdoUserContext userContext = (MdoUserContext) session.getAttribute(Constants.USER_SESSION_PREFIX_KEY);

		if (userContext == null) {
			MdoStrutsDispatcher.initSession(request);
		} else {
			String language = request.getParameter(super.parameterName);
			Locale locale = request.getLocale();
			if (language != null) {
				// Change language
				locale = new Locale(language, request.getLocale().getCountry());
				this.processLanguage(session, locale, userContext);
			} else {
				if (userContext.getCurrentLocale().getLanguageCode() == null || userContext.getUserAuthentication().getLocales() == null
						|| userContext.getUserAuthentication().getLocales().isEmpty()) {
					// Once authentication done then
					// userContext.getCurrentLocale().getLanguageCode()==null
					// Or no locale links to this user, so always set the
					// request one
//					if (userContext.getUser() != null && userContext.getUserAuthentication().getPrintingLocale() != null
//							&& userContext.getUserAuthentication().getPrintingLocale().getLanguageCode() != null) {
//						locale = new Locale(userContext.getUserAuthentication().getPrintingLocale().getLanguageCode(), request.getLocale().getCountry());
//					}
					// Add language parameter in order to force the Struts2 to change the language 
					invocation.getInvocationContext().getParameters().put(parameterName, locale);
					this.processLanguage(session, locale, userContext);
				}
			}
		}
		return super.intercept(invocation);
	}

	private void processLanguage(HttpSession session, Locale locale, IMdoBean userContext) {
		MdoUserContext userContextX = (MdoUserContext) userContext;
		Locale xLocale = locale;
		if (xLocale == null) {
			xLocale = Locale.getDefault();
		}

		try {
			userContextX.setCurrentLocale(WebAdministractionBeanFactory.getInstance().getLocalesManager().findLocale(xLocale, userContextX));
			// This is used for displaying language flag
			userContextX.setSystemAvailableLanguages(WebAdministractionBeanFactory.getInstance().getLocalesManager().getSystemAvailableLanguages(xLocale));
		} catch (Exception e) {

		}
		xLocale = new Locale(userContextX.getCurrentLocale().getLanguageCode(), xLocale.getCountry());
		MdoStrutsDispatcher.initI18nSession(session, xLocale);
	}
}
