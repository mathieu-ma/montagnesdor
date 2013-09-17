'use strict';

	Mdo.HeaderLanguagesView = Ember.View.extend({
		templateName: "headerLanguages",
		didInsertElement: function() {
		}
	});
	
	Mdo.FlagView = Ember.View.extend({
		tagName: 'div',
		classNameBindings: ['flagClasses'],
		attributeBindings: ['title'],
		id: null,
		languageIso2: null,
		displayLanguage: null,
		selected: false,
		title: function() {
			return this.displayLanguage;
		}.property('displayLanguage'),
		flagClasses: function() {
			var result = "flag flag-" + this.get("languageIso2"); 
			var viewSelected = this.get("selected");
			if (viewSelected) {
				result += " flag-state-active";
			}
			return result;
		}.property("languageIso2", "selected"),
		didInsertElement: function() {
			var self = this;
			var controller = self.get('controller');
			if (!this.selected) {
				this.$().click(function() {
					//controller.changeLanguage(self.languageIso2);
					Mdo.I18n.reload();
				});
			}
		},
	});
