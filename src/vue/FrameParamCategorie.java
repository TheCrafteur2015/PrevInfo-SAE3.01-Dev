package vue;

/**
 * FrameParamCategorie
 */
import modele.Intervenant;
import modele.Categorie;

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
import javafx.beans.value.*;

public class FrameParamCategorie {
	private Controleur ctrl;
	private AnchorPane centerPaneAccueil;
	private TextField nomCategorie;
	private TextField tfHeureMin;
	private TextField tfHeureMax;
	private Map<Integer, Categorie> hmCategorie;

	

	public FrameParamCategorie(Controleur ctrl, AnchorPane centerPaneAccueil) {
		this.ctrl = ctrl;
		this.centerPaneAccueil = centerPaneAccueil;
		this.init();
		this.hmCategorie = this.ctrl.getModele().getHmCategories();

	}

	public void init() {
		Stage popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.setTitle("Pop-up");

        ObservableList<CategorieIHM> olCategorie = FXCollections.observableArrayList();

        for (Categorie c : this.hmCategorie.values()) {
			Button btnSup = new Button();
            btnSup.setId(c.getId()+"");
            olCategorie.add(new CategorieIHM(c.getNom(),btnSup));
		}

        TableColumn<CategorieIHM, String> tcNomCategorie = new TableColumn<>("");
        tcNomCategorie.setCellValueFactory(new PropertyValueFactory<>("nomC"));

        TableColumn<CategorieIHM, Button> tcBtnSup = new TableColumn<>("");
        tcNomCategorie.setCellValueFactory(new PropertyValueFactory<>("btnSup"));

        TableView tableView = new TableView<>();
        tableView.setItems(olCategorie);
      
		VBox vbox = new VBox(5);
		Text nomC = new Text("Nom de la catégorie");
		this.nomCategorie = new TextField();

		Text tHeureMin       = new Text("Heures minimales: ");
		 this.tfHeureMin     = new TextField();

		Text tHeureMax       = new Text("Heure maximales: ");
		this.tfHeureMax      = new TextField();

	
		StackPane popupLayout = new StackPane();

		popupLayout.getChildren().add(vbox);
		Scene popupScene = new Scene(popupLayout, 200, 400);
		popupStage.setScene(popupScene);

		popupStage.showAndWait();
	}


}
