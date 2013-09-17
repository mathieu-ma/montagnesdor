'use strict';

Mdo.FocusViewUtils = {
	focusInterval: null,
	intervalDelay: 200,
	processFocus: function(jElementToBeFocus, intervalDelay) {
		var intervalDelay = intervalDelay || this.intervalDelay;
		// Clear old interval
		if (this.focusInterval) {
			window.clearInterval(this.focusInterval);
		}
		var focus = Mdo.FocusObject.create({jElementToBeFocus: jElementToBeFocus, intervalDelay: intervalDelay});
		// store the focusInterval for clearing later.
		this.focusInterval = focus.get('focusInterval');
	},
};

Mdo.FocusObject = Ember.Object.extend({
	focusInterval: null,
	intervalDelay: 2,
	jElementToBeFocus: null,
	init: function() {
		var self = this;
		self._processMdoFocusInterval();
		if (self.jElementToBeFocus) {
			self.jElementToBeFocus.blur(function() {
				// Clear interval if focus lost
				window.clearInterval(self.focusInterval);
			});
		}
	},
	_processMdoFocusInterval: function() {
		var self = this;
		self.focusInterval = window.setInterval(function() {
 			if (self.jElementToBeFocus) {
 				self.jElementToBeFocus.focus();
 			}
 		},
 		self.intervalDelay);
	}	
});
