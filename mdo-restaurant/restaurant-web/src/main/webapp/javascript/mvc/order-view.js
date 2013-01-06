/**
 * This file will contain all Mdo views.
 */
$(document).ready(function() {
	Mdo.OrderLineTextView = Ember.TextField.extend({
		orderLines: null,
		classNames: ['ui-widget-content'],
		classNameBindings: ['textClass'],
		textClass: function() {
			var result = 'no-border ui-widget-content';
			if (this.focus) {
				result = '';
			}
			return result;
		}.property('focus'),
		lastLine: false,
		attributeBindings: ['readOnly'],
		readOnly: function() {
			return !this.focus;
		}.property('focus'),
		focus: false,
		table: function() {
			return this.get('parentView.table');
		}.property(),
		tbody: function() {
			return this.get('parentView.tbody');
		}.property(),
		rowIndex: function() {
			// this.$() == jQuery text field. 
			// this.$().parent() ==  jQuery td.
			// this.$().parent().parent() == jQuery tr
			return this.$().parent().parent()[0].sectionRowIndex;
		}.property(),
		lastRow: function() {
			// this.$() == jQuery text field. 
			// this.$().parent() ==  jQuery td.
			// this.$().parent().parent() == jQuery tr
			var rowsLength = this.get('tbody')[0].rows.length;
alert(this.$().parent().parent()[0].tbody + "==" + rowsLength)
			return false;
		}.property(),
		cellIndex: function() {
			var result = 0;
			// this.$() == jQuery text field. 
			// this.$().parent() ==  jQuery td.
			return this.$().parent()[0].cellIndex;
		}.property(),
		didInsertElement: function() {
			if (this.lastLine) {
				this.$().focus();
			}
		},
		focusOut: function(event) {
			this.set('focus', false);
		},
		focusIn: function(event) {
			this.set('focus', true);
		},
		keyUp: function(event) {
			var self = this;
			switch(event.keyCode) {     	  
				//13 == key Enter
				case 13 :
				return;
				case 27 :
					//this.esc(jCell);
				return;
				//38 == key Up
				case 38 :
					self.set('focus', false);
console.log(self.get('parentView').$().find("table"))
//self.set('lastRow', true);
console.log(self.get('table')[0].rows)
console.log(self.get('tbody')[0].rows)
console.log(self.$().parent().parent())
alert(self.get('lastRow'))
					//this.up(jCell);
				return;
				//40 == key Down
				case 40 :
					//this.down(jCell);
				return;
			};
			return false;
		},
	});
	Mdo.OrderLinesView = Ember.View.extend({
		templateName: "orderLines",
		tagName: "form",
		layout: Ember.Handlebars.compile("<table id='order-lines'>{{yield}}</table>"),
		table: function() {
			return this.$().find("table");
		}.property(),
		tbody: function() {
			return this.$().find("tbody");
		}.property(),
		didInsertElement: function() {
			var self = this;
			$('#addOrderLineTest').click(function() {
console.log(self.get('controller.content').length)
self.get('controller').pushObject({quantity: 2, code: 13, label: "Salade aux crabes", price: 2.5, amount: 5});


			});
		},
	});
});