package fr.mch.mdo.restaurant.dao.tables.hibernate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.DaoServicesFactory;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.MdoTableAsEnumTypeDao;
import fr.mch.mdo.restaurant.dao.IMdoTableAsEnumsDao.TableCashingTypeName;
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
import fr.mch.mdo.restaurant.dao.table.orders.IDinnerTablesDao;
import fr.mch.mdo.restaurant.exception.MdoException;

public class DefaultDinnerTablesDaoTest extends DefaultDaoServicesTestCase 
{
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
	String number = "1";
	Date cashingDate = Calendar.getInstance().getTime();
	UserAuthentication user = new UserAuthentication();
	user.setId(1L);
	Long rooId  = 1L;
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
	Set<TableCashing> cashings = new HashSet<TableCashing>();
	DinnerTable dinnerTable = (DinnerTable) createNewBean(restaurant, number, cashingDate, user,
		    rooId, customersNumber, quantitiesSum, amountsSum, reductionRatio,
		    amountPay, registrationDate, printingDate, reductionRatioChanged, type,
		    orders, bills, credits, vats, cashings);
	
	OrderLine orderLine = new OrderLine();
	orderLine.setQuantity(BigDecimal.ONE);
	orderLine.setLabel("Label");
	orderLine.setUnitPrice(BigDecimal.TEN);
	orderLine.setAmount(BigDecimal.TEN);
	ProductSpecialCode productSpecialCode = new ProductSpecialCode();
	productSpecialCode.setId(1L);
	orderLine.setProductSpecialCode(productSpecialCode);
	dinnerTable.addOrderLine(orderLine);
	
	TableCashing cashing = new TableCashing();
	MdoTableAsEnum cashingType = null;
	try {
	    cashingType = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByUniqueKey(new Object[] {MdoTableAsEnumTypeDao.CASHING_TYPE.name(), TableCashingTypeName.BNP_CHECK.name()});
	} catch (MdoException e) {
	    fail("Could not found the cashing type.");
	}
	cashing.setType(cashingType);
	cashing.setValue(BigDecimal.TEN);
	dinnerTable.addCashing(cashing);
	
	return dinnerTable;
    }

    protected List<IMdoBean> createListBeans() {
	List<IMdoBean> list = new ArrayList<IMdoBean>();
	Restaurant restaurant = new Restaurant();
	try {
	    restaurant = (Restaurant) DaoServicesFactory.getRestaurantsDao().findByPrimaryKey(1L);
	} catch (MdoException e) {
	    fail("Could not found the Restaurant.");
	}
	String number = "2";
	Timestamp cashingDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
	UserAuthentication user = new UserAuthentication();
	user.setId(1L);
	Long rooId  = 1L;
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
	Set<TableCashing> cashings = new HashSet<TableCashing>();
	list.add(createNewBean(restaurant, number, cashingDate, user,
		    rooId, customersNumber, quantitiesSum, amountsSum, reductionRatio,
		    amountPay, registrationDate, printingDate, reductionRatioChanged, type,
		    orders, bills, credits, vats, cashings));
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
	Date cashingDate = null;
	try {
	    for (int i = 0; i < 3; i++) {
		IMdoBean bean = null;
		if (i == 0) {
		    bean = ((IDinnerTablesDao) this.getInstance()).findByUniqueKey(restaurantId, number, cashingDate);
		} else if (i == 1) {
		    bean = ((IDinnerTablesDao) this.getInstance()).findByNumber(restaurantId, number);
		} else {
		    List<IMdoBean> list = ((IDinnerTablesDao) this.getInstance()).findAllByPrefixNumber(restaurantId, "1%");
		    assertNotNull("Check List of IMdoBean", list);
		    assertEquals("Check List of IMdoBean size", 1, list.size());
		    bean = list.get(0);
		}
		assertTrue("IMdoBean must be instance of " + DinnerTable.class, bean instanceof DinnerTable);
		DinnerTable castedBean = (DinnerTable) bean;
		assertEquals("DinnerTable restaurantId must be equals to the lookup one", restaurantId, castedBean.getRestaurant().getId());
		assertNotNull("DinnerTable number must not be null", castedBean.getNumber());
		assertEquals("DinnerTable number must be equals to the lookup one", number, castedBean.getNumber());
		assertNull("DinnerTable cashingDate must be null", castedBean.getCashingDate());
		assertNotNull("DinnerTable orders not null", castedBean.getOrders());
		assertEquals("DinnerTable orders size equals", 1, castedBean.getOrders().size());
		assertNotNull("DinnerTable TableBills not null", castedBean.getBills());
		assertEquals("DinnerTable TableBills size equals", 1, castedBean.getBills().size());
		assertNotNull("DinnerTable Credits not null", castedBean.getCredits());
		assertEquals("DinnerTable Credits size equals", 1, castedBean.getCredits().size());
		assertNotNull("DinnerTable Vats not null", castedBean.getVats());
		assertEquals("DinnerTable Vats size equals", 1, castedBean.getVats().size());
		assertNotNull("DinnerTable Cashings not null", castedBean.getCashings());
		assertEquals("DinnerTable Cashings size equals", 1, castedBean.getCashings().size());
		    
		assertFalse("DinnerTable must not be deleted", castedBean.isDeleted());
	    }
	}
	catch (Exception e) {
	    fail(e.getMessage());
	}
    }

    @Override
    public void doUpdate() {
	DinnerTable newBean = null;
	Restaurant restaurant = new Restaurant();
	restaurant.setId(1L);
	String number = "1";
	Date cashingDate = null;
	UserAuthentication user = new UserAuthentication();
	user.setId(1L);
	Long rooId  = 1L;
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
	Set<TableCashing> cashings = new HashSet<TableCashing>();
	
	newBean = (DinnerTable) createNewBean(restaurant, number, cashingDate, user,
		    rooId, customersNumber, quantitiesSum, amountsSum, reductionRatio,
		    amountPay, registrationDate, printingDate, reductionRatioChanged, type,
		    orders, bills, credits, vats, cashings);
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
	    
	    TableCredit tableCredit  = new TableCredit();
	    Credit credit = new Credit();
	    credit.setId(1L);
	    tableCredit.setCredit(credit);
	    newBean.addCredit(tableCredit);
	    
	    TableCashing cashing = new TableCashing();
	    MdoTableAsEnum cashingType = null;
	    try {
		cashingType = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByUniqueKey(new Object[] {MdoTableAsEnumTypeDao.CASHING_TYPE.name(), TableCashingTypeName.GENERIC_CARD.name()});
	    } catch (MdoException e) {
		fail("Could not found the cashing type.");
	    }
	    cashing.setType(cashingType);
	    cashing.setValue(BigDecimal.TEN);
	    newBean.addCashing(cashing);

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
	    assertEquals("Check DinnerTable cashing size", 1, updatedBean.getCashings().size());
	    assertEquals("Check DinnerTable cashing type", MdoTableAsEnumTypeDao.CASHING_TYPE.name(), updatedBean.getCashings().iterator().next().getType().getType());
	    assertTrue("DinnerTable must be deleted", castedBean.isDeleted());
	    this.getInstance().delete(updatedBean);
	}
	catch (Exception e) {
	    fail(e.getMessage());
	}
    }

    private IMdoBean createNewBean(Restaurant restaurant, String number, Date cashingDate, UserAuthentication user,
	    Long rooId, Integer customersNumber, BigDecimal quantitiesSum, BigDecimal amountsSum, BigDecimal reductionRatio,
	    BigDecimal amountPay, Date registrationDate, Date printingDate, Boolean reductionRatioChanged, TableType type,
	    Set<OrderLine> orders, Set<TableBill> bills, Set<TableCredit> credits, Set<TableVat> vats, Set<TableCashing> cashings) {

	DinnerTable newBean = new DinnerTable();
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
