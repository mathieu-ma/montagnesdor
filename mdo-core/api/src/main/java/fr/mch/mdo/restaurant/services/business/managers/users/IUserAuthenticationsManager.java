package fr.mch.mdo.restaurant.services.business.managers.users;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;

public interface IUserAuthenticationsManager extends IAdministrationManager
{
    //public Map<String, String> getAvailableLanguages(IMdoBean dtoBean);

    IMdoDtoBean findByLogin(String login, MdoUserContext userContext) throws MdoException;
    
    /**
     *	This method will saved the new password
     * 
     * @param id the user id. 
     * @param levelPassword the level password to be saved
     * @param newPassword the new password to be saved
     * @param userContext user context
     * @throws MdoException any exception occurs
     */
    void changePassword(Long id, String levelPassword, String newPassword, IMdoBean userContext) throws MdoException;

}
