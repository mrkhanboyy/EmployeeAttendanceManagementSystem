package org.devshub.dbservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.devshub.bean.Admin;
import org.devshub.datasource.DataSource;

public class AdminDbservice {

	public static boolean validateLogin(Admin admin) throws ClassNotFoundException, SQLException {
		Connection connection = DataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement("select * from attendance.admin_login_details where email = ? " 
		+ "and password = ?");
		statement.setString(1, admin.getEmail());
		statement.setString(2, admin.getPassword());
		boolean valid = statement.executeQuery().isBeforeFirst() ? true : false;
		statement.close();
		DataSource.closeConnection(connection);
		return valid;
	}
	
	public static String getAdminPassword(String email) throws ClassNotFoundException, SQLException {
		Connection connection = DataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement("select password from attendance.admin_login_details where email = ?");
		statement.setString(1, email);
		ResultSet resultSet = statement.executeQuery();
		String pass = "";
		if (resultSet.next()) {
			pass = resultSet.getString("password");
		}
		resultSet.close();
		statement.close();
		DataSource.closeConnection(connection);
		return pass;
	}

}