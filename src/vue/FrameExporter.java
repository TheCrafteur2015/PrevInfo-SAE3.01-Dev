package vue;

import java.util.Map;

import controleur.Controleur;
import javafx.beans.value.ObservableValue;
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
import modele.Intervenant;
import modele.Module;

public class FrameExporter implements EventHandler<Event>{
	private Controleur ctrl;
	private AnchorPane centerPaneAccueil;
	private Map<Integer, Intervenant> hmIntervenants;

	private Button btnIntervenant;
	private ChoiceBox<Intervenant> choiceBoxIntervenant;


	private Map<Integer, Module> hmModules;
	private Button btnModule;
	private ChoiceBox<Module> choiceBoxModule;
	

	public FrameExporter(Controleur ctrl, AnchorPane centerPaneAccueil) {
		this.ctrl = ctrl;
		this.centerPaneAccueil = centerPaneAccueil;
		
		this.init();
	}

	public void init() {
		this.centerPaneAccueil.getChildren().clear();
		this.centerPaneAccueil.getStylesheets().add(ResourceManager.STYLESHEET.toExternalForm());

		VBox vbox = new VBox(5);

		BorderPane borderPane = new BorderPane();
		this.hmIntervenants = this.ctrl.getModele().getHmIntervenants();
		this.btnIntervenant = new Button("Confirmer");
		this.choiceBoxIntervenant = new ChoiceBox<>();

		for (Intervenant i : this.hmIntervenants.values()) {
			this.choiceBoxIntervenant.getItems().add(i);			
		}
		

		GridPane gridPane1 = new GridPane();
		gridPane1.add(new Text("Exportation par Intervenant"), 0, 0);
		gridPane1.add(this.choiceBoxIntervenant, 0, 1);
		gridPane1.add(this.btnIntervenant, 1, 1);
		gridPane1.setPadding(new Insets(10));
		gridPane1.setVgap(10);
		gridPane1.setHgap(20);


		this.hmModules = this.ctrl.getModele().getHmModules();
		this.btnModule = new Button("Confirmer");
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

		borderPane.setTop(gridPane1);
		borderPane.setBottom(gridPane2);

		this.choiceBoxIntervenant.setMaxWidth(500);
		this.choiceBoxModule.setMaxWidth(500);

		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(borderPane);

		this.centerPaneAccueil.getChildren().addAll(vbox);
		
	}

	public void handle(Event action) {
	
	}

	public void changed(ObservableValue<? extends String> observable, String oldStr, String newStr) {
		
	}
}
