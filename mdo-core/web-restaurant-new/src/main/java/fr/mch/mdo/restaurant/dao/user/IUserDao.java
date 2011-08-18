package fr.mch.mdo.restaurant.dao.user;

import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface IUserDao extends IDaoServices
{
    void savePassword(String levelPassword, String newPassword, Long userAuthenticationId) throws MdoException;
}
