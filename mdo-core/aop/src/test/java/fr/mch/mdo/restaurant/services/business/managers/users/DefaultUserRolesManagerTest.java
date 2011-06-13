package fr.mch.mdo.restaurant.services.business.managers.users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumDto;
import fr.mch.mdo.restaurant.dto.beans.UserRoleDto;
import fr.mch.mdo.restaurant.dto.beans.UserRolesManagerViewBean;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.DefaultAdministrationManagerTest;
import fr.mch.mdo.restaurant.services.business.managers.DefaultMdoTableAsEnumsManager;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultUserRolesManagerTest extends DefaultAdministrationManagerTest 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultUserRolesManagerTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultUserRolesManagerTest.class);
	}

	@Override
	protected IAdministrationManager getInstance() {
		return DefaultUserRolesManager.getInstance();
	}

	@Override
	protected IMdoDtoBean createNewBean() {
		// Use the existing data in database
		MdoTableAsEnumDto code = new MdoTableAsEnumDto();
		try {
			code = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(13L, userContext);
		} catch (MdoException e) {
			fail("Could not found the user role code.");
		}
		Map<Long, String> labels = null;
		return createNewBean(code, labels);
	}

	@Override
	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		// Use the existing data in database
		MdoTableAsEnumDto code = new MdoTableAsEnumDto();
		try {
			code = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(14L, userContext);
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

	@Override
	public void doUpdate() {
		IMdoDtoBean newBean = null;
		// Use the existing data in database
		MdoTableAsEnumDto code = new MdoTableAsEnumDto();
		try {
			code = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(15L, userContext);
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
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean, userContext);
			assertTrue("IMdoBean must be instance of " + UserRoleDto.class, beanToBeUpdated instanceof UserRoleDto);
			UserRoleDto castedBean = (UserRoleDto) beanToBeUpdated;
			assertNotNull("UserRole code must not be null", castedBean.getCode());
			assertEquals("UserRole name must be equals to the inserted value", code.toString(), castedBean.getCode().toString());
			assertNotNull("UserRole labels must not be null", castedBean.getLabels());
			assertEquals("Check UserRole labels size", labels.size(), castedBean.getLabels().size());
			// Update the created bean
			labels.clear();
			localeId = 1L;
			label = "Boy Administrator";
			labels.put(localeId, label);
			castedBean.setLabels(labels);
			this.getInstance().update(castedBean, userContext);
			// Reload the modified bean
			UserRoleDto updatedBean = new UserRoleDto();
			updatedBean.setId(castedBean.getId());
			updatedBean = (UserRoleDto) this.getInstance().load(updatedBean, userContext);
			assertNotNull("UserRole code must not be null", updatedBean.getCode());
			assertEquals("UserRole name must be equals to the updated value", code.toString(), updatedBean.getCode().toString());
			assertNotNull("UserRole labels must not be null", updatedBean.getLabels());
			assertEquals("Check UserRole labels size", labels.size(), updatedBean.getLabels().size());
			this.getInstance().delete(updatedBean, userContext);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Override
	public void doProcessList() {
		UserRolesManagerViewBean viewBean = new UserRolesManagerViewBean();
		try {
			this.getInstance().processList(viewBean, DefaultAdministrationManagerTest.userContext);
			assertNotNull("Main list must not be null", viewBean.getList());
			assertFalse("Main list must not be empty", viewBean.getList().isEmpty());
			assertNotNull("Restaurants list must not be null", viewBean.getLabels());
			assertFalse("Restaurants list must not be empty", viewBean.getLabels().isEmpty());
			assertNotNull("Languages map must not be null", viewBean.getLanguages());
			assertFalse("Languages map must not be empty", viewBean.getLanguages().isEmpty());
			assertNotNull("Roles List must not be null", viewBean.getCodes());
			assertFalse("Roles List must not be empty", viewBean.getCodes().isEmpty());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
	}

	public void testFindByCode() {
		IMdoBean dtoBean;
		try {
			dtoBean = this.getInstance().findByPrimaryKey(1L, DefaultAdministrationManagerTest.userContext);
			assertTrue("IMdoBean must be instance of " + UserRoleDto.class, dtoBean instanceof UserRoleDto);
			UserRoleDto castedBean = (UserRoleDto) dtoBean;
			assertNotNull("Code is not null", castedBean.getCode());
			dtoBean = ((IUserRolesManager) this.getInstance()).findByCode(castedBean.getCode().getName(), DefaultAdministrationManagerTest.userContext);
			assertTrue("IMdoBean must be instance of " + UserRoleDto.class, dtoBean instanceof UserRoleDto);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getLocalizedMessage());
		}
	}
	
	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof IUserRolesManager);
		assertTrue(this.getInstance() instanceof DefaultUserRolesManager);
	}

	private IMdoDtoBean createNewBean(MdoTableAsEnumDto code, Map<Long, String> labels) {
		UserRoleDto newBean = new UserRoleDto();
		newBean.setCode(code);
		newBean.setLabels(labels);
		return newBean;
	}
}
