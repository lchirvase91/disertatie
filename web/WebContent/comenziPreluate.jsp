<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page language="java"%>
<%@ page import="dbConnection.*"%>
<%@ page import="javaBeans.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>

<table align="center" text-align="center" border="1">
	<tr>
		<th colspan=4>Lista comenzi preluate</th>
	</tr>
	<tr>
		<th>Id comanda</th>
		<th>Numar colete</th>
		<th>Data comanda</th>
		<th>Generare AWB</th>
	</tr>
	<%
		try {
			conn = ConnectionManager.getConnection();
			String comenzi = "select distinct comanda_id, comanda_nr_colete, comanda_data_comanda from client, comanda, colet where client_id = comanda_exp_id and comanda_id = colet_comanda_id  and colet_awb is null and colet_status = 'preluat' and client_judet = (select hub_judet from hub where hub_id = (select user_hub_id from user where user_userlog_id = ?)) order by comanda_data_comanda";
			pst1 = conn.prepareStatement(comenzi);

			System.out.println(currentUser.getUid());
			pst1.setString(1, currentUser.getUid());
			rs1 = pst1.executeQuery();

			while (rs1.next()) {
	%>
	<tr>
		<td><%=rs1.getString("comanda_id")%></td>
		<td><%=rs1.getString("comanda_nr_colete")%></td>
		<td><%=rs1.getDate("comanda_data_comanda")%></td>
		<td><input class="btn" type="button" value="Generare AWB"
			id="bt_genAWB"
			onclick="callGenerareAWB('<%=rs1.getString("comanda_id")%>', asignare<%=rs1.getString("comanda_id")%>.value)"></td>
	</tr>
	<%
		}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs1 != null) {
					rs1.close();
					rs1 = null;
				}
				if (rs2 != null) {
					rs2.close();
					rs2 = null;
				}
				if (pst1 != null) {
					pst1.close();
					pst1 = null;
				}
				if (pst2 != null) {
					pst2.close();
					pst2 = null;
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
</table>
<script type="text/javascript">
function callGenerareAWB(comanda_id, user_id) {
	generareAWB(comanda_id, user_id);
}

generareAWB = function(comanda_id, user_id) {
	$.get("", {
		comanda_id : comanda_id,
		user_id : user_id,

	})
	location.reload();
};

function reload() {
    location.reload();
}

</script>
