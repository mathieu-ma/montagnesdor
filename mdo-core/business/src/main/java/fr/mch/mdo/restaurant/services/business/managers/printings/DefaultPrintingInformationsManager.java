package fr.mch.mdo.restaurant.services.business.managers.printings;

import java.util.ArrayList;
import java.util.List;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IBeanLabelable;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.printings.IPrintingInformationsDao;
import fr.mch.mdo.restaurant.dao.printings.hibernate.DefaultPrintingInformationsDao;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.PrintingInformationsManagerViewBean;
import fr.mch.mdo.restaurant.exception.MdoBusinessException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.AbstractAdministrationManagerLabelable;
import fr.mch.mdo.restaurant.services.business.managers.DefaultMdoTableAsEnumsManager;
import fr.mch.mdo.restaurant.services.business.managers.assembler.DefaultPrintingInformationsAssembler;
import fr.mch.mdo.restaurant.services.business.managers.locales.DefaultLocalesManager;
import fr.mch.mdo.restaurant.services.business.managers.locales.ILocalesManager;
import fr.mch.mdo.restaurant.services.business.managers.products.IMdoTableAsEnumsManager;
import fr.mch.mdo.restaurant.services.business.managers.restaurants.DefaultRestaurantsManager;
import fr.mch.mdo.restaurant.services.business.managers.restaurants.IRestaurantsManager;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultPrintingInformationsManager extends AbstractAdministrationManagerLabelable implements IPrintingInformationsManager 
{
	private IRestaurantsManager restaurantsManager;
	private IMdoTableAsEnumsManager mdoTableAsEnumsManager;
	private ILocalesManager localesManager;

	private static class LazyHolder {
		private static IPrintingInformationsManager instance = new DefaultPrintingInformationsManager(LoggerServiceImpl.getInstance().getLogger(DefaultPrintingInformationsManager.class.getName()),
				DefaultPrintingInformationsDao.getInstance(), DefaultPrintingInformationsAssembler.getInstance());
	}

	private DefaultPrintingInformationsManager(ILogger logger, IPrintingInformationsDao dao, IManagerAssembler assembler) {
		super.logger = logger;
		super.dao = dao;
		super.assembler = assembler;
		this.setRestaurantsManager(DefaultRestaurantsManager.getInstance());
		this.mdoTableAsEnumsManager = DefaultMdoTableAsEnumsManager.getInstance();
		this.localesManager = DefaultLocalesManager.getInstance();
	}

	/**
	 * This constructor is used by ioc
	 */
	public DefaultPrintingInformationsManager() {
	}

	public static IPrintingInformationsManager getInstance() {
		return LazyHolder.instance;
	}

	/**
	 * @param restaurantsManager the restaurantsManager to set
	 */
	public void setRestaurantsManager(IRestaurantsManager restaurantsManager) {
		this.restaurantsManager = restaurantsManager;
	}

	/**
	 * @return the restaurantsManager
	 */
	public IRestaurantsManager getRestaurantsManager() {
		return restaurantsManager;
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
		return null;
	}

	@Override
	public List<IMdoDtoBean> getList(Long restaurantId, MdoUserContext userContext) throws MdoException {
		List<IMdoDtoBean> result = new ArrayList<IMdoDtoBean>();
		
		try {
			List<IMdoBean> list = ((IPrintingInformationsDao) dao).findByRestaurant(restaurantId);
			if (list != null) {
				result = assembler.marshal(list, userContext);
			}
		} catch (MdoException e) {
			logger.error("message.error.administration.business.printing.informations.by.restaurant", new Object[] {restaurantId}, e);
			throw new MdoBusinessException("message.error.administration.business.printing.informations.by.restaurant", new Object[] {restaurantId}, e);
		}

		return result;
	}

	@Override
	public void processList(IAdministrationManagerViewBean viewBean, MdoUserContext userContext, boolean... lazy) throws MdoBusinessException {
		super.processList(viewBean, userContext, lazy);
		PrintingInformationsManagerViewBean managerViewBean = (PrintingInformationsManagerViewBean) viewBean;
		try {
			managerViewBean.setLabels(super.getLabels(userContext.getCurrentLocale()));
			managerViewBean.setLanguages(this.localesManager.getLanguages(userContext.getCurrentLocale().getLanguageCode()));
			managerViewBean.setAlignments(mdoTableAsEnumsManager.getPrintingInformationAlignments(userContext));
			managerViewBean.setParts(mdoTableAsEnumsManager.getPrintingInformationParts(userContext));
			managerViewBean.setSizes(mdoTableAsEnumsManager.getPrintingInformationSizes(userContext));
			managerViewBean.setRestaurants(restaurantsManager.findAll(userContext));
		} catch (Exception e) {
			logger.error("message.error.administration.business.find.all", e);
			throw new MdoBusinessException("message.error.administration.business.find.all", e);
		}
	}
}
