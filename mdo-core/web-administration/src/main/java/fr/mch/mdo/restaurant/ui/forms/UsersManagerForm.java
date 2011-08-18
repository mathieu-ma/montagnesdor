package fr.mch.mdo.restaurant.ui.forms;

import fr.mch.mdo.restaurant.dto.beans.UserDto;
import fr.mch.mdo.restaurant.dto.beans.UsersManagerViewBean;

public class UsersManagerForm extends MdoAdministrationForm 
{
	public UsersManagerForm() {
		super(new UserDto());
		super.setViewBean(new UsersManagerViewBean());
	}
}
