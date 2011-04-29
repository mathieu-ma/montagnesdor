/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dto.beans;

import java.math.BigDecimal;

import fr.mch.mdo.restaurant.beans.MdoDtoBean;

/**
 * This class is used for t_product_category mapping. This table is used for
 * product depending on the specific categories.
 * 
 * @author Mathieu MA sous conrad
 */
public class ProductCategoryDto extends MdoDtoBean {
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This is a foreign key that refers to t_product. It is used to specify the
	 * product. This field and the other cat_id field consist of a unique field.
	 */
	private ProductDto product;
	/**
	 * This is a foreign key that refers to t_category. It is used to specify
	 * the category of the product. This field and the other pdt_id field
	 * consist of a unique field.
	 */
	private CategoryDto category;
	/**
	 * This is the quantity of the category for the product. For example, if the
	 * category is FISH then the quantity is the quantity of fish for this
	 * product. This can be used for stock management.
	 */
	private BigDecimal quantity = new BigDecimal(0);

	public ProductDto getProduct() {
		return product;
	}

	public void setProduct(ProductDto product) {
		this.product = product;
	}

	public CategoryDto getCategory() {
		return category;
	}

	public void setCategory(CategoryDto category) {
		this.category = category;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1; // DO NOT call super.hashCode(); because ID could be null.
		result = prime * result + ((category == null || category.getId() == null) ? 0 : category.getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		// DO NOT call super.hashCode(); because ID could be null.
		// if (!super.equals(obj)) {
		// return false;
		// }
		if (getClass() != obj.getClass())
			return false;
		ProductCategoryDto other = (ProductCategoryDto) obj;
		if (category == null) {
			if (other.category != null) {
				return false;
			}
		} else if (category.getId() == null) {
			if (other.category.getId() != null) {
				return false;
			}
		} else if (other.category == null || !category.getId().equals(other.category.getId())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ProductCategory [category=" + category + ", quantity=" + quantity + ", id=" + id + "]";
	}

}
