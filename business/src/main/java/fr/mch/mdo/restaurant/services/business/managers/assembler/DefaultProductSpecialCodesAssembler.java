package fr.mch.mdo.restaurant.services.business.managers.assembler;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.dao.beans.ProductSpecialCode;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.ProductSpecialCodeDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultProductSpecialCodesAssembler extends AbstractAssembler implements IManagerAssembler, ILoggerBean 
{
	private ILogger logger;

	private IManagerAssembler mdoTableAsEnumsAssembler;
	
	private IManagerAssembler restaurantsAssembler;
	

	private static class LazyHolder {
		private static IManagerAssembler instance = new DefaultProductSpecialCodesAssembler(
				LoggerServiceImpl.getInstance().getLogger(DefaultProductSpecialCodesAssembler.class.getName()));
	}

	private DefaultProductSpecialCodesAssembler(ILogger logger) {
		this.setLogger(logger);
		this.mdoTableAsEnumsAssembler = DefaultMdoTableAsEnumsAssembler.getInstance();
		this.restaurantsAssembler = DefaultRestaurantsAssembler.getInstance();
	}

	public static IManagerAssembler getInstance() {
		return LazyHolder.instance;
	}

	public DefaultProductSpecialCodesAssembler() {
	}

	public IManagerAssembler getMdoTableAsEnumsAssembler() {
		return mdoTableAsEnumsAssembler;
	}

	public void setMdoTableAsEnumsAssembler(IManagerAssembler mdoTableAsEnumsAssembler) {
		this.mdoTableAsEnumsAssembler = mdoTableAsEnumsAssembler;
	}

	public IManagerAssembler getRestaurantsAssembler() {
		return restaurantsAssembler;
	}

	public void setRestaurantsAssembler(IManagerAssembler restaurantsAssembler) {
		this.restaurantsAssembler = restaurantsAssembler;
	}

	@Override
	public IMdoDtoBean marshal(IMdoDaoBean daoBean, MdoUserContext userContext) {
		ProductSpecialCodeDto dto = null;
		if (daoBean != null) {
			ProductSpecialCode bean = (ProductSpecialCode) daoBean;
			dto = new ProductSpecialCodeDto();
			dto.setId(bean.getId());
			dto.setShortCode(bean.getShortCode());
			RestaurantDto restaurant = (RestaurantDto) restaurantsAssembler.marshal(bean.getRestaurant(), userContext);  
			dto.setRestaurant(restaurant);
			MdoTableAsEnumDto code = (MdoTableAsEnumDto) mdoTableAsEnumsAssembler.marshal(bean.getCode(), userContext);
			dto.setCode(code);
			dto.setLabels(bean.getLabels());
		}
		return dto;
	}

	@Override
	public IMdoDaoBean unmarshal(IMdoDtoBean dtoBean, IMdoDaoBean... parents) {
		if (dtoBean == null) {
			return null;
		}
		ProductSpecialCode bean = new ProductSpecialCode();
		ProductSpecialCodeDto dto = (ProductSpecialCodeDto) dtoBean;
		bean.setId(dto.getId());
		bean.setShortCode(dto.getShortCode());
		Restaurant restaurant = (Restaurant) restaurantsAssembler.unmarshal(dto.getRestaurant());  
		bean.setRestaurant(restaurant);
		MdoTableAsEnum code = (MdoTableAsEnum) mdoTableAsEnumsAssembler.unmarshal(dto.getCode());
		bean.setCode(code);
		bean.setLabels(dto.getLabels());
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
