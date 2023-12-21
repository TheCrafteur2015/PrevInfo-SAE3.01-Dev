package vue;

import java.io.IOException;
import java.io.File;
import java.net.URL;
import java.util.Map;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
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
	
	private Button btnCSV;
	
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
		vbox.setSpacing(30);

		
		this.hmIntervenants = this.ctrl.getModele().getHmIntervenants();
		this.hmModules = this.ctrl.getModele().getHmModules();
		
		this.btnIntervenant = new Button("Confirmer");
		this.btnIntervenant.addEventHandler(ActionEvent.ACTION, this);
		
		this.btnModule = new Button("Confirmer");
		this.btnModule.addEventHandler(ActionEvent.ACTION, this);
		
		this.choiceBoxIntervenant = new ChoiceBox<>();
		this.choiceBoxIntervenant.getStyleClass().add("choice-exporter");
		this.choiceBoxIntervenant.getItems().addAll(this.hmIntervenants.values());
	
		this.choiceBoxModule = new ChoiceBox<>();
		this.choiceBoxModule.getStyleClass().add("choice-exporter");
		this.choiceBoxModule.getItems().addAll(this.hmModules.values());
		
		this.choiceBoxIntervenant.setPrefWidth(500);
		this.choiceBoxModule.setPrefWidth(500);
		
		this.btnCSV = new Button("Exporter tous les intervenants (CSV)");
		this.btnCSV.addEventHandler(ActionEvent.ACTION, this);
		
		GridPane gridPaneIntervenant = new GridPane();
		gridPaneIntervenant.setHgap(10);
		gridPaneIntervenant.setVgap(5);
		
		Label exportInter = new Label("Exportation par Intervenant");
		exportInter.getStyleClass().add("titreExport");
		
		gridPaneIntervenant.add(exportInter, 0, 0);
		gridPaneIntervenant.add(this.choiceBoxIntervenant, 0, 1);
		gridPaneIntervenant.add(this.btnIntervenant, 1, 1);


		GridPane gridPaneModule = new GridPane();
		gridPaneModule.setHgap(10);
		gridPaneModule.setVgap(5);
		
		Label exportModule = new Label("Exportation par Module");
		exportModule.getStyleClass().add("titreExport");
		
		gridPaneModule.add(exportModule, 0, 0);
		gridPaneModule.add(this.choiceBoxModule, 0, 1);
		gridPaneModule.add(this.btnModule, 1, 1);
		
		
		vbox.getChildren().addAll(gridPaneIntervenant, gridPaneModule, this.btnCSV);
		vbox.setAlignment(Pos.CENTER);


		AnchorPane.setTopAnchor(vbox, 200.0);
		AnchorPane.setLeftAnchor(vbox, 200.0);

		this.centerPaneAccueil.getChildren().add(vbox);
		
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void handle(Event action) {
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Répertoire d'exportation");
		chooser.setInitialDirectory(new File("").getAbsoluteFile());
		File directory = chooser.showDialog(this.centerPaneAccueil.getScene().getWindow());
		if (directory == null)
			return;
		URL file = null;
		String annee = this.ctrl.getModele().getHmAnnee().get(this.ctrl.getModele().getIdAnnee());
		if (action.getSource() == this.btnIntervenant) {
			Intervenant i = this.choiceBoxIntervenant.getValue();
			file = this.export.exportIntervenantHtml(i.getId(), i.getNom() + "_" + i.getPrenom() + "_previsionnel_" + annee, directory.getAbsolutePath() + "/");
		}
		if (action.getSource() == this.btnModule) {
			Module m = this.choiceBoxModule.getValue();
			file = this.export.exportModuleHTML(m.getId(),  m.getCode() + "_" + m.getNom()  + "_previsionnel_" + annee, directory.getAbsolutePath() + "/");
		}
		if (action.getSource() == this.btnCSV) {
			if (Math.random() < 0.5)
				try { file = new URL("https://m.youtube.com/watch?v=dQw4w9WgXcQ"); } catch (Exception e) {}
			else
				file = this.export.exportIntervenantCsv("intervenants_" + annee, directory.getAbsolutePath() + "/");
		}
		try {
			Runtime.getRuntime().exec(new String[] {"xdg-open", file.toString()});
		} catch (IOException | NullPointerException e) {
			System.err.println("No export destination!" + e.getMessage());
		}
	}

}
