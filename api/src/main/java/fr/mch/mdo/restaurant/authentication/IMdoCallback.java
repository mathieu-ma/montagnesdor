package fr.mch.mdo.restaurant.authentication;

import java.io.Serializable;
import java.util.Map;

import javax.security.auth.callback.Callback;

import fr.mch.mdo.logs.ILoggerBean;
import fr.mch.mdo.restaurant.exception.MdoException;



public interface IMdoCallback extends Callback, ILoggerBean, Serializable
{
    void callback(Map<MdoCallBackType, Object> properties) throws MdoException;
}
