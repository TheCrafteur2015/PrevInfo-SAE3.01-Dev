package vue;

import modele.Intervenant;
import controleur.Controleur;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.TableColumn;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
		this.centerPaneAccueil.getChildren().clear();
		this.centerPaneAccueil.getStylesheets().add(ResourceManager.STYLESHEET.toExternalForm());
		TableView<IntervenantIHM> tbV = new TableView<>();
		tbV.setEditable(true);

		TableColumn<IntervenantIHM, String> tbInfo = new TableColumn("Infos");
		TableColumn<IntervenantIHM, String> tbPrenom = new TableColumn("Prenom");
		TableColumn<IntervenantIHM, String> tbNom = new TableColumn("Nom");
		TableColumn<IntervenantIHM, String> tbCateg = new TableColumn("Categorie");
		TableColumn<IntervenantIHM, String> tbEmail = new TableColumn("Email");
		TableColumn<IntervenantIHM, String> tbSupp = new TableColumn("Supprimer");

		tbInfo.setCellValueFactory(new PropertyValueFactory<>("infos"));
		tbPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
		tbNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
		tbCateg.setCellValueFactory(new PropertyValueFactory<>("categorie"));
		tbEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tbSupp.setCellValueFactory(new PropertyValueFactory<>("supprimer"));

		tbV.getColumns().addAll(tbInfo, tbPrenom, tbNom, tbCateg, tbEmail, tbSupp);

		ObservableList<IntervenantIHM> lst = FXCollections.observableArrayList(
				new IntervenantIHM(null, "Arthur", "Lecomte", "Chomeur", "arthur.lecomte@etu.univ-lehavre.fr", null));

		tbV.setItems(lst);

		tbV.setPrefSize(400, 250);

		AnchorPane.setTopAnchor(tbV, 20.0);
		AnchorPane.setLeftAnchor(tbV, 20.0);

		Button btnParamIntervenants = new Button("Paramètrer les Intervenants");
		btnParamIntervenants.setStyle("-fx-background-radius: 100");

		AnchorPane.setTopAnchor(btnParamIntervenants, 300.0);
		AnchorPane.setLeftAnchor(btnParamIntervenants, 20.0);

		Button btnParamCategorie = new Button("Paramètrer une catégorie");
		btnParamCategorie.setStyle("-fx-background-radius: 100");

		AnchorPane.setTopAnchor(btnParamCategorie, 300.0);
		AnchorPane.setLeftAnchor(btnParamCategorie, 250.0);

		this.centerPaneAccueil.getChildren().add(tbV);
		this.centerPaneAccueil.getChildren().add(btnParamIntervenants);
		this.centerPaneAccueil.getChildren().add(btnParamCategorie);

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

}
