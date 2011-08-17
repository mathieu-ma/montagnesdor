package fr.mch.mdo.restaurant.dao.tables.hibernate;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.DaoServicesFactory;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.dao.beans.TableType;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServicesTestCase;
import fr.mch.mdo.restaurant.dao.tables.ITableTypesDao;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultTableTypesDaoTest extends DefaultDaoServicesTestCase 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultTableTypesDaoTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultTableTypesDaoTest.class);
	}

	protected IDaoServices getInstance() {
		return DefaultTableTypesDao.getInstance();
	}

	protected IMdoBean createNewBean() {
		// Use the existing data in database
		MdoTableAsEnum code = new MdoTableAsEnum();
		try {
			code = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(1L);
		} catch (MdoException e) {
			fail("Could not found the Table Type code.");
		}
		return createNewBean(code);
	}

	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		// Use the existing data in database
		MdoTableAsEnum code = new MdoTableAsEnum();
		try {
			code = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(2L);
		} catch (MdoException e) {
			fail("Could not found the Table Type code.");
		}
		list.add(createNewBean(code));
		return list;
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof ITableTypesDao);
		assertTrue(this.getInstance() instanceof DefaultTableTypesDao);
	}

	@Override
	public void doFindByUniqueKey() {
		// TAKE_AWAY code was created at HSQLDB startup
		String codeName = "TAKE_AWAY";
		try {
			IMdoBean bean = this.getInstance().findByUniqueKey(codeName);
			assertTrue("IMdoBean must be instance of " + TableType.class, bean instanceof TableType);
			TableType castedBean = (TableType) bean;
			assertNotNull("TableType code must not be null", castedBean.getCode());
			assertEquals("TableType code must be equals to unique key", codeName, castedBean.getCode().getName());
			assertFalse("TableType must not be deleted", castedBean.isDeleted());
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	@Override
	public void doUpdate() {
		IMdoBean newBean = null;
		// Use the existing data in database
		MdoTableAsEnum code = new MdoTableAsEnum();
		try {
			code = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(19L);
		} catch (MdoException e) {
			fail("Could not found the category code.");
		}
		newBean = createNewBean(code);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + TableType.class, beanToBeUpdated instanceof TableType);
			TableType castedBean = (TableType) beanToBeUpdated;
			assertNotNull("TableType code must not be null", castedBean.getCode());
			assertEquals("TableType name must be equals to the inserted value", code.toString(), castedBean.getCode().toString());
			assertFalse("TableType must not be deleted", castedBean.isDeleted());
			// Update the created bean
			castedBean.setDeleted(true);
			this.getInstance().update(castedBean);
			// Reload the modified bean
			TableType updatedBean = new TableType();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertNotNull("TableType code must not be null", updatedBean.getCode());
			assertEquals("TableType name must be equals to the updated value", code.toString(), updatedBean.getCode().toString());
			assertTrue("TableType must be deleted", updatedBean.isDeleted());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	private IMdoBean createNewBean(MdoTableAsEnum code) {
		TableType newBean = new TableType();
		newBean.setCode(code);
		return newBean;
	}
}
