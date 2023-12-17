package diagnostics;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ArrayList;

import application.GeneralUserInterface;
import application.Navigation;
import diagnostics.appointment.Appointment;
import diagnostics.prescription.Medicine;
import diagnostics.prescription.Prescription;
import diagnostics.prescription.Symptom;
import diagnostics.prescription.Test;

public class DiagnosticView implements GeneralUserInterface{
	@FXML private ComboBox<String> symptomSelector;
	@FXML private ComboBox<String> testSelector;
	@FXML private ComboBox<String> medicineSelector;
	@FXML private TextField remarksWriter;
	
	@FXML private ComboBox<String> amountSelector;
	@FXML private ComboBox<String> durationSelector;
	
	@FXML private RadioButton beforem;
	@FXML private RadioButton afterm;
	@FXML private ToggleGroup intakeinst;
	
	@FXML private CheckBox TimingChecks1;
	@FXML private CheckBox TimingChecks2;
	@FXML private CheckBox TimingChecks3;
	@FXML private CheckBox TimingChecks4;	
	
	@FXML private Text patientnamelbl;
	@FXML private Text patientinfolbl;
	@FXML private Text doctornamelbl;
	@FXML private Text datelbl;

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
	private final int doctor_id;
	private DiagnosticHandler controller;

	public DiagnosticView(Appointment appointment){
		doctor_id = appointment.getDoctorId();
		controller = new DiagnosticHandler();
		controller.makeHealthReport(appointment);
	}
	@Override
	public String getTitle() {
		return "Patient Diagnosis";
	}
	public void initialize() {
		initReportMakerFrame();		
		initReportPreviewFrame();
	}
	private void initReportMakerFrame() {
		this.initTestList();
		this.initSymptomList();
		this.initMedicineList();
		this.initPrescriptionList();
	}
	private void initReportPreviewFrame() {
		Appointment appointment = controller.getAppointment();
		this.patientnamelbl.setText(patientnamelbl.getText() + ' ' + appointment.getPatientName());
		this.patientinfolbl.setText(patientinfolbl.getText() + ' ' + appointment.getAgeGender());
		this.doctornamelbl.setText(doctornamelbl.getText() + ' ' + appointment.getDoctorName());
		this.datelbl.setText(datelbl.getText() + ' ' + appointment.getPrescribedDate());
	}
	private void initTestList() {
		ArrayList<Test> tests = controller.getTestList();
		for(int i=0;i<tests.size();i++) {
			testSelector.getItems().add(tests.get(i).getName());
		}
	}
	private void initSymptomList() {
		ArrayList<Symptom> symptoms = controller.getSymptomList();
		for(int i=0;i<symptoms.size();i++) {
			symptomSelector.getItems().add(symptoms.get(i).getName());
		}
	}
	private void initMedicineList() {
		ArrayList<Medicine> medicines = controller.getMedicineList();
		for(int i=0;i<medicines.size();i++) {
			medicineSelector.getItems().add(medicines.get(i).getViewValues());
		}
	}
	private void initPrescriptionList() {
		for (int quantity = 1; quantity < 5; quantity++)
			amountSelector.getItems().add(Integer.toString(quantity));
		
		for (int day_value = 1; day_value < 30; day_value++) 
			durationSelector.getItems().add(Integer.toString(day_value));
		
		intakeinst = new ToggleGroup();
		this.afterm.setToggleGroup(intakeinst);
		this.beforem.setToggleGroup(intakeinst);
	}
	void setBounds(Pane panel, int x, int y, int width, int height) {
		panel.setLayoutY(y);
		panel.setLayoutX(x);
		panel.setPrefWidth(width);
		panel.setPrefHeight(height);
	}
	void setBounds(ListView<String> panel, int x, int y, int width, int height) {
		panel.setLayoutY(y);
		panel.setLayoutX(x);
		panel.setPrefWidth(width);
		panel.setPrefHeight(height);
	}
	private void initPrescSymptomsPanel() {
		symptomsSize = Const.ipps;
		setBounds(psymptomsPanel, Const.ppx, Const.pspy, Const.ppw, symptomsSize);
		setBounds(symptomslist, Const.plx, Const.psly, Const.plw, 0);
		psymptomsPanel.setVisible(true); 
	}
	private void initPrescTestsPanel() {
		testsSize = Const.ipps;
		setBounds(ptestsPanel, Const.ppx, Const.pspy + symptomsSize, Const.ppw, testsSize);
		setBounds(testslist, Const.plx, Const.psly, Const.plw, 0);
		ptestsPanel.setVisible(true); 
	}
	private void initPrescMedicinesPanel() {
		medicinesSize = Const.ipps;
		setBounds(pmedicinesPanel, Const.ppx, Const.pspy + symptomsSize + testsSize, Const.ppw, medicinesSize);
		setBounds(medicineslist, Const.plx, Const.psly, Const.plw, 0);
		pmedicinesPanel.setVisible(true); 
	}
	private void initPrescRemarksPanel() {
		remarksSize = Const.ipps;
		setBounds(premarksPanel, Const.ppx, Const.pspy + symptomsSize + testsSize, Const.ppw, 100);
		premarksPanel.setVisible(true);
	}
	@FXML
	public void addPrescSymptom() {
		int idx = symptomSelector.getSelectionModel().getSelectedIndex();
		if(idx == -1 || !controller.addSymptom(idx)) return; 

		if(symptomslist.getItems().size() == 0)
			initPrescSymptomsPanel();
		
		symptomsSize += Const.lcs;
		symptomslist.getItems().add(controller.getSymptom(idx).getName());
		setBounds(psymptomsPanel, Const.ppx, Const.pspy, Const.ppw, symptomsSize);
		setBounds(symptomslist, Const.plx, Const.psly, Const.plw, symptomsSize - 25);
		
		setBounds(ptestsPanel, Const.ppx, Const.pspy + symptomsSize, Const.ppw, testsSize);
		setBounds(pmedicinesPanel, Const.ppx, Const.pspy + symptomsSize + testsSize, Const.ppw, medicinesSize);
		setBounds(premarksPanel, Const.ppx, Const.pspy + symptomsSize + testsSize + medicinesSize, Const.ppw, remarksSize);
		setBounds(prescriptionpreview, Const.ppmx, Const.ppmy, Const.ppmw, getPanelSize());	
	}
	@FXML
	public void addPrescTest() {
		int idx = testSelector.getSelectionModel().getSelectedIndex();
		if(idx == -1 || !controller.addTest(idx)) return; 

		if(testslist.getItems().size() == 0)
			initPrescTestsPanel();
		
		testsSize += Const.lcs;
		testslist.getItems().add(controller.getTest(idx).getName());
		setBounds(ptestsPanel, Const.ppx, Const.pspy + symptomsSize, Const.ppw, testsSize);
		setBounds(testslist, Const.plx, Const.psly, Const.plw, testsSize - 25);
		
		setBounds(pmedicinesPanel, Const.ppx, Const.pspy + symptomsSize + testsSize, Const.ppw, medicinesSize);
		setBounds(premarksPanel, Const.ppx, Const.pspy + symptomsSize + testsSize + medicinesSize, Const.ppw, remarksSize);
		setBounds(prescriptionpreview, Const.ppmx, Const.ppmy, Const.ppmw, getPanelSize());	
	}
	@FXML
	public void addPrescMedicine() {
		int idx = medicineSelector.getSelectionModel().getSelectedIndex();
		if(idx == -1) return; 
		
		if(medicineslist.getItems().size() == 0) 
			initPrescMedicinesPanel();
		if(!validPrescription(idx)) return;
		
		medicinesSize += Const.mlcs;
		setBounds(pmedicinesPanel, Const.ppx, Const.pspy + symptomsSize + testsSize, Const.ppw, medicinesSize);
		setBounds(medicineslist, Const.plx, Const.psly, Const.plw, medicinesSize - 25);
		
		setBounds(premarksPanel, Const.ppx, Const.pspy + symptomsSize + testsSize + medicinesSize, Const.ppw, remarksSize);
		setBounds(prescriptionpreview, Const.ppmx, Const.ppmy, Const.ppmw, getPanelSize());	
	}
	@FXML
	public void addPrescRemark() {		
		if(remarksbox.getText().equals("")) 
			initPrescRemarksPanel();
		String remark = remarksWriter.getText();
		controller.addRemark(remark);
		remarksbox.setText(remark);
		remarksSize = remark.length() / 40 * 23;

		setBounds(premarksPanel, Const.ppx, Const.pspy + symptomsSize + testsSize + medicinesSize, Const.ppw, remarksSize);
		setBounds(prescriptionpreview, Const.ppmx, Const.ppmy, Const.ppmw, getPanelSize());	
	}
	public boolean validPrescription(final int idx) {
		String tmgs, inst;
		int amt, days;
		try{ amt = amountSelector.getSelectionModel().getSelectedIndex () + 1;} catch(Exception e) {return false;}
		try{ tmgs = makeTimingString();} catch(Exception e) {return false;}
		try{ inst = ((RadioButton) intakeinst.getSelectedToggle()).getText();} catch(Exception e) {return false;}
		try{ days = Integer.parseInt(durationSelector.getSelectionModel().getSelectedItem());} catch(Exception e) {return false;}
		
		Prescription prescription = new Prescription(controller.getMedicine(idx), amt, tmgs, days, inst);
		boolean validPrescription = controller.addPrescription(prescription);
		if(validPrescription ) medicineslist.getItems().add(prescription.getViewValues());
		return validPrescription ;
	}
	private final int getPanelSize() {
		int totalSize = 240 + 30;
		if (symptomsSize > Const.ipps) totalSize += symptomsSize;
		if (testsSize > Const.ipps) totalSize += testsSize;
		if (medicinesSize > Const.ipps) totalSize += medicinesSize;	
		if (remarksSize > Const.ipps) totalSize += remarksSize;
		return totalSize;
	}
	private final String makeTimingString() {
		String result = "";
		if(TimingChecks1.isSelected()) result += TimingChecks1.getText() + ", ";
		if(TimingChecks2.isSelected()) result += TimingChecks2.getText() + ", ";
		if(TimingChecks3.isSelected()) result += TimingChecks3.getText() + ", ";
		if(TimingChecks4.isSelected()) result += TimingChecks4.getText() + ", ";
		
		if(result.length() != 0)
			result = result.substring(0, result.length() - 2);
		return result;
	}
	@FXML public void generateReport() {
		controller.finalizeDiagnosis();
		Navigation.self().loadApptSelectView();
	}
	@FXML public void redirectToPatientDiagnosis() {
		Navigation.self().loadApptSelectView();
	}

}
