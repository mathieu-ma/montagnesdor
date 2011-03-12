package fr.mch.mdo.restaurant.services.authorization.jaas;

import java.security.AccessControlException;
import java.security.Permission;
import java.security.PrivilegedAction;

import javax.security.auth.Subject;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.authorization.IMdoAuthorizationService;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

/**
 * @author Mathieu MA
 * 
 *         To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class MdoAuthorizationServiceImpl implements IMdoAuthorizationService 
{
	private static class LazyHolder {
		private static IMdoAuthorizationService instance = new MdoAuthorizationServiceImpl(
				LoggerServiceImpl.getInstance().getLogger(MdoAuthorizationServiceImpl.class.getName()));
	}

	public static IMdoAuthorizationService getInstance() {
		return LazyHolder.instance;
	}

	private ILogger logger;

	private MdoAuthorizationServiceImpl(ILogger logger) {
		this();
		this.logger = logger;
	}

	public MdoAuthorizationServiceImpl() {
	}

	public Permission getPermission(String url) {
		return new URLPermission(url);
	}

	public String permitted(final Subject subject, final String pageReq) {
		final SecurityManager securityManager;
		if (System.getSecurityManager() == null) {
			securityManager = new SecurityManager();
		} else {
			securityManager = System.getSecurityManager();
		}
		try {
			this.getLogger().debug("message.debug.permitted.action", new String[] { pageReq });

			String result = (String) Subject.doAsPrivileged(subject, new PrivilegedAction<String>() {
				public String run() {
					String path = null;
					try {
						Permission permission = getPermission(pageReq);
						securityManager.checkPermission(permission);
						path = pageReq;
					} catch (Exception e) {
						getLogger().error("message.error.permitted.action", new String[] { pageReq + " " + e.getMessage() });
					}
					return path;
				}
			}, null);

			if (result == null) {
				this.getLogger().error("message.error.permitted.action", new String[] { pageReq });
			}
			return result;
		} catch (AccessControlException e) {
			this.getLogger().error("message.error.permitted.action", new String[] { pageReq + " " + e.getMessage() });
			return null;
		} catch (Exception e) {
			this.getLogger().error("message.error.permitted.action", new String[] { pageReq + " " + e.getMessage() });
			return null;
		}
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
