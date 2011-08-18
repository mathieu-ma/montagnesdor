$(document).ready(function() {
	//For changing password
	var jChangePasswordDialog = jQuery.mdoDialog();
	jQuery.each(["0", "1", "2", "3"], function(i, prefix) {
		var jAnchor = $("#change-password-level-"+prefix);
		// Backup the href
		jAnchor.data("href", jAnchor.attr("href"));
		// Reset the href
		jAnchor.attr("href", "#");
		jAnchor.click(function() {
			var href = $(this).data("href");
			jChangePasswordDialog.dialog('option', 'title', $(this).parent().attr("title"));
			jChangePasswordDialog.dialog('option', 'width', 500);
			jChangePasswordDialog.dialog('option', 'height', 360);
			jChangePasswordDialog.unbind('dialogopen').bind('dialogopen', function(event) {
				jChangePasswordDialog.load(href);
			}).bind("dialogclose", function(event) {
				window.setTimeout(function() {jQuery.mdoFocus();}, 100);
				return false;
			});		
			jChangePasswordDialog.html("");
			jChangePasswordDialog.dialog('open');
			return false;
		});
	});
});

