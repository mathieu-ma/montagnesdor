package fr.mch.mdo.restaurant.web.struts;

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

/**
 * @author Mathieu MA
 * 
 *         To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class MdoSwitchSchemeInterceptor implements Interceptor
{
    /**
     * 
     */
    private static final long serialVersionUID = 7292151516143671927L;

    private String patternMethodAction = "";
    private int switchScheme = Constants.SWITCH_SCHEME_NONE;
    private String defaultEntryPatternWithMethodAction = "";

    @Override
    public void init()
    {
	defaultEntryPatternWithMethodAction = MdoStrutsDispatcher.getDefaultEnrtryURI();
	int i = 0;
	if ((i = defaultEntryPatternWithMethodAction.lastIndexOf(".")) > 0)
	{
	    defaultEntryPatternWithMethodAction = defaultEntryPatternWithMethodAction.substring(0, i) + patternMethodAction + defaultEntryPatternWithMethodAction.substring(i);
	}
    }

    public void destroy()
    {
    }

    public String intercept(ActionInvocation invocation) throws Exception
    {
	String result = null;
	switch (switchScheme)
	{
	    case Constants.SWITCH_SCHEME_NONE:
		result = switchSchemeNone(invocation);
	    break;
	    case Constants.SWITCH_SCHEME_ONLY_HTTP:
		result = switchSchemeOnlyHttp(invocation);
	    break;
	    case Constants.SWITCH_SCHEME_ONLY_HTTPS:
		result = switchSchemeOnlyHttps(invocation);
	    break;
	    case Constants.SWITCH_SCHEME_HTTPS_ENTRY_POINT_ONLY:
		result = switchSchemeHttpsEntryPointOnly(invocation);
	    break;
	}

	if (result == null)
	{
	    return invocation.invoke();
	}
	else
	{
	    return result;
	}
    }

    public String switchSchemeNone(ActionInvocation invocation) throws Exception
    {
	return null;
    }

    public String switchSchemeOnlyHttp(ActionInvocation invocation) throws Exception
    {
	String result = null;

	// Get the action context from the invocation so we can access the
	// HttpServletRequest and HttpSession objects.
	final ActionContext context = invocation.getInvocationContext();
	HttpServletRequest request = (HttpServletRequest) context.get(StrutsStatics.HTTP_REQUEST);
	HttpServletResponse response = (HttpServletResponse) context.get(StrutsStatics.HTTP_RESPONSE);

	String schemeHTTP = ServletActionContext.getServletContext().getInitParameter(Constants.SCHEME_HTTP_KEY);
	String portHTTP = ServletActionContext.getServletContext().getInitParameter(Constants.PORT_HTTP_KEY);

	if (request.isSecure())
	{
	    String url = schemeHTTP + "://" + request.getServerName() + ":" + portHTTP + request.getRequestURI();
	    if (request.getQueryString() != null)
	    {
		url += "?" + request.getQueryString();
	    }
	    response.sendRedirect(url);
	    result = Action.NONE;
	}
	return result;
    }

    public String switchSchemeOnlyHttps(ActionInvocation invocation) throws Exception
    {
	String result = null;

	// Get the action context from the invocation so we can access the
	// HttpServletRequest and HttpSession objects.
	final ActionContext context = invocation.getInvocationContext();
	HttpServletRequest request = (HttpServletRequest) context.get(StrutsStatics.HTTP_REQUEST);
	HttpServletResponse response = (HttpServletResponse) context.get(StrutsStatics.HTTP_RESPONSE);

	String schemeHTTPS = ServletActionContext.getServletContext().getInitParameter(Constants.SCHEME_HTTPS_KEY);
	String portHTTPS = ServletActionContext.getServletContext().getInitParameter(Constants.PORT_HTTPS_KEY);

	if (!request.isSecure())
	{
	    String url = schemeHTTPS + "://" + request.getServerName() + ":" + portHTTPS + request.getRequestURI();
	    if (request.getQueryString() != null)
	    {
		url += "?" + request.getQueryString();
	    }
	    response.sendRedirect(url);
	    result = Action.NONE;
	}
	return result;
    }

    public String switchSchemeHttpsEntryPointOnly(ActionInvocation invocation) throws Exception
    {
	String result = null;

	// Get the action context from the invocation so we can access the
	// HttpServletRequest and HttpSession objects.
	final ActionContext context = invocation.getInvocationContext();
	HttpServletRequest request = (HttpServletRequest) context.get(StrutsStatics.HTTP_REQUEST);
	HttpServletResponse response = (HttpServletResponse) context.get(StrutsStatics.HTTP_RESPONSE);

	// String selectedMenuItemId =
	// request.getParameter("selectedMenuItemId");

	// Be sure that the first entry point request is a HTTP request and
	// remember it to the HTTP request session
	// TOMCAT keeps alive the HTTP request session but not the HTTPS request
	// session. This means:
	// 1) Firstly, the application is in HTTP context and requires a request
	// session then TOMCAT creates a HTTP request session.
	// 2) Secondly, the application is in HTTPS context and requires a
	// request session then TOMCAT does not create a new HTTPS request
	// session but gives the previous HTTP request session.
	HttpSession session = request.getSession();

	String defaultEntryURI = MdoStrutsDispatcher.getDefaultEnrtryURI();
	String schemeHTTPS = ServletActionContext.getServletContext().getInitParameter(Constants.SCHEME_HTTPS_KEY);
	String portHTTPS = ServletActionContext.getServletContext().getInitParameter(Constants.PORT_HTTPS_KEY);
	String schemeHTTP = ServletActionContext.getServletContext().getInitParameter(Constants.SCHEME_HTTP_KEY);
	String portHTTP = ServletActionContext.getServletContext().getInitParameter(Constants.PORT_HTTP_KEY);

	if (request.isSecure())
	{
	    if (session.getAttribute(Constants.SCHEME_HTTP_KEY) == null)
	    {
		// If the HTTPS request session is not equal to the HTTP request
		// session i.e session.getAttribute(schemeHTTP)==null
		response.sendRedirect(schemeHTTP + "://" + request.getServerName() + ":" + portHTTP + defaultEntryURI);
		result = Action.NONE;
	    }
	    if (!request.getRequestURI().matches(defaultEntryPatternWithMethodAction))
	    {
		// If browser does not request the entry point

		// Use HTTPS only for entry point
		String url = schemeHTTP + "://" + request.getServerName() + ":" + portHTTP + request.getRequestURI();
		if (request.getQueryString() != null)
		{
		    url += "?" + request.getQueryString();
		}
		response.sendRedirect(url);
		result = Action.NONE;
	    }
	}
	else
	{
	    if (session.getAttribute(Constants.SCHEME_HTTP_KEY) == null)
	    {
		// This is only used as a flag to know that the application is
		// entered at least once in HTTP context
		session.setAttribute(Constants.SCHEME_HTTP_KEY, schemeHTTP);
	    }

	    if (request.getRequestURI().matches(defaultEntryPatternWithMethodAction))
	    {
		response.sendRedirect(schemeHTTPS + "://" + request.getServerName() + ":" + portHTTPS + defaultEntryURI);
		result = Action.NONE;
	    }
	}
	return result;
    }

    public String getPatternMethodAction()
    {
	return patternMethodAction;
    }

    public void setPatternMethodAction(String patternMethodAction)
    {
	this.patternMethodAction = patternMethodAction;
    }

    public int getSwitchScheme()
    {
        return switchScheme;
    }

    public void setSwitchScheme(int switchScheme)
    {
        this.switchScheme = switchScheme;
    }
}
