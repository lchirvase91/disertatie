<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page language="java"%>
<%@ page import="dbConnection.*"%>
<%@ page import="javaBeans.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>

<table align="center" text-align="center" border="1">
	<tr>
		<th colspan=4>Lista comenzi in curs de livrare</th>
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
			String comenzi = "select distinct comanda_id, comanda_nr_colete, comanda_data_comanda from client, comanda, colet where client_id = comanda_dest_id and comanda_id = colet_comanda_id  and colet_awb is not null and colet_status = 'in curs de livrare' and comanda_asignare not in (select user_id from user where user_hub_id = (select user_hub_id from user where user_userlog_id = ?)) and client_judet = (select hub_judet from hub where hub_id = (select user_hub_id from user where user_userlog_id = ?)) order by comanda_data_comanda";
			String curieri = "select user_id, user_nume, user_prenume from user where user_statut = 'curier' and user_hub_id = (select user_hub_id from user where user_userlog_id = ?)";
			pst1 = conn.prepareStatement(comenzi);
			pst1.setString(1, currentUser.getUid());
			pst1.setString(2, currentUser.getUid());
			rs1 = pst1.executeQuery();

			while (rs1.next()) {
	%>
	<tr>
		<td><%=rs1.getString("comanda_id")%></td>
		<td><%=rs1.getString("comanda_nr_colete")%></td>
		<td><%=rs1.getDate("comanda_data_comanda")%></td>
		<td><select name="asignareLivrare"
			id="asignareLivrare<%=rs1.getString("comanda_id")%>">
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
			id="bt_AsignareLivrare"
			onclick="callAsignareLivrare('<%=rs1.getString("comanda_id")%>', asignareLivrare<%=rs1.getString("comanda_id")%>.value);reload()"></td>
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
function callAsignareLivrare(comanda_id, user_id) {
	asignareLivrare(comanda_id, user_id);
}

asignareLivrare = function(comanda_id, user_id) {
	$.get("asignareLivrare.jsp", {
		comanda_id : comanda_id,
		user_id : user_id,
	})
};

function reload() {
    location.reload();
}

</script>
