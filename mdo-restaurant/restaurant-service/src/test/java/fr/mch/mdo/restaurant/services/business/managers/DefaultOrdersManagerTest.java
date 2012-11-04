package fr.mch.mdo.restaurant.services.business.managers;

import java.util.List;
import java.util.Locale;

import org.junit.Test;

import fr.mch.mdo.restaurant.beans.dto.DinnerTableDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultOrdersManagerTest extends MdoBusinessBasicTestCase 
{
	private IOrdersManager manager;
	
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultOrdersManagerTest(String testName) {
		super(testName);
		manager = DefaultOrdersManager.getInstance();
	}

	@Test
	public void testFindAllTables() {
		MdoUserContext userContext = MdoBusinessBasicTestCase.getUserContext();
		try {
			List<DinnerTableDto> tables = manager.findAllTables(userContext.getUserAuthentication().getRestaurant().getId(), TableState.ALTERABLE);
			assertNotNull("Alterable tables", tables);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	@Test
	public void testFindAllAlterableTables() {
		MdoUserContext userContext = MdoBusinessBasicTestCase.getUserContext();
		try {
			List<DinnerTableDto> tables = manager.findAllAlterableTables(userContext.getUserAuthentication().getRestaurant().getId());
			assertNotNull("Alterable tables", tables);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	@Test
	public void testFindTableHeader() {
		MdoUserContext userContext = MdoBusinessBasicTestCase.getUserContext();
		String number = "12";
		try {
			DinnerTableDto table = manager.findTableHeader(userContext.getUserAuthentication().getRestaurant().getId(), number);
			assertNotNull("Table Header", table);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	@Test
	public void testFindTable() {
		try {
			DinnerTableDto table = manager.findTable(1L, Locale.FRANCE);
			assertNotNull("Table", table);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	@Test
	public void testGetTableOrdersSize() {
		try {
			Integer size = manager.getTableOrdersSize(1L);
			assertNotNull("Table Orders size", size);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}
}
