package modele;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import controleur.Controleur;

public class Modele {

	private final static int DEBUT_ANNEE = 2022;

	private DB db;

	private Controleur ctrl;

	private Map<Integer, Categorie> hmCategories;
	private Map<Integer, Intervenant> hmIntervenants;
	private Map<String, Intervention> hmInterventions;
	private Map<Integer, Module> hmModules;
	private Map<Integer, Semestre> hmSemestres;
	private Map<Integer, TypeCours> hmTypeCours;
	private Map<Integer, TypeModule> hmTypeModule;;
	private Map<String, HeureCours> hmHeuresCours;
	private Map<Integer, String> hmAnnee;
	private int idAnnee;

	private boolean duplication;

	public Modele(Controleur ctrl) {

		this.ctrl = ctrl;
		this.duplication = false;

		this.db = DB.getInstance();

		try {
			this.hmAnnee = this.db.getAnnee();
			this.idAnnee = this.db.getDerAnnee();
			this.ctrl.getVue().setAnnee(this.hmAnnee.get(this.idAnnee));
			this.hmCategories = this.db.getCategories(idAnnee);
			this.hmIntervenants = this.db.getIntervenants(idAnnee);
			this.hmInterventions = this.db.getInterventions(idAnnee);
			this.hmModules = this.db.getModules(idAnnee);
			this.hmSemestres = this.db.getSemestres(idAnnee);
			this.hmTypeCours = this.db.getTypeCours();
			this.hmTypeModule = this.db.getTypeModule();
			this.hmHeuresCours = this.db.getHeureCours(idAnnee);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		/*
		 * this.ajouterCategorie(23, "cozuahjazbfi", 5, 10);
		 * this.ajouterIntervenant(12, "Arthur", "Lecomte",
		 * "arthur.lebg@univ-lehavre.fr", 0, 0, 1);
		 * this.ajouterHeureCours(1, 11, 1);
		 * this.ajouterIntervention(1, 2, 3, 4, 5);
		 * this.ajouterModule(45, "Module", 5, idAnnee, 1);
		 * this.ajouterSemestre(2012, 2, 3, 4, 5);
		 * 
		 * this.updateCategorie(new Categorie(23, "newNom", 5, 10, idAnnee));
		 * this.updateIntervenant(new Intervenant(12, "Arthurrr", "Lec", "zadazd", 0, 1,
		 * 1, idAnnee));
		 * this.updateHeureCours(new HeureCours(1, 11, 10, idAnnee));
		 * this.updateIntervention(new Intervention(1, 2, 3, 5, 6, idAnnee));
		 * this.updateModule(new Module(45, "newModule", 10, idAnnee, 1));
		 * this.updateSemestre(new Semestre(2012, 4, 5, 6, 8, idAnnee));
		 * this.updateTypeCours(new TypeCours(2, "ozeuhizne", 2.5));
		 */

	}

	public Integer getIdTypeCoursByNom(String nom) {
		try {
			return this.db.getIdTypeCoursByNom(nom);
		} catch (SQLException e) { e.printStackTrace(); }
		return null;
	}

	public List<HeureCours> getHeureCoursByModule(int idModule, int idAnnee) {
		try {
			return this.db.getHeureCoursByModule(idModule, idAnnee);
		} catch (SQLException e) { e.printStackTrace();}
		return null;
	}

	public List<Module> getModuleBySemestre(int idSemestre, int idAnnee) {
		try {
			return this.db.getModuleBySemestre(idSemestre, idAnnee);
		} catch (SQLException e) { e.printStackTrace();}
		return null;
	}

	public void ajouterCategorie(String nom, double hMin, double hMax, double ratioTp) {
		Categorie c = new Categorie(nom, hMin, hMax, ratioTp, this.idAnnee);
		try {
			this.db.ajouterCategorie(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.hmCategories.put(c.getId(), c);
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

	public void supprimerCategorie(int id) {
			try {
				this.db.supprimerCategorie(this.hmCategories.get(id));
				this.hmCategories.remove(id);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	public void ajouterIntervenant(String prenom, String nom, String email, double hMin, double hMax,
			int idCategorie) {
		ajouterIntervenant(new Intervenant(prenom, nom, email, hMin, hMax, idAnnee, idCategorie));
	}

	public void ajouterIntervenant(Intervenant i) {
		try {
			this.db.ajouterIntervenant(i);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.hmIntervenants.put(i.getId(), i);
	}

	public void updateIntervenant(Intervenant i) {
		try {
			this.db.updateIntervenant(i);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void supprimerIntervenant(int id) {
		try {
			this.db.supprimerIntervenant(id);
			this.hmIntervenants.remove(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
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

	public void ajouterModule(String nom, String code, int idTypeModule, int idSemestre) {
		Module m = new Module(nom, code, idTypeModule, this.idAnnee, idSemestre);
		try {
			this.db.ajouterModule(m);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.hmModules.put(m.getId(), m);
	}

	public void updateModule(Module m) {
		try {
			this.db.updateModule(m);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.hmModules.get(m.getId()).setNom(m.getNom());
		this.hmModules.get(m.getId()).setCode(m.getCode());
		this.hmModules.get(m.getId()).setIdAnnee(m.getIdAnnee());
		this.hmModules.get(m.getId()).setIdSemestre(m.getIdSemestre());
	}

	public void supprimerModule(int id) {
		try {
			List<HeureCours> lstHeuresCours = this.ctrl.getModele().getHeureCoursByModule(id,this.idAnnee);
			for (HeureCours hc : lstHeuresCours) {
				this.db.supprimerHeureCours(hc);
				this.hmHeuresCours.remove(hc.getIdTypeCours()+"-"+hc.getIdModule());
			}
			this.db.supprimerModule(id);
			this.hmModules.remove(id);
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void ajouterSemestre(int nbGTD, int nbGTP, int nbGCM, int nbGAutre) {
		Semestre s = new Semestre(nbGTD, nbGTD, nbGCM, nbGAutre, idAnnee);
		try {
			this.db.ajouterSemestre(s);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.hmSemestres.put(s.getId(), s);
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
		semestre.setNbSemaine(s.getNbSemaine());
	}

	public void ajouterHeureCours(int idTypeCours, int idModule, double heure, int nbSemaine, double hParSemaine) {
		HeureCours hc = new HeureCours(idTypeCours, idModule, heure,nbSemaine,hParSemaine, this.idAnnee);
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		TypeCours typeCours = this.hmTypeCours.get(tc.getId());
		typeCours.setCoefficient(tc.getCoefficient());
		typeCours.setNom(tc.getNom());
	}

	public void updateTypeCoursBrut(String nomTypeCours, Double newCoeff) {
		for (TypeCours tc : hmTypeCours.values()) {
			if (nomTypeCours.equals(tc.getNom())) {
				tc.setCoefficient(newCoeff);
				this.updateTypeCours(tc);
			}
		}
	}



	public void ajouterAnnee() {
		try {
			int iAnnee = this.db.getDerAnnee() + 1;
			String annee = (DEBUT_ANNEE + iAnnee) + "-" + (DEBUT_ANNEE + iAnnee + 1);
			this.db.ajouterAnnee(iAnnee, annee);
			this.hmAnnee.put(iAnnee, annee);
			
			if (this.duplication) {
				Map<Integer, Categorie>   hmTmpCategories = this.db.getCategories(this.idAnnee);
				Map<Integer, Intervenant> hmTmpIntervenants = this.db.getIntervenants(this.idAnnee);
				Map<String, Intervention> hmTmpInterventions = this.db.getInterventions(this.idAnnee);
				Map<Integer, Module>      hmTmpModules = this.db.getModules(this.idAnnee);
				Map<Integer, Semestre>    hmTmpSemestres = this.db.getSemestres(this.idAnnee);
				Map<String, HeureCours>   hmTmpHeuresCours = this.db.getHeureCours(this.idAnnee);
				this.idAnnee = iAnnee;
				for (Categorie c : hmTmpCategories.values()) ajouterCategorie(c.getNom(), c.gethMin(), c.gethMax(), c.getRatioTp());
				for (Intervenant i : hmTmpIntervenants.values()) ajouterIntervenant(i.getPrenom(), i.getNom(), i.getEmail(), i.gethMin(), i.gethMax(), i.getIdCategorie());
				for (Intervention i : hmTmpInterventions.values()) ajouterIntervention(i.getIdIntervenant(), i.getIdModule(), i.getIdTypeCours(), i.getNbSemaines(), i.getNbGroupe());
				for (Module m : hmTmpModules.values()) ajouterModule(m.getNom(), m.getCode(), m.getIdTypeModule(), m.getIdSemestre());	
				for (Semestre s : hmTmpSemestres.values()) ajouterSemestre(s.getNbGTD(), s.getNbGTP(), s.getNbGCM(), s.getNbSemaine());
				for (HeureCours hc : hmTmpHeuresCours.values()) ajouterHeureCours(hc.getIdTypeCours(), hc.getIdModule(), hc.getHeure(), hc.getNbSemaine(), hc.gethParSemaine());	
			} else { this.updateAnnee(annee); }

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateAnnee(String date) {
		for (Integer i : this.hmAnnee.keySet()) {
			if (this.hmAnnee.get(i).equals(date))
				this.idAnnee = i;
		}
		try {
			this.hmCategories = this.db.getCategories(idAnnee);
			this.hmIntervenants = this.db.getIntervenants(idAnnee);
			this.hmInterventions = this.db.getInterventions(idAnnee);
			this.hmModules = this.db.getModules(idAnnee);
			this.hmSemestres = this.db.getSemestres(idAnnee);
			this.hmHeuresCours = this.db.getHeureCours(idAnnee);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
	
	}

	public boolean isDuplication() {
		return duplication;
	}

	public void setDuplication(boolean duplication) {
		this.duplication = duplication;
	}

	public Map<Integer, Categorie> getHmCategories() {
		return hmCategories;
	}

	public void setHmCategories(Map<Integer, Categorie> hmCategories) {
		this.hmCategories = hmCategories;
	}

	public Map<Integer, Intervenant> getHmIntervenants() {
		return hmIntervenants;
	}

	public void setHmIntervenants(Map<Integer, Intervenant> hmIntervenants) {
		this.hmIntervenants = hmIntervenants;
	}

	public Map<String, Intervention> getHmInterventions() {
		return hmInterventions;
	}

	public void setHmInterventions(Map<String, Intervention> hmInterventions) {
		this.hmInterventions = hmInterventions;
	}

	public Map<Integer, Module> getHmModules() {
		return hmModules;
	}

	public void setHmModules(Map<Integer, Module> hmModules) {
		this.hmModules = hmModules;
	}

	public Map<Integer, Semestre> getHmSemestres() {
		return hmSemestres;
	}

	public void setHmSemestres(Map<Integer, Semestre> hmSemestres) {
		this.hmSemestres = hmSemestres;
	}

	public Map<Integer, TypeCours> getHmTypeCours() {
		return hmTypeCours;
	}

	public void setHmTypeCours(Map<Integer, TypeCours> hmTypeCours) {
		this.hmTypeCours = hmTypeCours;
	}

	public Map<String, HeureCours> getHmHeuresCours() {
		return hmHeuresCours;
	}

	public void setHmHeuresCours(Map<String, HeureCours> hmHeuresCours) {
		this.hmHeuresCours = hmHeuresCours;
	}

	public int getIdAnnee() {
		return idAnnee;
	}

	public void setIdAnnee(int idAnnee) {
		this.idAnnee = idAnnee;
	}

	public String getNomCateg(int idCategorie) {
		try {
			return this.db.getNomCateg(idCategorie);
		} catch (SQLException e) {
			return "";
		}
	}

	public Map<Integer, String> getHmAnnee() {
		return hmAnnee;
	}

	public void setHmAnnee(Map<Integer, String> hmAnnee) {
		this.hmAnnee = hmAnnee;
	}

	public Map<Integer, TypeModule> getHmTypeModule() {
		return hmTypeModule;
	}

	public void setHmTypeModule(Map<Integer, TypeModule> hmTypeModule) {
		this.hmTypeModule = hmTypeModule;
	}

	

}
