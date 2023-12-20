package vue;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(ResourceManager.ACCUEIL);
		stage.setTitle("PrevInfo");
		
		stage.getIcons().add(new Image("/images/LogoNUMIT.png"));

		Scene scene = new Scene(root);
		scene.getStylesheets().add(ResourceManager.STYLESHEET.toExternalForm());
		
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
