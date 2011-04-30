package fr.mch.mdo.restaurant.ui.forms;

import fr.mch.mdo.restaurant.dto.beans.ProductSpecialCodeDto;
import fr.mch.mdo.restaurant.dto.beans.ProductSpecialCodesManagerViewBean;

public class ProductSpecialCodesManagerForm extends MdoLabelsForm 
{
	public ProductSpecialCodesManagerForm() {
		super(new ProductSpecialCodeDto());
		super.setViewBean(new ProductSpecialCodesManagerViewBean());
	}
}
