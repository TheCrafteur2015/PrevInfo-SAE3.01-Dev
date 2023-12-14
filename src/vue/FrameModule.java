package vue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import controleur.Controleur;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import modele.Semestre;

public class FrameModule implements EventHandler<Event>{
	private Controleur ctrl;
	private AnchorPane centerPaneAccueil;
	private Map<Integer, Semestre> hmSemestres;
	
	private HashMap<Semestre,ArrayList<TextField>> hmTF;

	private TabPane tabPane;
	

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
			flowPaneTxtF.getChildren().add(new Label("nbGroupeTD : "));
			flowPaneTxtF.getChildren().add(txtFTD);

			flowPaneTxtF.getChildren().add(new Label("nbGroupeTP : "));
			flowPaneTxtF.getChildren().add(txtFTP);

			flowPaneTxtF.getChildren().add(new Label("nbGroupeCM : "));
			flowPaneTxtF.getChildren().add(txtFCM);

			flowPaneTxtF.getChildren().add(new Label("nbSemaines : "));
			flowPaneTxtF.getChildren().add(txtFNbSemaines);

				
			lsttxtF.add(txtFTD);
			lsttxtF.add(txtFTP);
			lsttxtF.add(txtFCM);
			lsttxtF.add(txtFNbSemaines);
			this.hmTF.put(semestre,lsttxtF);

			TableView tbV = new TableView<>();
			borderPaneTab.setCenter(tbV);
			borderPaneTab.setTop(flowPaneTxtF);

			Tab tab = new Tab("Semestre "+(cpt+1), borderPaneTab);
			tabTab[cpt] = tab;
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
