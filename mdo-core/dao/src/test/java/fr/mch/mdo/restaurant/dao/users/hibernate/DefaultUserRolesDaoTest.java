package fr.mch.mdo.restaurant.dao.users.hibernate;

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
import fr.mch.mdo.restaurant.dao.beans.UserRole;
import fr.mch.mdo.restaurant.dao.beans.UserRoleLanguage;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServicesTestCase;
import fr.mch.mdo.restaurant.dao.users.IUserRolesDao;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultUserRolesDaoTest extends DefaultDaoServicesTestCase 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultUserRolesDaoTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultUserRolesDaoTest.class);
	}

	protected IDaoServices getInstance() {
		return DefaultUserRolesDao.getInstance();
	}

	protected IMdoBean createNewBean() {
		// Use the existing data in database
		MdoTableAsEnum code = new MdoTableAsEnum();
		try {
			code = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(13L);
		} catch (MdoException e) {
			fail("Could not found the user role code.");
		}
		Map<Long, String> labels = null;
		return createNewBean(code, labels);
	}

	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		// Use the existing data in database
		MdoTableAsEnum code = new MdoTableAsEnum();
		try {
			code = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(14L);
		} catch (MdoException e) {
			fail("Could not found the user role code.");
		}
		Map<Long, String> labels = new HashMap<Long, String>();
		Long localeId = 1L;
		String label = "Administrateur";
		labels.put(localeId, label);
		list.add(createNewBean(code, labels));
		return list;
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof IUserRolesDao);
		assertTrue(this.getInstance() instanceof DefaultUserRolesDao);
	}

	@Override
	public void doFindByUniqueKey() {
		// GLOBAL_ADMINISTRATOR role was created at HSQLDB startup
		String role = "GLOBAL_ADMINISTRATOR";
		try {
			IMdoBean bean = this.getInstance().findByUniqueKey(role);
			assertTrue("IMdoBean must be instance of " + UserRole.class, bean instanceof UserRole);
			UserRole castedBean = (UserRole) bean;
			assertNotNull("UserRole role must not be null", castedBean.getCode());
			assertEquals("UserRole role must be equals to unique key", role, castedBean.getCode().getName());
			assertFalse("UserRole must not be deleted", castedBean.isDeleted());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Override
	public void doUpdate() {
		IMdoBean newBean = null;
		// Use the existing data in database
		MdoTableAsEnum code = new MdoTableAsEnum();
		try {
			code = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(15L);
		} catch (MdoException e) {
			fail("Could not found the user role code.");
		}
		Map<Long, String> labels = new HashMap<Long, String>();
		Long localeId = 1L;
		String label = "Administrateuse";
		labels.put(localeId, label);
		localeId = 2L;
		label = "Girly Administrator";
		labels.put(localeId, label);
		newBean = createNewBean(code, labels);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + UserRole.class, beanToBeUpdated instanceof UserRole);
			UserRole castedBean = (UserRole) beanToBeUpdated;
			assertNotNull("UserRole code must not be null", castedBean.getCode());
			assertEquals("UserRole name must be equals to the inserted value", code.toString(), castedBean.getCode().toString());
			assertNotNull("UserRole labels must not be null", castedBean.getLabels());
			assertEquals("Check UserRole labels size", labels.size(), castedBean.getLabels().size());
			assertFalse("UserRole must not be deleted", castedBean.isDeleted());
			// Update the created bean
			labels.clear();
			localeId = 1L;
			label = "Boy Administrator";
			labels.put(localeId, label);
			castedBean.setLabels(labels);
			castedBean.setDeleted(true);
			this.getInstance().update(castedBean);
			// Reload the modified bean
			UserRole updatedBean = new UserRole();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertNotNull("UserRole code must not be null", updatedBean.getCode());
			assertEquals("UserRole name must be equals to the updated value", code.toString(), updatedBean.getCode().toString());
			assertNotNull("UserRole labels must not be null", updatedBean.getLabels());
			assertEquals("Check UserRole labels size", labels.size(), updatedBean.getLabels().size());
			assertTrue("UserRole must be deleted", castedBean.isDeleted());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Override
	public void doUpdateFieldsAndDeleteByKeysSpecific() {
		IMdoBean newBean = null;
		// Use the existing data in database
		MdoTableAsEnum code = new MdoTableAsEnum();
		try {
			code = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(15L);
		} catch (MdoException e) {
			fail("Could not found the user role code.");
		}
		Map<Long, String> labels = new HashMap<Long, String>();
		Long localeId = 1L;
		String label = "Administrateuse";
		labels.put(localeId, label);
		localeId = 2L;
		label = "Girly Administrator";
		labels.put(localeId, label);
		newBean = createNewBean(code, labels);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + UserRole.class, beanToBeUpdated instanceof UserRole);
			UserRole castedBean = (UserRole) beanToBeUpdated;
			assertNotNull("UserRole code must not be null", castedBean.getCode());
			assertEquals("UserRole name must be equals to the inserted value", code.toString(), castedBean.getCode().toString());
			assertNotNull("UserRole labels must not be null", castedBean.getLabels());
			assertEquals("Check UserRole labels size", labels.size(), castedBean.getLabels().size());
			assertFalse("UserRole must not be deleted", castedBean.isDeleted());

			// Update the created bean
			Map<String, Object> fields = new HashMap<String, Object>();
			Map<String, Object> keys = new HashMap<String, Object>();
			castedBean.setDeleted(false);
			fields.put("deleted", castedBean.isDeleted());
			keys.put("id", castedBean.getId());
			this.getInstance().updateFieldsByKeys(fields, keys);
			// Reload the modified bean
			UserRole updatedBean = (UserRole) createNewBean();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertEquals("Check updated fields ", castedBean.isDeleted(), updatedBean.isDeleted());

			// Delete the bean by keys
			// Take the fields as keys
			try {
				super.doDeleteByKeysSpecific(updatedBean, keys, true);
			} catch (Exception e) {
				// We Have to delete following tables in the following order deleting the table t_user_role
				// 1) t_user_role_language
				// 2) t_user_authentication
				// 3) t_user_locale
				// 4) t_dinner_table
				// 5) t_table_credit
				// 6) t_table_bill
				// 7) t_table_vat
				// 8) t_order_line
				// 9) t_table_cashing
				// 10) t_cashing_type
				Object parentId = keys.get("id");
				Map<String, Object> childrenKeys = new HashMap<String, Object>();
				childrenKeys.put("parentId", parentId);
				super.doDeleteByKeysSpecific(UserRoleLanguage.class, childrenKeys);
				super.doDeleteByKeysSpecific(updatedBean, keys);
			}
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
		}
	}

	private IMdoBean createNewBean(MdoTableAsEnum code, Map<Long, String> labels) {
		UserRole newBean = new UserRole();
		newBean.setCode(code);
		newBean.setLabels(labels);
		return newBean;
	}
}
