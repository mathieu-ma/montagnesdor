package fr.mch.mdo.restaurant.dao.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.IMdoTableAsEnumsDao;
import fr.mch.mdo.restaurant.dao.MdoTableAsEnumTypeDao;
import fr.mch.mdo.restaurant.dao.beans.MdoString;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultMdoTableAsEnumsDaoTest extends DefaultDaoServicesTestCase 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultMdoTableAsEnumsDaoTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultMdoTableAsEnumsDaoTest.class);
	}

	protected IDaoServices getInstance() {
		return DefaultMdoTableAsEnumsDao.getInstance();
	}

	protected IMdoBean createNewBean() {
		MdoTableAsEnumTypeDao type = MdoTableAsEnumTypeDao.PREFIX_TABLE_NAME;
		String name = "C";
		int order = 2;
		String defaultLabel = "C";
		String languageKeyLabel = type.name() + "." + name + "." + order;

		return createNewBean(type, name, order, defaultLabel, languageKeyLabel);
	}

	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		MdoTableAsEnumTypeDao type = MdoTableAsEnumTypeDao.PREFIX_TABLE_NAME;
		String name = "D";
		int order = 3;
		String defaultLabel = "D";
		String languageKeyLabel = type.name() + "." + name + "." + order;
		list.add(createNewBean(type, name, order, defaultLabel, languageKeyLabel));
		type = MdoTableAsEnumTypeDao.PREFIX_TABLE_NAME;
		name = "E";
		order = 4;
		defaultLabel = "E";
		languageKeyLabel = type.name() + "." + name + "." + order;
		list.add(createNewBean(type, name, order, defaultLabel, languageKeyLabel));
		return list;
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof IMdoTableAsEnumsDao);
		assertTrue(this.getInstance() instanceof DefaultMdoTableAsEnumsDao);
	}

	@Override
	public void doFindByUniqueKey() {
		// MdoTableAsEnum with type PREFIX_TABLE_NAME and name "A" was created
		// at HSQLDB startup
		String type = MdoTableAsEnumTypeDao.PREFIX_TABLE_NAME.name();
		String name = "A";
		try {
			IMdoBean bean = this.getInstance().findByUniqueKey(new Object[] { type, name });
			assertTrue("IMdoBean must be instance of " + MdoTableAsEnum.class, bean instanceof MdoTableAsEnum);
			MdoTableAsEnum castedBean = (MdoTableAsEnum) bean;
			assertNotNull("MdoTableAsEnum type must not be null", castedBean.getType());
			assertNotNull("MdoTableAsEnum name must not be null", castedBean.getName());
			//assertEquals("MdoTableAsEnum type must be equals to unique key", type, castedBean.getType().getValue());
			assertEquals("MdoTableAsEnum type must be equals to unique key", type, castedBean.getType());
			assertEquals("MdoTableAsEnum name must be equals to unique key", name, castedBean.getName());
			assertFalse("MdoTableAsEnum must not be deleted", castedBean.isDeleted());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Override
	public void doUpdate() {
		IMdoBean newBean = null;
		MdoTableAsEnumTypeDao type = MdoTableAsEnumTypeDao.PREFIX_TABLE_NAME;
		String name = "D";
		int order = 3;
		String defaultLabel = "D";
		String languageKeyLabel = type.name() + "." + name + "." + order;
		newBean = createNewBean(type, name, order, defaultLabel, languageKeyLabel);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + MdoTableAsEnum.class, beanToBeUpdated instanceof MdoTableAsEnum);
			MdoTableAsEnum castedBean = (MdoTableAsEnum) beanToBeUpdated;
			assertNotNull("MdoTableAsEnum name must not be null", castedBean.getName());
			assertEquals("MdoTableAsEnum name must be equals to the inserted value", name, castedBean.getName());
			assertFalse("MdoTableAsEnum must not be deleted", castedBean.isDeleted());
			// Update the created bean
			castedBean.setName("E");
			castedBean.setDeleted(true);
			this.getInstance().update(castedBean);
			// Reload the modified bean
			MdoTableAsEnum updatedBean = (MdoTableAsEnum) createNewBean();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertNotNull("MdoTableAsEnum name must not be null", updatedBean.getName());
			assertEquals("MdoTableAsEnum name must be equals to updated value", castedBean.getName(), updatedBean.getName());
			assertTrue("MdoTableAsEnum must be deleted", updatedBean.isDeleted());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
		}
	}

	@Override
	public void doUpdateFieldsAndDeleteByKeysSpecific() {
		IMdoBean newBean = null;
		MdoTableAsEnumTypeDao type = MdoTableAsEnumTypeDao.PREFIX_TABLE_NAME;
		String name = "D";
		int order = 3;
		String defaultLabel = "D";
		String languageKeyLabel = type.name() + "." + name + "." + order;
		newBean = createNewBean(type, name, order, defaultLabel, languageKeyLabel);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + MdoTableAsEnum.class, beanToBeUpdated instanceof MdoTableAsEnum);
			MdoTableAsEnum castedBean = (MdoTableAsEnum) beanToBeUpdated;
			assertNotNull("MdoTableAsEnum name must not be null", castedBean.getName());
			assertEquals("MdoTableAsEnum name must be equals to the inserted value", name, castedBean.getName());
			assertFalse("MdoTableAsEnum must not be deleted", castedBean.isDeleted());

			// Update the created bean
			Map<String, Object> fields = new HashMap<String, Object>();
			Map<String, Object> keys = new HashMap<String, Object>();
			castedBean.setType("type");
			castedBean.setName("name");
			castedBean.setOrder(1);
			castedBean.setLanguageKeyLabel("languageKeyLabel");
			castedBean.setDefaultLabel("defaultLabel");
			fields.put("type", castedBean.getType());
			fields.put("name", castedBean.getName());
			fields.put("order", castedBean.getOrder());
			fields.put("languageKeyLabel", castedBean.getLanguageKeyLabel());
			fields.put("defaultLabel", castedBean.getDefaultLabel());
			keys.put("id", castedBean.getId());
			this.getInstance().updateFieldsByKeys(fields, keys);
			// Reload the modified bean
			MdoTableAsEnum updatedBean = (MdoTableAsEnum) createNewBean();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertEquals("Check updated fields ", castedBean.getType(), updatedBean.getType());
			assertEquals("Check updated fields ", castedBean.getName(), updatedBean.getName());
			assertEquals("Check updated fields ", castedBean.getOrder(), updatedBean.getOrder());
			assertEquals("Check updated fields ", castedBean.getLanguageKeyLabel(), updatedBean.getLanguageKeyLabel());
			assertEquals("Check updated fields ", castedBean.getDefaultLabel(), updatedBean.getDefaultLabel());

			// Delete the bean by keys
			// Take the fields as keys
			super.doDeleteByKeysSpecific(updatedBean, fields);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
		}
	}

	
	public void testGetBeans() throws MdoException {
		// There is already data inserted into database with id 0 and 1
		MdoTableAsEnumTypeDao type = MdoTableAsEnumTypeDao.PREFIX_TABLE_NAME;
		List<MdoTableAsEnum> list = ((DefaultMdoTableAsEnumsDao) this.getInstance()).getBeans(type.name());
		// Only 1 elements
		int expectedSize = 2;
		Map<String, String> expectedData = new LinkedHashMap<String, String>();
		expectedData.put("A", "A");
		expectedData.put("B", "B");
		int expectedBeginId = 0;
		checkList(type, list, expectedSize, expectedData, expectedBeginId);
	}

	public void testGetRestaurantPrefixTakeawayNames() throws MdoException {
		// There is already data inserted into database with id 0 and 1
		MdoTableAsEnumTypeDao type = MdoTableAsEnumTypeDao.PREFIX_TABLE_NAME;
		List<MdoTableAsEnum> list = ((DefaultMdoTableAsEnumsDao) this.getInstance()).getRestaurantPrefixTakeawayNames();
		// Only 1 elements
		int expectedSize = 2;
		Map<String, String> expectedData = new LinkedHashMap<String, String>();
		expectedData.put("A", "A");
		expectedData.put("B", "B");
		int expectedBeginId = 0;
		checkList(type, list, expectedSize, expectedData, expectedBeginId);
	}

	public void testGetSpecificRounds() throws MdoException {
		// There are already data inserted into database with id 2 and 3
		// INSERT INTO t_enum VALUES(2, 'SPECIFIC_ROUND', 'HALF_ROUND', 0,
		// 'HALF_ROUND', 'SPECIFIC_ROUND.HALF_ROUND.0', false);
		// INSERT INTO t_enum VALUES(3, 'SPECIFIC_ROUND', 'TENTH_ROUND', 1,
		// 'TENTH_ROUND', 'SPECIFIC_ROUND.TENTH_ROUND.1', false);
		MdoTableAsEnumTypeDao type = MdoTableAsEnumTypeDao.SPECIFIC_ROUND;
		List<MdoTableAsEnum> list = ((DefaultMdoTableAsEnumsDao) this.getInstance()).getSpecificRounds();
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
		List<MdoTableAsEnum> list = ((DefaultMdoTableAsEnumsDao) this.getInstance()).getPrintingInformationAlignments();
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
		List<MdoTableAsEnum> list = ((DefaultMdoTableAsEnumsDao) this.getInstance()).getPrintingInformationSizes();
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
		List<MdoTableAsEnum> list = ((DefaultMdoTableAsEnumsDao) this.getInstance()).getPrintingInformationParts();
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
		List<MdoTableAsEnum> list = ((IMdoTableAsEnumsDao) this.getInstance()).getUserRoles();
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

	private void checkList(MdoTableAsEnumTypeDao type, List<MdoTableAsEnum> list, int expectedSize, Map<String, String> expectedData, int expectedBeginId) {
		assertNotNull("This list must be not null", list);
		assertFalse("This list must be not empty", list.isEmpty());
		assertEquals("This list must have exactly this size", expectedSize, list.size());
		String name = null;
		int index = 0;
		String defaultLabel = null;
		String languageKeyLabel = null;
		MdoTableAsEnum mdoTableAsEnum = null;
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
			mdoTableAsEnum = list.get(order);
			assertEquals("The id must be equals to this expected value", new Long(order + expectedBeginId), mdoTableAsEnum.getId());
//			assertEquals("The type must be equals to this expected value", type.name(), mdoTableAsEnum.getType().getValue());
			assertEquals("The type must be equals to this expected value", type.name(), mdoTableAsEnum.getType());
			assertEquals("The name must be equals to this expected value", name, mdoTableAsEnum.getName());
			assertEquals("The order must be equals to this expected value", order, mdoTableAsEnum.getOrder());
			assertEquals("The defaultLabel must be equals to this expected value", defaultLabel, mdoTableAsEnum.getDefaultLabel());
			assertEquals("The languageKeyLabel must be equals to this expected value", languageKeyLabel, mdoTableAsEnum.getLanguageKeyLabel());
			assertFalse("The deleted must be equals to this expected value", mdoTableAsEnum.isDeleted());
		}
	}

	public void testFindAllTypes() {
		try {
			List<MdoString> list = ((IMdoTableAsEnumsDao) this.getInstance()).findAllTypes();
			assertNotNull("This list must be not null", list);
			assertFalse("This list must be not empty", list.isEmpty());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
		}
		
	}
	
	private IMdoBean createNewBean(MdoTableAsEnumTypeDao type, String name, int order, String defaultLabel, String languageKeyLabel) {
		MdoTableAsEnum newBean = new MdoTableAsEnum();
		//newBean.setType(new MdoString(type.name()));
		newBean.setType(type.name());
		newBean.setName(name);
		newBean.setOrder(order);
		newBean.setDefaultLabel(defaultLabel);
		newBean.setLanguageKeyLabel(languageKeyLabel);
		return newBean;
	}
}
