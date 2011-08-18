$(document).ready(
	function()
	{
		if($.browser.msie) 
	  	{
		  	//IE asks user to click on ActiveX before use it
	  		document.dateTimeApplet.click();
	  	}
	  	else if($.browser.mozilla)
	  	{
	  		//Hide the object element
	  		$("object").css("visibility", "hidden");
	  	}
		
		$('#waiting-dialog').dialog({
			autoOpen: false,
			closeOnEscape: false,
			position: 'center',
			closeText: '',
			modal: true,
			resizable: false,
			dialogClass: 'waiting-hidden-dialog-title-bar'
		});
		
		var jTempImageFlag = $("#temp-image-flag");
		$("img.flag-unselected,img.flag-selected").each(
			function()
			{
				this.onerror = function()
				{
					this.onerror=null;
					this.src = jTempImageFlag.attr("src");
					//alert("This image error="+jTempImageFlag.attr("src"));
				}
				//Reload the image to check if the image exists
				this.src = this.src;
			}
		);

		//For changing entry date
		var jEntryDateDialog = jQuery.mdoDialog();
		$("#header-date").click(
			function()
			{
				jEntryDateDialog.dialog('option', 'title', $("#header-date").attr("title"));
				jEntryDateDialog.dialog('option', 'width', 450);
				jEntryDateDialog.dialog('option', 'height', 250);
				jEntryDateDialog.unbind('dialogopen').bind('dialogopen',
					function(event)
					{
						jEntryDateDialog.load($(":hidden[name='changeEntryDateUrl']").val());
					}
				)
				.bind("dialogclose",
					function(event)
					{
						window.setTimeout(function() {jQuery.mdoFocus();}, 100);
						return false;
					}
				);		
				jEntryDateDialog.html("");
				jEntryDateDialog.dialog('open');
			}
		);
		timer();
	}
);


function timer() 
{
	var dateTimeApplet = document.getElementById('dateTimeApplet');
	var entryDate = $("#header-date");
	try
	{
		//currentTableRegistrationDate date of the current table
		var currentTableRegistrationDate = $("#currentTableRegistrationDate");
		//shortEntryDate starting date of the applet
		var shortEntryDate = dateTimeApplet.getShortEntryDate();

		if(currentTableRegistrationDate.get(0) && currentTableRegistrationDate.val()!=shortEntryDate)
		{
			entryDate.css({color: "#00FF00"});
			entryDate.html(dateTimeApplet.getDate(currentTableRegistrationDate.val()));
		}
		else
		{
			//Current date
			var shortCurrentDate = dateTimeApplet.getDateShort()+"";
		
			if(shortCurrentDate==shortEntryDate)
			{
				entryDate.html(dateTimeApplet.getDate());
				entryDate.css({color: "#FFFFFF"});
			}
			else
			{	
				entryDate.html(dateTimeApplet.getDate(shortEntryDate));
				if(jQuery.fn.mdoVariables.blink=="true")
				{
					entryDate.css({color: "#FF0000"});
					jQuery.fn.mdoVariables.blink = "false";
				}
				else
				{
					entryDate.css({color: "#FFFFFF"});
					jQuery.fn.mdoVariables.blink = "true";
				}
			}
		}

		window.status = dateTimeApplet.getDateTime().toString();
		jQuery.fn.mdoVariables.delay = 1000;
	}
	catch(err)
	{
		//alert("Appuyer sur OK pour continuer : "+err.message);
		try
		{
//console.log("1=="+err);
			//Variable waitLoadAppletsMessage définie dans la page header.jsp
			entryDate.html(entryDate.html()+jQuery.fn.mdoVariables.dot);
		}
		catch(err)
		{
			//alert("Appuyer sur OK pour continuer : "+err.message);		
		}
		jQuery.fn.mdoVariables.delay += 500;
	}
	//window.setTimeout("timer()", delay);
}
