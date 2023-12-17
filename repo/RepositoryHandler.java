package repo;

import java.io.IOException;
import java.util.ArrayList;

import application.GeneralUserInterface;
import application.Navigation;
import diagnostics.Const;
import diagnostics.DiagnosticHandler;
import diagnostics.appointment.Appointment;
import diagnostics.prescription.Prescription;
import diagnostics.prescription.Symptom;
import diagnostics.prescription.Test;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class RepositoryHandler implements GeneralUserInterface{
	private final int patient_id;
	private final int doctor_id;
	
	@FXML private Pane mainpanel;
	@FXML private Pane pastAppointments;
	@FXML private ListView<Pane> appointmentsList;
	@FXML private Pane prescriptionpreview;

	@FXML private Pane pmedicinesPanel;
	@FXML private Pane psymptomsPanel;
	@FXML private Pane premarksPanel;
	@FXML private Pane ptestsPanel;

	@FXML private ListView<String> medicineslist;
	@FXML private ListView<String> symptomslist;
	@FXML private ListView<String> testslist;
	@FXML private Label remarksbox;
	
	private int medicinesSize;
	private int symptomsSize;
	private int remarksSize;
	private int testsSize;
	
	@FXML private Text patientnamelbl;
	@FXML private Text patientinfolbl;
	@FXML private Text doctornamelbl;
	@FXML private Text datelbl;

	private ArrayList<HealthReportCard> reportCards;	
	private DiagnosticHandler controller;
	private HealthReport report;
	
	public RepositoryHandler(final int pat_id, final int doc_id) {
		patient_id = pat_id;
		doctor_id = doc_id;
		controller = new DiagnosticHandler();
	}
	public String getTitle() {
		return "Medical History";
	}
    public void initialize() {
    	initializeCardsList();
     	pastAppointments.setPrefHeight(Const.aslvh * appointmentsList.getItems().size() + 50);
     	appointmentsList.setPrefHeight(Const.aslvh * appointmentsList.getItems().size() + 50);
     	mainpanel.setPrefHeight(400 + Const.aslvh * appointmentsList.getItems().size() + 50);
    }
    private void initializeCardsList() {
    	reportCards = new ArrayList<>();
    	controller.LoadHealthReports(patient_id);
    	ArrayList<HealthReport> reports = controller.getReportList();
    	if(appointmentsList.getItems().size() != 0) return;
    	
    	for(HealthReport report : reports) { 
    		try { initReportCard(report); } 
    		catch (IOException e) { e.printStackTrace(); }
         }
    	listSelection();
    }
    private void initReportCard(HealthReport report) throws IOException {
    	HealthReportCard control = new HealthReportCard(report);
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/ui/AppointmentReport.fxml"));
        loader.setController(control);
        
        Pane cardBox = loader.load();
        appointmentsList.getItems().add(cardBox);
        reportCards.add(control);
        control.initialize(reportCards.size());
    }
    private void listSelection() {
    	appointmentsList.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() != -1) {
            	int idx = appointmentsList.getSelectionModel().getSelectedIndex();
            	controller.loadReport(idx);
            	report = controller.getReport();
            	
            	Appointment appointment = controller.getAppointment();
        		this.patientnamelbl.setText(patientnamelbl.getText() + '\t' + appointment.getPatientName());
        		this.patientinfolbl.setText(patientinfolbl.getText() + '\t' + appointment.getAgeGender());
        		this.doctornamelbl.setText(doctornamelbl.getText() + '\t' + appointment.getDoctorName());
        		this.datelbl.setText(datelbl.getText() + '\t' + appointment.getPrescribedDate());        
            	
            	generateReport();
            }
        });    	
    }
	private void generateReport() {
    	medicineslist.getItems().clear();
    	symptomslist.getItems().clear();
    	testslist.getItems().clear();
    	remarksbox.setText("");
    	
    	pmedicinesPanel.setVisible(false);
    	psymptomsPanel.setVisible(false);
    	premarksPanel.setVisible(false);
    	ptestsPanel.setVisible(false);
    	
    	medicinesSize = 0;
    	symptomsSize = 0;
    	remarksSize = 0;
    	testsSize = 0;
		
    	initPrescSymptoms();
		initPrescTests();
		initPrescMedicines();
		initPrescRemarks();
		
		setBounds(prescriptionpreview, Const.hppx, Const.hppy, Const.ppmw, getPanelSize());
		mainpanel.setPrefHeight(Math.max(getPanelSize() + 200, Const.aslvh * appointmentsList.getItems().size() + 200));
	}
	private void initPrescMedicines() {
		if(report.getPrescriptions().size() == 0) return;
		
		pmedicinesPanel.setVisible(true); 
		ArrayList<Prescription> prescMedicines = report.getPrescriptions();
		medicinesSize = Const.ipps + prescMedicines.size() * Const.mlcs;
		prescMedicines.forEach(medicine -> medicineslist.getItems().add(medicine.getViewValues()));
		setBounds(pmedicinesPanel, Const.ppx, Const.pspy + symptomsSize + testsSize, Const.ppw, medicinesSize);
		setBounds(medicineslist, Const.plx, Const.psly, Const.plw, medicinesSize - 25);
	}
	private void initPrescSymptoms() {
		if(report.getSymptoms().size() == 0) return;

		psymptomsPanel.setVisible(true); 
		ArrayList<Symptom> prescSymptoms = report.getSymptoms();
		symptomsSize = Const.ipps + prescSymptoms.size() * Const.lcs;
		prescSymptoms.forEach(symptom -> symptomslist.getItems().add(symptom.getName()));
		setBounds(psymptomsPanel, Const.ppx, Const.pspy, Const.ppw, symptomsSize);
		setBounds(symptomslist, Const.plx, Const.psly, Const.plw, symptomsSize - 25);
	}
	private void initPrescTests() {
		if(report.getTests().size() == 0) return;
		
		ptestsPanel.setVisible(true); 
		ArrayList<Test> prescTests = report.getTests();
		testsSize = Const.ipps + prescTests.size() * Const.lcs;
		prescTests.forEach(test -> testslist.getItems().add(test.getName()));
		setBounds(ptestsPanel, Const.ppx, Const.pspy + symptomsSize, Const.ppw, testsSize);
		setBounds(testslist, Const.plx, Const.psly, Const.plw, testsSize - 25);
	}
	private void initPrescRemarks() {
		String remarks = report.getRemarks();
		if(remarks.equals("")) return;
		
		premarksPanel.setVisible(true);
		remarksbox.setText(remarks);
		remarksSize = remarks.length() / 40 * 23;
		setBounds(premarksPanel, Const.ppx, Const.pspy + symptomsSize + testsSize + medicinesSize, Const.ppw, remarksSize);
	}
	private final int getPanelSize() {
		int totalSize = 240 + 30;
		if (symptomsSize > Const.ipps) totalSize += symptomsSize;
		if (testsSize > Const.ipps) totalSize += testsSize;
		if (medicinesSize > Const.ipps) totalSize += medicinesSize;	
		if (remarksSize > Const.ipps) totalSize += remarksSize;
		return totalSize;
	}
	private void setBounds(Pane panel, int x, int y, int width, int height) {
		panel.setLayoutY(y);
		panel.setLayoutX(x);
		panel.setPrefWidth(width);
		panel.setPrefHeight(height);
	}
	private void setBounds(ListView<String> panel, int x, int y, int width, int height) {
		panel.setLayoutY(y);
		panel.setLayoutX(x);
		panel.setPrefWidth(width);
		panel.setPrefHeight(height);
	}
	@FXML public void redirectToPatientDiagnosis() {
		if(patient_id == doctor_id) Navigation.self().loadScheduleAppointments();
		else Navigation.self().loadApptSelectView();
	}
}
