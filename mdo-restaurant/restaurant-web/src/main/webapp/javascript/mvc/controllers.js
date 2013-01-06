/**
 * This file will contain all Mdo controllers.
 */
$(document).ready(function() {

	// This is the application controller. It is required by Mdo application for initial context.
	Mdo.ApplicationController = Ember.Controller.extend({
	});

	
	Mdo.HeaderController = Ember.ObjectController.extend({
		content: [],
	});

	Mdo.UserController = Ember.ObjectController.extend({
		user: null,
		
	});
	
	Mdo.OrdersController = Ember.ObjectController.extend({
	});

	Mdo.CashedOrdersController = Ember.ObjectController.extend({
	});

	Mdo.LockedOrdersController = Ember.ObjectController.extend({
	});
});
