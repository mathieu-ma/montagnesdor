package fr.mch.mdo.restaurant.dao.table.orders;

import java.util.Date;
import java.util.List;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.DinnerTable;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface IDinnerTablesDao extends IDaoServices
{
    List<IMdoBean> findAllByPrefixNumber(Long restaurantId, String prefixTableNumber, boolean... isLazy) throws MdoException;

    DinnerTable findByNumber(Long restaurantId, String number, boolean... isLazy) throws MdoException;
    
    IMdoBean findByUniqueKey(Long restaurantId, String number, Date cashingDate, boolean... isLazy) throws MdoException;
}
