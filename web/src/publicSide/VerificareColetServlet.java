package publicSide;

import javaBeans.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbConnection.ConnectionManager;
import dbConnection.DBOperations;

public class VerificareColetServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String awb, status = null;
		Date dataComanda = null;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		
		awb = request.getParameter("search_awb");
		
		System.out.println("WWWWWWWWWWWWWWW ------ " + awb);
		
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
		
		System.out.println("WWWWWWWWWWWWWWW --222222---- " + status);
		
		RequestDispatcher requestDispatcher =
			    request.getRequestDispatcher("verificareAWB.jsp");

		request.setAttribute("awb", awb);
		request.setAttribute("data", dataComanda);
		request.setAttribute("status", status);
		
		
		requestDispatcher.forward(request, response);
		
	}
}