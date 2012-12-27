/**
 * This file will contain all Mdo views.
 */
$(document).ready(function() {
	// This is the application view. It is required by Mdo application for initial context.
	Mdo.ApplicationView = Ember.View.extend({
		template: Ember.Handlebars.compile('application'),
		didInsertElement: function() {
			$("#menu, #header, #footer").mdoResizable();
		},
	});

	Mdo.LabelView = Ember.View.extend({
		tagName: 'span',
		labelKey: null,
		i18nLabelChanged: function(sender, key) {
			var label = sender.get(key);
			this.$().html(label);
		},
		didInsertElement: function() {
			Mdo.I18n.propAddObserver(this.labelKey, null, this, this.i18nLabelChanged);
		},
		willDestroyElement: function() {
			// Have to remove because when this view is deleted, the observable object did not know of it.
			Mdo.I18n.propRemoveObserver(this.labelKey, this, this.i18nLabelChanged);
		}
	});

	Mdo.DialogView = Ember.ContainerView.extend({
		labelTitleKey: null,
		/**
		 * A function that must return an array of options buttons.
		 * 
		 * @returns {Array}
		 */
		buttons: function() {return [];},
		didInsertElement: function() {
			var self = this;
			var labelTitle = Mdo.LabelView.create({
				labelKey: self.labelTitleKey,
			});
			self.$().dialog({
				autoOpen: false,
				open: function() {
					labelTitle.appendTo($(this).parent().find(".ui-dialog-title"));
					_processButtons(this);
		        },
		        close: function() {
		        	$(this).dialog("destroy").remove();
		        }
			});
			_processButtons = function(dialog) {
				var buttons = self.buttons();
				$(dialog).dialog("option", "buttons", buttons);
				$.each(buttons, function(index, button) {
					var label = Mdo.LabelView.create({labelKey: button.labelKey});
					label.appendTo($(dialog).parent().find("#" + button.id).button({icons: button.icons}).find(".ui-button-text"));
				});
			};
			self.$().dialog("open");
		},
		openDialog: function() {
			// Call this in order to fire didInsertElement event.
			this.append('body');
		},
	});

	Mdo.HeaderView = Ember.View.extend({
		templateName: "header",
	});

	Mdo.HeaderDateTimeView = Ember.View.extend({
		templateName: "headerDateTime",
	});

	Mdo.DateDialogView = Mdo.DialogView.extend({
		labelTitleKey: "date.time.dialog.title",
		tagName: 'form',
		displayedDate: null,
		changeDateTimeController: null,
		buttons: function() {
			var self = this;
			var changeDateTimeController = this.changeDateTimeController; 
			return [{
	     	   id: "save",
	    	   labelKey: "common.save",
	    	   click: function() {
	    		   var form = Mdo.DateTimeForm.create();
	    		   // TODO
	    		   form.date = $(this).find('.user-entry-date').datepicker("getDate"); 
	    		   if (changeDateTimeController) {
	    			   changeDateTimeController(form);
	    		   }
	    		   $(this).dialog("close"); 
	    	   },
	    	   icons: {
					primary: "ui-icon-disk"
	    	   }
	       }, 
	       { 
	    	   id: "cancel",
	    	   labelKey: "common.cancel",
	    	   click: function() {
	    		   $(this).dialog("close"); 
	    	   },
	    	   icons: {
					primary: "ui-icon-close"
	    	   }
	       }];
		},
		childViews: [],
		labelDateTimeClass: Mdo.LabelView.extend({
			labelKey: "date.time.dialog.date",
		}),
		textDateTimeClass: Ember.TextField.extend({
			classNames: ['ui-widget-content', 'user-entry-date'],
			displayedDate: new Date(),
			didInsertElement: function() {
				this.$().datepicker({
					dateFormat: 'DD d MM yy',
					showOn: "button",
		            buttonImage: "images/calendar.gif",
		            buttonImageOnly: true,
					showButtonPanel: true,
				}).datepicker("setDate", this.displayedDate);
			},
		}),
		labelPasswordClass: Mdo.LabelView.extend({
			labelKey: "date.time.dialog.password",
		}),
		textPasswordClass: Ember.TextField.extend({
			classNames: ['ui-widget-content'],
		}),
		init: function() {
			this.childViews.clear();
			var labelDateTime = this.labelDateTimeClass.create();
			var textDateTime = this.textDateTimeClass.create();
			// Set the new value
			textDateTime.displayedDate = this.displayedDate;
			var labelPassword = this.labelPasswordClass.create();
			var textPassword = this.textPasswordClass.create();
			this.childViews.pushObject(labelDateTime);
			this.childViews.pushObject(textDateTime);
			this.childViews.pushObject(labelPassword);
			this.childViews.pushObject(textPassword);
			this._super();
		},
		didInsertElement: function() {
			var self = this;
			self._super();
			self.$().on("dialogclose", function( event, ui) {
				self.controller.target.send('gotoDateDialog', 'close');
			});
		},
	});
	Mdo.DateTimeView = Ember.View.extend({
		tagName: 'a',
		classNames: ['displayed-date'],
		classNameBindings: ['displayedDateClass'],
		toggleClass: true,
		dateTime: null,
		displayedDate: null,
		displayedDateClass: function() {
			var currentTableRegistrationDateFormatted = null;
			if (this.dateTime.currentTableRegistrationDate) {
				currentTableRegistrationDateFormatted = $.datepicker.formatDate(this.dateTime.datePattern, this.dateTime.currentTableRegistrationDate);
			}
			var userEntryDateFormatted = $.datepicker.formatDate(this.dateTime.datePattern, this.dateTime.userEntryDate);
			var result = 'displayed-date-now';
//console.log(currentTableRegistrationDateFormatted + "==" + userEntryDateFormatted + "==" + nowFormatted)
			if (currentTableRegistrationDateFormatted && currentTableRegistrationDateFormatted!=userEntryDateFormatted) {
				result = 'displayed-date-registration';
			} else {
//console.log(this.displayedDate)
				// Current date
				var nowFormatted = jQuery.datepicker.formatDate(this.dateTime.datePattern, new Date());
//console.log(currentTableRegistrationDateFormatted + "==" + userEntryDateFormatted + "==" + nowFormatted)
				if (nowFormatted!=userEntryDateFormatted) {
					if (this.toggleClass) {
						this.toggleClass = false;
						result = 'displayed-date-user-entry-odd';
					} else {
						this.toggleClass = true;
						result = 'displayed-date-user-entry-even';
					}
				}
			}
			return result;
		}.property('controller.displayedFormattedDate'),
		didInsertElement: function() {
			var self = this;
			///TODO remove the tests below.
			$('#setCurrentTableRegistrationDateTest').click(function() {
				self.get('controller').set('dateTime.currentTableRegistrationDate', new Date(1970, 7, 15)); 
			});
			$('#resetCurrentTableRegistrationDateTest').click(function() {
				self.get('controller').set('dateTime.currentTableRegistrationDate', null); 
			});
		},
		click: function() {
//			var dateDialog = Mdo.DateDialogView.create({
//				displayedDate: this.displayedDate,
//				changeDateTimeController: this.dateTime.changeDateTimeController,
//			});
//			dateDialog.openDialog();
			
			this.get('controller.target').send('gotoDateDialog', 'open');
			
			
		}
	});
	
	Mdo.FlagView = Ember.View.extend({
		tagName: 'div',
		classNameBindings: ['flagClasses'],
		attributeBindings: ['title'],
		id: null,
		languageIso2: null,
		displayLanguage: null,
		title: function() {
			return this.displayLanguage;
		}.property('displayLanguage'),
		selected: false,
		flagClasses: function() {
			var result = "flag flag-" + this.languageIso2; 
			var viewSelected = this.get("selected");
			if (viewSelected) {
				result += " flag-state-active";
			}
			return result;
		}.property("languageIso2", "selected"),
		init: function() {
			this._super();
			this.set('selected', this.selected);
		},
		didInsertElement: function() {
			var self = this;
			var controller = self.get('controller');
			if (!this.selected) {
				this.$().click(function() {
					controller.changeLanguage(self.languageIso2);
				});
			}
		},
	});
	Mdo.HeaderLanguagesView = Ember.View.extend({
		templateName: "headerLanguages",
	});

	Mdo.ButtonView = Ember.View.extend({
		tagName: 'button',
		labelKey: null,
		selected: false,
		controllerChangeButton: null,
		i18nLabelChanged: function(sender, key) {
			var label = sender.get(key);
			this.$().button({ label: label });
		},
		click: function() {
			this.get('parentView').manageButtons(this);
			this.controllerChangeButton();
		},
		didInsertElement: function() {
			var self = this;
			var button = self.$().button({
		    	// labelKey comes from HeaderView template.
		    	//label: $.mdoI18n.prop(self.labelKey),
		    	// icons comes from HeaderView template.
		    	icons: self.get('icons'),
		    });
			// Process the i18n label and wait the return from server with Ember.Observable
			// The label will be set in the method i18nLabelChanged
			Mdo.I18n.propAddObserver(this.labelKey, null, this, this.i18nLabelChanged);
			// selected comes from HeaderView template.
			this.get('parentView').disabledButton(this.$(), this.get('selected'));
		},
		willDestroyElement: function() {
			// Have to remove because when this view is deleted, the observable object did not know of it.
			Mdo.I18n.propRemoveObserver(this.labelKey, this, this.i18nLabelChanged);
		},
	});
	Mdo.HeaderButtonsView = Ember.View.extend({
		templateName: "headerButtons",
		didInsertElement: function() {
			$("#header-buttons").buttonset();
		},
		manageButtons: function(selectedButtonView) {
			var self = this;
			$("button", "#header-buttons").each(function() {
				var viewSelected = false;
				if (selectedButtonView.$().attr('id') == $(this).attr('id')) {
					viewSelected = true;
				}
				self.disabledButton(this, viewSelected);
			});
		},
		disabledButton: function(button, selected) {
			var jButton = $(button).button({ disabled: selected });
			jButton.removeClass('ui-state-disabled');
			if (selected) {
				jButton.addClass('ui-state-active');
			} else {
				jButton.removeClass('ui-state-hover ui-state-focus ui-state-active');
			}
		}
	});

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
							restaurantId: self.get('controller.headerOrder.restaurantId'), 
							userAuthenticationId: self.get('controller.headerOrder.userAuthenticationId'), 
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
console.log('enter' + $(this));
						// Upate the customers number and display list of order lines.
						//self.get('controller.target').send('gotoSaveCustomersNumber', this.$().val());
						// Do not fire event to router because we want to save data to server.
						// So call save method from controller and save data 
						// then in the same controller, we fire event to router in order to display order lines.
						self.get('controller')['updateCustomersNumber'](this.$().val());
					return;
					//27 == key Esc
					case 27 :
console.log('Esc' + $(this));
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

	Mdo.UserView = Ember.View.extend({
		templateName: "user"
	});

	Mdo.OrdersView = Ember.View.extend({
		templateName: "orders"
	});

	Mdo.CashedOrdersView = Ember.View.extend({
		templateName: "cashedOrders"
	});

	Mdo.LockedOrdersView = Ember.View.extend({
		templateName: "lockedOrders"
	});
});