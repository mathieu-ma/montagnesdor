package fr.mch.mdo.restaurant.services.business.managers.restaurants;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantPrefixTableDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantPrefixTablesManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.TableTypeDto;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.DefaultAdministrationManagerTest;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultRestaurantPrefixTablesManagerTest extends DefaultAdministrationManagerTest 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultRestaurantPrefixTablesManagerTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultRestaurantPrefixTablesManagerTest.class);
	}

	@Override
	protected IAdministrationManager getInstance() {
		return DefaultRestaurantPrefixTablesManager.getInstance();
	}

	@Override
	protected IMdoDtoBean createNewBean() {
		// Use the existing data in database
		MdoTableAsEnumDto prefix = new MdoTableAsEnumDto();
		prefix.setId(2L);
		RestaurantDto restaurant = new RestaurantDto();
		restaurant.setId(1L);
		TableTypeDto type = new TableTypeDto();
		type.setId(1L);
		return createNewBean(prefix, restaurant, type);
	}

	@Override
	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		// Use the existing data in database
		MdoTableAsEnumDto prefix = new MdoTableAsEnumDto();
		prefix.setId(3L);
		RestaurantDto restaurant = new RestaurantDto();
		restaurant.setId(1L);
		TableTypeDto type = new TableTypeDto();
		type.setId(1L);
		list.add(createNewBean(prefix, restaurant, type));
		return list;
	}

	@Override
	public void doUpdate() {
		IMdoDtoBean newBean = null;
		// Use the existing data in database
		MdoTableAsEnumDto prefix = new MdoTableAsEnumDto();
		prefix.setId(4L);
		RestaurantDto restaurant = new RestaurantDto();
		restaurant.setId(1L);
		TableTypeDto type = new TableTypeDto();
		type.setId(1L);
		newBean = createNewBean(prefix, restaurant, type);

		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + RestaurantPrefixTableDto.class, beanToBeUpdated instanceof RestaurantPrefixTableDto);
			RestaurantPrefixTableDto castedBean = (RestaurantPrefixTableDto) beanToBeUpdated;
			assertNotNull("RestaurantPrefixTable prefix must not be null", castedBean.getPrefix());
			assertEquals("RestaurantPrefixTable prefix must be equals to the inserted value", prefix.getId(), castedBean.getPrefix().getId());
			assertNotNull("RestaurantPrefixTable restaurant must not be null", castedBean.getRestaurant());
			assertEquals("RestaurantPrefixTable restaurant must be equals to the inserted value", restaurant.getId(), castedBean.getRestaurant().getId());
			assertNotNull("RestaurantPrefixTable type must not be null", castedBean.getType());
			assertEquals("RestaurantPrefixTable type must be equals to the inserted value", type.getId(), castedBean.getType().getId());

			// Update the created bean
			// Use the existing data in database
			prefix = new MdoTableAsEnumDto();
			prefix.setId(5L);
			castedBean.setPrefix(prefix);
			this.getInstance().update(castedBean);
			// Reload the modified bean
			RestaurantPrefixTableDto updatedBean = (RestaurantPrefixTableDto) createNewBean();
			updatedBean.setId(castedBean.getId());
			updatedBean = (RestaurantPrefixTableDto) this.getInstance().load(updatedBean);
			assertNotNull("RestaurantPrefixTable prefix must not be null", updatedBean.getPrefix());
			assertEquals("RestaurantPrefixTable prefix must be equals to the inserted value", prefix.getId(), updatedBean.getPrefix().getId());
			assertNotNull("RestaurantPrefixTable restaurant must not be null", updatedBean.getRestaurant());
			assertEquals("RestaurantPrefixTable restaurant must be equals to the inserted value", restaurant.getId(), updatedBean.getRestaurant().getId());
			assertNotNull("RestaurantPrefixTable type must not be null", updatedBean.getType());
			assertEquals("RestaurantPrefixTable type must be equals to the inserted value", type.getId(), updatedBean.getType().getId());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
	}

	@Override
	public void doProcessList() {
		RestaurantPrefixTablesManagerViewBean viewBean = new RestaurantPrefixTablesManagerViewBean();
		try {
			this.getInstance().processList(viewBean);
			assertNotNull("Main list not be null", viewBean.getList());
			assertFalse("Main list not be empty", viewBean.getList().isEmpty());
			assertNotNull("Prefix list not be null", viewBean.getPrefixes());
			assertFalse("Prefix list not be empty", viewBean.getPrefixes().isEmpty());
			assertNotNull("Types list not be null", viewBean.getTypes());
			assertFalse("Types list not be empty", viewBean.getTypes().isEmpty());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
	}

	public void testGetInstance() {
		IAdministrationManager manager = this.getInstance();
		assertTrue(manager instanceof IRestaurantPrefixTablesManager);
		assertTrue(manager instanceof DefaultRestaurantPrefixTablesManager);
	}

	public void testFindAll() {
		Long restaurantId = 1L;
		String typeName = "TAKE_AWAY";
		List<IMdoDtoBean> list = null;
		IRestaurantPrefixTablesManager manager = (IRestaurantPrefixTablesManager) this.getInstance();
		// By Restaurant Id
		try {
			list = manager.findAll(DefaultAdministrationManagerTest.userContext, restaurantId);
			assertNotNull("List by Restaurant Id must not be null", list);
			assertFalse("List by Restaurant Id must not be empty", list.isEmpty());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
		// By Restaurant Id and Type Name
		try {
			list = manager.findAll(DefaultAdministrationManagerTest.userContext, restaurantId, typeName);
			assertNotNull("List by Restaurant Id and type name must not be null", list);
			assertFalse("List by Restaurant Id must not be empty", list.isEmpty());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
	}
	
	private IMdoDtoBean createNewBean(MdoTableAsEnumDto prefix, RestaurantDto restaurant, TableTypeDto type) {
		RestaurantPrefixTableDto newBean = new RestaurantPrefixTableDto();
		newBean.setPrefix(prefix);
		newBean.setRestaurant(restaurant);
		newBean.setType(type);
		return newBean;
	}

}
