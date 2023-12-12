package vue;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;

public class ControlleurIHM /* implements Initializable */ {

    @FXML
    private AnchorPane centerPaneAccueil;
	private Stage stage;
	private Scene scene;
	private Parent root;

	public ControlleurIHM(String[] args)
	{
		App.main(args);
	}


	@FXML
    void allerAccueil(ActionEvent event) throws IOException {
		this.root =  FXMLLoader.load(getClass().getResource("lib\\Accueil.fxml"));
		this.stage = (Stage)((Node) event.getSource()).getScene().getWindow(); 
		
		this.scene = new Scene(this.root); 
		scene.getStylesheets().add(this.getClass().getResource("application.css").toExternalForm()); 
		
		this.stage.setScene(this.scene); 
		stage.show();
    }

	@FXML
	void allerIntervenants(ActionEvent event){

	}

	@FXML
	void allerModules(ActionEvent event){

	}

	@FXML
	void allerExporter(ActionEvent event){

	}

	
}
