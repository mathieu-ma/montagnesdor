/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dto.beans;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fr.mch.mdo.restaurant.beans.IBeanLabelable;
import fr.mch.mdo.restaurant.beans.MdoDtoBean;

/**
 * This class is used for t_product mapping. This table is used for product.
 * 
 * @author Mathieu MA sous conrad
 */
public class ProductDto extends MdoDtoBean implements IBeanLabelable
{
	/**
	 * Default Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This is a foreign key that refers to t_restaurant. It is used to specify
	 * the restaurant.
	 */
	private RestaurantDto restaurant;
	/**
	 * This is product code.
	 */
	private String code;
	/**
	 * This is product price.
	 */
	private BigDecimal price;
	/**
	 * This is the highlight color product line see table t_order_line. The
	 * value is formatted as css color like xxyyzz.
	 */
	private String colorRGB;
	/**
	 * This is a foreign key that refers to t_value_added_tax. It is used to
	 * specify the product value added tax.
	 */
	private ValueAddedTaxDto vat;

	/**
	 * This is used for i18n label.
	 */
	private Map<Long, String> labels;

	/**
	 * This is used to detail product categories.
	 */
	private Set<ProductCategoryDto> categories = new HashSet<ProductCategoryDto>();


	public RestaurantDto getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(RestaurantDto restaurant) {
		this.restaurant = restaurant;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getColorRGB() {
		return colorRGB;
	}

	public void setColorRGB(String colorRGB) {
		this.colorRGB = colorRGB;
	}

	public ValueAddedTaxDto getVat() {
		return vat;
	}

	public void setVat(ValueAddedTaxDto vat) {
		this.vat = vat;
	}

	public Map<Long, String> getLabels() {
		return labels;
	}

	public void setLabels(Map<Long, String> labels) {
		this.labels = labels;
	}

	public Set<ProductCategoryDto> getCategories() {
		return categories;
	}

	public void setCategories(Set<ProductCategoryDto> categories) {
		this.categories = categories;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((restaurant == null) ? 0 : restaurant.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductDto other = (ProductDto) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (restaurant == null) {
			if (other.restaurant != null)
				return false;
		} else if (!restaurant.equals(other.restaurant))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [categories=" + categories + ", code=" + code + ", colorRGB=" + colorRGB + ", labels=" + labels + ", price=" + price + ", restaurant=" + restaurant
				+ ", vat=" + vat + ", id=" + id + "]";
	}

}
