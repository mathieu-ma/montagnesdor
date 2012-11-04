package fr.mch.mdo.restaurant.services.business.managers.products;

import fr.mch.mdo.restaurant.dto.beans.ValueAddedTaxDto;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;

public interface IValueAddedTaxesManager extends IAdministrationManager
{
	/**
	 * Find ValueAddedTaxDto by code name.
	 * @param codeName the code name.
	 * @return a ValueAddedTaxDto.
	 * @throws MdoException when any exception occur.
	 */
	ValueAddedTaxDto findByCodeName(String codeName) throws MdoException;
}
