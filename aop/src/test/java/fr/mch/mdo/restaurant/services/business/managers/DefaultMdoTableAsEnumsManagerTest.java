package fr.mch.mdo.restaurant.services.business.managers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.MdoTableAsEnumTypeDao;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumDto;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumsManagerViewBean;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.products.IMdoTableAsEnumsManager;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultMdoTableAsEnumsManagerTest extends DefaultAdministrationManagerTest 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultMdoTableAsEnumsManagerTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultMdoTableAsEnumsManagerTest.class);
	}

	@Override
	protected IAdministrationManager getInstance() {
		return DefaultMdoTableAsEnumsManager.getInstance();
	}

	@Override
	protected IMdoDtoBean createNewBean() {
		MdoTableAsEnumTypeDao type = MdoTableAsEnumTypeDao.PREFIX_TABLE_NAME;
		String name = "B";
		int order = 1;
		String defaultLabel = "B";
		String languageKeyLabel = type.name() + "." + name + "." + order;

		return createNewBean(type, name, order, defaultLabel, languageKeyLabel);
	}

	@Override
	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		MdoTableAsEnumTypeDao type = MdoTableAsEnumTypeDao.PREFIX_TABLE_NAME;
		String name = "C";
		int order = 2;
		String defaultLabel = "C";
		String languageKeyLabel = type.name() + "." + name + "." + order;
		list.add(createNewBean(type, name, order, defaultLabel, languageKeyLabel));
		type = MdoTableAsEnumTypeDao.PREFIX_TABLE_NAME;
		name = "D";
		order = 3;
		defaultLabel = "D";
		languageKeyLabel = type.name() + "." + name + "." + order;
		list.add(createNewBean(type, name, order, defaultLabel, languageKeyLabel));
		return list;
	}

	@Override
	public void doUpdate() {
		IMdoDtoBean newBean = null;
		MdoTableAsEnumTypeDao type = MdoTableAsEnumTypeDao.PREFIX_TABLE_NAME;
		String name = "D";
		int order = 3;
		String defaultLabel = "D";
		String languageKeyLabel = type.name() + "." + name + "." + order;
		newBean = createNewBean(type, name, order, defaultLabel, languageKeyLabel);
		try {
			// Create new bean to be updated
			IMdoDtoBean beanToBeUpdated = this.getInstance().insert(newBean, userContext);
			assertTrue("IMdoBean must be instance of " + MdoTableAsEnumDto.class, beanToBeUpdated instanceof MdoTableAsEnumDto);
			MdoTableAsEnumDto castedBean = (MdoTableAsEnumDto) beanToBeUpdated;
			assertNotNull("MdoTableAsEnumDto name must not be null", castedBean.getName());
			assertEquals("MdoTableAsEnumDto name must be equals to the inserted value", name, castedBean.getName());
			// Update the created bean
			castedBean.setName("E");
			this.getInstance().update(castedBean, userContext);
			// Reload the modified bean
			MdoTableAsEnumDto updatedBean = (MdoTableAsEnumDto) createNewBean();
			updatedBean.setId(castedBean.getId());
			updatedBean = (MdoTableAsEnumDto) this.getInstance().load(updatedBean, userContext);
			assertNotNull("MdoTableAsEnumDto name must not be null", castedBean.getName());
			assertEquals("MdoTableAsEnumDto name must be equals to updated value", castedBean.getName(), updatedBean.getName());
			this.getInstance().delete(updatedBean, userContext);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Override
	public void doProcessList() {
		MdoTableAsEnumsManagerViewBean viewBean = new MdoTableAsEnumsManagerViewBean();
		try {
			this.getInstance().processList(viewBean, DefaultAdministrationManagerTest.userContext);
			assertNotNull("Main list not be null", viewBean.getList());
			assertFalse("Main list not be empty", viewBean.getList().isEmpty());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	public void testGetRestaurantPrefixTakeawayNames() throws MdoException {
		// There is already data inserted into database with id 1
		// INSERT INTO t_enum VALUES(1, 'PREFIX_TABLE_NAME', 'A', 0, 'A',
		// 'PREFIX_TABLE_NAME.A.0', false);
		MdoTableAsEnumTypeDao type = MdoTableAsEnumTypeDao.PREFIX_TABLE_NAME;
		List<IMdoDtoBean> list = ((DefaultMdoTableAsEnumsManager) this.getInstance()).getPrefixTableNames(userContext);
		// Only 1 elements
		int expectedSize = 1;
		Map<String, String> expectedData = new LinkedHashMap<String, String>();
		expectedData.put("A", "A");
		int expectedBeginId = 1;
		checkList(type, list, expectedSize, expectedData, expectedBeginId);
	}

	public void testGetSpecificRounds() throws MdoException {
		// There are already data inserted into database with id 2 and 3
		// INSERT INTO t_enum VALUES(2, 'SPECIFIC_ROUND', 'HALF_ROUND', 0,
		// 'HALF_ROUND', 'SPECIFIC_ROUND.HALF_ROUND.0', false);
		// INSERT INTO t_enum VALUES(3, 'SPECIFIC_ROUND', 'TENTH_ROUND', 1,
		// 'TENTH_ROUND', 'SPECIFIC_ROUND.TENTH_ROUND.1', false);
		MdoTableAsEnumTypeDao type = MdoTableAsEnumTypeDao.SPECIFIC_ROUND;
		List<IMdoDtoBean> list = ((DefaultMdoTableAsEnumsManager) this.getInstance()).getSpecificRounds(userContext);
		// Only 2 elements
		int expectedSize = 2;
		Map<String, String> expectedData = new LinkedHashMap<String, String>();
		expectedData.put("HALF_ROUND", "HALF_ROUND");
		expectedData.put("TENTH_ROUND", "TENTH_ROUND");
		int expectedBeginId = 2;
		checkList(type, list, expectedSize, expectedData, expectedBeginId);
	}

	public void testGetPrintingInformationAlignments() throws MdoException {
		// There are already data inserted into database
		// INSERT INTO t_enum VALUES(4, 'PRINTING_INFORMATION_ALIGNMENT',
		// 'RIGHT', 0, 'Right', 'PRINTING_INFORMATION_ALIGNMENT.RIGHT.0',
		// false);
		// INSERT INTO t_enum VALUES(5, 'PRINTING_INFORMATION_ALIGNMENT',
		// 'CENTER', 1, 'Center', 'PRINTING_INFORMATION_ALIGNMENT.CENTER.1',
		// false);
		// INSERT INTO t_enum VALUES(6, 'PRINTING_INFORMATION_ALIGNMENT',
		// 'LEFT', 2, 'Left', 'PRINTING_INFORMATION_ALIGNMENT.LEFT.2', false);

		MdoTableAsEnumTypeDao type = MdoTableAsEnumTypeDao.PRINTING_INFORMATION_ALIGNMENT;
		List<IMdoDtoBean> list = ((DefaultMdoTableAsEnumsManager) this.getInstance()).getPrintingInformationAlignments(userContext);
		// Only 3 elements
		int expectedSize = 3;
		Map<String, String> expectedData = new LinkedHashMap<String, String>();
		expectedData.put("RIGHT", "Right");
		expectedData.put("CENTER", "Center");
		expectedData.put("LEFT", "Left");
		int expectedBeginId = 4;
		checkList(type, list, expectedSize, expectedData, expectedBeginId);
	}

	public void testGetPrintingInformationSizes() throws MdoException {
		// There are already data inserted into database
		// INSERT INTO t_enum VALUES(7, 'PRINTING_INFORMATION_SIZE', 'SMALL', 0,
		// 'Small', 'PRINTING_INFORMATION_SIZE.SMALL.0', false);
		// INSERT INTO t_enum VALUES(8, 'PRINTING_INFORMATION_SIZE', 'MEDIUM',
		// 1, 'Medium', 'PRINTING_INFORMATION_SIZE.MEDIUM.1', false);
		// INSERT INTO t_enum VALUES(9, 'PRINTING_INFORMATION_SIZE', 'LARGE', 2,
		// 'Large', 'PRINTING_INFORMATION_SIZE.LARGE.2', false);

		MdoTableAsEnumTypeDao type = MdoTableAsEnumTypeDao.PRINTING_INFORMATION_SIZE;
		List<IMdoDtoBean> list = ((DefaultMdoTableAsEnumsManager) this.getInstance()).getPrintingInformationSizes(userContext);
		// Only 3 elements
		int expectedSize = 3;
		Map<String, String> expectedData = new LinkedHashMap<String, String>();
		expectedData.put("SMALL", "Small");
		expectedData.put("MEDIUM", "Medium");
		expectedData.put("LARGE", "Large");
		int expectedBeginId = 7;
		checkList(type, list, expectedSize, expectedData, expectedBeginId);
	}

	public void testGetPrintingInformationParts() throws MdoException {
		// There are already data inserted into database
		// INSERT INTO t_enum VALUES(10, 'PRINTING_INFORMATION_PART', 'HEADER',
		// 0, 'Header', 'PRINTING_INFORMATION_PART.HEADER.0', false);
		// INSERT INTO t_enum VALUES(11, 'PRINTING_INFORMATION_PART', 'FOOTER',
		// 1, 'Footer', 'PRINTING_INFORMATION_PART.FOOTER.1', false);

		MdoTableAsEnumTypeDao type = MdoTableAsEnumTypeDao.PRINTING_INFORMATION_PART;
		List<IMdoDtoBean> list = ((DefaultMdoTableAsEnumsManager) this.getInstance()).getPrintingInformationParts(userContext);
		// Only 2 elements
		int expectedSize = 2;
		Map<String, String> expectedData = new LinkedHashMap<String, String>();
		expectedData.put("HEADER", "Header");
		expectedData.put("FOOTER", "Footer");
		int expectedBeginId = 10;
		checkList(type, list, expectedSize, expectedData, expectedBeginId);
	}

	public void testGetUserRoles() throws MdoException {
		// There are already data inserted into database
		// INSERT INTO t_enum VALUES(12, 'USER_ROLE', 'GLOBAL_ADMINISTRATOR', 0,
		// 'Global Administrator', 'USER_ROLE.GLOBAL_ADMINISTRATOR.0', false);
		// INSERT INTO t_enum VALUES(13, 'USER_ROLE', 'ADMINISTRATOR', 1,
		// 'Administrator', 'USER_ROLE.ADMINISTRATOR.1', false);
		// INSERT INTO t_enum VALUES(14, 'USER_ROLE', 'EMPLOYEE', 2, 'Employee',
		// 'USER_ROLE.EMPLOYEE.2', false);
		// INSERT INTO t_enum VALUES(15, 'USER_ROLE', 'CUSTOMER', 3, 'Customer',
		// 'USER_ROLE.CUSTOMER.3', false);

		MdoTableAsEnumTypeDao type = MdoTableAsEnumTypeDao.USER_ROLE;
		List<IMdoDtoBean> list = ((DefaultMdoTableAsEnumsManager) this.getInstance()).getUserRoles(userContext);
		// Only 2 elements
		int expectedSize = 4;
		Map<String, String> expectedData = new LinkedHashMap<String, String>();
		expectedData.put("GLOBAL_ADMINISTRATOR", "Global Administrator");
		expectedData.put("ADMINISTRATOR", "Administrator");
		expectedData.put("EMPLOYEE", "Employee");
		expectedData.put("CUSTOMER", "Customer");
		int expectedBeginId = 12;
		checkList(type, list, expectedSize, expectedData, expectedBeginId);
	}

	private void checkList(MdoTableAsEnumTypeDao type, List<IMdoDtoBean> list, int expectedSize, Map<String, String> expectedData, int expectedBeginId) {
		assertNotNull("This list must be not null", list);
		assertFalse("This list must be not empty", list.isEmpty());
		assertEquals("This list must have exactly this size", expectedSize, list.size());
		String name = null;
		int index = 0;
		String defaultLabel = null;
		String languageKeyLabel = null;
		MdoTableAsEnumDto mdoTableAsEnum = null;
		for (int order = 0; order < list.size(); order++) {
			index = 0;
			for (Iterator<String> iterator = expectedData.keySet().iterator(); iterator.hasNext();) {
				name = iterator.next();
				defaultLabel = expectedData.get(name);
				languageKeyLabel = type.name() + "." + name + "." + order;
				if (index == order) {
					break;
				}
				index++;
			}
			mdoTableAsEnum = (MdoTableAsEnumDto) list.get(order);
			assertEquals("The id must be equals to this expected value", new Long(order + expectedBeginId), mdoTableAsEnum.getId());
			assertEquals("The type must be equals to this expected value", type.name(), mdoTableAsEnum.getType());
			assertEquals("The name must be equals to this expected value", name, mdoTableAsEnum.getName());
			assertEquals("The order must be equals to this expected value", order, mdoTableAsEnum.getOrder());
			assertEquals("The defaultLabel must be equals to this expected value", defaultLabel, mdoTableAsEnum.getDefaultLabel());
			assertEquals("The languageKeyLabel must be equals to this expected value", languageKeyLabel, mdoTableAsEnum.getLanguageKeyLabel());
		}
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof IMdoTableAsEnumsManager);
		assertTrue(this.getInstance() instanceof DefaultMdoTableAsEnumsManager);
	}

	private IMdoDtoBean createNewBean(MdoTableAsEnumTypeDao type, String name, int order, String defaultLabel, String languageKeyLabel) {
		MdoTableAsEnumDto newBean = new MdoTableAsEnumDto();
		newBean.setType(type.name());
		newBean.setName(name);
		newBean.setOrder(order);
		newBean.setDefaultLabel(defaultLabel);
		newBean.setLanguageKeyLabel(languageKeyLabel);
		return newBean;
	}
}
