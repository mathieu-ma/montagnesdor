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
		Character shortCode = '-';
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
		Character shortCode = '/';
		Restaurant restaurant = new Restaurant();
		restaurant.setId(1L);
		MdoTableAsEnum code = new MdoTableAsEnum();
		code.setId(22L);
		Map<Long, String> labels = new HashMap<Long, String>();
		labels.put(1L, "Commande personnalisée");
		list.add(createNewBean(shortCode, restaurant, code, labels));
		shortCode = '<';
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

	public void testFindByRestaurantAndShortCode() {
		IProductSpecialCodesDao dao = (IProductSpecialCodesDao) this.getInstance();
		try {
			IMdoBean bean = dao.findByRestaurantAndShortCode(1L, '#');
			assertNotNull(bean);
			assertTrue(bean instanceof ProductSpecialCode);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
	}

	@Override
	public void doFindByUniqueKey() {
		assertTrue("Nothing to check", Boolean.TRUE);
	}

	@Override
	public void doUpdate() {
		Character shortCode = '?';
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
			shortCode = '@';
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

	private IMdoBean createNewBean(Character shortCode, Restaurant restaurant, MdoTableAsEnum code, Map<Long, String> labels) {
		ProductSpecialCode newBean = new ProductSpecialCode();
		newBean.setShortCode(shortCode);
		newBean.setRestaurant(restaurant);
		newBean.setCode(code);
		newBean.setLabels(labels);
		return newBean;
	}
}
