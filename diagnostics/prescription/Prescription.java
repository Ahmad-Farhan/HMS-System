package diagnostics.prescription;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Prescription {
	private int id;
	private int no_of_days;
	private double amount;
	private String timings;
	private String instruction;
	private Medicine medicine;
	private int report_id;
	
	public Prescription(int id_val, Medicine med, int am_val, String pdi_val, int dur_val, String inst_val) {
		id = id_val;
		amount = am_val;
		medicine = med;
		no_of_days = dur_val;
		timings = pdi_val;
		instruction = inst_val;
	}
	public Prescription(Medicine med, int am_val, String pdi_val, int dur_val, String inst_val) {
		id = -1;
		amount = am_val;
		medicine = med;
		no_of_days = dur_val;
		timings = pdi_val;
		instruction = inst_val;
	}
	public Prescription(ResultSet rt) throws SQLException {
		try{ id = rt.getInt("prescription_id");} catch(SQLException e) {}
		try{ amount = rt.getInt("amount");} catch(SQLException e) {}
		try{ medicine = new Medicine(rt);} catch(SQLException e) {}
		try{ no_of_days = rt.getInt("no_of_days");} catch(SQLException e) {}
		try{ timings = rt.getString("timings");} catch(SQLException e) {};
		try{ instruction = rt.getString("instruction");} catch(SQLException e) {};
		try{ report_id = rt.getInt("healthrport_id");} catch(SQLException e) {}
	}	
	public void setReportId(final int healthreport_id) {
		report_id = healthreport_id;
	}
	public final String getinitValues() {
		return medicine.getId() + ",'" + timings + "'," + amount + ",'" + no_of_days + "', '" + instruction + "'";
	}
	public static final String getinitFields() {
		return "healthreport_id, medicine_id, timings, amount, no_of_days, instruction";
	}
	public final int getMedId() {
		return medicine.getId();
	}
	public final String getViewValues() {
		return medicine.getPrescValues() + "\t\t(" + no_of_days + " Days\n\t" +
				timings + "\n\t(" + 
				instruction + ")\t\t" + "Amt:" + amount + "\t Tot:" + calcTotalMeds();
	}
	private int calcTotalMeds() {
		int total = 0;
		
		if(timings.contains("Morning")) total++;
		if(timings.contains("Evening")) total++;
		if(timings.contains("Afternoon")) total++;
		if(timings.contains("Night")) total++;
		
		return total * no_of_days;
	}
}
