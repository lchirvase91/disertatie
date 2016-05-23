package publicSide;

import javaBeans.*;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbConnection.DBOperations;

public class CheamaCurierServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String nume_exp, judet_exp, loc_exp, adresa_exp, tel_exp, email_exp;
		String nume_dest, judet_dest, loc_dest, adresa_dest, tel_dest, email_dest;
		String nr_colete, greutate, obs;
		
		ClientBean expeditor;
		ClientBean destinatar;
		ComandaBean comanda;
		
		nume_exp = request.getParameter("nume_exp");
		judet_exp = request.getParameter("judet_exp");
		loc_exp = request.getParameter("loc_exp");
		adresa_exp = request.getParameter("adresa_exp");
		tel_exp = request.getParameter("tel_exp");
		email_exp = request.getParameter("email_exp");
		
		expeditor = new ClientBean(nume_exp, judet_exp, loc_exp, adresa_exp, tel_exp, email_exp);

		nume_dest = request.getParameter("nume_dest");
		judet_dest = request.getParameter("judet_dest");
		loc_dest = request.getParameter("loc_dest");
		adresa_dest = request.getParameter("adresa_dest");
		tel_dest = request.getParameter("tel_dest");
		email_dest = request.getParameter("email_dest");
		
		destinatar = new ClientBean(nume_dest, judet_dest, loc_dest, adresa_dest, tel_dest, email_dest);
		
		nr_colete = request.getParameter("nr_colete");
		greutate = request.getParameter("greutate");
		obs = request.getParameter("obs");
		
		comanda = new ComandaBean(expeditor, destinatar, nr_colete, greutate, obs);
		
		DBOperations.insertComanda(comanda);

		response.sendRedirect("cheamaCurier.jsp");
		
	}
}