package fr.mch.mdo.restaurant.ui.forms;

import fr.mch.mdo.restaurant.dto.beans.ChangePasswordDto;

public class ChangePasswordForm extends MdoRestaurantForm
{
	private String newPasswordConfirmed;

	public ChangePasswordForm() {
		super(new ChangePasswordDto());
	}

	public String getNewPasswordConfirmed() {
		return newPasswordConfirmed;
	}

	public void setNewPasswordConfirmed(String newPasswordConfirmed) {
		this.newPasswordConfirmed = newPasswordConfirmed;
	}
}
