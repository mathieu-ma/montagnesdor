package fr.mch.mdo.restaurant.dao.tables.hibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.DaoServicesFactory;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.IMdoTableAsEnumsDao.TableCashingTypeName;
import fr.mch.mdo.restaurant.dao.MdoTableAsEnumTypeDao;
import fr.mch.mdo.restaurant.dao.beans.CashingType;
import fr.mch.mdo.restaurant.dao.beans.DinnerTable;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.dao.beans.TableCashing;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServicesTestCase;
import fr.mch.mdo.restaurant.dao.tables.ITableCashingsDao;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultTableCashingsDaoTest extends DefaultDaoServicesTestCase 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultTableCashingsDaoTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultTableCashingsDaoTest.class);
	}

	protected IDaoServices getInstance() {
		return DefaultTableCashingsDao.getInstance();
	}

	protected IMdoBean createNewBean() {
		DinnerTable dinnerTable = new DinnerTable();
		// The others existing table id is already cashed. 
		dinnerTable.setId(6L);
		Date cashingDate = new Date();
		Set<CashingType> cashingTypes = new HashSet<CashingType>();
		TableCashing cashing = (TableCashing) createNewBean(dinnerTable, cashingDate, cashingTypes);
		CashingType cashingType = new CashingType();
		MdoTableAsEnum type = null;
		try {
			type = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByUniqueKey(
					new Object[] { MdoTableAsEnumTypeDao.CASHING_TYPE.name(), TableCashingTypeName.GENERIC_CARD.name() });
		} catch (MdoException e) {
			fail("Could not found the cashing type.");
		}
		cashingType.setType(type);
		cashingType.setAmount(BigDecimal.TEN);
		cashing.addCashingType(cashingType);

		return cashing;
	}

	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		DinnerTable dinnerTable = new DinnerTable();
		// The others existing table id is already cashed. 
		dinnerTable.setId(7L);
		Date cashingDate = new Date();
		Set<CashingType> cashingTypes = new HashSet<CashingType>();
		list.add(createNewBean(dinnerTable, cashingDate, cashingTypes));
		return list;
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof ITableCashingsDao);
		assertTrue(this.getInstance() instanceof DefaultTableCashingsDao);
	}

	@Override
	public void doFindByUniqueKey() {
		try {
			Long dinnerTableId = 1L;
			IMdoBean bean = this.getInstance().findByUniqueKey(new Object[] {dinnerTableId});
			assertNull("IMdoBean must be null " + TableCashing.class, bean);
			
			dinnerTableId = 2L;
			bean = this.getInstance().findByUniqueKey(new Object[] {dinnerTableId});
			assertTrue("IMdoBean must be instance of " + TableCashing.class, bean instanceof TableCashing);
			TableCashing castedBean = (TableCashing) bean;
			assertNotNull("TableCashing dinner table must not be null", castedBean.getDinnerTable());
			assertEquals("Check TableCashing dinner table ID", dinnerTableId, castedBean.getDinnerTable().getId());
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}
	
	@Override
	public void doUpdate() {
		TableCashing newBean = null;
		DinnerTable dinnerTable = new DinnerTable();
		// The others existing table id is already cashed. 
		dinnerTable.setId(8L);
		Date cashingDate = new Date();
		Set<CashingType> cashingTypes = new HashSet<CashingType>();
		newBean = (TableCashing) createNewBean(dinnerTable, cashingDate, cashingTypes);
		CashingType cashingType = new CashingType();
		MdoTableAsEnum type = new MdoTableAsEnum();
		type.setId(38L);
		cashingType.setType(type);
		cashingType.setAmount(BigDecimal.TEN);
		newBean.addCashingType(cashingType);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + TableCashing.class, beanToBeUpdated instanceof TableCashing);
			TableCashing castedBean = (TableCashing) beanToBeUpdated;
			assertNotNull("TableCashing has a not be null ID", castedBean.getId());
			assertNotNull("Check TableCashing types", castedBean.getCashingTypes());
			assertFalse("Check TableCashing types is not empty", castedBean.getCashingTypes().isEmpty());
			assertFalse("TableCashing must not be deleted", castedBean.isDeleted());
			// Update the created bean
			castedBean.setDeleted(true);
			this.getInstance().update(castedBean);
			// Reload the modified bean
			TableCashing updatedBean = new TableCashing();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertTrue("TableCashing must be deleted", updatedBean.isDeleted());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	@Override
	public void doUpdateFieldsByKeysSpecific() {
		TableCashing newBean = null;
		DinnerTable dinnerTable = new DinnerTable();
		// The others existing table id is already cashed. 
		dinnerTable.setId(8L);
		Date cashingDate = new Date();
		Set<CashingType> cashingTypes = new HashSet<CashingType>();
		newBean = (TableCashing) createNewBean(dinnerTable, cashingDate, cashingTypes);
		CashingType cashingType = new CashingType();
		MdoTableAsEnum type = new MdoTableAsEnum();
		type.setId(38L);
		cashingType.setType(type);
		cashingType.setAmount(BigDecimal.TEN);
		newBean.addCashingType(cashingType);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + TableCashing.class, beanToBeUpdated instanceof TableCashing);
			TableCashing castedBean = (TableCashing) beanToBeUpdated;
			assertNotNull("TableCashing has a not be null ID", castedBean.getId());
			assertNotNull("Check TableCashing types", castedBean.getCashingTypes());
			assertFalse("Check TableCashing types is not empty", castedBean.getCashingTypes().isEmpty());
			assertFalse("TableCashing must not be deleted", castedBean.isDeleted());

			// Update the created bean
			Map<String, Object> fields = new HashMap<String, Object>();
			Map<String, Object> keys = new HashMap<String, Object>();
			castedBean.setCashingDate(new Date());
			castedBean.setDeleted(false);
			fields.put("cashingDate", castedBean.getCashingDate());
			fields.put("deleted", castedBean.isDeleted());
			keys.put("id", castedBean.getId());
			this.getInstance().updateFieldsByKeys(fields, keys);
			// Reload the modified bean
			TableCashing updatedBean = (TableCashing) createNewBean();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
//			assertEquals("Check updated fields ", castedBean.getCashingDate(), updatedBean.getCashingDate());
			assertEquals("Check updated fields ", castedBean.isDeleted(), updatedBean.isDeleted());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
		}
	}

	private IMdoBean createNewBean(DinnerTable dinnerTable, Date cashingDate, Set<CashingType> cashingTypes) {
		
		TableCashing newBean = new TableCashing();
		newBean.setCashingDate(cashingDate);
		newBean.setDinnerTable(dinnerTable);
		newBean.setCashingTypes(cashingTypes);
		
		return newBean;
	}
}