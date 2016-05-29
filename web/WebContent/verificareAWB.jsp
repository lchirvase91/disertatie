<!DOCTYPE html>
<html>
<head>
<title>Verifica AWB!</title>
<link rel="shortcut icon" href="images/app_icon_32.png"
	type="image/x-icon">
<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
<link href="css/disertatie_style.css" rel="stylesheet" type="text/css" />
<link href='css/styles.css' rel='stylesheet' type='text/css' />
</head>
<body>
	<div id="top_div">
		<img id="header_left_title" src="images/sprint_image.png" /> <img
			id="header_title" src="images/app_icon_100.png" /> <img
			id="header_right_title" src="images/curier_image.png" />
	</div>

	<div id="middle_div">
		<div class="profil_public_awb" align="center">
			<table>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<th colspan="2" align="left"><font size=5>Cauta un colet</font></th>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="text"
						name="search_awb" id="search_awb" placeholder="AWB" size="50"
						style="font-size: 15px">
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input class="small_button"
						type="button" value="Cauta" id="bt_Cautare" onClick = "show()"></td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td></td>
				</tr>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td></td>
				</tr>
			</table>
		</div>
		<div class="profil_public_awb" id="afisare_rezultat" style="display:none"></div>
		<div style="position: absolute; left: 0%; top: 40%;">
			<img id="left_side" src="images/left_side.png" />
		</div>

		<div style="position: absolute; right: 0%; top: 40%;">
			<img id="right_side" src="images/right_side.png" />
		</div>
	</div>


	<div id="bottom_div">
		<div id="footer">
			<img id="footer_title" src="images/app_icon_50.png" />Copyright(C)
			2016
		</div>
	</div>

</body>
<script type="text/javascript">
	showColetInfos = function(awb) {
		$.get("infoAWB.jsp", {
			awb : awb,
		}).done(function(data) {
			$("#afisare_rezultat").html(data);
		});
		$("#afisare_rezultat").show();
	}



	$(function() {
		$("#bt_Cautare").click(function() {
			showColetInfos($("#search_awb").val());
		});
	});

</script>


</HTML>

