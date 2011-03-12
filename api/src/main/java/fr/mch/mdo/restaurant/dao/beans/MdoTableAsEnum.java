package fr.mch.mdo.restaurant.dao.beans;

import fr.mch.mdo.restaurant.beans.MdoDaoBean;

/**
 * This class is a t_enum mapping. This table is used for enumeration type for
 * other tables.
 * 
 * @author Mathieu
 */
public class MdoTableAsEnum extends MdoDaoBean {
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This is the type of the enum. This field consists with the enm_name an
	 * unique key.
	 */
	//private MdoString type;
	private String type;
	/**
	 * This is the name of the enum. This field consists with the enm_type an
	 * unique key.
	 */
	private String name;
	/**
	 * This is the order of the enum for a specific enum type.
	 */
	private int order;
	/**
	 * This field is a default label to display to user.
	 */
	private String languageKeyLabel;
	/**
	 * This field is used for java i18n. We can map this field with java
	 * properties files as a properties key in order to find the label value.
	 */
	private String defaultLabel;

	/**
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the languageKeyLabel
	 */
	public String getLanguageKeyLabel() {
		return languageKeyLabel;
	}

	/**
	 * @param languageKeyLabel
	 *            the languageKeyLabel to set
	 */
	public void setLanguageKeyLabel(String languageKeyLabel) {
		this.languageKeyLabel = languageKeyLabel;
	}

	/**
	 * @return the defaultLabel
	 */
	public String getDefaultLabel() {
		return defaultLabel;
	}

	/**
	 * @param defaultLabel
	 *            the defaultLabel to set
	 */
	public void setDefaultLabel(String defaultLabel) {
		this.defaultLabel = defaultLabel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		MdoTableAsEnum other = (MdoTableAsEnum) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MdoTableAsEnum [defaultLabel=" + defaultLabel + ", languageKeyLabel=" + languageKeyLabel + ", name=" + name + ", order=" + order + ", type=" + type + ", deleted="
				+ deleted + ", id=" + id + "]";
	}
}
