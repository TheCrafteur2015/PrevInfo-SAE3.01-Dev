package vue;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.Node;

public class ControleurIHM implements Initializable {

    @FXML
    private AnchorPane centerPaneAccueil;
	@FXML
    private ChoiceBox<String> choiceBoxAnnee;
	private Stage stage;
	private Scene scene;
	private Parent root;

	public void initialize(URL url, ResourceBundle rb) {
		this.choiceBoxAnnee.getItems().add(null);//Faut ajouter les années deja crée ici
	}

	@FXML
    void allerAccueil(ActionEvent event) throws IOException {
		this.root =  FXMLLoader.load(getClass().getResource("Accueil.fxml"));
		this.stage = (Stage)((Node) event.getSource()).getScene().getWindow(); 
		
		this.scene = new Scene(this.root); 
		scene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
		
		this.stage.setScene(this.scene); 
		stage.show();
    }
	
	@FXML
	void allerIntervenants(ActionEvent event)
	{
		Button btn = new Button("bonjour");
		this.centerPaneAccueil.getChildren().add(btn);
	}

	@FXML
	void allerModules(ActionEvent event){

	}

	@FXML
	void allerExporter(ActionEvent event){

	}

	@FXML
	void parametrerMultiplicateurs(ActionEvent event) 
	{
			Popup popup = new Popup();
			popup.getContent().add(centerPaneAccueil);

			popup.setAutoHide(true);
	}
	
}
