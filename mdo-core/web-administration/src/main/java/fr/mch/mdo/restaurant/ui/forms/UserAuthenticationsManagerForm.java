package fr.mch.mdo.restaurant.ui.forms;

import fr.mch.mdo.restaurant.dto.beans.UserAuthenticationDto;
import fr.mch.mdo.restaurant.dto.beans.UserAuthenticationsManagerViewBean;

public class UserAuthenticationsManagerForm extends MdoForm 
{
	public UserAuthenticationsManagerForm() {
		super(new UserAuthenticationDto());
		super.setViewBean(new UserAuthenticationsManagerViewBean());
	}
}
