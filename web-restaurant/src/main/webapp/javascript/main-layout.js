$(document).ready
(
	function()
	{
		//This is used to prevent Exception in Firefox when using Firebug plugin
		//Comment the following line in production and disable Firebug
		$("input[type!='hidden']").attr("autocomplete", "OFF");
		
		$("#menu, #header, #footer").mdoResizable();

	  	$(window).bind("unload",
	  		function (e)
	  		{
	  			$("#mdo-overlay").css('display', 'block');
	  			//$('#waiting-dialog').dialog('open');
	  			return false;
	  		} 
	  	);
	  	
	  	$("a[href]").bindTop("click",
			function() 
			{
	  			$("#mdo-overlay").css('display', 'block');

	  			if(/^(#)/.test($(this).attr("href")))
	  			{
	  				//Prevent display block for tabs
	  				//$("#mdo-overlay").fadeOut(500);
		  			$("#mdo-overlay").css('display', 'none');
	  				return false;
	  			}
	  			
	  			if($(this).parent().hasClass("mdo-ui-button"))
	  			{
	  				//Call server by clicking parent element
					$(this).parent().click();
					//Don't call server with this link
	  				return false;
	  			}
	  			else
	  			{
	  				window.location = $(this).attr("href");
	  				return false;
	  			}
			}
		);

	  	$(".mdo-ui-button").bindTop("click",
			function() 
			{
	  			$("#mdo-overlay").css('display', 'block');
	  			//$("#mdo-overlay").fadeIn(500);
	  			
  				$("a[href]", $(this)).each(
  					function()
  					{
  						window.location = $(this).attr("href");
  						return true;
  					}
  				);
			}
		);
	}
); 
