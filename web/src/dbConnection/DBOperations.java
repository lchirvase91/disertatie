package dbConnection;

import dbConnection.*;
import javaBeans.*;
import java.sql.*;

public class DBOperations {

	static Connection currentConnection = null;
	static PreparedStatement pst = null;
	static ResultSet rs = null;

	public static boolean editProfile(UserBean user, String parolaCurenta,
			String parolaNoua, String confirmareParolaNoua) {
		String searchQuery = "select userlog_password from userlog where userlog_username = ?";
		System.out.println("searchQuery is: " + searchQuery);
		System.out.println("Date formular editare:\nusername:"
				+ user.getUsername() + "\nparolacurenta:" + parolaCurenta
				+ "\nparolanoua:" + parolaNoua + "\nconfirmparola:"
				+ confirmareParolaNoua);
		try {
			currentConnection = ConnectionManager.getConnection();
			pst = currentConnection.prepareStatement(searchQuery);
			pst.setString(1, user.getUsername());
			rs = pst.executeQuery();
			boolean more = rs.next();

			if (more) {
				String sqlPass = rs.getString("userlog_password");
				if (sqlPass.equals(parolaCurenta)
						&& parolaNoua.equals(confirmareParolaNoua)) {
					String updateProfileStatement = "update userlog set userlog_password = ? where userlog_username = ?";
					pst = currentConnection
							.prepareStatement(updateProfileStatement);
					pst.setString(1, parolaNoua);
					pst.setString(2, user.getUsername());
					int result = pst.executeUpdate();
					if (result > 0) {
						System.out.println("Update user: " + user.getUsername()
								+ " with password: " + parolaNoua);
					}
					user.setPassword(parolaNoua);
					return true;
				}
			} else {
				System.out.println("Parola pentru user-ul "
						+ user.getUsername() + " nu a putut fi schimbata!");
				return false;
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
				if (currentConnection != null) {
					currentConnection.close();
					currentConnection = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return true;
	}

	public static boolean insertComanda(ComandaBean comanda) {

		int lastIdExp = 0, lastIdDest = 0, lastIdComanda = 0, result = 0;
		String lastID = "select last_insert_id()";
		String checkClient = "select * from client where client_nume = ? and client_judet = ? and client_localitate = ? and client_adresa = ?";
		String insertExp = "insert into client values(NULL, ?, ?, ?, ?, ?, ?)";
		String insertDest = "insert into client values(NULL, ?, ?, ?, ?, ?, ?)";
		String insertComanda = "insert into comanda(comanda_id, comanda_nr_colete, comanda_greutate, comanda_data_comanda, comanda_observatii, comanda_exp_id, comanda_dest_id) values (NULL, ?, ?, CURDATE(), ? , ?, ?)";
		String insertColet = "insert into colet(colet_id, colet_status, colet_comanda_id) values (NULL, 'nepreluat', ?)";

		try {
			currentConnection = ConnectionManager.getConnection();

			pst = currentConnection.prepareStatement(checkClient);
			pst.setString(1, comanda.getExpeditor().getNume());
			pst.setString(2, comanda.getExpeditor().getJudet());
			pst.setString(3, comanda.getExpeditor().getLocalitate());
			pst.setString(4, comanda.getExpeditor().getAdresa());
			rs = pst.executeQuery();
			if (rs.next()) {
				lastIdExp = rs.getInt("client_id");
			}

			pst = currentConnection.prepareStatement(checkClient);
			pst.setString(1, comanda.getDestinatar().getNume());
			pst.setString(2, comanda.getDestinatar().getJudet());
			pst.setString(3, comanda.getDestinatar().getLocalitate());
			pst.setString(4, comanda.getDestinatar().getAdresa());
			rs = pst.executeQuery();
			if (rs.next()) {
				lastIdDest = rs.getInt("client_id");
			}

			if (lastIdExp == 0) {
				pst = currentConnection.prepareStatement(insertExp);
				pst.setString(1, comanda.getExpeditor().getNume());
				pst.setString(2, comanda.getExpeditor().getJudet());
				pst.setString(3, comanda.getExpeditor().getLocalitate());
				pst.setString(4, comanda.getExpeditor().getAdresa());
				pst.setString(5, comanda.getExpeditor().getTelefon());
				pst.setString(6, comanda.getExpeditor().getEmail());
				result = pst.executeUpdate();

				if (result > 0) {
					pst = currentConnection.prepareStatement(lastID);
					rs = pst.executeQuery();
					if (rs.next()) {
						lastIdExp = rs.getInt(1);
					}
				}
			}

			if (lastIdDest == 0) {
				pst = currentConnection.prepareStatement(insertDest);
				pst.setString(1, comanda.getDestinatar().getNume());
				pst.setString(2, comanda.getDestinatar().getJudet());
				pst.setString(3, comanda.getDestinatar().getLocalitate());
				pst.setString(4, comanda.getDestinatar().getAdresa());
				pst.setString(5, comanda.getDestinatar().getTelefon());
				pst.setString(6, comanda.getDestinatar().getEmail());
				result = pst.executeUpdate();

				if (result > 0) {
					pst = currentConnection.prepareStatement(lastID);
					rs = pst.executeQuery();
					if (rs.next()) {
						lastIdDest = rs.getInt(1);
					}
				}
			}
			
			pst = currentConnection.prepareStatement(insertComanda);
			pst.setString(1, comanda.getNr_colete());
			pst.setString(2, comanda.getGreutate());
			pst.setString(3, comanda.getObservatii());
			pst.setInt(4, lastIdExp);
			pst.setInt(5, lastIdDest);
			result = pst.executeUpdate();

			if (result > 0) {
				pst = currentConnection.prepareStatement(lastID);
				rs = pst.executeQuery();
				if (rs.next()) {
					lastIdComanda = rs.getInt(1);
				}
			}

			pst = currentConnection.prepareStatement(insertColet);
			for (int i = 0; i < Integer.valueOf(comanda.getNr_colete()); i++) {
				pst.setInt(1, lastIdComanda);
				pst.executeUpdate();
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
				if (currentConnection != null) {
					currentConnection.close();
					currentConnection = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return true;
	}
}
