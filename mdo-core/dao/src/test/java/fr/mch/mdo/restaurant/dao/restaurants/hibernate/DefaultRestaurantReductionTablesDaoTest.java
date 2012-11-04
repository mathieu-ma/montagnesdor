package fr.mch.mdo.restaurant.dao.restaurants.hibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.beans.RestaurantReductionTable;
import fr.mch.mdo.restaurant.dao.beans.TableType;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServicesTestCase;
import fr.mch.mdo.restaurant.dao.restaurants.IRestaurantReductionTablesDao;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultRestaurantReductionTablesDaoTest extends DefaultDaoServicesTestCase 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultRestaurantReductionTablesDaoTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultRestaurantReductionTablesDaoTest.class);
	}

	protected IDaoServices getInstance() {
		return DefaultRestaurantReductionTablesDao.getInstance();
	}

	protected IMdoBean createNewBean() {
		BigDecimal minAmount = BigDecimal.valueOf(15);
		BigDecimal value = BigDecimal.valueOf(10);
		Restaurant restaurant = new Restaurant();
		restaurant.setId(1L);
		TableType type = new TableType();
		type.setId(1L);
		return createNewBean(minAmount, value, restaurant, type);
	}

	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		BigDecimal minAmount = BigDecimal.valueOf(15);
		BigDecimal value = BigDecimal.valueOf(10);
		Restaurant restaurant = new Restaurant();
		restaurant.setId(2L);
		TableType type = new TableType();
		type.setId(1L);
		list.add(createNewBean(minAmount, value, restaurant, type));
		return list;
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof IRestaurantReductionTablesDao);
		assertTrue(this.getInstance() instanceof DefaultRestaurantReductionTablesDao);
	}

	@Override
	public void doFindByUniqueKey() {
		assertTrue("Dummy", true);
	}

	@Override
	public void doUpdate() {
		IMdoBean newBean = null;
		BigDecimal minAmount = BigDecimal.valueOf(15);
		BigDecimal value = BigDecimal.valueOf(10);
		Restaurant restaurant = new Restaurant();
		restaurant.setId(2L);
		TableType type = new TableType();
		type.setId(2L);
		newBean = createNewBean(minAmount, value, restaurant, type);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + RestaurantReductionTable.class, beanToBeUpdated instanceof RestaurantReductionTable);
			RestaurantReductionTable castedBean = (RestaurantReductionTable) beanToBeUpdated;
			assertEquals("RestaurantReductionTable value must be equals to the inserted value", value, castedBean.getValue());
			assertFalse("RestaurantReductionTable must not be deleted", castedBean.isDeleted());
			// Update the created bean
			value = BigDecimal.valueOf(15);
			castedBean.setValue(value);
			castedBean.setDeleted(true);
			this.getInstance().update(castedBean);
			// Reload the modified bean
			RestaurantReductionTable updatedBean = new RestaurantReductionTable();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertEquals("RestaurantReductionTable value must be equals to the inserted value", value, updatedBean.getValue());
			assertTrue("RestaurantReductionTable must be deleted", updatedBean.isDeleted());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}
	
	@Override
	public void doUpdateFieldsAndDeleteByKeysSpecific() {
		IMdoBean newBean = null;
		BigDecimal minAmount = BigDecimal.valueOf(15);
		BigDecimal value = BigDecimal.valueOf(10);
		Restaurant restaurant = new Restaurant();
		restaurant.setId(2L);
		TableType type = new TableType();
		type.setId(2L);
		newBean = createNewBean(minAmount, value, restaurant, type);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + RestaurantReductionTable.class, beanToBeUpdated instanceof RestaurantReductionTable);
			RestaurantReductionTable castedBean = (RestaurantReductionTable) beanToBeUpdated;
			assertEquals("RestaurantReductionTable value must be equals to the inserted value", value, castedBean.getValue());
			assertFalse("RestaurantReductionTable must not be deleted", castedBean.isDeleted());

			// Update the created bean
			Map<String, Object> fields = new HashMap<String, Object>();
			Map<String, Object> keys = new HashMap<String, Object>();
			castedBean.setDeleted(true);
			fields.put("deleted", castedBean.isDeleted());
			keys.put("id", castedBean.getId());
			this.getInstance().updateFieldsByKeys(fields, keys);
			// Reload the modified bean
			RestaurantReductionTable updatedBean = (RestaurantReductionTable) createNewBean();
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
		IRestaurantReductionTablesDao dao = (IRestaurantReductionTablesDao) this.getInstance();
		// By Restaurant Id
		Long restaurantId = 1L;
		try {
			List<RestaurantReductionTable> list  = dao.findAll(restaurantId);
			assertNotNull("List by Restaurant Id must not be null", list);
			assertFalse("List by Restaurant Id must not be empty", list.isEmpty());

			RestaurantReductionTable reductionTable = list.get(0);
			try {
				// Non lazy exception
				assertNotNull("Non lazy exception", reductionTable.getType().getCode().getDefaultLabel());
			} catch (Exception e) {
				fail(MdoTestCase.DEFAULT_FAILED_MESSAGE);
			}
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		// By Restaurant Id and Type Name
		String typeName = "TAKE_AWAY";
		try {
			List<RestaurantReductionTable> list  = dao.findAll(restaurantId, typeName);
			assertNotNull("List by Restaurant Id and type name must not be null", list);
			assertFalse("List by Restaurant Id must not be empty", list.isEmpty());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}
	
	public void testFindOnlyReductionTables() {
		IRestaurantReductionTablesDao dao = (IRestaurantReductionTablesDao) this.getInstance();
		// By Restaurant Id
		Long restaurantId = 1L;
		try {
			List<RestaurantReductionTable> list  = dao.findOnlyReductionTables(restaurantId);
			assertNotNull("List by Restaurant Id must not be null", list);
			assertFalse("List by Restaurant Id must not be empty", list.isEmpty());

			RestaurantReductionTable reductionTable = list.get(0);
			try {
				// Non lazy exception
				assertNotNull("Non lazy exception", reductionTable.getType().getCode());
				assertNotNull("Non lazy exception", reductionTable.getType().getCode().getDefaultLabel());
			} catch (Exception e) {
				fail(MdoTestCase.DEFAULT_FAILED_MESSAGE);
			}
			try {
				assertNotNull("First element must not be null", reductionTable.getRestaurant());
				// Lazy exception
				reductionTable.getRestaurant().getReductionTables();
				fail(MdoTestCase.DEFAULT_FAILED_MESSAGE);
			} catch (Exception e) {
				assertTrue("Lazy exception", true);
			}
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	public void testFindReductionTable() {
		IRestaurantReductionTablesDao dao = (IRestaurantReductionTablesDao) this.getInstance();
		Long restaurantId = 1L;
		Long typeId = 2L;

		try {
			RestaurantReductionTable reductionTable = dao.findReductionTable(restaurantId, typeId);
			try {
				// Non lazy exception
				assertNotNull("Non lazy exception", reductionTable.getType().getCode());
				assertNotNull("Non lazy exception", reductionTable.getType().getCode().getDefaultLabel());
			} catch (Exception e) {
				fail(MdoTestCase.DEFAULT_FAILED_MESSAGE);
			}
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}
	
	private IMdoBean createNewBean(BigDecimal minAmount, BigDecimal value, Restaurant restaurant, TableType type) {
		RestaurantReductionTable newBean = new RestaurantReductionTable();
		newBean.setMinAmount(minAmount);
		newBean.setValue(value);
		newBean.setRestaurant(restaurant);
		newBean.setType(type);
		return newBean;
	}
}
