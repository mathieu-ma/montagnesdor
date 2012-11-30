/**
 * This file will contain Mdo application template.
 */
$(document).ready(function() {
	var src = '<div id="header"><div id="header-resizable" class="mdo-resizable">{{outlet header}}</div></div><div id="menu"><div id="menu-resizable" class="mdo-resizable">{{outlet menu}}</div></div><div id="body"><div id="body-resizable" class="mdo-resizable">{{outlet body}}</div></div><div id="footer"><div id="footer-resizable" class="mdo-resizable">{{outlet footer}}</div></div>';
	Ember.TEMPLATES["application"] = Em.Handlebars.compile(src);
});
