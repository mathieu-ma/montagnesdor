package fr.mch.mdo.restaurant.ui.forms;

import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumDto;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumsManagerViewBean;

public class MdoTableAsEnumsManagerForm extends MdoAdministrationForm 
{
	// Type manually enter by user
	private String userEntryType;
	
	public MdoTableAsEnumsManagerForm() {
		super(new MdoTableAsEnumDto());
		super.setViewBean(new MdoTableAsEnumsManagerViewBean());
	}

	/**
	 * @param userEntryType the userEntryType to set
	 */
	public void setUserEntryType(String userEntryType) {
		this.userEntryType = userEntryType;
	}

	/**
	 * @return the userEntryType
	 */
	public String getUserEntryType() {
		return userEntryType;
	}

}
