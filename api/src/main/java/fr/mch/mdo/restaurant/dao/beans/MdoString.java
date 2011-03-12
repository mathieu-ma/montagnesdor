package fr.mch.mdo.restaurant.dao.beans;

import fr.mch.mdo.restaurant.beans.IMdoBean;


/**
 * This class is a t_enum mapping. This table is used for enumeration type for
 * other tables.
 * 
 * @author Mathieu
 */
public class MdoString implements IMdoBean 
{
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This is the type of the enum. This field consists with the enm_name an
	 * unique key.
	 */
	private String value;
	
	public MdoString() {
	}

	public MdoString(String value) {
		this.setValue(value);
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		MdoString other = (MdoString) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MdoString [value=" + value + "]";
	}
	
}
