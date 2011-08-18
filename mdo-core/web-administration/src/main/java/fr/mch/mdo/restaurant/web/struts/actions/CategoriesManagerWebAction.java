package fr.mch.mdo.restaurant.web.struts.actions;

import fr.mch.mdo.restaurant.ioc.spring.WebAdministrationBeanFactory;
import fr.mch.mdo.restaurant.ui.forms.CategoriesManagerForm;

public class CategoriesManagerWebAction extends AdministrationManagerLabelsAction 
{
	/**
	 * Default Serial Version UID
     */
	private static final long serialVersionUID = 1L;

	public CategoriesManagerWebAction() {
		super(WebAdministrationBeanFactory.getInstance().getLogger(CategoriesManagerWebAction.class.getName()), new CategoriesManagerForm());
		administrationManager = WebAdministrationBeanFactory.getInstance().getCategoriesManager();
	}
}
