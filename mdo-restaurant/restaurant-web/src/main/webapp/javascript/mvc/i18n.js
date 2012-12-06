/**
 * This file will contain all Mdo i18n.
 */
$(document).ready(function() {
	Mdo.I18n = Ember.Object.extend();
	Mdo.I18n.cache = Ember.Map.create();

	var keys = {	
			"keyArgsMap" : 	{
//				"header.access.user": null,
//				"header.access.orders": null,
//				"header.access.cashed.orders": null,
//				"header.access.locked.orders": null
			}
	};
	$.mdoI18n.properties(keys);
	Mdo.I18n.prop = function(key, args, targetObject, targetAction) {
		// Create new key message and connect to server.
		var newKeys = {	
				keyArgsMap : {
				}
		};
		newKeys.keyArgsMap[key] = args;
		// Get from Mdo Ember i18n cache.
		var label = Mdo.I18n.cache.get(key);
		if (!label) {
			// Not in the cache.
			label = Mdo.Label.create();
			// Set in the cache.
			Mdo.I18n.cache.set(key, label);
		}
		if (targetObject && targetAction) {
			// Add observer to the desire observers.
			label.addObserver('label', targetObject, targetAction);
		}

		if ($.mdoI18n.map[key] && $.mdoI18n.map[key].state == 1) {
			// Label is already in Mdo jQuery i18n plug-in cache
			//label.set("label", $.mdoI18n.prop(key));
			label.set("counter", label.counter + 1);
		} else {
			// Connect to the server if needed
			$.mdoI18n.properties(newKeys, function() {
				// This will call after Ajax complete
				// The label will be changed and observers will be notified.
				//label.set("label", $.mdoI18n.prop(key));
				label.set("labelKey", key);
			});
		}
		return key;
	};
	Mdo.I18n.prop.removeObserver = function(key, targetObject, targetAction) {
		// Get from Mdo Ember i18n cache.
		var label = Mdo.I18n.cache.get(key);
		if (label && targetObject && targetAction) {
			// Add observer to the desire observers.
			label.removeObserver('label', targetObject, targetAction);
		}
	};
});