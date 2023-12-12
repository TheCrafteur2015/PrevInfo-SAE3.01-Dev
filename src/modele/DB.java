package modele;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
 * @since la version
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
			this.psSelectDerAnnee = connec.prepareStatement("SELECT * FROM Annee LIMIT 1");
			this.psSelectNomCateg = connec.prepareStatement("SELECT nomCategorie FROM Categorie WHERE idCategorie = ?");

			this.psInsertCategorie = connec.prepareStatement("INSERT INTO Categorie VALUES (?,?,?,?,?)");
			this.psInsertIntervenant = connec.prepareStatement("INSERT INTO Intervenant VALUES (?,?,?,?,?,?,?,?)");
			this.psInsertIntervention = connec.prepareStatement("INSERT INTO Intervention VALUES (?,?,?,?,?,?)");
			this.psInsertModule = connec.prepareStatement("INSERT INTO Module VALUES (?,?,?,?,?)");
			this.psInsertSemestre = connec.prepareStatement("INSERT INTO Semestre VALUES (?,?,?,?,?,?)");
			this.psInsertHeureCours = connec.prepareStatement("INSERT INTO HeureCours VALUES (?,?,?,?)");
			this.psInsertAnnee = connec.prepareStatement("INSERT INTO Annee VALUES (?)");

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

	/**
	 * @param idAnnee indice de l'année souhaitée
	 * @return une {@link HashMap} liant les {@link Categorie} a leur indices, sous
	 *         forme d'{@link Integer}
	 */
	public HashMap<Integer, Categorie> getCategories(int idAnnee) throws SQLException {
		HashMap<Integer, Categorie> hmCateg = new HashMap<Integer, Categorie>();
		this.psSelectCategorie.setInt(1, idAnnee);
		ResultSet rs = this.psSelectCategorie.executeQuery();
		while (rs.next()) {
			hmCateg.put(rs.getInt("idCategorie"), new Categorie(rs.getInt("idCategorie"), rs.getString("nomCategorie"),
					rs.getDouble("hMinCategorie"), rs.getDouble("hMaxCategorie"), idAnnee));
		}
		return hmCateg;
	}

	public void ajouterCategorie(Categorie c) throws SQLException {
		this.psInsertCategorie.setInt(1, c.getId());
		this.psInsertCategorie.setString(2, c.getNom());
		this.psInsertCategorie.setDouble(3, c.gethMin());
		this.psInsertCategorie.setDouble(4, c.gethMax());
		this.psInsertCategorie.setInt(5, c.getIdAnnee());
		this.psInsertCategorie.executeUpdate();
	}

	public void updateCategorie(Categorie c) throws SQLException {
		this.psUpdateCategorie.setInt(5, c.getId());
		this.psUpdateCategorie.setString(1, c.getNom());
		this.psUpdateCategorie.setDouble(3, c.gethMin());
		this.psUpdateCategorie.setDouble(2, c.gethMax());
		this.psUpdateCategorie.setInt(4, c.getIdAnnee());
		this.psUpdateCategorie.executeUpdate();
	}

	public void supprimerCategorie(Categorie c) throws SQLException {
		this.psDeleteCategorie.setInt(1, c.getId());
		this.psDeleteCategorie.executeUpdate();
	}

	/**
	 * @param idAnnee indice de l'année souhaitée
	 * @return une {@link HashMap} liant les {@link Intervenant} a leur indices,
	 *         sous forme d'{@link Integer}
	 */
	public HashMap<Integer, Intervenant> getIntervenants(int idAnnee) throws SQLException {
		HashMap<Integer, Intervenant> hmInter = new HashMap<Integer, Intervenant>();
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

	public void ajouterIntervenant(Intervenant i) throws SQLException {
		this.psInsertIntervenant.setInt(1, i.getId());
		this.psInsertIntervenant.setString(2, i.getPrenom());
		this.psInsertIntervenant.setString(3, i.getNom());
		this.psInsertIntervenant.setString(4, i.getEmail());
		this.psInsertIntervenant.setDouble(5, i.gethMin());
		this.psInsertIntervenant.setDouble(6, i.gethMax());
		this.psInsertIntervenant.setInt(7, i.getIdAnnee());
		this.psInsertIntervenant.setInt(8, i.getIdCategorie());
		this.psInsertIntervenant.executeUpdate();
	}

	public void updateIntervenant(Intervenant i) throws SQLException {
		this.psUpdateIntervenant.setInt(8, i.getId());
		this.psUpdateIntervenant.setString(1, i.getPrenom());
		this.psUpdateIntervenant.setString(2, i.getNom());
		this.psUpdateIntervenant.setString(3, i.getEmail());
		this.psUpdateIntervenant.setDouble(4, i.gethMin());
		this.psUpdateIntervenant.setDouble(5, i.gethMax());
		this.psUpdateIntervenant.setInt(6, i.getIdCategorie());
		this.psUpdateIntervenant.setInt(7, i.getIdAnnee());
		this.psUpdateIntervenant.executeUpdate();

	}

	public void supprimerIntervenant(Intervenant i) throws SQLException {
		this.psDeleteIntervenant.setInt(1, i.getId());
		this.psDeleteIntervenant.executeUpdate();
	}

	/**
	 * @param idAnnee indice de l'année souhaitée
	 * @return une {@link HashMap} liant les {@link Intervention} a leur indices,
	 *         sous forme d'un {@link String}, comportant l'id de
	 *         l'{@link Intervenant}, du {@link Module} et du {@link TypeCours}
	 */
	public HashMap<String, Intervention> getInterventions(int idAnnee) throws SQLException {
		HashMap<String, Intervention> hmInterventions = new HashMap<String, Intervention>();
		this.psSelectIntervention.setInt(1, idAnnee);
		ResultSet rs = this.psSelectIntervention.executeQuery();
		while (rs.next()) {
			hmInterventions.put(
					rs.getInt("idIntervenant") + "-" + rs.getInt("idModule") + "-" + rs.getInt("idTypeCours"),
					new Intervention(rs.getInt("idIntervenant"), rs.getInt("idModule"), rs.getInt("idTypeCours"),
							rs.getInt("nbSemainesIntervention"),
							rs.getInt("nbGroupe"), rs.getInt("idAnnee")));
		}
		return hmInterventions;
	}

	public void ajouterIntervention(Intervention i) throws SQLException {
		this.psInsertIntervention.setInt(1, i.getIdIntervenant());
		this.psInsertIntervention.setInt(2, i.getIdModule());
		this.psInsertIntervention.setInt(3, i.getIdTypeCours());
		this.psInsertIntervention.setInt(4, i.getNbSemaines());
		this.psInsertIntervention.setInt(5, i.getNbGroupe());
		this.psInsertIntervention.setInt(6, i.getIdAnnee());
		this.psInsertIntervention.executeUpdate();
	}

	public void updateIntervention(Intervention i) throws SQLException {
		this.psUpdateIntervention.setInt(4, i.getIdIntervenant());
		this.psUpdateIntervention.setInt(5, i.getIdModule());
		this.psUpdateIntervention.setInt(6, i.getIdTypeCours());
		this.psUpdateIntervention.setInt(1, i.getNbSemaines());
		this.psUpdateIntervention.setInt(2, i.getNbGroupe());
		this.psUpdateIntervention.setInt(3, i.getIdAnnee());
		this.psUpdateIntervention.executeUpdate();
	}

	public void supprimerIntervention(Intervention i) throws SQLException {
		this.psDeleteIntervention.setInt(1, i.getIdIntervenant());
		this.psDeleteIntervention.setInt(2, i.getIdModule());
		this.psDeleteIntervention.setInt(3, i.getIdTypeCours());
		this.psDeleteIntervention.executeUpdate();
	}

	/**
	 * @param idAnnee indice de l'année souhaitée
	 * @return une {@link HashMap} liant les {@link Module} a leur indices, sous
	 *         forme d'un {@link String}, comportant l'id de l'{@link Intervenant},
	 *         du {@link Module} et du {@link TypeCours}
	 */
	public HashMap<Integer, Module> getModules(int idAnnee) throws SQLException {
		HashMap<Integer, Module> hmModule = new HashMap<Integer, Module>();
		this.psSelectModule.setInt(1, idAnnee);
		ResultSet rs = this.psSelectModule.executeQuery();
		while (rs.next()) {
			hmModule.put(rs.getInt("idModule"),
					new Module(rs.getInt("idModule"), rs.getString("nomModule"), rs.getInt("nbSemainesModule"), idAnnee,
							rs.getInt("idSemestre")));
		}
		return hmModule;
	}

	public void ajouterModule(Module m) throws SQLException {
		this.psInsertModule.setInt(1, m.getId());
		this.psInsertModule.setString(2, m.getNom());
		this.psInsertModule.setInt(3, m.getNbSemaines());
		this.psInsertModule.setInt(4, m.getIdSemestre());
		this.psInsertModule.setInt(5, m.getIdAnnee());
		this.psInsertModule.executeUpdate();
	}

	public void updateModule(Module m) throws SQLException {
		this.psUpdateModule.setInt(5, m.getId());
		this.psUpdateModule.setString(1, m.getNom());
		this.psUpdateModule.setInt(2, m.getNbSemaines());
		this.psUpdateModule.setInt(3, m.getIdAnnee());
		this.psUpdateModule.setInt(4, m.getIdSemestre());
		this.psUpdateModule.executeUpdate();
	}

	public void supprimerModule(Module m) throws SQLException {
		this.psDeleteModule.setInt(1, m.getId());
		this.psDeleteModule.executeUpdate();
	}

	/**
	 * @param idAnnee indice de l'année souhaitée
	 * @return une {@link HashMap} liant les {@link Semestre} a leur indices, sous
	 *         forme d'un {@link String}, comportant l'id de l'{@link Intervenant},
	 *         du {@link Module} et du {@link TypeCours}
	 */
	public HashMap<Integer, Semestre> getSemestres(int idAnnee) throws SQLException {
		HashMap<Integer, Semestre> hmSemestre = new HashMap<Integer, Semestre>();
		this.psSelectSemestre.setInt(1, idAnnee);
		ResultSet rs = this.psSelectSemestre.executeQuery();
		while (rs.next()) {
			hmSemestre.put(rs.getInt("idSemestre"), new Semestre(rs.getInt("idSemestre"), rs.getInt("nbGTD"),
					rs.getInt("nbGTP"), rs.getInt("nbGCM"), rs.getInt("nbGAutre"), idAnnee));
		}
		return hmSemestre;
	}

	public void ajouterSemestre(Semestre s) throws SQLException {
		this.psInsertSemestre.setInt(1, s.getId());
		this.psInsertSemestre.setInt(2, s.getNbGTD());
		this.psInsertSemestre.setInt(3, s.getNbGTP());
		this.psInsertSemestre.setInt(4, s.getNbGCM());
		this.psInsertSemestre.setInt(5, s.getNbGAutre());
		this.psInsertSemestre.setInt(6, s.getIdAnnee());
		this.psInsertSemestre.executeUpdate();
	}

	public void updateSemestre(Semestre s) throws SQLException {
		this.psUpdateSemestre.setInt(6, s.getId());
		this.psUpdateSemestre.setInt(1, s.getNbGTD());
		this.psUpdateSemestre.setInt(2, s.getNbGTP());
		this.psUpdateSemestre.setInt(3, s.getNbGCM());
		this.psUpdateSemestre.setInt(4, s.getNbGAutre());
		this.psUpdateSemestre.setInt(5, s.getIdAnnee());
		this.psUpdateSemestre.executeUpdate();
	}

	public void supprimerSemestre(Semestre s) throws SQLException {
		this.psDeleteSemestre.setInt(1, s.getId());
		this.psDeleteSemestre.executeUpdate();
	}

	/**
	 * 
	 */
	public HashMap<Integer, TypeCours> getTypeCours() throws SQLException {
		HashMap<Integer, TypeCours> hmTypeCours = new HashMap<Integer, TypeCours>();
		ResultSet rs = this.psSelectTypeCours.executeQuery();
		while (rs.next()) {
			hmTypeCours.put(rs.getInt("idTypeCours"),
					new TypeCours(rs.getInt("idTypeCours"), rs.getString("nomCours"), rs.getDouble("coefficient")));
		}
		return hmTypeCours;
	}

	public void updateTypeCours(TypeCours tc) throws SQLException {
		this.psUpdateTypeCours.setDouble(1, tc.getCoefficient());
		this.psUpdateTypeCours.setString(2, tc.getNom());
		this.psUpdateTypeCours.setInt(3, tc.getId());
		this.psUpdateTypeCours.executeUpdate();
	}

	public void supprimerTypeCours(TypeCours tc) throws SQLException {
		this.psDeleteTypeCours.setInt(1, tc.getId());
		this.psDeleteTypeCours.executeUpdate();
	}

	/**
	 * 
	 */
	public HashMap<String, HeureCours> getHeureCours(int idAnnee) throws SQLException {
		HashMap<String, HeureCours> hmHeureCours = new HashMap<String, HeureCours>();
		this.psSelectHeureCours.setInt(1, idAnnee);
		ResultSet rs = this.psSelectHeureCours.executeQuery();
		while (rs.next()) {
			hmHeureCours.put(rs.getInt("idTypeCours") + "-" + rs.getInt("idModule"), new HeureCours(
					rs.getInt("idTypeCours"), rs.getInt("idModule"), rs.getDouble("heure"), rs.getInt("idAnnee")));
		}
		return hmHeureCours;
	}

	public void ajouterHeureCours(HeureCours h) throws SQLException {
		this.psInsertHeureCours.setInt(1, h.getIdTypeCours());
		this.psInsertHeureCours.setInt(2, h.getIdModule());
		this.psInsertHeureCours.setDouble(3, h.getHeure());
		this.psInsertHeureCours.setInt(4, h.getIdAnnee());
		this.psInsertHeureCours.executeUpdate();
	}

	public void updateHeureCours(HeureCours hc) throws SQLException {
		this.psUpdateHeureCours.setDouble(1, hc.getHeure());
		this.psUpdateHeureCours.setInt(2, hc.getIdTypeCours());
		this.psUpdateHeureCours.setInt(3, hc.getIdModule());
		this.psUpdateHeureCours.setInt(4, hc.getIdAnnee());
		this.psUpdateHeureCours.executeUpdate();
	}

	public void supprimerHeureCours(HeureCours hc) throws SQLException {
		this.psDeleteHeureCours.setInt(1, hc.getIdTypeCours());
		this.psDeleteTypeCours.setInt(2, hc.getIdModule());
		this.psDeleteHeureCours.executeUpdate();
	}

	/**
	 * 
	 */
	public int getDerAnnee() throws SQLException {
		int ret = 0;
		ResultSet rs = this.psSelectDerAnnee.executeQuery();
		while (rs.next()) {
			ret = rs.getInt("idAnnee");
		}
		return ret;
	}

	public void ajouterAnnee(String annee) throws SQLException {
		this.psInsertAnnee.setString(1, annee);
		this.psInsertAnnee.executeUpdate();
	}

	/**
	 * 
	 */
	public String getNomCateg(int idCategorie) throws SQLException {
		this.psSelectNomCateg.setInt(1, idCategorie);
		ResultSet rs = this.psSelectNomCateg.executeQuery();
		while (rs.next()) {
			return rs.getString("nomCategorie");
		}
		return "";
	}

}
