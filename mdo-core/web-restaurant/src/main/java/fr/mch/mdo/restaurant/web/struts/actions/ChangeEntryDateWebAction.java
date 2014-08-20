package fr.mch.mdo.restaurant.web.struts.actions;

import java.util.Date;

import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.authentication.IMdoAuthenticationService;
import fr.mch.mdo.restaurant.dao.authentication.AuthenticationPasswordLevel;
import fr.mch.mdo.restaurant.dto.beans.ChangeEntryDateDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.ioc.WebRestaurantBeanFactory;
import fr.mch.mdo.restaurant.ioc.spring.MdoBeanFactory;
import fr.mch.mdo.restaurant.ui.forms.ChangeEntryDateForm;

public final class ChangeEntryDateWebAction extends RestaurantWebAction
{
	/**
     * Default Serial Version UID.
     */
	private static final long serialVersionUID = 1L;

	public ChangeEntryDateWebAction() {
		super(MdoBeanFactory.getInstance().getLogger(ChangeEntryDateWebAction.class.getName()), new ChangeEntryDateForm());
	}

	public String form() throws Exception {
		String result = Constants.ACTION_RESULT_FORM;

		ChangeEntryDateForm form = (ChangeEntryDateForm) super.getForm();
		ChangeEntryDateDto dto = (ChangeEntryDateDto) form.getDtoBean();
		if (dto.getEntryDate() == null) {
			dto.setEntryDate(new Date());
		}
		
		return result;
	}

	public String authenticate() throws Exception {
		String forwardPage = "ajax-response";

		ChangeEntryDateForm form = (ChangeEntryDateForm) super.getForm();
		ChangeEntryDateDto dto = (ChangeEntryDateDto) form.getDtoBean();
		if (dto.getEntryDate() == null) {
			addActionError(getText("message.error.orders.action.change.entry.date.authenticate.entry.date.required"));
		}
		if (dto.getPassword() == null) {
			addActionError(getText("error.password.level.1.required"));
		}

		if (!super.hasActionErrors()) {
			MdoUserContext userContext = (MdoUserContext) form.getUserContext();
			// Check login and password are not null
			super.getLogger().debug("Login : " + userContext.getLogin() + ", Password : " + dto.getPassword() + ", Level Password : " + dto.getLevelPassword());
	
			try {
				IMdoAuthenticationService iMdoAuthenticationService = WebRestaurantBeanFactory.getInstance().getMdoAuthenticationService();
				iMdoAuthenticationService.authenticate(userContext.getLogin(), dto.getPassword(), AuthenticationPasswordLevel.valueOf(dto.getLevelPassword()));
				userContext.setEntryDate(dto.getEntryDate());
			} catch (Exception e) {
				addActionError(getText("error.password.level.1.failed"));
			}
		}
		// Forward control to the specified success URI
		return forwardPage;
	}
}
