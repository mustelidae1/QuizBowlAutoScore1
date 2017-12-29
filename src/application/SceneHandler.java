package application;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * Handler for scene changes and popups. 
 */
public class SceneHandler {

	/*
	 * Changes the scene
	 */
	public static void changeScene(String scenePath, Stage curStage) throws IOException {
		//Parent root = FXMLLoader.load(getClass().getResource(scenePath));
		//Scene newScene = new Scene(root); 
		
		//curStage.setScene(newScene);
		//curStage.show(); 
	}
	
	public static void popup(String filePath) {
		
	}
	
}
