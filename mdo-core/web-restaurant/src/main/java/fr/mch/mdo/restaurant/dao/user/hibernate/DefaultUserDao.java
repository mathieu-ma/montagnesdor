package fr.mch.mdo.restaurant.dao.user.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import fr.mch.mdo.restaurant.dao.beans.User;
import fr.mch.mdo.restaurant.dao.hibernate.DefaultDaoServices;
import fr.mch.mdo.restaurant.dao.hibernate.HibernateAopTransactionUtil;
import fr.mch.mdo.restaurant.dao.user.IUserDao;
import fr.mch.mdo.restaurant.services.logs.LoggerServiceImpl;

public class DefaultUserDao extends DefaultDaoServices implements IUserDao
{
    private static IUserDao instance = null;

    public static IUserDao getInstance()
    {
	if (instance == null)
	{
	    synchronized (DefaultUserDao.class)
	    {
		if (instance == null)
		{
		    instance = new DefaultUserDao();
		    instance.setBean(new User());
		    instance.setLogger(LoggerServiceImpl.getInstance()
			    .getLogger(DefaultUserDao.class.getName()));
		}
	    }
	}
	return instance;
    }

    @Override
    public void savePassword(String levelPassword, String newPassword, Long userAuthenticationId) throws Exception
    {
	try
	{
	    Session session = getSession();
	    if (getSession() == null)
	    {
		session = HibernateAopTransactionUtil.currentSession();
	    }

	    Query query = session.getNamedQuery("User.Save.Password.Level."+levelPassword);
	    query.setString("password", newPassword);
	    query.setLong("id", userAuthenticationId);

	    int result = query.executeUpdate();
	}
	catch (HibernateException e)
	{
	    logger.error("message.error.dao.save", new Object[]
	    {
		    super.getBean().getClass().getName(), userAuthenticationId
	    }, e);
	    throw new Exception(e);
	}
	catch (Exception e)
	{
	    logger.error("message.error.dao.save", new Object[]
	    {
		    super.getBean().getClass().getName(), userAuthenticationId
	    }, e);
	    throw new Exception(e);
	}
	finally
	{
	    try
	    {
		if (getSession() == null)
		{
		    HibernateAopTransactionUtil.closeSession();
		}
	    }
	    catch (HibernateException e)
	    {
		logger.error("message.error.hibernate.session.close", e);
		throw new Exception(e);
	    }
	}
    }
}
