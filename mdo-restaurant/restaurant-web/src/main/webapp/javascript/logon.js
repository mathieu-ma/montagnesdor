$(document).ready(
	function()
	{
		//$(".mdo-upsidedown").upsidedown();
		var upsidedown = new UpsideDown();
		
	  	$("#submit-form").bind("click",
	  		function(e) 
	  		{
 				//Click on submit button
  				$("#hidden-submit-form").click();
 	  			return false;
    		}
    	);

	  	//Override the Struts validate function
	  	jQuery.mdoSubmit();
   	}
);

