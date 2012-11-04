package fr.mch.mdo.utils;

import java.util.List;

import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;

public interface ILocalesAssembler extends IManagerAssembler, ILoggerBean 
{
	
	List<IMdoDtoBean> marshal(List<? extends IMdoBean> list, String defaultLanguageCode);
	
	IMdoDtoBean marshal(IMdoDaoBean daoBean, String defaultLanguageCode);

}
