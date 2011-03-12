package fr.mch.mdo.restaurant.services.business.managers.products;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.ProductSpecialCode;
import fr.mch.mdo.restaurant.dao.products.IProductSpecialCodesDao;
import fr.mch.mdo.restaurant.dao.products.hibernate.DefaultProductSpecialCodesDao;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.LocaleDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.ProductSpecialCodeDto;
import fr.mch.mdo.restaurant.dto.beans.ProductSpecialCodesManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.exception.MdoBusinessException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.ManagedProductSpecialCode;
import fr.mch.mdo.restaurant.services.business.managers.AbstractAdministrationManager;
import fr.mch.mdo.restaurant.services.business.managers.assembler.DefaultProductSpecialCodesAssembler;
import fr.mch.mdo.restaurant.services.business.managers.restaurants.DefaultRestaurantsManager;
import fr.mch.mdo.restaurant.services.business.managers.restaurants.IRestaurantsManager;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultProductSpecialCodesManager extends AbstractAdministrationManager implements IProductSpecialCodesManager 
{
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

	@Override
	public IMdoDtoBean save(IMdoDtoBean dtoBean, MdoUserContext userContext) throws MdoBusinessException {
		ProductSpecialCode daoBean = (ProductSpecialCode) assembler.unmarshal(dtoBean);
		if (daoBean != null) {
			// Check that short code product is unique in the restaurant
			ProductSpecialCode daoBeanX;
			try {
				daoBeanX = (ProductSpecialCode) ((IProductSpecialCodesDao) dao).findByRestaurantAndShortCode(daoBean.getRestaurant().getId(), daoBean.getShortCode());
			} catch (MdoException e) {
				throw new MdoBusinessException(e);
			}
			if (daoBeanX != null) {
				if (daoBean.getId() == null || !daoBean.getId().equals(daoBeanX.getId())) {
					throw new MdoBusinessException("Duplicate short code for this restaurant");
				}
				dtoBean = assembler.marshal(daoBeanX, userContext);
			}
		}
		return super.save(dtoBean, userContext);
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
			ProductSpecialCode mdoBean = (ProductSpecialCode) iterator.next();
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
		ProductSpecialCodesManagerViewBean productSpecialCodesManagerViewBean = (ProductSpecialCodesManagerViewBean) viewBean;
		try {
			//MdoUserContext userContext = viewBean.getUserContext();
			productSpecialCodesManagerViewBean.setLanguages(this.getLabels(userContext.getCurrentLocale()));
			productSpecialCodesManagerViewBean.setRestaurants(restaurantsManager.findAll(userContext));
			productSpecialCodesManagerViewBean.setProductSpecialCodes(this.getManagedProductSpecialCodes(viewBean.getDtoBean(), userContext));
		} catch (Exception e) {
			logger.error("message.error.administration.business.find.all", e);
			throw new MdoBusinessException("message.error.administration.business.find.all", e);
		}
	}

}
