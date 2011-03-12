(function($){
	//START bindTop
	jQuery.fn.extend({
		bindTop: 
			function(type, preFunction) {
				return $(this).each(function() {
						var backupFunction = $(this).attr("on"+type);
						$(this).removeAttr("on"+type);
						$(this).bind(type, function(){});
						
						var events = (jQuery.data(this, "events") || {});
						var handlers = events[type];
						var newHandlers = {};
						var index = 0;
						//Set first
						newHandlers[index++] = preFunction;
						//Set second
						if(typeof backupFunction === "function") {
							newHandlers[index++] = backupFunction;
						}
						//Set others binding at last
						for(var handle in handlers ) {
							newHandlers[index++] = handlers[handle];
						}
						
						$(this).unbind(type);
						//Bind all in the right order
						for(var handle in newHandlers) {
							$(this).bind(type, newHandlers[handle]);
						}
				});
			}
	});	
	//END bindTop
	//START mdoFocus
	jQuery.mdoFocus = function(object) {
		if($.browser.msie) {
			//Because of Applet gaining focus
			document.focus();
	  	}
		var inputs = ":text:not([readOnly]),:password:not([readOnly])";
		var jContext = object?$(object):$(document);
		var jInputs = jQuery(inputs, jContext);
		var input = jInputs[0] || $(object).get(0);
		if(input) {
			input.value = input.value+' ';
			input.value = input.value.substring(0, input.value.length-1);
			$(input).focus();
		}
	}
	//END mdoFocus
	//START mdoSubmit
	jQuery.mdoSubmit = function() {
	  	$("form").each(function() {
	    	var oldSubmitFunction = this["onsubmit"];
	    	this["onsubmit"] = function() {
	    		$("#mdo-overlay").css('display', 'block');
				if(oldSubmitFunction && !oldSubmitFunction()) {
					//$("#mdo-overlay").css('display', 'none');
					$("#mdo-overlay").fadeOut(500);
					jQuery.mdoFocus();
					return false;
				}
				return true;
	    	}
	  	});
	}
	//END mdoSubmit
	//START mdoDialog
	jQuery.mdoDialog = function(jContext) {
		jContext = jContext || jQuery(document);
		var jDiv = $("div.alert-dialog", jContext);
		if(!jDiv.get(0)) {
			jDiv = jQuery('<div class="alert-dialog"/>').appendTo(jContext);
		}
		var jDialog = jDiv.dialog({
			autoOpen: false,
			closeOnEscape: true,
			position: 'center',
			closeText: '',
			modal: true,
			resizable: false
		});
		return jDialog;
	}
	//END mdoDialog
	//START mdoPreSelectorName
	jQuery.mdoPreSelectorName = function(inString) {
		//Replace "[" by "\\[", "]" by "\\]" and "." by "\\."
	  	return inString.replace(/([\.\]\[:])/gi, "\\$1");
	}
	//END mdoPreSelectorName
	//START mdoParentsByCss
	jQuery.mdoParentByCss = function(jChild, cssProperty, cssValue) {
		var parent = null;
		$(jChild).parents().each(function(i) {
			if($(this).css(cssProperty) == cssValue) {
				parent = this;
				//Break the each
				return false;
			}
		});
	  	return parent;
	}
	//END mdoParentsByCss
	//START mdoTablesorter
	jQuery.fn.extend({
		mdoTablesorter: function(options) {
			 // add parser through the tablesorter addParser method 
//		    $.tablesorter.addParser({ 
//		        // set a unique id 
//		        id: 'mdonumeric', 
//		        is: function(s) { 
//		            // return false so this parser is not auto detected 
//		            return false; 
//		        }, 
//		        format: function(s) { 
//		            // format your data for normalization
//		            return s.replace(/[,.']/g, ''); 
//		        }, 
//		        // set type, either numeric or text 
//		        type: 'numeric' 
//		    });
		    $.tablesorter.addParser({ 
		        // set a unique id 
		        id: 'mdodate', 
		        is: function(s) { 
		            // return false so this parser is not auto detected 
		            return false; 
		        }, 
		        format: function(s, table, node) {
		        	var value = $(":hidden", $(node)).val() || s;
		            // format your data for normalization
					return value ? new Date(value).getTime() : "0";
		        }, 
		        // set type, either numeric or text 
		        type: 'numeric' 
		    });
		    options = options || {};
			var sortList = options.sortList || [[0,0]];
			if($("tbody tr", $(this)).length ==0) {
				// There is no tr in tbody
				sortList = [];
			}
			jQuery.extend(options, {
				sortList: sortList, 
				onRenderHeader: function(header) {
					this.append("<span class='ui-icon ui-icon-triangle-2-n-s'></span>");
				}
			});
			var result = $(this).tablesorter(options);
			return result;
		}
	});
	//END mdoTablesorter
	//START mdoValidate
	jQuery.fn.extend({
		mdoValidate: function(options) {
			var jAlert = jQuery.mdoDialog();
			// set dialog not modal
			jAlert.dialog("option", "modal", false);
			// Set dialog draggable
			jAlert.dialog("option", "draggable", true);
			options = options || {};
			jQuery.extend(options, {
				invalidHandler: function(form, validator) {
					$("#mdo-overlay").css('display', 'none');
					var errors = validator.numberOfInvalids();
					jAlert.html(jQuery.i18n.prop("error.validation.form.invalids.number",  [errors]));
					jAlert.dialog('open');
					return false;
				},
			});
			if(options.resourceMessages) {
				if (!options.browserLang) {
					// Override the i18n method in order to get only one resource
					// Because we presume that each resource in any language has the same pairs key/value
					jQuery.i18n.browserLang = function() {
						return null;
					}
				}
				jQuery.i18n.properties({
					name: 'AjaxI18nMessages.action?form.resource=' + options.resourceMessages,
					path: '',
					mode: 'map',
				});
				
				for (var key in options.messages) {
					options.messages[key] = jQuery.i18n.prop(options.messages[key]);
				}
			}
			$(this).validate(options);
		}
	});
	//END mdoValidate
	//START mdoDatePicker
	jQuery.fn.extend({
		mdoDatePicker: 
			function(options) {
				options = options || {};
				var altField = this.selector;
				var altFormat = options.altFormat || "yy-mm-ddT00:00:00";
				var defaultDateInput = $(altField);
				var defaultDate = $.datepicker.parseDate(altFormat, defaultDateInput.get(0)?defaultDateInput.get(0).value:'');
				
				jQuery.extend(options,
					{
						showOn: 'button', 
						defaultDate: defaultDate,
						buttonImage: '../images/calendar.gif', 
						buttonImageOnly: true,
						inline: false,
						dateFormat: 'DD dd MM yy',
						altField: altField, 
						altFormat: altFormat,
						showButtonPanel: true,
						onClose: function(dateText, inst) {
							jQuery.mdoFocus();
						}
					}	
				);
				var result = $(altField + "-datepicker").datepicker(options);
		
				$("#ui-datepicker-div").css({zIndex: '2'});
				return result;
			}
	});	
	//END mdoDatePicker
	//START mdoCrudList
	jQuery.fn.extend({
		mdoCrudList: 
		function() {
			var jListPart = $(this.selector);
			var jEntryData = $("input[type='text']", jListPart);
			var jHiddenData = $(".crud-list input[type='hidden'][name]", jListPart);
			var jAlert = jQuery.mdoDialog(jListPart);
			
			jAlert.bind("dialogclose", function(event) {
				window.setTimeout(function() {jQuery.mdoFocus(jEntryData);}, 100);
				return false;
			});
  			
			var jSelectList = $("select", jListPart).change(function(event, isHideOverlay) {
				var storedLabelName = jEntryData.attr("name").replace(/\[(-)?[0-9]+\]/, "["+$(this).val()+"]");
				jEntryData.attr("name", storedLabelName);
				var jStoredData = $(":hidden[name='"+jQuery.mdoPreSelectorName(jEntryData.attr("name"))+"']");
				var storedDataValue = "";
				jHiddenData.each(function() {
					$(this).removeAttr("disabled");
				});
				//Remove all background color first 
				$("div.bg-color-selected", jListPart).each(function() {
					$(this).toggleClass("bg-color-selected");
				});

				if($(this).val()==$(":hidden[name='select-list-header-key']", jListPart).val()) {
					jEntryData.attr("disabled", "disabled");
				} else {
					jEntryData.removeAttr("disabled");
					if(jStoredData.get(0)) {
						//Set background color to the parent
						jStoredData.parent().parent().toggleClass("bg-color-selected");
						storedDataValue = jStoredData.val();
						jStoredData.attr("disabled", "disabled");
					}
				}
					
				jEntryData.val(storedDataValue);
				if(!isHideOverlay) {
					//$("#mdo-overlay").css('display', 'none');
					$("#mdo-overlay").fadeOut(500);
				}
				jQuery.mdoFocus(jEntryData);
			});

			jSelectList.change();
			
			$(".crud-list-reset", jListPart).click(function() {
				jSelectList.change();
			});
			
			$(".crud-list-remove", jListPart).click(function() {
				var selectedId = $("[name='selectedId']", $(this));
				if(selectedId.val()) {
					$("option[value='"+selectedId.val()+"']",jSelectList).attr("selected", "selected");
					jSelectList.trigger("change", ["true"]);
				}
				if(prepareSubmit("remove")) {
					$("form").submit();
				}
			});
					
			$(".crud-list-edit", jListPart).click(function() {
				var selectedId = $("[name='selectedId']", $(this));
				if(selectedId.val()) {
					$("option[value='"+selectedId.val()+"']",jSelectList).attr("selected", "selected");
					jSelectList.change();
				}
			});

			$(".crud-list-add", jListPart).click(function() {
				if(prepareSubmit("add")) {
					$("form").submit();
				}
			});

			$("#hidden-submit-form").click(function(eventParam) {
				prepareSubmit("add");
				return false;
			});

			function prepareSubmit(action) {
				var isSubmit = false;
				//Using when Tabs exists and this part is hidden by a parent
				var parentHidden = jQuery.mdoParentByCss(jListPart, "display", "none");
				if(!parentHidden) {
					if(jEntryData.val()=="") {
						$("#mdo-overlay").fadeOut(500);
						jQuery.mdoFocus(jEntryData);
						if(jSelectList.val()==$("input[type='hidden'][name='select-list-header-key']", jListPart).val()) {
							return false;
						}
			  			//$("#mdo-overlay").css('display', 'none');
			  			var message = "data-remove-empty";
						if(action=="add") {
				  			message = "data-add-empty";
						}
			  			jAlert.html($(":hidden[name='"+message+"']", jListPart).val());
			  			jAlert.dialog('open');
					} else {
						if(action=="remove") {
							jEntryData.attr("disabled", "disabled");
						}
						$("form").submit();	
						isSubmit = true;
					}
				}
				return isSubmit;
			}
			
			jListPart.data("jSelectList", jSelectList);
			jListPart.data("jEntryData", jEntryData);
			jListPart.data("jHiddenData", jHiddenData);
			return jListPart;
		}
	});	
	//END mdoCrudList
})(jQuery);			

//START UpsideDown class
/**@class-definition*/
function UpsideDown(selector) {
	var oUpsideDown = this;
	// CURRENT_DECIMAL_SEPARATOR is come main-layout.jsp
	var decimalSeparator = CURRENT_DECIMAL_SEPARATOR || ".";
	this.inputsUpsideDownSelector = ":text,:password";
	this.jUpsideDownList = new Array();
	this.selector = selector || ".mdo-upsidedown";
	this.jSelectorResult = null;
	this.backupCurrentValue = "";
	this.currentIndex = 0;
	this.created = false;
	this.constructor = function() {
		this.jSelectorResult = jQuery(this.selector);
		this.jSelectorResult.each(function(iIndex) {
			var inputsList = jQuery(oUpsideDown.inputsUpsideDownSelector, jQuery(this));
			if(jQuery(this).is(oUpsideDown.inputsUpsideDownSelector)) {
				inputsList = jQuery(this);
			}
			inputsList.each(function(jIndex) {
				oUpsideDown.jUpsideDownList.push(jQuery(this));
//				if(jQuery(this).attr("class").indexOf("number")>=0) {
//					var value = jQuery(this).val().replace(/[,]/g, ".");
//					this.value = value;
//				}
				jQuery(this).keyup( function(eventParam) { oUpsideDown.processUserEntry(eventParam, jQuery(this)); return false;} ); 
				jQuery(this).focus( function(eventParam) { oUpsideDown.backupCurrentValue = this.value; return true;} ); 
			});
		});
		jQuery.each(this.jUpsideDownList, function() {
			if(!this.attr("readonly")) {
				jQuery.mdoFocus(this);
				return false;
			}	
		});
    }
	this.processUserEntry = function(eventParam, jCell) {
		for(var i=0, length=this.jUpsideDownList.length; i<length; i++) {
			// Use === because on IE, window == document
			if(this.jUpsideDownList[i].get(0) === jCell.get(0)) {
				this.currentIndex = i;
				break;
			}
		}
		this.keyup(jCell);
		switch(eventParam.keyCode) {     	  
			//13 == key Enter
			case 13 :
				this.enter(jCell);
			return;
			//27 == key Esc
			case 27 :
				this.esc(jCell);
			return;
			//38 == key Up
			case 38 :
				this.up(jCell);
			return;
			//40 == key Down
			case 40 :
				this.down(jCell);
			return;
		};
		this.checkNumber(eventParam, jCell);
    }
	this.keyup = function(jCell) {
    }
	this.up = function(cell) {
		cell.blur();
		var newIndex = this.move(this.currentIndex, -1);
		var newCell = this.jUpsideDownList[newIndex];
		if(this.created) {
			cell.attr("readonly", "true");
			newCell.removeAttr("readonly");
		}

		newCell.value = newCell.value+' ';
		newCell.value = newCell.value.substring(0, newCell.value.length-1);
		newCell.focus();
    }
	this.enter = function(jCell) {
		var mdoOverlay = $("#mdo-overlay");
		if(mdoOverlay) {
			var form = $("form");
			if(form && form.valid && form.valid()) {
				$("#mdo-overlay").css('display', 'block');
			}
		}
		return true;
    }
	this.esc = function(jCell) {
		var mdoOverlay = $("#mdo-overlay");
		if(mdoOverlay) {
			mdoOverlay.css('display', 'block');
		}
		
		var jCancel = $("#admin-manager-cancel,.admin-manager-cancel").each(function() {
			var href = $(this).attr("href");
			if(href) {
				//Exit for first found href
				window.location = href;
				return true;
			}
		});
		if(!jCancel.get(0) && mdoOverlay) {
			//There is no cancel button
			
			//mdoOverlay.css('display', 'none');
			mdoOverlay.fadeOut(500);
		}
		return true;
    }
	this.down = function(cell) {
		cell.blur();
		var newIndex = this.move(this.currentIndex, 1);
		if(this.created && this.currentIndex==this.jUpsideDownList.length-1) {
			newIndex = create(cell);
		}
		var newCell = this.jUpsideDownList[newIndex];
		if(this.created) {
			cell.attr("readonly", "true");
			newCell.removeAttr("readonly");
		}
		newCell.value = newCell.value+' ';
		newCell.value = newCell.value.substring(0, newCell.value.length-1);
		newCell.focus();
    }
	this.move = function(currentIndex, step) {
		var resultIndex = this.currentIndex+step;

		if(step>0) {
			if(resultIndex>=this.jUpsideDownList.length) {
				resultIndex=this.jUpsideDownList.length-1;
			}
		} else {
			if(resultIndex<0) {
				resultIndex=0;
			}
		}	
		return resultIndex;
    }
    this.create = function(jCell) {
		var parent = jCell.parents(this.selector);
		if(jCell.is("input"+this.selector)) {
			parent = jCell;
		}
		var cloned = parent.clone(true);
		var inputTextsList = jQuery(this.inputsUpsideDownSelector, cloned);
		if(jCell.is("input"+this.selector)) {
			inputTextsList = cloned;
		}
		inputTextsList.each(function() {
			this.jUpsideDownList.push(jQuery(this));
		});
		cloned.children(":not(:has("+this.inputsUpsideDownSelector+"))").empty();
		parent.after(cloned);

		return this.jUpsideDownList.length-1;
    }
    var regexFloatPercent = /^((\d{1,3}(\.\d{0,2})?)|((\d{1,3}\.)?\{0,2}))$/;
//    var regexFloatPercent = eval("/^((\\d{1,3}(\\" + decimalSeparator + "\\d{0,2})?)|((\\d{1,3}\\" + decimalSeparator + ")?\\{0,2}))$/");
    var regexNumber = /^(\d*)$/;
    var regexFloat = /^((\d+(\.\d*)?)|((\d*\.)?\d+))$/;
    var regexSignedFloat = /^(((\+|-)?\d+(\.\d*)?)|((\+|-)?(\d*\.)?\d+))$/;
	this.checkNumber = function(eventParam, jCell) {
		var value = jCell.val();
		if(jCell.hasClass("number")) {
			if(value!="" && !regexNumber.test(value)) {
				jCell.val(this.backupCurrentValue);
			} else {
				this.backupCurrentValue = jCell.val();
			}
		}
		if(jCell.hasClass("number-float")) {
			if(value!="" && !regexFloat.test(value)) {
				jCell.val(this.backupCurrentValue);
			} else {
				this.backupCurrentValue = jCell.val();
			}
		}
		if(jCell.hasClass("number-signed-float")) {
			if(value!="" && !regexSignedFloat.test(value)) {
				jCell.val(this.backupCurrentValue);
			} else {
				this.backupCurrentValue = jCell.val();
			}
		}
		if(jCell.hasClass("number-float-percent")) {
			if(value!="" && !regexFloatPercent.test(value)) {
				jCell.val(this.backupCurrentValue);
			} else {
//				value = value.replace(eval("/["+decimalSeparator+"]/g"), ".");
				if(eval(value)>100) {
					jCell.val(this.backupCurrentValue);
				}
				this.backupCurrentValue = jCell.val();
			}
		}
		return true;
    }
	this.constructor();
}
//END UpsideDown class


function dumpProps(obj, parent) {
	// Go through all the properties of the passed-in object
	for (var i in obj) {
		// if a parent (2nd parameter) was passed in, then use that to
	    // build the message. Message includes i (the object's property name)
	    // then the object's property value on a new line
    	var msg = i + "\n" + obj[i]; 
	    if (parent) {
	    	msg = parent + "." + i + "\n" + obj[i]; 
	    } 
	    // Display the message. If the user clicks "OK", then continue. If they
	    // click "CANCEL" then quit this level of recursion
	    if (!confirm(msg)) {
	    	return; 
	    }
	    //console.log(msg);
	    // If this property (i) is an object, then recursively process the object
	    if (typeof obj[i] == "object") {
	    	if (parent) {
	    		dumpProps(obj[i], parent + "." + i); 
	    	} else {
	    		dumpProps(obj[i], i); 
	    	}
	    }
	}
}

