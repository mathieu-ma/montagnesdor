package fr.mch.mdo.restaurant.dao.users.hibernate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.DaoServicesFactory;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.beans.User;
import fr.mch.mdo.restaurant.dao.beans.UserRestaurant;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServicesTestCase;
import fr.mch.mdo.restaurant.dao.users.IUsersDao;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultUsersDaoTest extends DefaultDaoServicesTestCase 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultUsersDaoTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultUsersDaoTest.class);
	}

	protected IDaoServices getInstance() {
		return DefaultUsersDao.getInstance();
	}

	protected IMdoBean createNewBean() {
		String name = "MA";
		String forename1 = "Chhui Huy";
		String forename2 = "Mathieu";
		Calendar calendar = Calendar.getInstance();
		calendar.set(1970, 7, 15);
		Date birthdate = calendar.getTime();
		boolean sex = true;
		// Use the existing data in database
		MdoTableAsEnum title = new MdoTableAsEnum();
		try {
			title = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(DefaultDaoServicesTestCase.ENUM_USER_TITLE_ID_0);
		} catch (MdoException e) {
			fail("Could not found the user title code.");
		}
		byte[] picture = { (byte) 0x01, (byte) 0x02 };
		Set<UserRestaurant> restaurants = new HashSet<UserRestaurant>();
		return createNewBean(name, forename1, forename2, birthdate, sex, title, picture, restaurants);
	}

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
		MdoTableAsEnum title = new MdoTableAsEnum();
		try {
			title = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(DefaultDaoServicesTestCase.ENUM_USER_TITLE_ID_0);
		} catch (MdoException e) {
			fail("Could not found the user title code.");
		}
		byte[] picture = { (byte) 0x01, (byte) 0x02, (byte) 0x03 };
		Set<UserRestaurant> restaurants = new HashSet<UserRestaurant>();
		newBean = createNewBean(name, forename1, forename2, birthdate, sex, title, picture, restaurants);
		// Use the existing data in database
		UserRestaurant userRestaurant = new UserRestaurant();
		Restaurant restaurant = null;
		try {
			restaurant = (Restaurant) DaoServicesFactory.getRestaurantsDao().findByPrimaryKey(2L);
		} catch (MdoException e) {
			fail("Could not found the restaurant.");
		}
		userRestaurant.setRestaurant(restaurant);
		((User) newBean).addRestaurant(userRestaurant);
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

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof IUsersDao);
		assertTrue(this.getInstance() instanceof DefaultUsersDao);
	}

	@Override
	public void doFindByUniqueKey() {
		assertTrue("Nothing to check", Boolean.TRUE);
	}

	@Override
	public void doUpdate() {
		IMdoBean newBean = null;
		String name = "MA";
		String forename1 = "Sui Tao";
		String forename2 = "Edouard";
		Calendar calendar = Calendar.getInstance();
		calendar.set(1967, 7, 15);
		Date birthdate = calendar.getTime();
		boolean sex = true;
		MdoTableAsEnum title = new MdoTableAsEnum();
		try {
			title = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(DefaultDaoServicesTestCase.ENUM_USER_TITLE_ID_0);
		} catch (MdoException e) {
			fail("Could not found the user title code.");
		}
		byte[] picture = { (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05 };
		Set<UserRestaurant> restaurants = new HashSet<UserRestaurant>();
		newBean = createNewBean(name, forename1, forename2, birthdate, sex, title, picture, restaurants);
		// Use the existing data in database
		UserRestaurant userRestaurant = new UserRestaurant();
		Restaurant restaurant = null;
		try {
			restaurant = (Restaurant) DaoServicesFactory.getRestaurantsDao().findByPrimaryKey(2L);
		} catch (MdoException e) {
			fail("Could not found the restaurant.");
		}
		userRestaurant.setRestaurant(restaurant);
		((User) newBean).addRestaurant(userRestaurant);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + User.class, beanToBeUpdated instanceof User);
			User castedBean = (User) beanToBeUpdated;
			assertNotNull("User ID must not be null", castedBean.getId());
			assertFalse("User must not be deleted", castedBean.isDeleted());
			// Update the created bean
			picture = new byte[] { (byte) 0xAA };
			castedBean.setPicture(picture);
			// Use the existing data in database
			userRestaurant = new UserRestaurant();
			restaurant = new Restaurant();
			restaurant.setId(1L);
			userRestaurant.setRestaurant(restaurant);
			castedBean.addRestaurant(userRestaurant);
			castedBean.setDeleted(true);
			this.getInstance().update(castedBean);
			// Reload the modified bean
			User updatedBean = new User();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertNotNull("User picture must not be null", updatedBean.getPicture());
			assertEquals("User picture size must be equals to 1", 1, updatedBean.getPicture().length);
			assertEquals("User picture data must be equals to 0xAA", (byte) 0xAA, updatedBean.getPicture()[0]);
			assertNotNull("User restaurants must not be null", updatedBean.getRestaurants());
			assertEquals("User restaurants size must be 1", restaurants.size(), updatedBean.getRestaurants().size());
			assertTrue("User must be deleted", castedBean.isDeleted());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Override
	public void doUpdateFieldsByKeysSpecific() {
		IMdoBean newBean = null;
		String name = "MA";
		String forename1 = "Sui Tao";
		String forename2 = "Edouard";
		Calendar calendar = Calendar.getInstance();
		calendar.set(1967, 7, 15);
		Date birthdate = calendar.getTime();
		boolean sex = true;
		MdoTableAsEnum title = new MdoTableAsEnum();
		try {
			title = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(DefaultDaoServicesTestCase.ENUM_USER_TITLE_ID_0);
		} catch (MdoException e) {
			fail("Could not found the user title code.");
		}
		byte[] picture = { (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05 };
		Set<UserRestaurant> restaurants = new HashSet<UserRestaurant>();
		newBean = createNewBean(name, forename1, forename2, birthdate, sex, title, picture, restaurants);
		// Use the existing data in database
		UserRestaurant userRestaurant = new UserRestaurant();
		Restaurant restaurant = null;
		try {
			restaurant = (Restaurant) DaoServicesFactory.getRestaurantsDao().findByPrimaryKey(2L);
		} catch (MdoException e) {
			fail("Could not found the restaurant.");
		}
		userRestaurant.setRestaurant(restaurant);
		((User) newBean).addRestaurant(userRestaurant);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + User.class, beanToBeUpdated instanceof User);
			User castedBean = (User) beanToBeUpdated;
			assertNotNull("User ID must not be null", castedBean.getId());
			assertFalse("User must not be deleted", castedBean.isDeleted());

			// Update the created bean
			Map<String, Object> fields = new HashMap<String, Object>();
			Map<String, Object> keys = new HashMap<String, Object>();
			castedBean.setBirthdate(new Date());
			castedBean.setForename1("forename1");
			castedBean.setForename2("forename2");
			castedBean.setName("name");
			castedBean.setSex(true);
			fields.put("birthdate", castedBean.getBirthdate());
			fields.put("forename1", castedBean.getForename1());
			fields.put("forename2", castedBean.getForename2());
			fields.put("name", castedBean.getName());
			fields.put("sex", castedBean.isSex());
			keys.put("id", castedBean.getId());
			this.getInstance().updateFieldsByKeys(fields, keys);
			// Reload the modified bean
			User updatedBean = (User) createNewBean();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
//			assertEquals("Check updated fields ", castedBean.getBirthdate(), updatedBean.getBirthdate());
			assertEquals("Check updated fields ", castedBean.getForename1(), updatedBean.getForename1());
			assertEquals("Check updated fields ", castedBean.getForename2(), updatedBean.getForename2());
			assertEquals("Check updated fields ", castedBean.getName(), updatedBean.getName());
			assertEquals("Check updated fields ", castedBean.isSex(), updatedBean.isSex());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
		}
	}

	private IMdoBean createNewBean(String name, String forename1, String forename2, Date birthdate, boolean sex, MdoTableAsEnum title, byte[] picture,
			Set<UserRestaurant> restaurants) {
		User newBean = new User();
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
