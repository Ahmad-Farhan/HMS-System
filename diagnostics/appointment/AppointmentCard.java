package diagnostics.appointment;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class AppointmentCard {
	@FXML private Text srnolbl;
	@FXML private Text namelbl;
	@FXML private Text agegenderlbl;
	@FXML private Text contactlbl;
	@FXML private Text statuslbl;
	@FXML private Text timelbl;
	
	private Appointment appointment;
	private int srno;
	
	public AppointmentCard() {
	}
	public AppointmentCard(Appointment appointment_val) {
		appointment = appointment_val;
	}
	public void initialize(final int id) {
		srno = id;
		srnolbl.setText(Integer.toString(id));
		setNameLabel();
		setAgeGenderLabel();
		setContactLabel();
		setStatus();
		setTime();
	}
	private void setTime() {
		timelbl.setText(appointment.getAppointmentTime());
	}
	private void setContactLabel() {
		contactlbl.setText(appointment.getContact());
	}
	private void setStatus() {
		statuslbl.setText(appointment.getStatus());
	}
	private void setAgeGenderLabel() {
		agegenderlbl.setText(appointment.getAgeGender());// + '/' + appointment.getGender());
	}
	private void setNameLabel() {
		namelbl.setText(appointment.getPatientName());
	}
	public Appointment getAppointment() {
		return appointment;
	}

}
