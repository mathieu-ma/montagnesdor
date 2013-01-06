/**
 * This file will contain all Mdo views.
 */
$(document).ready(function() {
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
});