package fr.mch.mdo.restaurant.services.business.managers.assembler;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.beans.RestaurantVatTableType;
import fr.mch.mdo.restaurant.dao.beans.TableType;
import fr.mch.mdo.restaurant.dao.beans.ValueAddedTax;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantVatTableTypeDto;
import fr.mch.mdo.restaurant.dto.beans.TableTypeDto;
import fr.mch.mdo.restaurant.dto.beans.ValueAddedTaxDto;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultRestaurantVatTableTypesAssembler extends AbstractAssembler implements IManagerAssembler, ILoggerBean 
{
	private ILogger logger;

	private IManagerAssembler vatsAssembler;
	private IManagerAssembler tableTypesAssembler;
	//private IManagerAssembler restaurantsAssembler;

	private static class LazyHolder {
		private static IManagerAssembler instance = new DefaultRestaurantVatTableTypesAssembler(LoggerServiceImpl.getInstance().getLogger(
				DefaultRestaurantVatTableTypesAssembler.class.getName()));
	}

	private DefaultRestaurantVatTableTypesAssembler(ILogger logger) {
		this.setLogger(logger);
		this.vatsAssembler = DefaultValueAddedTaxesAssembler.getInstance();
		this.tableTypesAssembler = DefaultTableTypesAssembler.getInstance();
		//this.restaurantsAssembler = DefaultRestaurantsAssembler.getInstance();
	}

	public static IManagerAssembler getInstance() {
		return LazyHolder.instance;
	}

	public DefaultRestaurantVatTableTypesAssembler() {
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


	public IManagerAssembler getTableTypesAssembler() {
		return tableTypesAssembler;
	}

	public void setTableTypesAssembler(IManagerAssembler tableTypesAssembler) {
		this.tableTypesAssembler = tableTypesAssembler;
	}

	@Override
	public IMdoDtoBean marshal(IMdoDaoBean daoBean) {
		RestaurantVatTableTypeDto dto = null;
		if (daoBean != null) {
			RestaurantVatTableType bean = (RestaurantVatTableType) daoBean;
			dto = new RestaurantVatTableTypeDto();
			dto.setId(bean.getId());
			ValueAddedTaxDto vat = (ValueAddedTaxDto) vatsAssembler.marshal(bean.getVat());
			dto.setVat(vat);
			if(bean.getRestaurant() != null) {
				RestaurantDto restaurant = new RestaurantDto();
				restaurant.setId(bean.getRestaurant().getId());
				dto.setRestaurant(restaurant);
			}
			TableTypeDto type = (TableTypeDto) tableTypesAssembler.marshal(bean.getType());
			dto.setType(type);
		}
		return dto;
	}

	@Override
	public IMdoDaoBean unmarshal(IMdoDtoBean dtoBean, IMdoDaoBean... parents) {
		if (dtoBean == null) {
			return null;
		}
		RestaurantVatTableType bean = new RestaurantVatTableType();
		RestaurantVatTableTypeDto dto = (RestaurantVatTableTypeDto) dtoBean;
		bean.setId(dto.getId());
		ValueAddedTax vat = (ValueAddedTax) vatsAssembler.unmarshal(dto.getVat());
		bean.setVat(vat);
		Restaurant restaurant = null;
		if (parents != null && parents.length == 1) {
			restaurant = (Restaurant) parents[0];
		} 
		if (restaurant == null && dto.getRestaurant() != null) {
			dto.getRestaurant().setPrefixTableNames(null);
			restaurant = new Restaurant();
			restaurant.setId(dto.getRestaurant().getId());
		}
		bean.setRestaurant(restaurant);
		TableType type = (TableType) tableTypesAssembler.unmarshal(dto.getType());
		bean.setType(type);
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
