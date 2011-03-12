/*
 * Created on 29 avr. 2004
 *
 */
package fr.mch.mdo.restaurant.dto.beans;

import java.util.List;

import fr.mch.mdo.restaurant.beans.IMdoDtoBean;

/**
 * @author Mathieu MA
 * 
 */
public class ValueAddedTaxesManagerViewBean extends AdministrationManagerViewBean 
{
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

    private List<IMdoDtoBean> codes;

	/**
	 * @param codes the codes to set
	 */
	public void setCodes(List<IMdoDtoBean> codes) {
		this.codes = codes;
	}

	/**
	 * @return the codes
	 */
	public List<IMdoDtoBean> getCodes() {
		return codes;
	}

}
