package fr.mch.mdo.restaurant.services.business.managers;

import org.junit.Test;

import fr.mch.mdo.restaurant.beans.dto.UserAuthenticationDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultUsersManagerTest extends MdoBusinessBasicTestCase 
{
	private IFrontUsersManager manager;
	
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultUsersManagerTest(String testName) {
		super(testName);
		manager = DefaultFrontUsersManager.getInstance();
	}

	@Test
	public void testFindAllTables() {
		MdoUserContext userContext = MdoBusinessBasicTestCase.getUserContext();
		try {
			UserAuthenticationDto user = manager.find(userContext.getUserAuthentication().getId());
			assertNotNull("UserAuthenticationDto", user);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}
}
