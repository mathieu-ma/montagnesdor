package fr.mch.mdo.restaurant.dao.tables.hibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.DaoServicesFactory;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.Credit;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServicesTestCase;
import fr.mch.mdo.restaurant.dao.tables.ICreditsDao;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultCreditsDaoTest extends DefaultDaoServicesTestCase
{
	private int incrementReference = 0;

	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultCreditsDaoTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultCreditsDaoTest.class);
	}

	protected IDaoServices getInstance() {
		return DefaultCreditsDao.getInstance();
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
		String reference = DefaultDaoServicesTestCase.CREDIT_FIRST_REFERENCE.concat(String.valueOf(++incrementReference));
		BigDecimal amount = BigDecimal.ONE;
		Date createdDate = new Date();
		Date closingDate = null;
		Boolean printed = Boolean.FALSE;
		return createNewBean(restaurant, reference, amount, createdDate, closingDate, printed);
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
		String reference = DefaultDaoServicesTestCase.CREDIT_FIRST_REFERENCE.concat(String.valueOf(++incrementReference));
		BigDecimal amount = BigDecimal.TEN;
		Date createdDate = new Date();
		Date closingDate = null;
		Boolean printed = Boolean.FALSE;
		list.add(createNewBean(restaurant, reference, amount, createdDate, closingDate, printed));
		return list;
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof ICreditsDao);
		assertTrue(this.getInstance() instanceof DefaultCreditsDao);
	}

	@Override
	public void doFindByUniqueKey() {
		// CREDIT_FIRST_REFERENCE code was created at HSQLDB startup
		Long restaurantId = 1L;
		String reference = DefaultDaoServicesTestCase.CREDIT_FIRST_REFERENCE;
		try {
			IMdoBean bean = this.getInstance().findByUniqueKey(new Object[] { restaurantId, reference });
			assertNotNull("IMdoBean must not be null", bean);
			assertTrue("IMdoBean must be instance of " + Credit.class, bean instanceof Credit);
			Credit castedBean = (Credit) bean;
			assertNotNull("Credit restaurant must not be null", castedBean.getRestaurant());
			assertEquals("Credit restaurant Id must be equals to unique key", restaurantId, castedBean.getRestaurant().getId());
			assertNotNull("Credit reference must not be null", castedBean.getReference());
			assertEquals("Credit reference must be equals to unique key", reference, castedBean.getReference());
			assertFalse("Credit must not be deleted", castedBean.isDeleted());
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
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
		String reference = DefaultDaoServicesTestCase.CREDIT_FIRST_REFERENCE.concat(String.valueOf(++incrementReference));
		BigDecimal amount = BigDecimal.ZERO;
		Date createdDate = new Date();
		Date closingDate = null;
		Boolean printed = Boolean.FALSE;
		newBean = createNewBean(restaurant, reference, amount, createdDate, closingDate, printed);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + Credit.class, beanToBeUpdated instanceof Credit);
			Credit castedBean = (Credit) beanToBeUpdated;
			assertNotNull("Credit reference must not be null", castedBean.getReference());
			assertEquals("Credit reference must be equals to the inserted value", reference, castedBean.getReference());
			assertFalse("Credit must not be deleted", castedBean.isDeleted());
			// Update the created bean
			reference = DefaultDaoServicesTestCase.CREDIT_FIRST_REFERENCE.concat(String.valueOf(++incrementReference));
			amount = BigDecimal.valueOf(5.123456789);
			closingDate = new Date();
			castedBean.setReference(reference);
			castedBean.setAmount(amount);
			castedBean.setClosingDate(closingDate);
			castedBean.setDeleted(true);
			this.getInstance().update(castedBean);
			// Reload the modified bean
			Credit updatedBean = new Credit();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertNotNull("Credit reference must not be null", updatedBean.getReference());
			assertEquals("Credit reference must be equals to the updated value", reference, updatedBean.getReference());
			assertEquals("Credit amount must be equals to the updated value", super.decimalFormat.format(amount), super.decimalFormat.format(updatedBean.getAmount()));
			assertEquals("Credit closing Date must be equals to the updated value", closingDate, updatedBean.getClosingDate());
			assertTrue("Credit must be deleted", castedBean.isDeleted());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	@Override
	public void doUpdateFieldsAndDeleteByKeysSpecific() {
		IMdoBean newBean = null;
		// Use the existing data in database
		Restaurant restaurant = null;
		try {
			restaurant = (Restaurant) DaoServicesFactory.getRestaurantsDao().findByPrimaryKey(1L);
		} catch (MdoException e) {
			fail("Could not found the restaurant.");
		}
		assertNotNull("The restaurant must not be null.", restaurant);
		String reference = DefaultDaoServicesTestCase.CREDIT_FIRST_REFERENCE.concat(String.valueOf(++incrementReference));
		BigDecimal amount = BigDecimal.ZERO;
		Date createdDate = new Date();
		Date closingDate = null;
		Boolean printed = Boolean.FALSE;
		newBean = createNewBean(restaurant, reference, amount, createdDate, closingDate, printed);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + Credit.class, beanToBeUpdated instanceof Credit);
			Credit castedBean = (Credit) beanToBeUpdated;
			assertNotNull("Credit reference must not be null", castedBean.getReference());
			assertEquals("Credit reference must be equals to the inserted value", reference, castedBean.getReference());
			assertFalse("Credit must not be deleted", castedBean.isDeleted());

			// Update the created bean
			Map<String, Object> fields = new HashMap<String, Object>();
			Map<String, Object> keys = new HashMap<String, Object>();
			castedBean.setAmount(BigDecimal.ONE);
			castedBean.setClosingDate(new Date());
			castedBean.setCreatedDate(new Date());
			castedBean.setPrinted(Boolean.TRUE);
			castedBean.setReference("reference");
			fields.put("amount", castedBean.getAmount());
			fields.put("closingDate", castedBean.getClosingDate());
			fields.put("createdDate", castedBean.getCreatedDate());
			fields.put("printed", castedBean.getPrinted());
			fields.put("reference", castedBean.getReference());
			keys.put("id", castedBean.getId());
			this.getInstance().updateFieldsByKeys(fields, keys);
			// Reload the modified bean
			Credit updatedBean = (Credit) createNewBean();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertEquals("Check updated fields ", castedBean.getAmount(), updatedBean.getAmount());
			assertEquals("Check updated fields ", castedBean.getClosingDate(), updatedBean.getClosingDate());
			assertEquals("Check updated fields ", castedBean.getCreatedDate(), updatedBean.getCreatedDate());
			assertEquals("Check updated fields ", castedBean.getPrinted(), updatedBean.getPrinted());
			assertEquals("Check updated fields ", castedBean.getReference(), updatedBean.getReference());

			// Delete the bean by keys
			// Take the fields as keys
			super.doDeleteByKeysSpecific(updatedBean, fields);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
		}
	}

	private IMdoBean createNewBean(Restaurant restaurant, String reference, BigDecimal amount, Date createdDate, Date closingDate, Boolean printed) {
		Credit newBean = new Credit();
		newBean.setRestaurant(restaurant);
		newBean.setReference(reference);
		newBean.setAmount(amount);
		newBean.setCreatedDate(createdDate);
		newBean.setClosingDate(closingDate);
		newBean.setPrinted(printed);
		return newBean;
	}
}
