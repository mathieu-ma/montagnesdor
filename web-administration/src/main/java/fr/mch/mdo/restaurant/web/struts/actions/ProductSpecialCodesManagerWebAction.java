package fr.mch.mdo.restaurant.web.struts.actions;

import java.util.Locale;

import fr.mch.mdo.restaurant.Constants;
import fr.mch.mdo.restaurant.dao.beans.ProductSpecialCode;
import fr.mch.mdo.restaurant.dao.beans.Restaurant;
import fr.mch.mdo.restaurant.dto.beans.IAdministrationManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.MdoUserContext;
import fr.mch.mdo.restaurant.dto.beans.ProductSpecialCodeDto;
import fr.mch.mdo.restaurant.dto.beans.ProductSpecialCodesManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.ProductsManagerViewBean;
import fr.mch.mdo.restaurant.dto.beans.RestaurantDto;
import fr.mch.mdo.restaurant.ioc.spring.WebAdministractionBeanFactory;
import fr.mch.mdo.restaurant.ui.forms.ProductSpecialCodesManagerForm;

public class ProductSpecialCodesManagerWebAction extends AdministrationManagerAction 
{
	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	public ProductSpecialCodesManagerWebAction() {
		super(WebAdministractionBeanFactory.getInstance().getLogger(ProductSpecialCodesManagerWebAction.class.getName()), new ProductSpecialCodesManagerForm());
		administrationManager = WebAdministractionBeanFactory.getInstance().getProductSpecialCodesManager();
	}

	@Override
	public String form() throws Exception {
		String result = Constants.ACTION_RESULT_LIST;

		ProductSpecialCodeDto dtoBean = ((ProductSpecialCodeDto) super.getForm().getDtoBean());
		if (dtoBean != null) {
			MdoUserContext userContext = (MdoUserContext) super.getForm().getUserContext();
			if (userContext != null) {
				super.getForm().setDtoBean(super.getAdministrationManager().findByPrimaryKey(dtoBean.getId(), userContext));
				IAdministrationManagerViewBean viewBean = super.getForm().getViewBean();
				if (viewBean != null) {
					super.getAdministrationManager().processList(viewBean, userContext);
				}
			}
		}
		return result;
	}

	public String getProductSpecialCodeLongCodesByRestaurant() throws Exception {
		String forwardPage = "ajax-response";

		ProductSpecialCodeDto dtoBean = ((ProductSpecialCodeDto) super.getForm().getDtoBean());
		ProductSpecialCodesManagerViewBean viewBean = (ProductSpecialCodesManagerViewBean) super.getForm().getViewBean();
		RestaurantDto selectedRestaurant = dtoBean.getRestaurant() != null ? dtoBean.getRestaurant() : (RestaurantDto) viewBean.getRestaurants().get(0);
//		dtoBean.setProductSpecialCodes(super.getAdministrationManager().
//				getProductSpecialCodeLongCodesByRestaurant(selectedRestaurant.getId(),
//						dtoBean.getId()));

		return forwardPage;
	}

	public String labels() throws Exception {
		super.save();
		return this.form();
	}
}
