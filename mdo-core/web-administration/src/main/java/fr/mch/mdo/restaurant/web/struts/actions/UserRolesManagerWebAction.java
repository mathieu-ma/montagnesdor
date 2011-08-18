package fr.mch.mdo.restaurant.web.struts.actions;

import fr.mch.mdo.restaurant.ioc.spring.WebAdministrationBeanFactory;
import fr.mch.mdo.restaurant.ui.forms.UserRolesManagerForm;

public class UserRolesManagerWebAction extends AdministrationManagerLabelsAction 
{
	/**
	 * Default Serial Version UID
     */
	private static final long serialVersionUID = 1L;

	public UserRolesManagerWebAction() {
		super(WebAdministrationBeanFactory.getInstance().getLogger(UserRolesManagerWebAction.class.getName()), new UserRolesManagerForm());
		administrationManager = WebAdministrationBeanFactory.getInstance().getUserRolesManager();
	}
}
