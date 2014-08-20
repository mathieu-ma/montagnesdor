/**
 * This file will contain all Mdo i18n.
 */
	Mdo.I18n = Ember.Object.extend();
	Mdo.I18n.cache = Ember.Map.create();

	// Initialize for testing
	$.mdoI18n.map["date.time.dialog.title"] = {state: 1, value: "Changement de date"};
	$.mdoI18n.map["date.time.dialog.date"] = {state: 1, value: "Nouvelle date"};
	$.mdoI18n.map["date.time.dialog.password"] = {state: 1, value: "Mot de passe"};
	$.mdoI18n.map["common.cancel"] = {state: 1, value: "Annuler"};
	$.mdoI18n.map["common.save"] = {state: 1, value: "Sauvegarder"};

	var keys = {	
			keyArgsMap : 	{
//				"header.access.user": null,
//				"header.access.orders": null,
//				"header.access.cashed.orders": null,
//				"header.access.locked.orders": null
			}
	};
	$.mdoI18n.properties(keys);

	Mdo.MessageObservable = Ember.Object.extend({
		key: null,
		params: null,
		counter: 0,
		/**
		 * The message is a computed property and depends on the 2 properties key and counter.
		 * The key property is used to find the message.
		 * The counter is only used for observing because we want the observer to be called whenever we need.
		 * See the Ember note on observable api:
		 * Note that if propertyKey is a computed property, the observer will be called when any of the property dependencies are changed, 
		 * even if the resulting value of the computed property is unchanged. 
		 * This is necessary because computed properties are not computed until get is called.
		 */
//index: 1,		
		message: function() {
			var result = $.mdoI18n.prop(this.key);
//console.log(this.index + ") " + this.counter + "==" + this.key + "==" + result + "==>" + this)
//this.incrementProperty("index");
			return result;
		}.property("key", "counter"),
		init: function() {
			// "precomputing" message property because 
			// message is a computed property 
			// and the message will be observed.
			this.get('message');
		}
	});
	
	/**
	 * This class is an observer for the class Mdo.MessageObservable.
	 * 
	 * BE AWARE:
	 * if for example, we create 2 instances of this class that observe the same observable.
	 * At initialize time, we increment the observable counter property twice.
	 * So the observable instance will fire that the message has changed 2 times for the 2 observer instances. 
	 * And we have 2*2(= 4) call of the propertyValueChangeAck method.
	 * If we create n instances of this class that observe the same observable.
	 * At initialize time, we increment the observable counter property n times.
	 * So the observable instance will fire that the message has changed n times for the n observer instances. 
	 * And we have n*n call of the propertyValueChangeAck method.
	 */
	Mdo.MessageObserver = Ember.Object.extend({
		key: null,
		params: null,
		message: null,
		callback: function(propertyValue) {
			// Empty method.
		},
		propertyValueChangeAck: function(sender, property) {
			var propertyValue = sender.get(property);
			if (!propertyValue) {
				propertyValue = this.key;
			}
			// Here the property is "message"
			this.set(property, propertyValue);
			// Give a chance for the caller instance 
			// that creates this instance to do what it wants with it callback method.
			this.callback(propertyValue);
		},
		init: function() {
			Mdo.I18n.propAddObserver(this.key, this.params, this, this.propertyValueChangeAck);
		},
		destroy: function() {
			// Have to remove because when this view is deleted, the observable object did not know of it.
			Mdo.I18n.propRemoveObserver(this.key, this, this.propertyValueChangeAck);
		}
	});

	Mdo.I18n.propAddObserver = function(key, params, observer, observerCallback) {
		// Create new key message and connect to server.
		var newKeys = {	
				keyArgsMap : {
				}
		};
		newKeys.keyArgsMap[key] = params;
		// Get from Mdo Ember i18n cache.
		var observable = Mdo.I18n.cache.get(key);
		if (!observable) {
			// Not in the cache.
			observable = Mdo.MessageObservable.create({key: key, params: params});
			// Set in the cache.
			Mdo.I18n.cache.set(key, observable);
		}
		if (observer && observerCallback) {
			// Add observer to 'message' property with specified callback.
			observable.addObserver('message', observer, observerCallback);
		}
		if ($.mdoI18n.map[key] && $.mdoI18n.map[key].state == 1) {
			// Label is already in Mdo jQuery i18n plug-in cache
			// The label.counter will be changed and observers will be notified because of the computed label.label property changing.
			Ember.run.later(null, function() {
				observable.incrementProperty("counter");
			}, 2000);

		} else {
			// Connect to the server if needed
			$.mdoI18n.properties(newKeys, function() {
				// This will call after Ajax complete
				// The label.counter will be changed and observers will be notified because of the computed label.label property changing.
				observable.incrementProperty("counter");
			});
		}
		return key;
	};
	Mdo.I18n.propRemoveObserver = function(key, observer, observerCallback) {
		// Get from Mdo Ember i18n cache.
		var observable = Mdo.I18n.cache.get(key);
		if (observable && observer && observerCallback) {
			// Add observer to the desire observers.
			observable.removeObserver('message', observer, observerCallback);
		}
	};

	/**
	 * This function is used to reload all keys from server.
	 * It is useful when changing the language:
	 * 1) Change the language with Ajax call.
	 * 2) Wait for the positive Ajax response.
	 * 3) Call this function to rebuild the cache with new language.
	 */
	Mdo.I18n.reload = function(languageIso2) {
		
		var cache = Mdo.I18n.cache;

		// Keys to be reload after reseting caches.
		var keysToBeReloaded = {	
				keyArgsMap : 	{
				}
		};
		cache.forEach(function(key) {
			// TODO Change the cache structure in order to take into account the key parameters.
			var observable = cache.get(key);
			// Build the previous keys to be reloaded.
			keysToBeReloaded.keyArgsMap[key] = observable.get("params");
		});
		
		// Reset the level 1 cache(the Mdo-Jquery cache).
		$.mdoI18n.map = {};
		
		// TODO: Ajax call: Change the server language. 
		// Simulate the language changed for TESTING
		$.mdoI18n.map["date.time.dialog.title"] = {state: 1, value: "Change the date"};
		$.mdoI18n.map["date.time.dialog.date"] = {state: 1, value: "New date"};
		$.mdoI18n.map["date.time.dialog.password"] = {state: 1, value: "Password"};
		$.mdoI18n.map["common.cancel"] = {state: 1, value: "Cancel"};
		$.mdoI18n.map["common.save"] = {state: 1, value: "Save"};
		$.mdoI18n.map["header.access.user"] = {state: 1, value: "User"};
		$.mdoI18n.map["header.access.orders"] = {state: 1, value: "Orders Management"};
		$.mdoI18n.map["header.access.cashed.orders"] = {state: 1, value: "Cashed Orders Management"};
		$.mdoI18n.map["header.access.locked.orders"] = {state: 1, value: "Locked Orders Management"};

		// Reload the existing keys from server.
		$.mdoI18n.properties(keysToBeReloaded);
	
		Ember.run.later(null, function() {
			// Refresh the cache.
			cache.forEach(function(key) {
				var observable = cache.get(key);
				// Fire change to all observers
				observable.incrementProperty("counter");
			});
		}, 2000);

	};