package fr.mch.mdo.restaurant.services.authorization.jaas;

import java.security.Policy;

import javax.security.auth.Subject;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.authentication.IMdoAuthenticationService;
import fr.mch.mdo.restaurant.authorization.IMdoAuthorizationService;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.resources.IResources;
import fr.mch.mdo.restaurant.services.authentication.jaas.MdoAuthenticationServiceImpl;
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
public class MdoAuthorizationServiceImplTest extends MdoAuthenticationBasicTestCase {
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public MdoAuthorizationServiceImplTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(MdoAuthorizationServiceImplTest.class);
	}

	public void testGetInstance() {
		IMdoAuthorizationService iMdoAuthorizationService = MdoAuthorizationServiceImpl.getInstance();
		assertTrue(iMdoAuthorizationService instanceof MdoAuthorizationServiceImpl);
	}

	public void testGetPermission() {
		IMdoAuthorizationService iMdoAuthorizationService = MdoAuthorizationServiceImpl.getInstance();
		assertTrue("Check the getPermission method", iMdoAuthorizationService.getPermission("/administration/LocalesManager") instanceof URLPermission);
	}

	public void testPermitted() {

		String authPolicyPath = null;
		try {
			// Don't use URL.getFile or URL.getPath instead convert to URI first
			// Because when using URL and the path contains space then the
			// URL.getFile or URL.getPath will convert space to "%20"
			authPolicyPath = IResources.class.getResource("jaas/montagnesdorjaasTest.policy").toURI().getPath();
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
		}
		// Gives the client the JAAS permissions it needs
		System.setProperty("java.security.auth.policy", authPolicyPath);

		Policy.getPolicy().refresh();

		IMdoAuthorizationService iMdoAuthorizationService = MdoAuthorizationServiceImpl.getInstance();
		Subject subject = new Subject();
		subject.getPrincipals().add(new TypedPrincipal("GLOBAL_ADMINISTRATOR"));
		String pageReqPermission = "/administration/LocalesManager";
		assertEquals("Check permitted method", pageReqPermission, iMdoAuthorizationService.permitted(subject, pageReqPermission));

		subject = new Subject();
		subject.getPrincipals().add(new TypedPrincipal("TATA"));
		// Every body could access to this page
		pageReqPermission = "/administration/Logon";
		assertEquals("Check permitted method", pageReqPermission, iMdoAuthorizationService.permitted(subject, pageReqPermission));

		subject = new Subject();
		subject.getPrincipals().add(new TypedPrincipal("USER"));
		// USER could not access to this page
		pageReqPermission = "/administration/LocalesManager";
		assertNull("Check permitted method", iMdoAuthorizationService.permitted(subject, pageReqPermission));

		subject = new Subject();
		subject.getPrincipals().add(new TypedPrincipal("TITI"));
		// USER could not access to this page
		pageReqPermission = "/administration/UserRolesManager";
		assertNull("Check permitted method", iMdoAuthorizationService.permitted(subject, pageReqPermission));

		subject = new Subject();
		subject.getPrincipals().add(new TypedPrincipal("TOTO"));
		// TOTO could not access to this page
		pageReqPermission = "/administration/RestaurantsManager";
		assertNull("Check permitted method", iMdoAuthorizationService.permitted(subject, pageReqPermission));
		subject.getPrincipals().add(new TypedPrincipal("TITI"));
		// TITI and TOTO could access to this page
		pageReqPermission = "/administration/UserRolesManager";
		assertEquals("Check permitted method", pageReqPermission, iMdoAuthorizationService.permitted(subject, pageReqPermission));

	}

	public void testNominalUseCase() {
		IMdoAuthenticationService iMdoAuthenticationService = MdoAuthenticationServiceImpl.getInstance();
		MdoUserContext userContext = null;
		try {
			userContext = (MdoUserContext) iMdoAuthenticationService.authenticate("mch", "mch");
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + " " + e.getMessage());
		}
		IMdoAuthorizationService iMdoAuthorizationService = MdoAuthorizationServiceImpl.getInstance();
		iMdoAuthorizationService.permitted(userContext.getSubject(), "/administration/LocalesManager");
	}
}
