package vue;

import modele.Intervenant;
import modele.*;

import controleur.Controleur;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.layout.VBox;

import javax.swing.Action;

import javafx.scene.control.TableColumn;
import java.util.*;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.*;
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

public class FrameModule implements EventHandler<Event> {
	private Controleur ctrl;
	private AnchorPane centerPaneAccueil;

	private Button btnAjouterSemestre;
	private VBox vbox;

	private List lstTxtFieldGrps;

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

		this.vbox = new VBox(10);
		this.vbox.setAlignment(Pos.CENTER);
		this.vbox.getChildren().add(btnAjouterSemestre);

		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(vbox);

		AnchorPane.setTopAnchor (borderPane,20.0);
		AnchorPane.setLeftAnchor(borderPane,20.0);

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(borderPane);
		
		this.centerPaneAccueil.getChildren().add(scrollPane);

		this.btnAjouterSemestre.addEventHandler(ActionEvent.ACTION, this);
	}

	public void creerSemestre() {
		BorderPane borderPaneSemestre = new BorderPane();
		FlowPane flowPaneTitreSemestre = new FlowPane();

		List<TextField> lsttxtFields = new ArrayList<TextField>();
		TextField txtFTD = new TextField();
		txtFTD.setMaxWidth(5*7);
		TextField txtFTP = new TextField();
		txtFTP.setMaxWidth(5*7);
		TextField txtFCM = new TextField();
		txtFCM.setMaxWidth(5*7);		
		TextField txtFAutre = new TextField();
		txtFAutre.setMaxWidth(5*7);
	
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
		if (action.getSource() == this.btnAjouterSemestre) {
			creerSemestre();
		}
	}
}
