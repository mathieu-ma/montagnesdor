package fr.mch.mdo.restaurant.authorization;

import java.security.Permission;

import javax.security.auth.Subject;

import fr.mch.mdo.logs.ILoggerBean;



/**
 * @author administrateur
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public interface IMdoAuthorizationService extends ILoggerBean
{
    public Permission getPermission(String url);

    public String permitted(final Subject subject, final String pageReq);
}
