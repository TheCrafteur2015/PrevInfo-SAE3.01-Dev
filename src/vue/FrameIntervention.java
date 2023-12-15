package vue;

import java.util.ArrayList;
import java.util.Map;

import controleur.Controleur;
import modele.*;
import modele.Module;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FrameIntervention implements EventHandler<ActionEvent>{
	private Controleur ctrl;
	private AnchorPane centerPaneAccueil;
	private Module module;

	private Map<Integer,Intervenant> hmIntervenants;
	private Map<Integer, TypeCours> hmTypeCours;
	private Map<Integer, TypeModule> hmTypeModule;
	private Map<String, Intervention> hmIntervention;

	private ArrayList<RadioButton> lstrButton;

	private TextField tfNbSemaines;
	private TextField tfNbGroupes;

	private Button btnAjouter;
	
	private ChoiceBox<Intervenant> chBoxIntervenants;

	private TableView<InterventionIHM> tbV; 
	

	public FrameIntervention(Controleur ctrl, AnchorPane centerPaneAccueil,Module module){
		this.ctrl = ctrl;
		this.centerPaneAccueil = centerPaneAccueil;
		this.module = module;

		this.init();
	}

	public void init(){
		Stage popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.setTitle(this.module.getNom());

		BorderPane borderPaneCentral = new BorderPane();


		/*---------------------*/
		/*-- BorderPane left --*/
		/*---------------------*/
		
		VBox vbox = new VBox();
		vbox.setSpacing(10);
		GridPane gridIntervenant = new GridPane();
		GridPane gridEntree = new GridPane();
		gridEntree.setHgap(5);
		gridEntree.setVgap(5);
		FlowPane flowrButton = new FlowPane();
		

		//GridIntervenants

		this.hmIntervenants = this.ctrl.getModele().getHmIntervenants();

		this.chBoxIntervenants = new ChoiceBox<Intervenant>();
		this.chBoxIntervenants.getStyleClass().add("choiceBox");
		for (Intervenant intervenant : this.hmIntervenants.values()) 
			this.chBoxIntervenants.getItems().add(intervenant);
		
		gridIntervenant.add(new Label("Intervenant"), 0, 0);
		gridIntervenant.add(this.chBoxIntervenants, 0, 1);

		//Get le Type de Module
		String nomTypeModule = this.hmTypeModule.get(this.module.getIdTypeModule()).getNom();

		//FlowPane de RadioButton
		this.hmTypeCours = this.ctrl.getModele().getHmTypeCours();
		this.lstrButton = new ArrayList<RadioButton>();
		ToggleGroup group = new ToggleGroup();

		HBox hBox = new HBox();  
		hBox.setSpacing(5); 		
		
		this.hmTypeModule = this.ctrl.getModele().getHmTypeModule();
	
		
		
		if (nomTypeModule.equals("normal") || nomTypeModule.equals("PPP")) {
			//CM, TD, TP, Tut, REH,      HP
			for (TypeCours typeCours : this.hmTypeCours.values()) {
				if (!typeCours.getNom().equals("SAE") && !typeCours.getNom().equals("REH") && !typeCours.getNom().equals("Tut") ){
						RadioButton rb = new RadioButton(typeCours.getNom());
						rb.setToggleGroup(group);
						this.lstrButton.add(rb);
						hBox.getChildren().add(rb);
				}

				if((typeCours.getNom().equals("Tut")) && nomTypeModule.equals("PPP")){
					RadioButton rb = new RadioButton(typeCours.getNom());
					rb.setToggleGroup(group);
					this.lstrButton.add(rb);
					hBox.getChildren().add(rb);
				}	
			}
		}

		if (nomTypeModule.equals("SAE") || nomTypeModule.equals("stage") ) {
			//REH, SAE, HP
			RadioButton rb1 = new RadioButton("Tut");
			RadioButton rb2 = new RadioButton("HP");
			RadioButton rb3 = new RadioButton();

			if (nomTypeModule.equals("SAE")) rb3.setText("SAE");
			if (nomTypeModule.equals("stage")) rb3.setText("REH");

			this.lstrButton.add(rb1);
			this.lstrButton.add(rb2);
			this.lstrButton.add(rb3);

			rb1.setToggleGroup(group);
			rb2.setToggleGroup(group);
			rb3.setToggleGroup(group);

			hBox.getChildren().addAll(rb1,rb2,rb3);
		}

		flowrButton.getChildren().add(hBox);

		this.btnAjouter = new Button("Ajouter");
		this.btnAjouter.addEventHandler(ActionEvent.ACTION, this);

		
		vbox.getChildren().add(gridIntervenant);

		if (nomTypeModule.equals("normal") || nomTypeModule.equals("PPP")) {
			//GridEntree pour le nombre de semaines et nombre de groupes
			this.tfNbSemaines = new TextField();
			this.tfNbGroupes  = new TextField();
			gridEntree.add(new Label("Nombre de semaines : "),0,0);
			gridEntree.add(this.tfNbSemaines,1,0);
			gridEntree.add(new Label("Nombre de groupes : "),0,1);
			gridEntree.add(this.tfNbGroupes,1,1);

			vbox.getChildren().add(gridEntree);
		}
		
		vbox.getChildren().addAll(flowrButton, this.btnAjouter);
		//Centrer le VBox
		StackPane stackPane = new StackPane();		
		stackPane.getChildren().addAll(vbox);

		StackPane popupLayout = new StackPane();
	
		//Mettre le vbox à gauche
		borderPaneCentral.setLeft(stackPane);	


		/*--------------------*/
		/*--BorderPane Right--*/
		/*--------------------*/
		
		ObservableList<InterventionIHM> olInterventionIHMs = FXCollections.observableArrayList();
		this.hmIntervention = this.ctrl.getModele().getHmInterventions();

		if (nomTypeModule.equals("normal") || nomTypeModule.equals("PPP")) {
			for (Intervention i : this.hmIntervention.values()) {
			Button btnSup = this.ctrl.getVue().getFrameIntervenant().getSupButton();
			btnSup.setId(i.getIdIntervenant() + "-" + i.getIdModule() + "-" + i.getIdTypeCours());
			btnSup.addEventHandler(ActionEvent.ACTION, this);

			//olInterventionIHMs.add(new InterventionIHM());
			}
		}
		else {	
		
		}
		/*
		}*/

		this.tbV = new TableView<>();

		String[] colonnes = new String[] { "Prenom","Nom","nombre semaines","nombre groupe","type","heure","heures réelles","Supprimer" };

		if (tbV.getColumns().size() < colonnes.length) {
			for (String colonne : colonnes) {
				TableColumn<InterventionIHM, String> tbcl = new TableColumn<>(colonne);
				tbcl.setStyle("-fx-alignment: CENTER;");
				tbcl.setCellValueFactory(new PropertyValueFactory<>(colonne.toLowerCase()));
				tbV.getColumns().add(tbcl);
			}
		}


		popupLayout.getChildren().add(borderPaneCentral);
		popupLayout.getStylesheets().add(ResourceManager.STYLESHEET.toExternalForm());
		Scene popupScene = new Scene(popupLayout, 500, 320);
		popupStage.setScene(popupScene);

		popupStage.setResizable(false);
		popupStage.showAndWait();
		
	}

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'handle'");
	}
}