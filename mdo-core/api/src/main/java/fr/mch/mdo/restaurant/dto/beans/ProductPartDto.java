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
 * This class is used for t_product_part mapping. This table is used for product
 * part.
 * 
 * @author Mathieu MA sous conrad
 */
public class ProductPartDto extends MdoDtoBean {
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This is a foreign key that refers to t_enum. It is used to specify the
	 * product part: ENTREE, PLAT DE RESISTANCE, DESSERT, CAFE...
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
	 * @param code
	 *            the code to set
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
	 * @param labels
	 *            the labels to set
	 */
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
		ProductPartDto other = (ProductPartDto) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductPart [code=" + code + ", labels=" + labels + ", id=" + id + "]";
	}
}
