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
		gotoOrders: Ember.Route.transitionTo('orders.index'),
		gotoCashedOrders: Ember.Route.transitionTo('cashedOrders'),
		gotoLockedOrders: Ember.Route.transitionTo('lockedOrders'),
		gotoDateDialog: Ember.Route.transitionTo('headerOrder.dateDialog'),
		gotoBackToNumber: Ember.Route.transitionTo('headerOrder.backToNumber'),
		gotoCustomersNumber: Ember.Route.transitionTo('headerOrder.customersNumber'),
		gotoDisplayOrderLines: Ember.Route.transitionTo('orders.displayOrderLines'),
		index: Ember.Route.extend({
			route: '/',
			init: function(router) {
				// This event method is used to setup needed routes.
				// Because we use separated javascripts routes files and need to load them later.

				// The following routes classes are set in a dedicated files and depend on others classes in others files.
				// So we have to initialize them here because the javascripts files could be loaded in any order. 
				this.user = Mdo.UserRoute.create();
				this.headerOrder = Mdo.HeaderOrderRoute.create();
				this.orders = Mdo.OrdersRoute.create();

				this._super();
			},
			enter: function (router) {
				console.log("Mdo index");
	        },
	        connectOutlets: function(router, context) {
	        	var userId = $(Mdo.rootElement).data("user-id");
	        	
				// Ajax find dinner table call
				var url = "/find/user/" + userId;
				var deferredFunction = function() {
					return $.get(url);
				};

var deferred = $.Deferred();
//deferred.reject()
deferred.resolve(Mdo.userManager.find(userId))
deferredFunction = function() {return deferred;};

				$.when(deferredFunction()).done(function(user) {
					// Initialize Mdo application.
					
					// i18n setting for date picker.
					$.datepicker.setDefaults($.datepicker.regional[user.get('selectedLanguageIso2')]);

					var userController = router.get('userController')
		        	var headerOrderController = router.get('headerOrderController');
					userController.set('user', user);
		        	headerOrderController.set('user', user);
					
					router.get('applicationController').connectOutlet('header', 'header');
		        	var headerController = router.get('headerController');
		        	headerController.connectOutlet('headerDateTime', 'headerDateTime');
		        	var headerLanguagesController = router.get('headerLanguagesController');
		        	headerController.connectOutlet('headerLanguages', 'headerLanguages', headerLanguagesController.languages(user.get("locales")));
		        	var headerButtonsController = router.get('headerButtonsController');
		        	headerController.connectOutlet('headerButtons', 'headerButtons', headerButtonsController.allButtons());
		        	headerController.connectOutlet('headerOrder', 'headerOrder');
		        	
		        	var headerDateTimeController = router.get('headerDateTimeController');
		        	headerDateTimeController.startDateTime(user.datePattern);
		        	
		        	headerDateTimeController.connectControllers('headerOrder');
		        	headerButtonsController.connectControllers('headerOrder');

				}).fail(function() {
					// Redirect to login
					window.location.href = "/login";
				});

	        },
			// STATES
			user: null,
			orders: null,
			cashedOrders: Mdo.CashedOrdersRoute,
			lockedOrders: Mdo.LockedOrdersRoute,
			headerOrder: null,
		}),
	});
});