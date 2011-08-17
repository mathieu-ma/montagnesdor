package fr.mch.mdo.restaurant.services.business.managers.tables;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.DinnerTableDto;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumDto;
import fr.mch.mdo.restaurant.dto.beans.OrderLineDto;
import fr.mch.mdo.restaurant.dto.beans.ProductSpecialCodeDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.dto.beans.TableBillDto;
import fr.mch.mdo.restaurant.dto.beans.TableCashingDto;
import fr.mch.mdo.restaurant.dto.beans.TableCreditDto;
import fr.mch.mdo.restaurant.dto.beans.TableTypeDto;
import fr.mch.mdo.restaurant.dto.beans.TableVatDto;
import fr.mch.mdo.restaurant.dto.beans.UserAuthenticationDto;
import fr.mch.mdo.restaurant.services.business.managers.DefaultAdministrationManagerTest;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultDinnerTablesManagerTest extends DefaultAdministrationManagerTest 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultDinnerTablesManagerTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultDinnerTablesManagerTest.class);
	}

	@Override
	protected IAdministrationManager getInstance() {
		return DefaultDinnerTablesManager.getInstance();
	}

	@Override
	protected IMdoDtoBean createNewBean() {
		RestaurantDto restaurant = new RestaurantDto();
		restaurant.setId(1L);
		String number = "1";
		Date cashingDate = Calendar.getInstance().getTime();
		UserAuthenticationDto user = new UserAuthenticationDto();
		user.setId(1L);
		// Used for equality of 2 dinner tables
		user.setLogin("mch");
		Long rooId = 1L;
		Integer customersNumber = 2;
		BigDecimal quantitiesSum = BigDecimal.valueOf(10.2);
		BigDecimal amountsSum = BigDecimal.valueOf(100.98);
		BigDecimal reductionRatio = BigDecimal.ZERO;
		// amountPay = amountsSum-amountsSum*reductionRatio/100
		BigDecimal amountPay = amountsSum.add(amountsSum.negate().multiply(reductionRatio).divide(BigDecimal.valueOf(100)));
		Date registrationDate = Calendar.getInstance().getTime();
		Date printingDate = Calendar.getInstance().getTime();
		Boolean reductionRatioChanged = Boolean.TRUE;
		TableTypeDto type = new TableTypeDto();
		type.setId(1L);
		Set<OrderLineDto> orders = new HashSet<OrderLineDto>();
		Set<TableBillDto> bills = null;
		Set<TableCreditDto> credits = null;
		Set<TableVatDto> vats = null;
		Set<TableCashingDto> cashings = new HashSet<TableCashingDto>();
		DinnerTableDto dinnerTable = (DinnerTableDto) createNewBean(restaurant,
				number, cashingDate, user, rooId, customersNumber,
				quantitiesSum, amountsSum, reductionRatio, amountPay,
				registrationDate, printingDate, reductionRatioChanged, type,
				orders, bills, credits, vats, cashings);

		OrderLineDto orderLine = new OrderLineDto();
		orderLine.setQuantity(BigDecimal.ONE);
		orderLine.setLabel("Label");
		orderLine.setUnitPrice(BigDecimal.TEN);
		orderLine.setAmount(BigDecimal.TEN);
		ProductSpecialCodeDto productSpecialCode = new ProductSpecialCodeDto();
		productSpecialCode.setId(1L);
		orderLine.setProductSpecialCode(productSpecialCode);
		dinnerTable.addOrderLine(orderLine);

		TableCashingDto cashing = new TableCashingDto();
		MdoTableAsEnumDto cashingType = new MdoTableAsEnumDto();
		cashingType.setId(35L);
		cashing.setType(cashingType);
		cashing.setAmount(BigDecimal.TEN);
		dinnerTable.addCashing(cashing);

		return dinnerTable;
	}

	@Override
	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		RestaurantDto restaurant = new RestaurantDto();
		restaurant.setId(1L);
		String number = "11";
		Date cashingDate = Calendar.getInstance().getTime();
		UserAuthenticationDto user = new UserAuthenticationDto();
		user.setId(1L);
		// Used for equality of 2 dinner tables
		user.setLogin("mch");
		Long rooId = 1L;
		Integer customersNumber = 2;
		BigDecimal quantitiesSum = BigDecimal.valueOf(10.2);
		BigDecimal amountsSum = BigDecimal.valueOf(100.98);
		BigDecimal reductionRatio = BigDecimal.ZERO;
		// amountPay = amountsSum-amountsSum*reductionRatio/100
		BigDecimal amountPay = amountsSum.add(amountsSum.negate().multiply(reductionRatio).divide(BigDecimal.valueOf(100)));
		Date registrationDate = Calendar.getInstance().getTime();
		Date printingDate = Calendar.getInstance().getTime();
		Boolean reductionRatioChanged = Boolean.TRUE;
		TableTypeDto type = new TableTypeDto();
		type.setId(1L);
		Set<OrderLineDto> orders = new HashSet<OrderLineDto>();
		Set<TableBillDto> bills = null;
		Set<TableCreditDto> credits = null;
		Set<TableVatDto> vats = null;
		Set<TableCashingDto> cashings = new HashSet<TableCashingDto>();
		DinnerTableDto dinnerTable = (DinnerTableDto) createNewBean(restaurant,
				number, cashingDate, user, rooId, customersNumber,
				quantitiesSum, amountsSum, reductionRatio, amountPay,
				registrationDate, printingDate, reductionRatioChanged, type,
				orders, bills, credits, vats, cashings);

		OrderLineDto orderLine = new OrderLineDto();
		orderLine.setQuantity(BigDecimal.ONE);
		orderLine.setLabel("Label");
		orderLine.setUnitPrice(BigDecimal.TEN);
		orderLine.setAmount(BigDecimal.TEN);
		ProductSpecialCodeDto productSpecialCode = new ProductSpecialCodeDto();
		productSpecialCode.setId(1L);
		orderLine.setProductSpecialCode(productSpecialCode);
		dinnerTable.addOrderLine(orderLine);

		TableCashingDto cashing = new TableCashingDto();
		MdoTableAsEnumDto cashingType = new MdoTableAsEnumDto();
		cashingType.setId(36L);
		cashing.setType(cashingType);
		cashing.setAmount(BigDecimal.TEN);
		dinnerTable.addCashing(cashing);

		list.add(dinnerTable);
		return list;
	}

	@Override
	public void doUpdate() {
		RestaurantDto restaurant = new RestaurantDto();
		restaurant.setId(1L);
		String number = "111";
		Date cashingDate = Calendar.getInstance().getTime();
		UserAuthenticationDto user = new UserAuthenticationDto();
		user.setId(1L);
		// Used for equality of 2 dinner tables
		user.setLogin("mch");
		Long rooId = 1L;
		Integer customersNumber = 2;
		BigDecimal quantitiesSum = BigDecimal.valueOf(10.2);
		BigDecimal amountsSum = BigDecimal.valueOf(100.98);
		BigDecimal reductionRatio = BigDecimal.ZERO;
		// amountPay = amountsSum-amountsSum*reductionRatio/100
		BigDecimal amountPay = amountsSum.add(amountsSum.negate().multiply(reductionRatio).divide(BigDecimal.valueOf(100)));
		Date registrationDate = Calendar.getInstance().getTime();
		Date printingDate = Calendar.getInstance().getTime();
		Boolean reductionRatioChanged = Boolean.TRUE;
		TableTypeDto type = new TableTypeDto();
		type.setId(1L);
		Set<OrderLineDto> orders = new HashSet<OrderLineDto>();
		Set<TableBillDto> bills = null;
		Set<TableCreditDto> credits = null;
		Set<TableVatDto> vats = null;
		Set<TableCashingDto> cashings = new HashSet<TableCashingDto>();
		DinnerTableDto dinnerTable = (DinnerTableDto) createNewBean(restaurant,
				number, cashingDate, user, rooId, customersNumber,
				quantitiesSum, amountsSum, reductionRatio, amountPay,
				registrationDate, printingDate, reductionRatioChanged, type,
				orders, bills, credits, vats, cashings);

		OrderLineDto orderLine = new OrderLineDto();
		orderLine.setQuantity(BigDecimal.ONE);
		orderLine.setLabel("Label");
		orderLine.setUnitPrice(BigDecimal.TEN);
		orderLine.setAmount(BigDecimal.TEN);
		ProductSpecialCodeDto productSpecialCode = new ProductSpecialCodeDto();
		productSpecialCode.setId(1L);
		orderLine.setProductSpecialCode(productSpecialCode);
		dinnerTable.addOrderLine(orderLine);

		TableCashingDto cashing = new TableCashingDto();
		MdoTableAsEnumDto cashingType = new MdoTableAsEnumDto();
		cashingType.setId(36L);
		cashing.setType(cashingType);
		cashing.setAmount(BigDecimal.TEN);
		dinnerTable.addCashing(cashing);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(dinnerTable, DefaultAdministrationManagerTest.userContext);
			assertTrue("IMdoBean must be instance of " + DinnerTableDto.class, beanToBeUpdated instanceof DinnerTableDto);
			DinnerTableDto castedBean = (DinnerTableDto) beanToBeUpdated;
			assertNotNull("DinnerTableDto number must not be null", castedBean.getNumber());
			assertEquals("Check DinnerTableDto number", number, castedBean.getNumber());
			// Update the created bean
			number = "111A";
			castedBean.setNumber(number);
			this.getInstance().update(castedBean, DefaultAdministrationManagerTest.userContext);
			// Reload the modified bean
			DinnerTableDto updatedBean = new DinnerTableDto();
			updatedBean.setId(castedBean.getId());
			IMdoBean loadedBean = this.getInstance().load(updatedBean, DefaultAdministrationManagerTest.userContext);
			assertTrue("IMdoBean must be instance of " + DinnerTableDto.class, loadedBean instanceof DinnerTableDto);
			updatedBean = (DinnerTableDto) loadedBean;
			assertNotNull("DinnerTableDto number must not be null", updatedBean.getNumber());
			assertEquals("Check DinnerTableDto number", number, updatedBean.getNumber());
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	@Override
	protected void doProcessList() {
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof IDinnerTablesManager);
		assertTrue(this.getInstance() instanceof DefaultDinnerTablesManager);
	}

	private IMdoDtoBean createNewBean(RestaurantDto restaurant, String number,
			Date cashingDate, UserAuthenticationDto user, Long rooId,
			Integer customersNumber, BigDecimal quantitiesSum,
			BigDecimal amountsSum, BigDecimal reductionRatio,
			BigDecimal amountPay, Date registrationDate, Date printingDate,
			Boolean reductionRatioChanged, TableTypeDto type,
			Set<OrderLineDto> orders, Set<TableBillDto> bills,
			Set<TableCreditDto> credits, Set<TableVatDto> vats,
			Set<TableCashingDto> cashings) {
		DinnerTableDto newBean = new DinnerTableDto();
		newBean.setRestaurant(restaurant);
		newBean.setNumber(number);
		newBean.setCashingDate(cashingDate);
		newBean.setUser(user);
		newBean.setRoo_id(rooId);
		newBean.setCustomersNumber(customersNumber);
		newBean.setQuantitiesSum(quantitiesSum);
		newBean.setAmountsSum(amountsSum);
		newBean.setReductionRatio(reductionRatio);
		newBean.setAmountPay(amountPay);
		newBean.setRegistrationDate(registrationDate);
		newBean.setPrintingDate(printingDate);
		newBean.setReductionRatioChanged(reductionRatioChanged);
		newBean.setType(type);
		newBean.setOrders(orders);
		newBean.setBills(bills);
		newBean.setCredits(credits);
		newBean.setVats(vats);
		newBean.setCashings(cashings);
		return newBean;
	}
}
