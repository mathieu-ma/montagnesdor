package fr.mch.mdo.restaurant.dao.products;

import java.util.Date;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface IProductSoldsDao  extends IDaoServices
{
    IMdoBean findByUniqueKey(Date soldDate, Long productId) throws MdoException;
}
