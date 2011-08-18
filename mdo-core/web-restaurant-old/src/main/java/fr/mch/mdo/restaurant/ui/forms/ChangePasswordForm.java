package fr.mch.mdo.restaurant.ui.forms;

import fr.mch.mdo.restaurant.dto.beans.ChangePasswordDtoBean;

public class ChangePasswordForm extends MdoForm
{
    private String newPasswordConfirmed;
    
    public ChangePasswordForm()
    {
	super(new ChangePasswordDtoBean());
    }

    public String getNewPasswordConfirmed()
    {
        return newPasswordConfirmed;
    }

    public void setNewPasswordConfirmed(String newPasswordConfirmed)
    {
        this.newPasswordConfirmed = newPasswordConfirmed;
    }
}
