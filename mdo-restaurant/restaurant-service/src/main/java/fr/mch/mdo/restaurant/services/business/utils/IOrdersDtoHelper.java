package fr.mch.mdo.restaurant.services.business.utils;

import java.util.List;

import fr.mch.mdo.restaurant.beans.dto.DinnerTableDto;
import fr.mch.mdo.restaurant.beans.dto.ProductSpecialCodeDto;
import fr.mch.mdo.restaurant.dao.beans.DinnerTable;
import fr.mch.mdo.restaurant.dao.beans.Product;
import fr.mch.mdo.restaurant.dao.beans.ProductSpecialCode;
import fr.mch.mdo.restaurant.dao.beans.TableType;
import fr.mch.mdo.restaurant.beans.dto.ProductDto;

public interface IOrdersDtoHelper {

	List<DinnerTableDto> findAllTablesFactoring(List<DinnerTable> daoBeans);

	DinnerTableDto findTableHeader(DinnerTable table);

	DinnerTableDto findTable(DinnerTable table);

	Boolean isTakeaway(TableType type);

	DinnerTable buildTableReset(Long dinnerTableId, Long restaurantId,
			Long userAuthenticationId, String number, Integer customersNumber);

	ProductDto findProduct(Product product);

	ProductSpecialCodeDto fromProductSpecialCode(ProductSpecialCode productSpecialCode);

}
