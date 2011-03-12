package fr.mch.mdo.restaurant.services.business.managers.products;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.beans.Category;
import fr.mch.mdo.restaurant.dao.products.ICategoriesDao;
import fr.mch.mdo.restaurant.dao.products.hibernate.DefaultCategoriesDao;
import fr.mch.mdo.restaurant.dto.beans.CategoriesManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.LocaleDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.exception.MdoBusinessException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.AbstractAdministrationManager;
import fr.mch.mdo.restaurant.services.business.managers.ICategoriesManager;
import fr.mch.mdo.restaurant.services.business.managers.assembler.DefaultCategoriesAssembler;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultCategoriesManager extends AbstractAdministrationManager implements ICategoriesManager 
{
	private static class LazyHolder {
		private static ICategoriesManager instance = new DefaultCategoriesManager(LoggerServiceImpl.getInstance().getLogger(DefaultCategoriesManager.class.getName()),
				DefaultCategoriesDao.getInstance(), DefaultCategoriesAssembler.getInstance());
	}

	private DefaultCategoriesManager(ILogger logger, ICategoriesDao dao, IManagerAssembler assembler) {
		super.logger = logger;
		super.dao = dao;
		super.assembler = assembler;
	}

	/**
	 * This constructor is used by ioc
	 */
	public DefaultCategoriesManager() {
	}

	public static ICategoriesManager getInstance() {
		return LazyHolder.instance;
	}

	@Override
	public Map<String, String> getLabels(LocaleDto currentLocale) throws MdoBusinessException {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		try {
			list = dao.findAll();
		} catch (MdoException e) {
			logger.error("message.error.administration.business.find.all", e);
			throw new MdoBusinessException("message.error.administration.business.find.all", e);
		}
		Map<String, String> result = new HashMap<String, String>(list.size());

		for (Iterator<IMdoBean> iterator = list.iterator(); iterator.hasNext();) {
			Category mdoBean = (Category) iterator.next();
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
		CategoriesManagerViewBean categoriesManagerViewBean = (CategoriesManagerViewBean) viewBean;
		try {
//			MdoUserContext userContext = viewBean.getUserContext(); 
			categoriesManagerViewBean.setLanguages(this.getLabels(userContext.getCurrentLocale()));
		} catch (Exception e) {
			logger.error("message.error.administration.business.find.all", e);
			throw new MdoBusinessException("message.error.administration.business.find.all", e);
		}
	}
}
