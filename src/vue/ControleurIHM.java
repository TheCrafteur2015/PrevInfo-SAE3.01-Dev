package vue;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import modele.Modele;
import modele.TypeCours;

public class ControleurIHM implements Initializable, EventHandler<Event>, ChangeListener<String> {
	
	@FXML
	private AnchorPane centerPaneAccueil;
	@FXML
	private ChoiceBox<String> choiceBoxAnnee;
	
	@FXML
	private Button btnExporter;
	
	@FXML
	private Button btnIntervenant;
	
	@FXML
	private Button btnModule;
	
	@FXML
	private Button btnAccueil;
	
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	private FrameIntervenant frameIntervenant;
	
	private Controleur ctrl;
	
	//private Button btnConfirmerIntervenant;
	
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
	
	private Button btnOui;
	private Button btnAnnuler;
	
	public static boolean bIsValidate = false;
	private boolean bErreur;
	
	public void initialize(URL url, ResourceBundle rb) {
		this.ctrl = Controleur.getInstance(this);
		
		// System.out.println(Arrays.toString(this.centerPaneAccueil.getStylesheets()));
		
		this.majListAnnee();
		
		this.setAnnee(this.ctrl.getModele().getHmAnnee().get(this.ctrl.getModele().getIdAnnee()));
		this.choiceBoxAnnee.addEventHandler(ActionEvent.ACTION, this);
	}
	
	@FXML
	void allerAccueil(ActionEvent event) throws IOException {
		this.btnAccueil.getStyleClass().add("selected-btn");
		
		this.btnExporter.getStyleClass().remove("selected-btn");
		this.btnIntervenant.getStyleClass().remove("selected-btn");
		this.btnModule.getStyleClass().remove("selected-btn");
		
		this.root = FXMLLoader.load(ResourceManager.ACCUEIL);
		this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		
		this.scene = new Scene(this.root);
		// Supposons que primaryStage est votre objet Stage
		
		scene.getStylesheets().add(ResourceManager.STYLESHEET.toExternalForm());
		
		this.stage.setScene(this.scene);	
		stage.show();
	}
	
	public void majListAnnee() {
		this.choiceBoxAnnee.getItems().clear();
		Map<Integer, String> hmAnnee = this.ctrl.getModele().getHmAnnee();
		for (String s : hmAnnee.values())
			this.choiceBoxAnnee.getItems().add(s);
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
			if (isInteger(tc.getCoefficient()) != null)
				textFieldTmp = new TextField(isInteger(tc.getCoefficient()) + "");
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
	
	public static Integer isInteger(double d) {
		if ((int) d + 0.0 == d)
			return (int) d;
		return null;
	}
	
	@FXML
	void modeDuplication() {
		this.ctrl.getModele().setDuplication(!this.ctrl.getModele().isDuplication());
		String message = "Le mode duplication est " + (this.ctrl.getModele().isDuplication() ? "activé" : "désactivé");
		this.afficherNotification("Mode Duplication", message, ControleurIHM.Notification.INFO);
	}
	
	@FXML
	void allerIntervenants(ActionEvent event) {
	this.btnIntervenant.getStyleClass().add("selected-btn");
		
		this.btnAccueil.getStyleClass().clear();
		this.btnAccueil.getStyleClass().addAll("button", "btn-accueil");
		
		this.btnExporter.getStyleClass().clear();
		this.btnExporter.getStyleClass().addAll("button", "btn-accueil");
		
		this.btnModule.getStyleClass().clear();
		this.btnModule.getStyleClass().addAll("button", "btn-accueil");
	
		if(this.ctrl.getModele().isDuplication()) 
			this.modeDuplication();
		this.frameIntervenant = new FrameIntervenant(this.ctrl, this.centerPaneAccueil);
	}
	
	public FrameIntervenant getFrameIntervenant() {
		return this.frameIntervenant;
	}
	
	@FXML
	void allerModules(ActionEvent event) {
		
		this.btnModule.getStyleClass().add("selected-btn");
		System.out.println(this.btnModule.getStyleClass());
		System.out.println(this.btnAccueil.getStyleClass());
		System.out.println(this.btnExporter.getStyleClass());
		System.out.println(this.btnIntervenant.getStyleClass());
		this.btnAccueil.getStyleClass().clear();
		this.btnAccueil.getStyleClass().addAll("button", "btn-accueil");
		
		this.btnExporter.getStyleClass().clear();
		this.btnExporter.getStyleClass().addAll("button", "btn-accueil");
		
		this.btnIntervenant.getStyleClass().clear();
		this.btnIntervenant.getStyleClass().addAll("button", "btn-accueil");
		
		if (this.ctrl.getModele().isDuplication())
			this.modeDuplication();
		new FrameModule(this.ctrl, this.centerPaneAccueil);
	}
	
	@FXML
	void allerExporter(ActionEvent event) {
	
	this.btnExporter.getStyleClass().add("selected-btn");
		
		this.btnAccueil.getStyleClass().clear();
		this.btnAccueil.getStyleClass().addAll("button", "btn-accueil");
		
		this.btnIntervenant.getStyleClass().clear();
		this.btnIntervenant.getStyleClass().addAll("button", "btn-accueil");
		
		this.btnModule.getStyleClass().clear();
		this.btnModule.getStyleClass().addAll("button", "btn-accueil");
		
		if (this.ctrl.getModele().isDuplication())
			this.modeDuplication();
		new FrameExporter(this.ctrl, this.centerPaneAccueil);
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
	
	@Override
	public void handle(Event event) {
		if (event instanceof ActionEvent action) {
			if (action.getSource() == this.choiceBoxAnnee) {
				this.ctrl.getModele().updateAnnee(this.choiceBoxAnnee.getValue());
				this.setAnnee(this.choiceBoxAnnee.getValue());
			} else if (action.getSource() == this.btnConfirmerMultiplicateur) {
				Double coeff;
				this.bErreur = false;
				List<String> alErreur = new ArrayList<>();
				
				for (int i = 0; i < this.alText.size(); i++) {
					coeff = null;
					if (this.alTextField.get(i).getText().contains("/") && !(this.alTextField.get(i).getText().charAt(this.alTextField.get(i).getText().length() - 1)=='/')) {
						String[] partTxt = this.alTextField.get(i).getText().split("/");
						coeff = Double.parseDouble(partTxt[0]) /  Double.parseDouble(partTxt[1]);
						this.ctrl.getModele().updateTypeCoursBrut(alText.get(i).getText(), coeff);
					} else if (!this.alTextField.get(i).getText().isEmpty() && !this.alTextField.get(i).getText().contains("/")) {
						coeff = Double.parseDouble(alTextField.get(i).getText());
						this.ctrl.getModele().updateTypeCoursBrut(alText.get(i).getText(), coeff);
					} else {
						alErreur.add(alText.get(i).getText());
						this.bErreur = true;
					}
				}
				if (!this.bErreur) {
					((Stage) this.btnConfirmerMultiplicateur.getScene().getWindow()).close();
					this.afficherNotification("Succès", "Les coefficients ont bien été modifiés", ControleurIHM.Notification.SUCCES);
				} else {
					String message = "Veuillez vérifier les champs suivants : ";
					for (String s : alErreur)
						message += s + ", ";
					message = message.substring(0, message.length() - 2);
					this.afficherNotification("Erreur", message, ControleurIHM.Notification.ERREUR);
				}
			}
			if (event.getSource() == this.btnOui) {
				ControleurIHM.bIsValidate = true;
				((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
			}
			if (event.getSource() == this.btnAnnuler) {
				ControleurIHM.bIsValidate = false;
				((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
			}
		}
	}
	
	public static double parseDouble(String sDouble) {
		if (sDouble == null || sDouble.trim().isEmpty())
			return -1;
		if (!sDouble.matches(Modele.REGEX_DOUBLE_FRACTION))
			return -1;
		try {
			String[] partTxt = sDouble.split("/");
			if (sDouble.contains("/"))
				return Double.parseDouble(partTxt[0]) /  Double.parseDouble(partTxt[1]);
			else
				return Double.parseDouble(partTxt[0]);
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	
	public void popupValider() {
		//popoUp pour demander si il souhaite vraiment supprimer
		Stage popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.centerOnScreen();
		popupStage.setTitle("Suppression");
		popupStage.setHeight(150);
		popupStage.setWidth(300);
		popupStage.setResizable(false);
		
		Text text = new Text("Souhaitez-vous vraiment supprimer ?");
		 this.btnOui = new Button("Oui");
		
		this.btnAnnuler = new Button("Annuler");
		
		btnOui.addEventHandler(ActionEvent.ACTION, this );
		btnOui.setMinSize(90, 30);
		btnOui.setMaxSize(90,30);
		btnAnnuler.setMinSize(90, 30);
		btnAnnuler.setMaxSize(90, 30);
		btnAnnuler.addEventHandler(ActionEvent.ACTION, this);
		
		StackPane popupLayout = new StackPane();
		
		VBox vbox = new VBox(5);
		vbox.getChildren().add(text);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(20,0,0,0));
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(20));
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(40);
		
		gridPane.add(btnOui, 0, 0);
		gridPane.add(btnAnnuler, 2, 0);
		vbox.getChildren().add(gridPane);
		
		popupLayout.setAlignment(Pos.CENTER);
		popupLayout.getChildren().add(vbox);
		Scene popupScene = new Scene(popupLayout, 300, 130);
		popupScene.getStylesheets().add(ResourceManager.STYLESHEET_POPUP.toExternalForm());
		popupStage.setScene(popupScene);
		popupStage.showAndWait();
	}
	
	public void afficherNotification(String titre, String texte, Notification type) {
		Notifications notificationBuilder = Notifications.create()
			.title(titre)
			.text(texte)
			.hideAfter(Duration.seconds(5))
			.position(Pos.TOP_RIGHT);
		
		Image img;
		
		switch (type) {
			case SUCCES -> img = new Image(ResourceManager.CHECK.toExternalForm(),  48, 48, false, false);
			case ERREUR -> img = new Image(ResourceManager.DELETE.toExternalForm(), 48, 48, false, false);
			default     -> img = new Image(ResourceManager.INFO.toExternalForm(),   48, 48, false, false);
		}
		
		notificationBuilder.graphic(new ImageView(img));
		notificationBuilder.show();
	}
	
	public enum Notification {
		SUCCES,
		ERREUR,
		INFO;
	}
	
	@Override
	public void changed(ObservableValue<? extends String> observable, String oldString, String newString) {
		for (TextField text : alTextField) {
			if (observable == text.textProperty()) {
				if (!text.getText().matches(Modele.REGEX_DOUBLE_FRACTION))
					text.setText(oldString);
			}
		}
	}
}