package fr.mch.mdo.restaurant.services.business.managers.assembler;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.Credit;
import fr.mch.mdo.restaurant.dao.beans.DinnerTable;
import fr.mch.mdo.restaurant.dao.beans.TableCredit;
import fr.mch.mdo.restaurant.dto.beans.CreditDto;
import fr.mch.mdo.restaurant.dto.beans.DinnerTableDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.TableCreditDto;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultTableCreditsAssembler extends AbstractAssembler implements IManagerAssembler, ILoggerBean 
{
	private ILogger logger;

	private IManagerAssembler creditsAssembler;

	private static class LazyHolder {
		private static IManagerAssembler instance = new DefaultTableCreditsAssembler(LoggerServiceImpl.getInstance().getLogger(DefaultTableCreditsAssembler.class.getName()));
	}

	private DefaultTableCreditsAssembler(ILogger logger) {
		this.setLogger(logger);
		this.creditsAssembler = DefaultCreditsAssembler.getInstance();
	}

	public static IManagerAssembler getInstance() {
		return LazyHolder.instance;
	}

	public DefaultTableCreditsAssembler() {
	}

	/**
	 * @return the creditsAssembler
	 */
	public IManagerAssembler getCreditsAssembler() {
		return creditsAssembler;
	}

	/**
	 * @param creditsAssembler the creditsAssembler to set
	 */
	public void setCreditsAssembler(IManagerAssembler creditsAssembler) {
		this.creditsAssembler = creditsAssembler;
	}

	@Override
	public IMdoDtoBean marshal(IMdoDaoBean daoBean, MdoUserContext userContext) {
		TableCreditDto dto = null;
		if (daoBean != null) {
			TableCredit bean = (TableCredit) daoBean;
			dto = new TableCreditDto();
			dto.setId(bean.getId());
			CreditDto credit = (CreditDto) creditsAssembler.marshal(bean.getCredit(), userContext);
			dto.setCredit(credit);
			if (bean.getDinnerTable() != null) {
				DinnerTableDto dinnerTable = new DinnerTableDto();
				dinnerTable.setId(bean.getDinnerTable().getId());
				dto.setDinnerTable(dinnerTable);
			}
		}
		return dto;
	}

	@Override
	public IMdoDaoBean unmarshal(IMdoDtoBean dtoBean, IMdoDaoBean... parents) {
		if (dtoBean == null) {
			return null;
		}
		TableCredit bean = new TableCredit();
		TableCreditDto dto = (TableCreditDto) dtoBean;
		bean.setId(dto.getId());

		Credit credit = (Credit) creditsAssembler.unmarshal(dto.getCredit());
		bean.setCredit(credit);
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
