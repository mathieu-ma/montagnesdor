package fr.mch.mdo.restaurant.ui.forms;

import fr.mch.mdo.restaurant.dto.beans.ProductDto;
import fr.mch.mdo.restaurant.dto.beans.ProductsManagerViewBean;

public class ProductsManagerForm extends MdoLabelsForm 
{
	public ProductsManagerForm() {
		super(new ProductDto());
		super.setViewBean(new ProductsManagerViewBean());
	}
}
