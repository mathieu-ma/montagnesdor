package fr.mch.mdo.restaurant.dao.authentication;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDao;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface IAuthenticationDao extends IDao
{
    /**
     *  return fr.mch.mdo.restaurant.dao.beans.UserAuthentication
     */
    public IMdoBean getUserByLogin(String login) throws MdoException;

    public void changeUserPassword(IMdoBean user, AuthenticationPasswordLevel levelPassword,
	    String newPassword) throws MdoException;

    public void changePrintLanguage(IMdoBean user, IMdoBean preferedLocale) throws MdoException;
}
