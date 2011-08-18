package fr.mch.mdo.restaurant.web.struts.actions;

import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.ioc.spring.WebAdministrationBeanFactory;
import fr.mch.mdo.restaurant.ui.forms.LocalesManagerForm;

public class LocalesManagerWebAction extends AdministrationManagerAction 
{
	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	public LocalesManagerWebAction() {
		super(WebAdministrationBeanFactory.getInstance().getLogger(LocalesManagerWebAction.class.getName()), new LocalesManagerForm());
		administrationManager = WebAdministrationBeanFactory.getInstance().getLocalesManager();
	}

	@Override
	public String form() throws Exception {
		super.validate();

		return Constants.ACTION_RESULT_LIST;
	}
}
