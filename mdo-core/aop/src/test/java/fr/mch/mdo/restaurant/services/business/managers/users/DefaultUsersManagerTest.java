package fr.mch.mdo.restaurant.services.business.managers.users;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.dto.beans.UserDto;
import fr.mch.mdo.restaurant.dto.beans.UserRestaurantDto;
import fr.mch.mdo.restaurant.dto.beans.UsersManagerViewBean;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.DefaultAdministrationManagerTest;
import fr.mch.mdo.restaurant.services.business.managers.DefaultMdoTableAsEnumsManager;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;
import fr.mch.mdo.restaurant.services.business.managers.restaurants.DefaultRestaurantsManager;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultUsersManagerTest extends DefaultAdministrationManagerTest 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultUsersManagerTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultUsersManagerTest.class);
	}

	@Override
	protected IAdministrationManager getInstance() {
		return DefaultUsersManager.getInstance();
	}

	@Override
	protected IMdoDtoBean createNewBean() {
		String name = "MA";
		String forename1 = "Chhui Huy";
		String forename2 = "Mathieu";
		Calendar calendar = Calendar.getInstance();
		calendar.set(1970, 7, 15);
		Date birthdate = calendar.getTime();
		boolean sex = true;
		// Use the existing data in database
		MdoTableAsEnumDto title = new MdoTableAsEnumDto();
		try {
			title = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(1L, userContext);
		} catch (MdoException e) {
			fail("Could not found the user title code.");
		}
		byte[] picture = { (byte) 0x01, (byte) 0x02 };
		Set<UserRestaurantDto> restaurants = new HashSet<UserRestaurantDto>();
		return createNewBean(name, forename1, forename2, birthdate, sex, title, picture, restaurants);
	}

	@Override
	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		// First element in the list
		IMdoBean newBean = null;
		String name = "MA";
		String forename1 = "Ngim Mui";
		String forename2 = "Julie";
		Calendar calendar = Calendar.getInstance();
		calendar.set(1968, 7, 15);
		Date birthdate = calendar.getTime();
		boolean sex = false;
		MdoTableAsEnumDto title = new MdoTableAsEnumDto();
		try {
			title = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(1L, userContext);
		} catch (MdoException e) {
			fail("Could not found the user title code.");
		}
		byte[] picture = { (byte) 0x01, (byte) 0x02, (byte) 0x03 };
		Set<UserRestaurantDto> restaurants = new HashSet<UserRestaurantDto>();
		newBean = createNewBean(name, forename1, forename2, birthdate, sex, title, picture, restaurants);
		// Use the existing data in database
		UserRestaurantDto userRestaurant = new UserRestaurantDto();
		RestaurantDto restaurant = null;
		try {
			restaurant = (RestaurantDto) DefaultRestaurantsManager.getInstance().findByPrimaryKey(2L, userContext);
		} catch (MdoException e) {
			fail("Could not found the restaurant.");
		}
		userRestaurant.setRestaurant(restaurant);
		restaurants.add(userRestaurant);
		list.add(newBean);

		// Second element in the list
		name = "MA";
		forename1 = "Sui Bor";
		forename2 = "Brice";
		calendar = Calendar.getInstance();
		calendar.set(1969, 7, 15);
		birthdate = calendar.getTime();
		sex = true;
		picture = null;
		restaurants = null;
		newBean = createNewBean(name, forename1, forename2, birthdate, sex, title, picture, restaurants);
		list.add(newBean);

		return list;
	}

	@Override
	public void doUpdate() {
		IMdoDtoBean newBean = null;
		String name = "MA";
		String forename1 = "Sui Tao";
		String forename2 = "Edouard";
		Calendar calendar = Calendar.getInstance();
		calendar.set(1967, 7, 15);
		Date birthdate = calendar.getTime();
		boolean sex = true;
		MdoTableAsEnumDto title = new MdoTableAsEnumDto();
		try {
			title = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(1L, userContext);
		} catch (MdoException e) {
			fail("Could not found the user title code.");
		}
		byte[] picture = null;
		Set<UserRestaurantDto> restaurants = new HashSet<UserRestaurantDto>();
		newBean = createNewBean(name, forename1, forename2, birthdate, sex, title, picture, restaurants);
		// Use the existing data in database
		UserRestaurantDto userRestaurant = new UserRestaurantDto();
		RestaurantDto restaurant = null;
		try {
			restaurant = (RestaurantDto) DefaultRestaurantsManager.getInstance().findByPrimaryKey(2L, userContext);
		} catch (MdoException e) {
			fail("Could not found the restaurant.");
		}
		userRestaurant.setRestaurant(restaurant);
		restaurants.add(userRestaurant);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean, userContext);
			assertTrue("IMdoBean must be instance of " + UserDto.class, beanToBeUpdated instanceof UserDto);
			UserDto castedBean = (UserDto) beanToBeUpdated;
			assertNotNull("User ID must not be null", castedBean.getId());
			assertNotNull("User restaurants must not be null", castedBean.getRestaurants());
			assertEquals("User restaurants size must be 1", restaurants.size(), castedBean.getRestaurants().size());
			// Update the created bean
			castedBean.setPicture(picture);
			// Use the existing data in database
			restaurants.clear();
			userRestaurant = new UserRestaurantDto();
			restaurant = new RestaurantDto();
			restaurant.setId(1L);
			userRestaurant.setRestaurant(restaurant);
			restaurants.add(userRestaurant);
			userRestaurant = new UserRestaurantDto();
			restaurant = new RestaurantDto();
			restaurant.setId(2L);
			userRestaurant.setRestaurant(restaurant);
			restaurants.add(userRestaurant);
			castedBean.setRestaurants(restaurants);
			this.getInstance().update(castedBean, userContext);
			// Reload the modified bean
			UserDto updatedBean = new UserDto();
			updatedBean.setId(castedBean.getId());
			updatedBean = (UserDto) this.getInstance().load(updatedBean, userContext);
			assertNotNull("User restaurants must not be null", updatedBean.getRestaurants());
			assertEquals("User restaurants size must be 2", restaurants.size(), updatedBean.getRestaurants().size());
			
			this.getInstance().delete(updatedBean, userContext);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		Set<UserRestaurantDto> restaurants = new HashSet<UserRestaurantDto>();
		UserRestaurantDto userRestaurant = new UserRestaurantDto();
		RestaurantDto restaurant = new RestaurantDto();
		restaurant.setId(1L);
		userRestaurant.setRestaurant(restaurant);
		restaurants.add(userRestaurant);
		userRestaurant = new UserRestaurantDto();
		restaurant = new RestaurantDto();
		restaurant.setId(2L);
		userRestaurant.setRestaurant(restaurant);
		restaurants.add(userRestaurant);
		System.out.println(restaurants.size());
	}

	@Override
	public void doProcessList() {
		UsersManagerViewBean viewBean = new UsersManagerViewBean();
		try {
			this.getInstance().processList(viewBean, DefaultAdministrationManagerTest.userContext);
			assertNotNull("Main list not be null", viewBean.getList());
			assertFalse("Main list not be empty", viewBean.getList().isEmpty());
			assertNotNull("Restaurants list not be null", viewBean.getRestaurants());
			assertFalse("Restaurants list not be empty", viewBean.getRestaurants().isEmpty());
			assertNotNull("Titles list not be null", viewBean.getTitles());
			assertFalse("Titles list not be empty", viewBean.getTitles().isEmpty());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof IUsersManager);
		assertTrue(this.getInstance() instanceof DefaultUsersManager);
	}

	private IMdoDtoBean createNewBean(String name, String forename1, String forename2, Date birthdate, boolean sex, 
			MdoTableAsEnumDto title, byte[] picture, Set<UserRestaurantDto> restaurants) {
		UserDto newBean = new UserDto();
		newBean.setName(name);
		newBean.setForename1(forename1);
		newBean.setForename2(forename2);
		newBean.setBirthdate(birthdate);
		newBean.setSex(sex);
		newBean.setTitle(title);
		newBean.setPicture(picture);
		newBean.setRestaurants(restaurants);
		return newBean;
	}

}
