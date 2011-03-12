package fr.mch.mdo.restaurant.services.business.managers.products;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.dao.beans.Product;
import fr.mch.mdo.restaurant.dao.products.IProductsDao;
import fr.mch.mdo.restaurant.dao.products.hibernate.DefaultProductsDao;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.LocaleDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.ProductsManagerViewBean;
import fr.mch.mdo.restaurant.exception.MdoBusinessException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.AbstractAdministrationManager;
import fr.mch.mdo.restaurant.services.business.managers.ICategoriesManager;
import fr.mch.mdo.restaurant.services.business.managers.assembler.DefaultProductsAssembler;
import fr.mch.mdo.restaurant.services.business.managers.restaurants.DefaultRestaurantsManager;
import fr.mch.mdo.restaurant.services.business.managers.restaurants.IRestaurantsManager;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultProductsManager extends AbstractAdministrationManager implements IProductsManager
{
	private IRestaurantsManager restaurantsManager;
	private IValueAddedTaxesManager valueAddedTaxesManager;
	private ICategoriesManager categoriesManager;
	
	private static class LazyHolder {
		private static IProductsManager instance = new DefaultProductsManager(
				LoggerServiceImpl.getInstance().getLogger(DefaultProductsManager.class.getName()),
				DefaultProductsDao.getInstance(), DefaultProductsAssembler.getInstance());
	}

	private DefaultProductsManager(ILogger logger, IProductsDao dao, IManagerAssembler assembler) {
		super.logger = logger;
		super.dao = dao;
		super.assembler = assembler;
		this.restaurantsManager = DefaultRestaurantsManager.getInstance();
		this.valueAddedTaxesManager = DefaultValueAddedTaxesManager.getInstance();
		this.categoriesManager = DefaultCategoriesManager.getInstance();
	}

	/**
	 * This constructor is used by ioc
	 */
	public DefaultProductsManager() {
	}

	public static IProductsManager getInstance() {
		return LazyHolder.instance;
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
			Product mdoBean = (Product) iterator.next();
		
			String label = null;
			if (currentLocale != null && mdoBean.getLabels() != null && !mdoBean.getLabels().isEmpty()) {
				label = mdoBean.getLabels().get(currentLocale.getId());
				if (label == null) {
					label = mdoBean.getLabels().values().iterator().next();
				}
			}
			if (label == null) {
				label = mdoBean.getCode();
			}
			result.put(mdoBean.getId().toString(), label);
		}
		return result;
	}

	@Override
	public void processList(IAdministrationManagerViewBean viewBean, MdoUserContext userContext, boolean... lazy) throws MdoBusinessException {
		super.processList(viewBean, userContext, lazy);
		ProductsManagerViewBean productsManagerViewBean = (ProductsManagerViewBean) viewBean;
		try {
			//MdoUserContext userContext = viewBean.getUserContext(); 
			productsManagerViewBean.setLanguages(this.getLabels(userContext.getCurrentLocale()));
			productsManagerViewBean.setRestaurants(restaurantsManager.findAll(userContext, lazy));
			productsManagerViewBean.setVats(valueAddedTaxesManager.findAll(userContext, lazy));
			productsManagerViewBean.setCategoryLabels(categoriesManager.getLabels(userContext.getCurrentLocale()));
		} catch (Exception e) {
			logger.error("message.error.administration.business.find.all", e);
			throw new MdoBusinessException("message.error.administration.business.find.all", e);
		}
	}
}
