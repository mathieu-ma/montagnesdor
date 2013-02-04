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
        },
        index: Ember.Route.extend({
    		route: '/',
    		enter: function (router) {
    			console.log("The orders sub-state was entered.");
            },
            connectOutlets: function(router, context) {
    	    	// Insert OrdersView in body outlet with OrdersController content. 
    	    	router.get('applicationController').connectOutlet('body', 'orders', {mma: "orders"});
            },
        }),
        displayOrderLines: Ember.Route.extend({
        	route: '/display/order/lines/:dinnerTableId',
        	connectOutlets: function(router, dinnerTableId) {
    			var headerOrderController = router.get('headerOrderController');
    			headerOrderController.displayOrderLines(dinnerTableId);
	        	var headerButtonsController = router.get('headerButtonsController');
//				$.each(headerButtonsController.content.filterProperty("name", "orders"), function(index, button) {
//					button.set('selected', true);
//				});

    			var orderLinesLoadingController = router.get('orderLinesLoadingController');
    			orderLinesLoadingController.set('orderLines', headerOrderController.get('orderLines'));
		    	// Insert OrderLinesView in body outlet with OrderLinesController content. 
		    	router.get('applicationController').connectOutlet('body', 'orderLinesLoading');
    			orderLinesLoadingController.connectOutlet('orderLines', 'orderLines');
	        	var orderLinesController = router.get('orderLinesController');
	        	orderLinesController.connectControllers('headerButtons');
        	}
        }),
	}); 
});