$(document).ready(
	function()
	{
		//$(".mdo-upsidedown").upsidedown();
		var upsidedown = new UpsideDown();
		
	  	$("#administration").bind("click",
	  		function(e) 
	  		{
 				//Click on submit button
  				$("#hidden-submit-form").click();
 	  			return false;
    		}
    	);

	  	//Override the Struts validate function
	  	jQuery.mdoSubmit();
	  	
	  	// Force the focus even this is already done in UpsideDown plug-in
	  	$("form :text").focus();
   	}
);

