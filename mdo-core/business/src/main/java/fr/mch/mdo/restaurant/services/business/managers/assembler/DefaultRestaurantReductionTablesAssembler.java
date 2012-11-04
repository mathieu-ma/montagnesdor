package fr.mch.mdo.restaurant.services.business.managers.assembler;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.beans.RestaurantReductionTable;
import fr.mch.mdo.restaurant.dao.beans.TableType;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantReductionTableDto;
import fr.mch.mdo.restaurant.dto.beans.TableTypeDto;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultRestaurantReductionTablesAssembler extends AbstractAssembler implements IManagerAssembler, ILoggerBean 
{
	private ILogger logger;

	private IManagerAssembler tableTypesAssembler;

	private static class LazyHolder {
		private static IManagerAssembler instance = new DefaultRestaurantReductionTablesAssembler(LoggerServiceImpl.getInstance().getLogger(
				DefaultRestaurantReductionTablesAssembler.class.getName()));
	}

	private DefaultRestaurantReductionTablesAssembler(ILogger logger) {
		this.setLogger(logger);
		this.tableTypesAssembler = DefaultTableTypesAssembler.getInstance();
	}

	public static IManagerAssembler getInstance() {
		return LazyHolder.instance;
	}

	public DefaultRestaurantReductionTablesAssembler() {
	}

	public IManagerAssembler getTableTypesAssembler() {
		return tableTypesAssembler;
	}

	public void setTableTypesAssembler(IManagerAssembler tableTypesAssembler) {
		this.tableTypesAssembler = tableTypesAssembler;
	}

	@Override
	public IMdoDtoBean marshal(IMdoDaoBean daoBean) {
		RestaurantReductionTableDto dto = null;
		if (daoBean != null) {
			RestaurantReductionTable bean = (RestaurantReductionTable) daoBean;
			dto = new RestaurantReductionTableDto();
			dto.setId(bean.getId());
			if(bean.getRestaurant() != null) {
				RestaurantDto restaurant = new RestaurantDto();
				restaurant.setId(bean.getRestaurant().getId());
				dto.setRestaurant(restaurant);
			}
			TableTypeDto type = (TableTypeDto) tableTypesAssembler.marshal(bean.getType());
			dto.setType(type);
			dto.setMinAmount(bean.getMinAmount());
			dto.setValue(bean.getValue());
		}
		return dto;
	}

	@Override
	public IMdoDaoBean unmarshal(IMdoDtoBean dtoBean, IMdoDaoBean... parents) {
		if (dtoBean == null) {
			return null;
		}
		RestaurantReductionTable bean = new RestaurantReductionTable();
		RestaurantReductionTableDto dto = (RestaurantReductionTableDto) dtoBean;
		bean.setId(dto.getId());
		Restaurant restaurant = null;
		if (parents != null && parents.length == 1) {
			restaurant = (Restaurant) parents[0];
		} 
		if (restaurant == null && dto.getRestaurant() != null) {
			dto.getRestaurant().setReductionTables(null);
			restaurant = new Restaurant();
			restaurant.setId(dto.getRestaurant().getId());
		}
		bean.setRestaurant(restaurant);
		TableType type = (TableType) tableTypesAssembler.unmarshal(dto.getType());
		bean.setType(type);
		bean.setMinAmount(dto.getMinAmount());
		bean.setValue(dto.getValue());
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
