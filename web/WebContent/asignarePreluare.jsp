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
	PreparedStatement pstSelect = null, pstInsert = null, pstUpdate = null;
	ResultSet rs = null;
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