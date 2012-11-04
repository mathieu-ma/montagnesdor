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
	public List<IMdoDtoBean> getList(String type) throws MdoException {
		MdoTableAsEnumType convertedType = MdoTableAsEnumType.DEFAULT;
		try {
			convertedType = MdoTableAsEnumType.valueOf(type);
		} catch(Exception e) {
			// type is not yet managed by the application
		}
		
		if (convertedType == MdoTableAsEnumType.DEFAULT) {
			return this.getList(convertedType, type);
		} else {
			return this.getList(convertedType);
		}
	}

	@Override
	public List<IMdoDtoBean> getList(MdoTableAsEnumType type) throws MdoException {
		return this.getList(type, null);
	}

	private List<IMdoDtoBean> getList(MdoTableAsEnumType enumtype, String type) throws MdoException {
		List<IMdoDtoBean> result = new ArrayList<IMdoDtoBean>();
		try {
			List<MdoTableAsEnum> list = enumtype.getList((IMdoTableAsEnumsDao) dao, type);
			if (list != null) {
				result = assembler.marshal(list);
			}
		} catch (MdoException e) {
			logger.error("message.error.administration.business.find.all", e);
			throw new MdoBusinessException("message.error.administration.business.find.all", e);
		}
		return result;
	}

	
	@Override
	public List<IMdoDtoBean> getSpecificRounds() throws MdoException {
		return this.getList(MdoTableAsEnumType.SPECIFIC_ROUND);
	}

	@Override
	public List<IMdoDtoBean> getTableTypes() throws MdoException {
		return this.getList(MdoTableAsEnumType.TABLE_TYPE);
	}

	@Override
	public List<IMdoDtoBean> getPrefixTableNames() throws MdoException {
		return this.getList(MdoTableAsEnumType.PREFIX_TABLE_NAME);
	}

	@Override
	public List<IMdoDtoBean> getPrintingInformationAlignments() throws MdoException {
		return this.getList(MdoTableAsEnumType.PRINTING_INFORMATION_ALIGNMENT);
	}

	@Override
	public List<IMdoDtoBean> getPrintingInformationSizes() throws MdoException {
		return this.getList(MdoTableAsEnumType.PRINTING_INFORMATION_SIZE);
	}

	@Override
	public List<IMdoDtoBean> getPrintingInformationParts() throws MdoException {
		return this.getList(MdoTableAsEnumType.PRINTING_INFORMATION_PART);
	}

	@Override
	public List<IMdoDtoBean> getUserRoles() throws MdoException {
		return this.getList(MdoTableAsEnumType.USER_ROLE);
	}

	@Override
	public List<IMdoDtoBean> getUserTitles() throws MdoException {
		return this.getList(MdoTableAsEnumType.USER_TITLE);
	}

	@Override
	public List<IMdoDtoBean> getCategories() throws MdoException {
		return this.getList(MdoTableAsEnumType.CATEGORY);
	}

	@Override
	public List<IMdoDtoBean> getProductSpecialCodes() throws MdoException {
		return this.getList(MdoTableAsEnumType.PRODUCT_SPECIAL_CODE);
	}

	@Override
	public List<IMdoDtoBean> getProductParts() throws MdoException {
		return this.getList(MdoTableAsEnumType.PRODUCT_PART);
	}

	@Override
	public List<IMdoDtoBean> getValueAddedTaxes() throws MdoException {
		return this.getList(MdoTableAsEnumType.VALUE_ADDED_TAX);
	}

	@Override
	public List<IMdoDtoBean> getCashings() throws MdoException {
		return this.getList(MdoTableAsEnumType.CASHING_TYPE);
	}

	@Override
	public void processList(IAdministrationManagerViewBean viewBean, boolean... lazy) throws MdoBusinessException {
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
	public IMdoDtoBean save(IMdoDtoBean dtoBean) throws MdoBusinessException {

		// Generate the key label by concatenating Type, Name, and Order.
		MdoTableAsEnumDto castedDtoBean = (MdoTableAsEnumDto) dtoBean;
		StringBuilder languageKeyLabel = new StringBuilder(castedDtoBean.getType());
		languageKeyLabel.append(IMdoTableAsEnumsManager.LANGUAGE_KEY_LABEL_SEPARATOR).append(castedDtoBean.getName())
		.append(IMdoTableAsEnumsManager.LANGUAGE_KEY_LABEL_SEPARATOR).append(castedDtoBean.getOrder());
		castedDtoBean.setLanguageKeyLabel(languageKeyLabel.toString());
		
		return super.save(dtoBean);
	}

	@Override
	public IMdoDtoBean findByTypeAndName(String type, String name) throws MdoBusinessException {
		IMdoDtoBean result = null;
		try {
			IMdoDaoBean bean = (IMdoDaoBean) ((IMdoTableAsEnumsDao) dao).findByUniqueKey(new String[] {type, name}, true);
			if (bean != null) {
				result = assembler.marshal(bean);
			}
		} catch (MdoException e) {
			logger.error("message.error.administration.business.enum.find.by.type.name", new Object[] {type, name}, e);
			throw new MdoBusinessException("message.error.administration.business.enum.find.by.type.name", new Object[] {type, name}, e);
		}
		return result;
	}
}
