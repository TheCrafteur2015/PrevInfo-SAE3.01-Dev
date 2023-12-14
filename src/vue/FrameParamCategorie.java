package vue;

import java.util.Map;

import controleur.Controleur;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modele.Categorie;


public class FrameParamCategorie implements ChangeListener<String>{
	private Controleur ctrl;
	private AnchorPane centerPaneAccueil;
	private TextField nomCategorie;
	private TextField tfHeureMin;
	private TextField tfHeureMax;
	private TextField tfRatioTp;
	private Map<Integer, Categorie> hmCategorie;
	private Button btnAjouter;

	public FrameParamCategorie(Controleur ctrl, AnchorPane centerPaneAccueil) {
		this.ctrl = ctrl;
		this.centerPaneAccueil = centerPaneAccueil;
		System.out.println(this.ctrl.getModele().getHmCategories().size());
		this.hmCategorie = this.ctrl.getModele().getHmCategories();
		this.init();

		

	}

	public void init() {
		Stage popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.setTitle("Pop-up");

		ObservableList<CategorieIHM> olCategorie = FXCollections.observableArrayList();

		for (Categorie c : this.hmCategorie.values()) {
			Button btnSup = this.ctrl.getVue().getFrameIntervenant().getSupButton();
			btnSup.setId(c.getId() + "");
			olCategorie.add(new CategorieIHM(c.getNom(), btnSup));
		}
		this.btnAjouter = new Button("Ajouter");
		

		BorderPane borderPane = new BorderPane();
		GridPane gridPaneHeure = new GridPane();

		TableView<CategorieIHM> tableView = new TableView<>();

		
		String[] colonnes = new String[] { "Nom", "Supprimer" };

		if (tableView.getColumns().size() < 2) {
			for (String colonne : colonnes) {
				TableColumn<CategorieIHM, String> tbcl = new TableColumn<>(colonne);
				tbcl.setStyle("-fx-alignment: CENTER;"); 
				tbcl.setCellValueFactory(new PropertyValueFactory<>(colonne.toLowerCase()));
				tableView.getColumns().add(tbcl);
			}
		}
		tableView.setItems(olCategorie);
		VBox vbox = new VBox(5);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(10));
		Text nomC = new Text("Nom de la catégorie");
		this.nomCategorie = new TextField();
		this.nomCategorie.textProperty().addListener(this);

		Text tHeureMin = new Text("Heures minimales: ");
		this.tfHeureMin = new TextField();
		this.tfHeureMin.textProperty().addListener(this);

		Text tHeureMax = new Text("Heure maximales: ");
		this.tfHeureMax = new TextField();
		this.tfHeureMax.textProperty().addListener(this);

		Text tRatioTp = new Text("Ratio TP: ");
		this.tfRatioTp = new TextField();
		this.tfRatioTp.textProperty().addListener(this);

		
		StackPane popupLayout = new StackPane();
	
		gridPaneHeure.add(tHeureMin, 0, 0);
		gridPaneHeure.add(this.tfHeureMin, 1, 0);
		gridPaneHeure.add(tHeureMax, 0, 1);
		gridPaneHeure.add(this.tfHeureMax, 1, 1);

		GridPane gridPaneCat = new GridPane();
		gridPaneCat.add(nomC, 0, 0);
		gridPaneCat.add(this.nomCategorie, 0, 1);
		
		StackPane stackPane = new StackPane();

		vbox.getChildren().addAll(gridPaneCat, gridPaneHeure, this.btnAjouter);
		stackPane.getChildren().add(vbox);
		borderPane.setLeft(tableView);
		borderPane.setCenter(stackPane);
		popupLayout.getChildren().add(borderPane);
		Scene popupScene = new Scene(popupLayout, 600, 300);
		popupStage.setScene(popupScene);

		popupStage.showAndWait();
	}

	@Override
	public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'changed'");
	}

}
