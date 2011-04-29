package fr.mch.mdo.restaurant.services.business.managers;

import java.util.ArrayList;
import java.util.List;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.IDao;
import fr.mch.mdo.restaurant.dao.IDaoServices;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.exception.MdoBusinessException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.utils.IManagerAssembler;

public abstract class AbstractAdministrationManager implements IAdministrationManager 
{
	protected ILogger logger;
	protected IDaoServices dao;
	protected IManagerAssembler assembler;

	public IManagerAssembler getAssembler() {
		return assembler;
	}

	public void setAssembler(IManagerAssembler assembler) {
		this.assembler = assembler;
	}

	@Override
	public ILogger getLogger() {
		return logger;
	}

	@Override
	public void setLogger(ILogger logger) {
		this.logger = logger;
	}

	@Override
	public IDao getDao() {
		return dao;
	}

	@Override
	public void setDao(IDao dao) {
		this.dao = (IDaoServices) dao;
	}

	@Override
	public IMdoDtoBean save(IMdoDtoBean dtoBean, MdoUserContext userContext) throws MdoBusinessException {
		if (dtoBean != null) {
			if (dtoBean.getId() == null) {
				dtoBean = insert(dtoBean, userContext);
			} else {
				dtoBean = update(dtoBean, userContext);
			}
		}
		return dtoBean;
	}

	@Override
	public IMdoDtoBean insert(IMdoDtoBean dtoBean, MdoUserContext userContext) throws MdoBusinessException {
		IMdoBean daoBean = assembler.unmarshal(dtoBean);
		try {
			return assembler.marshal((IMdoDaoBean) dao.insert(daoBean), userContext);
		} catch (MdoException e) {
			logger.error("message.error.administration.business.save", e);
			throw new MdoBusinessException("message.error.administration.business.save", e);
		}
	}

	@Override
	public IMdoDtoBean update(IMdoDtoBean dtoBean, MdoUserContext userContext) throws MdoBusinessException {
		IMdoBean daoBean = assembler.unmarshal(dtoBean);
		try {
			return assembler.marshal((IMdoDaoBean) dao.update(daoBean), userContext);
		} catch (MdoException e) {
			logger.error("message.error.administration.business.save", e);
			throw new MdoBusinessException("message.error.administration.business.save", e);
		}
	}

	@Override
	public IMdoDtoBean findByPrimaryKey(Long id, MdoUserContext userContext, boolean... lazy) throws MdoBusinessException {
		if (id != null) {
			try {
				return assembler.marshal((IMdoDaoBean) dao.findByPrimaryKey(id, lazy), userContext);
			} catch (MdoException e) {
				logger.error("message.error.administration.business.find", new Object[] { id }, e);
				throw new MdoBusinessException("message.error.administration.business.find", new Object[] { id }, e);
			}
		} else {
			logger.error("message.error.generic.field.null", new Object[] { Long.class + ".id" });
			throw new MdoBusinessException("message.error.generic.field.null");
		}
	}

	@Override
	public IMdoDtoBean delete(IMdoDtoBean dtoBean, MdoUserContext userContext) throws MdoBusinessException {
		IMdoBean daoBean = assembler.unmarshal(dtoBean);
		try {
			return assembler.marshal((IMdoDaoBean) dao.delete(daoBean), userContext);
		} catch (MdoException e) {
			logger.error("message.error.administration.business.delete", e);
			throw new MdoBusinessException("message.error.administration.business.delete", e);
		}
	}

	@Override
	public IMdoDtoBean load(IMdoDtoBean dtoBean, MdoUserContext userContext, boolean... lazy) throws MdoBusinessException {
		IMdoBean daoBean = assembler.unmarshal(dtoBean);
		try {
			dao.load(daoBean, lazy);
			return assembler.marshal((IMdoDaoBean) daoBean, userContext);
		} catch (MdoException e) {
			logger.error("message.error.administration.business.find", new Object[] { daoBean }, e);
			throw new MdoBusinessException("message.error.administration.business.find", new Object[] { daoBean }, e);
		}
	}

	@Override
	public List<IMdoDtoBean> findAll(MdoUserContext userContext, boolean... lazy) throws MdoBusinessException {
		List<IMdoDtoBean> result = new ArrayList<IMdoDtoBean>();
		try {
			List<IMdoBean> list = dao.findAll(lazy);
			if (list != null) {
				result = assembler.marshal(list, userContext);
			}
		} catch (MdoException e) {
			logger.error("message.error.administration.business.find.all", e);
			throw new MdoBusinessException("message.error.administration.business.find.all", e);
		}

		return result;
	}

	@Override
	public void processList(IAdministrationManagerViewBean viewBean, MdoUserContext userContext, boolean... lazy) throws MdoBusinessException {
		viewBean.setList(this.findAll(userContext, lazy));
		// throw new
		// MdoBusinessException("message.error.generic.method.not.implemented");
	}
}