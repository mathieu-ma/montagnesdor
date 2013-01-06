/**
 * This file will contain all Mdo views.
 */
$(document).ready(function() {
	Mdo.HeaderOrderView = Ember.ContainerView.extend({
		tagName: 'form',
		init: function() {
			this.get('childViews').pushObjects([
                        this.headerOrderNumberLabel,
                        this.headerOrderNumberText,
                        this.headerOrderCustomersNumberLabel,
                        this.headerOrderCustomersNumberText,
                        this.headerOrderTakeawayLabel,
                        this.headerOrderMergeAutoCheckbox,
                        this.headerOrderMergeAutoLabel,
            ]);
			this._super();
		},
		destroy: function() {
			this._super();
		},
		headerOrderNumberLabel: Mdo.LabelView.extend({
			labelKey: "header.order.number",
		}),
		headerOrderNumberText: Ember.TextField.extend({
			classNames: ['ui-widget-content'],
			classNameBindings: ['labelClass'],
			labelClass: "",
			attributeBindings: ['value', 'disabled'],
			value: "",
			disabled: false,
			step: 0,
			didValueChange: function(sender, key) {
				// Default values for both 2 cases:
				// Case 1) First entry.
				// Case 2) Back from customersNumber text field.
				var disabled = false;
				var labelClass = "";
				var step = this.get('controller.step');
				if (this.get('controller.headerOrder.number') && step > this.step) {
					// In case of success getting dinner table header details. 
					disabled = true;
					labelClass = 'no-border';
				}
				this.set('value', this.get('controller.headerOrder.number'));
				this.set('disabled', disabled);
				this.set('labelClass', labelClass);
				if (this.$()) {
					this.$().focus();
				}
			}.observes('controller.headerOrder.number', 'controller.headerOrder.customersNumber', 'controller.step'),
			didStepChange: function() {
				var step = this.get('controller.step');
				if (step != this.step) {
					this.set('disabled', true);
				}
			}.observes('controller.step'),
			keyUp: function(event) {
				var self = this;
				switch(event.keyCode) {     	  
					//13 == key Enter
					case 13 :
console.log('enter:');
						// Check the number and display Customers Number.
						self.get('controller.target').send('gotoCustomersNumber', {
							tableNumber: this.$().val()
						});
					return;
					//27 == key Esc
					case 27 :
console.log('Esc ' + $(this));
					return;
				};
				return false;
			},
			didInsertElement: function() {
				var self = this;
				self.$().focus();
			}
		}),
		headerOrderCustomersNumberLabel: Mdo.LabelView.extend({
			labelKey: "header.order.customers.number",
		}),
		headerOrderCustomersNumberText: Ember.TextField.extend({
			classNames: ['ui-widget-content'],
			classNameBindings: ['labelClass'],
			labelClass: "visibility-hidden",
			attributeBindings: ['value', 'disabled'],
			value: "",
			disabled: true,
			step: 1,
			didValueChange: function(sender, key) {
				var step = this.get('controller.step');
				// Default values for both 2 cases:
				// Case 1) First entry.
				// Case 2) Back to number text field.
				var disabled = true;
				var labelClass = 'visibility-hidden';
				var customersNumber = this.get('controller.headerOrder.customersNumber');
				if (key=='controller.headerOrder.customersNumber') {
					// Only set the value when this one changed
					this.set('value', customersNumber);
				}
				if (step >= this.step) {
					if (this.get('controller.headerOrder.number')) {
						disabled = false;
						labelClass = '';
						if (customersNumber && step > this.step) {
							disabled = true;
							labelClass = 'no-border';
						}
					}
				}
				this.set('disabled', disabled);
				this.set('labelClass', labelClass);
				if (this.$()) {
					this.$().focus();
				}
			}.observes('controller.headerOrder.number', 'controller.headerOrder.customersNumber', 'controller.step'),
			keyUp: function(event) {
				var self = this;
				switch(event.keyCode) {     	  
					//13 == key Enter
					case 13 :
						// Upate the customers number and display list of order lines.
						//self.get('controller.target').send('gotoSaveCustomersNumber', this.$().val());
						// Do not fire event to router because we want to save data to server.
						// So call save method from controller and save data 
						// then in the same controller, we fire event to router in order to display order lines.
						self.get('controller')['updateCustomersNumber'](this.$().val());
					return;
					//27 == key Esc
					case 27 :
						// Back to the number field.
						self.get('controller.target').send('gotoBackToNumber');
					return;
				};
				return false;
			},
		}),
		headerOrderTakeawayLabel: Mdo.LabelView.extend({
			labelKey: "header.order.takeaway",
			classNameBindings: ['labelClass'],
			labelClass: "visibility-hidden",
			toggleClass: false,
			clearInterval: null,
			blinkLabelClass: function() {
				var self = this;
				if (this.get('controller.headerOrder.takeaway')) {
					self.set('labelClass', 'header-takeaway-odd');
					this.clearInterval = window.setInterval(function() {
						var cssClass = "visibility-hidden";
						if (self.toggleClass) {
							cssClass = "header-takeaway-even";
						} else {
							cssClass = "header-takeaway-odd";
						}
						self.toggleClass = !self.toggleClass;
						self.set('labelClass', cssClass);
					},
					1000);
				} else {
					self.set('labelClass', 'visibility-hidden');
					window.clearInterval(this.clearInterval);
				}
			}.observes('controller.headerOrder.takeaway'),
		}),
		headerOrderMergeAutoCheckbox: Ember.Checkbox.extend({
			attributeBindings: ['checked'],
			checked: true,
		}),
		headerOrderMergeAutoLabel: Mdo.LabelView.extend({
			labelKey: "header.order.line.merge.auto",
		}),
		didInsertElement: function() {
			var self = this;
			// Focus the order number text field.
//			this.headerOrderNumberView.$().focus();
//			Em.View.views['number'].$().focus();
console.log(this.get('controller.headerOrder'))
$('#takeawayFalseTest').click(function() {
	self.set('controller.headerOrder.takeaway', false); 
});
		},
	});
});