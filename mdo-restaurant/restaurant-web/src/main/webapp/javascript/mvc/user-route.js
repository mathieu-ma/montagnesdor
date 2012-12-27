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
	    	// Insert UserView in body outlet with OrdersController content. 
	    	router.get('applicationController').connectOutlet('body', 'user', {mma: "user"});
	    },
	});
	
});