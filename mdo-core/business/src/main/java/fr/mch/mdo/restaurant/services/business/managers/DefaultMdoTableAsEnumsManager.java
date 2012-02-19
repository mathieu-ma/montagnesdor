package fr.mch.mdo.restaurant.services.business.managers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.IMdoTableAsEnumsDao;
import fr.mch.mdo.restaurant.dao.beans.MdoTableAsEnum;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultMdoTableAsEnumsDao;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumDto;
import fr.mch.mdo.restaurant.dto.beans.MdoTableAsEnumsManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.exception.MdoBusinessException;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.assembler.DefaultMdoTableAsEnumsAssembler;
import fr.mch.mdo.restaurant.services.business.managers.products.IMdoTableAsEnumsManager;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultMdoTableAsEnumsManager extends AbstractAdministrationManager implements IMdoTableAsEnumsManager 
{
	private static class LazyHolder 
	{
		private static IMdoTableAsEnumsManager instance = new DefaultMdoTableAsEnumsManager(LoggerServiceImpl.getInstance()
				.getLogger(DefaultMdoTableAsEnumsManager.class.getName()), DefaultMdoTableAsEnumsDao.getInstance(), DefaultMdoTableAsEnumsAssembler.getInstance());
	}

	private DefaultMdoTableAsEnumsManager(ILogger logger, IMdoTableAsEnumsDao dao, IManagerAssembler assembler) {
		super.logger = logger;
		super.dao = dao;
		super.assembler = assembler;
	}

	/**
	 * This constructor is used by ioc
	 */
	public DefaultMdoTableAsEnumsManager() {
	}

	public static IMdoTableAsEnumsManager getInstance() {
		return LazyHolder.instance;
	}

	@Override
	public List<IMdoDtoBean> getList(String type, MdoUserContext userContext) throws MdoException {
		MdoTableAsEnumType convertedType = MdoTableAsEnumType.DEFAULT;
		try {
			convertedType = MdoTableAsEnumType.valueOf(type);
		} catch(Exception e) {
			// type is not yet managed by the application
		}
		
		if (convertedType == MdoTableAsEnumType.DEFAULT) {
			return this.getList(convertedType, type, userContext);
		} else {
			return this.getList(convertedType, userContext);
		}
	}

	@Override
	public List<IMdoDtoBean> getList(MdoTableAsEnumType type, MdoUserContext userContext) throws MdoException {
		return this.getList(type, null, userContext);
	}

	private List<IMdoDtoBean> getList(MdoTableAsEnumType enumtype, String type, MdoUserContext userContext) throws MdoException {
		List<IMdoDtoBean> result = new ArrayList<IMdoDtoBean>();
		try {
			List<MdoTableAsEnum> list = enumtype.getList((IMdoTableAsEnumsDao) dao, type);
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
	public List<IMdoDtoBean> getSpecificRounds(MdoUserContext userContext) throws MdoException {
		return this.getList(MdoTableAsEnumType.SPECIFIC_ROUND, userContext);
	}

	@Override
	public List<IMdoDtoBean> getTableTypes(MdoUserContext userContext) throws MdoException {
		return this.getList(MdoTableAsEnumType.TABLE_TYPE, userContext);
	}

	@Override
	public List<IMdoDtoBean> getPrefixTableNames(MdoUserContext userContext) throws MdoException {
		return this.getList(MdoTableAsEnumType.PREFIX_TABLE_NAME, userContext);
	}

	@Override
	public List<IMdoDtoBean> getPrintingInformationAlignments(MdoUserContext userContext) throws MdoException {
		return this.getList(MdoTableAsEnumType.PRINTING_INFORMATION_ALIGNMENT, userContext);
	}

	@Override
	public List<IMdoDtoBean> getPrintingInformationSizes(MdoUserContext userContext) throws MdoException {
		return this.getList(MdoTableAsEnumType.PRINTING_INFORMATION_SIZE, userContext);
	}

	@Override
	public List<IMdoDtoBean> getPrintingInformationParts(MdoUserContext userContext) throws MdoException {
		return this.getList(MdoTableAsEnumType.PRINTING_INFORMATION_PART, userContext);
	}

	@Override
	public List<IMdoDtoBean> getUserRoles(MdoUserContext userContext) throws MdoException {
		return this.getList(MdoTableAsEnumType.USER_ROLE, userContext);
	}

	@Override
	public List<IMdoDtoBean> getUserTitles(MdoUserContext userContext) throws MdoException {
		return this.getList(MdoTableAsEnumType.USER_TITLE, userContext);
	}

	@Override
	public List<IMdoDtoBean> getCategories(MdoUserContext userContext) throws MdoException {
		return this.getList(MdoTableAsEnumType.CATEGORY, userContext);
	}

	@Override
	public List<IMdoDtoBean> getProductSpecialCodes(MdoUserContext userContext) throws MdoException {
		return this.getList(MdoTableAsEnumType.PRODUCT_SPECIAL_CODE, userContext);
	}

	@Override
	public List<IMdoDtoBean> getProductParts(MdoUserContext userContext) throws MdoException {
		return this.getList(MdoTableAsEnumType.PRODUCT_PART, userContext);
	}

	@Override
	public List<IMdoDtoBean> getValueAddedTaxes(MdoUserContext userContext) throws MdoException {
		return this.getList(MdoTableAsEnumType.VALUE_ADDED_TAX, userContext);
	}

	@Override
	public List<IMdoDtoBean> getCashings(MdoUserContext userContext) throws MdoException {
		return this.getList(MdoTableAsEnumType.CASHING_TYPE, userContext);
	}

	@Override
	public void processList(IAdministrationManagerViewBean viewBean, MdoUserContext userContext, boolean... lazy) throws MdoBusinessException {
		IMdoTableAsEnumsDao dao = (IMdoTableAsEnumsDao) super.getDao();
		List<IMdoDtoBean> list = new ArrayList<IMdoDtoBean>();
		List<String> existingTypes = new ArrayList<String>();
		List<?> types;
		try {
			types = dao.findAllTypes();
		} catch (MdoException e) {
			logger.error("message.error.administration.business.find.all", e);
			throw new MdoBusinessException("message.error.administration.business.find.all", e);
		}
		for (Iterator<?> iterator = types.iterator(); iterator.hasNext();) {
			String type = (String) iterator.next();
			MdoTableAsEnumDto bean = new MdoTableAsEnumDto();
			bean.setType(type);
			list.add(bean);
			
			if (!existingTypes.contains(type)) {
				existingTypes.add(type);
			}
		}
		viewBean.setList(list);
		((MdoTableAsEnumsManagerViewBean) viewBean).setExistingTypes(existingTypes);
	}

	@Override
	public IMdoDtoBean save(IMdoDtoBean dtoBean, MdoUserContext userContext) throws MdoBusinessException {

		// Generate the key label by concatenating Type, Name, and Order.
		MdoTableAsEnumDto castedDtoBean = (MdoTableAsEnumDto) dtoBean;
		StringBuilder languageKeyLabel = new StringBuilder(castedDtoBean.getType());
		languageKeyLabel.append(IMdoTableAsEnumsManager.LANGUAGE_KEY_LABEL_SEPARATOR).append(castedDtoBean.getName())
		.append(IMdoTableAsEnumsManager.LANGUAGE_KEY_LABEL_SEPARATOR).append(castedDtoBean.getOrder());
		castedDtoBean.setLanguageKeyLabel(languageKeyLabel.toString());
		
		return super.save(dtoBean, userContext);
	}

	@Override
	public IMdoDtoBean findByTypeAndName(String type, String name, MdoUserContext userContext) throws MdoBusinessException {
		IMdoDtoBean result = null;
		try {
			IMdoDaoBean bean = (IMdoDaoBean) ((IMdoTableAsEnumsDao) dao).findByUniqueKey(new String[] {type, name}, true);
			if (bean != null) {
				result = assembler.marshal(bean, userContext);
			}
		} catch (MdoException e) {
			logger.error("message.error.administration.business.enum.find.by.type.name", new Object[] {type, name}, e);
			throw new MdoBusinessException("message.error.administration.business.enum.find.by.type.name", new Object[] {type, name}, e);
		}
		return result;
	}
}
