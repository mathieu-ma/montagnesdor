package fr.mch.mdo.restaurant.web.struts.actions;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.ioc.spring.WebAdministractionBeanFactory;
import fr.mch.mdo.restaurant.resources.IResources;
import fr.mch.mdo.restaurant.ui.forms.AjaxI18nMessagesForm;

public final class AjaxI18nMessagesWebAction extends AdministrationManagerAction 
{
	/**
     * Default Serial Version UID
     */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public AjaxI18nMessagesWebAction() {
		super(WebAdministractionBeanFactory.getInstance().getLogger(AjaxI18nMessagesWebAction.class.getName()), new AjaxI18nMessagesForm());
	}

	/**
	 * Default method to be called by the Struts Framework.
	 */
	public String execute() throws Exception {
		AjaxI18nMessagesForm thisForm = (AjaxI18nMessagesForm) super.getForm();
		
		// Used for getting data from the outputstream.
		PipedInputStream in = new PipedInputStream();
		// Used for storing data from properties into the inputstream.
		PipedOutputStream out = new PipedOutputStream(in);
		MdoUserContext userContext = (MdoUserContext) super.getForm().getUserContext();
		Locale currentLocale = new Locale(userContext.getCurrentLocale().getLanguageCode());
		// thisForm.getResource() return "xxx.properties" and we want to retrieve xxx
		String filePropertiesBaseName = IResources.class.getPackage().getName() + ".i18n." + thisForm.getResource().substring(0, thisForm.getResource().length()-".properties".length());
		Properties poperties = convertResourceBundleToProperties(ResourceBundle.getBundle(filePropertiesBaseName, currentLocale));
		// Additional properties from global bundle
		poperties.put("error.validation.form.invalids.number", getText("error.validation.form.invalids.number"));
		// Store data into outputstream and transfer into inputstream because of PipedOutputStream.
		poperties.store(out, "comments");
		thisForm.setFileInputStream(in);
		
		out.close();
		
	    return SUCCESS;
	}

	public static Properties convertResourceBundleToProperties(ResourceBundle resource) {
		Properties properties = new Properties();

		Enumeration<String> keys = resource.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			properties.put(key, resource.getString(key));
		}

		return properties;
	}
	
	public static void main(String[] args) {
		
		String resource = "MdoTableAsEnums.properties";
		String filePropertiesBaseName = IResources.class.getPackage().getName() + ".i18n." +resource.substring(0, resource.length()-".properties".length());

		System.out.println(filePropertiesBaseName);
	}
}
