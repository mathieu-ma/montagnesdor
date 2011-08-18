package fr.mch.mdo.restaurant.ui.forms;

import fr.mch.mdo.restaurant.dto.beans.LocaleDto;
import fr.mch.mdo.restaurant.dto.beans.LocalesManagerViewBean;

public class LocalesManagerForm extends MdoAdministrationForm 
{
	public LocalesManagerForm() {
		super(new LocaleDto());
		super.setViewBean(new LocalesManagerViewBean());
	}
}
