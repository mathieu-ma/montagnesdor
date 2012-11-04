package fr.mch.mdo.restaurant.services.business.managers;

import java.util.List;

import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface IAdministrationManager extends IMdoManager 
{
    void processList(IAdministrationManagerViewBean viewBean, boolean... lazy) throws MdoException;

    List<IMdoDtoBean> findAll(boolean... lazy) throws MdoException;

    IMdoDtoBean save(IMdoDtoBean dtoBean) throws MdoException;

    IMdoDtoBean insert(IMdoDtoBean dtoBean) throws MdoException;

    IMdoDtoBean update(IMdoDtoBean dtoBean) throws MdoException;

    IMdoDtoBean delete(IMdoDtoBean dtoBean) throws MdoException;

    IMdoDtoBean findByPrimaryKey(Long id, boolean... lazy) throws MdoException;

    IMdoDtoBean load(IMdoDtoBean dtoBean, boolean... lazy) throws MdoException;
}
