package vue;

import java.util.Map;

import controleur.Controleur;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import modele.Exportation;
import modele.Intervenant;
import modele.Module;

public class FrameExporter implements EventHandler<Event> {
	
	private Controleur ctrl;
	private AnchorPane centerPaneAccueil;
	private Map<Integer, Intervenant> hmIntervenants;

	private Button btnIntervenant;
	private ChoiceBox<Intervenant> choiceBoxIntervenant;


	private Map<Integer, Module> hmModules;
	private Button btnModule;
	private ChoiceBox<Module> choiceBoxModule;
	private Exportation export;
	

	public FrameExporter(Controleur ctrl, AnchorPane centerPaneAccueil) {
		this.ctrl = ctrl;
		this.centerPaneAccueil = centerPaneAccueil;
		this.export = new Exportation(this.ctrl.getModele());
		this.init();
	}

	public void init() {
		this.centerPaneAccueil.getChildren().clear();
		this.centerPaneAccueil.getStylesheets().add(ResourceManager.STYLESHEET.toExternalForm());

		VBox vbox = new VBox();

		this.hmIntervenants = this.ctrl.getModele().getHmIntervenants();
		this.btnIntervenant = new Button("Confirmer");
		this.btnIntervenant.addEventHandler(ActionEvent.ACTION, this);
		this.choiceBoxIntervenant = new ChoiceBox<>();

		for (Intervenant i : this.hmIntervenants.values()) {
			this.choiceBoxIntervenant.getItems().add(i);
		}
		
		GridPane gridPaneIntervenant = new GridPane();
		gridPaneIntervenant.add(new Text("Exportation par Intervenant"), 0, 0);
		gridPaneIntervenant.add(this.choiceBoxIntervenant, 0, 1);
		gridPaneIntervenant.add(this.btnIntervenant, 1, 1);
		gridPaneIntervenant.setPadding(new Insets(10));
		gridPaneIntervenant.setVgap(10);
		gridPaneIntervenant.setHgap(20);


		this.hmModules = this.ctrl.getModele().getHmModules();
		this.btnModule = new Button("Confirmer");
		this.btnModule.addEventHandler(ActionEvent.ACTION, this);
		
		this.choiceBoxModule = new ChoiceBox<>();

		for (Module m : this.hmModules.values()) {
			this.choiceBoxModule.getItems().add(m);
		}

		GridPane gridPane2 = new GridPane();
		gridPane2.add(new Text("Exportation par Module"), 0, 0);
		gridPane2.add(this.choiceBoxModule, 0, 1);
		gridPane2.add(this.btnModule, 1, 1);
		gridPane2.setPadding(new Insets(10));
		gridPane2.setVgap(20);
		gridPane2.setHgap(20);

		
		GridPane gridPane3 = new GridPane();
		gridPane3.setAlignment(Pos.CENTER);
		gridPane3.add(this.choiceBoxIntervenant, 0, 0);
		gridPane3.add(gridPaneIntervenant, 1, 0);
		gridPane3.add(gridPane2, 1, 1);

		this.choiceBoxIntervenant.setMaxWidth(500);
		this.choiceBoxModule.setMaxWidth(500);
		
		//vbox.getChildren().addAll(gridPaneIntervenant, gridPaneModule);

		this.centerPaneAccueil.getChildren().add(gridPane3);
		
	}
	
	@Override
	public void handle(Event action) {
		if (action instanceof ActionEvent) {
			String annee = this.ctrl.getModele().getHmAnnee().get(this.ctrl.getModele().getIdAnnee());
			if (action.getSource() == this.btnIntervenant) {
				Intervenant i = this.choiceBoxIntervenant.getValue();
				this.export.exportIntervenantHtml(i.getId(), i.getNom() + "_" + i.getPrenom() + "_previsionnel_"+annee,"");
			}
			else if (action.getSource() == this.btnModule) {
				Module m = this.choiceBoxModule.getValue();
				this.export.exportModuleHTML(m.getId(),  m.getCode() + "_" + m.getNom()  + "_previsionnel_"+annee,"");
			}
		}
	}
	
	
	public void changed(ObservableValue<? extends String> observable, String oldStr, String newStr) {
		
	}

}
