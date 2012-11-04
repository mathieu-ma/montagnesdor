package fr.mch.mdo.restaurant.services.business.managers.assembler;

import java.util.Set;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.CashingType;
import fr.mch.mdo.restaurant.dao.beans.DinnerTable;
import fr.mch.mdo.restaurant.dao.beans.TableCashing;
import fr.mch.mdo.restaurant.dto.beans.CashingTypeDto;
import fr.mch.mdo.restaurant.dto.beans.DinnerTableDto;
import fr.mch.mdo.restaurant.dto.beans.TableCashingDto;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultTableCashingsAssembler extends AbstractAssembler implements IManagerAssembler, ILoggerBean 
{
	private ILogger logger;

	private IManagerAssembler cashingTypesAssembler;

	private static class LazyHolder {
		private static IManagerAssembler instance = new DefaultTableCashingsAssembler(LoggerServiceImpl.getInstance().getLogger(DefaultTableCashingsAssembler.class.getName()));
	}

	private DefaultTableCashingsAssembler(ILogger logger) {
		this.setLogger(logger);
		this.cashingTypesAssembler = DefaultCashingTypesAssembler.getInstance(); 
	}

	public static IManagerAssembler getInstance() {
		return LazyHolder.instance;
	}

	public DefaultTableCashingsAssembler() {
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IMdoDtoBean marshal(IMdoDaoBean daoBean) {
		TableCashingDto dto = null;
		if (daoBean != null) {
			TableCashing bean = (TableCashing) daoBean;
			dto = new TableCashingDto();
			dto.setId(bean.getId());
			dto.setCashingDate(bean.getCashingDate());
			if (bean.getDinnerTable() != null) {
				DinnerTableDto dinnerTable = new DinnerTableDto();
				dinnerTable.setId(bean.getDinnerTable().getId());
				dto.setDinnerTable(dinnerTable);
			}

			Set<CashingTypeDto> cashingTypes = null;
			if (bean.getCashingTypes() != null) {
				cashingTypes = (Set) cashingTypesAssembler.marshal(bean.getCashingTypes());
			}
			dto.setCashingTypes(cashingTypes);
		}
		return dto;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IMdoDaoBean unmarshal(IMdoDtoBean dtoBean, IMdoDaoBean... parents) {
		if (dtoBean == null) {
			return null;
		}
		TableCashing bean = new TableCashing();
		TableCashingDto dto = (TableCashingDto) dtoBean;
		bean.setId(dto.getId());

		bean.setCashingDate(dto.getCashingDate());
		DinnerTable dinnerTable = null;
		if (parents != null && parents.length == 1) {
			dinnerTable = (DinnerTable) parents[0];
		} 
		if (dinnerTable == null && dto.getDinnerTable() != null) {
			dto.getDinnerTable().setCashing(null);
			dinnerTable = new DinnerTable();
			dinnerTable.setId(dto.getDinnerTable().getId());
		}
		bean.setDinnerTable(dinnerTable);

		Set<CashingType> cashingTypes = null;
		if (dto.getCashingTypes() != null) {
			cashingTypes = (Set) cashingTypesAssembler.unmarshal(dto.getCashingTypes(), bean);
		}
		bean.setCashingTypes(cashingTypes);
		
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
