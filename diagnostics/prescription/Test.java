package diagnostics.prescription;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {
	private int id;
	private String name;

	public Test(int id_val, String n_val, String t_val) {
		id = id_val;
		name = n_val;
	}
	public Test(String n_val, String t_val) {
		id = -1;
		name = n_val;
	}
	public Test(ResultSet rt) throws SQLException{
		try{id = rt.getInt("test_id");} catch(SQLException e){}
		try{name = rt.getString("testname");} catch(SQLException e){}
	}
	public final String getinitValues() {
		return "'" + name + '\'';
	}
	public final String getinitFields() {
		return "name";
	}
	public final int getId() {
		return id;
	}
	public final String getName() {
		return name;//.substring(0, name.length() - 1);
	}
}
