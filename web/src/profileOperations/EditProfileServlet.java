package profileOperations;

import javaBeans.*;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbConnection.DBOperations;

public class EditProfileServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String userName;
		String parolaCurenta;
		String parolaNoua;
		String confirmareParolaNoua;
		
		HttpSession session = request.getSession(true);
		if (session != null) {
			UserBean currentUser = (UserBean) (session
					.getAttribute("currentSessionUser"));
			parolaCurenta = request.getParameter("current_psw");
			parolaNoua = request.getParameter("new_psw");
			confirmareParolaNoua = request.getParameter("confirm_new_psw");
			DBOperations.editProfile(currentUser, parolaCurenta, parolaNoua,
					confirmareParolaNoua);
			response.sendRedirect("userLogged.jsp");
		} else {
			response.sendRedirect("index.jsp");
		}

	}
}