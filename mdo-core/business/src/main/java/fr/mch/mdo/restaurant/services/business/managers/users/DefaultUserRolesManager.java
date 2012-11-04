package fr.mch.mdo.restaurant.services.business.managers.users;

import java.util.ArrayList;
import java.util.List;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IBeanLabelable;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.UserRole;
import fr.mch.mdo.restaurant.dao.users.IUserRolesDao;
import fr.mch.mdo.restaurant.dao.users.hibernate.DefaultUserRolesDao;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.LocaleDto;
import fr.mch.mdo.restaurant.dto.beans.UserRoleDto;
import fr.mch.mdo.restaurant.dto.beans.UserRolesManagerViewBean;
import fr.mch.mdo.restaurant.exception.MdoBusinessException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.AbstractAdministrationManagerLabelable;
import fr.mch.mdo.restaurant.services.business.managers.DefaultMdoTableAsEnumsManager;
import fr.mch.mdo.restaurant.services.business.managers.assembler.DefaultUserRolesAssembler;
import fr.mch.mdo.restaurant.services.business.managers.locales.DefaultLocalesManager;
import fr.mch.mdo.restaurant.services.business.managers.locales.ILocalesManager;
import fr.mch.mdo.restaurant.services.business.managers.products.IMdoTableAsEnumsManager;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultUserRolesManager extends AbstractAdministrationManagerLabelable implements IUserRolesManager 
{
	private IMdoTableAsEnumsManager mdoTableAsEnumsManager;
	private ILocalesManager localesManager;
	
	private static class LazyHolder {
		private static IUserRolesManager instance = new DefaultUserRolesManager(LoggerServiceImpl.getInstance().getLogger(DefaultUserRolesManager.class.getName()),
				DefaultUserRolesDao.getInstance(), DefaultUserRolesAssembler.getInstance());
	}

	private DefaultUserRolesManager(ILogger logger, IUserRolesDao dao, IManagerAssembler assembler) {
		super.logger = logger;
		super.dao = dao;
		super.assembler = assembler;
		this.mdoTableAsEnumsManager = DefaultMdoTableAsEnumsManager.getInstance();
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

	/**
	 * @return the mdoTableAsEnumsManager
	 */
	public IMdoTableAsEnumsManager getMdoTableAsEnumsManager() {
		return mdoTableAsEnumsManager;
	}

	/**
	 * @param mdoTableAsEnumsManager the mdoTableAsEnumsManager to set
	 */
	public void setMdoTableAsEnumsManager(
			IMdoTableAsEnumsManager mdoTableAsEnumsManager) {
		this.mdoTableAsEnumsManager = mdoTableAsEnumsManager;
	}

	@Override
	protected String getDefaultLabel(IBeanLabelable mdoBean) {
		String result = null;
		UserRole mdoBeanCasted = (UserRole) mdoBean;
		if (mdoBeanCasted != null && mdoBeanCasted.getCode() != null) {
			result = mdoBeanCasted.getCode().getLanguageKeyLabel();
		}
		return result;
	}
	
	@Override
	public IMdoDtoBean save(IMdoDtoBean dtoBean) throws MdoBusinessException {
		UserRoleDto result = (UserRoleDto) super.save(dtoBean);
		/*
		 The following code will be moved to caller method:
		if (result != null) {
			if (userContext != null && userContext.getUserRole() != null) {
				Long savedId = result.getId();
				Long sessionId = (Long) userContext.getUserRole().getId();
				if (savedId != null && savedId.equals(sessionId) && userContext.getUserAuthentication() != null) {
					try {
						userContext.getUserAuthentication().setUserRole((UserRoleDto) super.findByPrimaryKey(savedId));
					} catch (MdoBusinessException e) {
						logger.error("message.error.administration.business.user.roles.not.found", new Object[] {dtoBean}, e);
						throw new MdoBusinessException("message.error.administration.business.user.roles.not.found", new Object[] {dtoBean}, e);
					}
				}
			}
		}
		*/
		return result;
	}
	
	@Override
	public void processList(IAdministrationManagerViewBean viewBean, LocaleDto locale, boolean... lazy) throws MdoBusinessException {
		super.processList(viewBean, lazy);
		UserRolesManagerViewBean userRolesManagerViewBean = (UserRolesManagerViewBean) viewBean;
		try {
			userRolesManagerViewBean.setLabels(this.getLabels(locale));
			userRolesManagerViewBean.setLanguages(this.localesManager.getLanguages(locale.getLanguageCode()));
			userRolesManagerViewBean.setCodes(this.getRemainingList(mdoTableAsEnumsManager.getUserRoles()));
		} catch (Exception e) {
			logger.error("message.error.administration.business.find.all", e);
			throw new MdoBusinessException("message.error.administration.business.find.all", e);
		}
	}

	/**
	 * Remove already existing bean from listAll.
	 * @param listAll list of bean to be removed.
	 * @return a restricted list.
	 */
	private List<IMdoDtoBean> getRemainingList(List<IMdoDtoBean> listAll) {
		List<IMdoDtoBean> result = new ArrayList<IMdoDtoBean>(listAll);
		List<IMdoDtoBean> excludedList = new ArrayList<IMdoDtoBean>();
		try {
			excludedList = super.findAll();
		} catch (MdoBusinessException e) {
			logger.warn("message.error.administration.business.find.all", e);
		}
		for (IMdoDtoBean exlcudedBean : excludedList) {
			UserRoleDto exlcudedBeanCasted = (UserRoleDto) exlcudedBean;
			for (IMdoDtoBean tableAsEnum : listAll) {
				if (tableAsEnum.getId() != null && exlcudedBeanCasted.getCode() != null && tableAsEnum.getId().equals(exlcudedBeanCasted.getCode().getId())) {
					result.remove(tableAsEnum);
				}
			}
		}

		return result;
	}

	@Override
	public IMdoDtoBean findByCode(String code) throws MdoException {
		try {
			return assembler.marshal((IMdoDaoBean) dao.findByUniqueKey(code));
		} catch (MdoException e) {
			logger.error("message.error.administration.business.user.role.find.by.code", new Object[] {code},  e);
			throw new MdoBusinessException("message.error.administration.business.user.role.find.by.code", new Object[] {code},  e);
		}
	}
}
