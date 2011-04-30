$(document).ready(function() {
	// For sorting list
	$("#listsortable").mdoTablesorter({
		headers: {1:{sorter: false}},
	});

	// For sorting list
	$("#listproductssortable").mdoTablesorter({
		headers: {1:{sorter: false}},
	});

	new UpsideDown(".mdo-upsidedown-1");

	$("#tabs").tabs();

	$("#languages").mdoCrudList();

	var jCellColor = $("#colorRGB");
	var initColor = jCellColor.val()||'884444';
	var jColorPicker = $('#colorSelector').ColorPicker({
		color: initColor,
		onShow: function (colpkr) {
			$(colpkr).fadeIn(500);
			return false;
		},
		onHide: function (colpkr) {
			$(colpkr).fadeOut(500, function(){jQuery.mdoFocus();});
			return false;
		},
		onChange: function (hsb, hex, rgb) {
			jColorPicker.css('backgroundColor', '#' + hex);
			jCellColor.val(hex);
		},
		onSubmit: function(hsb, hex, rgb, el) {
			var jColpkr = $('#' + $(el).data('colorpickerId'));
			jCellColor.val(hex);
			jColpkr.fadeOut(500, function(){$(document).unbind('mousedown'); jQuery.mdoFocus();});
			return false;
		}
	});
	jColorPicker.css('backgroundColor', '#' + initColor);
});