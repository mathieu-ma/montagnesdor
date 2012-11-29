package fr.mch.mdo.restaurant.ui.forms;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used as container for client request to query message by key.
 * 
 * @author m.ma
 *
 */
public class I18nKeyArgsForm {
	
	/**
	 * The map of key + args. The map key is the key of the label.
	 * The map value is the arguments of the label. The arguments could be null. 
	 */
	private Map<String, String[]> keyArgsMap = new HashMap<String, String[]>();
	
	/**
	 * Get the map of i18n keys/args. 
	 * 
	 * @return the keyArgsMap
	 */
	public Map<String, String[]> getKeyArgsMap() {
		return keyArgsMap;
	}

	/**
	 * Set the map of i18n keys/args.
	 * 
	 * @param keyArgsMap the keyArgsMap to set
	 */
	public void setKeyArgsMap(Map<String, String[]> keyArgsMap) {
		this.keyArgsMap = keyArgsMap;
	}

	@Override
	public String toString() {
		return "I18nKeyArgsForm [keyArgsMap=" + keyArgsMap + "]";
	}
}