/**
 * This file will contain all Mdo i18n.
 */
$(document).ready(function() {
	Mdo.I18n = Ember.Object.extend();
	Mdo.I18n.cache = Ember.Map.create();

	Mdo.I18n.prop = function(key, args, targetObject, targetAction) {
		// Create new key message and connect to server.
		var newKeys = {	
				keyArgsMap : {
				}
		};
		newKeys.keyArgsMap[key] = args;
		var label = Mdo.I18n.cache.get(key);
		if (!label) {
			label = Mdo.Label.create();
			Mdo.I18n.cache.set(key, label);
		}
		if (targetObject && targetAction) {
			// Add observer to the desire observers.
			label.addObserver('label', targetObject, targetAction);
		}
		// Connect to the server of needed
		$.mdoI18n.properties(newKeys, function() {
			// This will call after Ajax complete
			// The label will be changed and observers will be notified.
			label.set("label", $.mdoI18n.prop(key));
		});
		return key;
	};
	
	// In order to use i18n in Handlebars
	Handlebars.registerHelper('i18n', function(key) {
		var length = arguments.length-1;
		var args = [];
		for(var i=1; i<length; i++) {
			args.push(arguments[i]);
		}
		var result = key;
		if ($.mdoI18n != undefined) {
			// Create new key message and connect to server.
			var newKeys = {	
					keyArgsMap : {
					}
			};
			newKeys.keyArgsMap[key] = args;
			
			result = Mdo.I18n.prop(key, args);
		} 		
		result = new Handlebars.SafeString(result);
		return result;
	});
});