package fr.mch.mdo.restaurant.web.struts.actions;

import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.ioc.MdoBeanFactory;
import fr.mch.mdo.restaurant.ui.forms.UserForm;

public final class UserWebAction extends MdoAbstractWebAction
{
    /**
     * 
     */
    private static final long serialVersionUID = -2361155708074907086L;

    public UserWebAction()
    {
	super(MdoBeanFactory.getInstance().getLogger(UserWebAction.class.getName()), new UserForm());
    }

    public String form() throws Exception
    {
	String result = Constants.ACTION_FORM;
	
	return result;
    }
}
