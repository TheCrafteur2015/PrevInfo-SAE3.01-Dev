package vue;

import javafx.scene.control.Button;

public class IntervantIHM {

	private String infos;
	private String prenom;
	private String nom;
	private String categorie;
	private String email;
	private String supprimer;

	public IntervantIHM(String infos, String prenom, String nom, String categorie, String email, String supprimer) {
		this.infos = infos;
		this.prenom = prenom;
		this.nom = nom;
		this.categorie = categorie;
		this.email = email;
		this.supprimer = supprimer;
	}

	public Button getInfos() {
		return infos;
	}

	public void setInfos(Button infos) {
		this.infos = infos;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Button getSupprimer() {
		return supprimer;
	}

	public void setSupprimer(Button supprimer) {
		this.supprimer = supprimer;
	}

}
