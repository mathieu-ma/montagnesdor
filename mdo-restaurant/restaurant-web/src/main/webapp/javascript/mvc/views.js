/**
 * This file will contain all Mdo views.
 */
$(document).ready(function() {
	// This is the application view. It is required by Mdo application for initial context.
	Mdo.ApplicationView = Ember.View.extend({
		template: Ember.Handlebars.compile('application')
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

	Mdo.HeaderView = Ember.View.extend({
		templateName: "header",
	});

	Mdo.HeaderDateTimeView = Ember.View.extend({
		templateName: "headerDateTime",
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
			this.append('body');
		}
	});
	Mdo.DateTimeDialogView = Mdo.DialogView.extend({
		labelTitleKey: "date.time.dialog.title",
		tagName: 'form',
		displayedDate: null,
		controllerChangeDateTime: null,
		buttons: function() {
			var self = this;
			var controllerChangeDateTime = this.controllerChangeDateTime; 
			return [{
	     	   id: "save",
	    	   labelKey: "common.save",
	    	   click: function() {
	    		   var form = Mdo.DateTimeForm.create();
	    		   // TODO
	    		   form.date = $(this).find('.user-entry-date').datepicker("getDate"); 
	    		   if (controllerChangeDateTime) {
	    			   controllerChangeDateTime(form);
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
			var dateTimeDialog = Mdo.DateTimeDialogView.create({
				displayedDate: this.displayedDate,
				controllerChangeDateTime: this.dateTime.controllerChangeDateTime,
			});
			dateTimeDialog.openDialog();
		}
	});
	
	Mdo.FlagView = Ember.View.extend({
		tagName: 'div',
		classNameBindings: ['flagClasses'],
		attributeBindings: ['title'],
		id: null,
		languageIso2: null,
		displayLanguage: null,
		controllerModifyLanguage: null,
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
			if (!this.selected) {
				this.$().click(function() {
					self.controllerModifyLanguage(self.languageIso2);
				});
			}
		}
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
			var thisEmberView = this;
			var button = thisEmberView.$().button({
		    	// labelKey comes from HeaderView template.
		    	//label: $.mdoI18n.prop(thisEmberView.labelKey),
		    	// icons comes from HeaderView template.
		    	icons: thisEmberView.get('icons'),
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
			var thisEmberView = this;
			$("button", "#header-buttons").each(function() {
				var viewSelected = false;
				if (selectedButtonView.$().attr('id') == $(this).attr('id')) {
					viewSelected = true;
				}
				thisEmberView.disabledButton(this, viewSelected);
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
	Mdo.HeaderOrderNumberView = Ember.View.extend({
		templateName: "headerOrderNumber",
		tagName: "span",
//		didInsertElement: function() {
//			// Focus the order number text field.
////			this.$().focus();
////alert("headerOrderNumber")			
////			Em.View.views['number'].$().focus();
//		},
	});
	Mdo.HeaderOrderCustomersNumberView = Ember.View.extend({
		templateName: "headerOrderCustomersNumber",
		tagName: "span",
		didInsertElement: function() {
			// Focus the order number text field.
//			this.headerOrderNumberView.$().focus();
//alert("headerOrderNumber")			
//			Em.View.views['number'].$().focus();
		},
	});
	Mdo.HeaderOrderView = Ember.ContainerView.extend({
//		templateName: "headerOrder",
		tagName: 'form',
		childViews: ['headerOrderNumberLabel', 'headerOrderNumberText', 'headerOrderCustomersNumberLabel', 'headerOrderCustomersNumberText'],
		headerOrderNumberLabel: Mdo.LabelView.create({
			labelKey: "header.order.number",
		}),
		headerOrderNumberText: Ember.TextField.create({
			classNames: ['ui-widget-content'],
		}),
		headerOrderCustomersNumberLabel: Mdo.LabelView.create({
			labelKey: "header.order.customers.number",
		}),
		headerOrderCustomersNumberText: Ember.TextField.create({
			classNames: ['ui-widget-content'],
		}),
		didInsertElement: function() {
			// Focus the order number text field.
//			this.headerOrderNumberView.$().focus();
//			Em.View.views['number'].$().focus();
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