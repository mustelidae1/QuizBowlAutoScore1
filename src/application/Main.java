package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try { 
			
			FXMLLoader loader =  new FXMLLoader(getClass().getResource("/GUI/RootMenu.fxml"));
	    	
	        Parent root = loader.load();
	        Controller controller = loader.getController(); 
	    	controller.setStats(new Tournament_Info());
	        
			Scene scene = new Scene(root,900,600);
			// Will implement CSS later. -- OT
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Quiz Bowl Auto Score");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
