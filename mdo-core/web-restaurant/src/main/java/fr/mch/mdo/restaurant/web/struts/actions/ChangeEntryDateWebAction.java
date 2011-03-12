package fr.mch.mdo.restaurant.web.struts.actions;

import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.authentication.IMdoAuthenticationService;
import fr.mch.mdo.restaurant.dto.beans.ChangeEntryDateDtoBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.ioc.MdoBeanFactory;
import fr.mch.mdo.restaurant.ui.forms.ChangeEntryDateForm;

public final class ChangeEntryDateWebAction extends MdoAbstractWebAction
{
    /**
     * 
     */
    private static final long serialVersionUID = -2361155708074907086L;

    public ChangeEntryDateWebAction()
    {
	super(MdoBeanFactory.getInstance().getLogger(ChangeEntryDateWebAction.class.getName()), new ChangeEntryDateForm());
    }

    public String form() throws Exception
    {
	String result = Constants.ACTION_FORM;
	
	return result;
    }

    public String authenticate() throws Exception
    {
	String forwardPage = "ajax-response";

	ChangeEntryDateDtoBean dto = (ChangeEntryDateDtoBean) super.getForm().getDtoBean();
	if(dto.getPassword()==null)
	{
	    addActionError(getText("error.password.level.1.required"));
	}

	MdoUserContext userContext = (MdoUserContext)dto.getUserContext();
	// Check login and password are not null
	super.getLogger().debug("Login : " + userContext.getLogin() + ", Password : " + dto.getPassword() + ", Level Password : " + dto.getLevelPassword());

	try
	{
	    IMdoAuthenticationService iMdoAuthenticationService = MdoBeanFactory.getInstance().getMdoAuthenticationService();
	    iMdoAuthenticationService.authenticate(userContext.getLogin(), dto.getPassword(), dto.getLevelPassword());
	}
	catch (Exception e)
	{
	    addActionError(getText("error.password.level.1.failed"));
	}

	// Forward control to the specified success URI
	return forwardPage;
    }
}
