package fr.mch.mdo.utils;

import java.util.List;
import java.util.Set;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;

public interface IManagerAssembler
{
    IMdoDtoBean marshal(IMdoDaoBean daoBean, MdoUserContext userContext);

    List<IMdoDtoBean> marshal(List<? extends IMdoBean> list, MdoUserContext userContext);

    Set<IMdoDtoBean> marshal(Set<? extends IMdoBean> set, MdoUserContext userContext);

    List<IMdoDaoBean> unmarshal(List<? extends IMdoBean> list, IMdoDaoBean... parents);

    Set<IMdoDaoBean> unmarshal(Set<? extends IMdoBean> set, IMdoDaoBean... parents);

    IMdoDaoBean unmarshal(IMdoDtoBean dtoBean, IMdoDaoBean... parents);
}