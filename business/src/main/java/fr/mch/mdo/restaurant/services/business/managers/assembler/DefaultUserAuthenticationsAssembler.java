package fr.mch.mdo.restaurant.services.business.managers.assembler;

import java.util.Set;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.Locale;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.beans.User;
import fr.mch.mdo.restaurant.dao.beans.UserAuthentication;
import fr.mch.mdo.restaurant.dao.beans.UserLocale;
import fr.mch.mdo.restaurant.dao.beans.UserRole;
import fr.mch.mdo.restaurant.dto.beans.LocaleDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.dto.beans.UserAuthenticationDto;
import fr.mch.mdo.restaurant.dto.beans.UserDto;
import fr.mch.mdo.restaurant.dto.beans.UserLocaleDto;
import fr.mch.mdo.restaurant.dto.beans.UserRoleDto;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultUserAuthenticationsAssembler extends AbstractAssembler implements IManagerAssembler, ILoggerBean 
{
	private ILogger logger;

	private IManagerAssembler localesAssembler;
	private IManagerAssembler restaurantsAssembler;
	private IManagerAssembler usersAssembler;
	private IManagerAssembler userRolesAssembler;
	private IManagerAssembler userLocalesAssembler;
	
	private static class LazyHolder {
		private static IManagerAssembler instance = new DefaultUserAuthenticationsAssembler(LoggerServiceImpl.getInstance().getLogger(
				DefaultUserAuthenticationsAssembler.class.getName()));
	}

	private DefaultUserAuthenticationsAssembler(ILogger logger) {
		this.setLogger(logger);
		this.localesAssembler = DefaultLocalesAssembler.getInstance();
		this.restaurantsAssembler = DefaultRestaurantsAssembler.getInstance();
		this.usersAssembler = DefaultUsersAssembler.getInstance();
		this.userRolesAssembler = DefaultUserRolesAssembler.getInstance();
		this.userLocalesAssembler = DefaultUserLocalesAssembler.getInstance();
	}

	public static IManagerAssembler getInstance() {
		return LazyHolder.instance;
	}

	public DefaultUserAuthenticationsAssembler() {
	}

	public IManagerAssembler getLocalesAssembler() {
		return localesAssembler;
	}

	public void setLocalesAssembler(IManagerAssembler localesAssembler) {
		this.localesAssembler = localesAssembler;
	}

	public IManagerAssembler getRestaurantsAssembler() {
		return restaurantsAssembler;
	}

	public void setRestaurantsAssembler(IManagerAssembler restaurantsAssembler) {
		this.restaurantsAssembler = restaurantsAssembler;
	}

	public IManagerAssembler getUsersAssembler() {
		return usersAssembler;
	}

	public void setUsersAssembler(IManagerAssembler usersAssembler) {
		this.usersAssembler = usersAssembler;
	}

	public IManagerAssembler getUserRolesAssembler() {
		return userRolesAssembler;
	}

	public void setUserRolesAssembler(IManagerAssembler userRolesAssembler) {
		this.userRolesAssembler = userRolesAssembler;
	}

	public IManagerAssembler getUserLocalesAssembler() {
		return userLocalesAssembler;
	}

	public void setUserLocalesAssembler(IManagerAssembler userLocalesAssembler) {
		this.userLocalesAssembler = userLocalesAssembler;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IMdoDtoBean marshal(IMdoDaoBean daoBean, MdoUserContext userContext) {
		UserAuthenticationDto dto = null;
		if (daoBean != null) {
			UserAuthentication bean = (UserAuthentication) daoBean;
			dto = new UserAuthenticationDto();
			dto.setId(bean.getId());
			dto.setLevelPass1(bean.getLevelPass1());
			dto.setLevelPass2(bean.getLevelPass2());
			dto.setLevelPass3(bean.getLevelPass3());
			dto.setLogin(bean.getLogin());
			dto.setPassword(bean.getPassword());
			LocaleDto printingLocale = (LocaleDto) localesAssembler.marshal(bean.getPrintingLocale(), userContext);
			dto.setPrintingLocale(printingLocale);
			RestaurantDto restaurant = (RestaurantDto) restaurantsAssembler.marshal(bean.getRestaurant(), userContext);
			dto.setRestaurant(restaurant);	
			UserDto user = (UserDto) usersAssembler.marshal(bean.getUser(), userContext);
			dto.setUser(user);
			UserRoleDto userRole = (UserRoleDto) userRolesAssembler.marshal(bean.getUserRole(), userContext);
			dto.setUserRole(userRole);
			Set<UserLocaleDto> locales = (Set) userLocalesAssembler.marshal(bean.getLocales(), userContext); 
			dto.setLocales(locales);
		}
		return dto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IMdoDaoBean unmarshal(IMdoDtoBean dtoBean, IMdoDaoBean... parents) {
		if (dtoBean == null) {
			return null;
		}
		UserAuthentication bean = new UserAuthentication();
		UserAuthenticationDto dto = (UserAuthenticationDto) dtoBean;
		bean.setId(dto.getId());

		bean.setLevelPass1(dto.getLevelPass1());
		bean.setLevelPass2(dto.getLevelPass2());
		bean.setLevelPass3(dto.getLevelPass3());
		bean.setLogin(dto.getLogin());
		bean.setPassword(dto.getPassword());
		Locale printingLocale = (Locale) localesAssembler.unmarshal(dto.getPrintingLocale());
		bean.setPrintingLocale(printingLocale);
		Restaurant restaurant = (Restaurant) restaurantsAssembler.unmarshal(dto.getRestaurant());
		bean.setRestaurant(restaurant);	
		User user = (User) usersAssembler.unmarshal(dto.getUser());
		bean.setUser(user);
		UserRole userRole = (UserRole) userRolesAssembler.unmarshal(dto.getUserRole());
		bean.setUserRole(userRole);
		Set<UserLocale> locales = (Set) userLocalesAssembler.unmarshal(dto.getLocales(), bean); 
		bean.setLocales(locales);
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
