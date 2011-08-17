package fr.mch.mdo.restaurant.services.business.managers.assembler;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.Credit;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dto.beans.CreditDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultCreditsAssembler extends AbstractAssembler implements IManagerAssembler, ILoggerBean 
{
	private ILogger logger;

	private IManagerAssembler restaurantsAssembler;

	private static class LazyHolder {
		private static IManagerAssembler instance = new DefaultCreditsAssembler(
				LoggerServiceImpl.getInstance().getLogger(DefaultCreditsAssembler.class.getName()));
	}

	private DefaultCreditsAssembler(ILogger logger) {
		this.setLogger(logger);
		this.restaurantsAssembler = DefaultRestaurantsAssembler.getInstance();
	}

	public static IManagerAssembler getInstance() {
		return LazyHolder.instance;
	}

	public DefaultCreditsAssembler() {
	}

	/**
	 * @return the restaurantsAssembler
	 */
	public IManagerAssembler getRestaurantsAssembler() {
		return restaurantsAssembler;
	}

	/**
	 * @param restaurantsAssembler the restaurantsAssembler to set
	 */
	public void setRestaurantsAssembler(IManagerAssembler restaurantsAssembler) {
		this.restaurantsAssembler = restaurantsAssembler;
	}

	@Override
	public IMdoDtoBean marshal(IMdoDaoBean daoBean, MdoUserContext userContext) {
		CreditDto dto = null;
		if (daoBean != null) {
			Credit bean = (Credit) daoBean;
			dto = new CreditDto();
			dto.setId(bean.getId());
			dto.setAmount(bean.getAmount());
			dto.setClosingDate(bean.getClosingDate());
			dto.setCreatedDate(bean.getCreatedDate());
			dto.setPrinted(bean.getPrinted());
			dto.setReference(bean.getReference());
			RestaurantDto restaurant = (RestaurantDto) restaurantsAssembler.marshal(bean.getRestaurant(), userContext);
			dto.setRestaurant(restaurant);
		}
		return dto;
	}

	@Override
	public IMdoDaoBean unmarshal(IMdoDtoBean dtoBean, IMdoDaoBean... parents) {
		if (dtoBean == null) {
			return null;
		}
		Credit bean = new Credit();
		CreditDto dto = (CreditDto) dtoBean;
		bean.setId(dto.getId());
		bean.setAmount(dto.getAmount());
		bean.setClosingDate(dto.getClosingDate());
		bean.setCreatedDate(dto.getCreatedDate());
		bean.setPrinted(dto.getPrinted());
		bean.setReference(dto.getReference());
		Restaurant restaurant = (Restaurant) restaurantsAssembler.unmarshal(dto.getRestaurant());
		bean.setRestaurant(restaurant);
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
