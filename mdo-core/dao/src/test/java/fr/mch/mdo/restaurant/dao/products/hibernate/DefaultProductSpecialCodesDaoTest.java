package fr.mch.mdo.restaurant.dao.products.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.dao.beans.ProductSpecialCode;
import fr.mch.mdo.restaurant.dao.beans.ProductSpecialCodeLabel;
import fr.mch.mdo.restaurant.dao.beans.ProductSpecialCodeLanguage;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.beans.ValueAddedTax;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServicesTestCase;
import fr.mch.mdo.restaurant.dao.products.IProductSpecialCodesDao;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultProductSpecialCodesDaoTest extends DefaultDaoServicesTestCase 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultProductSpecialCodesDaoTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultProductSpecialCodesDaoTest.class);
	}

	@Override
	protected IDaoServices getInstance() {
		return DefaultProductSpecialCodesDao.getInstance();
	}

	protected IMdoBean createNewBean() {
		String shortCode = "-";
		Restaurant restaurant = new Restaurant();
		restaurant.setId(1L);
		MdoTableAsEnum code = new MdoTableAsEnum();
		code.setId(11L);
		ValueAddedTax vat = new ValueAddedTax();
		vat.setId(1L);

		Map<Long, String> labels = new HashMap<Long, String>();
		labels.put(1L, "Réduction");
		return createNewBean(shortCode, restaurant, code, vat, labels);
	}

	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		String shortCode = "+";
		Restaurant restaurant = new Restaurant();
		restaurant.setId(1L);
		MdoTableAsEnum code = new MdoTableAsEnum();
		code.setId(12L);
		ValueAddedTax vat = new ValueAddedTax();
		vat.setId(1L);

		Map<Long, String> labels = new HashMap<Long, String>();
		labels.put(1L, "Commande personnalisée");
		list.add(createNewBean(shortCode, restaurant, code, vat, labels));
		shortCode = "<";
		restaurant = new Restaurant();
		restaurant.setId(1L);
		code = new MdoTableAsEnum();
		code.setId(13L);
		labels = new HashMap<Long, String>();
		labels.put(1L, "Test 1");
		list.add(createNewBean(shortCode, restaurant, code, vat, labels));
		return list;
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof IProductSpecialCodesDao);
		assertTrue(this.getInstance() instanceof DefaultProductSpecialCodesDao);
	}

	@Override
	public void doFindByUniqueKey() {
		IProductSpecialCodesDao dao = (IProductSpecialCodesDao) this.getInstance();
		try {
			IMdoBean bean = dao.findByShortCode(1L, "#");
			assertNotNull(bean);
			assertTrue(bean instanceof ProductSpecialCode);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
		try {
			IMdoBean bean = dao.findByCodeName(1L, "OFFERED_PRODUCT");
			assertNotNull(bean);
			assertTrue(bean instanceof ProductSpecialCode);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
	}

	@Override
	public void doUpdate() {
		String shortCode = "?";
		Restaurant restaurant = new Restaurant();
		restaurant.setId(1L);
		MdoTableAsEnum code = new MdoTableAsEnum();
		code.setId(24L);
		ValueAddedTax vat = new ValueAddedTax();
		vat.setId(1L);

		Map<Long, String> labels = new HashMap<Long, String>();
		labels.put(1L, "Test 2");
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(createNewBean(shortCode, restaurant, code, vat, labels));
			assertTrue("IMdoBean must be instance of " + ProductSpecialCode.class, beanToBeUpdated instanceof ProductSpecialCode);
			ProductSpecialCode castedBean = (ProductSpecialCode) beanToBeUpdated;
			assertEquals("ProductSpecialCode short code must be equals to unique key", shortCode, castedBean.getShortCode());
			assertNotNull("ProductSpecialCode labels must not be null", castedBean.getLabels());
			assertEquals("Check ProductSpecialCode labels size", labels.size(), castedBean.getLabels().size());
			assertFalse("ProductSpecialCode must not be deleted", castedBean.isDeleted());
			// Update the created bean
			shortCode = "@";
			castedBean.setShortCode(shortCode);
			labels.put(2L, "Test 2 ES");
			castedBean.setLabels(labels);
			castedBean.setDeleted(true);
			this.getInstance().update(castedBean);
			// Reload the modified bean
			ProductSpecialCode updatedBean = new ProductSpecialCode();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertEquals("ProductSpecialCode short code must be equals to unique key", shortCode, castedBean.getShortCode());
			assertNotNull("ProductSpecialCode labels must not be null", castedBean.getLabels());
			assertEquals("Check ProductSpecialCode labels size", labels.size(), castedBean.getLabels().size());
			assertTrue("ProductSpecialCode must be deleted", updatedBean.isDeleted());
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
	}

	@Override
	public void doUpdateFieldsAndDeleteByKeysSpecific() {
		String shortCode = "%";
		Restaurant restaurant = new Restaurant();
		restaurant.setId(1L);
		MdoTableAsEnum code = new MdoTableAsEnum();
		code.setId(25L);
		ValueAddedTax vat = new ValueAddedTax();
		vat.setId(1L);

		Map<Long, String> labels = new HashMap<Long, String>();
		labels.put(1L, "Test 2");
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(createNewBean(shortCode, restaurant, code, vat, labels));
			assertTrue("IMdoBean must be instance of " + ProductSpecialCode.class, beanToBeUpdated instanceof ProductSpecialCode);
			ProductSpecialCode castedBean = (ProductSpecialCode) beanToBeUpdated;
			assertEquals("ProductSpecialCode short code must be equals to unique key", shortCode, castedBean.getShortCode());
			assertNotNull("ProductSpecialCode labels must not be null", castedBean.getLabels());
			assertEquals("Check ProductSpecialCode labels size", labels.size(), castedBean.getLabels().size());
			assertFalse("ProductSpecialCode must not be deleted", castedBean.isDeleted());

			// Update the created bean
			Map<String, Object> fields = new HashMap<String, Object>();
			Map<String, Object> keys = new HashMap<String, Object>();
			castedBean.setShortCode("Z");
			fields.put("shortCode", castedBean.getShortCode());
			keys.put("id", castedBean.getId());
			this.getInstance().updateFieldsByKeys(fields, keys);
			// Reload the modified bean
			ProductSpecialCode updatedBean = (ProductSpecialCode) createNewBean();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertEquals("Check updated fields ", castedBean.getShortCode(), updatedBean.getShortCode());

			// Delete the bean by keys
			// Take the fields as keys
			try {
				super.doDeleteByKeysSpecific(updatedBean, keys, true);
			} catch (Exception e) {
				// We Have to delete following tables in the following order deleting the table t_product_special_code
				// 1) t_product_special_code_language
				// 2) t_order_line
				Object parentId = keys.get("id");
				Map<String, Object> childrenKeys = new HashMap<String, Object>();
				childrenKeys.put("parentId", parentId);
				super.doDeleteByKeysSpecific(ProductSpecialCodeLanguage.class, childrenKeys);
				super.doDeleteByKeysSpecific(updatedBean, keys);
			}
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
		}
	}
	
	public void testGetIdByCodeName() {
		IProductSpecialCodesDao dao = (IProductSpecialCodesDao) this.getInstance();
		try {
			Long beanId = dao.getIdByCodeName(1L, "USER_ORDER");
			assertNotNull(beanId);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
	}
	
	private IMdoBean createNewBean(String shortCode, Restaurant restaurant, MdoTableAsEnum code, ValueAddedTax vat, Map<Long, String> labels) {
		ProductSpecialCode newBean = new ProductSpecialCode();
		newBean.setShortCode(shortCode);
		newBean.setRestaurant(restaurant);
		newBean.setCode(code);
		newBean.setVat(vat);
		newBean.setLabels(labels);
		return newBean;
	}
	
	/**
	 * Test the findAllByRestaurant method.
	 */
	public void testFindAllByRestaurant() {
		// 1 restaurant was created at HSQLDB startup
		Long restaurantId = 1L;
		try {
			IProductSpecialCodesDao productSpecialCodesDao = (IProductSpecialCodesDao) this.getInstance();

			List<ProductSpecialCode> list = productSpecialCodesDao.findAllByRestaurant(restaurantId);
			assertNotNull("List of IMdoBean must not be null", list);
			assertFalse("List of IMdoBean must not be empty", list.isEmpty());
			ProductSpecialCode psc = (ProductSpecialCode) list.get(0);
			// Only these 3 fields are not null: psc.getId(), psc.getShortCode(), psc.getCode().getName()
			assertNotNull("ProductSpecialCode id", psc.getId());
			assertNotNull("ProductSpecialCode short code", psc.getShortCode());
			assertNotNull("ProductSpecialCode code not null", psc.getCode());
			assertNotNull("ProductSpecialCode code name not null", psc.getCode().getName());
			
			Long locId = 1L;
			List<ProductSpecialCodeLabel> listLabels = productSpecialCodesDao.findAllByRestaurant(restaurantId, locId);
			assertNotNull("List of IMdoBean must not be null", listLabels);
			assertFalse("List of IMdoBean must not be empty", listLabels.isEmpty());
			ProductSpecialCodeLabel pscLabel = (ProductSpecialCodeLabel) listLabels.get(0);
			// Only these 5 fields are not null: pscLabel.getId(), pscLabel.getShortCode(), pscLabel.getCode().getName(), pscLabel.getLabel(), pscLabel.getLocale().getId()
			assertNotNull("ProductSpecialCode id", pscLabel.getId());
			assertNotNull("ProductSpecialCode short code", pscLabel.getShortCode());
			assertNotNull("ProductSpecialCode code not null", pscLabel.getCode());
			assertNotNull("ProductSpecialCode code name not null", pscLabel.getCode().getName());
			assertNotNull("ProductSpecialCode label not null", pscLabel.getLabel());
			assertNotNull("ProductSpecialCode locale name not null", pscLabel.getLocale());
			assertNotNull("ProductSpecialCode locale name not null", pscLabel.getLocale().getId());
			assertNotNull("ProductSpecialCode VAT must not be null", pscLabel.getVat());
			assertNotNull("ProductSpecialCode VAT must not be null", pscLabel.getVat().getId());

		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

}
