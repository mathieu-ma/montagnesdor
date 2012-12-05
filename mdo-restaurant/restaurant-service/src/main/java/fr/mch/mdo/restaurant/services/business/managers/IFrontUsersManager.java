package fr.mch.mdo.restaurant.services.business.managers;

import fr.mch.mdo.restaurant.beans.dto.UserAuthenticationDto;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface IFrontUsersManager
{
	UserAuthenticationDto findByLogin(String login) throws MdoException;

	UserAuthenticationDto find(Long authId) throws MdoException;

    /**
     *	This method will saved the new password
     * 
     * @param authId the user authentication id. 
     * @param levelPassword the level password to be saved
     * @param newPassword the new password to be saved
     * @throws MdoException any exception occurs
     */
    void changePassword(Long authId, String levelPassword, String newPassword) throws MdoException;
}
