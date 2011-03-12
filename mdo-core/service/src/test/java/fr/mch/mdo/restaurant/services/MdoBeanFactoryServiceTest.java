package fr.mch.mdo.restaurant.services;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Properties;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.IocBeanName;
import fr.mch.mdo.restaurant.ioc.IBeanFactory;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;
import fr.mch.mdo.restaurant.services.util.UtilsImpl;
import fr.mch.mdo.test.MdoTestCase;

public class MdoBeanFactoryServiceTest extends MdoTestCase 
{
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public MdoBeanFactoryServiceTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(MdoBeanFactoryServiceTest.class);
	}

	private IBeanFactory reloadAndGetInstance() {
		// Get the class MdoBeanFactoryService
		Class<?> MdoBeanFactoryServiceClass = MdoBeanFactoryService.class;
		// Get all inner classes
		Class<?>[] classes = MdoBeanFactoryServiceClass.getDeclaredClasses();
		// Reload the class MdoBeanFactoryService.InitializeOnDemandHolder.class
		// in order to take into account the
		// new configuration file
		Class<?> clazz = super.reloadClass(classes[0]);
		IBeanFactory beanFactory = (IBeanFactory) super.getField(clazz, "instance");
		return beanFactory;
	}

	public void testGetInstance() {
		// Test without MdoBeanFactoryService.properties: remove all files
		// MdoBeanFactoryService.properties in the class path(2 files)
		Class<?> relativePropertiesFileName = MdoBeanFactoryService.class;
		String fileExtension = ".properties";
		String backupFileExtension = ".backup";

		System.out.println("******************* START test without properties file *******************");
		// Rename
		super.renamePropertiesFileExtension(relativePropertiesFileName, fileExtension, backupFileExtension);
		try {
			IBeanFactory beanFactory = reloadAndGetInstance();
			assertNotNull("The bean factory could not be null", beanFactory);
			assertTrue("The bean factory must be the default one MdoBeanFactoryServiceDefault", beanFactory instanceof MdoBeanFactoryServiceDefault);
			// Get the protected field factory
			Field factory = null;
			try {
				factory = MdoBeanFactoryServiceDefault.class.getDeclaredField("factory");
			} catch (SecurityException e) {
				fail("Security exception when getting field in class " + MdoBeanFactoryServiceDefault.class + ": " + e);
			} catch (NoSuchFieldException e) {
				fail("Could not find field in class " + MdoBeanFactoryServiceDefault.class + ": " + e);
			}
			assertNotNull("Field found", factory);
			factory.setAccessible(true);

			try {
				assertTrue("Field must be an HashMap", factory.get(beanFactory) instanceof HashMap<?, ?>);
				HashMap<?, ?> factoryMap = (HashMap<?, ?>) factory.get(beanFactory);
				assertNotNull("This factory must contain a not null BEAN_LOG_NAME", factoryMap.get(IocBeanName.BEAN_LOG_NAME));
				assertTrue("The BEAN_LOG_NAME must be instance of LoggerServiceImpl", factoryMap.get(IocBeanName.BEAN_LOG_NAME) instanceof LoggerServiceImpl);
				assertNotNull("This factory must contain a not null BEAN_UTILS_NAME", factoryMap.get(IocBeanName.BEAN_UTILS_NAME));
				assertTrue("The BEAN_UTILS_NAME must be instance of UtilsImpl", factoryMap.get(IocBeanName.BEAN_UTILS_NAME) instanceof UtilsImpl);
			} catch (IllegalArgumentException e) {
				fail("Could not get the instance field factory " + e);
			} catch (IllegalAccessException e) {
				fail("Could not get the instance field factory " + e);
			}
		} finally {
			// Rename back
			super.renamePropertiesFileExtension(relativePropertiesFileName, backupFileExtension, fileExtension);
		}
		System.out.println("******************* END test without properties file *******************");

		System.out.println("******************* START test without key MdoBeanFactoryService.MDO_BEAN_FACTORY_CLASS_KEY in properties file *******************");
		Properties newProperties = new Properties();
		// Remove the key MdoBeanFactoryService.MDO_BEAN_FACTORY_CLASS_KEY in
		// the properties file
		String key = MdoBeanFactoryService.MDO_BEAN_FACTORY_CLASS_KEY;
		String value = "";
		newProperties.put(key, value);
		// Update and backup the property to be removed
		newProperties = super.updatePropertiesFile(MdoBeanFactoryService.class, newProperties, true);
		try {
			IBeanFactory beanFactory = reloadAndGetInstance();
			assertNotNull("The bean factory could not be null", beanFactory);
			assertTrue("The bean factory must be the default one MdoBeanFactoryServiceDefault", beanFactory instanceof MdoBeanFactoryServiceDefault);

			assertNotNull("The bean could not be null", beanFactory);

		} finally {
			// Restore the backup property
			super.updatePropertiesFile(MdoBeanFactoryService.class, newProperties, false);
		}
		System.out.println("******************* END test without key MdoBeanFactoryService.MDO_BEAN_FACTORY_CLASS_KEY in properties file *******************");

		System.out.println("******************* START test with key value in properties file that is not a default class *******************");
		newProperties = new Properties();
		// Change value of the key
		// MdoBeanFactoryService.MDO_BEAN_FACTORY_CLASS_KEY in the properties
		// file
		key = MdoBeanFactoryService.MDO_BEAN_FACTORY_CLASS_KEY;
		// Key Value is now
		// "fr.mch.mdo.restaurant.services.MdoBeanFactoryServiceNullForTesting"
		value = MdoBeanFactoryServiceDefaultNullForTesting.class.getName();
		newProperties.put(key, value);
		// Update and backup the property to be removed
		newProperties = super.updatePropertiesFile(MdoBeanFactoryService.class, newProperties, false);
		try {
			IBeanFactory instance = reloadAndGetInstance();
			assertTrue("The instance must be MdoBeanFactoryServiceDefaultNullForTesting", instance instanceof MdoBeanFactoryServiceDefaultNullForTesting);
			assertNull("Any bean must be null", instance.getBean(null));
		} finally {
			// Restore the backup property
			super.updatePropertiesFile(MdoBeanFactoryService.class, newProperties, false);
		}
		System.out.println("******************* END test with key value in properties file that is not a default class *******************");

		System.out.println("******************* START test with key value in properties file which class does not exist *******************");
		newProperties = new Properties();
		// Change value of the key
		// MdoBeanFactoryService.MDO_BEAN_FACTORY_CLASS_KEY in the properties
		// file
		key = MdoBeanFactoryService.MDO_BEAN_FACTORY_CLASS_KEY;
		// Key Value is now "toto"
		value = "toto";
		newProperties.put(key, value);
		// Update and backup the property to be removed
		newProperties = super.updatePropertiesFile(MdoBeanFactoryService.class, newProperties, false);
		try {
			reloadAndGetInstance();
			fail("Instruction could not cover here");
		} catch (Error e) {
			assertTrue("Exception In Initializer Error", e.getCause() instanceof ClassNotFoundException);
		} finally {
			// Restore the backup property
			super.updatePropertiesFile(MdoBeanFactoryService.class, newProperties, false);
		}
		System.out.println("******************* END test with key value in properties file which class does not exist *******************");

		System.out.println("******************* START test with key value in properties file which class constructor has argument *******************");
		newProperties = new Properties();
		// Change value of the key
		// MdoBeanFactoryService.MDO_BEAN_FACTORY_CLASS_KEY in the properties
		// file
		key = MdoBeanFactoryService.MDO_BEAN_FACTORY_CLASS_KEY;
		// Key Value is now
		// "fr.mch.mdo.restaurant.services.MdoBeanFactoryServiceForceInstantiationExceptionForTesting"
		value = "fr.mch.mdo.restaurant.services.MdoBeanFactoryServiceForceInstantiationExceptionForTesting";
		newProperties.put(key, value);
		// Update and backup the property to be removed
		newProperties = super.updatePropertiesFile(MdoBeanFactoryService.class, newProperties, false);
		try {
			reloadAndGetInstance();
			fail("Instruction could not cover here");
		} catch (Error e) {
			assertTrue("Exception In Initializer Error", e.getCause() instanceof InstantiationException);
		} finally {
			// Restore the backup property
			super.updatePropertiesFile(MdoBeanFactoryService.class, newProperties, false);
		}
		System.out.println("******************* END test with key value in properties file which class constructor has argument *******************");

		System.out.println("******************* START test with key value in properties file which class constructor is private *******************");
		newProperties = new Properties();
		// Change value of the key
		// MdoBeanFactoryService.MDO_BEAN_FACTORY_CLASS_KEY in the properties
		// file
		key = MdoBeanFactoryService.MDO_BEAN_FACTORY_CLASS_KEY;
		// Key Value is now
		// "fr.mch.mdo.restaurant.services.MdoBeanFactoryServiceForceIllegalAccessExceptionForTesting"
		value = "fr.mch.mdo.restaurant.services.MdoBeanFactoryServiceDefaultForceIllegalAccessExceptionForTesting";
		newProperties.put(key, value);
		// Update and backup the property to be removed
		newProperties = super.updatePropertiesFile(MdoBeanFactoryService.class, newProperties, false);
		try {
			reloadAndGetInstance();
			fail("Instruction could not cover here");
		} catch (Error e) {
			assertTrue("Exception In Initializer Error", e.getCause() instanceof IllegalAccessException);
		} finally {
			// Restore the backup property
			super.updatePropertiesFile(MdoBeanFactoryService.class, newProperties, false);
		}
		System.out.println("******************* END test with key value in properties file which class constructor is private *******************");
	}
}
