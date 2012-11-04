package fr.mch.mdo.restaurant.services.business.managers.assembler;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.dao.beans.PrintingInformation;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumDto;
import fr.mch.mdo.restaurant.dto.beans.PrintingInformationDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultPrintingInformationsAssembler extends AbstractAssembler implements IManagerAssembler, ILoggerBean 
{
	private ILogger logger;

	private IManagerAssembler mdoTableAsEnumsAssembler;
	private IManagerAssembler restaurantsAssembler;

	private static class LazyHolder {
		private static IManagerAssembler instance = new DefaultPrintingInformationsAssembler(
				LoggerServiceImpl.getInstance().getLogger(DefaultPrintingInformationsAssembler.class.getName()));
	}

	private DefaultPrintingInformationsAssembler(ILogger logger) {
		this.setLogger(logger);
		this.mdoTableAsEnumsAssembler = DefaultMdoTableAsEnumsAssembler.getInstance();
		this.restaurantsAssembler = DefaultRestaurantsAssembler.getInstance();
	}

	public static IManagerAssembler getInstance() {
		return LazyHolder.instance;
	}

	public DefaultPrintingInformationsAssembler() {
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
	public IMdoDtoBean marshal(IMdoDaoBean daoBean) {
		PrintingInformationDto dto = null;
		if (daoBean != null) {
			PrintingInformation bean = (PrintingInformation) daoBean;
			dto = new PrintingInformationDto();
			dto.setId(bean.getId());
			dto.setOrder(bean.getOrder());
			MdoTableAsEnumDto alignment = (MdoTableAsEnumDto) mdoTableAsEnumsAssembler.marshal(bean.getAlignment());
			dto.setAlignment(alignment);
			MdoTableAsEnumDto part = (MdoTableAsEnumDto) mdoTableAsEnumsAssembler.marshal(bean.getPart());
			dto.setPart(part);
			MdoTableAsEnumDto size = (MdoTableAsEnumDto) mdoTableAsEnumsAssembler.marshal(bean.getSize());
			dto.setSize(size);
			RestaurantDto restaurant = (RestaurantDto) restaurantsAssembler.marshal(bean.getRestaurant());
			dto.setRestaurant(restaurant);
			dto.setLabels(super.getLabels(bean.getLabels()));
		}
		return dto;
	}

	@Override
	public IMdoDaoBean unmarshal(IMdoDtoBean dtoBean, IMdoDaoBean... parents) {
		if (dtoBean == null) {
			return null;
		}
		PrintingInformation bean = new PrintingInformation();
		PrintingInformationDto dto = (PrintingInformationDto) dtoBean;
		bean.setId(dto.getId());

		bean.setId(dto.getId());
		bean.setOrder(dto.getOrder());
		MdoTableAsEnum alignment = (MdoTableAsEnum) mdoTableAsEnumsAssembler.unmarshal(dto.getAlignment());
		bean.setAlignment(alignment);
		MdoTableAsEnum part = (MdoTableAsEnum) mdoTableAsEnumsAssembler.unmarshal(dto.getPart());
		bean.setPart(part);
		MdoTableAsEnum size = (MdoTableAsEnum) mdoTableAsEnumsAssembler.unmarshal(dto.getSize());
		bean.setSize(size);
		Restaurant restaurant = (Restaurant) restaurantsAssembler.unmarshal(dto.getRestaurant());
		bean.setRestaurant(restaurant);

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
