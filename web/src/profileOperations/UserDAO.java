package profileOperations;

import dbConnection.*;
import javaBeans.UserBean;
import java.sql.*;

public class UserDAO {

	static Connection currentConnection = null;
	static ResultSet rs = null;
	static PreparedStatement pst = null;

	public static UserBean login (UserBean bean) {

		String searchQuery;
		String userName = bean.getUsername();
		String userPassword = bean.getPassword();

		searchQuery = "select * from user where user_userlog_id = (select userlog_id FROM userlog WHERE userlog_username = ? AND userlog_password = ?)"; 
		
		System.out.println("Before getting connection your given user name is: " + userName);
		System.out.println("Before getting connection your given user password is: " + userPassword);
		System.out.println("searchQuery is: " + searchQuery);
		
		try {
			currentConnection = ConnectionManager.getConnection();
			pst = currentConnection.prepareStatement(searchQuery);
			pst.setString(1, userName);
			pst.setString(2, userPassword);
			rs = pst.executeQuery();
			boolean more = rs.next();

			if (more) {
				String uid = rs.getString("user_id");
				String nume = rs.getString("user_nume");
				String prenume = rs.getString("user_prenume");
				StringBuilder user = new StringBuilder(nume).append(" ").append(prenume);
				System.out.println("Welcome " + user.toString() + "!");
				bean.setConnection(currentConnection);
				bean.setUid(uid);
				bean.setNume(nume);
				bean.setPrenume(prenume);
				bean.setValid(true);
			} else {
				System.out.println("This user is not registerd!");
				bean.setValid(false);
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
		return bean;
	}
}
