package fr.mch.mdo.restaurant.services.business.managers;

import java.util.List;

import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface IAdministrationManager extends IMdoManager 
{
    void processList(IAdministrationManagerViewBean viewBean, MdoUserContext userContext, boolean... lazy) throws MdoException;

    List<IMdoDtoBean> findAll(MdoUserContext userContext, boolean... lazy) throws MdoException;

    IMdoDtoBean save(IMdoDtoBean dtoBean, MdoUserContext userContext) throws MdoException;

    IMdoDtoBean insert(IMdoDtoBean dtoBean, MdoUserContext userContext) throws MdoException;

    IMdoDtoBean update(IMdoDtoBean dtoBean, MdoUserContext userContext) throws MdoException;

    IMdoDtoBean delete(IMdoDtoBean dtoBean, MdoUserContext userContext) throws MdoException;

    IMdoDtoBean findByPrimaryKey(Long id, MdoUserContext userContext, boolean... lazy) throws MdoException;

    IMdoDtoBean load(IMdoDtoBean dtoBean, MdoUserContext userContext, boolean... lazy) throws MdoException;
}
