package fr.mch.mdo.restaurant.services.business.managers.assembler;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.DinnerTable;
import fr.mch.mdo.restaurant.dao.beans.TableBill;
import fr.mch.mdo.restaurant.dto.beans.DinnerTableDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.TableBillDto;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultTableBillsAssembler extends AbstractAssembler implements IManagerAssembler, ILoggerBean 
{
	private ILogger logger;

	private static class LazyHolder {
		private static IManagerAssembler instance = new DefaultTableBillsAssembler(LoggerServiceImpl.getInstance().getLogger(DefaultTableBillsAssembler.class.getName()));
	}

	private DefaultTableBillsAssembler(ILogger logger) {
		this.setLogger(logger);
	}

	public static IManagerAssembler getInstance() {
		return LazyHolder.instance;
	}

	public DefaultTableBillsAssembler() {
	}

	@Override
	public IMdoDtoBean marshal(IMdoDaoBean daoBean, MdoUserContext userContext) {
		TableBillDto dto = null;
		if (daoBean != null) {
			TableBill bean = (TableBill) daoBean;
			dto = new TableBillDto();
			dto.setId(bean.getId());
			dto.setAmount(bean.getAmount());
			if (bean.getDinnerTable() != null) {
				DinnerTableDto dinnerTable = new DinnerTableDto();
				dinnerTable.setId(bean.getDinnerTable().getId());
				dto.setDinnerTable(dinnerTable);
			}
			dto.setMealNumber(bean.getMealNumber());
			dto.setOrder(bean.getOrder());
			dto.setPrinted(bean.getPrinted());
			dto.setReference(bean.getReference());
		}
		return dto;
	}

	@Override
	public IMdoDaoBean unmarshal(IMdoDtoBean dtoBean, IMdoDaoBean... parents) {
		if (dtoBean == null) {
			return null;
		}
		TableBill bean = new TableBill();
		TableBillDto dto = (TableBillDto) dtoBean;
		bean.setId(dto.getId());
		bean.setAmount(bean.getAmount());
		DinnerTable dinnerTable = null;
		if (parents != null && parents.length == 1) {
			dinnerTable = (DinnerTable) parents[0];
		} 
		if (dinnerTable == null && dto.getDinnerTable() != null) {
			dto.getDinnerTable().setBills(null);
			dinnerTable = new DinnerTable();
			dinnerTable.setId(dto.getDinnerTable().getId());
		}
		bean.setDinnerTable(dinnerTable);
		bean.setMealNumber(dto.getMealNumber());
		bean.setOrder(dto.getOrder());
		bean.setPrinted(dto.getPrinted());
		bean.setReference(dto.getReference());

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
