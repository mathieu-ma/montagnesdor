package fr.mch.mdo.restaurant.web.struts.actions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dto.beans.LocaleDto;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.UserAuthenticationDto;
import fr.mch.mdo.restaurant.dto.beans.UserAuthenticationsManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.UserLocaleDto;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.ioc.spring.WebAdministractionBeanFactory;
import fr.mch.mdo.restaurant.ui.forms.UserAuthenticationsManagerForm;

public class UserAuthenticationsManagerWebAction extends AdministrationManagerAction 
{
	/**
	 * Default Serial Version UID
     */
	private static final long serialVersionUID = 1L;

	public UserAuthenticationsManagerWebAction() {
		super(WebAdministractionBeanFactory.getInstance().getLogger(UserAuthenticationsManagerWebAction.class.getName()), new UserAuthenticationsManagerForm());
		administrationManager = WebAdministractionBeanFactory.getInstance().getUserAuthenticationsManager();
	}

	@Override
	public String form() throws Exception {
		String result = super.form();
		
		processAvailableLanguages();

		return result;
	}

	@Override
	public void validate() {
		super.validate();
		
		if (super.hasErrors()) {
			// In case of validation failed
			// Remove the Language with null id
			removeLocaleBeforeSaving(null);
			processAvailableLanguages();
		}
	}

	@Override
	public String save() {
		
		this.processSave(new String[] {null});
		
		// Return to the list
		return Constants.ACTION_RESULT_AFTER_CUD;

	}

	/**
	 * Process languages list on element which is not already stored in database.
	 * Must be called after super.prepare because we have to use viewBean. 
	 */
	private void processAvailableLanguages() {
		UserAuthenticationDto dtoBean = (UserAuthenticationDto) super.getForm().getDtoBean();
		UserAuthenticationsManagerViewBean viewBean = (UserAuthenticationsManagerViewBean) super.getForm().getViewBean();

		List<LocaleDto> listAll = viewBean.getLanguages();
		List<LocaleDto> availableLanguages = new ArrayList<LocaleDto>(listAll);
		if (dtoBean != null && dtoBean.getLocales() != null) {
			for (IMdoDtoBean language : listAll) {
				for (UserLocaleDto exlcudedBean : dtoBean.getLocales()) {
					if (language.getId() != null && exlcudedBean.getLocale() != null && language.getId().equals(exlcudedBean.getLocale().getId())) {
						availableLanguages.remove(language);
					}
				}
			}
		}

		viewBean.setLanguages(availableLanguages);
	}

	private void processSave(String... languageIdToRemove) {
		if (languageIdToRemove != null && languageIdToRemove.length == 1) {
			removeLocaleBeforeSaving(languageIdToRemove[0]);
		}
		UserAuthenticationDto dtoBean = (UserAuthenticationDto) super.getForm().getDtoBean();
		// Check the list of locales
		if (dtoBean.getLocales() == null || dtoBean.getLocales().isEmpty()) {
			// Add the locale from user Printing Locale
			Set<UserLocaleDto> locales = new HashSet<UserLocaleDto>();
			UserLocaleDto userLocale = new UserLocaleDto();
			userLocale.setLocale(dtoBean.getPrintingLocale());
			userLocale.setUser(dtoBean);
			locales.add(userLocale);
			dtoBean.setLocales(locales);
		}
		super.save();
		// Reload the current user authentication
		MdoUserContext userContext = (MdoUserContext) super.getForm().getUserContext();
		if (dtoBean.getId() != null && dtoBean.getId().equals(userContext.getUserAuthentication().getId())) {
			UserAuthenticationDto newDtoBean = dtoBean; 
			if (dtoBean.getId() !=null) {
				try {
					newDtoBean = (UserAuthenticationDto) this.getAdministrationManager().findByPrimaryKey(dtoBean.getId(), (MdoUserContext) super.getForm().getUserContext());
				} catch (MdoException e) {
					super.addActionError(super.getText("error.action.technical", new String[] {this.getClass().getName(), "form"}));
				}
			}
			userContext.setUserAuthentication(newDtoBean);
		}
	}
	
	private void removeLocaleBeforeSaving(String languageIdToRemove) {
		UserAuthenticationsManagerForm form = (UserAuthenticationsManagerForm) super.getForm();
		UserAuthenticationDto user = (UserAuthenticationDto) form.getDtoBean();
		Set<UserLocaleDto> locales = new HashSet<UserLocaleDto>();
		// Try to remove from user.getLocales() list 2 elements of UserLocaleDto
		// One of id null and one of id equals to languageIdToRemove
		for (UserLocaleDto userLocale : user.getLocales()) {
			if (userLocale.getId() != null && !userLocale.getId().toString().equals(languageIdToRemove)) {
				locales.add(userLocale);
			}
		}
		user.setLocales(locales);
	}
	
	public String removeLanguage() throws Exception {
		String restaurantIdToRemove = super.getRequest().getParameter("method:removeLanguage");
		this.processSave(restaurantIdToRemove);
		this.form();
		return Constants.ACTION_RESULT_AFTER_CUD_LANGUAGE;
	}

	public String addLanguage() throws Exception {
		this.processSave();
		this.form();
		return Constants.ACTION_RESULT_AFTER_CUD_LANGUAGE;
	}

}
