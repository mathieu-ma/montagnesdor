package fr.mch.mdo.restaurant.ui.forms;

import fr.mch.mdo.restaurant.dto.beans.PrintingInformationDto;
import fr.mch.mdo.restaurant.dto.beans.PrintingInformationsManagerViewBean;


public class PrintingInformationsManagerForm extends MdoLabelsForm 
{
	public PrintingInformationsManagerForm() {
		super(new PrintingInformationDto());
		super.setViewBean(new PrintingInformationsManagerViewBean());
	}
}