package fr.mch.mdo.restaurant.web.struts.actions;

import fr.mch.mdo.restaurant.ioc.spring.WebAdministractionBeanFactory;
import fr.mch.mdo.restaurant.ui.forms.ProductSpecialCodesManagerForm;

public class ProductSpecialCodesManagerWebAction extends AdministrationManagerLabelsAction 
{
	/**
	 * Default Serial Version UID
     */
	private static final long serialVersionUID = 1L;

	public ProductSpecialCodesManagerWebAction() {
		super(WebAdministractionBeanFactory.getInstance().getLogger(ProductSpecialCodesManagerWebAction.class.getName()), new ProductSpecialCodesManagerForm());
		administrationManager = WebAdministractionBeanFactory.getInstance().getProductSpecialCodesManager();
	}
}
