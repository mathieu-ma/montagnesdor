package fr.mch.mdo.restaurant.beans;

import java.util.Map;

public interface IBeanLabelable extends IMdoBeanIdentifiable 
{
	Map<Long, String> getLabels();

	void setLabels(Map<Long, String> labels);

}
