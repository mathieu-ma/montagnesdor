/**
 * This is the Ember application name space for the Mdo application.
 * It must be declared at first and out of any javascript block 
 * because the variable Mdo must be accessible by others objects. 
 */
Mdo = Ember.Application.create({
	// autoinit default value is true so application calls initialize method at creation(init method)
	// and then we can not recall initialize method for initializing router.
	autoinit: false,
	rootElement: "#main-application",
	ready: function() {
		console.log("Created Mdo namespace");
	}
});
