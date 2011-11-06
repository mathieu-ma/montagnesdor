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
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
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
		code.setId(21L);
		Map<Long, String> labels = new HashMap<Long, String>();
		labels.put(1L, "Réduction");
		return createNewBean(shortCode, restaurant, code, labels);
	}

	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		String shortCode = "/";
		Restaurant restaurant = new Restaurant();
		restaurant.setId(1L);
		MdoTableAsEnum code = new MdoTableAsEnum();
		code.setId(22L);
		Map<Long, String> labels = new HashMap<Long, String>();
		labels.put(1L, "Commande personnalisée");
		list.add(createNewBean(shortCode, restaurant, code, labels));
		shortCode = "<";
		restaurant = new Restaurant();
		restaurant.setId(1L);
		code = new MdoTableAsEnum();
		code.setId(23L);
		labels = new HashMap<Long, String>();
		labels.put(1L, "Test 1");
		list.add(createNewBean(shortCode, restaurant, code, labels));
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
		Map<Long, String> labels = new HashMap<Long, String>();
		labels.put(1L, "Test 2");
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(createNewBean(shortCode, restaurant, code, labels));
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

	public void testGetIdByCodeName() {
		IProductSpecialCodesDao dao = (IProductSpecialCodesDao) this.getInstance();
		try {
			Long beanId = dao.getIdByCodeName(1L, "DEFAULT");
			assertNotNull(beanId);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
	}
	
	private IMdoBean createNewBean(String shortCode, Restaurant restaurant, MdoTableAsEnum code, Map<Long, String> labels) {
		ProductSpecialCode newBean = new ProductSpecialCode();
		newBean.setShortCode(shortCode);
		newBean.setRestaurant(restaurant);
		newBean.setCode(code);
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

			List<IMdoBean> list = productSpecialCodesDao.findAllByRestaurant(restaurantId);
			assertNotNull("List of IMdoBean must not be null", list);
			assertFalse("List of IMdoBean must not be empty", list.isEmpty());
			ProductSpecialCode psc = (ProductSpecialCode) list.get(0);
			assertNotNull("ProductSpecialCode code not null", psc.getCode());
			assertNotNull("ProductSpecialCode code id not null", psc.getCode().getId());
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

}
