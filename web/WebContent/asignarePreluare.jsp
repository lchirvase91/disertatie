<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="dbConnection.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="javaBeans.UserBean"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Calendar"%>
<%

	final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

	UserBean currentUser = ((UserBean) (session
		.getAttribute("currentSessionUser")));

	String comandaId = request.getParameter("comanda_id");
	String userId = request.getParameter("user_id");
	System.out.println(comandaId + "   " + userId);

    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
    String currentDateTime = sdf.format(cal.getTime());
	
	Connection conn = null;
	PreparedStatement pstSelect = null, pstInsert = null, pstUpdate = null, pst1 = null, pst2 = null;
	ResultSet rs = null,  rs1 = null,  rs2 = null;
	int result = 0;
	try {
		conn = ConnectionManager.getConnection();
		String select = "select colet_id from colet where colet_comanda_id = ? ";
		String insert = "insert into operare values(NULL, ?, ?, ?)";
		String update = "update colet, comanda, client, operare, user, hub set colet_status = 'in curs de preluare', comanda_asignare = ? where colet_comanda_id = comanda_id and comanda_exp_id = client_id and colet_id = operare_colet_id and operare_user_id = user_id and user_hub_id = hub_id and hub_judet = client_judet and comanda_id = ?";
		pstSelect = conn.prepareStatement(select);
		pstSelect.setString(1, comandaId);
		rs = pstSelect.executeQuery();
		while (rs.next()) {
			pstInsert = conn.prepareStatement(insert);
			pstInsert.setString(1, currentUser.getUid());
			pstInsert.setString(2, rs.getString("colet_id"));
			pstInsert.setString(3, currentDateTime);
			result = pstInsert.executeUpdate();
		}

		if (result > 0) {
			pstUpdate = conn.prepareStatement(update);
			pstUpdate.setString(1, userId);
			pstUpdate.setString(2, comandaId);
			pstUpdate.executeUpdate();
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (pstSelect != null) {
				pstSelect.close();
				pstSelect = null;
			}
			if (pstInsert != null) {
				pstInsert.close();
				pstInsert = null;
			}
			if (pstUpdate != null) {
				pstUpdate.close();
				pstUpdate = null;
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

<table class="common_table">
	<tr>
		<th colspan=5>Lista comenzi nepreluate</th>
	</tr>
	<tr>
		<th>Id comanda</th>
		<th>Numar colete</th>
		<th>Data comanda</th>
		<th colspan=2>Asignare</th>
	</tr>
	<%
			try {
				conn = ConnectionManager.getConnection();
				String comenzi = "select distinct comanda_id, comanda_nr_colete, comanda_data_comanda from client, comanda, colet where client_id = comanda_exp_id and comanda_id = colet_comanda_id  and colet_awb is null and colet_status = 'nepreluat' and comanda_asignare is null and client_judet = (select hub_judet from hub where hub_id = (select user_hub_id from user where user_userlog_id = ?)) order by comanda_data_comanda, comanda_id";
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