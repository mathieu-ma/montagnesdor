$(document).ready(
	function()
	{
		new UpsideDown("form#ChangePassword div.mdo-upsidedown");

		//Cancel
		var jDialog = $("div.alert-dialog");
		var jCancel = $("#cancel").bind("click",
			function(event)
			{
				jDialog.dialog("close");
			}
		);

		//Current Password input
		var jPassword = $(":password",$("form#ChangePassword")).keyup(
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
				if(checkRequired() && checkNewPassword())
				{
					var jForm = $("form#ChangePassword");
					var params = jForm.serialize();
					$("#ajax-response").load(jForm.attr("action"), params, 
						function() 
						{
							var result = $("#change-password-ajax-response", $(this)).html();
							if(result=="OK")
							{
								jCancel.click();
							}
							else
							{
								displayMessages([4]);
								jQuery.mdoFocus($("form#ChangePassword"));
							}
						}
					);
				}
				
				function checkRequired()
				{
					var result = true;
					var displayedMessagesIndexes = [];
					$(":password", $("form#ChangePassword")).each(
						function(i)
						{
							if(this.value=="")
							{
								displayedMessagesIndexes.push(i);
								jQuery.mdoFocus($(this));
								result = false;
							}
						}
					);
					if(!result)
					{
						displayMessages(displayedMessagesIndexes);
					}
					return result;
				}
				
				function checkNewPassword()
				{
					var result = true;
					var displayedMessagesIndexes = [];
					var newPassword = "";
					$(":password",$("form#ChangePassword")).each(
						function(i)
						{
							if(i<2)
							{
								newPassword = this.value;
							}
							else
							{
								//i==2
								if(newPassword != this.value)
								{
									displayedMessagesIndexes.push(3);
									jQuery.mdoFocus($(this));
									result = false;
								}
							}
						}
					);
					if(!result)
					{
						displayMessages(displayedMessagesIndexes);
					}
					return result;
				}
				
				function displayMessages(displayedMessagesIndexes)
				{
					$("#displayed-messages li", $("form#ChangePassword")).each(
						function(i)
						{
							$("#displayed-message-"+i).css("visibility", "hidden");
						}
					);
					jQuery.each(displayedMessagesIndexes,
						function(index, value)
						{
							var jMessage = $("#displayed-message-"+index).css("visibility", "visible");
							jMessage.html($("#hidden-message-"+value).html());
						}
					);
					var jMessagesBox = $("#messages-box").css("visibility", "hidden");
					jMessagesBox.css("visibility", "visible");
				}
			}
		);
	}
);

