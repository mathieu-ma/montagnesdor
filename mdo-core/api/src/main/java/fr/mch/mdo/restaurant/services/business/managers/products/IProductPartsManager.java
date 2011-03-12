package fr.mch.mdo.restaurant.services.business.managers.products;

import java.util.Map;

import fr.mch.mdo.restaurant.dto.beans.LocaleDto;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;

public interface IProductPartsManager extends IAdministrationManager
{
	public Map<String, String> getLabels(LocaleDto currentLocale) throws MdoException;
}

