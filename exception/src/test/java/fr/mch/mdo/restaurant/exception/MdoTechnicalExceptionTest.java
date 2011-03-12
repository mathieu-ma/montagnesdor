package fr.mch.mdo.restaurant.exception;

import java.util.Locale;

import junit.framework.Test;
import junit.framework.TestSuite;

public class MdoTechnicalExceptionTest extends MdoCommonExceptionTest {
    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public MdoTechnicalExceptionTest(String testName) {
	super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
	return new TestSuite(MdoTechnicalExceptionTest.class);
    }

    public void testMdoTechnicalException() {
	super.checkMdoMdoExceptionConstrutors(MdoTechnicalException.class, null);
	super.checkMdoMdoExceptionConstrutors(MdoTechnicalException.class, Locale.ENGLISH);
    }
}
