package fr.mch.mdo.restaurant.dao.products.hibernate;

import java.math.BigDecimal;
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
import fr.mch.mdo.restaurant.dao.beans.ValueAddedTax;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServicesTestCase;
import fr.mch.mdo.restaurant.dao.products.IValueAddedTaxesDao;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultValueAddedTaxesDaoTest extends DefaultDaoServicesTestCase 
{
	private BigDecimal basicTestRate = new BigDecimal(20);

	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultValueAddedTaxesDaoTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultValueAddedTaxesDaoTest.class);
	}

	@Override
	protected IDaoServices getInstance() {
		return DefaultValueAddedTaxesDao.getInstance();
	}

	protected IMdoBean createNewBean() {
		MdoTableAsEnum code = new MdoTableAsEnum();
		// Use the existing data in database
		code.setId(1L);
		BigDecimal rate = basicTestRate.add(new BigDecimal(0.1f));
		return createNewBean(code, rate);
	}

	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		MdoTableAsEnum code = new MdoTableAsEnum();
		// Use the existing data in database
		try {
			// Do not use code.setId because the find list test uses compareTo
			// of ValueAddedTax
			code = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(2L);
		} catch (MdoException e) {
			fail("Could not found the vat code.");
		}
		BigDecimal rate = basicTestRate.add(new BigDecimal(0.2f));
		list.add(createNewBean(code, rate));
		code = new MdoTableAsEnum();
		// Use the existing data in database
		try {
			// Do not use code.setId because the find list test uses compareTo
			// of ValueAddedTax
			code = (MdoTableAsEnum) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(3L);
		} catch (MdoException e) {
			fail("Could not found the vat code.");
		}
		rate = basicTestRate.add(new BigDecimal(0.3f));
		list.add(createNewBean(code, rate));
		return list;
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof IValueAddedTaxesDao);
		assertTrue(this.getInstance() instanceof DefaultValueAddedTaxesDao);
	}

	@Override
	public void doFindByUniqueKey() {
		// ALCOHOL code was created at HSQLDB startup
		String codeName = "ALCOHOL";
		try {
			IMdoBean bean = this.getInstance().findByUniqueKey(codeName);
			assertTrue("IMdoBean must be instance of " + ValueAddedTax.class, bean instanceof ValueAddedTax);
			ValueAddedTax castedBean = (ValueAddedTax) bean;
			assertNotNull("ValueAddedTax code must not be null", castedBean.getCode());
			assertEquals("ValueAddedTax code must be equals to unique key", codeName, castedBean.getCode().getName());
			assertFalse("ValueAddedTax must not be deleted", castedBean.isDeleted());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Override
	public void doUpdate() {
		MdoTableAsEnum codeToBeUpdated = new MdoTableAsEnum();
		// Use the existing data in database
		codeToBeUpdated.setId(4L);
		BigDecimal rateToBeUpdated = basicTestRate.add(new BigDecimal(0.4f));
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(createNewBean(codeToBeUpdated, rateToBeUpdated));
			assertTrue("IMdoBean must be instance of " + ValueAddedTax.class, beanToBeUpdated instanceof ValueAddedTax);
			ValueAddedTax castedBean = (ValueAddedTax) beanToBeUpdated;
			assertEquals("ValueAddedTax code must be equals to unique key", codeToBeUpdated, castedBean.getCode());
			assertEquals("ValueAddedTax rate must be equals", rateToBeUpdated, castedBean.getRate());
			assertFalse("ValueAddedTax must not be deleted", castedBean.isDeleted());
			// Update the created bean
			codeToBeUpdated = new MdoTableAsEnum();
			// Use the existing data in database
			codeToBeUpdated.setId(5L);
			castedBean.setRate(basicTestRate.add(new BigDecimal(0.5f)));
			castedBean.setDeleted(true);
			this.getInstance().update(castedBean);
			// Reload the modified bean
			ValueAddedTax updatedBean = new ValueAddedTax();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertEquals("ValueAddedTax code must be equals to unique key", castedBean.getCode().getId(), updatedBean.getCode().getId());
			assertEquals("ValueAddedTax rate must be equals", super.decimalFormat.format(castedBean.getRate()), super.decimalFormat.format(updatedBean.getRate()));
			assertTrue("ValueAddedTax must be deleted", updatedBean.isDeleted());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Override
	public void doUpdateFieldsByKeysSpecific() {
		MdoTableAsEnum codeToBeUpdated = new MdoTableAsEnum();
		// Use the existing data in database
		codeToBeUpdated.setId(5L);
		BigDecimal rateToBeUpdated = basicTestRate.add(new BigDecimal(0.4f));
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(createNewBean(codeToBeUpdated, rateToBeUpdated));
			assertTrue("IMdoBean must be instance of " + ValueAddedTax.class, beanToBeUpdated instanceof ValueAddedTax);
			ValueAddedTax castedBean = (ValueAddedTax) beanToBeUpdated;
			assertEquals("ValueAddedTax code must be equals to unique key", codeToBeUpdated, castedBean.getCode());
			assertEquals("ValueAddedTax rate must be equals", rateToBeUpdated, castedBean.getRate());
			assertFalse("ValueAddedTax must not be deleted", castedBean.isDeleted());

			// Update the created bean
			Map<String, Object> fields = new HashMap<String, Object>();
			Map<String, Object> keys = new HashMap<String, Object>();
			castedBean.setRate(BigDecimal.ONE);
			fields.put("rate", castedBean.getRate());
			keys.put("id", castedBean.getId());
			this.getInstance().updateFieldsByKeys(fields, keys);
			// Reload the modified bean
			ValueAddedTax updatedBean = (ValueAddedTax) createNewBean();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertEquals("Check updated fields ", castedBean.getRate(), updatedBean.getRate());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
		}
	}

	private IMdoBean createNewBean(MdoTableAsEnum code, BigDecimal rate) {
		ValueAddedTax newBean = new ValueAddedTax();
		newBean.setCode(code);
		newBean.setRate(rate);
		return newBean;
	}
}
