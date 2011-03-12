/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dao.beans;

import java.util.Map;

import fr.mch.mdo.restaurant.beans.MdoDaoBean;

/**
 * This class is used for t_user_role mapping. 
 * This table is used for user role.
 * 
 * @author Mathieu MA sous conrad
 */
public class UserRole extends MdoDaoBean 
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * This is a foreign key that refers to t_enum.
     * It is used to specify the user role code like GLOBAL_ADMINISTRATOR, ADMINISTRATOR ...
     */
    private MdoTableAsEnum code;

    /**
     * This is used for i18n label.
     */
    private Map<Long, String> labels;

    /**
     * @param code the code to set
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

    public Map<Long, String> getLabels() {
	return labels;
    }

    public void setLabels(Map<Long, String> labels) {
	this.labels = labels;
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
	UserRole other = (UserRole) obj;
	if (code == null) {
	    if (other.code != null)
		return false;
	} else if (!code.equals(other.code))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "UserRole [code=" + code + ", labels=" + labels + ", deleted="
		+ deleted + ", id=" + id + "]";
    }
}
