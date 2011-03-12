package fr.mch.mdo.restaurant.dao;

import java.sql.Connection;

import org.hibernate.Session;

import fr.mch.mdo.restaurant.exception.MdoDataBeanException;

public interface ISessionFactory 
{
    Session currentSession() throws MdoDataBeanException;

    void closeSession() throws MdoDataBeanException;
    
    void initialize(Object object) throws MdoDataBeanException;
    
    Connection getConnection() throws MdoDataBeanException;
}
