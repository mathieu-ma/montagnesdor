package fr.mch.mdo.restaurant.services.business.managers.restaurants;

import java.util.ArrayList;
import java.util.List;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
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

	private String generateReference(RestaurantDto restaurant, MdoUserContext userContext) {
		StringBuilder result = new StringBuilder(restaurant.getName()).append(restaurant.getRegistrationDate());
		
		return result.toString();
	}
	
	@Override
	public IMdoDtoBean save(IMdoDtoBean dtoBean, MdoUserContext userContext) throws MdoBusinessException {
		RestaurantDto restaurant = (RestaurantDto) dtoBean;
		restaurant.setReference(this.generateReference(restaurant, userContext));

		return super.save(dtoBean, userContext);
	}

	@Override
	public IMdoDtoBean update(IMdoDtoBean dtoBean, MdoUserContext userContext) throws MdoBusinessException {
		Restaurant daoBean = (Restaurant) assembler.unmarshal(dtoBean);
		try {
			if (daoBean != null && daoBean.getId() != null) {
				// dummy is just used for updating daoBean.getVats() and daoBean.getPrefixTableNames()
				Restaurant dummy  = (Restaurant) dao.findByPrimaryKey(daoBean.getId());
				dummy.getVats().clear();
				dummy.getVats().addAll(daoBean.getVats());
				daoBean.setVats(dummy.getVats());
				dummy.getPrefixTableNames().clear();
				dummy.getPrefixTableNames().addAll(daoBean.getPrefixTableNames());
				daoBean.setPrefixTableNames(dummy.getPrefixTableNames());
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
		// Delete Vats/Prefix Table Names before
		this.update(dtoBean, userContext);
		// Delete dto
		return super.delete(dtoBean, userContext);
	}
}
