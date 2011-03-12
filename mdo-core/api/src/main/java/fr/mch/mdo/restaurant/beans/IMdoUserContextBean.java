package fr.mch.mdo.restaurant.beans;

import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;


public interface IMdoUserContextBean extends IMdoBean
{
	MdoUserContext getUserContext();

    void setUserContext(MdoUserContext userContext);
}
