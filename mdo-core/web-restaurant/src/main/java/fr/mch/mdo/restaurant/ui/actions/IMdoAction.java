package fr.mch.mdo.restaurant.ui.actions;

import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.ui.forms.IMdoForm;

public interface IMdoAction extends ILoggerBean
{
    public IMdoForm getForm();
    
    public void setForm(IMdoForm form);
    
    public IMdoBean getUserContext();
    
    public void setUserContext(IMdoBean userContext);
    
    public String execute() throws Exception;
}
