package fr.mch.mdo.restaurant.services.business.managers.products;

import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.ValueAddedTaxDto;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;

public interface IValueAddedTaxesManager extends IAdministrationManager
{
	/**
	 * Find ValueAddedTaxDto by code name.
	 * @param codeName the code name.
	 * @param userContext the user context.
	 * @return a ValueAddedTaxDto.
	 * @throws MdoException when any exception occur.
	 */
	ValueAddedTaxDto findByCodeName(String codeName, MdoUserContext userContext) throws MdoException;
}
