package modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import vue.App;
import vue.ResourceManager;

/**
 * Classe de liaison à la base de données
 * 
 * @see Intervenant
 * @see Intervention
 * @see Module
 * @see Semestre
 * @see TypeCours
 * @see HeureCours
 * 
 * @since la version Java
 * 
 */
public class DB {
	
	private static DB dbInstance;
	
	private Connection connec;
	
	private PreparedStatement psSelectCategorie;
	private PreparedStatement psSelectIntervenant;
	private PreparedStatement psSelectIntervention;
	private PreparedStatement psSelectInterventionModule;
	private PreparedStatement psSelectModule;
	private PreparedStatement psSelectSemestre;
	private PreparedStatement psSelectModuleBySemestre;
	private PreparedStatement psSelectTypeCours;
	private PreparedStatement psSelectHeureCours;
	private PreparedStatement psSelectAnnee;
	private PreparedStatement psSelectDerAnnee;
	private PreparedStatement psSelectNomCateg;
	private PreparedStatement psSelectHeureCoursByModule;
	private PreparedStatement psSelectTypeModule;
	private PreparedStatement psSelectInterventionByIntervenant;
	private PreparedStatement psSelectIdTypeCoursByNom;
	private PreparedStatement psSelectIntervenantByCateg;
	
	private PreparedStatement psInsertCategorie;
	private PreparedStatement psInsertIntervenant;
	private PreparedStatement psInsertIntervention;
	private PreparedStatement psInsertModule;
	private PreparedStatement psInsertSemestre;
	private PreparedStatement psInsertHeureCours;
	private PreparedStatement psInsertAnnee;
	
	private PreparedStatement psUpdateCategorie;
	private PreparedStatement psUpdateIntervenant;
	private PreparedStatement psUpdateIntervention;
	private PreparedStatement psUpdateModule;
	private PreparedStatement psUpdateSemestre;
	private PreparedStatement psUpdateTypeCours;
	private PreparedStatement psUpdateHeureCours;
	
	private PreparedStatement psDeleteCategorie;
	private PreparedStatement psDeleteIntervenant;
	private PreparedStatement psDeleteIntervention;
	private PreparedStatement psDeleteModule;
	private PreparedStatement psDeleteSemestre;
	private PreparedStatement psDeleteHeureCours;
	private PreparedStatement psDeleteTypeCours;
	
	private DB() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			App.log(Level.SEVERE, e);
		}
		
		try {
			LireFichier lf = new LireFichier(ResourceManager.getIdFile());
			this.connec = DriverManager.getConnection("jdbc:postgresql://" + ResourceManager.getDBHost() + "/" + lf.getIdentifiant(), lf.getIdentifiant(), lf.getMotDePasse());
			this.psSelectCategorie                 = this.connec.prepareStatement("SELECT * FROM Categorie WHERE idAnnee = ?");
			this.psSelectIntervenant               = this.connec.prepareStatement("SELECT * FROM Intervenant WHERE idAnnee = ?");
			this.psSelectIntervention              = this.connec.prepareStatement("SELECT * FROM Intervention WHERE idAnnee = ?");
			this.psSelectInterventionModule        = this.connec.prepareStatement("SELECT * FROM Intervention WHERE idAnnee = ? AND idModule = ?");
			this.psSelectModule                    = this.connec.prepareStatement("SELECT * FROM Module WHERE idAnnee = ?");
			this.psSelectSemestre                  = this.connec.prepareStatement("SELECT * FROM Semestre WHERE idAnnee = ?");
			this.psSelectModuleBySemestre          = this.connec.prepareStatement("SELECT * FROM Module WHERE idSemestre = ? AND idAnnee = ?");
			this.psSelectTypeCours                 = this.connec.prepareStatement("SELECT * FROM TypeCours");
			this.psSelectHeureCours                = this.connec.prepareStatement("SELECT * FROM HeureCours JOIN Module USING(idModule) WHERE Module.idAnnee = ? ");
			this.psSelectDerAnnee                  = this.connec.prepareStatement("SELECT * FROM annee ORDER BY idannee DESC LIMIT 1;");
			this.psSelectNomCateg                  = this.connec.prepareStatement("SELECT nomCategorie FROM Categorie WHERE idCategorie = ?");
			this.psSelectAnnee                     = this.connec.prepareStatement("SELECT * FROM Annee");
			this.psSelectHeureCoursByModule        = this.connec.prepareStatement("SELECT * FROM HeureCours WHERE idModule = ? AND idAnnee = ?");
			this.psSelectTypeModule                = this.connec.prepareStatement("SELECT * FROM TypeModule");
			
			this.psSelectIdTypeCoursByNom          = this.connec.prepareStatement("SELECT idTypeCours FROM TypeCours WHERE nomCours = ?");
			this.psSelectInterventionByIntervenant = this.connec.prepareStatement("SELECT * FROM Intervention WHERE idIntervenant = ?");
			this.psSelectIntervenantByCateg        = this.connec.prepareStatement("SELECT * FROM Intervenant WHERE idCategorie = ? AND idAnnee = ?");
			
			this.psInsertCategorie                 = this.connec.prepareStatement("INSERT INTO Categorie VALUES (?,?,?,?,?,?)");
			this.psInsertIntervenant               = this.connec.prepareStatement("INSERT INTO Intervenant VALUES (?,?,?,?,?,?,?,?)");
			this.psInsertIntervention              = this.connec.prepareStatement("INSERT INTO Intervention VALUES (?,?,?,?,?,?,?,?)");
			this.psInsertModule                    = this.connec.prepareStatement("INSERT INTO Module VALUES (?,?,?,?,?,?,?)");
			this.psInsertSemestre                  = this.connec.prepareStatement("INSERT INTO Semestre VALUES (?,?,?,?,?,?,?)");
			this.psInsertHeureCours                = this.connec.prepareStatement("INSERT INTO HeureCours VALUES (?,?,?,?,?,?)");
			this.psInsertAnnee                     = this.connec.prepareStatement("INSERT INTO Annee VALUES (?, ?)");
			
			this.psUpdateCategorie                 = this.connec.prepareStatement("UPDATE Categorie SET nomCategorie = ?, hMinCategorie = ?, hMaxCategorie = ?, ratioTp = ? WHERE idAnnee = ? AND idCategorie = ?");
			this.psUpdateIntervenant               = this.connec.prepareStatement("UPDATE Intervenant SET prenom = ?, nom = ?, email = ?, hMinIntervenant = ?, hMaxIntervenant = ?, idCategorie = ? WHERE idAnnee = ? AND idIntervenant = ?");
			this.psUpdateIntervention              = this.connec.prepareStatement("UPDATE Intervention SET commentaire = ?, nbSemainesIntervention = ?, nbGroupe = ?, idIntervenant = ?, idModule = ? WHERE idIntervention = ? AND idAnnee = ?");
			this.psUpdateModule                    = this.connec.prepareStatement("UPDATE Module SET nomModule = ?, code = ?, valid=?, idSemestre = ?, idTypeModule = ? WHERE idAnnee = ? AND idModule = ?");
			this.psUpdateSemestre                  = this.connec.prepareStatement("UPDATE Semestre SET nbGTD = ?, nbGTP = ?, nbGCM = ?, nbSemaine = ?, couleur = ? WHERE idAnnee = ? AND idSemestre = ?");
			this.psUpdateHeureCours                = this.connec.prepareStatement("UPDATE HeureCours SET heure = ?, nbSemaine = ?, hParSemaine = ? WHERE idTypeCours = ? AND idModule = ? AND idAnnee = ? ");
			this.psUpdateTypeCours                 = this.connec.prepareStatement("UPDATE TypeCours SET coefficient = ?, nomCours = ? WHERE idTypeCours = ?");
			
			this.psDeleteCategorie                 = this.connec.prepareStatement("DELETE FROM Categorie WHERE idCategorie = ?");
			this.psDeleteIntervenant               = this.connec.prepareStatement("DELETE FROM Intervenant WHERE idIntervenant = ?");
			this.psDeleteIntervention              = this.connec.prepareStatement("DELETE FROM Intervention WHERE idIntervention = ?");
			this.psDeleteModule                    = this.connec.prepareStatement("DELETE FROM Module WHERE idModule = ?");
			this.psDeleteSemestre                  = this.connec.prepareStatement("DELETE FROM Semestre WHERE idSemestre = ?");
			this.psDeleteHeureCours                = this.connec.prepareStatement("DELETE FROM HeureCours WHERE idTypeCours = ? and idModule = ?");
			this.psDeleteTypeCours                 = this.connec.prepareStatement("DELETE FROM TypeCours WHERE idTypeCours = ?");
		} catch (SQLException e) {
			App.log(Level.WARNING, e);
		}
	}
	
	public static DB getInstance() {
		if (DB.dbInstance == null)
			return DB.dbInstance = new DB();
		return DB.dbInstance;
	}
	
	/*-----------*/
	/* Categorie */
	/*-----------*/
	
	/**
	 * @param idAnnee indice de l'année souhaitée
	 * @return une {@link Map} composé de :
	 *         <ul>
	 *         <li>indice en {@link Integer}</li>
	 *         <li>{@link Categorie} correspondante</li>
	 *         </ul>
	 */
	public Map<Integer, Categorie> getCategories(int idAnnee) throws SQLException {
		Map<Integer, Categorie> hmCateg = new HashMap<>();
		this.psSelectCategorie.setInt(1, idAnnee);
		ResultSet rs = this.psSelectCategorie.executeQuery();
		while (rs.next()) {
			hmCateg.put(rs.getInt("idCategorie"), new Categorie(rs.getInt("idCategorie"), rs.getString("nomCategorie"),
					rs.getDouble("hMinCategorie"), rs.getDouble("hMaxCategorie"), rs.getDouble("ratioTp"), idAnnee));
		}
		return hmCateg;
	}
	
	/**
	 * méthode pour ajouter une {@link Categorie} à la base de données
	 * 
	 * @param categorie {@link Categorie} à ajouter
	 * @throws SQLException
	 */
	public void ajouterCategorie(Categorie categorie) throws SQLException {
		this.psInsertCategorie.setInt(1, categorie.getId());
		this.psInsertCategorie.setString(2, categorie.getNom());
		this.psInsertCategorie.setDouble(3, categorie.gethMin());
		this.psInsertCategorie.setDouble(4, categorie.gethMax());
		this.psInsertCategorie.setDouble(5, categorie.getRatioTp());
		this.psInsertCategorie.setInt(6, categorie.getIdAnnee());
		this.psInsertCategorie.executeUpdate();
	}
	
	/**
	 * méthode pour modifier une {@link Categorie} de la base de données
	 * 
	 * @param categorie {@link Categorie} à modifier
	 * @throws SQLException
	 */
	public void updateCategorie(Categorie categorie) throws SQLException {
		this.psUpdateCategorie.setInt(6, categorie.getId());
		this.psUpdateCategorie.setString(1, categorie.getNom());
		this.psUpdateCategorie.setDouble(2, categorie.gethMin());
		this.psUpdateCategorie.setDouble(3, categorie.gethMax());
		this.psUpdateCategorie.setDouble(4, categorie.getRatioTp());
		this.psUpdateCategorie.setInt(5, categorie.getIdAnnee());
		this.psUpdateCategorie.executeUpdate();
	}
	
	/**
	 * méthode pour supprimer une {@link Categorie} de la base de données
	 * 
	 * @param categorie {@link Categorie} à supprimer
	 * @throws SQLException
	 */
	public void supprimerCategorie(Categorie categorie) throws SQLException {
		this.psDeleteCategorie.setInt(1, categorie.getId());
		this.psDeleteCategorie.executeUpdate();
	}
	
	/**
	 * méthode pour obtenir le nom d'une {@link Categorie}
	 * 
	 * @param idCategorie identifiant de la {@link Categorie}
	 * @return le nom de la {@link Categorie}
	 * @throws SQLException
	 */
	public String getNomCateg(int idCategorie) throws SQLException {
		this.psSelectNomCateg.setInt(1, idCategorie);
		ResultSet rs = this.psSelectNomCateg.executeQuery();
		while (rs.next())
			return rs.getString("nomCategorie");
		return "";
	}
	
	/*-------------*/
	/* Intervenant */
	/*-------------*/
	
	/**
	 * @param idAnnee indice de l'année souhaitée
	 * @return une {@link Map} composé de :
	 *         <ul>
	 *         <li>indice en {@link Integer}</li>
	 *         <li>{@link Intervenant} correspondant</li>
	 *         </ul>
	 */
	public Map<Integer, Intervenant> getIntervenants(int idAnnee) throws SQLException {
		Map<Integer, Intervenant> hmInter = new HashMap<>();
		this.psSelectIntervenant.setInt(1, idAnnee);
		ResultSet rs = this.psSelectIntervenant.executeQuery();
		while (rs.next()) {
			hmInter.put(rs.getInt("idIntervenant"),
					new Intervenant(rs.getInt("idIntervenant"), rs.getString("prenom"), rs.getString("nom"),
							rs.getString("email"), rs.getDouble("hMinIntervenant"), rs.getDouble("hMaxIntervenant"),
							idAnnee, rs.getInt("idCategorie")));
		}
		return hmInter;
	}
	
	/**
	 * méthode pour ajouter un {@link Intervenant} de la base de données
	 * 
	 * @param itervenant {@link Intervenant} à ajouter
	 * @throws SQLException
	 */
	public void ajouterIntervenant(Intervenant itervenant) throws SQLException {
		this.psInsertIntervenant.setInt(1, itervenant.getId());
		this.psInsertIntervenant.setString(2, itervenant.getPrenom());
		this.psInsertIntervenant.setString(3, itervenant.getNom());
		this.psInsertIntervenant.setString(4, itervenant.getEmail());
		this.psInsertIntervenant.setDouble(5, itervenant.gethMin());
		this.psInsertIntervenant.setDouble(6, itervenant.gethMax());
		this.psInsertIntervenant.setInt(7, itervenant.getIdAnnee());
		this.psInsertIntervenant.setInt(8, itervenant.getIdCategorie());
		this.psInsertIntervenant.executeUpdate();
	}
	
	/**
	 * méthode pour modifier un {@link Intervenant} de la base de données
	 * 
	 * @param itervenant {@link Intervenant} à modifier
	 * @throws SQLException
	 */
	public void updateIntervenant(Intervenant itervenant) throws SQLException {
		this.psUpdateIntervenant.setInt(8, itervenant.getId());
		this.psUpdateIntervenant.setString(1, itervenant.getPrenom());
		this.psUpdateIntervenant.setString(2, itervenant.getNom());
		this.psUpdateIntervenant.setString(3, itervenant.getEmail());
		this.psUpdateIntervenant.setDouble(4, itervenant.gethMin());
		this.psUpdateIntervenant.setDouble(5, itervenant.gethMax());
		this.psUpdateIntervenant.setInt(6, itervenant.getIdCategorie());
		this.psUpdateIntervenant.setInt(7, itervenant.getIdAnnee());
		this.psUpdateIntervenant.executeUpdate();
	}
	
	/**
	 * méthode pour supprimer un {@link Intervenant} de la base de données
	 * 
	 * @param id de l'{@link Intervenant} à supprimer
	 * @throws SQLException
	 */
	public void supprimerIntervenant(int id) throws SQLException {
		this.psDeleteIntervenant.setInt(1, id);
		this.psDeleteIntervenant.executeUpdate();
	}
	
	/*--------------*/
	/* Intervention */
	/*--------------*/
	
	/**
	 * @param idAnnee indice de l'année souhaitée
	 * @return une {@link Map} composé de :
	 *         <ul>
	 *         <li>indice en {@link Integer}</li>
	 *         <li>{@link Intervention} correspondante</li>
	 *         </ul>
	 */
	public Map<Integer, Intervention> getInterventions(int idAnnee) throws SQLException {
		Map<Integer, Intervention> hmInterventions = new HashMap<>();
		this.psSelectIntervention.setInt(1, idAnnee);
		ResultSet rs = this.psSelectIntervention.executeQuery();
		while (rs.next()) {
			hmInterventions
					.put(rs.getInt("idIntervention"),
							new Intervention(rs.getInt("idIntervention"), rs.getInt("idIntervenant"),
									rs.getInt("idModule"), rs.getInt("idTypeCours"), rs.getInt("nbSemainesIntervention"),
									rs.getInt("nbGroupe"), rs.getString("commentaire"), rs.getInt("idAnnee")));
		}
		return hmInterventions;
	}
	
	public Map<Integer, Intervention> getInterventionsByIntervenant(int idIntervenant) throws SQLException {
		Map<Integer, Intervention> hmInterventions = new HashMap<>();
		this.psSelectInterventionByIntervenant.setInt(1, idIntervenant);
		ResultSet rs = this.psSelectInterventionByIntervenant.executeQuery();
		while (rs.next()) {
			hmInterventions
					.put(rs.getInt("idIntervention"),
							new Intervention(rs.getInt("idIntervention"), rs.getInt("idIntervenant"),
									rs.getInt("idModule"), rs.getInt("idTypeCours"), rs.getInt("nbSemainesIntervention"),
									rs.getInt("nbGroupe"), rs.getString("commentaire"), rs.getInt("idAnnee")));
		}
		return hmInterventions;
	}
	
	public Map<Integer, Intervention> getInterventionsModule(int idAnnee, int idModule) throws SQLException {
		Map<Integer, Intervention> hmInterventions = new HashMap<>();
		this.psSelectInterventionModule.setInt(1, idAnnee);
		this.psSelectInterventionModule.setInt(2, idModule);
		ResultSet rs = this.psSelectInterventionModule.executeQuery();
		while (rs.next()) {
			hmInterventions
					.put(rs.getInt("idIntervention"),
							new Intervention(rs.getInt("idIntervention"), rs.getInt("idIntervenant"),
									rs.getInt("idModule"), rs.getInt("idTypeCours"), rs.getInt("nbSemainesIntervention"),
									rs.getInt("nbGroupe"), rs.getString("commentaire"), rs.getInt("idAnnee")));
		}
		return hmInterventions;
	}
	
	/**
	 * méthode pour ajouter une {@link Intervention} de la base de données
	 * 
	 * @param intervention {@link Intervention} à ajouter
	 * @throws SQLException
	 */
	public void ajouterIntervention(Intervention intervention) throws SQLException {
		this.psInsertIntervention.setInt   (1, intervention.getIdIntervention());
		this.psInsertIntervention.setInt   (2, intervention.getIdIntervenant());
		this.psInsertIntervention.setInt   (3, intervention.getIdModule());
		this.psInsertIntervention.setInt   (4, intervention.getIdTypeCours());
		this.psInsertIntervention.setInt   (5, intervention.getNbSemaines());
		this.psInsertIntervention.setInt   (6, intervention.getNbGroupe());
		this.psInsertIntervention.setString(7, intervention.getCommentaire());
		this.psInsertIntervention.setInt   (8, intervention.getIdAnnee());
		this.psInsertIntervention.executeUpdate();
	}
	
	/**
	 * méthode pour modifier une {@link Intervention} de la base de données
	 * 
	 * @param intervention {@link Intervention} à modifier
	 * @throws SQLException
	 */
	public void updateIntervention(Intervention intervention) throws SQLException {
		this.psUpdateIntervention.setString(1, intervention.getCommentaire());
		this.psUpdateIntervention.setInt   (2, intervention.getNbSemaines());
		this.psUpdateIntervention.setInt   (3, intervention.getNbGroupe());
		this.psUpdateIntervention.setInt   (4, intervention.getIdIntervenant());
		this.psUpdateIntervention.setInt   (5, intervention.getIdModule());
		this.psUpdateIntervention.setInt   (6, intervention.getIdIntervention());
		this.psUpdateIntervention.setInt   (7, intervention.getIdAnnee());
		this.psUpdateIntervention.executeUpdate();
	}
	
	/**
	 * méthode pour supprimer une {@link Intervention} de la base de données
	 * 
	 * @param intervention {@link Intervention} à supprimer
	 * @throws SQLException
	 */
	public void supprimerIntervention(Intervention intervention) throws SQLException {
		this.psDeleteIntervention.setInt(1, intervention.getIdIntervention());
		this.psDeleteIntervention.executeUpdate();
	}
	
	/*--------*/
	/* Module */
	/*--------*/
	
	/**
	 * @param idAnnee indice de l'année souhaitée
	 * @return une {@link Map} composé de :
	 *         <ul>
	 *         <li>indice en {@link Integer}</li>
	 *         <li>{@link Module} correspondant</li>
	 *         </ul>
	 */
	public Map<Integer, Module> getModules(int idAnnee) throws SQLException {
		Map<Integer, Module> hmModule = new HashMap<>();
		this.psSelectModule.setInt(1, idAnnee);
		ResultSet rs = this.psSelectModule.executeQuery();
		while (rs.next()) {
			hmModule.put(rs.getInt("idModule"),
					new Module(rs.getInt("idModule"), rs.getString("nomModule"), rs.getString("code"), rs.getBoolean("valid"),
							rs.getInt("idTypeModule"), idAnnee,
							rs.getInt("idSemestre")));
		}
		return hmModule;
	}
	
	public List<Intervenant> getIntervenantParCateg(int idAnnee, int idCategorie) throws SQLException {
		List<Intervenant> hmModule = new ArrayList<>();
		this.psSelectIntervenantByCateg.setInt(1, idCategorie);
		this.psSelectIntervenantByCateg.setInt(2, idAnnee);
		ResultSet rs = this.psSelectIntervenantByCateg.executeQuery();
		while (rs.next()) {
			hmModule.add(new Intervenant(rs.getInt("idIntervenant"), rs.getString("prenom"), rs.getString("nom"),
							rs.getString("email"), rs.getDouble("hMinIntervenant"), rs.getDouble("hMaxIntervenant"),
							idAnnee, rs.getInt("idCategorie")));
		}
		return hmModule;
	}
	
	/**
	 * méthode pour obtenir la list des modules d'un semestre
	 * 
	 * @param idSemestre identifiant du {@link Semestre}
	 * @param idAnnee    identifiant de l'année
	 * @return {@link List} de {@link Module} du {@link Semestre}
	 * @throws SQLException
	 */
	public List<Module> getModuleBySemestre(int idSemestre, int idAnnee) throws SQLException {
		List<Module> lstModules = new ArrayList<>();
		this.psSelectModuleBySemestre.setInt(1, idSemestre);
		this.psSelectModuleBySemestre.setInt(2, idAnnee);
		ResultSet rs = this.psSelectModuleBySemestre.executeQuery();
		while (rs.next()) {
			lstModules.add(new Module(rs.getInt("idModule"), rs.getString("nomModule"), rs.getString("code"), rs.getBoolean("valid"),
					rs.getInt("idTypeModule"), idAnnee,
					rs.getInt("idSemestre")));
		}
		return lstModules;
	}
	
	/**
	 * méthode pour ajouter une {@link Module} de la base de données
	 * 
	 * @param module {@link Module} à ajouter
	 * @throws SQLException
	 */
	public void ajouterModule(Module module) throws SQLException {
		this.psInsertModule.setInt(1, module.getId());
		this.psInsertModule.setString(2, module.getNom());
		this.psInsertModule.setString(3, module.getCode());
		this.psInsertModule.setBoolean(4, module.isValid());
		this.psInsertModule.setInt(5, module.getIdTypeModule());
		this.psInsertModule.setInt(7, module.getIdSemestre());
		this.psInsertModule.setInt(6, module.getIdAnnee());
		this.psInsertModule.executeUpdate();
	}
	
	/**
	 * méthode pour modifier une {@link Module} de la base de données
	 * 
	 * @param module {@link Module} à modifier
	 * @throws SQLException
	 */
	public void updateModule(Module module) throws SQLException {
		this.psUpdateModule.setInt(7, module.getId());
		this.psUpdateModule.setString(1, module.getNom());
		this.psUpdateModule.setString(2, module.getCode());
		this.psUpdateModule.setBoolean(3, module.isValid());
		this.psUpdateModule.setInt(6, module.getIdAnnee());
		this.psUpdateModule.setInt(4, module.getIdSemestre());
		this.psUpdateModule.setInt(5, module.getIdTypeModule());
		this.psUpdateModule.executeUpdate();
	}
	
	/**
	 * méthode pour supprimer une {@link Module} de la base de données
	 * 
	 * @param idModule indentifiant du {@link Module} à supprimer
	 * @throws SQLException
	 */
	public void supprimerModule(int idModule) throws SQLException {
		this.psDeleteModule.setInt(1, idModule);
		this.psDeleteModule.executeUpdate();
	}
	
	/*----------*/
	/* Semestre */
	/*----------*/
	
	/**
	 * @param idAnnee indice de l'année souhaitée
	 * @return une {@link Map} composé de :
	 *         <ul>
	 *         <li>indice en {@link Integer}</li>
	 *         <li>{@link Semestre} correspondant</li>
	 *         </ul>
	 */
	public Map<Integer, Semestre> getSemestres(int idAnnee) throws SQLException {
		Map<Integer, Semestre> hmSemestre = new HashMap<>();
		this.psSelectSemestre.setInt(1, idAnnee);
		ResultSet rs = this.psSelectSemestre.executeQuery();
		while (rs.next()) {
			hmSemestre.put(rs.getInt("idSemestre"),
					new Semestre(rs.getInt("idSemestre"), rs.getInt("nbGTD"), rs.getInt("nbGTP"), rs.getInt("nbGCM"),
							rs.getInt("nbSemaine"), rs.getString("couleur"), idAnnee));
		}
		return hmSemestre;
	}
	
	/**
	 * méthode pour ajouter une {@link Semestre} de la base de données
	 * 
	 * @param semestre {@link Semestre} à ajouter
	 * @throws SQLException
	 */
	public void ajouterSemestre(Semestre semestre) throws SQLException {
		this.psInsertSemestre.setInt(1, semestre.getId());
		this.psInsertSemestre.setInt(2, semestre.getNbGTD());
		this.psInsertSemestre.setInt(3, semestre.getNbGTP());
		this.psInsertSemestre.setInt(4, semestre.getNbGCM());
		this.psInsertSemestre.setInt(5, semestre.getNbSemaine());
		this.psInsertSemestre.setString(6, semestre.getCouleur());
		this.psInsertSemestre.setInt(7, semestre.getIdAnnee());
		this.psInsertSemestre.executeUpdate();
	}
	
	/**
	 * méthode pour modifier une {@link Semestre} de la base de données
	 * 
	 * @param semestre {@link Semestre} à modifier
	 * @throws SQLException
	 */
	public void updateSemestre(Semestre semestre) throws SQLException {
		this.psUpdateSemestre.setInt(7, semestre.getId());
		this.psUpdateSemestre.setInt(1, semestre.getNbGTD());
		this.psUpdateSemestre.setInt(2, semestre.getNbGTP());
		this.psUpdateSemestre.setInt(3, semestre.getNbGCM());
		this.psUpdateSemestre.setInt(4, semestre.getNbSemaine());
		this.psUpdateSemestre.setInt(6, semestre.getIdAnnee());
		this.psUpdateSemestre.setString(5, semestre.getCouleur());
		this.psUpdateSemestre.executeUpdate();
	}
	
	/**
	 * méthode pour supprimer une {@link Semestre} de la base de données
	 * 
	 * @param semestre {@link Semestre} à supprimer
	 * @throws SQLException
	 */
	public void supprimerSemestre(Semestre semestre) throws SQLException {
		this.psDeleteSemestre.setInt(1, semestre.getId());
		this.psDeleteSemestre.executeUpdate();
	}
	
	/*------------*/
	/* TypeModule */
	/*------------*/
	
	/**
	 * @param idAnnee indice de l'année souhaitée
	 * @return une {@link Map} composé de :
	 *         <ul>
	 *         <li>indice en {@link Integer}</li>
	 *         <li>{@link TypeModule} correspondant</li>
	 *         </ul>
	 */
	public Map<Integer, TypeModule> getTypeModule() throws SQLException {
		Map<Integer, TypeModule> hmTypeModule = new HashMap<>();
		ResultSet rs = this.psSelectTypeModule.executeQuery();
		while (rs.next()) {
			hmTypeModule.put(rs.getInt("idTypeModule"),
					new TypeModule(rs.getInt("idTypeModule"), rs.getString("nomTypeModule")));
		}
		return hmTypeModule;
	}
	
	/*-----------*/
	/* TypeCours */
	/*-----------*/
	
	/**
	 * @param idAnnee indice de l'année souhaitée
	 * @return une {@link Map} composé de :
	 *         <ul>
	 *         <li>indice en {@link Integer}</li>
	 *         <li>{@link TypeCours} correspondant</li>
	 *         </ul>
	 */
	public Map<Integer, TypeCours> getTypeCours() throws SQLException {
		Map<Integer, TypeCours> hmTypeCours = new HashMap<>();
		ResultSet rs = this.psSelectTypeCours.executeQuery();
		while (rs.next()) {
			hmTypeCours.put(rs.getInt("idTypeCours"),
					new TypeCours(rs.getInt("idTypeCours"), rs.getString("nomCours"), rs.getDouble("coefficient")));
		}
		return hmTypeCours;
	}
	
	/**
	 * méthode pour modifier une {@link TypeCours} de la base de données
	 * 
	 * @param typeCours {@link TypeCours} à modifier
	 * @throws SQLException
	 */
	public void updateTypeCours(TypeCours typeCours) throws SQLException {
		this.psUpdateTypeCours.setDouble(1, typeCours.getCoefficient());
		this.psUpdateTypeCours.setString(2, typeCours.getNom());
		this.psUpdateTypeCours.setInt(3, typeCours.getId());
		this.psUpdateTypeCours.executeUpdate();
	}
	
	/**
	 * méthode pour supprimer une {@link TypeCours} de la base de données
	 * 
	 * @param typeCours {@link TypeCours} à supprimer
	 * @throws SQLException
	 */
	public void supprimerTypeCours(TypeCours typeCours) throws SQLException {
		this.psDeleteTypeCours.setInt(1, typeCours.getId());
		this.psDeleteTypeCours.executeUpdate();
	}
	
	/*------------*/
	/* HeureCours */
	/*------------*/
	
	/**
	 * @param idAnnee indice de l'année souhaitée
	 * @return une {@link Map} composé de :
	 *         <ul>
	 *         <li>indice en {@link String}, composé de :
	 *         <ol>
	 *         <li>l'indice du {@link TypeCours}</li>
	 *         <li>l'indice du {@link Module}</li>
	 *         </ol>
	 *         </li>
	 *         <li>{@link HeureCours} correspondante</li>
	 *         </ul>
	 */
	public Map<String, HeureCours> getHeureCours(int idAnnee) throws SQLException {
		Map<String, HeureCours> hmHeureCours = new HashMap<>();
		this.psSelectHeureCours.setInt(1, idAnnee);
		ResultSet rs = this.psSelectHeureCours.executeQuery();
		while (rs.next()) {
			hmHeureCours.put(rs.getInt("idTypeCours") + "-" + rs.getInt("idModule"),
					new HeureCours(rs.getInt("idTypeCours"), rs.getInt("idModule"), rs.getDouble("heure"),
							rs.getInt("nbSemaine"), rs.getDouble("hParSemaine"),
							rs.getInt("idAnnee")));
		}
		return hmHeureCours;
	}
	
	/**
	 * méthode pour obtenir les {@link HeureCours} d'un {@link Module}
	 * 
	 * @param idModule identifiant du {@link Module}
	 * @param idAnnee  indice de l'année souhaitée
	 * @return {@link List} des {@link HeureCours} du {@link Module}
	 * @throws SQLException
	 */
	public List<HeureCours> getHeureCoursByModule(int idModule, int idAnnee) throws SQLException {
		List<HeureCours> lstHeureCours = new ArrayList<>();
		this.psSelectHeureCoursByModule.setInt(1, idModule);
		this.psSelectHeureCoursByModule.setInt(2, idAnnee);
		ResultSet rs = this.psSelectHeureCoursByModule.executeQuery();
		while (rs.next()) {
			lstHeureCours.add(new HeureCours(rs.getInt("idTypeCours"), rs.getInt("idModule"), rs.getDouble("heure"),
					rs.getInt("nbSemaine"), rs.getDouble("hParSemaine"), idAnnee));
		}
		return lstHeureCours;
	}
	
	/**
	 * méthode pour ajouter une {@link HeureCours} de la base de données
	 * 
	 * @param heureCours {@link HeureCours} à ajouter
	 * @throws SQLException
	 */
	public void ajouterHeureCours(HeureCours heureCours) throws SQLException {
		this.psInsertHeureCours.setInt(1, heureCours.getIdTypeCours());
		this.psInsertHeureCours.setInt(2, heureCours.getIdModule());
		this.psInsertHeureCours.setDouble(3, heureCours.getHeure());
		this.psInsertHeureCours.setInt(4, heureCours.getNbSemaine());
		this.psInsertHeureCours.setDouble(5, heureCours.gethParSemaine());
		this.psInsertHeureCours.setInt(6, heureCours.getIdAnnee());
		this.psInsertHeureCours.executeUpdate();
	}
	
	/**
	 * méthode pour modifier une {@link HeureCours} de la base de données
	 * 
	 * @param heureCours {@link HeureCours} à modifier
	 * @throws SQLException
	 */
	public void updateHeureCours(HeureCours heureCours) throws SQLException {
		this.psUpdateHeureCours.setDouble(1, heureCours.getHeure());
		this.psUpdateHeureCours.setInt(4, heureCours.getIdTypeCours());
		this.psUpdateHeureCours.setInt(5, heureCours.getIdModule());
		this.psUpdateHeureCours.setInt(2, heureCours.getNbSemaine());
		this.psUpdateHeureCours.setDouble(3, heureCours.gethParSemaine());
		this.psUpdateHeureCours.setInt(6, heureCours.getIdAnnee());
		this.psUpdateHeureCours.executeUpdate();
	}
	
	/**
	 * méthode pour supprimer une {@link HeureCours} de la base de données
	 * 
	 * @param heureCours {@link HeureCours} à supprimer
	 * @throws SQLException
	 */
	public void supprimerHeureCours(HeureCours heureCours) throws SQLException {
		this.psDeleteHeureCours.setInt(1, heureCours.getIdTypeCours());
		this.psDeleteHeureCours.setInt(2, heureCours.getIdModule());
		this.psDeleteHeureCours.executeUpdate();
	}
	
	/*-------*/
	/* annee */
	/*-------*/
	
	/**
	 * méthode pour obtenir la derniere année
	 * 
	 * @return la derniere année
	 * @throws SQLException
	 */
	public int getDerAnnee() throws SQLException {
		ResultSet rs = this.psSelectDerAnnee.executeQuery();
		rs.next();
		return rs.getInt("idAnnee");
		/*
		int ret = 0;
		while (rs.next())
			ret = rs.getInt("idAnnee");
		return ret;
		*/
	}
	
	/**
	 * méthode pour ajouter une année de la base de données
	 * 
	 * @param id    identifiant de l'année à supprimer
	 * @param annee {@link String} représentant l'année exemple : {@code 2022-2023}
	 * @throws SQLException
	 */
	public void ajouterAnnee(int id, String annee) throws SQLException {
		this.psInsertAnnee.setInt(1, id);
		this.psInsertAnnee.setString(2, annee);
		this.psInsertAnnee.executeUpdate();
	}
	
	/**
	 * @param idAnnee indice de l'année souhaitée
	 * @return une {@link Map} composé de :
	 *         <ul>
	 *         <li>indice en {@link Integer}</li>
	 *         <li>l'année correspondante en {@link String}</li>
	 *         </ul>
	 */
	public Map<Integer, String> getAnnee() throws SQLException {
		Map<Integer, String> hmAnnee = new HashMap<>();
		ResultSet rs = this.psSelectAnnee.executeQuery();
		while (rs.next())
			hmAnnee.put(rs.getInt("idAnnee"), rs.getString("annee"));
		return hmAnnee;
	}
	
	public int getIdTypeCoursByNom(String nom) throws SQLException {
		this.psSelectIdTypeCoursByNom.setString(1, nom);
		ResultSet rs = this.psSelectIdTypeCoursByNom.executeQuery();
		rs.next();
		return rs.getInt("idTypeCours");
	}
	
}