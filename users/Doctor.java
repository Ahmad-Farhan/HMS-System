package users;

import java.sql.ResultSet;
import java.sql.SQLException;

import application.Navigation;

public class Doctor extends Practitioner implements UserType{
	public Doctor(int id_val, String fn_val, String ln_val, String ps_val, String ct_val, String gn_val, String ssn_val, String age_val, String spec_val) {
		super(id_val, fn_val, ln_val, ps_val, ct_val, ssn_val, gn_val, age_val, spec_val);
	}
	public Doctor(String fn_val, String ln_val, String ps_val, String ct_val, String gn_val, String ssn_val, String age_val, String spec_val) {
		super(fn_val, ln_val, ps_val, ct_val, ssn_val, gn_val, age_val, spec_val);
	}
	public Doctor(ResultSet rt) throws SQLException {
		super(rt);		
	}
	public Doctor(ResultSet rt, boolean a) throws SQLException{
		try{ this.id = rt.getInt("DOCTOR.user_id"); } catch(SQLException e) {}
		try{ this.first_name = rt.getString("DOCTOR.first_name"); } catch(SQLException e) {}
		try{ this.last_name = rt.getString("DOCTOR.last_name"); } catch(SQLException e) {}
	}
	@Override
	public String getinitValues() {
		return super.getinitValues();
	}
	public static String getinitFields() {
		return User.getinitFields();
	}
	@Override
	public void redirectToHome() {
		Navigation.self().loadApptSelectView();
	}
}
