package fr.mch.mdo.restaurant.services.business.managers.printings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.LocaleDto;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumDto;
import fr.mch.mdo.restaurant.dto.beans.PrintingInformationDto;
import fr.mch.mdo.restaurant.dto.beans.PrintingInformationsManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.DefaultAdministrationManagerTest;
import fr.mch.mdo.restaurant.services.business.managers.DefaultMdoTableAsEnumsManager;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;
import fr.mch.mdo.restaurant.services.business.managers.restaurants.DefaultRestaurantsManager;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultPrintingInformationsManagerTest extends DefaultAdministrationManagerTest
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DefaultPrintingInformationsManagerTest(String testName)
    {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(DefaultPrintingInformationsManagerTest.class);
    }

	@Override
	protected IAdministrationManager getInstance() {
		return DefaultPrintingInformationsManager.getInstance();
	}

	@Override
	protected IMdoDtoBean createNewBean() {
		int order = 1;
		RestaurantDto restaurant = new RestaurantDto();
		// Use the existing data in database
		restaurant.setId(1L);
		MdoTableAsEnumDto alignment = new MdoTableAsEnumDto();
		// Use the existing data in database
		alignment.setId(4L);
		try {
			alignment = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(alignment.getId(), userContext);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		MdoTableAsEnumDto size = new MdoTableAsEnumDto();
		// Use the existing data in database
		size.setId(7L);
		try {
			size = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(size.getId(), userContext);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		MdoTableAsEnumDto part = new MdoTableAsEnumDto();
		// Use the existing data in database
		part.setId(10L);
		try {
			part = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(part.getId(), userContext);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		Map<Long, String> labels = null;
		
		return createNewBean(alignment, order, part, restaurant, size, labels);
	}

	@Override
	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		int order = 2;
		RestaurantDto restaurant = new RestaurantDto();
		// Use the existing data in database
		restaurant.setId(1L);
		try {
			restaurant = (RestaurantDto) DefaultRestaurantsManager.getInstance().findByPrimaryKey(restaurant.getId(), userContext);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		MdoTableAsEnumDto alignment = new MdoTableAsEnumDto();
		// Use the existing data in database
		alignment.setId(5L);
		try {
			alignment = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(alignment.getId(), userContext);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		MdoTableAsEnumDto size = new MdoTableAsEnumDto();
		// Use the existing data in database
		size.setId(8L);
		try {
			size = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(size.getId(), userContext);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		MdoTableAsEnumDto part = new MdoTableAsEnumDto();
		// Use the existing data in database
		part.setId(11L);
		try {
			part = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(part.getId(), userContext);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		Map<Long, String> labels = new HashMap<Long, String>();
		Long localeId = 1L;
		String label = "Partie Haut - Information Haut";
		labels.put(localeId, label);
		list.add(createNewBean(alignment, order, part, restaurant, size, labels));
		
		order = 3;
		restaurant = new RestaurantDto();
		// Use the existing data in database
		restaurant.setId(1L);
		try {
			restaurant = (RestaurantDto) DefaultRestaurantsManager.getInstance().findByPrimaryKey(restaurant.getId(), userContext);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		alignment = new MdoTableAsEnumDto();
		// Use the existing data in database
		alignment.setId(6L);
		try {
			alignment = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(alignment.getId(), userContext);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		size = new MdoTableAsEnumDto();
		// Use the existing data in database
		size.setId(9L);
		try {
			size = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(size.getId(), userContext);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		part = new MdoTableAsEnumDto();
		// Use the existing data in database
		part.setId(11L);
		try {
			part = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(part.getId(), userContext);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		labels = new HashMap<Long, String>();
		localeId = 1L;
		label = "Partie Bas - Information Bas";
		labels.put(localeId, label);
		list.add(createNewBean(alignment, order, part, restaurant, size, labels));
		return list;
	}

	@Override
	public void doUpdate() {
		int order = 4;
		RestaurantDto restaurant = new RestaurantDto();
		// Use the existing data in database
		restaurant.setId(1L);

		MdoTableAsEnumDto alignment = new MdoTableAsEnumDto();
		// Use the existing data in database
		alignment.setId(5L);
		try {
			alignment = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(alignment.getId(), userContext);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		MdoTableAsEnumDto size = new MdoTableAsEnumDto();
		// Use the existing data in database
		size.setId(8L);
		try {
			size = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(size.getId(), userContext);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		MdoTableAsEnumDto part = new MdoTableAsEnumDto();
		// Use the existing data in database
		part.setId(11L);
		try {
			part = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(part.getId(), userContext);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		Map<Long, String> labels = new HashMap<Long, String>();
		Long localeId = 1L;
		String label = "Haut Droite Taille 4";
		labels.put(localeId, label);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(createNewBean(alignment, order, part, restaurant, size, labels), DefaultAdministrationManagerTest.userContext);
			assertTrue("IMdoBean must be instance of " + PrintingInformationDto.class, beanToBeUpdated instanceof PrintingInformationDto);
			PrintingInformationDto castedBean = (PrintingInformationDto) beanToBeUpdated;
			assertNotNull("PrintingInformationDto Alignment must not be null", castedBean.getAlignment());
			assertEquals("PrintingInformationDto Alignment must be equals to unique key", alignment.getLanguageKeyLabel(), castedBean.getAlignment().getLanguageKeyLabel());
			assertNotNull("PrintingInformationDto labels must not be null", castedBean.getLabels());
			assertFalse("PrintingInformationDto labels must not be empty", castedBean.getLabels().isEmpty());
			assertEquals("Check PrintingInformationDto labels size", labels.size(), castedBean.getLabels().size());
			// Use the existing data in database
			alignment.setId(5L);
			alignment = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(alignment.getId(), userContext);
			// Update the created bean
			castedBean.setAlignment(alignment);
			localeId = 2L;
			label = "Haut Droite Taille 5";
			labels.put(localeId, label);
			castedBean.setLabels(labels);
			castedBean = (PrintingInformationDto) this.getInstance().update(castedBean, DefaultAdministrationManagerTest.userContext);
			// Reload the modified bean
			PrintingInformationDto updatedBean = new PrintingInformationDto();
			updatedBean.setId(castedBean.getId());
			IMdoBean loadedBean = this.getInstance().load(updatedBean, DefaultAdministrationManagerTest.userContext);
			assertTrue("IMdoBean must be instance of " + PrintingInformationDto.class, loadedBean instanceof PrintingInformationDto);
			updatedBean = (PrintingInformationDto) loadedBean;
			assertNotNull("PrintingInformationDto code must not be null", updatedBean.getAlignment());
			assertEquals("PrintingInformationDto code must be equals to unique key", alignment.getLanguageKeyLabel(), updatedBean.getAlignment().getLanguageKeyLabel());
			assertNotNull("PrintingInformationDto labels must not be null", updatedBean.getLabels());
			assertFalse("PrintingInformationDto labels must not be empty", updatedBean.getLabels().isEmpty());
			assertEquals("Check PrintingInformationDto labels size", labels.size(), updatedBean.getLabels().size());
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
	}

	@Override
	public void doProcessList() {
		PrintingInformationsManagerViewBean viewBean = new PrintingInformationsManagerViewBean();
		try {
			this.getInstance().processList(viewBean, DefaultAdministrationManagerTest.userContext);
			assertNotNull("Main list not be null", viewBean.getList());
			assertFalse("Main list not be empty", viewBean.getList().isEmpty());
			assertNotNull("Labels map not be null", viewBean.getLabels());
			assertFalse("Labels map not be empty", viewBean.getLabels().isEmpty());
			assertNotNull("Languages list not be null", viewBean.getLanguages());
			assertFalse("Languages list not be empty", viewBean.getLanguages().isEmpty());
			assertNotNull("Alignments list not be null", viewBean.getAlignments());
			assertFalse("Alignments list not be empty", viewBean.getAlignments().isEmpty());
			assertNotNull("Parts list not be null", viewBean.getParts());
			assertFalse("Parts list not be empty", viewBean.getParts().isEmpty());
			assertNotNull("Restaurants list not be null", viewBean.getRestaurants());
			assertFalse("Restaurants list not be empty", viewBean.getRestaurants().isEmpty());
			assertNotNull("Sizes list not be null", viewBean.getSizes());
			assertFalse("Sizes list not be empty", viewBean.getSizes().isEmpty());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	public void testGetLabels() {
		LocaleDto currentLocale = new LocaleDto();
		currentLocale.setId(1L);
		try {
			Map<Long, String> labels = ((DefaultPrintingInformationsManager) this.getInstance()).getLabels(currentLocale);
			assertNotNull("Check labels not null", labels);
			assertTrue("Check labels size", labels.size()>0);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
		
	}
	
	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof IPrintingInformationsManager);
		assertTrue(this.getInstance() instanceof DefaultPrintingInformationsManager);
	}

    private IMdoDtoBean createNewBean(MdoTableAsEnumDto alignment, int order, MdoTableAsEnumDto part, RestaurantDto restaurant, MdoTableAsEnumDto size, Map<Long, String> labels) {
    	PrintingInformationDto newBean = new PrintingInformationDto();
    	newBean.setAlignment(alignment);
    	newBean.setOrder(order);
    	newBean.setPart(part);
    	newBean.setRestaurant(restaurant);
    	newBean.setSize(size);
    	
    	newBean.setLabels(labels);
    	return newBean;
    }
    
	/**
	 * Test the getList method.
	 */
	public void testGetList() {
		Long restaurantId = 1L;
		try {
			List<IMdoDtoBean> list= ((IPrintingInformationsManager) DefaultPrintingInformationsManager.getInstance()).getList(restaurantId, userContext);
			assertNotNull("List must not be null", list);
			assertFalse("List must not be empty", list.isEmpty());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}	
	}

}
