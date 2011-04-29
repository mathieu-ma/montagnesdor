/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dao.beans;

import java.util.Map;

import fr.mch.mdo.restaurant.beans.IBeanLabelable;
import fr.mch.mdo.restaurant.beans.MdoDaoBean;

/**
 * This class is used for t_printing_information mapping.
 * This table is used for printing custom informations on specific restaurant.
 * 
 * @author Mathieu MA sous conrad
 */
public class PrintingInformation extends MdoDaoBean implements IBeanLabelable
{
    /**
     * Default Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * This is a foreign key that refers to t_restaurant. 
     * It is used to specify the restaurant. 
     */
    private Restaurant restaurant;

    /**
     * It is used to specify the order of the printing information. 
     */
    private int order;

    /**
     * This is a foreign key that refers to t_enum. 
     * It is used to specify the alignment of the printing information.
     */
    private MdoTableAsEnum alignment;

    /**
     * This is a foreign key that refers to t_enum. 
     * It is used to specify the size of the printing information.
     */
    private MdoTableAsEnum size;

    /**
     * This is a foreign key that refers to t_enum. 
     * It is used to specify the part of the printing information.
     */
    private MdoTableAsEnum part;

    /**
     * This is used for i18n label.
     */
    private Map<Long, String> labels;

    /**
     * @return the restaurant
     */
    public Restaurant getRestaurant() {
        return restaurant;
    }

    /**
     * @param restaurant the restaurant to set
     */
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    /**
     * @return the order
     */
    public int getOrder() {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * @return the alignment
     */
    public MdoTableAsEnum getAlignment() {
        return alignment;
    }

    /**
     * @param alignment the alignment to set
     */
    public void setAlignment(MdoTableAsEnum alignment) {
        this.alignment = alignment;
    }

    /**
     * @param size the size to set
     */
    public void setSize(MdoTableAsEnum size) {
	this.size = size;
    }

    /**
     * @return the size
     */
    public MdoTableAsEnum getSize() {
	return size;
    }

    /**
     * @param part the part to set
     */
    public void setPart(MdoTableAsEnum part) {
	this.part = part;
    }

    /**
     * @return the part
     */
    public MdoTableAsEnum getPart() {
	return part;
    }

    /**
     * @param labels the labels to set
     */
    public void setLabels(Map<Long, String> labels) {
	this.labels = labels;
    }

    /**
     * @return the labels
     */
    public Map<Long, String> getLabels() {
	return labels;
    }

    @Override
    public String toString() {
	return "PrintingInformation [alignment=" + alignment + ", labels="
		+ labels + ", order=" + order + ", part=" + part
		+ ", restaurant=" + restaurant + ", size=" + size
		+ ", deleted=" + deleted + ", id=" + id + "]";
    }

}
