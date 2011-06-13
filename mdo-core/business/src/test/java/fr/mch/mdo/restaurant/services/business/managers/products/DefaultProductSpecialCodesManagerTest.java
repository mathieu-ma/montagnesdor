package fr.mch.mdo.restaurant.services.business.managers.products;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumDto;
import fr.mch.mdo.restaurant.dto.beans.ProductSpecialCodeDto;
import fr.mch.mdo.restaurant.dto.beans.ProductSpecialCodesManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.DefaultAdministrationManagerTest;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultProductSpecialCodesManagerTest extends DefaultAdministrationManagerTest
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultProductSpecialCodesManagerTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultProductSpecialCodesManagerTest.class);
	}

	@Override
	protected IAdministrationManager getInstance() {
		return DefaultProductSpecialCodesManager.getInstance();
	}

	@Override
	protected IMdoDtoBean createNewBean() {
		String shortCode = "-";
		RestaurantDto restaurant = new RestaurantDto();
		restaurant.setId(1L);
		MdoTableAsEnumDto code = new MdoTableAsEnumDto();
		code.setId(21L);
		Map<Long, String> labels = new HashMap<Long, String>();
		labels.put(1L, "Réduction");
		return createNewBean(shortCode, restaurant, code, labels);
	}

	@Override
	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		String shortCode = "/";
		RestaurantDto restaurant = new RestaurantDto();
		restaurant.setId(1L);
		MdoTableAsEnumDto code = new MdoTableAsEnumDto();
		code.setId(22L);
		Map<Long, String> labels = new HashMap<Long, String>();
		labels.put(1L, "Commande personnalisée");
		list.add(createNewBean(shortCode, restaurant, code, labels));
		
		shortCode = "<";
		restaurant = new RestaurantDto();
		restaurant.setId(1L);
		code = new MdoTableAsEnumDto();
		code.setId(23L);
		labels = new HashMap<Long, String>();
		labels.put(1L, "Test 1");
		list.add(createNewBean(shortCode, restaurant, code, labels));
		return list;
	}

	@Override
	public void doUpdate() {
		String shortCode = "?";
		RestaurantDto restaurant = new RestaurantDto();
		restaurant.setId(1L);
		MdoTableAsEnumDto code = new MdoTableAsEnumDto();
		code.setId(24L);
		Map<Long, String> labels = new HashMap<Long, String>();
		labels.put(1L, "Test 2");
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(createNewBean(shortCode, restaurant, code, labels), userContext);
			assertTrue("IMdoBean must be instance of " + ProductSpecialCodeDto.class, beanToBeUpdated instanceof ProductSpecialCodeDto);
			ProductSpecialCodeDto castedBean = (ProductSpecialCodeDto) beanToBeUpdated;
			assertEquals("ProductSpecialCode short code must be equals to unique key", shortCode, castedBean.getShortCode());
			assertNotNull("ProductSpecialCode labels must not be null", castedBean.getLabels());
			assertEquals("Check ProductSpecialCode labels size", labels.size(), castedBean.getLabels().size());
			// Update the created bean
			shortCode = "@";
			castedBean.setShortCode(shortCode);
			labels.put(2L, "Test 2 ES");
			castedBean.setLabels(labels);
			castedBean = (ProductSpecialCodeDto) this.getInstance().update(castedBean, userContext);
			// Reload the modified bean
			ProductSpecialCodeDto updatedBean = new ProductSpecialCodeDto();
			updatedBean.setId(castedBean.getId());
			updatedBean = (ProductSpecialCodeDto) this.getInstance().load(updatedBean, userContext);
			assertEquals("ProductSpecialCode short code must be equals to unique key", shortCode, castedBean.getShortCode());
			assertNotNull("ProductSpecialCode labels must not be null", castedBean.getLabels());
			assertEquals("Check ProductSpecialCode labels size", labels.size(), castedBean.getLabels().size());
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
	}

	@Override
	public void doProcessList() {
		ProductSpecialCodesManagerViewBean viewBean = new ProductSpecialCodesManagerViewBean();
		viewBean.setDtoBean(createNewBean());
		try {
			this.getInstance().processList(viewBean, DefaultAdministrationManagerTest.userContext);
			assertNotNull("Main list not be null", viewBean.getList());
			assertFalse("Main list not be empty", viewBean.getList().isEmpty());
			assertNotNull("Labels list not be null", viewBean.getLabels());
			assertFalse("Labels list not be empty", viewBean.getLabels().isEmpty());
			assertNotNull("Languages list not be null", viewBean.getLanguages());
			assertFalse("Languages list not be empty", viewBean.getLanguages().isEmpty());
			assertNotNull("Codes list not be null", viewBean.getCodes());
			assertFalse("Codes list not be empty", viewBean.getCodes().isEmpty());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof IProductSpecialCodesManager);
		assertTrue(this.getInstance() instanceof DefaultProductSpecialCodesManager);
	}

	private IMdoDtoBean createNewBean(String shortCode, RestaurantDto restaurant, MdoTableAsEnumDto code, Map<Long, String> labels) {
		ProductSpecialCodeDto newBean = new ProductSpecialCodeDto();
		newBean.setShortCode(shortCode);
		newBean.setRestaurant(restaurant);
		newBean.setCode(code);
		newBean.setLabels(labels);
		return newBean;
	}

}
