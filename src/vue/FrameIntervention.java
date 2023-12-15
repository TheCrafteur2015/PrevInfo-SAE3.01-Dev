package vue;

import java.util.ArrayList;
import java.util.Map;

import controleur.Controleur;
import modele.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FrameIntervention{
	private Controleur ctrl;
	private AnchorPane centerPaneAccueil;
	private modele.Module module;

	private ArrayList<CheckBox> lstCheckBoxs;
	
	private ChoiceBox<Intervenant> chBoxIntervenants;
	private Map<Integer,Intervenant> hmIntervenants;
	private Map<Integer, TypeCours> hmTypeCours;

	public FrameIntervention(Controleur ctrl, AnchorPane centerPaneAccueil,modele.Module module){
		this.ctrl = ctrl;
		this.centerPaneAccueil = centerPaneAccueil;

		this.init();
	}

	public void init(){
		Stage popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.setTitle(this.module.getNom());

		BorderPane borderPaneCentral = new BorderPane();
		VBox vbox = new VBox();
		GridPane gridIntervenant = new GridPane();
		GridPane gridEntree = new GridPane();
		gridEntree.setHgap(5);
		gridEntree.setVgap(5);
		FlowPane flowCheckbox = new FlowPane();

		//GridIntervenants
		this.hmIntervenants = this.ctrl.getModele().getHmIntervenants();

		this.chBoxIntervenants = new ChoiceBox<Intervenant>();
		this.chBoxIntervenants.getStyleClass().add("choiceBox");
		for (Intervenant intervenant : this.hmIntervenants.values()) 
			this.chBoxIntervenants.getItems().add(intervenant);
		
		gridIntervenant.add(new Label("Intervenant"), 0, 0);
		gridIntervenant.add(this.chBoxIntervenants, 1, 0);

		
		//GridEntree pour le nombre de semaines et nombre de groupes
		TextField tfNbSemaines = new TextField();
		TextField tfNbGroupes  = new TextField();
		gridEntree.add(new Label("Nombre de semaines : "),0,0);
		gridEntree.add(tfNbSemaines,0,1);
		gridEntree.add(new Label("Nombre de groupes : "),1,0);
		gridEntree.add(tfNbGroupes,1,1);


		//FlowPane de Checkbox
		this.hmTypeCours = this.ctrl.getModele().getHmTypeCours();
		this.lstCheckBoxs = new ArrayList<CheckBox>();

		HBox hBox = new HBox();  
		hBox.setSpacing(5); 

		for (TypeCours typeCours : this.hmTypeCours.values()) {
			CheckBox cb = new CheckBox(typeCours.getNom());
			this.lstCheckBoxs.add(cb);
			hBox.getChildren().add(cb);
		}

		flowCheckbox.getChildren().add(hBox);

		Button btnAjouter = new Button("Ajouter");

		vbox.getChildren().addAll(gridIntervenant, gridEntree, flowCheckbox, btnAjouter);
		//Centrer le VBox
		StackPane stackPane = new StackPane();		
		stackPane.getChildren().addAll(vbox);

		StackPane popupLayout = new StackPane();
	
		//Mettre le vbox à gauche
		borderPaneCentral.setLeft(stackPane);	
		popupLayout.getChildren().add(borderPaneCentral);
		popupLayout.getStylesheets().add(ResourceManager.STYLESHEET.toExternalForm());
		Scene popupScene = new Scene(popupLayout, 500, 320);
		popupStage.setScene(popupScene);

		popupStage.showAndWait();
		
	}
}