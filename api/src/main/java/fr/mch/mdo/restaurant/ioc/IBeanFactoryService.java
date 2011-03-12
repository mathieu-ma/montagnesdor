package fr.mch.mdo.restaurant.ioc;

import fr.mch.mdo.logs.ILoggerService;
import fr.mch.mdo.restaurant.services.util.IUtils;


public interface IBeanFactoryService extends IBeanFactory
{
    ILoggerService getLoggerService();

    IUtils getUtils();
}
