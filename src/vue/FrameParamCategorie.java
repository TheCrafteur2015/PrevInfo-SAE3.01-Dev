package vue;

import java.util.Map;

import controleur.Controleur;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modele.Categorie;


public class FrameParamCategorie implements ChangeListener<String>,  EventHandler<MouseEvent>{
	private Controleur ctrl;
	private AnchorPane centerPaneAccueil;
	private TextField nomCategorie;
	private TextField tfHeureMin;
	private TextField tfHeureMax;
	private TextField tfRatioTp;
	private Map<Integer, Categorie> hmCategorie;
	private Button btnAjouter;
	private TableView<CategorieIHM> tableView;

	public FrameParamCategorie(Controleur ctrl, AnchorPane centerPaneAccueil) {
		this.ctrl = ctrl;
		this.centerPaneAccueil = centerPaneAccueil;
		
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
		this.btnAjouter.setDisable(true);

		BorderPane borderPane = new BorderPane();
		GridPane gridPaneHeure = new GridPane();

		this.tableView = new TableView<>();
		tableView.setPrefWidth(200);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView.setMaxHeight(195);
this.tableView.setOnMouseClicked(this);


		
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
		vbox.setPadding(new Insets(30,0,30,20));
		vbox.setSpacing(50);


		
		
		Text nomC = new Text("Nom de la catégorie");
		this.nomCategorie = new TextField();
		this.nomCategorie.textProperty().addListener(this);
		this.nomCategorie.setMaxWidth(30 * 7);

		Text tHeureMin = new Text("Heures minimales: ");
		this.tfHeureMin = new TextField();
		this.tfHeureMin.textProperty().addListener(this);
		this.tfHeureMin.setMaxWidth(8 * 7);

		Text tHeureMax = new Text("Heure maximales: ");
		this.tfHeureMax = new TextField();
		this.tfHeureMax.textProperty().addListener(this);
		this.tfHeureMax.setMaxWidth(8 * 7);

		Text tRatioTp = new Text("Ratio TP: ");
		this.tfRatioTp = new TextField();
		this.tfRatioTp.textProperty().addListener(this);
		this.tfRatioTp.setMaxWidth(8 * 7);
		
		
		StackPane popupLayout = new StackPane();
	
		gridPaneHeure.add(tHeureMin, 0, 0);
		gridPaneHeure.add(this.tfHeureMin, 1, 0);
		gridPaneHeure.add(tHeureMax, 0, 1);
		gridPaneHeure.add(this.tfHeureMax, 1, 1);
		gridPaneHeure.add(tRatioTp, 0, 2);
		gridPaneHeure.add(this.tfRatioTp, 1, 2);

		gridPaneHeure.setVgap(10);

		GridPane gridPaneCat = new GridPane();
		gridPaneCat.add(nomC, 0, 0);
		gridPaneCat.add(this.nomCategorie, 0, 1);
		
		borderPane.setPadding(new Insets(10));
		StackPane stackPane = new StackPane();

		vbox.getChildren().addAll(gridPaneCat, gridPaneHeure,this.btnAjouter);
		stackPane.getChildren().addAll(vbox);
		borderPane.setLeft(tableView);
		borderPane.setCenter(stackPane);
		popupLayout.getChildren().add(borderPane);
		popupLayout.getStylesheets().add(ResourceManager.STYLESHEET.toExternalForm());

		Scene popupScene = new Scene(popupLayout, 500, 320);
		popupStage.setScene(popupScene);

		popupStage.showAndWait();
	}

	@Override
	public void changed(ObservableValue<? extends String> observable, String oldString, String newString) {
		if (!this.tfHeureMin.getText().matches("[0-9 .]*")) {
					this.tfHeureMin.setText(oldString);
					}
		if (!this.tfHeureMax.getText().matches("[0-9 .]*")) {
			this.tfHeureMax.setText(oldString);
			}
		if (this.nomCategorie.getText().length() > 0 && this.tfHeureMin.getText().length() > 0
				&& this.tfHeureMax.getText().length() > 0 && this.tfRatioTp.getText().length() > 0) {
			
			this.btnAjouter.setDisable(false);
		} else {
			this.btnAjouter.setDisable(true);
		}
	}

	

	@Override
	public void handle(MouseEvent event) {
		if (event.getClickCount()==1)
{
		int id = Integer.parseInt(this.tableView.getSelectionModel().getSelectedItem().getSupprimer().getId());
		Categorie c = this.ctrl.getModele().getHmCategories().get(id);
		this.nomCategorie.setText(c.getNom());
		this.tfHeureMin.setText(c.gethMin()+"");
		this.tfHeureMax.setText(c.gethMax()+"");
		this.tfRatioTp.setText(c.getRatioTp()+"");
		this.btnAjouter.setText("Modifier");

}
					
	}

}
