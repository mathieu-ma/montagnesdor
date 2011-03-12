package fr.mch.mdo.restaurant.ui.forms;

import org.apache.commons.beanutils.BeanUtils;

import fr.mch.mdo.restaurant.dto.beans.LogonDto;

public class LogonForm extends MdoForm
{
    public LogonForm()
    {
	super(new LogonDto());
    }
}
