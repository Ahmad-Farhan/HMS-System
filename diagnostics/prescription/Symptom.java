package diagnostics.prescription;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Symptom {
	private int id;
	private String name;
	public Symptom(int id_val, String name_val) {
		id = id_val;
		name = name_val;
	}
	public Symptom(String name_val) {
		id = -1;
		name = name_val;
	}	
	public Symptom(ResultSet rt) throws SQLException{
		id = rt.getInt("symptom_id");
		name = rt.getString("name");
	}
	public final String getinitValues() {
		return name; 
	}
	public final String getinitFields() {
		return "name";
	}
	public final int getId() {
		return id;
	}
	public final String getName() {
		return name.substring(0, name.length() - 1);
	}
}
