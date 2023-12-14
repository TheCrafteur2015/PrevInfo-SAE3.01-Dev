package vue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controleur.Controleur;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
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
			this.hmTypeCours = this.ctrl.getModele().getHmTypeCours();
			ObservableList<LigneModuleIHM> lst = FXCollections.observableArrayList();

	
			for (Module m : this.hmModule.values()) {
				if (m.getIdSemestre() == cpt+1) {
					List<HeureCours> lstHeuresCours = this.ctrl.getModele().getHeureCoursByModule(m.getId(), m.getIdAnnee());
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
					lst.add(new LigneModuleIHM(m.getId(), null, m.getNom(), "", "", "", "", "", "", "", null));
					lst.add(new LigneModuleIHM(m.getId(), null, "nombre d'heures totales"   , moduleIHM.getCmHeure()+""       , moduleIHM.getTdHeure()+""       , moduleIHM.getTpHeure()+""       , "", "", "", "", null));
					lst.add(new LigneModuleIHM(m.getId(), null, "nombre de semaines"        , moduleIHM.getCmSemaine()+""     , moduleIHM.getTdSemaine()+""     , moduleIHM.getTpSemaine()+""     , "", "", "", "", null));
					lst.add(new LigneModuleIHM(m.getId(), null, "nombre d'heure par semaine", moduleIHM.getCmHeureSemaine()+"", moduleIHM.getTdHeureSemaine()+"", moduleIHM.getTpHeureSemaine()+"", "", "", "", "", null));
					lst.add(new LigneModuleIHM(m.getId(), null, "", "", "", "", "", "", "", "", null));
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

	public void handle(Event action) {

		this.majTabs();
	}

	public void changed(ObservableValue<? extends String> observable, String oldStr, String newStr) {
		
	}
}
