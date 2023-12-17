package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repo.RepositoryHandler;
import scheduling.SchedulingView;
import users.Patient;
import users.User;
import users.UserLoginHandler;
import billing.BillingView;
import diagnostics.DiagnosticView;
import diagnostics.appointment.Appointment;
import diagnostics.appointment.AppointmentView;


public class Navigation {
	private static Navigation navInstance;
	private Stage primaryStage;
	private User user;
	
	private Navigation() { }
	public static Navigation self() {
		if(navInstance == null) 
			navInstance = new Navigation();
		return navInstance;
	}
	public void setStage(Stage mainStage) {
		primaryStage = mainStage;
	}
	public void setUser(final User user_val) {
		user = user_val;
	}

	public void loadMedicalHistoryView(final int id) {
		try {														//"/package/fxml"
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/ui/medicalHistory.fxml"));
	        RepositoryHandler repository = new RepositoryHandler(id, user.getId());
	        loader.setController(repository);

	        Parent root = loader.load();
	        Scene scene = new Scene(root, 1280, 720);

	        primaryStage.setTitle("Medical History");
	        primaryStage.setScene(scene);
	        primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void loadPatientDiagnosisView(final Appointment appointment){
		try {
		    FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/ui/diagnosisScene.fxml"));
		    DiagnosticView control = new DiagnosticView(appointment);
		    loader.setController(control);
		    
		    Parent root = loader.load();
		    Scene scene = new Scene(root, 1280, 720);
		
		    primaryStage.setTitle("Patient Diagnostics");
		    primaryStage.setScene(scene);
		    primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void loadApptSelectView() {	
		try {
		    FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/ui/appSelectScene.fxml"));
		    AppointmentView controller = new AppointmentView(user.getId());
		    loader.setController(controller);
		
		    Parent root = loader.load();
		    Scene scene = new Scene(root, 1280, 720);
		
		    primaryStage.setTitle(controller.getTitle());
		    primaryStage.setScene(scene);
		    primaryStage.show();
		    
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void loadBillSelectView() {	
		try {
		    FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/ui/billpayments.fxml"));
		    BillingView controller = new BillingView();
		    loader.setController(controller);
		
		    Parent root = loader.load();
		    Scene scene = new Scene(root, 1280, 720);
		
		    primaryStage.setTitle(controller.getTitle());
		    primaryStage.setScene(scene);
		    primaryStage.show();
		    
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void loadloginView() {
		try {
		    FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/ui/userlogin.fxml"));
		    UserLoginHandler controller = new UserLoginHandler();
		    loader.setController(controller);
		    
		    Parent root = loader.load();
		    Scene scene = new Scene(root, 1280, 720);
		
		    primaryStage.setTitle("Patient Login");
		    primaryStage.setScene(scene);
		    primaryStage.show();
		    
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void loadScheduleAppointments() {
		try {
		    FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/ui/schedulingScene.fxml"));
		    SchedulingView controller = new SchedulingView(user);
		    loader.setController(controller);
		    
		    Parent root = loader.load();
		    Scene scene = new Scene(root, 1280, 720);
		
		    primaryStage.setTitle("Patient Login");
		    primaryStage.setScene(scene);
		    primaryStage.show();
		    
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
