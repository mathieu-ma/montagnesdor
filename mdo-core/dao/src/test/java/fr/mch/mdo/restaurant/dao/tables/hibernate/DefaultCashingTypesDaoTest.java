package fr.mch.mdo.restaurant.dao.tables.hibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.CashingType;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.dao.beans.TableCashing;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServicesTestCase;
import fr.mch.mdo.restaurant.dao.tables.ICashingTypesDao;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultCashingTypesDaoTest extends DefaultDaoServicesTestCase 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultCashingTypesDaoTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultCashingTypesDaoTest.class);
	}

	protected IDaoServices getInstance() {
		return DefaultCashingTypesDao.getInstance();
	}

	protected IMdoBean createNewBean() {
		BigDecimal amount = BigDecimal.TEN;
		TableCashing tableCashing = new TableCashing();
		tableCashing.setId(2L);
		MdoTableAsEnum type = new MdoTableAsEnum();
		type.setId(36L);
		return createNewBean(amount, tableCashing, type);
	}

	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		BigDecimal amount = BigDecimal.TEN;
		TableCashing tableCashing = new TableCashing();
		tableCashing.setId(2L);
		MdoTableAsEnum type = new MdoTableAsEnum();
		type.setId(37L);
		list.add(createNewBean(amount, tableCashing, type));
		return list;
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof ICashingTypesDao);
		assertTrue(this.getInstance() instanceof DefaultCashingTypesDao);
	}

	@Override
	public void doFindByUniqueKey() {
		try {
			Long tableCashingId = 2L;
			String type = "GENERIC_CASH";
			IMdoBean bean = this.getInstance().findByUniqueKey(new Object[] {tableCashingId, type});
			assertTrue("IMdoBean must be instance of " + CashingType.class, bean instanceof CashingType);
			CashingType castedBean = (CashingType) bean;
			assertNotNull("TableCashing dinner table must not be null", castedBean.getTableCashing());
			assertEquals("Check TableCashing dinner table ID", tableCashingId, castedBean.getTableCashing().getId());
			assertEquals("Check TableCashing type", type, castedBean.getType().getName());
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	@Override
	public void doUpdate() {
		IMdoBean newBean = null;
		BigDecimal amount = BigDecimal.TEN;
		TableCashing tableCashing = new TableCashing();
		tableCashing.setId(2L);
		MdoTableAsEnum type = new MdoTableAsEnum();
		type.setId(38L);
		newBean = createNewBean(amount, tableCashing, type);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + CashingType.class, beanToBeUpdated instanceof CashingType);
			CashingType castedBean = (CashingType) beanToBeUpdated;
			assertNotNull("CashingType has a not be null ID", castedBean.getId());
			assertEquals("Check CashingType type", type.getId(), castedBean.getType().getId());
			assertFalse("CashingType must not be deleted", castedBean.isDeleted());
			// Update the created bean
			amount = new BigDecimal("125");
			castedBean.setAmount(amount);
			castedBean.setDeleted(true);
			this.getInstance().update(castedBean);
			// Reload the modified bean
			CashingType updatedBean = new CashingType();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertEquals("Check CashingType ammount", amount, updatedBean.getAmount());
			assertTrue("CashingType must be deleted", updatedBean.isDeleted());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	@Override
	public void doUpdateFieldsAndDeleteByKeysSpecific() {
		IMdoBean newBean = null;
		BigDecimal amount = BigDecimal.TEN;
		TableCashing tableCashing = new TableCashing();
		tableCashing.setId(2L);
		MdoTableAsEnum type = new MdoTableAsEnum();
		type.setId(38L);
		newBean = createNewBean(amount, tableCashing, type);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + CashingType.class, beanToBeUpdated instanceof CashingType);
			CashingType castedBean = (CashingType) beanToBeUpdated;
			assertNotNull("CashingType has a not be null ID", castedBean.getId());
			assertEquals("Check CashingType type", type.getId(), castedBean.getType().getId());
			assertFalse("CashingType must not be deleted", castedBean.isDeleted());

			// Update the created bean
			Map<String, Object> fields = new HashMap<String, Object>();
			Map<String, Object> keys = new HashMap<String, Object>();
			castedBean.setAmount(BigDecimal.ONE);
			fields.put("amount", castedBean.getAmount());
			keys.put("id", castedBean.getId());
			this.getInstance().updateFieldsByKeys(fields, keys);
			// Reload the modified bean
			CashingType updatedBean = (CashingType) createNewBean();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertEquals("Check updated fields ", castedBean.getAmount(), updatedBean.getAmount());

			// Delete the bean by keys
			// Take the fields as keys
			super.doDeleteByKeysSpecific(updatedBean, fields);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
		}
	}

	private IMdoBean createNewBean(BigDecimal amount, TableCashing tableCashing, MdoTableAsEnum type) {
		
		CashingType newBean = new CashingType();
		newBean.setAmount(amount);
		newBean.setTableCashing(tableCashing);
		newBean.setType(type);
		
		return newBean;
	}
}