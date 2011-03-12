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
import fr.mch.mdo.restaurant.ioc.MdoBeanFactory;

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
     * 
     */
    private static final long serialVersionUID = 5050095343337146669L;

    public void init()
    {
    }

    public void destroy()
    {
    }

    /*
     * Cette méthode est utilisée pour vérifier le choix du language de
     * l'utilisateur. Dans le cas où l'utilisateur n'a pas choisi une langue,
     * c'est le language du navigateur qui est pris en compte.
     */
    public String intercept(ActionInvocation invocation) throws Exception
    {
	// Get the action context from the invocation so we can access the
	// HttpServletRequest and HttpSession objects.
	final ActionContext context = invocation.getInvocationContext();
	HttpServletRequest request = (HttpServletRequest) context.get(StrutsStatics.HTTP_REQUEST);

	HttpSession session = request.getSession();
	MdoUserContext userContext = (MdoUserContext) session.getAttribute(Constants.USER_SESSION_PREFIX_KEY);

	if (userContext == null)
	{
	    MdoStrutsDispatcher.initSession(request);
	}
	else
	{
	    String language = request.getParameter("language");
	    if (language != null)
	    {
		// Change language
		processLanguage(session, new Locale(language, request.getLocale().getCountry()), userContext);
	    }
	    else
	    {
		if (userContext.getCurrentLocale().getLanguage() == null || userContext.getUserAuthentication().getLocales() == null || userContext.getUserAuthentication().getLocales().isEmpty())
		{
		    Locale locale = request.getLocale();
		    if (userContext.getUser() != null && userContext.getUserAuthentication().getPreferedLocale() != null && userContext.getUserAuthentication().getPreferedLocale().getLanguage() != null)
		    {
			locale = new Locale(userContext.getUserAuthentication().getPreferedLocale().getLanguage(), request.getLocale().getCountry());
		    }
		    // Once authentication done then
		    // userContext.getCurrentLocale().getLanguage()==null
		    // Or no locale links to this user, so always set the
		    // request one
		    processLanguage(session, locale, userContext);
		}
	    }

	}
	return super.intercept(invocation);
    }

    private void processLanguage(HttpSession session, Locale locale, IMdoBean userContext)
    {
	MdoUserContext userContextX = (MdoUserContext) userContext;
	Locale xLocale = locale;
	if (xLocale == null)
	{
	    xLocale = Locale.getDefault();
	}

	try
	{
	    userContextX.setCurrentLocale(MdoBeanFactory.getInstance().getLocalesManager().getLocaleFromUser(xLocale, userContextX.getUserAuthentication()));
	    userContextX.setSystemAvailableLanguages(MdoBeanFactory.getInstance().getLocalesManager().getSystemAvailableLanguages(xLocale));
	}
	catch (Exception e)
	{

	}
	xLocale = new Locale(userContextX.getCurrentLocale().getLanguage(), xLocale.getCountry());
	MdoStrutsDispatcher.initI18nSession(session, xLocale);
    }
}
