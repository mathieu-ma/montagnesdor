$(document).ready(
	function()
	{
		$("#languages").mdoCrudList();

		new UpsideDown();

	  	//Override the Struts validate function
	  	jQuery.mdoSubmit();
   	}
);