package fr.mch.mdo.restaurant.dao.users.hibernate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.DaoServicesFactory;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.authentication.AuthenticationPasswordLevel;
import fr.mch.mdo.restaurant.dao.beans.Locale;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.beans.User;
import fr.mch.mdo.restaurant.dao.beans.UserAuthentication;
import fr.mch.mdo.restaurant.dao.beans.UserAuthenticationJaas;
import fr.mch.mdo.restaurant.dao.beans.UserLocale;
import fr.mch.mdo.restaurant.dao.beans.UserRole;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServicesTestCase;
import fr.mch.mdo.restaurant.dao.users.IUserAuthenticationsDao;
import fr.mch.mdo.restaurant.exception.MdoException;

public class DefaultUserAuthenticationsDaoTest extends DefaultDaoServicesTestCase {
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultUserAuthenticationsDaoTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultUserAuthenticationsDaoTest.class);
	}

	protected IDaoServices getInstance() {
		return DefaultUserAuthenticationsDao.getInstance();
	}

	protected IMdoBean createNewBean() {
		// Use the existing data in database
		Locale printingLocale = new Locale();
		printingLocale.setId(1L);
		// Use the existing data in database
		User user = new User();
		user.setId(1L);
		// Use the existing data in database
		Restaurant restaurant = new Restaurant();
		restaurant.setId(1L);
		// Use the existing data in database
		UserRole userRole = new UserRole();
		try {
			userRole = (UserRole) DaoServicesFactory.getUserRolesDao().findByPrimaryKey(1L);
		} catch (MdoException e) {
			fail("Could not found the user role.");
		}
		String login = "mma";
		String password = "mma";
		String levelPass1 = "mma1";
		String levelPass2 = "mma2";
		String levelPass3 = "mma3";
		Set<UserLocale> locales = new HashSet<UserLocale>();
		UserAuthentication newBean = (UserAuthentication) createNewBean(printingLocale, user, restaurant, userRole, login, password, levelPass1, levelPass2, levelPass3, locales);

		UserLocale userLocale = new UserLocale();
		Locale locale = new Locale();
		try {
			locale = (Locale) DaoServicesFactory.getLocalesDao().findByPrimaryKey(1L);
		} catch (MdoException e) {
			fail("Could not found the locale.");
		}
		userLocale.setLocale(locale);
		newBean.addLocale(userLocale);

		return newBean;
	}

	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		// Use the existing data in database
		Locale printingLocale = null;
		try {
			printingLocale = (Locale) DaoServicesFactory.getLocalesDao().findByPrimaryKey(1L);
		} catch (MdoException e) {
			fail("Could not found the printing locale.");
		}
		// Use the existing data in database
		User user = null;
		try {
			user = (User) DaoServicesFactory.getUsersDao().findByPrimaryKey(1L);
		} catch (MdoException e) {
			fail("Could not found the user.");
		}
		// Use the existing data in database
		Restaurant restaurant = null;
		try {
			restaurant = (Restaurant) DaoServicesFactory.getRestaurantsDao().findByPrimaryKey(1L);
		} catch (MdoException e) {
			fail("Could not found the restaurant.");
		}
		// Use the existing data in database
		UserRole userRole = new UserRole();
		try {
			userRole = (UserRole) DaoServicesFactory.getUserRolesDao().findByPrimaryKey(1L);
		} catch (MdoException e) {
			fail("Could not found the user role.");
		}
		String login = "mathieu.ma";
		String password = "mathieu.ma";
		String levelPass1 = "mathieu.ma1";
		String levelPass2 = "mathieu.ma2";
		String levelPass3 = "mathieu.ma3";
		Set<UserLocale> locales = null;
		list.add(createNewBean(printingLocale, user, restaurant, userRole, login, password, levelPass1, levelPass2, levelPass3, locales));
		return list;
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof IUserAuthenticationsDao);
		assertTrue(this.getInstance() instanceof DefaultUserAuthenticationsDao);
	}

	@Override
	public void doFindByUniqueKey() {
		// mch login was created at HSQLDB startup
		String login = "mch";
		try {
			IMdoBean bean = this.getInstance().findByUniqueKey(login);
			assertNotNull("IMdoBean must not be null", bean);
			assertTrue("IMdoBean must be instance of " + UserAuthentication.class, bean instanceof UserAuthentication);
			UserAuthentication castedBean = (UserAuthentication) bean;
			assertEquals("UserAuthentication login must be equals to unique key", login, castedBean.getLogin());
			assertFalse("UserAuthentication must not be deleted", castedBean.isDeleted());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testfindByLogin() {
		// mch login was created at HSQLDB startup
		String login = "mch";
		try {
			IMdoBean bean = ((IUserAuthenticationsDao) this.getInstance()).findByLogin(login);
			assertNotNull("IMdoBean must not be null", bean);
			assertTrue("IMdoBean must be instance of " + UserAuthenticationJaas.class, bean instanceof UserAuthenticationJaas);
			UserAuthenticationJaas castedBean = (UserAuthenticationJaas) bean;
			assertEquals("UserAuthenticationJaas login must be equals to unique key", login, castedBean.getLogin());
			assertFalse("UserAuthenticationJaas must not be deleted", castedBean.isDeleted());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Override
	public void doUpdate() {
		IMdoBean newBean = null;
		// Use the existing data in database
		Locale printingLocale = new Locale();
		printingLocale.setId(1L);
		// Use the existing data in database
		User user = new User();
		user.setId(1L);
		// Use the existing data in database
		Restaurant restaurant = new Restaurant();
		restaurant.setId(1L);
		// Use the existing data in database
		UserRole userRole = new UserRole();
		userRole.setId(1L);
		String login = "lchristophe";
		String password = "lchristophe";
		String levelPass1 = "lchristophe1";
		String levelPass2 = "lchristophe2";
		String levelPass3 = "lchristophe3";
		Set<UserLocale> locales = new HashSet<UserLocale>();

		newBean = createNewBean(printingLocale, user, restaurant, userRole, login, password, levelPass1, levelPass2, levelPass3, locales);

		UserLocale userLocale = new UserLocale();
		Locale locale = new Locale();
		try {
			locale = (Locale) DaoServicesFactory.getLocalesDao().findByPrimaryKey(1L);
		} catch (MdoException e) {
			fail("Could not found the locale.");
		}
		userLocale.setLocale(locale);
		((UserAuthentication) newBean).addLocale(userLocale);

		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + UserAuthentication.class, beanToBeUpdated instanceof UserAuthentication);
			UserAuthentication castedBean = (UserAuthentication) beanToBeUpdated;
			assertEquals("UserAuthentication login must be equals to the inserted value", login, castedBean.getLogin());
			assertNotNull("UserAuthentication locales must not be null", castedBean.getLocales());
			assertEquals("Check UserAuthentication locales size", locales.size(), castedBean.getLocales().size());
			assertFalse("UserAuthentication must not be deleted", castedBean.isDeleted());
			// Update the created bean
			login = "kiki";
			castedBean.setLogin(login);

			castedBean.getLocales().clear();
			Long backupId = userLocale.getId();
			userLocale = new UserLocale();
			locale = new Locale();
			locale.setId(1L);
			userLocale.setLocale(locale);
			// For updating, if not Hibernate will insert
			userLocale.setId(backupId);
			castedBean.addLocale(userLocale);
			userLocale = new UserLocale();
			locale = new Locale();
			locale.setId(2L);
			userLocale.setLocale(locale);
			castedBean.addLocale(userLocale);
			castedBean.setDeleted(true);
			this.getInstance().update(castedBean);
			// Reload the modified bean
			UserAuthentication updatedBean = new UserAuthentication();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertEquals("UserAuthentication login must be equals to the updated value", login, updatedBean.getLogin());
			assertNotNull("UserAuthentication locales must not be null", updatedBean.getLocales());
			assertEquals("Check UserAuthentication locales size", castedBean.getLocales().size(), updatedBean.getLocales().size());
			assertTrue("UserAuthentication must be deleted", castedBean.isDeleted());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	private IMdoBean createNewBean(Locale printingLocale, User user, Restaurant restaurant, UserRole userRole, String login, String password, String levelPass1, String levelPass2,
			String levelPass3, Set<UserLocale> locales) {
		UserAuthentication newBean = new UserAuthentication();
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

	public void testChangePassword() {
		try {
			assertTrue(this.getInstance() instanceof IUserAuthenticationsDao);
			IUserAuthenticationsDao iUserAuthenticationsDao = (IUserAuthenticationsDao) this.getInstance();

			for (int i = 0; i < 2; i++) {
				if (i == 0) {
					IMdoBean bean = this.getInstance().findByPrimaryKey(1L);
					assertNotNull("IMdoBean must not be null", bean);
					assertTrue("IMdoBean must be instance of " + UserAuthentication.class, bean instanceof UserAuthentication);
					UserAuthentication castedBean = (UserAuthentication) bean;
					for (AuthenticationPasswordLevel levelPassword : AuthenticationPasswordLevel.values()) {
						String newPassword = levelPassword.name();
						iUserAuthenticationsDao.changePassword(castedBean, levelPassword, newPassword);
						UserAuthentication updatedbean = (UserAuthentication) this.getInstance().findByPrimaryKey(1L);
						assertEquals("Check changed password", newPassword, levelPassword.getPassword(updatedbean));
					}
				} else {
					for (AuthenticationPasswordLevel levelPassword : AuthenticationPasswordLevel.values()) {
						String newPassword = levelPassword.name();
						iUserAuthenticationsDao.changePassword(1L, levelPassword, newPassword);
						UserAuthentication updatedbean = (UserAuthentication) this.getInstance().findByPrimaryKey(1L);
						assertEquals("Check changed password", newPassword, levelPassword.getPassword(updatedbean));
					}
				}
			}
		} catch (MdoException e) {
			fail(e.getMessage());
		}
	}

	public void testChangePrintingLanguage() {
		try {
			assertTrue(this.getInstance() instanceof IUserAuthenticationsDao);
			IUserAuthenticationsDao iUserAuthenticationsDao = (IUserAuthenticationsDao) this.getInstance();

			UserAuthentication castedBean = null;
			for (int i = 0; i < 2; i++) {
				Locale printingLocale = new Locale();
				printingLocale.setId(1L);
				if (i == 0) {
					IMdoBean bean = this.getInstance().findByPrimaryKey(1L);
					assertNotNull("IMdoBean must not be null", bean);
					assertTrue("IMdoBean must be instance of " + UserAuthentication.class, bean instanceof UserAuthentication);
					castedBean = (UserAuthentication) bean;
					iUserAuthenticationsDao.changePrintingLanguage(castedBean, printingLocale);
				} else {
					iUserAuthenticationsDao.changePrintingLanguage(1L, printingLocale);
				}
				UserAuthentication updatedbean = (UserAuthentication) this.getInstance().findByPrimaryKey(1L);
				assertNotNull("UserAuthentication PrintingLocale must not be null", updatedbean.getPrintingLocale());
				assertEquals("Check changed printing language", printingLocale.getId(), updatedbean.getPrintingLocale().getId());
			}

		} catch (MdoException e) {
			fail(e.getMessage());
		}
	}
}
