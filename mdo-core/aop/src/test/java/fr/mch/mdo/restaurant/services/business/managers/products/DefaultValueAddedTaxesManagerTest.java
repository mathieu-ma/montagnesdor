package fr.mch.mdo.restaurant.services.business.managers.products;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.DaoServicesFactory;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumDto;
import fr.mch.mdo.restaurant.dto.beans.ValueAddedTaxDto;
import fr.mch.mdo.restaurant.dto.beans.ValueAddedTaxesManagerViewBean;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.DefaultAdministrationManagerTest;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;
import fr.mch.mdo.restaurant.services.business.managers.assembler.DefaultMdoTableAsEnumsAssembler;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultValueAddedTaxesManagerTest extends DefaultAdministrationManagerTest {
	private BigDecimal basicTestRate = new BigDecimal(20);

	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultValueAddedTaxesManagerTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultValueAddedTaxesManagerTest.class);
	}

	@Override
	protected IAdministrationManager getInstance() {
		return DefaultValueAddedTaxesManager.getInstance();
	}

	@Override
	protected IMdoDtoBean createNewBean() {
		MdoTableAsEnumDto code = new MdoTableAsEnumDto();
		// Use the existing data in database
		code.setId(1L);
		BigDecimal rate = basicTestRate.add(new BigDecimal(0.1f));
		return createNewBean(code, rate);
	}

	@Override
	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		MdoTableAsEnumDto code = new MdoTableAsEnumDto();
		// Use the existing data in database
		try {
			// Do not use code.setId because the find list test uses compareTo
			// of ValueAddedTax
			code = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsAssembler.getInstance().marshal((IMdoDaoBean) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(2L),
					userContext);
		} catch (MdoException e) {
			fail("Could not found the vat code.");
		}
		BigDecimal rate = basicTestRate.add(new BigDecimal(0.2f));
		list.add(createNewBean(code, rate));
		code = new MdoTableAsEnumDto();
		// Use the existing data in database
		try {
			// Do not use code.setId because the find list test uses compareTo
			// of ValueAddedTax
			code = (MdoTableAsEnumDto) DefaultMdoTableAsEnumsAssembler.getInstance().marshal((IMdoDaoBean) DaoServicesFactory.getMdoTableAsEnumsDao().findByPrimaryKey(3L),
					userContext);
		} catch (MdoException e) {
			fail("Could not found the vat code.");
		}
		rate = basicTestRate.add(new BigDecimal(0.3f));
		list.add(createNewBean(code, rate));
		return list;
	}

	@Override
	public void doUpdate() {
		MdoTableAsEnumDto codeToBeUpdated = new MdoTableAsEnumDto();
		// Use the existing data in database
		codeToBeUpdated.setId(4L);
		BigDecimal rateToBeUpdated = basicTestRate.add(new BigDecimal(0.4f));
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(createNewBean(codeToBeUpdated, rateToBeUpdated), userContext);
			assertTrue("IMdoBean must be instance of " + ValueAddedTaxDto.class, beanToBeUpdated instanceof ValueAddedTaxDto);
			ValueAddedTaxDto castedBean = (ValueAddedTaxDto) beanToBeUpdated;
			assertEquals("ValueAddedTaxDto code must be equals to unique key", codeToBeUpdated, castedBean.getCode());
			assertEquals("ValueAddedTaxDto rate must be equals", rateToBeUpdated, castedBean.getRate());
			// Update the created bean
			codeToBeUpdated = new MdoTableAsEnumDto();
			// Use the existing data in database
			codeToBeUpdated.setId(5L);
			castedBean.setRate(basicTestRate.add(new BigDecimal(0.5f)));
			this.getInstance().update(castedBean, userContext);
			// Reload the modified bean
			ValueAddedTaxDto updatedBean = new ValueAddedTaxDto();
			updatedBean.setId(castedBean.getId());
			updatedBean = (ValueAddedTaxDto) this.getInstance().load(updatedBean, userContext);
			assertEquals("ValueAddedTaxDto code must be equals to unique key", castedBean.getCode().getId(), updatedBean.getCode().getId());
			assertEquals("ValueAddedTaxDto rate must be equals", super.decimalFormat.format(castedBean.getRate()), super.decimalFormat.format(updatedBean.getRate()));
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	@Override
	public void doProcessList() {
		ValueAddedTaxesManagerViewBean viewBean = new ValueAddedTaxesManagerViewBean();
		try {
			this.getInstance().processList(viewBean, DefaultAdministrationManagerTest.userContext);
			assertNotNull("Main list not be null", viewBean.getList());
			assertFalse("Main list not be empty", viewBean.getList().isEmpty());
			assertNotNull("Code list not be null", viewBean.getCodes());
			assertTrue("Codes list be empty because all available table types are already set into database", viewBean.getCodes().isEmpty());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof IValueAddedTaxesManager);
		assertTrue(this.getInstance() instanceof DefaultValueAddedTaxesManager);
	}

	private IMdoDtoBean createNewBean(MdoTableAsEnumDto code, BigDecimal rate) {
		ValueAddedTaxDto newBean = new ValueAddedTaxDto();
		newBean.setCode(code);
		newBean.setRate(rate);
		return newBean;
	}
}
