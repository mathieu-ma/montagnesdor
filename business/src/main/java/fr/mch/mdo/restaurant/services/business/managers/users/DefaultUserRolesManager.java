package fr.mch.mdo.restaurant.services.business.managers.users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.UserRole;
import fr.mch.mdo.restaurant.dao.users.IUserRolesDao;
import fr.mch.mdo.restaurant.dao.users.hibernate.DefaultUserRolesDao;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.LocaleDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.UserRoleDto;
import fr.mch.mdo.restaurant.dto.beans.UserRolesManagerViewBean;
import fr.mch.mdo.restaurant.exception.MdoBusinessException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.AbstractAdministrationManager;
import fr.mch.mdo.restaurant.services.business.managers.assembler.DefaultUserRolesAssembler;
import fr.mch.mdo.restaurant.services.business.managers.locales.DefaultLocalesManager;
import fr.mch.mdo.restaurant.services.business.managers.locales.ILocalesManager;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultUserRolesManager extends AbstractAdministrationManager implements IUserRolesManager 
{
	private ILocalesManager localesManager;
	
	private static class LazyHolder {
		private static IUserRolesManager instance = new DefaultUserRolesManager(LoggerServiceImpl.getInstance().getLogger(DefaultUserRolesManager.class.getName()),
				DefaultUserRolesDao.getInstance(), DefaultUserRolesAssembler.getInstance());
	}

	private DefaultUserRolesManager(ILogger logger, IUserRolesDao dao, IManagerAssembler assembler) {
		super.logger = logger;
		super.dao = dao;
		super.assembler = assembler;
		this.localesManager = DefaultLocalesManager.getInstance();
	}

	/**
	 * This constructor is used by ioc
	 */
	public DefaultUserRolesManager() {
	}

	public static IUserRolesManager getInstance() {
		return LazyHolder.instance;
	}

	/**
	 * @param localesManager the localesManager to set
	 */
	public void setLocalesManager(ILocalesManager localesManager) {
		this.localesManager = localesManager;
	}

	/**
	 * @return the localesManager
	 */
	public ILocalesManager getLocalesManager() {
		return localesManager;
	}

	@Override
	public IMdoDtoBean save(IMdoDtoBean dtoBean, MdoUserContext userContext) throws MdoBusinessException {
		UserRoleDto result = (UserRoleDto) super.save(dtoBean, userContext);
		if (result != null) {
			if (userContext != null && userContext.getUserRole() != null) {
				Long savedId = result.getId();
				Long sessionId = (Long) userContext.getUserRole().getId();
				if (savedId != null && savedId.equals(sessionId) && userContext.getUserAuthentication() != null) {
					try {
						userContext.getUserAuthentication().setUserRole((UserRoleDto) super.findByPrimaryKey(savedId, userContext));
					} catch (MdoBusinessException e) {
						logger.error("message.error.administration.business.user.roles.not.found", new Object[] {dtoBean}, e);
						throw new MdoBusinessException("message.error.administration.business.user.roles.not.found", new Object[] {dtoBean}, e);
					}
				}
			}
		}
		return result;
	}
	
	private Map<String, String> getLabels(LocaleDto currentLocale) throws MdoBusinessException {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		try {
			list = dao.findAll();
		} catch (MdoException e) {
			logger.error("message.error.administration.business.find.all", e);
			throw new MdoBusinessException("message.error.administration.business.find.all", e);
		}
		Map<String, String> result = new HashMap<String, String>(list.size());

		for (Iterator<IMdoBean> iterator = list.iterator(); iterator.hasNext();) {
			UserRole mdoBean = (UserRole) iterator.next();
		
			String label = null;
			if (currentLocale != null && mdoBean.getLabels() != null && !mdoBean.getLabels().isEmpty()) {
				label = mdoBean.getLabels().get(currentLocale.getId());
				if (label == null) {
					label = mdoBean.getLabels().values().iterator().next();
				}
			}
			if (label == null) {
				label = mdoBean.getCode().getLanguageKeyLabel();
			}
			result.put(mdoBean.getId().toString(), label);
		}
		return result;
	}

	@Override
	public void processList(IAdministrationManagerViewBean viewBean, MdoUserContext userContext, boolean... lazy) throws MdoBusinessException {
		super.processList(viewBean, userContext, lazy);
		UserRolesManagerViewBean userRolesManagerViewBean = (UserRolesManagerViewBean) viewBean;
		try {
			userRolesManagerViewBean.setLabels(this.getLabels(userContext.getCurrentLocale()));
			userRolesManagerViewBean.setLanguages(this.localesManager.getLanguages(userContext.getCurrentLocale().getLanguageCode()));
		} catch (Exception e) {
			logger.error("message.error.administration.business.find.all", e);
			throw new MdoBusinessException("message.error.administration.business.find.all", e);
		}
	}
}
