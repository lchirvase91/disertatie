<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="dbConnection.*"%>
<%@ page import="java.sql.*"%>

<%
	String comandaId = request.getParameter("comanda_id");
	String user_Id = request.getParameter("user_id");
	System.out.println(comandaId + "   " + user_Id);

	Connection conn = null;
	PreparedStatement pst = null;
	try {
		conn = ConnectionManager.getConnection();
		String sql = "update comanda set comanda_asignare = ? where comanda_id = ?";
		pst = conn.prepareStatement(sql);
		pst.setString(1, user_Id);
		pst.setString(2, comandaId);
		pst.executeUpdate();
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		try {
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