$(document).ready(
	function()
	{
		//For changing password
		var jChangePasswordDialog = jQuery.mdoDialog();
		jQuery.each(["0", "1", "2", "3"], 
			function(i, prefix)
			{
				$("#change-password-level-"+prefix).click(
					function()
					{
						jChangePasswordDialog.dialog('option', 'title', $(this).attr("title"));
						jChangePasswordDialog.dialog('option', 'width', 500);
						jChangePasswordDialog.dialog('option', 'height', 360);
						jChangePasswordDialog.unbind('dialogopen').bind('dialogopen',
							function(event)
							{
								jChangePasswordDialog.load("/mdo-web-restaurant/restaurant/ChangePassword!form.action?form.dtoBean.levelPassword="+prefix);
							}
						)
						.bind("dialogclose",
							function(event)
							{
								window.setTimeout(function() {jQuery.mdoFocus();}, 100);
								return false;
							}
						);		
						jChangePasswordDialog.html("");
						jChangePasswordDialog.dialog('open');
					}
				);
			}
		);
	}
);

