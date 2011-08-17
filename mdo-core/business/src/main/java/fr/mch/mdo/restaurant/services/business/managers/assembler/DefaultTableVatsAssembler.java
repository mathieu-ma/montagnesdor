package fr.mch.mdo.restaurant.services.business.managers.assembler;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.DinnerTable;
import fr.mch.mdo.restaurant.dao.beans.TableVat;
import fr.mch.mdo.restaurant.dao.beans.ValueAddedTax;
import fr.mch.mdo.restaurant.dto.beans.DinnerTableDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.TableVatDto;
import fr.mch.mdo.restaurant.dto.beans.ValueAddedTaxDto;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultTableVatsAssembler extends AbstractAssembler implements IManagerAssembler, ILoggerBean 
{
	private ILogger logger;

	private IManagerAssembler vatsAssembler;

	private static class LazyHolder {
		private static IManagerAssembler instance = new DefaultTableVatsAssembler(LoggerServiceImpl.getInstance().getLogger(DefaultTableVatsAssembler.class.getName()));
	}

	private DefaultTableVatsAssembler(ILogger logger) {
		this.setLogger(logger);
		this.vatsAssembler = DefaultValueAddedTaxesAssembler.getInstance();
	}

	public static IManagerAssembler getInstance() {
		return LazyHolder.instance;
	}

	public DefaultTableVatsAssembler() {
	}

	/**
	 * @return the vatsAssembler
	 */
	public IManagerAssembler getVatsAssembler() {
		return vatsAssembler;
	}

	/**
	 * @param vatsAssembler the vatsAssembler to set
	 */
	public void setVatsAssembler(IManagerAssembler vatsAssembler) {
		this.vatsAssembler = vatsAssembler;
	}

	@Override
	public IMdoDtoBean marshal(IMdoDaoBean daoBean, MdoUserContext userContext) {
		TableVatDto dto = null;
		if (daoBean != null) {
			TableVat bean = (TableVat) daoBean;
			dto = new TableVatDto();
			dto.setId(bean.getId());
			dto.setAmount(bean.getAmount());
			if (bean.getDinnerTable() != null) {
				DinnerTableDto dinnerTable = new DinnerTableDto();
				dinnerTable.setId(bean.getDinnerTable().getId());
				dto.setDinnerTable(dinnerTable);
			}
			dto.setValue(bean.getValue());
			ValueAddedTaxDto vat = (ValueAddedTaxDto) vatsAssembler.marshal(bean.getVat(), userContext);
			dto.setVat(vat);
		}
		return dto;
	}

	@Override
	public IMdoDaoBean unmarshal(IMdoDtoBean dtoBean, IMdoDaoBean... parents) {
		if (dtoBean == null) {
			return null;
		}
		TableVat bean = new TableVat();
		TableVatDto dto = (TableVatDto) dtoBean;
		bean.setId(dto.getId());

		bean.setAmount(dto.getAmount());
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
		bean.setValue(dto.getValue());
		ValueAddedTax vat = (ValueAddedTax) vatsAssembler.unmarshal(dto.getVat());
		bean.setVat(vat);

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
