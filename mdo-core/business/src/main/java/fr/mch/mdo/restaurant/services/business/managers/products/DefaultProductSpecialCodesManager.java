package fr.mch.mdo.restaurant.services.business.managers.products;

import java.util.ArrayList;
import java.util.List;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IBeanLabelable;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.ProductSpecialCode;
import fr.mch.mdo.restaurant.dao.products.IProductSpecialCodesDao;
import fr.mch.mdo.restaurant.dao.products.hibernate.DefaultProductSpecialCodesDao;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.ProductSpecialCodeDto;
import fr.mch.mdo.restaurant.dto.beans.ProductSpecialCodesManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.exception.MdoBusinessException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.ManagedProductSpecialCode;
import fr.mch.mdo.restaurant.services.business.managers.AbstractAdministrationManagerLabelable;
import fr.mch.mdo.restaurant.services.business.managers.DefaultMdoTableAsEnumsManager;
import fr.mch.mdo.restaurant.services.business.managers.assembler.DefaultProductSpecialCodesAssembler;
import fr.mch.mdo.restaurant.services.business.managers.locales.DefaultLocalesManager;
import fr.mch.mdo.restaurant.services.business.managers.locales.ILocalesManager;
import fr.mch.mdo.restaurant.services.business.managers.restaurants.DefaultRestaurantsManager;
import fr.mch.mdo.restaurant.services.business.managers.restaurants.IRestaurantsManager;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultProductSpecialCodesManager extends AbstractAdministrationManagerLabelable implements IProductSpecialCodesManager 
{
	private IMdoTableAsEnumsManager mdoTableAsEnumsManager;
	private ILocalesManager localesManager;
	private IRestaurantsManager restaurantsManager;

	private static class LazyHolder {
		private static IProductSpecialCodesManager instance = new DefaultProductSpecialCodesManager(
				LoggerServiceImpl.getInstance().getLogger(DefaultProductSpecialCodesManager.class.getName()),
				DefaultProductSpecialCodesDao.getInstance(), DefaultProductSpecialCodesAssembler.getInstance());
	}

	private DefaultProductSpecialCodesManager(ILogger logger, IProductSpecialCodesDao dao, IManagerAssembler assembler) {
		super.logger = logger;
		super.dao = dao;
		super.assembler = assembler;
		this.mdoTableAsEnumsManager = DefaultMdoTableAsEnumsManager.getInstance();
		this.localesManager = DefaultLocalesManager.getInstance();
		this.restaurantsManager = DefaultRestaurantsManager.getInstance();
	}

	/**
	 * This constructor is used by ioc
	 */
	public DefaultProductSpecialCodesManager() {
	}

	public static IProductSpecialCodesManager getInstance() {
		return LazyHolder.instance;
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

	@Override
	protected String getDefaultLabel(IBeanLabelable mdoBean) {
		String result = null;
		ProductSpecialCode mdoBeanCasted = (ProductSpecialCode) mdoBean;
		if (mdoBeanCasted != null && mdoBeanCasted.getCode() != null) {
			result = mdoBeanCasted.getCode().getLanguageKeyLabel();
		}
		return result;
	}

	
	@Override
	public List<ManagedProductSpecialCode> getManagedProductSpecialCodes(IMdoDtoBean dtoBean, MdoUserContext userContext) throws MdoBusinessException {
		List<ManagedProductSpecialCode> result = new ArrayList<ManagedProductSpecialCode>();

		ProductSpecialCodeDto psc = (ProductSpecialCodeDto) dtoBean;
		List<IMdoBean> productSpecialCodes;
		try {
			List<IMdoDtoBean> restaurants = restaurantsManager.findAll(userContext);
			RestaurantDto selectedRestaurant = psc!=null && psc.getRestaurant()!=null?psc.getRestaurant():(RestaurantDto) restaurants.get(0);
			productSpecialCodes = ((IProductSpecialCodesDao) dao).findProductSpecialCodesByRestaurant(selectedRestaurant.getId());
		} catch (MdoException e) {
			logger.error("message.error.administration.business.find.all", e);
			throw new MdoBusinessException("message.error.administration.business.find.all", e);
		}
		for (ManagedProductSpecialCode mPSC : ManagedProductSpecialCode.values()) {
			boolean isAlreadyStored = false;
			for (IMdoBean bean : productSpecialCodes) {
				ProductSpecialCode productSpecialCode = (ProductSpecialCode) bean;
				if (mPSC.name().equals(productSpecialCode.getCode().getName()) 
						&& psc!=null && !productSpecialCode.getId().equals(psc.getId())) {
					isAlreadyStored = true;
					break;
				}
			}
			if (!isAlreadyStored) {
				result.add(mPSC);
			}
		}

		return result;
	}

//	@Override
//	public IMdoDtoBean save(IMdoDtoBean dtoBean, MdoUserContext userContext) throws MdoBusinessException {
//		ProductSpecialCode daoBean = (ProductSpecialCode) assembler.unmarshal(dtoBean);
//		if (daoBean != null) {
//			// Check that short code product is unique in the restaurant
//			ProductSpecialCode daoBeanX;
//			try {
//				daoBeanX = (ProductSpecialCode) ((IProductSpecialCodesDao) dao).findByRestaurantAndShortCode(daoBean.getRestaurant().getId(), daoBean.getShortCode());
//			} catch (MdoException e) {
//				throw new MdoBusinessException(e);
//			}
//			if (daoBeanX != null) {
//				if (daoBean.getId() == null || !daoBean.getId().equals(daoBeanX.getId())) {
//					throw new MdoBusinessException("Duplicate short code for this restaurant");
//				}
//				dtoBean = assembler.marshal(daoBeanX, userContext);
//			}
//		}
//		return super.save(dtoBean, userContext);
//	}
	
	@Override
	public void processList(IAdministrationManagerViewBean viewBean, MdoUserContext userContext, boolean... lazy) throws MdoBusinessException {
		super.processList(viewBean, userContext, lazy);
		ProductSpecialCodesManagerViewBean productSpecialCodesManagerViewBean = (ProductSpecialCodesManagerViewBean) viewBean;
		try {
			productSpecialCodesManagerViewBean.setLabels(super.getLabels(userContext.getCurrentLocale()));
			productSpecialCodesManagerViewBean.setLanguages(this.localesManager.getLanguages(userContext.getCurrentLocale().getLanguageCode()));
			productSpecialCodesManagerViewBean.setCodes(this.getRemainingList(mdoTableAsEnumsManager.getProductSpecialCodes(userContext), userContext));
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
			ProductSpecialCodeDto exlcudedBeanCasted = (ProductSpecialCodeDto) exlcudedBean;
			for (IMdoDtoBean tableAsEnum : listAll) {
				if (tableAsEnum.getId() != null && exlcudedBeanCasted.getCode() != null && tableAsEnum.getId().equals(exlcudedBeanCasted.getCode().getId())) {
					result.remove(tableAsEnum);
				}
			}
		}

		return result;
	}

}
