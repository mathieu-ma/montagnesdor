package fr.mch.mdo.restaurant.services.business.utils;

import java.util.List;

import fr.mch.mdo.restaurant.beans.dto.DinnerTableDto;
import fr.mch.mdo.restaurant.dao.beans.DinnerTable;
import fr.mch.mdo.restaurant.dao.beans.TableType;

public interface IOrdersDtoHelper {

	List<DinnerTableDto> findAllTablesFactoring(List<DinnerTable> daoBeans);

	DinnerTableDto findTableHeader(DinnerTable table);

	DinnerTableDto findTable(DinnerTable table);

	Boolean isTakeaway(TableType type);

}
