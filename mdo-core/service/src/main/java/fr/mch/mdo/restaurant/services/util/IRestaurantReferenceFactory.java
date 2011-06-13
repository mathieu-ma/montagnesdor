package fr.mch.mdo.restaurant.services.util;

import fr.mch.mdo.restaurant.exception.MdoException;

public interface IRestaurantReferenceFactory
{
	String getReferenceFromValue(String key, String value) throws MdoException;
	
	String getValueFromReference(String key, String reference) throws MdoException;

}
