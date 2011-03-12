package fr.mch.mdo.restaurant.services.business.managers.assembler;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.Locale;
import fr.mch.mdo.restaurant.dto.beans.LocaleDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultLocalesAssembler extends AbstractAssembler implements IManagerAssembler, ILoggerBean 
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

	@Override
	public IMdoDtoBean marshal(IMdoDaoBean daoBean, MdoUserContext userContext) {
		LocaleDto dto = null;
		if (daoBean != null) {
			Locale bean = (Locale) daoBean;
			dto = new LocaleDto();
			dto.setId(bean.getId());
			dto.setLanguageCode(bean.getLanguage());
			if (userContext != null && userContext.getCurrentLocale() != null 
					&& userContext.getCurrentLocale().getLanguageCode() != null
					&& bean.getLanguage() != null) {
				java.util.Locale currentUserLocale = new java.util.Locale(userContext.getCurrentLocale().getLanguageCode());
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
}
