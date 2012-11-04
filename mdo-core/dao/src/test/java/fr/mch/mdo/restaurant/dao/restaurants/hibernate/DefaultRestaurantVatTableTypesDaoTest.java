package fr.mch.mdo.restaurant.dao.restaurants.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.beans.RestaurantVatTableType;
import fr.mch.mdo.restaurant.dao.beans.TableType;
import fr.mch.mdo.restaurant.dao.beans.ValueAddedTax;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServicesTestCase;
import fr.mch.mdo.restaurant.dao.restaurants.IRestaurantVatTableTypesDao;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultRestaurantVatTableTypesDaoTest extends DefaultDaoServicesTestCase 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultRestaurantVatTableTypesDaoTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultRestaurantVatTableTypesDaoTest.class);
	}

	protected IDaoServices getInstance() {
		return DefaultRestaurantVatTableTypesDao.getInstance();
	}

	protected IMdoBean createNewBean() {
		// Use the existing data in database
		ValueAddedTax vat = new ValueAddedTax();
		vat.setId(1L);
		Restaurant restaurant = new Restaurant();
		restaurant.setId(1L);
		TableType type = new TableType();
		type.setId(1L);
		return createNewBean(vat, restaurant, type);
	}

	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		// Use the existing data in database
		ValueAddedTax vat = new ValueAddedTax();
		vat.setId(1L);
		Restaurant restaurant = new Restaurant();
		restaurant.setId(1L);
		TableType type = new TableType();
		type.setId(2L);
		list.add(createNewBean(vat, restaurant, type));
		return list;
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof IRestaurantVatTableTypesDao);
		assertTrue(this.getInstance() instanceof DefaultRestaurantVatTableTypesDao);
	}

	@Override
	public void doFindByUniqueKey() {
		assertTrue("Dummy", true);
	}

	@Override
	public void doUpdate() {
		IMdoBean newBean = null;
		// Use the existing data in database
		ValueAddedTax vat = new ValueAddedTax();
		vat.setId(2L);
		Restaurant restaurant = new Restaurant();
		restaurant.setId(1L);
		TableType type = new TableType();
		type.setId(1L);
		newBean = createNewBean(vat, restaurant, type);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + RestaurantVatTableType.class, beanToBeUpdated instanceof RestaurantVatTableType);
			RestaurantVatTableType castedBean = (RestaurantVatTableType) beanToBeUpdated;
			assertNotNull("RestaurantVatTableType vat must not be null", castedBean.getVat());
			assertEquals("RestaurantVatTableType vat name must be equals to the inserted value", vat.toString(), castedBean.getVat().toString());
			assertFalse("RestaurantVatTableType must not be deleted", castedBean.isDeleted());
			// Update the created bean
			castedBean.setDeleted(true);
			this.getInstance().update(castedBean);
			// Reload the modified bean
			RestaurantVatTableType updatedBean = new RestaurantVatTableType();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertNotNull("RestaurantVatTableType vat must not be null", updatedBean.getVat());
			assertEquals("RestaurantVatTableType vat name must be equals to the updated value", updatedBean.getVat().toString(), updatedBean.getVat().toString());
			assertTrue("RestaurantVatTableType must be deleted", updatedBean.isDeleted());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}
	
	@Override
	public void doUpdateFieldsAndDeleteByKeysSpecific() {
		IMdoBean newBean = null;
		// Use the existing data in database
		ValueAddedTax vat = new ValueAddedTax();
		vat.setId(2L);
		Restaurant restaurant = new Restaurant();
		restaurant.setId(2L);
		TableType type = new TableType();
		type.setId(2L);
		newBean = createNewBean(vat, restaurant, type);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + RestaurantVatTableType.class, beanToBeUpdated instanceof RestaurantVatTableType);
			RestaurantVatTableType castedBean = (RestaurantVatTableType) beanToBeUpdated;
			assertNotNull("RestaurantVatTableType vat must not be null", castedBean.getVat());
			assertEquals("RestaurantVatTableType vat name must be equals to the inserted value", vat.toString(), castedBean.getVat().toString());
			assertFalse("RestaurantVatTableType must not be deleted", castedBean.isDeleted());

			// Update the created bean
			Map<String, Object> fields = new HashMap<String, Object>();
			Map<String, Object> keys = new HashMap<String, Object>();
			castedBean.setDeleted(true);
			fields.put("deleted", castedBean.isDeleted());
			keys.put("id", castedBean.getId());
			this.getInstance().updateFieldsByKeys(fields, keys);
			// Reload the modified bean
			RestaurantVatTableType updatedBean = (RestaurantVatTableType) createNewBean();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertEquals("Check updated fields ", castedBean.isDeleted(), updatedBean.isDeleted());

			// Delete the bean by keys
			// Take the fields as keys
			super.doDeleteByKeysSpecific(updatedBean, fields);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
		}
	}

	public void testFindAll() {
		IRestaurantVatTableTypesDao dao = (IRestaurantVatTableTypesDao) this.getInstance();
		// By Restaurant Id
		Long restaurantId = 1L;
		try {
			List<RestaurantVatTableType> list  = dao.findAll(restaurantId);
			assertNotNull("List by Restaurant Id must not be null", list);
			assertFalse("List by Restaurant Id must not be empty", list.isEmpty());

			RestaurantVatTableType vatTable = list.get(0);
			try {
				// Non lazy exception
				assertNotNull("Non lazy exception", vatTable.getType().getCode().getDefaultLabel());
			} catch (Exception e) {
				fail(MdoTestCase.DEFAULT_FAILED_MESSAGE);
			}
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		// By Restaurant Id and Type Name
		String typeName = "TAKE_AWAY";
		try {
			List<RestaurantVatTableType> list  = dao.findAll(restaurantId, typeName);
			assertNotNull("List by Restaurant Id and type name must not be null", list);
			assertFalse("List by Restaurant Id must not be empty", list.isEmpty());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	public void testFindOnlyVats() {
		IRestaurantVatTableTypesDao dao = (IRestaurantVatTableTypesDao) this.getInstance();
		// By Restaurant Id
		Long restaurantId = 1L;
		try {
			List<RestaurantVatTableType> list  = dao.findOnlyVats(restaurantId);
			assertNotNull("List by Restaurant Id must not be null", list);
			assertFalse("List by Restaurant Id must not be empty", list.isEmpty());

			RestaurantVatTableType vatTable = list.get(0);
			try {
				// Non lazy exception
				assertNotNull("Non lazy exception", vatTable.getType().getCode());
				assertNotNull("Non lazy exception", vatTable.getType().getCode().getDefaultLabel());
			} catch (Exception e) {
				fail(MdoTestCase.DEFAULT_FAILED_MESSAGE);
			}
			try {
				assertNotNull("First element must not be null", vatTable.getRestaurant());
				// Lazy exception
				vatTable.getRestaurant().getReductionTables();
				fail(MdoTestCase.DEFAULT_FAILED_MESSAGE);
			} catch (Exception e) {
				assertTrue("Lazy exception", true);
			}
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	private IMdoBean createNewBean(ValueAddedTax vat, Restaurant restaurant, TableType type) {
		RestaurantVatTableType newBean = new RestaurantVatTableType();
		newBean.setVat(vat);
		newBean.setRestaurant(restaurant);
		newBean.setType(type);
		return newBean;
	}
}
