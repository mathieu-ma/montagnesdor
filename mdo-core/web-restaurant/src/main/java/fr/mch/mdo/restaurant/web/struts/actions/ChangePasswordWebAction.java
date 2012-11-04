package fr.mch.mdo.restaurant.web.struts.actions;

import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.authentication.IMdoAuthenticationService;
import fr.mch.mdo.restaurant.dao.authentication.AuthenticationPasswordLevel;
import fr.mch.mdo.restaurant.dto.beans.ChangePasswordDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.ioc.spring.MdoBeanFactory;
import fr.mch.mdo.restaurant.services.WebRestaurantBeanFactory;
import fr.mch.mdo.restaurant.services.business.managers.users.IUserAuthenticationsManager;
import fr.mch.mdo.restaurant.ui.forms.ChangePasswordForm;

public final class ChangePasswordWebAction extends MdoAbstractWebAction
{
	/**
     * Default Serial Version UID.
     */
	private static final long serialVersionUID = 1L;

	IUserAuthenticationsManager userManager = WebRestaurantBeanFactory.getInstance().getUserAuthenticationsManager();

	public ChangePasswordWebAction() {
		super(WebRestaurantBeanFactory.getInstance().getLogger(ChangePasswordWebAction.class.getName()), new ChangePasswordForm());
	}

	public String form() throws Exception {
		String result = Constants.ACTION_RESULT_FORM;

		return result;
	}

	public String authenticate() throws Exception {
		String forwardPage = "ajax-response";

		ChangePasswordForm form = (ChangePasswordForm) super.getForm();
		ChangePasswordDto dto = (ChangePasswordDto) form.getDtoBean();

		if (dto.getPassword() == null) {
			addActionError(getText("error.current.password.level.required"));
		}
		if (form.getNewPasswordConfirmed() == null) {
			addActionError(getText("error.new.confirmed.password.level.required"));
		}
		if (dto.getNewPassword() == null) {
			addActionError(getText("error.new.password.level.required"));
		}
		if (form.getNewPasswordConfirmed() != null && dto.getNewPassword() != null && !form.getNewPasswordConfirmed().equals(dto.getNewPassword())) {
			addActionError(getText("error.new.confirmed.password.level.different"));
		}

		// Check login and password are not null
		MdoUserContext userContext = (MdoUserContext) form.getUserContext();
		super.getLogger().debug("Login : " + userContext.getLogin() + ", Password : " + dto.getPassword() + ", Level Password : " + dto.getLevelPassword());

		try {
			IMdoAuthenticationService authenticationService = MdoBeanFactory.getInstance().getMdoAuthenticationService();
			AuthenticationPasswordLevel levelPassword = AuthenticationPasswordLevel.values()[dto.getLevelPassword()];
			authenticationService.authenticate(userContext.getLogin(), dto.getPassword(), levelPassword);
			userManager.changePassword(userContext.getUserAuthentication().getId(), levelPassword.name(), dto.getNewPassword());
		} catch (MdoException e) {
			addActionError(getText("error.password.level.failed"));
		}

		// Forward control to the specified success URI
		return forwardPage;
	}
}
