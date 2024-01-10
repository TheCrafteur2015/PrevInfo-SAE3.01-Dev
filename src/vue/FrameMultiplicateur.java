package vue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import controleur.Controleur;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modele.Modele;
import modele.TypeCours;

public class FrameMultiplicateur implements EventHandler<Event>, ChangeListener<String> {

	private Controleur ctrl;
	private List<Text> alText;
	private List<TextField> alTextField;
	private Button btnConfirmerMultiplicateur;
	private boolean bErreur;

	public FrameMultiplicateur(Controleur ctrl) {
		this.ctrl = ctrl;
		this.init();
	}

	public void init() {
		Stage popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.centerOnScreen();
		popupStage.setTitle("Modification des multiplicateurs");
		popupStage.setHeight(350);
		popupStage.setWidth(300);
		popupStage.setResizable(false);

		Map<Integer, TypeCours> hmTypeCours = this.ctrl.getModele().getHmTypeCours();

		this.alText = new ArrayList<>();
		this.alTextField = new ArrayList<>();
		Text textTmp;
		TextField textFieldTmp;
		for (TypeCours tc : hmTypeCours.values()) {
			textTmp = new Text(tc.getNom());
			textFieldTmp = new TextField(tc.getCoefficient() + "");
			String coeff = tc.getCoefficient() + "";
			if (coeff.startsWith("0.666")) textFieldTmp.setText("2/3");
			if (isInteger(tc.getCoefficient()) != null)
				textFieldTmp = new TextField(isInteger(tc.getCoefficient()) + "");
			textFieldTmp.getStyleClass().add("coeffValue");
			textFieldTmp.setMaxWidth(7 * 7);
			textFieldTmp.textProperty().addListener(this);
			alText.add(textTmp);
			this.alTextField.add(textFieldTmp);
		}

		VBox vbox = new VBox(5);
		vbox.setMaxSize(200, alText.size() * 50);

		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		for (int i = 0; i < alText.size(); i++) {
			gridPane.add(alText.get(i), 0, i);
			gridPane.add(alTextField.get(i), 1, i);
		}
		gridPane.setPadding(new Insets(10));
		gridPane.setHgap(10);
		gridPane.setVgap(10);

		this.btnConfirmerMultiplicateur = new Button("Confirmer");
		this.btnConfirmerMultiplicateur.getStyleClass().add("confirmBtn");
		this.btnConfirmerMultiplicateur.addEventHandler(ActionEvent.ACTION, this);
		StackPane popupLayout = new StackPane();
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(this.btnConfirmerMultiplicateur);

		vbox.getChildren().add(gridPane);
		vbox.getChildren().add(borderPane);
		popupLayout.getChildren().add(vbox);
		Scene popupScene = new Scene(popupLayout, 200, alText.size() * 50);
		popupScene.getStylesheets().add(ResourceManager.STYLESHEET_POPUP.toExternalForm());
		popupStage.setScene(popupScene);
		popupStage.showAndWait();
	}

	public static Integer isInteger(double d) {
		if ((int) d + 0.0 == d)
			return (int) d;
		return null;
	}

	@Override
	public void handle(Event action) {
		if (action.getSource() == this.btnConfirmerMultiplicateur) {
			Double coeff;
			this.bErreur = false;
			List<String> alErreur = new ArrayList<>();

			for (int i = 0; i < this.alText.size(); i++) {
				coeff = null;
				if (this.alTextField.get(i).getText().contains("/") && !(this.alTextField.get(i).getText()
						.charAt(this.alTextField.get(i).getText().length() - 1) == '/')) {
					String[] partTxt = this.alTextField.get(i).getText().split("/");
					coeff = Double.parseDouble(partTxt[0]) / Double.parseDouble(partTxt[1]);
					this.ctrl.getModele().updateTypeCoursBrut(alText.get(i).getText(), coeff);
				} else if (!this.alTextField.get(i).getText().isEmpty()
						&& !this.alTextField.get(i).getText().contains("/")) {
					coeff = Double.parseDouble(alTextField.get(i).getText());
					this.ctrl.getModele().updateTypeCoursBrut(alText.get(i).getText(), coeff);
				} else {
					alErreur.add(alText.get(i).getText());
					this.bErreur = true;
				}
			}
			if (!this.bErreur) {
					((Stage) this.btnConfirmerMultiplicateur.getScene().getWindow()).close();
					this.ctrl.getVue().afficherNotification("Succès", "Les coefficients ont bien été modifiés", ControleurIHM.Notification.SUCCES);
				} else {
					String message = "Veuillez vérifier les champs suivants : ";
					for (String s : alErreur)
						message += s + ", ";
					message = message.substring(0, message.length() - 2);
					this.ctrl.getVue().afficherNotification("Erreur", message, ControleurIHM.Notification.ERREUR);
				}
		}
	}

	public void changed(ObservableValue<? extends String> observable, String oldString, String newString) {
		for (TextField text : alTextField) {
			if (observable == text.textProperty()) {
				if (!text.getText().matches(Modele.REGEX_DOUBLE_FRACTION) || text.getText().length() > 7)
					text.setText(oldString);
			}
		}
	}
}
