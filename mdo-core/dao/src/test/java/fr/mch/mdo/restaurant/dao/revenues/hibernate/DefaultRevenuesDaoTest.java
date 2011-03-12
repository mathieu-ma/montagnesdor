package fr.mch.mdo.restaurant.dao.revenues.hibernate;

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
import fr.mch.mdo.restaurant.dao.DaoServicesFactory;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.beans.Revenue;
import fr.mch.mdo.restaurant.dao.beans.RevenueCashing;
import fr.mch.mdo.restaurant.dao.beans.RevenueVat;
import fr.mch.mdo.restaurant.dao.beans.TableType;
import fr.mch.mdo.restaurant.dao.beans.ValueAddedTax;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServicesTestCase;
import fr.mch.mdo.restaurant.dao.revenues.IRevenuesDao;
import fr.mch.mdo.restaurant.exception.MdoException;

public class DefaultRevenuesDaoTest extends DefaultDaoServicesTestCase 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultRevenuesDaoTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultRevenuesDaoTest.class);
	}

	protected IDaoServices getInstance() {
		return DefaultRevenuesDao.getInstance();
	}

	protected IMdoBean createNewBean() {
		// Use the existing data in database
		Restaurant restaurant = null;
		try {
			restaurant = (Restaurant) DaoServicesFactory.getRestaurantsDao().findByPrimaryKey(1L);
		} catch (MdoException e) {
			fail("Could not found the restaurant.");
		}
		assertNotNull("The restaurant must not be null.", restaurant);
		Date revenueDate = new Date();
		TableType tableType = new TableType();
		tableType.setId(1L);
		Date printingDate = null;
		Date closingDate = null;
		BigDecimal amount = BigDecimal.ONE;
		Set<RevenueCashing> cashings = new HashSet<RevenueCashing>();
		Set<RevenueVat> vats = null;
		Revenue revenue = (Revenue) createNewBean(restaurant, revenueDate, tableType, printingDate, closingDate, amount, cashings, vats);
		RevenueCashing revenueCashing = new RevenueCashing();
		revenueCashing.setAmount(amount);
		MdoTableAsEnum type = new MdoTableAsEnum();
		type.setId(1L);
		revenueCashing.setType(type);
		revenue.addCashing(revenueCashing);
		return revenue;
	}

	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		// Use the existing data in database
		Restaurant restaurant = null;
		try {
			restaurant = (Restaurant) DaoServicesFactory.getRestaurantsDao().findByPrimaryKey(1L);
		} catch (MdoException e) {
			fail("Could not found the restaurant.");
		}
		assertNotNull("The restaurant must not be null.", restaurant);
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(2010, 7, 15);
		Date revenueDate = calendar.getTime();
		TableType tableType = new TableType();
		tableType.setId(2L);
		Date printingDate = null;
		Date closingDate = null;
		BigDecimal amount = BigDecimal.ONE;
		Set<RevenueCashing> cashings = new HashSet<RevenueCashing>();
		Set<RevenueVat> vats = new HashSet<RevenueVat>();
		Revenue revenue = (Revenue) createNewBean(restaurant, revenueDate, tableType, printingDate, closingDate, amount, cashings, vats);
		RevenueCashing revenueCashing = new RevenueCashing();
		revenueCashing.setAmount(amount);
		MdoTableAsEnum type = new MdoTableAsEnum();
		type.setId(1L);
		revenueCashing.setType(type);
		revenue.addCashing(revenueCashing);

		RevenueVat revenueVat = new RevenueVat();
		ValueAddedTax vat = new ValueAddedTax();
		vat.setId(1L);
		revenueVat.setVat(vat);
		revenueVat.setAmount(amount);
		revenueVat.setValue(amount);
		revenue.addVat(revenueVat);

		list.add(revenue);
		return list;
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof IRevenuesDao);
		assertTrue(this.getInstance() instanceof DefaultRevenuesDao);
	}

	@Override
	public void doFindByUniqueKey() {
		// Restaurant Id = 1L was created at HSQLDB startup
		Long restaurantId = 1L;
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(1970, 7, 15);
		Date revenueDate = calendar.getTime();
		Long tableTypeId = 1L;
		try {
			for (int i = 0; i < 2; i++) {
				IMdoBean bean = this.getInstance().findByUniqueKey(new Object[] { restaurantId, revenueDate, tableTypeId });
				if (i == 0) {
					bean = this.getInstance().findByUniqueKey(new Object[] { restaurantId, revenueDate, tableTypeId });
				} else {
					bean = ((IRevenuesDao) this.getInstance()).findByUniqueKey(restaurantId, revenueDate, tableTypeId);
				}
				assertNotNull("IMdoBean must not be null", bean);
				assertTrue("IMdoBean must be instance of " + Revenue.class, bean instanceof Revenue);
				Revenue castedBean = (Revenue) bean;
				assertNotNull("Revenue restaurant must not be null", castedBean.getRestaurant());
				assertEquals("Revenue restaurant Id must be equals to unique key", restaurantId, castedBean.getRestaurant().getId());
				assertNotNull("Revenue date must not be null", castedBean.getRevenueDate());
				assertEquals("Revenue date must be equals to unique key", revenueDate, castedBean.getRevenueDate());
				assertNotNull("Revenue Table Type must not be null", castedBean.getTableType());
				assertEquals("Revenue Table Type Id must be equals to unique key", tableTypeId, castedBean.getTableType().getId());
				assertNotNull("Revenue cashings must not be null", castedBean.getCashings());
				assertEquals("Check Revenue cashings size", 1, castedBean.getCashings().size());
				assertNotNull("Revenue vats must not be null", castedBean.getVats());
				assertEquals("Check Revenue vats size", 1, castedBean.getVats().size());
				assertFalse("Revenue must not be deleted", castedBean.isDeleted());
			}
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Override
	public void doUpdate() {
		IMdoBean newBean = null;
		// Use the existing data in database
		Restaurant restaurant = null;
		try {
			restaurant = (Restaurant) DaoServicesFactory.getRestaurantsDao().findByPrimaryKey(1L);
		} catch (MdoException e) {
			fail("Could not found the restaurant.");
		}
		assertNotNull("The restaurant must not be null.", restaurant);
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(2010, 8, 15);
		Date revenueDate = calendar.getTime();
		TableType tableType = new TableType();
		tableType.setId(2L);
		Date printingDate = null;
		Date closingDate = null;
		BigDecimal amount = BigDecimal.ONE;
		Set<RevenueCashing> cashings = new HashSet<RevenueCashing>();
		Set<RevenueVat> vats = new HashSet<RevenueVat>();
		newBean = createNewBean(restaurant, revenueDate, tableType, printingDate, closingDate, amount, cashings, vats);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + Revenue.class, beanToBeUpdated instanceof Revenue);
			Revenue castedBean = (Revenue) beanToBeUpdated;
			assertNotNull("Revenue amount must not be null", castedBean.getAmount());
			assertEquals("Revenue amount must be equals to the inserted value", amount, castedBean.getAmount());
			assertNotNull("Revenue cashings must not be null", castedBean.getCashings());
			assertTrue("Revenue cashings must be empty", castedBean.getCashings().isEmpty());
			assertFalse("Revenue must not be deleted", castedBean.isDeleted());
			// Update the created bean
			amount = BigDecimal.valueOf(5.123456789);
			closingDate = new Date();
			castedBean.setAmount(amount);
			castedBean.setClosingDate(closingDate);
			castedBean.setDeleted(true);
			RevenueCashing revenueCashing = new RevenueCashing();
			revenueCashing.setAmount(amount);
			MdoTableAsEnum type = new MdoTableAsEnum();
			type.setId(1L);
			revenueCashing.setType(type);
			castedBean.addCashing(revenueCashing);

			RevenueVat revenueVat = new RevenueVat();
			ValueAddedTax vat = new ValueAddedTax();
			vat.setId(1L);
			revenueVat.setVat(vat);
			revenueVat.setAmount(amount);
			revenueVat.setValue(amount);
			castedBean.addVat(revenueVat);

			this.getInstance().update(castedBean);
			// Reload the modified bean
			Revenue updatedBean = new Revenue();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertEquals("Revenue amount must be equals to the updated value", super.decimalFormat.format(amount), super.decimalFormat.format(updatedBean.getAmount()));
			assertEquals("Revenue closing Date must be equals to the updated value", closingDate, updatedBean.getClosingDate());
			assertNotNull("Revenue cashings must not be null", castedBean.getCashings());
			assertEquals("Check Revenue cashings size", 1, castedBean.getCashings().size());
			assertNotNull("Revenue vats must not be null", castedBean.getVats());
			assertEquals("Check Revenue vats size", 1, castedBean.getVats().size());
			assertTrue("Revenue must be deleted", castedBean.isDeleted());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	private IMdoBean createNewBean(Restaurant restaurant, Date revenueDate, TableType tableType, Date printingDate, Date closingDate, BigDecimal amount,
			Set<RevenueCashing> cashings, Set<RevenueVat> vats) {
		Revenue newBean = new Revenue();
		newBean.setRestaurant(restaurant);
		newBean.setRevenueDate(revenueDate);
		newBean.setTableType(tableType);
		newBean.setPrintingDate(printingDate);
		newBean.setClosingDate(closingDate);
		newBean.setAmount(amount);
		newBean.setCashings(cashings);
		newBean.setVats(vats);
		return newBean;
	}
}
