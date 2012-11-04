package fr.mch.mdo.restaurant.services.business.managers.assembler;

import java.util.HashSet;
import java.util.Set;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.beans.RestaurantPrefixTable;
import fr.mch.mdo.restaurant.dao.beans.RestaurantReductionTable;
import fr.mch.mdo.restaurant.dao.beans.RestaurantValueAddedTax;
import fr.mch.mdo.restaurant.dao.beans.RestaurantVatTableType;
import fr.mch.mdo.restaurant.dao.beans.TableType;
import fr.mch.mdo.restaurant.dao.beans.ValueAddedTax;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantPrefixTableDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantReductionTableDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantValueAddedTaxDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantVatTableTypeDto;
import fr.mch.mdo.restaurant.dto.beans.TableTypeDto;
import fr.mch.mdo.restaurant.dto.beans.ValueAddedTaxDto;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultRestaurantsAssembler extends AbstractAssembler implements IManagerAssembler, ILoggerBean 
{
	private ILogger logger;

	private IManagerAssembler mdoTableAsEnumsAssembler;
	private IManagerAssembler tableTypesAssembler;
	private IManagerAssembler restaurantVatsAssembler;
	private IManagerAssembler restaurantPrefixTablesAssembler;
	private IManagerAssembler restaurantReductionTablesAssembler;
	private IManagerAssembler restaurantVatTableTypesAssembler;
//	private IManagerAssembler productSpecialCodesAssembler;
	private IManagerAssembler vatsAssembler;

	private static class LazyHolder {
		private static IManagerAssembler instance = new DefaultRestaurantsAssembler(LoggerServiceImpl.getInstance().getLogger(DefaultRestaurantsAssembler.class.getName()));
	}

	private DefaultRestaurantsAssembler(ILogger logger) {
		this.setLogger(logger);
		this.mdoTableAsEnumsAssembler = DefaultMdoTableAsEnumsAssembler.getInstance();
		this.tableTypesAssembler = DefaultTableTypesAssembler.getInstance();
		this.restaurantVatsAssembler = DefaultRestaurantValueAddedTaxesAssembler.getInstance();
		this.restaurantPrefixTablesAssembler = DefaultRestaurantPrefixTablesAssembler.getInstance();
		this.restaurantReductionTablesAssembler = DefaultRestaurantReductionTablesAssembler.getInstance();
		this.restaurantVatTableTypesAssembler = DefaultRestaurantVatTableTypesAssembler.getInstance();
//		this.productSpecialCodesAssembler = DefaultProductSpecialCodesAssembler.getInstance();
		this.vatsAssembler = DefaultValueAddedTaxesAssembler.getInstance();
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

	/**
	 * @return the tableTypesAssembler
	 */
	public IManagerAssembler getTableTypesAssembler() {
		return tableTypesAssembler;
	}

	/**
	 * @param tableTypesAssembler the tableTypesAssembler to set
	 */
	public void setTableTypesAssembler(IManagerAssembler tableTypesAssembler) {
		this.tableTypesAssembler = tableTypesAssembler;
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

	/**
	 * @return the restaurantReductionTablesAssembler
	 */
	public IManagerAssembler getRestaurantReductionTablesAssembler() {
		return restaurantReductionTablesAssembler;
	}

	/**
	 * @param restaurantReductionTablesAssembler the restaurantReductionTablesAssembler to set
	 */
	public void setRestaurantReductionTablesAssembler(
			IManagerAssembler restaurantReductionTablesAssembler) {
		this.restaurantReductionTablesAssembler = restaurantReductionTablesAssembler;
	}

//	/**
//	 * @return the productSpecialCodesAssembler
//	 */
//	public IManagerAssembler getProductSpecialCodesAssembler() {
//		return productSpecialCodesAssembler;
//	}
//
//	/**
//	 * @param productSpecialCodesAssembler the productSpecialCodesAssembler to set
//	 */
//	public void setProductSpecialCodesAssembler(IManagerAssembler productSpecialCodesAssembler) {
//		this.productSpecialCodesAssembler = productSpecialCodesAssembler;
//	}

	/**
	 * @return the restaurantVatTableTypesAssembler
	 */
	public IManagerAssembler getRestaurantVatTableTypesAssembler() {
		return restaurantVatTableTypesAssembler;
	}

	/**
	 * @param restaurantVatTableTypesAssembler the restaurantVatTableTypesAssembler to set
	 */
	public void setRestaurantVatTableTypesAssembler(
			IManagerAssembler restaurantVatTableTypesAssembler) {
		this.restaurantVatTableTypesAssembler = restaurantVatTableTypesAssembler;
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IMdoDtoBean marshal(IMdoDaoBean daoBean) {
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
				specificRound = (MdoTableAsEnumDto) mdoTableAsEnumsAssembler.marshal(bean.getSpecificRound());
			}
			dto.setSpecificRound(specificRound);
			TableTypeDto defaultTableType = null;
			if (bean.getDefaultTableType() != null) {
				defaultTableType = (TableTypeDto) tableTypesAssembler.marshal(bean.getDefaultTableType());
			}
			dto.setDefaultTableType(defaultTableType);
			ValueAddedTaxDto orderLineDefaultVat = null;
			if (bean.getVat() != null) {
				orderLineDefaultVat = (ValueAddedTaxDto) vatsAssembler.marshal(bean.getVat());
			}
			dto.setVat(orderLineDefaultVat);
			dto.setTripleDESKey(bean.getTripleDESKey());
			dto.setVatByTakeaway(bean.isVatByTakeaway());
			dto.setVatRef(bean.getVatRef());
			dto.setVisaRef(bean.getVisaRef());
			Set<RestaurantValueAddedTaxDto> vats = null;
			if (bean.getVats() != null) {
				vats = (Set) restaurantVatsAssembler.marshal(bean.getVats());
			}
			dto.setVats(vats);
			Set<RestaurantPrefixTableDto> prefixTableNames = null;
			if (bean.getPrefixTableNames() != null) {
				prefixTableNames = (Set) restaurantPrefixTablesAssembler.marshal(bean.getPrefixTableNames());
				// Set the user restaurant
//				List<String> prefixTakeawayName = new ArrayList<String>();
//				for (Iterator iterator = prefixTableNames.iterator(); iterator.hasNext();) {
//					RestaurantPrefixTableDto restaurantPrefixTableDto = (RestaurantPrefixTableDto) iterator.next();
//					if (ManagedTableType.TAKE_AWAY.name().equals(restaurantPrefixTableDto.getType().getCode().getName())) {
//						prefixTakeawayName.add(restaurantPrefixTableDto.getPrefix().getName());
//					}
//				}
//				dto.setPrefixTakeawayNames(prefixTakeawayName.toArray(new String[0]));
			}
			dto.setPrefixTableNames(prefixTableNames);

			Set<RestaurantReductionTableDto> reductionTables = null;
			if (bean.getReductionTables() != null) {
				reductionTables = (Set) restaurantReductionTablesAssembler.marshal(bean.getReductionTables());
			}
			dto.setReductionTables(reductionTables);

			Set<RestaurantVatTableTypeDto> vatTableTypes = null;
			if (bean.getVatTableTypes() != null) {
				vatTableTypes = (Set) restaurantVatTableTypesAssembler.marshal(bean.getVatTableTypes());
			}
			dto.setVatTableTypes(vatTableTypes);

//			Set<ProductSpecialCodeDto> productSpecialCodes = null;
//			if (bean.getProductSpecialCodes() != null) {
//				productSpecialCodes = (Set) productSpecialCodesAssembler.marshal(bean.getProductSpecialCodes());
//			}
//			dto.setProductSpecialCodes(productSpecialCodes);
		}
		return dto;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
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
		TableType defaultTableType = null;
		if (dto.getDefaultTableType() != null) {
			defaultTableType = (TableType) tableTypesAssembler.unmarshal(dto.getDefaultTableType());
		}
		bean.setDefaultTableType(defaultTableType);
		ValueAddedTax orderLineDefaultVat = null;
		if (dto.getVat() != null) {
			orderLineDefaultVat = (ValueAddedTax) vatsAssembler.unmarshal(dto.getVat());
		}
		bean.setVat(orderLineDefaultVat);
		bean.setTripleDESKey(dto.getTripleDESKey());
		bean.setVatByTakeaway(dto.isVatByTakeaway());
		bean.setVatRef(dto.getVatRef());
		bean.setVisaRef(dto.getVisaRef());
		Set<RestaurantValueAddedTax> vats = new HashSet<RestaurantValueAddedTax>();
		if (dto.getVats() != null) {
			vats = (Set) restaurantVatsAssembler.unmarshal(dto.getVats(), bean);
		}
		bean.setVats(vats);
		Set<RestaurantPrefixTable> prefixTableNames = new HashSet<RestaurantPrefixTable>();
		if (dto.getPrefixTableNames() != null) {
			prefixTableNames = (Set) restaurantPrefixTablesAssembler.unmarshal(dto.getPrefixTableNames(), bean);
		}
		bean.setPrefixTableNames(prefixTableNames);
		Set<RestaurantReductionTable> reductionTables = new HashSet<RestaurantReductionTable>();
		if (dto.getReductionTables() != null) {
			reductionTables = (Set) restaurantReductionTablesAssembler.unmarshal(dto.getReductionTables(), bean);
		}
		bean.setReductionTables(reductionTables);
		Set<RestaurantVatTableType> vatTableTypes = new HashSet<RestaurantVatTableType>();
		if (dto.getVatTableTypes() != null) {
			vatTableTypes = (Set) restaurantVatTableTypesAssembler.unmarshal(dto.getVatTableTypes(), bean);
		}
		bean.setVatTableTypes(vatTableTypes);
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
