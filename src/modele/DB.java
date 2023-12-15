package modele;

import java.sql.*;
import java.util.Map;
import java.util.HashMap;

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
	private Connection connec;
	private static DB dbInstance;
	private PreparedStatement psSelectCategorie;
	private PreparedStatement psSelectIntervenant;
	private PreparedStatement psSelectIntervention;
	private PreparedStatement psSelectModule;
	private PreparedStatement psSelectSemestre;
	private PreparedStatement psSelectTypeCours;
	private PreparedStatement psSelectHeureCours;
	private PreparedStatement psSelectAnnee;
	private PreparedStatement psSelectDerAnnee;
	private PreparedStatement psSelectNomCateg;

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
			e.printStackTrace();
		}

		try {
			connec = DriverManager.getConnection("jdbc:postgresql://woody/ld220835", "ld220835", "USERA262");
			this.psSelectCategorie = connec.prepareStatement("SELECT * FROM Categorie WHERE idAnnee = ?");
			this.psSelectIntervenant = connec.prepareStatement("SELECT * FROM Intervenant WHERE idAnnee = ?");
			this.psSelectIntervention = connec.prepareStatement("SELECT * FROM Intervention WHERE idAnnee = ?");
			this.psSelectModule = connec.prepareStatement("SELECT * FROM Module WHERE idAnnee = ?");
			this.psSelectSemestre = connec.prepareStatement("SELECT * FROM Semestre WHERE idAnnee = ?");
			this.psSelectTypeCours = connec.prepareStatement("SELECT * FROM TypeCours");
			this.psSelectHeureCours = connec
					.prepareStatement("SELECT * FROM HeureCours JOIN Module USING(idModule) WHERE Module.idAnnee = ? ");
			// this.psSelectDerAnnee = connec.prepareStatement("SELECT * FROM Annee LIMIT
			// 1");
			this.psSelectDerAnnee = connec.prepareStatement("SELECT * FROM annee ORDER BY idannee DESC LIMIT 1;");
			// this.psSelectDerAnnee.setFetchDirection(ResultSet.TYPE_SCROLL_SENSITIVE);
			this.psSelectNomCateg = connec.prepareStatement("SELECT nomCategorie FROM Categorie WHERE idCategorie = ?");
			this.psSelectAnnee = connec.prepareStatement("SELECT * FROM Annee");

			this.psInsertCategorie = connec.prepareStatement("INSERT INTO Categorie VALUES (?,?,?,?,?)");
			this.psInsertIntervenant = connec.prepareStatement("INSERT INTO Intervenant VALUES (?,?,?,?,?,?,?,?)");
			this.psInsertIntervention = connec.prepareStatement("INSERT INTO Intervention VALUES (?,?,?,?,?,?)");
			this.psInsertModule = connec.prepareStatement("INSERT INTO Module VALUES (?,?,?,?,?)");
			this.psInsertSemestre = connec.prepareStatement("INSERT INTO Semestre VALUES (?,?,?,?,?,?)");
			this.psInsertHeureCours = connec.prepareStatement("INSERT INTO HeureCours VALUES (?,?,?,?)");
			this.psInsertAnnee = connec.prepareStatement("INSERT INTO Annee VALUES (?, ?)");

			this.psUpdateCategorie = connec.prepareStatement(
					"UPDATE Categorie SET nomCategorie = ?, hMaxCategorie = ?, hMinCategorie = ? WHERE idAnnee = ? AND idCategorie = ?");
			this.psUpdateIntervenant = connec.prepareStatement(
					"UPDATE Intervenant SET prenom = ?, nom = ?, email = ?, hMinIntervenant = ?, hMaxIntervenant = ?, idCategorie = ? WHERE idAnnee = ? AND idIntervenant = ?");
			this.psUpdateIntervention = connec.prepareStatement(
					"UPDATE Intervention SET nbSemainesIntervention = ?, nbGroupe = ? WHERE idAnnee = ? AND idIntervenant = ? AND idModule = ? AND idTypeCours = ?");
			this.psUpdateModule = connec.prepareStatement(
					"UPDATE Module SET nomModule = ?, nbSemainesModule = ?, idSemestre = ? WHERE idAnnee = ? AND idModule = ?");
			this.psUpdateSemestre = connec.prepareStatement(
					"UPDATE Semestre SET nbGTD = ?, nbGTP = ?, nbGCM = ?, nbGAutre = ? WHERE idAnnee = ? AND idSemestre = ?");
			this.psUpdateHeureCours = connec.prepareStatement(
					"UPDATE HeureCours SET heure = ? WHERE idTypeCours = ? AND idModule = ? AND idAnnee = ?");
			this.psUpdateTypeCours = connec
					.prepareStatement("UPDATE TypeCours SET coefficient = ?, nomCours = ? WHERE idTypeCours = ?");

			this.psDeleteCategorie = connec.prepareStatement("DELETE FROM Categorie WHERE idCategorie = ?");
			this.psDeleteIntervenant = connec.prepareStatement("DELETE FROM Intervenant WHERE idIntervenant = ?");
			this.psDeleteIntervention = connec.prepareStatement(
					"DELETE FROM Intervention WHERE idIntervenant = ? AND idModule = ? AND idTypeCours = ?");
			this.psDeleteModule = connec.prepareStatement("DELETE FROM Module WHERE idModule = ?");
			this.psDeleteSemestre = connec.prepareStatement("DELETE FROM Semestre WHERE idSemestre = ?");
			this.psDeleteHeureCours = connec
					.prepareStatement("DELETE FROM HeureCours WHERE idTypeCours = ? and idModule = ?");
			this.psDeleteTypeCours = connec.prepareStatement("DELETE FROM TypeCours WHERE idTypeCours = ?");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static DB getInstance() {
		if (dbInstance == null) {
			dbInstance = new DB();
		}
		return dbInstance;
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
					rs.getDouble("hMinCategorie"), rs.getDouble("hMaxCategorie"), idAnnee));
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
		this.psInsertCategorie.setInt(5, categorie.getIdAnnee());
		this.psInsertCategorie.executeUpdate();
	}

	/**
	 * méthode pour modifier une {@link Categorie} de la base de données
	 * 
	 * @param categorie {@link Categorie} à modifier
	 * @throws SQLException
	 */
	public void updateCategorie(Categorie categorie) throws SQLException {
		this.psUpdateCategorie.setInt(5, categorie.getId());
		this.psUpdateCategorie.setString(1, categorie.getNom());
		this.psUpdateCategorie.setDouble(3, categorie.gethMin());
		this.psUpdateCategorie.setDouble(2, categorie.gethMax());
		this.psUpdateCategorie.setInt(4, categorie.getIdAnnee());
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
	 * @param idCategorie
	 * @return
	 * @throws SQLException
	 */
	public String getNomCateg(int idCategorie) throws SQLException {
		this.psSelectNomCateg.setInt(1, idCategorie);
		ResultSet rs = this.psSelectNomCateg.executeQuery();
		while (rs.next()) {
			return rs.getString("nomCategorie");
		}
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
	 *         <li>indice en {@link String}, composé de :
	 *         <ol>
	 *         <li>l'indice de l'{@link Intervenant}</li>
	 *         <li>l'indice du {@link Module}</li>
	 *         <li>l'indice du {@link TypeCours}</li>
	 *         </ol>
	 *         </li>
	 *         <li>{@link Intervention} correspondante</li>
	 *         </ul>
	 */
	public Map<String, Intervention> getInterventions(int idAnnee) throws SQLException {
		Map<String, Intervention> hmInterventions = new HashMap<>();
		this.psSelectIntervention.setInt(1, idAnnee);
		ResultSet rs = this.psSelectIntervention.executeQuery();
		while (rs.next()) {
			hmInterventions
					.put(rs.getInt("idIntervenant") + "-" + rs.getInt("idModule") + "-" + rs.getInt("idTypeCours"),
							new Intervention(rs.getInt("idIntervenant"), rs.getInt("idModule"),
									rs.getInt("idTypeCours"), rs.getInt("nbSemainesIntervention"),
									rs.getInt("nbGroupe"), rs.getInt("idAnnee")));
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
		this.psInsertIntervention.setInt(1, intervention.getIdIntervenant());
		this.psInsertIntervention.setInt(2, intervention.getIdModule());
		this.psInsertIntervention.setInt(3, intervention.getIdTypeCours());
		this.psInsertIntervention.setInt(4, intervention.getNbSemaines());
		this.psInsertIntervention.setInt(5, intervention.getNbGroupe());
		this.psInsertIntervention.setInt(6, intervention.getIdAnnee());
		this.psInsertIntervention.executeUpdate();
	}

	/**
	 * méthode pour modifier une {@link Intervention} de la base de données
	 * 
	 * @param intervention {@link Intervention} à modifier
	 * @throws SQLException
	 */
	public void updateIntervention(Intervention intervention) throws SQLException {
		this.psUpdateIntervention.setInt(4, intervention.getIdIntervenant());
		this.psUpdateIntervention.setInt(5, intervention.getIdModule());
		this.psUpdateIntervention.setInt(6, intervention.getIdTypeCours());
		this.psUpdateIntervention.setInt(1, intervention.getNbSemaines());
		this.psUpdateIntervention.setInt(2, intervention.getNbGroupe());
		this.psUpdateIntervention.setInt(3, intervention.getIdAnnee());
		this.psUpdateIntervention.executeUpdate();
	}

	/**
	 * méthode pour supprimer une {@link Intervention} de la base de données
	 * 
	 * @param intervention {@link Intervention} à supprimer
	 * @throws SQLException
	 */
	public void supprimerIntervention(Intervention intervention) throws SQLException {
		this.psDeleteIntervention.setInt(1, intervention.getIdIntervenant());
		this.psDeleteIntervention.setInt(2, intervention.getIdModule());
		this.psDeleteIntervention.setInt(3, intervention.getIdTypeCours());
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
					new Module(rs.getInt("idModule"), rs.getString("nomModule"), rs.getString("code"), idAnnee,
							rs.getInt("idSemestre")));
		}
		return hmModule;
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
		this.psInsertModule.setInt(5, module.getIdSemestre());
		this.psInsertModule.setInt(4, module.getIdAnnee());
		this.psInsertModule.executeUpdate();
	}

	/**
	 * méthode pour modifier une {@link Module} de la base de données
	 * 
	 * @param module {@link Module} à modifier
	 * @throws SQLException
	 */
	public void updateModule(Module module) throws SQLException {
		this.psUpdateModule.setInt(5, module.getId());
		this.psUpdateModule.setString(1, module.getNom());
		this.psUpdateModule.setString(2, module.getCode());
		this.psUpdateModule.setInt(3, module.getIdAnnee());
		this.psUpdateModule.setInt(4, module.getIdSemestre());
		this.psUpdateModule.executeUpdate();
	}

	/**
	 * méthode pour supprimer une {@link Module} de la base de données
	 * 
	 * @param module {@link Module} à supprimer
	 * @throws SQLException
	 */
	public void supprimerModule(Module module) throws SQLException {
		this.psDeleteModule.setInt(1, module.getId());
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
//			display(rs);
			hmSemestre.put(rs.getInt("idSemestre"),
					new Semestre(rs.getInt("idSemestre"), rs.getInt("nbGTD"), rs.getInt("nbGTP"), rs.getInt("nbGCM"),
							rs.getInt("nbsemaine"), idAnnee));
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
		this.psInsertSemestre.setInt(5, semestre.getNbGAutre());
		this.psInsertSemestre.setInt(6, semestre.getIdAnnee());
		this.psInsertSemestre.executeUpdate();
	}

	/**
	 * méthode pour modifier une {@link Semestre} de la base de données
	 * 
	 * @param semestre {@link Semestre} à modifier
	 * @throws SQLException
	 */
	public void updateSemestre(Semestre semestre) throws SQLException {
		this.psUpdateSemestre.setInt(6, semestre.getId());
		this.psUpdateSemestre.setInt(1, semestre.getNbGTD());
		this.psUpdateSemestre.setInt(2, semestre.getNbGTP());
		this.psUpdateSemestre.setInt(3, semestre.getNbGCM());
		this.psUpdateSemestre.setInt(4, semestre.getNbGAutre());
		this.psUpdateSemestre.setInt(5, semestre.getIdAnnee());
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
	 * @param semestre {@link TypeCours} à modifier
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
	 * @param semestre {@link TypeCours} à supprimer
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
							rs.getInt("idAnnee")));
		}
		return hmHeureCours;
	}

	/**
	 * méthode pour ajouter une {@link HeureCours} de la base de données
	 * 
	 * @param semestre {@link HeureCours} à ajouter
	 * @throws SQLException
	 */
	public void ajouterHeureCours(HeureCours heureCours) throws SQLException {
		this.psInsertHeureCours.setInt(1, heureCours.getIdTypeCours());
		this.psInsertHeureCours.setInt(2, heureCours.getIdModule());
		this.psInsertHeureCours.setDouble(3, heureCours.getHeure());
		this.psInsertHeureCours.setInt(4, heureCours.getIdAnnee());
		this.psInsertHeureCours.executeUpdate();
	}

	/**
	 * méthode pour modifier une {@link HeureCours} de la base de données
	 * 
	 * @param semestre {@link HeureCours} à modifier
	 * @throws SQLException
	 */
	public void updateHeureCours(HeureCours heureCours) throws SQLException {
		this.psUpdateHeureCours.setDouble(1, heureCours.getHeure());
		this.psUpdateHeureCours.setInt(2, heureCours.getIdTypeCours());
		this.psUpdateHeureCours.setInt(3, heureCours.getIdModule());
		this.psUpdateHeureCours.setInt(4, heureCours.getIdAnnee());
		this.psUpdateHeureCours.executeUpdate();
	}

	/**
	 * méthode pour supprimer une {@link HeureCours} de la base de données
	 * 
	 * @param semestre {@link HeureCours} à supprimer
	 * @throws SQLException
	 */
	public void supprimerHeureCours(HeureCours heureCours) throws SQLException {
		this.psDeleteHeureCours.setInt(1, heureCours.getIdTypeCours());
		this.psDeleteTypeCours.setInt(2, heureCours.getIdModule());
		this.psDeleteHeureCours.executeUpdate();
	}

	/*-------*/
	/* annee */
	/*-------*/

	public int getDerAnnee() throws SQLException {
		 int ret = 0;
		ResultSet rs = this.psSelectDerAnnee.executeQuery();
//		rs.next();
//		return rs.getInt("idAnnee");
		// ResultSetMetaData rsmd = rs.getMetaData();
		// rs.last();
		// rs.previous();
		
		while (rs.next())
			ret = rs.getInt("idAnnee");
		 return ret;
	}

	/**
	 * méthode pour ajouter une année de la base de données
	 * 
	 * @param semestre année à supprimer
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
		while (rs.next()) {
			hmAnnee.put(rs.getInt("idAnnee"), rs.getString("annee"));
		}
		return hmAnnee;
	}
	
	public static void display(ResultSet set) throws SQLException {
		ResultSetMetaData rsmd = set.getMetaData();
		System.out.println("Column count: " + rsmd.getColumnCount());
		for (int i = 1; i <= rsmd.getColumnCount(); i++)
			System.out.print(rsmd.getColumnName(i) + " - ");
		System.out.println();
		for (int i = 1; i <= rsmd.getColumnCount(); i++)
			System.out.print(set.getString(i) + ", ");
		System.out.println();
	}

}