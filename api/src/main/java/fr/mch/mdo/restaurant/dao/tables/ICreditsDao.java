package fr.mch.mdo.restaurant.dao.tables;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface ICreditsDao extends IDaoServices
{
    IMdoBean findByUniqueKey(Long restaurantId, String reference) throws MdoException;
}
