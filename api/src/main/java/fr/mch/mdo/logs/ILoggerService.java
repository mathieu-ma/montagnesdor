package fr.mch.mdo.logs;

public interface ILoggerService extends ILoggerBean
{
    ILogger getLogger(String className);
}
