/**
 * This file will contain all Mdo i18n.
 */
$(document).ready(function() {
	var keys = {	
			"keyArgsMap" : 	{
				"header.access.user": null,
				"header.access.orders": null,
				"header.access.cashed.orders": null,
				"header.access.locked.orders": null
			}
	};
	$.mdoI18n.properties(keys);
	
	// In order to use i18n in Handlebars
	Handlebars.registerHelper('i18n', function(key) {
		var length = arguments.length-1;
		var args = [];
		for(var i=1; i<length; i++) {
			args.push(arguments[i]);
		}
		var result = key;
		if ($.mdoI18n != undefined) {
			result = $.mdoI18n.prop(key, args);
		} 		
		return new Handlebars.SafeString(result);
	});
});