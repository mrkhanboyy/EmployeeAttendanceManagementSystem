package org.devshub.dbservice;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import org.devshub.bean.AttandenceHistory;
import org.devshub.bean.Employee;
import org.devshub.datasource.DataSource;

public class EmployeeDbservice {

	public static boolean validateLogin(Employee employee) throws ClassNotFoundException, SQLException {
		Connection connection = DataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(
				"select email from attendance.employee_login_details where email = ? " + "and password = ?");
		statement.setString(1, employee.getEmail());
		statement.setString(2, employee.getPassword());
		boolean valid = statement.executeQuery().isBeforeFirst() ? true : false;
		statement.close();
		DataSource.closeConnection(connection);
		return valid;
	}

	public static String getEmployeePassword(String email) throws ClassNotFoundException, SQLException {
		Connection connection = DataSource.getConnection();
		PreparedStatement statement = connection
				.prepareStatement("select password from attendance.employee_login_details where email = ?");
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

	public static boolean setNewPassword(String email, String newPassword) throws ClassNotFoundException, SQLException {
		Connection connection = DataSource.getConnection();
		PreparedStatement statement = connection
				.prepareStatement("UPDATE attendance.employee_login_details SET employee_login_details.password = ?"
						+ " where email = ?");
		statement.setString(1, newPassword);
		statement.setString(2, email);
		boolean valid = statement.executeUpdate() == 1 ? true : false;
		statement.close();
		DataSource.closeConnection(connection);
		return valid;
	}

	public static int getEmployeeId(String email) throws ClassNotFoundException, SQLException {
		Connection connection = DataSource.getConnection();
		PreparedStatement statement = connection
				.prepareStatement("select employee_id from attendance.employee_login_details where email = ?");
		statement.setString(1, email);
		ResultSet resultSet = statement.executeQuery();
		int employeeId = 0;
		if (resultSet.next()) {
			employeeId = resultSet.getInt("employee_id");
		}
		resultSet.close();
		statement.close();
		DataSource.closeConnection(connection);
		return employeeId;
	}
	
	public static String getEmployeeName(int employeeId) throws SQLException, ClassNotFoundException {
		Connection connection = DataSource.getConnection();
		PreparedStatement statement = connection
				.prepareStatement("select name from attendance.employee_details where employee_id = ?");
		statement.setInt(1, employeeId);
		ResultSet resultSet = statement.executeQuery();
		String employeeName = "";
		if (resultSet.next()) {
			employeeName = resultSet.getString("name");
		}
		resultSet.close();
		statement.close();
		DataSource.closeConnection(connection);
		return employeeName;
	}

	public static boolean addEmployee(Employee employee) throws ClassNotFoundException, SQLException {
		Connection connection = DataSource.getConnection();
		connection.setAutoCommit(false);
		PreparedStatement statement = connection
				.prepareStatement("INSERT INTO employee_details( employee_details.name, employee_details.age,"
						+ " employee_details.gender, employee_details.address) VALUES (?, ?, ?, ?)");
		statement.setString(1, employee.getEmployeeName());
		statement.setInt(2, employee.getAge());
		statement.setString(3, employee.getGender());
		statement.setString(4, employee.getAddress());

		boolean valid = statement.executeUpdate() > 0;

		try {
			if (valid) {
				statement = connection.prepareStatement(
						"INSERT INTO employee_login_details(employee_login_details.email, employee_login_details.password, employee_login_details.employee_id) "
								+ "VALUES ( ?, ?, LAST_INSERT_ID())");
				statement.setString(1, employee.getEmail());
				statement.setString(2, employee.getPassword());
				valid = statement.executeUpdate() > 0;
				connection.commit();
				connection.setAutoCommit(true);
				return valid;
			} else {
				throw new SQLException("Can't Add Employee");
			}
		} finally {
			statement.close();
			DataSource.closeConnection(connection);
		}
	}

	public static boolean updateEmployee(Employee employee) throws ClassNotFoundException, SQLException {
		Connection connection = DataSource.getConnection();
		connection.setAutoCommit(false);
		PreparedStatement statement = connection
				.prepareStatement("UPDATE attendance.employee_details SET name = ?, age = ?, gender = ?, "
						+ "address = ? WHERE employee_id = ?");
		statement.setString(1, employee.getEmployeeName());
		statement.setInt(2, employee.getAge());
		statement.setString(3, employee.getGender());
		statement.setString(4, employee.getAddress());
		statement.setInt(5, employee.getEmployeeId());
		System.out.println("empId " + employee.getEmployeeId());
		boolean valid = statement.executeUpdate() > 0;
		try {
			if (valid) {
				statement = connection.prepareStatement(
						"UPDATE attendance.employee_login_details SET email = ? WHERE employee_id = ?");
				statement.setString(1, employee.getEmail());
				statement.setInt(2, employee.getEmployeeId());
				valid = statement.executeUpdate() > 0;
				connection.commit();
				connection.setAutoCommit(true);
				return valid;
			} else {
				throw new SQLException("Can't Update Employee");
			}
		} finally {
			statement.close();
			DataSource.closeConnection(connection);
		}
	}

	public static ArrayList<Employee> searchEmployee(Employee employee) throws SQLException, ClassNotFoundException {
		Connection connection = DataSource.getConnection();
		StringBuilder query = new StringBuilder(
				"select employee_details.employee_id, employee_details.name, employee_details.age, employee_details.gender, "
						+ "employee_login_details.email, employee_details.address FROM employee_details LEFT JOIN employee_login_details on "
						+ "employee_details.employee_id = employee_login_details.employee_id where");

		boolean isName = false, isAge = false, isGender = false, isEmail = false;
		if (!employee.getEmployeeName().isEmpty()) {
			query.append(" employee_details.name LIKE ? and");
			isName = true;
		}
		if (employee.getAge() > 0) {
			query.append(" employee_details.age = ? and");
			isAge = true;
		}
		if (!employee.getGender().isEmpty()) {
			query.append(" employee_details.gender LIKE ? and");
			isGender = true;
		}
		if (!employee.getEmail().isEmpty()) {
			query.append(" employee_login_details.email LIKE ? and");
			isEmail = true;
		}

		String str = query.toString();
		String searchQuery = null;

		if (str.endsWith(" where")) {
			searchQuery = str.replace("where", "");
		} else if (str.endsWith("and")) {
			searchQuery = str.substring(0, str.length() - 4);
		}

		PreparedStatement statement = connection.prepareStatement(searchQuery);

		switch (statement.getParameterMetaData().getParameterCount()) {
		case 1:
			if (isName) {
				statement.setString(1, employee.getEmployeeName() + "%");
			} else if (isAge) {
				statement.setInt(1, employee.getAge());
			} else if (isGender) {
				statement.setString(1, employee.getGender() + "%");
			} else if (isEmail) {
				statement.setString(1, employee.getEmail() + "%");
			}
			break;
		case 2:
			if (isName && isAge) {
				statement.setString(1, employee.getEmployeeName() + "%");
				statement.setInt(2, employee.getAge());
			} else if (isName && isGender) {
				statement.setString(1, employee.getEmployeeName() + "%");
				statement.setString(2, employee.getGender() + "%");
			} else if (isName && isEmail) {
				statement.setString(1, employee.getEmployeeName() + "%");
				statement.setString(2, employee.getEmail() + "%");
			} else if (isAge && isGender) {
				statement.setInt(1, employee.getAge());
				statement.setString(2, employee.getGender() + "%");
			} else if (isAge && isEmail) {
				statement.setInt(1, employee.getAge());
				statement.setString(2, employee.getEmail() + "%");
			} else if (isGender && isEmail) {
				statement.setString(1, employee.getGender() + "%");
				statement.setString(2, employee.getEmail() + "%");
			}
			break;
		case 3:
			if (isName && isAge && isGender) {
				statement.setString(1, employee.getEmployeeName() + "%");
				statement.setInt(2, employee.getAge());
				statement.setString(3, employee.getGender() + "%");
			} else if (isName && isAge && isEmail) {
				statement.setString(1, employee.getEmployeeName() + "%");
				statement.setInt(2, employee.getAge());
				statement.setString(3, employee.getEmail() + "%");
			} else if (isAge && isGender && isEmail) {
				statement.setInt(1, employee.getAge());
				statement.setString(2, employee.getGender() + "%");
				statement.setString(3, employee.getEmail() + "%");
			} else if (isName && isGender && isEmail) {
				statement.setString(1, employee.getEmployeeName() + "%");
				statement.setString(2, employee.getGender() + "%");
				statement.setString(3, employee.getEmail() + "%");
			}
			break;
		case 4:
			statement.setString(1, "%" + employee.getEmployeeName() + "%");
			statement.setInt(2, employee.getAge());
			statement.setString(3, "%" + employee.getGender() + "%");
			statement.setString(4, "%" + employee.getEmail() + "%");
			break;
		default:
			
			break;
		}

		ArrayList<Employee> employees = new ArrayList<>();
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			Employee emp = new Employee();
			emp.setEmployeeId(resultSet.getInt("employee_details.employee_id"));
			emp.setEmployeeName(resultSet.getString("employee_details.name"));
			emp.setAge(resultSet.getInt("employee_details.age"));
			emp.setEmail(resultSet.getString("employee_login_details.email"));
			emp.setGender(resultSet.getString("employee_details.gender"));
			emp.setAddress(resultSet.getString("employee_details.address"));
			employees.add(emp);
		}
		resultSet.close();
		statement.close();
		DataSource.closeConnection(connection);
		return employees;
	}

	public static boolean deleteEmployee(int employeeId) throws ClassNotFoundException, SQLException {
		Connection connection = DataSource.getConnection();
		connection.setAutoCommit(false);
		PreparedStatement statement = connection
				.prepareStatement("DELETE FROM employee_login_details WHERE employee_login_details.employee_id = ?");
		statement.setInt(1, employeeId);
		boolean valid = statement.executeUpdate() > 0;

		try {
			if (valid) {
				statement = connection
						.prepareStatement("DELETE FROM employee_details WHERE employee_details.employee_id = ?");
				statement.setInt(1, employeeId);

				valid = statement.executeUpdate() > 0;
				connection.commit();
				return valid;
			} else {
				throw new SQLException("Can't Delete Employee");
			}
		} finally {
			statement.close();
			DataSource.closeConnection(connection);
		}
	}

	public static Employee getEmployeeDetails(int employeeId) throws ClassNotFoundException, SQLException {
		Connection connection = DataSource.getConnection();
		PreparedStatement statement = connection
				.prepareStatement("SELECT employee_details.name, employee_details.age, employee_details.gender, "
						+ "employee_login_details.email, employee_details.address FROM employee_details INNER "
						+ "JOIN employee_login_details on employee_details.employee_id = employee_login_details.employee_id WHERE employee_login_details.employee_id = ?");
		statement.setInt(1, employeeId);
		ResultSet resultSet = statement.executeQuery();
		Employee employee = new Employee();
		if (resultSet.next()) {
			employee.setEmployeeId(employeeId);
			employee.setEmployeeName(resultSet.getString("name"));
			employee.setAge(resultSet.getInt("age"));
			employee.setGender(resultSet.getString("gender"));
			employee.setEmail(resultSet.getString("email"));
			employee.setAddress(resultSet.getString("address"));
		}
		statement.close();
		resultSet.close();
		DataSource.closeConnection(connection);
		return employee;
	}

	public static ArrayList<Employee> getAllEmployees() throws ClassNotFoundException, SQLException {
		Connection connection = DataSource.getConnection();
		PreparedStatement statement = connection
				.prepareStatement("select employee_details.employee_id, employee_details.name, employee_details.age, "
						+ "employee_details.gender, employee_login_details.email, employee_details.address FROM employee_details INNER JOIN "
						+ "employee_login_details WHERE employee_details.employee_id = employee_login_details.employee_id");
		ArrayList<Employee> employees = new ArrayList<>();
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			Employee employee = new Employee();
			employee.setEmployeeId(resultSet.getInt("employee_details.employee_id"));
			employee.setEmployeeName(resultSet.getString("employee_details.name"));
			employee.setAge(resultSet.getInt("employee_details.age"));
			employee.setEmail(resultSet.getString("employee_login_details.email"));
			employee.setGender(resultSet.getString("employee_details.gender"));
			employee.setAddress(resultSet.getString("employee_details.address"));
			employees.add(employee);
		}
		resultSet.close();
		statement.close();
		DataSource.closeConnection(connection);
		return employees;
	}

	public static boolean ifEntryExist(int e_id) throws ClassNotFoundException, SQLException {
		Connection con = DataSource.getConnection();

		// checking if Entry already exist for today or not.

		PreparedStatement checkingData = con
				.prepareStatement("select * from attendance_history where employee_id = ? and date = ?");
		checkingData.setInt(1, e_id);
		checkingData.setDate(2, Date.valueOf(LocalDate.now()));
		ResultSet check = checkingData.executeQuery();
		return check.next();

	}

	public static boolean doEntry(int e_id) throws ClassNotFoundException, SQLException {
		Connection con = DataSource.getConnection();
		if (EmployeeDbservice.ifEntryExist(e_id)) {
			return false;
		}
		// if entry do not exist already then create an entry
		PreparedStatement stmt = con.prepareStatement(
				"insert into attendance_history (date,entry_time,exit_time,employee_id) values(?,?,?,?)");
		stmt.setDate(1, Date.valueOf(java.time.LocalDate.now()));
		stmt.setTime(2, Time.valueOf(LocalTime.now()));
		stmt.setTime(3, Time.valueOf(LocalTime.now()));
		stmt.setInt(4, e_id);
		stmt.executeUpdate();
		stmt.close();
		DataSource.closeConnection(con);
		return true;
	}

	public static boolean doExit(int e_id) throws ClassNotFoundException, SQLException {

		Connection con = DataSource.getConnection();
		PreparedStatement stmt = con
				.prepareStatement("update attendance_history SET exit_time = ? where employee_id = ? and date = ?");
		stmt.setTime(1, Time.valueOf(LocalTime.now()));
		stmt.setInt(2, e_id);
		stmt.setDate(3, Date.valueOf(LocalDate.now()));
		stmt.executeUpdate();
		stmt.close();
		DataSource.closeConnection(con);
		return true;
	}

	public static ArrayList<AttandenceHistory> getAttandenceHistory(int e_id)
			throws ClassNotFoundException, SQLException {
		Connection con = DataSource.getConnection();
		PreparedStatement stmt = con.prepareStatement("select * from attendance_history where employee_id = ?");
		stmt.setInt(1, e_id);
		ResultSet rst = stmt.executeQuery();
		ArrayList<AttandenceHistory> historyList = new ArrayList<AttandenceHistory>();
		while (rst.next()) {
			AttandenceHistory ah = new AttandenceHistory();
			ah.setDate(rst.getDate("date").toString());
			ah.setEntryTime(rst.getTime("entry_time").toString());
			ah.setExitTime(rst.getTime("exit_time").toString());
			historyList.add(ah);
		}
		DataSource.closeConnection(con);
		return historyList;
	}
}
