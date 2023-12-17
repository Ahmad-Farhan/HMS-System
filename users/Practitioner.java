package users;

import java.sql.ResultSet;
import java.sql.SQLException;

import users.User;

public abstract class Practitioner extends User{
	private String specialization;

	public Practitioner() {}
	public Practitioner(int id_val, String fn_val, String ln_val, String ps_val, String ct_val, String gn_val, String ssn_val, String dob_val, String spec_val) {
		super(id_val, fn_val, ln_val, ps_val, ct_val, gn_val, ssn_val, dob_val);
		specialization = spec_val;
	}
	public Practitioner(String fn_val, String ln_val, String ps_val, String ct_val, String gn_val, String ssn_val, String dob_val, String spec_val) {
		super(fn_val, ln_val, ps_val, ct_val, gn_val, ssn_val, dob_val);
		specialization = spec_val;
	}
	public Practitioner(ResultSet rt) throws SQLException {
		super(rt);
		try{ specialization = rt.getString("specialization "); } catch(SQLException e) {}
	}
	@Override
	public String getinitValues() {
		return super.getinitValues() + "','" + specialization + "'";
	}
	public static String getinitFields() {
		return User.getinitFields() + ",specialization";
	}
}
