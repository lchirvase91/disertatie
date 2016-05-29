<%@ page language="java"%>
<%@ page import="dbConnection.*"%>
<%@ page import="javaBeans.*"%>
<%@ page import="java.sql.*"%>
<link href="css/disertatie_style.css" rel="stylesheet" type="text/css" />
<link href='css/styles.css' rel='stylesheet' type='text/css' />

<%
	String awb = request.getParameter("awb");
	String status = null;
	Date dataComanda = null;
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	String searchQuery = "select * from colet, comanda where colet_comanda_id = comanda_id and colet_awb = ?";

	try {
		conn = ConnectionManager.getConnection();
		pst = conn.prepareStatement(searchQuery);
		pst.setString(1, awb);
		rs = pst.executeQuery();

		if (rs.next()) {
			dataComanda = rs.getDate("comanda_data_comanda");
			status = rs.getString("colet_status");
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (pst != null) {
				pst.close();
				pst = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
%>

<table>
	<tr>
		<td></td>
	</tr>
	<tr>
		<td></td>
	</tr>
	<tr>
		<th colspan="2" align="left"><font size=5>Detalii comanda</font></th>
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
		<td><font size="4">Numar AWB:</font></td>
		<td><input type="text" name="nr_awb" id="nr_awb"
			placeholder="Numar AWB" size="50" style="font-size: 15px" value="<%=awb%>">
	</tr>
	<tr>
		<td></td>
	</tr>
	<tr>
		<td></td>
	</tr>
	<tr>
		<td><font size="4">Data comanda:</font></td>
		<td><input type="text" name="data_comanda" id="data_comanda"
			placeholder="Data comanda" size="50" style="font-size: 15px" value="<%=dataComanda%>">
	</tr>
	<tr>
		<td></td>
	</tr>
	<tr>
		<td></td>
	</tr>
	<tr>
		<td><font size="4">Status:</font></td>
		<td><input type="text" name="status" id="status"
			placeholder="Status" size="50" style="font-size: 15px" value="<%=status%>">
	</tr>
</table>
