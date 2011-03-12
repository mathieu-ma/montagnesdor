/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dao.beans;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fr.mch.mdo.restaurant.beans.MdoDaoBean;

/**
 * This class is used for t_product mapping. This table is used for product.
 * 
 * @author Mathieu MA sous conrad
 */
public class Product extends MdoDaoBean 
{
	/**
	 * Default Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This is a foreign key that refers to t_restaurant. It is used to specify
	 * the restaurant.
	 */
	private Restaurant restaurant;
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
	private ValueAddedTax vat;

	/**
	 * This is used for i18n label.
	 */
	private Map<Long, String> labels;

	/**
	 * This is used to detail product categories.
	 */
	private Set<ProductCategory> categories;

	/**
	 * @return the restaurant
	 */
	public Restaurant getRestaurant() {
		return restaurant;
	}

	/**
	 * @param restaurant
	 *            the restaurant to set
	 */
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * @return the colorRGB
	 */
	public String getColorRGB() {
		return colorRGB;
	}

	/**
	 * @param colorRGB
	 *            the colorRGB to set
	 */
	public void setColorRGB(String colorRGB) {
		this.colorRGB = colorRGB;
	}

	/**
	 * @return the vat
	 */
	public ValueAddedTax getVat() {
		return vat;
	}

	/**
	 * @param vat
	 *            the vat to set
	 */
	public void setVat(ValueAddedTax vat) {
		this.vat = vat;
	}

	/**
	 * @return the labels
	 */
	public Map<Long, String> getLabels() {
		return labels;
	}

	/**
	 * @param labels
	 *            the labels to set
	 */
	public void setLabels(Map<Long, String> labels) {
		this.labels = labels;
	}

	/**
	 * @return the categories
	 */
	public Set<ProductCategory> getCategories() {
		return categories;
	}

	/**
	 * @param categories
	 *            the categories to set
	 */
	public void setCategories(Set<ProductCategory> categories) {
		this.categories = categories;
	}

	/**
	 * Add productCategory to categories
	 * 
	 * @param productCategory
	 *            the productCategory
	 */
	public void addCategory(ProductCategory productCategory) {
		if (categories == null) {
			categories = new HashSet<ProductCategory>();
		}
		if (productCategory != null) {
			productCategory.setProduct(this);
		}
		categories.add(productCategory);
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
		Product other = (Product) obj;
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
				+ ", vat=" + vat + ", deleted=" + deleted + ", id=" + id + "]";
	}

}
