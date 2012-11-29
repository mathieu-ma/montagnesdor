/**
 * This file will contain all Mdo controllers.
 */
$(document).ready(function() {
	// This is the application controller. It is required by Mdo application for initial context.
	Mdo.ApplicationController = Ember.Controller.extend();
	
	Mdo.HeaderController = Ember.ObjectController.extend({
		content: [	{
						labelKey: "header.access.user",
						icons: {
							primary: "ui-icon-person"
						},
						click: function(event){
						}
					},
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
						labelKey: "header.access.locked.orders",
				    	icons: {
				            primary: "ui-icon-locked"
				        }
					}
		]
	});
});
