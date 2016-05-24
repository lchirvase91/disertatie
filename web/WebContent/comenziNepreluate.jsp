<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page language="java"%>
<%@ page import="dbConnection.*"%>
<%@ page import="javaBeans.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>

<table align="center" text-align="center" border="1">
	<tr>
		<th colspan=4>Lista comenzi nepreluate</th>
	</tr>
	<tr>
		<th>Id comanda</th>
		<th>Numar colete</th>
		<th>Data comanda</th>
		<th>Asignare</th>
	</tr>
	<%
		try {
			conn = ConnectionManager.getConnection();
			String comenzi = "select distinct comanda_id, comanda_nr_colete, comanda_data_comanda from client, comanda, colet where client_id = comanda_exp_id and comanda_id = colet_comanda_id  and colet_awb is null and colet_status = 'nepreluat' and comanda_asignare is null and client_judet = (select hub_judet from hub where hub_id = (select user_hub_id from user where user_userlog_id = ?)) order by comanda_data_comanda";
			String curieri = "select user_id, user_nume, user_prenume from user where user_statut = 'curier' and user_hub_id = (select user_hub_id from user where user_userlog_id = ?)";
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
		<td><select name="asignare"
			id="asignare<%=rs1.getString("comanda_id")%>">
				<option value="Asignare">Asignare</option>
				<%
					pst2 = conn.prepareStatement(curieri);
							pst2.setString(1, currentUser.getUid());
							rs2 = pst2.executeQuery();
							while (rs2.next()) {
				%>
				<option value="<%=rs2.getString("user_id")%>"><%=rs2.getString("user_nume")%>
					<%=rs2.getString("user_prenume")%></option>
				<%
					}
				%>
		</select></td>
		<td><input class="btn" type="button" value="Asignare"
			id="bt_Asignare"
			onclick="callAsignareComanda('<%=rs1.getString("comanda_id")%>', asignare<%=rs1.getString("comanda_id")%>.value);reload()"></td>
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
function callAsignareComanda(comanda_id, user_id) {
	asignareComanda(comanda_id, user_id);
}

asignareComanda = function(comanda_id, user_id) {
	$.get("asignarePreluare.jsp", {
		comanda_id : comanda_id,
		user_id : user_id,
	})
};

function reload() {
    location.reload();
}

</script>
