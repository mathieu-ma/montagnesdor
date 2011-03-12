package fr.mch.mdo.restaurant.web.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;

/**
 * @author Mathieu MA
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class MdoParamMenuIdInterceptor implements Interceptor
{
    /**
     * 
     */
    private static final long serialVersionUID = -5912183615588038860L;
    
    private String selectedMenuItemIdKey;

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

	String selectedMenuItemId = request.getParameter(selectedMenuItemIdKey);

	HttpSession session = request.getSession();
	MdoUserContext userContext = (MdoUserContext) session.getAttribute(Constants.USER_SESSION_PREFIX_KEY);
	if (userContext == null)
	{
	    MdoStrutsDispatcher.initSession(request);
	}
	else
	{
	    if (selectedMenuItemId != null)
	    {
		userContext.setSelectedMenuItemId(selectedMenuItemId);
	    }
	}
	return invocation.invoke();
    }

    public String getSelectedMenuItemIdKey()
    {
	return selectedMenuItemIdKey;
    }

    public void setSelectedMenuItemIdKey(String selectedMenuItemIdKey)
    {
	this.selectedMenuItemIdKey = selectedMenuItemIdKey;
    }
}
