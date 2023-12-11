package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controleur.Controleur;

public class Modele {

	private Controleur ctrl;
	private List<Categorie> lstCategories;
	private List<Intervenant> lstIntervenants;
	private List<Intervention> lstInterventions;
	private List<Module> lstModules;
	private List<Semestre> lstSemestres;
	private Map<String,TypeCours> hmTypeCours;

	public Modele(Controleur ctrl) {

		this.ctrl = ctrl;

		this.lstCategories    = new ArrayList<>();
		this.lstIntervenants  = new ArrayList<>();
		this.lstInterventions = new ArrayList<>();
		this.lstModules       = new ArrayList<>();
		this.lstSemestres 	  = new ArrayList<>();
		this.hmTypeCours      = this.initTypeCours();
		
	}

	public void ajouterCategorie(int id, String nom, double hMin, double hMax){
		this.lstCategories.add(new Categorie(id, nom, hMin, hMax));
	}

	public void ajouterIntervenant(int id,String prenom, String nom, String email, double hMin, double hMax, int idCategorie) {
		this.lstIntervenants.add(new Intervenant(id,prenom,nom,email,idCategorie));
	}
	
	public void ajouterIntervention(int idIntervenant, int idModule, int idTypeCours, int nbSemaines, int nbGroupe) {
		this.lstInterventions.add(new Intervention(idIntervenant, idModule, idTypeCours, nbSemaines, nbGroupe));
	}

	public void ajouterModule(int id, String nom, int nbSemaines, double hTD, double hTP, double hCM, int idSemestre) {
		this.lstModules.add(new Module(id, nom, nbSemaines, hTD, hTP, hCM, idSemestre));
	}

	public void ajouterSemestre(int id, int nbGTD, int nbGTP, int nbGCM, int nbGAutre) {
		this.lstSemestres.add(new Semestre(id, nbGTD, nbGTD, nbGCM, nbGAutre));
	}

	public void changerCoeffTypeCours(String nom, int coeff) {
		this.hmTypeCours.get(nom).setCoefficient(coeff);
	}
	
	private HashMap<String,TypeCours> initTypeCours(){
		HashMap<String,TypeCours> hmTypeCours      = new HashMap<>();
		hmTypeCours.put("TP",         new TypeCours(2,"TP", 0.75));
		hmTypeCours.put("TD",         new TypeCours(1,"TD", 1));
		hmTypeCours.put("CM",         new TypeCours(3,"CM", 1.5));
		hmTypeCours.put("SAE",        new TypeCours(4,"SAE", 1));
		hmTypeCours.put("REH",        new TypeCours(5,"REH", 1));
		hmTypeCours.put("Evaluation", new TypeCours(6,"Evaluation", 1));
		hmTypeCours.put("Autre",      new TypeCours(7,"Autre", 1));
		return hmTypeCours;
	}
	
}
