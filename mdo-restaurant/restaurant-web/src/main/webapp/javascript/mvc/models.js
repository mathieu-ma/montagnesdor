	Mdo = Ember.Application.create({
		// autoinit default value is true so application calls initialize method at creation(init method)
		// and then we can not recall initialize method for initializing router.
		autoinit: false,
		rootElement: "#header-resizable",
		ready: function() {
			console.log("Created Mdo namespace");
		}
	});
