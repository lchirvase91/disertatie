<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="javaBeans.UserBean"%>
<%
	UserBean currentUser = ((UserBean) (session
			.getAttribute("currentSessionUser")));
%>
<!DOCTYPE html>
<html>
<head>
<title>Bun venit - <%=currentUser.getPrenume()%> <%=currentUser.getNume()%>!
</title>
<link rel="shortcut icon" href="images/app_icon_32.png"
	type="image/x-icon">
<link href="css/ui-lightness/jquery-ui-1.10.4.css" rel="stylesheet">
<link href="css/disertatie_style.css" rel="stylesheet" type="text/css" />
<link href='css/styles.css' rel='stylesheet' type='text/css' />
<script src="js/jquery.1.10.2.min.js"></script>
<script src="js/jquery-ui-1.10.4.min.js"></script>
<script>
	function hideDisplay() {
		$('.P').hide();
	}
	function includeFile(id) {
		$('.P').hide();
		$(id).show();
	}
</script>
</head>
<body onload="hideDisplay()">
	<div id='cssmenu'>

		<ul>
			<li><img
				style="height: auto; width: auto; max-width: 35px; max-height: 35px;"
				src="images/app_icon_32.png"></li>
			<li style="float: right;" class='has-sub'><a href='#'><span><img
						style="vertical-align: middle; height: auto; width: auto; max-width: 20px; max-height: 20px;"
						src="images/user_photo.png"> <%=currentUser.getUsername()%>
						(<%=currentUser.getPrenume()%> <%=currentUser.getNume()%>)</span></a>
				<ul>
					<li><a href='#SchimbareParola'
						onclick="includeFile('#SchimbareParola')"><span>Schimbare
								parola</span></a></li>
					<li class='last'><a href='LogoutServlet'><span>Iesire</span></a></li>
				</ul></li>
		</ul>
	</div>

	<div id="SchimbareParola" class="P"><%@ include
			file="schimbareParola.jsp"%></div>


</body>
</html>