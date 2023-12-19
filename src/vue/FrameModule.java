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
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import modele.HeureCours;
import modele.Modele;
import modele.Module;
import modele.Semestre;
import modele.TypeCours;
import modele.TypeModule;

public class FrameModule implements EventHandler<Event> {
	
	private Controleur ctrl;
	private AnchorPane centerPaneAccueil;
	private Map<Integer, Semestre> hmSemestres;

	private FrameIntervention frameIntervention;

	private Map<Semestre, List<TextField>> hmTF;
	private Map<Integer, Module> hmModule;
	private Map<Integer, TypeCours> hmTypeCours;
	private Map<Integer, TypeModule> hmTypeModule;
	private Map<String, HeureCours> hmHeureCours;

	private TabPane tabPane;

	private List<TableView<LigneModuleIHM>> lstTableView;
	private ObservableList<LigneModuleIHM> lst;
	private TextField txtFTD;
	private TextField txtFTP;
	private TextField txtFCM;
	private TextField txtFNbSemaines;

	private List<TableColumn<LigneModuleIHM, String>> lstTableColumns;

	private Button btnAjouter;
	private ChoiceBox<TypeModule> choiceBoxTypeModule;

	private List<TextField> lstTxtF;
	private ColorPicker colorPicker;

	public FrameModule(Controleur ctrl, AnchorPane centerPaneAccueil) {
		this.ctrl = ctrl;
		this.centerPaneAccueil = centerPaneAccueil;
		this.hmTF = new HashMap<>();

		this.init(1);
	}

	public void init(int idSelectedSemestre) {
		this.hmHeureCours = this.ctrl.getModele().getHmHeuresCours();
		this.centerPaneAccueil.getChildren().clear();
		this.centerPaneAccueil.getStylesheets().add(ResourceManager.STYLESHEET.toExternalForm());
		this.hmTypeModule = this.ctrl.getModele().getHmTypeModule();
		this.lstTxtF = new ArrayList<>();

		this.majTabs(idSelectedSemestre);

		FlowPane flowPane = new FlowPane();
		this.btnAjouter = new Button("Ajouter");
		this.btnAjouter.addEventHandler(ActionEvent.ACTION, this);
		this.choiceBoxTypeModule = new ChoiceBox<>();
		for (TypeModule tm : this.hmTypeModule.values()) {
			this.choiceBoxTypeModule.getItems().add(tm);
		}
		flowPane.getChildren().addAll(this.choiceBoxTypeModule, this.btnAjouter);
		flowPane.setHgap(15);
		
		AnchorPane.setLeftAnchor(flowPane,this.centerPaneAccueil.getWidth()/2.0 - 80);
		AnchorPane.setBottomAnchor(flowPane, 15.0);
		
		this.centerPaneAccueil.getChildren().addAll(tabPane, flowPane);
	}

	public void majTabs(int idSelectedSemestre) {
		this.tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		this.hmModule = this.ctrl.getModele().getHmModules();

		this.hmSemestres= this.ctrl.getModele().getHmSemestres();
	 	List<Integer> keysList = new ArrayList<>(this.hmSemestres.keySet());
		keysList.sort(null);
			
		this.lstTableView = new ArrayList<>();
		this.lstTableColumns = new ArrayList<>();

		Tab[] tabTab = new Tab[this.hmSemestres.size()];

		int cpt = 0;
		for (Integer i : keysList) {
			Semestre semestre = this.hmSemestres.get(i);
			BorderPane borderPaneTab = new BorderPane();
			FlowPane flowPaneTxtF = new FlowPane();
			flowPaneTxtF.setHgap(5);
		
			GridPane gridPaneTmp = new GridPane();
			TextField textFieldTmp = new TextField(semestre.getNbGTD() + "");
			textFieldTmp.setMaxWidth(5*7);
			textFieldTmp.setId("TD-"+semestre.getId());
			this.lstTxtF.add(textFieldTmp);
			gridPaneTmp.add(new Label("nbGroupeTD : "), 0, 0);
			gridPaneTmp.add(textFieldTmp, 1, 0);
			flowPaneTxtF.getChildren().add(gridPaneTmp);

			
			gridPaneTmp = new GridPane();
			textFieldTmp = new TextField(semestre.getNbGTP() + "");
			textFieldTmp.setMaxWidth(5*7);
			textFieldTmp.setId("TP-"+semestre.getId());
			this.lstTxtF.add(textFieldTmp);
			gridPaneTmp.add(new Label("nbGroupeTP : "), 0, 0);
			gridPaneTmp.add(textFieldTmp, 1, 0);
			flowPaneTxtF.getChildren().add(gridPaneTmp);

			gridPaneTmp = new GridPane();
			textFieldTmp = new TextField(semestre.getNbGCM() + "");
			textFieldTmp.setMaxWidth(5*7);
			textFieldTmp.setId("CM-"+semestre.getId());
			this.lstTxtF.add(textFieldTmp);
			gridPaneTmp.add(new Label("nbGroupeCM : "), 0, 0);
			gridPaneTmp.add(textFieldTmp, 1, 0);
			flowPaneTxtF.getChildren().add(gridPaneTmp);

			gridPaneTmp = new GridPane();
			textFieldTmp = new TextField(semestre.getNbSemaine() + "");
			textFieldTmp.setMaxWidth(5*7);
			textFieldTmp.setId("Semaine-"+semestre.getId());
			this.lstTxtF.add(textFieldTmp);
			gridPaneTmp.add(new Label("nbSemaines : "), 0, 0);
			gridPaneTmp.add(textFieldTmp, 1, 0);
			flowPaneTxtF.getChildren().add(gridPaneTmp);

			this.colorPicker = new ColorPicker(Color.web(semestre.getCouleur()));
			colorPicker.addEventHandler(ActionEvent.ACTION, this);
			
			flowPaneTxtF.setVgap(20);
			flowPaneTxtF.setHgap(20);
			flowPaneTxtF.getChildren().add(colorPicker);

			TableView<LigneModuleIHM> tbV = new TableView<>();

			String[] colonnes = new String[] { "id", "info", "Code", "Nom", "CM", "TD", "TP", "REH", "HTut", "SAE",
					"HP", "supprimer" };

			if (tbV.getColumns().size() < 11) {
				for (String colonne : colonnes) {
					TableColumn<LigneModuleIHM, String> tbcl = new TableColumn<>(colonne);
					this.lstTableColumns.add(tbcl);
					tbcl.setCellValueFactory(new PropertyValueFactory<>(colonne.toLowerCase()));

					if (!colonne.equals("id") && !colonne.equals("info") && !colonne.equals("supprimer")) {
						tbcl.setEditable(true);
						tbcl.setCellFactory(TextFieldTableCell.forTableColumn());
						tbcl.setOnEditCommit((TableColumn.CellEditEvent<LigneModuleIHM, String> event) -> {
							String oldValue = event.getOldValue();
							if (!oldValue.isEmpty() && !oldValue.equals("nombre d'heures totales") && !oldValue.equals("nombre de semaine") && !oldValue.equals("nombre d'heures par semaine")) {
								LigneModuleIHM ligne = event.getTableView().getItems().get(event.getTablePosition().getRow());
								int id = ligne.getId();
								String col = event.getTableColumn().getText();
								String newValue = event.getNewValue();
								Module m = this.hmModule.get(id);
								if (col.equals("Nom"))  m.setNom(newValue);
								else if (col.equals("Code")) m.setCode(newValue);
								else {
									int idTypeCours = this.ctrl.getModele().getIdTypeCoursByNom(col);
									HeureCours hc = this.hmHeureCours.get(idTypeCours+"-"+m.getId());
									if (ligne.getNom().equals("nombre de semaine")) hc.setNbSemaine(Integer.parseInt(newValue));
									else if (ligne.getNom().equals("nombre d'heures totales")) hc.setHeure(Double.parseDouble(newValue));
									else if (ligne.getNom().equals("nombre d'heures par semaine")) hc.sethParSemaine(Double.parseDouble(newValue));
									this.ctrl.getModele().updateHeureCours(hc);
								}	
								this.ctrl.getModele().updateModule(m);
							}
						});

					}

					if (colonne.equals("id"))
						tbcl.setVisible(false);
					else if (colonne.equals("Nom"))
						tbcl.prefWidthProperty().bind(tbV.widthProperty().multiply(0.3));
					else if (colonne.equals("supprimer"))
						tbcl.prefWidthProperty().bind(tbV.widthProperty().multiply(0.093));
					else
						tbcl.prefWidthProperty().bind(tbV.widthProperty().multiply(0.065));
					tbV.getColumns().add(tbcl);
					
				}
				
			}

			tbV.setEditable(true);
			tbV.getSelectionModel().setCellSelectionEnabled(true);

			List<Module> lstModule = this.ctrl.getModele().getModuleBySemestre(semestre.getId(), semestre.getIdAnnee());
			lstModule.sort(null);
			this.hmTypeCours = this.ctrl.getModele().getHmTypeCours();
			this.lst = FXCollections.observableArrayList();

			for (Module m : lstModule) {
				switch (this.hmTypeModule.get(m.getIdTypeModule()).getNom()) {
					case "PPP"    -> this.ajouterModulePPP(m);
					case "SAE"    -> this.ajouterModuleSAE(m);
					case "normal" -> this.ajouterModuleNormale(m);
					case "stage"  -> this.ajouterModuleStage(m);
				}
			}

			tbV.setItems(lst);
			lstTableView.add(tbV);

			borderPaneTab.setCenter(tbV);
			borderPaneTab.setTop(flowPaneTxtF);

			Tab tab = new Tab("Semestre " + (cpt + 1), borderPaneTab);
			tab.setId(semestre.getId()+"");
			tabTab[cpt++] = tab;

		}

		for (TextField txt : lstTxtF) {
			txt.textProperty().addListener(new ChangeListener<String>() {
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (txt.textProperty() == observable)
						if (newValue.matches(Modele.REGEX_INT)) {
							
							String[] partTxt = txt.getId().split("-");
							Semestre s = hmSemestres.get(Integer.parseInt(partTxt[1]));							
							int nb = 0;
							if (!txt.getText().isEmpty()) nb = Integer.parseInt(txt.getText());
							switch (partTxt[0]) {
								case "TD" -> s.setNbGTD(nb);
								case "TP" ->s.setNbGTP(nb);
								case "CM" -> s.setNbGCM(nb);
								case "Semaine" -> s.setNbSemaine(nb);
							}
							ctrl.getModele().updateSemestre(s);
						} else {txt.setText(oldValue);}
				}
			});
		}

		this.tabPane.getTabs().addAll(tabTab);
		this.tabPane.getSelectionModel().select(idSelectedSemestre);

		AnchorPane.setTopAnchor(tabPane, 5.0);
		AnchorPane.setRightAnchor(tabPane, 5.0);
		AnchorPane.setBottomAnchor(tabPane, 50.0);
		AnchorPane.setLeftAnchor(tabPane, 5.0);
	}

	public void ajouterModulePPP(Module m) {
		int id = m.getId();
		List<HeureCours> lstHeuresCours = this.ctrl.getModele().getHeureCoursByModule(id, m.getIdAnnee());
		ModuleIHM moduleIHM = new ModuleIHM();
		if (lstHeuresCours != null) {
			for (HeureCours hc : lstHeuresCours) {
				switch (this.hmTypeCours.get(hc.getIdTypeCours()).getNom()) {
					case "TP"  -> moduleIHM.setTp(hc);
					case "TD"  -> moduleIHM.setTd(hc);
					case "CM"  -> moduleIHM.setCm(hc);
					case "Tut" -> moduleIHM.setTut(hc);
					case "HP"  -> moduleIHM.setHp(hc);
				}
			}
		}
		Button btnAjouterIntervenant = this.getAjouterIntervenantButton();
		btnAjouterIntervenant.setId("AjouterIntervenant-" + id);
		btnAjouterIntervenant.addEventFilter(ActionEvent.ACTION, this);

		Button supButton = ResourceManager.getSupButton();
		supButton.setId("Sup-" + id);
		supButton.addEventHandler(ActionEvent.ACTION, this);
		this.lst.add(new LigneModuleIHM(id, btnAjouterIntervenant, m.getCode(), m.getNom(), "", "", "", "", "", "", "",
				supButton));
		this.lst.add(new LigneModuleIHM(id, null, "", "nombre d'heures totales", moduleIHM.getCmHeure() + "",
				moduleIHM.getTdHeure() + "", moduleIHM.getTpHeure() + "", "", moduleIHM.getTutHeure() + "", "",
				moduleIHM.getHpHeure() + "", null));
		this.lst.add(new LigneModuleIHM(id, null, "", "nombre de semaine", moduleIHM.getCmSemaine() + "",
				moduleIHM.getTdSemaine() + "", moduleIHM.getTpSemaine() + "", "", "", "", "", null));
		this.lst.add(new LigneModuleIHM(id, null, "", "nombre d'heures par semaine", moduleIHM.getCmHeureSemaine() + "",
				moduleIHM.getTdHeureSemaine() + "", moduleIHM.getTpHeureSemaine() + "", "", "", "", "", null));
		this.lst.add(new LigneModuleIHM(id, null, "", "", "", "", "", "", "", "", "", null));
	}

	public void ajouterModuleSAE(Module m) {
		int id = m.getId();
		List<HeureCours> lstHeuresCours = this.ctrl.getModele().getHeureCoursByModule(id, m.getIdAnnee());
		ModuleIHM moduleIHM = new ModuleIHM();
		if (lstHeuresCours != null) {
			for (HeureCours hc : lstHeuresCours) {
				switch (this.hmTypeCours.get(hc.getIdTypeCours()).getNom()) {
					case "Tut" -> moduleIHM.setTut(hc);
					case "SAE" -> moduleIHM.setSae(hc);
					case "HP" -> moduleIHM.setHp(hc);
				}
			}
		}
		Button btnAjouterIntervenant = getAjouterIntervenantButton();
		btnAjouterIntervenant.setId("AjouterIntervenant-" + id);
		btnAjouterIntervenant.addEventFilter(ActionEvent.ACTION, this);

		Button supButton = ResourceManager.getSupButton();
		supButton.setId("Sup-" + id);
		supButton.addEventHandler(ActionEvent.ACTION, this);
		this.lst.add(new LigneModuleIHM(id, btnAjouterIntervenant, m.getCode(), m.getNom(), "", "", "", "", "", "", "",
				supButton));
		this.lst.add(new LigneModuleIHM(id, null, "", "nombre d'heures totales", "", "", "", "",
				moduleIHM.getTutHeure() + "", moduleIHM.getSaeHeure() + "", moduleIHM.getHpHeure() + "", null));
		this.lst.add(new LigneModuleIHM(id, null, "", "", "", "", "", "", "", "", "", null));
	}

	public void ajouterModuleNormale(Module m) {
		int id = m.getId();
		List<HeureCours> lstHeuresCours = this.ctrl.getModele().getHeureCoursByModule(id, m.getIdAnnee());
		ModuleIHM moduleIHM = new ModuleIHM();
		if (lstHeuresCours != null) {
			for (HeureCours hc : lstHeuresCours) {
				switch (this.hmTypeCours.get(hc.getIdTypeCours()).getNom()) {
					case "TP" -> moduleIHM.setTp(hc);
					case "TD" -> moduleIHM.setTd(hc);
					case "CM" -> moduleIHM.setCm(hc);
					case "HP" -> moduleIHM.setHp(hc);
				}
			}
		}
		Button btnAjouterIntervenant = getAjouterIntervenantButton();
		btnAjouterIntervenant.setId("AjouterIntervenant-" + id);
		btnAjouterIntervenant.addEventFilter(ActionEvent.ACTION, this);
		Button supButton = ResourceManager.getSupButton();
		supButton.setId("Sup-" + id);
		supButton.addEventHandler(ActionEvent.ACTION, this);
		this.lst.add(new LigneModuleIHM(id, btnAjouterIntervenant, m.getCode(), m.getNom(), "", "", "", "", "", "", "",
				supButton));
		this.lst.add(new LigneModuleIHM(id, null, "", "nombre d'heures totales", moduleIHM.getCmHeure() + "",
				moduleIHM.getTdHeure() + "", moduleIHM.getTpHeure() + "", "", "", "", moduleIHM.getHpHeure() + "",
				null));
		this.lst.add(new LigneModuleIHM(id, null, "", "nombre de semaine", moduleIHM.getCmSemaine() + "",
				moduleIHM.getTdSemaine() + "", moduleIHM.getTpSemaine() + "", "", "", "", "", null));
		this.lst.add(new LigneModuleIHM(id, null, "", "nombre d'heures par semaine", moduleIHM.getCmHeureSemaine() + "",
				moduleIHM.getTdHeureSemaine() + "", moduleIHM.getTpHeureSemaine() + "", "", "", "", "", null));
		this.lst.add(new LigneModuleIHM(id, null, "", "", "", "", "", "", "", "", "", null));
	}

	public void ajouterModuleStage(Module m) {
		int id = m.getId();
		List<HeureCours> lstHeuresCours = this.ctrl.getModele().getHeureCoursByModule(id, m.getIdAnnee());
		ModuleIHM moduleIHM = new ModuleIHM();
		if (lstHeuresCours != null) {
			for (HeureCours hc : lstHeuresCours) {
				switch (this.hmTypeCours.get(hc.getIdTypeCours()).getNom()) {
					case "Tut" -> moduleIHM.setTut(hc);
					case "REH" -> moduleIHM.setReh(hc);
					case "HP" -> moduleIHM.setHp(hc);
				}
			}
		}
		Button btnAjouterIntervenant = getAjouterIntervenantButton();
		btnAjouterIntervenant.setId("AjouterIntervenant-" + id);
		btnAjouterIntervenant.addEventFilter(ActionEvent.ACTION, this);

		Button supButton = ResourceManager.getSupButton();
		supButton.setId("Sup-" + id);
		supButton.addEventHandler(ActionEvent.ACTION, this);
		this.lst.add(new LigneModuleIHM(id, btnAjouterIntervenant, m.getCode(), m.getNom(), "", "", "", "", "", "", "",
				supButton));
		this.lst.add(new LigneModuleIHM(id, null, "", "nombre d'heures totales", "", "", "",
				moduleIHM.getRehHeure() + "", moduleIHM.getTutHeure() + "", "", moduleIHM.getHpHeure() + "", null));
		this.lst.add(new LigneModuleIHM(id, null, "", "", "", "", "", "", "", "", "", null));
	}

	

	public Button getAjouterIntervenantButton() {
		SVGPath supsvg = new SVGPath();
		supsvg.setContent(
				"M25 21H16M20.5 17V25M1 25C1 19.8453 5.70102 15.6667 11.5 15.6667C12.5425 15.6667 13.5495 15.8017 14.5 16.0532M17.5 6.33333C17.5 9.27885 14.8137 11.6667 11.5 11.6667C8.18629 11.6667 5.5 9.27885 5.5 6.33333C5.5 3.38781 8.18629 1 11.5 1C14.8137 1 17.5 3.38781 17.5 6.33333Z");
		supsvg.setStroke(Color.BLACK);
		supsvg.setFill(Color.WHITE);
		supsvg.setStrokeWidth(1.5);
		supsvg.setStrokeLineCap(StrokeLineCap.ROUND);
		supsvg.setStrokeLineJoin(StrokeLineJoin.ROUND);

		Button btnAjouterIntervenant = new Button();
		btnAjouterIntervenant.setGraphic(supsvg);
		btnAjouterIntervenant.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		btnAjouterIntervenant.getStyleClass().add("info-btn");
		return btnAjouterIntervenant;

	}

	public void nouveauModule(List<Integer> lstTypesCours, TypeModule tm) {
		int idSemestre = Integer.parseInt(tabPane.getSelectionModel().getSelectedItem().getId());
		String code = "R"+(tabPane.getSelectionModel().getSelectedIndex()+1)+".00";
		if (tm.getNom().equals("SAE")) code = "S"+(tabPane.getSelectionModel().getSelectedIndex()+1)+".00";
		else if (tm.getNom().equals("stage")) code = "Stage";
		this.ctrl.getModele().ajouterModule("Nom de la ressource", code, tm.getId(), idSemestre);
		int idModule = Module.nbModule;
		for (Integer i : lstTypesCours) {
			this.ctrl.getModele().ajouterHeureCours(i, idModule, 0, 0, 0);
		}
	}

	public void handle(Event action) {
		if (action instanceof ActionEvent) {
			if (action.getSource() instanceof Button) {
				Button btn = (Button) action.getSource();
				if (btn == this.btnAjouter) {
					if (this.choiceBoxTypeModule.getValue() != null) {
						TypeModule tm = this.choiceBoxTypeModule.getValue();
						List<Integer> lstTypesCours = new ArrayList<>();
						switch (tm.getNom()) {
							case "PPP" -> lstTypesCours = List.of(1, 2, 3, 4, 7);
							case "SAE" -> lstTypesCours = List.of(6, 4, 7);
							case "normal" -> lstTypesCours = List.of(1, 2, 3, 7);
							case "stage" -> lstTypesCours = List.of(5, 4, 7);
						}
						nouveauModule(lstTypesCours, tm);
						this.init(this.tabPane.getSelectionModel().getSelectedIndex());
					}
				} 	
				else {
					String[] textBtn = btn.getId().split("-");
					if (textBtn[0].equals("Sup")) {
						this.ctrl.getVue().popupValider();
						if (ControleurIHM.bIsValidate)
						this.ctrl.getModele().supprimerModule(Integer.parseInt(textBtn[1]));
					}
					
					if (textBtn[0].equals("AjouterIntervenant")) {
						this.frameIntervention = new FrameIntervention(this.ctrl, this.centerPaneAccueil,
								this.hmModule.get(Integer.parseInt(textBtn[1])));
					}
				}
			}
			else if (action.getSource() instanceof ColorPicker) {
				System.out.println(this.colorPicker.getValue());
			}

		}
		this.init(this.tabPane.getSelectionModel().getSelectedIndex());
	}

	
}