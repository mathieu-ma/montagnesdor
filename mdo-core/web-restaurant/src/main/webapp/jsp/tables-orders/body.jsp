



























<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="shortcut icon" type="image/x-icon"
	href="/web-restaurant/favicon.ico" />
<link rel="shortcut icon" type="image/png"
	href="/web-restaurant/favicon.png" />



<link rel="stylesheet" type="text/css"
	href="/web-restaurant/css/montagnesdor-commons.css" />

<link rel="stylesheet" type="text/css"
	href="/web-restaurant/css/jquery-ui/themes/ui-darkness/jquery.ui.all.css" />

<link rel="stylesheet" type="text/css"
	href="/web-restaurant/struts/css_xhtml/styles.css" />




<link rel="stylesheet" type="text/css"
	href="/web-restaurant/css/montagnesdor-header.css" />


<!-- s:head theme="css_xhtml" debug="false"/ -->

<title>???montagnesdor.welcome??? - ???orders.manager.title???</title>
</head>
<body>




	<script type="text/javascript"
		src="/web-restaurant/javascript/jquery/jquery-1.6.4.min.js"></script>

	<script type="text/javascript"
		src="/web-restaurant/javascript/jquery/jquery-ui/minified/jquery-ui-1.8.7.custom.min.js"></script>

	<script type="text/javascript"
		src="/web-restaurant/javascript/mdo-jquery-plugin.js"></script>

	<script type="text/javascript"
		src="/web-restaurant/javascript/jquery/plugins/jquery.i18n.properties-min.js"></script>

	<script type="text/javascript"
		src="/web-restaurant/javascript/jquery/plugins/jquery.tablesorter.min.js"></script>

	<script type="text/javascript"
		src="/web-restaurant/javascript/main-layout.js"></script>

	<script type="text/javascript">
		// This part is for the global javascript constants
		var CURRENT_DECIMAL_SEPARATOR = ",";

		//This is used for dojo i18n
		//dojo.locale="fr";
		//
		//dojo.locale="fr";
		//
	</script>
	<div id="global-transparent" class="global-transparent-hidden">&nbsp;</div>


	<div id="header">
		<div id="mdo-overlay" class="ui-widget-overlay mdo-overlay"></div>
		<div id="waiting-dialog"></div>
		<div id="header-resizable" class="mdo-resizable">

			<!--
	When you use an absolute URI, you do not have to add the taglib element to web.xml; 
	the JSP container automatically locates the TLD inside the JSTL library implementation.
-->







			<script type="text/javascript"
				src="/web-restaurant/javascript/jquery/jquery-ui/i18n/jquery.ui.datepicker-fr.js"></script>
			<script type="text/javascript"
				src="/web-restaurant/javascript/header.js"></script>
			<script type="text/javascript"
				src="/web-restaurant/javascript/change-entry-date.js"></script>

			<div class="vspacer-left-100p">

				<div class="hspacer-left-20p">
					<div class="anchor" title="Changer la date d'entrée des commandes">
						<a id="header-date"
							href="/web-restaurant/orders/ChangeEntryDate!form.action">Chargement
							des applets</a> <a href="../jnlp/printer/printer.jnlp">PROG</a>
					</div>
				</div>

				<div class="header-menu-top">
					<!-- Menu Top : Chargement des fichiers XML et XLS : -->


					<!-- Analyse du fichier XML chargé avec c:import -->


					<!-- Transformation XLS : -->
					<div id="menu-top">
						<ul>
							<li>
								<h2>
									<a
										href="/web-restaurant/orders/User.action?selectedMenuItemId=1">Infos
										utilisateur</a>
								</h2></li>
						</ul>
						<ul>
							<li>
								<h2
									class="&#10;&#9;&#9;&#9;&#9;&#9;&#9;&#9;toggle&#10;&#9;&#9;&#9;&#9;&#9;&#9;">
									<a
										class="&#10;&#9;&#9;&#9;&#9;&#9;&#9;&#9;&#9;toggle&#10;&#9;&#9;&#9;&#9;&#9;&#9;&#9;"
										href="/web-restaurant/orders/TablesOrders!list.action?selectedMenuItemId=2">Gestion
										commandes</a>
								</h2></li>
						</ul>
						<ul>
							<li>
								<h2>
									<a
										href="/web-restaurant/orders/DailyReturns.action?selectedMenuItemId=3">Recettes
										journalières </a>

								</h2></li>
						</ul>
						<ul>
							<li>
								<h2>
									<a
										href="/web-restaurant/orders/MonthlyReturns.action?selectedMenuItemId=4">Recettes
										mensuelles </a>
								</h2></li>
						</ul>
						<ul>
							<li>
								<h2>
									<a
										href="/web-restaurant/orders/Menu.action?selectedMenuItemId=5">Gestion
										du menu </a>

								</h2></li>
						</ul>
					</div>


				</div>
				<div class="clear">&nbsp;</div>
				<div class="hspacer-left-20p" style="margin-top: -15px;">
					<label>Bienvenue Chez Kim-San</label> <img class="logo1"
						src="/web-restaurant/images/logo1.gif" /> <br />

					<!--
	When you use an absolute URI, you do not have to add the taglib element to web.xml; 
	the JSP container automatically locates the TLD inside the JSTL library implementation.
-->







					<img id="temp-image-flag"
						src="/web-restaurant/images/flags/flag_ar.jpg" /> <img
						class="flag-selected" alt='français' title='français'
						src="/web-restaurant/images/flags/flag_fr.jpg" lang="fr" /> <a
						href="http://localhost:9080/web-restaurant/orders/TablesOrders!list.action?selectedMenuItemId=2&amp;request_locale=en"
						class="language" id="en"> <img class="flag-unselected"
						alt="anglais" title="anglais"
						src="/web-restaurant/images/flags/flag_en.jpg" lang="en" /> </a> <a
						href="http://localhost:9080/web-restaurant/orders/TablesOrders!list.action?selectedMenuItemId=2&amp;request_locale=zh"
						class="language" id="zh"> <img class="flag-unselected"
						alt="chinois" title="chinois"
						src="/web-restaurant/images/flags/flag_zh.jpg" lang="zh" /> </a>



				</div>








				<script type="text/javascript"
					src="/web-restaurant/javascript/orders-header.js"></script>


				<script type="text/javascript"
					src="/web-restaurant/javascript/orders-body.js"></script>

				<div id='header-order' class="mdo-upsidedown">

					<form id="HeaderTablesOrders" name="HeaderTablesOrders"
						action="/web-restaurant/orders/TablesOrders!form.action"
						method="post">

						<input type="hidden" name="" value="1318540497436"
							id="user-entry-date" />
						<table style="width: 80%">
							<tbody>
								<tr>
									<td style="width: 5%">
										<div class='header-order-table-number'>

											<div id="wwgrp_input-header-order-table-number" class="wwgrp">
												<span id="wwlbl_input-header-order-table-number"
													class="wwlbl"> <label
													for="input-header-order-table-number" class="label">
														Table n°: </label>
												</span> <span id="wwctrl_input-header-order-table-number"
													class="wwctrl"> <input type="text"
													name="form.dtoBean.number" size="4" maxlength="4" value=""
													id="input-header-order-table-number"
													class="string uppercase" />
												</span>
											</div>

											<input type="hidden" name="form.dtoBean.id" value=""
												id="HeaderTablesOrders_form_dtoBean_id" />
										</div></td>
									<td style="width: 5%">

										<div class='header-order-takeaway'>
											<input type="hidden"
												name="#session.userSession.userAuthentication.restaurant.prefixTakeawayNames"
												value="TAKE_AWAY" id="prefixesTakeawayName" />
											<div id="wwgrp_checkbox-header-order-table-takeaway"
												class="wwgrp">
												<span id="wwlbl_checkbox-header-order-table-takeaway"
													class="wwlbl"> <label
													for="checkbox-header-order-table-takeaway" class="label">Emporter</label>
												</span> <span id="wwctrl_checkbox-header-order-table-takeaway"
													class="wwctrl"> <input type="checkbox"
													name="form.dtoBean.takeaway" value="false"
													id="checkbox-header-order-table-takeaway" /> <input
													type="hidden"
													id="__checkbox_checkbox-header-order-table-takeaway"
													name="__checkbox_form.dtoBean.takeaway" value="false" /> </span>
											</div>

										</div></td>
									<td style="width: 7%">
										<div class='header-order-table-customer'>
											<div id="wwgrp_input-header-order-table-customer"
												class="wwgrp">
												<span id="wwlbl_input-header-order-table-customer"
													class="wwlbl"> <label
													for="input-header-order-table-customer" class="label">
														Nombre de personne(s): </label>
												</span> <span id="wwctrl_input-header-order-table-customer"
													class="wwctrl"> <input type="text"
													name="form.dtoBean.customersNumber" size="4" maxlength="4"
													value="" id="input-header-order-table-customer"
													class="integer" />
												</span>
											</div>

										</div></td>
									<td style="width: 9%">
										<div class='header-order-auto-update'>
											<div id="wwgrp_HeaderTablesOrders_header_order_auto_update"
												class="wwgrp">
												<span id="wwlbl_HeaderTablesOrders_header_order_auto_update"
													class="wwlbl"> <label
													for="HeaderTablesOrders_header_order_auto_update"
													class="label">Ajout Automatique</label>
												</span> <span
													id="wwctrl_HeaderTablesOrders_header_order_auto_update"
													class="wwctrl"> <input type="checkbox"
													name="header.order.auto.update" value="true"
													checked="checked"
													id="HeaderTablesOrders_header_order_auto_update" /> <input
													type="hidden"
													id="__checkbox_HeaderTablesOrders_header_order_auto_update"
													name="__checkbox_header.order.auto.update" value="true" />
												</span>
											</div>

										</div></td>
								</tr>
							</tbody>
						</table>
					</form>




				</div>

			</div>
		</div>
	</div>

	<div id="menu-body-footer">







		<script type="text/javascript"
			src="/web-restaurant/javascript/menu-body-footer-main-layout.js"></script>



		<div id="menu">
			<div id="menu-resizable" class="mdo-resizable">Menu Orders</div>
		</div>

		<div id="body-scroll">






			<link rel="stylesheet" media="screen" type="text/css"
				href="/web-restaurant/css/orders.css" />
			<script type="text/javascript"
				src="/web-restaurant/javascript/orders-body.js"></script>

			<form id="TablesOrders" name="TablesOrders"
				action="/web-restaurant/orders/TablesOrders.action" method="post">

				<input type="hidden" name="method:list" value=""
					id="TablesOrders_method:list" />
				<div class="scroll-table-outer-body">
					<div class="scroll-table-inner-body">
						<table id="listsortable" class="sortable">
							<thead>
								<tr>
									<th>???restaurants.manager.name???</th>

									<th>Couverts</th>
									<th>???restaurants.manager.registration.date???</th>
									<th>???restaurants.manager.reference???</th>
									<th>???restaurants.manager.vat.reference???</th>
									<th>???restaurants.manager.visa.reference???</th>
									<th style="width: 21em;">

										<div style="padding: 7px; float: left;">
											<span class="mdo-ui-button ui-state-default ui-corner-all">
												<span class="ui-icon ui-icon-pencil"></span> <a
												id="TablesOrders_"
												href="/web-restaurant/orders/RestaurantsManager!form.action">???admin.manager.create???</a>
											</span>
										</div>
										<div class="error"></div>

										<div class="error"></div></th>
								</tr>
							</thead>
							<tbody>

								<tr>
									<td>12D</td>

									<td>0</td>
									<td>96,0</td>
									<td>643,30</td>


									<td class="mdo-date">dimanche 27 décembre 2009 19:20:34 <!-- Used for javascript sortable -->

										<input type="hidden" value="2009/12/27" /></td>

									<td class="mdo-date">dimanche 27 décembre 2009 <!-- Used for javascript sortable -->

										<input type="hidden" value="2009/12/27" /></td>
									<td>
										<div style="float: left; padding-right: 1em;">
											<span class="mdo-ui-button ui-state-default ui-corner-all">
												<span class="ui-icon ui-icon-pencil"></span> <a id="278"
												href="/web-restaurant/orders/RestaurantsManager!form.action?form.dtoBean.id=278">???admin.manager.update???</a>
											</span>
										</div>

										<div>
											<span class="mdo-ui-button ui-state-default ui-corner-all">
												<span class="ui-icon ui-icon-trash"></span> <a id="278"
												href="/web-restaurant/orders/RestaurantsManagerCUD!delete.action?form.dtoBean.id=278">???admin.manager.delete???</a>
											</span>

										</div></td>
								</tr>

							</tbody>
						</table>
					</div>
				</div>
			</form>




			<div class="error"></div>

		</div>


		<div id="footer">
			<div id="footer-resizable" class="mdo-resizable">Footer 1</div>
		</div>



	</div>
</body>
</html>
