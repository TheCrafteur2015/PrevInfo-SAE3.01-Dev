package vue;

import modele.Intervenant;
import controleur.Controleur;

import java.util.Map;
import java.util.HashMap;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.TableColumn;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
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

public class ControleurIHM implements Initializable {

	@FXML
	private AnchorPane centerPaneAccueil;
	@FXML
	private ChoiceBox<String> choiceBoxAnnee;
	private Stage stage;
	private Scene scene;
	private Parent root;

	private Controleur ctrl;

	public void initialize(URL url, ResourceBundle rb) {
		this.ctrl = new Controleur(this);
		this.choiceBoxAnnee.getItems().add(null);// Faut ajouter les années deja crée ici
	}

	@FXML
	void allerAccueil(ActionEvent event) throws IOException {
		this.root = FXMLLoader.load(ResourceManager.ACCUEIL);
		this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		this.scene = new Scene(this.root);
		scene.getStylesheets().add(ResourceManager.STYLESHEET.toExternalForm());

		this.stage.setScene(this.scene);
		stage.show();
	}

	@FXML
	void allerIntervenants(ActionEvent event) {
		//Reset la center Pane et ajouter le CSS
		this.centerPaneAccueil.getChildren().clear();
		this.centerPaneAccueil.getStylesheets().add(ResourceManager.STYLESHEET.toExternalForm());

		//Creer le tableau
		TableView<IntervenantIHM> tbV = new TableView<>();
		tbV.setEditable(true);

		String[] colonnes = new String[] { "Infos", "Prenom", "Nom", "Categorie", "Email", "Supprimer" };

		for (String colonne : colonnes) {
			TableColumn<IntervenantIHM, String> tbcl = new TableColumn(colonne);
			tbcl.setCellValueFactory(new PropertyValueFactory<>(colonne.toLowerCase()));
			tbV.getColumns().add(tbcl);
		}

		Map<Integer, Intervenant> hmInter = this.ctrl.getModele().getHmIntervenants();
		ObservableList<IntervenantIHM> lst = FXCollections.observableArrayList();

		for (Intervenant i : hmInter.values()) {
			Button infoButton = getInfoButton();
			infoButton.setId(i.getId() + "");
			Button supButton = getSupButton();
			supButton.setId(i.getId() + "");
			lst.add(new IntervenantIHM(infoButton, i.getPrenom(), i.getNom(),
					this.ctrl.getModele().getNomCateg(i.getIdCategorie()), i.getEmail(), supButton));
		}

		tbV.setItems(lst);

		tbV.setPrefHeight(500);
		tbV.setPrefWidth(667);

		AnchorPane.setTopAnchor(tbV, 20.0);
		AnchorPane.setLeftAnchor(tbV, 20.0);

		Button btnParamIntervenants = new Button("Paramètrer les Intervenants");
		btnParamIntervenants.setStyle("-fx-background-radius: 100");

		btnParamIntervenants.setPrefSize(250, 40);

		AnchorPane.setTopAnchor(btnParamIntervenants, 580.0);
		AnchorPane.setLeftAnchor(btnParamIntervenants, 20.0);

		Button btnParamCategorie = new Button("Paramètrer une catégorie");
		btnParamCategorie.setStyle("-fx-background-radius: 100");

		btnParamCategorie.setPrefSize(250, 40);

		AnchorPane.setTopAnchor(btnParamCategorie, 580.0);
		AnchorPane.setLeftAnchor(btnParamCategorie, 400.0);

		this.centerPaneAccueil.getChildren().add(tbV);
		this.centerPaneAccueil.getChildren().add(btnParamIntervenants);
		this.centerPaneAccueil.getChildren().add(btnParamCategorie);

		/*
		Faudra ajouter les supButton et infoButton dans une List ou les stocker quelque part pour avoir leurs ids
		
		EventHandler<ActionEvent> handlerSupprimer = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (event.getSource() == ) {
				}
			}
		};*/

	}

	public Button getInfoButton() {
		SVGPath infosvg = new SVGPath();
		infosvg.setContent(ResourceManager.getData("book"));
		// infosvg.setContent(
		// "M11.8626 4.02794C9.87308 2.05641 7.29356 0.967859 4.62087 0.971936C3.35115
		// 0.971936 2.13213 1.21194 1 1.6546V20.6546C2.16305 20.2013 3.38756 19.9704
		// 4.62087 19.9719C7.40291 19.9719 9.94114 21.1279 11.8626 23.0279M11.8626
		// 4.02794C13.8521 2.0563 16.4317 0.967733 19.1044 0.971936C20.3741 0.971936
		// 21.5931 1.21194 22.7252 1.6546V20.6546C21.5622 20.2013 20.3377 19.9704
		// 19.1044 19.9719C16.4317 19.9679 13.8522 21.0564 11.8626 23.0279M11.8626
		// 4.02794V23.0279");
		infosvg.setStroke(Color.BLACK);
		infosvg.setFill(Color.WHITE);
		infosvg.setStrokeWidth(1.5);
		infosvg.setStrokeLineCap(StrokeLineCap.ROUND);
		infosvg.setStrokeLineJoin(StrokeLineJoin.ROUND);

		Button infobtn = new Button();
		infobtn.setGraphic(infosvg);
		infobtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		infobtn.getStyleClass().add("info-btn");
		return infobtn;
	}

	public Button getSupButton() {
		SVGPath supsvg = new SVGPath();
		supsvg.setContent(
				"M13.7766 10.1543L13.3857 21.6924M7.97767 21.6924L7.58686 10.1543M18.8458 6.03901C19.2321 6.10567 19.6161 6.17618 20.0001 6.25182M18.8458 6.03901L17.6395 23.8373C17.5902 24.5619 17.3018 25.2387 16.8319 25.7324C16.362 26.2261 15.7452 26.5002 15.1049 26.5H6.25856C5.61824 26.5002 5.00145 26.2261 4.53152 25.7324C4.0616 25.2387 3.77318 24.5619 3.72395 23.8373L2.51764 6.03901M18.8458 6.03901C17.5422 5.81532 16.2318 5.64555 14.9174 5.53005M2.51764 6.03901C2.13135 6.10439 1.74731 6.1749 1.36328 6.25054M2.51764 6.03901C3.82124 5.81532 5.13158 5.64556 6.44606 5.53005M14.9174 5.53005V4.35572C14.9174 2.84294 13.8895 1.58144 12.5567 1.534C11.307 1.48867 10.0564 1.48867 8.80673 1.534C7.47391 1.58144 6.44606 2.84422 6.44606 4.35572V5.53005M14.9174 5.53005C12.0978 5.28272 9.26562 5.28272 6.44606 5.53005");
		supsvg.setStroke(Color.BLACK);
		supsvg.setFill(Color.WHITE);
		supsvg.setStrokeWidth(1.5);
		supsvg.setStrokeLineCap(StrokeLineCap.ROUND);
		supsvg.setStrokeLineJoin(StrokeLineJoin.ROUND);

		Button supbtn = new Button();
		supbtn.setGraphic(supsvg);
		supbtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		supbtn.getStyleClass().add("info-btn");
		return supbtn;
	}

	@FXML
	void allerModules(ActionEvent event) {

	}

	@FXML
	void allerExporter(ActionEvent event) {

	}

	@FXML
	void parametrerMultiplicateurs(ActionEvent event) {
		Popup popup = new Popup();
		popup.getContent().add(centerPaneAccueil);

		popup.setAutoHide(true);
	}

	@FXML
	void parametrerCategories(ActionEvent event)
	{

	}

}
