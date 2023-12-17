package diagnostics;

import java.util.ArrayList;

import diagnostics.appointment.Appointment;

public class SchedulableHandler {
	ArrayList<Appointment> appointments; 
	public SchedulableHandler() {
	}
	public ArrayList<Appointment> getTodaysAppointments(final int doc_id){
		appointments = SchedulableDatabase.retrieveTodaysAppointments(doc_id);
		return appointments;
	}
	public ArrayList<Appointment> getConductedAppointments(){
		appointments = SchedulableDatabase.retrieveConductedAppointments();
		return appointments;
	}
	
}
