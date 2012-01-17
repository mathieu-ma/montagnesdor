$(document).ready(function() {
	// For sorting list
	$("table.sortable").each(function() {
		var selectorHeaders = "thead th";
		var headers = {1:{sorter: false}};
		if($(this).hasClass("type")) {
			// Sort only from the Second th==eq(1)(0-based index)
			selectorHeaders = "thead tr:eq(1) th";
			headers = {4:{sorter: false}};
		}
		$(this).mdoTableSorter({
			selectorHeaders: selectorHeaders,
			headers: headers,
		});
	});
	
	$("form").mdoValidate({
		resourceMessages: 'MdoTableAsEnumsManagerWebAction',
		rules: {
			"form.dtoBean.type": {
				required: {
					depends: function(element) {
						return jQuery.trim($(":text[name='"+jQuery.mdoPreSelectorName("form.userEntryType")+"']").val())=="";
					}
				}
			}
		},
		messages: {
			"form.dtoBean.type": "error.emuns.type.required",
			"form.userEntryType": "error.emuns.type.required",
			"form.dtoBean.name": "error.emuns.name.required",
			"form.dtoBean.order": "error.emuns.order.required",
			"form.dtoBean.defaultLabel": "error.emuns.default.label.required",
		},
	});
	
	var jName = $("#MdoTableAsEnumsManagerCUD_form_dtoBean_name").focus();
	var jOrder = $("#MdoTableAsEnumsManagerCUD_form_dtoBean_order");
	var jLanguageKeyLabel = $("#MdoTableAsEnumsManagerCUD_form_dtoBean_languageKeyLabel");
	// If select type is not empty then manual(input text) type is activate
	var jManualType = $("#MdoTableAsEnumsManagerCUD_form_userEntryType").val("").attr("disabled", "disabled"); 
	var jSelectType = $("#MdoTableAsEnumsManagerCUD_form_dtoBean_type").change(function() {
		checkType($(this));
	});
	checkType(jSelectType);
	
	function checkType(jSelect) {
		if(jSelect.val()!="") {
			jManualType.val("").attr("disabled", "disabled");
			jName.focus();
		} else {
			jManualType.removeAttr("disabled").focus();
		}
		jSelect.blur();
	}
	
	function fillLanguageKeyLabel() {
		var type = jSelectType.val() || jManualType.val(); 
		jLanguageKeyLabel.val(type + "." + jName.val() + "." + jOrder.val());
	}

	// This include select and text area
	$(':input').bind("blur keyup", function() {
		fillLanguageKeyLabel(); 
	}); 

//		$.each([jManualType, jName, jOrder], function(index, jObject) {
//			jObject.keyup(function() {
//				fillLanguageKeyLabel(); 
//			});
//		});

	new UpsideDown();
	
  	//Override the Struts validate function
  	//jQuery.mdoSubmit();
});