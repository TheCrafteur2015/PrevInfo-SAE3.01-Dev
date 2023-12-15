package vue;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.controlsfx.control.ToggleSwitch;

import controleur.Controleur;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import modele.TypeCours;

public class ControleurIHM implements Initializable, EventHandler<Event> , ChangeListener<String>{

	@FXML
	private AnchorPane centerPaneAccueil;
	@FXML
	private ChoiceBox<String> choiceBoxAnnee;
	private Stage stage;
	private Scene scene;
	private Parent root;

	private FrameIntervenant frameIntervenant;
	@SuppressWarnings("unused")
	private FrameModule frameModule;

	private Controleur ctrl;

	@SuppressWarnings("unused")
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

		this.imageAccueil.setImage(new Image(ResourceManager.HOUSE.toExternalForm()));
		this.imageIntervenant.setImage(new Image(ResourceManager.INTERVENANT.toExternalForm()));
		this.imageModule.setImage(new Image(ResourceManager.MODULE.toExternalForm()));
		this.imageDownload.setImage(new Image(ResourceManager.DOWNLOAD.toExternalForm()));
		
//		System.out.println(new Button("").getTypeSelector().toLowerCase());
//		System.out.println(new ToggleButton("").getTypeSelector().toLowerCase());
//		System.out.println(new ChoiceBox<>().getTypeSelector().toLowerCase());
	}

	@FXML
	void allerAccueil(ActionEvent event) throws IOException {
		this.root = FXMLLoader.load(ResourceManager.ACCUEIL);
		this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		this.scene = new Scene(this.root);
		scene.getStylesheets().add(ResourceManager.STYLESHEET.toExternalForm());

		this.stage.setScene(this.scene);

		stage.show();
		
		btnOnClick(event.getSource());
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
		System.out.println("izdaziu");
		Stage popupStage = FXHelper.createPopup("Modification des multiplicateurs", 350, 300, false);
		
		Map<Integer, TypeCours> hmTypeCours = this.ctrl.getModele().getHmTypeCours();

		this.alText = new ArrayList<>();
		this.alTextField = new ArrayList<>();
		Text textTmp;
		TextField textFieldTmp;
		for (TypeCours tc : hmTypeCours.values()) {
			textTmp = new Text(tc.getNom() + " :");
			textFieldTmp = new TextField(String.valueOf(tc.getCoefficient()));
			FXHelper.addClass(textFieldTmp, "coeffValue");
			textFieldTmp.setMaxWidth(7 * 7);
			textFieldTmp.textProperty().addListener(this);
			
			this.alText.add(textTmp);
			this.alTextField.add(textFieldTmp);
		}

		VBox vbox = new VBox(5);
		vbox.setMaxSize(200, this.alText.size() * 50);
//		vbox.setAlignment(Pos.CENTER);

		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		for (int i = 0; i < this.alText.size(); i++) {
			gridPane.add(this.alText.get(i), 0, i);
			gridPane.add(this.alTextField.get(i), 1, i);
		}
		gridPane.setPadding(new Insets(10));
		gridPane.setHgap(10);
		gridPane.setVgap(10);

		this.btnConfirmerMultiplicateur = new Button("Confirmer");
		FXHelper.addClass(this.btnConfirmerMultiplicateur, "confirmBtn");
		this.btnConfirmerMultiplicateur.addEventHandler(ActionEvent.ACTION, this);
		StackPane popupLayout = new StackPane();
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(this.btnConfirmerMultiplicateur);
		
		
		FXHelper.append(vbox, gridPane);
		FXHelper.append(vbox, borderPane);
		FXHelper.append(popupLayout, vbox);
		Scene popupScene = new Scene(popupLayout, 200, this.alText.size() * 50);
		FXHelper.addStylesheet(popupScene, ResourceManager.STYLESHEET_POPUP);
		
		popupStage.setScene(popupScene);
		popupStage.showAndWait();
	}

	@FXML
	void modeDuplication(MouseEvent event) {
		// this.ctrl.getModele().setDuplication(!this.ctrl.getModele().isDuplication());
		this.ctrl.getModele().setDuplication(((ToggleSwitch) event.getSource()).isSelected());
	}

	@FXML
	void allerIntervenants(ActionEvent event) {
		this.frameIntervenant = new FrameIntervenant(this.ctrl, this.centerPaneAccueil);
		btnOnClick(event.getSource());
	}

	public FrameIntervenant getFrameIntervenant() {
		return this.frameIntervenant;
	}

	@FXML
	void allerModules(ActionEvent event) {
		this.frameModule = new FrameModule(this.ctrl, this.centerPaneAccueil);
		btnOnClick(event.getSource());
	}

	@FXML
	void allerExporter(ActionEvent event) {
		btnOnClick(event.getSource());
	}

	@FXML
	void parametrerCategories(ActionEvent event) {

	}

	@FXML
	void ajouterAnnee() {
		this.ctrl.getModele().ajouterAnnee();
		this.majListAnnee();
	}
	
	void btnOnClick(Object source) {
		if (source instanceof Button b) {
			System.out.println(b.getText());
			System.out.println("Height: " + b.getHeight());
			System.out.println("Width: " + b.getWidth());
			System.out.println("-".repeat(20));
		}
	}

	public void handle(Event event) {
		
		if (event instanceof ActionEvent action) {
			if (action.getSource() == this.choiceBoxAnnee) {
				this.ctrl.getModele().updateAnnee(this.choiceBoxAnnee.getValue());
				this.setAnnee(this.choiceBoxAnnee.getValue());
			} else if (action.getSource() == this.btnConfirmerMultiplicateur) {
				System.out.println("coucou");
				Double coeff;
				for (int i = 0; i < this.alText.size(); i++) {
					coeff = null;
					try {
						coeff = Double.parseDouble(alTextField.get(i).getText());
						this.ctrl.getModele().updateTypeCoursBrut(alText.get(i).getText(),coeff);
						((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
					} catch (Exception e) { System.out.println("Le coefficient n'est pas un nombre ! "); }
				}
			}
		}
	}

	@Override
	public void changed(ObservableValue<? extends String> observable, String oldString, String newString) {

		System.out.println("cc");
		for (TextField text : alTextField) {
			if (observable == text.textProperty()) {
				if (!(text.getText().matches("[0-9 .]*")))
					text.setText("");
			}
		}
		
	}

}
