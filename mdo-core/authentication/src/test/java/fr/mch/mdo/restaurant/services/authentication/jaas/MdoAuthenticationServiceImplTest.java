package fr.mch.mdo.restaurant.services.authentication.jaas;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.authentication.IMdoAuthenticationService;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.authentication.AuthenticationPasswordLevel;
import fr.mch.mdo.restaurant.exception.MdoAuthenticationException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.jaas.MdoAuthenticationBasicTestCase;
import fr.mch.mdo.test.MdoTestCase;

/**
 * @author Mathieu MA
 * 
 *         To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class MdoAuthenticationServiceImplTest extends MdoAuthenticationBasicTestCase 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public MdoAuthenticationServiceImplTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(MdoAuthenticationServiceImplTest.class);
	}

	public void testGetInstance() {
		IMdoAuthenticationService iMdoAuthenticationService = MdoAuthenticationServiceImpl.getInstance();
		assertTrue(iMdoAuthenticationService instanceof MdoAuthenticationServiceImpl);
	}

	public void testAuthenticate() {
		IMdoAuthenticationService iMdoAuthenticationService = MdoAuthenticationServiceImpl.getInstance();
		String login = "mch";
		String password = "mch";
		IMdoBean userContext = null;

		for (int i = 0; i < 2; i++) {
			if (i == 0) {
				// Bad login
				login = "mst";
				password = "mch";
				try {
					userContext = iMdoAuthenticationService.authenticate(login, password);
					fail(MdoTestCase.DEFAULT_FAILED_MESSAGE);
				} catch (MdoException e) {
					assertTrue("Check iMdoAuthenticationService.authenticate exception", e instanceof MdoAuthenticationException);
				}
				try {
					userContext = iMdoAuthenticationService.authenticate(login, password, AuthenticationPasswordLevel.PASSWORD_LEVEL_ONE);
					fail(MdoTestCase.DEFAULT_FAILED_MESSAGE);
				} catch (MdoException e) {
					assertTrue("Check iMdoAuthenticationService.authenticate exception", e instanceof MdoAuthenticationException);
				}

				// Bad password
				login = "mch";
				password = "mst";
				try {
					userContext = iMdoAuthenticationService.authenticate(login, password);
					fail(MdoTestCase.DEFAULT_FAILED_MESSAGE);
				} catch (MdoException e) {
					assertTrue("Check iMdoAuthenticationService.authenticate exception", e instanceof MdoAuthenticationException);
				}
				try {
					userContext = iMdoAuthenticationService.authenticate(login, password, AuthenticationPasswordLevel.PASSWORD_LEVEL_TWO);
					fail(MdoTestCase.DEFAULT_FAILED_MESSAGE);
				} catch (MdoException e) {
					assertTrue("Check iMdoAuthenticationService.authenticate exception", e instanceof MdoAuthenticationException);
				}

				// It's OK
				login = "mch";
				password = "mch";
				try {
					userContext = iMdoAuthenticationService.authenticate(login, password);
					assertNotNull("Check iMdoAuthenticationService.authenticate OK", userContext);
				} catch (MdoException e) {
					fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
				}
				password = "mch3";
				try {
					userContext = iMdoAuthenticationService.authenticate(login, password, AuthenticationPasswordLevel.PASSWORD_LEVEL_THREE);
					assertNotNull("Check iMdoAuthenticationService.authenticate OK", userContext);
				} catch (MdoException e) {
					fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
				}
			}
		}
	}
}
