package fr.mch.mdo.restaurant.business.managers.authentication.jaas;

import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.dao.DaoServicesFactory;
import fr.mch.mdo.restaurant.dao.authentication.AuthenticationPasswordLevel;
import fr.mch.mdo.restaurant.dao.beans.UserAuthenticationJaas;
import fr.mch.mdo.restaurant.dao.users.ILoginUserAuthenticationsDao;
import fr.mch.mdo.restaurant.exception.MdoAuthenticationException;
import fr.mch.mdo.restaurant.services.authentication.jaas.LevelPasswordMdoCallback;
import fr.mch.mdo.restaurant.services.authentication.jaas.NameMdoCallback;
import fr.mch.mdo.restaurant.services.authentication.jaas.PasswordMdoCallback;
import fr.mch.mdo.restaurant.services.authorization.jaas.TypedPrincipal;
import fr.mch.mdo.restaurant.services.business.managers.authentication.jaas.ILoginDataBaseManager;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

/**
 * @author Mathieu MA
 * 
 *         To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class DefaultLoginDataBaseManager implements ILoginDataBaseManager 
{
	// initial state
	private Subject subject;
	private CallbackHandler callbackHandler;
	@SuppressWarnings("unused")
	private Map<String, ?> sharedState;
	@SuppressWarnings("unused")
	private Map<String, ?> options;

	private UserAuthenticationJaas userAuthentication = null;
	private ILogger logger = null;
	protected ILoginUserAuthenticationsDao dao;

	public DefaultLoginDataBaseManager() {
		// Instantiate by JAAS from new LoginContext
		logger = LoggerServiceImpl.getInstance().getLogger(DefaultLoginDataBaseManager.class.getName());
		dao = DaoServicesFactory.getUserAuthenticationsDao();
	}

	/**
	 * Initialize this <code>LoginModule</code>.
	 * 
	 * <p>
	 * 
	 * @param subject
	 *            the <code>Subject</code> to be authenticated.
	 *            <p>
	 * 
	 * @param callbackHandler
	 *            a <code>CallbackHandler</code> for communicating with the end
	 *            user (prompting for usernames and passwords, for example).
	 *            <p>
	 * 
	 * @param sharedState
	 *            shared <code>LoginModule</code> state.
	 *            <p>
	 * 
	 * @param options
	 *            options specified in the login <code>Configuration</code> for
	 *            this particular <code>LoginModule</code>.
	 */
	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		this.subject = subject;
		this.callbackHandler = callbackHandler;
		this.sharedState = sharedState;
		this.options = options;

		// initialize any configured options
		// boolean debug = "true".equalsIgnoreCase((String)
		// options.get("debug"));
	}

	/**
	 * Authenticate the user by prompting for a username and password.
	 * 
	 * <p>
	 * 
	 * @return true in all cases since this <code>LoginModule</code> should not
	 *         be ignored.
	 * 
	 * @exception FailedLoginException
	 *                if the authentication fails.
	 *                <p>
	 * 
	 * @exception LoginException
	 *                if this <code>LoginModule</code> is unable to perform the
	 *                authentication.
	 */
	public boolean login() throws LoginException {
		Callback callbacks[] = new Callback[3];
		callbacks[0] = new NameMdoCallback();
		callbacks[1] = new PasswordMdoCallback();
		callbacks[2] = new LevelPasswordMdoCallback();

		try {
			callbackHandler.handle(callbacks);
			String login = ((NameCallback) callbacks[0]).getName();
			char password[] = ((PasswordCallback) callbacks[1]).getPassword();
			AuthenticationPasswordLevel levelPassword = ((LevelPasswordMdoCallback) callbacks[2]).getLevelPassword();

			userAuthentication = (UserAuthenticationJaas) dao.findByLogin(login);
			if (userAuthentication == null) {
				// No need to log because this catching exception will do
				throw new MdoAuthenticationException("message.error.authentication.login.no.user");
			}
			// levelPassword and password are never null here
			// because callbackHandler.handle will rise an exception
			String userPassword = levelPassword.getPassword(userAuthentication);
			if (!new String(password).equals(userPassword)) {
				// No need to log because this catching exception will do
				throw new MdoAuthenticationException("message.error.authentication.login.wrong.password");
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			return false;
		}
		return true;
	}

	/**
	 * <p>
	 * This method is called if the LoginContext's overall authentication
	 * succeeded (the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL
	 * LoginModules succeeded).
	 * 
	 * <p>
	 * If this LoginModule's own authentication attempt succeeded (checked by
	 * retrieving the private state saved by the <code>login</code> method),
	 * then this method associates a <code>SamplePrincipal</code> with the
	 * <code>Subject</code> located in the <code>LoginModule</code>. If this
	 * LoginModule's own authentication attempted failed, then this method
	 * removes any state that was originally saved.
	 * 
	 * <p>
	 * 
	 * @exception LoginException
	 *                if the commit fails.
	 * 
	 * @return true if this LoginModule's own login and commit attempts
	 *         succeeded, or false otherwise.
	 */
	public boolean commit() throws LoginException {

		// add a Principal (authenticated identity)
		// to the Subject
		// assume the user we authenticated is the SamplePrincipal
		TypedPrincipal userPrincipal;

		if (userAuthentication != null && userAuthentication.getUserRole() != null && userAuthentication.getUserRole().getCode() != null
				&& userAuthentication.getUserRole().getCode().getName() != null) {
			userPrincipal = new TypedPrincipal(userAuthentication.getUserRole().getCode().getName());
		} else {
			return false;
		}
		if (subject != null && subject.getPrincipals() != null) {
			if (!subject.getPrincipals().contains(userPrincipal)) {
				subject.getPrincipals().add(userPrincipal);
			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * <p>
	 * This method is called if the LoginContext's overall authentication
	 * failed. (the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL
	 * LoginModules did not succeed).
	 * 
	 * <p>
	 * If this LoginModule's own authentication attempt succeeded (checked by
	 * retrieving the private state saved by the <code>login</code> and
	 * <code>commit</code> methods), then this method cleans up any state that
	 * was originally saved.
	 * 
	 * <p>
	 * 
	 * @exception LoginException
	 *                if the abort fails.
	 * 
	 * @return false if this LoginModule's own login and/or commit attempts
	 *         failed, and true otherwise.
	 */
	public boolean abort() throws LoginException {
		return true;
	}

	/**
	 * Logout the user.
	 * 
	 * <p>
	 * This method removes the <code>SamplePrincipal</code> that was added by
	 * the <code>commit</code> method.
	 * 
	 * <p>
	 * 
	 * @exception LoginException
	 *                if the logout fails.
	 * 
	 * @return true in all cases since this <code>LoginModule</code> should not
	 *         be ignored.
	 */
	public boolean logout() throws LoginException {
		return true;
	}
}
