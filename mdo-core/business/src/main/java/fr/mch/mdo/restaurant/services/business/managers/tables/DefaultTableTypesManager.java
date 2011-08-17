package fr.mch.mdo.restaurant.services.business.managers.tables;

import java.util.ArrayList;
import java.util.List;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.tables.ITableTypesDao;
import fr.mch.mdo.restaurant.dao.tables.hibernate.DefaultTableTypesDao;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.TableTypeDto;
import fr.mch.mdo.restaurant.dto.beans.TableTypesManagerViewBean;
import fr.mch.mdo.restaurant.exception.MdoBusinessException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.AbstractAdministrationManager;
import fr.mch.mdo.restaurant.services.business.managers.DefaultMdoTableAsEnumsManager;
import fr.mch.mdo.restaurant.services.business.managers.assembler.DefaultTableTypesAssembler;
import fr.mch.mdo.restaurant.services.business.managers.products.IMdoTableAsEnumsManager;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultTableTypesManager extends AbstractAdministrationManager implements ITableTypesManager 
{
	private IMdoTableAsEnumsManager mdoTableAsEnumsManager;

	private static class LazyHolder {
		private static ITableTypesManager instance = new DefaultTableTypesManager(
				LoggerServiceImpl.getInstance().getLogger(DefaultTableTypesManager.class.getName()),
				DefaultTableTypesDao.getInstance(), DefaultTableTypesAssembler.getInstance());
	}

	private DefaultTableTypesManager(ILogger logger, ITableTypesDao dao, IManagerAssembler assembler) {
		super.logger = logger;
		super.dao = dao;
		super.assembler = assembler;
		this.mdoTableAsEnumsManager = DefaultMdoTableAsEnumsManager.getInstance();
	}

	/**
	 * This constructor is used by ioc
	 */
	public DefaultTableTypesManager() {
	}

	public static ITableTypesManager getInstance() {
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

	@Override
	public void processList(IAdministrationManagerViewBean viewBean, MdoUserContext userContext, boolean... lazy) throws MdoBusinessException {
		super.processList(viewBean, userContext, lazy);

		TableTypesManagerViewBean tableTypesManagerViewBean = (TableTypesManagerViewBean) viewBean;
		
		try {
			tableTypesManagerViewBean.setCodes(this.getAvailableTypeTables(mdoTableAsEnumsManager.getTableTypes(userContext), userContext));
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
	private List<IMdoDtoBean> getAvailableTypeTables(List<IMdoDtoBean> listAll, MdoUserContext userContext) {
		List<IMdoDtoBean> result = new ArrayList<IMdoDtoBean>(listAll);
		List<IMdoDtoBean> excludedList = new ArrayList<IMdoDtoBean>();
		try {
			excludedList = super.findAll(userContext);
		} catch (MdoBusinessException e) {
			logger.warn("message.error.administration.business.find.all", e);
		}
		for (IMdoDtoBean exlcudedBean : excludedList) {
			TableTypeDto exlcudedBeanCasted = (TableTypeDto) exlcudedBean;
			for (IMdoDtoBean tableAsEnum : listAll) {
				if (tableAsEnum.getId() != null && exlcudedBeanCasted.getCode() != null && tableAsEnum.getId().equals(exlcudedBeanCasted.getCode().getId())) {
					result.remove(tableAsEnum);
				}
			}
		}

		return result;
	}

	@Override
	public TableTypeDto findByCodeName(String codeName, MdoUserContext userContext) throws MdoBusinessException {
		TableTypeDto result = null;
		try {
			result = (TableTypeDto) assembler.marshal((IMdoDaoBean) super.dao.findByUniqueKey(codeName), userContext);
		} catch (MdoException e) {
			logger.error("message.error.business.DefaultTableTypesManager.findByCodeName", new Object[]{codeName}, e);
			throw new MdoBusinessException("message.error.business.DefaultTableTypesManager.findByCodeName", new Object[]{codeName}, e);
		}
		return result;
	}
}
