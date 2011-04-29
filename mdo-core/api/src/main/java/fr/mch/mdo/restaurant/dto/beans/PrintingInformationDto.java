/*
 * Created on 29 avr. 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package fr.mch.mdo.restaurant.dto.beans;

import java.util.Map;

import fr.mch.mdo.restaurant.beans.IBeanLabelable;
import fr.mch.mdo.restaurant.beans.MdoDtoBean;

/**
 * This class is used for t_category mapping. This table is used for product
 * category.
 * 
 * @author Mathieu MA sous conrad
 */
public class PrintingInformationDto extends MdoDtoBean implements IBeanLabelable 
{
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

    /**
     * This is a foreign key that refers to t_restaurant. 
     * It is used to specify the restaurant. 
     */
    private RestaurantDto restaurant;

    /**
     * It is used to specify the order of the printing information. 
     */
    private int order;

    /**
     * This is a foreign key that refers to t_enum. 
     * It is used to specify the alignment of the printing information.
     */
    private MdoTableAsEnumDto alignment;

    /**
     * This is a foreign key that refers to t_enum. 
     * It is used to specify the size of the printing information.
     */
    private MdoTableAsEnumDto size;

    /**
     * This is a foreign key that refers to t_enum. 
     * It is used to specify the part of the printing information.
     */
    private MdoTableAsEnumDto part;

    /**
     * This is used for i18n label.
     */
    private Map<Long, String> labels;

	/**
	 * @param restaurant the restaurant to set
	 */
	public void setRestaurant(RestaurantDto restaurant) {
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
	public MdoTableAsEnumDto getAlignment() {
		return alignment;
	}


	/**
	 * @param alignment the alignment to set
	 */
	public void setAlignment(MdoTableAsEnumDto alignment) {
		this.alignment = alignment;
	}


	/**
	 * @return the size
	 */
	public MdoTableAsEnumDto getSize() {
		return size;
	}


	/**
	 * @param size the size to set
	 */
	public void setSize(MdoTableAsEnumDto size) {
		this.size = size;
	}


	/**
	 * @return the part
	 */
	public MdoTableAsEnumDto getPart() {
		return part;
	}


	/**
	 * @param part the part to set
	 */
	public void setPart(MdoTableAsEnumDto part) {
		this.part = part;
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
		return "PrintingInformationDto [restaurant=" + restaurant + ", order="
				+ order + ", alignment=" + alignment + ", size=" + size
				+ ", part=" + part + ", labels=" + labels + ", id=" + id + "]";
	}


	/**
	 * @return the restaurant
	 */
	public RestaurantDto getRestaurant() {
		return restaurant;
	}
}
