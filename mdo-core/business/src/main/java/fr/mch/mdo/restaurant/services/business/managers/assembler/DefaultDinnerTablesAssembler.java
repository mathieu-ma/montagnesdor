package fr.mch.mdo.restaurant.services.business.managers.assembler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.DinnerTable;
import fr.mch.mdo.restaurant.dao.beans.OrderLine;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.beans.TableBill;
import fr.mch.mdo.restaurant.dao.beans.TableCashing;
import fr.mch.mdo.restaurant.dao.beans.TableCredit;
import fr.mch.mdo.restaurant.dao.beans.TableType;
import fr.mch.mdo.restaurant.dao.beans.TableVat;
import fr.mch.mdo.restaurant.dao.beans.UserAuthentication;
import fr.mch.mdo.restaurant.dto.beans.DinnerTableDto;
import fr.mch.mdo.restaurant.dto.beans.OrderLineDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.dto.beans.TableBillDto;
import fr.mch.mdo.restaurant.dto.beans.TableCashingDto;
import fr.mch.mdo.restaurant.dto.beans.TableCreditDto;
import fr.mch.mdo.restaurant.dto.beans.TableTypeDto;
import fr.mch.mdo.restaurant.dto.beans.TableVatDto;
import fr.mch.mdo.restaurant.dto.beans.UserAuthenticationDto;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IDinnerTablesManagerAssembler;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultDinnerTablesAssembler extends AbstractAssembler implements IDinnerTablesManagerAssembler 
{
	private ILogger logger;

	private IManagerAssembler mdoTableAsEnumsAssembler;
	private IManagerAssembler tableTypesAssembler;
	private IManagerAssembler tableBillsAssembler;
	private IManagerAssembler tableCashingsAssembler;
	private IManagerAssembler tableCreditsAssembler;
	private IManagerAssembler orderLinesAssembler;
	private IManagerAssembler restaurantsAssembler;
	private IManagerAssembler userAuthenticationsAssembler;
	private IManagerAssembler tableVatsAssembler;
	
	private static class LazyHolder {
		private static IManagerAssembler instance = new DefaultDinnerTablesAssembler(LoggerServiceImpl.getInstance().getLogger(DefaultDinnerTablesAssembler.class.getName()));
	}

	private DefaultDinnerTablesAssembler(ILogger logger) {
		this.setLogger(logger);
		this.mdoTableAsEnumsAssembler = DefaultMdoTableAsEnumsAssembler.getInstance();
		this.tableTypesAssembler = DefaultTableTypesAssembler.getInstance();
		this.tableBillsAssembler = DefaultTableBillsAssembler.getInstance();
		this.tableCashingsAssembler = DefaultTableCashingsAssembler.getInstance();
		this.tableCreditsAssembler = DefaultTableCreditsAssembler.getInstance();
		this.orderLinesAssembler = DefaultOrderLinesAssembler.getInstance();
		this.restaurantsAssembler = DefaultRestaurantsAssembler.getInstance();
		this.userAuthenticationsAssembler = DefaultUserAuthenticationsAssembler.getInstance();
		this.tableVatsAssembler = DefaultTableVatsAssembler.getInstance();
	}

	public static IManagerAssembler getInstance() {
		return LazyHolder.instance;
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

	/**
	 * @return the tableBillsAssembler
	 */
	public IManagerAssembler getTableBillsAssembler() {
		return tableBillsAssembler;
	}

	/**
	 * @param tableBillsAssembler the tableBillsAssembler to set
	 */
	public void setTableBillsAssembler(IManagerAssembler tableBillsAssembler) {
		this.tableBillsAssembler = tableBillsAssembler;
	}

	/**
	 * @return the tableCashingsAssembler
	 */
	public IManagerAssembler getTableCashingsAssembler() {
		return tableCashingsAssembler;
	}

	/**
	 * @param tableCashingsAssembler the tableCashingsAssembler to set
	 */
	public void setTableCashingsAssembler(IManagerAssembler tableCashingsAssembler) {
		this.tableCashingsAssembler = tableCashingsAssembler;
	}

	/**
	 * @return the tableCreditsAssembler
	 */
	public IManagerAssembler getTableCreditsAssembler() {
		return tableCreditsAssembler;
	}

	/**
	 * @param tableCreditsAssembler the tableCreditsAssembler to set
	 */
	public void setTableCreditsAssembler(IManagerAssembler tableCreditsAssembler) {
		this.tableCreditsAssembler = tableCreditsAssembler;
	}

	/**
	 * @return the orderLinesAssembler
	 */
	public IManagerAssembler getOrderLinesAssembler() {
		return orderLinesAssembler;
	}

	/**
	 * @param orderLinesAssembler the orderLinesAssembler to set
	 */
	public void setOrderLinesAssembler(IManagerAssembler orderLinesAssembler) {
		this.orderLinesAssembler = orderLinesAssembler;
	}

	/**
	 * @return the restaurantsAssembler
	 */
	public IManagerAssembler getRestaurantsAssembler() {
		return restaurantsAssembler;
	}

	/**
	 * @param restaurantsAssembler the restaurantsAssembler to set
	 */
	public void setRestaurantsAssembler(IManagerAssembler restaurantsAssembler) {
		this.restaurantsAssembler = restaurantsAssembler;
	}

	/**
	 * @return the userAuthenticationsAssembler
	 */
	public IManagerAssembler getUserAuthenticationsAssembler() {
		return userAuthenticationsAssembler;
	}

	/**
	 * @param userAuthenticationsAssembler the userAuthenticationsAssembler to set
	 */
	public void setUserAuthenticationsAssembler(IManagerAssembler userAuthenticationsAssembler) {
		this.userAuthenticationsAssembler = userAuthenticationsAssembler;
	}

	/**
	 * @return the tableVatsAssembler
	 */
	public IManagerAssembler getTableVatsAssembler() {
		return tableVatsAssembler;
	}

	/**
	 * @param tableVatsAssembler the tableVatsAssembler to set
	 */
	public void setTableVatsAssembler(IManagerAssembler tableVatsAssembler) {
		this.tableVatsAssembler = tableVatsAssembler;
	}

	public DefaultDinnerTablesAssembler() {
	}

	public IManagerAssembler getMdoTableAsEnumsAssembler() {
		return mdoTableAsEnumsAssembler;
	}

	public void setMdoTableAsEnumsAssembler(IManagerAssembler mdoTableAsEnumsAssembler) {
		this.mdoTableAsEnumsAssembler = mdoTableAsEnumsAssembler;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IMdoDtoBean marshal(IMdoDaoBean daoBean) {
		DinnerTableDto dto = null;
		if (daoBean != null) {
			DinnerTable bean = (DinnerTable) daoBean;
			dto = new DinnerTableDto();
			// Dinner Table
			dto.setId(bean.getId());
			dto.setAmountsSum(bean.getAmountsSum());
			if (dto.getAmountsSum() == null) {
				dto.setAmountsSum(bean.getAmountsSumByFormula());
			}
			dto.setAmountPay(bean.getAmountPay());
			if (dto.getAmountPay() == null) {
				dto.setAmountPay(bean.getAmountPayByFormula());
			}
			Set<TableBillDto> bills = null;
			if (bean.getBills() != null) {
				bills = (Set) tableBillsAssembler.marshal(bean.getBills());
			}
			dto.setBills(bills);
			TableCashingDto cashing = null;
			if (bean.getCashing() != null) {
				cashing = (TableCashingDto) tableCashingsAssembler.marshal(bean.getCashing());
			}
			dto.setCashing(cashing);
			Set<TableCreditDto> credits = null;
			if (bean.getCredits() != null) {
				credits = (Set) tableCreditsAssembler.marshal(bean.getCredits());
			}
			dto.setCredits(credits);
			dto.setCustomersNumber(bean.getCustomersNumber());
			dto.setNumber(bean.getNumber());
			Set<OrderLineDto> orders = null;
			if (bean.getOrders() != null) {
				orders = (Set) orderLinesAssembler.marshal(bean.getOrders());
			}
			dto.setOrders(orders);
			dto.setPrintingDate(bean.getPrintingDate());
			dto.setQuantitiesSum(bean.getQuantitiesSum());
			if (dto.getQuantitiesSum() == null) {
				dto.setQuantitiesSum(bean.getQuantitiesSumByFormula());
			}
			dto.setReductionRatio(bean.getReductionRatio());
			dto.setReductionRatioChanged(bean.getReductionRatioChanged());
			dto.setRegistrationDate(bean.getRegistrationDate());
			RestaurantDto restaurant = (RestaurantDto) restaurantsAssembler.marshal(bean.getRestaurant()); 
			dto.setRestaurant(restaurant);
			dto.setRoo_id(null);
			TableTypeDto type = (TableTypeDto) tableTypesAssembler.marshal(bean.getType()); 
			dto.setType(type);
			UserAuthenticationDto user = (UserAuthenticationDto) userAuthenticationsAssembler.marshal(bean.getUser()); 
			dto.setUser(user);
			Set<TableVatDto> vats = null;
			if (bean.getVats() != null) {
				vats = (Set) tableVatsAssembler.marshal(bean.getVats());
			}
			dto.setVats(vats);
		}
		return dto;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IMdoDaoBean unmarshal(IMdoDtoBean dtoBean, IMdoDaoBean... parents) {
		if (dtoBean == null) {
			return null;
		}
		DinnerTable bean = new DinnerTable();
		DinnerTableDto dto = (DinnerTableDto) dtoBean;
		bean.setId(dto.getId());

		bean.setAmountsSum(dto.getAmountsSum());
		bean.setAmountPay(dto.getAmountPay());
		Set<TableBill> bills = new HashSet<TableBill>();
		if (dto.getBills() != null) {
			bills = (Set) tableBillsAssembler.unmarshal(dto.getBills(), bean);
		}
		bean.setBills(bills);
		TableCashing cashing = null;
		if (dto.getCashing() != null) {
			cashing = (TableCashing) tableCashingsAssembler.unmarshal(dto.getCashing());
		}
		bean.setCashing(cashing);
		Set<TableCredit> credits = new HashSet<TableCredit>();
		if (dto.getCredits() != null) {
			credits = (Set) tableCreditsAssembler.unmarshal(dto.getCredits(), bean);
		}
		bean.setCredits(credits);
		bean.setCustomersNumber(dto.getCustomersNumber());
		bean.setNumber(dto.getNumber());
		Set<OrderLine> orders = new HashSet<OrderLine>();
		if (dto.getOrders() != null) {
			orders = (Set) orderLinesAssembler.unmarshal(dto.getOrders(), bean);
		}
		bean.setOrders(orders);
		bean.setPrintingDate(dto.getPrintingDate());
		bean.setQuantitiesSum(dto.getQuantitiesSum());
		bean.setReductionRatio(dto.getReductionRatio());
		bean.setReductionRatioChanged(dto.getReductionRatioChanged());
		bean.setRegistrationDate(dto.getRegistrationDate());
		Restaurant restaurant = (Restaurant) restaurantsAssembler.unmarshal(dto.getRestaurant()); 
		bean.setRestaurant(restaurant);
		bean.setRoo_id(null);
		TableType type = (TableType) tableTypesAssembler.unmarshal(dto.getType()); 
		bean.setType(type);
		UserAuthentication user = (UserAuthentication) userAuthenticationsAssembler.unmarshal(dto.getUser()); 
		bean.setUser(user);
		Set<TableVat> vats = new HashSet<TableVat>();
		if (dto.getVats() != null) {
			vats = (Set) tableVatsAssembler.unmarshal(dto.getVats(), bean);
		}
		bean.setVats(vats);

		return bean;
	}

	@Override
	public IMdoDtoBean marshalTableType(IMdoDaoBean daoBean) {
		return tableTypesAssembler.marshal(daoBean);
	}
	
	@Override
	public ILogger getLogger() {
		return logger;
	}

	@Override
	public void setLogger(ILogger logger) {
		this.logger = logger;
	}

	private IMdoDtoBean marshalFreeTable(IMdoDaoBean daoBean) {
		DinnerTableDto dto = null;
		if (daoBean != null) {
			DinnerTable bean = (DinnerTable) daoBean;
			dto = new DinnerTableDto();
			// Dinner Table
			dto.setId(bean.getId());
			dto.setAmountsSum(bean.getAmountsSum());
			if (dto.getAmountsSum() == null) {
				dto.setAmountsSum(bean.getAmountsSumByFormula());
			}
			dto.setAmountPay(bean.getAmountPay());
			if (dto.getAmountPay() == null) {
				dto.setAmountPay(bean.getAmountPayByFormula());
			}
			dto.setCustomersNumber(bean.getCustomersNumber());
			dto.setNumber(bean.getNumber());
			dto.setPrintingDate(bean.getPrintingDate());
			dto.setQuantitiesSum(bean.getQuantitiesSum());
			if (dto.getQuantitiesSum() == null) {
				dto.setQuantitiesSum(bean.getQuantitiesSumByFormula());
			}
			dto.setReductionRatio(bean.getReductionRatio());
			dto.setReductionRatioChanged(bean.getReductionRatioChanged());
			dto.setRegistrationDate(bean.getRegistrationDate());
			dto.setRoo_id(null);
		}
		return dto;
	}

	@Override
	public List<IMdoDtoBean> marshalFreeTables(List<? extends IMdoBean> list) {
		List<IMdoDtoBean> result = new ArrayList<IMdoDtoBean>();
		if (list != null) {
			for (IMdoBean iMdoBean : list) {
				result.add(marshalFreeTable((IMdoDaoBean) iMdoBean));
			}
		}
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IMdoDtoBean marshalFreeTableByNumber(IMdoDaoBean daoBean) {
		DinnerTableDto dto = (DinnerTableDto) marshalFreeTable(daoBean);
		if (dto != null) {
			DinnerTable bean = (DinnerTable) daoBean;
			Set<OrderLineDto> orders = null;
			if (bean.getOrders() != null) {
				orders = (Set) orderLinesAssembler.marshal(bean.getOrders());
			}
			dto.setOrders(orders);
		}
		return dto;
	}
}
