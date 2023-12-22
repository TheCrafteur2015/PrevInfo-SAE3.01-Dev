package modele;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import controleur.Controleur;
import vue.ControleurIHM;
import vue.App;

public class Modele {

	private static final int DEBUT_ANNEE = 2022;
	public static final String REGEX_INT = "\\d*";
	public static final String REGEX_DOUBLE = "\\d*\\.?\\d*"; // ((\\d+\\.?\\d*)|(\\.\\d+))
	public static final String REGEX_DOUBLE_FRACTION = "^(\\d*\\.?(\\d+(\\/(\\d*\\.?[1-9]+)?)?)?|)$"; // ^\\d?+(?:\\/[1-9]+|\\.?\\d*)$
	public static final String REGEX_EMAIL = "^[^\\d\\s()<>@,;:\"\\[\\]\\|ç%&,]{1}[^\\s]{0,63}@[^\\d\\s()<>@,;:\"\\[\\]\\|ç%&,]{1,192}\\.[a-zA-Z]{2,63}$"; // ^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$

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
			this.hmCategories = this.db.getCategories(this.idAnnee);
			this.hmIntervenants = this.db.getIntervenants(this.idAnnee);
			this.hmInterventions = this.db.getInterventions(this.idAnnee);
			this.hmModules = this.db.getModules(this.idAnnee);
			this.hmSemestres = this.db.getSemestres(this.idAnnee);
			this.hmTypeCours = this.db.getTypeCours();
			this.hmTypeModule = this.db.getTypeModule();
			this.hmHeuresCours = this.db.getHeureCours(this.idAnnee);
		} catch (SQLException e) {
			App.log(Level.SEVERE, e);
		}

		try {
			Categorie.nbCategorie = getNbObject(this.hmCategories);
			for (int i = 0; getNbObject(this.db.getCategories(this.idAnnee - i)) == -1; i++)
				Categorie.nbCategorie = getNbObject(this.db.getCategories(this.idAnnee - i));

			Intervenant.nbIntervenant = getNbObject(this.hmIntervenants);
			for (int i = 0; getNbObject(this.db.getIntervenants(this.idAnnee - i)) == -1; i++)
				Intervenant.nbIntervenant = getNbObject(this.db.getIntervenants(this.idAnnee - i));

			Intervention.nbIntervention = getNbObject(this.hmInterventions);
			for (int i = 0; getNbObject(this.db.getInterventions(this.idAnnee - i)) == -1; i++)
				Intervention.nbIntervention = getNbObject(this.db.getInterventions(this.idAnnee - i));

			Module.nbModule = getNbObject(this.hmModules);
			for (int i = 0; getNbObject(this.db.getModules(this.idAnnee - i)) == -1; i++)
				Module.nbModule = getNbObject(this.db.getModules(this.idAnnee - i));

			Semestre.nbSemestre = getNbObject(this.hmSemestres);
			for (int i = 0; getNbObject(this.db.getSemestres(this.idAnnee - i)) == -1; i++)
				Semestre.nbSemestre = getNbObject(this.db.getSemestres(this.idAnnee - i));

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public <T> int getNbObject(Map<Integer, T> hm) {
		List<Integer> al = new ArrayList<>(hm.keySet());
		al.sort(null);
		if (al.size() != 0)
			return al.get(al.size() - 1);
		return -1;
	}

	public Integer getIdTypeCoursByNom(String nom) {
		try {
			return this.db.getIdTypeCoursByNom(nom);
		} catch (SQLException e) {
			App.log(Level.SEVERE, e, "Modele.getIdTypeCoursByNom(String)");
			return null;
		}
	}

	public List<HeureCours> getHeureCoursByModule(int idModule, int idAnnee) {
		try {
			return this.db.getHeureCoursByModule(idModule, idAnnee);
		} catch (SQLException e) {
			App.log(Level.SEVERE, e, "Modele.getHeureCoursByModule(int,int)");
			return null;
		}
	}

	public List<Module> getModuleBySemestre(int idSemestre, int idAnnee) {
		try {
			return this.db.getModuleBySemestre(idSemestre, idAnnee);
		} catch (SQLException e) {
			App.log(Level.SEVERE, e, "Modele.getModuleBySemestre(int,int)");
			return null;
		}
	}

	public void ajouterCategorie(String nom, double hMin, double hMax, double ratioTp) {
		Categorie c = new Categorie(nom, hMin, hMax, ratioTp, this.idAnnee);
		try {
			this.db.ajouterCategorie(c);
			this.hmCategories.put(c.getId(), c);
			if (!this.bEnDuplication)
				this.ctrl.getVue().afficherNotification("Catégorie ajoutée", "Catégorie ajoutée avec succès",
						ControleurIHM.Notification.SUCCES);
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible d'ajouter la catégorie",
					ControleurIHM.Notification.ERREUR);
			App.log(Level.WARNING, e, "Modele.ajouterCategorie(String,double,double,double)");
		}
	}

	public void dupliquerCategorie(int oldId, String nom, double hMin, double hMax, double ratioTp) {
		Categorie ancienneCateg = this.hmCategories.get(oldId);
		List<Intervenant> lstIntervantParCateg = null;
		Categorie c = new Categorie(nom, hMin, hMax, ratioTp, this.idAnnee);
		try {
			lstIntervantParCateg = this.db.getIntervenantParCateg(ancienneCateg.getIdAnnee(), oldId);
			this.db.ajouterCategorie(c);
			this.hmCategories.put(c.getId(), c);
			if (!this.bEnDuplication)
				this.ctrl.getVue().afficherNotification("Catégorie ajoutée", "Catégorie ajoutée avec succès",
						ControleurIHM.Notification.SUCCES);
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible d'ajouter la catégorie",
					ControleurIHM.Notification.ERREUR);
			App.log(Level.WARNING, e, "Modele.dupliquerCategorie(int,String,double,double,double)");
		}
		for (Intervenant i : lstIntervantParCateg)
			this.dupliquerIntervenant(i.getId(), i.getPrenom(), i.getNom(), i.getEmail(), i.gethMin(), i.gethMax(),
					c.getId());
	}

	public void updateCategorie(Categorie c) {
		try {
			this.db.updateCategorie(c);
			this.hmCategories.put(c.getId(), c);
			this.ctrl.getVue().afficherNotification("Catégorie modifiée", "Catégorie modifiée avec succès",
					ControleurIHM.Notification.SUCCES);
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible de modifier la catégorie",
					ControleurIHM.Notification.ERREUR);
			App.log(Level.WARNING, e, "Modele.updateCategorie(Categorie)");
		}
	}

	public void supprimerCategorie(int id) {
		try {
			this.db.supprimerCategorie(this.hmCategories.get(id));
			this.hmCategories.remove(id);
			if (!this.bEnDuplication)
				this.ctrl.getVue().afficherNotification("Catégorie supprimée", "Catégorie supprimée avec succès",
						ControleurIHM.Notification.SUCCES);
		} catch (SQLException e) {
			Categorie c = this.hmCategories.get(id);
			String msg = String.format(
					"Impossible de supprimer la catégorie : \n%1$s car elle est reférencée \ndans un ou plusieurs intervenant(s)",
					c.getNom());
			this.ctrl.getVue().afficherNotification("Erreur", msg, ControleurIHM.Notification.ERREUR);
			App.log(Level.WARNING, e, "Modele.supprimerCategorie(int)");
		}
	}

	public void ajouterIntervenant(String prenom, String nom, String email, double hMin, double hMax, int idCategorie) {
		this.ajouterIntervenant(new Intervenant(prenom, nom, email, hMin, hMax, idAnnee, idCategorie));
	}

	public void ajouterIntervenant(Intervenant i) {
		try {
			this.db.ajouterIntervenant(i);
			this.hmIntervenants.put(i.getId(), i);
			if (!this.bEnDuplication)
				this.ctrl.getVue().afficherNotification("Intervenant ajouté", "Intervenant ajouté avec succès",
						ControleurIHM.Notification.SUCCES);
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible d'ajouter l'intervenant",
					ControleurIHM.Notification.ERREUR);
			App.log(Level.WARNING, e, "Modele.ajouterIntervenant(Intervenant)");
		}
	}

	public void dupliquerIntervenant(int oldId, String prenom, String nom, String email, double hMin, double hMax,
			int idCategorie) {
		Map<Integer, Intervention> hmInter = null;
		Intervenant i = new Intervenant(prenom, nom, email, hMin, hMax, idAnnee, idCategorie);
		try {
			hmInter = this.db.getInterventionsByIntervenant(oldId);
			this.db.ajouterIntervenant(i);
			this.hmIntervenants.put(i.getId(), i);
			if (!this.bEnDuplication)
				this.ctrl.getVue().afficherNotification("Intervenant ajouté", "Intervenant ajouté avec succès",
						ControleurIHM.Notification.SUCCES);
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible d'ajouter l'intervenant",
					ControleurIHM.Notification.ERREUR);
			App.log(Level.WARNING, e, "Modele.dupliquerIntervenant(int,String,String,String,double,double,int)");
		}
		for (Intervention inter : hmInter.values()) {
			inter.setIdIntervenant(i.getId());
			inter.setIdAnnee(this.idAnnee);
			this.updateIntervention(inter);
		}
	}

	public void updateIntervenant(Intervenant i) {
		try {
			this.db.updateIntervenant(i);
			this.hmIntervenants.put(i.getId(), i);
			if (!this.bEnDuplication)
				this.ctrl.getVue().afficherNotification("Intervenant modifié", "Intervenant modifié avec succès",
						ControleurIHM.Notification.SUCCES);
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible de modifier l'intervenant",
					ControleurIHM.Notification.ERREUR);
			App.log(Level.WARNING, e, "Modele.updateIntervenant(Intervenant)");
		}
	}

	public void supprimerIntervenant(int id) {
		try {
			this.db.supprimerIntervenant(id);
			this.hmIntervenants.remove(id);
			if (!this.bEnDuplication)
				this.ctrl.getVue().afficherNotification("Intervenant supprimé", "Intervenant supprimé avec succès",
						ControleurIHM.Notification.SUCCES);
		} catch (SQLException e) {
			Intervenant i = this.hmIntervenants.get(id);
			String msg = String.format(
					"Impossible de supprimer l'intervenant :\n%1$s %2$s car il est reférencé \ndans une ou plusieurs intervention(s)",
					i.getPrenom(), i.getNom());
			this.ctrl.getVue().afficherNotification("Erreur", msg, ControleurIHM.Notification.ERREUR);
			App.log(Level.WARNING, e, "Modele.supprimerIntervenant(int)");
		}
	}

	public void ajouterIntervention(int idIntervenant, int idModule, int idTypeCours, int nbSemaines,
			String commentaire, int nbGroupe) {
		Intervention i = new Intervention(idIntervenant, idModule, idTypeCours, nbSemaines, nbGroupe, commentaire,
				idAnnee);
		try {
			this.db.ajouterIntervention(i);
			this.hmInterventions.put(i.getIdIntervention(), i);
			if (!this.bEnDuplication)
				this.ctrl.getVue().afficherNotification("Intervention ajoutée", "Intervention ajoutée avec succès",
						ControleurIHM.Notification.SUCCES);
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible d'ajouter l'intervention",
					ControleurIHM.Notification.ERREUR);
			App.log(Level.WARNING, e, "Modele.ajouterIntervention(int,int,int,int,String,int)");
		}
	}

	public void updateIntervention(Intervention i) {
		try {
			this.db.updateIntervention(i);
			this.hmInterventions.put(i.getIdIntervention(), i);
			if (!this.bEnDuplication)
				this.ctrl.getVue().afficherNotification("Intervention modifiée", "Intervention modifiée avec succès",
						ControleurIHM.Notification.SUCCES);
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible de modifier l'intervention",
					ControleurIHM.Notification.ERREUR);
			App.log(Level.WARNING, e, "Modele.updateIntervention(Intervention)");
		}
	}

	public void supprimerIntervention(int id) {
		try {
			this.db.supprimerIntervention(this.hmInterventions.get(id));
			this.hmInterventions.remove(id);
			if (!this.bEnDuplication)
				this.ctrl.getVue().afficherNotification("Intervention supprimée", "Intervention supprimée avec succès",
						ControleurIHM.Notification.SUCCES);
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible de supprimer l'intervention",
					ControleurIHM.Notification.ERREUR);
			App.log(Level.WARNING, e, "Modele.supprimerIntervention(int)");
		}
	}

	public void ajouterModule(String nom, String code, boolean valid, int idTypeModule, int idSemestre) {
		Module m = new Module(nom, code, valid, idTypeModule, this.idAnnee, idSemestre);
		try {
			this.db.ajouterModule(m);
			this.hmModules.put(m.getId(), m);
			if (!this.bEnDuplication)
				this.ctrl.getVue().afficherNotification("Module ajouté", "Module ajouté avec succès",
						ControleurIHM.Notification.SUCCES);
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible d'ajouter le module",
					ControleurIHM.Notification.ERREUR);
			App.log(Level.WARNING, e, "Modele.ajouterModule(String,String,boolean,int,int)");
		}
	}

	public void dupliquerModule(int id, String nom, String code, boolean valid, int idTypeModule, int idSemestre) {
		Module ancienModule = this.hmModules.get(id);
		List<HeureCours> lstHeureCours = this.getHeureCoursByModule(id, ancienModule.getIdAnnee());
		Map<Integer, Intervention> hmInter = this.getHmInterventionsModule(id);
		Module m = new Module(nom, code, valid, idTypeModule, this.idAnnee, idSemestre);
		try {
			this.db.ajouterModule(m);
		} catch (SQLException e) {
			App.log(Level.WARNING, e, "Modele.dupliquerModule(int,String,String,boolean,int,int)");
		}
		this.hmModules.put(m.getId(), m);
		for (HeureCours hc : lstHeureCours)
			this.ajouterHeureCours(hc.getIdTypeCours(), m.getId(), hc.getHeure(), hc.getNbSemaine(),
					hc.gethParSemaine());
		for (Intervention i : hmInter.values())
			this.ajouterIntervention(i.getIdIntervenant(), m.getId(), i.getIdTypeCours(), i.getNbSemaines(),
					i.getCommentaire(), i.getNbGroupe());
	}

	public void updateModule(Module m) {
		try {
			this.db.updateModule(m);
			this.hmModules.put(m.getId(), m);
			if (!this.bEnDuplication)
				this.ctrl.getVue().afficherNotification("Module modifié", "Module modifié avec succès",
						ControleurIHM.Notification.SUCCES);
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible de modifier le module",
					ControleurIHM.Notification.ERREUR);
			App.log(Level.WARNING, e, "Modele.updateModule(Module)");
		}
	}

	public void supprimerModule(int id) {
		if (this.getHmInterventionsModule(id).size() != 0)
			this.ctrl.getVue().afficherNotification("Erreur",
					"Impossible de supprimer le module car il\n possède une ou plusieurs intervention(s)",
					ControleurIHM.Notification.ERREUR);
		else {
			try {
				List<HeureCours> lstHeuresCours = this.ctrl.getModele().getHeureCoursByModule(id, this.idAnnee);
				for (HeureCours hc : lstHeuresCours) {
					this.db.supprimerHeureCours(hc);
					this.hmHeuresCours.remove(hc.getIdTypeCours() + "-" + hc.getIdModule());
				}
				this.db.supprimerModule(id);
				this.hmModules.remove(id);
				if (!this.bEnDuplication)
					this.ctrl.getVue().afficherNotification("Module supprimé", "Module supprimé avec succès",
							ControleurIHM.Notification.SUCCES);
			} catch (SQLException e) {
				this.ctrl.getVue().afficherNotification("Erreur", "Impossible de supprimer le module",
						ControleurIHM.Notification.ERREUR);
				App.log(Level.WARNING, e, "Modele.supprimerModule(int)");
			}
		}
	}

	public void ajouterSemestre(int nbGTD, int nbGTP, int nbGCM, int nbGAutre, String couleur) {
		Semestre s = new Semestre(nbGTD, nbGTD, nbGCM, nbGAutre, couleur, this.idAnnee);
		try {
			this.db.ajouterSemestre(s);
			this.hmSemestres.put(s.getId(), s);
			if (!this.bEnDuplication)
				this.ctrl.getVue().afficherNotification("Semestre ajouté", "Semestre ajouté avec succès",
						ControleurIHM.Notification.SUCCES);
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible d'ajouter le semestre",
					ControleurIHM.Notification.ERREUR);
			App.log(Level.WARNING, e, "Modele.ajouterSemestre(int,int,int,int,String)");
		}
	}

	public void dupliquerSemestre(List<Module> lstModuleParSemestre, int nbGTD, int nbGTP, int nbGCM, int nbGAutre,
			String couleur) {
		Semestre s = new Semestre(nbGTD, nbGTD, nbGCM, nbGAutre, couleur, this.idAnnee);
		try {
			this.db.ajouterSemestre(s);
			this.hmSemestres.put(s.getId(), s);
		} catch (SQLException e) {
			App.log(Level.WARNING, e, "Modele.dupliquerSemestre(List<Module>,int,int,int,int,String)");
		}
		for (Module m : lstModuleParSemestre)
			this.dupliquerModule(m.getId(), m.getNom(), m.getCode(), m.isValid(), m.getIdTypeModule(), s.getId());
	}

	public void updateSemestre(Semestre s) {
		try {
			this.db.updateSemestre(s);
			this.hmSemestres.put(s.getId(), s);
			if (!this.bEnDuplication)
				this.ctrl.getVue().afficherNotification("Semestre modifié", "Semestre modifié avec succès",
						ControleurIHM.Notification.SUCCES);
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible de modifier le semestre",
					ControleurIHM.Notification.ERREUR);
			App.log(Level.WARNING, e, "Modele.updateSemestre(Semestre)");
		}
	}

	public void ajouterHeureCours(int idTypeCours, int idModule, double heure, int nbSemaine, double hParSemaine) {
		HeureCours hc = new HeureCours(idTypeCours, idModule, heure, nbSemaine, hParSemaine, this.idAnnee);
		try {
			this.db.ajouterHeureCours(hc);
			this.hmHeuresCours.put(idTypeCours + "-" + idModule, hc);
			if (!this.bEnDuplication)
				this.ctrl.getVue().afficherNotification("Heure de cours ajoutée", "Heure de cours ajoutée avec succès",
						ControleurIHM.Notification.SUCCES);
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible d'ajouter l'heure de cours",
					ControleurIHM.Notification.ERREUR);
			App.log(Level.WARNING, e, "Modele.ajouterHeureCours(int,int,double,int,double)");
		}
	}

	public void updateHeureCours(HeureCours heureCours) {
		try {
			this.db.updateHeureCours(heureCours);
			this.hmHeuresCours.put(heureCours.getIdTypeCours() + "-" + heureCours.getIdModule(), heureCours);
			if (!this.bEnDuplication)
				this.ctrl.getVue().afficherNotification("Heure de cours modifiée",
						"Heure de cours modifiée avec succès", ControleurIHM.Notification.SUCCES);
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible de modifier l'heure de cours",
					ControleurIHM.Notification.ERREUR);
			App.log(Level.WARNING, e, "Modele.updateHeureCours(HeureCours)");
		}
	}

	public void updateTypeCours(TypeCours tc) {
		try {
			this.db.updateTypeCours(tc);
			this.hmTypeCours.put(tc.getId(), tc);
			if (!this.bEnDuplication)
				this.ctrl.getVue().afficherNotification("Type de cours modifié", "Type de cours modifié avec succès",
						ControleurIHM.Notification.SUCCES);
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible de modifier le type de cours",
					ControleurIHM.Notification.ERREUR);
			App.log(Level.WARNING, e, "Modele.updateTypeCours(TypeCours)");
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
			String annee = (Modele.DEBUT_ANNEE + iAnnee) + "-" + (Modele.DEBUT_ANNEE + iAnnee + 1);
			this.db.ajouterAnnee(iAnnee, annee);
			this.hmAnnee.put(iAnnee, annee);

			if (this.duplication) {
				this.bEnDuplication = true;
				Map<Integer, Categorie> hmTmpCategories = this.db.getCategories(this.idAnnee);
				Map<Integer, Semestre> hmTmpSemestres = this.db.getSemestres(this.idAnnee);
				this.idAnnee = iAnnee;
				List<Integer> keysList = new ArrayList<>(this.hmSemestres.keySet());
				keysList.sort(null);
				for (Integer i : keysList) {
					Semestre s = hmTmpSemestres.get(i);
					this.dupliquerSemestre(this.getModuleBySemestre(s.getId(), s.getIdAnnee()), s.getNbGTD(),
							s.getNbGTP(), s.getNbGCM(), s.getNbSemaine(), s.getCouleur());
				}
				for (Categorie c : hmTmpCategories.values())
					this.dupliquerCategorie(c.getId(), c.getNom(), c.gethMin(), c.gethMax(), c.getRatioTp());
			} else {
				this.updateAnnee(annee);
				for (int i = 0; i < 6; i++)
					this.ajouterSemestre(0, 0, 0, 0, "#ffffff");
				this.ajouterCategorie("Enseignant", 384, 576, 1);
				this.ajouterCategorie("Contractuel", 192, 384, 1);
				this.ajouterCategorie("Vacataire", 0, 90, 2 / 3);
				this.ajouterCategorie("Enseignant-Chercheur", 192, 364, 1);
			}
			if (!this.bEnDuplication)
				this.ctrl.getVue().afficherNotification("Année ajoutée", "Année ajoutée avec succès",
						ControleurIHM.Notification.SUCCES);
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible d'ajouter l'année",
					ControleurIHM.Notification.ERREUR);
			App.log(Level.WARNING, e, "Modele.ajouterAnnee()");
		}
		this.bEnDuplication = false;
	}

	public void updateAnnee(String date) {
		for (Integer i : this.hmAnnee.keySet())
			if (this.hmAnnee.get(i).equals(date))
				this.idAnnee = i;
		try {
			this.hmCategories = this.db.getCategories(this.idAnnee);
			this.hmIntervenants = this.db.getIntervenants(this.idAnnee);
			this.hmInterventions = this.db.getInterventions(this.idAnnee);
			this.hmModules = this.db.getModules(this.idAnnee);
			this.hmSemestres = this.db.getSemestres(this.idAnnee);
			this.hmHeuresCours = this.db.getHeureCours(this.idAnnee);
			if (!this.bEnDuplication)
				this.ctrl.getVue().afficherNotification("Année modifiée", "Année modifiée avec succès",
						ControleurIHM.Notification.SUCCES);
		} catch (SQLException e) {
			this.ctrl.getVue().afficherNotification("Erreur", "Impossible de modifier l'année",
					ControleurIHM.Notification.ERREUR);
			App.log(Level.WARNING, e, "Modele.updateAnnee(String)");
		}
	}

	public boolean isDuplication() {
		return this.duplication;
	}

	public void setDuplication(boolean duplication) {
		this.duplication = duplication;
	}

	public Map<Integer, Categorie> getHmCategories() {
		return this.hmCategories;
	}

	public void setHmCategories(Map<Integer, Categorie> hmCategories) {
		this.hmCategories = hmCategories;
	}

	public Map<Integer, Intervenant> getHmIntervenants() {
		return this.hmIntervenants;
	}

	public void setHmIntervenants(Map<Integer, Intervenant> hmIntervenants) {
		this.hmIntervenants = hmIntervenants;
	}

	public Map<Integer, Intervention> getHmInterventions() {
		return this.hmInterventions;
	}

	public Map<Integer, Intervention> getHmInterventionsModule(int idModule) {
		Map<Integer, Intervention> ret = new HashMap<>();
		for (Integer id : this.hmInterventions.keySet())
			if (this.hmInterventions.get(id).getIdModule() == idModule)
				ret.put(id, this.hmInterventions.get(id));
		return ret;
	}

	public void setHmInterventions(Map<Integer, Intervention> hmInterventions) {
		this.hmInterventions = hmInterventions;
	}

	public Map<Integer, Module> getHmModules() {
		return this.hmModules;
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
		return this.hmTypeCours;
	}

	public void setHmTypeCours(Map<Integer, TypeCours> hmTypeCours) {
		this.hmTypeCours = hmTypeCours;
	}

	public Map<String, HeureCours> getHmHeuresCours() {
		return this.hmHeuresCours;
	}

	public void setHmHeuresCours(Map<String, HeureCours> hmHeuresCours) {
		this.hmHeuresCours = hmHeuresCours;
	}

	public int getIdAnnee() {
		return this.idAnnee;
	}

	public void setIdAnnee(int idAnnee) {
		this.idAnnee = idAnnee;
	}

	public String getNomCateg(int idCategorie) {
		try {
			return this.db.getNomCateg(idCategorie);
		} catch (SQLException e) {
			App.log(Level.WARNING, e, "Modele.getNomCateg(int)");
			return "";
		}
	}

	public Map<Integer, String> getHmAnnee() {
		return this.hmAnnee;
	}

	public void setHmAnnee(Map<Integer, String> hmAnnee) {
		this.hmAnnee = hmAnnee;
	}

	public Map<Integer, TypeModule> getHmTypeModule() {
		return this.hmTypeModule;
	}

	public void setHmTypeModule(Map<Integer, TypeModule> hmTypeModule) {
		this.hmTypeModule = hmTypeModule;
	}

	public Map<Module, List<Intervention>> getModulesIntervenant(int idIntervenant) {
		Map<Module, List<Intervention>> hmIntervenantModule = new HashMap<>();

		for (Intervention intervention : this.hmInterventions.values()) {
			if (intervention.getIdIntervenant() == idIntervenant) {
				Module mod = this.hmModules.get(intervention.getIdModule());
				if (hmIntervenantModule.keySet().contains(mod)) {
					hmIntervenantModule.get(mod).add(intervention);
				} else {
					hmIntervenantModule.put(mod, new ArrayList<>(Arrays.asList(intervention)));
					// hmIntervenantModule.get(mod).add();
				}
			}
		}
		return hmIntervenantModule;
	}

	public Map<Intervenant, List<Intervention>> getIntervenantsModule(int idModule) {
		Map<Intervenant, List<Intervention>> hmModuleIntervenant = new HashMap<>();

		for (Intervention intervention : this.hmInterventions.values()) {
			if (intervention.getIdModule() == idModule) {
				Intervenant interv = this.hmIntervenants.get(intervention.getIdIntervenant());
				if (hmModuleIntervenant.keySet().contains(interv)) {
					hmModuleIntervenant.get(interv).add(intervention);
				} else {
					hmModuleIntervenant.put(interv, new ArrayList<Intervention>());
					hmModuleIntervenant.get(interv).add(intervention);
				}
			}
		}
		return hmModuleIntervenant;
	}

	public Map<Intervenant, List<Intervention>> getIntervenantInterventions() {
		Map<Intervenant, List<Intervention>> hmModuleIntervenant = new HashMap<>();

		for (Intervention intervention : this.hmInterventions.values()) {
			Intervenant interv = this.hmIntervenants.get(intervention.getIdIntervenant());
			if (hmModuleIntervenant.keySet().contains(interv)) {
				hmModuleIntervenant.get(interv).add(intervention);
			} else {
				hmModuleIntervenant.put(interv, new ArrayList<Intervention>());
				hmModuleIntervenant.get(interv).add(intervention);
			}
		}
		return hmModuleIntervenant;
	}

}