package users;

import java.sql.ResultSet;
import java.sql.SQLException;

import application.Navigation;

public class Patient extends User implements UserType {
	public Patient(int id_val, String fn_val, String ln_val, String ps_val, String ct_val, String gn_val, String ssn_val,
			String age_val) {
		super(id_val, fn_val, ln_val, ps_val, ct_val, gn_val, ssn_val, age_val);
	}
	public Patient(ResultSet rt) throws SQLException {
		super(rt);
	}
	@Override
	public String getinitValues() { 
		return null; 
	}
//	public static String getinitFields() {
//		return User.getinitFields();
//	}
	public static String getAppSelectFields() {
		return "USER.user_id, USER.first_name, USER.last_name, USER.contact, USER.dob, USER.gender";
	}
	public static String getFields() {
		return User.getFields();
	}
	@Override
	public void redirectToHome() {	
		Navigation.self().loadScheduleAppointments();
	}
	public int getId() {
		return id;
	}
}
