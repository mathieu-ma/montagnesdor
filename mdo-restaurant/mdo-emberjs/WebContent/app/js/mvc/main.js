'use strict';
/**
 * This is the Ember application name space for the Mdo application.
 * It must be declared at first and out of any javascript block 
 * because the variable Mdo must be accessible by others objects. 
 * It is an instance of Ember.Application is the starting point.
 * It is global Ember variable.
 */
window.Mdo = Ember.Application.create({
	rootElement: "#main-application",
	userEntryDate: null,
	userAuthentication: null,
	ready: function() {
		this.userEntryDate = Mdo.UserEntryDate.create();
		console.log("Created Mdo namespace");
	},
	LOG_TRANSITIONS: true,
});
