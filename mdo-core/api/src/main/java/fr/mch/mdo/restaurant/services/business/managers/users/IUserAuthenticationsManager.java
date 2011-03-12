package fr.mch.mdo.restaurant.services.business.managers.users;

import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;

public interface IUserAuthenticationsManager extends IAdministrationManager
{
    //public Map<String, String> getAvailableLanguages(IMdoBean dtoBean);

    IMdoDtoBean findByLogin(String login, MdoUserContext userContext) throws MdoException;
}
