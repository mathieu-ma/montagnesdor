package fr.mch.mdo.restaurant.services.business.managers.assembler;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.beans.User;
import fr.mch.mdo.restaurant.dao.beans.UserRestaurant;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.dto.beans.UserDto;
import fr.mch.mdo.restaurant.dto.beans.UserRestaurantDto;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultUserRestaurantsAssembler extends AbstractAssembler implements IManagerAssembler, ILoggerBean 
{
	private ILogger logger;
	private IManagerAssembler restaurantsAssembler;

	private static class LazyHolder {
		private static IManagerAssembler instance = new DefaultUserRestaurantsAssembler(LoggerServiceImpl.getInstance().getLogger(DefaultUserRestaurantsAssembler.class.getName()));
	}

	private DefaultUserRestaurantsAssembler(ILogger logger) {
		this.setLogger(logger);
		this.restaurantsAssembler = DefaultRestaurantsAssembler.getInstance();
	}

	public static IManagerAssembler getInstance() {
		return LazyHolder.instance;
	}

	public DefaultUserRestaurantsAssembler() {
	}

	public IManagerAssembler getRestaurantsAssembler() {
		return restaurantsAssembler;
	}

	public void setRestaurantsAssembler(IManagerAssembler restaurantsAssembler) {
		this.restaurantsAssembler = restaurantsAssembler;
	}

	@Override
	public IMdoDtoBean marshal(IMdoDaoBean daoBean) {
		UserRestaurantDto dto = null;
		if (daoBean != null) {
			UserRestaurant bean = (UserRestaurant) daoBean;
			dto = new UserRestaurantDto();
			dto.setId(bean.getId());
			RestaurantDto restaurant = (RestaurantDto) restaurantsAssembler.marshal(bean.getRestaurant());
			dto.setRestaurant(restaurant);
			UserDto user = null;
			dto.setUser(user);
		}
		return dto;
	}

	@Override
	public IMdoDaoBean unmarshal(IMdoDtoBean dtoBean, IMdoDaoBean... parents) {
		if (dtoBean == null) {
			return null;
		}
		UserRestaurant bean = new UserRestaurant();
		UserRestaurantDto dto = (UserRestaurantDto) dtoBean;
		bean.setId(dto.getId());
		Restaurant restaurant = (Restaurant) restaurantsAssembler.unmarshal(dto.getRestaurant());
		bean.setRestaurant(restaurant);
		if (parents != null && parents.length == 1) {
			bean.setUser((User) parents[0]);
		}
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
