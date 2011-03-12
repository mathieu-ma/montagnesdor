package fr.mch.mdo.restaurant.exception;

import java.util.Locale;

import junit.framework.Test;
import junit.framework.TestSuite;

public class MdoMessageManagerExceptionTest extends MdoCommonExceptionTest {
    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public MdoMessageManagerExceptionTest(String testName) {
	super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
	return new TestSuite(MdoMessageManagerExceptionTest.class);
    }

    public void testMdoMessageManagerException() {
	super.checkMdoMdoExceptionConstrutors(MdoMessageManagerException.class, null);
	super.checkMdoMdoExceptionConstrutors(MdoMessageManagerException.class, Locale.ENGLISH);
    }
}
