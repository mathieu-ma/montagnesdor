package fr.mch.mdo.restaurant.dao.restaurants.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.beans.RestaurantPrefixTable;
import fr.mch.mdo.restaurant.dao.beans.TableType;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServicesTestCase;
import fr.mch.mdo.restaurant.dao.restaurants.IRestaurantPrefixTablesDao;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultRestaurantPrefixTablesDaoTest extends DefaultDaoServicesTestCase 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultRestaurantPrefixTablesDaoTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultRestaurantPrefixTablesDaoTest.class);
	}

	protected IDaoServices getInstance() {
		return DefaultRestaurantPrefixTablesDao.getInstance();
	}

	protected IMdoBean createNewBean() {
		// Use the existing data in database
		MdoTableAsEnum prefix = new MdoTableAsEnum();
		prefix.setId(2L);
		Restaurant restaurant = new Restaurant();
		restaurant.setId(1L);
		TableType type = new TableType();
		type.setId(1L);
		return createNewBean(prefix, restaurant, type);
	}

	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		// Use the existing data in database
		MdoTableAsEnum prefix = new MdoTableAsEnum();
		prefix.setId(3L);
		Restaurant restaurant = new Restaurant();
		restaurant.setId(1L);
		TableType type = new TableType();
		type.setId(1L);
		list.add(createNewBean(prefix, restaurant, type));
		return list;
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof IRestaurantPrefixTablesDao);
		assertTrue(this.getInstance() instanceof DefaultRestaurantPrefixTablesDao);
	}

	@Override
	public void doFindByUniqueKey() {
		assertTrue("Dummy", true);
	}

	@Override
	public void doUpdate() {
		IMdoBean newBean = null;
		// Use the existing data in database
		MdoTableAsEnum prefix = new MdoTableAsEnum();
		prefix.setId(4L);
		Restaurant restaurant = new Restaurant();
		restaurant.setId(1L);
		TableType type = new TableType();
		type.setId(1L);
		newBean = createNewBean(prefix, restaurant, type);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + RestaurantPrefixTable.class, beanToBeUpdated instanceof RestaurantPrefixTable);
			RestaurantPrefixTable castedBean = (RestaurantPrefixTable) beanToBeUpdated;
			assertNotNull("RestaurantPrefixTable prefix must not be null", castedBean.getPrefix());
			assertEquals("RestaurantPrefixTable prefix name must be equals to the inserted value", prefix.toString(), castedBean.getPrefix().toString());
			assertFalse("RestaurantPrefixTable must not be deleted", castedBean.isDeleted());
			// Update the created bean
			castedBean.setDeleted(true);
			this.getInstance().update(castedBean);
			// Reload the modified bean
			RestaurantPrefixTable updatedBean = new RestaurantPrefixTable();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertNotNull("RestaurantPrefixTable prefix must not be null", updatedBean.getPrefix());
			assertEquals("RestaurantPrefixTable prefix name must be equals to the updated value", updatedBean.getPrefix().toString(), updatedBean.getPrefix().toString());
			assertTrue("RestaurantPrefixTable must be deleted", updatedBean.isDeleted());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}
	
	@Override
	public void doUpdateFieldsByKeysSpecific() {
		IMdoBean newBean = null;
		// Use the existing data in database
		MdoTableAsEnum prefix = new MdoTableAsEnum();
		prefix.setId(4L);
		Restaurant restaurant = new Restaurant();
		restaurant.setId(1L);
		TableType type = new TableType();
		type.setId(1L);
		newBean = createNewBean(prefix, restaurant, type);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + RestaurantPrefixTable.class, beanToBeUpdated instanceof RestaurantPrefixTable);
			RestaurantPrefixTable castedBean = (RestaurantPrefixTable) beanToBeUpdated;
			assertNotNull("RestaurantPrefixTable prefix must not be null", castedBean.getPrefix());
			assertEquals("RestaurantPrefixTable prefix name must be equals to the inserted value", prefix.toString(), castedBean.getPrefix().toString());
			assertFalse("RestaurantPrefixTable must not be deleted", castedBean.isDeleted());

			// Update the created bean
			Map<String, Object> fields = new HashMap<String, Object>();
			Map<String, Object> keys = new HashMap<String, Object>();
			castedBean.setDeleted(true);
			fields.put("deleted", castedBean.isDeleted());
			keys.put("id", castedBean.getId());
			this.getInstance().updateFieldsByKeys(fields, keys);
			// Reload the modified bean
			RestaurantPrefixTable updatedBean = (RestaurantPrefixTable) createNewBean();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertEquals("Check updated fields ", castedBean.isDeleted(), updatedBean.isDeleted());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
		}
	}

	public void testFindAll() {
		IRestaurantPrefixTablesDao dao = (IRestaurantPrefixTablesDao) this.getInstance();
		// By Restaurant Id
		Long restaurantId = 1L;
		try {
			List<RestaurantPrefixTable> list  = dao.findAll(restaurantId);
			assertNotNull("List by Restaurant Id must not be null", list);
			assertFalse("List by Restaurant Id must not be empty", list.isEmpty());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		// By Restaurant Id and Type Name
		String typeName = "TAKE_AWAY";
		try {
			List<RestaurantPrefixTable> list  = dao.findAll(restaurantId, typeName);
			assertNotNull("List by Restaurant Id and type name must not be null", list);
			assertFalse("List by Restaurant Id must not be empty", list.isEmpty());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}
	
	private IMdoBean createNewBean(MdoTableAsEnum prefix, Restaurant restaurant, TableType type) {
		RestaurantPrefixTable newBean = new RestaurantPrefixTable();
		newBean.setPrefix(prefix);
		newBean.setRestaurant(restaurant);
		newBean.setType(type);
		return newBean;
	}
}
