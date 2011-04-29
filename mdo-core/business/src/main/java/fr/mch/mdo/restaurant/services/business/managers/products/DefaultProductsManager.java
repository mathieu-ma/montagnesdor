package fr.mch.mdo.restaurant.services.business.managers.products;

import java.util.ArrayList;
import java.util.List;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IBeanLabelable;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.Product;
import fr.mch.mdo.restaurant.dao.products.IProductsDao;
import fr.mch.mdo.restaurant.dao.products.hibernate.DefaultProductsDao;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.ProductsManagerViewBean;
import fr.mch.mdo.restaurant.exception.MdoBusinessException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.AbstractAdministrationManagerLabelable;
import fr.mch.mdo.restaurant.services.business.managers.ICategoriesManager;
import fr.mch.mdo.restaurant.services.business.managers.assembler.DefaultProductsAssembler;
import fr.mch.mdo.restaurant.services.business.managers.locales.DefaultLocalesManager;
import fr.mch.mdo.restaurant.services.business.managers.locales.ILocalesManager;
import fr.mch.mdo.restaurant.services.business.managers.restaurants.DefaultRestaurantsManager;
import fr.mch.mdo.restaurant.services.business.managers.restaurants.IRestaurantsManager;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultProductsManager extends AbstractAdministrationManagerLabelable implements IProductsManager
{
	private IRestaurantsManager restaurantsManager;
	private IValueAddedTaxesManager valueAddedTaxesManager;
	private ICategoriesManager categoriesManager;
	private ILocalesManager localesManager;
	
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
		this.localesManager = DefaultLocalesManager.getInstance();
	}

	/**
	 * This constructor is used by ioc
	 */
	public DefaultProductsManager() {
	}

	public static IProductsManager getInstance() {
		return LazyHolder.instance;
	}

	/**
	 * @return the restaurantsManager
	 */
	public IRestaurantsManager getRestaurantsManager() {
		return restaurantsManager;
	}

	/**
	 * @param restaurantsManager the restaurantsManager to set
	 */
	public void setRestaurantsManager(IRestaurantsManager restaurantsManager) {
		this.restaurantsManager = restaurantsManager;
	}

	/**
	 * @return the valueAddedTaxesManager
	 */
	public IValueAddedTaxesManager getValueAddedTaxesManager() {
		return valueAddedTaxesManager;
	}

	/**
	 * @param valueAddedTaxesManager the valueAddedTaxesManager to set
	 */
	public void setValueAddedTaxesManager(
			IValueAddedTaxesManager valueAddedTaxesManager) {
		this.valueAddedTaxesManager = valueAddedTaxesManager;
	}

	/**
	 * @return the categoriesManager
	 */
	public ICategoriesManager getCategoriesManager() {
		return categoriesManager;
	}

	/**
	 * @param categoriesManager the categoriesManager to set
	 */
	public void setCategoriesManager(ICategoriesManager categoriesManager) {
		this.categoriesManager = categoriesManager;
	}

	/**
	 * @return the localesManager
	 */
	public ILocalesManager getLocalesManager() {
		return localesManager;
	}

	/**
	 * @param localesManager the localesManager to set
	 */
	public void setLocalesManager(ILocalesManager localesManager) {
		this.localesManager = localesManager;
	}

	@Override
	public void processList(IAdministrationManagerViewBean viewBean, MdoUserContext userContext, boolean... lazy) throws MdoBusinessException {
		super.processList(viewBean, userContext, lazy);
		ProductsManagerViewBean productsManagerViewBean = (ProductsManagerViewBean) viewBean;
		try {
			productsManagerViewBean.setLabels(super.getLabels(userContext.getCurrentLocale()));
			productsManagerViewBean.setLanguages(localesManager.getLanguages(userContext.getCurrentLocale().getLanguageCode()));
			productsManagerViewBean.setRestaurants(restaurantsManager.findAll(userContext, lazy));
			productsManagerViewBean.setVats(valueAddedTaxesManager.findAll(userContext, lazy));
			productsManagerViewBean.setCategories(categoriesManager.findAll(userContext, lazy));
		} catch (Exception e) {
			logger.error("message.error.administration.business.find.all", e);
			throw new MdoBusinessException("message.error.administration.business.find.all", e);
		}
	}

	@Override
	protected String getDefaultLabel(IBeanLabelable mdoBean) {
		String result = null;
		if (mdoBean != null) {
			Product mdoBeanCasted = (Product) mdoBean;
			result = mdoBeanCasted.getCode();
		}
		return result;
	}

	@Override
	public List<IMdoDtoBean> getList(Long restaurantId, MdoUserContext userContext) throws MdoException {
		List<IMdoDtoBean> result = new ArrayList<IMdoDtoBean>();
		
		try {
			List<IMdoBean> list = ((IProductsDao) dao).findByRestaurant(restaurantId);
			if (list != null) {
				result = assembler.marshal(list, userContext);
			}
		} catch (MdoException e) {
			logger.error("message.error.administration.business.products.by.restaurant", new Object[] {restaurantId}, e);
			throw new MdoBusinessException("message.error.administration.business.products.by.restaurant", new Object[] {restaurantId}, e);
		}

		return result;
	}
	
	@Override
	public IMdoDtoBean update(IMdoDtoBean dtoBean, MdoUserContext userContext) throws MdoBusinessException {
		Product daoBean = (Product) assembler.unmarshal(dtoBean);
		try {
			if (daoBean != null && daoBean.getId() != null) {
				// dummy is just used for updating daoBean.getCategories()
				Product dummy  = (Product) dao.findByPrimaryKey(daoBean.getId());
				dummy.getCategories().clear();
				dummy.getCategories().addAll(daoBean.getCategories());
				daoBean.setCategories(dummy.getCategories());
			}
			return assembler.marshal((IMdoDaoBean) dao.update(daoBean), userContext);
		} catch (MdoException e) {
			logger.error("message.error.administration.business.save", e);
			throw new MdoBusinessException("message.error.administration.business.save", e);
		}
	}
	
	@Override
	public IMdoDtoBean delete(IMdoDtoBean dtoBean, MdoUserContext userContext) throws MdoBusinessException {
		// Load data
		dtoBean = super.findByPrimaryKey(dtoBean.getId(), userContext);
		// Delete categories before
		this.update(dtoBean, userContext);
		// Delete dto
		return super.delete(dtoBean, userContext);
	}

}
