package fr.mch.mdo.restaurant.services.business.managers;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.dto.UserAuthenticationDto;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dao.users.hibernate.DefaultUserAuthenticationsDao;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.utils.DefaultUsersHelper;
import fr.mch.mdo.restaurant.services.business.utils.IUsersHelper;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultUsersManager extends AbstractRestaurantManager implements IUsersManager
{
	private IUsersHelper helper;
	
	private static class LazyHolder {
		private static IUsersManager instance = new DefaultUsersManager(
				LoggerServiceImpl.getInstance().getLogger(DefaultUsersManager.class.getName()),
				DefaultUserAuthenticationsDao.getInstance());
	}

	private DefaultUsersManager(ILogger logger, IDaoServices dao) {
		super.logger = logger;
		super.dao = dao;
		this.helper = DefaultUsersHelper.getInstance(); 
	}

	public static IUsersManager getInstance() {
		return LazyHolder.instance;
	}

	/**
	 * This constructor is used by ioc
	 */
	public DefaultUsersManager() {
	}

	/**
	 * @return the helper
	 */
	public IUsersHelper getHelper() {
		return helper;
	}

	/**
	 * @param utils the helper to set
	 */
	public void setHelper(IUsersHelper helper) {
		this.helper = helper;
	}

	@Override
	public UserAuthenticationDto findByLogin(String login) throws MdoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserAuthenticationDto find(Long authId) throws MdoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changePassword(Long authId, String levelPassword,
			String newPassword) throws MdoException {
		// TODO Auto-generated method stub
		
	}
	
}
