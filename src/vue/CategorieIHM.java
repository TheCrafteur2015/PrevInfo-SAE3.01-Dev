package vue;

import javafx.scene.control.Button;

/**
 * CategorieIHM
 */
public class CategorieIHM {

	private String nom;
	private Button supprimer;

	public CategorieIHM(String nom, Button supprimer) {
		this.nom = nom;
		this.supprimer = supprimer;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Button getSupprimer() {
		return this.supprimer;
	}

	public void setSupprimer(Button supprimer) {
		this.supprimer = supprimer;
	}
	
}