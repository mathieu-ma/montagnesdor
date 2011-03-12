package fr.mch.mdo.restaurant.dao;

import fr.mch.mdo.restaurant.beans.IMdoBean;

public interface IDaoBase extends IDao
{
    void setBean(IMdoBean bean);
    IMdoBean getBean();

}
