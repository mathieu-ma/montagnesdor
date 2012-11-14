package fr.mch.mdo.restaurant.services.business.utils;

import java.util.List;

import fr.mch.mdo.restaurant.beans.dto.DinnerTableDto;
import fr.mch.mdo.restaurant.beans.dto.OrderLineDto;
import fr.mch.mdo.restaurant.beans.dto.ProductDto;
import fr.mch.mdo.restaurant.beans.dto.ProductSpecialCodeDto;
import fr.mch.mdo.restaurant.dao.beans.DinnerTable;
import fr.mch.mdo.restaurant.dao.beans.OrderLine;
import fr.mch.mdo.restaurant.dao.beans.ProductLabel;
import fr.mch.mdo.restaurant.dao.beans.ProductSpecialCodeLabel;
import fr.mch.mdo.restaurant.dao.beans.TableType;

public interface IOrdersDtoHelper {

	List<DinnerTableDto> findAllTablesFactoring(List<DinnerTable> daoBeans);

	DinnerTableDto findTableHeader(DinnerTable table);

	DinnerTableDto findTable(DinnerTable table);

	Boolean isTakeaway(TableType type);

	DinnerTable buildTableReset(Long dinnerTableId, Long restaurantId,
			Long userAuthenticationId, String number, Integer customersNumber);

	ProductSpecialCodeDto fromProductSpecialCode(ProductSpecialCodeLabel productSpecialCode);

	ProductDto fromProduct(ProductLabel product);

	OrderLine toOrderLine(OrderLineDto orderLine);

}
