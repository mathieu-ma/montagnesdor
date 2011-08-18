package fr.mch.mdo.restaurant.web.struts.forms;

import java.util.List;

import fr.mch.mdo.logs.ILogger;
import fr.mch.mdo.restaurant.beans.IMdoBean;

public abstract class AdministrationManagerForm
{	
	private ILogger logger;
	
	private List<IMdoBean> list;
	
	public abstract IMdoBean getBean();

	public abstract void setBean(IMdoBean bean);

	public List<IMdoBean> getList()
	{
		return list;
	}

	public void setList(List<IMdoBean> list)
	{
		this.list = list;
	}

	public String validate()
	{
	    return null;
	}

	public ILogger getLogger()
	{
		return logger;
	}

	public void setLogger(ILogger logger)
	{
		this.logger = logger;
	}
}
