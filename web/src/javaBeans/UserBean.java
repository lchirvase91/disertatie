package javaBeans;

import java.sql.*;

public class UserBean {

	private Connection conn;
	private String uid;
	private String username;
	private String password;
	private String nume;
	private String prenume;
	private boolean isValid;

	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public void closeConnection() throws SQLException {
		if (!conn.isClosed()) {
			conn.close();
		}
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public String getPrenume() {
		return prenume;
	}

	public void setPrenume(String prenume) {
		this.prenume = prenume;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

}