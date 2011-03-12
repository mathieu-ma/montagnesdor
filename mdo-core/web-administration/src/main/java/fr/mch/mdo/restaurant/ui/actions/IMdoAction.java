package fr.mch.mdo.restaurant.ui.actions;

import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.ui.forms.IMdoForm;

public interface IMdoAction extends ILoggerBean
{
    IMdoForm getForm();
    
    void setForm(IMdoForm form);
    
    String execute() throws Exception;
}
