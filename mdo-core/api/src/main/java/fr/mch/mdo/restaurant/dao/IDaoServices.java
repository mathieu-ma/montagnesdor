package fr.mch.mdo.restaurant.dao;

import java.util.List;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.exception.MdoException;

public interface IDaoServices extends IDaoBase
{
    List<IMdoBean> findAll(boolean... isLazy) throws MdoException;

    IMdoBean findByPrimaryKey(Object key, boolean... isLazy) throws MdoException;

    void load(IMdoBean daoBean, boolean... isLazy) throws MdoException;


    IMdoBean findByUniqueKey(Object propertyValue, boolean... isLazy) throws MdoException;
    IMdoBean findByUniqueKey(Object[] propertyValues, boolean... isLazy) throws MdoException;

    IMdoBean insert(IMdoBean bean, boolean... isLazy) throws MdoException;

    IMdoBean update(IMdoBean bean, boolean... isLazy) throws MdoException;

    IMdoBean delete(IMdoBean bean, boolean... isLazy) throws MdoException;
}
