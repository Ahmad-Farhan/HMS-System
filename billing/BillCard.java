package billing;

import diagnostics.appointment.Appointment;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class BillCard {

	@FXML private Text srnolbl;
	@FXML private Text namelbl;
	@FXML private Text timelbl;
	@FXML private Text datelbl;
	private int srno;

	private Appointment appointment;
	
	public BillCard() { }
	
	public BillCard(Appointment app_val) {
		appointment = app_val;
	}
	public void initialize(final int id) {
		srno = id;
		srnolbl.setText(Integer.toString(id));
		setNameLabel();
		setTimeLabel();
		setDateLabel();
	}
	private void setTimeLabel() {
		timelbl.setText(appointment.getAppointmentTime());
	}
	private void setDateLabel() {
		datelbl.setText(appointment.getPrescribedDate());
	}
	private void setNameLabel() {
		namelbl.setText(appointment.getDoctorName());
	}
	public Appointment getAppointment() {
		return appointment;
	}

}
