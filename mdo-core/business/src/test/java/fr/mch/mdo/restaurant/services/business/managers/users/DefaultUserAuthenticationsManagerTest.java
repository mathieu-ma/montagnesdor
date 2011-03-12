package fr.mch.mdo.restaurant.services.business.managers.users;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.LocaleDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.dto.beans.UserAuthenticationDto;
import fr.mch.mdo.restaurant.dto.beans.UserAuthenticationsManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.UserDto;
import fr.mch.mdo.restaurant.dto.beans.UserLocaleDto;
import fr.mch.mdo.restaurant.dto.beans.UserRoleDto;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.DefaultAdministrationManagerTest;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;
import fr.mch.mdo.restaurant.services.business.managers.locales.DefaultLocalesManager;
import fr.mch.mdo.restaurant.services.business.managers.restaurants.DefaultRestaurantsManager;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultUserAuthenticationsManagerTest extends DefaultAdministrationManagerTest 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultUserAuthenticationsManagerTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultUserAuthenticationsManagerTest.class);
	}

	@Override
	protected IAdministrationManager getInstance() {
		return DefaultUserAuthenticationsManager.getInstance();
	}

	@Override
	protected IMdoDtoBean createNewBean() {		// Use the existing data in database
		LocaleDto printingLocale = new LocaleDto();
		printingLocale.setId(1L);
		// Use the existing data in database
		UserDto user = new UserDto();
		user.setId(1L);
		// Use the existing data in database
		RestaurantDto restaurant = new RestaurantDto();
		restaurant.setId(1L);
		// Use the existing data in database
		UserRoleDto userRole = new UserRoleDto();
		try {
			userRole = (UserRoleDto) DefaultUserRolesManager.getInstance().findByPrimaryKey(1L, userContext);
		} catch (MdoException e) {
			fail("Could not found the user role.");
		}
		String login = "mma";
		String password = "mma";
		String levelPass1 = "mma1";
		String levelPass2 = "mma2";
		String levelPass3 = "mma3";
		Set<UserLocaleDto> locales = new HashSet<UserLocaleDto>();
		UserAuthenticationDto newBean = (UserAuthenticationDto) createNewBean(printingLocale, user, restaurant, userRole, login, password, levelPass1, levelPass2, levelPass3, locales);

		UserLocaleDto userLocale = new UserLocaleDto();
		LocaleDto locale = new LocaleDto();
		locale.setId(1L);
		userLocale.setLocale(locale);
		locales.add(userLocale);

		return newBean;
	}

	@Override
	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		// Use the existing data in database
		LocaleDto printingLocale = null;
		try {
			printingLocale = (LocaleDto) DefaultLocalesManager.getInstance().findByPrimaryKey(1L, userContext);
		} catch (MdoException e) {
			fail("Could not found the printing locale.");
		}
		// Use the existing data in database
		UserDto user = null;
		try {
			user = (UserDto) DefaultUsersManager.getInstance().findByPrimaryKey(1L, userContext);
		} catch (MdoException e) {
			fail("Could not found the user.");
		}
		// Use the existing data in database
		RestaurantDto restaurant = null;
		try {
			restaurant = (RestaurantDto) DefaultRestaurantsManager.getInstance().findByPrimaryKey(1L, userContext);
		} catch (MdoException e) {
			fail("Could not found the restaurant.");
		}
		// Use the existing data in database
		UserRoleDto userRole = new UserRoleDto();
		try {
			userRole = (UserRoleDto) DefaultUserRolesManager.getInstance().findByPrimaryKey(1L, userContext);
		} catch (MdoException e) {
			fail("Could not found the user role.");
		}
		String login = "mathieu.ma";
		String password = "mathieu.ma";
		String levelPass1 = "mathieu.ma1";
		String levelPass2 = "mathieu.ma2";
		String levelPass3 = "mathieu.ma3";
		Set<UserLocaleDto> locales = null;
		list.add(createNewBean(printingLocale, user, restaurant, userRole, login, password, levelPass1, levelPass2, levelPass3, locales));
		return list;
	}

	@Override
	public void doUpdate() {
		IMdoDtoBean newBean = null;
		// Use the existing data in database
		LocaleDto printingLocale = new LocaleDto();
		printingLocale.setId(1L);
		// Use the existing data in database
		UserDto user = new UserDto();
		user.setId(1L);
		// Use the existing data in database
		RestaurantDto restaurant = new RestaurantDto();
		restaurant.setId(1L);
		// Use the existing data in database
		UserRoleDto userRole = new UserRoleDto();
		userRole.setId(1L);
		String login = "lchristophe";
		String password = "lchristophe";
		String levelPass1 = "lchristophe1";
		String levelPass2 = "lchristophe2";
		String levelPass3 = "lchristophe3";
		Set<UserLocaleDto> locales = new HashSet<UserLocaleDto>();

		newBean = createNewBean(printingLocale, user, restaurant, userRole, login, password, levelPass1, levelPass2, levelPass3, locales);

		UserLocaleDto userLocale = new UserLocaleDto();
		LocaleDto locale = new LocaleDto();
		try {
			locale = (LocaleDto) DefaultLocalesManager.getInstance().findByPrimaryKey(1L, userContext);
		} catch (MdoException e) {
			fail("Could not found the locale.");
		}
		userLocale.setLocale(locale);
		locales.add(userLocale);

		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean, userContext);
			assertTrue("IMdoBean must be instance of " + UserAuthenticationDto.class, beanToBeUpdated instanceof UserAuthenticationDto);
			UserAuthenticationDto castedBean = (UserAuthenticationDto) beanToBeUpdated;
			assertEquals("UserAuthentication login must be equals to the inserted value", login, castedBean.getLogin());
			assertNotNull("UserAuthentication locales must not be null", castedBean.getLocales());
			assertEquals("Check UserAuthentication locales size", locales.size(), castedBean.getLocales().size());
			// Update the created bean
			login = "kiki";
			castedBean.setLogin(login);

			locales.clear();
			Long backupId = castedBean.getLocales().iterator().next().getId();
			userLocale = new UserLocaleDto();
			locale = new LocaleDto();
			locale.setId(1L);
			userLocale.setLocale(locale);
			// For updating, if not Hibernate will insert
			userLocale.setId(backupId);
			locales.add(userLocale);
			
			userLocale = new UserLocaleDto();
			locale = new LocaleDto();
			locale.setId(2L);
			userLocale.setLocale(locale);
			locales.add(userLocale);
			castedBean.setLocales(locales);
			this.getInstance().update(castedBean, userContext);
			// Reload the modified bean
			UserAuthenticationDto updatedBean = new UserAuthenticationDto();
			updatedBean.setId(castedBean.getId());
			updatedBean = (UserAuthenticationDto) this.getInstance().load(updatedBean, userContext);
			assertEquals("UserAuthentication login must be equals to the updated value", login, updatedBean.getLogin());
			assertNotNull("UserAuthentication locales must not be null", updatedBean.getLocales());
			assertEquals("Check UserAuthentication locales size", castedBean.getLocales().size(), updatedBean.getLocales().size());
			this.getInstance().delete(updatedBean, userContext);
		} catch (Exception e) {
			fail(e.getLocalizedMessage());
		}
	}

	@Override
	public void doProcessList() {
		UserAuthenticationsManagerViewBean viewBean = new UserAuthenticationsManagerViewBean();
		try {
			this.getInstance().processList(viewBean, DefaultAdministrationManagerTest.userContext);
			assertNotNull("Main list not be null", viewBean.getList());
			assertFalse("Main list must not be empty", viewBean.getList().isEmpty());

			assertNotNull("Languages list not be null", viewBean.getLanguages());
			assertFalse("setLanguages list must not be empty", viewBean.getLanguages().isEmpty());

			assertNotNull("Languages list not be null", viewBean.getLanguages());
			assertFalse("Languages list must not be empty", viewBean.getLanguages().isEmpty());

			assertNotNull("Users list not be null", viewBean.getUsers());
			assertFalse("Users list must not be empty", viewBean.getUsers().isEmpty());

			assertNotNull("UserRoles list not be null", viewBean.getUserRoles());
			assertFalse("UserRoles list must not be empty", viewBean.getUserRoles().isEmpty());

			assertNotNull("UserRestaurants list not be null", viewBean.getUserRestaurants());
			assertFalse("UserRestaurants list must not be empty", viewBean.getUserRestaurants().isEmpty());

		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof IUserAuthenticationsManager);
		assertTrue(this.getInstance() instanceof DefaultUserAuthenticationsManager);
	}

	public void testFindByLogin() {
		IUserAuthenticationsManager manager = (IUserAuthenticationsManager) this.getInstance();
		String login = null;
		try {
			manager.findByLogin(login, DefaultAdministrationManagerTest.userContext);
		} catch (MdoException e) {
			assertTrue("Login must not be null", true); 
		}
		try {
			login = "toto";
			assertNull("User Authentication must be null", manager.findByLogin(login, DefaultAdministrationManagerTest.userContext));
			login = "mch";
			IMdoDtoBean dtoBean = manager.findByLogin(login, DefaultAdministrationManagerTest.userContext);
			assertNotNull("User Authentication must NOT be null", dtoBean);
			assertTrue("User Authentication must be instance of UserAuthenticationDto", dtoBean instanceof UserAuthenticationDto);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
	}

	private IMdoDtoBean createNewBean(LocaleDto printingLocale, UserDto user, RestaurantDto restaurant, UserRoleDto userRole, 
			String login, String password, String levelPass1, String levelPass2, String levelPass3, 
			Set<UserLocaleDto> locales) {
		UserAuthenticationDto newBean = new UserAuthenticationDto();
		newBean.setPrintingLocale(printingLocale);
		newBean.setUser(user);
		newBean.setRestaurant(restaurant);
		newBean.setUserRole(userRole);
		newBean.setLogin(login);
		newBean.setPassword(password);
		newBean.setLevelPass1(levelPass1);
		newBean.setLevelPass2(levelPass2);
		newBean.setLevelPass3(levelPass3);
		newBean.setLocales(locales);
		return newBean;
	}

}
