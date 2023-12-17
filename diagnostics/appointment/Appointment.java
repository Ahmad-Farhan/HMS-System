package diagnostics.appointment;

import java.sql.ResultSet;
import java.sql.SQLException;

import diagnostics.Schedulable;
import users.Doctor;
import users.Patient;

public class Appointment implements Schedulable {
	private int id;
	private String status;
	private String date;
	private Patient patient;
	private TimeInterval timeslot;
	private Doctor doctor;
	
	public Appointment(final int appointment_id) {
		id = appointment_id;
	}
	public Appointment(ResultSet rt) throws SQLException {
		try{ id = rt.getInt("appointment_id"); } catch(SQLException e) {}
		try{ status = rt.getString("status");} catch(SQLException e) {}
		try{ date = rt.getString("date");} catch(SQLException e) {}
		try{ patient = new Patient(rt);} catch(SQLException e) {}
		try{ timeslot = new TimeInterval(rt);} catch(SQLException e) {}
		try{ doctor = new Doctor(rt, true);} catch(SQLException e) {}
	} 
	public static String getinitFields() {
		return "Appointment.appointment_id, Appointment.date, Appointment.status, "+ Patient.getFields() + ',' + TimeInterval.getinitFields();
	}
	public static String getAppSelectFields() {
		return "Appointment.appointment_id, Appointment.date, Appointment.status, " + 
				Patient.getAppSelectFields() + ',' + TimeInterval.getAppSelectFields() +
				", DOCTOR.user_id, DOCTOR.first_name, DOCTOR.last_name"; 
	}
	public final int getId() {
		return id;
	}
	public final String getAppointmentTime() {
		return timeslot.getStartTime();
	}
	public final String getContact() {
		return patient.getContact();
	}
	public final String getStatus() {
		return status;
	}
	public final String getAgeGender() {
		return patient.getAgeGender();
	}
	public final String getPatientName() {
		return patient.getName();
	}
	public final String getPrescribedDate() {
		return date;
	}
	public final String getDoctorName() {
		return doctor.getName();
	}
	public final int getPatientId() {
		return patient.getId();
	}
	public final int getDoctorId() {
		return doctor.getId();
	}
}
