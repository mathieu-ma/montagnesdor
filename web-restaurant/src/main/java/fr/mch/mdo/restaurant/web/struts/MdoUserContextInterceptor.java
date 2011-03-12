package fr.mch.mdo.restaurant.web.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.ui.actions.IMdoAction;
		
/**
 * @author Mathieu MA
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class MdoUserContextInterceptor implements Interceptor
{
    /**
     * 
     */
    private static final long serialVersionUID = 1365657444909693487L;

    public void init()
    {
    }

    public void destroy()
    {
    }

    public String intercept(ActionInvocation invocation) throws Exception
    {
	// Get the action context from the invocation so we can access the
	// HttpServletRequest and HttpSession objects.
	final ActionContext context = invocation.getInvocationContext();
	HttpServletRequest request = (HttpServletRequest) context.get(StrutsStatics.HTTP_REQUEST);
	HttpServletResponse response = (HttpServletResponse) context.get(StrutsStatics.HTTP_RESPONSE);

	String defaultEntryURI = MdoStrutsDispatcher.getDefaultEnrtryURI();

	HttpSession session = request.getSession();
	MdoUserContext userContext = (MdoUserContext) session.getAttribute(Constants.USER_SESSION_PREFIX_KEY);

	if (userContext == null)
	{
	    // userContext is only null if the request URI is entry point
	    // No authorization required for entry point

	    String currentScheme = request.getScheme();
	    String currentPort = request.getServerPort()+"";
	    // redirect to login page
	    response.sendRedirect(currentScheme + "://" + request.getServerName() + ":" + currentPort + defaultEntryURI);

	    return Action.NONE;
	}

	if (invocation.getAction() instanceof IMdoAction)
	{
	    IMdoAction action = (IMdoAction) invocation.getAction();
	    action.setUserContext(userContext);
	    if(action.getForm()!=null && action.getForm().getDtoBean()!=null)
	    {
		IMdoDtoBean dtoBean = (IMdoDtoBean)action.getForm().getDtoBean();
		dtoBean.setUserContext(userContext);
	    }
	}
	return invocation.invoke();
    }
}
