package vue;

import modele.Intervenant;
import controleur.Controleur;

import java.util.Map;
import java.util.HashMap;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.layout.VBox;

import javax.swing.Action;

import org.w3c.dom.events.MouseEvent;

import javafx.scene.control.TableColumn;

import javafx.event.ActionEvent;
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

public class ControleurIHM implements Initializable, EventHandler<ActionEvent> {

	@FXML
	private AnchorPane centerPaneAccueil;
	@FXML
	private ChoiceBox<String> choiceBoxAnnee;
	private Stage stage;
	private Scene scene;
	private Parent root;

	private TableView<IntervenantIHM> tableViewIntervenant;

	private Controleur ctrl;

	private Button btnParamIntervenants;

	@FXML
	private ImageView imageAccueil;

	@FXML
	private ImageView imageIntervenant;

	@FXML
	private ImageView imageModule;

	public void initialize(URL url, ResourceBundle rb) {
		this.ctrl = Controleur.getInstance(this);

		this.choiceBoxAnnee.setOnAction(this);
		this.majListAnnee();

		this.setAnnee(this.ctrl.getModele().getHmAnnee().get(this.ctrl.getModele().getIdAnnee()));

		this.imageAccueil.setImage(new Image(ResourceManager.HOUSE.toExternalForm()));
		this.imageIntervenant.setImage(new Image(ResourceManager.INTERVENANT.toExternalForm()));
		this.imageModule.setImage(new Image(ResourceManager.MODULE.toExternalForm()));

		this.tableViewIntervenant = new TableView<>();
		this.tableViewIntervenant.setEditable(true);
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

	public void majListAnnee() {
		this.choiceBoxAnnee.getItems().clear();
		Map<Integer, String> hmAnnee = this.ctrl.getModele().getHmAnnee();
		for (String s : hmAnnee.values()) {
			this.choiceBoxAnnee.getItems().add(s);
		}
	}

	public void setAnnee(String annee) {
		this.choiceBoxAnnee.setValue(annee);
	}

	@FXML
	void allerIntervenants(ActionEvent event) {

		// Reset la center Pane et ajouter le CSS
		this.centerPaneAccueil.getChildren().clear();
		this.centerPaneAccueil.getStylesheets().add(ResourceManager.STYLESHEET.toExternalForm());

		// Creer le tableau

		String[] colonnes = new String[] { "Infos", "Prenom", "Nom", "Categorie", "Email", "Supprimer" };

		if (this.tableViewIntervenant.getColumns().size() < 6) {
			for (String colonne : colonnes) {
				TableColumn<IntervenantIHM, String> tbcl = new TableColumn<>(colonne);
				tbcl.setCellValueFactory(new PropertyValueFactory<>(colonne.toLowerCase()));
				this.tableViewIntervenant.getColumns().add(tbcl);
			}
		}

		majTableIntervenant();

		this.tableViewIntervenant.setPrefHeight(500);
		this.tableViewIntervenant.setPrefWidth(670);

		AnchorPane.setTopAnchor(this.tableViewIntervenant, 20.0);
		AnchorPane.setLeftAnchor(this.tableViewIntervenant, 20.0);

		this.btnParamIntervenants = new Button("Paramètrer les Intervenants");
		this.btnParamIntervenants.setStyle("-fx-background-radius: 100");

		this.btnParamIntervenants.setPrefSize(250, 40);
		this.btnParamIntervenants.setId("btnParamIntervenants");
		this.btnParamIntervenants.setOnAction(this);

		AnchorPane.setTopAnchor(this.btnParamIntervenants, 580.0);
		AnchorPane.setLeftAnchor(this.btnParamIntervenants, 20.0);

		Button btnParamCategorie = new Button("Paramètrer une catégorie");
		btnParamCategorie.setStyle("-fx-background-radius: 100");

		btnParamCategorie.setPrefSize(250, 40);

		AnchorPane.setTopAnchor(btnParamCategorie, 580.0);
		AnchorPane.setLeftAnchor(btnParamCategorie, 400.0);

		this.centerPaneAccueil.getChildren().add(this.tableViewIntervenant);
		this.centerPaneAccueil.getChildren().add(btnParamIntervenants);
		this.centerPaneAccueil.getChildren().add(btnParamCategorie);

		/*
		 * Faudra ajouter les supButton et infoButton dans une List ou les stocker
		 * quelque part pour avoir leurs ids
		 * 
		 * EventHandler<ActionEvent> handlerSupprimer = new EventHandler<ActionEvent>()
		 * {
		 * 
		 * @Override
		 * public void handle(ActionEvent event) {
		 * if (event.getSource() == ) {
		 * }
		 * }
		 * };
		 */

	}

	public void popupParamIntervenant() {
		Stage popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.setTitle("Pop-up");
		Text pnom = new Text("Prénom  ");
		TextField prenom = new TextField();
		prenom.setMaxWidth(30);

		Text nom = new Text("Nom  ");
		TextField tnom = new TextField();

		Text email = new Text("Email ");
		TextField temail = new TextField();

		Text categorie = new Text("Catégorie ");
		ChoiceBox<String> choiceBoxCategorie = new ChoiceBox<>();

		Text hMinText = new Text("Heures Minimales ");
		TextField hMin = new TextField();

		Text hMaxText = new Text("Heures Maximales ");
		TextField hMax = new TextField();

		VBox vbox = new VBox(5);
		vbox.setMaxSize(200, 400);

		BorderPane borderPane = new BorderPane();
		borderPane.setTop(pnom);
		borderPane.setCenter(tnom);
		borderPane.setPadding(new Insets(10));

		BorderPane borderPane2 = new BorderPane();
		borderPane2.setTop(nom);
		borderPane2.setCenter(tnom);
		borderPane2.setPadding(new Insets(10));

		BorderPane borderPane3 = new BorderPane();
		borderPane3.setTop(email);
		borderPane3.setCenter(temail);
		StackPane popupLayout = new StackPane();
		// popupLayout.getChildren().add(closeButton);
		vbox.getChildren().add(borderPane);
		vbox.getChildren().add(borderPane2);
		vbox.getChildren().add(borderPane3);
		popupLayout.getChildren().add(vbox);
		Scene popupScene = new Scene(popupLayout, 200, 400);
		popupStage.setScene(popupScene);

		popupStage.showAndWait();
	}

	public void majTableIntervenant() {
		Map<Integer, Intervenant> hmInter = this.ctrl.getModele().getHmIntervenants();
		ObservableList<IntervenantIHM> lst = FXCollections.observableArrayList();

		for (Intervenant i : hmInter.values()) {
			Button infoButton = getInfoButton();
			infoButton.setId("Info-" + i.getId());
			Button supButton = getSupButton();
			supButton.setId("Sup-" + i.getId());

			infoButton.setOnAction(this);
			supButton.setOnAction(this);
			lst.add(new IntervenantIHM(infoButton, i.getPrenom(), i.getNom(),
					this.ctrl.getModele().getNomCateg(i.getIdCategorie()), i.getEmail(), supButton));
		}

		this.tableViewIntervenant.setItems(lst);
	}

	public Button getInfoButton() {
		SVGPath infosvg = new SVGPath();
		// infosvg.setContent(ResourceManager.getData("book"));
		infosvg.setContent(
				"M11.8626 4.02794C9.87308 2.05641 7.29356 0.967859 4.62087 0.971936C3.35115 0.971936 2.13213 1.21194 1 1.6546V20.6546C2.16305 20.2013 3.38756 19.9704 4.62087 19.9719C7.40291 19.9719 9.94114 21.1279 11.8626 23.0279M11.8626 4.02794C13.8521 2.0563 16.4317 0.967733 19.1044 0.971936C20.3741 0.971936 21.5931 1.21194 22.7252 1.6546V20.6546C21.5622 20.2013 20.3377 19.9704 19.1044 19.9719C16.4317 19.9679 13.8522 21.0564 11.8626 23.0279M11.8626 4.02794V23.0279");
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
	void parametrerCategories(ActionEvent event) {

	}

	@FXML
	void ajouterAnnee() {
		this.ctrl.getModele().ajouterAnnee();
	}

	public void handle(ActionEvent event) {
		if (event.getSource() == this.btnParamIntervenants) {
			this.popupParamIntervenant();
		} else if (event.getSource() instanceof Button) {
			Button button = (Button) event.getSource();
			String[] textButton = button.getId().split("-");
			if (textButton[0].equals("Info")) {
				System.out.println("Info : " + textButton[1]);
			} else if (textButton[0].equals("Sup")) {
				this.ctrl.getModele().supprimerIntervenant(Integer.parseInt(textButton[1]));
			}
		} else if (event.getSource() == this.choiceBoxAnnee) {
			this.ctrl.getModele().updateAnnee(this.choiceBoxAnnee.getValue());
			this.setAnnee(this.choiceBoxAnnee.getValue());
		}
	}
}
