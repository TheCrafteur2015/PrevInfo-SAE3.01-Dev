package vue;

import javafx.scene.control.Button;

public class IntervenantModuleIHM {

	private String nom;
	private Button supprimer;

	public IntervenantModuleIHM(String nom, Button supprimer) {
		this.nom = nom;
		this.supprimer = supprimer;
	}

	public String getNom() {
		return nom;
	}

	public void setNomModule(String nom) {
		this.nom = nom;
	}

	public Button getSupprimer() {
		return supprimer;
	}

	public void setSupprimer(Button supprimer) {
		this.supprimer = supprimer;
	}
	
}
