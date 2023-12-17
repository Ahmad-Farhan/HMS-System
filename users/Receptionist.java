package users;

import java.sql.ResultSet;
import java.sql.SQLException;

import application.Navigation;

public class Receptionist extends User implements UserType {
	public Receptionist(int id_val, String fn_val, String ln_val, String ps_val, String ct_val, String gn_val, String ssn_val,
			String age_val) {
		super(id_val, fn_val, ln_val, ps_val, ct_val, gn_val, ssn_val, age_val);
	}
	public Receptionist(ResultSet rt) throws SQLException {
		super(rt);
	}
	@Override
	public void redirectToHome() {	
		Navigation.self().loadBillSelectView();
	}
}
