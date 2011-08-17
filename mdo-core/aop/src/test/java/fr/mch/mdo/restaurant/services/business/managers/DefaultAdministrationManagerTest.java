package fr.mch.mdo.restaurant.services.business.managers;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.security.auth.Subject;

import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.LocaleDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.UserAuthenticationDto;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.users.DefaultUserAuthenticationsManager;
import fr.mch.mdo.test.MdoTestCase;

public abstract class DefaultAdministrationManagerTest extends MdoBusinessBasicTestCase 
{
	public static final String RESTAURANT_FIRST_REFERENCE = "10203040506070";

	
	protected NumberFormat decimalFormat = DecimalFormat.getInstance();
	{
		decimalFormat.setMaximumFractionDigits(4);
	}


	private IMdoDtoBean insertedBeanToBeDeleted = null;

	/** This value MUST exist in database. */
	protected Long primaryKey = 1L;

	protected static MdoUserContext userContext = null;
	protected static MdoUserContext getUserContext() {
		if (userContext == null) {
			userContext = new MdoUserContext(new Subject());
			LocaleDto currentLocale = new LocaleDto();
			currentLocale.setLanguageCode(Locale.FRANCE.getLanguage());
			userContext.setCurrentLocale(currentLocale);
			UserAuthenticationDto user = null;
			try {
				user = (UserAuthenticationDto) DefaultUserAuthenticationsManager.getInstance().findByPrimaryKey(1L, userContext, false);
			} catch (MdoException e) {
				fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
			}
			userContext.setUserAuthentication(user);
		}
		return userContext;
	}

	protected abstract IAdministrationManager getInstance();

	protected abstract IMdoDtoBean createNewBean();

	protected abstract List<IMdoBean> createListBeans();

	protected abstract void doProcessList();

	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultAdministrationManagerTest(String testName) {
		super(testName);
		getUserContext();
	}

	public void testAll() {
		this.doInsert();
		this.doUpdate();
		this.doLoad();
		this.doFindByPrimaryKey();
		this.doFindAll();
		this.doProcessList();
		this.doDelete();
	}

	public void doInsert() {
		try {
			IMdoDtoBean dtoBean = createNewBean();
			assertNotNull("dto Bean must not be null", dtoBean);
			assertNull("dto Bean id is null", dtoBean.getId());
			// Be careful to not insert data already exist in database
			insertedBeanToBeDeleted = this.getInstance().insert(dtoBean, userContext);
			assertNotNull("Bean id is not null now", insertedBeanToBeDeleted.getId());
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
	}

	public abstract void doUpdate();

	public void doLoad() {
		try {
			IMdoDtoBean bean = createNewBean();
			assertNull("Bean id is null", bean.getId());
			bean.setId(primaryKey);
			bean = getInstance().load(bean, userContext);
			assertNotNull("bean must not be null", bean);
			assertNotNull("Bean id is not null", bean.getId());
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
	}

	public void doFindByPrimaryKey(boolean... lazy) {
		try {
			IMdoDtoBean bean = getInstance().findByPrimaryKey(primaryKey, userContext);
			assertNotNull("Bean is not null", bean);
			assertNotNull("Bean id is not null", bean.getId());
			assertEquals("Bean id is equals to the searched id", primaryKey, bean.getId());
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
		if (lazy.length == 0) {
			doFindByPrimaryKey(false);
		}
	}

	public void doFindAll() {
		try {
			List<IMdoBean> beans = createListBeans();
			List<IMdoBean> insertedBeans = new ArrayList<IMdoBean>(beans.size());
			assertNotNull("beans must not be null", beans);
			if (!beans.isEmpty()) {
				for (IMdoBean bean : beans) {
					assertTrue("The bean must be an instance of IMdoDaoBean", bean instanceof IMdoDtoBean);
					// Create
					insertedBeans.add(getInstance().insert((IMdoDtoBean) bean, userContext));
				}
				List<IMdoDtoBean> list = getInstance().findAll(userContext);
				assertNotNull("list must not be null", list);
				assertFalse("list must not be empty", list.isEmpty());
				assertTrue("The findAll list size must be greateror equals than beans list size", list.size() >= beans.size());

				for (IMdoBean bean : insertedBeans) {
					boolean beanInFindAllList = false;
					for (IMdoBean iMdoBean : list) {
						assertTrue("The iMdoBean must be an instance of IMdoDtoBean", iMdoBean instanceof IMdoDtoBean);
						if (iMdoBean.equals(bean)) {
							beanInFindAllList = true;
							break;
						}
					}
					assertTrue("bean must be in the findAllList", beanInFindAllList);
					// Remove after created
					getInstance().delete((IMdoDtoBean) bean, userContext);
				}
			}
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
	}

	public void doDelete() {
		try {
			// Create bean to be deleted
			assertNotNull("Inserted bean must not be null", insertedBeanToBeDeleted);
			// Delete the created bean
			getInstance().delete(insertedBeanToBeDeleted, userContext);
			IMdoBean deletedBean = getInstance().findByPrimaryKey(insertedBeanToBeDeleted.getId(), userContext);
			assertNull("deletedBean must be null", deletedBean);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
	}

}