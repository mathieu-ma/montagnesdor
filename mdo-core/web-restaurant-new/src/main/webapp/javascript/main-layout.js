$(document).ready(function() {
		//This is used to prevent Exception in Firefox when using Firebug plugin
		//Comment the following line in production and disable Firebug
		$("input[type!='hidden']").attr("autocomplete", "OFF");

		$("#menu, #header, #footer").mdoResizable();

		$('#waiting-dialog').dialog({
			autoOpen: false,
			closeOnEscape: false,
			position: 'center',
			closeText: '',
			modal: true,
			resizable: false,
			dialogClass: 'waiting-hidden-dialog-title-bar'
		});

	  	$(window).bind("unload", function (e) {
  			$("#mdo-overlay").css('display', 'block');
  			//$('#waiting-dialog').dialog('open');
  			return false;
	  	});
	  	
	  	$("a[href]").bindTop("click", function() {
  			var jOverlay = $("#mdo-overlay").css('display', 'block');

  			if(/^(#)/.test($(this).attr("href"))) {
  				//Prevent display block when using tabs for example
  				//$("#mdo-overlay").fadeOut(500);
  				$("#mdo-overlay").css('display', 'none');
  				return true;
  			}

  			if($(this).parent().hasClass("mdo-ui-button")) {
  				//Call server by clicking parent element
				$(this).parent().click();
				//Don't call server with this link
  				return false;
  			} else {
  				window.location = $(this).attr("href");
  				return false;
  			}
		});

	  	$(".mdo-ui-button[type!=reset]").bindTop("click", function() {
  			$("#mdo-overlay").css('display', 'block');
  			//$("#mdo-overlay").fadeIn(500);
  			
			$("a[href]", $(this)).each(function() {
	  			if ($(this).hasClass("mdo-no-overlay")) {
	  				//Prevent display block
	  				//$("#mdo-overlay").fadeOut(500);
	  				$("#mdo-overlay").css('display', 'none');
	  			}
				window.location = $(this).attr("href");
				return true;
			});
		});
	  	
		var jTempImageFlag = $("#temp-image-flag");
		$("img.flag-unselected,img.flag-selected").each(function() {
			this.onerror = function() {
				this.onerror=null;
				this.src = jTempImageFlag.attr("src");
				//alert("This image error="+jTempImageFlag.attr("src"));
			}
			//Reload the image to check if the image exists
			this.src = this.src;
		});
}); 
