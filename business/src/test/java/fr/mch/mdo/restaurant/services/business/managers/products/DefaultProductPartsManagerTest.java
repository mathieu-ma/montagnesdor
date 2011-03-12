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
import fr.mch.mdo.restaurant.dto.beans.ProductPartDto;
import fr.mch.mdo.restaurant.dto.beans.ProductPartsManagerViewBean;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.DefaultAdministrationManagerTest;
import fr.mch.mdo.restaurant.services.business.managers.DefaultMdoTableAsEnumsManager;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultProductPartsManagerTest extends DefaultAdministrationManagerTest {
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultProductPartsManagerTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultProductPartsManagerTest.class);
	}

	@Override
	protected IAdministrationManager getInstance() {
		return DefaultProductPartsManager.getInstance();
	}

	@Override
	protected IMdoDtoBean createNewBean() {
		MdoTableAsEnumDto code = new MdoTableAsEnumDto();
		// Use the existing data in database
		code.setId(1L);
		try {
			code = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(code.getId(), userContext);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		Map<Long, String> labels = null;
		return createNewBean(code, labels);
	}

	@Override
	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		MdoTableAsEnumDto code = new MdoTableAsEnumDto();
		// Use the existing data in database
		code.setId(2L);
		try {
			code = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(code.getId(), userContext);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		Map<Long, String> labels = new HashMap<Long, String>();
		Long localeId = 1L;
		String label = "Poisson";
		labels.put(localeId, label);
		list.add(createNewBean(code, labels));
		
		// Use the existing data in database
		code = new MdoTableAsEnumDto();
		code.setId(3L);
		try {
			code = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(code.getId(), userContext);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		labels = new HashMap<Long, String>();
		localeId = 1L;
		label = "Crabe";
		labels.put(localeId, label);
		list.add(createNewBean(code, labels));
		return list;
	}

	@Override
	public void doUpdate() {
		MdoTableAsEnumDto code = new MdoTableAsEnumDto();
		// Use the existing data in database
		code.setId(4L);
		try {
			code = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(code.getId(), userContext);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		Map<Long, String> labels = new HashMap<Long, String>();
		Long localeId = 1L;
		String label = "Poisson";
		labels.put(localeId, label);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(createNewBean(code, labels), DefaultAdministrationManagerTest.userContext);
			assertTrue("IMdoBean must be instance of " + ProductPartDto.class, beanToBeUpdated instanceof ProductPartDto);
			ProductPartDto castedBean = (ProductPartDto) beanToBeUpdated;
			assertNotNull("ProductPartDto code must not be null", castedBean.getCode());
			assertEquals("ProductPartDto code must be equals to unique key", code.getLanguageKeyLabel(), castedBean.getCode().getLanguageKeyLabel());
			assertNotNull("ProductPartDto labels must not be null", castedBean.getLabels());
			assertFalse("ProductPartDto labels must not be empty", castedBean.getLabels().isEmpty());
			assertEquals("Check ProductPartDto labels size", labels.size(), castedBean.getLabels().size());
			// Use the existing data in database
			code.setId(5L);
			code = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(code.getId(), userContext);
			// Update the created bean
			castedBean.setCode(code);
			castedBean = (ProductPartDto) this.getInstance().update(castedBean, DefaultAdministrationManagerTest.userContext);
			// Reload the modified bean
			ProductPartDto updatedBean = new ProductPartDto();
			updatedBean.setId(castedBean.getId());
			localeId = 2L;
			label = "Poisson 2";
			labels.put(localeId, label);
			updatedBean.setLabels(labels);
			IMdoBean loadedBean = this.getInstance().load(updatedBean, DefaultAdministrationManagerTest.userContext);
			assertTrue("IMdoBean must be instance of " + ProductPartDto.class, loadedBean instanceof ProductPartDto);
			updatedBean = (ProductPartDto) loadedBean;
			assertNotNull("ProductPartDto code must not be null", updatedBean.getCode());
			assertEquals("ProductPartDto code must be equals to unique key", code.getLanguageKeyLabel(), updatedBean.getCode().getLanguageKeyLabel());
			assertNotNull("ProductPartDto labels must not be null", castedBean.getLabels());
			assertFalse("ProductPartDto labels must not be empty", castedBean.getLabels().isEmpty());
			assertEquals("Check ProductPartDto labels size", labels.size(), castedBean.getLabels().size());
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
	}

	@Override
	public void doProcessList() {
		ProductPartsManagerViewBean viewBean = new ProductPartsManagerViewBean();
		try {
			this.getInstance().processList(viewBean, DefaultAdministrationManagerTest.userContext);
			assertNotNull("Main list not be null", viewBean.getList());
			assertFalse("Main list not be empty", viewBean.getList().isEmpty());
			assertNotNull("Languages list not be null", viewBean.getLanguages());
			assertFalse("Languages list not be empty", viewBean.getLanguages().isEmpty());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof IProductPartsManager);
		assertTrue(this.getInstance() instanceof DefaultProductPartsManager);
	}

	private IMdoDtoBean createNewBean(MdoTableAsEnumDto code, Map<Long, String> labels) {
		ProductPartDto newBean = new ProductPartDto();
		newBean.setCode(code);
		newBean.setLabels(labels);
		return newBean;
	}


}
