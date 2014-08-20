'use strict';

Mdo.Router.map(function () {
	this.route('user');
	this.resource('orders', function() {
		this.route('orderLines',  { path: '/orderLines/:orderId' });
	});
	this.route('cashedOrders');
	this.route('lockedOrders');
});

Mdo.ApplicationRoute = Ember.Route.extend({
	model: function() {
		Mdo.userAuthentication = this.store.find('userAuthentication', 1);
	},
	setupController: function() {
		var self = this;
		Ember.bind(self.controllerFor('headerOrderDateTime'), "userEntryDate", "Mdo.userEntryDate.date");
		Ember.bind(self.controllerFor('dateTimeDialog'), "userEntryDate", "Mdo.userEntryDate.date");
		
		Mdo.headerOrder = Mdo.HeaderOrder.create({
			currentOrderNumber: "5", currentOrderCustomersNumber: 16
		});
		self.controllerFor('headerOrder').set("headerOrder", Mdo.headerOrder);		

		Ember.run.later(null, function() {
			//Simulate Ajax

			self.controllerFor('headerLanguages').set("model", Mdo.userAuthentication.get("locales"));


			self.controllerFor('headerMenus').set("model", Mdo.HEADER_MENUS);
		}, 400);
	},
	// These events will be fire in any state of this application. 
	actions: {
		openDateTimeDialog: function(context) {
			this.controllerFor('dateTimeDialog').send('openDateDialog');
        },
	}
});

Mdo.IndexRoute = Ember.Route.extend({
	renderTemplate: function() {
	    this.transitionTo('user');
	},
});

// To be renamed
Mdo.HeaderRoute = Ember.Route.extend({
	renderTemplate: function() {
		this.controllerFor('header').update(this.controller);
	},
});

Mdo.UserRoute = Mdo.HeaderRoute.extend({
	model: function () {
		return Mdo.userAuthentication;
	},
	renderTemplate: function() {
		this._super();
		// Set the default render when first access
	    this.render('user', {		// the template to render
	    		into: 'application',// the template to render into
	      		outlet: 'body',     // the name of the outlet in that template
		});
	},
});

Mdo.UserIndexRoute = Ember.Route.extend({
	setupController: function (controller, model) {
		//var user = Mdo.User.find();
		//this.controllerFor('user').set('filteredUser', user);
console.log("userRoute")		
	},
});

Mdo.OrdersRoute = Mdo.HeaderRoute.extend({
	renderTemplate: function() {
		this._super();
		// Set the default render when first access
	    this.render('ordersIndex', {		// the template to render
	    		into: 'application',// the template to render into
	      		outlet: 'body',     // the name of the outlet in that template
		});
	},
});

Mdo.OrdersIndexRoute = Mdo.OrdersRoute.extend({
	// This route must be declared when user clicks again on the "orders" menu item and we already in this menu.
});

Mdo.OrdersOrderLinesRoute = Mdo.HeaderRoute.extend({
	setupController: function(controller, model) {
		controller.fillOrder(model.orderId);
	},
	renderTemplate: function() {
		this._super();
		// Set the default render when first access
	    this.render('ordersOrderLines', {		// the template to render
	    		into: 'application',// the template to render into
	      		outlet: 'body',     // the name of the outlet in that template
		});
	},
});

Mdo.CashedOrdersRoute = Mdo.HeaderRoute.extend({
	renderTemplate: function() {
		this._super();
		// Set the default render when first access
	    this.render('cashedOrders', {		// the template to render
	    		into: 'application',// the template to render into
	      		outlet: 'body',     // the name of the outlet in that template
		});
	},
});

Mdo.LockedOrdersRoute = Mdo.HeaderRoute.extend({
	renderTemplate: function() {
		this._super();
		// Set the default render when first access
	    this.render('lockedOrders', {		// the template to render
	    		into: 'application',// the template to render into
	      		outlet: 'body',     // the name of the outlet in that template
		});
	},
});
