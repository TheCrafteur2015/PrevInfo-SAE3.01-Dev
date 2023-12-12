package modele;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controleur.Controleur;

public class Modele {

	private final static int DEBUT_ANNEE = 2022;

	private Controleur ctrl;
	private DB db;

	private Map<Integer, Categorie> hmCategories;
	private Map<Integer, Intervenant> hmIntervenants;
	private Map<String, Intervention> hmInterventions;
	private Map<Integer, Module> hmModules;
	private Map<Integer, Semestre> hmSemestres;
	private Map<Integer, TypeCours> hmTypeCours;
	private Map<String, HeureCours> hmHeuresCours;
	private int idAnnee;

	public Modele(Controleur ctrl) {

		this.ctrl = ctrl;
		this.db = DB.getInstance();

		try {
			this.idAnnee = this.db.getDerAnnee();
			this.hmCategories = this.db.getCategories(idAnnee);
			this.hmIntervenants = this.db.getIntervenants(idAnnee);
			this.hmInterventions = this.db.getInterventions(idAnnee);
			this.hmModules = this.db.getModules(idAnnee);
			this.hmSemestres = this.db.getSemestres(idAnnee);
			this.hmTypeCours = this.db.getTypeCours();
			this.hmHeuresCours = this.db.getHeureCours(idAnnee);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.ajouterCategorie(23, "cozuahjazbfi", 5, 10);
		this.ajouterIntervenant(12, "Arthur", "Lecomte", "arthur.lebg@univ-lehavre.fr", 0, 0, 1);
		this.ajouterHeureCours(1, 11, 1);
		this.ajouterIntervention(1, 2, 3, 4, 5);
		this.ajouterModule(45, "Module", 5, idAnnee, 1);
		this.ajouterSemestre(2012, 2, 3, 4, 5);

		this.updateCategorie(new Categorie(23, "newNom", 5, 10, idAnnee));
		this.updateIntervenant(new Intervenant(12, "Arthurrr", "Lec", "zadazd", 0, 1, 1, idAnnee));
		this.updateHeureCours(new HeureCours(1, 11, 10, idAnnee));
		this.updateIntervention(new Intervention(1, 2, 3, 5, 6, idAnnee));
		this.updateModule(new Module(45, "newModule", 10, idAnnee, 1));
		this.updateSemestre(new Semestre(2012, 4, 5, 6, 8, idAnnee));
		this.updateTypeCours(new TypeCours(2, "ozeuhizne", 2.5));

	}

	public void ajouterCategorie(int id, String nom, double hMin, double hMax) {
		Categorie c = new Categorie(id, nom, hMin, hMax, idAnnee);
		try {
			this.db.ajouterCategorie(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.hmCategories.put(id, c);
	}

	public void updateCategorie(Categorie c) {
		try {
			this.db.updateCategorie(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Categorie categorie = this.hmCategories.get(c.getId());
		categorie.setNom(c.getNom());
		categorie.sethMax(c.gethMax());
		categorie.sethMin(c.gethMin());
	}

	public void ajouterIntervenant(int id, String prenom, String nom, String email, double hMin, double hMax,
			int idCategorie) {
		Intervenant i = new Intervenant(id, prenom, nom, email, hMin, hMax, idAnnee, idCategorie);
		try {
			this.db.ajouterIntervenant(i);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.hmIntervenants.put(id, i);
	}

	public void updateIntervenant(Intervenant i) {
		try {
			this.db.updateIntervenant(i);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Intervenant intervenant = this.hmIntervenants.get(i.getId());
		intervenant.setPrenom(i.getPrenom());
		intervenant.setNom(i.getNom());
		intervenant.setEmail(i.getEmail());
		intervenant.sethMin(i.gethMin());
		intervenant.sethMax(i.gethMax());
		intervenant.setIdCategorie(i.getIdCategorie());

	}

	public void ajouterIntervention(int idIntervenant, int idModule, int idTypeCours, int nbSemaines, int nbGroupe) {
		Intervention i = new Intervention(idIntervenant, idModule, idTypeCours, nbSemaines, nbGroupe, idAnnee);
		try {
			this.db.ajouterIntervention(i);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.hmInterventions.put(idIntervenant + "-" + idModule + "-" + idTypeCours, i);
	}

	public void updateIntervention(Intervention i) {
		try {
			this.db.updateIntervention(i);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Intervention intervention = this.hmInterventions
				.get(i.getIdIntervenant() + "-" + i.getIdModule() + "-" + i.getIdTypeCours());
		intervention.setNbSemaines(i.getNbSemaines());
		intervention.setNbGroupe(i.getNbGroupe());
	}

	public void ajouterModule(int id, String nom, int nbSemaines, int idAnnee, int idSemestre) {
		Module m = new Module(id, nom, nbSemaines, idAnnee, idSemestre);
		try {
			this.db.ajouterModule(m);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.hmModules.put(id, m);
	}

	public void updateModule(Module m) {
		try {
			this.db.updateModule(m);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.hmModules.get(m.getId()).setNom(m.getNom());
		this.hmModules.get(m.getId()).setNbSemaines(m.getNbSemaines());
		this.hmModules.get(m.getId()).setIdAnnee(m.getIdAnnee());
		this.hmModules.get(m.getId()).setIdSemestre(m.getIdSemestre());
	}

	public void ajouterSemestre(int id, int nbGTD, int nbGTP, int nbGCM, int nbGAutre) {
		Semestre s = new Semestre(id, nbGTD, nbGTD, nbGCM, nbGAutre, idAnnee);
		try {
			this.db.ajouterSemestre(s);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.hmSemestres.put(id, s);
	}

	public void updateSemestre(Semestre s) {
		try {
			this.db.updateSemestre(s);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Semestre semestre = this.hmSemestres.get(s.getId());
		semestre.setNbGTD(s.getNbGTD());
		semestre.setNbGTP(s.getNbGTP());
		semestre.setNbGCM(s.getNbGCM());
		semestre.setNbGAutre(s.getNbGAutre());
	}

	public void ajouterHeureCours(int idTypeCours, int idModule, double heure) {
		HeureCours hc = new HeureCours(idTypeCours, idModule, heure, idAnnee);
		try {
			this.db.ajouterHeureCours(hc);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.hmHeuresCours.put(idTypeCours + "-" + idModule, hc);
	}

	public void updateHeureCours(HeureCours heureCours) {
		try {
			this.db.updateHeureCours(heureCours);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		HeureCours hCours = this.hmHeuresCours.get(heureCours.getIdTypeCours() + "-" + heureCours.getIdModule());
		hCours.setHeure(heureCours.getHeure());

	}

	public void updateTypeCours(TypeCours tc) {
		try {
			this.db.updateTypeCours(tc);
		} catch (SQLException e) {e.printStackTrace();}
		TypeCours typeCours = this.hmTypeCours.get(tc.getId());
		typeCours.setCoefficient(tc.getCoefficient());
		typeCours.setNom(tc.getNom());
	}

	public void ajouterAnnee() {
		int iAnnee = 0;
		try {
			iAnnee = db.getDerAnnee();
			this.db.ajouterAnnee((DEBUT_ANNEE + iAnnee) + "-" + (DEBUT_ANNEE + iAnnee + 1));
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
