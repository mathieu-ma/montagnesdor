var dot = ".";
var blink = "true";
var isFirstTime = "true";
var timerID;
var delay = 1000;

$(document).ready(function() {
	//var dateTimeApplet = document.getElementById('embedDateTimeApplet') || document.getElementById('objectDateTimeApplet');
	var dateTimeApplet = getApplet("dateTimeApplet");
	if($.browser.msie) {
	  	//IE asks user to click on ActiveX before use it
  		dateTimeApplet.click();
  	} else if($.browser.mozilla) {
  		//Hide the object element
  		$("object").css("visibility", "hidden");
  	}
	
	//For changing entry date
	var jChangeEntryDateDialog = jQuery.mdoDialog();
	var jAnchor = $("#header-date");
	// Backup the href
	jAnchor.data("href", jAnchor.attr("href"));
	// Reset the href
	jAnchor.attr("href", "#");
	jAnchor.click(function() {
		var href = $(this).data("href");
		jChangeEntryDateDialog.dialog('option', 'title', $(this).parent().attr("title"));
		jChangeEntryDateDialog.dialog('option', 'width', 500);
		jChangeEntryDateDialog.dialog('option', 'height', 360);
		jChangeEntryDateDialog.unbind('dialogopen').bind('dialogopen', function(event) {
			jChangeEntryDateDialog.load(href);
		}).bind("dialogclose", function(event) {
			window.setTimeout(function() {jQuery.mdoFocus();}, 100);
			return false;
		});		
		jChangeEntryDateDialog.html("");
		jChangeEntryDateDialog.dialog('open');
		return false;
	});

	timer();

//var printerApplet = getApplet("printerApplet");
////Vider le buffer de l'applet 
//printerApplet.resetDataBuffer();
////Entete
//printerApplet.addData2("document.getElementById(");
//printerApplet.print();

});

function getApplet(name) {
	var result = "";
	var jApplets = $("[name="+name+"]", $("#applets")).each(function() {
		try {
			result = $(this).get(0);
			if(result.isActive()) {
				return false;
			}
		} catch(e) {}
	});
	
	return result;
}

function focus() {
	
	window.setTimeout(function() {jQuery.mdoFocus("#input-header-order-table-number");}, 1);
	
//	alert(99)
}

function timer() {
	// Container for displaying date
	var headerDate = $("#header-date");
	// Date Full format
	var dateFullFormat = 'DD d MM yy';
	// Date short format
	var dateShortFormat = 'yy-mm-dd';
	// Now date
	var now = new Date();
	// Date of the user session connection
	var entryDate = new Date(parseInt($("#user-entry-date").val()));
	var entryFormattedDate = jQuery.datepicker.formatDate(dateShortFormat, entryDate);
//	var settings = {dayNamesShort: $.datepicker.regional[locale].dayNamesShort, 
//		dayNames: $.datepicker.regional[locale].dayNames, 
//		monthNamesShort: $.datepicker.regional[locale].monthNamesShort, 
//		monthNames: $.datepicker.regional[locale].monthNames};
	var settings = null;
	
	try {
		// Registration Date of the current table
		var currentTableRegistrationDate = $("#currentTableRegistrationDate");
		if(currentTableRegistrationDate.get(0) && currentTableRegistrationDate.val()!=entryFormattedDate) {
			headerDate.css({color: "#00FF00"});
			headerDate.html(jQuery.datepicker.formatDate(dateFullFormat, new Date(currentTableRegistrationDate.val()), settings));
		} else {
			// Current date
			var shortCurrentDate = jQuery.datepicker.formatDate(dateShortFormat, now);
			if(shortCurrentDate==entryFormattedDate) {
				headerDate.html(jQuery.datepicker.formatDate(dateFullFormat, now, settings));
				headerDate.css({color: "#FFFFFF"});
			} else {	
				headerDate.html(jQuery.datepicker.formatDate(dateFullFormat, entryDate, settings));
				if(blink=="true") {
					headerDate.css({color: "#FF0000"});
					blink = "false";
				} else {
					headerDate.css({color: "#FFFFFF"});
					blink = "true";
				}
			}
		}

		// Current date + Time
		window.status = jQuery.datepicker.formatDate(dateFullFormat, now, settings) + " " + now.getHours() + ":" + now.getMinutes() + ":" + now.getSeconds();
		delay = 1000;
	} catch(err) {
		try {
			//Variable waitLoadAppletsMessage définie dans la page header.jsp
			entryDate.html(entryDate.html()+dot);
		} catch(err) {
			//alert("Appuyer sur OK pour continuer : "+err.message);		
		}
		delay += 500;
	}
	window.setTimeout(function() { timer();}, delay);
}