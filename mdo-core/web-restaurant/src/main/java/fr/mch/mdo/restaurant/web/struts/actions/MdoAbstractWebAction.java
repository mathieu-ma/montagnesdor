package fr.mch.mdo.restaurant.web.struts.actions;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.ui.actions.IMdoAction;
import fr.mch.mdo.restaurant.ui.forms.IMdoForm;

public abstract class MdoAbstractWebAction extends ActionSupport implements ServletRequestAware, IMdoAction
{
    /** Captured only to show that undesired data can creep into the result. */
    private HttpServletRequest request;

    private ILogger logger;
    
    private IMdoForm form;
    
    private IMdoBean userContext;

    @SuppressWarnings("unused")
    private MdoAbstractWebAction()
    {
    }
    
    protected MdoAbstractWebAction(ILogger logger, IMdoForm form)
    {
	this.logger = logger;
	this.form = form;
    }

    @Override
    public void setServletRequest(HttpServletRequest request)
    {
	this.request = request;
    }

    public HttpServletRequest getRequest()
    {
        return request;
    }
    
    @Override    
    public ILogger getLogger()
    {
	return logger;
    }

    @Override    
    public void setLogger(ILogger logger)
    {
	this.logger = logger;
    }
    
    @Override
    public IMdoForm getForm()
    {
	return form;
    }

    @Override
    public void setForm(IMdoForm form)
    {
	this.form = form;
    }
    
    @Override
    public void setUserContext(IMdoBean userContext)
    {
//	HttpSession session = this.getRequest().getSession();
//	session.setAttribute(Constants.USER_SESSION_PREFIX_KEY, userContext);
	this.userContext = userContext;
    }
    
    @Override
    public IMdoBean getUserContext()
    {
//	HttpSession session = this.getRequest().getSession();
//      return (MdoUserContext) session.getAttribute(Constants.USER_SESSION_PREFIX_KEY);
        return userContext;
    }
}
