package fr.mch.mdo.restaurant.web.struts.actions;

import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.UserAuthenticationDto;
import fr.mch.mdo.restaurant.ioc.spring.WebAdministractionBeanFactory;
import fr.mch.mdo.restaurant.ui.forms.UserAuthenticationsManagerForm;

public class UserAuthenticationsManagerAction extends AdministrationManagerAction 
{
	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	public UserAuthenticationsManagerAction() {
		super(WebAdministractionBeanFactory.getInstance().getLogger(UserAuthenticationsManagerAction.class.getName()), new UserAuthenticationsManagerForm());
		administrationManager = WebAdministractionBeanFactory.getInstance().getUserAuthenticationsManager();
	}

	@Override
	public String form() throws Exception {
		String result = Constants.ACTION_RESULT_LIST;

		UserAuthenticationDto dtoBean = ((UserAuthenticationDto) super.getForm().getDtoBean());
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

	public String findRestaurantsByUser() throws Exception {
		String forwardPage = "ajax-response";

		UserAuthenticationDto dtoBean = ((UserAuthenticationDto) super.getForm().getDtoBean());
		if (dtoBean != null) {
			try {
				MdoUserContext userContext = (MdoUserContext) super.getForm().getUserContext();
				if (userContext != null) {
					IAdministrationManagerViewBean viewBean = super.getForm().getViewBean();
					if (viewBean != null) {
						super.getAdministrationManager().processList(viewBean, userContext);
					}
				}
			} catch (Exception e) {
				addActionError(getText("error.user.authentication.restaurants"));
			}
		}
		return forwardPage;
	}
}
