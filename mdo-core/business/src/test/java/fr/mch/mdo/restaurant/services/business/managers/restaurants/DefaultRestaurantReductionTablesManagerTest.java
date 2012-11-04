package fr.mch.mdo.restaurant.services.business.managers.restaurants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantReductionTableDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantReductionTablesManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.TableTypeDto;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.DefaultAdministrationManagerTest;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultRestaurantReductionTablesManagerTest extends DefaultAdministrationManagerTest 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultRestaurantReductionTablesManagerTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultRestaurantReductionTablesManagerTest.class);
	}

	@Override
	protected IAdministrationManager getInstance() {
		return DefaultRestaurantReductionTablesManager.getInstance();
	}

	@Override
	protected IMdoDtoBean createNewBean() {
		BigDecimal minAmount = BigDecimal.valueOf(15);
		BigDecimal value = BigDecimal.valueOf(10);
		RestaurantDto restaurant = new RestaurantDto();
		restaurant.setId(1L);
		TableTypeDto type = new TableTypeDto();
		type.setId(1L);
		return createNewBean(minAmount, value, restaurant, type);
	}

	@Override
	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		BigDecimal minAmount = BigDecimal.valueOf(15);
		BigDecimal value = BigDecimal.valueOf(10);
		RestaurantDto restaurant = new RestaurantDto();
		restaurant.setId(2L);
		TableTypeDto type = new TableTypeDto();
		type.setId(1L);
		list.add(createNewBean(minAmount, value, restaurant, type));
		return list;
	}

	@Override
	public void doUpdate() {
		IMdoDtoBean newBean = null;
		BigDecimal minAmount = BigDecimal.valueOf(15);
		BigDecimal value = BigDecimal.valueOf(10);
		RestaurantDto restaurant = new RestaurantDto();
		restaurant.setId(2L);
		TableTypeDto type = new TableTypeDto();
		type.setId(1L);
		newBean = createNewBean(minAmount, value, restaurant, type);

		try {

			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + RestaurantReductionTableDto.class, beanToBeUpdated instanceof RestaurantReductionTableDto);
			RestaurantReductionTableDto castedBean = (RestaurantReductionTableDto) beanToBeUpdated;
			assertEquals("RestaurantReductionTable minimum amount must be equals to the inserted value", minAmount, castedBean.getMinAmount());
			assertEquals("RestaurantReductionTable value must be equals to the inserted value", value, castedBean.getValue());
			assertNotNull("RestaurantReductionTable restaurant must not be null", castedBean.getRestaurant());
			assertEquals("RestaurantReductionTable restaurant must be equals to the inserted value", restaurant.getId(), castedBean.getRestaurant().getId());
			assertNotNull("RestaurantReductionTable type must not be null", castedBean.getType());
			assertEquals("RestaurantReductionTable type must be equals to the inserted value", type.getId(), castedBean.getType().getId());

			// Update the created bean
			castedBean.setValue(BigDecimal.ONE);
			this.getInstance().update(castedBean);
			// Reload the modified bean
			RestaurantReductionTableDto updatedBean = (RestaurantReductionTableDto) createNewBean();
			updatedBean.setId(castedBean.getId());
			updatedBean = (RestaurantReductionTableDto) this.getInstance().load(updatedBean);
			assertEquals("RestaurantReductionTable value must be equals to the inserted value", castedBean.getValue(), updatedBean.getValue());
			assertNotNull("RestaurantReductionTable restaurant must not be null", updatedBean.getRestaurant());
			assertEquals("RestaurantReductionTable restaurant must be equals to the inserted value", restaurant.getId(), updatedBean.getRestaurant().getId());
			assertNotNull("RestaurantReductionTable type must not be null", updatedBean.getType());
			assertEquals("RestaurantReductionTable type must be equals to the inserted value", type.getId(), updatedBean.getType().getId());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
	}

	@Override
	public void doProcessList() {
		RestaurantReductionTablesManagerViewBean viewBean = new RestaurantReductionTablesManagerViewBean();
		try {
			this.getInstance().processList(viewBean);
			assertNotNull("Main list not be null", viewBean.getList());
			assertFalse("Main list not be empty", viewBean.getList().isEmpty());
			assertNotNull("Restaurants list not be null", viewBean.getRestaurants());
			assertFalse("Restaurants list not be empty", viewBean.getRestaurants().isEmpty());
			assertNotNull("Types list not be null", viewBean.getTypes());
			assertFalse("Types list not be empty", viewBean.getTypes().isEmpty());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
	}

	public void testGetInstance() {
		IAdministrationManager manager = this.getInstance();
		assertTrue(manager instanceof IRestaurantReductionTablesManager);
		assertTrue(manager instanceof DefaultRestaurantReductionTablesManager);
	}

	public void testFindAll() {
		Long restaurantId = 1L;
		String typeName = "TAKE_AWAY";
		List<IMdoDtoBean> list = null;
		IRestaurantReductionTablesManager manager = (IRestaurantReductionTablesManager) this.getInstance();
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
	
	private IMdoDtoBean createNewBean(BigDecimal minAmount, BigDecimal value, RestaurantDto restaurant, TableTypeDto type) {
		RestaurantReductionTableDto newBean = new RestaurantReductionTableDto();
		newBean.setMinAmount(minAmount);
		newBean.setValue(value);
		newBean.setRestaurant(restaurant);
		newBean.setType(type);
		return newBean;
	}

}
