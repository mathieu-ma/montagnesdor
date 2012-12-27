/**
 * This is the definition of the Mdo router.
 * It must be declared at last in order to initialize the whole Mdo application.
 */
$(document).ready(function() {

	Mdo.RootRoute = Ember.Route.extend({
		// EVENTS
		//i18n/modify/language?lang=fr
		gotoUser: Ember.Route.transitionTo('user'),
		gotoChangeLanguage: Ember.Route.transitionTo('user.changeLanguage'),
		gotoOrders: Ember.Route.transitionTo('orders'),
		gotoCashedOrders: Ember.Route.transitionTo('cashedOrders'),
		gotoLockedOrders: Ember.Route.transitionTo('lockedOrders'),
		gotoDateDialog: Ember.Route.transitionTo('headerOrder.dateDialog'),
		gotoBackToNumber: Ember.Route.transitionTo('headerOrder.backToNumber'),
		gotoCustomersNumber: Ember.Route.transitionTo('headerOrder.customersNumber'),
		gotoDisplayOrderLines: Ember.Route.transitionTo('headerOrder.displayOrderLines'),
		index: Ember.Route.extend({
			route: '/',
			init: function(router) {
				// This event method is used to setup needed routes.
				// Because we use separated javascripts routes files and need to load them later.

				// The following routes classes are set in a dedicated files and depend on others classes in others files.
				// So we have to initialize them here because the javascripts files could be loaded in any order. 
				this.user = Mdo.UserRoute.create();
				this.headerOrder = Mdo.HeaderOrderRoute.create();

				this._super();
			},
			enter: function (router) {
				console.log("Mdo index");
	        },
	        connectOutlets: function(router, context) {
var userId = 'context.userId';
				// Global variable for Mdo.user.
				Mdo.user = Mdo.userManager.find(userId);
				// i18n setting for date picker.
				$.datepicker.setDefaults($.datepicker.regional[Mdo.user.get('selectedLanguageIso2')]);

				router.get('applicationController').connectOutlet('header', 'header');
	        	var headerController = router.get('headerController');
	        	headerController.connectOutlet('headerDateTime', 'headerDateTime');
	        	var headerLanguagesController = router.get('headerLanguagesController');
	        	headerController.connectOutlet('headerLanguages', 'headerLanguages', headerLanguagesController.languages(Mdo.user.get("locales")));
	        	var headerButtonsController = router.get('headerButtonsController');
	        	headerController.connectOutlet('headerButtons', 'headerButtons', headerButtonsController.allButtons());
	        	headerController.connectOutlet('headerOrder', 'headerOrder');
	        	
	        	router.get('headerDateTimeController').startDateTime(Mdo.user.datePattern);
	        	
	        	router.get('headerOrderController').connectControllers('headerDateTime');
	        	headerButtonsController.connectControllers('headerOrder');
	        },
			// STATES
			user: null,
			orders: Mdo.OrdersRoute,
			cashedOrders: Mdo.CashedOrdersRoute,
			lockedOrders: Mdo.LockedOrdersRoute,
			headerOrder: null,
		}),
	});
});