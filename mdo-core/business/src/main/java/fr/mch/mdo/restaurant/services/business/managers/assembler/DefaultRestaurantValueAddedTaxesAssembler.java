package fr.mch.mdo.restaurant.services.business.managers.assembler;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.beans.RestaurantValueAddedTax;
import fr.mch.mdo.restaurant.dao.beans.ValueAddedTax;
import fr.mch.mdo.restaurant.dto.beans.RestaurantValueAddedTaxDto;
import fr.mch.mdo.restaurant.dto.beans.ValueAddedTaxDto;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultRestaurantValueAddedTaxesAssembler extends AbstractAssembler implements IManagerAssembler, ILoggerBean 
{
	private ILogger logger;

	private IManagerAssembler vatsAssembler;

	private static class LazyHolder {
		private static IManagerAssembler instance = new DefaultRestaurantValueAddedTaxesAssembler(LoggerServiceImpl.getInstance().getLogger(
				DefaultRestaurantValueAddedTaxesAssembler.class.getName()));
	}

	private DefaultRestaurantValueAddedTaxesAssembler(ILogger logger) {
		this.setLogger(logger);
		this.vatsAssembler = DefaultValueAddedTaxesAssembler.getInstance();
	}

	public static IManagerAssembler getInstance() {
		return LazyHolder.instance;
	}

	public DefaultRestaurantValueAddedTaxesAssembler() {
	}

	@Override
	public IMdoDtoBean marshal(IMdoDaoBean daoBean) {
		RestaurantValueAddedTaxDto dto = null;
		if (daoBean != null) {
			RestaurantValueAddedTax bean = (RestaurantValueAddedTax) daoBean;
			dto = new RestaurantValueAddedTaxDto();
			dto.setId(bean.getId());
			ValueAddedTaxDto vat = (ValueAddedTaxDto) vatsAssembler.marshal(bean.getVat());
			dto.setVat(vat);
		}
		return dto;
	}

	@Override
	public IMdoDaoBean unmarshal(IMdoDtoBean dtoBean, IMdoDaoBean... parents) {
		if (dtoBean == null) {
			return null;
		}
		RestaurantValueAddedTax bean = new RestaurantValueAddedTax();
		RestaurantValueAddedTaxDto dto = (RestaurantValueAddedTaxDto) dtoBean;
		bean.setId(dto.getId());
		if (parents != null && parents.length == 1) {
			bean.setRestaurant((Restaurant) parents[0]);
		}
		bean.setVat((ValueAddedTax) vatsAssembler.unmarshal(dto.getVat()));
		return bean;
	}

	/**
	 * @return the vatsAssembler
	 */
	public IManagerAssembler getVatsAssembler() {
		return vatsAssembler;
	}

	/**
	 * @param vatsAssembler
	 *            the vatsAssembler to set
	 */
	public void setVatsAssembler(IManagerAssembler vatsAssembler) {
		this.vatsAssembler = vatsAssembler;
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
