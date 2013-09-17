/**
 * This file will contain all Mdo views.
 */
	Mdo.ButtonView = Ember.View.extend({
		tagName: 'button',
		labelKey: null,
		priIcon: null,
		secIcon: null,
		didInsertElement: function() {
			var self = this;
			var jSelf = self.$();
			// Create jQuery ui button and get the icons
			var icons = jSelf.button().button( "option", "icons" );
			// Manage button icons
			icons.primary = self.priIcon;
			icons.secondary = self.secIcon;
			jSelf.button("option", "icons", icons);
			// Manage button label
			Mdo.MessageObserver.create({key: this.labelKey, callback: function(label) {
				jSelf.button("option", "label", label);	
			}});
		},
	});
	
	Mdo.HeaderButtonsView = Ember.View.extend({
		templateName: "headerMenus",
		didInsertElement: function() {
			$("#header-menus").buttonset();
		},
	});
