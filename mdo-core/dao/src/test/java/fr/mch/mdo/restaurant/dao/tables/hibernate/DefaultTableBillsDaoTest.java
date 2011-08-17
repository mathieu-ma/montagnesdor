package fr.mch.mdo.restaurant.dao.tables.hibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.DinnerTable;
import fr.mch.mdo.restaurant.dao.beans.TableBill;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServicesTestCase;
import fr.mch.mdo.restaurant.dao.tables.ITableBillsDao;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultTableBillsDaoTest extends DefaultDaoServicesTestCase 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultTableBillsDaoTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultTableBillsDaoTest.class);
	}

	protected IDaoServices getInstance() {
		return DefaultTableBillsDao.getInstance();
	}

	protected IMdoBean createNewBean() {
		BigDecimal amount = BigDecimal.TEN;
		DinnerTable dinnerTable = new DinnerTable();
		dinnerTable.setId(1L);
		Integer mealNumber = Integer.MAX_VALUE;
		Integer order = Integer.MIN_VALUE;
		Boolean printed = Boolean.FALSE;
		String reference = "reference";
		return createNewBean(amount, dinnerTable, mealNumber, order, printed, reference);
	}

	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		BigDecimal amount = BigDecimal.ONE;
		DinnerTable dinnerTable = new DinnerTable();
		dinnerTable.setId(1L);
		Integer mealNumber = Integer.MAX_VALUE;
		Integer order = Integer.MAX_VALUE-1;
		Boolean printed = Boolean.TRUE;
		String reference = "reference";
		list.add(createNewBean(amount, dinnerTable, mealNumber, order, printed, reference));
		return list;
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof ITableBillsDao);
		assertTrue(this.getInstance() instanceof DefaultTableBillsDao);
	}

	@Override
	public void doFindByUniqueKey() {
		try {
			Long dinnerTableId = 1L;
			String reference = "reference1";
			Integer order = 1;
			IMdoBean bean = this.getInstance().findByUniqueKey(new Object[] {dinnerTableId, reference, order});
			assertTrue("IMdoBean must be instance of " + TableBill.class, bean instanceof TableBill);
			TableBill castedBean = (TableBill) bean;
			assertNotNull("TableBill dinner table must not be null", castedBean.getDinnerTable());
			assertEquals("Check TableBill dinner table ID", dinnerTableId, castedBean.getDinnerTable().getId());
			assertEquals("Check TableBill reference", reference, castedBean.getReference());
			assertEquals("Check TableBill order", order, castedBean.getOrder());
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	@Override
	public void doUpdate() {
		IMdoBean newBean = null;
		BigDecimal amount = BigDecimal.ONE;
		DinnerTable dinnerTable = new DinnerTable();
		dinnerTable.setId(1L);
		Integer mealNumber = Integer.MAX_VALUE;
		Integer order = Integer.MIN_VALUE;
		Boolean printed = Boolean.TRUE;
		String reference = "reference10";
		newBean = createNewBean(amount, dinnerTable, mealNumber, order, printed, reference);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + TableBill.class, beanToBeUpdated instanceof TableBill);
			TableBill castedBean = (TableBill) beanToBeUpdated;
			assertNotNull("TableBill has a not be null ID", castedBean.getId());
			assertEquals("Check TableBill order", order, castedBean.getOrder());
			assertFalse("TableBill must not be deleted", castedBean.isDeleted());
			// Update the created bean
			castedBean.setDeleted(true);
			order = Integer.MIN_VALUE+1;
			castedBean.setOrder(order);
			this.getInstance().update(castedBean);
			// Reload the modified bean
			TableBill updatedBean = new TableBill();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertEquals("Check TableBill order", order, updatedBean.getOrder());
			assertTrue("TableBill must be deleted", updatedBean.isDeleted());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	private IMdoBean createNewBean(BigDecimal amount, DinnerTable dinnerTable, Integer mealNumber, Integer order, Boolean printed, String reference) {
		
		TableBill newBean = new TableBill();
		newBean.setAmount(amount);
		newBean.setDinnerTable(dinnerTable);
		newBean.setMealNumber(mealNumber);
		newBean.setOrder(order);
		newBean.setPrinted(printed);
		newBean.setReference(reference);
		
		return newBean;
	}
}
