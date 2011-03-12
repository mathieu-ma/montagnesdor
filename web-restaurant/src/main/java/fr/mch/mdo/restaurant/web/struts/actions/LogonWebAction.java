package fr.mch.mdo.restaurant.web.struts.actions;

import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.Action;

import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.authentication.IMdoAuthenticationService;
import fr.mch.mdo.restaurant.dto.beans.LogonDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.ioc.MdoBeanFactory;
import fr.mch.mdo.restaurant.ui.actions.ILogonAction;
import fr.mch.mdo.restaurant.ui.forms.LogonForm;
import fr.mch.mdo.restaurant.web.struts.MdoStrutsDispatcher;

public final class LogonWebAction extends MdoAbstractWebAction implements ILogonAction
{
    /**
     * 
     */
    private static final long serialVersionUID = -2361155708074907086L;

    public LogonWebAction()
    {
	super(MdoBeanFactory.getInstance().getLogger(LogonWebAction.class.getName()), new LogonForm());
    }

    public String authenticate() throws Exception
    {
	String forwardPage = Action.INPUT;

	HttpSession session = super.getRequest().getSession();
	if (session != null)
	{
	    LogonDto dto = (LogonDto) super.getForm().getDtoBean();
	    // Check login and password are not null
	    super.getLogger().debug("login : " + dto.getLogin() + " password : " + dto.getPassword());

	    try
	    {
		IMdoAuthenticationService iMdoAuthenticationService = MdoBeanFactory.getInstance().getMdoAuthenticationService();
		session.setAttribute(Constants.USER_SESSION_PREFIX_KEY, iMdoAuthenticationService.authenticate(dto.getLogin(), dto.getPassword()));

		forwardPage = Action.SUCCESS;
	    }
	    catch (Exception e)
	    {
		addActionError(getText("error.authentication.failed"));
		MdoStrutsDispatcher.initSession(super.getRequest());
	    }
	}

	// Forward control to the specified success URI
	return forwardPage;
    }

    public String execute() throws Exception
    {
	return Action.INPUT;
    }
}
