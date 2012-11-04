package fr.mch.mdo.restaurant.services.business.managers.users;

import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;
import fr.mch.mdo.restaurant.services.business.managers.IManagerLabelable;

public interface IUserRolesManager extends IAdministrationManager, IManagerLabelable
{
	IMdoDtoBean findByCode(String code) throws MdoException; 
}
