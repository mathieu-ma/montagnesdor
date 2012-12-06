/**
 * This is the definition of the Mdo router.
 * It must be declared at last in order to initialize the whole Mdo application.
 */
$(document).ready(function() {

	/**
	 * User route. It must be declared before the Mdo.Router because Mdo.Router uses it. 
	 */
	Mdo.UserRoute = Ember.Route.extend({
		route: '/',
		enter: function (router) {
			console.log("The user sub-state was entered.");
        },
		connectOutlets: function(router, context) {
        	// Insert HeaderView in header outlet with default HeaderController content.
			var headerController = router.get('headerController');
			headerController.refreshButtons("user");
	    	// Insert UserView in body outlet with OrdersController content. 
	    	router.get('applicationController').connectOutlet('body', 'user', {mma: "user"});
	    }		
	}); 

	/**
	 * Orders route. It must be declared before the Mdo.Router because Mdo.Router uses it. 
	 */
	Mdo.OrdersRoute = Ember.Route.extend({
		route: '/orders',
		enter: function (router) {
			console.log("The orders sub-state was entered.");
        },
		connectOutlets: function(router, context) {
        	// Insert HeaderView in header outlet with default HeaderController content.
			var headerController = router.get('headerController');
			headerController.refreshButtons("orders");
	    	// Insert OrdersView in body outlet with OrdersController content. 
	    	router.get('applicationController').connectOutlet('body', 'orders', {mma: "orders"});
	    }		
	}); 
	
	/**
	 * Cashed Orders route. It must be declared before the Mdo.Router because Mdo.Router uses it. 
	 */
	Mdo.CashedOrdersRoute = Ember.Route.extend({
		route: '/cashed/orders',
		enter: function (router) {
			console.log("The cashed orders sub-state was entered.");
        },
		connectOutlets: function(router, context) {
        	// Insert HeaderView in header outlet with default HeaderController content.
			var headerController = router.get('headerController');
			headerController.refreshButtons("cashedOrders");
	    	// Insert OrdersView in body outlet with OrdersController content. 
	    	router.get('applicationController').connectOutlet('body', 'cashedOrders', {mma: "cashed orders"});
	    }		
	}); 

	/**
	 * Locked Orders route. It must be declared before the Mdo.Router because Mdo.Router uses it. 
	 */
	Mdo.LockedOrdersRoute = Ember.Route.extend({
		route: '/locked/orders',
		enter: function (router) {
			console.log("The locked orders sub-state was entered.");
        },
		connectOutlets: function(router, context) {
        	// Insert HeaderView in header outlet with default HeaderController content.
			var headerController = router.get('headerController');
			headerController.refreshButtons("lockedOrders");
	    	// Insert OrdersView in body outlet with OrdersController content. 
	    	router.get('applicationController').connectOutlet('body', 'lockedOrders', {mma: "locked orders"});
	    }		
	}); 

	Mdo.Router = Ember.Router.extend({
		enableLogging:  true,
		root: Ember.Route.extend({
			// EVENTS
			gotoUser: Ember.Route.transitionTo('user'),
			gotoOrders: Ember.Route.transitionTo('orders'),
			gotoCashedOrders: Ember.Route.transitionTo('cashedOrders'),
			gotoLockedOrders: Ember.Route.transitionTo('lockedOrders'),
			index: Ember.Route.extend({
				route: '/',
				enter: function (router) {
					console.log("Mdo index");
		        },
		        connectOutlets: function(router, context) {
		        	// Insert HeaderView in header outlet with default HeaderController content.
					var headerController = router.get('headerController');
					headerController.initButtons("user");
		        	router.get('applicationController').connectOutlet('header', 'header');
		        	router.get('headerController').connectOutlet('headerOrder', 'headerOrder');
		        	router.get('headerOrderController').connectOutlet('headerOrderNumber', 'headerOrderNumber');
		        	router.get('headerOrderController').connectOutlet('headerOrderCustomersNumber', 'headerOrderCustomersNumber');
		        },
				// STATES
				user: Mdo.UserRoute,
				orders: Mdo.OrdersRoute,
				cashedOrders: Mdo.CashedOrdersRoute,
				lockedOrders: Mdo.LockedOrdersRoute,
			}),
		})
	});

	/**
	 * A call to Mdo.initialize() has always been the last line of code.
	 * The function of this call is multiple:
	 * 1) It runs .create() on the Router instance and saves it as Mdo.router.
	 * 2) It iterates through the controller and view namespaces and, for each class found, 
	 *    it executes .create() on the class and sets that instance as a property on Mdo.router
	 * 3) Each created view is given a controller property that corresponds to its controller 
	 *    (thus setting up the context to be helpful by default so that templates look in the right place for data).
	 */
	Mdo.initialize();
});
