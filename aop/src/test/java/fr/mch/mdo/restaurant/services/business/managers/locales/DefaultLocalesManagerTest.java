package fr.mch.mdo.restaurant.services.business.managers.locales;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.security.auth.Subject;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.beans.IMdoBean;
import fr.mch.mdo.restaurant.beans.IMdoDtoBean;
import fr.mch.mdo.restaurant.dao.beans.Locale;
import fr.mch.mdo.restaurant.dto.beans.LocaleDto;
import fr.mch.mdo.restaurant.dto.beans.LocalesManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.UserAuthenticationDto;
import fr.mch.mdo.restaurant.dto.beans.UserLocaleDto;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.restaurant.services.business.managers.DefaultAdministrationManagerTest;
import fr.mch.mdo.restaurant.services.business.managers.IAdministrationManager;
import fr.mch.mdo.test.MdoTestCase;

public class DefaultLocalesManagerTest extends DefaultAdministrationManagerTest 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public DefaultLocalesManagerTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(DefaultLocalesManagerTest.class);
	}

	@Override
	protected IAdministrationManager getInstance() {
		return DefaultLocalesManager.getInstance();
	}

	@Override
	protected IMdoDtoBean createNewBean() {
		return createNewBean(java.util.Locale.ENGLISH.getLanguage());
	}

	@Override
	protected List<IMdoBean> createListBeans() {
		List<IMdoBean> list = new ArrayList<IMdoBean>();
		list.add(createNewBean(java.util.Locale.JAPANESE.getLanguage()));
		list.add(createNewBean(java.util.Locale.CHINESE.getLanguage()));
		return list;
	}

	@Override
	public void doUpdate() {
		String localeCodeToBeUpdated = java.util.Locale.GERMAN.getLanguage();
		try {
			// Create new bean to be updated
			IMdoBean beanToBeUpdated = this.getInstance().insert(createNewBean(localeCodeToBeUpdated), DefaultAdministrationManagerTest.userContext);
			assertTrue("IMdoBean must be instance of " + Locale.class, beanToBeUpdated instanceof LocaleDto);
			LocaleDto castedBean = (LocaleDto) beanToBeUpdated;
			assertNotNull("Locale language must not be null", castedBean.getLanguageCode());
			assertEquals("Locale language must be equals to unique key", localeCodeToBeUpdated, castedBean.getLanguageCode());
			// Update the created bean
			castedBean.setLanguageCode(java.util.Locale.ITALIAN.getLanguage());
			this.getInstance().update(castedBean, DefaultAdministrationManagerTest.userContext);
			// Reload the modified bean
			LocaleDto updatedBean = new LocaleDto();
			updatedBean.setId(castedBean.getId());
			IMdoBean loadedBean = this.getInstance().load(updatedBean, DefaultAdministrationManagerTest.userContext);
			assertTrue("IMdoBean must be instance of " + LocaleDto.class, loadedBean instanceof LocaleDto);
			updatedBean = (LocaleDto) loadedBean;
			assertNotNull("Locale language must not be null", updatedBean.getLanguageCode());
			assertEquals("Locale language must be equals to unique key", java.util.Locale.ITALIAN.getLanguage(), updatedBean.getLanguageCode());
		} catch (Exception e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	@Override
	public void doProcessList() {
		LocalesManagerViewBean viewBean = new LocalesManagerViewBean();
		try {
			this.getInstance().processList(viewBean, DefaultAdministrationManagerTest.userContext);
			assertNotNull("Main list not be null", viewBean.getList());
			assertFalse("Main list not be empty", viewBean.getList().isEmpty());
			assertNotNull("Languages list not be null", viewBean.getLanguages());
			assertFalse("Languages list not be empty", viewBean.getLanguages().isEmpty());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	public void testGetInstance() {
		assertTrue(this.getInstance() instanceof ILocalesManager);
		assertTrue(this.getInstance() instanceof DefaultLocalesManager);
	}

	public void testFindByLanguage() {
		ILocalesManager manager = (ILocalesManager) this.getInstance();
		try {
			String languageCode = "fr";
			LocaleDto bean = (LocaleDto) manager.findByLanguage(languageCode, userContext);
			assertNotNull("Bean is not null", bean);
			assertNotNull("Bean id is not null", bean.getId());
			assertEquals("Bean id is equals to the searched id", languageCode, bean.getLanguageCode());

			languageCode = "nono";
			bean = (LocaleDto) manager.findByLanguage(languageCode, userContext);
			assertNull("Bean is not null", bean);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	public void testGetAvailableLanguages() {
		ILocalesManager manager = (ILocalesManager) this.getInstance();
		java.util.Locale locale = null;
		Map<String, String> map = null;
		try {
			map = manager.getAvailableLanguages(locale);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		assertNotNull("Check Available Languages size", map);
		List<IMdoDtoBean> locales = new ArrayList<IMdoDtoBean>();
		try {
			locales = manager.findAll(userContext);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		Set<String> isoLanguages = new HashSet<String>();
		if (locale == null) {
			locale = java.util.Locale.getDefault();
		}
		for (java.util.Locale isoLanguageCode : java.util.Locale.getAvailableLocales()) {
			isoLanguages.add(isoLanguageCode.getLanguage());
		}
		assertEquals("Check Available Languages size", isoLanguages.size() - locales.size(), map.size());
	}

	public void testGetLanguages() {
		ILocalesManager manager = (ILocalesManager) this.getInstance();
		// Locale null
		java.util.Locale locale = null;
		Map<String, String> map;
		try {
			map = manager.getLanguages(locale);
			checkLanguage(map, java.util.Locale.getDefault());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}

		// Locale does not exist
		locale = new java.util.Locale("titi", "toto");
		try {
			map = manager.getLanguages(locale);
			checkLanguage(map, java.util.Locale.getDefault());
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}

		// Nominal Scenario
		locale = java.util.Locale.FRENCH;
		try {
			map = manager.getLanguages(locale);
			checkLanguage(map, java.util.Locale.FRENCH);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	private void checkLanguage(Map<String, String> map, java.util.Locale selectedLocale) {

		// Check only sorted displayed languages
		String[] values = new String[map.size()];
		int index = 0;
		for (Iterator<String> i = map.keySet().iterator(); i.hasNext();) {
			String key = i.next();
			values[index++] = (map.get(key));
		}
		// Get Available Locales
		ILocalesManager manager = (ILocalesManager) this.getInstance();
		List<IMdoDtoBean> locales = new ArrayList<IMdoDtoBean>();
		try {
			locales = manager.findAll(userContext);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		// Several locales could have the same displayed language
		// So put all displayed languages in a set to have no duplicated
		// displayed languages
		Set<String> languagesSet = new HashSet<String>();
		for (IMdoBean locale : locales) {
			languagesSet.add(new java.util.Locale(((LocaleDto) locale).getLanguageCode()).getDisplayLanguage(selectedLocale));
		}
		// Create sorted arrays by displayed languages
		String[] languages = new String[languagesSet.size()];
		// Sort displayed languages
		languagesSet.toArray(languages);
		Arrays.sort(languages);
		// Check languages
		assertEquals("The 2 arrays languages length must be equals", languages.length, values.length);
		for (index = 0; index < languages.length; index++) {
			assertEquals("Each language must be equals in string natural order", languages[index], values[index]);
		}
	}

	public void testGetLanguageLocales() {
		ILocalesManager manager = (ILocalesManager) this.getInstance();
		try {
			List<LocaleDto> sortedList = manager.getLanguageLocales(userContext);
			assertNotNull("sortedList is not null", sortedList);
			// Check that the list is sorted by Display Language
			Set<String> displayLanguagesSet = new HashSet<String>();
			for (LocaleDto locale : sortedList) {
				// Fill the set in any order
				displayLanguagesSet.add(locale.getDisplayLanguage());
			}
			// Sort the set into list
			List<String> displayLanguagesList = new ArrayList<String>(displayLanguagesSet);
			Collections.sort(displayLanguagesList);
			for (int i = 0; i < sortedList.size(); i++) {
				LocaleDto locale = sortedList.get(i);
				assertEquals("Check Display Language", displayLanguagesList.get(i), locale.getDisplayLanguage());
			}
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}

	public void testFindLocale() {
		ILocalesManager manager = (ILocalesManager) this.getInstance();

		Map<String, String> availableLanguages = null;
		try {
			availableLanguages = manager.getAvailableLanguages(java.util.Locale.getDefault());
			assertNotNull("Check not null availableLanguages", availableLanguages);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}

		LocaleDto dto = null;
		// Locale null
		java.util.Locale currentLocale = null;
		// MdoUserContext null
		MdoUserContext userContextX = null;
		try {
			dto = manager.findLocale(currentLocale, userContextX);
			assertNotNull("Check LocaleDto not null", dto);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
		// MdoUserContext null
		// && currentLocale not in database
		currentLocale = new java.util.Locale(availableLanguages.keySet().iterator().next());
		try {
			dto = manager.findLocale(currentLocale, userContextX);
			assertNotNull("Check LocaleDto not null", dto);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}

		// MdoUserContext not null
		// && dao.findAll() return null
		userContextX = new MdoUserContext(new Subject());
		Object backupDao = super.getField(manager, "dao");
		super.setField(manager, "dao", new LocalesDaoForLocalesManagerTest());
		try {
			dto = manager.findLocale(currentLocale, userContextX);
			assertNotNull("Check LocaleDto not null", dto);
		} catch (Exception e) {
			assertTrue("No dao session: ", e instanceof NullPointerException);
		} finally {
			// Backup dao
			super.setField(manager, "dao", backupDao);
		}

		// MdoUserContext not null
		// && UserAuthenticationDto not null
		UserAuthenticationDto userAuthentication = new UserAuthenticationDto();
		userContextX.setUserAuthentication(userAuthentication);
		try {
			dto = manager.findLocale(currentLocale, userContextX);
			assertNotNull("Check LocaleDto not null", dto);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}

		// MdoUserContext not null
		// && UserAuthenticationDto not null
		// && locales not null && empty
		Set<UserLocaleDto> locales = new HashSet<UserLocaleDto>();
		userAuthentication.setLocales(locales);
		try {
			dto = manager.findLocale(currentLocale, userContextX);
			assertNotNull("Check LocaleDto not null", dto);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}

		// MdoUserContext not null && UserAuthenticationDto not null
		// && locales not null and not empty
		// && UserLocaleDto null
		UserLocaleDto userLocaleDto = null;
		locales.add(userLocaleDto);
		try {
			dto = manager.findLocale(currentLocale, userContextX);
			assertNotNull("Check LocaleDto not null", dto);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}

		// MdoUserContext not null && UserAuthenticationDto not null
		// && locales not null and not empty
		// && UserLocaleDto not null
		userLocaleDto = new UserLocaleDto();
		locales.add(userLocaleDto);
		try {
			dto = manager.findLocale(currentLocale, userContextX);
			assertNotNull("Check LocaleDto not null", dto);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}

		// MdoUserContext not null && UserAuthenticationDto not null
		// && locales not null and not empty
		// && UserLocaleDto not null
		// && LocaleDto not null
		LocaleDto localeDto = new LocaleDto();
		userLocaleDto.setLocale(localeDto);
		try {
			dto = manager.findLocale(currentLocale, userContextX);
			assertNotNull("Check LocaleDto not null", dto);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}

		// MdoUserContext not null && UserAuthenticationDto not null
		// && locales not null and not empty
		// && UserLocaleDto not null
		// && LocaleDto not null
		// && languageCode not null
		String languageCode = "";
		localeDto.setLanguageCode(languageCode);
		try {
			dto = manager.findLocale(currentLocale, userContextX);
			assertNotNull("Check LocaleDto not null", dto);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}

		// MdoUserContext not null && UserAuthenticationDto not null
		// && locales not null and not empty
		// && UserLocaleDto not null
		// && LocaleDto not null
		// && languageCode not null && equals to the current locale
		if (currentLocale == null) {
			languageCode = java.util.Locale.getDefault().getLanguage();
		} else {
			languageCode = currentLocale.getLanguage();
		}
		localeDto.setLanguageCode(languageCode);
		try {
			dto = manager.findLocale(currentLocale, userContextX);
			assertNotNull("Check LocaleDto not null", dto);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}

	}

	private IMdoDtoBean createNewBean(String language) {
		LocaleDto newBean = new LocaleDto();
		newBean.setLanguageCode(language);
		return newBean;
	}
}
