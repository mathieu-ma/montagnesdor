Mdo = Ember.Application.create({
	ready: function() {
		console.log("Created App namespace");
	}
});

//$(document).ready(function() {
	Mdo.Router = Ember.Router.extend({
		enableLogging:  true,
		root: Ember.Route.extend({
			index: Ember.Route.extend({
				route: '/'
			}),
			orders:  Ember.Route.extend({
				route: '/orders',
				enter: function (router){
					console.log("The shoes sub-state was entered.");
		        },
			})
		})
	});

	Mdo.initialize();
//});
