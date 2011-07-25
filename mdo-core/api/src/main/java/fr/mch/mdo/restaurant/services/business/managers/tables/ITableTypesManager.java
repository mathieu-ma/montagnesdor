package fr.mch.mdo.restaurant.services.business.managers.tables;

import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.TableTypeDto;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;

public interface ITableTypesManager extends IAdministrationManager
{
	/**
	 * Find TableTypeDto by code name.
	 * @param codeName the code name.
	 * @param userContext the user context.
	 * @return a TableTypeDto.
	 * @throws MdoException when any exception occur.
	 */
	TableTypeDto findByCodeName(String codeName, MdoUserContext userContext) throws MdoException;
}
