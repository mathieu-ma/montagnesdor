package fr.mch.mdo.restaurant.web.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.ui.actions.IMdoAction;

/**
 * @author Mathieu MA
 * 
 *         To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class MdoUserContextInterceptor implements Interceptor 
{
	/**
     * 
     */
	private static final long serialVersionUID = 1365657444909693487L;

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

		String defaultEntryURI = MdoStrutsDispatcher.getDefaultEnrtryURI();
		String schemeHTTPS = ServletActionContext.getServletContext().getInitParameter(Constants.SCHEME_HTTPS_KEY);
		String portHTTPS = ServletActionContext.getServletContext().getInitParameter(Constants.PORT_HTTPS_KEY);

		HttpSession session = request.getSession();
		MdoUserContext userContext = (MdoUserContext) session.getAttribute(Constants.USER_SESSION_PREFIX_KEY);

		if (userContext == null) {
			// userContext is only null if the request URI is entry point
			// No authorization required for entry point

			String currentScheme = request.getScheme();
			String currentPort = request.getServerPort() + "";
			// String switchSchemeStr =
			// ServletActionContext.getServletContext().getInitParameter("switchScheme");
			// int switchScheme = 0;
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
			// redirect to login page
			response.sendRedirect(currentScheme + "://" + request.getServerName() + ":" + currentPort + defaultEntryURI);

			return Action.NONE;
		}

		if (invocation.getAction() instanceof IMdoAction) {
			IMdoAction action = (IMdoAction) invocation.getAction();
			if (action.getForm() != null && action.getForm() != null) {
				action.getForm().setUserContext(userContext);
			}
		}
		// This is used to for changing flag country language to stay in the same page
		userContext.setCurrentNameSpace(invocation.getProxy().getNamespace());
		userContext.setCurrentActionName(invocation.getProxy().getActionName());
		userContext.setCurrentActionMethod(invocation.getProxy().getMethod());
		userContext.setCurrentURLWithParameters(getCurrentURL(request, true));
		userContext.setCurrentURL(getCurrentURL(request, false));

		return invocation.invoke();
	}

	private String getCurrentURL(HttpServletRequest request, boolean isIncludeParameters) {

		StringBuilder result = new StringBuilder();

		result.append(request.getRequestURL().toString());
		if (isIncludeParameters && StringUtils.isNotEmpty(request.getQueryString())) {
			result.append("?").append(request.getQueryString());
		}

		return result.toString();
	}
}
