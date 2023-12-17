package repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import diagnostics.appointment.Appointment;
import diagnostics.prescription.Prescription;
import diagnostics.prescription.Symptom;
import diagnostics.prescription.Test;

public class HealthReport {
	private int id;
	private ArrayList<Prescription> prescriptions;
	private ArrayList<Symptom> symptoms;
	private ArrayList<Test> tests;
	private String remark;
	private boolean isComplete;
	private int doctor_id;
	private int patient_id;
	
	private Appointment appointment;
	
	public HealthReport(int doc_id, int pat_id, String re_val) {
		prescriptions = new ArrayList<Prescription>();
		symptoms = new ArrayList<Symptom>();
		tests = new ArrayList<Test>();
		appointment = null;
		patient_id = pat_id;
		doctor_id = doc_id;
		isComplete = false;
	}
	public HealthReport(final int app_id) {
		prescriptions = new ArrayList<Prescription>();
		symptoms = new ArrayList<Symptom>();
		tests = new ArrayList<Test>();
		appointment = new Appointment(app_id);
		patient_id = -1;
		doctor_id = -1;
		isComplete = false;		
	}
	public HealthReport(final Appointment app) {
		prescriptions = new ArrayList<Prescription>();
		symptoms = new ArrayList<Symptom>();
		tests = new ArrayList<Test>();
		appointment = app;
		patient_id = app.getPatientId();
		doctor_id = app.getDoctorId();
		isComplete = false;		
	}
	public HealthReport(ResultSet rt) throws SQLException{		
		prescriptions = null;
		symptoms = null;
		tests = null;
		isComplete = true;
		
		id = rt.getInt("healthreport_id");
		try{ appointment = new Appointment(rt);} catch(SQLException e) {}
		try{ patient_id = rt.getInt("patient_id");} catch(SQLException e) {}
		try{ doctor_id = rt.getInt("doctor_id");} catch(SQLException e) {}
		try{ patient_id = rt.getInt("patient_id");} catch(SQLException e) {}
		try{ remark = rt.getString("remarks");} catch(SQLException e) {}
	}
	public final String getinitValues() {
		return patient_id + "," + doctor_id + "," + appointment.getId() + ",'" + remark + "'";
	}
	public static final String getinitFields() {
		return "HealthReport.patient_id, HealthReport.doctor_id, HealthReport.appointment_id, HealthReport.remarks";
	}
	public boolean addPrescription(Prescription prescription) {		
        //if(! prescriptionAdded(prescription.getMedId())) {
		if(! prescriptions.stream().anyMatch(obj -> obj.getMedId() == prescription.getMedId())) {
        	prescriptions.add(prescription);
			return true;
        } return false;
	}
	public boolean addSymptom(Symptom symptom) {
        //if(!symptomAdded(symptom.getId())) {
        if(! symptoms.stream().anyMatch(obj -> obj.getId() == symptom.getId())) {
        	symptoms.add(symptom);
			return true;
        } return false;
	}
	public boolean addTest(Test test) {
        //if(!testAdded(test.getId())) {
        if(! tests.stream().anyMatch(obj -> obj.getId() == test.getId())) {
        	tests.add(test);
			return true;
        } return false;
	}
	public void addRemark(final String remark_val) {
		remark = remark_val;
	}
	public void finalizeReport() {
		isComplete = true;
	}
	public final String getPrescriptionValues() {
		String prescriptionsquery = "";
		for(int i = 0; i < prescriptions.size(); i++) {
			prescriptionsquery += "(" + id + "," + prescriptions.get(i).getinitValues() + ")";
			if(i != prescriptions.size() - 1) prescriptionsquery += ',';
		}
		return prescriptionsquery;
	}
	public final String getTestValues() {
		String testsquery = "";
		for(int i = 0; i < tests.size(); i++) {
			testsquery += "(" + id + "," + tests.get(i).getId() + ")";
			if(i != tests.size() - 1) testsquery += ',';
		}
		return testsquery;
	}
	public final String getSymptomValues() {
		String symptomsquery = "";
		for(int i = 0; i < symptoms.size(); i++) {
			symptomsquery += "(" + id + "," + symptoms.get(i).getId() + ")";
			if(i != symptoms.size() - 1) symptomsquery += ',';
		}
		return symptomsquery;
	}
	public void loadSymptoms(ResultSet rt) {
		if(!isComplete) return;
		symptoms = new ArrayList<>();
		try{ while(rt.next()) symptoms.add(new Symptom(rt)); rt.close();}
		catch(SQLException e) {e.printStackTrace();}
	}
	public void loadTests(ResultSet rt) {
		if(!isComplete) return;
		tests = new ArrayList<>();
		try{ while(rt.next()) tests.add(new Test(rt)); rt.close();}
		catch(SQLException e) {e.printStackTrace();}
	}
	public void loadPrescriptions(ResultSet rt) {
		if(!isComplete) return;
		prescriptions = new ArrayList<>();
		try{ while(rt.next()) prescriptions.add(new Prescription(rt)); rt.close();}
		catch(SQLException e) {e.printStackTrace();}
	}
	public void loadAppointments(ResultSet rt) {
		if(!isComplete) return;
		try { if(rt.next()) appointment = new Appointment(rt); } catch(SQLException e) {}
	}
	public void setId(final int report_id) {
		id = report_id;
	}
	public boolean detailsEmpty() {
		return symptoms == null && tests == null && prescriptions == null;
	}
	public final int getId() {
		return id;
	}
	public final Appointment getAppointment() {
		return appointment;
	}
	public final ArrayList<Prescription> getPrescriptions(){
		return prescriptions;
	}
	public final ArrayList<Symptom> getSymptoms(){
		return symptoms;
	}
	public final ArrayList<Test> getTests(){
		return tests;
	}
	public final String getRemarks() {
		return remark;
	}
}
