package fr.mch.mdo.restaurant.services.business.managers.assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.Locale;
import fr.mch.mdo.restaurant.dto.beans.LocaleDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.ILocalesAssembler;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultLocalesAssembler extends AbstractAssembler implements ILocalesAssembler 
{
	private ILogger logger;

	private static class LazyHolder {
		private static IManagerAssembler instance = new DefaultLocalesAssembler(LoggerServiceImpl.getInstance().getLogger(DefaultLocalesAssembler.class.getName()));
	}

	private DefaultLocalesAssembler(ILogger logger) {
		this.setLogger(logger);
	}

	public static IManagerAssembler getInstance() {
		return LazyHolder.instance;
	}

	public DefaultLocalesAssembler() {
	}

	private void marshal(Collection<IMdoDtoBean> collections, Collection<? extends IMdoBean> collection, String defaultLanguageCode) {
		if (collection != null) {
			for (IMdoBean iMdoBean : collection) {
				collections.add(marshal((IMdoDaoBean) iMdoBean, defaultLanguageCode));
			}
		}
	}

	@Override
	public List<IMdoDtoBean> marshal(List<? extends IMdoBean> list, String defaultLanguageCode) {
		List<IMdoDtoBean> result = new ArrayList<IMdoDtoBean>();
		this.marshal(result, list, defaultLanguageCode);
		return result;
	}

	@Override
	public IMdoDtoBean marshal(IMdoDaoBean daoBean, String defaultLanguageCode) {
		LocaleDto dto = null;
		if (daoBean != null) {
			Locale bean = (Locale) daoBean;
			dto = new LocaleDto();
			dto.setId(bean.getId());
			dto.setLanguageCode(bean.getLanguage());
			if (defaultLanguageCode != null
					&& bean.getLanguage() != null) {
				java.util.Locale currentUserLocale = new java.util.Locale(defaultLanguageCode);
				java.util.Locale currentLocale = new java.util.Locale(bean.getLanguage());
				String displayLanguage = currentLocale.getDisplayLanguage(currentUserLocale);
				dto.setDisplayLanguage(displayLanguage);
			} else {
				logger.warn("message.error.generic.field.null", new Object[] { MdoUserContext.class });
			}
		}
		return dto;
	}

	@Override
	public IMdoDaoBean unmarshal(IMdoDtoBean dtoBean, IMdoDaoBean... parents) {
		if (dtoBean == null) {
			return null;
		}
		Locale bean = new Locale();
		LocaleDto dto = (LocaleDto) dtoBean;
		bean.setId(dto.getId());
		bean.setLanguage(dto.getLanguageCode());
		return bean;
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
	public IMdoDtoBean marshal(IMdoDaoBean daoBean) {
		return this.marshal(daoBean, null);
	}

}
