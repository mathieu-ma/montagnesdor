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
	Mdo.DialogView = Ember.View.extend({
		labelTitleKey: null,
		buttons: [],
		dialogContent: $("<div/>"),
		init: function() {
			var self = this;
			var labelTitle = Mdo.LabelView.create({
				labelKey: self.labelTitleKey,
			});
        	self.dialogContent.html("");
			self.appendTo(self.dialogContent);
			self.dialogContent.dialog({
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
				$(dialog).dialog("option", "buttons", self.buttons);
				$.each(self.buttons, function(index, button) {
					var label = Mdo.LabelView.create({labelKey: button.labelKey});
					label.appendTo($(dialog).parent().find("#" + button.id).button({icons: button.icons}).find(".ui-button-text"));
				});
			};
		},
		openDialog: function() {
			return this.dialogContent.dialog("open");
		}
	});
	Mdo.DateTimeDialogView = Mdo.DialogView.extend({
		labelTitleKey: "date.time.dialog.title", 
		buttons: [{
     	   id: "save",
    	   labelKey: "common.save",
    	   click: function() { 
    		   if (self.controllerChangeDateTime) {
    			   self.controllerChangeDateTime();
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
       }],
       init: function() {
			//var dialogContent = $("<div/>");
    	   var dialogContent = this.dialogContent;

    	   var textDateTime = Ember.TextField.create({
    		   classNames: ['ui-widget-content'],
    		   didInsertElement: function() {
    			   this.$().datetimepicker({
    				   dateFormat: 'DD d MM yy',
    				   defaultDate: new Date(1985,01,01),
    				   hour: 13,
    				   minute: 15
    			   }).datetimepicker("setDate", new Date(1985,01,01));
    		   },
    	   });
    	   textDateTime.appendTo(dialogContent);
    	   
			var labelPassword = Mdo.LabelView.create({
				labelKey: "date.time.dialog.password",
			});
			labelPassword.appendTo(dialogContent);
			var textPassword = Ember.TextField.create({
				classNames: ['ui-widget-content'],
			});
			textPassword.appendTo(dialogContent);
			this.dialogContent = dialogContent;
			this._super();
       },
	});
	Mdo.DateTimeView = Ember.View.extend({
		tagName: 'a',
//		classNames: ['ui-widget-content'],
		datePattern: null,
		timePattern: null,
		controllerChangeDateTime: null,
		didInsertElement: function() {
			var entryFormattedDate = $.datepicker.formatDate(this.datePattern, new Date());
			this.$().html(entryFormattedDate);
//			this.$().datetimepicker();
		},
		click: function() {
			var dateTimeDialog = Mdo.DateTimeDialogView.create();
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
		didInsertElement: function() {
			// Focus the order number text field.
//			this.headerOrderNumberView.$().focus();
//alert("headerOrderNumber")			
//			Em.View.views['number'].$().focus();
		},
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
	Mdo.HeaderOrderView = Ember.View.extend({
		templateName: "headerOrder",
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