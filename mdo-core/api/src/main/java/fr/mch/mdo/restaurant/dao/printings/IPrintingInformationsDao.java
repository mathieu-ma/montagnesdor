package fr.mch.mdo.restaurant.dao.printings;

import java.util.List;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface IPrintingInformationsDao extends IDaoServices 
{
    /**
     * Find list of printing informations by restaurant id.
     * @param restaurantId the restaurant id.
     * @return list of printing informations by restaurant id.
     * @throws MdoException when any exception occur.
     */
    List<IMdoBean> findByRestaurant(Long restaurantId) throws MdoException;
}
