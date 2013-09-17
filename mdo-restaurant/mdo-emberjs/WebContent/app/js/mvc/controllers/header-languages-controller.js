'use strict';
/**
 * This file will contain all Mdo controllers.
 */
	Mdo.HeaderLanguagesController = Ember.ArrayController.extend({
		model: [],
		languages: function(locales) {
			this.model.clear();
			var languages = this.model;
			languages.clear();
			var locales = locales;
			$.each(locales, function(index, value) {
				var language = Mdo.Language.create({
					languageIso2: index,
					id: value.id, 
					displayLanguage: value.displayLanguage,
					selected: value.selected, 
				});
//				if (value.selected) {
//					// Set Global variable for Mdo.selectedLocale.
//					Mdo.user.set('selectedLanguage', language);
//				}
				languages.pushObject(language);
			});
		},
		changeLanguage: function(lang) {
			var route = this.target.get('currentState.route');

console.log(this)			
alert(this)

			if (route=="/") {
				route = "";
			}
			window.location.href = "?lang=" + lang + "#" + route;
		}
	});
