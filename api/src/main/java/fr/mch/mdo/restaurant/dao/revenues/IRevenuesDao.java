package fr.mch.mdo.restaurant.dao.revenues;

import java.util.Date;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.exception.MdoException;


public interface IRevenuesDao extends IDaoServices
{
    IMdoBean findByUniqueKey(Long restaurantId, Date revenueDate, Long typeTableId, boolean... isLazy) throws MdoException;
}
