/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dto.beans;

import fr.mch.mdo.restaurant.beans.MdoDtoBean;

/**
 * This class is a DTO for table type.
 * 
 * @author Mathieu
 */
public class TableTypeDto extends MdoDtoBean 
{
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This is the code of the table type. This is a unique field. It is a
	 * foreign that refers to the t_enum table for type TABLE_TYPE.
	 */
	private MdoTableAsEnumDto code;

	/**
	 * @return the code
	 */
	public MdoTableAsEnumDto getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(MdoTableAsEnumDto code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "TableTypeDto [code=" + code + ", id=" + id + "]";
	}
}
