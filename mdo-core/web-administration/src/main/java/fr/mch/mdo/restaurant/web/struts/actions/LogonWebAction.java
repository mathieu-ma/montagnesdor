package fr.mch.mdo.restaurant.web.struts.actions;

import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.Action;

import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.authentication.IMdoAuthenticationService;
import fr.mch.mdo.restaurant.dto.beans.LogonDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.UserAuthenticationDto;
import fr.mch.mdo.restaurant.ioc.spring.WebAdministractionBeanFactory;
import fr.mch.mdo.restaurant.ui.actions.ILogonAction;
import fr.mch.mdo.restaurant.ui.forms.LogonForm;
import fr.mch.mdo.restaurant.web.struts.MdoStrutsDispatcher;

public final class LogonWebAction extends MdoAbstractWebAction implements ILogonAction 
{
	/**
     * Default Serial Version UID
     */
	private static final long serialVersionUID = 1L;

	public LogonWebAction() {
		super(WebAdministractionBeanFactory.getInstance().getLogger(LogonWebAction.class.getName()), new LogonForm());
	}

	public String authenticate() throws Exception {
		String forwardPage = Action.INPUT;

		HttpSession session = super.getRequest().getSession();
		if (session != null) {
			LogonDto dto = (LogonDto) super.getForm().getDtoBean();
			// Check login and password are not null
			super.getLogger().debug("login : " + dto.getLogin() + " password : " + dto.getPassword());

			try {
				IMdoAuthenticationService iMdoAuthenticationService = WebAdministractionBeanFactory.getInstance().getMdoAuthenticationService();
				MdoUserContext userContext = (MdoUserContext) iMdoAuthenticationService.authenticate(dto.getLogin(), dto.getPassword());
				// Fill User Context bean
				UserAuthenticationDto userAuthentication = (UserAuthenticationDto) WebAdministractionBeanFactory.getInstance().getUserAuthenticationsManager().findByLogin(dto.getLogin(), userContext);
				userContext.setUserAuthentication(userAuthentication);
				session.setAttribute(Constants.USER_SESSION_PREFIX_KEY, userContext);
				forwardPage = Action.SUCCESS;
			} catch (Exception e) {
				addActionError(getText("error.authentication.failed"));
				MdoStrutsDispatcher.initSession(super.getRequest());
			}
		}

		// Forward control to the specified success URI
		return forwardPage;
	}

	public String execute() throws Exception {
		return Action.INPUT;
	}
}
