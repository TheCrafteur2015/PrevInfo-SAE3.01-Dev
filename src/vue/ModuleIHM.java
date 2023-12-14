package vue;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ModuleIHM {

	private Button ajouterProf;
	private TextField nom;
	private TextField nbSemaines;
	private TextField hTD;
	private TextField hTP;
	private TextField hCM;
	private Button supprimer;

	public ModuleIHM(Button ajouterProf, TextField nom, TextField nbSemaines, TextField hTD, TextField hTP, TextField hCM, Button supprimer) {
		this.ajouterProf = ajouterProf;
		this.nom = nom;
		this.nbSemaines = nbSemaines;
		this.hTD = hTD;
		this.hTP = hTP;
		this.hTP = hCM;
		this.supprimer = supprimer;
	}

	public Button getAjouterProf() {
		return ajouterProf;
	}

	public void setAjouterProf(Button ajouterProf) {
		this.ajouterProf = ajouterProf;
	}

	public TextField getNom() {
		return nom;
	}

	public void setNom(TextField nom) {
		this.nom = nom;
	}

	public TextField getNbSemaines() {
		return nbSemaines;
	}

	public void setNbSemaines(TextField nbSemaines) {
		this.nbSemaines = nbSemaines;
	}

	public TextField gethTD() {
		return hTD;
	}

	public void sethTD(TextField hTD) {
		this.hTD = hTD;
	}

	public TextField gethTP() {
		return hTP;
	}

	public void sethTP(TextField hTP) {
		this.hTP = hTP;
	}

	public TextField gethCM() {
		return hCM;
	}

	public void sethCM(TextField hCM) {
		this.hCM = hCM;
	}

	public Button getSupprimer() {
		return supprimer;
	}

	public void setSupprimer(Button supprimer) {
		this.supprimer = supprimer;
	}

	

}
