package fr.mch.mdo.restaurant.dao.restaurants.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.DaoServicesFactory;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.dao.beans.PrintingInformation;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServicesTestCase;
import fr.mch.mdo.restaurant.dao.restaurants.IPrintingInformationsDao;
import fr.mch.mdo.restaurant.exception.MdoException;

public class DefaultPrintingInformationsDaoTest extends DefaultDaoServicesTestCase 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultPrintingInformationsDaoTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultPrintingInformationsDaoTest.class);
	}

	@Override
	protected IDaoServices getInstance() {
		return DefaultPrintingInformationsDao.getInstance();
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof IPrintingInformationsDao);
		assertTrue(this.getInstance() instanceof DefaultPrintingInformationsDao);
	}

	@Override
	protected IMdoBean createNewBean() {
		Restaurant restaurant = new Restaurant();
		restaurant.setId(1L);
		int order = 0;
		MdoTableAsEnum alignment = new MdoTableAsEnum();
		// Use the existing data in database
		try {
			alignment = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(4L);
		} catch (MdoException e) {
			fail("Could not found the printing information alignment.");
		}
		MdoTableAsEnum size = new MdoTableAsEnum();
		// Use the existing data in database
		try {
			size = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(7L);
		} catch (MdoException e) {
			fail("Could not found the printing information size.");
		}
		MdoTableAsEnum part = new MdoTableAsEnum();
		// Use the existing data in database
		try {
			part = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(10L);
		} catch (MdoException e) {
			fail("Could not found the printing information part.");
		}
		Map<Long, String> labels = null;
		return createNewBean(restaurant, order, alignment, size, part, labels);
	}

	@Override
	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		// First bean in list
		IMdoBean newBean = null;
		Restaurant restaurant = new Restaurant();
		restaurant.setId(1L);
		int order = 1;
		MdoTableAsEnum alignment = new MdoTableAsEnum();
		// Use the existing data in database
		try {
			alignment = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(5L);
		} catch (MdoException e) {
			fail("Could not found the printing information alignment.");
		}
		MdoTableAsEnum size = new MdoTableAsEnum();
		// Use the existing data in database
		try {
			size = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(8L);
		} catch (MdoException e) {
			fail("Could not found the printing information size.");
		}
		MdoTableAsEnum part = new MdoTableAsEnum();
		// Use the existing data in database
		try {
			part = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(11L);
		} catch (MdoException e) {
			fail("Could not found the printing information part.");
		}
		Map<Long, String> labels = new HashMap<Long, String>();
		Long localeId = 1L;
		String label = "Partie Haut - Information Haut";
		labels.put(localeId, label);
		newBean = createNewBean(restaurant, order, alignment, size, part, labels);
		list.add(newBean);

		// Second bean in list
		newBean = null;
		restaurant.setId(1L);
		order = 2;
		alignment = new MdoTableAsEnum();
		// Use the existing data in database
		try {
			alignment = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(6L);
		} catch (MdoException e) {
			fail("Could not found the printing information alignment.");
		}
		size = new MdoTableAsEnum();
		// Use the existing data in database
		try {
			size = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(9L);
		} catch (MdoException e) {
			fail("Could not found the printing information size.");
		}
		part = new MdoTableAsEnum();
		// Use the existing data in database
		try {
			part = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(11L);
		} catch (MdoException e) {
			fail("Could not found the printing information part.");
		}
		labels = new HashMap<Long, String>();
		localeId = 1L;
		label = "Partie Bas - Information Bas";
		labels.put(localeId, label);
		newBean = createNewBean(restaurant, order, alignment, size, part, labels);
		list.add(newBean);

		return list;
	}

	@Override
	public void doFindByUniqueKey() {
		assertTrue("There is no field for unique key", true);
	}

	@Override
	public void doUpdate() {
		IMdoBean newBean = null;
		Restaurant restaurant = new Restaurant();
		restaurant.setId(1L);
		int order = 1;
		MdoTableAsEnum alignment = new MdoTableAsEnum();
		// Use the existing data in database
		try {
			alignment = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(5L);
		} catch (MdoException e) {
			fail("Could not found the printing information alignment.");
		}
		MdoTableAsEnum size = new MdoTableAsEnum();
		// Use the existing data in database
		try {
			size = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(8L);
		} catch (MdoException e) {
			fail("Could not found the printing information size.");
		}
		MdoTableAsEnum part = new MdoTableAsEnum();
		// Use the existing data in database
		try {
			part = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(11L);
		} catch (MdoException e) {
			fail("Could not found the printing information part.");
		}
		Map<Long, String> labels = new HashMap<Long, String>();
		Long localeId = 1L;
		String label = "Partie Haut - Information Haut";
		labels.put(localeId, label);
		newBean = createNewBean(restaurant, order, alignment, size, part, labels);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean, false);
			assertTrue("IMdoBean must be instance of " + PrintingInformation.class, beanToBeUpdated instanceof PrintingInformation);
			PrintingInformation castedBean = (PrintingInformation) beanToBeUpdated;
			assertNotNull("PrintingInformation labels must not be null", castedBean.getLabels());
			assertEquals("PrintingInformation labels size must be equals to 1", 1, castedBean.getLabels().size());
			Long firstKey = castedBean.getLabels().keySet().iterator().next();
			assertEquals("Check PrintingInformation first label", label, castedBean.getLabels().get(firstKey));
			assertFalse("PrintingInformation must not be deleted", castedBean.isDeleted());
			// Update the created bean
			String newLabel = "Partie Haut - Information Haut 2";
			castedBean.getLabels().put(firstKey, newLabel);
			castedBean.setDeleted(true);
			this.getInstance().update(castedBean);
			// Reload the modified bean
			PrintingInformation updatedBean = new PrintingInformation();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean, false);
			assertNotNull("PrintingInformation labels must not be null", updatedBean.getLabels());
			assertEquals("PrintingInformation labels size must be equals to 1", 1, updatedBean.getLabels().size());
			assertEquals("Check PrintingInformation first label", newLabel, updatedBean.getLabels().get(firstKey));
			assertTrue("Restaurant must be deleted", updatedBean.isDeleted());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	private IMdoBean createNewBean(Restaurant restaurant, int order, MdoTableAsEnum alignment, MdoTableAsEnum size, MdoTableAsEnum part, Map<Long, String> labels) {
		PrintingInformation newBean = new PrintingInformation();
		newBean.setRestaurant(restaurant);
		newBean.setOrder(order);
		newBean.setAlignment(alignment);
		newBean.setSize(size);
		newBean.setPart(part);
		newBean.setLabels(labels);
		return newBean;
	}

}
