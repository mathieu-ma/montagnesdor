package fr.mch.mdo.restaurant.exception;

import java.util.Locale;

import junit.framework.Test;
import junit.framework.TestSuite;

public class MdoBusinessExceptionTest extends MdoCommonExceptionTest {
    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public MdoBusinessExceptionTest(String testName) {
	super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
	return new TestSuite(MdoBusinessExceptionTest.class);
    }

    public void testMdoBusinessException() {
	super.checkMdoMdoExceptionConstrutors(MdoBusinessException.class, null);
	super.checkMdoMdoExceptionConstrutors(MdoBusinessException.class, Locale.ENGLISH);
    }
}
