package users;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class User implements UserType{
	protected int id;
	protected String first_name;
	protected String last_name;
	protected String password;
	protected String contact;
	protected String ssn;
	protected String dob;
	protected String gender;
	protected int age;
	
	public User() {}
	public User(int id_val, String fn_val, String ln_val, String ps_val, String ct_val, String gn_val, String ssn_val, String dob_val) {
		id = id_val;
		first_name = fn_val;
		last_name = ln_val;
		password = ps_val;
		contact = ct_val;
		gender = gn_val;
		ssn = ssn_val;
		dob = dob_val;
	}
	public User(String fn_val, String ln_val, String ps_val, String ct_val, String gn_val, String ssn_val, String dob_val) {
		id = -1;
		first_name = fn_val;
		last_name = ln_val;
		password = ps_val;
		contact = ct_val;
		gender = gn_val;
		ssn = ssn_val;
		dob = dob_val;
	}
	public User(ResultSet rt) throws SQLException {
		try{ id = rt.getInt("User.user_id"); } catch(SQLException e) {}
		try{ first_name = rt.getString("first_name"); } catch(SQLException e) {}
		try{ last_name = rt.getString("last_name"); } catch(SQLException e) {}
		try{ password = rt.getString("password"); } catch(SQLException e) {}
		try{ contact = rt.getString("contact"); } catch(SQLException e) {}
		try{ gender = rt.getString("gender"); } catch(SQLException e) {}
		try{ ssn = rt.getString("ssn"); } catch(SQLException e) {}
		try{ dob = rt.getString("dob"); } catch(SQLException e) {}
	}
	
	public static String getinitFields() {
		return "User.first_name, User.last_name, User.password, User.contact, User.dob, User.ssn, USER.gender";
	}
	public static String getFields() {
		return "User.user_id, User.first_name, User.last_name, User.password, User.contact, User.dob, User.ssn, USER.gender";
	}
	@Override
	public String getinitValues() {
		return "'" + first_name + "','" + last_name + "','" + password + "','" 
				+ contact + "','" + dob + "'," + ssn + "', '" + gender + '\'';
	}
	public String getPassword() {
		return password;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return first_name + ' ' + last_name;
	}
	public String getContact() {
		return contact;
	}
	public String getAgeGender() {
		return calculateAge(dob) + "yrs/" + gender;
	}
	public static int calculateAge(String dobString) {
        LocalDate dob = LocalDate.parse(dobString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate currentDate = LocalDate.now();
        
        int age = currentDate.getYear() - dob.getYear();
        if (currentDate.getMonthValue() < dob.getMonthValue() ||
            (currentDate.getMonthValue() == dob.getMonthValue() &&
             currentDate.getDayOfMonth() < dob.getDayOfMonth())) age--;

        return age;
    }
}
