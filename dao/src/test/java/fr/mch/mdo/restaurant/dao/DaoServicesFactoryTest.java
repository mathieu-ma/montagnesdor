package fr.mch.mdo.restaurant.dao;

import junit.framework.Test;
import junit.framework.TestSuite;
import fr.mch.mdo.restaurant.dao.authentication.IAuthenticationDao;
import fr.mch.mdo.restaurant.dao.authentication.hibernate.DefaultAuthenticationDao;
import fr.mch.mdo.restaurant.dao.locales.ILocalesDao;
import fr.mch.mdo.restaurant.dao.locales.hibernate.DefaultLocalesDao;
import fr.mch.mdo.restaurant.dao.products.ICategoriesDao;
import fr.mch.mdo.restaurant.dao.products.IProductPartsDao;
import fr.mch.mdo.restaurant.dao.products.IProductSpecialCodesDao;
import fr.mch.mdo.restaurant.dao.products.IProductsDao;
import fr.mch.mdo.restaurant.dao.products.IValueAddedTaxesDao;
import fr.mch.mdo.restaurant.dao.products.hibernate.DefaultCategoriesDao;
import fr.mch.mdo.restaurant.dao.products.hibernate.DefaultProductPartsDao;
import fr.mch.mdo.restaurant.dao.products.hibernate.DefaultProductSpecialCodesDao;
import fr.mch.mdo.restaurant.dao.products.hibernate.DefaultProductsDao;
import fr.mch.mdo.restaurant.dao.products.hibernate.DefaultValueAddedTaxesDao;
import fr.mch.mdo.restaurant.dao.restaurants.IRestaurantsDao;
import fr.mch.mdo.restaurant.dao.restaurants.hibernate.DefaultRestaurantsDao;
import fr.mch.mdo.restaurant.dao.tables.ITableTypesDao;
import fr.mch.mdo.restaurant.dao.tables.hibernate.DefaultTableTypesDao;
import fr.mch.mdo.restaurant.dao.users.IUserAuthenticationsDao;
import fr.mch.mdo.restaurant.dao.users.IUserRolesDao;
import fr.mch.mdo.restaurant.dao.users.IUsersDao;
import fr.mch.mdo.restaurant.dao.users.hibernate.DefaultUserAuthenticationsDao;
import fr.mch.mdo.restaurant.dao.users.hibernate.DefaultUserRolesDao;
import fr.mch.mdo.restaurant.dao.users.hibernate.DefaultUsersDao;
import fr.mch.mdo.test.MdoTestCase;

public class DaoServicesFactoryTest extends MdoTestCase {
    /**
     * Create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public DaoServicesFactoryTest(String testName) {
	super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
	return new TestSuite(DaoServicesFactoryTest.class);
    }

    public void testGetAuthenticationDao() {
	IAuthenticationDao dao = DaoServicesFactory.getAuthenticationDao();
	assertTrue("The instance of the dao must be IAuthenticationDao type", dao instanceof IAuthenticationDao);
	assertTrue("The instance of the dao must be DefaultAuthenticationDao type", dao instanceof DefaultAuthenticationDao);
    }

    public void testGetLocalesDao() {
	ILocalesDao dao = DaoServicesFactory.getLocalesDao();
	assertTrue("The instance of the dao must be ILocalesDao type", dao instanceof ILocalesDao);
	assertTrue("The instance of the dao must be DefaultLocalesDao type", dao instanceof DefaultLocalesDao);
    }
    
    public void testGetCategoriesDao() {
	ICategoriesDao dao = DaoServicesFactory.getCategoriesDao();
	assertTrue("The instance of the dao must be ICategoriesDao type", dao instanceof ICategoriesDao);
	assertTrue("The instance of the dao must be DefaultCategoriesDao type", dao instanceof DefaultCategoriesDao);
    }

    public void testGetProductPartsDao() {
	IProductPartsDao dao = DaoServicesFactory.getProductPartsDao();
	assertTrue("The instance of the dao must be IProductPartsDao type", dao instanceof IProductPartsDao);
	assertTrue("The instance of the dao must be DefaultProductPartsDao type", dao instanceof DefaultProductPartsDao);
    }

    public void testGetProductsDao() {
	IProductsDao dao = DaoServicesFactory.getProductsDao();
	assertTrue("The instance of the dao must be IProductsDao type", dao instanceof IProductsDao);
	assertTrue("The instance of the dao must be DefaultProductsDao type", dao instanceof DefaultProductsDao);
    }

    public void testGetProductSpecialCodesDao() {
	IProductSpecialCodesDao dao = DaoServicesFactory.getProductSpecialCodesDao();
	assertTrue("The instance of the dao must be IProductSpecialCodesDao type", dao instanceof IProductSpecialCodesDao);
	assertTrue("The instance of the dao must be DefaultProductSpecialCodesDao type", dao instanceof DefaultProductSpecialCodesDao);
    }

    public void testGetValueAddedTaxesDao() {
	IValueAddedTaxesDao dao = DaoServicesFactory.getValueAddedTaxesDao();
	assertTrue("The instance of the dao must be IValueAddedTaxesDao type", dao instanceof IValueAddedTaxesDao);
	assertTrue("The instance of the dao must be DefaultValueAddedTaxesDao type", dao instanceof DefaultValueAddedTaxesDao);
    }

    public void testGetRestaurantsDao() {
	IRestaurantsDao dao = DaoServicesFactory.getRestaurantsDao();
	assertTrue("The instance of the dao must be IRestaurantsDao type", dao instanceof IRestaurantsDao);
	assertTrue("The instance of the dao must be DefaultRestaurantsDao type", dao instanceof DefaultRestaurantsDao);
    }

    public void testGetTableTypesDao() {
	ITableTypesDao dao = DaoServicesFactory.getTableTypesDao();
	assertTrue("The instance of the dao must be ITableTypesDao type", dao instanceof ITableTypesDao);
	assertTrue("The instance of the dao must be DefaultTableTypesDao type", dao instanceof DefaultTableTypesDao);
    }

    public void testGetUserAuthenticationsDao() {
	IUserAuthenticationsDao dao = DaoServicesFactory.getUserAuthenticationsDao();
	assertTrue("The instance of the dao must be IUserAuthenticationsDao type", dao instanceof IUserAuthenticationsDao);
	assertTrue("The instance of the dao must be DefaultUserAuthenticationsDao type", dao instanceof DefaultUserAuthenticationsDao);
    }

    public void testGetUserRolesDao() {
	IUserRolesDao dao = DaoServicesFactory.getUserRolesDao();
	assertTrue("The instance of the dao must be IUserRolesDao type", dao instanceof IUserRolesDao);
	assertTrue("The instance of the dao must be DefaultUserRolesDao type", dao instanceof DefaultUserRolesDao);
    }

    public void testGetUsersDao() {
	IUsersDao dao = DaoServicesFactory.getUsersDao();
	assertTrue("The instance of the dao must be IUsersDao type", dao instanceof IUsersDao);
	assertTrue("The instance of the dao must be DefaultUsersDao type", dao instanceof DefaultUsersDao);
    }
}
