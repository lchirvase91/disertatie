package profileOperations;

import java.io.IOException;
import javaBeans.UserBean;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		UserBean user = new UserBean();
		user.setUsername(request.getParameter("uname"));
		user.setPassword(request.getParameter("upassword"));
		user = UserDAO.login(user);

		if (user.isValid()) {
			HttpSession session = request.getSession(true);
			if(session == null) {
				response.sendRedirect("index.jsp");
				return;
			}
			session.setAttribute("currentSessionUser", user);
			session.setAttribute("currentConnection", UserDAO.currentConnection);
			response.sendRedirect("userLogged.jsp");
		} else {
			response.sendRedirect("index.jsp");
		}
	}
}