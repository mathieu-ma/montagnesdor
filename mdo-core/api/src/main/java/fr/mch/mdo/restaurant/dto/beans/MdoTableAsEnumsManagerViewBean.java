/*
 * Created on 29 avr. 2004
 *
 */
package fr.mch.mdo.restaurant.dto.beans;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Mathieu MA
 * 
 */
public class MdoTableAsEnumsManagerViewBean extends AdministrationManagerViewBean 
{
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * List of ids that must not be deleted. 
	 */
	private List<Long> notDeletedBeanIds = new ArrayList<Long>();
	
	/**
	 * List of already stored type names
	 */
	private List<String> existingTypes = new ArrayList<String>();

	/**
	 * @param notDeletedBeanIds the notDeletedBeanIds to set
	 */
	public void setNotDeletedBeanIds(List<Long> notDeletedBeanIds) {
		this.notDeletedBeanIds = notDeletedBeanIds;
	}

	/**
	 * @return the notDeletedBeanIds
	 */
	public List<Long> getNotDeletedBeanIds() {
		return notDeletedBeanIds;
	}

	/**
	 * @param existingTypes the existingTypes to set
	 */
	public void setExistingTypes(List<String> existingTypes) {
		this.existingTypes = existingTypes;
	}

	/**
	 * @return the existingTypes
	 */
	public List<String> getExistingTypes() {
		return existingTypes;
	}
}
