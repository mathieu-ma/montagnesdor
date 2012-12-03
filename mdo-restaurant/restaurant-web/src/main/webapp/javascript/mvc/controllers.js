/**
 * This file will contain all Mdo controllers.
 */
$(document).ready(function() {
	// This is the application controller. It is required by Mdo application for initial context.
	Mdo.ApplicationController = Ember.Controller.extend();
	
	Mdo.HeaderController = Ember.ArrayController.extend({
		content: [],
		gotoUser: function() {
			this.target.router.send('gotoUser');
		},
		gotoOrders: function() {
			this.target.router.send('gotoOrders');
		}	
	});
	
	Mdo.UserController = Ember.ObjectController.extend({
	});
	
	Mdo.OrdersController = Ember.ObjectController.extend({
	});

});
