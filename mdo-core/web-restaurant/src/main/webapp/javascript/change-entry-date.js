$(document).ready(
	function()
	{
		//Init date value
		var dateTimeApplet = document.getElementById('dateTimeApplet');
		var shortEntryDate = dateTimeApplet.getShortEntryDate();
		$("#form-dtoBean-entryDate-datepicker").val(dateTimeApplet.getDate(shortEntryDate));
		var jEntryDate = $("#form-dtoBean-entryDate");
		jEntryDate.val(shortEntryDate);
		
		//Cancel
		var jDialog = $("div.alert-dialog");
		var jCancel = $("#cancel").bind("click",
			function(event)
			{
				jDialog.dialog("close");
			}
		);
		
		//Get the parent zIndex
		var zIndexDatepicker = eval($("#ChangeEntryDate").parent().parent().css("z-index"))+10;
		//Replace list of short month names by list of full months names
		var lang = $("img.flag-selected").attr("lang");
		if(lang=="en")
		{
			lang="";
		}
		var options = {
			beforeShow: function(input, inst) 
			{ 
				var datepickerDiv = $("#ui-datepicker-div");
				datepickerDiv.draggable();
				datepickerDiv.css({zIndex: zIndexDatepicker});
				zIndexDatepicker = eval(datepickerDiv.css("z-index"))+10;
				var monthNames = jQuery.datepicker.regional[lang].monthNames;
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
		jEntryDate.mdoDatePicker(options);

		//Password input
		var jPassword = $(":password[name='"+jQuery.mdoPreSelectorName("form.dtoBean.password")+"']").focus().keyup(
			function(eventParam)
			{
				switch(eventParam.keyCode)
				{     	  
					//13 == key Enter
					case 13 :
						$("#submit").click();
					return;
				};
				return false;
			} 
		);
		var jSubmitPassword = $("#submit").click(
			function(eventParam)
			{
				if(jPassword.val())
				{
					var jForm = $("#ChangeEntryDate");
					var params = jForm.serialize();
					$("#ajax-response").load(jForm.attr("action"), params, 
						function() 
						{
							var result = $("#change-entry-date-ajax-response", $(this)).html();
							if(result=="OK")
							{
								var today = new Date();
								var entryDate = jEntryDate.val()+"/"+today.getHours()+"/"+today.getMinutes()+"/"+today.getSeconds(); 
								dateTimeApplet.setEntryDate(entryDate);
								jCancel.click();
							}
							else
							{
								$("#messages-box").css("visibility", "visible");
								$("label", $("#messages-box")).css("display", "none");
								$("#message-2").css("display", "inline");
								jQuery.mdoFocus(jPassword);
							}
						}
					);
				}
				else
				{
					$("#messages-box").css("visibility", "visible");
					$("label", $("#messages-box")).css("display", "none");
					$("#message-1").css("display", "inline");
					jQuery.mdoFocus(jPassword);
				}
			}
		);
	}
);
