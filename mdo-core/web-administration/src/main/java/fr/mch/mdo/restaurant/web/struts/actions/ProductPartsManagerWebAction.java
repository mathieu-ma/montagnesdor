package fr.mch.mdo.restaurant.web.struts.actions;

import fr.mch.mdo.restaurant.ioc.spring.WebAdministrationBeanFactory;
import fr.mch.mdo.restaurant.ui.forms.ProductPartsManagerForm;

public class ProductPartsManagerWebAction extends AdministrationManagerLabelsAction 
{
	/**
	 * Default Serial Version UID
     */
	private static final long serialVersionUID = 1L;

	public ProductPartsManagerWebAction() {
		super(WebAdministrationBeanFactory.getInstance().getLogger(ProductPartsManagerWebAction.class.getName()), new ProductPartsManagerForm());
		administrationManager = WebAdministrationBeanFactory.getInstance().getProductPartsManager();
	}
}
