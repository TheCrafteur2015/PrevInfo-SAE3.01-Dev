package vue;

import java.util.Map;

import controleur.Controleur;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
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
import javafx.util.Callback;
import modele.Categorie;

public class FrameParamCategorie implements ChangeListener<String>, EventHandler<ActionEvent> {
	private Controleur ctrl;
	private AnchorPane centerPaneAccueil;
	private TextField nomCategorie;
	private TextField tfHeureMin;
	private TextField tfHeureMax;
	private TextField tfRatioTp;
	private Map<Integer, Categorie> hmCategorie;
	private Button btnAjouter;
	private Button btnSup;
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
		popupStage.setTitle("Paramétrer une catégorie");

		ObservableList<CategorieIHM> olCategorie = FXCollections.observableArrayList();

		for (Categorie c : this.hmCategorie.values()) {
			Button btnSup = this.ctrl.getVue().getFrameIntervenant().getSupButton();
			btnSup.setId(c.getId() + "");
			btnSup.addEventHandler(ActionEvent.ACTION, this);

			olCategorie.add(new CategorieIHM(c.getNom(), btnSup));
		}
		this.btnAjouter = new Button("Ajouter");
		this.btnAjouter.setDisable(true);
		this.btnAjouter.addEventHandler(ActionEvent.ACTION, this);

		BorderPane borderPane = new BorderPane();
		GridPane gridPaneHeure = new GridPane();

		this.tableView = new TableView<>();
		tableView.setPrefWidth(200);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView.setMaxHeight(195);

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
		tableView.setRowFactory(new Callback<TableView<CategorieIHM>, TableRow<CategorieIHM>>() {
			@Override
			public TableRow<CategorieIHM> call(TableView<CategorieIHM> tableView2) {
				final TableRow<CategorieIHM> row = new TableRow<>();
				row.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						final int index = row.getIndex();
						if (index >= 0 && index < tableView.getItems().size()
								&& tableView.getSelectionModel().isSelected(index)) {
							tableView.getSelectionModel().clearSelection();
							nomCategorie.setText("");
							tfHeureMin.setText("");
							tfHeureMax.setText("");
							tfRatioTp.setText("");
							btnAjouter.setText("Ajouter");
							event.consume();
						} else if (index >=0 && index < tableView.getItems().size()) {
							int id = Integer.parseInt(tableView.getItems().get(index).getSupprimer().getId());
							Categorie c = ctrl.getModele().getHmCategories().get(id);
							nomCategorie.setText(c.getNom());
							tfHeureMin.setText(c.gethMin() + "");
							tfHeureMax.setText(c.gethMax() + "");
							tfRatioTp.setText(c.getRatioTp() + "");
							btnAjouter.setText("Modifier");
						}

					}
				});
				return row;
			}
		});
		VBox vbox = new VBox(5);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(30, 0, 30, 20));
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

		vbox.getChildren().addAll(gridPaneCat, gridPaneHeure, this.btnAjouter);
		stackPane.getChildren().addAll(vbox);
		borderPane.setLeft(tableView);
		borderPane.setCenter(stackPane);
		popupLayout.getChildren().add(borderPane);
		popupLayout.getStylesheets().add(ResourceManager.STYLESHEET.toExternalForm());

		Scene popupScene = new Scene(popupLayout, 500, 320);
		popupStage.setScene(popupScene);

		popupStage.showAndWait();
	}
	public void maj() {
		this.hmCategorie = this.ctrl.getModele().getHmCategories();
		ObservableList<CategorieIHM> olCategorie = FXCollections.observableArrayList();

		for (Categorie c : this.hmCategorie.values()) {
			Button btnSup = this.ctrl.getVue().getFrameIntervenant().getSupButton();
			btnSup.setId(c.getId() + "");
			btnSup.addEventHandler(ActionEvent.ACTION, this);

			olCategorie.add(new CategorieIHM(c.getNom(), btnSup));
		}
		this.tableView.setItems(olCategorie);
	}

	@Override
	public void changed(ObservableValue<? extends String> observable, String oldString, String newString) {
		if (!this.tfHeureMin.getText().matches("[0-9. ]*")) {
			this.tfHeureMin.setText(oldString);
		}
		if (!this.tfHeureMax.getText().matches("[0-9. ]*")) {
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
	public void handle(ActionEvent event) {
		if (event.getSource() instanceof Button btn&& event.getSource() != this.btnAjouter) {
		int id = Integer.parseInt(btn.getId());
				this.ctrl.getModele().supprimerCategorie(id);
				this.maj();
			}
		if (event.getSource() == this.btnAjouter) {
			if (this.btnAjouter.getText().equals("Ajouter")) {
				this.ctrl.getModele().ajouterCategorie(this.nomCategorie.getText(),
						Double.parseDouble(this.tfHeureMin.getText()), Double.parseDouble(this.tfHeureMax.getText()),
						Double.parseDouble(this.tfRatioTp.getText()));
				((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
			}
			if (this.btnAjouter.getText().equals("Modifier")) {
				int id = Integer.parseInt(this.tableView.getSelectionModel()
						.getSelectedItem().getSupprimer().getId());
				Categorie c = this.ctrl.getModele().getHmCategories().get(id);
				c.setNom(this.nomCategorie.getText());
				c.sethMin(Double.parseDouble(this.tfHeureMin.getText()));
				c.sethMax(Double.parseDouble(this.tfHeureMax.getText()));
				c.setRatioTp(Double.parseDouble(this.tfRatioTp.getText()));
				this.ctrl.getModele().updateCategorie(c);
				((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
			}
			
				

		}
	}

}
