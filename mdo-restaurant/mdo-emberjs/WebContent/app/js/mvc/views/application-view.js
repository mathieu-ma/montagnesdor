'use strict';

/**
 * This file will contain the Mdo application view.
 * It is required by Mdo application for initial context.
 */
Mdo.ApplicationView = Ember.View.extend({
	templateName: "application",
	classNames: ['application'],
	focusInterval: null,
	init: function() {
		this._super();
		//this.startFocus();
	},
	didInsertElement: function() {
		$("#menu, #header, #footer").mdoResizable();
	},
	startFocus: function(jElementToBeFocus) {
		var self = this;
		if (self.focusInterval) {
			window.clearInterval(self.focusInterval);
		}
		this.focusInterval = window.setInterval(function() {
			if (!jElementToBeFocus) {
				if (self.$()) {
					jElementToBeFocus = $(':text', self.$()); 
				}
			} 
			if (jElementToBeFocus) {
				jElementToBeFocus.focus();
			}
		},
		1000);
	},
});

