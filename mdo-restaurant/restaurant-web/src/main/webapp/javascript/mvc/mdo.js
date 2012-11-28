$(document).ready(function() {
	Mdo.Router = Ember.Router.extend({
		enableLogging:  true,
		root: Ember.Route.extend({
			index: Ember.Route.extend({
				route: '/',
				enter: function (router){
					console.log("Mdo index");
		        },
			}),
			orders:  Ember.Route.extend({
				route: '/orders',
				enter: function (router){
					console.log("The orders sub-state was entered.");
		        },
			})
		})
	});

	Mdo.initialize();
});
