package fr.mch.mdo.restaurant.web.struts.actions;

import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.UserRoleDto;
import fr.mch.mdo.restaurant.exception.MdoBusinessException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.ioc.spring.WebAdministrationBeanFactory;
import fr.mch.mdo.restaurant.ui.forms.UserRolesManagerForm;

public class UserRolesManagerWebAction extends AdministrationManagerLabelsAction 
{
	/**
	 * Default Serial Version UID
     */
	private static final long serialVersionUID = 1L;

	public UserRolesManagerWebAction() {
		super(WebAdministrationBeanFactory.getInstance().getLogger(UserRolesManagerWebAction.class.getName()), new UserRolesManagerForm());
		administrationManager = WebAdministrationBeanFactory.getInstance().getUserRolesManager();
	}
	
	@Override
	public String save() throws MdoBusinessException {
		String result = super.save();
		
		MdoUserContext userContext = (MdoUserContext) super.getForm().getUserContext();
		UserRoleDto savedUserRole = (UserRoleDto) super.getForm().getDtoBean();
		if (savedUserRole != null) {
			if (userContext != null && userContext.getUserRole() != null) {
				Long savedId = savedUserRole.getId();
				Long sessionId = (Long) userContext.getUserRole().getId();
				if (savedId != null && savedId.equals(sessionId) && userContext.getUserAuthentication() != null) {
					try {
						userContext.getUserAuthentication().setUserRole((UserRoleDto) super.getAdministrationManager().findByPrimaryKey(savedId));
					} catch (MdoException e) {
						super.getLogger().error("message.error.administration.business.user.roles.not.found", new Object[] {savedUserRole}, e);
						throw new MdoBusinessException("message.error.administration.business.user.roles.not.found", new Object[] {savedUserRole}, e);
					}
				}
			}
		}

		
		return result;
	}

}
