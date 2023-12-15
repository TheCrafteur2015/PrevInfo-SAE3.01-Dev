package vue;

import controleur.Controleur;

import modele.Categorie;
import modele.Intervenant;
import modele.TypeCours;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import java.io.IOException;
import java.net.URL;

import javax.swing.Action;

import javafx.fxml.*;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.Parent;

import javafx.scene.paint.Color;

import javafx.scene.shape.SVGPath;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

import javafx.scene.control.*;
/*
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableVi.button:hover, .togglebutton:hover, .choicebox:hover {
	-fx-cursor: hand;
}ew;
import javafx.scene.control.TextField;
*/

import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.MouseEvent;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.*;

import javafx.scene.text.Text;

import javafx.application.Application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.stage.Stage;
import javafx.stage.Popup;
import javafx.stage.Modality;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.Event;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class ControleurIHM implements Initializable, EventHandler<Event>, ChangeListener<String> {

	@FXML
	private AnchorPane centerPaneAccueil;
	@FXML
	private ChoiceBox<String> choiceBoxAnnee;
	private Stage stage;
	private Scene scene;
	private Parent root;

	private FrameIntervenant frameIntervenant;
	private FrameModule frameModule;
	private FrameExporter frameExporter;

	private Controleur ctrl;

	private Button btnConfirmerIntervenant;

	@FXML
	private ImageView imageAccueil;

	@FXML
	private ImageView imageIntervenant;

	@FXML
	private ImageView imageModule;

	@FXML
	private ImageView imageDownload;

	private Button btnConfirmerMultiplicateur;

	private List<Text> alText;
	private List<TextField> alTextField;

	public void initialize(URL url, ResourceBundle rb) {
		this.ctrl = Controleur.getInstance(this);

		this.choiceBoxAnnee.addEventHandler(ActionEvent.ACTION, this);
		this.majListAnnee();

		this.setAnnee(this.ctrl.getModele().getHmAnnee().get(this.ctrl.getModele().getIdAnnee()));

		// this.imageAccueil.setImage(new Image(ResourceManager.HOUSE.toExternalForm()));
		// this.imageAccueil.setImage(new Image(this.getClass().getResourceAsStream("/images/accueil_icone.png")));
		// this.imageIntervenant.setImage(new Image(ResourceManager.INTERVENANT.toExternalForm()));
		// this.imageModule.setImage(new Image(ResourceManager.MODULE.toExternalForm()));
		// this.imageDownload.setImage(new Image(ResourceManager.DOWNLOAD.toExternalForm()));

		// FXHelper.append(this.btnAccueil, new ImageView(new Image(ResourceManager.HOUSE.toExternalForm())));
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
	void parametrerMultiplicateurs() {

		Stage popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.centerOnScreen();
		popupStage.setTitle("Modification des multiplicateurs");
		popupStage.setHeight(350);
		popupStage.setWidth(300);
		popupStage.setResizable(false);

		Map<Integer, TypeCours> hmTypeCours = this.ctrl.getModele().getHmTypeCours();

		this.alText = new ArrayList<>();
		this.alTextField = new ArrayList<>();
		Text textTmp;
		TextField textFieldTmp;
		for (TypeCours tc : hmTypeCours.values()) {
			textTmp = new Text(tc.getNom());
			textFieldTmp = new TextField(tc.getCoefficient() + "");
			textFieldTmp.getStyleClass().add("coeffValue");
			textFieldTmp.setMaxWidth(7 * 7);
			textFieldTmp.textProperty().addListener(this);
			alText.add(textTmp);

			this.alTextField.add(textFieldTmp);
		}

		VBox vbox = new VBox(5);
		vbox.setMaxSize(200, alText.size() * 50);

		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		for (int i = 0; i < alText.size(); i++) {
			gridPane.add(alText.get(i), 0, i);
			gridPane.add(alTextField.get(i), 1, i);
		}
		gridPane.setPadding(new Insets(10));
		gridPane.setHgap(10);
		gridPane.setVgap(10);

		this.btnConfirmerMultiplicateur = new Button("Confirmer");
		this.btnConfirmerMultiplicateur.getStyleClass().add("confirmBtn");
		this.btnConfirmerMultiplicateur.addEventHandler(ActionEvent.ACTION, this);
		StackPane popupLayout = new StackPane();
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(this.btnConfirmerMultiplicateur);

		vbox.getChildren().add(gridPane);
		vbox.getChildren().add(borderPane);
		popupLayout.getChildren().add(vbox);
		Scene popupScene = new Scene(popupLayout, 200, alText.size() * 50);
		popupScene.getStylesheets().add(ResourceManager.STYLESHEET_POPUP.toExternalForm());
		popupStage.setScene(popupScene);

		popupStage.showAndWait();
	}

	@FXML
	void modeDuplication() {
		this.ctrl.getModele().setDuplication(!this.ctrl.getModele().isDuplication());
	}

	@FXML
	void allerIntervenants(ActionEvent event) {
		modeDuplication();
		this.frameIntervenant = new FrameIntervenant(this.ctrl, this.centerPaneAccueil);
	}

	public FrameIntervenant getFrameIntervenant() {
		return this.frameIntervenant;
	}

	@FXML
	void allerModules(ActionEvent event) {
		modeDuplication();
		this.frameModule = new FrameModule(this.ctrl, this.centerPaneAccueil);
	}

	@FXML
	void allerExporter(ActionEvent event) {
		modeDuplication();
		this.frameExporter = new FrameExporter(this.ctrl, this.centerPaneAccueil);
	}

	@FXML
	void parametrerCategories(ActionEvent event) {

	}

	@FXML
	void ajouterAnnee() {
		this.ctrl.getModele().ajouterAnnee();
		this.majListAnnee();
		this.setAnnee(this.ctrl.getModele().getHmAnnee().get(this.ctrl.getModele().getIdAnnee()));
	}


	public void handle(Event event) {

		if (event instanceof ActionEvent action) {
			if (action.getSource() == this.choiceBoxAnnee) {
				this.ctrl.getModele().updateAnnee(this.choiceBoxAnnee.getValue());
				this.setAnnee(this.choiceBoxAnnee.getValue());
			} else if (action.getSource() == this.btnConfirmerMultiplicateur) {

				Double coeff;
				boolean isEmpty = false;
				for (int i = 0; i < this.alText.size(); i++) {
					coeff = null;
					if (!this.alTextField.get(i).getText().isEmpty()) {
						coeff = Double.parseDouble(alTextField.get(i).getText());
						this.ctrl.getModele().updateTypeCoursBrut(alText.get(i).getText(), coeff);
					} else {
						isEmpty = true;
					}

				}
				if (!isEmpty) {
					((Stage) this.btnConfirmerMultiplicateur.getScene().getWindow()).close();
				}
				else{
					System.out.println("Un champ est libre");
	}
			}
		}
	}

	@Override
	public void changed(ObservableValue<? extends String> observable, String oldString, String newString) {

		for (TextField text : alTextField) {
			if (observable == text.textProperty()) {
				if (!(text.getText().matches("[0-9 .]*")))
					text.setText("");
			}
		}

	}

}
