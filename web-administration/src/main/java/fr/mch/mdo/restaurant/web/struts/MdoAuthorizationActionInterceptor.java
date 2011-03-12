package fr.mch.mdo.restaurant.web.struts;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

import fr.mch.mdo.restaurant.Constants;
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
public class MdoAuthorizationActionInterceptor implements Interceptor 
{
	/**
     * 
     */
	private static final long serialVersionUID = 5958586297654689531L;

	public void init() {
	}

	public void destroy() {
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		// Get the action context from the invocation so we can access the
		// HttpServletRequest and HttpSession objects.
		final ActionContext context = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) context.get(StrutsStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) context.get(StrutsStatics.HTTP_RESPONSE);

		HttpSession session = request.getSession();
		MdoUserContext userContext = (MdoUserContext) session.getAttribute(Constants.USER_SESSION_PREFIX_KEY);

		String defaultEntryURI = MdoStrutsDispatcher.getDefaultEnrtryURI();
		String schemeHTTPS = ServletActionContext.getServletContext().getInitParameter(Constants.SCHEME_HTTPS_KEY);
		String portHTTPS = ServletActionContext.getServletContext().getInitParameter(Constants.PORT_HTTPS_KEY);

		boolean isRedirect = true;
		if (userContext != null) {
			// This is done for chained actions
			String requestedAction = (new StringBuffer(invocation.getProxy().getNamespace()).append("/").append(invocation.getProxy().getActionName())).toString();

			Subject subject = userContext.getSubject();
			if (WebAdministractionBeanFactory.getInstance().getMdoAuthorizationService().permitted(subject, requestedAction) != null) {
				isRedirect = false;
			}
		}

		if (isRedirect) {
			// String switchSchemeStr =
			// ServletActionContext.getServletContext().getInitParameter("switchScheme");
			// int switchScheme = 0;
			// String currentScheme = request.getScheme();
			// String currentPort = request.getServerPort()+"";
			// try
			// {
			// switchScheme = Integer.parseInt(switchSchemeStr);
			// }
			// catch(NumberFormatException e)
			// {
			// }
			// switch(switchScheme)
			// {
			// case 21 :
			// currentScheme = schemeHTTPS;
			// currentPort = portHTTPS;
			// break;
			// }
			String currentScheme = request.getScheme();
			String currentPort = request.getServerPort() + "";

			session.removeAttribute(Constants.USER_SESSION_PREFIX_KEY);
			response.sendRedirect(currentScheme + "://" + request.getServerName() + ":" + currentPort + defaultEntryURI);
			return Action.NONE;
		}
		return invocation.invoke();
	}
}
