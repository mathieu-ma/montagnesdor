package fr.mch.mdo.restaurant.ui.forms;

import fr.mch.mdo.restaurant.dto.beans.ProductDto;

public class ProductsManagerForm extends MdoForm 
{
	private String selectedTab = "0";

	public ProductsManagerForm() {
		super(new ProductDto());
	}

	public String getSelectedTab() {
		return selectedTab;
	}

	public void setSelectedTab(String selectedTab) {
		this.selectedTab = selectedTab;
	}
}
