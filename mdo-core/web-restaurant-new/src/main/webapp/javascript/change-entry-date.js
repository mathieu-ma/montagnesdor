$(document).ready(function() {
	var jPassword = $(":password[name='"+jQuery.mdoPreSelectorName("form.dtoBean.password")+"']").focus();
	//Init date value
	// Date Full format
	var dateFullFormat = 'DD d MM yy';
	// Date short format
	var dateShortFormat = 'dd/mm/yy';
	// Now date
	var now = new Date();
	var nowTimeOnly = now.getHours() + ":" + now.getMinutes() + ":" + now.getSeconds();
	var entryDate = new Date(parseInt($("#user-entry-date").val()));
	var entryFormattedDate = jQuery.datepicker.formatDate(dateFullFormat, entryDate);
	$("#form-dtoBean-entryDate-datepicker").val(entryFormattedDate);
	var jEntryDateToBeChanged = $("#form-dtoBean-entryDate");
		
	//Cancel
	var jDialog = $("div.alert-dialog");
	var jCancel = $("#cancel").bind("click", function(event) {
		jDialog.dialog("close");
	});
	
	//Get the parent zIndex
	var zIndexDatepicker = eval($("#ChangeEntryDate").parent().parent().css("z-index"))+10;
	var options = {
		beforeShow: function(input, inst) { 
			var datepickerDiv = $("#ui-datepicker-div");
			datepickerDiv.draggable();
			datepickerDiv.css({zIndex: zIndexDatepicker});
			zIndexDatepicker = eval(datepickerDiv.css("z-index"))+10;
			//Replace list of short month names by list of full months names
			//Replace monthNamesShort by monthNames
			var monthNames = jQuery.datepicker._defaults.monthNames;
			$(this).datepicker('option', 'monthNamesShort', monthNames);
			//Move the date picker position
			inst.dpDiv.css({marginTop: -input.offsetHeight + 'px', marginLeft: (input.offsetWidth+15) + 'px'});
		},
		onSelect: function(dateText, inst) { jQuery.mdoFocus(jPassword); },
		onClose: function(dateText, inst) { jQuery.mdoFocus(jPassword); },
		altFormat: "dd/mm/yy",
		//Show Month selection
		changeMonth: true,
		//Show Year selection
		changeYear: true,
		//Only 3 years before and 1 year after the current date for year selection
		yearRange: "-03:+01",
	};
	jEntryDateToBeChanged.hide().mdoDatePicker(options);
	
	//Password input
	jPassword.keyup(function(eventParam) {
		switch(eventParam.keyCode) {     	  
			//13 == key Enter
			case 13 :
				$("#submit").click();
			return;
		};
		return false;
	});
	var jSubmitPassword = $("#submit").click(function(eventParam) {
		if(jPassword.val()) {
			var jForm = $("#ChangeEntryDate");
			var backupedDate = jEntryDateToBeChanged.val();
			jEntryDateToBeChanged.val(backupedDate + " " +nowTimeOnly);
			var params = jForm.serialize();
			$("#ajax-response").hide().load(jForm.attr("action"), params, function() {
				var result = $("#change-entry-date-ajax-response", $(this)).html();
				if(result=="OK") {
					var date = jQuery.datepicker.parseDate("dd/mm/yy", backupedDate);
					date.setHours(now.getHours());
					date.setMinutes(now.getMinutes());
					date.setSeconds(now.getSeconds())
					$("#user-entry-date").val(date.getTime());
					timer();
					jCancel.click();
				} else {
					jEntryDateToBeChanged.val(backupedDate);
					$("#messages-box").css("visibility", "visible");
					$("label", $("#messages-box")).css("display", "none");
					$("#message-2").css("display", "inline");
					jQuery.mdoFocus(jPassword);
				}
			});
		} else {
			$("#messages-box").css("visibility", "visible");
			$("label", $("#messages-box")).css("display", "none");
			$("#message-1").css("display", "inline");
			jQuery.mdoFocus(jPassword);
		}
	});
});
