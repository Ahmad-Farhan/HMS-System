package diagnostics.prescription;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Medicine {
	private int id;
	private String name;
	private String sci_name;
	private String dosage;
	private double price;
	private String type;
	
	public Medicine(int id_val, String n_val, String bn_val, String ds_val, String t_val, double p_val) {
		id = id_val;
		name = n_val;
		sci_name = bn_val;
		dosage = ds_val;
		price = p_val;
		type = t_val;
	}
	public Medicine(String n_val, String bn_val, String ds_val, String t_val, double p_val) {
		id = -1;
		name = n_val;
		sci_name = bn_val;
		dosage = ds_val;
		price = p_val;
		type = t_val;
	}
	public Medicine(ResultSet rt) throws SQLException{
		try{id = rt.getInt("Medicine_id");} catch(SQLException e){}
		try{name = rt.getString("medicinename");} catch(SQLException e){}
		try{sci_name = rt.getString("scientificname");} catch(SQLException e){}
		try{dosage = rt.getString("dosage");} catch(SQLException e){}
		try{price = rt.getDouble("price");} catch(SQLException e){}
		try{type = rt.getString("type");} catch(SQLException e){}
	}
	public final String getInitFields() {
		return "medicinename, scientificname, dosage, price, type";
	}
	
	public final String getInitValues() {
		return "'" + name + "','" + sci_name+ "','" + dosage+ "'," + price + ",'" + type + "',";
	}
	public final int getId() {
		return id;
	}
	@Override
	public String toString() {
		return id + ',' + name + ',' + sci_name + ',' + dosage + ',' + price + ',' + type + ',';
	}
	public String getViewValues() {
		//System.out.println(toString());
		return name + " - " + type + '(' + dosage + ')';
	}
	public String getPrescValues() {
		return name + "\t\t" + type + " - " + dosage;
	}
}
