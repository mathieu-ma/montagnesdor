package fr.mch.mdo.restaurant.web.struts.actions;

import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.ProductPartDto;
import fr.mch.mdo.restaurant.ioc.spring.WebAdministractionBeanFactory;
import fr.mch.mdo.restaurant.ui.forms.ProductPartsManagerForm;

public class ProductPartsManagerWebAction extends AdministrationManagerAction 
{
	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	public ProductPartsManagerWebAction() {
		super(WebAdministractionBeanFactory.getInstance().getLogger(ProductPartsManagerWebAction.class.getName()), new ProductPartsManagerForm());
		administrationManager = WebAdministractionBeanFactory.getInstance().getProductPartsManager();
	}

	@Override
	public String form() throws Exception {
		String result = Constants.ACTION_RESULT_LIST;

		ProductPartDto dtoBean = ((ProductPartDto) super.getForm().getDtoBean());
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

	public String labels() throws Exception {
		super.save();
		return this.form();
	}
}
