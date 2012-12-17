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
	    	// Insert OrdersView in body outlet with OrdersController content. 
	    	router.get('applicationController').connectOutlet('body', 'lockedOrders', {mma: "locked orders"});
	    }		
	}); 

	Mdo.Router = Ember.Router.extend({
		enableLogging:  true,
		root: Ember.Route.extend({
			// EVENTS
			//i18n/modify/language?lang=fr
			gotoUser: Ember.Route.transitionTo('user'),
			gotoOrders: Ember.Route.transitionTo('orders'),
			gotoCashedOrders: Ember.Route.transitionTo('cashedOrders'),
			gotoLockedOrders: Ember.Route.transitionTo('lockedOrders'),
			gotoOpenDialog: Ember.Route.transitionTo('openDialog'),
			index: Ember.Route.extend({
				route: '/',
				enter: function (router) {
					console.log("Mdo index");
					// Init i18n
					//Mdo.I18n.init();
					
		        },
		        connectOutlets: function(router, context) {
					router.get('applicationController').connectOutlet('header', 'header');
		        	router.get('headerController').connectOutlet('headerDateTime', 'headerDateTime');
		        	router.get('headerController').connectOutlet('headerLanguages', 'headerLanguages');
		        	router.get('headerController').connectOutlet('headerButtons', 'headerButtons', router.get('headerButtonsController').allButtons());
		        	router.get('headerController').connectOutlet('headerOrder', 'headerOrder');
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
	
	// Get the user details with Ajax. 
	// On Ajax success, call the Mdo.initialize() method and close the waiting dialog.
//	setTimeout(function() {
//		// Global variable for Mdo.user.
//		Mdo.user = Mdo.userManager.find(1);
//		// i18n setting
//		$.datepicker.setDefaults($.datepicker.regional[Mdo.user.get('selectedLanguageIso2')]);
//		Mdo.initialize();
//		waitingDialog.dialog("close");
//	}, 1000);
	
	Ember.run.later(null, function() {
		// Global variable for Mdo.user.
		Mdo.user = Mdo.userManager.find(1);
		// i18n setting
		$.datepicker.setDefaults($.datepicker.regional[Mdo.user.get('selectedLanguageIso2')]);
		Mdo.initialize();
		waitingDialog.dialog("close");
	}, 1000);
});
