package modele;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controleur.Controleur;

public class Modele {

	private Controleur ctrl;
	private DB db;

	private Map<Integer,Categorie> hmCategories;
	private Map<Integer, Intervenant> hmIntervenants;
	private Map<Integer, Intervention> hmInterventions;
	private Map<Integer, Module> hmModules;
	private Map<Integer,Semestre> hmSemestres;
	private Map<Integer,TypeCours> hmTypeCours;
	private Map<String,HeureCours> hmHeuresCours;
	private int idAnnee;

	public Modele(Controleur ctrl) {

		this.ctrl = ctrl;
		this.db = DB.getInstance();

		try {
			this.hmCategories    = this.db.getCategories(idAnnee);
			this.hmIntervenants  = this.db.getIntervenants(idAnnee);
			this.hmInterventions = this.db.getInterventions(idAnnee);
			this.hmModules       = this.db.getModules(idAnnee);
			this.hmSemestres 	 = this.db.getSemestres(idAnnee);
			this.hmTypeCours     = this.db.getTypeCours();
			this.hmHeuresCours   = this.db.getHeureCours();
		} catch (SQLException e) { e.printStackTrace();	}
		
	}

	public void ajouterCategorie(int id, String nom, double hMin, double hMax) throws SQLException {
		Categorie c = new Categorie(id, nom, hMin, hMax, idAnnee);
		this.db.ajouterCategorie(c);
		this.hmCategories.put(id, c);
	}
	
	public void updateCategorie(int id, String nom, double hMin, double hMax) throws SQLException {
		this.db.updateCategorie(id, nom, hMin, hMax,this.idAnnee);
		this.hmCategories.get(id).setNom(nom);
		this.hmCategories.get(id).sethMin(hMin);
		this.hmCategories.get(id).sethMax(hMax);
	}

	public void ajouterIntervenant(int id,String prenom, String nom, String email, double hMin, double hMax, int idCategorie) throws SQLException {
		Intervenant i = new Intervenant(id,prenom,nom,email,hMin, hMax, idAnnee, idCategorie);
		this.db.ajouterIntervenant(i);
		this.hmIntervenants.put(id,i);
	}
	public void updateIntervenant(int id,String prenom, String nom, String email, double hMin, double hMax, int idCategorie) throws SQLException {
		this.db.updateIntervenant(id,prenom,nom,email,hMin, hMax, idAnnee, idCategorie);
		this.hmIntervenants.get(id).setPrenom(prenom);
		this.hmIntervenants.get(id).setNom(nom);
		this.hmIntervenants.get(id).setEmail(email);
		this.hmIntervenants.get(id).sethMin(hMin);
		this.hmIntervenants.get(id).sethMax(hMax);
		this.hmIntervenants.get(id).setIdCategorie(idCategorie);
	}
	
	public void ajouterIntervention(int idIntervenant, int idModule, int idTypeCours, int nbSemaines, int nbGroupe) throws SQLException {
		Intervention i = new Intervention(idIntervenant, idModule, idTypeCours, nbSemaines, nbGroupe, idAnnee);
		this.db.ajouterIntervention(i);
		this.hmInterventions.put(idIntervenant, i);
	}
	public void updateIntervention(int idIntervenant, int idModule, int idTypeCours, int nbSemaines, int nbGroupe) throws SQLException {
		this.db.updateIntervention(idIntervenant, idModule, idTypeCours, nbSemaines, nbGroupe, idAnnee);
		this.hmInterventions.get(idIntervenant).setIdModule(idModule);
		this.hmInterventions.get(idIntervenant).setIdTypeCours(idTypeCours);
		this.hmInterventions.get(idIntervenant).setNbSemaines(nbSemaines);
		this.hmInterventions.get(idIntervenant).setNbGroupe(nbGroupe);
	}

	public void ajouterModule(int id, String nom, int nbSemaines,int idAnnee, int idSemestre)throws SQLException {
		Module m = new Module(id, nom, nbSemaines,idAnnee, idSemestre);
		this.db.ajouterModule(m);
		this.hmModules.put(id, m);
	}
	public void updateModule(int id, String nom, int nbSemaines,int idAnnee, int idSemestre)throws SQLException {
		this.db.updateModule(id, nom, nbSemaines,idAnnee, idSemestre);
		this.hmModules.get(id).setNom(nom);
		this.hmModules.get(id).setNbSemaines(nbSemaines);
		this.hmModules.get(id).setIdAnnee(idAnnee);
		this.hmModules.get(id).setIdSemestre(idSemestre);
	}

	public void ajouterSemestre(int id, int nbGTD, int nbGTP, int nbGCM, int nbGAutre) throws SQLException {
		Semestre s = new Semestre(id, nbGTD, nbGTD, nbGCM, nbGAutre, idAnnee);
		this.db.ajouterSemestre(s);
		this.hmSemestres.put(id, s);
	}
	public void updateSemestre(int id, int nbGTD, int nbGTP, int nbGCM, int nbGAutre) throws SQLException {
		this.db.updateSemestre(id, nbGTD, nbGTD, nbGCM, nbGAutre, idAnnee);
		this.hmSemestres.get(id).setNbGTD(nbGTD);
		this.hmSemestres.get(id).setNbGTP(nbGTP);
		this.hmSemestres.get(id).setNbGCM(nbGCM);
		this.hmSemestres.get(id).setNbGAutre(nbGAutre);
	}

	public void ajouterHeureCours(int idTypeCours, int idModule, double heure) throws SQLException {
		HeureCours hc = new HeureCours(idTypeCours, idModule, heure);
		this.db.ajouterHeureCours(hc);
		this.hmHeuresCours.put(idTypeCours+"-"+idModule,hc);
	}


	public void updateTypeCours(int id, String nom, int coeff) throws SQLException {
		this.db.updateTypeCours(id, nom, coeff);
		this.hmTypeCours.get(id).setCoefficient(coeff);
		this.hmTypeCours.get(id).setNom(nom);
	}

	
	
}
