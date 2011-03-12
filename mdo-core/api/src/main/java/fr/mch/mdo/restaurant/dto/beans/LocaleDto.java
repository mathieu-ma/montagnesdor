package fr.mch.mdo.restaurant.dto.beans;

import fr.mch.mdo.restaurant.beans.MdoDtoBean;

/**
 * This class is a DTO for locale.
 * 
 * @author Mathieu MA sous conrad
 */
public class LocaleDto extends MdoDtoBean 
{
	/**
	 * Default Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	private String languageCode;

	private String displayLanguage;

	/**
	 * @param languageCode
	 *            the languageCode to set
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * @return the languageCode
	 */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * @param displayLanguage
	 *            the displayLanguage to set
	 */
	public void setDisplayLanguage(String displayLanguage) {
		this.displayLanguage = displayLanguage;
	}

	/**
	 * @return the displayLanguage
	 */
	public String getDisplayLanguage() {
		return displayLanguage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((languageCode == null) ? 0 : languageCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		LocaleDto other = (LocaleDto) obj;
		if (languageCode == null) {
			if (other.languageCode != null) {
				return false;
			}
		} else if (!languageCode.equals(other.languageCode)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "LocaleDto [displayLanguage=" + displayLanguage + ", languageCode=" + languageCode + ", id=" + id + "]";
	}
}
