package fr.mch.mdo.restaurant.services.business.managers.restaurants;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantVatTableTypeDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantVatTableTypesManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.TableTypeDto;
import fr.mch.mdo.restaurant.dto.beans.ValueAddedTaxDto;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.DefaultAdministrationManagerTest;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultRestaurantVatTableTypesManagerTest extends DefaultAdministrationManagerTest 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultRestaurantVatTableTypesManagerTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultRestaurantVatTableTypesManagerTest.class);
	}

	@Override
	protected IAdministrationManager getInstance() {
		return DefaultRestaurantVatTableTypesManager.getInstance();
	}

	@Override
	protected IMdoDtoBean createNewBean() {
		// Use the existing data in database
		ValueAddedTaxDto vat = new ValueAddedTaxDto();
		vat.setId(1L);
		RestaurantDto restaurant = new RestaurantDto();
		restaurant.setId(1L);
		TableTypeDto type = new TableTypeDto();
		type.setId(1L);
		return createNewBean(vat, restaurant, type);
	}

	@Override
	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		// Use the existing data in database
		ValueAddedTaxDto vat = new ValueAddedTaxDto();
		vat.setId(1L);
		RestaurantDto restaurant = new RestaurantDto();
		restaurant.setId(1L);
		TableTypeDto type = new TableTypeDto();
		type.setId(2L);
		list.add(createNewBean(vat, restaurant, type));
		return list;
	}

	@Override
	public void doUpdate() {
		IMdoDtoBean newBean = null;
		// Use the existing data in database
		ValueAddedTaxDto vat = new ValueAddedTaxDto();
		vat.setId(1L);
		RestaurantDto restaurant = new RestaurantDto();
		restaurant.setId(2L);
		TableTypeDto type = new TableTypeDto();
		type.setId(1L);
		newBean = createNewBean(vat, restaurant, type);

		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + RestaurantVatTableTypeDto.class, beanToBeUpdated instanceof RestaurantVatTableTypeDto);
			RestaurantVatTableTypeDto castedBean = (RestaurantVatTableTypeDto) beanToBeUpdated;
			assertNotNull("RestaurantVatTableType vat must not be null", castedBean.getVat());
			assertEquals("RestaurantVatTableType vat must be equals to the inserted value", vat.getId(), castedBean.getVat().getId());
			assertNotNull("RestaurantVatTableType restaurant must not be null", castedBean.getRestaurant());
			assertEquals("RestaurantVatTableType restaurant must be equals to the inserted value", restaurant.getId(), castedBean.getRestaurant().getId());
			assertNotNull("RestaurantVatTableType type must not be null", castedBean.getType());
			assertEquals("RestaurantVatTableType type must be equals to the inserted value", type.getId(), castedBean.getType().getId());

			// Update the created bean
			// Use the existing data in database
			vat = new ValueAddedTaxDto();
			vat.setId(2L);
			castedBean.setVat(vat);
			this.getInstance().update(castedBean);
			// Reload the modified bean
			RestaurantVatTableTypeDto updatedBean = (RestaurantVatTableTypeDto) createNewBean();
			updatedBean.setId(castedBean.getId());
			updatedBean = (RestaurantVatTableTypeDto) this.getInstance().load(updatedBean);
			assertNotNull("RestaurantVatTableType prefix must not be null", updatedBean.getVat());
			assertEquals("RestaurantVatTableType prefix must be equals to the inserted value", vat.getId(), updatedBean.getVat().getId());
			assertNotNull("RestaurantVatTableType restaurant must not be null", updatedBean.getRestaurant());
			assertEquals("RestaurantVatTableType restaurant must be equals to the inserted value", restaurant.getId(), updatedBean.getRestaurant().getId());
			assertNotNull("RestaurantVatTableType type must not be null", updatedBean.getType());
			assertEquals("RestaurantVatTableType type must be equals to the inserted value", type.getId(), updatedBean.getType().getId());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
	}

	@Override
	public void doProcessList() {
		RestaurantVatTableTypesManagerViewBean viewBean = new RestaurantVatTableTypesManagerViewBean();
		try {
			this.getInstance().processList(viewBean);
			assertNotNull("Main list not be null", viewBean.getList());
			assertFalse("Main list not be empty", viewBean.getList().isEmpty());
			assertNotNull("Types list not be null", viewBean.getTypes());
			assertFalse("Types list not be empty", viewBean.getTypes().isEmpty());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
	}

	public void testGetInstance() {
		IAdministrationManager manager = this.getInstance();
		assertTrue(manager instanceof IRestaurantVatTableTypesManager);
		assertTrue(manager instanceof DefaultRestaurantVatTableTypesManager);
	}

	public void testFindAll() {
		Long restaurantId = 1L;
		String typeName = "TAKE_AWAY";
		List<IMdoDtoBean> list = null;
		IRestaurantVatTableTypesManager manager = (IRestaurantVatTableTypesManager) this.getInstance();
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
	
	private IMdoDtoBean createNewBean(ValueAddedTaxDto vat, RestaurantDto restaurant, TableTypeDto type) {
		RestaurantVatTableTypeDto newBean = new RestaurantVatTableTypeDto();
		newBean.setVat(vat);
		newBean.setRestaurant(restaurant);
		newBean.setType(type);
		return newBean;
	}

}
                                             