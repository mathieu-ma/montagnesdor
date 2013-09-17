'use strict';

/**
 * This file will contain all Mdo views.
 */

	Mdo.UserView = Ember.View.extend({
		templateName: "user",
		didInsertElement: function() {
		},
	});

	Mdo.OrdersView = Ember.View.extend({
		templateName: "orders"
	});

	Mdo.CashedOrdersView = Ember.View.extend({
		templateName: "cashedOrders"
	});

	Mdo.LockedOrdersView = Ember.View.extend({
		templateName: "lockedOrders"
	});

