package fr.mch.mdo.restaurant.services.util;

import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.exception.MdoTechnicalException;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class RestaurantReferenceFactory implements IRestaurantReferenceFactory
{
	private static ILogger logger = LoggerServiceImpl.getInstance().getLogger(RestaurantReferenceFactory.class.getName());

	/**
	 * This class is only used for Singleton lazy initialization
	 * 
	 * @author Mathieu
	 * 
	 */
	private static class InitializeOnDemandHolder
	{
		/** Singleton */
		private static IRestaurantReferenceFactory instance;
		static {
			logger.info("Instantiating the class " + RestaurantReferenceFactory.class.getName());
			instance = new RestaurantReferenceFactory();
			logger.info(RestaurantReferenceFactory.class.getName() + " class instantiated");
		}
	}

	public static IRestaurantReferenceFactory getInstance() {
		return InitializeOnDemandHolder.instance;
	}

	private byte[] encrypt(String mdKey, String message) throws Exception {
		final MessageDigest md = MessageDigest.getInstance("md5");
		final byte[] digestOfPassword = md.digest(mdKey.getBytes("UTF-8"));
		final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
		for (int j = 0, k = 16; j < 8;) {
			keyBytes[k++] = keyBytes[j++];
		}

		final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
		final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
		final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key, iv);

		final byte[] plainTextBytes = message.getBytes("UTF-8");
		final byte[] cipherText = cipher.doFinal(plainTextBytes);

		return cipherText;
	}

	private String decrypt(String mdKey, byte[] message) throws Exception {
		final MessageDigest md = MessageDigest.getInstance("md5");
		final byte[] digestOfPassword = md.digest(mdKey.getBytes("UTF-8"));
		final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
		for (int j = 0, k = 16; j < 8;) {
			keyBytes[k++] = keyBytes[j++];
		}

		final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
		final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
		final Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		decipher.init(Cipher.DECRYPT_MODE, key, iv);

		final byte[] plainText = decipher.doFinal(message);

		return new String(plainText, "UTF-8");
	}

	private static final String HEXES = "0123456789ABCDEF";
	public static String byteArrayToHexaString(byte[] raw) {
		if (raw == null) {
			return null;
		}
		final StringBuilder hex = new StringBuilder(2 * raw.length);
		for (final byte b : raw) {
			hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt((b & 0x0F)));
		}
		return hex.toString();
	}
	public static byte[] hexaStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}

	@Override
	public String getReferenceFromValue(String key, String value) throws MdoTechnicalException {
		String result = null;
		if (key == null) {
			key = "";
		}
		if (value == null) {
			value = "";
		}
		try {
			byte[] data = this.encrypt(key, value);
			result = byteArrayToHexaString(data);
		} catch (Exception e) {
			logger.error("Could not generate reference from value " + value + " with key " + key, e);
			throw new MdoTechnicalException("Could not generate reference from value " + value + " with key " + key, e);
		}
		return result;
	}

	@Override
	public String getValueFromReference(String key, String reference) throws MdoTechnicalException {
		String result = null;
		if (key == null) {
			key = "";
		}
		if (reference == null) {
			reference = "";
		}
		try {
			result = this.decrypt(key, hexaStringToByteArray(reference));
		} catch (Exception e) {
			logger.error("Could not generate value from reference " + reference + " with key " + key, e);
			throw new MdoTechnicalException("Could not generate value from reference " + reference + " with key " + key, e);
		}
		return result;
	}
}
