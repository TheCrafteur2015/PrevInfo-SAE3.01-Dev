package vue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controleur.Controleur;
import modele.*;
import modele.Module;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

public class FrameIntervention implements EventHandler<ActionEvent>,ChangeListener<String> {
	private Controleur ctrl;
	private AnchorPane centerPaneAccueil;
	private Module module;

	private Map<Integer, Intervenant> hmIntervenants;
	private Map<Integer, TypeCours> hmTypeCours;
	private Map<Integer, TypeModule> hmTypeModule;
	private Map<Integer, Intervention> hmIntervention;

	private ArrayList<RadioButton> lstrButton;
	private ToggleGroup group;

	private TextField tfNbSemaines;
	private TextField tfNbGroupes;
	private TextField tfCommentaire;

	private Button btnAjouter;

	private Label lblErreurPN;
	private Label lblErreurGrp;
	private Label lblErreurSemaine;

	private ChoiceBox<Intervenant> chBoxIntervenants;

	private TableView<InterventionIHM> tbV;

	public FrameIntervention(Controleur ctrl, AnchorPane centerPaneAccueil, Module module) {
		this.ctrl = ctrl;
		this.centerPaneAccueil = centerPaneAccueil;
		this.module = module;

		this.init();
	}

	public void init() {
		Stage popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.setTitle(this.module.getNom());

		BorderPane borderPaneCentral = new BorderPane();

		/*---------------------*/
		/*-- BorderPane left --*/
		/*---------------------*/

		VBox vbox = new VBox();
		vbox.setSpacing(30);
		vbox.setAlignment(Pos.CENTER);
		GridPane gridIntervenant = new GridPane();
		gridIntervenant.setAlignment(Pos.CENTER);

		GridPane gridEntree = new GridPane();
		gridEntree.setHgap(5);
		gridEntree.setVgap(5);
		gridEntree.setAlignment(Pos.CENTER);

		FlowPane flowrButton = new FlowPane();
		flowrButton.setAlignment(Pos.CENTER);

		// GridIntervenants

		this.hmIntervenants = this.ctrl.getModele().getHmIntervenants();

		this.chBoxIntervenants = new ChoiceBox<Intervenant>();
		this.chBoxIntervenants.getStyleClass().add("choiceBox");
		for (Intervenant intervenant : this.hmIntervenants.values())
			this.chBoxIntervenants.getItems().add(intervenant);

		gridIntervenant.add(new Label("Intervenant"), 0, 0);
		gridIntervenant.add(this.chBoxIntervenants, 0, 1);

		this.hmTypeModule = this.ctrl.getModele().getHmTypeModule();

		// Get le Type de Module
		String nomTypeModule = this.hmTypeModule.get(this.module.getIdTypeModule()).getNom();

		// FlowPane de RadioButton
		this.hmTypeCours = this.ctrl.getModele().getHmTypeCours();
		this.lstrButton = new ArrayList<RadioButton>();
		this.group = new ToggleGroup();

		HBox hBox = new HBox();
		hBox.setSpacing(5);

		if (nomTypeModule.equals("normal") || nomTypeModule.equals("PPP")) {
			// CM, TD, TP, Tut, REH, HP
			for (TypeCours typeCours : this.hmTypeCours.values()) {
				if (!typeCours.getNom().equals("SAE") && !typeCours.getNom().equals("REH")
						&& !typeCours.getNom().equals("Tut")) {
					RadioButton rb = new RadioButton(typeCours.getNom());
					rb.setToggleGroup(this.group);
					this.lstrButton.add(rb);
					hBox.getChildren().add(rb);
				}

				if ((typeCours.getNom().equals("Tut")) && nomTypeModule.equals("PPP")) {
					RadioButton rb = new RadioButton(typeCours.getNom());
					rb.setToggleGroup(this.group);
					this.lstrButton.add(rb);
					hBox.getChildren().add(rb);
				}
			}
		}

		if (nomTypeModule.equals("SAE") || nomTypeModule.equals("stage")) {
			// REH, SAE, HP
			RadioButton rb1 = new RadioButton("Tut");
			RadioButton rb2 = new RadioButton("HP");
			RadioButton rb3 = new RadioButton();

			if (nomTypeModule.equals("SAE"))
				rb3.setText("SAE");
			if (nomTypeModule.equals("stage"))
				rb3.setText("REH");

			this.lstrButton.add(rb1);
			this.lstrButton.add(rb2);
			this.lstrButton.add(rb3);

			rb1.setToggleGroup(this.group);
			rb2.setToggleGroup(this.group);
			rb3.setToggleGroup(this.group);

			hBox.getChildren().addAll(rb1, rb2, rb3);
		}

		flowrButton.getChildren().add(hBox);

		this.btnAjouter = new Button("⨁ Ajouter");
		this.btnAjouter.addEventHandler(ActionEvent.ACTION, this);
		this.btnAjouter.setAlignment(Pos.BASELINE_CENTER);

		vbox.getChildren().add(gridIntervenant);

		if (nomTypeModule.equals("normal") || nomTypeModule.equals("PPP")) {
			// GridEntree pour le nombre de semaines et nombre de groupes
			this.tfNbSemaines = new TextField();
			this.tfNbSemaines.setPrefWidth(5*7);
			this.tfNbSemaines.textProperty().addListener(this);

			this.tfNbGroupes = new TextField();
			this.tfNbGroupes.setPrefWidth(5*7);
			this.tfNbGroupes.textProperty().addListener(this);

			gridEntree.add(new Label("Nombre de semaines : "), 0, 0);
			gridEntree.add(this.tfNbSemaines, 1, 0);
			gridEntree.add(new Label("Nombre de groupes : "), 0, 1);
			gridEntree.add(this.tfNbGroupes, 1, 1);

			vbox.getChildren().add(gridEntree);
		}
		
		GridPane gridCommentaire = new GridPane();
		gridCommentaire.setHgap(5);
		gridCommentaire.add(new Label("Commentaire : "),0,0);
		this.tfCommentaire = new TextField();
		this.tfCommentaire.setPrefSize(30*7,20);
		this.tfCommentaire.setAlignment(Pos.CENTER);
		gridCommentaire.add(this.tfCommentaire,0,1);
		
		gridCommentaire.setAlignment(Pos.CENTER);
	
		vbox.getChildren().addAll(gridCommentaire, flowrButton, this.btnAjouter);
		// Centrer le VBox
		StackPane stackPane = new StackPane();
		stackPane.getChildren().addAll(vbox);

		StackPane popupLayout = new StackPane();

		// Mettre le vbox à gauche
		borderPaneCentral.setLeft(stackPane);

		/*---------------------*/
		/*--BorderPane Center--*/
		/*---------------------*/

		BorderPane borderLayoutErrTBV = new BorderPane();
		VBox vboxErreur = new VBox(5);

		// VBox erreurs
		this.lblErreurGrp = new Label();
		this.lblErreurPN = new Label();
		this.lblErreurSemaine = new Label();
		vboxErreur.getChildren().addAll(this.lblErreurGrp, this.lblErreurPN, this.lblErreurSemaine);

		borderLayoutErrTBV.setTop(vboxErreur);

		// Table View Intervention
		ObservableList<InterventionIHM> olInterventionIHMs = FXCollections.observableArrayList();
		Map<Integer, Intervention> hmInterventionTmp = this.ctrl.getModele().getHmInterventions();
		// Map<Integer, Intervention> hmInterventionTmp =
		// this.ctrl.getModele().getHmInterventionsModule( idModule);
		this.hmIntervention = new HashMap<>();
		for (Intervention i : hmInterventionTmp.values()) {
			if (i.getIdModule() == module.getId())
				this.hmIntervention.put(i.getIdIntervention(), i);
		}

		String[] colonnes = new String[10];

		if (nomTypeModule.equals("normal") || nomTypeModule.equals("PPP")) {
			colonnes = new String[] { "Prenom", "Nom", "semaines", "groupes", "type", "heures", "reelles",
					"commentaire", "supprimer" };
		} else {
			colonnes = new String[] { "Prenom", "Nom", "type", "heures", "reelles", "commentaire", "supprimer" };
		}

		for (Intervention i : this.hmIntervention.values()) {
			Button btnSup = ResourceManager.getSupButton();
			btnSup.setId(i.getIdIntervenant() + "-" + i.getIdModule() + "-" + i.getIdTypeCours());
			btnSup.addEventHandler(ActionEvent.ACTION, this);

			Intervenant intervenant = this.hmIntervenants.get(i.getIdIntervenant());
			Categorie categorie = this.ctrl.getModele().getHmCategories().get(intervenant.getIdCategorie());
			TypeCours typeCours = this.hmTypeCours.get(i.getIdTypeCours());

			double heureCours = 0;
			double heureCoursReelles = 0;
			double hParSemaine = 1;
			for (HeureCours hCours : this.ctrl.getModele().getHeureCoursByModule(i.getIdModule(), i.getIdAnnee()))
				if (hCours.getIdTypeCours() == typeCours.getId()) {
					if (hCours.gethParSemaine() != 0)
						hParSemaine = hCours.gethParSemaine();
					heureCours = hParSemaine * i.getNbSemaines() * i.getNbGroupe();
					heureCoursReelles = heureCours * typeCours.getCoefficient();
					if (typeCours.getNom().equals("TP"))
						heureCoursReelles = heureCoursReelles * categorie.getRatioTp();
				}
			if (nomTypeModule.equals("normal") || nomTypeModule.equals("PPP")) {
				olInterventionIHMs.add(new InterventionIHM(intervenant.getPrenom(), intervenant.getNom(),
						i.getNbSemaines() + "", i.getNbGroupe() + "",
						typeCours.getNom(), heureCours + "", heureCoursReelles + "", i.getCommentaire(), btnSup));
			} else {
				olInterventionIHMs.add(new InterventionIHM(intervenant.getPrenom(), intervenant.getNom(),
						typeCours.getNom(), heureCours + "", heureCoursReelles + "", i.getCommentaire(), btnSup));
			}

		}

		this.tbV = new TableView<>();
		//this.tbV.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		if (tbV.getColumns().size() < colonnes.length) {
			for (String colonne : colonnes) {
				TableColumn<InterventionIHM, String> tbcl = new TableColumn<>(colonne);
				tbcl.setStyle("-fx-alignment: CENTER;");
				tbcl.setCellValueFactory(new PropertyValueFactory<>(colonne.toLowerCase()));
				tbV.getColumns().add(tbcl);

			}
			tbV.getColumns().get(7).prefWidthProperty().bind(tbV.widthProperty().multiply(0.3));
		}

		tbV.setItems(olInterventionIHMs);

		borderLayoutErrTBV.setCenter(this.tbV);
		borderLayoutErrTBV.setPadding(new Insets(0, 20, 20, 0));

		borderPaneCentral.setCenter(borderLayoutErrTBV);
		popupLayout.getChildren().add(borderPaneCentral);
		popupLayout.getStylesheets().add(ResourceManager.STYLESHEET.toExternalForm());
		Scene popupScene = new Scene(popupLayout, 1000, 500);
		popupStage.setScene(popupScene);

		popupStage.setResizable(false);
		popupStage.showAndWait();

	}

	@Override
	public void handle(ActionEvent action) {
		if (action.getSource() == this.btnAjouter) {
			Intervenant interSelected = this.chBoxIntervenants.getSelectionModel().getSelectedItem();
			if (interSelected == null){
				this.ctrl.getVue().afficherNotification("Ajouter une intervention","Sélectionnez un intervenant","erreur");
				return;
			} 

			RadioButton selectedRadioButton = (RadioButton) this.group.getSelectedToggle();

			if (selectedRadioButton == null) {
				this.ctrl.getVue().afficherNotification("Ajouter une intervention","Sélectionnez un type de module","erreur");
				return;
			}
					
			int idTypeCours = -1;
			for (TypeCours typeCours : this.hmTypeCours.values()) {
				if (typeCours.getNom().equals(selectedRadioButton.getText())) {
					idTypeCours = typeCours.getId();
				}
			}

			int nbSemaines = Integer.parseInt(this.tfNbSemaines.getText());
			Semestre sem = this.ctrl.getModele().getHmSemestres().get(this.module.getIdSemestre());
			
			if (nbSemaines > sem.getNbSemaine()) {
				this.ctrl.getVue().afficherNotification("Ajouter une intervention","Le nombre de semaines est supérieur au nombre de semaines du semestre ("+sem.getNbSemaine()+")","erreur");
				return;
			}

			System.out.println(this.tfCommentaire.getText()); 
			this.ctrl.getModele().ajouterIntervention(interSelected.getId(),this.module.getId(),idTypeCours,nbSemaines,this.tfCommentaire.getText(), Integer.parseInt(this.tfNbGroupes.getText()));
			init();
		}
	}

	@Override
	public void changed(ObservableValue<? extends String> observable, String oldString, String newString) {
		if (!this.tfNbGroupes.getText().matches(Modele.REGEX_INT)) {
			this.tfNbGroupes.setText(oldString);
		}

		if (!this.tfNbSemaines.getText().matches(Modele.REGEX_INT)) {
			this.tfNbSemaines.setText(oldString);
		}
	}
}