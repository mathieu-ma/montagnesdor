package fr.mch.mdo.restaurant.dao;

import java.util.List;
import java.util.Map;

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
    /**
     * Update fields "fields" of the current bean by keys "keys".
     * This method allows to update in mass or only one row depending on the values in the keys map.
     * You can only update first level fields not child fields.
     * 
     * @param fields is a map of key property with values to be updated.
     * @param keys is a map of key property with values to be filtered.
     * @throws MdoException when any exception occur.
     */
    void updateFieldsByKeys(Map<String, Object> fields, Map<String, Object> keys) throws MdoException;
    /**
     * Update fields "fields" of the bean of class "clazz" by keys "keys".
     * This method allows to update in mass or only one row depending on the values in the keys map.
     * You can only update first level fields not child fields.
     *  
     * @param clazz the mapped class to be updated.
     * @param fields is a map of key property with values to be updated.
     * @param keys is a map of key property with values to be filtered.
     * @throws MdoException when any exception occur.
     */
    void updateFieldsByKeys(Class<? extends IMdoBean> clazz, Map<String, Object> fields, Map<String, Object> keys) throws MdoException;

    IMdoBean delete(IMdoBean bean, boolean... isLazy) throws MdoException;
    /**
     * Delete current bean by keys "keys".
     * This method allows to delete in mass or only one row depending on the values in the keys map.
     * 
     * @param keys is a map of key property with values to be filtered.
     * @throws MdoException when any exception occur.
     */
    void deleteByKeys(Map<String, Object> keys) throws MdoException;
    /**
     * Delete bean of class "clazz" by keys "keys".
     * This method allows to delete in mass or only one row depending on the values in the keys map.
     *  
     * @param clazz the mapped class to be deleted.
     * @param keys is a map of key property with values to be filtered.
     * @throws MdoException when any exception occur.
     */
    void deleteByKeys(Class<? extends IMdoBean> clazz, Map<String, Object> keys) throws MdoException;
}
