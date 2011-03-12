package fr.mch.mdo.restaurant.dto.beans;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;

public class MdoDtoBean implements IMdoDtoBean
{
    protected IMdoBean userContext;

    public IMdoBean getUserContext()
    {
        return userContext;
    }

    public void setUserContext(IMdoBean userContext)
    {
        this.userContext = userContext;
    }    
}
