package vue;

import modele.*;

import controleur.Controleur;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.*;

import javax.swing.Action;

import javafx.scene.control.TableColumn;

import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.control.ContentDisplay;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Popup;
import javafx.stage.Modality;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.beans.value.*;
import javafx.util.Callback;

public class FrameModule implements EventHandler<Event>, ChangeListener<String> {
	private Controleur ctrl;
	private AnchorPane centerPaneAccueil;

	private Button btnAjouterSemestre;
	private VBox vbox;

	private List<List<TextField>> lstTxtFieldGrps;

	public FrameModule(Controleur ctrl, AnchorPane centerPaneAccueil) {
		this.ctrl = ctrl;
		this.centerPaneAccueil = centerPaneAccueil;
		this.lstTxtFieldGrps = new ArrayList<>(); // Une ArrayList d'une ArrayList de TextField
		this.init();
	}

	public void init() {
		this.centerPaneAccueil.getChildren().clear();
		this.centerPaneAccueil.getStylesheets().add(ResourceManager.STYLESHEET.toExternalForm());

		this.btnAjouterSemestre = new Button("Ajouter un Semestre");
		this.btnAjouterSemestre.setStyle("-fx-background-radius: 100");

		//Créer le Vbox pour bien placer les composants
		this.vbox = new VBox(10);
		this.vbox.setAlignment(Pos.CENTER);
		this.vbox.getChildren().add(btnAjouterSemestre);

		//Mettre le vBox dans le content du scrollPane qui va prendre toute la page
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPrefSize(this.centerPaneAccueil.getHeight(), this.centerPaneAccueil.getWidth());
		scrollPane.setContent(vbox);

		AnchorPane.setTopAnchor (scrollPane, 20.0);
		AnchorPane.setLeftAnchor(scrollPane, 20.0);
		
		this.centerPaneAccueil.getChildren().add(scrollPane);

		this.btnAjouterSemestre.addEventHandler(ActionEvent.ACTION, this);
	}

	public void creerSemestre() {
		Semestre semestre = new Semestre(0,0,1,1,this.ctrl.getModele().getIdAnnee());
		//BorderPane avec un flowTitre en haut et le tableau au centre
		BorderPane borderPaneSemestre = new BorderPane();
		FlowPane flowPaneTitreSemestre = new FlowPane();

		//liste de textfields pour saisir les groupes du semestre
		List<TextField> lsttxtFields = new ArrayList<>();
		TextField txtFTD = new TextField();
		txtFTD.setMaxWidth(5 * 7);
		TextField txtFTP = new TextField();
		txtFTP.setMaxWidth(5 * 7);
		TextField txtFCM = new TextField();
		txtFCM.setMaxWidth(5 * 7);								
		TextField txtFAutre = new TextField();
		txtFAutre.setMaxWidth(5 * 7);

		Button exit = new Button();
		
	
		//Ajouter les txtFields et labels au flowPane
		flowPaneTitreSemestre.getChildren().add(new Label("Semestre "+ Semestre.NB_SEMESTRE+1));
		flowPaneTitreSemestre.getChildren().add(new Label("  "));
		flowPaneTitreSemestre.getChildren().add(new Label("TD : "));
		flowPaneTitreSemestre.getChildren().add(txtFTD);
		flowPaneTitreSemestre.getChildren().add(new Label("TP : "));
		flowPaneTitreSemestre.getChildren().add(txtFTP);
		flowPaneTitreSemestre.getChildren().add(new Label("CM : "));
		flowPaneTitreSemestre.getChildren().add(txtFCM);
		flowPaneTitreSemestre.getChildren().add(new Label("Autre : "));
		flowPaneTitreSemestre.getChildren().add(txtFAutre);
		
	
		//flowPaneTitreSemestre.getChildren().add();

		lsttxtFields.add(txtFTD);
		lsttxtFields.add(txtFTP);
		lsttxtFields.add(txtFCM);
		lsttxtFields.add(txtFAutre);
		this.lstTxtFieldGrps.add(lsttxtFields);
		borderPaneSemestre.setTop(flowPaneTitreSemestre);
		this.vbox.getChildren().add(this.vbox.getChildren().size() - 1, borderPaneSemestre);
	}

	public void handle(Event action) {
		if (action.getSource() == this.btnAjouterSemestre) 
			this.creerSemestre();
		
	}

	public void changed(ObservableValue<? extends String> observable, String oldStr, String newStr) {
		
	}
}
