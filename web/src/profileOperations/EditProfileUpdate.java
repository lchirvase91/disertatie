package profileOperations;

import dbConnection.*;
import javaBeans.*;
import java.sql.*;

public class EditProfileUpdate {

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
			System.out.println("Edit profile failed: " + e.getMessage());
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
