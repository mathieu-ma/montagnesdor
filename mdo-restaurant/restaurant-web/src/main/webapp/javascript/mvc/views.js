/**
 * This file will contain all Mdo views.
 */
$(document).ready(function() {
	// This is the application view. It is required by Mdo application for initial context.
	Mdo.ApplicationView = Ember.View.extend({
		template: Ember.Handlebars.compile('<div id="header"><div id="header-resizable" class="mdo-resizable">{{outlet header}}</div></div><div id="menu"><div id="menu-resizable" class="mdo-resizable">{{outlet menu}}</div></div><div id="body"><div id="body-resizable" class="mdo-resizable">{{outlet body}}</div></div><div id="footer"><div id="footer-resizable" class="mdo-resizable">{{outlet footer}}</div></div>')
//		template: Ember.Handlebars.compile($("body").html())
	});
	
	Mdo.ButtonView = Ember.View.extend({
		tagName: 'button',
		didInsertElement: function() {
		    this.$().button({ 
		    	label: $.mdoI18n.prop(this.get('labelKey')),
		    	icons: this.get('icons')
		    });
		}
	});

	Mdo.HeaderView = Ember.ContainerView.extend({
		childViews: ['home', 'orders', 'cashedOrders', 'lockedOrders'],
		home: Mdo.ButtonView.create({
			labelKey: "header.access.user",
	    	icons: {
	            primary: "ui-icon-person"
	        },
	        click: function(event){
alert(this.get("labelKey"))	        	

	        }
		}),
		orders: Mdo.ButtonView.create({
			labelKey: "header.access.orders",
	    	icons: {
	            primary: "ui-icon-cart"
	        }
		}),
		cashedOrders: Mdo.ButtonView.create({
			labelKey: "header.access.cashed.orders",
	    	icons: {
	            primary: "ui-icon-document"
	        }
		}),
		lockedOrders: Mdo.ButtonView.create({
			labelKey: "header.access.locked.orders",
	    	icons: {
	            primary: "ui-icon-locked"
	        }
		}),
	});

});