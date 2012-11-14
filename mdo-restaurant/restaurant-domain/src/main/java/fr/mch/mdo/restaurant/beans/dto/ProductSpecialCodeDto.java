package fr.mch.mdo.restaurant.beans.dto;

import fr.mch.mdo.restaurant.beans.MdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumDto;

/**
 * @author Mathieu MA
 * 
 */
public class ProductSpecialCodeDto extends MdoDtoBean
{
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This is used to specify the short code enter by user.
	 */
	private String shortCode;

	/**
	 * This is a foreign key that refers to t_enum. It is used to specify the
	 * product special code.
	 */
	private MdoTableAsEnumDto code;
	
	/**
	 * This is a foreign key that refers to t_value_added_tax. It is used to
	 * specify the product special code value added tax.
	 */
	private Long vatId;

	/**
	 * This is used for i18n label.
	 */
	private String label;

	/**
	 * @return the shortCode
	 */
	public String getShortCode() {
		return shortCode;
	}

	/**
	 * @param shortCode
	 *            the shortCode to set
	 */
	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

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
	 * @return the vatId
	 */
	public Long getVatId() {
		return vatId;
	}

	/**
	 * @param vatId the vatId to set
	 */
	public void setVatId(Long vatId) {
		this.vatId = vatId;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return "ProductSpecialCode [code=" + code + ", label=" + label 
				+ ", shortCode=" + shortCode 
				+ ", vatId=" + vatId 
				+ ", id=" + id + "]";
	}
}
