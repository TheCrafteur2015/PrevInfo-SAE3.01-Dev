package vue;

import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import controleur.Controleur;
import modele.Categorie;
import modele.Modele;
import vue.ControleurIHM.Notification;
import vue.modeleIHM.CategorieIHM;

public class FrameParamCategorie implements ChangeListener<String>, EventHandler<ActionEvent> {
	
	private Controleur ctrl;
	
	
	private TextField nomCategorie;
	private TextField tfHeureMin;
	private TextField tfHeureMax;
	private TextField tfRatioTp;
	
	private Map<Integer, Categorie> hmCategorie;
	
	private Button btnAjouter;
	
	private TableView<CategorieIHM> tableView;
	
	public FrameParamCategorie(Controleur ctrl, AnchorPane centerPaneAccueil) {
		this.ctrl = ctrl;
		this.hmCategorie = this.ctrl.getModele().getHmCategories();
		this.init();
	}
	
	public void init() {
		Stage popupStage = new Stage();
		popupStage.setResizable(false);
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.setTitle("Paramétrer les catégories");
		popupStage.setOnCloseRequest(this::closeWindowEvent);
		
		ObservableList<CategorieIHM> olCategorie = FXCollections.observableArrayList();
		
		for (Categorie c : this.hmCategorie.values()) {
			Button btnSup = ResourceManager.getSupButton();
			btnSup.setId(c.getId() + "");
			btnSup.addEventHandler(ActionEvent.ACTION, this);
			olCategorie.add(new CategorieIHM(c.getNom(), btnSup));
		}
		
		this.btnAjouter = new Button("Ajouter");
		this.btnAjouter.setDisable(true);
		this.btnAjouter.addEventHandler(ActionEvent.ACTION, this);
		
		GridPane gridPane = new GridPane();
		GridPane gridPaneHeure = new GridPane();
		
		this.tableView = new TableView<>();
		tableView.setPrefWidth(350);
		// tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView.setMaxHeight(178);
		tableView.getStyleClass().add("noheader");
		
		String[] colonnes = { "Nom", "Supprimer" };
		
		if (tableView.getColumns().size() < 2) {
			for (String colonne : colonnes) {
				TableColumn<CategorieIHM, String> tbcl = new TableColumn<>(colonne);
				tbcl.getStyleClass().add("center");
				tbcl.setCellValueFactory(new PropertyValueFactory<>(colonne.toLowerCase()));
				tbcl.setReorderable(false);
				if (colonne.equals("Nom"))
					tbcl.prefWidthProperty().bind(tableView.widthProperty().multiply(0.775));
				else
					tbcl.prefWidthProperty().bind(tableView.widthProperty().multiply(0.175));
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
						double x = event.getScreenX();
						if (index >= 0 && index < tableView.getItems().size() && (x < 850 || x > 890)
								&& tableView.getSelectionModel().isSelected(index)) {
							tableView.getSelectionModel().clearSelection();
							nomCategorie.setText("");
							tfHeureMin.setText("");
							tfHeureMax.setText("");
							tfRatioTp.setText("");
							btnAjouter.setText("Ajouter");
							event.consume();
						} else if (index >= 0 && index < tableView.getItems().size()) {
							int id = Integer.parseInt(tableView.getItems().get(index).getSupprimer().getId());
							Categorie c = ctrl.getModele().getHmCategories().get(id);
							nomCategorie.setText(c.getNom());
							tfHeureMin.setText(c.gethMin() + "");
							tfHeureMax.setText(c.gethMax() + "");
							String ratioTp =  c.getRatioTp() + "";
							if (FrameMultiplicateur.isInteger(c.getRatioTp()) != null) tfRatioTp.setText(FrameMultiplicateur.isInteger(c.getRatioTp()) + "");
							else tfRatioTp.setText(String.format("%.3f",c.getRatioTp()).replace(",", "."));
							if (ratioTp.startsWith("0.666")) tfRatioTp.setText("2/3");
							btnAjouter.setText("Modifier");

						}
					}
				});
				return row;
			}
		});
		VBox vbox = new VBox(5);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(15, 0, 15, 20));
		vbox.setSpacing(30);
		
		Text nomC = new Text("Nom de la catégorie");
		this.nomCategorie = new TextField();
		this.nomCategorie.textProperty().addListener(this);
		this.nomCategorie.setPrefWidth(26 * 7);
		
		Text tHeureMin = new Text("Heures minimales: ");
		this.tfHeureMin = new TextField();
		this.tfHeureMin.textProperty().addListener(this);
		this.tfHeureMin.setMaxWidth(8 * 7);
		
		Text tHeureMax = new Text("Heures maximales: ");
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
		
		gridPaneHeure.setVgap(5);
		
		GridPane gridPaneCat = new GridPane();
		gridPaneCat.add(nomC, 0, 0);
		gridPaneCat.add(this.nomCategorie, 0, 1);
		
		gridPane.setPadding(new Insets(30, 50, 30, 20));
		StackPane stackPane = new StackPane();
		
		vbox.getChildren().addAll(gridPaneCat, gridPaneHeure, this.btnAjouter);
		stackPane.getChildren().addAll(vbox);
		gridPane.add(tableView, 0, 0);
		gridPane.add(stackPane, 1, 0);
		popupLayout.getChildren().add(gridPane);
		popupLayout.getStylesheets().add(ResourceManager.STYLESHEET.toExternalForm());
		
		Scene popupScene = new Scene(popupLayout, 620, 300);
		popupStage.setScene(popupScene);
		
		popupStage.showAndWait();
		
		
	}
	
	private void closeWindowEvent(WindowEvent event) {
		this.ctrl.getVue().getFrameIntervenant().init();
		
	}

	public void maj() {
		this.hmCategorie = this.ctrl.getModele().getHmCategories();
		ObservableList<CategorieIHM> olCategorie = FXCollections.observableArrayList();
		
		for (Categorie c : this.hmCategorie.values()) {
			Button btnSup = ResourceManager.getSupButton();
			btnSup.setId(c.getId() + "");
			btnSup.addEventHandler(ActionEvent.ACTION, this);
			olCategorie.add(new CategorieIHM(c.getNom(), btnSup));
		}
		this.tableView.setItems(olCategorie);
		this.tfHeureMax.setText("");
		this.tfHeureMin.setText("");
		this.tfRatioTp.setText("");
		this.nomCategorie.setText("");
		this.btnAjouter.setText("Ajouter");
	}
	
	@Override
	public void changed(ObservableValue<? extends String> observable, String oldString, String newString) {
		if (!this.tfHeureMin.getText().matches(Modele.REGEX_DOUBLE) || this.tfHeureMin.getText().length() > 7) {
			this.tfHeureMin.setText(oldString);
		}
		if (!this.tfHeureMax.getText().matches(Modele.REGEX_DOUBLE)|| this.tfHeureMax.getText().length() > 7) {
			this.tfHeureMax.setText(oldString);
		}
		if (!this.tfRatioTp.getText().matches(Modele.REGEX_DOUBLE_FRACTION) || this.tfRatioTp.getText().length() > 7) {
			this.tfRatioTp.setText(oldString);
		}
		if ( this.nomCategorie.getText().length() > 20) 
			this.nomCategorie.setText(oldString);
		
		this.btnAjouter.setDisable(this.nomCategorie.getText().isEmpty() || this.tfHeureMin.getText().isEmpty()
				|| this.tfHeureMax.getText().isEmpty() || this.tfRatioTp.getText().isEmpty());
	}
	
	@Override
	public void handle(ActionEvent event) {
		double ratioTp = 1.0;
		if (event.getSource() instanceof Button btn && event.getSource() != this.btnAjouter) {
			this.ctrl.getVue().popupValider();
			if (ControleurIHM.bIsValidate) {
				int id = Integer.parseInt(btn.getId());
				this.ctrl.getModele().supprimerCategorie(id);
			}
			ControleurIHM.bIsValidate = false;
			this.maj();
		}
		if (event.getSource() == this.btnAjouter) {
			if (this.tfRatioTp.getText().contains("/")) {
				ratioTp = Double.parseDouble(this.tfRatioTp.getText().split("/")[0])
						/ Double.parseDouble(this.tfRatioTp.getText().split("/")[1]);
			} else {
				ratioTp = Double.parseDouble(this.tfRatioTp.getText());
			}
			
			if (Double.parseDouble(this.tfHeureMax.getText()) < Double.parseDouble(this.tfHeureMin.getText())) {
				this.ctrl.getVue().afficherNotification("Erreur",
						"L'heure minimale est supérieur à l'heure maximale", Notification.ERREUR);
				return;
			} 
			
			if (this.btnAjouter.getText().equals("Ajouter")) {
				this.ctrl.getModele().ajouterCategorie(this.nomCategorie.getText(),
						Double.parseDouble(this.tfHeureMin.getText()),
						Double.parseDouble(this.tfHeureMax.getText()),
						ratioTp);
				this.maj();
			}
				
			if (this.btnAjouter.getText().equals("Modifier")) {
				int id = Integer.parseInt(this.tableView.getSelectionModel().getSelectedItem().getSupprimer().getId());
				Categorie c = this.ctrl.getModele().getHmCategories().get(id);
				c.setNom(this.nomCategorie.getText());
				c.sethMin(Double.parseDouble(this.tfHeureMin.getText()));
				c.sethMax(Double.parseDouble(this.tfHeureMax.getText()));
				c.setRatioTp(ratioTp);
				
				this.ctrl.getModele().updateCategorie(c);
				this.maj();
				
				this.ctrl.getVue().getFrameIntervenant().majTableIntervenant();
				
				
				
			}
		}
	}
}