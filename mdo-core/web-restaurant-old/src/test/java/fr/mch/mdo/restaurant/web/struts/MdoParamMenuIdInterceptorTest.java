package fr.mch.mdo.restaurant.web.struts;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Mathieu MA
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class MdoParamMenuIdInterceptorTest extends TestCase
{
    /**
     * Create the test case
     * 
     * @param testName
     *                name of the test case
     */
    public MdoParamMenuIdInterceptorTest(String testName)
    {
	super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
	return new TestSuite(MdoParamMenuIdInterceptorTest.class);
    }

    public void testInit()
    {
    }

    public void TestDestroy()
    {
    }

    /*
     * Cette méthode est utilisée pour vérifier le choix du language de
     * l'utilisateur. Dans le cas où l'utilisateur n'a pas choisi une langue,
     * c'est le language du navigateur qui est pris en compte.
     */
    public void testIntercept()
    {

    }

    public void testGetSelectedMenuItemIdKey()
    {
    }

    public void testSetSelectedMenuItemIdKey()
    {

    }
}
