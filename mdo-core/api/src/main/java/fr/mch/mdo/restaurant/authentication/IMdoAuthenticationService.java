package fr.mch.mdo.restaurant.authentication;

import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.authentication.AuthenticationPasswordLevel;
import fr.mch.mdo.restaurant.exception.MdoException;

/**
 * @author Mathieu MA
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public interface IMdoAuthenticationService extends ILoggerBean
{
    public IMdoBean authenticate(String login, String password) throws MdoException;
    public IMdoBean authenticate(String login, String password, AuthenticationPasswordLevel levelPassword) throws MdoException;
}
