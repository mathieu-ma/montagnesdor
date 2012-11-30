/**
 * This file will contain all Mdo controllers.
 */
$(document).ready(function() {
	// This is the application controller. It is required by Mdo application for initial context.
	Mdo.ApplicationController = Ember.Controller.extend();
	
	
	Mdo.Items = [	
					{
						labelKey: "header.access.orders",
				    	icons: {
				            primary: "ui-icon-cart"
				        }
					},
					{
						labelKey: "header.access.cashed.orders",
				    	icons: {
				            primary: "ui-icon-document"
				        }
					},
		          	{
						labelKey: "header.access.user",
						icons: {
							primary: "ui-icon-person"
						},
						click: function(event) {
							alert(1)
						}
					},
					{
						labelKey: "header.access.locked.orders",
				    	icons: {
				            primary: "ui-icon-locked"
				        }
					}
				];
	
	Mdo.HeaderController = Ember.ObjectController.extend({
		content: [	
		          	{
						labelKey: "header.access.user",
						icons: {
							primary: "ui-icon-person"
						},
						click: function(event) {
							this.get('controller').gotoUser();
						}
					},
					{
						labelKey: "header.access.orders",
				    	icons: {
				            primary: "ui-icon-cart"
				        },
						click: function(event) {
							this.get('controller').gotoOrders();
						}
					},
					{
						labelKey: "header.access.cashed.orders",
				    	icons: {
				            primary: "ui-icon-document"
				        }
					},
					{
						labelKey: "header.access.locked.orders",
				    	icons: {
				            primary: "ui-icon-locked"
				        }
					}
				],
		gotoUser: function() {
console.log(this)			
			this.target.applicationController.connectOutlet('body', 'user', {mma: "user"});
		},
		gotoOrders: function() {
//			Ember.Route.transitionTo('orders')
			this.target.router.send('moveElsewhere');
//			Ember.Route.transitionTo('orders');
//			Mdo.get('router').send('orders');
//			this.target.applicationController.connectOutlet('body', 'orders', {mma: "orders"});
		}	
	});
	
	Mdo.UserController = Ember.ObjectController.extend({
	});
	
	Mdo.OrdersController = Ember.ObjectController.extend({
	});

});
