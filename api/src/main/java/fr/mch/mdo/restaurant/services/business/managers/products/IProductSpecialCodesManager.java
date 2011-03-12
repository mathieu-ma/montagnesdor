package fr.mch.mdo.restaurant.services.business.managers.products;

import java.util.List;

import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.ManagedProductSpecialCode;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;

public interface IProductSpecialCodesManager extends IAdministrationManager
{
    List<ManagedProductSpecialCode> getManagedProductSpecialCodes(IMdoDtoBean dtoBean, MdoUserContext userContext) throws MdoException;
}

