package fr.mch.mdo.restaurant.services.business.managers.products;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.CategoriesManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.CategoryDto;
import fr.mch.mdo.restaurant.dto.beans.LocaleDto;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumDto;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.DefaultAdministrationManagerTest;
import fr.mch.mdo.restaurant.services.business.managers.DefaultMdoTableAsEnumsManager;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;
import fr.mch.mdo.restaurant.services.business.managers.ICategoriesManager;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultCategoriesManagerTest extends DefaultAdministrationManagerTest
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DefaultCategoriesManagerTest(String testName)
    {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(DefaultCategoriesManagerTest.class);
    }

	@Override
	protected IAdministrationManager getInstance() {
		return DefaultCategoriesManager.getInstance();
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
			assertTrue("IMdoBean must be instance of " + CategoryDto.class, beanToBeUpdated instanceof CategoryDto);
			CategoryDto castedBean = (CategoryDto) beanToBeUpdated;
			assertNotNull("CategoryDto code must not be null", castedBean.getCode());
			assertEquals("CategoryDto code must be equals to unique key", code.getLanguageKeyLabel(), castedBean.getCode().getLanguageKeyLabel());
			assertNotNull("CategoryDto labels must not be null", castedBean.getLabels());
			assertFalse("CategoryDto labels must not be empty", castedBean.getLabels().isEmpty());
			assertEquals("Check CategoryDto labels size", labels.size(), castedBean.getLabels().size());
			// Use the existing data in database
			code.setId(5L);
			code = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(code.getId(), userContext);
			// Update the created bean
			castedBean.setCode(code);
			localeId = 2L;
			label = "Poisson 2";
			labels.put(localeId, label);
			castedBean.setLabels(labels);
			castedBean = (CategoryDto) this.getInstance().update(castedBean, DefaultAdministrationManagerTest.userContext);
			// Reload the modified bean
			CategoryDto updatedBean = new CategoryDto();
			updatedBean.setId(castedBean.getId());
			IMdoBean loadedBean = this.getInstance().load(updatedBean, DefaultAdministrationManagerTest.userContext);
			assertTrue("IMdoBean must be instance of " + CategoryDto.class, loadedBean instanceof CategoryDto);
			updatedBean = (CategoryDto) loadedBean;
			assertNotNull("CategoryDto code must not be null", updatedBean.getCode());
			assertEquals("CategoryDto code must be equals to unique key", code.getLanguageKeyLabel(), updatedBean.getCode().getLanguageKeyLabel());
			assertNotNull("CategoryDto labels must not be null", updatedBean.getLabels());
			assertFalse("CategoryDto labels must not be empty", updatedBean.getLabels().isEmpty());
			assertEquals("Check CategoryDto labels size", labels.size(), updatedBean.getLabels().size());
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
	}

	@Override
	public void doProcessList() {
		CategoriesManagerViewBean viewBean = new CategoriesManagerViewBean();
		try {
			this.getInstance().processList(viewBean, DefaultAdministrationManagerTest.userContext);
			assertNotNull("Main list not be null", viewBean.getList());
			assertFalse("Main list not be empty", viewBean.getList().isEmpty());
			assertNotNull("Codes list not be null", viewBean.getCodes());
			assertFalse("Codes list not be empty", viewBean.getCodes().isEmpty());
			assertNotNull("Labels map not be null", viewBean.getLabels());
			assertFalse("Labels map not be empty", viewBean.getLabels().isEmpty());
			assertNotNull("Languages list not be null", viewBean.getLanguages());
			assertFalse("Languages list not be empty", viewBean.getLanguages().isEmpty());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	public void testGetLabels() {
		LocaleDto currentLocale = new LocaleDto();
		currentLocale.setId(1L);
		try {
			Map<Long, String> labels = ((DefaultCategoriesManager) this.getInstance()).getLabels(currentLocale);
			assertNotNull("Check labels not null", labels);
			assertTrue("Check labels size", labels.size()>0);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
		
	}
	
	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof ICategoriesManager);
		assertTrue(this.getInstance() instanceof DefaultCategoriesManager);
	}

    private IMdoDtoBean createNewBean(MdoTableAsEnumDto code, Map<Long, String> labels) {
    	CategoryDto newBean = new CategoryDto();
    	newBean.setCode(code);
    	newBean.setLabels(labels);
    	return newBean;
    }
}
