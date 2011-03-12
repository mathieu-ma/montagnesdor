package fr.mch.mdo.restaurant.web.struts.actions;

import fr.mch.mdo.restaurant.ioc.spring.WebAdministractionBeanFactory;
import fr.mch.mdo.restaurant.ui.forms.TableTypesManagerForm;

public class TableTypesManagerWebAction extends AdministrationManagerAction 
{
	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	public TableTypesManagerWebAction() {
		super(WebAdministractionBeanFactory.getInstance().getLogger(TableTypesManagerWebAction.class.getName()), new TableTypesManagerForm());
		administrationManager = WebAdministractionBeanFactory.getInstance().getTableTypesManager();
	}

	@Override
	public String form() throws Exception {
		String result = super.form();
		super.list();
		return result;
	}

}
