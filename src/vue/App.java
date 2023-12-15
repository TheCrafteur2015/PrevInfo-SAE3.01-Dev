package vue;
import org.controlsfx.control.ToggleSwitch;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

public class App extends Application /* implements EventHandler<Event> */ {

	@Override
	public void start(Stage stage) throws Exception {
//		System.out.println(ResourceManager.ACCUEIL);
		Parent root = FXMLLoader.load(ResourceManager.ACCUEIL);
		stage.setTitle("PrevInfo");

		Scene scene = new Scene(root);
		scene.getStylesheets().add(ResourceManager.STYLESHEET.toExternalForm());
		
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		try {
			Application.launch(args);
		} catch (RuntimeException e) {
			System.err.println("Exception : " + e.getMessage());
			e.printStackTrace();
		}
	}

}
