package fr.mch.mdo.restaurant.services.authentication.jaas;

import java.security.Policy;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.authentication.IMdoAuthenticationService;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.authentication.AuthenticationPasswordLevel;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.exception.MdoAuthenticationException;
import fr.mch.mdo.restaurant.resources.IResources;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

/**
 * @author Mathieu MA
 * 
 *         To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class MdoAuthenticationServiceImpl implements IMdoAuthenticationService 
{
	private static class LazyHolder {
		private static IMdoAuthenticationService instance = new MdoAuthenticationServiceImpl(
				LoggerServiceImpl.getInstance().getLogger(MdoAuthenticationServiceImpl.class.getName()));
	}

	public static IMdoAuthenticationService getInstance() {
		return LazyHolder.instance;
	}

	private ILogger logger;

	private MdoAuthenticationServiceImpl(ILogger logger) {
		this();
		this.logger = logger;
	}

	public MdoAuthenticationServiceImpl() {
		this(IResources.class.getResource(IResources.JAAS_LOGIN_CONFIGURATION_FILE).getPath(), 
				IResources.class.getResource(IResources.JAAS_POLICY_FILE).getPath());
	}

	public MdoAuthenticationServiceImpl(String authLoginConfigPath, String authPolicyPath) {
		// Tells the LoginContext where to find the configuration file
		System.setProperty("java.security.auth.login.config", authLoginConfigPath);
		// Gives the client the JAAS permissions it needs
		System.setProperty("java.security.auth.policy", authPolicyPath);
		// Gives the provider the Java 2 permissions it needs
		// System.setProperty("java.security.policy",
		// MdoAuthenticationServiceImpl.class.getResource("montagnesdorjaas.policy").getPath());

		// login.configuration.provider=com.ibm.security.auth.login.ConfigFile
		// auth.policy.provider=com.ibm.security.auth.PolicyFile

		Policy.getPolicy().refresh();
	}

	/**
	 * @see org.apache.struts.webapp.example.Auth#authenticate()
	 */
	public IMdoBean authenticate(String login, String password) throws MdoAuthenticationException {
		return authenticate(login, password, AuthenticationPasswordLevel.PASSWORD_LEVEL_ZERO);
	}

	/**
	 * @see org.apache.struts.webapp.example.Auth#authenticate()
	 */
	public IMdoBean authenticate(String login, String password, AuthenticationPasswordLevel levelPassword) throws MdoAuthenticationException {

		MdoUserContext userContext = null;
		MdoCallBackHandler mdoCallBackHandler = new MdoCallBackHandler(login, password, levelPassword);
		try {
			LoginContext lc = new LoginContext(Constants.JAAS_LOGIN_MODULE_CLASS_NAME, mdoCallBackHandler);
			lc.login();
			userContext = new MdoUserContext(lc.getSubject());
		} catch (LoginException e) {
			logger.fatal("message.error.authentication.failed", e);
			throw new MdoAuthenticationException("message.error.authentication.failed", e);
		}

		return userContext;
	}

	@Override
	public ILogger getLogger() {
		return logger;
	}

	@Override
	public void setLogger(ILogger logger) {
		this.logger = logger;
	}
}
