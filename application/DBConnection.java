package application;

import java.sql.*;

public class DBConnection {
	public static String url = "jdbc:mysql://localhost:3306/serenlife";
	public static String username = "root";
	public static String password = "root";
	
	public static Connection getConnection() {
		Connection con = null;
		try { 
			con = (Connection)DriverManager.getConnection(url, username, password);	
		} catch (SQLException e) {
			e.printStackTrace();
        }
		return con;
	}
	public static int updateQuery(String query) {
		int resultRows = -1;
		try {
			Connection con = DBConnection.getConnection();
			PreparedStatement sqlquery = con.prepareStatement(query);
			resultRows = sqlquery.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
        }
		return resultRows;
	}
	public static String getOneValue(String query) {
		String result = null;
		try {
			Connection con = DBConnection.getConnection();
			PreparedStatement sqlquery = con.prepareStatement(query);
			ResultSet rt = sqlquery.executeQuery(query);
			if(rt.next()) result = rt.getString("reqval");
			
		} catch (SQLException e) {
			e.printStackTrace();
        }
		return result;		
	}
	public static boolean exists(String query) {
		try {
			Connection con = DBConnection.getConnection();
			PreparedStatement sqlquery = con.prepareStatement(query);
			ResultSet rt = sqlquery.executeQuery(query);
			if(rt.next()) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static ResultSet getMultiSet(String query) {
		try {
			Connection con = DBConnection.getConnection();
			PreparedStatement sqlquery = con.prepareStatement(query);
			ResultSet rt = sqlquery.executeQuery(query);
			return rt;
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return null;
	}
}
