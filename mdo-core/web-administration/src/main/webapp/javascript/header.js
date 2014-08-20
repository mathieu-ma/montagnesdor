var dot = ".";
var blink = "true";
var isFirstTime = "true";
var timerID;
var delay = 1000;

$(document).ready(function() {
	moment.locale("fr")
//	$("a.language").each(function () {
//		var language = "request_locale";
//		var languageId = $(this).attr("id");
//		var languageHref = $(this).attr("href");
//		if (languageHref.indexOf(language+"=")>0) {
//			// The parameter request_locale could be appeared more than one
//			// So we remove all and then insert back again
//			// Remove language
//			languageHref = languageHref.replace(new RegExp("&"+language+"=..|"+language+"=..&?", "gi"), "");
//			// Insert back again
//			languageHref += "&"+language+"=" + languageId;
//		}
//		$(this).attr("href", languageHref);
//	});

	timer();
});

function timer() {
	var entryDateContainer = $("#header-date");
	var shortDatePattern = "DD/MM/YYYY";
	var largeDatePattern = "dddd DD MMMM YYYY";
	var largeDateTimePattern = "dddd DD MMMM YYYY à HH:mm:ss";
	try {
		var currentTableRegistrationDate = $("#currentTableRegistrationDate");
		if(currentTableRegistrationDate.get(0) && currentTableRegistrationDate.val()) {
			currentTableRegistrationDate = moment(currentTableRegistrationDate, shortDatePattern);
		} else {
			currentTableRegistrationDate = moment();
		}
		var userEntryDate = $("#userEntryDate");
		if(userEntryDate.get(0) && userEntryDate.val()) {
			userEntryDate = moment(userEntryDate, shortDatePattern);
		} else {
			userEntryDate = moment();
		}
		if(currentTableRegistrationDate.format(shortDatePattern)!=userEntryDate.format(shortDatePattern)) {
			entryDateContainer.css({color: "#00FF00"});
			entryDateContainer.html(currentTableRegistrationDate.format(largeDatePattern));
		} else {
			var shortCurrentDate = moment();
		
			if(shortCurrentDate.format(shortDatePattern)==userEntryDate.format(shortDatePattern)) {
				entryDateContainer.html(moment().format(largeDatePattern));
				entryDateContainer.css({color: "#FFFFFF"});
			} else {	
				entryDate.html(userEntryDate.format(largeDatePattern));
				if(blink=="true") {
					entryDateContainer.css({color: "#FF0000"});
					blink = "false";
				} else {
					entryDateContainer.css({color: "#FFFFFF"});
					blink = "true";
				}
			}
		}

		window.status = moment().format(largeDateTimePattern);
		delay = 1000;
	} catch(err) {
		try {
			//Variable waitLoadAppletsMessage définie dans la page header.jsp
			entryDateContainer.html(entryDateContainer.html()+dot);
		} catch(err) {
			//alert("Appuyer sur OK pour continuer : "+err.message);		
		}
		delay += 500;
	}
	window.setTimeout("timer()", delay);
}