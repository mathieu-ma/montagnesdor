/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dao.beans;

import fr.mch.mdo.restaurant.beans.MdoDaoBean;

/**
 * This class is a t_table_type mapping. This table is used for table type.
 * 
 * @author Mathieu
 */
public class TableType extends MdoDaoBean 
{
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This is the code of the table type. This is a unique field. It is a
	 * foreign that refers to the t_enum table for type TABLE_TYPE.
	 */
	private MdoTableAsEnum code;

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(MdoTableAsEnum code) {
		this.code = code;
	}

	/**
	 * @return the code
	 */
	public MdoTableAsEnum getCode() {
		return code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		TableType other = (TableType) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TableType [code=" + code + ", deleted=" + deleted + ", id=" + id + "]";
	}
}
