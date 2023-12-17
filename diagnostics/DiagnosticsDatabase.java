package diagnostics;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.DBConnection;
import diagnostics.prescription.Medicine;
import diagnostics.prescription.Prescription;
import diagnostics.prescription.Symptom;
import diagnostics.prescription.Test;
import repo.HealthReport;

public class DiagnosticsDatabase {
	public static ArrayList<Medicine> loadMedicinesList(){
		ArrayList<Medicine> medicines = new ArrayList<Medicine>();
		ResultSet rt = DBConnection.getMultiSet("SELECT * FROM MEDICINE ORDER BY medicinename");
		
		try { while(rt.next()) medicines.add(new Medicine(rt)); } 
		catch ( SQLException e){ e.printStackTrace(); }
		
		return medicines;
	}
	public static ArrayList<Test> loadTestsList(){
		ArrayList<Test> tests = new ArrayList<Test>();
		ResultSet rt = DBConnection.getMultiSet("SELECT * FROM TEST ORDER BY testname");
		
		try {while(rt.next()) tests.add(new Test(rt)); } 
		catch ( SQLException e){ e.printStackTrace(); }
		
		return tests;
	}
	public static ArrayList<Symptom> loadSymptomsList(){
		ArrayList<Symptom> symptoms = new ArrayList<Symptom>();
		ResultSet rt = DBConnection.getMultiSet("SELECT * FROM SYMPTOM ORDER BY name");
		
		try { while(rt.next()) symptoms.add(new Symptom(rt)); } 
		catch ( SQLException e){ e.printStackTrace();}
		
		return symptoms;
	}
	public static void saveHealthReport(HealthReport report) {
		if(reportAlreadyExists(report)) return;
			
		String reportInsert = "INSERT INTO HEALTHREPORT (" + HealthReport.getinitFields() + ") VALUES (" + report.getinitValues() + ")";
		//System.out.println(reportInsert);
		DBConnection.updateQuery(reportInsert);
		final int report_id = getHealthReportID();
		report.setId(report_id);
		
		String presc = "INSERT INTO PRESCRIPTION (" + Prescription.getinitFields() + ") VALUES " + report.getPrescriptionValues();
		DBConnection.updateQuery(presc);
		String tests = "INSERT INTO RECOMMENDATION (healthreport_id,  test_id) VALUES " + report.getTestValues();
		DBConnection.updateQuery(tests);
		String symps = "INSERT INTO IDENTIFIEDSYMPTOM (healthreport_id, symptom_id) VALUES " + report.getSymptomValues();
		DBConnection.updateQuery(symps);
	}
	public static void loadHealthReport(HealthReport report) {
		ResultSet prescriptions = DBConnection.getMultiSet("SELECT * FROM PRESCRIPTION INNER JOIN MEDICINE ON MEDICINE.medicine_id = PRESCRIPTION.medicine_id WHERE healthreport_id = " + report.getId());
		report.loadPrescriptions(prescriptions);
		ResultSet symptoms = DBConnection.getMultiSet("SELECT * FROM IDENTIFIEDSYMPTOM INNER JOIN SYMPTOM ON IDENTIFIEDSYMPTOM.symptom_id = SYMPTOM.symptom_id WHERE healthreport_id = " + report.getId());
		report.loadSymptoms(symptoms);
		ResultSet tests = DBConnection.getMultiSet("SELECT * FROM RECOMMENDATION INNER JOIN TEST ON RECOMMENDATION.test_id = TEST.test_id WHERE healthreport_id = " + report.getId());
		report.loadTests(tests);
		ResultSet app = DBConnection.getMultiSet(loadAppointmentDetailsQuery(report.getAppointment().getId()));
		report.loadAppointments(app);
	}
	private static String loadAppointmentDetailsQuery(final int id) {
		return  "SELECT APPOINTMENT.Appointment_id, APPOINTMENT.date, TIMEINTERVAL.start_time, USER.user_id, "
				+ "USER.first_name, USER.last_name, USER.dob, USER.gender, DOCTOR.first_name, DOCTOR.last_name FROM APPOINTMENT "
				+ "INNER JOIN USER ON USER.user_id = APPOINTMENT.patient_id "
				+ "INNER JOIN TIMEINTERVAL ON APPOINTMENT.interval_id = TIMEINTERVAL.interval_id "
				+ "INNER JOIN USER DOCTOR ON APPOINTMENT.doctor_id = DOCTOR.user_id "
				+ "WHERE APPOINTMENT.appointment_id = " + id;
	}
	public static boolean reportAlreadyExists(HealthReport report) {
		return DBConnection.exists("SELECT HEALTHREPORT.healthreport_id as reqval FROM HEALTHREPORT WHERE appointment_id = "  + report.getAppointment().getId());
	}
	public static ArrayList<HealthReport> retrieveReportsList(final int patient_id) {
		String query = "SELECT HEALTHREPORT.healthreport_id, " + HealthReport.getinitFields() + ", APPOINTMENT.date, TIMEINTERVAL.start_time, "
				+ "DOCTOR.user_id, DOCTOR.first_name, DOCTOR.last_name FROM HEALTHREPORT "
				+ "INNER JOIN APPOINTMENT ON HEALTHREPORT.Appointment_id = APPOINTMENT.Appointment_id "
				+ "INNER JOIN TIMEINTERVAL ON APPOINTMENT.interval_id = TIMEINTERVAL.interval_id "
				+ "INNER JOIN USER DOCTOR ON APPOINTMENT.doctor_id = DOCTOR.user_id "
				+ "WHERE HEALTHREPORT.patient_id = " + patient_id + " ORDER BY date DESC, start_time DESC";
		
		ResultSet rt = DBConnection.getMultiSet(query);
		ArrayList<HealthReport> reports = new ArrayList<>();
		
		try { while(rt.next()) reports.add(new HealthReport(rt));}
		catch(SQLException e) { e.printStackTrace(); }
		
		return reports;
	}
	private static int getHealthReportID() {
		String id_str = DBConnection.getOneValue("SELECT MAX(healthreport_id) as reqval FROM HEALTHREPORT");
		final int report_id = Integer.parseInt(id_str);
		return report_id;
	}
	public static void updateAppointmentStatus(int app_id, String string) {
		DBConnection.updateQuery("UPDATE APPOINTMENT SET status = '" + string + "'  WHERE Appointment_id = " + app_id);
	}
	
	
}


