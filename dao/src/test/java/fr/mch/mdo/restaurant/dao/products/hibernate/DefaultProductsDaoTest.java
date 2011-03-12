package fr.mch.mdo.restaurant.dao.products.hibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.DaoServicesFactory;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.Category;
import fr.mch.mdo.restaurant.dao.beans.Product;
import fr.mch.mdo.restaurant.dao.beans.ProductCategory;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.beans.ValueAddedTax;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServicesTestCase;
import fr.mch.mdo.restaurant.dao.products.IProductsDao;
import fr.mch.mdo.restaurant.exception.MdoException;

public class DefaultProductsDaoTest extends DefaultDaoServicesTestCase 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultProductsDaoTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultProductsDaoTest.class);
	}

	protected IDaoServices getInstance() {
		return DefaultProductsDao.getInstance();
	}

	protected IMdoBean createNewBean() {
		// Use the existing data in database
		String code = "A";
		Restaurant restaurant = new Restaurant();
		restaurant.setId(1L);
		// Used for Restaurant.equals
		restaurant.setReference(DefaultDaoServicesTestCase.RESTAURANT_FIRST_REFERENCE);
		ValueAddedTax vat = new ValueAddedTax();
		vat.setId(1L);
		BigDecimal price = new BigDecimal(12);

		Set<ProductCategory> categories = null;

		Map<Long, String> labels = null;

		return createNewBean(restaurant, vat, code, price, categories, labels);
	}

	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		// Use the existing data in database
		String code = "B";
		Restaurant restaurant = new Restaurant();
		restaurant.setId(1L);
		// Used for Restaurant.equals
		restaurant.setReference(DefaultDaoServicesTestCase.RESTAURANT_FIRST_REFERENCE);
		ValueAddedTax vat = new ValueAddedTax();
		vat.setId(1L);
		BigDecimal price = new BigDecimal(12);

		Set<ProductCategory> categories = null;

		Map<Long, String> labels = new HashMap<Long, String>();
		Long localeId = 1L;
		String label = "Boulette de porc à la vapeur";
		labels.put(localeId, label);
		list.add(createNewBean(restaurant, vat, code, price, categories, labels));
		return list;
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof IProductsDao);
		assertTrue(this.getInstance() instanceof DefaultProductsDao);
	}

	@Override
	public void doFindByUniqueKey() {
		// 1 restaurant was created at HSQLDB startup
		Long restaurantId = 1L;
		// 11 code was created at HSQLDB startup
		String code = "11";
		try {
			IMdoBean bean = this.getInstance().findByUniqueKey(new Object[] { restaurantId, code });
			assertTrue("IMdoBean must be instance of " + Product.class, bean instanceof Product);
			Product castedBean = (Product) bean;
			assertEquals("Product code must be equals to unique key", code, castedBean.getCode());
			assertNotNull("Product Restaurant must not be null", castedBean.getRestaurant());
			assertEquals("Product Restaurant must be equals to unique key", restaurantId, castedBean.getRestaurant().getId());
			assertEquals("Product Restaurant Reference must be equals to unique key", DefaultDaoServicesTestCase.RESTAURANT_FIRST_REFERENCE, castedBean.getRestaurant()
					.getReference());
			assertFalse("Product must not be deleted", castedBean.isDeleted());

			IProductsDao productsDao = (IProductsDao) this.getInstance();
			bean = productsDao.findByCode(DefaultDaoServicesTestCase.RESTAURANT_FIRST_REFERENCE, code);
			assertTrue("IMdoBean must be instance of " + Product.class, bean instanceof Product);
			castedBean = (Product) bean;
			assertEquals("Product code must be equals to unique key", code, castedBean.getCode());
			assertNotNull("Product Restaurant must not be null", castedBean.getRestaurant());
			assertEquals("Product Restaurant must be equals to unique key", restaurantId, castedBean.getRestaurant().getId());
			assertEquals("Product Restaurant Reference must be equals to unique key", DefaultDaoServicesTestCase.RESTAURANT_FIRST_REFERENCE, castedBean.getRestaurant()
					.getReference());
			assertFalse("Product must not be deleted", castedBean.isDeleted());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Override
	public void doUpdate() {
		IMdoBean newBean = null;
		String code = "C";
		// Use the existing data in database
		Restaurant restaurant = null;
		try {
			restaurant = (Restaurant) DaoServicesFactory.getRestaurantsDao().findByPrimaryKey(1L);
		} catch (MdoException e) {
			fail("Could not found the restaurant.");
		}
		assertNotNull("Restaurant must not be null", restaurant);
		ValueAddedTax vat = new ValueAddedTax();
		try {
			vat = (ValueAddedTax) DaoServicesFactory.getValueAddedTaxesDao().findByPrimaryKey(1L);
		} catch (MdoException e) {
			fail("Could not found the vat.");
		}
		assertNotNull("VAT must not be null", vat);
		BigDecimal price = new BigDecimal(12);

		Set<ProductCategory> categories = new HashSet<ProductCategory>();

		Map<Long, String> labels = new HashMap<Long, String>();
		Long localeId = 1L;
		String label = "Soupe Phnom Penh";
		labels.put(localeId, label);
		localeId = 2L;
		label = "Soupe Phnom Penh ES";
		labels.put(localeId, label);
		newBean = createNewBean(restaurant, vat, code, price, categories, labels);

		ProductCategory productCategory = new ProductCategory();
		Category category = new Category();
		category.setId(1L);
		productCategory.setCategory(category);
		BigDecimal quantity = new BigDecimal(1.7);
		productCategory.setQuantity(quantity);
		((Product) newBean).addCategory(productCategory);

		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + Product.class, beanToBeUpdated instanceof Product);
			Product castedBean = (Product) beanToBeUpdated;
			assertNotNull("Product code must not be null", castedBean.getCode());
			assertEquals("Product name must be equals to the inserted value", code.toString(), castedBean.getCode().toString());
			assertNotNull("Product labels must not be null", castedBean.getLabels());
			assertEquals("Check Product labels size", labels.size(), castedBean.getLabels().size());
			assertNotNull("Product categories must not be null", castedBean.getCategories());
			assertEquals("Check Product categories size", categories.size(), castedBean.getCategories().size());
			assertFalse("Product must not be deleted", castedBean.isDeleted());
			// Update the created bean
			productCategory = new ProductCategory();
			category = new Category();
			category.setId(2L);
			productCategory.setCategory(category);
			quantity = new BigDecimal(3.57);
			productCategory.setQuantity(quantity);
			castedBean.addCategory(productCategory);
			labels.clear();
			localeId = 1L;
			label = "O2";
			labels.put(localeId, label);
			castedBean.setLabels(labels);
			castedBean.setDeleted(true);
			this.getInstance().update(castedBean);
			// Reload the modified bean
			Product updatedBean = new Product();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertNotNull("Product code must not be null", updatedBean.getCode());
			assertEquals("Product name must be equals to the updated value", code.toString(), updatedBean.getCode().toString());
			assertNotNull("Product labels must not be null", updatedBean.getLabels());
			assertEquals("Check Product labels size", labels.size(), updatedBean.getLabels().size());
			assertNotNull("Product categories must not be null", castedBean.getCategories());
			assertEquals("Check Product categories size", categories.size(), castedBean.getCategories().size());
			assertTrue("Product must be deleted", castedBean.isDeleted());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	private IMdoBean createNewBean(Restaurant restaurant, ValueAddedTax vat, String code, BigDecimal price, Set<ProductCategory> categories, Map<Long, String> labels) {
		Product newBean = new Product();
		newBean.setCode(code);
		newBean.setPrice(price);
		newBean.setRestaurant(restaurant);
		newBean.setVat(vat);
		newBean.setCategories(categories);
		newBean.setLabels(labels);
		return newBean;
	}

	public void testFindAllByPrefixCode() {
		// 1 restaurant was created at HSQLDB startup
		Long restaurantId = 1L;
		// 11 code was created at HSQLDB startup
		String prefixCode = "1";
		try {
			IProductsDao productsDao = (IProductsDao) this.getInstance();

			List<IMdoBean> list = productsDao.findAllByPrefixCode(restaurantId, prefixCode + "%");
			assertNotNull("List<IMdoBean> must not be null", list);
			assertFalse("List<IMdoBean> must not be empty", list.isEmpty());
			IMdoBean bean = list.get(0);
			assertTrue("IMdoBean must be instance of " + Product.class, bean instanceof Product);
			Product castedBean = (Product) bean;
			assertTrue("Check Product code ", castedBean.getCode().contains(prefixCode));
			assertNotNull("Product Restaurant must not be null", castedBean.getRestaurant());
			assertEquals("Product Restaurant must be equals to unique key", restaurantId, castedBean.getRestaurant().getId());
			assertEquals("Product Restaurant Reference must be equals to unique key", DefaultDaoServicesTestCase.RESTAURANT_FIRST_REFERENCE, castedBean.getRestaurant()
					.getReference());
			assertFalse("Product must not be deleted", castedBean.isDeleted());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public void testFindByCode() {
		// 1 restaurant was created at HSQLDB startup
		Long restaurantId = 1L;
		// 11 code was created at HSQLDB startup
		String code = "11";
		try {
			IProductsDao productsDao = (IProductsDao) this.getInstance();

			IMdoBean bean = productsDao.findByCode(DefaultDaoServicesTestCase.RESTAURANT_FIRST_REFERENCE, code);
			assertTrue("IMdoBean must be instance of " + Product.class, bean instanceof Product);
			Product castedBean = (Product) bean;
			assertEquals("Product code must be equals to unique key", code, castedBean.getCode());
			assertNotNull("Product Restaurant must not be null", castedBean.getRestaurant());
			assertEquals("Product Restaurant must be equals to unique key", restaurantId, castedBean.getRestaurant().getId());
			assertEquals("Product Restaurant Reference must be equals to unique key", DefaultDaoServicesTestCase.RESTAURANT_FIRST_REFERENCE, castedBean.getRestaurant()
					.getReference());
			assertFalse("Product must not be deleted", castedBean.isDeleted());

			bean = productsDao.findByCode(restaurantId, code);
			assertTrue("IMdoBean must be instance of " + Product.class, bean instanceof Product);
			castedBean = (Product) bean;
			assertEquals("Product code must be equals to unique key", code, castedBean.getCode());
			assertNotNull("Product Restaurant must not be null", castedBean.getRestaurant());
			assertEquals("Product Restaurant must be equals to unique key", restaurantId, castedBean.getRestaurant().getId());
			assertEquals("Product Restaurant Reference must be equals to unique key", DefaultDaoServicesTestCase.RESTAURANT_FIRST_REFERENCE, castedBean.getRestaurant()
					.getReference());
			assertFalse("Product must not be deleted", castedBean.isDeleted());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
