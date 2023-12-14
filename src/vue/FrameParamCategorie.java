package vue;

import java.util.Map;

import controleur.Controleur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modele.Categorie;

@SuppressWarnings("unused")
public class FrameParamCategorie {
	private Controleur ctrl;
	private AnchorPane centerPaneAccueil;
	private TextField nomCategorie;
	private TextField tfHeureMin;
	private TextField tfHeureMax;
	private Map<Integer, Categorie> hmCategorie;

	public FrameParamCategorie(Controleur ctrl, AnchorPane centerPaneAccueil) {
		this.ctrl = ctrl;
		this.centerPaneAccueil = centerPaneAccueil;
		System.out.println(this.ctrl.getModele().getHmCategories().size());
		this.hmCategorie = this.ctrl.getModele().getHmCategories();
		this.init();

		

	}

	@SuppressWarnings("deprecation")
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

		

		TableView<CategorieIHM> tableView = new TableView<>();
		


		String[] colonnes = new String[] { "Nom", "Supprimer" };

		if (tableView.getColumns().size() < 2) {
			for (String colonne : colonnes) {
				TableColumn<CategorieIHM, String> tbcl = new TableColumn<>(colonne);
				tbcl.setCellValueFactory(new PropertyValueFactory<>(colonne.toLowerCase()));
				tableView.getColumns().add(tbcl);
			}
		}
		tableView.setItems(olCategorie);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		VBox vbox = new VBox(5);
		Text nomC = new Text("Nom de la catégorie");
		this.nomCategorie = new TextField();

		Text tHeureMin = new Text("Heures minimales: ");
		this.tfHeureMin = new TextField();

		Text tHeureMax = new Text("Heure maximales: ");
		this.tfHeureMax = new TextField();

		StackPane popupLayout = new StackPane();
		vbox.getChildren().add(tableView);
		popupLayout.getChildren().add(vbox);
		Scene popupScene = new Scene(popupLayout, 400, 200);
		popupStage.setScene(popupScene);

		popupStage.showAndWait();
	}

}
