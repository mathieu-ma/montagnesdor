package fr.mch.mdo.restaurant.services.business.managers.assembler;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.dao.beans.ProductPart;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.ProductPartDto;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultProductPartsAssembler extends AbstractAssembler implements IManagerAssembler, ILoggerBean 
{
	private ILogger logger;

	private IManagerAssembler mdoTableAsEnumsAssembler;

	private static class LazyHolder {
		private static IManagerAssembler instance = new DefaultProductPartsAssembler(
				LoggerServiceImpl.getInstance().getLogger(DefaultProductPartsAssembler.class.getName()));
	}

	private DefaultProductPartsAssembler(ILogger logger) {
		this.setLogger(logger);
		this.mdoTableAsEnumsAssembler = DefaultMdoTableAsEnumsAssembler.getInstance();
	}

	public static IManagerAssembler getInstance() {
		return LazyHolder.instance;
	}

	public DefaultProductPartsAssembler() {
	}

	public IManagerAssembler getMdoTableAsEnumsAssembler() {
		return mdoTableAsEnumsAssembler;
	}

	public void setMdoTableAsEnumsAssembler(IManagerAssembler mdoTableAsEnumsAssembler) {
		this.mdoTableAsEnumsAssembler = mdoTableAsEnumsAssembler;
	}

	@Override
	public IMdoDtoBean marshal(IMdoDaoBean daoBean, MdoUserContext userContext) {
		ProductPartDto dto = null;
		if (daoBean != null) {
			ProductPart bean = (ProductPart) daoBean;
			dto = new ProductPartDto();
			dto.setId(bean.getId());
			MdoTableAsEnumDto code = (MdoTableAsEnumDto) mdoTableAsEnumsAssembler.marshal(bean.getCode(), userContext);
			dto.setCode(code);
			dto.setLabels(bean.getLabels());
		}
		return dto;
	}

	@Override
	public IMdoDaoBean unmarshal(IMdoDtoBean dtoBean, IMdoDaoBean... parents) {
		if (dtoBean == null) {
			return null;
		}
		ProductPart bean = new ProductPart();
		ProductPartDto dto = (ProductPartDto) dtoBean;
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
