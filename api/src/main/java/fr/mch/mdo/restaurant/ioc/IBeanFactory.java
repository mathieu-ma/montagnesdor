package fr.mch.mdo.restaurant.ioc;

import fr.mch.mdo.restaurant.IocBeanName;


public interface IBeanFactory
{
    Object getBean(IocBeanName beanName);
}
