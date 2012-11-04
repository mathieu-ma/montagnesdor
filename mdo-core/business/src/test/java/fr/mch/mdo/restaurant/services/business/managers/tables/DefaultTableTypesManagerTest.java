package fr.mch.mdo.restaurant.services.business.managers.tables;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.Locale;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumDto;
import fr.mch.mdo.restaurant.dto.beans.TableTypeDto;
import fr.mch.mdo.restaurant.dto.beans.TableTypesManagerViewBean;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.DefaultAdministrationManagerTest;
import fr.mch.mdo.restaurant.services.business.managers.DefaultMdoTableAsEnumsManager;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultTableTypesManagerTest extends DefaultAdministrationManagerTest 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultTableTypesManagerTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultTableTypesManagerTest.class);
	}

	@Override
	protected IAdministrationManager getInstance() {
		return DefaultTableTypesManager.getInstance();
	}

	@Override
	protected IMdoDtoBean createNewBean() {
		MdoTableAsEnumDto code = new MdoTableAsEnumDto();
		// Use the existing data in database
		code.setId(1L);
		try {
			code = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(code.getId());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		return createNewBean(code);
	}

	@Override
	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		MdoTableAsEnumDto code = new MdoTableAsEnumDto();
		// Use the existing data in database
		code.setId(2L);
		try {
			code = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(code.getId());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		list.add(createNewBean(code));
		// Use the existing data in database
		code = new MdoTableAsEnumDto();
		code.setId(3L);
		try {
			code = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(code.getId());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		list.add(createNewBean(code));
		return list;
	}

	@Override
	public void doUpdate() {
		MdoTableAsEnumDto code = new MdoTableAsEnumDto();
		// Use the existing data in database
		code.setId(4L);
		try {
			code = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(code.getId());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(createNewBean(code));
			assertTrue("IMdoBean must be instance of " + Locale.class, beanToBeUpdated instanceof TableTypeDto);
			TableTypeDto castedBean = (TableTypeDto) beanToBeUpdated;
			assertNotNull("TableTypeDto code must not be null", castedBean.getCode());
			assertEquals("TableTypeDto code must be equals to unique key", code.getLanguageKeyLabel(), castedBean.getCode().getLanguageKeyLabel());
			// Use the existing data in database
			code.setId(5L);
			code = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsManager.getInstance().findByPrimaryKey(code.getId());
			// Update the created bean
			castedBean.setCode(code);
			castedBean = (TableTypeDto) this.getInstance().update(castedBean);
			// Reload the modified bean
			TableTypeDto updatedBean = new TableTypeDto();
			updatedBean.setId(castedBean.getId());
			IMdoBean loadedBean = this.getInstance().load(updatedBean);
			assertTrue("IMdoBean must be instance of " + TableTypeDto.class, loadedBean instanceof TableTypeDto);
			updatedBean = (TableTypeDto) loadedBean;
			assertNotNull("TableTypeDto code must not be null", updatedBean.getCode());
			assertEquals("TableTypeDto code must be equals to unique key", code.getLanguageKeyLabel(), updatedBean.getCode().getLanguageKeyLabel());
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	@Override
	public void doProcessList() {
		TableTypesManagerViewBean viewBean = new TableTypesManagerViewBean();
		try {
			this.getInstance().processList(viewBean);
			assertNotNull("Main list not be null", viewBean.getList());
			assertFalse("Main list not be empty", viewBean.getList().isEmpty());
			assertNotNull("Codes list not be null", viewBean.getCodes());
			assertTrue("Codes list be empty because all available table types are already set into database", viewBean.getCodes().isEmpty());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof ITableTypesManager);
		assertTrue(this.getInstance() instanceof DefaultTableTypesManager);
	}

	public void testFindByCodeName() {
		String codeName = null;
		try {
			((ITableTypesManager) this.getInstance()).findByCodeName(codeName);
			fail(DEFAULT_FAILED_MESSAGE);
		} catch (MdoException e) {
			assertNotNull("Exception because codeName null", e);
		}
		try {
			codeName = "";
			TableTypeDto tableType = ((ITableTypesManager) this.getInstance()).findByCodeName(codeName);
			assertNull("TableTypeDto must be null", tableType);

			codeName = "TAKE_AWAY";
			tableType = ((ITableTypesManager) this.getInstance()).findByCodeName(codeName);
			assertNotNull("TableTypeDto must NOT be null", tableType);
			assertEquals("Check TableTypeDto code name", codeName, tableType.getCode().getName());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	private IMdoDtoBean createNewBean(MdoTableAsEnumDto code) {
		TableTypeDto newBean = new TableTypeDto();
		newBean.setCode(code);
		return newBean;
	}
}
