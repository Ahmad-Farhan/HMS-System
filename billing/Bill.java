package billing;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Bill {
	protected int id;
	protected double tax;
	protected double amount;
	protected String status;
	protected String generated_on;
	protected String paid_on;
	
	public Bill(int id_val, double amt_val, String st_val, String gen_val, String pd_val) {
		id = id_val;
		amount = amt_val;
		status = st_val;
		generated_on = gen_val;
		paid_on = pd_val;
		calculateTax();
	}
	public Bill(double amt_val, String st_val, String gen_val, String pd_val) {
		amount = amt_val;
		status = st_val;
		generated_on = gen_val;
		paid_on = pd_val;
		calculateTax();
	}
	public Bill(ResultSet rt) throws SQLException {
		try{ id = rt.getInt("bill_id"); } catch(SQLException e) {}
		try{ amount = rt.getDouble("amount"); } catch(SQLException e) {}
		try{ status = rt.getString("status"); } catch(SQLException e) {}
		try{ generated_on = rt.getString("generated_on"); } catch(SQLException e) {}
		try{ paid_on = rt.getString("paid_on"); } catch(SQLException e) {}
		calculateTax();
	}
	public String getinitValues() {
		return amount  + ", '" + status + "','" + generated_on + '\'';
	}
	public static String getinitFields() {
		return "amount, status, generated_on";
	}
	public void setPaid(String date_value) {
		status = "Paid";
		paid_on = date_value;
	}
	public double getTax() {
		return tax;
	}
	public double getAmount() {
		return amount;
	}
	public void setPrice(final double price) {
		amount = price;
		calculateTax();
	}
	private void calculateTax() {
		tax = 0.17 * amount;
	}
	
	
}
