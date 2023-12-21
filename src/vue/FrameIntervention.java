package vue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controleur.Controleur;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modele.Categorie;
import modele.HeureCours;
import modele.Intervenant;
import modele.Intervention;
import modele.Modele;
import modele.Module;
import modele.Semestre;
import modele.TypeCours;
import modele.TypeModule;

public class FrameIntervention implements EventHandler<ActionEvent>, ChangeListener<String> {

	private Stage popupStage;
	private Controleur ctrl;
	private AnchorPane centerPaneAccueil;
	private Module module;
	private Semestre semestre;
	private String nomTypeModule;

	private Map<Integer, Intervenant> hmIntervenants;
	private Map<Integer, TypeCours> hmTypeCours;
	private Map<Integer, TypeModule> hmTypeModule;
	private Map<Integer, Intervention> hmIntervention;

	private GridPane gridEntree;

	private List<RadioButton> lstrButton;
	private ToggleGroup group;

	private RadioButton rbValider;

	private TextField tfNbSemaines;
	private TextField tfNbGroupes;
	private TextField tfCommentaire;
	private TextField tfNbHeures;

	private Button btnAjouter;

	private Label lblErreurPN;
	private Label lblErreurGrp;
	
	private double totalAffecte;
	private double totalPromo;

	private ChoiceBox<Intervenant> chBoxIntervenants;

	private TableView<InterventionIHM> tbV;

	public FrameIntervention(Controleur ctrl, AnchorPane centerPaneAccueil, Module module, boolean afficherPopup) {
		this.ctrl = ctrl;
		this.centerPaneAccueil = centerPaneAccueil;
		this.module = module;
		this.semestre = this.ctrl.getModele().getHmSemestres().get(this.module.getIdSemestre());
		this.init(afficherPopup);
	}

	public void init(boolean afficherPopup) {
		this.popupStage = new Stage();
		this.popupStage.initModality(Modality.APPLICATION_MODAL);
		this.popupStage.setTitle(this.module.getNom());

		this.maj();

		this.popupStage.setResizable(false);
		if (afficherPopup) this.popupStage.showAndWait();
	}

	public void maj() {
		BorderPane borderPaneCentral = new BorderPane();
		BorderPane borderRecapEntree = new BorderPane();

		/*---------------------*/
		/*-- BorderPane left --*/
		/*---------------------*/

		VBox vbox = new VBox();
		vbox.setSpacing(30);
		vbox.setAlignment(Pos.CENTER);
		GridPane gridIntervenant = new GridPane();
		gridIntervenant.setAlignment(Pos.CENTER);

		this.gridEntree = new GridPane();
		gridEntree.setHgap(5);
		gridEntree.setVgap(5);
		gridEntree.setAlignment(Pos.CENTER);

		FlowPane flowrButton = new FlowPane();
		flowrButton.setAlignment(Pos.CENTER);

		// GridIntervenants

		this.hmIntervenants = this.ctrl.getModele().getHmIntervenants();

		this.chBoxIntervenants = new ChoiceBox<>();
		for (Intervenant intervenant : this.hmIntervenants.values())
			this.chBoxIntervenants.getItems().add(intervenant);

		gridIntervenant.add(new Label("Intervenant"), 0, 0);
		gridIntervenant.add(this.chBoxIntervenants, 0, 1);

		this.hmTypeModule = this.ctrl.getModele().getHmTypeModule();

		// Get le Type de Module
		this.nomTypeModule = this.hmTypeModule.get(this.module.getIdTypeModule()).getNom();

		// FlowPane de RadioButton
		this.hmTypeCours = this.ctrl.getModele().getHmTypeCours();
		this.lstrButton = new ArrayList<>();
		this.group = new ToggleGroup();

		HBox hBox = new HBox();
		hBox.setSpacing(5);

		if (this.nomTypeModule.equals("normal") || this.nomTypeModule.equals("PPP")) {
			// CM, TD, TP, Tut, REH, HP
			for (TypeCours typeCours : this.hmTypeCours.values()) {
				if (!typeCours.getNom().equals("SAE") && !typeCours.getNom().equals("REH")
						&& !typeCours.getNom().equals("Tut")) {
					RadioButton rb = new RadioButton(typeCours.getNom());
					rb.addEventHandler(ActionEvent.ACTION, this);

					rb.setToggleGroup(this.group);
					this.lstrButton.add(rb);
					hBox.getChildren().add(rb);
				}

				if ((typeCours.getNom().equals("Tut")) && this.nomTypeModule.equals("PPP")) {
					RadioButton rb = new RadioButton(typeCours.getNom());
					rb.addEventHandler(ActionEvent.ACTION, this);
					rb.setToggleGroup(this.group);
					this.lstrButton.add(rb);
					hBox.getChildren().add(rb);
				}
			}
		}

		if (this.nomTypeModule.equals("SAE") || this.nomTypeModule.equals("stage")) {
			// REH, SAE, HP
			RadioButton rb1 = new RadioButton("Tut");
			rb1.addEventHandler(ActionEvent.ACTION, this);
			RadioButton rb3 = new RadioButton();
			if (this.nomTypeModule.equals("SAE"))
				rb3.setText("SAE");
			if (this.nomTypeModule.equals("stage"))
				rb3.setText("REH");

			rb3.addEventHandler(ActionEvent.ACTION, this);
			this.lstrButton.add(rb1);
			this.lstrButton.add(rb3);

			rb1.setToggleGroup(this.group);
			rb3.setToggleGroup(this.group);

			hBox.getChildren().addAll(rb1, rb3);
			
			
		}

		flowrButton.getChildren().add(hBox);

		this.btnAjouter = new Button("Ajouter");
		this.btnAjouter.addEventHandler(ActionEvent.ACTION, this);
		this.btnAjouter.setAlignment(Pos.BASELINE_CENTER);
		
		//Creer radioButton valider
		this.rbValider = new RadioButton("Valider");
		this.rbValider.setSelected(this.module.isValid());
		this.rbValider.addEventHandler(ActionEvent.ACTION,this);
		

		vbox.getChildren().add(rbValider);
		vbox.getChildren().add(gridIntervenant);

		if (this.nomTypeModule.equals("normal") || this.nomTypeModule.equals("PPP")) {
			// GridEntree pour le nombre de semaines et nombre de groupes
			this.tfNbHeures = new TextField();
			this.tfNbHeures.setPrefWidth(5 * 7);
			this.tfNbSemaines = new TextField();
			this.tfNbSemaines.setPrefWidth(5 * 7);
			this.tfNbSemaines.textProperty().addListener(this);

			this.tfNbGroupes = new TextField();
			this.tfNbGroupes.setPrefWidth(5 * 7);
			this.tfNbGroupes.textProperty().addListener(this);

			gridEntree.add(new Label("Nombre de semaines : "), 0, 0);
			gridEntree.add(this.tfNbSemaines, 1, 0);
			gridEntree.add(new Label("Nombre de groupes : "), 0, 1);
			gridEntree.add(this.tfNbGroupes, 1, 1);
		}
		
		else {
			this.tfNbHeures = new TextField();
			this.tfNbHeures.setPrefWidth(5 * 7);
			this.gridEntree.add(new Label("Nombre d'heures : "), 0, 0);
			this.gridEntree.add(this.tfNbHeures, 1, 0);
		}
		vbox.getChildren().add(gridEntree);
		GridPane gridCommentaire = new GridPane();
		gridCommentaire.setHgap(5);
		gridCommentaire.add(new Label("Commentaire : "), 0, 0);
		this.tfCommentaire = new TextField();
		this.tfCommentaire.setPrefSize(30 * 7, 20);
		this.tfCommentaire.setAlignment(Pos.CENTER);
		gridCommentaire.add(this.tfCommentaire, 0, 1);

		gridCommentaire.setAlignment(Pos.CENTER);

		vbox.getChildren().addAll(gridCommentaire, flowrButton, this.btnAjouter);
		// Centrer le VBox
		StackPane stackPane = new StackPane();
		stackPane.getChildren().addAll(vbox);

		/*---------------------*/
		/*--BorderPane Center--*/
		/*---------------------*/

		BorderPane borderLayoutErrTBV = new BorderPane();
		VBox vboxErreur = new VBox(5);

		// VBox erreurs
		this.lblErreurGrp = new Label();
		this.lblErreurGrp.getStyleClass().clear();
		this.lblErreurGrp.getStyleClass().add("error-label");
		this.lblErreurPN = new Label("PROGRAMME NATIONAL : ");
		this.lblErreurPN.getStyleClass().clear();
		this.lblErreurPN.getStyleClass().add("error-label");

		vboxErreur.getChildren().addAll(this.lblErreurGrp, this.lblErreurPN);

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

		// String[] colonnes = new String[10];

		// if (this.nomTypeModule.equals("normal") || this.nomTypeModule.equals("PPP")) {
		// 	colonnes = new String[] { "Prenom", "Nom", "semaines", "groupes", "type", "heures", "reelles",
		// 			"commentaire", "supprimer" };
		// } else {
		// 	colonnes = new String[] { "Prenom", "Nom", "type", "heures", "reelles", "commentaire", "supprimer" };
		// }
		
		String[] colonnes = { "Prenom", "Nom", "semaines", "groupes", "type", "heures", "reelles", "commentaire", "supprimer" };
		
		if (!this.nomTypeModule.equals("normal") && !this.nomTypeModule.equals("PPP"))
			colonnes[2] = colonnes[3] = null;
		
		for (Intervention i : this.hmIntervention.values()) {
			Button btnSup = ResourceManager.getSupButton();
			btnSup.setId(i.getIdIntervention() + "");
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
			if (this.nomTypeModule.equals("normal") || this.nomTypeModule.equals("PPP")) {
				if (typeCours.getNom().equals("HP")) {
					olInterventionIHMs.add(new InterventionIHM(intervenant.getPrenom(), intervenant.getNom(),
							"" + "", "" + "",
							typeCours.getNom(), i.getNbGroupe() + "", i.getNbGroupe() * typeCours.getCoefficient() + "",
							i.getCommentaire(), btnSup));
				} else {
					olInterventionIHMs.add(new InterventionIHM(intervenant.getPrenom(), intervenant.getNom(),
							i.getNbSemaines() + "", i.getNbGroupe() + "",
							typeCours.getNom(), heureCours + "", heureCoursReelles + "", i.getCommentaire(), btnSup));
				}
			} else {
				olInterventionIHMs.add(new InterventionIHM(intervenant.getPrenom(), intervenant.getNom(),
						typeCours.getNom(), i.getNbGroupe() + "",  i.getNbGroupe() * typeCours.getCoefficient() + "" + "", i.getCommentaire(), btnSup));
			}

		}

		this.tbV = new TableView<>();
		// this.tbV.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		if (tbV.getColumns().size() < colonnes.length) {
			for (String colonne : colonnes) {
				if (colonne == null)
					continue;
				TableColumn<InterventionIHM, String> tbcl = new TableColumn<>(colonne);
				tbcl.setStyle("-fx-alignment: CENTER;");
				tbcl.getStyleClass().add("col");
				tbcl.setCellValueFactory(new PropertyValueFactory<>(colonne.toLowerCase()));
				tbV.getColumns().add(tbcl);
				if (colonne.equals("commentaire"))
					tbcl.prefWidthProperty().bind(tbV.widthProperty().multiply(0.2));
				else
					tbcl.prefWidthProperty().bind(tbV.widthProperty().multiply((0.8)/(colonnes.length-1)-0.0025));
			}

		}

		tbV.setItems(olInterventionIHMs);

		// tester erreurs groupes et PN du tableau
		int nbGroupeCM = 0;
		int nbGroupeTD = 0;
		int nbGroupeTP = 0;

		Double nbHeureCM = 0.0;
		Double nbHeureTD = 0.0;
		Double nbHeureTP = 0.0;
		Double nbHeureTut = 0.0;
		Double nbHeureSAE = 0.0;
		Double nbHeureREH = 0.0;
		Double nbHeureHP = 0.0;
		for (InterventionIHM interventionIHM : olInterventionIHMs) {

			// Compter nbGroupes
			if (interventionIHM.getType().equals("CM")) {
				nbGroupeCM += Integer.parseInt(interventionIHM.getGroupes());
				nbHeureCM += Double.parseDouble(interventionIHM.getHeures());
			}

			if (interventionIHM.getType().equals("TD")) {
				nbGroupeTD += Integer.parseInt(interventionIHM.getGroupes());
				nbHeureTD += Double.parseDouble(interventionIHM.getHeures());
			}

			if (interventionIHM.getType().equals("TP")) {
				nbGroupeTP += Integer.parseInt(interventionIHM.getGroupes());
				nbHeureTP += Double.parseDouble(interventionIHM.getHeures());
			}
			
			if (interventionIHM.getType().equals("Tut")) {
				nbHeureTut += Double.parseDouble(interventionIHM.getHeures());	
			}
			
			if (interventionIHM.getType().equals("SAE")) {
				nbHeureSAE += Double.parseDouble(interventionIHM.getHeures());	
			}
			
			if (interventionIHM.getType().equals("REH")) {
				nbHeureREH += Double.parseDouble(interventionIHM.getHeures());	
			}
			
			if (interventionIHM.getType().equals("HP")) {
				nbHeureHP += Double.parseDouble(interventionIHM.getHeures());	
			}

		}

		// Afficher message d'erreur groupes
		if (!this.nomTypeModule.equals("SAE") && !this.nomTypeModule.equals("stage") ) {
			if (nbGroupeCM != this.semestre.getNbGCM() || nbGroupeTD != this.semestre.getNbGTD()
				|| nbGroupeTP != this.semestre.getNbGTP()) {
			this.lblErreurGrp.setText("ERREUR GROUPE :");

			if (nbGroupeCM != this.semestre.getNbGCM())
				this.lblErreurGrp.setText(this.lblErreurGrp.getText() + " CM ");

			if (nbGroupeTD != this.semestre.getNbGTD())
				this.lblErreurGrp.setText(this.lblErreurGrp.getText() + " TD ");

			if (nbGroupeTP != this.semestre.getNbGTP())
				this.lblErreurGrp.setText(this.lblErreurGrp.getText() + " TP ");
			}
		}
		

		List<HeureCours> lstHeureCours = this.ctrl.getModele().getHeureCoursByModule(module.getId(),
				module.getIdAnnee());
		// Afficher message d'erreur heures
		for (HeureCours heureCours : lstHeureCours) {
			TypeCours tc = this.hmTypeCours.get(heureCours.getIdTypeCours());
			if (tc.getNom().equals("CM") && nbHeureCM != heureCours.getHeure()) {
				this.lblErreurPN.setText(this.lblErreurPN.getText() + " CM ");
			}

			if (tc.getNom().equals("TD") && nbHeureTD != heureCours.getHeure()) {
				this.lblErreurPN.setText(this.lblErreurPN.getText() + " TD ");
			}

			if (tc.getNom().equals("TP") && nbHeureTP != heureCours.getHeure()) {
				this.lblErreurPN.setText(this.lblErreurPN.getText() + " TP ");
			}
			
			if (tc.getNom().equals("Tut") && nbHeureTut != heureCours.getHeure()) {
				this.lblErreurPN.setText(this.lblErreurPN.getText() + " Tut ");
			}
			
			if (tc.getNom().equals("SAE") && nbHeureTut != heureCours.getHeure()) {
				this.lblErreurPN.setText(this.lblErreurPN.getText() + " SAE ");
			}
			
			if (tc.getNom().equals("REH") && nbHeureTut != heureCours.getHeure()) {
				this.lblErreurPN.setText(this.lblErreurPN.getText() + " REH ");
			}
			
			if (tc.getNom().equals("HP") && nbHeureTut != heureCours.getHeure()) {
				this.lblErreurPN.setText(this.lblErreurPN.getText() + " HP ");
			}
		}

		borderLayoutErrTBV.setCenter(this.tbV);
		borderLayoutErrTBV.setPadding(new Insets(0, 20, 20, 0));

		// Mettre le vbox à gauche
		borderRecapEntree.setCenter(stackPane);

		// Créer une tableView Recap
		TableView<RecapInterventionIHM> tbVRecap = new TableView<>();
		tbVRecap.setEditable(false);
		tbVRecap.setPrefHeight(103);
		tbVRecap.setPrefWidth(150);

		String[] col = new String[lstHeureCours.size() + 2];
		col[0] = "info";
		Map<Integer, List<String>> hmTcHc = new HashMap<>();
		int cpt = 1;
		for (HeureCours hc : lstHeureCours) {
			int idTc = hc.getIdTypeCours();
			col[cpt++] = this.hmTypeCours.get(idTc).getNom();
			if (hmTcHc.get(hc.getIdTypeCours()) == null)
				hmTcHc.put(idTc, new ArrayList<>());
			double hTotal = hc.gethParSemaine() * hc.getNbSemaine();
			hmTcHc.get(idTc).add(hTotal + "");
			int nbGroupe = 1;
			TypeCours tc = this.hmTypeCours.get(idTc);
			switch (tc.getNom()) {
				case "TP" -> nbGroupe = this.semestre.getNbGTP();
				case "CM" -> nbGroupe = this.semestre.getNbGCM();
				case "TD" -> nbGroupe = this.semestre.getNbGTD();
				case "HP" -> nbGroupe = this.semestre.getNbGTD(); 	
			}
			hmTcHc.get(idTc).add(hTotal * nbGroupe * tc.getCoefficient() + "");
			double sommeAffecte = 0.0;
			for (InterventionIHM i : olInterventionIHMs) {
				if (i.getType().equals(tc.getNom()))
					sommeAffecte += Double.parseDouble(i.getReelles());
			}
			hmTcHc.get(idTc).add(sommeAffecte + "");

		}
		col[cpt] = "somme";

		if (tbVRecap.getColumns().size() < col.length) {
			for (String colonne : col) {
				TableColumn<RecapInterventionIHM, String> tbcl = new TableColumn<>(colonne);
				tbcl.setCellValueFactory(new PropertyValueFactory<>(colonne.toLowerCase()));
				if (colonne.equals("info")) tbcl.prefWidthProperty().bind(tbVRecap.widthProperty().multiply(0.35));
				else if (colonne.equals("somme")) tbcl.prefWidthProperty().bind(tbVRecap.widthProperty().multiply(0.15));
				else tbcl.prefWidthProperty().bind(tbVRecap.widthProperty().multiply((0.5 / (col.length - 2)) - 0.002*col.length));
				tbVRecap.getColumns().add(tbcl);
			}
		}

		ObservableList<RecapInterventionIHM> olRecapInterventionIHMs = FXCollections.observableArrayList();
		RecapInterventionIHM recap1 = new RecapInterventionIHM();
		recap1.setInfo("Total par étudiant");
		RecapInterventionIHM recap2 = new RecapInterventionIHM();
		recap2.setInfo("Total promo (eqtd)");
		RecapInterventionIHM recap3 = new RecapInterventionIHM();
		recap3.setInfo("Total affecté (eqtd)");
		
		this.totalAffecte = 0.0;
		this.totalPromo = 0.0;
		double totalPn = 0.0;
			
		for (Integer i : hmTcHc.keySet()) {
			List<String> lStrings = hmTcHc.get(i);
			
			switch (this.hmTypeCours.get(i).getNom()) {
				case "TD" -> {
					recap1.setTd(lStrings.get(0));
					recap2.setTd(lStrings.get(1));
					recap3.setTd(lStrings.get(2));
				}
				case "TP" -> {
					recap1.setTp(lStrings.get(0));
					recap2.setTp(lStrings.get(1));
					recap3.setTp(lStrings.get(2));
				}
				case "CM" -> {
					recap1.setCm(lStrings.get(0));
					recap2.setCm(lStrings.get(1));
					recap3.setCm(lStrings.get(2));
				}
				case "REH" -> {
					recap1.setReh(lStrings.get(0));
					recap2.setReh(lStrings.get(1));
					recap3.setReh(lStrings.get(2));
				}
				case "SAE" -> {
					recap1.setSae(lStrings.get(0));
					recap2.setSae(lStrings.get(1));
					recap3.setSae(lStrings.get(2));
				}
				case "Tut" -> {
					recap1.setTut(lStrings.get(0));
					recap2.setTut(lStrings.get(1));
					recap3.setTut(lStrings.get(2));
				}
				case "HP" -> {
					recap1.setHp(lStrings.get(0));
					recap2.setHp(lStrings.get(1));
					recap3.setHp(lStrings.get(2));
				}

			}
			totalPn += Double.parseDouble(lStrings.get(0));
			this.totalPromo += Double.parseDouble(lStrings.get(1));
			this.totalAffecte += Double.parseDouble(lStrings.get(2));

			recap1.setSomme(totalPn+"");
			recap2.setSomme(this.totalPromo+"");
			recap3.setSomme(this.totalAffecte+"");

			
		}

		olRecapInterventionIHMs.add(recap1);
		olRecapInterventionIHMs.add(recap2);
		olRecapInterventionIHMs.add(recap3);

		tbVRecap.setItems(olRecapInterventionIHMs);

		borderRecapEntree.setTop(tbVRecap);
		borderRecapEntree.setPadding(new Insets(40, 10, 0, 10));

		borderPaneCentral.setLeft(borderRecapEntree);
		
		if (this.lblErreurPN.getText().equals("PROGRAMME NATIONAL : ")) this.lblErreurPN.setText("");

		borderPaneCentral.setCenter(borderLayoutErrTBV);
		StackPane popupLayout = new StackPane();
		popupLayout.getChildren().add(borderPaneCentral);
		popupLayout.getStylesheets().add(ResourceManager.STYLESHEET.toExternalForm());
		Scene popupScene = new Scene(popupLayout, 1300, 650);
		this.popupStage.setScene(popupScene);
	}

	@Override
	public void handle(ActionEvent action) {
		
		if (action.getSource() == this.rbValider) {
			this.module.setValid(this.rbValider.isSelected());
			this.ctrl.getModele().updateModule(this.module);
			return;
		}
		
		RadioButton selectedRadioButton = (RadioButton) this.group.getSelectedToggle();
		
		if (action.getSource() == this.btnAjouter) {
			Intervenant interSelected = this.chBoxIntervenants.getSelectionModel().getSelectedItem();
			if (interSelected == null) {
				this.ctrl.getVue().afficherNotification("Ajouter une intervention", "Sélectionnez un intervenant", ControleurIHM.Notification.ERREUR);
				return;
			}
			
			if (selectedRadioButton == null) {
				this.ctrl.getVue().afficherNotification("Ajouter une intervention", "Sélectionnez un type de module", ControleurIHM.Notification.ERREUR);
				return;
			}
			
			if ((this.nomTypeModule.equals("normal") || this.nomTypeModule.equals("PPP")) && !selectedRadioButton.getText().equals("HP") && this.tfNbGroupes.getText().isEmpty()) {
				this.ctrl.getVue().afficherNotification("Ajouter une intervention", "Il faut rentrer un nombre de groupes", ControleurIHM.Notification.ERREUR);
				return;
			}
			
			if ((this.nomTypeModule.equals("normal") || this.nomTypeModule.equals("PPP")) && !selectedRadioButton.getText().equals("HP") && this.tfNbSemaines.getText().isEmpty()) {
				this.ctrl.getVue().afficherNotification("Ajouter une intervention",
						"Il faut rentrer un nombre de semaines", ControleurIHM.Notification.ERREUR);
				return;
			}
			
			int idTypeCours = -1;
			for (TypeCours typeCours : this.hmTypeCours.values()) {
				if (typeCours.getNom().equals(selectedRadioButton.getText())) {
					idTypeCours = typeCours.getId();
				}
			}

			int nbSemaines = 0;
			int nbGroupes = 0;
			try {
				nbSemaines = Integer.parseInt(this.tfNbSemaines.getText());
				nbGroupes = Integer.parseInt(this.tfNbGroupes.getText());
			} catch (Exception e) {
			}

			HeureCours hc = null;
			TypeCours tc = null;

			for (TypeCours t : this.ctrl.getModele().getHmTypeCours().values()) {
				if (t.getNom().equals(selectedRadioButton.getText()))
					tc = t;
			}
			if (tc != null) {
				for (HeureCours h : this.ctrl.getModele().getHeureCoursByModule(this.module.getId(),
						this.module.getIdAnnee())) {
					if (h.getIdTypeCours() == tc.getId())
						hc = h;
				}
			}

			if (!selectedRadioButton.getText().equals("HP") && hc != null && nbSemaines > hc.getNbSemaine()) {
				this.ctrl.getVue().afficherNotification("Ajouter une intervention",
						"Le nombre de semaines est supérieur au nombre de semaines du module (" + hc.getNbSemaine() + ")",
						ControleurIHM.Notification.ERREUR);
				return;
			}

			int nbGroupeTc = 0;
			switch (selectedRadioButton.getText()) {
				case "TP" -> nbGroupeTc = this.semestre.getNbGTP();
				case "TD" -> nbGroupeTc = this.semestre.getNbGTD();
				case "CM" -> nbGroupeTc = this.semestre.getNbGCM();
			}

			if (!selectedRadioButton.getText().equals("HP") && nbGroupes > nbGroupeTc) {
				this.ctrl.getVue().afficherNotification("Ajouter une intervention",
						"Le nombre de groupe est supérieur au nombre de groupe du module (" + nbGroupeTc + ")",
						ControleurIHM.Notification.ERREUR);
				return;
			}

			if (this.nomTypeModule.equals("stage") || this.nomTypeModule.equals("SAE") || selectedRadioButton.getText().equals("HP")) {
				if (this.tfNbHeures.getText().isEmpty()) {
					this.ctrl.getVue().afficherNotification("Ajouter une intervention",
							"Il faut saisir un nombre d'heures", ControleurIHM.Notification.ERREUR);
					return;
				}
				this.ctrl.getModele().ajouterIntervention(interSelected.getId(), this.module.getId(), idTypeCours, 0,
						this.tfCommentaire.getText(), Integer.parseInt(this.tfNbHeures.getText()));
			} else {
				this.ctrl.getModele().ajouterIntervention(interSelected.getId(), this.module.getId(), idTypeCours,
						nbSemaines, this.tfCommentaire.getText(), Integer.parseInt(this.tfNbGroupes.getText()));
			}
			
			
			
			this.maj();
		}
		
		
		if (this.nomTypeModule.equals("SAE") || this.nomTypeModule.equals("stage") || ( selectedRadioButton != null && selectedRadioButton.getText().equals("HP"))) {
			String txtH = "";
			if (this.tfNbHeures != null) txtH = this.tfNbHeures.getText();
			this.gridEntree.getChildren().clear();
			this.tfNbHeures = new TextField();
			this.tfNbHeures.setPrefWidth(5 * 7);
			this.tfNbHeures.textProperty().addListener(this);
			this.tfNbHeures.setText(txtH);

			this.gridEntree.add(new Label("Nombre d'heures : "), 0, 0);
			this.gridEntree.add(this.tfNbHeures, 1, 0);
			return;
		}

		else if ( selectedRadioButton != null && !selectedRadioButton.getText().equals("HP")) {
			this.gridEntree.getChildren().clear();
			gridEntree.add(new Label("Nombre de semaines : "), 0, 0);
			gridEntree.add(this.tfNbSemaines, 1, 0);
			gridEntree.add(new Label("Nombre de groupes : "), 0, 1);
			gridEntree.add(this.tfNbGroupes, 1, 1);
			return;
		}
		
		if (action.getSource() instanceof Button btn) {
			this.ctrl.getVue().popupValider();
			if (ControleurIHM.bIsValidate)
			this.ctrl.getModele().supprimerIntervention(Integer.parseInt(btn.getId()));
		}
		this.maj();
	}

	@Override
	public void changed(ObservableValue<? extends String> observable, String oldString, String newString) {
		if (this.nomTypeModule.equals("PPP") || this.nomTypeModule.equals("normal")) {
			if (!this.tfNbGroupes.getText().matches(Modele.REGEX_INT))
				this.tfNbGroupes.setText(oldString);
			if (!this.tfNbSemaines.getText().matches(Modele.REGEX_INT))
				this.tfNbSemaines.setText(oldString);
		}
		if (!this.tfNbHeures.getText().matches(Modele.REGEX_INT))
			this.tfNbHeures.setText(oldString);
	}

	public double getTotalAffecte() {
		return totalAffecte;
	}

	public double getTotalPromo() {
		return totalPromo;
	}
	
	
}