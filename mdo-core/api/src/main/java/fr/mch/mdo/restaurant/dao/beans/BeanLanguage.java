/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dao.beans;

import fr.mch.mdo.restaurant.beans.MdoDaoBean;

/**
 * This class is used for parent specific language.
 * 
 * @author Mathieu MA sous conrad
 */
public class BeanLanguage extends MdoDaoBean
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

	/**
     * This is a foreign key that refers to parent table. 
     * This field and the other loc_id field consist of a unique field.
     */
    private Long parentId;

    /**
     * This is a foreign key that refers to t_locale. 
     * It is used to specify the language of the parent table. 
     * This field and the other parent id field consist of a unique field.
     */
    private Long locId;

    /**
     * This is the label of the product part depending on the language.
     */
    private String label;

	/**
	 * @return the parentId
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the locId
	 */
	public Long getLocId() {
		return locId;
	}

	/**
	 * @param locId the locId to set
	 */
	public void setLocId(Long locId) {
		this.locId = locId;
	}

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

	@Override
	public String toString() {
		return "BeanLanguage [parentId=" + parentId + ", locId="
				+ locId + ", label=" + label + "]";
	}
}
