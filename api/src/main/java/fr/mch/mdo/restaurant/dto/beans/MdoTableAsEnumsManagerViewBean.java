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
}
