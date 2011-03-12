package fr.mch.mdo.restaurant.business.managers.user;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.business.managers.IMdoManager;

public interface IUserManager extends IMdoManager
{

    /**
     *	This method will saved the new password
     * 
     * @param userContext user context
     * @param newPassowrd the new password to be saved
     * @throws Exception any exception occurs
     */
    public void savePassword(String levelPassword, String newPassword, IMdoBean userContext) throws Exception;

}
