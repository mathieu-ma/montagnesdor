package fr.mch.mdo.restaurant.web.struts.actions;

import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.ProductDto;
import fr.mch.mdo.restaurant.ioc.spring.WebAdministractionBeanFactory;
import fr.mch.mdo.restaurant.ui.forms.ProductsManagerForm;

public class ProductsManagerWebAction extends AdministrationManagerAction 
{
	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	public ProductsManagerWebAction() {
		super(WebAdministractionBeanFactory.getInstance().getLogger(ProductsManagerWebAction.class.getName()), new ProductsManagerForm());
		administrationManager = WebAdministractionBeanFactory.getInstance().getProductsManager();
	}

	public String form() throws Exception {
		String result = Constants.ACTION_RESULT_LIST;

		ProductDto dtoBean = ((ProductDto) super.getForm().getDtoBean());
		if (dtoBean != null) {
			MdoUserContext userContext = (MdoUserContext) super.getForm().getUserContext();
			if (userContext != null) {
				super.getForm().setDtoBean(super.getAdministrationManager().findByPrimaryKey(dtoBean.getId(), userContext));
				IAdministrationManagerViewBean viewBean = super.getForm().getViewBean();
				if (viewBean != null) {
					super.getAdministrationManager().processList(viewBean, userContext);
				}
			}
		}

		return result;
	}

	@Override
	public String list() throws Exception {
		ProductDto dtoBean = ((ProductDto) super.getForm().getDtoBean());
		if (dtoBean != null) {
			MdoUserContext userContext = (MdoUserContext) super.getForm().getUserContext();
			if (userContext != null) {
				IAdministrationManagerViewBean viewBean = super.getForm().getViewBean();
				if (viewBean != null) {
					super.getAdministrationManager().processList(viewBean, userContext);
				}
			}
		}
		return super.list();
	}

	public String labels() throws Exception {
		super.save();
		return this.form();
	}
}
