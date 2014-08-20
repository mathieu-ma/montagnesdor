/**
 * This file will contain all Mdo views.
 */

	Mdo.HeaderOrderNumberText = Ember.TextField.extend({
		classNames: ['ui-widget-content'],
		classNameBindings: ['labelClass'],
		labelClass: "",
		attributeBindings: ['autocomplete'],
		pattern: "[A..Z,0..9]+",
		valueBinding: 'parentView.currentOrderNumber',
		currentStepBinding: 'parentView.currentStep',
		// autocomplete is used to not display old entries.
		autocomplete: "off",
		step: 0,
		resetHeaderOrderBinding: 'parentView.resetHeaderOrder',
		didCurrentStepChange: function(sender, key) {
			var currentStep = this.get('currentStep');
			var disabled = false;
			var labelClass = "";
			if (currentStep == this.step) {
				Mdo.FocusViewUtils.processFocus(this.$());
			} else {
				// In case of success getting dinner table header details. 
				disabled = true;
				labelClass = 'no-border';
			}
			this.set('disabled', disabled);
			this.set('labelClass', labelClass);
		}.observes('currentStep'),
		// Override the insertNewline Ember.TextSupport method
		insertNewline: function(event) {
			var self = this;
			//13 == key Enter
			if (self.value) {
				// Check the number and display Customers Number.
				self.get('parentView.controller').send("searchOrder");
			}
		},
		// Override the cancel Ember.TextSupport method
		cancel: function(event) {
			var self = this;
			//27 == key Esc
			if (self.get('value')) {
				// There are 2 observers for the resetHeaderOrder field: the controller the parent view.
				// 1) the controller will reset information about the header order.
				// 2) the parent view will only reset information about the text fields values: HeaderOrderNumberText and HeaderOrderCustomersNumberText.
				self.set('resetHeaderOrder', true);
			}
		},
	});

	Mdo.HeaderOrderCustomersNumberText = Ember.TextField.extend({
		classNames: ['ui-widget-content'],
		classNameBindings: ['labelClass'],
		labelClass: "visibility-hidden",
		pattern: "[1..9]{2}",
		disabled: true,
		valueBinding: 'parentView.currentOrderCustomersNumber',
		currentStepBinding: 'parentView.currentStep',
		resetHeaderOrderBinding: 'parentView.resetHeaderOrder',
		step: 1,
		didCurrentStepChange: function(sender, key) {
			var currentStep = this.get('currentStep');
			// Default values for both 2 cases:
			// Case 1) First entry.
			// Case 2) Back to number text field.
			var disabled = true;
			var labelClass = 'visibility-hidden';
			if (currentStep == this.step) {
				disabled = false;
				labelClass = "";
				Mdo.FocusViewUtils.processFocus(this.$());
			} else if (currentStep > this.step) {
				labelClass = 'no-border';
			}
			this.set('disabled', disabled);
			this.set('labelClass', labelClass);
		}.observes('currentStep'),
		// Override the insertNewline Ember.TextSupport method
		insertNewline: function(event) {
			//13 == key Enter
			var self = this;
			// Upate the customers number and display list of order lines.
			//self.get('controller.target').send('gotoSaveCustomersNumber', this.$().val());
			// Do not fire event to router because we want to save data to server.
			// So call save method from controller and save data 
			// then in the same controller, we fire event to router in order to display/show order lines.
			self.get('parentView.controller').send("saveOrder", self.$().val());
		},
		// Override the cancel Ember.TextSupport method
		cancel: function(event) {
			var self = this;
			//27 == key Esc
			self.set('currentStep', 0);
		},
	});
	
	Mdo.HeaderOrderView = Ember.ContainerView.extend({
		tagName: 'form',
		currentStepBinding: 'controller.currentStep',
		resetHeaderOrderBinding: 'controller.resetHeaderOrder',
		currentOrderNumberBinding: 'controller.currentOrderNumber',
		currentOrderCustomersNumberBinding: 'controller.currentOrderCustomersNumber',
		currentTakeawayBinding: 'controller.currentTakeaway',
		init: function() {
			this._super();
			this.pushObjects([
                        this.headerOrderNumberLabel,
                        this.headerOrderNumberText,
                        this.headerOrderCustomersNumberLabel,
                        this.headerOrderCustomersNumberText,
                        this.headerOrderTakeawayLabel,
                        this.headerOrderMergeAutoCheckbox,
                        this.headerOrderMergeAutoLabel,
            ]);
		},
		headerOrderNumberLabel: Mdo.LabelView.create({
			key: "header.order.number",
		}),
		headerOrderNumberText: Mdo.HeaderOrderNumberText.create(),
		headerOrderCustomersNumberLabel: Mdo.LabelView.create({
			key: "header.order.customers.number",
		}),
		headerOrderCustomersNumberText: Mdo.HeaderOrderCustomersNumberText.create(),
		headerOrderTakeawayLabel: Mdo.LabelView.create({
			key: "header.order.takeaway",
			classNameBindings: ['labelClass'],
			labelClass: "visibility-hidden",
			toggleClass: false,
			clearInterval: null,
			blinkLabelClass: function() {
				var self = this;
				if (this.get('parentView.currentTakeaway')) {
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
			}.observes('parentView.currentTakeaway'),
		}),
		headerOrderMergeAutoCheckbox: Ember.Checkbox.create({
			attributeBindings: ['checked'],
			checked: true,
		}),
		headerOrderMergeAutoLabel: Mdo.LabelView.create({
			key: "header.order.line.merge.auto",
		}),
		didResetHeaderOrderChange: function() {
			if (this.get('resetHeaderOrder')) {
				this.headerOrderNumberText.set('value', '');
				this.headerOrderCustomersNumberText.set('value', '');
			}
		}.observes('resetHeaderOrder'),
		didInsertElement: function() {
			var self = this;
			// Focus the order number text field.
//			this.headerOrderNumberView.$().focus();
//			Em.View.views['number'].$().focus();
$('#reset').click(function() {
	self.set('currentStep', 0); 
});
		},
	});

