<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page language="java"%>
<!DOCTYPE html>
<HTML>
<head>
<title>Sistem de gestiune si monitorizare pentru o firma de curierat
</title>
<link rel="shortcut icon" href="images/app_icon_32.png"
	type="image/x-icon">
<link href="css/disertatie_style.css" rel="stylesheet" type="text/css" />
<link href="css/ui-lightness/jquery-ui-1.10.4.css" rel="stylesheet">
<script>
	function showLoadBar() {
		document.getElementById('loadBar').style.display = 'block';
	}
</script>
</head>
<body>


	<div id="top_div">
		<img id="header_left_title" src="images/sprint_image.png" />
		<img id="header_title" src="images/app_icon_100.png" />
		<img id="header_right_title" src="images/curier_image.png" />
	</div>

	<div id="middle_div">
		<form name="logginForm" action="LoginServlet" method="post">
			<div class="wrapper">
				<div class="main">
					<div class="logo" style="float: left;"></div>
					<div style="padding-top: 5px; float: right; max-width: 320px;">
						<div
							style="float: left; padding: 4px 10px 4px 0px; font-weight: bold; font-size: 11px; color: #ffffff;">Autentificare</div>
						<div style="float: right; padding: 4px 10px 4px 0px;">
							<input type="text" name="uname" id="uname" placeholder="User"
								size="40">
						</div>
						<div style="float: right; padding: 4px 10px 4px 0px;">
							<input type="password" name="upassword" id="upassword"
								placeholder="Parola" size="40">
						</div>
						<div style="float: right; display: none;" id="loadBar"></div>
						<div style="float: right; padding: 4px 10px 4px 0px;">
							<input class="btn" type="submit" value="Login"
								onclick="showLoadBar()">
						</div>
					</div>
				</div>
			</div>
		</form>

		<div style="position: absolute; left: 0%; top: 40%;">
			<img id="left_side" src="images/left_side.png" />
		</div>

		<div style="position: absolute; right: 0%; top: 40%;">
			<img id="right_side" src="images/right_side.png" />
		</div>

		<div style="position: absolute; left: 10%; top: 80%;">
			<input class="big_button" type="button" value="Cheama curier"
				onclick="location.href='cheamaCurier.jsp'">
		</div>
		<div style="position: absolute; right: 10%; top: 80%;">
			<input class="big_button" type="button" value="Verifica AWB"
				onclick="location.href='verificareAWB.jsp'">
		</div>
	</div>
	<div id="bottom_div">
		<div id="footer">
			<img id="footer_title" src="images/app_icon_50.png" />Copyright(C)
			2016
		</div>
	</div>
</body>
</HTML>