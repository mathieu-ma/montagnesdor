package fr.mch.mdo.restaurant.dao.locales.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.Locale;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServicesTestCase;
import fr.mch.mdo.restaurant.dao.locales.ILocalesDao;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultLocalesDaoTest extends DefaultDaoServicesTestCase 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultLocalesDaoTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultLocalesDaoTest.class);
	}

	@Override
	protected IDaoServices getInstance() {
		return DefaultLocalesDao.getInstance();
	}

	@Override
	protected IMdoBean createNewBean() {
		return createNewBean(java.util.Locale.ENGLISH.getLanguage());
	}

	@Override
	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		list.add(createNewBean(java.util.Locale.JAPANESE.getLanguage()));
		list.add(createNewBean(java.util.Locale.GERMAN.getLanguage()));
		return list;
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof ILocalesDao);
		assertTrue(this.getInstance() instanceof DefaultLocalesDao);
	}

	@Override
	public void doFindByUniqueKey() {
		// French locale was created at HSQLDB startup
		String localeCode = java.util.Locale.FRENCH.getLanguage();
		try {
			IMdoBean bean = this.getInstance().findByUniqueKey(localeCode);
			assertTrue("IMdoBean must be instance of " + Locale.class, bean instanceof Locale);
			Locale castedBean = (Locale) bean;
			assertNotNull("Locale language must not be null", castedBean.getLanguage());
			assertEquals("Locale language must be equals to unique key", localeCode, castedBean.getLanguage());
			assertFalse("Locale must not be deleted", castedBean.isDeleted());
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	@Override
	public void doUpdate() {
		String localeCodeToBeUpdated = java.util.Locale.GERMAN.getLanguage();
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(createNewBean(localeCodeToBeUpdated));
			assertTrue("IMdoBean must be instance of " + Locale.class, beanToBeUpdated instanceof Locale);
			Locale castedBean = (Locale) beanToBeUpdated;
			assertNotNull("Locale language must not be null", castedBean.getLanguage());
			assertEquals("Locale language must be equals to unique key", localeCodeToBeUpdated, castedBean.getLanguage());
			assertFalse("Locale must not be deleted", castedBean.isDeleted());
			// Update the created bean
			castedBean.setLanguage(java.util.Locale.ITALIAN.getLanguage());
			castedBean.setDeleted(true);
			this.getInstance().update(castedBean);
			// Reload the modified bean
			Locale updatedBean = new Locale();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertNotNull("Locale language must not be null", updatedBean.getLanguage());
			assertEquals("Locale language must be equals to unique key", java.util.Locale.ITALIAN.getLanguage(), updatedBean.getLanguage());
			assertTrue("Locale must be deleted", updatedBean.isDeleted());
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	@Override
	public void doUpdateFieldsAndDeleteByKeysSpecific() {
		String localeCodeToBeUpdated = java.util.Locale.JAPAN.getLanguage();
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(createNewBean(localeCodeToBeUpdated));
			assertTrue("IMdoBean must be instance of " + Locale.class, beanToBeUpdated instanceof Locale);
			Locale castedBean = (Locale) beanToBeUpdated;
			assertNotNull("Locale language must not be null", castedBean.getLanguage());
			assertEquals("Locale language must be equals to unique key", localeCodeToBeUpdated, castedBean.getLanguage());
			assertFalse("Locale must not be deleted", castedBean.isDeleted());

			// Update the created bean
			Map<String, Object> fields = new HashMap<String, Object>();
			Map<String, Object> keys = new HashMap<String, Object>();
			castedBean.setLanguage(java.util.Locale.KOREA.getLanguage());
			fields.put("language", castedBean.getLanguage());
			keys.put("id", castedBean.getId());
			this.getInstance().updateFieldsByKeys(fields, keys);
			// Reload the modified bean
			Locale updatedBean = (Locale) createNewBean();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertEquals("Check updated fields ", castedBean.getLanguage(), updatedBean.getLanguage());

			// Delete the bean by keys
			// Take the fields as keys
			super.doDeleteByKeysSpecific(updatedBean, fields);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
		}
	}

	private IMdoBean createNewBean(String language) {
		Locale newBean = new Locale();
		newBean.setLanguage(language);
		return newBean;
	}
}
