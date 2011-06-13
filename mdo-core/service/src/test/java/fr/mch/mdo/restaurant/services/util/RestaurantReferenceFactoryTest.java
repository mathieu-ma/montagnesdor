package fr.mch.mdo.restaurant.services.util;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.exception.MdoException;
import fr.mch.mdo.test.MdoTestCase;

public class RestaurantReferenceFactoryTest extends MdoTestCase
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public RestaurantReferenceFactoryTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(RestaurantReferenceFactoryTest.class);
	}
	
	public void testGetReferenceFromValue() {
		IRestaurantReferenceFactory restaurantReferenceFactory = RestaurantReferenceFactory.getInstance();

		try {
			String key = "Il était une fois";
			String value = "3 petits cochons";
			String reference = "E5B1F665D9A8386FFEA5862CDE6380A36678540C30DCF26C";
			String newReference = restaurantReferenceFactory.getReferenceFromValue(key, value);

			assertNotNull("Generated Reference not null", reference);
			assertEquals("Check generated reference", reference, newReference);
			
			key = "Il était une fois";
			value = "";
			reference = "69E1262247D50A11";
			newReference = restaurantReferenceFactory.getReferenceFromValue(key, value);
			assertNotNull("Generated Reference not null", reference);
			assertEquals("Check generated reference", reference, newReference);

			key = "Il était une fois";
			value = null;
			reference = "69E1262247D50A11";
			newReference = restaurantReferenceFactory.getReferenceFromValue(key, value);
			assertNotNull("Generated Reference not null", reference);
			assertEquals("Check generated reference", reference, newReference);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}
	
	public void testGetValueFromReference() {
		try {
			String key = "Il était une fois";
			String value = "3 petits cochons";
			String reference = "E5B1F665D9A8386FFEA5862CDE6380A36678540C30DCF26C";
			IRestaurantReferenceFactory restaurantReferenceFactory = RestaurantReferenceFactory.getInstance();
			String newValue = restaurantReferenceFactory.getValueFromReference(key, reference);
			assertNotNull("Value not null", value);
			assertEquals("Check value", value, newValue);
		} catch (MdoException e) {
			fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
		}
	}
	
}
