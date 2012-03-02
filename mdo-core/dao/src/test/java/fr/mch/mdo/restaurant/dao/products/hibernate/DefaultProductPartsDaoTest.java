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
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.dao.beans.ProductPart;
import fr.mch.mdo.restaurant.dao.beans.ProductPartLanguage;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServicesTestCase;
import fr.mch.mdo.restaurant.dao.products.IProductPartsDao;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultProductPartsDaoTest extends DefaultDaoServicesTestCase 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultProductPartsDaoTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultProductPartsDaoTest.class);
	}

	protected IDaoServices getInstance() {
		return DefaultProductPartsDao.getInstance();
	}

	protected IMdoBean createNewBean() {
		// Use the existing data in database
		MdoTableAsEnum code = new MdoTableAsEnum();
		try {
			code = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(2L);
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
			code = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(3L);
		} catch (MdoException e) {
			fail("Could not found the ProductPart code.");
		}
		Map<Long, String> labels = new HashMap<Long, String>();
		Long localeId = 1L;
		String label = "Banane caramel";
		labels.put(localeId, label);
		list.add(createNewBean(code, labels));
		return list;
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof IProductPartsDao);
		assertTrue(this.getInstance() instanceof DefaultProductPartsDao);
	}

	@Override
	public void doFindByUniqueKey() {
		// ENTRY role was created at HSQLDB startup
		String codeName = "ENTRY";
		try {
			IMdoBean bean = this.getInstance().findByUniqueKey(codeName);
			assertNotNull("ProductPart must not be null", bean);
			assertTrue("IMdoBean must be instance of " + ProductPart.class, bean instanceof ProductPart);
			ProductPart castedBean = (ProductPart) bean;
			assertNotNull("ProductPart code must not be null", castedBean.getCode());
			assertEquals("ProductPart code must be equals to unique key", codeName, castedBean.getCode().getName());
			assertFalse("ProductPart must not be deleted", castedBean.isDeleted());
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
			code = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(4L);
		} catch (MdoException e) {
			fail("Could not found the ProductPart code.");
		}
		Map<Long, String> labels = new HashMap<Long, String>();
		Long localeId = 1L;
		String label = "Pomme";
		labels.put(localeId, label);
		localeId = 2L;
		label = "Apple";
		labels.put(localeId, label);
		newBean = createNewBean(code, labels);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + ProductPart.class, beanToBeUpdated instanceof ProductPart);
			ProductPart castedBean = (ProductPart) beanToBeUpdated;
			assertNotNull("ProductPart code must not be null", castedBean.getCode());
			assertEquals("ProductPart name must be equals to the inserted value", code.toString(), castedBean.getCode().toString());
			assertNotNull("ProductPart labels must not be null", castedBean.getLabels());
			assertEquals("Check ProductPart labels size", labels.size(), castedBean.getLabels().size());
			assertFalse("ProductPart must not be deleted", castedBean.isDeleted());
			// Update the created bean
			labels.clear();
			localeId = 1L;
			label = "Pomme Fuji";
			labels.put(localeId, label);
			castedBean.setLabels(labels);
			castedBean.setDeleted(true);
			this.getInstance().update(castedBean);
			// Reload the modified bean
			ProductPart updatedBean = new ProductPart();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertNotNull("ProductPart code must not be null", updatedBean.getCode());
			assertEquals("ProductPart name must be equals to the updated value", code.toString(), updatedBean.getCode().toString());
			assertNotNull("ProductPart labels must not be null", updatedBean.getLabels());
			assertEquals("Check ProductPart labels size", labels.size(), updatedBean.getLabels().size());
			assertTrue("ProductPart must be deleted", castedBean.isDeleted());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Override
	public void doUpdateFieldsAndDeleteByKeysSpecific() {
		IMdoBean newBean = null;
		// Use the existing data in database
		MdoTableAsEnum code = new MdoTableAsEnum();
		try {
			code = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(4L);
		} catch (MdoException e) {
			fail("Could not found the ProductPart code.");
		}
		Map<Long, String> labels = new HashMap<Long, String>();
		Long localeId = 1L;
		String label = "Pomme";
		labels.put(localeId, label);
		localeId = 2L;
		label = "Apple";
		labels.put(localeId, label);
		newBean = createNewBean(code, labels);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + ProductPart.class, beanToBeUpdated instanceof ProductPart);
			ProductPart castedBean = (ProductPart) beanToBeUpdated;
			assertNotNull("ProductPart code must not be null", castedBean.getCode());
			assertEquals("ProductPart name must be equals to the inserted value", code.toString(), castedBean.getCode().toString());
			assertNotNull("ProductPart labels must not be null", castedBean.getLabels());
			assertEquals("Check ProductPart labels size", labels.size(), castedBean.getLabels().size());
			assertFalse("ProductPart must not be deleted", castedBean.isDeleted());

			// Update the created bean
			Map<String, Object> fields = new HashMap<String, Object>();
			Map<String, Object> keys = new HashMap<String, Object>();
			castedBean.setDeleted(true);
			fields.put("deleted", castedBean.isDeleted());
			keys.put("id", castedBean.getId());
			this.getInstance().updateFieldsByKeys(fields, keys);
			// Reload the modified bean
			ProductPart updatedBean = (ProductPart) createNewBean();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertEquals("Check updated fields ", castedBean.isDeleted(), updatedBean.isDeleted());

			// Delete the bean by keys
			// Take the fields as keys
			try {
				super.doDeleteByKeysSpecific(updatedBean, keys, true);
			} catch (Exception e) {
				// We Have to delete following tables in the following order deleting the table t_product_part.
				// 1) t_product_part_language
				// 2) t_order_line
				Object parentId = keys.get("id");
				Map<String, Object> childrenKeys = new HashMap<String, Object>();
				childrenKeys.put("parentId", parentId);
				super.doDeleteByKeysSpecific(ProductPartLanguage.class, childrenKeys);
				super.doDeleteByKeysSpecific(updatedBean, keys);
			}
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
		}
	}

	private IMdoBean createNewBean(MdoTableAsEnum code, Map<Long, String> labels) {
		ProductPart newBean = new ProductPart();
		newBean.setCode(code);
		newBean.setLabels(labels);
		return newBean;
	}
}
