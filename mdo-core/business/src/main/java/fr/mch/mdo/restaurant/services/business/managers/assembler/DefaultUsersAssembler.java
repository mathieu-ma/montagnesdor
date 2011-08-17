package fr.mch.mdo.restaurant.services.business.managers.assembler;

import java.util.HashSet;
import java.util.Set;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.dao.beans.RestaurantValueAddedTax;
import fr.mch.mdo.restaurant.dao.beans.User;
import fr.mch.mdo.restaurant.dao.beans.UserRestaurant;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.UserDto;
import fr.mch.mdo.restaurant.dto.beans.UserRestaurantDto;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultUsersAssembler extends AbstractAssembler implements IManagerAssembler, ILoggerBean 
{
	private ILogger logger;
	private IManagerAssembler userRestaurantsAssembler;
	private IManagerAssembler mdoTableAsEnumsAssembler;

	private static class LazyHolder {
		private static IManagerAssembler instance = new DefaultUsersAssembler(LoggerServiceImpl.getInstance().getLogger(DefaultUsersAssembler.class.getName()));
	}

	private DefaultUsersAssembler(ILogger logger) {
		this.setLogger(logger);
		this.mdoTableAsEnumsAssembler = DefaultMdoTableAsEnumsAssembler.getInstance();
		this.userRestaurantsAssembler = DefaultUserRestaurantsAssembler.getInstance();
	}

	public static IManagerAssembler getInstance() {
		return LazyHolder.instance;
	}

	public DefaultUsersAssembler() {
	}

	public IManagerAssembler getUserRestaurantsAssembler() {
		return userRestaurantsAssembler;
	}

	public void setUserRestaurantsAssembler(IManagerAssembler userRestaurantsAssembler) {
		this.userRestaurantsAssembler = userRestaurantsAssembler;
	}

	public IManagerAssembler getMdoTableAsEnumsAssembler() {
		return mdoTableAsEnumsAssembler;
	}

	public void setMdoTableAsEnumsAssembler(IManagerAssembler mdoTableAsEnumsAssembler) {
		this.mdoTableAsEnumsAssembler = mdoTableAsEnumsAssembler;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IMdoDtoBean marshal(IMdoDaoBean daoBean, MdoUserContext userContext) {
		UserDto dto = null;
		if (daoBean != null) {
			User bean = (User) daoBean;
			dto = new UserDto();
			dto.setId(bean.getId());
			dto.setBirthdate(bean.getBirthdate());
			dto.setForename1(bean.getForename1());
			dto.setForename2(bean.getForename2());
			dto.setName(bean.getName());
			dto.setSex(bean.isSex());
			MdoTableAsEnumDto title = (MdoTableAsEnumDto) mdoTableAsEnumsAssembler.marshal(bean.getTitle(), userContext);
			dto.setTitle(title);
			Set<UserRestaurantDto> restaurants = (Set) userRestaurantsAssembler.marshal(bean.getRestaurants(), userContext);
			dto.setRestaurants(restaurants);
		}
		return dto;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IMdoDaoBean unmarshal(IMdoDtoBean dtoBean, IMdoDaoBean... parents) {
		if (dtoBean == null) {
			return null;
		}
		User bean = new User();
		UserDto dto = (UserDto) dtoBean;
		bean.setId(dto.getId());
		bean.setBirthdate(dto.getBirthdate());
		bean.setForename1(dto.getForename1());
		bean.setForename2(dto.getForename2());
		bean.setName(dto.getName());
		bean.setSex(dto.isSex());
		MdoTableAsEnum title = (MdoTableAsEnum) mdoTableAsEnumsAssembler.unmarshal(dto.getTitle());
		bean.setTitle(title);
		Set<UserRestaurant> restaurants = new HashSet<UserRestaurant>();
		if (dto.getRestaurants() != null) {
			restaurants = (Set) userRestaurantsAssembler.unmarshal(dto.getRestaurants(), bean);
		}
		bean.setRestaurants(restaurants);
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
