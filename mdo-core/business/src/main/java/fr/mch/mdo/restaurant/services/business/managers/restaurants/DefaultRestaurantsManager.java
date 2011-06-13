package fr.mch.mdo.restaurant.services.business.managers.restaurants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dao.beans.RestaurantPrefixTable;
import fr.mch.mdo.restaurant.dao.beans.RestaurantValueAddedTax;
import fr.mch.mdo.restaurant.dao.restaurants.IRestaurantsDao;
import fr.mch.mdo.restaurant.dao.restaurants.hibernate.DefaultRestaurantsDao;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.dto.beans.RestaurantsManagerViewBean;
import fr.mch.mdo.restaurant.exception.MdoBusinessException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.AbstractAdministrationManager;
import fr.mch.mdo.restaurant.services.business.managers.DefaultMdoTableAsEnumsManager;
import fr.mch.mdo.restaurant.services.business.managers.assembler.DefaultRestaurantsAssembler;
import fr.mch.mdo.restaurant.services.business.managers.products.DefaultValueAddedTaxesManager;
import fr.mch.mdo.restaurant.services.business.managers.products.IMdoTableAsEnumsManager;
import fr.mch.mdo.restaurant.services.business.managers.products.IValueAddedTaxesManager;
import fr.mch.mdo.restaurant.services.business.managers.tables.DefaultTableTypesManager;
import fr.mch.mdo.restaurant.services.business.managers.tables.ITableTypesManager;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.restaurant.services.util.RestaurantReferenceFactory;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultRestaurantsManager extends AbstractAdministrationManager implements IRestaurantsManager 
{
	private IMdoTableAsEnumsManager mdoTableAsEnumsManager;

	private IValueAddedTaxesManager valueAddedTaxesManager;

	private ITableTypesManager tableTypesManager;

	
	private static class LazyHolder {
		private static IRestaurantsManager instance = new DefaultRestaurantsManager(
				LoggerServiceImpl.getInstance().getLogger(DefaultRestaurantsManager.class.getName()),
				DefaultRestaurantsDao.getInstance(), DefaultRestaurantsAssembler.getInstance());
	}

	private DefaultRestaurantsManager(ILogger logger, IRestaurantsDao dao, IManagerAssembler assembler) {
		super.logger = logger;
		super.dao = dao;
		super.assembler = assembler;
		this.mdoTableAsEnumsManager = DefaultMdoTableAsEnumsManager.getInstance();
		this.valueAddedTaxesManager = DefaultValueAddedTaxesManager.getInstance();
		this.setTableTypesManager(DefaultTableTypesManager.getInstance());
	}

	/**
	 * This constructor is used by ioc
	 */
	public DefaultRestaurantsManager() {
	}

	public static IRestaurantsManager getInstance() {
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
	public void setMdoTableAsEnumsManager(IMdoTableAsEnumsManager mdoTableAsEnumsManager) {
		this.mdoTableAsEnumsManager = mdoTableAsEnumsManager;
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
	public void setValueAddedTaxesManager(IValueAddedTaxesManager valueAddedTaxesManager) {
		this.valueAddedTaxesManager = valueAddedTaxesManager;
	}

	/**
	 * @param tableTypesManager the tableTypesManager to set
	 */
	public void setTableTypesManager(ITableTypesManager tableTypesManager) {
		this.tableTypesManager = tableTypesManager;
	}

	/**
	 * @return the tableTypesManager
	 */
	public ITableTypesManager getTableTypesManager() {
		return tableTypesManager;
	}

	@Override
	public List<IMdoDtoBean> findRestaurantsByUser(Long userId, MdoUserContext userContext) throws MdoBusinessException {
		List<IMdoDtoBean> result = new ArrayList<IMdoDtoBean>();
		try {
			List<IMdoBean> restaurants = ((IRestaurantsDao) super.getDao()).findRestaurants(userId);
			result = assembler.marshal(restaurants, userContext);
		} catch (Exception e) {
			logger.error("message.error.administration.business.users.restaurants.not.found", new Object[] {userId}, e);
			throw new MdoBusinessException("message.error.administration.business.users.restaurants.not.found", new Object[] {userId}, e);
		}
		return result;
	}
	
	@Override
	public void processList(IAdministrationManagerViewBean viewBean, MdoUserContext userContext, boolean... lazy) throws MdoBusinessException {
		super.processList(viewBean, userContext, lazy);

		RestaurantsManagerViewBean restaurantsManagerViewBean = (RestaurantsManagerViewBean) viewBean;
		
		try {
			restaurantsManagerViewBean.setPrefixTableNames(mdoTableAsEnumsManager.getPrefixTableNames(userContext));
			restaurantsManagerViewBean.setSpecificRounds(mdoTableAsEnumsManager.getSpecificRounds(userContext));
			restaurantsManagerViewBean.setValueAddedTaxes(valueAddedTaxesManager.findAll(userContext, lazy));
			restaurantsManagerViewBean.setTableTypes(tableTypesManager.findAll(userContext, lazy));
			
		} catch (MdoException e) {
			logger.error("message.error.administration.business.find.all", e);
			throw new MdoBusinessException("message.error.administration.business.find.all", e);
		}
	}

	private String generateReference(RestaurantDto restaurant, MdoUserContext userContext) throws MdoBusinessException {
		String key = restaurant.getTripleDESKey();
		String value = new Long(restaurant.getRegistrationDate().getTime()).toString();
		String result = null;
		try {
			result = RestaurantReferenceFactory.getInstance().getReferenceFromValue(key, value);
		} catch (MdoException e) {
			throw new MdoBusinessException(e);
		}
		return result;
	}

	private String generateKey(RestaurantDto restaurant, MdoUserContext userContext) throws MdoBusinessException {
		String key = new StringBuilder(new Long(restaurant.getRegistrationDate().getTime()).toString()).append(restaurant.getName())
		.append(restaurant.getVatRef()).append(restaurant.getVisaRef()).toString();
		String value = "";
		String result = null;
		try {
			result = RestaurantReferenceFactory.getInstance().getReferenceFromValue(key, value);
		} catch (MdoException e) {
			throw new MdoBusinessException(e);
		}
		return result;
	}

	@Override
	public IMdoDtoBean save(IMdoDtoBean dtoBean, MdoUserContext userContext) throws MdoBusinessException {
		RestaurantDto restaurant = (RestaurantDto) dtoBean;
		// 1) Generate key
		String key = restaurant.getTripleDESKey();
		if (key == null || key.isEmpty()) {
			key = this.generateKey(restaurant, userContext);
		}
		restaurant.setTripleDESKey(key);
		// 1) Generate reference using generated key
		String reference = restaurant.getReference();
		if (reference == null || reference.isEmpty()) {
			reference = this.generateReference(restaurant, userContext);
		}
		restaurant.setReference(reference);

		return super.save(dtoBean, userContext);
	}

	@Override
	public IMdoDtoBean update(IMdoDtoBean dtoBean, MdoUserContext userContext) throws MdoBusinessException {
		Restaurant daoBean = (Restaurant) assembler.unmarshal(dtoBean);
		try {
			// Deleting daoBean.getVats() and daoBean.getPrefixTableNames() before inserting new ones
			Set<RestaurantValueAddedTax> backupVats = new HashSet<RestaurantValueAddedTax>(daoBean.getVats());
			Set<RestaurantPrefixTable> backupPrefixTableNames = new HashSet<RestaurantPrefixTable>(daoBean.getPrefixTableNames());
			// Removing
			daoBean.getVats().clear();
			daoBean.getPrefixTableNames().clear();
			dao.update(daoBean);
			// Restoring
			daoBean.getVats().addAll(backupVats);
			daoBean.getPrefixTableNames().addAll(backupPrefixTableNames);
			
			return assembler.marshal((IMdoDaoBean) dao.update(daoBean), userContext);
		} catch (MdoException e) {
			logger.error("message.error.administration.business.save", e);
			throw new MdoBusinessException("message.error.administration.business.save", e);
		}
	}
	
	@Override
	public IMdoDtoBean delete(IMdoDtoBean dtoBean, MdoUserContext userContext) throws MdoBusinessException {
		// No need to Delete Vats/Prefix before Deleting user because of hibernate mapping all-delete-orphan in collection
		// Delete dto
		return super.delete(dtoBean, userContext);
	}

	@Override
	public IMdoDtoBean findByReference(String reference, MdoUserContext userContext) throws MdoBusinessException {
		try {
			return assembler.marshal((IMdoDaoBean) dao.findByUniqueKey(reference), userContext);
		} catch (MdoException e) {
			logger.error("message.error.administration.business.restaurant.find.by.reference", new Object[] {reference},  e);
			throw new MdoBusinessException("message.error.administration.business.restaurant.find.by.reference", new Object[] {reference},  e);
		}
	}
}
