package fr.mch.mdo.restaurant.ui.forms;

import fr.mch.mdo.restaurant.beans.IMdoDtoBean;

public class MdoLabelsForm extends MdoForm 
{
	private Long updatingLanguage;
	private String updatingLabel;
	
	protected MdoLabelsForm(IMdoDtoBean dtoBean) {
		super(dtoBean);
	}

	/**
	 * @param updatingLanguage the updatingLanguage to set
	 */
	public void setUpdatingLanguage(Long updatingLanguage) {
		this.updatingLanguage = updatingLanguage;
	}

	/**
	 * @return the updatingLanguage
	 */
	public Long getUpdatingLanguage() {
		return updatingLanguage;
	}

	/**
	 * @return the updatingLabel
	 */
	public String getUpdatingLabel() {
		return updatingLabel;
	}

	/**
	 * @param updatingLabel the updatingLabel to set
	 */
	public void setUpdatingLabel(String updatingLabel) {
		this.updatingLabel = updatingLabel;
	}
}
