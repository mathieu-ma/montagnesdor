/**
 * This file will contain all Mdo controllers.
 */
	Mdo.HeaderMenusController = Ember.ArrayController.extend({
		selectedMenuItem: null,
		menus: function() {
			var menus = Ember.A();
			var model = this.get('model');
			model.forEach(function(item, index) {
				var menuItem = {};
				menuItem.menuItemId = "menuItemId" + index;
				menuItem.key = item.key;
				menuItem.icons = {
					primary: item.icon
				};
				menus.pushObject(Ember.Object.create(menuItem));
			});
			return menus;
		}.property('model'),
		actions: {
			routeTo: function(key) {
				var route = null;
				switch (key) {
				case Mdo.USER_MENU_ITEM_KEY:
					route = "user";
					break;
				case Mdo.ORDERS_MENU_ITEM_KEY:
					route = "orders";
					break;
				case Mdo.CASHED_ORDERS_MENU_ITEM_KEY:
					route = "cashedOrders";
					break;
				case Mdo.LOCKED_ORDERS_MENU_ITEM_KEY:
					route = "lockedOrders";
					break;
				default:
					break;
				}
				this.transitionToRoute(route);
			},			
		},
	});
