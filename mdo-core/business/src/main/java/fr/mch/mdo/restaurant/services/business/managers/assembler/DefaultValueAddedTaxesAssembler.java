package fr.mch.mdo.restaurant.services.business.managers.assembler;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.dao.beans.ValueAddedTax;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumDto;
import fr.mch.mdo.restaurant.dto.beans.ValueAddedTaxDto;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultValueAddedTaxesAssembler extends AbstractAssembler implements IManagerAssembler, ILoggerBean 
{
	private ILogger logger;

	private IManagerAssembler mdoTableAsEnumsAssembler;

	private static class LazyHolder {
		private static IManagerAssembler instance = new DefaultValueAddedTaxesAssembler(LoggerServiceImpl.getInstance().getLogger(DefaultValueAddedTaxesAssembler.class.getName()));
	}

	private DefaultValueAddedTaxesAssembler(ILogger logger) {
		this.setLogger(logger);
		this.mdoTableAsEnumsAssembler = DefaultMdoTableAsEnumsAssembler.getInstance();
	}

	public static IManagerAssembler getInstance() {
		return LazyHolder.instance;
	}

	public DefaultValueAddedTaxesAssembler() {
	}

	@Override
	public IMdoDtoBean marshal(IMdoDaoBean daoBean) {
		ValueAddedTaxDto dto = null;
		if (daoBean != null) {
			ValueAddedTax bean = (ValueAddedTax) daoBean;
			dto = new ValueAddedTaxDto();
			dto.setId(bean.getId());
			MdoTableAsEnum code = bean.getCode();
			MdoTableAsEnumDto codeDto = (MdoTableAsEnumDto) mdoTableAsEnumsAssembler.marshal(code);
			dto.setCode(codeDto);
			dto.setRate(bean.getRate());
		}
		return dto;
	}

	@Override
	public IMdoDaoBean unmarshal(IMdoDtoBean dtoBean, IMdoDaoBean... parents) {
		if (dtoBean == null) {
			return null;
		}
		ValueAddedTax bean = new ValueAddedTax();
		ValueAddedTaxDto dto = (ValueAddedTaxDto) dtoBean;
		bean.setId(dto.getId());
		bean.setCode((MdoTableAsEnum) mdoTableAsEnumsAssembler.unmarshal(dto.getCode()));
		bean.setRate(dto.getRate());
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

	/**
	 * @return the mdoTableAsEnumsAssembler
	 */
	public IManagerAssembler getMdoTableAsEnumsAssembler() {
		return mdoTableAsEnumsAssembler;
	}

	/**
	 * @param mdoTableAsEnumsAssembler
	 *            the mdoTableAsEnumsAssembler to set
	 */
	public void setMdoTableAsEnumsAssembler(IManagerAssembler mdoTableAsEnumsAssembler) {
		this.mdoTableAsEnumsAssembler = mdoTableAsEnumsAssembler;
	}
}
