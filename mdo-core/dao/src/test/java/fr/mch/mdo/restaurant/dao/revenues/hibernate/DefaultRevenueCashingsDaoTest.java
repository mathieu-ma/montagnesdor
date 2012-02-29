package fr.mch.mdo.restaurant.dao.revenues.hibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.dao.beans.Revenue;
import fr.mch.mdo.restaurant.dao.beans.RevenueCashing;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServicesTestCase;
import fr.mch.mdo.restaurant.dao.revenues.IRevenueCashingsDao;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultRevenueCashingsDaoTest extends DefaultDaoServicesTestCase 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultRevenueCashingsDaoTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultRevenueCashingsDaoTest.class);
	}

	protected IDaoServices getInstance() {
		return DefaultRevenueCashingsDao.getInstance();
	}

	protected IMdoBean createNewBean() {
		BigDecimal amount = BigDecimal.TEN;
		Revenue revenue = new Revenue(); 
		revenue.setId(1L);
		MdoTableAsEnum type = new MdoTableAsEnum();
		type.setId(36L);
		return createNewBean(amount, revenue, type);
	}

	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		BigDecimal amount = BigDecimal.TEN;
		Revenue revenue = new Revenue(); 
		revenue.setId(1L);
		MdoTableAsEnum type = new MdoTableAsEnum();
		type.setId(37L);
		list.add(createNewBean(amount, revenue, type));
		return list;
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof IRevenueCashingsDao);
		assertTrue(this.getInstance() instanceof DefaultRevenueCashingsDao);
	}

	@Override
	public void doFindByUniqueKey() {
		Long revenueId = 1L;
		String type = "GENERIC_CASH";
		try {
			for (int i = 0; i < 2; i++) {
				IMdoBean bean = null;
				if (i == 0) {
					bean = this.getInstance().findByUniqueKey(new Object[] { revenueId, type });
				} else {
					bean = ((IRevenueCashingsDao) this.getInstance()).findByUniqueKey(revenueId, type);
				}
				assertNotNull("IMdoBean must not be null", bean);
				assertTrue("IMdoBean must be instance of " + RevenueCashing.class, bean instanceof RevenueCashing);
				RevenueCashing castedBean = (RevenueCashing) bean;
				assertNotNull("RevenueCashing revenue must not be null", castedBean.getRevenue());
				assertEquals("RevenueCashing revenue Id must be equals to unique key", revenueId, castedBean.getRevenue().getId());
				assertNotNull("RevenueCashing Type must not be null", castedBean.getType());
				assertEquals("RevenueCashing Type Id must be equals to unique key", type, castedBean.getType().getName());
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
		revenue.setId(1L);
		MdoTableAsEnum type = new MdoTableAsEnum();
		type.setId(38L);
		newBean = createNewBean(amount, revenue, type);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + RevenueCashing.class, beanToBeUpdated instanceof RevenueCashing);
			RevenueCashing castedBean = (RevenueCashing) beanToBeUpdated;
			assertNotNull("RevenueCashing amount must not be null", castedBean.getAmount());
			assertEquals("RevenueCashing amount must be equals to the inserted value", amount, castedBean.getAmount());
			assertFalse("RevenueCashing must not be deleted", castedBean.isDeleted());
			// Update the created bean
			amount = BigDecimal.valueOf(5.123456789);
			castedBean.setAmount(amount);
			castedBean.setDeleted(true);
			this.getInstance().update(castedBean);
			// Reload the modified bean
			RevenueCashing updatedBean = new RevenueCashing();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertEquals("RevenueCashing amount must be equals to the updated value", super.decimalFormat.format(amount), super.decimalFormat.format(updatedBean.getAmount()));
			assertTrue("RevenueCashing must be deleted", castedBean.isDeleted());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	@Override
	public void doUpdateFieldsByKeysSpecific() {
		IMdoBean newBean = null;
		BigDecimal amount = BigDecimal.TEN;
		Revenue revenue = new Revenue(); 
		revenue.setId(1L);
		MdoTableAsEnum type = new MdoTableAsEnum();
		type.setId(38L);
		newBean = createNewBean(amount, revenue, type);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + RevenueCashing.class, beanToBeUpdated instanceof RevenueCashing);
			RevenueCashing castedBean = (RevenueCashing) beanToBeUpdated;
			assertNotNull("RevenueCashing amount must not be null", castedBean.getAmount());
			assertEquals("RevenueCashing amount must be equals to the inserted value", amount, castedBean.getAmount());
			assertFalse("RevenueCashing must not be deleted", castedBean.isDeleted());

			// Update the created bean
			Map<String, Object> fields = new HashMap<String, Object>();
			Map<String, Object> keys = new HashMap<String, Object>();
			castedBean.setAmount(BigDecimal.ONE);
			fields.put("amount", castedBean.getAmount());
			keys.put("id", castedBean.getId());
			this.getInstance().updateFieldsByKeys(fields, keys);
			// Reload the modified bean
			RevenueCashing updatedBean = (RevenueCashing) createNewBean();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertEquals("Check updated fields ", castedBean.getAmount(), updatedBean.getAmount());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
		}
	}

	private IMdoBean createNewBean(BigDecimal amount, Revenue revenue, MdoTableAsEnum type) {

		RevenueCashing newBean = new RevenueCashing();
		newBean.setAmount(amount);
		newBean.setRevenue(revenue);
		newBean.setType(type);

		return newBean;
	}
}
