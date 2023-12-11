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

	private List<Categorie> lstCategories;
	private List<Intervenant> lstIntervenants;
	private List<Intervention> lstInterventions;
	private List<Module> lstModules;
	private List<Semestre> lstSemestres;
	private Map<String,TypeCours> hmTypeCours;
	private List<HeureCours> lstHeuresCours;
	private int idAnnee;

	public Modele(Controleur ctrl) {

		this.ctrl = ctrl;
		this.db = DB.getInstance();

		try {
			this.lstCategories    = this.db.getCategories(idAnnee);
			this.lstIntervenants  = this.db.getIntervenants(idAnnee);
			this.lstInterventions = this.db.getInterventions(idAnnee);
			this.lstModules       = this.db.getModules(idAnnee);
			this.lstSemestres 	  = this.db.getSemestres(idAnnee);
			this.hmTypeCours      = this.db.getTypeCours();
		} catch (SQLException e) { e.printStackTrace();	}
		
	}

	public void ajouterCategorie(int id, String nom, double hMin, double hMax) throws SQLException {
		Categorie c = new Categorie(id, nom, hMin, hMax, idAnnee);
		this.db.ajouterCategorie(c);
		this.lstCategories.add(c);
	}

	public void ajouterIntervenant(int id,String prenom, String nom, String email, double hMin, double hMax, int idCategorie) throws SQLException {
		Intervenant i = new Intervenant(id,prenom,nom,email,hMin, hMax, idAnnee, idCategorie);
		this.db.ajouterIntervenant(i);
		this.lstIntervenants.add(i);
	}
	
	public void ajouterIntervention(int idIntervenant, int idModule, int idTypeCours, int nbSemaines, int nbGroupe) throws SQLException {
		Intervention i = new Intervention(idIntervenant, idModule, idTypeCours, nbSemaines, nbGroupe, idAnnee);
		this.db.ajouterIntervention(i);
		this.lstInterventions.add(i);
	}

	public void ajouterModule(int id, String nom, int nbSemaines,int idAnnee, int idSemestre)throws SQLException {
		Module m = new Module(id, nom, nbSemaines,idAnnee, idSemestre);
		this.db.ajouterModule(m);
		this.lstModules.add(m);
	}
	

	public void ajouterSemestre(int id, int nbGTD, int nbGTP, int nbGCM, int nbGAutre) throws SQLException {
		Semestre s = new Semestre(id, nbGTD, nbGTD, nbGCM, nbGAutre, idAnnee);
		this.db.ajouterSemestre(s);
		this.lstSemestres.add(s);
	}

	public void ajouterHeureCours(int idTypeCours, int idModule, double heure) throws SQLException {
		HeureCours hc = new HeureCours(idTypeCours, idModule, heure);
		this.db.ajouterHeureCours(hc);
		this.lstHeuresCours.add(hc);
	}

	public void changerCoeffTypeCours(String nom, int coeff) throws SQLException {
		this.db.changerCoeffTypeCours(nom, coeff);
		this.hmTypeCours.get(nom).setCoefficient(coeff);
	}
	
	
}
