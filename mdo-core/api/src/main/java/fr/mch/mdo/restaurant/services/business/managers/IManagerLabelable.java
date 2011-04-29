package fr.mch.mdo.restaurant.services.business.managers;

import java.util.Map;

import fr.mch.mdo.restaurant.dto.beans.LocaleDto;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface IManagerLabelable {
	Map<Long, String> getLabels(LocaleDto currentLocale) throws MdoException;
}
