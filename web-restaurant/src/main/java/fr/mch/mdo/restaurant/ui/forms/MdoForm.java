package fr.mch.mdo.restaurant.ui.forms;

import fr.mch.mdo.restaurant.beans.IMdoBean;

public class MdoForm implements IMdoForm
{
    protected IMdoBean dtoBean;

    @SuppressWarnings("unused")
    private MdoForm()
    {
    }

    protected MdoForm(IMdoBean dtoBean)
    {
	this.dtoBean = dtoBean;
    }

    @Override
    public IMdoBean getDtoBean()
    {
	return dtoBean;
    }

    @Override
    public void setDtoBean(IMdoBean dtoBean)
    {
	this.dtoBean = dtoBean;
    }
}
