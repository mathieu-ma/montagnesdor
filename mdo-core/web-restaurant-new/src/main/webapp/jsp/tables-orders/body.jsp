<%@ taglib uri="http://java.sun.com/jsp/jstl/core".com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"om/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<style type="text/css">

	.mdo-restaurant-table tbody td
	{
		text-align: center;
	} 

	.td1
	{
		width: 9%;
	}
	.th2, .td2 
	{
		width: 9%;
	}
	
	.th3, .td3 
	{
		width: 64%;
	}

	.th4, .td4 
	{
		width: 9%;
	}

	.th5, .td5 
	{
		width: 9%;
	}
	
	table.mdo-restaurant-table input[type=text]
	{
		width: 90%;
	}

</style>
<script>
$(document).ready(
	function()
	{
		var jQuantity;
		var jCode;
		var jLabel;
		var jUnitPrice;
		var jAmount;
		$(":text", $(".order-line")).each(
			function(index)
			{
				if(index==0)
				{
					jQuantity = $(this);
				}
				if(index==1)
				{
					jCode = $(this);
				}
				if(index==2)
				{
					jLabel = $(this);
				}
				if(index==3)
				{
					jUnitPrice = $(this);
				}
				if(index==4)
				{
					jAmount = $(this);
				}
			}
		);

		//New options for jQuantity
		var options = {
			preProcessEnter: function(jCell) {
				if(jCell.val()=="")
				{
					jCell.val(jQuery.mdoFormatNumber("1", 2));
				}
			},
			processEnter: function(jCell) {
				jCell.val(jQuery.mdoFormatNumber(jCell.val(), 2));
				displayCode();
			}
		};
		jQuantity.mdoInputText(options);

		//New options for jCode
		options = {
			processEnter: function(jCell) {
				jCell.val(jCell.val().toUpperCase());
				//Check product special code
				displayOrderLine(jCell);
			},
			processEsc: function(jCell) {
				displayQuantity();
			},
		};
		jCode.mdoInputText(options);
		//URL for ajax call
		var autoCompleteProductsCodesUrl = $("#TablesOrders").attr("action").replace("form", "autoCompleteProductsCodes");		
		jCode.autocomplete(autoCompleteProductsCodesUrl, 
			{
				extraParams: {"form.dtoBean.prefixProductCode": function() {return checkAutoCompleteProductsCodesParams();}},
				cacheLength: 0,
				selectFirst: true,
				scroll: true,
				scrollHeight: 250,
				width: 75
			}
		).result( function(event, data, formatted) {
			if(data)
			{
				//alert(data[1]);
			}
		});
		function checkAutoCompleteProductsCodesParams()
		{
			var result = jCode.val().toUpperCase();
			return result;
		}
		function displayCode()
		{
			jCode.removeAttr("readOnly");
			jCode.focus();
		}
		function displayQuantity()
		{
			jCode.attr("readOnly", "readonly");
			jQuantity.removeAttr("readOnly");
			jQuantity.focus();
		}
		//URL for ajax call
		var saveOrderLineUrl = $("#TablesOrders").attr("action").replace("form", "saveOrderLineUrl");
		//Table Number, Customer Number, 
		//Order Line id(maybe empty), Product code, Quantity, Label(maybe empty), Unit Price(maybe empty)	
		var params = {"form.dtoBean.dinnerTableDtoBean.tableNumber":function() {return jTableNumber.val().toUpperCase();}};
		function displayOrderLine(jCell)
		{
			//Variable to store data from ajax call
			var jAjaxOrderLine = "";
			//Ajax call to check the entry code
			jQuery.post(saveOrderLineUrl, params, 
				function (data) {
					//Get data from ajax call
					jAjaxOrderLine = $("<div></div>").html(data);
				}
			);

			//Depending on the ajax result: check if we display the orderline and add a new orderline
			//or display the label of this order line
			
			//Try to know if we have to display(enable) label of this order line 
			var jOrderLineByUser = $("#ajax-order-line-by-user", jAjaxOrderLine);
			var jOrderLine = $("#ajax-order-line", jAjaxOrderLine);
			if(jAjaxOrderLineByUser.val()=="true")
			{
				
			}
			else
			{
				//Insert the saved order line

			}
		}
		
		jQuery.mdoFocus();
	}
);

</script>
		<div class="table-scroller-header">
			<div class="table-scroller">
	<form name="OrdersLines">
				<table class="mdo-restaurant-table">
					<thead>
						<tr>
						    <th scope="col" class="th1"><label class="policeGray"><fmt:message key="tables.orders.label.quantity"/></label></th>
						    <th scope="col" class="th2"><label class="policeGray"><fmt:message key="tables.orders.label.code"/></label></th>
						    <th scope="col" class="th3"><label class="policeGray"><fmt:message key="tables.orders.label.description"/></label></th>
						    <th scope="col" class="th4"><label class="policeGray"><fmt:message key="tables.orders.label.unit.price"/></label></th>
						    <th scope="col" class="th5"><label class="policeGray"><fmt:message key="tables.orders.label.amount"/></label></th>
						</tr>
					</thead>
					<tfoot>
						<tr class="order-line" id="order-line-end">
							<td class="td1"><input type="text" maxlength="5" size="10" class="number-float" value=""/></td>
							<td class="td2"><input type="text" maxlength="5" size="10" class="string-code uppercase" readonly="readonly" value="11"/></td>
							<td class="td3"><input type="text" maxlength="5" size="10" class="string" readonly="readonly" value=""/></td>
							<td class="td4"><input type="text" maxlength="5" size="10" class="number-float" readonly="readonly" value=""/></td>
							<td class="td5"><input type="text" maxlength="5" size="10" class="number-float" readonly="readonly" value=""/></td>
						</tr>
					</tfoot>
					<tbody>
						<tr>
							<td class="td1"><input type="text" maxlength="5" size="10" class="number-float" readonly="readonly" value="1.8"/></td>
							<td><input type="text" maxlength="5" size="10" class="number-float" readonly="readonly" value="PARIS"/></td>
							<td><input type="text" maxlength="5" size="10" class="string" readonly="readonly" value="11:45"/></td>
							<td><input type="text" maxlength="5" size="10" class="number-float" readonly="readonly" value="11:57"/></td>
							<td><input type="text" maxlength="5" size="10" class="number-float" readonly="readonly" value="LANDED"/></td>
						</tr>
						<tr>
							<td class="td1 number-float" scope="row">T3 4264</td>
							<td class="td2 string">ISLE</td>
							<td class="td3 string">11:40</td>
							<td class="td4 number-float">11:42</td>
							<td class="td5 number-float">LANDED</td>
						</tr>
						<tr>
							<td scope="row">BE 843</td>
							<td>BELFAST</td>
							<td>11:45</td>
							<td>11:40</td>
							<td>LANDED</td>
						</tr>
						<tr>
							<td scope="row">GR 642</td>
							<td>GUERN</td>
							<td>11:55</td>
							<td>11:38</td>
							<td>LANDE</td>
						</tr>
					</tbody>
				</table>
	</form>
			</div>
		</div>

<div class="error"><s:actionerror /></div>