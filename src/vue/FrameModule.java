package vue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controleur.Controleur;
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
import modele.Semestre;
import modele.HeureCours;
import modele.Module;
import modele.TypeCours;

public class FrameModule implements EventHandler<Event>{
	private Controleur ctrl;
	private AnchorPane centerPaneAccueil;
	private Map<Integer, Semestre> hmSemestres;
	
	private HashMap<Semestre,ArrayList<TextField>> hmTF;
	private Map<Integer, Module> hmModule;
	private Map<Integer, TypeCours> hmTypeCours;

	private TabPane tabPane;

	private List<TableView> lstTableView;
	

	public FrameModule(Controleur ctrl, AnchorPane centerPaneAccueil) {
		this.ctrl = ctrl;
		this.centerPaneAccueil = centerPaneAccueil;
		this.hmTF = new HashMap<Semestre,ArrayList<TextField>>();		

		this.init();
	}

	public void init() {
		this.centerPaneAccueil.getChildren().clear();
		this.centerPaneAccueil.getStylesheets().add(ResourceManager.STYLESHEET.toExternalForm());
		
		this.majTabs();

		this.centerPaneAccueil.getChildren().addAll(tabPane);
	}

	public void majTabs(){
		this.tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		this.hmSemestres = this.ctrl.getModele().getHmSemestres();
		this.lstTableView = new ArrayList<TableView>();

		Tab[] tabTab = new Tab[this.hmSemestres.size()];
	
		int cpt = 0;
		for (Semestre semestre : this.hmSemestres.values()) {
		
			BorderPane borderPaneTab = new BorderPane();
			ArrayList<TextField> lsttxtF = new ArrayList<TextField>();
			TextField txtFTD = new TextField(semestre.getNbGTD()+"");
			TextField txtFTP = new TextField(semestre.getNbGTP()+"");
			TextField txtFCM = new TextField(semestre.getNbGCM()+"");
			TextField txtFNbSemaines = new TextField(semestre.getNbSemaine()+"");

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
			this.hmTF.put(semestre,lsttxtF);

			TableView<LigneModuleIHM> tbV = new TableView<LigneModuleIHM>();
			//tbV.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

			String[] colonnes = new String[] { "id", "info", "Nom", "CM", "TD", "TP", "REH", "HTut", "SAE", "HP", "supprimer" };

			if (tbV.getColumns().size() < 11) {
				for (String colonne : colonnes) {
					TableColumn<LigneModuleIHM, String> tbcl = new TableColumn<>(colonne);
					tbcl.setCellValueFactory(new PropertyValueFactory<>(colonne.toLowerCase()));
					if (colonne.equals("id")) tbcl.setVisible(false);
					else if (colonne.equals("Nom")) tbcl.prefWidthProperty().bind(tbV.widthProperty().multiply(0.3));
					else if (colonne.equals("supprimer")) tbcl.prefWidthProperty().bind(tbV.widthProperty().multiply(0.14));
					else tbcl.prefWidthProperty().bind(tbV.widthProperty().multiply(0.07));
					tbV.getColumns().add(tbcl);
				}
			}
			
			this.hmModule = this.ctrl.getModele().getHmModules();	
			// System.out.println(hmModule);	
			this.hmTypeCours = this.ctrl.getModele().getHmTypeCours();
			ObservableList<LigneModuleIHM> lst = FXCollections.observableArrayList();

	
			for (Module m : this.hmModule.values()) {
				int id = m.getId();					
				if (m.getIdSemestre() == cpt+1) {
					List<HeureCours> lstHeuresCours = this.ctrl.getModele().getHeureCoursByModule(id, m.getIdAnnee());
					ModuleIHM moduleIHM = new ModuleIHM();
					if (lstHeuresCours != null) {
						for (HeureCours hc : lstHeuresCours) {
							switch (this.hmTypeCours.get(hc.getIdTypeCours()).getNom()) {
								case "TP" -> moduleIHM.setTp(hc);
								case "TD" -> moduleIHM.setTd(hc);
								case "CM" -> moduleIHM.setCm(hc);
								case "Tut" -> moduleIHM.setTut(hc);
								case "REH" -> moduleIHM.setReh(hc);
								case "SAE" -> moduleIHM.setSae(hc);
								case "HP" -> moduleIHM.setHp(hc);								
							}
						}
					}
					Button supButton = getSupButton();
					supButton.setId("Sup-" + id);
					supButton.addEventHandler(ActionEvent.ACTION, this);
					lst.add(new LigneModuleIHM(id, null, m.getNom(), "", "", "", "", "", "", "", supButton));
					lst.add(new LigneModuleIHM(id, null, "nombre d'heures totales"   , moduleIHM.getCmHeure()+""       , moduleIHM.getTdHeure()+""       , moduleIHM.getTpHeure()+""       , "", "", "", "", null));
					lst.add(new LigneModuleIHM(id, null, "nombre de semaines"        , moduleIHM.getCmSemaine()+""     , moduleIHM.getTdSemaine()+""     , moduleIHM.getTpSemaine()+""     , "", "", "", "", null));
					lst.add(new LigneModuleIHM(id, null, "nombre d'heure par semaine", moduleIHM.getCmHeureSemaine()+"", moduleIHM.getTdHeureSemaine()+"", moduleIHM.getTpHeureSemaine()+"", "", "", "", "", null));
					lst.add(new LigneModuleIHM(id, null, "", "", "", "", "", "", "", "", null));
				}
				
			}
			tbV.setItems(lst);
			lstTableView.add(tbV);


			borderPaneTab.setCenter(tbV);
			borderPaneTab.setTop(flowPaneTxtF);

			Tab tab = new Tab("Semestre "+(cpt+1), borderPaneTab);
			tabTab[cpt++] = tab;
			
		}		

		this.tabPane.getTabs().addAll(tabTab);
		AnchorPane.setTopAnchor   (tabPane, 5.0);
		AnchorPane.setRightAnchor (tabPane, 5.0);
		AnchorPane.setBottomAnchor(tabPane, 5.0);
		AnchorPane.setLeftAnchor  (tabPane, 5.0);
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

	public void handle(Event action) {
		if (action instanceof ActionEvent) {
			if (action.getSource() instanceof Button) {
				Button btn = (Button)action.getSource();
				String[] textBtn = btn.getId().split("-");
				if (textBtn[0].equals("Sup")) {
					this.ctrl.getModele().supprimerModule(Integer.parseInt(textBtn[1]));
				}
			}
				
		}
		this.init();
	}

	public void changed(ObservableValue<? extends String> observable, String oldStr, String newStr) {
		
	}
}
