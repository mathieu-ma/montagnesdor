package fr.mch.mdo.restaurant.web.struts.actions;

import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.dto.beans.CategoryDto;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.ioc.spring.WebAdministractionBeanFactory;
import fr.mch.mdo.restaurant.ui.forms.CategoriesManagerForm;

public class CategoriesManagerWebAction extends AdministrationManagerAction 
{
	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	public CategoriesManagerWebAction() {
		super(WebAdministractionBeanFactory.getInstance().getLogger(CategoriesManagerWebAction.class.getName()), new CategoriesManagerForm());
		administrationManager = WebAdministractionBeanFactory.getInstance().getCategoriesManager();
	}

	@Override
	public String form() throws Exception {
		String result = Constants.ACTION_RESULT_LIST;

		CategoryDto dtoBean = ((CategoryDto) super.getForm().getDtoBean());
		if (dtoBean != null) {
			MdoUserContext userContext = (MdoUserContext) super.getForm().getUserContext();
			if (userContext != null) {
				IAdministrationManagerViewBean viewBean = super.getForm().getViewBean();
				if (viewBean != null) {
					super.getAdministrationManager().processList(viewBean, userContext);
				}
				super.getForm().setDtoBean(super.getAdministrationManager().findByPrimaryKey(dtoBean.getId(), userContext));
			}
		}
		return result;
	}

	public String labels() throws Exception {
		super.save();
		return this.form();
	}
}
