package fr.mch.mdo.restaurant.services.business.managers.products;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.i18n.IMessageQuery;
import fr.mch.mdo.i18n.MessageQueryResourceBundleImpl;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.CategoryDto;
import fr.mch.mdo.restaurant.dto.beans.ProductCategoryDto;
import fr.mch.mdo.restaurant.dto.beans.ProductDto;
import fr.mch.mdo.restaurant.dto.beans.ProductsManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.dto.beans.UserAuthenticationDto;
import fr.mch.mdo.restaurant.dto.beans.ValueAddedTaxDto;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.DefaultAdministrationManagerTest;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;
import fr.mch.mdo.restaurant.services.business.managers.IManagerLabelable;
import fr.mch.mdo.restaurant.services.business.managers.restaurants.DefaultRestaurantsManager;
import fr.mch.mdo.restaurant.services.business.managers.users.DefaultUserAuthenticationsManager;
import fr.mch.mdo.test.MdoTestCase;
import fr.mch.mdo.test.resources.ITestResources;

public class DefaultProductsManagerTest extends DefaultAdministrationManagerTest
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DefaultProductsManagerTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(DefaultProductsManagerTest.class);
    }

	@Override
	protected IAdministrationManager getInstance() {
		return DefaultProductsManager.getInstance();
	}

	@Override
	protected IMdoDtoBean createNewBean() {
		// Use the existing data in database
		String code = "A";
		RestaurantDto restaurant = new RestaurantDto();
		restaurant.setId(1L);
		// Used for Restaurant.equals
		restaurant.setReference(DefaultAdministrationManagerTest.RESTAURANT_FIRST_REFERENCE);
		ValueAddedTaxDto vat = new ValueAddedTaxDto();
		vat.setId(1L);
		BigDecimal price = new BigDecimal(12);

		Set<ProductCategoryDto> categories = null;

		Map<Long, String> labels = null;

		return createNewBean(restaurant, vat, code, price, categories, labels);
	}

	@Override
	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		// Use the existing data in database
		String code = "B";
		RestaurantDto restaurant = new RestaurantDto();
		restaurant.setId(1L);
		// Used for Restaurant.equals
		restaurant.setReference(DefaultAdministrationManagerTest.RESTAURANT_FIRST_REFERENCE);
		ValueAddedTaxDto vat = new ValueAddedTaxDto();
		vat.setId(1L);
		BigDecimal price = new BigDecimal(12);

		Set<ProductCategoryDto> categories = null;

		Map<Long, String> labels = new HashMap<Long, String>();
		Long localeId = 1L;
		String label = "Boulette de porc à la vapeur";
		labels.put(localeId, label);
		list.add(createNewBean(restaurant, vat, code, price, categories, labels));
		return list;
	}

	@Override
	public void doUpdate() {
		IMdoDtoBean newBean = null;
		String code = "C";
		// Use the existing data in database
		RestaurantDto restaurant = null;
		try {
			restaurant = (RestaurantDto) DefaultRestaurantsManager.getInstance().findByPrimaryKey(1L);
		} catch (MdoException e) {
			fail("Could not found the restaurant.");
		}
		assertNotNull("Restaurant must not be null", restaurant);
		ValueAddedTaxDto vat = new ValueAddedTaxDto();
		try {
			vat = (ValueAddedTaxDto) DefaultValueAddedTaxesManager.getInstance().findByPrimaryKey(1L);
		} catch (MdoException e) {
			fail("Could not found the vat.");
		}
		assertNotNull("VAT must not be null", vat);
		BigDecimal price = new BigDecimal(12);

		Set<ProductCategoryDto> categories = new HashSet<ProductCategoryDto>();

		Map<Long, String> labels = new HashMap<Long, String>();
		Long localeId = 1L;
		String label = "Soupe Phnom Penh";
		labels.put(localeId, label);
		localeId = 2L;
		label = "Soupe Phnom Penh ES";
		labels.put(localeId, label);
		newBean = createNewBean(restaurant, vat, code, price, categories, labels);

		ProductCategoryDto productCategory = new ProductCategoryDto();
		CategoryDto category = new CategoryDto();
		category.setId(1L);
		productCategory.setCategory(category);
		BigDecimal quantity = new BigDecimal(1.7);
		productCategory.setQuantity(quantity);
		categories.add(productCategory);

		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + ProductDto.class, beanToBeUpdated instanceof ProductDto);
			ProductDto castedBean = (ProductDto) beanToBeUpdated;
			assertNotNull("Product code must not be null", castedBean.getCode());
			assertEquals("Product name must be equals to the inserted value", code.toString(), castedBean.getCode().toString());
			assertNotNull("Product labels must not be null", castedBean.getLabels());
			assertEquals("Check Product labels size", labels.size(), castedBean.getLabels().size());
			assertNotNull("Product categories must not be null", castedBean.getCategories());
			assertEquals("Check Product categories size", categories.size(), castedBean.getCategories().size());
			// Update the created bean
			castedBean.getCategories().clear();
			productCategory = new ProductCategoryDto();
			category = new CategoryDto();
			category.setId(1L);
			productCategory.setCategory(category);
			quantity = new BigDecimal(3.14);
			productCategory.setQuantity(quantity);
			castedBean.getCategories().add(productCategory);
			productCategory = new ProductCategoryDto();
			category = new CategoryDto();
			category.setId(2L);
			productCategory.setCategory(category);
			quantity = new BigDecimal(3.57);
			productCategory.setQuantity(quantity);
			castedBean.getCategories().add(productCategory);
			labels.clear();
			localeId = 1L;
			label = "O2";
			labels.put(localeId, label);
			castedBean.setLabels(labels);
			castedBean = (ProductDto) this.getInstance().update(castedBean);
			// Reload the modified bean
			ProductDto updatedBean = new ProductDto();
			updatedBean.setId(castedBean.getId());
			updatedBean = (ProductDto) this.getInstance().load(updatedBean);
			assertNotNull("Product code must not be null", updatedBean.getCode());
			assertEquals("Product name must be equals to the updated value", code.toString(), updatedBean.getCode().toString());
			assertNotNull("Product labels must not be null", updatedBean.getLabels());
			assertEquals("Check Product labels size", labels.size(), updatedBean.getLabels().size());
			assertNotNull("Product categories must not be null", castedBean.getCategories());
			// Because of ProductDto is a bean that is not attach to Hibernate session so the collection Categories is not too.
			// Then Categories collection is never removed(but still updated) and the size must be the same as before updating the ProductDto
			assertEquals("Check Product categories size", 2, castedBean.getCategories().size());
		} catch (Exception e) {
			fail(e.getLocalizedMessage());
		}
	}

	@Override
	public void doProcessList() {
		ProductsManagerViewBean viewBean = new ProductsManagerViewBean();
		try {
			((IManagerLabelable) this.getInstance()).processList(viewBean, DefaultAdministrationManagerTest.userContext.getCurrentLocale());
			// Do not have to call find all products because we want list of products by restaurants
			assertNull("Main list not null", viewBean.getList());
			assertNotNull("Languages list not be null", viewBean.getLanguages());
			assertFalse("Languages list not be empty", viewBean.getLanguages().isEmpty());
			assertNotNull("Restaurants list not be null", viewBean.getRestaurants());
			assertFalse("Restaurants list not be empty", viewBean.getRestaurants().isEmpty());
			assertNotNull("Categories list not be null", viewBean.getCategories());
			assertFalse("Categories list not be empty", viewBean.getCategories().isEmpty());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof IProductsManager);
		assertTrue(this.getInstance() instanceof DefaultProductsManager);
	}

	private IMdoDtoBean createNewBean(RestaurantDto restaurant, ValueAddedTaxDto vat, 
			String code, BigDecimal price, Set<ProductCategoryDto> categories, Map<Long, String> labels) {
		ProductDto newBean = new ProductDto();
		newBean.setCode(code);
		newBean.setPrice(price);
		newBean.setRestaurant(restaurant);
		newBean.setVat(vat);
		newBean.setCategories(categories);
		newBean.setLabels(labels);
		return newBean;
	}
	
	/**
	 * Test the getList method.
	 */
	public void testGetList() {
		Long restaurantId = 1L;
		try {
			List<IMdoDtoBean> list= ((IProductsManager) DefaultProductsManager.getInstance()).getList(restaurantId);
			assertNotNull("List must not be null", list);
			assertFalse("List must not be empty", list.isEmpty());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}	
	}
	
	public void testImportData() {
		File file = null;
		try {
			// Don't use URL.getFile or URL.getPath instead convert to URI first
			// Because when using URL and the path contains space then the URL.getFile or URL.getPath will convert space to "%20"
			file = new File(ITestResources.class.getResource("inport-data-10203040506070-fr.ods").toURI().getPath());
		} catch (URISyntaxException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		 try {
			((IProductsManager) DefaultProductsManager.getInstance()).importData(file.getName(), file);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	public void testExportData() {
		FileOutputStream fos = null;
		final File file = new File("target/export-data.ods");
		try {
			fos = new FileOutputStream(file);

			assertEquals("File must be empty", 0,file.length());
			
			String restaurantReference = "10203040506070";
			
			IMessageQuery messages = new MessageQueryResourceBundleImpl("fr.mch.mdo.restaurant.resources.i18n.ApplicationMessagesResources");
			String[] headers = new String[] { messages.getMessage("products.manager.code"), 
					messages.getMessage("products.manager.label"), messages.getMessage("products.manager.price"), 
					messages.getMessage("products.manager.vat"),  messages.getMessage("products.manager.color") };

			((IProductsManager) DefaultProductsManager.getInstance())
					.exportData(fos, restaurantReference, headers, DefaultProductsManagerTest.userContext.getCurrentLocale());
			
			fos.flush();

		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		} finally {
			try {
				fos.close();
				// Open with ooo: Do not use the following instruction in Ubuntu Jenkins if the user who laucnh the test has no rights to open ooo
		        //OOUtils.open(file);
			} catch (IOException e) {
				fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
			}
		}
		assertTrue("File must be not empty", file.length() != 0);
	}
	
	public void testLookupProductsCodesByPrefixCode() {
		// Used for finding restaurant
		Long userId = 1L;
		try {
			UserAuthenticationDto user = (UserAuthenticationDto) DefaultUserAuthenticationsManager.getInstance().findByPrimaryKey(userId, null);
			DefaultProductsManagerTest.userContext.setUserAuthentication(user);
		} catch(Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		String prefixProductCode = "1";
		try {
			Map<Long, String> codes = ((IProductsManager) DefaultProductsManager.getInstance()).lookupProductsCodesByPrefixCode(DefaultProductsManagerTest.userContext.getUserAuthentication().getRestaurant().getId(), prefixProductCode);
			assertNotNull("Map of codes must not be null", codes);
			assertFalse("Map of codes must not be empty", codes.isEmpty());
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}
}
