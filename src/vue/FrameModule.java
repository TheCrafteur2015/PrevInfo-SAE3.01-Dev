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
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.util.Callback;
import modele.Semestre;
import modele.HeureCours;
import modele.Module;
import modele.TypeCours;
import modele.TypeModule;

public class FrameModule implements EventHandler<Event>, ChangeListener<String> {
	private Controleur ctrl;
	private AnchorPane centerPaneAccueil;
	private Map<Integer, Semestre> hmSemestres;

	private FrameIntervention frameIntervention;

	private HashMap<Semestre, ArrayList<TextField>> hmTF;
	private Map<Integer, Module> hmModule;
	private Map<Integer, TypeCours> hmTypeCours;
	private Map<Integer, TypeModule> hmTypeModule;

	private TabPane tabPane;

	private List<TableView> lstTableView;
	private ObservableList<LigneModuleIHM> lst;
	private TextField txtFTD;
	private TextField txtFTP;
	private TextField txtFCM;
	private TextField txtFNbSemaines;

	public FrameModule(Controleur ctrl, AnchorPane centerPaneAccueil) {
		this.ctrl = ctrl;
		this.centerPaneAccueil = centerPaneAccueil;
		this.hmTF = new HashMap<Semestre, ArrayList<TextField>>();

		this.init();
	}

	public void init() {
		this.centerPaneAccueil.getChildren().clear();
		this.centerPaneAccueil.getStylesheets().add(ResourceManager.STYLESHEET.toExternalForm());
		this.hmTypeModule = this.ctrl.getModele().getHmTypeModule();
		this.majTabs();

		this.centerPaneAccueil.getChildren().addAll(tabPane);
	}

	public void majTabs() {
		this.tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		this.hmSemestres = this.ctrl.getModele().getHmSemestres();
		this.lstTableView = new ArrayList<TableView>();

		Tab[] tabTab = new Tab[this.hmSemestres.size()];

		int cpt = 0;
		for (Semestre semestre : this.hmSemestres.values()) {

			BorderPane borderPaneTab = new BorderPane();
			ArrayList<TextField> lsttxtF = new ArrayList<TextField>();
			this.txtFTD = new TextField(semestre.getNbGTD() + "");

			this.txtFTP = new TextField(semestre.getNbGTP() + "");
			this.txtFCM = new TextField(semestre.getNbGCM() + "");
			this.txtFNbSemaines = new TextField(semestre.getNbSemaine() + "");

			this.txtFTD.textProperty().addListener((observable, oldStr, newStr) -> {
				final String regex = "\\d+";
				if (newStr.matches(regex)) {
					this.hmSemestres.get(this.tabPane.getSelectionModel().getSelectedIndex())
							.setNbGTD(Integer.parseInt(newStr));
				} else {
					this.txtFTD.setText(oldStr);
				}

			});
			this.txtFTP.textProperty().addListener((observable, oldStr, newStr) -> {
				final String regex = "\\d+";
				if (newStr.matches(regex)) {
					this.hmSemestres.get(this.tabPane.getSelectionModel().getSelectedIndex())
							.setNbGTD(Integer.parseInt(newStr));
				} else {
					this.txtFTD.setText(oldStr);
				}

			});
			this.txtFCM.textProperty().addListener((observable, oldStr, newStr) -> {
				final String regex = "\\d+";
				if (newStr.matches(regex)) {
					this.hmSemestres.get(this.tabPane.getSelectionModel().getSelectedIndex())
							.setNbGCM(Integer.parseInt(newStr));
				} else {
					this.txtFTD.setText(oldStr);
				}

			});
			this.txtFTP.textProperty().addListener((observable, oldStr, newStr) -> {
				final String regex = "\\d+";
				if (newStr.matches(regex)) {
					this.txtFTD.setText(oldStr);
				} else {
					
this.hmSemestres.get(this.tabPane.getSelectionModel().getSelectedIndex())
							.setNbGTD(Integer.parseInt(newStr));
				}

			});
			this.txtFNbSemaines.textProperty().addListener(this);

			FlowPane flowPaneTxtF = new FlowPane();
			flowPaneTxtF.setHgap(5);
			flowPaneTxtF.getChildren().add(new Label("nbGroupeTD :"));
			flowPaneTxtF.getChildren().add(txtFTD);

			flowPaneTxtF.getChildren().add(new Label("nbGroupeTP :"));
			flowPaneTxtF.getChildren().add(txtFTP);

			flowPaneTxtF.getChildren().add(new Label("nbGroupeCM :"));
			flowPaneTxtF.getChildren().add(txtFCM);

			flowPaneTxtF.getChildren().add(new Label("nbSemaines :"));
			flowPaneTxtF.getChildren().add(txtFNbSemaines);

			lsttxtF.add(txtFTD);
			lsttxtF.add(txtFTP);
			lsttxtF.add(txtFCM);
			lsttxtF.add(txtFNbSemaines);
			this.hmTF.put(semestre, lsttxtF);

			TableView<LigneModuleIHM> tbV = new TableView<LigneModuleIHM>();
			// tbV.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

			// Créez une usine de cellules personnalisée pour permettre l'édition
			Callback<TableColumn<LigneModuleIHM, String>, TableCell<LigneModuleIHM, String>> cellFactory = col -> new EditableTableCell();

			String[] colonnes = new String[] { "id", "info", "Code", "Nom", "CM", "TD", "TP", "REH", "HTut", "SAE",
					"HP", "supprimer" };

			if (tbV.getColumns().size() < 11) {
				for (String colonne : colonnes) {
					TableColumn<LigneModuleIHM, String> tbcl = new TableColumn<>(colonne);
					tbcl.setCellValueFactory(new PropertyValueFactory<>(colonne.toLowerCase()));
					if (!colonne.equals("id") && !colonne.equals("info") && !colonne.equals("supprimer")) tbcl.setCellFactory(cellFactory);
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

			List<Module> lstModule = this.ctrl.getModele().getModuleBySemestre(semestre.getId(), semestre.getIdAnnee());
			// System.out.println(hmModule);
			this.hmTypeCours = this.ctrl.getModele().getHmTypeCours();
			this.lst = FXCollections.observableArrayList();

			for (Module m : lstModule) {
				switch (this.hmTypeModule.get(m.getIdTypeModule()).getNom()) {
					case "PPP" -> ajouterModulePPP(m);
					case "SAE" -> ajouterModuleSAE(m);
					case "normal" -> ajouterModuleNormale(m);
					case "stage" -> ajouterModuleStage(m);
				}
			}
			tbV.setItems(lst);
			lstTableView.add(tbV);

			borderPaneTab.setCenter(tbV);
			borderPaneTab.setTop(flowPaneTxtF);

			Tab tab = new Tab("Semestre " + (cpt + 1), borderPaneTab);
			tabTab[cpt++] = tab;

		}

		this.tabPane.getTabs().addAll(tabTab);
		AnchorPane.setTopAnchor(tabPane, 5.0);
		AnchorPane.setRightAnchor(tabPane, 5.0);
		AnchorPane.setBottomAnchor(tabPane, 5.0);
		AnchorPane.setLeftAnchor(tabPane, 5.0);
	}

	public void ajouterModulePPP(Module m) {
		int id = m.getId();
		List<HeureCours> lstHeuresCours = this.ctrl.getModele().getHeureCoursByModule(id, m.getIdAnnee());
		ModuleIHM moduleIHM = new ModuleIHM();
		if (lstHeuresCours != null) {
			for (HeureCours hc : lstHeuresCours) {
				switch (this.hmTypeCours.get(hc.getIdTypeCours()).getNom()) {
					case "TP" -> moduleIHM.setTp(hc);
					case "TD" -> moduleIHM.setTd(hc);
					case "CM" -> moduleIHM.setCm(hc);
					case "Tut" -> moduleIHM.setTut(hc);
					case "HP" -> moduleIHM.setHp(hc);
				}
			}
		}
		Button btnAjouterIntervenant = this.getAjouterIntervenantButton();
		btnAjouterIntervenant.setId("AjouterIntervenant-" + id);
		btnAjouterIntervenant.addEventFilter(ActionEvent.ACTION, this);

		Button supButton = getSupButton();
		supButton.setId("Sup-" + id);
		supButton.addEventHandler(ActionEvent.ACTION, this);
		this.lst.add(new LigneModuleIHM(id, btnAjouterIntervenant, m.getCode(), m.getNom(), "", "", "", "", "", "", "",
				supButton));
		this.lst.add(new LigneModuleIHM(id, null, "", "nombre d'heures totales", moduleIHM.getCmHeure() + "",
				moduleIHM.getTdHeure() + "", moduleIHM.getTpHeure() + "", "", moduleIHM.getTutHeure() + "", "",
				moduleIHM.getHpHeure() + "", null));
		this.lst.add(new LigneModuleIHM(id, null, "", "nombre de semaines", moduleIHM.getCmSemaine() + "",
				moduleIHM.getTdSemaine() + "", moduleIHM.getTpSemaine() + "", "", "", "", "", null));
		this.lst.add(new LigneModuleIHM(id, null, "", "nombre d'heure par semaine", moduleIHM.getCmHeureSemaine() + "",
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

		Button supButton = getSupButton();
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

		Button supButton = getSupButton();
		supButton.setId("Sup-" + id);
		supButton.addEventHandler(ActionEvent.ACTION, this);
		this.lst.add(new LigneModuleIHM(id, btnAjouterIntervenant, m.getCode(), m.getNom(), "", "", "", "", "", "", "",
				supButton));
		this.lst.add(new LigneModuleIHM(id, null, "", "nombre d'heures totales", moduleIHM.getCmHeure() + "",
				moduleIHM.getTdHeure() + "", moduleIHM.getTpHeure() + "", "", "", "", moduleIHM.getHpHeure() + "",
				null));
		this.lst.add(new LigneModuleIHM(id, null, "", "nombre de semaines", moduleIHM.getCmSemaine() + "",
				moduleIHM.getTdSemaine() + "", moduleIHM.getTpSemaine() + "", "", "", "", "", null));
		this.lst.add(new LigneModuleIHM(id, null, "", "nombre d'heure par semaine", moduleIHM.getCmHeureSemaine() + "",
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

		Button supButton = getSupButton();
		supButton.setId("Sup-" + id);
		supButton.addEventHandler(ActionEvent.ACTION, this);
		this.lst.add(new LigneModuleIHM(id, btnAjouterIntervenant, m.getCode(), m.getNom(), "", "", "", "", "", "", "",
				supButton));
		this.lst.add(new LigneModuleIHM(id, null, "", "nombre d'heures totales", "", "", "",
				moduleIHM.getRehHeure() + "", moduleIHM.getTutHeure() + "", "", moduleIHM.getHpHeure() + "", null));
		this.lst.add(new LigneModuleIHM(id, null, "", "", "", "", "", "", "", "", "", null));
	}

	public Button getSupButton() {
		SVGPath supsvg = new SVGPath();
		supsvg.setContent(
				"M13.7766 10.1543L13.3857 21.6924M7.97767 21.6924L7.58686 10.1543M18.8458 6.03901C19.2321 6.10567 19.6161 6.17618 20.0001 6.25182M18.8458 6.03901L17.6395 23.8373C17.5902 24.5619 17.3018 25.2387 16.8319 25.7324C16.362 26.2261 15.7452 26.5002 15.1049 26.5H6.25856C5.61824 26.5002 5.00145 26.2261 4.53152 25.7324C4.0616 25.2387 3.77318 24.5619 3.72395 23.8373L2.51764 6.03901M18.8458 6.03901C17.5422 5.81532 16.2318 5.64555 14.9174 5.53005M2.51764 6.03901C2.13135 6.10439 1.74731 6.1749 1.36328 6.25054M2.51764 6.03901C3.82124 5.81532 5.13158 5.64556 6.44606 5.53005M14.9174 5.53005V4.35572C14.9174 2.84294 13.8895 1.58144 12.5567 1.534C11.307 1.48867 10.0564 1.48867 8.80673 1.534C7.47391 1.58144 6.44606 2.84422 6.44606 4.35572V5.53005M14.9174 5.53005C12.0978 5.28272 9.26562 5.28272 6.44606 5.53005");
		supsvg.setStroke(Color.BLACK);
		supsvg.setFill(Color.WHITE);
		supsvg.setStrokeWidth(1.5);
		supsvg.setStrokeLineCap(StrokeLineCap.ROUND);
		supsvg.setStrokeLineJoin(StrokeLineJoin.ROUND);

		Button supbtn = new Button();
		supbtn.setGraphic(supsvg);
		supbtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		supbtn.getStyleClass().add("info-btn");
		return supbtn;
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

	public void handle(Event action) {
		if (action instanceof ActionEvent) {
			if (action.getSource() instanceof Button) {
				Button btn = (Button) action.getSource();
				String[] textBtn = btn.getId().split("-");
				if (textBtn[0].equals("Sup")) {
					this.ctrl.getModele().supprimerModule(Integer.parseInt(textBtn[1]));
				}
				if (textBtn[0].equals("AjouterIntervenant")) {
					this.frameIntervention = new FrameIntervention(this.ctrl, this.centerPaneAccueil,
							this.hmModule.get(Integer.parseInt(textBtn[1])));
				}
			}

		}
		this.init();
	}

	public void changed(ObservableValue<? extends String> observable, String oldStr, String newStr) {
		final String regex = "\\d+";
		System.out.println("changed");
		if (observable == this.txtFTD.textProperty()) {
			System.out.println("fonctionne");
			if (newStr.matches(regex)) {
				this.hmSemestres.get(this.tabPane.getSelectionModel().getSelectedIndex())
						.setNbGTD(Integer.parseInt(newStr));
			} else {
				this.txtFTD.setText(oldStr);
			}
		}
		if (observable == this.txtFTP.textProperty()) {
			if (newStr.matches(regex)) {
				this.hmSemestres.get(this.tabPane.getSelectionModel().getSelectedIndex())
						.setNbGTP(Integer.parseInt(newStr));
			} else {
				this.txtFTP.setText(oldStr);
			}
		}
		if (observable == this.txtFCM.textProperty()) {
			if (newStr.matches(regex)) {
				this.hmSemestres.get(this.tabPane.getSelectionModel().getSelectedIndex())
						.setNbGCM(Integer.parseInt(newStr));
			} else {
				this.txtFCM.setText(oldStr);
			}
		}
		if (observable == this.txtFNbSemaines.textProperty()) {
			if (newStr.matches(regex)) {
				this.hmSemestres.get(this.tabPane.getSelectionModel().getSelectedIndex())
						.setNbSemaine(Integer.parseInt(newStr));
			} else {
				this.txtFNbSemaines.setText(oldStr);
			}
		}
	}

	public static class EditableTableCell extends TableCell<LigneModuleIHM, String> {

    private TextField textField;

    public EditableTableCell() {
        this.textField = new TextField();
        this.textField.setOnAction(event -> commitEdit(textField.getText()));
        this.textField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                commitEdit(textField.getText());
            }
        });
    }

    @Override
    public void startEdit() {
        super.startEdit();

        if (isEmpty()) {
            return;
        }

        setText(null);
        setGraphic(textField);
        textField.setText(getItem());
        textField.requestFocus();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(getItem());
        setGraphic(null);
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(item);
            setGraphic(null);
        }
    }
}


}