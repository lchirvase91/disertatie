<!DOCTYPE html>
<html>
<head>
<title>Fa o comanda! Cheama un curier!</title>
<link rel="shortcut icon" href="images/app_icon_32.png"
	type="image/x-icon">
<link href="css/disertatie_style.css" rel="stylesheet" type="text/css" />
<link href='css/styles.css' rel='stylesheet' type='text/css' />
</head>
<body>
	<div id="top_div">
		<img id="header_left_title" src="images/sprint_image.png" /> <a href="index.jsp"><img
			id="header_title" src="images/app_icon_100.png" title="Go to Homepage!"/></a> <img
			id="header_right_title" src="images/curier_image.png" />
	</div>
	<div id="middle_div">
		<div class="profil_public_comanda" align="center">
			<form name="CheamaCurierForm" action="CheamaCurierServlet"
				method="post">
				<table>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<th colspan="2" align="center"><font size=5>Expeditor</font></th>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td><font size="4">Nume:</font></td>
						<td><input type="text" name="nume_exp" id="nume_exp"
							placeholder="Nume" size="50" style="font-size: 15px">
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td><font size="4">Judet:</font></td>
						<td><input type="text" name="judet_exp" id="judet_exp"
							placeholder="Judet" size="30" style="font-size: 15px">
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td><font size="4">Localitate:</font></td>
						<td><input type="text" name="loc_exp" id="loc_exp"
							placeholder="Localitate" size="30" style="font-size: 15px">
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td><font size="4">Adresa:</font></td>
						<td><input type="text" name="adresa_exp" id="adresa_exp"
							placeholder="Adresa" size="100" style="font-size: 15px">
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td><font size="4">Telefon:</font></td>
						<td><input type="text" name="telefon_exp" id="telefon_exp"
							placeholder="Telefon" size="10" style="font-size: 15px">
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td><font size="4">Email:</font></td>
						<td><input type="text" name="email_exp" id="email_exp"
							placeholder="Email" size="50" style="font-size: 15px">
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
						<th colspan="2" align="center"><font size=5>Destinatar</font></th>
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
						<td><font size="4">Nume:</font></td>
						<td><input type="text" name="nume_dest" id="nume_dest"
							placeholder="Nume" size="50" style="font-size: 15px">
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td><font size="4">Judet:</font></td>
						<td><input type="text" name="judet_dest" id="judet_dest"
							placeholder="Judet" size="30" style="font-size: 15px">
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td><font size="4">Localitate:</font></td>
						<td><input type="text" name="loc_dest" id="loc_dest"
							placeholder="Localitate" size="30" style="font-size: 15px">
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td><font size="4">Adresa:</font></td>
						<td><input type="text" name="adresa_dest" id="adresa_dest"
							placeholder="Adresa" size="100" style="font-size: 15px">
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td><font size="4">Telefon:</font></td>
						<td><input type="text" name="telefon_dest" id="telefon_dest"
							placeholder="Telefon" size="10" style="font-size: 15px">
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td><font size="4">Email:</font></td>
						<td><input type="text" name="email_dest" id="email_dest"
							placeholder="Email" size="50" style="font-size: 15px">
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
						<th colspan="2" align="center"><font size=5>Detalii
								comanda</font></th>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td><font size="4">Numar colete:</font></td>
						<td><input type="text" name="nr_colete" id="nr_colete"
							placeholder="Numar colete" size="10" style="font-size: 15px">
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td><font size="4">Greutate totala (kg):</font></td>
						<td><input type="text" name="greutate" id="greutate"
							placeholder="Greutate" size="10" style="font-size: 15px">
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td></td>
					</tr>
					<tr>
						<td><font size="4">Observatii:</font></td>
						<td><textarea name="obs" id="obs" rows="4" cols="30" placeholder="Observatii"
								style="font-size: 15px"></textarea>
					</tr>
				</table>
				<div style="float: center; padding: 30px 0px 0px 0px;">
					<input class="small_button" type="submit" value="Trimite comanda" >
				</div>
			</form>
		</div>
		<div style="position: absolute; left: 0%; top: 40%;">
			<img id="left_side" src="images/left_side.png" />
		</div>

		<div style="position: absolute; right: 0%; top: 40%;">
			<img id="right_side" src="images/right_side.png" />
		</div>
	</div>

	<div id="footer">
		<img id="footer_title" src="images/app_icon_50.png" />Copyright(C)
		2016
	</div>

</body>
</HTML>