package fr.mch.mdo.restaurant.services.business.managers.assembler;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.beans.IMdoDaoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.Locale;
import fr.mch.mdo.restaurant.dao.beans.UserAuthentication;
import fr.mch.mdo.restaurant.dao.beans.UserLocale;
import fr.mch.mdo.restaurant.dto.beans.LocaleDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.UserAuthenticationDto;
import fr.mch.mdo.restaurant.dto.beans.UserLocaleDto;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.utils.IManagerAssembler;

public class DefaultUserLocalesAssembler extends AbstractAssembler implements IManagerAssembler, ILoggerBean 
{
	private ILogger logger;
	private IManagerAssembler localesAssembler;

	private static class LazyHolder {
		private static IManagerAssembler instance = new DefaultUserLocalesAssembler(
				LoggerServiceImpl.getInstance().getLogger(DefaultUserLocalesAssembler.class.getName()));
	}

	private DefaultUserLocalesAssembler(ILogger logger) {
		this.setLogger(logger);
		this.localesAssembler = DefaultLocalesAssembler.getInstance();
	}

	public static IManagerAssembler getInstance() {
		return LazyHolder.instance;
	}

	public DefaultUserLocalesAssembler() {
	}

	public IManagerAssembler getLocalesAssembler() {
		return localesAssembler;
	}

	public void setLocalesAssembler(IManagerAssembler localesAssembler) {
		this.localesAssembler = localesAssembler;
	}

	@Override
	public IMdoDtoBean marshal(IMdoDaoBean daoBean, MdoUserContext userContext) {
		UserLocaleDto dto = null;
		if (daoBean != null) {
			UserLocale bean = (UserLocale) daoBean;
			dto = new UserLocaleDto();
			dto.setId(bean.getId());
			LocaleDto locale = (LocaleDto) localesAssembler.marshal(bean.getLocale(), userContext);
			dto.setLocale(locale);
			UserAuthenticationDto user = null;
			dto.setUser(user);
		}
		return dto;
	}

	@Override
	public IMdoDaoBean unmarshal(IMdoDtoBean dtoBean, IMdoDaoBean... parents) {
		if (dtoBean == null) {
			return null;
		}
		UserLocale bean = new UserLocale();
		UserLocaleDto dto = (UserLocaleDto) dtoBean;
		bean.setId(dto.getId());
		if (parents != null && parents.length == 1) {
			bean.setUser((UserAuthentication) parents[0]);
		}
		Locale locale = (Locale) localesAssembler.unmarshal(dto.getLocale()); 
		bean.setLocale(locale);
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
