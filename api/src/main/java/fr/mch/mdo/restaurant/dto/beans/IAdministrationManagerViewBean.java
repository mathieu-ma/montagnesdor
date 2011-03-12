package fr.mch.mdo.restaurant.dto.beans;

import java.util.List;

import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.beans.IMdoViewBean;

/**
 * @author Mathieu MA
 * 
 */
public interface IAdministrationManagerViewBean extends IMdoViewBean
{
    public List<IMdoDtoBean> getList();

    public void setList(List<IMdoDtoBean> list);
}
