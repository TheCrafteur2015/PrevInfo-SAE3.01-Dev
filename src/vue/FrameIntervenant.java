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
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Text;
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
import vue.ControleurIHM.Notification;

public class FrameIntervenant implements EventHandler<Event>, ChangeListener<String> {
	
	private Controleur ctrl;
	
	private AnchorPane centerPaneAccueil;
	
	private TableView<IntervenantIHM> tableViewIntervenant;
	
	private Button btnParamIntervenants;
	private Button btnConfirmerIntervenant;
	
	private TextField tfPrenom;
	private TextField tfNom;
	private TextField tfEmail;
	private TextField tfHMin;
	private TextField tfHMax;
	
	private Button btnParamCategorie;
	
	@SuppressWarnings("unused")
	private FrameParamCategorie frameParamCategorie;
	
	private ChoiceBox<Categorie> choiceBoxCategorie;
	
	private Intervenant modifIntervenant;
	
	public FrameIntervenant(Controleur ctrl, AnchorPane centerPaneAccueil) {
		this.ctrl = ctrl;
		this.centerPaneAccueil = centerPaneAccueil;
		this.tableViewIntervenant = new TableView<>();
		this.tableViewIntervenant.setPlaceholder(new Label("Aucun intervenant dans la table !"));
		this.tableViewIntervenant.setId("my-table");
		this.tableViewIntervenant.setEditable(true);
		this.modifIntervenant = null;
		this.centerPaneAccueil.getStylesheets().add(ResourceManager.STYLESHEET.toExternalForm());
		this.init();
	}
	
	public void init() {
		this.centerPaneAccueil.getChildren().clear();
		
		// Creer le tableau
		String[] colonnes = { "Infos", "Prenom", "Nom", "Categorie", "Email", "Supprimer" };
		
		if (this.tableViewIntervenant.getColumns().size() < 6) {
			for (String colonne : colonnes) {
				TableColumn<IntervenantIHM, String> tbcl = new TableColumn<>(colonne);
				tbcl.setCellValueFactory(new PropertyValueFactory<>(colonne.toLowerCase()));
				tbcl.setReorderable(false);
				if (colonne.equals("Email")) {
					tbcl.prefWidthProperty().bind(this.tableViewIntervenant.widthProperty().multiply(0.23));
				} else if (colonne != "Infos" && colonne != "Supprimer") {
					tbcl.prefWidthProperty().bind(this.tableViewIntervenant.widthProperty().multiply(0.18));
				} else {
					tbcl.prefWidthProperty().bind(this.tableViewIntervenant.widthProperty().multiply(0.1055));
				}
				tbcl.setResizable(false);
				
				if (colonne.equals("Infos"))
					tbcl.setStyle("-fx-alignment: CENTER;");
				if (colonne.equals("Supprimer"))
					tbcl.setStyle("-fx-alignment: CENTER;");
				
				this.tableViewIntervenant.getColumns().add(tbcl);
				this.tableViewIntervenant.getStyleClass().add("noheader");
			}
		}
		
		this.tableViewIntervenant.setPrefHeight(520);
		this.tableViewIntervenant.setPrefWidth(1100);
		
		this.tableViewIntervenant.setRowFactory(tv -> new TableRow<IntervenantIHM>() {
			@Override
			protected void updateItem(IntervenantIHM item, boolean empty) {
				super.updateItem(item, empty);
				this.getStyleClass().remove("erreur-row");
				this.getStyleClass().remove("normal-row");
				if (item != null && item.getErreurInter() != null && !item.getErreurInter().isEmpty()) {
					this.getStyleClass().add("erreur-row");
				} else {
					this.getStyleClass().add("normal-row");
				}
				
			}
			
			{
				this.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						final int index = getIndex();
						final FrameIntervenant frame = FrameIntervenant.this;
						double x = event.getScreenX();
						if (index >= 0 && index < frame.tableViewIntervenant.getItems().size() && (x < 578 || x > 632)
								&& (x < 1498 || x > 1560)
								&& frame.tableViewIntervenant.getSelectionModel().isSelected(index)) {
							frame.tableViewIntervenant.getSelectionModel().clearSelection();
							frame.btnParamIntervenants.setText("Ajouter un intervenant");
							event.consume();
						} else if ((x < 578 || x > 632) && (x < 1498 || x > 1560)) {
							frame.btnParamIntervenants.setText("Paramètrer un Intervenant");
						}
					}
				});
			}
		});
		
		AnchorPane.setTopAnchor(this.tableViewIntervenant, 20.0);
		AnchorPane.setLeftAnchor(this.tableViewIntervenant, 20.0);
		
		this.btnParamIntervenants = new Button("Ajouter un Intervenant");
		this.btnParamIntervenants.setStyle("-fx-background-radius: 100");
		
		this.btnParamIntervenants.setPrefSize(250, 40);
		this.btnParamIntervenants.setId("btnParamIntervenants");
		this.btnParamIntervenants.addEventHandler(ActionEvent.ACTION, this);
		if (this.ctrl.getModele().getHmCategories().size() == 0)
			this.btnParamIntervenants.setDisable(true);
		
		AnchorPane.setTopAnchor(this.btnParamIntervenants, 570.0);
		AnchorPane.setLeftAnchor(this.btnParamIntervenants, (this.centerPaneAccueil.getWidth() / 2) + 50);
		
		this.btnParamCategorie = new Button("Paramétrer les catégories");
		this.btnParamCategorie.setStyle("-fx-background-radius: 100");
		this.btnParamCategorie.setPrefSize(250, 40);
		this.btnParamCategorie.addEventHandler(ActionEvent.ACTION, this);
		
		AnchorPane.setTopAnchor(this.btnParamCategorie, 570.0);
		AnchorPane.setLeftAnchor(this.btnParamCategorie, (this.centerPaneAccueil.getWidth() / 2) - 300);
		
		this.centerPaneAccueil.getChildren().addAll(this.tableViewIntervenant, this.btnParamIntervenants, this.btnParamCategorie);
		
		this.majTableIntervenant();
	}

	public void majTableIntervenant() {
		Map<Integer, Intervenant> hmInter = this.ctrl.getModele().getHmIntervenants();
		ObservableList<IntervenantIHM> lst = FXCollections.observableArrayList();
		
		for (Intervenant i : hmInter.values()) {
			Button infoButton = this.getInfoButton();
			infoButton.setId("Info-" + i.getId());
			Button supButton = ResourceManager.getSupButton();
			supButton.setId("Sup-" + i.getId());
			String erreurInter = getErreurInter(i);
			infoButton.addEventHandler(ActionEvent.ACTION, this);
			supButton.addEventHandler(ActionEvent.ACTION, this);
			lst.add(new IntervenantIHM(infoButton, i.getPrenom(), i.getNom(),
				this.ctrl.getModele().getNomCateg(i.getIdCategorie()), i.getEmail(), supButton, erreurInter));
		}
		this.tableViewIntervenant.setItems(lst);
		this.tableViewIntervenant.refresh();
	}
	
	/*/
	private String getErreurInter(Intervenant i) {
		String ret = "";
		double totalHeure = 0.0;
		double totalREH = 0.0;
		double heure = 0.0;
		Map<Integer, Intervention> hmInter = this.ctrl.getModele().getHmInterventions();
		Map<Integer, TypeCours> hmTypeCours = this.ctrl.getModele().getHmTypeCours();
		Map<String, HeureCours> hmHeureCours = this.ctrl.getModele().getHmHeuresCours();
		for (Intervention in : hmInter.values()) {
			if (in.getIdIntervenant() == i.getId()) {
				TypeCours tc = hmTypeCours.get(in.getIdTypeCours());
				HeureCours hc = hmHeureCours.get(in.getIdTypeCours() + "-" + in.getIdModule());
				heure = in.getNbGroupe() * hc.gethParSemaine() * hc.getNbSemaine();
				if (tc.getNom().equals("Tut") || tc.getNom().equals("REH") || tc.getNom().equals("SAE")
						|| tc.getNom().equals("HP"))
					heure = in.getNbGroupe();
					
				if (!tc.getNom().equals("REH"))
					totalHeure += in.getNbGroupe() * heure * tc.getCoefficient();
				else
					totalREH += in.getNbGroupe() * heure * tc.getCoefficient();
			}
		}
		
		if (totalHeure + totalREH < i.gethMin())
			ret += "L'intervenant est en sous-service";
		else if (totalHeure > i.gethMax())
			ret += "L'intervenant est en sur-service";
		
		return ret;
	}
	*/
	
	private String getErreurInter(Intervenant i) {
		String ret = "";
		double totalHeure = 0.0;
		double totalREH = 0.0;
		double heure = 0.0;
		Map<Integer, Intervention> hmInter = this.ctrl.getModele().getHmInterventions();
		Map<Integer, TypeCours> hmTypeCours = this.ctrl.getModele().getHmTypeCours();
		Map<String, HeureCours> hmHeureCours = this.ctrl.getModele().getHmHeuresCours();
		for (Intervention in : hmInter.values()) {
			if (in.getIdIntervenant() == i.getId()) {
				TypeCours tc = hmTypeCours.get(in.getIdTypeCours());
				HeureCours hc = hmHeureCours.get(in.getIdTypeCours() + "-" + in.getIdModule());
				heure = in.getNbGroupe() * hc.gethParSemaine() * hc.getNbSemaine();
				if (tc.getNom().equals("Tut") || tc.getNom().equals("REH") || tc.getNom().equals("SAE")
						|| tc.getNom().equals("HP"))
					heure = in.getNbGroupe();
					
				if (!tc.getNom().equals("REH"))
					totalHeure += heure * tc.getCoefficient();
				else
					totalREH += heure * tc.getCoefficient();
			}
		}
		totalHeure = Double.parseDouble(String.format("%.2f", totalHeure).replace(",", "."));
		if (totalHeure + totalREH < i.gethMin())
			ret += "L'intervenant est en sous-service";
		else if (totalHeure > i.gethMax())
			ret += "L'intervenant est en sur-service";
		
		return ret;
	}
	
	public Button getInfoButton() {
		SVGPath infosvg = new SVGPath();
		// infosvg.setContent(ResourceManager.getData("book"));
		infosvg.setContent(
				"M11.8626 4.02794C9.87308 2.05641 7.29356 0.967859 4.62087 0.971936C3.35115 0.971936 2.13213 1.21194 1 1.6546V20.6546C2.16305 20.2013 3.38756 19.9704 4.62087 19.9719C7.40291 19.9719 9.94114 21.1279 11.8626 23.0279M11.8626 4.02794C13.8521 2.0563 16.4317 0.967733 19.1044 0.971936C20.3741 0.971936 21.5931 1.21194 22.7252 1.6546V20.6546C21.5622 20.2013 20.3377 19.9704 19.1044 19.9719C16.4317 19.9679 13.8522 21.0564 11.8626 23.0279M11.8626 4.02794V23.0279");
		infosvg.setStroke(Color.BLACK);
		infosvg.setFill(Color.WHITE);
		infosvg.setStrokeWidth(1.5);
		infosvg.setStrokeLineCap(StrokeLineCap.ROUND);
		infosvg.setStrokeLineJoin(StrokeLineJoin.ROUND);
		
		Button infobtn = new Button();
		infobtn.setGraphic(infosvg);
		infobtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		infobtn.getStyleClass().add("info-btn");
		return infobtn;
	}
	
	public void popupParamIntervenant(IntervenantIHM i) {
		this.btnParamIntervenants.setText("Ajouter un intervenant");
		if (i == null)
			this.modifIntervenant = null;
		
		Stage popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.setTitle("Ajouter un Intervenant");
		popupStage.centerOnScreen();
		popupStage.setHeight(450);
		popupStage.setWidth(300);
		popupStage.setResizable(false);
		
		Text pnom = new Text("Prénom  ");
		this.tfPrenom = new TextField();
		this.tfPrenom.setMaxWidth(30 * 7);
		this.tfPrenom.textProperty().addListener(this);
		this.tfPrenom.getStyleClass().add("textField");
		
		Text nom = new Text("Nom  ");
		this.tfNom = new TextField();
		this.tfNom.setMaxWidth(30 * 7);
		this.tfNom.textProperty().addListener(this);
		this.tfNom.getStyleClass().add("textField");
		
		Text email = new Text("Email  ");
		this.tfEmail = new TextField();
		this.tfEmail.setMaxWidth(30 * 7);
		this.tfEmail.textProperty().addListener(this);
		this.tfEmail.getStyleClass().add("textField");
		
		Text categorie = new Text("Catégorie ");
		this.choiceBoxCategorie = new ChoiceBox<>();
		choiceBoxCategorie.setMaxWidth(30 * 7);
		
		Map<Integer, Categorie> hmCategorie = this.ctrl.getModele().getHmCategories();
		for (Categorie c : hmCategorie.values())
			choiceBoxCategorie.getItems().add(c);
		
		this.choiceBoxCategorie.addEventHandler(ActionEvent.ACTION, this);
		
		Text hMinText = new Text("Heures Minimales ");
		this.tfHMin = new TextField();
		this.tfHMin.setMaxWidth(10 * 7);
		this.tfHMin.textProperty().addListener(this);
		this.tfHMin.getStyleClass().add("textField");
		
		Text hMaxText = new Text("Heures Maximales ");
		this.tfHMax = new TextField();
		this.tfHMax.setMaxWidth(10 * 7);
		this.tfHMax.textProperty().addListener(this);
		this.tfHMax.getStyleClass().add("textField");
		
		if (choiceBoxCategorie.getItems().size() > 0) {
			choiceBoxCategorie.setValue(choiceBoxCategorie.getItems().get(0));
			Categorie c = this.choiceBoxCategorie.getValue();
			this.tfHMin.setText("" + c.gethMin());
			this.tfHMax.setText("" + c.gethMax());
		}
				
		this.btnConfirmerIntervenant = new Button("Ajouter");
		this.btnConfirmerIntervenant.getStyleClass().add("confirmBtn");
		this.btnConfirmerIntervenant.addEventHandler(ActionEvent.ACTION, this);
		this.btnConfirmerIntervenant.setDisable(true);
		
		VBox vbox = new VBox(5);
		vbox.setMaxSize(200, 400);
		vbox.setAlignment(Pos.CENTER);
		
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(pnom);
		borderPane.setCenter(this.tfPrenom);
		borderPane.setPadding(new Insets(10));
		
		BorderPane borderPane2 = new BorderPane();
		borderPane2.setTop(nom);
		borderPane2.setCenter(this.tfNom);
		borderPane2.setPadding(new Insets(10));
		
		BorderPane borderPane3 = new BorderPane();
		borderPane3.setTop(email);
		borderPane3.setCenter(this.tfEmail);
		borderPane3.setPadding(new Insets(10));
		
		BorderPane borderPane4 = new BorderPane();
		borderPane4.setTop(categorie);
		borderPane4.setCenter(choiceBoxCategorie);
		borderPane4.setPadding(new Insets(10));
		
		GridPane gridPane1 = new GridPane();
		gridPane1.add(hMinText, 0, 0);
		gridPane1.add(this.tfHMin, 1, 0);
		gridPane1.add(hMaxText, 0, 1);
		gridPane1.add(this.tfHMax, 1, 1);
		gridPane1.setPadding(new Insets(10));
		gridPane1.setVgap(10);
		

		BorderPane borderPane5 = new BorderPane();
		borderPane5.setCenter(this.btnConfirmerIntervenant);
		
		if (i != null) {
			popupStage.setTitle("Paramétrer un Intervenant");
			this.btnConfirmerIntervenant.setText("Confirmer");
			Map<Integer, Intervenant> hmInter = this.ctrl.getModele().getHmIntervenants();
			Intervenant inter = hmInter.get(Integer.parseInt(i.getSupprimer().getId().split("-")[1]));
			this.modifIntervenant = inter;
			this.tfPrenom.setText(inter.getPrenom());
			this.tfNom.setText(inter.getNom());
			this.tfEmail.setText(inter.getEmail());
			this.tfHMin.setText(inter.gethMin() + "");
			this.tfHMax.setText(inter.gethMax() + "");
			/*
			for (Categorie c : hmCategorie.values()) {
				if (inter.getIdCategorie() == c.getId()) {
					this.choiceBoxCategorie.setValue(c);
					break;
				}
			}
			*/
		}
		
		StackPane popupLayout = new StackPane();
		// popupLayout.getChildren().add(closeButton);
		vbox.getChildren().addAll(borderPane, borderPane2, borderPane3, borderPane4, gridPane1, borderPane5);
		popupLayout.getChildren().add(vbox);
		popupLayout.getStylesheets().add(ResourceManager.STYLESHEET.toExternalForm());
		Scene popupScene = new Scene(popupLayout, 200, 400);
		popupScene.getStylesheets().add(ResourceManager.STYLESHEET_POPUP.toExternalForm());
		popupStage.setScene(popupScene);
		popupStage.showAndWait();
	}
	
	public void popupAfficheModule(int idIntervenant, String erreur) {
		Intervenant intervenant = this.ctrl.getModele().getHmIntervenants().get(idIntervenant);
		Categorie categorie = this.ctrl.getModele().getHmCategories().get(intervenant.getIdCategorie());
		
		Stage popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.setTitle("État de l'intervenant");
		
		GridPane gridPaneTotal = new GridPane();
		gridPaneTotal.setHgap(20);
		gridPaneTotal.setVgap(20);
		
		GridPane gridPaneInfoInter = new GridPane();
		gridPaneInfoInter.add(new Label(categorie.getNom() + " : "), 0, 0);
		gridPaneInfoInter.add(new Label(intervenant.getNom()), 1, 0);
		gridPaneInfoInter.add(new Label(intervenant.getPrenom()), 2, 0);
		
		gridPaneInfoInter.add(new Label("hMin : " + intervenant.gethMin()), 0, 1);
		gridPaneInfoInter.add(new Label("hMax : " + intervenant.gethMax()), 1, 1);
		gridPaneInfoInter.add(new Label("RatioTp : " + String.format("%.2f", categorie.getRatioTp())), 2, 1);
		// gridPaneInfoInter.add(new Label(erreur), 1, 2);
		gridPaneInfoInter.setHgap(20);
		gridPaneInfoInter.setVgap(20);
		
		List<Double> lstHSem = new ArrayList<>();
		Map<Integer, Semestre> hmSem = this.ctrl.getModele().getHmSemestres();
		Map<Integer, TypeCours> hmTypeCours = this.ctrl.getModele().getHmTypeCours();
		Map<Integer, Module> hmModule = this.ctrl.getModele().getHmModules();
		Map<Integer, Intervention> hmIntervention = this.ctrl.getModele().getHmInterventions();
		
		Map<Module, Double> hmHSem = new HashMap<>();
		
		for (Semestre s : hmSem.values()) {
			lstHSem.add(0.0);
			for (Intervention i : hmIntervention.values()) {
				Module m = hmModule.get(i.getIdModule());
				if (i.getIdIntervenant() == idIntervenant && s.getId() == m.getIdSemestre()) {
					TypeCours tc = hmTypeCours.get(i.getIdTypeCours());
					double nbHeure = 0.0;
					HeureCours hc = this.ctrl.getModele().getHmHeuresCours()
							.get(i.getIdTypeCours() + "-" + i.getIdModule());
					if (tc.getNom().equals("TD") || tc.getNom().equals("TP") || tc.getNom().equals("CM"))
						nbHeure = hc.gethParSemaine() * hc.getNbSemaine() * i.getNbGroupe();
					else
						nbHeure = i.getNbGroupe();
					nbHeure = nbHeure * tc.getCoefficient();
					if (tc.getNom().equals("TP"))
						nbHeure = nbHeure * categorie.getRatioTp();
					if (hmHSem.get(m) == null)
						hmHSem.put(m, nbHeure);
					else
						hmHSem.put(m, hmHSem.get(m) + nbHeure);
				}
			}
		}
		
		List<Double> lstParSem = new ArrayList<>();
		for (int i = 0; i < 6; i++)
			lstParSem.add(0.0);
		String[] colonnes = { "Module", "s1", "s3", "s5", "sTotImpair", "s2", "s4", "s6", "sTotPair", "Total" };
		
		TableView<RecapIntervenantIHM> tbVRecap = new TableView<>();
		tbVRecap.setPrefWidth(1000);
		tbVRecap.getStyleClass().add("tbV-module");
		
		if (tbVRecap.getColumns().size() < colonnes.length) {
			for (String colonne : colonnes) {
				TableColumn<RecapIntervenantIHM, String> tbcl = new TableColumn<>(colonne);
				tbcl.setCellValueFactory(new PropertyValueFactory<>(colonne.toLowerCase()));
				tbcl.setReorderable(false);
				tbcl.setResizable(false);
				tbcl.setSortable(false);
				
				if (colonne.equals("Module"))
					tbcl.prefWidthProperty().bind(tbVRecap.widthProperty().multiply(0.32));
				else if (colonne.equals("sTotImpair"))
					tbcl.prefWidthProperty().bind(tbVRecap.widthProperty().multiply(0.1));
				else if (colonne.equals("sTotPair"))
					tbcl.prefWidthProperty().bind(tbVRecap.widthProperty().multiply(0.075));
				else
					tbcl.prefWidthProperty().bind(tbVRecap.widthProperty().multiply(0.415 / 6));
				tbVRecap.getColumns().add(tbcl);
			}
		}
		
		ObservableList<RecapIntervenantIHM> olRecap = FXCollections.observableArrayList();
		
		List<Integer> alHmSem = new ArrayList<>(hmSem.keySet());
		alHmSem.sort(null);
		int premierSemestre = alHmSem.get(0);
		
		for (Module m : hmHSem.keySet()) {
			double heure = hmHSem.get(m);
			String heureFormat = String.format("%.2f", heure).replace(",", ".");
			RecapIntervenantIHM rIhm = new RecapIntervenantIHM(m.getCode() + "_" + m.getNom());
			switch (m.getIdSemestre() - premierSemestre) {
				case 0 -> {
					rIhm.setS1(heureFormat + "");
					rIhm.setStotimpair(heureFormat + "");
					lstParSem.set(0, lstParSem.get(0) + heure);
				}
				case 1 -> {
					rIhm.setS2(heureFormat + "");
					rIhm.setStotpair(heureFormat + "");
					lstParSem.set(1, lstParSem.get(1) + heure);
				}
				case 2 -> {
					rIhm.setS3(heureFormat + "");
					rIhm.setStotimpair(heureFormat + "");
					lstParSem.set(2, lstParSem.get(2) + heure);
				}
				case 3 -> {
					rIhm.setS4(heureFormat + "");
					rIhm.setStotpair(heureFormat + "");
					lstParSem.set(3, lstParSem.get(3) + heure);
				}
				case 4 -> {
					rIhm.setS5(heureFormat + "");
					rIhm.setStotimpair(heureFormat + "");
					lstParSem.set(4, lstParSem.get(4) + heure);
				}
				case 5 -> {
					rIhm.setS6(heureFormat + "");
					rIhm.setStotpair(heureFormat + "");
					lstParSem.set(5, lstParSem.get(5) + heure);
				}
			}
			double total = 0.0;
			if (rIhm.getStotimpair() != null)
				total = Double.parseDouble(rIhm.getStotimpair());
			if (rIhm.getStotpair() != null)
				total = Double.parseDouble(rIhm.getStotpair());
			
			rIhm.setTotal(String.format("%.2f", total) + "");
			olRecap.add(rIhm);
		}
		
		double totalImpair = lstParSem.get(0) + lstParSem.get(2) + lstParSem.get(4);
		double totalPair = lstParSem.get(1) + lstParSem.get(3) + lstParSem.get(5);
		olRecap.add(new RecapIntervenantIHM("Total", String.format("%.2f", lstParSem.get(0)) + "", String.format("%.2f", lstParSem.get(2)) + "",
				String.format("%.2f", lstParSem.get(4)) + "", String.format("%.2f", totalImpair) + "",
				String.format("%.2f", lstParSem.get(1)) + "", String.format("%.2f", lstParSem.get(3)) + "",String.format("%.2f", lstParSem.get(5)) + "", String.format("%.2f", totalPair) + "",
				String.format("%.2f", (totalImpair+totalPair)) + ""));
		
		tbVRecap.setItems(olRecap);
		tbVRecap.setPrefHeight(41 * olRecap.size());
		if (olRecap.size() == 1)
			tbVRecap.setPrefHeight(80);
		if (olRecap.size() == 2)
			tbVRecap.setPrefHeight(tbVRecap.getPrefHeight() + 20);
		
		gridPaneInfoInter.setAlignment(Pos.CENTER);
		GridPane gridPaneInfoErreur = new GridPane();
		gridPaneInfoErreur.add(gridPaneInfoInter, 0, 0);
		Label lblErreur = new Label(erreur);
		lblErreur.setStyle("-fx-text-fill: red;");
		lblErreur.setPadding(new Insets(10));
		gridPaneInfoErreur.add(lblErreur, 0, 1);
		gridPaneInfoErreur.setAlignment(Pos.CENTER);
		
		gridPaneTotal.add(gridPaneInfoErreur, 0, 0);
		gridPaneTotal.add(tbVRecap, 0, 1);
		
		VBox vbox = new VBox(5);
		vbox.setMaxSize(800, tbVRecap.getPrefHeight() + 130);
		vbox.setPadding(new Insets(10));
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(30);
		vbox.getChildren().addAll(gridPaneTotal);
		
		StackPane popupLayout = new StackPane();
		popupLayout.setAlignment(Pos.CENTER);
		popupLayout.getChildren().add(vbox);
		popupLayout.getStylesheets().add(ResourceManager.STYLESHEET.toExternalForm());
		Scene popupScene = new Scene(popupLayout, 820, tbVRecap.getPrefHeight() + 150);
		popupStage.setScene(popupScene);
		popupStage.setResizable(false);
		popupStage.showAndWait();
	}
	
	@Override
	public void handle(Event event) {
		if (event.getSource() == this.btnParamCategorie)
			this.frameParamCategorie = new FrameParamCategorie(this.ctrl, this.centerPaneAccueil);
		
		if (event.getSource() == this.btnParamIntervenants) {
			if (this.tableViewIntervenant.getSelectionModel().getSelectedItems().size() != 0)
				this.popupParamIntervenant(this.tableViewIntervenant.getSelectionModel().getSelectedItems().get(0));
			else
				this.popupParamIntervenant(null);
		}
		
		if (event.getSource() == this.choiceBoxCategorie) {
			Categorie c = this.choiceBoxCategorie.getValue();
			this.tfHMin.setText("" + c.gethMin());
			this.tfHMax.setText("" + c.gethMax());
		}
		
		if (event.getSource() == this.btnConfirmerIntervenant) {
			String prenom = this.tfPrenom.getText();
			String nom = this.tfNom.getText();
			String email = this.tfEmail.getText();
			String tfHMinText = this.tfHMin.getText();
			String tfHMaxText = this.tfHMax.getText();
			
			Categorie c = this.choiceBoxCategorie.getValue();
			
			if (!prenom.isEmpty() && !nom.isEmpty()
					&& !this.tfHMin.getText().isEmpty()
					&& !this.tfHMax.getText().isEmpty() && c != null) {
				double hMin = Double.parseDouble(tfHMinText);
				double hMax = Double.parseDouble(tfHMaxText);
				if (hMin > hMax) {
					this.ctrl.getVue().afficherNotification("Erreur", "Les heures minimales doivent être inférieures aux heures maximales", Notification.ERREUR );
					return;
				} else if (hMax < hMin) {
						this.ctrl.getVue().afficherNotification("Erreur", "Les heures maximales doivent être supérieures aux heures minimales", Notification.ERREUR );
						return;
					} else if (hMax > c.gethMax()) {
					this.ctrl.getVue().afficherNotification("Erreur", "Les heures maximales doivent être inférieures\naux heures maximales de la catégorie !", Notification.ERREUR );
					return;
				}
				((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
				if (this.modifIntervenant != null) {
					this.modifIntervenant.setPrenom(prenom);
					this.modifIntervenant.setNom(nom);
					this.modifIntervenant.setEmail(email);
					this.modifIntervenant.sethMin(hMin);
					this.modifIntervenant.sethMax(hMax);
					this.modifIntervenant.setIdCategorie(this.choiceBoxCategorie.getValue().getId());
					this.ctrl.getModele().updateIntervenant(this.modifIntervenant);
				} else {
					this.ctrl.getModele().ajouterIntervenant(prenom, nom, email, hMin, hMax, this.choiceBoxCategorie.getValue().getId());
				}
				this.majTableIntervenant();
			}
		
		} else if (event.getSource() instanceof Button button) {
			if (button.getId() != null) {
				String[] textButton = button.getId().split("-");
				if (textButton[0].equals("Info")) {
					Intervenant i = this.ctrl.getModele().getHmIntervenants().get(Integer.parseInt(textButton[1]));
					this.popupAfficheModule(i.getId(), getErreurInter(i));
				} else if (textButton[0].equals("Sup")) {
					btnParamIntervenants.setText("Ajouter un intervenant");
					this.ctrl.getVue().popupValider();
					if (ControleurIHM.bIsValidate)
						this.ctrl.getModele().supprimerIntervenant(Integer.parseInt(textButton[1]));
					ControleurIHM.bIsValidate = false;
					this.majTableIntervenant();
				}
			}
		}
	}
	
	public ObservableList<String> getStylesheets() {
		return this.centerPaneAccueil.getStylesheets();
	}
	
	public void changed(ObservableValue<? extends String> observable, String oldStr, String newStr) {
		if ((observable == this.tfPrenom.textProperty() || observable == this.tfNom.textProperty())
				&& (!(this.tfPrenom.getText().isEmpty() || this.tfNom.getText().isEmpty()))) {
			this.tfEmail.setText(this.tfPrenom.getText().toLowerCase() + "." + this.tfNom.getText().toLowerCase()
					+ "@univ-lehavre.fr");
			if (!this.tfEmail.getText().matches(Modele.REGEX_EMAIL))
				this.tfEmail.setText(this.tfEmail.getText().replaceAll("[^-a-zA-Z0-9@.]", ""));
		}
		if (observable == this.tfHMin.textProperty()) {
			if (!(this.tfHMin.getText().matches(Modele.REGEX_DOUBLE)))
				this.tfHMin.setText(oldStr);
		} else if (observable == this.tfHMax.textProperty()) {
			if (!(this.tfHMax.getText().matches(Modele.REGEX_DOUBLE)))
				this.tfHMax.setText(oldStr);
		}
		if (this.tfPrenom.getText().isEmpty() || this.tfNom.getText().isEmpty()
				|| this.tfHMin.getText().isEmpty() || this.tfHMax.getText().isEmpty()
				|| this.choiceBoxCategorie.getValue() == null) {
			if (this.btnConfirmerIntervenant != null)
				this.btnConfirmerIntervenant.setDisable(true);
		} else {
			this.btnConfirmerIntervenant.setDisable(false);
		}
	}
}