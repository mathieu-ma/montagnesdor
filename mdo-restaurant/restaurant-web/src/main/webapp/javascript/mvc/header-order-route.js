/**
 * This is the definition of the Mdo router.
 * It must be declared at last in order to initialize the whole Mdo application.
 */
$(document).ready(function() {

	/**
	 * Locked Orders route. It must be declared before the Mdo.Router because Mdo.Router uses it. 
	 */
	Mdo.HeaderOrderRoute = Ember.Route.extend({
		route: '/header/order',
		enter: function (router) {
			console.log("The header order sub-state was entered.");
        },
        index:  Mdo.Index.reopenClass({
            connectOutlets:  function(router, context){
//    			var userController = router.get('userController');
//    			var headerOrderController = router.get('headerOrderController');
//    			headerOrderController.setUser(userController.getUser());
            }
        }),
    	dateDialog: Ember.Route.extend({
    		route: '/date/dialog/:action',
        	deserialize:  function(router, context) {
    			return context.action;
        	},
    		serialize:  function(router, context) {
    			return {
    				// Replace :action in the url by the value of context.action. 
    				action: context
    			};
    		},
    		enter: function (router) {
    			console.log("The /date/dialog sub-state was entered.");
            },
    		connectOutlets: function(router, action) {
    			var controller = router.get('headerDateTimeController');
    			controller[action + 'DateDialog']();
    	    }		
    	}), 
        number: Ember.Route.extend({
        	route: '/number',
        	connectOutlets: function(router, context) {
        		
        	}
        }),
        customersNumber: Ember.Route.extend({
        	route: '/:restaurantId/:userAuthenticationId/table/header/by/number/:tableNumber',
        	connectOutlets: function(router, context) {
    			var controller = router.get('headerOrderController');
				controller.customersNumber(context.restaurantId, context.userAuthenticationId, context.tableNumber);
        	}
        }),
        backToNumber: Ember.Route.extend({
        	route: '/back/to/number',
        	connectOutlets: function(router, context) {
				// Reset the controller data.
				var controller = router.get('headerOrderController');
				controller.backToNumber();
        	}
        }),
        displayOrderLines: Ember.Route.extend({
        	route: '/display/order/lines/:id',
        	deserialize:  function(router, context) {
    			return context.id;
        	},
    		serialize:  function(router, context) {
    			return {
    				// Replace :customersNumber in the url by the value of context.number. 
    				id: context
    			};
    		},
        	connectOutlets: function(router, id) {
    			var controller = router.get('headerOrderController');
				controller.displayOrderLines(id);
		    	// Insert OrderLinesView in body outlet with OrderLinesController content. 
		    	//router.get('applicationController').connectOutlet('body', 'orderLines', {mma: "orders"});
//alert('displayOrderLines')        		
        	}
        }),
	}); 
	
});
