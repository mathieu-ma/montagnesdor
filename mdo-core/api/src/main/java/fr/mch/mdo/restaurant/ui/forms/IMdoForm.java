package fr.mch.mdo.restaurant.ui.forms;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;

public interface IMdoForm
{
    IMdoBean getUserContext();
    
    void setUserContext(IMdoBean userContext);

    void setDtoBean(IMdoDtoBean dto);

    IMdoDtoBean getDtoBean();
    
    IAdministrationManagerViewBean getViewBean();
    
    void setViewBean(IAdministrationManagerViewBean viewBean);
}
