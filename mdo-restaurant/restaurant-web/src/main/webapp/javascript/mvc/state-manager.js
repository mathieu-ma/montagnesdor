/**
 * This is the definition of the Mdo router.
 * It must be declared at last in order to initialize the whole Mdo application.
 */
$(document).ready(function() {

	Mdo.Router = Ember.Router.extend({
		enableLogging:  true,
		init: function() {
			// This event method is used to setup needed routes.
			// Because we use separated javascripts routes files and need to load them later.
			
			// The following routes classes are set in a dedicated files and depend on others classes in others files.
			// So we have to initialize them here because the javascripts files could be loaded in any order. 
			this.root = Mdo.RootRoute.create();

			this._super();
		},
		root: null
	});
	
    Mdo.Index =  Ember.Route.extend({
        route: '/',
        enter: function ( router ){
          console.log("The generic index sub-state was entered.");
        },
        connectOutlets: function(router, context) {
        	router.send('gotoUser');
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
    			var controller = router.get('headerOrderController');
				controller.displayOrderLines(dinnerTableId);
	        	var headerButtonsController = router.get('headerButtonsController');
//				$.each(headerButtonsController.content.filterProperty("name", "orders"), function(index, button) {
//					button.set('selected', true);
//				});

		    	// Insert OrderLinesView in body outlet with OrderLinesController content. 
		    	router.get('applicationController').connectOutlet('body', 'headerOrder', controller.get('orderLines'));
//alert('displayOrderLines')        		
        	}
        }),
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
	    	// Insert OrdersView in body outlet with OrdersController content. 
	    	router.get('applicationController').connectOutlet('body', 'cashedOrders', {mma: "cashed orders"});
	    },
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
	    	// Insert OrdersView in body outlet with OrdersController content. 
	    	router.get('applicationController').connectOutlet('body', 'lockedOrders', {mma: "locked orders"});
	    }		
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
	// Loading images ui-icon-arrowrefresh-1-s, ui-icon-arrowrefresh-1-w, ui-icon-arrowrefresh-1-n , ui-icon-arrowrefresh-1-e
	var applicationLoading = null;
	applicationLoading = Ember.Object.create({
		clearSpinnerTimeout: null,
		iconDirsIndex: 0,
		iconDirs: ['s', 'w', 'n', 'e'],
		spinnerIcon: $("<span class='ui-icon'>&#160;</span>"),
		waitingDots: $("<span>&#160;</span>"),
		label: Mdo.LabelView.create({
			labelKey: "application.waiting.loading",
		}),
		waitingLabel: $("<label></label>"),
		init: function() {
			// The method appendTo will process automatically the following:
			// 1) Create the DOM element.
			// 2) Initialize the i18n label.
			this.label.appendTo(this.waitingLabel);
		},
		spinner: function(jContainer) {
			jContainer.html(this.spinnerIcon).append(this.waitingLabel).append(this.waitingDots);
			this.spinnerIcon.removeClass('ui-icon-arrowrefresh-1-' + this.iconDirs[this.iconDirsIndex]);
			this.iconDirsIndex = this.iconDirsIndex + 1;
			if (this.iconDirsIndex >= this.iconDirs.length) {
				this.iconDirsIndex = 0;
				this.waitingDots.html("");
			}
			this.spinnerIcon.addClass('ui-icon-arrowrefresh-1-' + this.iconDirs[this.iconDirsIndex]);
			this.waitingDots.html(this.waitingDots.html() + ".");
			var self = this;
			this.clearSpinnerTimeout = window.setTimeout(function() {
				self.spinner(jContainer);
			}, 200);
		},
		destroy: function() {
			// Destroy Mdo 18n Label event.
			this.label.destroy();
			window.clearTimeout(this.clearSpinnerTimeout);
			this._super();
		}
	});

	var waitingDialog = $("<div/>").dialog({
		modal: true,
		resizable: false,
		closeOnEscape: false,
		create: function(event, ui) {
			// Remove close button
			$(".ui-dialog-titlebar-close").hide();
		},		
		open: function(event, ui) {
			applicationLoading.spinner($(this));
		},
		close: function() {
			applicationLoading.destroy();
			// Remove the dialog elements
			// Note: this will put the original div element in the dom
			$(this).dialog("destroy");
            // Remove the left over element (the original div element)
			$(this).remove();
		}
	});
	
	// Include routes scripts.
	var routes = [
	              "root-route", 
	              "user-route", 
	              "header-order-route",
	              "orders-route",
	              ];
	// Include views scripts.
	var views = [
	                   "dialog-view", 
	                   "button-view", 
	                   "header-order-view", 
	                   "order-view", 
	                   ];
	// Include controllers scripts.
	var controllers = [
	                   "header-languages-controller", 
	                   "header-date-time-controller", 
	                   "header-buttons-controller", 
	                   "header-order-controller",
	                   "order-controller",
	                   ];
	// Include all in one.
	var scripts = routes.concat(views, controllers);
	var cachedScripts = [];
	$.each(scripts, function(key, value) {
		var url = "javascript/mvc/" + value + ".js";
		var cachedScript = $.cachedScript(url).done(function(script, textStatus) {
			console.log("Load " + url + " with " + textStatus + " status");
		});
		cachedScripts.push(cachedScript);
	});
	// To pass an array of values to any function that normally expects them to be separate parameters, use Function.apply.
	$.when.apply($, cachedScripts).done(function() {
		Ember.run.later(null, function() {
			
			Mdo.initialize();
			
			waitingDialog.dialog("close");
		}, 1000);
	});
});