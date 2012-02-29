package fr.mch.mdo.restaurant.dao.products.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.DaoServicesFactory;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.Category;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServicesTestCase;
import fr.mch.mdo.restaurant.dao.products.ICategoriesDao;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultCategoriesDaoTest extends DefaultDaoServicesTestCase 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultCategoriesDaoTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultCategoriesDaoTest.class);
	}

	protected IDaoServices getInstance() {
		return DefaultCategoriesDao.getInstance();
	}

	protected IMdoBean createNewBean() {
		// Use the existing data in database
		MdoTableAsEnum code = new MdoTableAsEnum();
		try {
			code = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(17L);
		} catch (MdoException e) {
			fail("Could not found the category code.");
		}
		Map<Long, String> labels = null;
		return createNewBean(code, labels);
	}

	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		// Use the existing data in database
		MdoTableAsEnum code = new MdoTableAsEnum();
		try {
			code = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(18L);
		} catch (MdoException e) {
			fail("Could not found the category code.");
		}
		Map<Long, String> labels = new HashMap<Long, String>();
		Long localeId = 1L;
		String label = "Poisson";
		labels.put(localeId, label);
		list.add(createNewBean(code, labels));
		return list;
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof ICategoriesDao);
		assertTrue(this.getInstance() instanceof DefaultCategoriesDao);
	}

	@Override
	public void doFindByUniqueKey() {
		// MEAT code was created at HSQLDB startup
		String codeName = "MEAT";
		try {
			IMdoBean bean = this.getInstance().findByUniqueKey(codeName);
			assertTrue("IMdoBean must be instance of " + Category.class, bean instanceof Category);
			Category castedBean = (Category) bean;
			assertNotNull("Category code must not be null", castedBean.getCode());
			assertEquals("Category code must be equals to unique key", codeName, castedBean.getCode().getName());
			assertFalse("Category must not be deleted", castedBean.isDeleted());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Override
	public void doUpdate() {
		IMdoBean newBean = null;
		// Use the existing data in database
		MdoTableAsEnum code = new MdoTableAsEnum();
		try {
			code = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(19L);
		} catch (MdoException e) {
			fail("Could not found the category code.");
		}
		Map<Long, String> labels = new HashMap<Long, String>();
		Long localeId = 1L;
		String label = "Eau";
		labels.put(localeId, label);
		localeId = 2L;
		label = "Aqua";
		labels.put(localeId, label);
		newBean = createNewBean(code, labels);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + Category.class, beanToBeUpdated instanceof Category);
			Category castedBean = (Category) beanToBeUpdated;
			assertNotNull("Category code must not be null", castedBean.getCode());
			assertEquals("Category name must be equals to the inserted value", code.toString(), castedBean.getCode().toString());
			assertNotNull("Category labels must not be null", castedBean.getLabels());
			assertEquals("Check Category labels size", labels.size(), castedBean.getLabels().size());
			assertFalse("Category must not be deleted", castedBean.isDeleted());
			// Update the created bean
			labels.clear();
			localeId = 1L;
			label = "O2";
			labels.put(localeId, label);
			castedBean.setLabels(labels);
			castedBean.setDeleted(true);
			this.getInstance().update(castedBean);
			// Reload the modified bean
			Category updatedBean = new Category();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertNotNull("Category code must not be null", updatedBean.getCode());
			assertEquals("Category name must be equals to the updated value", code.toString(), updatedBean.getCode().toString());
			assertNotNull("Category labels must not be null", updatedBean.getLabels());
			assertEquals("Check Category labels size", labels.size(), updatedBean.getLabels().size());
			assertTrue("Category must be deleted", castedBean.isDeleted());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Override
	public void doUpdateFieldsByKeysSpecific() {
		IMdoBean newBean = null;
		// Use the existing data in database
		MdoTableAsEnum code = new MdoTableAsEnum();
		try {
			code = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(19L);
		} catch (MdoException e) {
			fail("Could not found the category code.");
		}
		Map<Long, String> labels = new HashMap<Long, String>();
		Long localeId = 1L;
		String label = "Eau";
		labels.put(localeId, label);
		localeId = 2L;
		label = "Aqua";
		labels.put(localeId, label);
		newBean = createNewBean(code, labels);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + Category.class, beanToBeUpdated instanceof Category);
			Category castedBean = (Category) beanToBeUpdated;
			assertNotNull("Category code must not be null", castedBean.getCode());
			assertEquals("Category name must be equals to the inserted value", code.toString(), castedBean.getCode().toString());
			assertNotNull("Category labels must not be null", castedBean.getLabels());
			assertEquals("Check Category labels size", labels.size(), castedBean.getLabels().size());
			assertFalse("Category must not be deleted", castedBean.isDeleted());

			// Update the created bean
			Map<String, Object> fields = new HashMap<String, Object>();
			Map<String, Object> keys = new HashMap<String, Object>();
			castedBean.setDeleted(true);
			fields.put("deleted", castedBean.isDeleted());
			keys.put("id", castedBean.getId());
			this.getInstance().updateFieldsByKeys(fields, keys);
			// Reload the modified bean
			Category updatedBean = (Category) createNewBean();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertEquals("Check updated fields ", castedBean.isDeleted(), updatedBean.isDeleted());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
		}
	}

	private IMdoBean createNewBean(MdoTableAsEnum code, Map<Long, String> labels) {
		Category newBean = new Category();
		newBean.setCode(code);
		newBean.setLabels(labels);
		return newBean;
	}
}
