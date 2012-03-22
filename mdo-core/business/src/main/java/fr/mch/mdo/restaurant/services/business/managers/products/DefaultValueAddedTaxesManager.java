package fr.mch.mdo.restaurant.services.business.managers.products;

import java.util.ArrayList;
import java.util.List;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.products.IValueAddedTaxesDao;
import fr.mch.mdo.restaurant.dao.products.hibernate.DefaultValueAddedTaxesDao;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.ValueAddedTaxDto;
import fr.mch.mdo.restaurant.dto.beans.ValueAddedTaxesManagerViewBean;
import fr.mch.mdo.restaurant.exception.MdoBusinessException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.AbstractAdministrationManager;
import fr.mch.mdo.restaurant.services.business.managers.DefaultMdoTableAsEnumsManager;
import fr.mch.mdo.restaurant.services.business.managers.assembler.DefaultValueAddedTaxesAssembler;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultValueAddedTaxesManager extends AbstractAdministrationManager implements IValueAddedTaxesManager 
{
	private IMdoTableAsEnumsManager mdoTableAsEnumsManager;

	private static class LazyHolder {
		private static IValueAddedTaxesManager instance = new DefaultValueAddedTaxesManager(
				LoggerServiceImpl.getInstance().getLogger(DefaultValueAddedTaxesManager.class.getName()), DefaultValueAddedTaxesDao.getInstance(), 
				DefaultValueAddedTaxesAssembler.getInstance());
	}

	private DefaultValueAddedTaxesManager(ILogger logger, IValueAddedTaxesDao dao, IManagerAssembler assembler) {
		super.logger = logger;
		super.dao = dao;
		super.assembler = assembler;
		this.mdoTableAsEnumsManager = DefaultMdoTableAsEnumsManager.getInstance();
	}

	/**
	 * This constructor is used by ioc
	 */
	public DefaultValueAddedTaxesManager() {
	}

	public static IValueAddedTaxesManager getInstance() {
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

	@Override
	public void processList(IAdministrationManagerViewBean viewBean, MdoUserContext userContext, boolean... lazy) throws MdoBusinessException {
		super.processList(viewBean, userContext, lazy);

		ValueAddedTaxesManagerViewBean valueAddedTaxesManagerViewBean = (ValueAddedTaxesManagerViewBean) viewBean;
		
		try {
			valueAddedTaxesManagerViewBean.setCodes(this.getAvailableValueAddedTaxes(mdoTableAsEnumsManager.getValueAddedTaxes(userContext), userContext));
		} catch (MdoException e) {
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
	private List<IMdoDtoBean> getAvailableValueAddedTaxes(List<IMdoDtoBean> listAll, MdoUserContext userContext) {
		List<IMdoDtoBean> result = new ArrayList<IMdoDtoBean>(listAll);
		List<IMdoDtoBean> excludedList = new ArrayList<IMdoDtoBean>();
		try {
			excludedList = super.findAll(userContext);
		} catch (MdoBusinessException e) {
			logger.warn("message.error.administration.business.find.all", e);
		}
		for (IMdoDtoBean exlcudedBean : excludedList) {
			ValueAddedTaxDto exlcudedBeanCasted = (ValueAddedTaxDto) exlcudedBean;
			for (IMdoDtoBean tableAsEnum : listAll) {
				if (tableAsEnum.getId() != null && exlcudedBeanCasted.getCode() != null && tableAsEnum.getId().equals(exlcudedBeanCasted.getCode().getId())) {
					result.remove(tableAsEnum);
				}
			}
		}

		return result;
	}
	
	@Override
	public ValueAddedTaxDto findByCodeName(String codeName, MdoUserContext userContext) throws MdoBusinessException {
		ValueAddedTaxDto result = null;
		try {
			result = (ValueAddedTaxDto) assembler.marshal((IMdoDaoBean) super.dao.findByUniqueKey(codeName), userContext);
		} catch (MdoException e) {
			logger.error("message.error.business.DefaultValueAddedTaxesManager.findByCodeName", new Object[]{codeName}, e);
			throw new MdoBusinessException("message.error.business.DefaultValueAddedTaxesManager.findByCodeName", new Object[]{codeName}, e);
		}
		return result;
	}
}
