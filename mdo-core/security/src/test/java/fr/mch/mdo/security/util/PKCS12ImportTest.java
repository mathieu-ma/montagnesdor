package fr.mch.mdo.security.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.test.MdoTestCase;

/**
 * This class can be used to import a key/certificate pair from a pkcs12 file
 * into a regular JKS format keystore for use with jetty and other java based
 * SSL applications, etc.
 * 
 * <PRE>
 *    usage: java PKCS12Import {pkcs12file} [newjksfile]
 * </PRE>
 * 
 * If you don't supply newjksfile, newstore.jks will be used. This can be an
 * existing JKS keystore.
 * <P>
 * Upon execution, you will be prompted for the password for the pkcs12 keystore
 * as well as the password for the jdk file. After execution you should have a
 * JKS keystore file that contains the private key and certificate that were in
 * the pkcs12
 * <P>
 * You can generate a pkcs12 file from PEM encoded certificate and key files
 * using the following openssl command:
 * 
 * <PRE>
 *    openssl pkcs12 -export -out keystore.pkcs12 -in www.crt -inkey www.key
 * </PRE>
 * 
 * then run:
 * 
 * <PRE>
 *    java PKCS12Import keystore.pkcs12 keytore.jks
 * </PRE>
 * 
 * @author Jason Gilbert &lt;jason@doozer.com&gt;
 */
public class PKCS12ImportTest extends MdoTestCase
{
    /** Dummy file for testing */
    private static final String DUMMY_FILE = "src/test/resources/generated/dummy.file";
    /** The PKCS12 file to be converted */
    private static final String PKCS12_INPUT_FILE = "src/test/resources/mdo-keystore.pkcs12";
    /** The converted JKS file */
    private static final String JKS_OUTPUT_FILE = "src/test/resources/generated/mdo-keystore.jks";
    /** The pass phrases file for PKCS12_INPUT_FILE and JKS_OUTPUT_FILE*/
    private static final String PASS_PHRASES_INPUT_FILE_2 = "src/test/resources/keystore-pkcs12_2.pass";
    /** The pass phrases file for PKCS12_INPUT_FILE and JKS_OUTPUT_FILE*/
    private static final String PASS_PHRASES_INPUT_FILE_1 = "src/test/resources/keystore-pkcs12_1.pass";
    /** The pass phrases file for PKCS12_INPUT_FILE and JKS_OUTPUT_FILE*/
    private static final String PASS_PHRASES_INPUT_FILE_0 = "src/test/resources/keystore-pkcs12_0.pass";

    
    /**
     * Create the test case
     * 
     * @param testName
     *                name of the test case
     */
    public PKCS12ImportTest(String testName)
    {
	super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
	return new TestSuite(PKCS12ImportTest.class);
    }

    public void testProcessImport()
    {
	File jksFile = new File(PKCS12ImportTest.JKS_OUTPUT_FILE);
	assertFalse("The JKS file must not exist", jksFile.exists());
	PKCS12Import.processImport(PKCS12ImportTest.PKCS12_INPUT_FILE, PKCS12ImportTest.JKS_OUTPUT_FILE, PKCS12ImportTest.PASS_PHRASES_INPUT_FILE_2);
	assertTrue("The JKS file must be generated", jksFile.exists());
	assertTrue("The JKS file must be deleted", jksFile.delete());
    }

    public void testProcessParameters() {
	//0 argument
	try {
	    PKCS12Import.processImport();
	} catch(IllegalArgumentException e) {
	    assertEquals("The method PKCS12Import.processImport() must throw an Exception", PKCS12Import.ILLEGAL_NUMBER_ARGUMENT_EXCEPTION_MESSAGE, e.getMessage());
	}
	//4 arguments
	try {
	    PKCS12Import.processImport(PKCS12ImportTest.PKCS12_INPUT_FILE, PKCS12ImportTest.JKS_OUTPUT_FILE, PKCS12ImportTest.PASS_PHRASES_INPUT_FILE_2, PKCS12ImportTest.DUMMY_FILE);
	} catch(IllegalArgumentException e) {
	    assertEquals("The method PKCS12Import.processImport() must throw an Exception", PKCS12Import.ILLEGAL_NUMBER_ARGUMENT_EXCEPTION_MESSAGE, e.getMessage());
	}
	//1 arguments file can not be read
	File pkcs12File = new File(PKCS12ImportTest.DUMMY_FILE);
	try {
	    assertFalse("This file can not be read", pkcs12File.canRead());
	    PKCS12Import.processImport(PKCS12ImportTest.DUMMY_FILE);
	} catch(IllegalArgumentException e) {
	    assertEquals("The method PKCS12Import.processImport() must throw an Exception", PKCS12Import.ILLEGAL_FILE_READ_EXCEPTION_MESSAGE, e.getMessage());
	}
	//2 arguments file can not be written
	File jksFile = new File(PKCS12ImportTest.JKS_OUTPUT_FILE);
	try {
	    if(!jksFile.exists()) {
		try {
		    jksFile.createNewFile();
		    jksFile.setReadOnly();
		} catch (IOException e) {
		   fail("Could not create JKS file" + e.getMessage());
		}
	    }
	    assertFalse("This file can not be written", jksFile.canWrite());
	    PKCS12Import.processImport(PKCS12ImportTest.PKCS12_INPUT_FILE, PKCS12ImportTest.JKS_OUTPUT_FILE);
	} catch(IllegalArgumentException e) {
	    assertEquals("The method PKCS12Import.processImport() must throw an Exception", PKCS12Import.ILLEGAL_FILE_WRITE_EXCEPTION_MESSAGE, e.getMessage());
	}
	if(jksFile.exists()) {
	    jksFile.delete();
	}
	//3 arguments password file can not be read
	try {
	    PKCS12Import.processImport(PKCS12ImportTest.PKCS12_INPUT_FILE, PKCS12ImportTest.JKS_OUTPUT_FILE, PKCS12ImportTest.DUMMY_FILE);
	} catch(IllegalArgumentException e) {
	    assertEquals("The method PKCS12Import.processImport() must throw an Exception", PKCS12Import.ILLEGAL_FILE_READ_EXCEPTION_MESSAGE, e.getMessage());
	}
    }
    
    public void testProcessPassPhrases() {
	//pass phrases file does not exist
	try {
	    // Call private method PKCS12Import.processPassPhrases
	    invokeProcessPassPhrases(new File(PKCS12ImportTest.DUMMY_FILE));
	} catch (InvocationTargetException e) {
	    assertTrue("The method PKCS12Import.processImport() must throw an IllegalArgumentException", e.getTargetException() instanceof IllegalArgumentException);
	    assertEquals("The method PKCS12Import.processImport() must throw an Exception", PKCS12Import.ILLEGAL_FILE_READ_EXCEPTION_MESSAGE, e.getMessage());
	}
	//No input password phrase
	try {
	    PKCS12Import.processImport(PKCS12ImportTest.PKCS12_INPUT_FILE, PKCS12ImportTest.JKS_OUTPUT_FILE, PKCS12ImportTest.PASS_PHRASES_INPUT_FILE_0);
	} catch(NoSuchFieldError e) {
	    assertEquals("The method PKCS12Import.processImport() must throw an Exception", PKCS12Import.NO_SUCH_FIELD_EXCEPTION_MESSAGE, e.getMessage());
	}
	//No output password phrase
	try {
	    PKCS12Import.processImport(PKCS12ImportTest.PKCS12_INPUT_FILE, PKCS12ImportTest.JKS_OUTPUT_FILE, PKCS12ImportTest.PASS_PHRASES_INPUT_FILE_1);
	} catch(NoSuchFieldError e) {
	    assertEquals("The method PKCS12Import.processImport() must throw an Exception", PKCS12Import.NO_SUCH_FIELD_EXCEPTION_MESSAGE, e.getMessage());
	}
	//No pass phrases file
	try {
	    PKCS12Import.processImport(PKCS12ImportTest.PKCS12_INPUT_FILE, PKCS12ImportTest.JKS_OUTPUT_FILE);
	} catch(NoSuchFieldError e) {
	    assertEquals("The method PKCS12Import.processImport() must throw an Exception", PKCS12Import.NO_SUCH_FIELD_EXCEPTION_MESSAGE, e.getMessage());
	}


    }
    
    public void testProcessKeyStore()
    {
	File dummyFile = new File(PKCS12ImportTest.DUMMY_FILE);
	File pkcs12File = new File(PKCS12ImportTest.PKCS12_INPUT_FILE);
	try {
	    invokeProcessKeyStore(PKCS12Import.PKCS12_KEY_STORE_INSTANCE_TYPE + "3", null, null, null, null, null);
	} catch(InvocationTargetException e) {
	    assertTrue("The method PKCS12Import.processImport() must throw an IllegalArgumentException", e.getTargetException() instanceof IllegalArgumentException);
	    assertEquals("The method PKCS12Import.processImport() must throw an Exception", PKCS12Import.KEY_STORE_TYPE_IN_EXCEPTION_MESSAGE, e.getMessage());	
	}

	try {
	    invokeProcessKeyStore(PKCS12Import.PKCS12_KEY_STORE_INSTANCE_TYPE, null, null, PKCS12Import.JKS_KEY_STORE_INSTANCE_TYPE + "3", null, null);
	} catch(InvocationTargetException e) {
	    assertTrue("The method PKCS12Import.processImport() must throw an IllegalArgumentException", e.getTargetException() instanceof IllegalArgumentException);
	    assertEquals("The method PKCS12Import.processImport() must throw an Exception", PKCS12Import.KEY_STORE_TYPE_OUT_EXCEPTION_MESSAGE, e.getMessage());	
	}

	try {
	    // Null input file
	    invokeProcessKeyStore(PKCS12Import.PKCS12_KEY_STORE_INSTANCE_TYPE, null, null, PKCS12Import.JKS_KEY_STORE_INSTANCE_TYPE , null, null);
	} catch(InvocationTargetException e) {
	    assertTrue("The method PKCS12Import.processImport() must throw an IllegalArgumentException", e.getTargetException() instanceof IllegalArgumentException);
	    assertEquals("The method PKCS12Import.processImport() must throw an Exception", PKCS12Import.ILLEGAL_FILE_IN_NOT_FOUND_EXCEPTION_MESSAGE, e.getMessage());	
	}
	try {
	    // Dummy input file
	    invokeProcessKeyStore(PKCS12Import.PKCS12_KEY_STORE_INSTANCE_TYPE, dummyFile, null, PKCS12Import.JKS_KEY_STORE_INSTANCE_TYPE , null, null);
	} catch(InvocationTargetException e) {
	    assertTrue("The method PKCS12Import.processImport() must throw an IllegalArgumentException", e.getTargetException() instanceof IllegalArgumentException);
	    assertEquals("The method PKCS12Import.processImport() must throw an Exception", PKCS12Import.ILLEGAL_FILE_IN_NOT_FOUND_EXCEPTION_MESSAGE, e.getMessage());	
	}
	try {
	    // Load pkcs12 NoSuchAlgorithmException
	    invokeProcessKeyStore(PKCS12Import.PKCS12_KEY_STORE_INSTANCE_TYPE, pkcs12File, 
		    new char[] {'K', 'I', 'M', 'S', 'A', 'N'}, PKCS12Import.JKS_KEY_STORE_INSTANCE_TYPE , null, null);
	} catch(InvocationTargetException e) {
	    assertTrue("The method PKCS12Import.processImport() must throw an IllegalArgumentException", e.getTargetException() instanceof IllegalArgumentException);
	    assertEquals("The method PKCS12Import.processImport() must throw an Exception", PKCS12Import.ILLEGAL_KEY_STORE_LOAD_FILE_IN, e.getMessage());	
	}
	try {
	    // Load jks IOException
	    invokeProcessKeyStore(PKCS12Import.PKCS12_KEY_STORE_INSTANCE_TYPE, pkcs12File, 
		    new char[] {'k', 'i', 'm', 's', 'a', 'n'}, PKCS12Import.JKS_KEY_STORE_INSTANCE_TYPE , pkcs12File, null);
	} catch(InvocationTargetException e) {
	    assertTrue("The method PKCS12Import.processImport() must throw an IllegalArgumentException", e.getTargetException() instanceof IllegalArgumentException);
	    assertEquals("The method PKCS12Import.processImport() must throw an Exception", PKCS12Import.ILLEGAL_KEY_STORE_LOAD_FILE_OUT, e.getMessage());	
	}
    }

    private Object invokeProcessKeyStore(String keySoreTypeIn, File fileIn, char[] inPhrase,
	    String keySoreTypeOut, File fileOut, char[] outPhrase)
    throws InvocationTargetException {

	// Purposely pass null values to the method, to make sure it throws
	// NullPointerException

	
	Class<?>[] argClasses = { String.class, File.class, char[].class, String.class, File.class, char[].class };
	Object[] argObjects = { keySoreTypeIn, fileIn, inPhrase, keySoreTypeOut, fileOut, outPhrase };

	return invokeStaticMethod(PKCS12Import.class, "processKeyStore", argClasses, argObjects);
    }

    private Object invokeProcessPassPhrases(File filePass)
            throws InvocationTargetException {

        // Purposely pass null values to the method, to make sure it throws
        // NullPointerException

        Class<?>[] argClasses = { File.class };
        Object[] argObjects = { filePass };

        return invokeStaticMethod(PKCS12Import.class, "processPassPhrases", argClasses, argObjects);
    }
}
