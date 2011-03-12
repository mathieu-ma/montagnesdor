package fr.mch.mdo.restaurant.exception;

import java.util.Locale;

import junit.framework.Test;
import junit.framework.TestSuite;

public class MdoFunctionalExceptionTest extends MdoCommonExceptionTest {
    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public MdoFunctionalExceptionTest(String testName) {
	super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
	return new TestSuite(MdoFunctionalExceptionTest.class);
    }

    public void testMdoFunctionalException() {
	super.checkMdoMdoExceptionConstrutors(MdoFunctionalException.class, null);
	super.checkMdoMdoExceptionConstrutors(MdoFunctionalException.class, Locale.ENGLISH);
    }
}
