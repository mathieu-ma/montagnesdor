package fr.mch.mdo.restaurant.exception;

import java.util.Locale;

import junit.framework.Test;
import junit.framework.TestSuite;

public class MdoAuthenticationExceptionTest extends MdoCommonExceptionTest {
    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public MdoAuthenticationExceptionTest(String testName) {
	super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
	return new TestSuite(MdoAuthenticationExceptionTest.class);
    }

    public void testMdoDataBeanException() {
	super.checkMdoMdoExceptionConstrutors(MdoAuthenticationException.class, null);
	super.checkMdoMdoExceptionConstrutors(MdoAuthenticationException.class, Locale.ENGLISH);
    }
}
