package diagnostics.appointment;

import java.io.IOException;
import java.util.ArrayList;

import application.GeneralUserInterface;
import application.Navigation;
import diagnostics.Const;
import diagnostics.SchedulableHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class AppointmentView implements GeneralUserInterface{
	
	@FXML private Pane mainpanel;
	@FXML private Pane scheduledAppointments;
	@FXML private ListView<Pane> appointmentsList;
	
	@FXML private Text active_srno;
	@FXML private Text active_patname;
	@FXML private Text active_patinfo;
	@FXML private Text active_patcontact;
	@FXML private Text active_apptime;

	private int doctor_id;
	private Appointment activeAppointment;
	private ArrayList<AppointmentCard> appointmentCards;	
	private SchedulableHandler controller;
	
	public AppointmentView(final int doc_id){
		doctor_id = doc_id;
		controller = new SchedulableHandler();
	}
	
	public final String getTitle() {
		return "Scheduled Appointments";
	}

    public void initialize() {
    	initializeCardsList();
     	scheduledAppointments.setPrefHeight(calHeight());
     	appointmentsList.setPrefHeight(calHeight());
     	mainpanel.setPrefHeight(400 + calHeight());
    }
    private void initializeCardsList() {
    	appointmentCards = new ArrayList<>();
    	ArrayList<Appointment> appointments = controller.getTodaysAppointments(doctor_id);
    	if(appointmentsList.getItems().size() != 0) return;
    	
    	for(Appointment appointment : appointments) { 
    		try { initAppCard(appointment); } 
    		catch (IOException e) { e.printStackTrace(); }
         }
    	listSelection();
    }
    private void initAppCard(Appointment appointment) throws IOException {
    	AppointmentCard control = new AppointmentCard(appointment);
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/ui/AppointmentSlots.fxml"));
        loader.setController(control);
        
        Pane cardBox = loader.load();
        appointmentsList.getItems().add(cardBox);
        appointmentCards.add(control);
        control.initialize(appointmentCards.size());
    }
    private void listSelection() {
    	appointmentsList.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() != -1) {
            	int idx = appointmentsList.getSelectionModel().getSelectedIndex();
            	activeAppointment = appointmentCards.get(idx).getAppointment();
            	active_srno.setText(Integer.toString(idx + 1));
            	active_patname.setText("Patient: " + activeAppointment.getPatientName());
            	active_patinfo.setText("Age/Gender: " + activeAppointment.getAgeGender());;
            	active_patcontact.setText("Contact: " + activeAppointment.getContact());;
            	active_apptime.setText(activeAppointment.getAppointmentTime());            }
        });    	
    }
    
    private int calHeight() {
    	return Const.aslvh * appointmentsList.getItems().size() + 50;
    }
    @FXML private void accessMedicalHistory() {
		if(activeAppointment == null) return;
		int patient_id = activeAppointment.getPatientId();
		Navigation.self().loadMedicalHistoryView(patient_id);
    }
	@FXML public void initiatePatientDiagnosis() {
		if(activeAppointment == null) return;
		Navigation.self().loadPatientDiagnosisView(activeAppointment);
	}
	@FXML public void redirectToLogout() {
		Navigation.self().loadloginView();
	}
}