package fr.mch.mdo.restaurant.ui.forms;

import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;

public interface IMdoAdministrationForm extends IMdoForm
{
    IAdministrationManagerViewBean getViewBean();
    
    void setViewBean(IAdministrationManagerViewBean viewBean);
}
