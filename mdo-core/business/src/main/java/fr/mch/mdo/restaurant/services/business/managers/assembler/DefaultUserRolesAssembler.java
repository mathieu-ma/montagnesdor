package fr.mch.mdo.restaurant.services.business.managers.assembler;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.dao.beans.UserRole;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumDto;
import fr.mch.mdo.restaurant.dto.beans.UserRoleDto;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultUserRolesAssembler extends AbstractAssembler implements IManagerAssembler, ILoggerBean 
{
	private ILogger logger;
	private IManagerAssembler mdoTableAsEnumsAssembler;

	private static class LazyHolder {
		private static IManagerAssembler instance = new DefaultUserRolesAssembler(LoggerServiceImpl.getInstance().getLogger(DefaultUserRolesAssembler.class.getName()));
	}

	private DefaultUserRolesAssembler(ILogger logger) {
		this.setLogger(logger);
		this.mdoTableAsEnumsAssembler = DefaultMdoTableAsEnumsAssembler.getInstance();
	}

	public static IManagerAssembler getInstance() {
		return LazyHolder.instance;
	}

	public DefaultUserRolesAssembler() {
	}

	public IManagerAssembler getMdoTableAsEnumsAssembler() {
		return mdoTableAsEnumsAssembler;
	}

	public void setMdoTableAsEnumsAssembler(IManagerAssembler mdoTableAsEnumsAssembler) {
		this.mdoTableAsEnumsAssembler = mdoTableAsEnumsAssembler;
	}

	@Override
	public IMdoDtoBean marshal(IMdoDaoBean daoBean) {
		UserRoleDto dto = null;
		if (daoBean != null) {
			UserRole bean = (UserRole) daoBean;
			dto = new UserRoleDto();
			dto.setId(bean.getId());
			MdoTableAsEnumDto code = (MdoTableAsEnumDto) mdoTableAsEnumsAssembler.marshal(bean.getCode());
			dto.setCode(code);
			dto.setLabels(super.getLabels(bean.getLabels()));
		}
		return dto;
	}

	@Override
	public IMdoDaoBean unmarshal(IMdoDtoBean dtoBean, IMdoDaoBean... parents) {
		if (dtoBean == null) {
			return null;
		}
		UserRole bean = new UserRole();
		UserRoleDto dto = (UserRoleDto) dtoBean;
		bean.setId(dto.getId());
		MdoTableAsEnum code = (MdoTableAsEnum) mdoTableAsEnumsAssembler.unmarshal(dto.getCode());
		bean.setCode(code);
		bean.setLabels(dto.getLabels());
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
