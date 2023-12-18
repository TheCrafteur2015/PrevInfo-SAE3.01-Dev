package vue;

import javafx.scene.control.Button;

public class InterventionIHM{
	private String prenom;
	private String nom;
	private String nbSemaines;
	private String nbGroupes;
	private String type;
	private String heures;
	private String heuresReeles;
	private String commentaire;
	private Button supprimer;
	
	public InterventionIHM(String prenom, String nom, String nbSemaines, String nbGroupes, String type, String heures,
			String heuresReeles,String commentaire, Button supprimer) {
		this.prenom = prenom;
		this.nom = nom;
		this.nbSemaines = nbSemaines;
		this.nbGroupes = nbGroupes;
		this.type = type;
		this.heures = heures;
		this.heuresReeles = heuresReeles;
		this.commentaire = commentaire;		
		this.supprimer = supprimer;
	}

	

	public InterventionIHM(String prenom, String nom, String type, String heures, String heuresReeles,
			String commentaire, Button supprimer) {
		this.prenom = prenom;
		this.nom = nom;
		this.type = type;
		this.heures = heures;
		this.heuresReeles = heuresReeles;
		this.commentaire = commentaire;
		this.supprimer = supprimer;
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

	public String getNbSemaines() {
		return nbSemaines;
	}

	public void setNbSemaines(String nbSemaines) {
		this.nbSemaines = nbSemaines;
	}

	public String getNbGroupes() {
		return nbGroupes;
	}

	public void setNbGroupes(String nbGroupes) {
		this.nbGroupes = nbGroupes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHeures() {
		return heures;
	}

	public void setHeures(String heures) {
		this.heures = heures;
	}

	public String getHeuresReeles() {
		return heuresReeles;
	}

	public void setHeuresReeles(String heuresReeles) {
		this.heuresReeles = heuresReeles;
	}

	public Button getSupprimer() {
		return supprimer;
	}

	public void setSupprimer(Button supprimer) {
		this.supprimer = supprimer;
	}

	@Override
	public String toString() {
		return "InterventionIHM [prenom=" + prenom + ", nom=" + nom + ", nbSemaines=" + nbSemaines + ", nbGroupes="
				+ nbGroupes + ", type=" + type + ", heures=" + heures + ", heuresReeles=" + heuresReeles
				+ ", supprimer=" + supprimer + "]";
	}

	
	
}