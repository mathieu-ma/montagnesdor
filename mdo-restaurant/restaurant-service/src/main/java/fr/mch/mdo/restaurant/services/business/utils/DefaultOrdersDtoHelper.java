package fr.mch.mdo.restaurant.services.business.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.dto.DinnerTableDto;
import fr.mch.mdo.restaurant.beans.dto.OrderLineDto;
import fr.mch.mdo.restaurant.beans.dto.ProductDto;
import fr.mch.mdo.restaurant.dao.beans.DinnerTable;
import fr.mch.mdo.restaurant.dao.beans.OrderLine;
import fr.mch.mdo.restaurant.dao.beans.Product;
import fr.mch.mdo.restaurant.dao.beans.ProductSpecialCode;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.beans.TableType;
import fr.mch.mdo.restaurant.dao.beans.UserAuthentication;
import fr.mch.mdo.restaurant.services.business.managers.assembler.ManagedTableType;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultOrdersDtoHelper implements IOrdersDtoHelper 
{
	private ILogger logger;

	private static class LazyHolder {
		private static IOrdersDtoHelper instance = new DefaultOrdersDtoHelper(
				LoggerServiceImpl.getInstance().getLogger(DefaultOrdersDtoHelper.class.getName()));
	}

	private DefaultOrdersDtoHelper(ILogger logger) {
		this.setLogger(logger);
	}

	public static IOrdersDtoHelper getInstance() {
		return LazyHolder.instance;
	}

	/**
	 * This constructor is used by ioc
	 */
	public DefaultOrdersDtoHelper() {
	}

	/**
	 * @return the logger
	 */
	public ILogger getLogger() {
		return logger;
	}

	/**
	 * @param logger the logger to set
	 */
	public void setLogger(ILogger logger) {
		this.logger = logger;
	}

	@Override
	public List<DinnerTableDto> findAllTablesFactoring(List<DinnerTable> daoBeans) {
		List<DinnerTableDto> result = new ArrayList<DinnerTableDto>();
		
		for (IMdoBean iMdoBean : daoBeans) {
			DinnerTable dinnerTable = (DinnerTable) iMdoBean;
			DinnerTableDto DinnerTableDto = new DinnerTableDto();
			DinnerTableDto.setAmountPay(dinnerTable.getAmountPay());
			if (dinnerTable.getCashing() != null) {
				DinnerTableDto.setCashingDate(dinnerTable.getCashing().getCashingDate());
			}
			DinnerTableDto.setCustomersNumber(dinnerTable.getCustomersNumber());
			DinnerTableDto.setId(dinnerTable.getId());
			DinnerTableDto.setPrintingDate(dinnerTable.getPrintingDate());
			DinnerTableDto.setQuantitiesSum(dinnerTable.getQuantitiesSum());
			DinnerTableDto.setNumber(dinnerTable.getNumber());
			
			String typeCodeName = null;
			if (dinnerTable.getType() != null && dinnerTable.getType().getCode() != null) {
				typeCodeName = dinnerTable.getType().getCode().getName();
			}
			DinnerTableDto.setTakeaway(TableTypeEnum.TAKE_AWAY.name().equals(typeCodeName));
			
			result.add(DinnerTableDto);
		}
		
		return result;
	}

	@Override
	public DinnerTableDto findTableHeader(DinnerTable table) {
		DinnerTableDto result = new DinnerTableDto();

		result.setCustomersNumber(table.getCustomersNumber());
		result.setId(table.getId());
		result.setNumber(table.getNumber());
		result.setTakeaway(this.isTakeaway(table.getType()));
		
		return result;
	}

	@Override
	public DinnerTableDto findTable(DinnerTable table) {
		DinnerTableDto result = new DinnerTableDto();
		result.setId(table.getId());
		result.setNumber(table.getNumber());
		result.setCustomersNumber(table.getCustomersNumber());
		result.setTakeaway(this.isTakeaway(table.getType()));
		result.setRegistrationDate(table.getRegistrationDate());
		result.setPrintingDate(table.getPrintingDate());
		result.setReductionRatio(table.getReductionRatio());
		result.setReductionRatioManuallyChanged(table.getReductionRatioChanged());

		BigDecimal quantitiesSum = BigDecimal.ZERO;
		BigDecimal amountsSum = BigDecimal.ZERO;
		List<OrderLineDto> orders = new ArrayList<OrderLineDto>();
		if (table.getOrders() != null) {
			for (OrderLine orderLine : table.getOrders()) {
				OrderLineDto orderLineDto = new OrderLineDto();
				
				orderLineDto.setAmount(orderLine.getAmount());
				String code = this.getCode(orderLine);
				orderLineDto.setCode(code);
				orderLineDto.setId(orderLine.getId());
				orderLineDto.setLabel(orderLine.getLabel());
				orderLineDto.setQuantity(orderLine.getQuantity());
				orderLineDto.setUnitPrice(orderLine.getUnitPrice());
				ProductDto product = this.productToProductDto(orderLine.getProduct());
				orderLineDto.setProduct(product);
	
				orders.add(orderLineDto);
				
				quantitiesSum = quantitiesSum.add(orderLineDto.getQuantity());
				amountsSum = amountsSum.add(orderLineDto.getAmount());
			}
		}
		result.setOrders(orders);
//		result.setAmountsSum(table.getAmountsSum());
		result.setAmountsSum(amountsSum);
//		result.setQuantitiesSum(table.getQuantitiesSum());
		result.setQuantitiesSum(quantitiesSum);
		//amountPay = dtb_amounts_sum-dtb_amounts_sum*dtb_reduction_ratio/100
		BigDecimal reduction = amountsSum.multiply(result.getReductionRatio()).divide(BigDecimal.TEN.multiply(BigDecimal.TEN));
		BigDecimal amountPay = amountsSum.subtract(reduction); 
		result.setReduction(reduction);
		result.setAmountPay(amountPay);
						
		return result;
	}

	private ProductDto productToProductDto(Product product) {
		ProductDto result = null;
		if (product != null) {
			result = new ProductDto();
			// Maybe useless 
			//result.setId(product.getId());
			result.setColorRGB(product.getColorRGB());
		}
		return result;
	}

	private String getCode(OrderLine orderLine) {
		String result = null;
		ProductSpecialCode psc = orderLine.getProductSpecialCode();
		if (psc != null) {
			result = psc.getShortCode();
		}
		if (orderLine.getProduct() != null) {
			result += orderLine.getProduct().getCode();
		}
		return result;
	}
	
	@Override
	public Boolean isTakeaway(TableType type) {
		Boolean result = Boolean.FALSE;
		// The type is never null because of restaurant default table type.
		if (ManagedTableType.TAKE_AWAY.name().equals(type.getCode().getName())) {
			result = Boolean.TRUE;
		}
		return result;
	}

	@Override
	public DinnerTable buildTableReset(Long dinnerTableId, Long restaurantId, Long userAuthenticationId, String number, Integer customersNumber) {
		DinnerTable result = new DinnerTable();
		result.setId(dinnerTableId);
		Restaurant restaurant = new Restaurant();
		restaurant.setId(restaurantId);
		result.setRestaurant(restaurant);
		UserAuthentication userAuthentication = new UserAuthentication();
		userAuthentication.setId(userAuthenticationId);
		result.setUser(userAuthentication);
		result.setRegistrationDate(new Date());
		result.setCustomersNumber(customersNumber);
		result.setNumber(number);

		return result;
	}

	@Override
	public fr.mch.mdo.restaurant.dto.beans.ProductDto findProduct(Product product) {
		// TODO Auto-generated method stub
		return null;
	}
}
