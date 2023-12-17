package scheduling;

import java.util.ArrayList;
import users.User;

public class SchedulingHandler
{
	private User user;
	private Appointment appointment;
	private Time time;
	
	public SchedulingHandler(User patient){
		user = patient;
		appointment = new Appointment();
		time = Time.getInstance();
	}
	public ArrayList<String> getSpecialization(){
		return appointment.getSpecialization();
	}
	public ArrayList<String> getDoctor(String specialization){
		return appointment.getDoctor(specialization);
	}
	public ArrayList<String> getDate(){
		return time.getNextDates();
	}
	public ArrayList<String> getScheduleInfo(String date, String day, String Doctor){
		return appointment.RequestScheduleinfo(date, day, Doctor);
	}
	public Boolean BookSlot(String interval, String Doctor, String date){
		return appointment.BookSlot(interval, user.getId(), Doctor, date);
	}
	public ArrayList<String> getScheduledAppointments(){
		return appointment.GetScheduledAppointments(user.getId());
	}
	public boolean cancelAppointment(String appointmentdetails){
		return appointment.cancelAppointment(appointmentdetails);
	}
	public String getDoctorInfo(String a){
		return appointment.getDoctorinfo(a);
	}
	public ArrayList<String> getScheduleInfoRaw(String date, String day, String Doctor){
		return appointment.RequestScheduleinfoRaw(date, day, Doctor);
	}
	public Boolean BookSlotRaw(String interval, String Doctor, String date){
		return appointment.BookSlotRaw(interval, user.getId(), Doctor, date);
	}
}
