package fr.mch.mdo.restaurant.services.business.managers.assembler;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultMdoTableAsEnumsAssembler extends AbstractAssembler implements IManagerAssembler, ILoggerBean 
{
	private ILogger logger;

	private static class LazyHolder {
		private static IManagerAssembler instance = new DefaultMdoTableAsEnumsAssembler(
				LoggerServiceImpl.getInstance().getLogger(DefaultMdoTableAsEnumsAssembler.class.getName()));
	}

	private DefaultMdoTableAsEnumsAssembler(ILogger logger) {
		this.setLogger(logger);
	}

	public static IManagerAssembler getInstance() {
		return LazyHolder.instance;
	}

	public DefaultMdoTableAsEnumsAssembler() {
	}

	@Override
	public IMdoDtoBean marshal(IMdoDaoBean daoBean, MdoUserContext userContext) {
		MdoTableAsEnumDto dto = null;
		if (daoBean != null) {
			MdoTableAsEnum bean = (MdoTableAsEnum) daoBean;
			dto = new MdoTableAsEnumDto();
			dto.setId(bean.getId());
			dto.setDefaultLabel(bean.getDefaultLabel());
			dto.setLanguageKeyLabel(bean.getLanguageKeyLabel());
			dto.setName(bean.getName());
			dto.setOrder(bean.getOrder());
//			dto.setType(bean.getType().getValue());
			dto.setType(bean.getType());
		}
		return dto;
	}

	@Override
	public IMdoDaoBean unmarshal(IMdoDtoBean dtoBean, IMdoDaoBean... parents) {
		if (dtoBean == null) {
			return null;
		}
		MdoTableAsEnum bean = new MdoTableAsEnum();
		MdoTableAsEnumDto dto = (MdoTableAsEnumDto) dtoBean;
		bean.setId(dto.getId());
		bean.setDefaultLabel(dto.getDefaultLabel());
		bean.setLanguageKeyLabel(dto.getLanguageKeyLabel());
		bean.setName(dto.getName());
		bean.setOrder(dto.getOrder());
		//bean.setType(new MdoString(dto.getType()));
		bean.setType(dto.getType());
		return bean;
	}

	@Override
	public ILogger getLogger() {
		return logger;
	}

	@Override
	public void setLogger(ILogger logger) {
		this.logger = logger;
	}

}
