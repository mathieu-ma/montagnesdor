/*
 * Created on 30 mai 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dao.beans;


/**
 * This class is NOT a t_product_special_code mapping.
 * It is used to retrieve ProductSpecialCode with current label and current locale.
 * 
 * @author Mathieu
 */
public class ProductSpecialCodeLabel extends ProductSpecialCode
{
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	private String label;
	
	private Locale locale;

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the locale
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * @param locale the locale to set
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	@Override
	public String toString() {
		return "ProductSpecialCodeLabel [label=" + label + ", locale=" + locale
				+ ", super.toString()=" + super.toString() + "]";
	}
}
