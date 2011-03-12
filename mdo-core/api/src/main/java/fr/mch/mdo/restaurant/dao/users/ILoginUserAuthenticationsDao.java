package fr.mch.mdo.restaurant.dao.users;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface ILoginUserAuthenticationsDao
{
    IMdoBean findByLogin(String login) throws MdoException;
}
