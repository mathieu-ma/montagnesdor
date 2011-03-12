package fr.mch.mdo.restaurant.beans;

public interface IMdoViewBean extends IMdoBean 
{
	IMdoDtoBean getDtoBean();
	
	void setDtoBean(IMdoDtoBean dtoBean);
}
