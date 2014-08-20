/**
 * This file will contain all Mdo views.
 */
	Mdo.DialogView = Ember.ContainerView.extend({
		labelTitleKey: null,
		elementIsInserted: false,
		/**
		 * A function that must return an array of options buttons.
		 * 
		 * @returns {Array}
		 */
		buttons: function() {return [];},
		didInsertElement: function() {
			var self = this;
			var labelTitle = Mdo.LabelView.create({
				key: self.labelTitleKey,
			});
			self.$().dialog({
				autoOpen: false,
				open: function() {
					if (!self.elementIsInserted) {
						labelTitle.appendTo($(this).parent().find(".ui-dialog-title"));
						_processButtons(this);
					}
		        },
		        close: function() {
		        	// TODO Analyze if have to destroy
		        	//$(this).dialog("destroy").remove();
		        }
			});
			_processButtons = function(dialog) {
				var buttons = self.buttons();
				$(dialog).dialog("option", "buttons", buttons);
				$.each(buttons, function(index, button) {
					var label = Mdo.LabelView.create({key: button.labelKey});
					label.appendTo($(dialog).parent().find("#" + button.id).button({icons: button.icons}).find(".ui-button-text"));
				});
			};
			// Need to call here because this method is asynchronous
			self.$().dialog("open");
			self.set('elementIsInserted', true);
		},
		openDialog: function() {
			if (!this.elementIsInserted) {
				// Call this in order to fire didInsertElement event and then build the view.
				this.append('body');
			} else {
				this.$().dialog("open");
			}
		},
	});

	Mdo.DateDialogView = Mdo.DialogView.extend({
		labelTitleKey: "date.time.dialog.title",
		tagName: 'form',
		userEntryDate: null,
		saveDate: function(controller, form) {
			controller.send("saveDate", form);
		},
		buttons: function() {
			var self = this;
			var saveDate = self.saveDate; 
			return [{
	     	   id: "save",
	    	   labelKey: "common.save",
	    	   click: function() {
	    		   var form = Mdo.UserEntryDateTimeForm.create();
	    		   // TODO
	    		   form.date = $(this).find('.user-entry-date').datepicker("getDate"); 
	    		   if (saveDate) {
	    			   saveDate(self.get('controller'), form);
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
			key: "date.time.dialog.date",
		}),
		textDateTimeClass: Ember.TextField.extend({
			classNames: ['ui-widget-content', 'user-entry-date'],
			userEntryDateBinding: "parentView.parentView.userEntryDate",
			userEntryDateChanged: function() {
				var self = this;
				// This method could be called before the element will be inserted in the DOM.
				// So we have to check it: self.$() 
				if (self.$()) {
					self.$().datepicker("setDate", self.userEntryDate);
				}
			}.observes("userEntryDate"),
			didInsertElement: function() {
				var self = this;
				var jqueryUiThemePath = "libs/jquery/ext/ui/css/themes/kimsan93";
				self.$().datepicker({
					dateFormat: 'DD d MM yy',
					showOn: "button",
		            buttonImage: jqueryUiThemePath + "/images/calendar.gif",
		            buttonImageOnly: true,
					showButtonPanel: true,
				}).datepicker("setDate", self.userEntryDate);
			},
		}),
		textDateTime: null,
		textPassword: null,
		labelPasswordClass: Mdo.LabelView.extend({
			key: "date.time.dialog.password",
		}),
		textPasswordClass: Ember.TextField.extend({
			classNames: ['ui-widget-content'],
		}),
		init: function() {
			this.childViews.clear();
			
			// Date Time part.
			var dateTimeViewContainer = Ember.ContainerView.create();
			var labelDateTime = this.labelDateTimeClass.create();
			var textDateTime = this.textDateTimeClass.create();
			this.textDateTime = textDateTime;
			// Set the new value
			//textDateTime.userEntryDate = this.userEntryDate;
			dateTimeViewContainer.pushObject(labelDateTime);
			dateTimeViewContainer.pushObject(textDateTime);
			
			// Password part.
			var passwordViewContainer = Ember.ContainerView.create();
			var labelPassword = this.labelPasswordClass.create();
			var textPassword = this.textPasswordClass.create();
			this.textPassword = textPassword;
			passwordViewContainer.pushObject(labelPassword);
			passwordViewContainer.pushObject(textPassword);
			
			this.childViews.pushObject(dateTimeViewContainer);
			this.childViews.pushObject(passwordViewContainer);
			
			this._super();
		},
		openDialog: function() {
			var self = this;
			self._super();
			if (self.elementIsInserted) {
				self.textDateTime.$().datepicker("setDate", self.userEntryDate);
			}
		},
		didInsertElement: function() {
			var self = this;
			self._super();
			Mdo.FocusViewUtils.processFocus(self.textPassword.$());
		}
	});
