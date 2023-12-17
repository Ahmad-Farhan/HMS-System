package billing;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import application.GeneralUserInterface;
import application.Navigation;
import billing.payments.PaymentByCash;
import diagnostics.Const;
import diagnostics.appointment.Appointment;

public class BillingView implements GeneralUserInterface{
	@FXML private Pane mainpanel;
	@FXML private Pane pastAppointments;
	@FXML private ListView<Pane> appointmentsList;
	
	@FXML private Pane billpreview;
	
	@FXML private Text patientnamelbl;
	@FXML private Text patientinfolbl;
	@FXML private Text doctornamelbl;
	@FXML private Text datelbl;
	
	@FXML private Text subtotallbl;
	@FXML private Text taxlbl;
	@FXML private Text totallbl;

	private int selectedIdx;
	private SchedulableItemBill bill;
	private Appointment appointment;
	private ArrayList<BillCard> billCards;	
	private BillingService service;
	
	public BillingView() {
		service = new BillingService();
	}
    public void initialize() {
    	initializeCardsList();
     	pastAppointments.setPrefHeight(Const.aslvh * appointmentsList.getItems().size() + 50);
     	appointmentsList.setPrefHeight(Const.aslvh * appointmentsList.getItems().size() + 50);
     	mainpanel.setPrefHeight(400 + Const.aslvh * appointmentsList.getItems().size() + 50);
    }
	public final String getTitle() {
		return "Make Payments";
	}
    private void initializeCardsList() {
    	billCards = new ArrayList<>();
    	ArrayList<Appointment> appointments = service.getConductedAppointments();
    	if(appointmentsList.getItems().size() != 0) return;
    	
    	for(Appointment appointment : appointments) { 
    		try { initAppCard(appointment); } 
    		catch (IOException e) { e.printStackTrace(); }
         }
    	listSelection();
    }
    private void initAppCard(Appointment appointment) throws IOException {
    	BillCard control = new BillCard(appointment);
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/ui/AppointmentBill.fxml"));
        loader.setController(control);
        
        Pane cardBox = loader.load();
        appointmentsList.getItems().add(cardBox);
        billCards.add(control);
        control.initialize(billCards.size());
    }	
    private void loadBillDetails() {
    	bill = service.loadBillDetails(appointment.getId());
    	double amount = bill.getAmount(), tax = calculateTax(amount);
    	subtotallbl.setText(subtotallbl.getText() +"\t\t" + amount);
    	taxlbl.setText(taxlbl.getText() +"\t\t\t" + tax);
    	totallbl.setText(totallbl.getText() +"\t\t" + (amount + tax));
    }
    private void listSelection() {
    	appointmentsList.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() != -1) {
            	selectedIdx = appointmentsList.getSelectionModel().getSelectedIndex();
            	appointment = billCards.get(selectedIdx).getAppointment();
        		this.patientnamelbl.setText(patientnamelbl.getText() + '\t' + appointment.getPatientName());
        		this.patientinfolbl.setText(patientinfolbl.getText() + '\t' + appointment.getAgeGender());
        		this.doctornamelbl.setText(doctornamelbl.getText() + '\t' + appointment.getDoctorName());
        		this.datelbl.setText(datelbl.getText() + '\t' + appointment.getPrescribedDate());        
        		loadBillDetails();
            }
        });    	
    }
    private double calculateTax(final double amount) {
    	return amount * 0.7;
    }
    private void updateSelectedInfo() {
    	appointmentsList.getItems().remove(selectedIdx);
		this.patientnamelbl.setText("Patient Name: ");
		this.patientinfolbl.setText("Age/Gender: " );
		this.doctornamelbl.setText("Prescribed By: ");
		this.datelbl.setText("Prescribed On: ");       
    	subtotallbl.setText("SubTotal: ");
    	taxlbl.setText("Tax: ");
    	totallbl.setText("Total: ");
       
    	showAlert("Payment Successfull", "Payment Made Successfully!");
    }
    private void showAlert(String title, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();    	
    }
    @FXML public void payByCash() {
    	if(this.bill == null) {
    		showAlert("Invalid Selection", "Please Select a Bill!");
    		return;
    	}
    	service.setStrategy(new PaymentByCash());
    	service.processBill(bill);
    	updateSelectedInfo();
    	bill = null;
    }
	@FXML public void redirectToLogout() {
		Navigation.self().loadloginView();
	}
}
