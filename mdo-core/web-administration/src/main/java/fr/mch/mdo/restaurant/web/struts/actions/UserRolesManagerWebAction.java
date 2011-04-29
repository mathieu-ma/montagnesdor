package fr.mch.mdo.restaurant.web.struts.actions;

import fr.mch.mdo.restaurant.ioc.spring.WebAdministractionBeanFactory;
import fr.mch.mdo.restaurant.ui.forms.UserRolesManagerForm;

public class UserRolesManagerWebAction extends AdministrationManagerAction 
{
	/**
	 * Default Serial Version UID
     */
	private static final long serialVersionUID = 1L;

	public UserRolesManagerWebAction() {
		super(WebAdministractionBeanFactory.getInstance().getLogger(UserRolesManagerWebAction.class.getName()), new UserRolesManagerForm());
		administrationManager = WebAdministractionBeanFactory.getInstance().getUserRolesManager();
	}

	public String labels() throws Exception {
		super.save();
		return super.form();
	}
}