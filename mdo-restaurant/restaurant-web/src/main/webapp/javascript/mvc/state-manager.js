/**
 * This is the definition of the Mdo router.
 * It must be declared at last in order to initialize the whole Mdo application.
 */
$(document).ready(function() {
	
	/**
	 * Orders route. It must be declared before the Mdo.Router because Mdo.Router uses it. 
	 */
	Mdo.OrdersRoute = Ember.Route.extend({
		route: '/orders',
		enter: function (router) {
			console.log("The orders sub-state was entered.");
        }
	}); 
	
	Mdo.Router = Ember.Router.extend({
		enableLogging:  true,
		root: Ember.Route.extend({
			index: Ember.Route.extend({
				route: '/',
				enter: function (router) {
					console.log("Mdo index");
		        },
		        connectOutlets:  function(router, context){
		        	router.get('applicationController').connectOutlet('header', 'header', { greeting: "My Ember App" });
		        }		        
			}),
			orders: Mdo.OrdersRoute  
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
