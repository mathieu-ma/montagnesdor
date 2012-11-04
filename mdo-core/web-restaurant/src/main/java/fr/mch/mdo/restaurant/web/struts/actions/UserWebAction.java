package fr.mch.mdo.restaurant.web.struts.actions;

import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.services.WebRestaurantBeanFactory;
import fr.mch.mdo.restaurant.ui.forms.UserForm;

public final class UserWebAction extends RestaurantWebAction
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    public UserWebAction()
    {
		super(WebRestaurantBeanFactory.getInstance().getLogger(UserWebAction.class.getName()), new UserForm());
    }

	public String form() throws Exception {
		return Constants.ACTION_RESULT_FORM;
	}
}
