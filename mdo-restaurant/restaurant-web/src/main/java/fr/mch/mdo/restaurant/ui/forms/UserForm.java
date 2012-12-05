package fr.mch.mdo.restaurant.ui.forms;

import fr.mch.mdo.restaurant.beans.dto.UserAuthenticationDto;

public class UserForm extends MdoRestaurantForm
{
	public UserForm() {
		super(new UserAuthenticationDto());
	}
}
