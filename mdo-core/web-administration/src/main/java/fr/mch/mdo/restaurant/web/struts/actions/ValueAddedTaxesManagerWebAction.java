package fr.mch.mdo.restaurant.web.struts.actions;

import com.opensymphony.xwork2.util.LocalizedTextUtil;

import fr.mch.mdo.restaurant.ioc.spring.WebAdministractionBeanFactory;
import fr.mch.mdo.restaurant.ui.forms.ValueAddedTaxesManagerForm;

public class ValueAddedTaxesManagerWebAction extends AdministrationManagerAction 
{
	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	public ValueAddedTaxesManagerWebAction() {
		super(WebAdministractionBeanFactory.getInstance().getLogger(ValueAddedTaxesManagerWebAction.class.getName()), new ValueAddedTaxesManagerForm());
		administrationManager = WebAdministractionBeanFactory.getInstance().getValueAddedTaxesManager();
		// Load the ValueAddedTaxesResources
		LocalizedTextUtil.addDefaultResourceBundle("fr/mch/mdo/restaurant/resources/i18n/ValueAddedTaxesResources");
	}
}
