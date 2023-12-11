package modele;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

	private PreparedStatement psInsertCategorie;
	private PreparedStatement psInsertIntervenant;
	private PreparedStatement psInsertIntervention;
	private PreparedStatement psInsertModule;
	private PreparedStatement psInsertSemestre;
	private PreparedStatement psInsertHeureCours;

	private PreparedStatement psUpdateCategorie;
	private PreparedStatement psUpdateIntervenant;
	private PreparedStatement psUpdateIntervention;
	private PreparedStatement psUpdateModule;
	private PreparedStatement psUpdateSemestre;
	private PreparedStatement psUpdateTypeCours;
	private PreparedStatement psUpdateHeureCours;


	private DB() {
		try {
			Class.forName("org.postgresql.Driver");
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			connec = DriverManager.getConnection("jdbc:postgresql://woody/ld220835","ld220835","USERA262");
			this.psSelectCategorie    = connec.prepareStatement("SELECT * FROM Categorie WHERE idAnnee = ?");
			this.psSelectIntervenant  = connec.prepareStatement("SELECT * FROM Intervenant WHERE idAnnee = ?");
			this.psSelectIntervention = connec.prepareStatement("SELECT * FROM Intervention WHERE idAnnee = ?");
			this.psSelectModule       = connec.prepareStatement("SELECT * FROM Module WHERE idAnnee = ?");
			this.psSelectSemestre     = connec.prepareStatement("SELECT * FROM Semestre WHERE idAnnee = ?");
			this.psSelectTypeCours    = connec.prepareStatement("SELECT * FROM TypeCours");
			this.psSelectHeureCours   = connec.prepareStatement("SELECT * FROM HeureCours JOIN Module USING(idModule) WHERE Module.idAnnee = ? ");

			this.psInsertCategorie	  = connec.prepareStatement("INSERT INTO Categorie VALUES (?,?,?,?,?)");
			this.psInsertIntervenant  = connec.prepareStatement("INSERT INTO Intervenant VALUES (?,?,?,?,?,?,?,?)");
			this.psInsertIntervention = connec.prepareStatement("INSERT INTO Intervention VALUES (?,?,?,?,?,?)");
			this.psInsertModule		  = connec.prepareStatement("INSERT INTO Module VALUES (?,?,?,?,?)");
			this.psInsertSemestre	  = connec.prepareStatement("INSERT INTO Semestre VALUES (?,?,?,?,?,?)");
			this.psInsertHeureCours   = connec.prepareStatement("INSERT INTO HeureCours VALUES (?,?,?)");

			this.psUpdateCategorie    = connec.prepareStatement("UPDATE Categorie SET nomCategorie = ?, hMaxCategorie = ?, hMinCategorie = ?, idAnnee = ? WHERE idCategorie = ?");
			this.psUpdateIntervenant  = connec.prepareStatement("UPDATE Intervenant SET prenom = ?, nom = ?, email = ?, hMinIntervenant = ?, hMaxIntervenant = ?, idAnnee = ?, idCategorie = ? WHERE idIntervenant = ?");
			this.psUpdateIntervention = connec.prepareStatement("UPDATE Intervention SET idModule = ?, idTypeCours = ?, nbSemainesIntervention = ?, nbGroupe = ?, idAnnee = ? WHERE idIntervention = ?");
			this.psUpdateModule       = connec.prepareStatement("UPDATE Module SET nomModule = ?, nbSemainesModule = ?, idAnnee = ?, idSemestres = ? WHERE idModule = ?");
			this.psUpdateSemestre     = connec.prepareStatement("UPDATE Semestre SET nbGTD = ?, nbGTP ?, nbGCM = ?, nbGAutre = ?, idAnnee = ? WHERE idSemestre = ?");
			this.psUpdateHeureCours   = connec.prepareStatement("UPDATE HeureCours SET heure = ? WHERE idTypeCours = ? AND idModule = ?");
			this.psUpdateTypeCours    = connec.prepareStatement("UPDATE TypeCours SET coeff = ? WHERE nomCours = ?");

		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static DB getInstance() {
		if(dbInstance==null){dbInstance=new DB();}
		return dbInstance;
	}

	public ArrayList<Categorie> getCategories(int idAnnee) throws SQLException{
		ArrayList<Categorie> lstCateg = new ArrayList<>();
		this.psSelectCategorie.setInt(1, idAnnee);
		ResultSet rs = this.psSelectCategorie.executeQuery();
		while (rs.next()) {
			lstCateg.add(new Categorie(rs.getInt("idCategorie"), rs.getString("nomCategorie"), rs.getDouble("hMinCategorie"), rs.getDouble("hMaxCategorie"), idAnnee ));
		}
		return lstCateg;
	}
	
	public void ajouterCategorie(Categorie c) throws SQLException{
		this.psInsertCategorie.setInt(1, c.getId());
		this.psInsertCategorie.setString(2, c.getNom());
		this.psInsertCategorie.setDouble(3, c.gethMin());
		this.psInsertCategorie.setDouble(4, c.gethMax());
		this.psInsertCategorie.setInt(5, c.getIdAnnee());
		this.psInsertCategorie.executeUpdate();
	}

	public void updateCategorie (int id, String nom, double hMin, double hMax, int idAnnee)throws SQLException
	{
		this.psUpdateCategorie.setInt(5,id);
		this.psUpdateCategorie.setString(1, nom);
		this.psUpdateCategorie.setDouble(3, hMin);
		this.psUpdateCategorie.setDouble(2, hMax);
		this.psUpdateCategorie.setInt(4,idAnnee);
		this.psUpdateCategorie.executeUpdate();
		
	}

	public ArrayList<Intervenant> getIntervenants(int idAnnee) throws SQLException {
		ArrayList<Intervenant> lstInter = new ArrayList<>();
		this.psSelectIntervenant.setInt(1, idAnnee);
		ResultSet rs = this.psSelectIntervenant.executeQuery();
		while (rs.next()) {
			lstInter.add(new Intervenant(rs.getInt("idIntervenant"), rs.getString("prenom"), rs.getString("nom"), rs.getString("email"), rs.getDouble("hMinIntervenant"), rs.getDouble("hMaxIntervenant"), idAnnee, rs.getInt("idCategorie")));
		}
		return lstInter;
	}
	public void ajouterIntervenant(Intervenant i) throws SQLException{
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
	
	public void updateIntervenant (int id, String prenom, String nom, String email, double hMin, double hMax, int idAnnee, int idCategorie)throws SQLException
	{
		this.psUpdateIntervenant.setInt(8,id);
		this.psUpdateIntervenant.setString(1, prenom);
		this.psUpdateIntervenant.setString(2, nom);
		this.psUpdateIntervenant.setString(3, email);
		this.psUpdateIntervenant.setDouble(4, hMin);
		this.psUpdateIntervenant.setDouble(5, hMax);
		this.psUpdateIntervenant.setInt(6, idAnnee);
		this.psUpdateIntervenant.setInt(7, idCategorie);
		this.psUpdateIntervenant.executeUpdate();
		
	}

	public ArrayList<Intervention> getInterventions (int idAnnee) throws SQLException{
		ArrayList<Intervention> lstInterventions = new ArrayList<>();
		this.psSelectIntervention.setInt(1, idAnnee);
		ResultSet rs = this.psSelectIntervention.executeQuery();
		while (rs.next()) {
			lstInterventions.add(new Intervention(rs.getInt("idIntervention"), rs.getInt("idModule"), rs.getInt("idTypeCours"),rs.getInt("nbSemainesIntervention"),
			rs.getInt("nbGroupe"),rs.getInt("idAnnee")));
		}
		return lstInterventions;
	}
	
	public void ajouterIntervention(Intervention i ) throws SQLException {
		this.psInsertIntervention.setInt(1, i.getIdIntervenant());
		this.psInsertIntervention.setInt(2, i.getIdModule());
		this.psInsertIntervention.setInt(3, i.getIdTypeCours());
		this.psInsertIntervention.setInt(4, i.getNbSemaines());
		this.psInsertIntervention.setInt(5, i.getNbGroupe());
		this.psInsertIntervention.setInt(6, i.getIdAnnee());
		this.psInsertIntervention.executeUpdate();
	}
	public void updateIntervention (int id, int idModule, int idTypeCours, int nbSemaines, int nbGroupe, int idAnnee)throws SQLException
	{
		this.psUpdateIntervention.setInt(6,id);
		this.psUpdateIntervention.setInt(1, idModule);
		this.psUpdateIntervention.setInt(2, idTypeCours);
		this.psUpdateIntervention.setInt(3, nbSemaines);
		this.psUpdateIntervention.setInt(4, nbGroupe);
		this.psUpdateIntervention.setInt(5, idAnnee);
		this.psUpdateIntervention.executeUpdate();
		
	}

	public ArrayList<Module> getModules(int idAnnee) throws SQLException {
		ArrayList<Module> lstModule = new ArrayList<>();
		this.psSelectModule.setInt(1, idAnnee);
		ResultSet rs = this.psSelectIntervenant.executeQuery();
		while (rs.next()) {
			lstModule.add(new Module(rs.getInt("idModule"), rs.getString("nomModule"), rs.getInt("nbSemainesModule"), idAnnee, rs.getInt("idSemestres")));
		}
		return lstModule;
	}
	public void ajouterModule(Module m) throws SQLException{
		this.psInsertModule.setInt(1, m.getId());
		this.psInsertModule.setString(2, m.getNom());
		this.psInsertModule.setInt(3, m.getNbSemaines());
		this.psInsertModule.setInt(4, m.getIdAnnee());
		this.psInsertModule.setInt(5, m.getIdSemestre());
		this.psInsertModule.executeUpdate();
	}

	public void updateModule(int id, String nom, int nbSemaines, int idAnnee, int idSemestre) throws SQLException {
		this.psUpdateModule.setInt(5, id);
		this.psUpdateModule.setString(1, nom);
		this.psUpdateModule.setInt(2, nbSemaines);
		this.psUpdateModule.setInt(3, idAnnee);
		this.psUpdateModule.setInt(4, idSemestre);
		this.psUpdateModule.executeUpdate();
	}

	public ArrayList<Semestre> getSemestres(int idAnnee) throws SQLException {
		ArrayList<Semestre> lstSemestre = new ArrayList<>();
		this.psSelectModule.setInt(1, idAnnee);
		ResultSet rs = this.psSelectSemestre.executeQuery();
		while (rs.next()) {
			lstSemestre.add(new Semestre(rs.getInt("idSemestre"), rs.getInt("nbGTD"), rs.getInt("nbGTP"), rs.getInt("nbGCM"), rs.getInt("nbGAutre"), idAnnee));
		}
		return lstSemestre;
	}
	public void ajouterSemestre(Semestre s) throws SQLException{
		this.psInsertSemestre.setInt(1, s.getId());
		this.psInsertSemestre.setInt(2, s.getNbGTD());
		this.psInsertSemestre.setInt(3, s.getNbGTP());
		this.psInsertSemestre.setInt(4, s.getNbGCM());
		this.psInsertSemestre.setInt(5, s.getNbGAutre());
		this.psInsertSemestre.setInt(6, s.getIdAnnee());
		this.psInsertSemestre.executeUpdate();
	}
	
	public void updateSemestre(int id, int nbGTD, int nbGTP, int nbGCM, int nbGAutre, int idAnnee) throws SQLException {
		this.psUpdateSemestre.setInt(6, id);
		this.psUpdateSemestre.setInt(1, nbGTD);
		this.psUpdateSemestre.setInt(2, nbGTP);
		this.psUpdateSemestre.setInt(3, nbGCM);
		this.psUpdateSemestre.setInt(4, nbGAutre);
		this.psUpdateSemestre.setInt(5, idAnnee);
		this.psUpdateSemestre.executeUpdate();
	}
	
	public HashMap<String, TypeCours> getTypeCours () throws SQLException{
		HashMap<String, TypeCours> lstTypeCours = new HashMap<>();
		ResultSet rs = this.psSelectTypeCours.executeQuery();
		while (rs.next()) {
			lstTypeCours.put(rs.getString("nomCours"),new TypeCours(rs.getInt("idTypeCours"), rs.getString("nomCours"), rs.getDouble("coefficient")));
		}
		return lstTypeCours;
	}

	public void changerCoeffTypeCours(String nom, int coeff) throws SQLException {
		this.psUpdateTypeCours.setInt(1, coeff);
		this.psUpdateTypeCours.setString(2, nom);
		this.psUpdateTypeCours.executeUpdate();
	}

	public ArrayList<HeureCours> getHeureCours () throws SQLException{
		ArrayList<HeureCours> lstHeureCours = new ArrayList<>();
		ResultSet rs = this.psSelectTypeCours.executeQuery();
		while (rs.next()) {
			lstHeureCours.add(new HeureCours(rs.getInt("idTypeCours"), rs.getInt("idModule"), rs.getDouble("heure")));
		}
		return lstHeureCours;
	
	}
	public void ajouterHeureCours(HeureCours h) throws SQLException{
		this.psInsertHeureCours.setInt		 (1, h.getIdTypeCours());
		this.psInsertHeureCours.setInt   	 (2, h.getIdModule()   );
		this.psInsertHeureCours.setDouble	 (3, h.getHeure()	   );
		this.psInsertHeureCours.executeUpdate();
	}


	  
	
}
