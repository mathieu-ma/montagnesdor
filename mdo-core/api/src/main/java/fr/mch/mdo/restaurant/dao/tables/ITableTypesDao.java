package fr.mch.mdo.restaurant.dao.tables;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface ITableTypesDao extends IDaoServices
{
	/**
	 * Find by Unique Keys.
	 * @param code the type of table. Could be TAKE_AWAY, EAT_IN.
	 * @param isLazy lazy loading ?
	 * @return a Table Type.
	 * @throws MdoException when any exceptions occur. 
	 */
	IMdoBean findByUniqueKey(String code, boolean... isLazy) throws MdoException;
}
