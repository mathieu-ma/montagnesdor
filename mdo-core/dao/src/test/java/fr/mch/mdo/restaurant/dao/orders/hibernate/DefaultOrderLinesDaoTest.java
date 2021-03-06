package fr.mch.mdo.restaurant.dao.orders.hibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.beans.Credit;
import fr.mch.mdo.restaurant.dao.beans.DinnerTable;
import fr.mch.mdo.restaurant.dao.beans.OrderLine;
import fr.mch.mdo.restaurant.dao.beans.Product;
import fr.mch.mdo.restaurant.dao.beans.ProductPart;
import fr.mch.mdo.restaurant.dao.beans.ProductSpecialCode;
import fr.mch.mdo.restaurant.dao.beans.ValueAddedTax;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServicesTestCase;
import fr.mch.mdo.restaurant.dao.orders.IOrderLinesDao;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultOrderLinesDaoTest extends DefaultDaoServicesTestCase
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultOrderLinesDaoTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultOrderLinesDaoTest.class);
	}

	protected IDaoServices getInstance() {
		return DefaultOrderLinesDao.getInstance();
	}

	protected IMdoBean createNewBean() {
		BigDecimal amount = BigDecimal.ONE;
		Credit credit = new Credit();
		credit.setId(1L);
		DinnerTable dinnerTable = new DinnerTable();
		dinnerTable.setId(1L);
		String label = "Create new Order Label"; 
		Product product = new Product();
		product.setId(1L);
		ProductPart productPart = new ProductPart();
		productPart.setId(1L);
		ProductSpecialCode productSpecialCode = new ProductSpecialCode();
		productSpecialCode.setId(1L);
		BigDecimal quantity = BigDecimal.TEN; 
		BigDecimal unitPrice = BigDecimal.ZERO;
		ValueAddedTax vat = new ValueAddedTax();
		vat.setId(1L);
		return createNewBean(amount, credit, dinnerTable, label, product, productPart, productSpecialCode, quantity, unitPrice, vat);
	}

	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		BigDecimal amount = BigDecimal.ONE.multiply(BigDecimal.valueOf(2));
		Credit credit =null;
		DinnerTable dinnerTable = new DinnerTable();
		dinnerTable.setId(1L);
		String label = "Create new Order Label in list"; 
		Product product = new Product();
		product.setId(2L);
		ProductPart productPart = new ProductPart();
		productPart.setId(1L);
		ProductSpecialCode productSpecialCode = new ProductSpecialCode();
		productSpecialCode.setId(1L);
		BigDecimal quantity = BigDecimal.TEN.multiply(BigDecimal.valueOf(2)); 
		BigDecimal unitPrice = BigDecimal.ONE;
		ValueAddedTax vat = new ValueAddedTax();
		vat.setId(1L);
		list.add(createNewBean(amount, credit, dinnerTable, label, product, productPart, productSpecialCode, quantity, unitPrice, vat));
		return list;
	}

	@Override
	public void doFindByUniqueKey() {
		assertTrue("The only unique key is the primaray key", true);	
	}

	@Override
	public void doUpdate() {
		IMdoBean newBean = null;
		BigDecimal amount = BigDecimal.TEN.multiply(BigDecimal.valueOf(2));
		Credit credit =null;
		DinnerTable dinnerTable = new DinnerTable();
		dinnerTable.setId(1L);
		String label = "Create new Order Label in update"; 
		Product product = new Product();
		product.setId(2L);
		ProductPart productPart = new ProductPart();
		productPart.setId(1L);
		ProductSpecialCode productSpecialCode = new ProductSpecialCode();
		productSpecialCode.setId(1L);
		BigDecimal quantity = BigDecimal.TEN.divide(BigDecimal.valueOf(2)); 
		BigDecimal unitPrice = BigDecimal.ONE;
		ValueAddedTax vat = new ValueAddedTax();
		vat.setId(1L);
		newBean = createNewBean(amount, credit, dinnerTable, label, product, productPart, productSpecialCode, quantity, unitPrice, vat);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + OrderLine.class, beanToBeUpdated instanceof OrderLine);
			OrderLine castedBean = (OrderLine) beanToBeUpdated;
			assertNotNull("OrderLine ID must not be null", castedBean.getId());
			assertNotNull("OrderLine label must not be null", castedBean.getLabel());
			assertEquals("OrderLine label must be equals to the inserted value", label, castedBean.getLabel());
			assertFalse("OrderLine must not be deleted", castedBean.isDeleted());
			// Update the created bean
			amount = BigDecimal.valueOf(5.123456789);
			castedBean.setAmount(amount);
			castedBean.setDeleted(true);
			this.getInstance().update(castedBean);
			// Reload the modified bean
			OrderLine updatedBean = new OrderLine();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertEquals("OrderLine amount must be equals to the updated value", super.decimalFormat.format(amount), super.decimalFormat.format(updatedBean.getAmount()));
			assertTrue("OrderLine must be deleted", castedBean.isDeleted());
			this.getInstance().delete(updatedBean);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	@Override
	public void doUpdateFieldsAndDeleteByKeysSpecific() {
		IMdoBean newBean = null;
		BigDecimal amount = BigDecimal.TEN.multiply(BigDecimal.valueOf(2));
		Credit credit =null;
		DinnerTable dinnerTable = new DinnerTable();
		dinnerTable.setId(1L);
		String label = "Create new Order Label in update"; 
		Product product = new Product();
		product.setId(2L);
		ProductPart productPart = new ProductPart();
		productPart.setId(1L);
		ProductSpecialCode productSpecialCode = new ProductSpecialCode();
		productSpecialCode.setId(1L);
		BigDecimal quantity = BigDecimal.TEN.divide(BigDecimal.valueOf(2)); 
		BigDecimal unitPrice = BigDecimal.ONE;
		ValueAddedTax vat = new ValueAddedTax();
		vat.setId(1L);
		newBean = createNewBean(amount, credit, dinnerTable, label, product, productPart, productSpecialCode, quantity, unitPrice, vat);
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(newBean);
			assertTrue("IMdoBean must be instance of " + OrderLine.class, beanToBeUpdated instanceof OrderLine);
			OrderLine castedBean = (OrderLine) beanToBeUpdated;
			assertNotNull("OrderLine ID must not be null", castedBean.getId());
			assertNotNull("OrderLine label must not be null", castedBean.getLabel());
			assertEquals("OrderLine label must be equals to the inserted value", label, castedBean.getLabel());
			assertFalse("OrderLine must not be deleted", castedBean.isDeleted());

			// Update the created bean
			Map<String, Object> fields = new HashMap<String, Object>();
			Map<String, Object> keys = new HashMap<String, Object>();
			castedBean.setAmount(BigDecimal.ONE);
			castedBean.setLabel("label");
			castedBean.setQuantity(BigDecimal.TEN);
			castedBean.setUnitPrice(BigDecimal.ZERO);
			fields.put("amount", castedBean.getAmount());
			fields.put("label", castedBean.getLabel());
			fields.put("quantity", castedBean.getQuantity());
			fields.put("unitPrice", castedBean.getUnitPrice());
			keys.put("id", castedBean.getId());
			this.getInstance().updateFieldsByKeys(fields, keys);
			// Reload the modified bean
			OrderLine updatedBean = (OrderLine) createNewBean();
			updatedBean.setId(castedBean.getId());
			this.getInstance().load(updatedBean);
			assertEquals("Check updated fields ", castedBean.getAmount(), updatedBean.getAmount());
			assertEquals("Check updated fields ", castedBean.getLabel(), updatedBean.getLabel());
			assertEquals("Check updated fields ", castedBean.getQuantity(), updatedBean.getQuantity());
			assertEquals("Check updated fields ", castedBean.getUnitPrice(), updatedBean.getUnitPrice());

			// Delete the bean by keys
			// Take the fields as keys
			super.doDeleteByKeysSpecific(updatedBean, fields);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
		}
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof IOrderLinesDao);
		assertTrue(this.getInstance() instanceof DefaultOrderLinesDao);
	}

	private IMdoBean createNewBean(BigDecimal amount, Credit credit, DinnerTable dinnerTable, String label, Product product, ProductPart productPart,
			ProductSpecialCode productSpecialCode, BigDecimal quantity, BigDecimal unitPrice, ValueAddedTax vat) {
		
		OrderLine newBean = new OrderLine();
		newBean.setAmount(amount);
		newBean.setCredit(credit);
		newBean.setDinnerTable(dinnerTable);
		newBean.setLabel(label);
		newBean.setProduct(product);
		newBean.setProductPart(productPart);
		newBean.setProductSpecialCode(productSpecialCode);
		newBean.setQuantity(quantity);
		newBean.setUnitPrice(unitPrice);
		newBean.setVat(vat);
		
		return newBean;
	}
	
	public void testGetOrderLine() {
		Long id = 1L;
		try {
			IOrderLinesDao orderLinesDao = (IOrderLinesDao) this.getInstance();

			OrderLine orderLine = (OrderLine) orderLinesDao.getOrderLine(id);
			assertNotNull("Product must not be null", orderLine);
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}
	
	public void testGetOrderLinesSize() {
		IOrderLinesDao orderLinesDao = (IOrderLinesDao) this.getInstance();
		try {
			Long dinnerTableId = Long.valueOf(1);
			int size = orderLinesDao.getOrderLinesSize(dinnerTableId);
			assertTrue("Check Dinner Table's Order Lines Size initial value", size>0);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}


	public void testAddUpdateFindRemoveOrderLine() {
		IOrderLinesDao orderLinesDao = (IOrderLinesDao) this.getInstance();
		OrderLine orderLine = new OrderLine();
		DinnerTable dinnerTable = new DinnerTable();
		dinnerTable.setId(Long.valueOf(1));
		orderLine.setDinnerTable(dinnerTable);
		ProductSpecialCode psc = new ProductSpecialCode();
		psc.setId(Long.valueOf(1));
		orderLine.setProductSpecialCode(psc);
		Product product = new Product();
		product.setId(Long.valueOf(1));
		orderLine.setProduct(product);
		orderLine.setQuantity(new BigDecimal(2));
		orderLine.setLabel("Test");
		orderLine.setUnitPrice(new BigDecimal(2.1));
		orderLine.setAmount(orderLine.getQuantity().multiply(orderLine.getUnitPrice()));
		ValueAddedTax vat = new ValueAddedTax();
		vat.setId(1L);
		orderLine.setVat(vat);

		try {
			assertNull("Order Line id must be null", orderLine.getId());
			orderLinesDao.insert(orderLine);
			assertNotNull("Order Line id must not be null", orderLine.getId());
			orderLine.setLabel("Test Update");
			orderLinesDao.update(orderLine);
			OrderLine foundOrderLine = (OrderLine) orderLinesDao.findByPrimaryKey(orderLine.getId());
			assertNotNull("Order Line must not be null", foundOrderLine);
			assertEquals("Order Line label updated", orderLine.getLabel(), foundOrderLine.getLabel());
			orderLinesDao.delete(orderLine.getId());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		try {
			orderLinesDao.findByPrimaryKey(orderLine.getId());
		} catch (MdoException e) {
			assertTrue("Exception because bean not found after deletion", true);
		}
	}
	
	public void testFindAllScalarFieldsByDinnerTableId() {
		IOrderLinesDao orderLinesDao = (IOrderLinesDao) this.getInstance();
		try {
			List<OrderLine> result = orderLinesDao.findAllScalarFieldsByDinnerTableId(1L, 2L);
//			for (IMdoBean iMdoBean : result) {
//				OrderLine castedBean = (OrderLine) iMdoBean;
//System.out.println(castedBean.getId());
//System.out.println(castedBean.getLabel());
//			}
			assertNotNull("List of OrderLines", result);
			assertFalse("List of OrderLines not empty", result.isEmpty());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}
}
