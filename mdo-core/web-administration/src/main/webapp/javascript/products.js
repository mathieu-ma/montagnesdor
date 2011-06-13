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

	var jColorPicker = $('#colorSelector');
	if (jColorPicker.get(0)) {
		var jCellColor = $("#colorRGB");
		var initColor = jCellColor.val()||'884444';
		jColorPicker.ColorPicker({
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
	}

	function initFileUploads() {
		var jFileInputs = $("#fileinputs").css({position: "relative"})
		var jFile = jFileInputs.children("#file");
		var jImportData = jFileInputs.children("button");

		jFile.css({position: "absolute", top: "9px", left: "0px", opacity: "0", zIndex: "2", marginTop: "0px", float: "none"});
		
		// Load the property fake.input.file.browse
		jQuery.mdoI18nProperties({
			keys: ["fake.input.file.browse"]
		});

		var jFakeFileUploadDiv = $('<div id="fakefilediv"><input id="fakefile" /><span class="mdo-ui-button-file ui-state-default ui-corner-all"><span class="ui-icon ui-icon-plusthick"></span>'+jQuery.i18n.prop("fake.input.file.browse")+'</span></div>');
		jFakeFileUploadDiv.insertAfter(jFile);
		var jFakeFileUploadDiv = $('#fakefilediv'); 
		jFileInputs.css("width", (jFile.width()+jImportData.width()+50) + "px");
		jFileInputs.css("width", (jFakeFileUploadDiv.width()+jImportData.width()+20) + "px");
		jFakeFileUploadDiv.click(function() {
			jFile.click();
		});
		jFile.change(function() {
			$('#fakefile').val($(this).val());
		}); 
	}
	initFileUploads();
});