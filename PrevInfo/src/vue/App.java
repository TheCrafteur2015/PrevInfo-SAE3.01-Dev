package vue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(this.getClass().getResource("lib\\Accueil.fxml"));
		stage.setTitle("PrevInfo");

		Scene scene = new Scene(root);
		scene.getStylesheets().add(this.getClass().getResource("lib\\style.css").toExternalForm());

		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		try {
			Application.launch((Class<? extends Application>) Class.forName(args[0]));
		} catch (ClassNotFoundException e) {}
	}

}
