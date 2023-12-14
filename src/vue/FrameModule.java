package vue;

import modele.*;

import controleur.Controleur;


import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import javafx.scene.control.TextField;
import javafx.geometry.Pos;
import javafx.beans.value.*;

public class FrameModule implements EventHandler<Event>, ChangeListener<String> {
	private Controleur ctrl;
	private AnchorPane centerPaneAccueil;
	private Map<Integer, Semestre> hmSemestres;

	private Button btnAjouterSemestre;
	private VBox vbox;

	private List<List<TextField>> lstTxtFieldGrps;

	public FrameModule(Controleur ctrl, AnchorPane centerPaneAccueil) {
		this.ctrl = ctrl;
		this.centerPaneAccueil = centerPaneAccueil;
		this.lstTxtFieldGrps = new ArrayList<>(); // Une ArrayList d'une ArrayList de TextField
		this.init();
	}

	public void init() {
		this.centerPaneAccueil.getChildren().clear();
		this.centerPaneAccueil.getStylesheets().add(ResourceManager.STYLESHEET.toExternalForm());

		this.btnAjouterSemestre = new Button("Ajouter un Semestre");
		this.btnAjouterSemestre.setStyle("-fx-background-radius: 100");

		//Créer le Vbox pour bien placer les composants
		this.vbox = new VBox(10);
		this.vbox.setAlignment(Pos.CENTER);
		this.vbox.getChildren().add(btnAjouterSemestre);

		majAffichageSemestres();

		//Mettre le vBox dans le content du scrollPane qui va prendre toute la page
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPrefSize(this.centerPaneAccueil.getHeight(), this.centerPaneAccueil.getWidth());
		scrollPane.setContent(vbox);

		AnchorPane.setTopAnchor (scrollPane, 20.0);
		AnchorPane.setLeftAnchor(scrollPane, 20.0);
		
		this.centerPaneAccueil.getChildren().add(scrollPane);

		this.btnAjouterSemestre.addEventHandler(ActionEvent.ACTION, this);
	}

	public void majAffichageSemestres(){
		this.hmSemestres = this.ctrl.getModele().getHmSemestres();	

		for (@SuppressWarnings("unused") Semestre semestre : this.hmSemestres.values()) {
			// BorderPane avec un flowTitre en haut et le tableau au centre
			BorderPane borderPaneSemestre = new BorderPane();
			FlowPane flowPaneTitreSemestre = new FlowPane();

			// liste de textfields pour saisir les groupes du semestre
			List<TextField> lsttxtFields = new ArrayList<>();
			TextField txtFTD = new TextField();
			txtFTD.setMaxWidth(5 * 7);
			TextField txtFTP = new TextField();
			txtFTP.setMaxWidth(5 * 7);
			TextField txtFCM = new TextField();
			txtFCM.setMaxWidth(5 * 7);
			TextField txtFAutre = new TextField();
			txtFAutre.setMaxWidth(5 * 7);

			SVGPath svgP = new SVGPath();
			svgP.setContent("M0.81631 0.813695L26.4489 26.4463M26.4489 0.813695L0.81631 26.4463");
			Button exit = new Button();
			exit.setGraphic(svgP);

			// Ajouter les txtFields et labels au flowPane
			flowPaneTitreSemestre.getChildren().add(new Label("Semestre " + Semestre.NB_SEMESTRE + 1));
			flowPaneTitreSemestre.getChildren().add(new Label("  "));
			flowPaneTitreSemestre.getChildren().add(new Label("TD : "));
			flowPaneTitreSemestre.getChildren().add(txtFTD);
			flowPaneTitreSemestre.getChildren().add(new Label("TP : "));
			flowPaneTitreSemestre.getChildren().add(txtFTP);
			flowPaneTitreSemestre.getChildren().add(new Label("CM : "));
			flowPaneTitreSemestre.getChildren().add(txtFCM);
			flowPaneTitreSemestre.getChildren().add(new Label("Autre : "));
			flowPaneTitreSemestre.getChildren().add(txtFAutre);
			flowPaneTitreSemestre.getChildren().add(exit);

			// flowPaneTitreSemestre.getChildren().add();

			lsttxtFields.add(txtFTD);
			lsttxtFields.add(txtFTP);
			lsttxtFields.add(txtFCM);
			lsttxtFields.add(txtFAutre);
			this.lstTxtFieldGrps.add(lsttxtFields);
			borderPaneSemestre.setTop(flowPaneTitreSemestre);
			this.vbox.getChildren().add(this.vbox.getChildren().size() - 1, borderPaneSemestre);
		}
	}

	public void creerSemestre() {
		this.hmSemestres.put(Semestre.NB_SEMESTRE+1,new Semestre(0,0,1,1,this.ctrl.getModele().getIdAnnee()));
	}

	public void handle(Event action) {
		if (action.getSource() == this.btnAjouterSemestre) 
			this.creerSemestre();
		else if (action.getSource() instanceof Button) {
			Button button = (Button) action.getSource();
			if (!(button.getId() == null)) {
				
			}
		}
		
	}

	public void changed(ObservableValue<? extends String> observable, String oldStr, String newStr) {
		
	}
}
