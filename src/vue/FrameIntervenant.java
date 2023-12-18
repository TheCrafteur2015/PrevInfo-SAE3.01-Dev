package vue;

import java.util.Map;

import controleur.Controleur;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import modele.Categorie;
import modele.Intervenant;
import modele.Intervention;
import modele.Module;

public class FrameIntervenant implements EventHandler<Event>, ChangeListener<String> {

	private Controleur ctrl;
	private AnchorPane centerPaneAccueil;
	private TableView<IntervenantIHM> tableViewIntervenant;
	private Button btnParamIntervenants;
	private Button btnConfirmerIntervenant;

	private TextField tfPrenom;
	private TextField tfNom;
	private TextField tfEmail;
	private TextField tfHMin;
	private TextField tfHMax;
	private Button btnParamCategorie;
	private FrameParamCategorie frameParamCategorie;

	private ChoiceBox<Categorie> choiceBoxCategorie;

	private Intervenant modifIntervenant;

	public FrameIntervenant(Controleur ctrl, AnchorPane centerPaneAccueil) {
		this.ctrl = ctrl;
		this.centerPaneAccueil = centerPaneAccueil;
		this.tableViewIntervenant = new TableView<>();
		this.tableViewIntervenant.setEditable(true);
		this.modifIntervenant = null;

		this.init();
	}
	
	@SuppressWarnings("deprecation")
	public void init() {
		this.centerPaneAccueil.getChildren().clear();
		this.centerPaneAccueil.getStylesheets().add(ResourceManager.STYLESHEET.toExternalForm());

		// Creer le tableau

		String[] colonnes = new String[] { "Infos", "Prenom", "Nom", "Categorie", "Email", "Supprimer" };

		if (this.tableViewIntervenant.getColumns().size() < 6) {
			for (String colonne : colonnes) {
				TableColumn<IntervenantIHM, String> tbcl = new TableColumn<>(colonne);
				tbcl.setCellValueFactory(new PropertyValueFactory<>(colonne.toLowerCase()));
				this.tableViewIntervenant.getColumns().add(tbcl);
			}
		}

		this.majTableIntervenant();

		this.tableViewIntervenant.setPrefHeight(500);
		this.tableViewIntervenant.setPrefWidth(1100);
		this.tableViewIntervenant.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		this.tableViewIntervenant.setRowFactory(new Callback<TableView<IntervenantIHM>, TableRow<IntervenantIHM>>() {
			@Override
			public TableRow<IntervenantIHM> call(TableView<IntervenantIHM> tableView2) {
				final TableRow<IntervenantIHM> row = new TableRow<>();
				row.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						final int index = row.getIndex();
						if (index >= 0 && index < tableViewIntervenant.getItems().size()
								&& tableViewIntervenant.getSelectionModel().isSelected(index)) {
							tableViewIntervenant.getSelectionModel().clearSelection();
							
							event.consume();
						} 

					}
				});
				return row;
			}
		});

		AnchorPane.setTopAnchor(this.tableViewIntervenant, 20.0);
		AnchorPane.setLeftAnchor(this.tableViewIntervenant, 20.0);

		this.btnParamIntervenants = new Button("Paramètrer les Intervenants");
		this.btnParamIntervenants.setStyle("-fx-background-radius: 100");

		this.btnParamIntervenants.setPrefSize(250, 40);
		this.btnParamIntervenants.setId("btnParamIntervenants");
		this.btnParamIntervenants.addEventHandler(ActionEvent.ACTION, this);
		if (this.ctrl.getModele().getHmIntervenants().size() == 0) {
			this.btnParamIntervenants.setDisable(true);
		}

		AnchorPane.setTopAnchor(this.btnParamIntervenants, 580.0);
		AnchorPane.setLeftAnchor(this.btnParamIntervenants, 20.0);

		this.btnParamCategorie = new Button("Paramètrer une catégorie");
		
		btnParamCategorie.setStyle("-fx-background-radius: 100");

		btnParamCategorie.setPrefSize(250, 40);
		this.btnParamCategorie.addEventHandler(ActionEvent.ACTION, this);
		if (this.ctrl.getModele().getHmCategories().size() == 0) {
			this.btnParamCategorie.setDisable(true);
		}

		AnchorPane.setTopAnchor(btnParamCategorie, 580.0);
		AnchorPane.setLeftAnchor(btnParamCategorie, 800.0);

		this.centerPaneAccueil.getChildren().add(this.tableViewIntervenant);
		this.centerPaneAccueil.getChildren().add(btnParamIntervenants);
		this.centerPaneAccueil.getChildren().add(btnParamCategorie);
	}

	public void majTableIntervenant() {
		Map<Integer, Intervenant> hmInter = this.ctrl.getModele().getHmIntervenants();
		ObservableList<IntervenantIHM> lst = FXCollections.observableArrayList();

		for (Intervenant i : hmInter.values()) {
			Button infoButton = getInfoButton();
			infoButton.setId("Info-" + i.getId());
			Button supButton = ResourceManager.getSupButton();
			supButton.setId("Sup-" + i.getId());

			// infoButton.setOnAction(this);
			infoButton.addEventHandler(ActionEvent.ACTION, this);
			// supButton.setOnAction(this);
			supButton.addEventHandler(ActionEvent.ACTION, this);
			lst.add(new IntervenantIHM(infoButton, i.getPrenom(), i.getNom(),
					this.ctrl.getModele().getNomCateg(i.getIdCategorie()), i.getEmail(), supButton));
		}

		this.tableViewIntervenant.setItems(lst);
	}

	public Button getInfoButton() {
		SVGPath infosvg = new SVGPath();
		// infosvg.setContent(ResourceManager.getData("book"));
		infosvg.setContent(
				"M11.8626 4.02794C9.87308 2.05641 7.29356 0.967859 4.62087 0.971936C3.35115 0.971936 2.13213 1.21194 1 1.6546V20.6546C2.16305 20.2013 3.38756 19.9704 4.62087 19.9719C7.40291 19.9719 9.94114 21.1279 11.8626 23.0279M11.8626 4.02794C13.8521 2.0563 16.4317 0.967733 19.1044 0.971936C20.3741 0.971936 21.5931 1.21194 22.7252 1.6546V20.6546C21.5622 20.2013 20.3377 19.9704 19.1044 19.9719C16.4317 19.9679 13.8522 21.0564 11.8626 23.0279M11.8626 4.02794V23.0279");
		infosvg.setStroke(Color.BLACK);
		infosvg.setFill(Color.WHITE);
		infosvg.setStrokeWidth(1.5);
		infosvg.setStrokeLineCap(StrokeLineCap.ROUND);
		infosvg.setStrokeLineJoin(StrokeLineJoin.ROUND);

		Button infobtn = new Button();
		infobtn.setGraphic(infosvg);
		infobtn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		infobtn.getStyleClass().add("info-btn");
		return infobtn;
	}

	

	public void popupParamIntervenant(IntervenantIHM i) {
		if (i == null) this.modifIntervenant = null;
	
		Stage popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.setTitle("Ajouter un Intervenant");
		popupStage.centerOnScreen();
		popupStage.setHeight(450);
		popupStage.setWidth(300);
		popupStage.setResizable(false);

		Text pnom = new Text("Prénom  ");
		this.tfPrenom = new TextField();
		this.tfPrenom.setMaxWidth(30 * 7);
		this.tfPrenom.textProperty().addListener(this);
		this.tfPrenom.getStyleClass().add("textField");

		Text nom = new Text("Nom  ");
		this.tfNom = new TextField();
		this.tfNom.setMaxWidth(30 * 7);
		this.tfNom.textProperty().addListener(this);
		this.tfNom.getStyleClass().add("textField");

		Text email = new Text("Email  ");
		this.tfEmail = new TextField();
		this.tfEmail.setMaxWidth(30 * 7);
		this.tfEmail.textProperty().addListener(this);
		this.tfEmail.getStyleClass().add("textField");

		Text categorie = new Text("Catégorie ");
		this.choiceBoxCategorie = new ChoiceBox<>();
		choiceBoxCategorie.setMaxWidth(30 * 7);

		Map<Integer, Categorie> hmCategorie = this.ctrl.getModele().getHmCategories();
		for (Categorie c : hmCategorie.values()) {
			choiceBoxCategorie.getItems().add(c);
		}

		this.choiceBoxCategorie.addEventHandler(ActionEvent.ACTION, this);

		Text hMinText = new Text("Heures Minimales ");
		this.tfHMin = new TextField();
		this.tfHMin.setMaxWidth(10 * 7);
		this.tfHMin.textProperty().addListener(this);
		this.tfHMin.getStyleClass().add("textField");

		Text hMaxText = new Text("Heures Maximales ");
		this.tfHMax = new TextField();
		this.tfHMax.setMaxWidth(10 * 7);
		this.tfHMax.textProperty().addListener(this);
		this.tfHMax.getStyleClass().add("textField");

		if (choiceBoxCategorie.getItems().size() > 0) {
			choiceBoxCategorie.setValue(choiceBoxCategorie.getItems().get(0));
			Categorie c = this.choiceBoxCategorie.getValue();
			this.tfHMin.setText("" + c.gethMin());
			this.tfHMax.setText("" + c.gethMax());
		}
		this.btnConfirmerIntervenant = new Button("⨁  Ajouter");
		this.btnConfirmerIntervenant.getStyleClass().add("confirmBtn");
		this.btnConfirmerIntervenant.addEventHandler(ActionEvent.ACTION, this);
		this.btnConfirmerIntervenant.setDisable(true);

		VBox vbox = new VBox(5);
		vbox.setMaxSize(200, 400);

		BorderPane borderPane = new BorderPane();
		borderPane.setTop(pnom);
		borderPane.setCenter(this.tfPrenom);
		borderPane.setPadding(new Insets(10));

		BorderPane borderPane2 = new BorderPane();
		borderPane2.setTop(nom);
		borderPane2.setCenter(this.tfNom);
		borderPane2.setPadding(new Insets(10));

		BorderPane borderPane3 = new BorderPane();
		borderPane3.setTop(email);
		borderPane3.setCenter(this.tfEmail);
		borderPane3.setPadding(new Insets(10));

		BorderPane borderPane4 = new BorderPane();
		borderPane4.setTop(categorie);
		borderPane4.setCenter(choiceBoxCategorie);
		borderPane4.setPadding(new Insets(10));

		GridPane gridPane1 = new GridPane();
		gridPane1.add(hMinText, 0, 0);
		gridPane1.add(this.tfHMin, 1, 0);
		gridPane1.add(hMaxText, 0, 1);
		gridPane1.add(this.tfHMax, 1, 1);
		gridPane1.setPadding(new Insets(10));
		gridPane1.setVgap(10);

		BorderPane borderPane5 = new BorderPane();
		borderPane5.setCenter(this.btnConfirmerIntervenant);

		vbox.setAlignment(Pos.CENTER);

		if (i != null) {
			popupStage.setTitle("Paramétrer un Intervenant");
			this.btnConfirmerIntervenant.setText("Confirmer");
			Map<Integer, Intervenant> hmInter = this.ctrl.getModele().getHmIntervenants();
			Intervenant inter = hmInter.get(Integer.parseInt(i.getSupprimer().getId().split("-")[1]));
			this.modifIntervenant = inter;
			this.tfPrenom.setText(inter.getPrenom());
			this.tfNom.setText(inter.getNom());
			this.tfEmail.setText(inter.getEmail());
			this.tfHMin.setText(inter.gethMin()+"");
			this.tfHMax.setText(inter.gethMax()+"");
			for (Categorie c : hmCategorie.values()) {
				if (inter.getIdCategorie() == c.getId()) {
					this.choiceBoxCategorie.setValue(c);
					break;	
				}
			}
		}

		StackPane popupLayout = new StackPane();
		// popupLayout.getChildren().add(closeButton);
		vbox.getChildren().addAll(borderPane, borderPane2, borderPane3, borderPane4, gridPane1, borderPane5);
		popupLayout.getChildren().add(vbox);
		popupLayout.getStylesheets().add(ResourceManager.STYLESHEET.toExternalForm());
		Scene popupScene = new Scene(popupLayout, 200, 400);
		popupScene.getStylesheets().add(ResourceManager.STYLESHEET_POPUP.toExternalForm());
		popupStage.setScene(popupScene);

		popupStage.showAndWait();
	}
	
	@SuppressWarnings("deprecation")
	public void popupAfficheModule(int idIntervenant) {
		Stage popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.setTitle("Affichage des Modules");

		TableView<String> tableViewModules;
		tableViewModules = new TableView<>();
		tableViewModules.setEditable(true);

		tableViewModules.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		Map<Integer, Module> hmModule = this.ctrl.getModele().getHmModules();
		Map<Integer, Intervention> hmIntervention = this.ctrl.getModele().getHmInterventions();
		ObservableList<String> lst = FXCollections.observableArrayList();

		String[] colonnes = new String[] { "Nom" };

		TableColumn<String, String> tbcl = new TableColumn<>(colonnes[0]);
        // tbcl.setCellValueFactory(cellData -> cellData.getValue());
		/*
		tbcl.setCellValueFactory(new Callback<>() {
            @Override
            public javafx.beans.value.ObservableValue<String> call(TableColumn.CellDataFeatures<String, String> p) {
                return javafx.beans.binding.Bindings.createObjectBinding(() -> p.getValue());
            }
        });*/
		tbcl.setCellValueFactory(p -> Bindings.createObjectBinding(() -> p.getValue()));

		tableViewModules.getColumns().add(tbcl);


		for (Intervention i : hmIntervention.values()) {
			if (i.getIdIntervenant() == idIntervenant && !lst.contains(hmModule.get(i.getIdModule()).getNom())){
				lst.add(hmModule.get(i.getIdModule()).getNom());
			}
		}

		tableViewModules.setItems(lst);

		VBox vbox = new VBox(5);
		vbox.setMaxSize(400, 50 + lst.size() * 40);

		vbox.setAlignment(Pos.CENTER);

		StackPane popupLayout = new StackPane();
		vbox.getChildren().add(tableViewModules);
		popupLayout.getChildren().add(vbox);
		popupLayout.getStylesheets().add(ResourceManager.STYLESHEET.toExternalForm());
		Scene popupScene = new Scene(popupLayout, 400, 50 + lst.size() * 40);
		popupStage.setScene(popupScene);

		popupStage.showAndWait();
	}

	public void handle(Event event) {
		if (event.getSource() == this.btnParamCategorie) {
			this.frameParamCategorie = new FrameParamCategorie(this.ctrl, this.centerPaneAccueil, this);

		}
		if (event.getSource() == this.btnParamIntervenants) {
			if (this.tableViewIntervenant.getSelectionModel().getSelectedItems().size() != 0 )
				this.popupParamIntervenant(this.tableViewIntervenant.getSelectionModel().getSelectedItems().get(0));
			else 
				this.popupParamIntervenant(null);
		}
		if (event.getSource() == this.choiceBoxCategorie) {
			Categorie c = this.choiceBoxCategorie.getValue();
			this.tfHMin.setText("" + c.gethMin());
			this.tfHMax.setText("" + c.gethMax());
		}

		if (event.getSource() == this.btnConfirmerIntervenant) {
			String prenom = this.tfPrenom.getText();
			String nom = this.tfNom.getText();
			String email = this.tfEmail.getText();
			String tfHMinText = this.tfHMin.getText();
			String tfHMaxText = this.tfHMin.getText();

			Categorie c = this.choiceBoxCategorie.getValue();

			if (prenom.isEmpty() || nom.isEmpty()
					|| this.tfHMin.getText().isEmpty()
					|| this.tfHMax.getText().isEmpty() || c == null) {
				System.out.println("Veuillez remplir tous les champs");
				System.out.println("Prenom : " + prenom);
				System.out.println("Nom : " + nom);
				System.out.println("HMax : " + this.tfHMax.getText());
				System.out.println("Categorie : " + c);
			} else {
				double hMin = Double.parseDouble(tfHMinText);
				double hMax = Double.parseDouble(tfHMaxText);
				int annee = this.ctrl.getModele().getIdAnnee();

				// Intervenant i = new Intervenant(prenom, nom, email, hMin, hMax, annee,
				// c.getId());
				if (this.modifIntervenant != null) {
					this.modifIntervenant.setPrenom(prenom);
					this.modifIntervenant.setNom(nom);
					this.modifIntervenant.setEmail(email);
					this.modifIntervenant.sethMin(hMin);
					this.modifIntervenant.sethMax(hMax);
					this.ctrl.getModele().updateIntervenant(this.modifIntervenant);
				} else {
					this.ctrl.getModele().ajouterIntervenant(prenom, nom, email, hMin, hMax, annee);
				}
				
				((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
				this.majTableIntervenant();
			}

		} else if (event.getSource() instanceof Button) {
			Button button = (Button) event.getSource();
			if (!(button.getId() == null)) {
				String[] textButton = button.getId().split("-");
				if (textButton[0].equals("Info")) {
					this.popupAfficheModule(Integer.parseInt(textButton[1]));
				} else if (textButton[0].equals("Sup")) {
					this.ctrl.getModele().supprimerIntervenant(Integer.parseInt(textButton[1]));
					this.majTableIntervenant();
				}
			}
		}
	}
public ObservableList<String> getStylesheets()
{
	return this.centerPaneAccueil.getStylesheets();
}

	public void changed(ObservableValue<? extends String> observable, String oldStr, String newStr) {
		final String regex = "([0-9]+([.][0-9]*)?|[.][0-9]+)";

		if ((observable == this.tfPrenom.textProperty()|| observable == this.tfNom.textProperty())&&(!(this.tfPrenom.getText().isEmpty() || this.tfNom.getText().isEmpty()))) {
			this.tfEmail.setText(this.tfPrenom.getText().toLowerCase() + "." + this.tfNom.getText().toLowerCase() + "@univ-lehavre.fr");
		} 
		if (observable == this.tfHMin.textProperty()) {
			if (!(this.tfHMin.getText().matches(regex))) {
				this.tfHMin.setText(oldStr);
			}
		} else if (observable == this.tfHMax.textProperty()) {
			if (!(this.tfHMax.getText().matches(regex))) {
				this.tfHMax.setText(oldStr);
			}
		}
		if (this.tfPrenom.getText().isEmpty() || this.tfNom.getText().isEmpty()
				|| this.tfHMin.getText().isEmpty() || this.tfHMax.getText().isEmpty()
				|| this.choiceBoxCategorie.getValue() == null) {
			if (this.btnConfirmerIntervenant != null)
			this.btnConfirmerIntervenant.setDisable(true);
		} else {
			this.btnConfirmerIntervenant.setDisable(false);
			
		}
		

	}
}