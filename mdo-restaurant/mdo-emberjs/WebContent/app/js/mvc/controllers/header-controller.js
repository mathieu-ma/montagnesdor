'use strict';

/**
 * This file will contain Mdo header controllers.
 */

Mdo.HeaderController = Ember.ObjectController.extend({
	needs: [ 
	        // Header controllers
	        "headerMenus", "headerOrder",
	        // Menu controllers
	        "user", "orders", "ordersIndex", "cashedOrders", "lockedOrders"
	        ],
	update: function(selectedMenuItemController) {
		var headerMenusController = this.get('controllers.headerMenus');
		var headerOrderController = this.get('controllers.headerOrder');
		var item = headerMenusController.get('selectedMenuItem');
		var resetHeaderOrder = false;
		switch (selectedMenuItemController) {
		case this.get('controllers.user'):
			item = Mdo.USER_MENU_ITEM_KEY;
			resetHeaderOrder = true;
			break;
		case this.get('controllers.orders'):
		case this.get('controllers.ordersIndex'):
			item = Mdo.ORDERS_MENU_ITEM_KEY;				
			resetHeaderOrder = true;
			break;
		case this.get('controllers.cashedOrders'):
			item = Mdo.CASHED_ORDERS_MENU_ITEM_KEY;				
			resetHeaderOrder = true;
			break;
		case this.get('controllers.lockedOrders'):
			item = Mdo.LOCKED_ORDERS_MENU_ITEM_KEY;				
			resetHeaderOrder = true;
			break;
		default:
			break;
		}
		headerMenusController.set('selectedMenuItem', item);
		headerOrderController.set('resetHeaderOrder', resetHeaderOrder);
	}
});
