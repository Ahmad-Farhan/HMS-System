package repo;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class HealthReportCard {
	@FXML private Text srnolbl;
	@FXML private Text namelbl;
	@FXML private Text timelbl;
	@FXML private Text datelbl;
	private HealthReport report;
	
	public HealthReportCard() { }
	
	public HealthReportCard(HealthReport report_val) {
		report = report_val;
	}
	public void initialize(final int id) {
		srnolbl.setText(Integer.toString(id));
		setNameLabel();
		setTimeLabel();
		setDateLabel();
	}
	private void setTimeLabel() {
		timelbl.setText(report.getAppointment().getAppointmentTime());
	}
	private void setDateLabel() {
		datelbl.setText(report.getAppointment().getPrescribedDate());
	}
	private void setNameLabel() {
		namelbl.setText(report.getAppointment().getDoctorName());
	}
	public HealthReport getReport() {
		return report;
	}

}
