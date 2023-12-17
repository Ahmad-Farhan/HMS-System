package billing;

import diagnostics.appointment.Appointment;
import application.DBConnection;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BillingDatabase {
	public static void saveBill(SchedulableItemBill bill) {
		System.out.println("INSERT INTO BILL (" + SchedulableItemBill.getinitFields() + ") values (" + bill.getinitValues() + ")");
		if(DBConnection.exists("SELECT bill_id as reqval FROM BILL WHERE appointment_id = " + bill.getAppointment_id())) return;
			DBConnection.updateQuery("INSERT INTO BILL (" + SchedulableItemBill.getinitFields() + ") values (" + bill.getinitValues() + ")");
 	}
	public static SchedulableItemBill loadBillDetails(final int app_id) {
		ResultSet rt = DBConnection.getMultiSet("SELECT " + SchedulableItemBill.getFields() + " FROM BILL WHERE BILL.Appointment_id = " + app_id);
		
		SchedulableItemBill bill = null;
		try{ if(rt.next()) bill = new SchedulableItemBill(rt); }
		catch( SQLException e ) {e.printStackTrace();}
		
		String specialization = DBConnection.getOneValue("SELECT specialization as reqval FROM APPOINTMENT INNER JOIN USER "
				+ "ON APPOINTMENT.doctor_id =  USER.user_id WHERE APPOINTMENT.appointment_id = " + app_id);
		if(specialization.contains("\r")) specialization = specialization.substring(0, specialization.length() - 1);		
		try {
			String cost_value = DBConnection.getOneValue("SELECT cost as reqval FROM APPOINTMENTPRICE WHERE specialization = '" + specialization + "';");
			bill.setPrice(Integer.parseInt(cost_value));
		} catch(Exception e) {}
		
		return bill;
	}
	public static ArrayList<Appointment> retrieveConductedAppointments(){
		ArrayList<Appointment> appointments = new ArrayList<>();
		String query = "SELECT APPOINTMENT.Appointment_id, APPOINTMENT.date, TIMEINTERVAL.start_time, USER.user_id, "
				+ "USER.first_name, USER.last_name, USER.dob, USER.gender, DOCTOR.first_name, DOCTOR.last_name FROM APPOINTMENT "
				+ "INNER JOIN USER ON USER.user_id = APPOINTMENT.patient_id "
				+ "INNER JOIN TIMEINTERVAL ON APPOINTMENT.interval_id = TIMEINTERVAL.interval_id "
				+ "INNER JOIN USER DOCTOR ON APPOINTMENT.doctor_id = DOCTOR.user_id "
				+ "WHERE APPOINTMENT.status = 'Completed'";// AND DATE(APPOINTMENT.date) = CURDATE() ORDER BY start_time ASC ";
		//System.out.println(query);
		ResultSet rt = DBConnection.getMultiSet(query);
		try{ while(rt.next()) { appointments.add(new Appointment(rt)); } }
		catch(SQLException e) { e.printStackTrace(); }
		return appointments;
	}
	public static void payAppointmentBill(SchedulableItemBill bill) {
		DBConnection.updateQuery("UPDATE BILL SET Paid_on = '" + bill.getPaidOn() + "', status = '" + bill.getStatus() + "' WHERE bill_id = " + bill.getId());
	}
}
