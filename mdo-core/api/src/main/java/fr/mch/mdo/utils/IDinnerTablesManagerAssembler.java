package fr.mch.mdo.utils;

import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;

public interface IDinnerTablesManagerAssembler extends IManagerAssembler, ILoggerBean
{
    IMdoDtoBean marshalTableType(IMdoDaoBean daoBean, MdoUserContext userContext);
}