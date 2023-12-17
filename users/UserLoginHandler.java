package users;

import java.sql.ResultSet;
import java.sql.SQLException;

import application.DBConnection;
import application.Navigation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

//Factory Gang of Four

public class UserLoginHandler {
	@FXML PasswordField passwordInput;
	@FXML TextField usernameInput;
	@FXML Label invalidLogin;
	
	public UserLoginHandler() {}
	
	@FXML void userlogin() {
		String username = usernameInput.getText();
		String password = passwordInput.getText();
		User user = login(username, password);
		if(user == null) displayLoginInvalid();
		else { Navigation.self().setUser(user);
			user.redirectToHome();
		}
	}
	private void displayLoginInvalid() {
		passwordInput.setText("");
		usernameInput.setText("");
		//invalidLogin.setVisible(true);
	}
	private User login(String username, String password){
		String query = "SELECT * FROM USER WHERE password = '" + password + "' AND username = '" + username + '\'';
		
		User user = null;
		ResultSet rt = DBConnection.getMultiSet(query);

		try{ if(rt.next()) user = initializeUser(rt);}
		catch(SQLException e) {}
		return user;
	}
	private User initializeUser(ResultSet rt) throws SQLException{
		String type;
		try{ type = rt.getString("type"); }
		catch(SQLException e) { return null; }

		if(type.equals("patient"))
			return new Patient(rt);
		if(type.equals("doctor"))
			return new Doctor(rt);
		if(type.equals("receptionist"))
			return new Receptionist(rt);
		
		return null;
	}
	
}
