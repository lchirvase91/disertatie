package dbConnection;

import java.sql.*;

public class ConnectionManager {
	
	static Connection conn;
	static String URL = "jdbc:mysql://localhost:3306/disertatie_db";
	static String USER = "lchirvase";
	static String PSW = "test";

	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PSW);
			System.out.println("connection success");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}

}