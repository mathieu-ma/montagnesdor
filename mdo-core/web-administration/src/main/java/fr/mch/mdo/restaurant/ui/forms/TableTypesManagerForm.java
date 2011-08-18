package fr.mch.mdo.restaurant.ui.forms;

import fr.mch.mdo.restaurant.dto.beans.TableTypeDto;
import fr.mch.mdo.restaurant.dto.beans.TableTypesManagerViewBean;

public class TableTypesManagerForm extends MdoAdministrationForm 
{
	public TableTypesManagerForm() {
		super(new TableTypeDto());
		super.setViewBean(new TableTypesManagerViewBean());
	}
}