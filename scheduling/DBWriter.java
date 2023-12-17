package scheduling;

import java.sql.*;

import application.DBConnection;
import application.Navigation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import users.User;

public class DBWriter 
{
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
	
	public static boolean insertData(String tableName, String[] columns, Object[] values) 
	{
        if (columns.length != values.length) {
            return false;
        }

        String columnNames = String.join(", ", columns);
        String placeholders = String.join(", ", java.util.Collections.nCopies(columns.length, "?"));
        String query = "INSERT INTO " + tableName + " (" + columnNames + ") VALUES (" + placeholders + ")";

        try 
        {
        	Connection con = DBConnection.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(query);
        
            for (int i = 0; i < values.length; i++) {
                preparedStatement.setObject(i + 1, values[i]);
            }

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } 
        
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        
        return false;
	}
	
	public static boolean deleteData(String tableName, String conditionColumn, Object conditionValue) {
	    String query = "DELETE FROM " + tableName + " WHERE " + conditionColumn + " = ?";

	    try {
	        Connection con = DBConnection.getConnection();
	        PreparedStatement preparedStatement = con.prepareStatement(query);

	        preparedStatement.setObject(1, conditionValue);

	        int rowsAffected = preparedStatement.executeUpdate();
	        if (rowsAffected > 0) {
	            return true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
	}
	
}
