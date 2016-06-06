package profileOperations;

import javaBeans.UserBean;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		
		if (session == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		
		UserBean currentUser = (UserBean) (session
				.getAttribute("currentSessionUser"));

		if (currentUser == null) {
			response.sendRedirect("index.jsp");
			return;
		}
		
		if (currentUser.isValid()) {
			try {
				currentUser.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			response.sendRedirect("index.jsp");
		}

	}
}