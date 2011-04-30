package fr.mch.mdo.restaurant.ui.forms;

import fr.mch.mdo.restaurant.dto.beans.UserRoleDto;
import fr.mch.mdo.restaurant.dto.beans.UserRolesManagerViewBean;

public class UserRolesManagerForm extends MdoLabelsForm 
{
	public UserRolesManagerForm() {
		super(new UserRoleDto());
		super.setViewBean(new UserRolesManagerViewBean());
	}
}
