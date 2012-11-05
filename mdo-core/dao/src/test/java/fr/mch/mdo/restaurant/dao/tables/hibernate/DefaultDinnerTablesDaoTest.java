package fr.mch.mdo.restaurant.dao.tables.hibernate;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
		List<URL> fileURLsList = new ArrayList<URL>(Arrays.asList(fileURLs));
//		fileURLsList.add(ITestResources.class.getResource("montagnesdorDatas-dao.sql"));
		super.loadFiles(connection, sqlDialectName, fileURLsList.toArray(fileURLs));
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
		ValueAddedTax vat = new ValueAddedTax();
		vat.setId(1L);
		orderLine.setVat(vat);
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
		Long id = 1L;
		try {
			for (int i = 0; i < 2; i++) {
				IMdoBean bean = null;
				if (i == 0) {
					bean = ((IDinnerTablesDao) this.getInstance()).findTable(id);
				} else {
					List<DinnerTable> list = ((IDinnerTablesDao) this.getInstance()).findAllByPrefixNumber(restaurantId, "1");
					assertNotNull("Check List of IMdoBean", list);
					assertEquals("Check List of IMdoBean size", 2, list.size());
					bean = list.get(0);
				}
				assertTrue("IMdoBean must be instance of " + DinnerTable.class, bean instanceof DinnerTable);
				DinnerTable castedBean = (DinnerTable) bean;
				assertNotNull("DinnerTable number must not be null", castedBean.getNumber());
				
				if (i > 0) {
					assertNotNull("DinnerTable orders not null", castedBean.getOrders());
					assertEquals("DinnerTable orders size equals", 3, castedBean.getOrders().size());
					assertNotNull("DinnerTable TableBills not null", castedBean.getBills());
					assertEquals("DinnerTable TableBills size equals", 1, castedBean.getBills().size());
					assertNotNull("DinnerTable Credits not null", castedBean.getCredits());
					assertEquals("DinnerTable Credits size equals", 1, castedBean.getCredits().size());
					assertNotNull("DinnerTable Vats not null", castedBean.getVats());
					assertEquals("DinnerTable Vats size equals", 1, castedBean.getVats().size());
					assertNull("DinnerTable Cashing null", castedBean.getCashing());
				}

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
		ValueAddedTax vat = new ValueAddedTax();
		vat.setId(1L);
		orderLine.setVat(vat);
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
			orderLine.setVat(vat);
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
		ValueAddedTax vat = new ValueAddedTax();
		vat.setId(1L);
		orderLine.setVat(vat);
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
			BigDecimal reductionRatio = BigDecimal.ZERO, reductionRatioBackup = dinnerTable.getReductionRatio();
			BigDecimal amountPay = new BigDecimal(23), amountPayBackup = dinnerTable.getAmountPay();
			Boolean reductionRatioChanged = Boolean.FALSE, reductionRatioChangedBackup = dinnerTable.getReductionRatioChanged();
			assertEquals("Check Dinner Table's reduction ratio initial value", super.decimalFormat.format(reductionRatio), super.decimalFormat.format(dinnerTable.getReductionRatio()));
			assertEquals("Check Dinner Table's amount pay initial value", super.decimalFormat.format(amountPay), super.decimalFormat.format(dinnerTable.getAmountPay()));
			assertEquals("Check Dinner Table's reduction ratio changed initial value", reductionRatioChanged, dinnerTable.getReductionRatioChanged());
			reductionRatio = new BigDecimal(25.123);
			amountPay = new BigDecimal(25.012);
			reductionRatioChanged = Boolean.TRUE;
			iDinnerTablesDao.updateReductionRatio(dinnerTableId, reductionRatio, reductionRatioChanged, amountPay);
			DinnerTable updatedDinnerTable = (DinnerTable) iDinnerTablesDao.findByPrimaryKey(dinnerTableId);
			assertEquals("Check Dinner Table's reduction ratio updated value", super.decimalFormat.format(reductionRatio), super.decimalFormat.format(updatedDinnerTable.getReductionRatio()));
			assertEquals("Check Dinner Table's amount pay updated value", super.decimalFormat.format(amountPay), super.decimalFormat.format(updatedDinnerTable.getAmountPay()));
			assertTrue("Check Dinner Table's reduction ratio changed updated value", updatedDinnerTable.getReductionRatioChanged());
			// Restore old values
			iDinnerTablesDao.updateReductionRatio(dinnerTableId, reductionRatioBackup, reductionRatioChangedBackup, amountPayBackup);
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

	public void testFindTableHeader() {
		IDinnerTablesDao iDinnerTablesDao = (IDinnerTablesDao) this.getInstance();
		Long restaurantId = Long.valueOf(1);
		Long userAuthenticationId = Long.valueOf(1);
		String number = "12";
		DinnerTable table = null;
		try {
			table = iDinnerTablesDao.findTableHeader(restaurantId, number);
			assertNotNull("Table found", table);
			assertNotNull("Table Number found", table.getNumber());
			assertNotNull("Table Type found", table.getType());
			assertNotNull("Table Type Code found", table.getType().getCode());
			assertNotNull("Table Type Code Name found", table.getType().getCode().getName());
			table = iDinnerTablesDao.findTableHeader(restaurantId, userAuthenticationId, number);
			assertNotNull("Table found", table);
			assertNotNull("Table Number found", table.getNumber());
			assertNotNull("Table Type found", table.getType());
			assertNotNull("Table Type Code found", table.getType().getCode());
			assertNotNull("Table Type Code Name found", table.getType().getCode().getName());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
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

	
	public void testFindAllNumberByPrefixNumber() {
		IDinnerTablesDao iDinnerTablesDao = (IDinnerTablesDao) this.getInstance();
		Long restaurantId = Long.valueOf(1);
		Long userAuthenticationId = Long.valueOf(1);
		String prefixTableNumber = "1";
		try {
			Map<Long, String> result = iDinnerTablesDao.findAllNumberByPrefixNumber(restaurantId, prefixTableNumber);
			assertNotNull("Check that there are tables found", result);
			result = iDinnerTablesDao.findAllNumberByPrefixNumber(restaurantId, userAuthenticationId, prefixTableNumber);
			assertNotNull("Check that there are tables found", result);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}
	
	public void testFindAllCashed() {
		IDinnerTablesDao iDinnerTablesDao = (IDinnerTablesDao) this.getInstance();
		Long restaurantId = Long.valueOf(1);
		Long userAuthenticationId = Long.valueOf(1);
		try {
			List<DinnerTable> result = iDinnerTablesDao.findAllCashed(restaurantId);
			assertNotNull("Check that there are tables found", result);
			assertFalse("Check that there are tables found", result.isEmpty());
			result = iDinnerTablesDao.findAllCashed(restaurantId, userAuthenticationId);
			assertNotNull("Check that there are tables found", result);
			assertFalse("Check that there are tables found", result.isEmpty());
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	public void testFindAllPrinted() {
		IDinnerTablesDao iDinnerTablesDao = (IDinnerTablesDao) this.getInstance();
		Long restaurantId = Long.valueOf(1);
		Long userAuthenticationId = Long.valueOf(1);
		try {
			List<DinnerTable> result = iDinnerTablesDao.findAllPrinted(restaurantId);
			assertNotNull("Check that there are tables found", result);
			assertFalse("Check that there are tables found", result.isEmpty());
			result = iDinnerTablesDao.findAllPrinted(restaurantId, userAuthenticationId);
			assertNotNull("Check that there are tables found", result);
			assertFalse("Check that there are tables found", result.isEmpty());
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	public void testFindAllNotPrinted() {
		IDinnerTablesDao iDinnerTablesDao = (IDinnerTablesDao) this.getInstance();
		Long restaurantId = Long.valueOf(1);
		Long userAuthenticationId = Long.valueOf(1);
		try {
			List<DinnerTable> result = iDinnerTablesDao.findAllNotPrinted(restaurantId);
			assertNotNull("Check that there are tables found", result);
			assertFalse("Check that there are tables found", result.isEmpty());
			result = iDinnerTablesDao.findAllNotPrinted(restaurantId, userAuthenticationId);
			assertNotNull("Check that there are tables found", result);
			assertFalse("Check that there are tables found", result.isEmpty());
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	public void testFindAllAlterable() {
		IDinnerTablesDao iDinnerTablesDao = (IDinnerTablesDao) this.getInstance();
		Long restaurantId = Long.valueOf(1);
		Long userAuthenticationId = Long.valueOf(1);
		try {
			List<DinnerTable> result = iDinnerTablesDao.findAllAlterable(restaurantId);
			assertNotNull("Check that there are tables found", result);
			assertFalse("Check that there are tables found", result.isEmpty());
			result = iDinnerTablesDao.findAllAlterable(restaurantId, userAuthenticationId);
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
			Integer customersNumber = new Integer(2), customersNumberBackup = customersNumber;
			DinnerTable dinnerTable = (DinnerTable) iDinnerTablesDao.findByPrimaryKey(dinnerTableId);
			assertEquals("Check Dinner Table's Customers Number initial value", customersNumber, dinnerTable.getCustomersNumber());
			customersNumber = new Integer(6);
			iDinnerTablesDao.updateCustomersNumber(dinnerTableId, customersNumber);
			DinnerTable updatedDinnerTable = (DinnerTable) iDinnerTablesDao.findByPrimaryKey(dinnerTableId);
			assertEquals("Check Dinner Table's Customers Number updated value", customersNumber, updatedDinnerTable.getCustomersNumber());
			// Restore updated values
			iDinnerTablesDao.updateCustomersNumber(dinnerTableId, customersNumberBackup);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}
	
	public void testFindTable() {
		IDinnerTablesDao iDinnerTablesDao = (IDinnerTablesDao) this.getInstance();
		try {
			Long id = Long.valueOf(1);
			DinnerTable dinnerTable = iDinnerTablesDao.findTable(id);
			assertNotNull("Check Dinner Table", dinnerTable);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	public void testUpdateDerivedFieldsFromOrderLine() {
		IDinnerTablesDao iDinnerTablesDao = (IDinnerTablesDao) this.getInstance();
		try {
			Long dinnerTableId = Long.valueOf(1);
			DinnerTable dinnerTable = (DinnerTable) iDinnerTablesDao.findByPrimaryKey(dinnerTableId);
			// The following values are in the file montagnesdorDatas.sql
			BigDecimal quantitiesSum = new BigDecimal(3), quantitiesSumBackup = dinnerTable.getQuantitiesSum();
			BigDecimal amountsSum = new BigDecimal(23), amountsSumBackup = dinnerTable.getAmountsSum();
			BigDecimal amountPay = new BigDecimal(23), amountPayBackup = dinnerTable.getAmountPay();
			assertEquals("Check Dinner Table's quantitiesSum initial value", super.decimalFormat.format(quantitiesSum), super.decimalFormat.format(dinnerTable.getQuantitiesSum()));
			assertEquals("Check Dinner Table's amountsSum initial value", super.decimalFormat.format(amountsSum), super.decimalFormat.format(dinnerTable.getAmountsSum()));
			assertEquals("Check Dinner Table's amountPay initial value", super.decimalFormat.format(amountPay), super.decimalFormat.format(dinnerTable.getAmountPay()));
			quantitiesSum = quantitiesSum.add(new BigDecimal(3));
			amountsSum = amountsSum.add(new BigDecimal(23));
			amountPay = amountPay.add(new BigDecimal(23));
			iDinnerTablesDao.updateDerivedFieldsFromOrderLine(dinnerTableId, quantitiesSum, amountsSum, amountPay);
			DinnerTable updatedDinnerTable = (DinnerTable) iDinnerTablesDao.findByPrimaryKey(dinnerTableId);
			assertEquals("Check Dinner Table's quantitiesSum updated value", super.decimalFormat.format(quantitiesSum), super.decimalFormat.format(updatedDinnerTable.getQuantitiesSum()));
			assertEquals("Check Dinner Table's amountsSum updated value", super.decimalFormat.format(amountsSum), super.decimalFormat.format(updatedDinnerTable.getAmountsSum()));
			assertEquals("Check Dinner Table's amountPay updated value", super.decimalFormat.format(amountPay), super.decimalFormat.format(updatedDinnerTable.getAmountPay()));
			// Restore updated values
			iDinnerTablesDao.updateDerivedFieldsFromOrderLine(dinnerTableId, quantitiesSumBackup, amountsSumBackup, amountPayBackup);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	public void testGetReductionRatio() {
		IDinnerTablesDao iDinnerTablesDao = (IDinnerTablesDao) this.getInstance();
		try {
			Long dinnerTableId = Long.valueOf(1);
			BigDecimal expectedReductionRatio = new BigDecimal(0);
			BigDecimal reductionRatio = iDinnerTablesDao.getReductionRatio(dinnerTableId);
			assertEquals("Check Dinner Table's reductionRatio initial value", super.decimalFormat.format(expectedReductionRatio), super.decimalFormat.format(reductionRatio));
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}
	
	public void testResetTableCreationDateCustomersNumber() {
		IDinnerTablesDao iDinnerTablesDao = (IDinnerTablesDao) this.getInstance();
		try {
			Long dinnerTableId = Long.valueOf(1);
			DinnerTable dinnerTable = (DinnerTable) iDinnerTablesDao.findByPrimaryKey(dinnerTableId);
			Date registrationDateBackup = dinnerTable.getRegistrationDate();
			Integer customersNumberBackup = dinnerTable.getCustomersNumber();
			iDinnerTablesDao.resetTableCreationDateCustomersNumber(dinnerTableId);
			DinnerTable updatedDinnerTable = (DinnerTable) iDinnerTablesDao.findByPrimaryKey(dinnerTableId);
			DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
			assertEquals("Check Dinner Table's registrationDate updated value", df.format(new Date()), df.format(updatedDinnerTable.getRegistrationDate()));
			assertEquals("Check Dinner Table's customersNumber updated value", Integer.valueOf(0), updatedDinnerTable.getCustomersNumber());
			// Restore updated values
			updatedDinnerTable.setRegistrationDate(registrationDateBackup);
			updatedDinnerTable.setCustomersNumber(customersNumberBackup);
			iDinnerTablesDao.update(updatedDinnerTable);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}
	
}
