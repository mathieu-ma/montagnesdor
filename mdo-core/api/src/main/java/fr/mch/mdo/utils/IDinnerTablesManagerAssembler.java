package fr.mch.mdo.utils;

import java.util.List;

import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;

public interface IDinnerTablesManagerAssembler extends IManagerAssembler, ILoggerBean
{
    IMdoDtoBean marshalTableType(IMdoDaoBean daoBean, MdoUserContext userContext);

    IMdoDtoBean marshalFreeTableByNumber(IMdoDaoBean daoBean, MdoUserContext userContext);
    
    List<IMdoDtoBean> marshalFreeTables(List<? extends IMdoBean> list, MdoUserContext userContext);
}