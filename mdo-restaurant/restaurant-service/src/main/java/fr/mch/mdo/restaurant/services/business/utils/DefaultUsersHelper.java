package fr.mch.mdo.restaurant.services.business.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.dto.MdoUserTitle;
import fr.mch.mdo.restaurant.beans.dto.UserAuthenticationDto;
import fr.mch.mdo.restaurant.beans.dto.UserDto;
import fr.mch.mdo.restaurant.dao.beans.UserAuthentication;
import fr.mch.mdo.restaurant.dao.beans.UserLocale;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultUsersHelper implements IUsersHelper 
{
	private ILogger logger;

	private static class LazyHolder {
		private static IUsersHelper instance = new DefaultUsersHelper(
				LoggerServiceImpl.getInstance().getLogger(DefaultUsersHelper.class.getName()));
	}

	private DefaultUsersHelper(ILogger logger) {
		this.setLogger(logger);
	}

	public static IUsersHelper getInstance() {
		return LazyHolder.instance;
	}

	/**
	 * This constructor is used by ioc
	 */
	public DefaultUsersHelper() {
	}

	/**
	 * @return the logger
	 */
	public ILogger getLogger() {
		return logger;
	}

	/**
	 * @param logger the logger to set
	 */
	public void setLogger(ILogger logger) {
		this.logger = logger;
	}

	@Override
	public UserAuthenticationDto fromUserAuthentication(UserAuthentication user) {
		UserAuthenticationDto result = new UserAuthenticationDto();
		result.setId(user.getId());
		result.setLogin(user.getLogin());
		Set<UserLocale> userlocales = user.getLocales();
		if (userlocales != null) {
			Map<String, Long> locales = new HashMap<String, Long>();
			for (UserLocale userLocale : userlocales) {
				String language = userLocale.getLocale().getLanguage();
				Long id = userLocale.getLocale().getId();
				locales.put(language, id);
			}
			result.setLocales(locales);
		}
		UserDto _user = new UserDto();
		_user.setName(user.getUser().getName());
		_user.setForename1(user.getUser().getForename1());
		_user.setForename1(user.getUser().getForename2());
		_user.setTitle(MdoUserTitle.valueOf(user.getUser().getTitle().getName()));
		result.setUser(_user);
		return result;
	}

	@Override
	public UserAuthentication toUserAuthentication(UserAuthenticationDto user) {
		return null;
	}
}
