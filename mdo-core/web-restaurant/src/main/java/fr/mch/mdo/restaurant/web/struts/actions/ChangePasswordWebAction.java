package fr.mch.mdo.restaurant.web.struts.actions;

import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.authentication.IMdoAuthenticationService;
import fr.mch.mdo.restaurant.business.managers.user.IUserManager;
import fr.mch.mdo.restaurant.dto.beans.ChangePasswordDtoBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.ioc.MdoBeanFactory;
import fr.mch.mdo.restaurant.ui.forms.ChangePasswordForm;

public final class ChangePasswordWebAction extends MdoAbstractWebAction
{
    /**
     * 
     */
    private static final long serialVersionUID = -2361155708074907086L;

    public ChangePasswordWebAction()
    {
	super(MdoBeanFactory.getInstance().getLogger(ChangePasswordWebAction.class.getName()), new ChangePasswordForm());
    }

    public String form() throws Exception
    {
	String result = Constants.ACTION_FORM;
	
	return result;
    }

    public String authenticate() throws Exception
    {
	String forwardPage = "ajax-response";
	
	ChangePasswordForm form = (ChangePasswordForm) super.getForm();
	ChangePasswordDtoBean dto = (ChangePasswordDtoBean) form.getDtoBean();

	if(dto.getPassword()==null)
	{
	    addActionError(getText("error.current.password.level.required"));
	}
	if(form.getNewPasswordConfirmed()==null)
	{
	    addActionError(getText("error.new.confirmed.password.level.required"));
	}
	if(dto.getNewPassword()==null)
	{
	    addActionError(getText("error.new.password.level.required"));
	}
	if(form.getNewPasswordConfirmed()!=null && dto.getNewPassword()!=null && !form.getNewPasswordConfirmed().equals(dto.getNewPassword()))
	{
	    addActionError(getText("error.new.confirmed.password.level.different"));
	}
	
	// Check login and password are not null
	MdoUserContext userContext = (MdoUserContext)dto.getUserContext();
	super.getLogger().debug("Login : " + userContext.getLogin() + ", Password : " + dto.getPassword() + ", Level Password : " + dto.getLevelPassword());

	try
	{
	    IMdoAuthenticationService authenticationService = MdoBeanFactory.getInstance().getMdoAuthenticationService();
	    authenticationService.authenticate(userContext.getLogin(), dto.getPassword(), dto.getLevelPassword());
	    IUserManager userManager = MdoBeanFactory.getInstance().getUserManager();
	    userManager.savePassword(dto.getLevelPassword(), dto.getNewPassword(), userContext);
	}
	catch (Exception e)
	{
	    addActionError(getText("error.password.level.failed"));
	}

	// Forward control to the specified success URI
	return forwardPage;
    }
}
