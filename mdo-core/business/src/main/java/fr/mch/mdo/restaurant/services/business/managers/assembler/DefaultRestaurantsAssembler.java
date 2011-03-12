package fr.mch.mdo.restaurant.services.business.managers.assembler;

import java.util.Set;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.beans.RestaurantPrefixTable;
import fr.mch.mdo.restaurant.dao.beans.RestaurantValueAddedTax;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantPrefixTableDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantValueAddedTaxDto;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultRestaurantsAssembler extends AbstractAssembler implements IManagerAssembler, ILoggerBean 
{
	private ILogger logger;

	private IManagerAssembler mdoTableAsEnumsAssembler;
	private IManagerAssembler restaurantVatsAssembler;
	private IManagerAssembler restaurantPrefixTablesAssembler;

	private static class LazyHolder {
		private static IManagerAssembler instance = new DefaultRestaurantsAssembler(LoggerServiceImpl.getInstance().getLogger(DefaultRestaurantsAssembler.class.getName()));
	}

	private DefaultRestaurantsAssembler(ILogger logger) {
		this.setLogger(logger);
		this.mdoTableAsEnumsAssembler = DefaultMdoTableAsEnumsAssembler.getInstance();
		this.restaurantVatsAssembler = DefaultRestaurantValueAddedTaxesAssembler.getInstance();
		this.restaurantPrefixTablesAssembler = DefaultRestaurantPrefixTablesAssembler.getInstance();
	}

	public static IManagerAssembler getInstance() {
		return LazyHolder.instance;
	}

	public DefaultRestaurantsAssembler() {
	}

	public IManagerAssembler getMdoTableAsEnumsAssembler() {
		return mdoTableAsEnumsAssembler;
	}

	public void setMdoTableAsEnumsAssembler(IManagerAssembler mdoTableAsEnumsAssembler) {
		this.mdoTableAsEnumsAssembler = mdoTableAsEnumsAssembler;
	}

	public IManagerAssembler getRestaurantVatsAssembler() {
		return restaurantVatsAssembler;
	}

	public void setRestaurantVatsAssembler(IManagerAssembler restaurantVatsAssembler) {
		this.restaurantVatsAssembler = restaurantVatsAssembler;
	}

	/**
	 * @param restaurantPrefixTablesAssembler the restaurantPrefixTablesAssembler to set
	 */
	public void setRestaurantPrefixTablesAssembler(IManagerAssembler restaurantPrefixTablesAssembler) {
		this.restaurantPrefixTablesAssembler = restaurantPrefixTablesAssembler;
	}

	/**
	 * @return the restaurantPrefixTablesAssembler
	 */
	public IManagerAssembler getRestaurantPrefixTablesAssembler() {
		return restaurantPrefixTablesAssembler;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IMdoDtoBean marshal(IMdoDaoBean daoBean, MdoUserContext userContext) {
		RestaurantDto dto = null;
		if (daoBean != null) {
			Restaurant bean = (Restaurant) daoBean;
			dto = new RestaurantDto();
			dto.setId(bean.getId());
			dto.setAddressCity(bean.getAddressCity());
			dto.setAddressRoad(bean.getAddressRoad());
			dto.setAddressZip(bean.getAddressZip());
			dto.setName(bean.getName());
			dto.setPhone(bean.getPhone());
			dto.setReference(bean.getReference());
			dto.setRegistrationDate(bean.getRegistrationDate());
			MdoTableAsEnumDto specificRound = null;
			if (bean.getSpecificRound() != null) {
				specificRound = (MdoTableAsEnumDto) mdoTableAsEnumsAssembler.marshal(bean.getSpecificRound(), userContext);
			}
			dto.setSpecificRound(specificRound);
			dto.setTakeawayBasicReduction(bean.getTakeawayBasicReduction());
			dto.setTakeawayMinAmountReduction(bean.getTakeawayMinAmountReduction());
			dto.setTripleDESKey(bean.getTripleDESKey());
			dto.setVatByTakeaway(bean.isVatByTakeaway());
			dto.setVatRef(bean.getVatRef());
			dto.setVisaRef(bean.getVisaRef());
			Set<RestaurantValueAddedTaxDto> vats = null;
			if (bean.getVats() != null) {
				vats = (Set) restaurantVatsAssembler.marshal(bean.getVats(), userContext);
			}
			dto.setVats(vats);
			Set<RestaurantPrefixTableDto> prefixTableNames = null;
			if (bean.getPrefixTableNames() != null) {
				prefixTableNames = (Set) restaurantPrefixTablesAssembler.marshal(bean.getPrefixTableNames(), userContext);
			}
			dto.setPrefixTableNames(prefixTableNames);
		}
		return dto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IMdoDaoBean unmarshal(IMdoDtoBean dtoBean, IMdoDaoBean... parents) {
		if (dtoBean == null) {
			return null;
		}
		Restaurant bean = new Restaurant();
		RestaurantDto dto = (RestaurantDto) dtoBean;
		bean.setId(dto.getId());
		bean.setAddressCity(dto.getAddressCity());
		bean.setAddressRoad(dto.getAddressRoad());
		bean.setAddressZip(dto.getAddressZip());
		bean.setName(dto.getName());
		bean.setPhone(dto.getPhone());
		bean.setReference(dto.getReference());
		bean.setRegistrationDate(dto.getRegistrationDate());
		MdoTableAsEnum specificRound = null;
		if (dto.getSpecificRound() != null) {
			specificRound = (MdoTableAsEnum) mdoTableAsEnumsAssembler.unmarshal(dto.getSpecificRound());
		}
		bean.setSpecificRound(specificRound);
		bean.setTakeawayBasicReduction(dto.getTakeawayBasicReduction());
		bean.setTakeawayMinAmountReduction(dto.getTakeawayMinAmountReduction());
		bean.setTripleDESKey(dto.getTripleDESKey());
		bean.setVatByTakeaway(dto.isVatByTakeaway());
		bean.setVatRef(dto.getVatRef());
		bean.setVisaRef(dto.getVisaRef());
		Set<RestaurantValueAddedTax> vats = null;
		if (dto.getVats() != null) {
			vats = (Set) restaurantVatsAssembler.unmarshal(dto.getVats(), bean);
		}
		bean.setVats(vats);
		Set<RestaurantPrefixTable> prefixTableNames = null;
		if (dto.getPrefixTableNames() != null) {
			prefixTableNames = (Set) restaurantPrefixTablesAssembler.unmarshal(dto.getPrefixTableNames(), bean);
		}
		bean.setPrefixTableNames(prefixTableNames);
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
