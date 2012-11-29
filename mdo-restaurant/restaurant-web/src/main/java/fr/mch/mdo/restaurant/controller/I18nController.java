package fr.mch.mdo.restaurant.controller;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.mch.mdo.restaurant.ui.forms.I18nKeyArgsForm;


/**
 * This class is a controller that serves client query in order to find message by key.
 * @author m.ma
 *
 */
@Controller
@RequestMapping("/i18n")
public class I18nController extends AbstractController {

	/**
	 * Default method to be called in order to provide map of i18n key-value pairs.
	 * This is for HTTP POST method with only keys with or without args.
	 * This is useful when the length of request parameters is too long.
	 * @param form the form that contains keys and args.
	 * @param locale the current locale.
	 * @return a map of i18n key-value pairs.
	 */
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public Map<Object, Object> i18n(@RequestBody I18nKeyArgsForm form, Locale locale) {

		Properties properties = new Properties();
		
		// List of keys part
		for (String key : form.getKeyArgsMap().keySet()) {
			String value = messageSource.getMessage(key, form.getKeyArgsMap().get(key), key, locale);
			properties.put(key, value);
		}

		return properties;
	}

}
