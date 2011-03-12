var dot = ".";
var blink = "true";
var isFirstTime = "true";
var timerID;
var delay = 1000;

$(document).ready(function() {
	if($.browser.msie) {
	  	//IE asks user to click on ActiveX before use it
  		document.dateTimeApplet.click();
  	} else if($.browser.mozilla) {
  		//Hide the object element
  		$("object").css("visibility", "hidden");
  	}
		
	$("a.language").each(function () {
		var languageId = $(this).attr("id");
		var languageHref = $(this).attr("href");
		if (languageHref.indexOf("language=")>0) {
			// The parameter language could be appeared more than one
			// So we remove all and then insert back again
			// Remove language
			languageHref = languageHref.replace(/&language=..|language=..&?/gi, "");
			// Insert back again
			languageHref += "&language=" + languageId;
		}
		$(this).attr("href", languageHref);
	});

	timer();
});

function timer() {
	var dateTimeApplet = document.getElementById('dateTimeApplet');
	var entryDate = $("#header-date");
	try {
		var currentTableRegistrationDate = $("#currentTableRegistrationDate");
		var shortEntryDate = dateTimeApplet.getShortEntryDate();
		if(currentTableRegistrationDate.get(0) && currentTableRegistrationDate.val()!=shortEntryDate) {
			entryDate.css({color: "#00FF00"});
			entryDate.html(dateTimeApplet.getDate(currentTableRegistrationDate.val()));
		} else {
			var shortCurrentDate = dateTimeApplet.getDateShort()+"";
		
			if(shortCurrentDate==shortEntryDate) {
				entryDate.html(dateTimeApplet.getDate());
				entryDate.css({color: "#FFFFFF"});
			} else {	
				entryDate.html(dateTimeApplet.getDate(shortEntryDate));
				if(blink=="true") {
					entryDate.css({color: "#FF0000"});
					blink = "false";
				} else {
					entryDate.css({color: "#FFFFFF"});
					blink = "true";
				}
			}
		}

		window.status = dateTimeApplet.getDateTime().toString();
		delay = 1000;
	} catch(err) {
		//alert("Appuyer sur OK pour continuer : "+err.message);
		try {
//console.log("1=="+err);
			//Variable waitLoadAppletsMessage définie dans la page header.jsp
			entryDate.html(entryDate.html()+dot);
		} catch(err) {
			//alert("Appuyer sur OK pour continuer : "+err.message);		
		}
		delay += 500;
	}
	window.setTimeout("timer()", delay);
}