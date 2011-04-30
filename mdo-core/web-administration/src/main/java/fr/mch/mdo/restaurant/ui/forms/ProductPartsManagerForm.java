package fr.mch.mdo.restaurant.ui.forms;

import fr.mch.mdo.restaurant.dto.beans.ProductPartDto;
import fr.mch.mdo.restaurant.dto.beans.ProductPartsManagerViewBean;

public class ProductPartsManagerForm extends MdoLabelsForm 
{
	public ProductPartsManagerForm() {
		super(new ProductPartDto());
		super.setViewBean(new ProductPartsManagerViewBean());
	}
}
