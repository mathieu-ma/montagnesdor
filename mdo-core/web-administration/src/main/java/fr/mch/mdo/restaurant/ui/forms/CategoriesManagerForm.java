package fr.mch.mdo.restaurant.ui.forms;

import fr.mch.mdo.restaurant.dto.beans.CategoriesManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.CategoryDto;


public class CategoriesManagerForm extends MdoLabelsForm 
{
	public CategoriesManagerForm() {
		super(new CategoryDto());
		super.setViewBean(new CategoriesManagerViewBean());
	}
}