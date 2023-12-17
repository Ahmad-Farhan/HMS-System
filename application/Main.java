package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import users.Patient;

public class Main extends Application{
	@Override
	public void start(Stage primaryStage) {
		Navigation.self().setStage(primaryStage);
		Navigation.self().loadloginView();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
