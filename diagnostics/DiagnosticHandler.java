package diagnostics;

import java.util.ArrayList;

import billing.BillingService;
import diagnostics.appointment.Appointment;
import diagnostics.prescription.Medicine;
import diagnostics.prescription.Prescription;
import diagnostics.prescription.Symptom;
import diagnostics.prescription.Test;
import repo.HealthReport;

public class DiagnosticHandler {
	ArrayList<Medicine> medicines;
	ArrayList<Test> tests;
	ArrayList<Symptom> symptoms;
	ArrayList<HealthReport> reports;
	HealthReport report;
	
	public DiagnosticHandler() {
		medicines = DiagnosticsDatabase.loadMedicinesList();
		tests = DiagnosticsDatabase.loadTestsList();
		symptoms = DiagnosticsDatabase.loadSymptomsList();
	}
	public ArrayList<HealthReport> getReportList(){
		return reports;
	}
	public ArrayList<Medicine> getMedicineList(){
		return medicines;
	}
	public ArrayList<Symptom> getSymptomList(){
		return symptoms;
	}
	public ArrayList<Test> getTestList(){
		return tests;
	}
	public Medicine getMedicine(final int index){
		return medicines.get(index);
	}
	public Symptom getSymptom(final int index){
		return symptoms.get(index);
	}
	public Test getTest(final int index){
		return tests.get(index);
	}
	public void makeHealthReport(final Appointment appointment) {
		report = new HealthReport(appointment);
	}
	public HealthReport getReport() {
		return report;
	}
	public Appointment getAppointment() {
		return report.getAppointment();
	}
	public boolean addPrescription(Prescription prescription) {
		return report.addPrescription(prescription);
	}
	public boolean addSymptom(final int symptom_num) {
		return report.addSymptom(symptoms.get(symptom_num));
	}
	public boolean addTest(final int test_num) {
		return report.addTest(tests.get(test_num));
	}
	public void addRemark(String remark) {
		report.addRemark(remark);
	}
	public void loadReport(final int index) {
		report = reports.get(index);
		if(!report.detailsEmpty()) {
			System.out.println("No Details Available");
			return;
		}
		DiagnosticsDatabase.loadHealthReport(report);
	}
	public ArrayList<HealthReport> LoadHealthReports(final int patient_id) {
		reports = DiagnosticsDatabase.retrieveReportsList(patient_id);
		return reports;
	}
	public void saveReport() {
		report.finalizeReport();
		DiagnosticsDatabase.updateAppointmentStatus(report.getAppointment().getId(), "Completed");
		DiagnosticsDatabase.saveHealthReport(report);
	}
	public void generateBill() {
		BillingService service = new BillingService();
		service.generateBill(report);
	}
	public void finalizeDiagnosis() {
		generateBill();
		saveReport();
	}
	
}
