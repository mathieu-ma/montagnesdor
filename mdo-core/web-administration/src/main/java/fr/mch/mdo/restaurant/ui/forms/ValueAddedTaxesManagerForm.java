package fr.mch.mdo.restaurant.ui.forms;

import fr.mch.mdo.restaurant.dto.beans.ValueAddedTaxDto;
import fr.mch.mdo.restaurant.dto.beans.ValueAddedTaxesManagerViewBean;

public class ValueAddedTaxesManagerForm extends MdoAdministrationForm 
{
	public ValueAddedTaxesManagerForm() {
		super(new ValueAddedTaxDto());
		super.setViewBean(new ValueAddedTaxesManagerViewBean());
	}
}
