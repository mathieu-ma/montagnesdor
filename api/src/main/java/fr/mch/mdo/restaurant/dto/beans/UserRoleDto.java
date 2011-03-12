/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dto.beans;

import java.util.Map;

import fr.mch.mdo.restaurant.beans.MdoDtoBean;

/**
 * This class is a DTO for user role.
 * 
 * @author Mathieu MA sous conrad
 */
public class UserRoleDto extends MdoDtoBean 
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * This is a foreign key that refers to t_enum.
     * It is used to specify the user role code like GLOBAL_ADMINISTRATOR, ADMINISTRATOR ...
     */
    private MdoTableAsEnumDto code;

    /**
     * This is used for i18n label.
     */
    private Map<Long, String> labels;

    /**
     * @return the code
     */
    public MdoTableAsEnumDto getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(MdoTableAsEnumDto code) {
        this.code = code;
    }

    /**
     * @return the labels
     */
    public Map<Long, String> getLabels() {
        return labels;
    }

    /**
     * @param labels the labels to set
     */
    public void setLabels(Map<Long, String> labels) {
        this.labels = labels;
    }

    @Override
    public String toString() {
	return "UserRoleDto [code=" + code + ", id=" + id + "]";
    }
}
