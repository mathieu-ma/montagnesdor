package fr.mch.mdo.restaurant.services.business.managers.printings;

import java.util.List;
import java.util.Map;

import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.LocaleDto;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;


public interface IPrintingInformationsManager extends IAdministrationManager
{
	Map<Long, String> getLabels(LocaleDto currentLocale) throws MdoException;
	
	/**
	 * Get list a list of printing informations by restaurant id.
	 * @param restaurantId the restaurant id.
	 * @return a list of printing informations
	 * @throws MdoException when any exception occur.
	 */
	List<IMdoDtoBean> getList(Long restaurantId) throws MdoException;

}
