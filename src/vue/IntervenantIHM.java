package vue;

import javafx.scene.control.Button;

public class IntervenantIHM {
	
	private Button infos;
	private String prenom;
	private String nom;
	private String categorie;
	private String email;
	private Button supprimer;
	private String erreurInter;
	
	public IntervenantIHM(Button infos, String prenom, String nom, String categorie, String email, Button supprimer, String erreurInter) {
		this.infos = infos;
		this.prenom = prenom;
		this.nom = nom;
		this.categorie = categorie;
		this.email = email;
		this.supprimer = supprimer;
		this.erreurInter = erreurInter;
	}
	
	public Button getInfos() {
		return this.infos;
	}
	
	public void setInfos(Button infos) {
		this.infos = infos;
	}
	
	public String getPrenom() {
		return this.prenom;
	}
	
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getCategorie() {
		return this.categorie;
	}
	
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Button getSupprimer() {
		return this.supprimer;
	}
	
	public void setSupprimer(Button supprimer) {
		this.supprimer = supprimer;
	}
	
	public String getErreurInter() {
		return erreurInter;
	}
	
	public void setErreurInter(String erreurInter) {
		this.erreurInter = erreurInter;
	}
	
}