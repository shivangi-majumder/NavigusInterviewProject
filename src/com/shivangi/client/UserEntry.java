package com.shivangi.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.shivangi.bean.Login;
import com.shivangi.exception.NavigusException;
import com.shivangi.util.DBUtil;

public class UserEntry {

	private Logger myLogger = null;
	private Connection connection = null;
	private PreparedStatement pst = null;
	// private Statement st = null;
	private ResultSet rs = null;

	public UserEntry() {
		try {
			connection = DBUtil.getConnection();
		} catch (NavigusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PropertyConfigurator.configure("resources/log4j.properties");
		myLogger = Logger.getLogger("UserServiceImpl.class");
	}

	public int registerEmployee(Login employee)
			throws ClassNotFoundException {
		String INSERT_USERS_SQL = "INSERT INTO employee"
				+ "  (id, first_name, last_name, username, password, address, contact) VALUES "
				+ " (?, ?, ?, ?, ?,?,?);";

		int result = 0;

		try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
			preparedStatement.setInt(1, 1);
			preparedStatement.setString(2, employee.getFirstName());
			preparedStatement.setString(3, employee.getLastName());
			preparedStatement.setString(4, employee.getUsername());
			preparedStatement.setString(5, employee.getPassword());
			preparedStatement.setString(6, employee.getAddress());
			preparedStatement.setString(7, employee.getContact());

			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			result = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			myLogger.error(e);
		}
		return result;
	}

	@SuppressWarnings("unused")
	private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                myLogger.error("SQLState: " + ((SQLException) e).getSQLState());
                myLogger.error("Error Code: " + ((SQLException) e).getErrorCode());
                myLogger.error("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
	
	
	public int validateAdminLogin(String username, String password) {
		int result = 0;

		String sql = "SELECT * FROM users where username='" + username
				+ "' and password='" + password + "'";
		try {
			pst = connection.prepareStatement(sql);
			rs = pst.executeQuery(sql);
			if (rs.next()) {
				result = 1;
			}

		} catch (SQLException e) {

			myLogger.error("Sorry!! Unable to validate Login" + e.getMessage());

		}
		// System.out.println(result);
		return result;
	}
}
