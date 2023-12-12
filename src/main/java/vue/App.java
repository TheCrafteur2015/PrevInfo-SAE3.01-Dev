package vue;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application /* implements EventHandler<Event> */ {

	@Override
	public void start(Stage stage) throws Exception {
		URL url = getClass().getResource("Accueil.fxml");
		System.out.println(url);
		Parent root = FXMLLoader.load(url);
		stage.setTitle("PrevInfo");

		Scene scene = new Scene(root);
		scene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());

		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
