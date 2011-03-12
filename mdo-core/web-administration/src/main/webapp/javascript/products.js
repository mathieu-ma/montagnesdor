$(document).ready(
	function()
	{
		if($(":hidden[name='form.dtoBean.daoBean.id']").length==0)
		{
			//Used in product list-body 
			var productsManagerWarnRemoveProductVatLabel = $("#products-manager-warn-create-product-vat-label");
			if(productsManagerWarnRemoveProductVatLabel.length>0)
			{
				var jAlertNoVats = jQuery.mdoDialog();
				
				var jCreateProduct = $("#create-product").unbind("click").click(
					function(event)
					{
						$("a", $(this)).unbind();
						//Change the selectedMenuItemId to point to the ValueAddedTaxesManager.action item
						jAlertNoVats.html(productsManagerWarnRemoveProductVatLabel.html());
						jAlertNoVats.dialog('open');
						return false;
					}
				).click();
			}
			return;
		}
		
		var languages = $("#languages").mdoCrudList();
		var categories = $("#categories").mdoCrudList();

		var tabProduct = new UpsideDown(".mdo-upsidedown-1");
		var tabLabels = new UpsideDown(".mdo-upsidedown-2");
		new UpsideDown(".mdo-upsidedown-3");

		var selectedTab = $(":hidden[name='form.selectedTab']");
		var tabs = $("#tabs").tabs({
			selected: selectedTab.val()
		});
		var jCurrentTab = $("#tabs-1");
		tabs.bind('tabsshow', function(event, ui) {
			selectedTab.val(ui.index);
			jCurrentTab = $(ui.panel);
			jQuery.mdoFocus($(ui.panel));
		});

		$("a.a-tabs").each(
			function(index)
			{
				$(this).bind("click", 
					function()
					{
						if(languages.data("jEntryData").val()=="")
						{
							$("option:first", languages.data("jSelectList")).attr("selected","selected");
							languages.data("jSelectList").change();
						}
						if(categories.data("jEntryData").val()=="")
						{
							$("option:first", categories.data("jSelectList")).attr("selected","selected");
							categories.data("jSelectList").change();
						}
						jQuery.mdoFocus($($(this).attr("href"))); 
					}
				);
				if(index==selectedTab.val())
				{
					jQuery.mdoFocus($($(this).attr("href")));
				}
			}	
		);
		
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

		$("#submit").click(
	  		function()
	  		{
	  			$("#method-save").removeAttr("disabled");
	  			$("#method-labels").attr("disabled", "disabled");
	  			$("#hidden-submit-form").click();
			}
		);

		var jAlert = jQuery.mdoDialog($("#tabs-1"));
		jAlert.bind("dialogclose",
			function(event)
			{
				window.setTimeout(function() {jQuery.mdoFocus(jCurrentTab);}, 100);
				return false;
			}
		);
		var jCode = $(":text[name='"+jQuery.mdoPreSelectorName("form.dtoBean.daoBean.code")+"']");
		var productsManagerWarnRemoveProductOneLabel = $(":hidden[name='products-manager-warn-remove-product-one-label']").val(); 
		var productsManagerWarnRemoveProductEmptyLabel = $(":hidden[name='products-manager-warn-remove-product-empty-label']").val(); 
		var productsManagerWarnRemoveProductLabel = $(":hidden[name='products-manager-warn-remove-product-label']").val(); 
		$(".crud-list-remove", languages).unbind("click");
		$(".crud-list-remove", languages).bind("click",
			function()
			{
				var jHiddenSelectedId = $(":hidden[name='selectedId']", $(this));
				$("#mdo-overlay").hide();
				if(jHiddenSelectedId.get(0) || languages.data("jHiddenData").get(0) && languages.data("jSelectList").val()==languages.data("jHiddenData")[1].value && languages.data("jEntryData").val()!="")
				{
					jAlert.html(productsManagerWarnRemoveProductOneLabel);
				}
				else if(languages.data("jEntryData").val()=="")
				{
					jAlert.html(productsManagerWarnRemoveProductEmptyLabel);
				}
				else
				{
					jAlert.html(productsManagerWarnRemoveProductLabel);
				}
				jAlert.dialog('open');
			}
		);
		var jSpanTabProduct = $("a.a-tabs[href='#tabs-1']:first");
		var tabProductLabel = jSpanTabProduct.html();
		tabProduct.keyup = function()
		{
			if(jCode.val()=="")
			{
				tabs.tabs('option', 'disabled', [1, 2]);
				//Unbind click for crud plugin
				$("#hidden-submit-form").unbind("click");
			}
			else
			{
				tabs.tabs('option', 'disabled', [2]);
				if(languages.data("jHiddenData").length==0)
				{
					if(jCode.attr("disabled"))
					{
						tabProduct.esc = function() {return false};
						tabLabels.esc = function() {return false};
						$(".admin-manager-cancel").each(
							function()
							{
								var jParent = $(this).parent();
								//Mdo unbind all
								jParent.unbind("click");
								jParent.bind("click",
									function()
									{
										jQuery.mdoFocus(jCurrentTab);
										return false;
									}
								);
								$(this).attr("href", "#");
								$(this).click(
									function()
									{
										jQuery.mdoFocus(jCurrentTab);						
										return false;
									}
								);
							}
						);
					}
				}
				else
				{
					tabs.tabs('option', 'disabled', []);
				}
			}
			jSpanTabProduct.html(tabProductLabel+" "+jCode.val());
			return true;
		}
		//Simulate key up for initializing page
		tabProduct.keyup();
		$("#hidden-submit-form").bind("click", function()
		{
			var result = languages.data("jHiddenData").length==0 && languages.data("jEntryData").val()=="";
			if(result)
			{
				$("#mdo-overlay").hide();
	  			jAlert.html($(":hidden[name='products-manager-warn-create-product-label']").val());
	  			jAlert.dialog('open');
			}
			else
			{
				if(languages.data("jEntryData").val()!="")
				{
					$("form").submit();
				}
			}
			return false;
		});

		//Override the Struts validate function
	  	jQuery.mdoSubmit();
   	}
);