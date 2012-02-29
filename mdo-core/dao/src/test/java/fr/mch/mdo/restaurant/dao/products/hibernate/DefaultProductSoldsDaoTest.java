package fr.mch.mdo.restaurant.dao.products.hibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.Product;
import fr.mch.mdo.restaurant.dao.beans.ProductSold;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServicesTestCase;
import fr.mch.mdo.restaurant.dao.products.IProductSoldsDao;
import fr.mch.mdo.restaurant.exception.MdoDataBeanException;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultProductSoldsDaoTest extends DefaultDaoServicesTestCase
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultProductSoldsDaoTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultProductSoldsDaoTest.class);
	}

	protected IDaoServices getInstance() {
		return DefaultProductSoldsDao.getInstance();
	}

	protected IMdoBean createNewBean() {
		Date soldDate = new Date();
		Product product = new Product();
		product.setId(1L);
		BigDecimal quantity = BigDecimal.ONE;
		return createNewBean(soldDate, product, quantity);
	}

	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(1970, 7, 16);
		Date soldDate = calendar.getTime();
		Product product = new Product();
		product.setId(2L);
		BigDecimal quantity = BigDecimal.ONE;
		list.add(createNewBean(soldDate, product, quantity));
		return list;
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof IProductSoldsDao);
		assertTrue(this.getInstance() instanceof DefaultProductSoldsDao);
	}

	@Override
	public void doFindByUniqueKey() {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(1970, 7, 15);
		Date soldDate = calendar.getTime();
		Long productId = 1L;
		try {
			for (int i = 0; i < 2; i++) {
				IMdoBean bean = null;
				if (i == 0) {
					bean = ((IProductSoldsDao) this.getInstance()).findByUniqueKey(soldDate, productId);
				} else {
					bean = this.getInstance().findByUniqueKey(new Object[] { soldDate, productId });
				}
				assertNotNull("Product Sold must not be null", bean);
				assertTrue("IMdoBean must be instance of " + ProductSold.class, bean instanceof ProductSold);
				ProductSold castedBean = (ProductSold) bean;
				assertNotNull("ProductSold product Id must not be null", castedBean.getProduct().getId());
				assertEquals("ProductSold product Id must be equals to unique key", productId, castedBean.getProduct().getId());
				assertNotNull("ProductSold sold year must not be null", castedBean.getSoldYear());
				assertEquals("ProductSold sold year must be equals to unique key", new Integer(calendar.get(Calendar.YEAR)), castedBean.getSoldYear());
				assertNotNull("ProductSold sold month must not be null", castedBean.getSoldMonth());
				assertEquals("ProductSold sold month must be equals to unique key", new Integer(calendar.get(Calendar.MONTH) + 1), castedBean.getSoldMonth());
				assertNotNull("ProductSold sold day must not be null", castedBean.getSoldDay());
				assertEquals("ProductSold sold day must be equals to unique key", new Integer(calendar.get(Calendar.DAY_OF_MONTH)), castedBean.getSoldDay());
				assertFalse("ProductSold must not be deleted", castedBean.isDeleted());

			}
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	@Override
	public void doUpdate() {
		IMdoBean newBean = null;
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(1970, 7, 17);
		Date soldDate = calendar.getTime();
		Product product = new Product();
		product.setId(2L);
		BigDecimal quantity = BigDecimal.ONE;
		newBean = createNewBean(soldDate, product, quantity);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + ProductSold.class, beanToBeUpdated instanceof ProductSold);
			ProductSold castedBean = (ProductSold) beanToBeUpdated;
			assertNotNull("Product Sold Id must not be null", castedBean.getProduct().getId());
			assertFalse("ProductSold must not be deleted", castedBean.isDeleted());

			// Update the created bean
			calendar.set(1970, 7, 18);
			quantity = BigDecimal.TEN;
			castedBean.setSoldYear(calendar.get(Calendar.YEAR));
			castedBean.setSoldMonth(calendar.get(Calendar.MONTH));
			castedBean.setSoldDay(calendar.get(Calendar.DAY_OF_MONTH));
			castedBean.setQuantity(quantity);
			castedBean.setDeleted(true);
			this.getInstance().update(castedBean);

			// Reload the modified bean
			ProductSold updatedBean = new ProductSold();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertNotNull("ProductSold code must not be null", updatedBean.getProduct().getCode());
			assertNotNull("ProductSold sold year must not be null", updatedBean.getSoldYear());
			assertEquals("Check ProductSold sold year", new Integer(calendar.get(Calendar.YEAR)), updatedBean.getSoldYear());
			assertNotNull("ProductSold sold month must not be null", updatedBean.getSoldMonth());
			assertEquals("Check ProductSold sold month", new Integer(calendar.get(Calendar.MONTH)), updatedBean.getSoldMonth());
			assertNotNull("ProductSold sold day must not be null", updatedBean.getSoldDay());
			assertEquals("Check ProductSold sold day", new Integer(calendar.get(Calendar.DAY_OF_MONTH)), updatedBean.getSoldDay());
			assertNotNull("ProductSold quantity must not be null", updatedBean.getQuantity());
			assertEquals("Check ProductSold quantity", super.decimalFormat.format(quantity), super.decimalFormat.format(updatedBean.getQuantity()));
			assertTrue("ProductSold must be deleted", castedBean.isDeleted());

			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	@Override
	public void doUpdateFieldsByKeysSpecific() {
		IMdoBean newBean = null;
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(1970, 7, 17);
		Date soldDate = calendar.getTime();
		Product product = new Product();
		product.setId(2L);
		BigDecimal quantity = BigDecimal.ONE;
		newBean = createNewBean(soldDate, product, quantity);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + ProductSold.class, beanToBeUpdated instanceof ProductSold);
			ProductSold castedBean = (ProductSold) beanToBeUpdated;
			assertNotNull("Product Sold Id must not be null", castedBean.getProduct().getId());
			assertFalse("ProductSold must not be deleted", castedBean.isDeleted());

			// Update the created bean
			Map<String, Object> fields = new HashMap<String, Object>();
			Map<String, Object> keys = new HashMap<String, Object>();
			castedBean.setQuantity(BigDecimal.TEN);
			castedBean.setSoldDay(1);
			castedBean.setSoldMonth(2);
			castedBean.setSoldYear(3);
			fields.put("quantity", castedBean.getQuantity());
			fields.put("soldDay", castedBean.getSoldDay());
			fields.put("soldMonth", castedBean.getSoldMonth());
			fields.put("soldYear", castedBean.getSoldYear());
			keys.put("id", castedBean.getId());
			this.getInstance().updateFieldsByKeys(fields, keys);
			// Reload the modified bean
			ProductSold updatedBean = (ProductSold) createNewBean();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertEquals("Check updated fields ", castedBean.getQuantity(), updatedBean.getQuantity());
			assertEquals("Check updated fields ", castedBean.getSoldDay(), updatedBean.getSoldDay());
			assertEquals("Check updated fields ", castedBean.getSoldMonth(), updatedBean.getSoldMonth());
			assertEquals("Check updated fields ", castedBean.getSoldYear(), updatedBean.getSoldYear());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
		}
	}

	private IMdoBean createNewBean(Date soldDate, Product product, BigDecimal quantity) {
		ProductSold newBean = new ProductSold();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(soldDate);
		Integer soldYear = new Integer(calendar.get(Calendar.YEAR));
		Integer soldMonth = new Integer(calendar.get(Calendar.MONTH));
		Integer soldDay = new Integer(calendar.get(Calendar.DAY_OF_MONTH));
		newBean.setSoldYear(soldYear);
		newBean.setSoldMonth(soldMonth);
		newBean.setSoldDay(soldDay);
		newBean.setProduct(product);
		newBean.setQuantity(quantity);
		return newBean;
	}

	public void testGetByYear() {
		try {
			((DefaultProductSoldsDao) this.getInstance()).findByYear(1970);
		} catch (MdoDataBeanException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}
}
