package fr.mch.mdo.restaurant.services.business.managers.assembler;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.CashingType;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.dao.beans.TableCashing;
import fr.mch.mdo.restaurant.dto.beans.CashingTypeDto;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumDto;
import fr.mch.mdo.restaurant.dto.beans.TableCashingDto;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultCashingTypesAssembler extends AbstractAssembler implements IManagerAssembler, ILoggerBean 
{
	private ILogger logger;

	private IManagerAssembler mdoTableAsEnumsAssembler;

	private static class LazyHolder {
		private static IManagerAssembler instance = new DefaultCashingTypesAssembler(LoggerServiceImpl.getInstance().getLogger(DefaultCashingTypesAssembler.class.getName()));
	}

	private DefaultCashingTypesAssembler(ILogger logger) {
		this.setLogger(logger);
		this.mdoTableAsEnumsAssembler = DefaultMdoTableAsEnumsAssembler.getInstance();
	}

	public static IManagerAssembler getInstance() {
		return LazyHolder.instance;
	}

	public DefaultCashingTypesAssembler() {
	}

	@Override
	public IMdoDtoBean marshal(IMdoDaoBean daoBean) {
		CashingTypeDto dto = null;
		if (daoBean != null) {
			CashingType bean = (CashingType) daoBean;
			dto = new CashingTypeDto();
			dto.setId(bean.getId());
			dto.setAmount(bean.getAmount());
			MdoTableAsEnumDto type = (MdoTableAsEnumDto) mdoTableAsEnumsAssembler.marshal(bean.getType());
			dto.setType(type);
			if (bean.getTableCashing() != null) {
				TableCashingDto tableCashing = new TableCashingDto();
				tableCashing.setId(bean.getTableCashing().getId());
				dto.setTableCashing(tableCashing);
			}
		}
		return dto;
	}

	@Override
	public IMdoDaoBean unmarshal(IMdoDtoBean dtoBean, IMdoDaoBean... parents) {
		if (dtoBean == null) {
			return null;
		}
		CashingType bean = new CashingType();
		CashingTypeDto dto = (CashingTypeDto) dtoBean;
		bean.setId(dto.getId());

		bean.setAmount(dto.getAmount());
		MdoTableAsEnum type = (MdoTableAsEnum) mdoTableAsEnumsAssembler.unmarshal(dto.getType());
		bean.setType(type);
		TableCashing tableCashing = null;
		if (parents != null && parents.length == 1) {
			tableCashing = (TableCashing) parents[0];
		} 
		if (tableCashing == null && dto.getTableCashing() != null) {
			dto.getTableCashing().setCashingTypes(null);
			tableCashing = new TableCashing();
			tableCashing.setId(dto.getTableCashing().getId());
		}
		bean.setTableCashing(tableCashing);

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
