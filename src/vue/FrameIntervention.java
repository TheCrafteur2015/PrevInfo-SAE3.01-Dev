package vue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import controleur.Controleur;
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
		if (afficherPopup)
			this.popupStage.showAndWait();
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
		this.gridEntree.setHgap(5);
		this.gridEntree.setVgap(5);
		this.gridEntree.setAlignment(Pos.CENTER);

		FlowPane flowrButton = new FlowPane();
		flowrButton.setAlignment(Pos.CENTER);

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

		if (this.nomTypeModule.equals("Ressource") || this.nomTypeModule.equals("PPP")) {
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

		if (this.nomTypeModule.equals("SAE") || this.nomTypeModule.equals("Stage")) {
			// REH, SAE, HP
			RadioButton rb1 = new RadioButton("Tut");
			rb1.addEventHandler(ActionEvent.ACTION, this);
			RadioButton rb3 = new RadioButton();
			if (this.nomTypeModule.equals("SAE"))
				rb3.setText("SAE");
			if (this.nomTypeModule.equals("Stage"))
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
		this.btnAjouter.setId("Ajouter");

		// Creer radioButton valider
		this.rbValider = new RadioButton("Valider");
		this.rbValider.setSelected(this.module.isValid());
		this.rbValider.addEventHandler(ActionEvent.ACTION, this);

		if (this.nomTypeModule.equals("Ressource") || this.nomTypeModule.equals("PPP")) {
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
		} else {
			this.tfNbHeures = new TextField();
			this.tfNbHeures.setPrefWidth(5 * 7);
			this.gridEntree.add(new Label("Nombre d'heures : "), 0, 0);
			this.gridEntree.add(this.tfNbHeures, 1, 0);
		}

		GridPane gridCommentaire = new GridPane();
		gridCommentaire.setHgap(5);
		gridCommentaire.setAlignment(Pos.CENTER);

		this.tfCommentaire = new TextField();
		this.tfCommentaire.setPrefSize(30 * 7, 20);
		this.tfCommentaire.setAlignment(Pos.CENTER);

		gridCommentaire.add(new Label("Commentaire : "), 0, 0);
		gridCommentaire.add(this.tfCommentaire, 0, 1);

		vbox.getChildren().addAll(rbValider, gridIntervenant, gridEntree, gridCommentaire, flowrButton,
				this.btnAjouter);

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
		// this.ctrl.getModele().getHmInterventionsModule(idModule);
		this.hmIntervention = new HashMap<>();
		for (Intervention i : hmInterventionTmp.values())
			if (i.getIdModule() == module.getId())
				this.hmIntervention.put(i.getIdIntervention(), i);

		String[] colonnes = { "Prenom", "Nom", "semaines", "groupes", "type", "heures", "reelles", "commentaire",
				"supprimer" };

		if (!this.nomTypeModule.equals("Ressource") && !this.nomTypeModule.equals("PPP"))
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

			if (this.nomTypeModule.equals("Ressource") || this.nomTypeModule.equals("PPP")) {
				if (typeCours.getNom().equals("HP")) {
					olInterventionIHMs.add(new InterventionIHM(intervenant.getPrenom(), intervenant.getNom(),
							"" + "", "" + "",
							typeCours.getNom(), i.getNbGroupe() + "", i.getNbGroupe() * typeCours.getCoefficient() + "",
							i.getCommentaire(), btnSup));
				} else {
					olInterventionIHMs.add(new InterventionIHM(intervenant.getPrenom(), intervenant.getNom(),
							i.getNbSemaines() + "", i.getNbGroupe() + "",
							typeCours.getNom(), String.format("%.2f", heureCours).replace(",", "."),
							String.format("%.2f", heureCoursReelles).replace(",", ".") + "", i.getCommentaire(),
							btnSup));
				}
			} else {
				olInterventionIHMs.add(new InterventionIHM(intervenant.getPrenom(), intervenant.getNom(),
						typeCours.getNom(), i.getNbGroupe() + "",
						i.getNbGroupe() * typeCours.getCoefficient() + "" + "", i.getCommentaire(), btnSup));
			}
		}

		this.tbV = new TableView<>();
		// this.tbV.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		this.tbV.getStyleClass().add("tbV-module");

		if (tbV.getColumns().size() < colonnes.length) {
			for (String colonne : colonnes) {
				if (colonne == null)
					continue;
				TableColumn<InterventionIHM, String> tbcl = new TableColumn<>(colonne);
				tbcl.setStyle("-fx-alignment: CENTER;");
				tbcl.getStyleClass().add("col");
				tbcl.setCellValueFactory(new PropertyValueFactory<>(colonne.toLowerCase()));
				tbcl.setResizable(false);
				tbcl.setReorderable(false);
				tbV.getColumns().add(tbcl);
				if (colonne.equals("commentaire"))
					tbcl.prefWidthProperty().bind(tbV.widthProperty().multiply(0.2));
				else {
					if (this.nomTypeModule.equals("Ressource") || this.nomTypeModule.equals("PPP"))
						tbcl.prefWidthProperty()
								.bind(tbV.widthProperty().multiply(0.79 / (colonnes.length - 1) - 0.0025));
					else
						tbcl.prefWidthProperty()
								.bind(tbV.widthProperty().multiply(0.79 / (colonnes.length - 3) - 0.0025));
				}
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
			int nbGroupes = 0;
			try {
				nbGroupes = Integer
						.parseInt(interventionIHM.getGroupes().isEmpty() ? "0" : interventionIHM.getGroupes());
			} catch (Exception e) {
			}
			double heuresInter = Double.parseDouble(interventionIHM.getHeures());
			switch (interventionIHM.getType()) {
				case "CM" -> {
					nbGroupeCM += nbGroupes;
					nbHeureCM += heuresInter;
				}
				case "TD" -> {
					nbGroupeTD += nbGroupes;
					nbHeureTD += heuresInter;
				}
				case "TP" -> {
					nbGroupeTP += nbGroupes;
					nbHeureTP += heuresInter;
				}
				case "Tut" -> nbHeureTut += heuresInter;
				case "SAE" -> nbHeureSAE += heuresInter;
				case "REH" -> nbHeureREH += heuresInter;
				case "HP" -> nbHeureHP += heuresInter;
			}
		}

		List<HeureCours> lstHeureCours = this.ctrl.getModele().getHeureCoursByModule(module.getId(),
				module.getIdAnnee());

		HeureCours heureCoursCM = null;
		HeureCours heureCoursTD = null;
		HeureCours heureCoursTP = null;

		for (HeureCours heureCours : lstHeureCours) {
			switch (heureCours.getIdTypeCours()) {
				case 1 -> heureCoursTD = heureCours; /* TD */
				case 2 -> heureCoursTP = heureCours; /* TP */
				case 3 -> heureCoursCM = heureCours; /* CM */
			}
		}

		// Afficher message d'erreur groupes
		if (!this.nomTypeModule.equals("SAE") && !this.nomTypeModule.equals("Stage")) {
			if (nbGroupeCM != this.semestre.getNbGCM() || nbGroupeTD != this.semestre.getNbGTD()
					|| nbGroupeTP != this.semestre.getNbGTP()) {
				this.lblErreurGrp.setText("ERREUR GROUPE :");
				if (nbGroupeCM != this.semestre.getNbGCM() && heureCoursCM.getHeure() != 0)
					this.lblErreurGrp.setText(this.lblErreurGrp.getText() + " CM ");
				if (nbGroupeTD != this.semestre.getNbGTD() && heureCoursTD.getHeure() != 0)
					this.lblErreurGrp.setText(this.lblErreurGrp.getText() + " TD ");
				if (nbGroupeTP != this.semestre.getNbGTP() && heureCoursTP.getHeure() != 0)
					this.lblErreurGrp.setText(this.lblErreurGrp.getText() + " TP ");
				if (this.lblErreurGrp.getText().equals("ERREUR GROUPE :"))
					this.lblErreurGrp.setText("");
			}
		}

		// Afficher message d'erreur heures
		for (HeureCours heureCours : lstHeureCours) {
			TypeCours tc = this.hmTypeCours.get(heureCours.getIdTypeCours());
			if (tc.getNom().equals("CM") && nbHeureCM / this.semestre.getNbGCM() != heureCours.getHeure())
				this.lblErreurPN.setText(this.lblErreurPN.getText() + " CM ");
			else if (tc.getNom().equals("TD") && nbHeureTD / this.semestre.getNbGTD() != heureCours.getHeure())
				this.lblErreurPN.setText(this.lblErreurPN.getText() + " TD ");
			else if (tc.getNom().equals("TP") && nbHeureTP / this.semestre.getNbGTP() != heureCours.getHeure())
				this.lblErreurPN.setText(this.lblErreurPN.getText() + " TP ");
			else if (tc.getNom().equals("Tut") && nbHeureTut != heureCours.getHeure())
				this.lblErreurPN.setText(this.lblErreurPN.getText() + " Tut ");
			else if (tc.getNom().equals("SAE") && nbHeureSAE != heureCours.getHeure())
				this.lblErreurPN.setText(this.lblErreurPN.getText() + " SAE ");
			else if (tc.getNom().equals("REH") && nbHeureREH != heureCours.getHeure())
				this.lblErreurPN.setText(this.lblErreurPN.getText() + " REH ");
			else if (tc.getNom().equals("HP") && nbHeureHP / this.semestre.getNbGTD() != heureCours.getHeure())
				this.lblErreurPN.setText(this.lblErreurPN.getText() + " HP ");
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
		tbVRecap.getStyleClass().add("tbV-module");

		String[] col = new String[lstHeureCours.size() + 2];
		col[0] = "info";
		Map<Integer, List<String>> hmTcHc = new HashMap<>();
		int cpt = 1;
		for (HeureCours hc : lstHeureCours) {
			int idTc = hc.getIdTypeCours();
			col[cpt++] = this.hmTypeCours.get(idTc).getNom();
			if (hmTcHc.get(hc.getIdTypeCours()) == null)
				hmTcHc.put(idTc, new ArrayList<>());
			double hTotal = 0.0;
			TypeCours tc = this.hmTypeCours.get(idTc);
			if (tc.getNom().equals("TP") || tc.getNom().equals("TD") || tc.getNom().equals("CM"))
				hTotal = hc.gethParSemaine() * hc.getNbSemaine();
			else
				hTotal = hc.getHeure();
			hmTcHc.get(idTc).add(hTotal + "");
			int nbGroupe = 1;
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
				tbcl.setResizable(false);
				tbcl.setReorderable(false);
				if (colonne.equals("info"))
					tbcl.prefWidthProperty().bind(tbVRecap.widthProperty().multiply(0.26));
				else if (colonne.equals("somme")) {
					tbcl.prefWidthProperty().bind(tbVRecap.widthProperty().multiply(0.15));
					tbcl.setStyle(tbcl.getStyle() + ";-fx-alignment:BASELINE-LEFT");
				} else {
					tbcl.prefWidthProperty()
							.bind(tbVRecap.widthProperty().multiply((0.59 / (col.length - 2)) - 0.002 * col.length));
					tbcl.setStyle(tbcl.getStyle() + ";-fx-alignment:BASELINE-LEFT");

				}

				tbVRecap.getColumns().add(tbcl);
			}
		}

		ObservableList<RecapInterventionIHM> olRecapInterventionIHMs = FXCollections.observableArrayList();
		RecapInterventionIHM recap1 = new RecapInterventionIHM();
		recap1.setInfo("Par étudiant");
		RecapInterventionIHM recap2 = new RecapInterventionIHM();
		recap2.setInfo("Promo (eqtd)");
		RecapInterventionIHM recap3 = new RecapInterventionIHM();
		recap3.setInfo("Affecté (eqtd)");

		this.totalAffecte = 0.0;
		this.totalPromo = 0.0;
		double totalPn = 0.0;

		for (Integer i : hmTcHc.keySet()) {
			List<String> lStrings = hmTcHc.get(i);

			switch (this.hmTypeCours.get(i).getNom()) {
				case "TD" -> {
					recap1.setTd(String.format("%.2f", Double.parseDouble(lStrings.get(0))));
					recap2.setTd(String.format("%.2f", Double.parseDouble(lStrings.get(1))));
					recap3.setTd(String.format("%.2f", Double.parseDouble(lStrings.get(2))));
				}
				case "TP" -> {
					recap1.setTp(String.format("%.2f", Double.parseDouble(lStrings.get(0))));
					recap2.setTp(String.format("%.2f", Double.parseDouble(lStrings.get(1))));
					recap3.setTp(String.format("%.2f", Double.parseDouble(lStrings.get(2))));
				}
				case "CM" -> {
					recap1.setCm(String.format("%.2f", Double.parseDouble(lStrings.get(0))));
					recap2.setCm(String.format("%.2f", Double.parseDouble(lStrings.get(1))));
					recap3.setCm(String.format("%.2f", Double.parseDouble(lStrings.get(2))));
				}
				case "REH" -> {
					recap1.setReh(String.format("%.2f", Double.parseDouble(lStrings.get(0))));
					recap2.setReh(String.format("%.2f", Double.parseDouble(lStrings.get(1))));
					recap3.setReh(String.format("%.2f", Double.parseDouble(lStrings.get(2))));
				}
				case "SAE" -> {
					recap1.setSae(String.format("%.2f", Double.parseDouble(lStrings.get(0))));
					recap2.setSae(String.format("%.2f", Double.parseDouble(lStrings.get(1))));
					recap3.setSae(String.format("%.2f", Double.parseDouble(lStrings.get(2))));
				}
				case "Tut" -> {
					recap1.setTut(String.format("%.2f", Double.parseDouble(lStrings.get(0))));
					recap2.setTut(String.format("%.2f", Double.parseDouble(lStrings.get(1))));
					recap3.setTut(String.format("%.2f", Double.parseDouble(lStrings.get(2))));
				}
				case "HP" -> {
					recap1.setHp(String.format("%.2f", Double.parseDouble(lStrings.get(0))));
					recap2.setHp(String.format("%.2f", Double.parseDouble(lStrings.get(1))));
					recap3.setHp(String.format("%.2f", Double.parseDouble(lStrings.get(2))));
				}
			}
			totalPn += Double.parseDouble(lStrings.get(0));
			this.totalPromo += Double.parseDouble(lStrings.get(1));
			this.totalAffecte += Double.parseDouble(lStrings.get(2));

			recap1.setSomme(String.format("%.2f", totalPn));
			recap2.setSomme(String.format("%.2f", this.totalPromo));
			recap3.setSomme(String.format("%.2f", this.totalAffecte));
			
		}

		olRecapInterventionIHMs.addAll(recap1, recap2, recap3);

		tbVRecap.setItems(olRecapInterventionIHMs);

		borderRecapEntree.setTop(tbVRecap);
		borderRecapEntree.setPadding(new Insets(40, 10, 0, 10));

		borderPaneCentral.setLeft(borderRecapEntree);

		if (this.lblErreurPN.getText().equals("PROGRAMME NATIONAL : "))
			this.lblErreurPN.setText("");

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

		if (action.getSource() instanceof Button btn && !btn.getId().equals("Ajouter")) {
			this.ctrl.getVue().popupValider();
			if (ControleurIHM.bIsValidate)
				this.ctrl.getModele().supprimerIntervention(Integer.parseInt(btn.getId()));
			this.maj();
		}

		RadioButton selectedRadioButton = (RadioButton) this.group.getSelectedToggle();

		if (action.getSource() == this.btnAjouter) {
			Intervenant interSelected = this.chBoxIntervenants.getSelectionModel().getSelectedItem();
			if (interSelected == null) {
				this.ctrl.getVue().afficherNotification("Ajouter une intervention", "Sélectionnez un intervenant",
						ControleurIHM.Notification.ERREUR);
				return;
			}

			if (selectedRadioButton == null) {
				this.ctrl.getVue().afficherNotification("Ajouter une intervention", "Sélectionnez un type de module",
						ControleurIHM.Notification.ERREUR);
				return;
			}

			if ((this.nomTypeModule.equals("Ressource") || this.nomTypeModule.equals("PPP"))
					&& !selectedRadioButton.getText().equals("HP") && this.tfNbGroupes.getText().isEmpty()) {
				this.ctrl.getVue().afficherNotification("Ajouter une intervention",
						"Il faut saisir un nombre de groupes", ControleurIHM.Notification.ERREUR);
				return;
			}

			if ((this.nomTypeModule.equals("Ressource") || this.nomTypeModule.equals("PPP"))
					&& !selectedRadioButton.getText().equals("HP") && this.tfNbSemaines.getText().isEmpty()) {
				this.ctrl.getVue().afficherNotification("Ajouter une intervention",
						"Il faut saisir un nombre de semaines", ControleurIHM.Notification.ERREUR);
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

			for (TypeCours t : this.ctrl.getModele().getHmTypeCours().values())
				if (t.getNom().equals(selectedRadioButton.getText()))
					tc = t;

			if (tc != null) {
				for (HeureCours h : this.ctrl.getModele().getHeureCoursByModule(this.module.getId(),
						this.module.getIdAnnee())) {
					if (h.getIdTypeCours() == tc.getId())
						hc = h;
				}
			}

			if (!selectedRadioButton.getText().equals("HP") && hc != null && nbSemaines > hc.getNbSemaine()) {
				this.ctrl.getVue().afficherNotification("Ajouter une intervention",
						"Le nombre de semaines est supérieur au nombre de semaines du module (" + hc.getNbSemaine()
								+ ")",
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

			if (this.nomTypeModule.equals("Stage") || this.nomTypeModule.equals("SAE")
					|| selectedRadioButton.getText().equals("HP")) {
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

		if (this.nomTypeModule.equals("SAE") || this.nomTypeModule.equals("Stage")
				|| (selectedRadioButton != null && selectedRadioButton.getText().equals("HP"))) {
			String txtH = "";
			if (this.tfNbHeures != null)
				txtH = this.tfNbHeures.getText();
			this.gridEntree.getChildren().clear();
			this.tfNbHeures = new TextField();
			this.tfNbHeures.setPrefWidth(5 * 7);
			this.tfNbHeures.textProperty().addListener(this);
			this.tfNbHeures.setText(txtH);

			this.gridEntree.add(new Label("Nombre d'heures : "), 0, 0);
			this.gridEntree.add(this.tfNbHeures, 1, 0);
			return;
		} else if (selectedRadioButton != null && !selectedRadioButton.getText().equals("HP")) {
			this.gridEntree.getChildren().clear();
			gridEntree.add(new Label("Nombre de semaines : "), 0, 0);
			gridEntree.add(this.tfNbSemaines, 1, 0);
			gridEntree.add(new Label("Nombre de groupes : "), 0, 1);
			gridEntree.add(this.tfNbGroupes, 1, 1);
			return;
		}
		this.maj();
	}

	@Override
	public void changed(ObservableValue<? extends String> observable, String oldString, String newString) {
		if (this.nomTypeModule.equals("PPP") || this.nomTypeModule.equals("Ressource")) {
			if (!this.tfNbGroupes.getText().matches(Modele.REGEX_INT))
				this.tfNbGroupes.setText(oldString);
			if (!this.tfNbSemaines.getText().matches(Modele.REGEX_INT))
				this.tfNbSemaines.setText(oldString);
		}
		if (!this.tfNbHeures.getText().matches(Modele.REGEX_INT))
			this.tfNbHeures.setText(oldString);
	}

	public double getTotalAffecte() {
		return this.totalAffecte;
	}

	public double getTotalPromo() {
		return this.totalPromo;
	}

}