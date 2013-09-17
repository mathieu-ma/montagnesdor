'use strict';

/**
 * This file will contain all Mdo views.
 */

	Mdo.LabelView = Ember.View.extend({
		tagName: 'label',
		attributeBindings: ['for'],
		'for': null,
		key: null,
		params: null,
		didInsertElement: function() {
			var self = this;
			Mdo.MessageObserver.create({key: this.key, callback: function(message) {
				if (self.$()) {
					self.$().html(message);	
				}
			}});
		},
	});
