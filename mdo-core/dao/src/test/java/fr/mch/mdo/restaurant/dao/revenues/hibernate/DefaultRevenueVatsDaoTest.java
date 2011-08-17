package fr.mch.mdo.restaurant.dao.revenues.hibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.Revenue;
import fr.mch.mdo.restaurant.dao.beans.RevenueVat;
import fr.mch.mdo.restaurant.dao.beans.ValueAddedTax;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServicesTestCase;
import fr.mch.mdo.restaurant.dao.revenues.IRevenueVatsDao;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultRevenueVatsDaoTest extends DefaultDaoServicesTestCase 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultRevenueVatsDaoTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultRevenueVatsDaoTest.class);
	}

	protected IDaoServices getInstance() {
		return DefaultRevenueVatsDao.getInstance();
	}

	protected IMdoBean createNewBean() {
		BigDecimal amount = BigDecimal.TEN;
		Revenue revenue = new Revenue(); 
		revenue.setId(1L);
		BigDecimal value = BigDecimal.ONE;
		ValueAddedTax vat = new ValueAddedTax();
		vat.setId(2L);
		return createNewBean(amount, revenue, value, vat);
	}

	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		BigDecimal amount = BigDecimal.TEN;
		Revenue revenue = new Revenue(); 
		revenue.setId(2L);
		BigDecimal value = BigDecimal.ONE;
		ValueAddedTax vat = new ValueAddedTax();
		vat.setId(1L);
		list.add(createNewBean(amount, revenue, value, vat));
		return list;
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof IRevenueVatsDao);
		assertTrue(this.getInstance() instanceof DefaultRevenueVatsDao);
	}

	@Override
	public void doFindByUniqueKey() {
		Long revenueId = 1L;
		String vat = "DEFAULT";
		try {
			for (int i = 0; i < 2; i++) {
				IMdoBean bean = null;
				if (i == 0) {
					bean = this.getInstance().findByUniqueKey(new Object[] { revenueId, vat });
				} else {
					bean = ((IRevenueVatsDao) this.getInstance()).findByUniqueKey(revenueId, vat);
				}
				assertNotNull("IMdoBean must not be null", bean);
				assertTrue("IMdoBean must be instance of " + RevenueVat.class, bean instanceof RevenueVat);
				RevenueVat castedBean = (RevenueVat) bean;
				assertNotNull("RevenueVat revenue must not be null", castedBean.getRevenue());
				assertEquals("RevenueVat revenue Id must be equals to unique key", revenueId, castedBean.getRevenue().getId());
				assertNotNull("RevenueVat vat must not be null", castedBean.getVat());
				assertEquals("RevenueVat vat Id must be equals to unique key", vat, castedBean.getVat().getCode().getName());
				assertFalse("Revenue must not be deleted", castedBean.isDeleted());
			}
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	@Override
	public void doUpdate() {
		IMdoBean newBean = null;
		BigDecimal amount = BigDecimal.TEN;
		Revenue revenue = new Revenue(); 
		revenue.setId(2L);
		BigDecimal value = BigDecimal.ONE;
		ValueAddedTax vat = new ValueAddedTax();
		vat.setId(2L);
		newBean = createNewBean(amount, revenue, value, vat);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + RevenueVat.class, beanToBeUpdated instanceof RevenueVat);
			RevenueVat castedBean = (RevenueVat) beanToBeUpdated;
			assertNotNull("RevenueVat amount must not be null", castedBean.getAmount());
			assertEquals("RevenueVat amount must be equals to the inserted value", amount, castedBean.getAmount());
			assertFalse("RevenueVat must not be deleted", castedBean.isDeleted());
			// Update the created bean
			amount = BigDecimal.valueOf(5.123456789);
			castedBean.setAmount(amount);
			castedBean.setDeleted(true);
			this.getInstance().update(castedBean);
			// Reload the modified bean
			RevenueVat updatedBean = new RevenueVat();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertEquals("RevenueVat amount must be equals to the updated value", super.decimalFormat.format(amount), super.decimalFormat.format(updatedBean.getAmount()));
			assertTrue("RevenueVat must be deleted", castedBean.isDeleted());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	private IMdoBean createNewBean(BigDecimal amount, Revenue revenue, BigDecimal value, ValueAddedTax vat) {

		RevenueVat newBean = new RevenueVat();
		newBean.setAmount(amount);
		newBean.setRevenue(revenue);
		newBean.setValue(value);
		newBean.setVat(vat);

		return newBean;
	}
}
