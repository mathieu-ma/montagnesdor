package fr.mch.mdo.restaurant.services.business.managers.assembler;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.beans.RestaurantPrefixTable;
import fr.mch.mdo.restaurant.dao.beans.TableType;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantPrefixTableDto;
import fr.mch.mdo.restaurant.dto.beans.TableTypeDto;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultRestaurantPrefixTablesAssembler extends AbstractAssembler implements IManagerAssembler, ILoggerBean 
{
	private ILogger logger;

	private IManagerAssembler mdoTableAsEnumsAssembler;
	private IManagerAssembler tableTypesAssembler;
	//private IManagerAssembler restaurantsAssembler;

	private static class LazyHolder {
		private static IManagerAssembler instance = new DefaultRestaurantPrefixTablesAssembler(LoggerServiceImpl.getInstance().getLogger(
				DefaultRestaurantPrefixTablesAssembler.class.getName()));
	}

	private DefaultRestaurantPrefixTablesAssembler(ILogger logger) {
		this.setLogger(logger);
		this.mdoTableAsEnumsAssembler = DefaultMdoTableAsEnumsAssembler.getInstance();
		this.tableTypesAssembler = DefaultTableTypesAssembler.getInstance();
		//this.restaurantsAssembler = DefaultRestaurantsAssembler.getInstance();
	}

	public static IManagerAssembler getInstance() {
		return LazyHolder.instance;
	}

	public DefaultRestaurantPrefixTablesAssembler() {
	}

	public IManagerAssembler getMdoTableAsEnumsAssembler() {
		return mdoTableAsEnumsAssembler;
	}

	public void setMdoTableAsEnumsAssembler(IManagerAssembler mdoTableAsEnumsAssembler) {
		this.mdoTableAsEnumsAssembler = mdoTableAsEnumsAssembler;
	}

	public IManagerAssembler getTableTypesAssembler() {
		return tableTypesAssembler;
	}

	public void setTableTypesAssembler(IManagerAssembler tableTypesAssembler) {
		this.tableTypesAssembler = tableTypesAssembler;
	}

//	/**
//	 * @param restaurantsAssembler the restaurantsAssembler to set
//	 */
//	public void setRestaurantsAssembler(IManagerAssembler restaurantsAssembler) {
//		this.restaurantsAssembler = restaurantsAssembler;
//	}
//
//	/**
//	 * @return the restaurantsAssembler
//	 */
//	public IManagerAssembler getRestaurantsAssembler() {
//		return restaurantsAssembler;
//	}

	@Override
	public IMdoDtoBean marshal(IMdoDaoBean daoBean) {
		RestaurantPrefixTableDto dto = null;
		if (daoBean != null) {
			RestaurantPrefixTable bean = (RestaurantPrefixTable) daoBean;
			dto = new RestaurantPrefixTableDto();
			dto.setId(bean.getId());
			MdoTableAsEnumDto prefix = (MdoTableAsEnumDto) mdoTableAsEnumsAssembler.marshal(bean.getPrefix());
			dto.setPrefix(prefix);
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
		RestaurantPrefixTable bean = new RestaurantPrefixTable();
		RestaurantPrefixTableDto dto = (RestaurantPrefixTableDto) dtoBean;
		bean.setId(dto.getId());
		MdoTableAsEnum prefix = (MdoTableAsEnum) mdoTableAsEnumsAssembler.unmarshal(dto.getPrefix());
		bean.setPrefix(prefix);
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
