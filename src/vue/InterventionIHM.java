



package vue;

import javafx.scene.control.Button;

public class InterventionIHM{
	private String prenom;
	private String nom;
	private String semaines;
	private String groupes;
	private String type;
	private String heures;
	private String reelles;
	private String commentaire;
	private Button supprimer;
	
	public InterventionIHM(String prenom, String nom, String semaines, String groupes, String type, String heures,
			String reelles,String commentaire, Button supprimer) {
		this.prenom = prenom;
		this.nom = nom;
		this.semaines = semaines;
		this.groupes = groupes;
		this.type = type;
		this.heures = heures;
		this.reelles = reelles;
		this.commentaire = commentaire;
		this.supprimer = supprimer;
	}

	

	public InterventionIHM(String prenom, String nom, String type, String heures, String reelles,
			String commentaire, Button supprimer) {
		this.prenom = prenom;
		this.nom = nom;
		this.type = type;
		this.heures = heures;
		this.reelles = reelles;
		this.commentaire = commentaire;
		this.supprimer = supprimer;
	}



	public String getPrenom() {
		return prenom;
	}



	public String getNom() {
		return nom;
	}



	public String getSemaines() {
		return semaines;
	}



	public String getGroupes() {
		return groupes;
	}



	public String getType() {
		return type;
	}



	public String getHeures() {
		return heures;
	}



	public String getReelles() {
		return reelles;
	}



	public String getCommentaire() {
		return commentaire;
	}



	public Button getSupprimer() {
		return supprimer;
	}


	
	
	
}