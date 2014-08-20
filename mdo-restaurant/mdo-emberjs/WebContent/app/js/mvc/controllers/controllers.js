'use strict';

/**
 * This file will contain all Mdo controllers.
 */

Mdo.ApplicationController = Ember.ObjectController.extend({
	user: null,
	init: function() {
	}
	
});

Mdo.UserController = Ember.ObjectController.extend({
	user: null,
	init: function() {
	}
	
});

Mdo.OrdersController = Ember.ObjectController.extend({
	mma: "Orders"
});

Mdo.OrdersIndexController = Ember.ObjectController.extend({
	mma: "OrdersIndex"
});

Mdo.CashedOrdersController = Ember.ObjectController.extend({
});

Mdo.LockedOrdersController = Ember.ObjectController.extend({
});

