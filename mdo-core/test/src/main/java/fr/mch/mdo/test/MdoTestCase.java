package fr.mch.mdo.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Comparator;
import java.util.Properties;
import java.util.ResourceBundle;

import junit.framework.TestCase;

import org.apache.commons.beanutils.BeanUtils;

/**
 * This class is abstract because we do not want JUnit to launch this class.
 * 
 * @author Mathieu
 * 
 */
public abstract class MdoTestCase extends TestCase 
{

	public static final String DEFAULT_FAILED_MESSAGE = "Could never be there";

	protected MdoTestCase(String testName) {
		super(testName);
	}

	/**
	 * @param clazz
	 *            used to process Relative properties file path name on
	 *            classpath
	 * @param fileExtension
	 *            extension of the file
	 * @param newFileExtension
	 *            new extension
	 */
	protected void renamePropertiesFileExtension(Class<?> clazz, String fileExtension, String newFileExtension) {
		String relativeFilePathName = clazz.getName().replace('.', '/') + fileExtension;
		renamePropertiesFileExtension(relativeFilePathName, newFileExtension);
	}

	/**
	 * @param clazz
	 *            used to process Relative properties file path name on
	 *            classpath
	 * @param fileExtension
	 *            extension of the file
	 * @param newFileExtension
	 *            new extension
	 */
	protected void renamePropertiesFileExtension(String relativeFilePathName, String newFileExtension) {
		URL fileUrl = null;
		while ((fileUrl = MdoTestCase.class.getClassLoader().getResource(relativeFilePathName)) != null) {
			File file = null;
			URI fileUri = null;
			try {
				fileUri = fileUrl.toURI();
			} catch (Exception e) {
				fail("Could not get the file URI " + fileUri);
			}
			if (!"jar".equals(fileUri.getScheme())) {
				try {
					file = new File(fileUri);
				} catch (Exception e) {
					fail("Could not get the file from URI " + fileUri + "==>" + e);
				}
				// It could be happened that there is more than one properties
				// files because of classpath
				String absoluteFilePathName = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(".")) + newFileExtension;
				File newFile = new File(absoluteFilePathName);
				assertTrue("Check the file " + file.getAbsolutePath(), file.exists());
				assertTrue("Check rename properties file: " + file.getAbsolutePath() + " to " + newFile.getAbsolutePath(), file.renameTo(newFile));
				// Refresh resources
				ResourceBundle.clearCache();
				// break;
			} else {
				break;
			}
		}
	}

	protected File getFileFromClassPath(final Class<?> clazz, final String relativeFilePathName) {
		URL fileUrl = clazz.getResource(relativeFilePathName);

		if (fileUrl == null) {
			fileUrl = clazz.getClassLoader().getResource(relativeFilePathName);
		}

		assertNotNull("The file url must not be null", fileUrl);

		File file = null;
		try {
			file = new File(fileUrl.toURI());
		} catch (URISyntaxException e) {
			fail("Could not get the file from URL");
		}
		assertNotNull("The file must not be null", file);
		return file;
	}

	protected Properties getProperties(final Class<?> clazz) {
		// Relative properties file path name on classpath
		String fileExtension = ".properties";
		String relativeFilePathName = clazz.getName().replace('.', '/') + fileExtension;
		return this.getProperties(clazz, relativeFilePathName);
	}

	protected Properties getProperties(final Class<?> clazz, final String relativeFilePathName) {
		Properties result = new Properties();

		File file = getFileFromClassPath(clazz, relativeFilePathName);

		String absoluteFilePathName = file.getAbsolutePath();
		BufferedReader fileBufferedReader = null;
		try {
			fileBufferedReader = new BufferedReader(new FileReader(file));
			// Load and append new properties from properties file
			try {
				result.load(fileBufferedReader);
			} catch (IOException e) {
				fail("Could not load the properties file " + absoluteFilePathName);
			} catch (Exception e) {
				fail("Unexpected exception for loading file " + absoluteFilePathName);
			}
		} catch (FileNotFoundException e) {
			fail("File not found " + absoluteFilePathName);
		} catch (Exception e) {
			fail("Unexpected exception for reading file " + absoluteFilePathName);
		} finally {
			try {
				fileBufferedReader.close();
			} catch (IOException e) {
				fail("Could not close the properties file " + absoluteFilePathName);
			}
		}
		return result;
	}

	protected void storeProperties(final Class<?> clazz, final String relativeFilePathName, final Properties properties) {

		File file = getFileFromClassPath(clazz, relativeFilePathName);

		String absoluteFilePathName = file.getAbsolutePath();
		// Save new properties in properties file
		BufferedWriter fileBufferedWrite = null;
		try {
			fileBufferedWrite = new BufferedWriter(new FileWriter(file));
			try {
				properties.store(fileBufferedWrite, null);
			} catch (IOException e) {
				fail("Could not save the properties file " + absoluteFilePathName);
			}
		} catch (IOException e) {
			fail("File not found " + absoluteFilePathName);
		} finally {
			try {
				fileBufferedWrite.close();
			} catch (IOException e) {
				fail("Could not close the properties file " + absoluteFilePathName);
			}
		}
		// Refresh resources
		ResourceBundle.clearCache();
	}

	/**
	 * @param relativeFilePathName
	 *            Relative properties file path name on classpath
	 * @param newProperties
	 *            new properties to add or remove
	 * @param isRemoved
	 *            do we remove ?
	 * @return the backup updated properties
	 */
	protected Properties updatePropertiesFile(final String relativeFilePathName, final Properties newProperties, final boolean isRemoved) {

		Properties result = new Properties();

		Properties properties = this.getProperties(MdoTestCase.class, relativeFilePathName);

		// Add or remove new properties
		if (newProperties != null) {
			for (Object key : newProperties.keySet()) {
				Object oldValue = null;
				if (isRemoved) {
					oldValue = properties.remove(key);
				} else {
					oldValue = properties.put(key, newProperties.get(key));
				}
				if (oldValue != null) {
					result.put(key, oldValue);
				}
			}
		}
		// Save new properties in properties file
		storeProperties(MdoTestCase.class, relativeFilePathName, properties);

		return result;
	}

	/**
	 * @param clazz
	 *            used to process Relative properties file path name on
	 *            classpath
	 * @param newProperties
	 *            new properties to add or remove
	 * @param isRemoved
	 *            do we remove ?
	 * @return the updated properties
	 */
	protected Properties updatePropertiesFile(Class<?> clazz, Properties newProperties, boolean isRemoved) {
		// Relative properties file path name on classpath
		String fileExtension = ".properties";
		String relativeFilePathName = clazz.getName().replace('.', '/') + fileExtension;
		return updatePropertiesFile(relativeFilePathName, newProperties, isRemoved);
	}

	/**
	 * This is the main method to invoke private/public static/instance methods
	 * 
	 * @param targetInstance
	 *            if null then call static method else call instance method by
	 *            JVM
	 * @param clazz
	 *            the method class
	 * @param methodName
	 *            the method name
	 * @param argClasses
	 *            the type of parameters
	 * @param argObjects
	 *            the instances of parameters types
	 * @return null if the method return void
	 * @throws InvocationTargetException
	 *             when Invocation Target Exception occurs
	 */
	protected Object invokeMethod(Object targetInstance, Class<?> clazz, String methodName, Class<?>[] argClasses, Object[] argObjects) throws InvocationTargetException {
		Object result = null;
		if (clazz != null) {
			try {
				Method method = clazz.getDeclaredMethod(methodName, argClasses);
				method.setAccessible(true);
				result = method.invoke(targetInstance, argObjects);
			} catch (NoSuchMethodException e) {
				// Should happen only rarely, because most times the
				// specified method should exist. If it does happen, just let
				// the test fail so the programmer can fix the problem.
				throw new InvocationTargetException(e, e.getMessage());
			} catch (SecurityException e) {
				// Should happen only rarely, because the setAccessible(true)
				// should be allowed in when running unit tests. If it does
				// happen, just let the test fail so the programmer can fix
				// the problem.
				throw new InvocationTargetException(e, e.getMessage());
			} catch (IllegalAccessException e) {
				// Should never happen, because setting accessible flag to
				// true. If setting accessible fails, should throw a security
				// exception at that point and never get to the invoke. But
				// just in case, wrap it in a TestFailedException and let a
				// human figure it out.
				throw new InvocationTargetException(e, e.getMessage());
			} catch (IllegalArgumentException e) {
				// Should happen only rarely, because usually the right
				// number and types of arguments will be passed. If it does
				// happen, just let the test fail so the programmer can fix
				// the problem.
				throw new InvocationTargetException(e, e.getMessage());
			} catch (InvocationTargetException e) {
				// Should happen every times the invoked method throw an
				// exception.
				throw new InvocationTargetException(e.getTargetException(), e.getTargetException().getMessage());
			}
		}
		return result;
	}

	/**
	 * This method invokes private/public instance methods
	 * 
	 * @param targetInstance
	 *            if null then return null else call instance method by JVM
	 * @param methodName
	 *            the method name
	 * @param argClasses
	 *            the type of parameters
	 * @param argObjects
	 *            the instances of parameters types
	 * @return null if the method return void
	 * @throws InvocationTargetException
	 *             when Invocation Target Exception occurs
	 */
	protected Object invokeInstanceMethod(Object targetInstance, String methodName, Class<?>[] argClasses, Object[] argObjects) throws InvocationTargetException {
		if (targetInstance != null) {
			return invokeMethod(targetInstance, targetInstance.getClass(), methodName, argClasses, argObjects);
		}
		return null;
	}

	/**
	 * This method invokes private/public static methods
	 * 
	 * @param targetClass
	 *            call static method of this targetClass parameter
	 * @param methodName
	 *            the method name
	 * @param argClasses
	 *            the type of parameters
	 * @param argObjects
	 *            the instances of parameters types
	 * @return null if the method return void
	 * @throws InvocationTargetException
	 *             when Invocation Target Exception occurs
	 */
	protected Object invokeStaticMethod(Class<?> targetClass, String methodName, Class<?>[] argClasses, Object[] argObjects) throws InvocationTargetException {
		return invokeMethod(null, targetClass, methodName, argClasses, argObjects);
	}

	/**
	 * This inner class is used to reload an already loaded class
	 * 
	 * @author Mathieu
	 * 
	 */
	protected final class MdoTestClassLoader extends ClassLoader {

		/** the class to be reloaded */
		private Class<?> classToBeReloaded = null;

		@Override
		public java.lang.Class<?> loadClass(String name) throws ClassNotFoundException {

			// This method is call recursively or in the loop by the super
			// ClassLoader

			// Call the super method
			Class<?> clazz = super.loadClass(name);
			if (classToBeReloaded == null || !classToBeReloaded.getName().equals(name)) {
				// If the class name and the name is different to the one of
				// classToBeReloaded
				// then return the super result
				return clazz;
			}

			InputStream input = null;
			ByteArrayOutputStream buffer = null;
			// At this point the class name is equal to the one of
			// classToBeReloaded
			try {
				// Get the class file and convert to an InputStream
				input = clazz.getResourceAsStream(clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1) + ".class");
				buffer = new ByteArrayOutputStream();

				int data = input.read();
				while (data != -1) {
					buffer.write(data);
					data = input.read();
				}

				// Get the data of classToBeReloaded class file
				byte[] classData = buffer.toByteArray();

				// Call the super method to refresh the class
				clazz = defineClass(clazz.getName(), classData, 0, classData.length);

			} catch (Exception e) {
				fail(e.toString());
			} finally {
				try {
					input.close();
				} catch (Exception e) {
					fail("Could not close the input stream file " + clazz.getSimpleName());
				}
				try {
					buffer.close();
				} catch (Exception e) {
					fail("Could not close the output stream file " + clazz.getSimpleName());
				}
			}
			// The new refreshed classToBeReloaded class
			return clazz;
		}

		/**
		 * This method is a convenient method to reload a class
		 * 
		 * @param classToBeReloaded
		 *            the class to be reloaded
		 * @return the reloaded class
		 * @throws ClassNotFoundException
		 *             if Class Not Found Exception occurs
		 */
		public Class<?> reloadClass(Class<?> classToBeReloaded) throws ClassNotFoundException {
			this.classToBeReloaded = classToBeReloaded;
			return loadClass(classToBeReloaded.getName());
		}
	}

	/**
	 * This method is a convenient method for subclass to perform a class
	 * reloading
	 * 
	 * @param classToBeReloaded
	 *            the class to be reloaded
	 * @return the new instance of the classToBeReloaded class
	 */
	protected Class<?> reloadClass(Class<?> classToBeReloaded) {
		Class<?> result = null;
		// Instance of new custom class loader
		MdoTestClassLoader myClassLoader = new MdoTestClassLoader();
		try {
			// Reload the class in the new class loader
			// but this doesn't load yet the class: we have to call new instance
			// in order to really reload the class
			Class<?> clazz = (Class<?>) myClassLoader.reloadClass(classToBeReloaded);
			assertNotNull("The class to be reloaded " + classToBeReloaded + " must not be null", clazz);
			result = clazz;
		} catch (Exception e) {
			fail(e.getMessage());
		}
		return result;
	}

	protected Object getField(Object object, String fieldName) {
		return this.getOrSetField(object, fieldName);
	}

	protected Object setField(Object object, String fieldName, Object value) {
		return this.getOrSetField(object, fieldName, value);
	}

	private Object getOrSetField(Object object, String fieldName, Object... value) {
		Object result = value.length == 1 ? value[0] : null;

		if (object == null) {
			fail("Could not access to " + fieldName + " in null object ");
		}
		Field field = null;
		boolean noSuchField = false;
		Class<?> clazz = object.getClass();
		if (object instanceof Class<?>) {
			clazz = (Class<?>) object;
		}
		do {
			try {
				field = clazz.getDeclaredField(fieldName);
				noSuchField = false;
			} catch (SecurityException e) {
				noSuchField = true;
				// fail("Security exception when getting " + fieldName
				// + " field in class " + clazz + ": " + e);
			} catch (NoSuchFieldException e) {
				noSuchField = true;
				// fail("Could not find " + fieldName + " field in class " +
				// clazz
				// + ": " + e);
			}
			if (clazz.getName().equals(Object.class.getName())) {
				fail("Could not find " + fieldName + " field in any super classes of class " + clazz);
			}
			// Try to find the field in the super class
			clazz = clazz.getSuperclass();
		} while (noSuchField);

		try {
			assertNotNull("Field found", field);
			field.setAccessible(true);
			if (value.length == 1) {
				field.set(object, result);
			} else {
				result = field.get(object);
			}
		} catch (IllegalAccessException e) {
			fail("Could not access to " + fieldName + " in object " + e);
		} catch (Exception e) {
			fail("Unexpected exception: " + e);
		}
		return result;
	}

	protected class MdoStringPropertiesComparator<T> implements Comparator<T> {

		String[] properties;

		public MdoStringPropertiesComparator(String... properties) {
			this.properties = properties;
		}

		@Override
		public int compare(T o1, T o2) {
			int result = 0;
			if (o1 == null && o2 == null) {
			} else if (o1 == null) {
				result = -1;
			} else if (o2 == null) {
				result = 1;
			} else {
				for (String property : properties) {
					if (result == 0) {
						String value1 = this.getProperty(o1, property);
						String value2 = this.getProperty(o1, property);
						if (value1 != null) {
							result = value1.compareTo(value2);
						} else if (value2 != null) {
							result = -1;
						}
					}
				}
			}
			return result;
		}

		private String getProperty(Object o, String property) {
			String result = null;
			try {
				result = BeanUtils.getProperty(o, property);
			} catch (Exception e) {
				fail(MdoTestCase.DEFAULT_FAILED_MESSAGE + ": " + e.getMessage());
			}
			return result;
		}

	}
}
