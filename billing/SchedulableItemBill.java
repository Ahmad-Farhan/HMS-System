package billing;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SchedulableItemBill extends Bill {
	private int appointment_id;
	public SchedulableItemBill(int id_val, double amt_val, String st_val, String gen_val, String pd_val, int itid_val) {
		super(id_val, amt_val, st_val, gen_val, pd_val);
		appointment_id = itid_val;
	}
	public SchedulableItemBill(double amt_val, String st_val, String gen_val, String pd_val, int itid_val) {
		super(amt_val, st_val, gen_val, pd_val);
		appointment_id = itid_val;
	}
	public SchedulableItemBill(ResultSet rt) throws SQLException {
		super(rt);
		try{ appointment_id = rt.getInt("appointment_id"); } catch(SQLException e) {}
	}
	public final static String getFields() {
		return "bill_id, amount, status, generated_on, appointment_id";
	}
	@Override
	public String getinitValues() {
		return appointment_id + ", " + super.getinitValues();
	}
	public static String getinitFields() {
		return "appointment_id, " + Bill.getinitFields();
	}
	public final int getAppointment_id() {
		return appointment_id;
	}
	public int getId() {
		return id;
	}
	public String getStatus() {
		return status;
	}
	public String getPaidOn() {
		return paid_on;
	}
}
