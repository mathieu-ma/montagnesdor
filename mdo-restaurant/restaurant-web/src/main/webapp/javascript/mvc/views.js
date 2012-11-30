/**
 * This file will contain all Mdo views.
 */
$(document).ready(function() {
	// This is the application view. It is required by Mdo application for initial context.
	Mdo.ApplicationView = Ember.View.extend({
		template: Ember.Handlebars.compile('application')
	});
	
	Mdo.ButtonView = Ember.View.extend({
		tagName: 'button',
		didInsertElement: function() {
		    this.$().button({ 
		    	// labelKey comes from HeaderView template.
		    	label: $.mdoI18n.prop(this.get('labelKey')),
		    	// icons comes from HeaderView template.
		    	icons: this.get('icons')
		    });
		}
	});
	
	Mdo.HeaderView = Ember.View.extend({
		templateName: "header"
	});

	Mdo.UserView = Ember.View.extend({
		templateName: "user"
	});

	Mdo.OrdersView = Ember.View.extend({
		templateName: "orders"
	});
});