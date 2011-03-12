package fr.mch.mdo.restaurant.dao.users;

import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.authentication.AuthenticationPasswordLevel;
import fr.mch.mdo.restaurant.dao.beans.Locale;
import fr.mch.mdo.restaurant.dao.beans.UserAuthentication;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface IUserAuthenticationsDao extends IDaoServices, ILoginUserAuthenticationsDao
{
    void changePassword(UserAuthentication userAuthentication, AuthenticationPasswordLevel levelPassword, String newPassword) throws MdoException;
    UserAuthentication changePassword(Long id, AuthenticationPasswordLevel levelPassword, String newPassword) throws MdoException;

    void changePrintingLanguage(UserAuthentication userAuthentication, Locale printingLocale) throws MdoException;
    UserAuthentication changePrintingLanguage(Long id, Locale printingLocale) throws MdoException;
}
