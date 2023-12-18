package modele;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controleur.Controleur;

public class Modele {

	private final static int DEBUT_ANNEE = 2022;
	public final static String REGEX_INT = "[0-9]*";

	private DB db;

	private Controleur ctrl;

	private Map<Integer, Categorie> hmCategories;
	private Map<Integer, Intervenant> hmIntervenants;
	private Map<Integer, Intervention> hmInterventions;
	private Map<Integer, Module> hmModules;
	private Map<Integer, Semestre> hmSemestres;
	private Map<Integer, TypeCours> hmTypeCours;
	private Map<Integer, TypeModule> hmTypeModule;;
	private Map<String, HeureCours> hmHeuresCours;
	private Map<Integer, String> hmAnnee;
	private int idAnnee;

	

	public boolean bEnDuplication;
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
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible de charger les données","erreur");
		}

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
			this.hmCategories.put(c.getId(), c);
			if (!bEnDuplication)this.ctrl.getVue().afficherNotification("Catégorie ajoutée", "Catégorie ajoutée avec succès","succes");
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible d'ajouter la catégorie","erreur");
		}
		
		
	}

	public void dupliquerCategorie(int oldId, String nom, double hMin, double hMax, double ratioTp) {
		List<Intervenant> lstIntervantParCateg = null;
		Categorie c = new Categorie(nom, hMin, hMax, ratioTp, this.idAnnee);
		try {
			lstIntervantParCateg = this.db.getIntervenantParCateg(idAnnee-1, oldId);
			this.db.ajouterCategorie(c);
			this.hmCategories.put(c.getId(), c);
			if (!bEnDuplication)this.ctrl.getVue().afficherNotification("Catégorie ajoutée", "Catégorie ajoutée avec succès","succes");
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible d'ajouter la catégorie","erreur");
		}
		
		for (Intervenant i : lstIntervantParCateg) {dupliquerIntervenant(i.getId(), i.getPrenom(), i.getNom(), i.getEmail(), i.gethMin(), i.gethMax(), c.getId());}
		
		
	}

		


	public void updateCategorie(Categorie c) {
		try {
			this.db.updateCategorie(c);
			this.hmCategories.put(c.getId(), c);
			this.ctrl.getVue().afficherNotification("Catégorie modifiée", "Catégorie modifiée avec succès","succes");
		
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible de modifier la catégorie","erreur");
		}
		
	}

	public void supprimerCategorie(int id) {
			try {
				this.db.supprimerCategorie(this.hmCategories.get(id));
				this.hmCategories.remove(id);
				if (!bEnDuplication)this.ctrl.getVue().afficherNotification("Catégorie supprimée", "Catégorie supprimée avec succès","succes");
			} catch (SQLException e) {
				String str ="Impossible de supprimer la catégorie : \n";
				Categorie c = this.hmCategories.get(id);
				str += c.getNom();
				str += " car elle est reférencée \ndans un ou plusieurs intervenant(s)";	
				this.ctrl.getVue().afficherNotification("Erreur", str ,"erreur");
			}
		}

	public void ajouterIntervenant(String prenom, String nom, String email, double hMin, double hMax,
			int idCategorie) {
		ajouterIntervenant(new Intervenant(prenom, nom, email, hMin, hMax, idAnnee, idCategorie));
	}

	public void ajouterIntervenant(Intervenant i) {
		try {
			this.db.ajouterIntervenant(i);
			this.hmIntervenants.put(i.getId(), i);
			if (!bEnDuplication)this.ctrl.getVue().afficherNotification("Intervenant ajouté", "Intervenant ajouté avec succès","succes");
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible d'ajouter l'intervenant","erreur");
		}
		

	}

	public void dupliquerIntervenant(int oldId, String prenom, String nom, String email, double hMin, double hMax,int idCategorie) {
		Map<Integer, Intervention> hmInter = null;
		Intervenant i = new Intervenant(prenom, nom, email, hMin, hMax, idAnnee, idCategorie);
		try {
			hmInter = this.db.getInterventionsByIntervenant(oldId);
			this.db.ajouterIntervenant(i);
			this.hmIntervenants.put(i.getId(), i);
			if (!bEnDuplication)this.ctrl.getVue().afficherNotification("Intervenant ajouté", "Intervenant ajouté avec succès","succes");
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible d'ajouter l'intervenant","erreur");
		}
		for (Intervention inter : hmInter.values()) {
			inter.setIdIntervenant(i.getId());
			updateIntervention(inter);
		}

	}

	public void updateIntervenant(Intervenant i) {
		try {
			this.db.updateIntervenant(i);
			this.hmIntervenants.put(i.getId(), i);
			if (!bEnDuplication)this.ctrl.getVue().afficherNotification("Intervenant modifié", "Intervenant modifié avec succès","succes");
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible de modifier l'intervenant","erreur");
		}
		
	}

	public void supprimerIntervenant(int id) {
		try {
			this.db.supprimerIntervenant(id);
			this.hmIntervenants.remove(id);
			if (!bEnDuplication)this.ctrl.getVue().afficherNotification("Intervenant supprimé", "Intervenant supprimé avec succès","succes");
		} catch (SQLException e) {
			String str ="Impossible de supprimer l'intervenant : \n";
			Intervenant i = this.hmIntervenants.get(id);
			str += i.getPrenom() + " " + i.getNom();
			str += " car il est reférencé \ndans une ou plusieurs intervention(s)";	
			this.ctrl.getVue().afficherNotification("Erreur", str ,"erreur");
		}
	}

	public void ajouterIntervention(int idIntervenant, int idModule, int idTypeCours, int nbSemaines, String commentaire, int nbGroupe) {
		Intervention i = new Intervention(idIntervenant, idModule, idTypeCours, nbSemaines, nbGroupe, commentaire, idAnnee);
		try {
			this.db.ajouterIntervention(i);
			this.hmInterventions.put(i.getIdIntervention(), i);
			if (!bEnDuplication)this.ctrl.getVue().afficherNotification("Intervention ajoutée", "Intervention ajoutée avec succès","succes");
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible d'ajouter l'intervention","erreur");
			
		}
	
	}

	public void updateIntervention(Intervention i) {
		try {
			this.db.updateIntervention(i);
			this.hmInterventions.put(i.getIdIntervention(), i);
			if (!bEnDuplication)this.ctrl.getVue().afficherNotification("Intervention modifiée", "Intervention modifiée avec succès","succes");
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible de modifier l'intervention","erreur");

		}
		
	}

	public void ajouterModule(String nom, String code, int idTypeModule, int idSemestre) {
		Module m = new Module(nom, code, idTypeModule, this.idAnnee, idSemestre);
		try {
			this.db.ajouterModule(m);
			this.hmModules.put(m.getId(), m);
			if (!bEnDuplication)this.ctrl.getVue().afficherNotification("Module ajouté", "Module ajouté avec succès","succes");
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible d'ajouter le module","erreur");
		}
		
		
	}

	public void dupliquerModule(int id, String nom, String code, int idTypeModule, int idSemestre) {
		List<HeureCours> lstHeureCours = this.getHeureCoursByModule(id, this.idAnnee-1);
		Map<Integer, Intervention> hmInter = this.getHmInterventionsModule(id);
		Module m = new Module(nom, code, idTypeModule, this.idAnnee, idSemestre);
		try {
			this.db.ajouterModule(m);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.hmModules.put(m.getId(), m);
		for (HeureCours hc : lstHeureCours) ajouterHeureCours(hc.getIdTypeCours(), m.getId(), hc.getHeure(), hc.getNbSemaine(), hc.gethParSemaine());
		for (Intervention i : hmInter.values()) ajouterIntervention(i.getIdIntervenant(), m.getId(), i.getIdTypeCours(), i.getNbSemaines(),i.getCommentaire(), i.getNbGroupe());
			
	}

	public void updateModule(Module m) {
		try {
			this.db.updateModule(m);
			this.hmModules.put(m.getId(), m);
			if (!bEnDuplication)this.ctrl.getVue().afficherNotification("Module modifié", "Module modifié avec succès","succes");
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible de modifier le module","erreur");
		}

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
			if (!bEnDuplication)this.ctrl.getVue().afficherNotification("Module supprimé", "Module supprimé avec succès","succes");
		
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible de supprimer le module","erreur");
		}
	}

	public void ajouterSemestre(int nbGTD, int nbGTP, int nbGCM, int nbGAutre) {
		Semestre s = new Semestre(nbGTD, nbGTD, nbGCM, nbGAutre, idAnnee);
		try {
			this.db.ajouterSemestre(s);
			this.hmSemestres.put(s.getId(), s);
			if (!bEnDuplication)this.ctrl.getVue().afficherNotification("Semestre ajouté", "Semestre ajouté avec succès","succes");
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible d'ajouter le semestre","erreur");
		}
		
	}

	public void dupliquerSemestre(List<Module> lstModuleParSemestre, int nbGTD, int nbGTP, int nbGCM, int nbGAutre) {
		Semestre s = new Semestre(nbGTD, nbGTD, nbGCM, nbGAutre, idAnnee);
		try {
			this.db.ajouterSemestre(s);
			this.hmSemestres.put(s.getId(), s);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for (Module m : lstModuleParSemestre) {
			dupliquerModule(m.getId(),m.getNom(), m.getCode(), m.getIdTypeModule(), s.getId());
		}
	}


	public void updateSemestre(Semestre s) {
		try {
			this.db.updateSemestre(s);
			this.hmSemestres.put(s.getId(), s);
			if (!bEnDuplication)this.ctrl.getVue().afficherNotification("Semestre modifié", "Semestre modifié avec succès","succes");
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible de modifier le semestre","erreur");
		}

	}

	public void ajouterHeureCours(int idTypeCours, int idModule, double heure, int nbSemaine, double hParSemaine) {
		HeureCours hc = new HeureCours(idTypeCours, idModule, heure,nbSemaine,hParSemaine, this.idAnnee);
		try {
			this.db.ajouterHeureCours(hc);
			this.hmHeuresCours.put(idTypeCours + "-" + idModule, hc);
			if (!bEnDuplication)this.ctrl.getVue().afficherNotification("Heure de cours ajoutée", "Heure de cours ajoutée avec succès","succes");
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible d'ajouter l'heure de cours","erreur");
		}
		
	}

	public void updateHeureCours(HeureCours heureCours) {
		try {
			this.db.updateHeureCours(heureCours);
			this.hmHeuresCours.put(heureCours.getIdTypeCours() + "-" + heureCours.getIdModule(), heureCours);
			if (!bEnDuplication)this.ctrl.getVue().afficherNotification("Heure de cours modifiée", "Heure de cours modifiée avec succès","succes");
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible de modifier l'heure de cours","erreur");
		}
	

	}

	public void updateTypeCours(TypeCours tc) {
		try {
			this.db.updateTypeCours(tc);
			this.hmTypeCours.put(tc.getId(), tc);
			if (!bEnDuplication)this.ctrl.getVue().afficherNotification("Type de cours modifié", "Type de cours modifié avec succès","succes");
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible de modifier le type de cours","erreur");
		}
		
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
				bEnDuplication = true;
				Map<Integer, Categorie>   hmTmpCategories = this.db.getCategories(this.idAnnee);
				Map<Integer, Semestre>    hmTmpSemestres = this.db.getSemestres(this.idAnnee);
				this.idAnnee = iAnnee;
				for (Semestre s : hmTmpSemestres.values()) dupliquerSemestre(this.getModuleBySemestre(s.getId(), this.idAnnee-1), s.getNbGTD(), s.getNbGTP(), s.getNbGCM(), s.getNbSemaine());
				for (Categorie c : hmTmpCategories.values()) dupliquerCategorie(c.getId(),c.getNom(), c.gethMin(), c.gethMax(), c.getRatioTp());
			} 
			else { 
				this.updateAnnee(annee); 
				for (int i = 0; i < 6; i++) {
					this.ajouterSemestre(0, 0, 0, 0);
				}
			}
			if (!bEnDuplication) this.ctrl.getVue().afficherNotification("Année ajoutée", "Année ajoutée avec succès","succes");

		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible d'ajouter l'année","erreur");
		}
		bEnDuplication = false;
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
			if (!bEnDuplication)this.ctrl.getVue().afficherNotification("Année modifiée", "Année modifiée avec succès","succes");
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible de modifier l'année","erreur");
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

	public Map<Integer, Intervention> getHmInterventions() {
		return hmInterventions;
	}

	public Map<Integer, Intervention> getHmInterventionsModule( int idModule) {
		Map<Integer, Intervention> ret = new HashMap<Integer, Intervention>();
		for (Integer id : this.hmInterventions.keySet()) {
			if (this.hmInterventions.get(id).getIdModule()==idModule)
			{
				ret.put(id,this.hmInterventions.get(id));
			}
		}
		return ret;
	}

	public void setHmInterventions(Map<Integer, Intervention> hmInterventions) {
		this.hmInterventions = hmInterventions;
	}

	public Map<Integer, Module> getHmModules() {
		return hmModules;
	}

	public void setHmModules(Map<Integer, Module> hmModules) {
		this.hmModules = hmModules;
	}

	public Map<Integer, Semestre> getHmSemestres() {
		return this.hmSemestres;
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
