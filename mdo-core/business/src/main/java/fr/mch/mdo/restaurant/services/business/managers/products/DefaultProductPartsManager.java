package fr.mch.mdo.restaurant.services.business.managers.products;

import java.util.ArrayList;
import java.util.List;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IBeanLabelable;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.ProductPart;
import fr.mch.mdo.restaurant.dao.products.IProductPartsDao;
import fr.mch.mdo.restaurant.dao.products.hibernate.DefaultProductPartsDao;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.ProductPartDto;
import fr.mch.mdo.restaurant.dto.beans.ProductPartsManagerViewBean;
import fr.mch.mdo.restaurant.exception.MdoBusinessException;
import fr.mch.mdo.restaurant.services.business.managers.AbstractAdministrationManagerLabelable;
import fr.mch.mdo.restaurant.services.business.managers.DefaultMdoTableAsEnumsManager;
import fr.mch.mdo.restaurant.services.business.managers.assembler.DefaultProductPartsAssembler;
import fr.mch.mdo.restaurant.services.business.managers.locales.DefaultLocalesManager;
import fr.mch.mdo.restaurant.services.business.managers.locales.ILocalesManager;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultProductPartsManager extends AbstractAdministrationManagerLabelable implements IProductPartsManager
{
	private IMdoTableAsEnumsManager mdoTableAsEnumsManager;
	private ILocalesManager localesManager;

	private static class LazyHolder {
		private static IProductPartsManager instance = new DefaultProductPartsManager(
				LoggerServiceImpl.getInstance().getLogger(DefaultProductPartsManager.class.getName()),
				DefaultProductPartsDao.getInstance(), DefaultProductPartsAssembler.getInstance());
	}

	private DefaultProductPartsManager(ILogger logger, IProductPartsDao dao, IManagerAssembler assembler) {
		super.logger = logger;
		super.dao = dao;
		super.assembler = assembler;
		this.mdoTableAsEnumsManager = DefaultMdoTableAsEnumsManager.getInstance();
		this.localesManager = DefaultLocalesManager.getInstance();
	}

	/**
	 * This constructor is used by ioc
	 */
	public DefaultProductPartsManager() {
	}

	public static IProductPartsManager getInstance() {
		return LazyHolder.instance;
	}

	/**
	 * @param mdoTableAsEnumsManager the mdoTableAsEnumsManager to set
	 */
	public void setMdoTableAsEnumsManager(IMdoTableAsEnumsManager mdoTableAsEnumsManager) {
		this.mdoTableAsEnumsManager = mdoTableAsEnumsManager;
	}

	/**
	 * @return the mdoTableAsEnumsManager
	 */
	public IMdoTableAsEnumsManager getMdoTableAsEnumsManager() {
		return mdoTableAsEnumsManager;
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
	protected String getDefaultLabel(IBeanLabelable mdoBean) {
		String result = null;
		ProductPart mdoBeanCasted = (ProductPart) mdoBean;
		if (mdoBeanCasted != null && mdoBeanCasted.getCode() != null) {
			result = mdoBeanCasted.getCode().getLanguageKeyLabel();
		}
		return result;
	}

	@Override
	public void processList(IAdministrationManagerViewBean viewBean, MdoUserContext userContext, boolean... lazy) throws MdoBusinessException {
		super.processList(viewBean, userContext, lazy);
		ProductPartsManagerViewBean productPartsManagerViewBean = (ProductPartsManagerViewBean) viewBean;
		try {
			productPartsManagerViewBean.setLabels(super.getLabels(userContext.getCurrentLocale()));
			productPartsManagerViewBean.setLanguages(this.localesManager.getLanguages(userContext.getCurrentLocale().getLanguageCode()));
			productPartsManagerViewBean.setCodes(this.getRemainingList(mdoTableAsEnumsManager.getProductParts(userContext), userContext));
			
		} catch (Exception e) {
			logger.error("message.error.administration.business.find.all", e);
			throw new MdoBusinessException("message.error.administration.business.find.all", e);
		}
	}

	/**
	 * Remove already existing bean from listAll.
	 * @param listAll list of bean to be removed.
	 * @param userContext user context.
	 * @return a restricted list.
	 */
	private List<IMdoDtoBean> getRemainingList(List<IMdoDtoBean> listAll, MdoUserContext userContext) {
		List<IMdoDtoBean> result = new ArrayList<IMdoDtoBean>(listAll);
		List<IMdoDtoBean> excludedList = new ArrayList<IMdoDtoBean>();
		try {
			excludedList = super.findAll(userContext);
		} catch (MdoBusinessException e) {
			logger.warn("message.error.administration.business.find.all", e);
		}
		for (IMdoDtoBean exlcudedBean : excludedList) {
			ProductPartDto exlcudedBeanCasted = (ProductPartDto) exlcudedBean;
			for (IMdoDtoBean tableAsEnum : listAll) {
				if (tableAsEnum.getId() != null && exlcudedBeanCasted.getCode() != null && tableAsEnum.getId().equals(exlcudedBeanCasted.getCode().getId())) {
					result.remove(tableAsEnum);
				}
			}
		}

		return result;
	}
}
