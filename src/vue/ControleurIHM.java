package vue;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import controleur.Controleur;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import modele.Modele;

public class ControleurIHM implements Initializable, EventHandler<Event> {
	
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
		
	@FXML
	private ImageView imageAccueil;
	
	@FXML
	private ImageView imageIntervenant;
	
	@FXML
	private ImageView imageModule;
	
	@FXML
	private ImageView imageDownload;
	
		
	private Button btnOui;
	private Button btnAnnuler;
	
	public static boolean bIsValidate = false;
	
	public void initialize(URL url, ResourceBundle rb) {
		this.ctrl = Controleur.getInstance(this);
				
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
		new FrameMultiplicateur(this.ctrl);
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
		//popUp pour demander si il souhaite vraiment supprimer
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

	public void setFrameIntervenant(FrameIntervenant frameIntervenant2) {
	this.frameIntervenant = frameIntervenant2;
	}
	

}