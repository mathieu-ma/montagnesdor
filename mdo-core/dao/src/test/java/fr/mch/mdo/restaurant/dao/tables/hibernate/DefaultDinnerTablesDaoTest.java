package fr.mch.mdo.restaurant.dao.tables.hibernate;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.DaoServicesFactory;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.CashingType;
import fr.mch.mdo.restaurant.dao.beans.Credit;
import fr.mch.mdo.restaurant.dao.beans.DinnerTable;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.dao.beans.OrderLine;
import fr.mch.mdo.restaurant.dao.beans.Product;
import fr.mch.mdo.restaurant.dao.beans.ProductSpecialCode;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.beans.TableBill;
import fr.mch.mdo.restaurant.dao.beans.TableCashing;
import fr.mch.mdo.restaurant.dao.beans.TableCredit;
import fr.mch.mdo.restaurant.dao.beans.TableType;
import fr.mch.mdo.restaurant.dao.beans.TableVat;
import fr.mch.mdo.restaurant.dao.beans.UserAuthentication;
import fr.mch.mdo.restaurant.dao.beans.ValueAddedTax;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServicesTestCase;
import fr.mch.mdo.restaurant.dao.tables.IDinnerTablesDao;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultDinnerTablesDaoTest extends DefaultDaoServicesTestCase
{
	@Override
	protected void loadFiles(Connection connection, String sqlDialectName, URL[] fileURLs) {
		/**
		 * For testing without loading data then comment the next instruction
		 **/
//		fileURLs = new URL[] { ITestResources.class.getResource("montagnesdorStructure.sql"),
//				ITestResources.class.getResource("DataMigrationKimsan93.sql"),
//				ITestResources.class.getResource("DataMigrationProductWork.sql"),
//				ITestResources.class.getResource("DataMigrationProductCategoryWork.sql"),
//				ITestResources.class.getResource("DataMigrationDinnerTableWork.sql"),
//				ITestResources.class.getResource("DataMigrationRevenueWork.sql"),
//				ITestResources.class.getResource("DataMigrationProductSoldWork.sql"), // Will take a very long time
//		};
		super.loadFiles(connection, sqlDialectName, fileURLs);
//		assertTrue(false);
	}

	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultDinnerTablesDaoTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultDinnerTablesDaoTest.class);
	}

	protected IDaoServices getInstance() {
		return DefaultDinnerTablesDao.getInstance();
	}

	protected IMdoBean createNewBean() {
		Restaurant restaurant = new Restaurant();
		restaurant.setId(1L);
		String number = "31";
		UserAuthentication user = new UserAuthentication();
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
		TableType type = new TableType();
		type.setId(1L);
		Set<OrderLine> orders = new HashSet<OrderLine>();
		Set<TableBill> bills = null;
		Set<TableCredit> credits = null;
		Set<TableVat> vats = null;
		TableCashing cashing = new TableCashing();
		cashing.setCashingDate(new Date());
		CashingType cashingType = new CashingType();
		cashingType.setAmount(amountPay);
		MdoTableAsEnum cashType = new MdoTableAsEnum();
		cashType.setId(1L);
		cashingType.setType(cashType);
		cashing.addCashingType(cashingType);
		DinnerTable dinnerTable = (DinnerTable) createNewBean(restaurant,
				number, user, rooId, customersNumber,
				quantitiesSum, amountsSum, reductionRatio, amountPay,
				registrationDate, printingDate, reductionRatioChanged, type,
				orders, bills, credits, vats, cashing);

		OrderLine orderLine = new OrderLine();
		orderLine.setQuantity(BigDecimal.ONE);
		orderLine.setLabel("Label");
		orderLine.setUnitPrice(BigDecimal.TEN);
		orderLine.setAmount(BigDecimal.TEN);
		ProductSpecialCode productSpecialCode = new ProductSpecialCode();
		productSpecialCode.setId(1L);
		orderLine.setProductSpecialCode(productSpecialCode);
		dinnerTable.addOrderLine(orderLine);

		return dinnerTable;
	}

	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		Restaurant restaurant = new Restaurant();
		try {
			restaurant = (Restaurant) DaoServicesFactory.getRestaurantsDao()
					.findByPrimaryKey(1L);
		} catch (MdoException e) {
			fail("Could not found the Restaurant.");
		}
		String number = "2";
		UserAuthentication user = new UserAuthentication();
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
		TableType type = new TableType();
		type.setId(2L);
		Set<OrderLine> orders = null;
		Set<TableBill> bills = null;
		Set<TableCredit> credits = null;
		Set<TableVat> vats = null;
		TableCashing cashing = null;
		list.add(createNewBean(restaurant, number, user, rooId,
				customersNumber, quantitiesSum, amountsSum, reductionRatio,
				amountPay, registrationDate, printingDate,
				reductionRatioChanged, type, orders, bills, credits, vats,
				cashing));
		return list;
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof IDinnerTablesDao);
		assertTrue(this.getInstance() instanceof DefaultDinnerTablesDao);
	}

	@Override
	public void doFindByUniqueKey() {
		// 12 number was created at HSQLDB startup
		Long restaurantId = 1L;
		String number = "12";
		try {
			for (int i = 0; i < 2; i++) {
				IMdoBean bean = null;
				if (i == 0) {
					bean = ((IDinnerTablesDao) this.getInstance()).findByNumber(restaurantId, number);
				} else {
					List<IMdoBean> list = ((IDinnerTablesDao) this.getInstance()).findAllByPrefixNumber(restaurantId, "1");
					assertNotNull("Check List of IMdoBean", list);
					assertEquals("Check List of IMdoBean size", 1, list.size());
					bean = list.get(0);
				}
				assertTrue("IMdoBean must be instance of " + DinnerTable.class, bean instanceof DinnerTable);
				DinnerTable castedBean = (DinnerTable) bean;
				assertNotNull("DinnerTable number must not be null", castedBean.getNumber());
				assertEquals("DinnerTable number must be equals to the lookup one", number, castedBean.getNumber());
				assertNotNull("DinnerTable orders not null", castedBean.getOrders());
				assertEquals("DinnerTable orders size equals", 2, castedBean.getOrders().size());
				assertNotNull("DinnerTable TableBills not null", castedBean.getBills());
				assertEquals("DinnerTable TableBills size equals", 1, castedBean.getBills().size());
				assertNotNull("DinnerTable Credits not null", castedBean.getCredits());
				assertEquals("DinnerTable Credits size equals", 1, castedBean.getCredits().size());
				assertNotNull("DinnerTable Vats not null", castedBean.getVats());
				assertEquals("DinnerTable Vats size equals", 1, castedBean.getVats().size());
				assertNull("DinnerTable Cashing null", castedBean.getCashing());

				assertFalse("DinnerTable must not be deleted", castedBean.isDeleted());
			}
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	@Override
	public void doUpdate() {
		DinnerTable newBean = null;
		Restaurant restaurant = new Restaurant();
		restaurant.setId(1L);
		String number = "1";
		UserAuthentication user = new UserAuthentication();
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
		TableType type = new TableType();
		type.setId(1L);
		Set<OrderLine> orders = new HashSet<OrderLine>();
		Set<TableBill> bills = new HashSet<TableBill>();
		Set<TableCredit> credits = new HashSet<TableCredit>();
		Set<TableVat> vats = new HashSet<TableVat>();
		TableCashing cashing = new TableCashing();
		cashing.setCashingDate(new Date());

		newBean = (DinnerTable) createNewBean(restaurant, number,
				user, rooId, customersNumber, quantitiesSum, amountsSum,
				reductionRatio, amountPay, registrationDate, printingDate,
				reductionRatioChanged, type, orders, bills, credits, vats,
				cashing);
		OrderLine orderLine = new OrderLine();
		orderLine.setQuantity(BigDecimal.ONE);
		orderLine.setLabel("Label 1");
		orderLine.setUnitPrice(BigDecimal.TEN);
		orderLine.setAmount(BigDecimal.TEN);
		ProductSpecialCode productSpecialCode = new ProductSpecialCode();
		productSpecialCode.setId(1L);
		orderLine.setProductSpecialCode(productSpecialCode);
		newBean.addOrderLine(orderLine);
		TableBill bill = new TableBill();
		bill.setAmount(BigDecimal.ONE);
		bill.setMealNumber(2);
		bill.setOrder(1);
		bill.setPrinted(Boolean.FALSE);
		bill.setReference("reference");
		newBean.addBill(bill);
		TableVat tableVat = new TableVat();
		tableVat.setAmount(BigDecimal.ONE);
		tableVat.setValue(BigDecimal.TEN);
		ValueAddedTax vat = new ValueAddedTax();
		vat.setId(1L);
		tableVat.setVat(vat);
		newBean.addTableVat(tableVat);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + DinnerTable.class, beanToBeUpdated instanceof DinnerTable);
			DinnerTable castedBean = (DinnerTable) beanToBeUpdated;
			assertNotNull("DinnerTable number must not be null", castedBean.getNumber());
			assertEquals("DinnerTable number must be equals to the inserted value", number, castedBean.getNumber());
			assertNotNull("DinnerTable cashing must not be null", castedBean.getCashing());
			assertNotNull("DinnerTable cashing Id must not be null", castedBean.getCashing().getId());
			assertFalse("DinnerTable must not be deleted", castedBean.isDeleted());

			// Update the created bean
			castedBean.setDeleted(true);
			
			castedBean.getOrders().clear();
			orderLine = new OrderLine();
			orderLine.setQuantity(BigDecimal.ONE);
			orderLine.setLabel("Label 2");
			orderLine.setUnitPrice(BigDecimal.TEN);
			orderLine.setAmount(BigDecimal.TEN);
			productSpecialCode = new ProductSpecialCode();
			productSpecialCode.setId(1L);
			orderLine.setProductSpecialCode(productSpecialCode);
			castedBean.addOrderLine(orderLine);

			bill = new TableBill();
			bill.setAmount(BigDecimal.ONE);
			bill.setMealNumber(2);
			bill.setOrder(2);
			bill.setPrinted(Boolean.FALSE);
			bill.setReference("reference");
			newBean.addBill(bill);

			TableCredit tableCredit = new TableCredit();
			Credit credit = new Credit();
			credit.setId(1L);
			tableCredit.setCredit(credit);
			newBean.addCredit(tableCredit);

			castedBean.setCashing(null);

			this.getInstance().update(castedBean);
			// Reload the modified bean
			DinnerTable updatedBean = new DinnerTable();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertNotNull("DinnerTable number must not be null", updatedBean.getNumber());
			assertEquals("DinnerTable number must be equals to the updated value", number, updatedBean.getNumber());
			assertEquals("Check DinnerTable order line size", 1, updatedBean.getOrders().size());
			assertEquals("Check DinnerTable bill size", 2, updatedBean.getBills().size());
			assertEquals("Check DinnerTable credit size", 1, updatedBean.getCredits().size());
			assertEquals("Check DinnerTable vat size", 1, updatedBean.getVats().size());
			assertNull("DinnerTable cashing must be null", castedBean.getCashing());
			assertTrue("DinnerTable must be deleted", castedBean.isDeleted());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}
	
	@Override
	public void doUpdateFieldsAndDeleteByKeysSpecific() {
		DinnerTable newBean = null;
		Restaurant restaurant = new Restaurant();
		restaurant.setId(1L);
		String number = "1";
		UserAuthentication user = new UserAuthentication();
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
		TableType type = new TableType();
		type.setId(1L);
		Set<OrderLine> orders = new HashSet<OrderLine>();
		Set<TableBill> bills = new HashSet<TableBill>();
		Set<TableCredit> credits = new HashSet<TableCredit>();
		Set<TableVat> vats = new HashSet<TableVat>();
		TableCashing cashing = new TableCashing();
		cashing.setCashingDate(new Date());

		newBean = (DinnerTable) createNewBean(restaurant, number,
				user, rooId, customersNumber, quantitiesSum, amountsSum,
				reductionRatio, amountPay, registrationDate, printingDate,
				reductionRatioChanged, type, orders, bills, credits, vats,
				cashing);
		OrderLine orderLine = new OrderLine();
		orderLine.setQuantity(BigDecimal.ONE);
		orderLine.setLabel("Label 1");
		orderLine.setUnitPrice(BigDecimal.TEN);
		orderLine.setAmount(BigDecimal.TEN);
		ProductSpecialCode productSpecialCode = new ProductSpecialCode();
		productSpecialCode.setId(1L);
		orderLine.setProductSpecialCode(productSpecialCode);
		newBean.addOrderLine(orderLine);
		TableBill bill = new TableBill();
		bill.setAmount(BigDecimal.ONE);
		bill.setMealNumber(2);
		bill.setOrder(1);
		bill.setPrinted(Boolean.FALSE);
		bill.setReference("reference");
		newBean.addBill(bill);
		TableVat tableVat = new TableVat();
		tableVat.setAmount(BigDecimal.ONE);
		tableVat.setValue(BigDecimal.TEN);
		ValueAddedTax vat = new ValueAddedTax();
		vat.setId(1L);
		tableVat.setVat(vat);
		newBean.addTableVat(tableVat);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + DinnerTable.class, beanToBeUpdated instanceof DinnerTable);
			DinnerTable castedBean = (DinnerTable) beanToBeUpdated;
			assertNotNull("DinnerTable number must not be null", castedBean.getNumber());
			assertEquals("DinnerTable number must be equals to the inserted value", number, castedBean.getNumber());
			assertNotNull("DinnerTable cashing must not be null", castedBean.getCashing());
			assertNotNull("DinnerTable cashing Id must not be null", castedBean.getCashing().getId());
			assertFalse("DinnerTable must not be deleted", castedBean.isDeleted());

			// Update the created bean
			Map<String, Object> fields = new HashMap<String, Object>();
			Map<String, Object> keys = new HashMap<String, Object>();
			castedBean.setAmountPay(amountPay.add(BigDecimal.ONE));
			castedBean.setAmountsSum(amountsSum.add(BigDecimal.ONE));
			castedBean.setCustomersNumber(1);
			castedBean.setNumber("NUM");
			castedBean.setPrintingDate(new Date());
			castedBean.setQuantitiesSum(quantitiesSum.add(BigDecimal.ONE));
			castedBean.setReductionRatio(reductionRatio.add(BigDecimal.ONE));
			castedBean.setReductionRatioChanged(!reductionRatioChanged);
			fields.put("amountPay", castedBean.getAmountPay());
			fields.put("amountsSum", castedBean.getAmountsSum());
			fields.put("customersNumber", castedBean.getCustomersNumber());
			fields.put("number", castedBean.getNumber());
			fields.put("printingDate", castedBean.getPrintingDate());
			fields.put("quantitiesSum", castedBean.getQuantitiesSum());
			fields.put("reductionRatio", castedBean.getReductionRatio());
			fields.put("reductionRatioChanged", castedBean.getReductionRatioChanged());
			keys.put("id", castedBean.getId());
			this.getInstance().updateFieldsByKeys(fields, keys);
			// Reload the modified bean
			DinnerTable updatedBean = (DinnerTable) createNewBean();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertEquals("Check updated fields ", castedBean.getAmountPay(), updatedBean.getAmountPay());
			assertEquals("Check updated fields ", castedBean.getAmountsSum(), updatedBean.getAmountsSum());
			assertEquals("Check updated fields ", castedBean.getCustomersNumber(), updatedBean.getCustomersNumber());
			assertEquals("Check updated fields ", castedBean.getNumber(), updatedBean.getNumber());
			assertEquals("Check updated fields ", castedBean.getPrintingDate(), updatedBean.getPrintingDate());
			assertEquals("Check updated fields ", castedBean.getQuantitiesSum(), updatedBean.getQuantitiesSum());
			assertEquals("Check updated fields ", castedBean.getReductionRatio(), updatedBean.getReductionRatio());
			assertEquals("Check updated fields ", castedBean.getReductionRatioChanged(), updatedBean.getReductionRatioChanged());

			// Delete the bean by keys
			// Take the fields as keys
			try {
				super.doDeleteByKeysSpecific(updatedBean, keys, true);
			} catch (Exception e) {
				// We Have to delete following tables in the following order deleting the table t_dinner_table.
				// 1) t_table_credit
				// 2) t_table_bill
				// 3) t_table_vat
				// 4) t_order_line
				// 5) t_table_cashing
				// 6) t_cashing_type
				Object parentId = keys.get("id");
				Map<String, Object> childrenKeys = new HashMap<String, Object>();
				childrenKeys.put("dinnerTable.id", parentId);
				super.doDeleteByKeysSpecific(TableBill.class, childrenKeys);
				super.doDeleteByKeysSpecific(TableVat.class, childrenKeys);
				super.doDeleteByKeysSpecific(OrderLine.class, childrenKeys);
				super.doDeleteByKeysSpecific(TableCashing.class, childrenKeys);
				super.doDeleteByKeysSpecific(updatedBean, keys);
			}
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
		}
	}

	public void testUpdateReductionRatio() {
		IDinnerTablesDao iDinnerTablesDao = (IDinnerTablesDao) this.getInstance();
		try {
			Long dinnerTableId = Long.valueOf(1);
			DinnerTable dinnerTable = (DinnerTable) iDinnerTablesDao.findByPrimaryKey(dinnerTableId);
			assertEquals("Check Dinner Table's reduction ratio initial value", super.decimalFormat.format(BigDecimal.ZERO), super.decimalFormat.format(dinnerTable.getReductionRatio()));
			assertEquals("Check Dinner Table's amount pay initial value", super.decimalFormat.format(BigDecimal.valueOf(23)), super.decimalFormat.format(dinnerTable.getAmountPay()));
			assertFalse("Check Dinner Table's reduction ratio changed initial value", dinnerTable.getReductionRatioChanged());
			BigDecimal reductionRatio = new BigDecimal(25.123);
			Boolean reductionRatioChanged = Boolean.TRUE;
			BigDecimal amountPay = new BigDecimal(25.012);
			iDinnerTablesDao.updateReductionRatio(dinnerTableId, reductionRatio, reductionRatioChanged, amountPay);
			DinnerTable updatedDinnerTable = (DinnerTable) iDinnerTablesDao.findByPrimaryKey(dinnerTableId);
			assertEquals("Check Dinner Table's reduction ratio updated value", super.decimalFormat.format(reductionRatio), super.decimalFormat.format(updatedDinnerTable.getReductionRatio()));
			assertEquals("Check Dinner Table's amount pay updated value", super.decimalFormat.format(amountPay), super.decimalFormat.format(updatedDinnerTable.getAmountPay()));
			assertTrue("Check Dinner Table's reduction ratio changed updated value", updatedDinnerTable.getReductionRatioChanged());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	private IMdoBean createNewBean(Restaurant restaurant, String number,
			UserAuthentication user, Long rooId,
			Integer customersNumber, BigDecimal quantitiesSum,
			BigDecimal amountsSum, BigDecimal reductionRatio,
			BigDecimal amountPay, Date registrationDate, Date printingDate,
			Boolean reductionRatioChanged, TableType type,
			Set<OrderLine> orders, Set<TableBill> bills,
			Set<TableCredit> credits, Set<TableVat> vats,
			TableCashing cashing) {

		DinnerTable newBean = new DinnerTable();
		newBean.setRestaurant(restaurant);
		newBean.setNumber(number);
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
		newBean.setCashing(cashing);

		return newBean;
	}

	public void testFindCustomersNumberByNumber() {
		IDinnerTablesDao iDinnerTablesDao = (IDinnerTablesDao) this.getInstance();
		Long restaurantId = Long.valueOf(1);
		String number = "12";
		Integer customersNumber = null;
		try {
			customersNumber = iDinnerTablesDao.findCustomersNumberByNumber(restaurantId, number);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		assertNotNull("Customer Number found", customersNumber);
	}

	public void testIsDinnerTableFree() {
		IDinnerTablesDao iDinnerTablesDao = (IDinnerTablesDao) this.getInstance();
		Long restaurantId = Long.valueOf(1);
		String number = "12";
		boolean isDinnerTableFree = true;
		try {
			isDinnerTableFree = iDinnerTablesDao.isDinnerTableFree(restaurantId, number);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		assertFalse("DinnerTable is not Free", isDinnerTableFree);
	}

	public void testAddUpdateFindRemoveOrderLine() {
		IDinnerTablesDao iDinnerTablesDao = (IDinnerTablesDao) this.getInstance();
		OrderLine orderLine = new OrderLine();
		DinnerTable dinnerTable = new DinnerTable();
		dinnerTable.setId(Long.valueOf(1));
		orderLine.setDinnerTable(dinnerTable);
		ProductSpecialCode psc = new ProductSpecialCode();
		psc.setId(Long.valueOf(1));
		orderLine.setProductSpecialCode(psc);
		Product product = new Product();
		product.setId(Long.valueOf(1));
		orderLine.setProduct(product);
		orderLine.setQuantity(new BigDecimal(2));
		orderLine.setLabel("Test");
		orderLine.setUnitPrice(new BigDecimal(2.1));
		orderLine.setAmount(orderLine.getQuantity().multiply(orderLine.getUnitPrice()));

		try {
			assertNull("Order Line id must be null", orderLine.getId());
			iDinnerTablesDao.addOrderLine(orderLine);
			assertNotNull("Order Line id must not be null", orderLine.getId());
			orderLine.setLabel("Test Update");
			iDinnerTablesDao.updateOrderLine(orderLine);
			OrderLine foundOrderLine = iDinnerTablesDao.findOrderLine(orderLine.getId());
			assertNotNull("Order Line must not be null", foundOrderLine);
			assertEquals("Order Line label updated", orderLine.getLabel(), foundOrderLine.getLabel());
			iDinnerTablesDao.removeOrderLine(orderLine);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		try {
			iDinnerTablesDao.findOrderLine(orderLine.getId());
		} catch (MdoException e) {
			assertTrue("Exception because bean not found after deletion", true);
		}
	}
	
	public void testLookupTablesNamesByPrefixNumber() {
		IDinnerTablesDao iDinnerTablesDao = (IDinnerTablesDao) this.getInstance();
		Long restaurantId = Long.valueOf(1);
		String prefixTableNumber = "1";
		try {
			Map<Long, String> result = iDinnerTablesDao.findAllTableNamesByPrefix(restaurantId, prefixTableNumber);
			assertNotNull("Check that there are tables found", result);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}
	
	public void testFindAllFreeTables() {
		IDinnerTablesDao iDinnerTablesDao = (IDinnerTablesDao) this.getInstance();
		Long restaurantId = Long.valueOf(1);
		try {
			List<IMdoBean> result = iDinnerTablesDao.findAllFreeTables(restaurantId);
			assertNotNull("Check that there are tables found", result);
			assertFalse("Check that there are tables found", result.isEmpty());
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}
	
	public void testUpdateCustomersNumber() {
		IDinnerTablesDao iDinnerTablesDao = (IDinnerTablesDao) this.getInstance();
		try {
			Long dinnerTableId = Long.valueOf(1);
			// The value 2 is in the file montagnesdorDatas.sql
			Integer customersNumber = new Integer(2);
			DinnerTable dinnerTable = (DinnerTable) iDinnerTablesDao.findByPrimaryKey(dinnerTableId);
			assertEquals("Check Dinner Table's Customers Number initial value", customersNumber, dinnerTable.getCustomersNumber());
			customersNumber = new Integer(6);
			iDinnerTablesDao.updateCustomersNumber(dinnerTableId, customersNumber);
			DinnerTable updatedDinnerTable = (DinnerTable) iDinnerTablesDao.findByPrimaryKey(dinnerTableId);
			assertEquals("Check Dinner Table's Customers Number updated value", customersNumber, updatedDinnerTable.getCustomersNumber());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}
	
	public void testGetOrderLinesSize() {
		IDinnerTablesDao iDinnerTablesDao = (IDinnerTablesDao) this.getInstance();
		try {
			Long dinnerTableId = Long.valueOf(1);
			int size = iDinnerTablesDao.getOrderLinesSize(dinnerTableId);
			assertTrue("Check Dinner Table's Order Lines Size initial value", size>0);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}
}
