package modele;

import java.sql.*;
import java.util.ArrayList;
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
	
	public ArrayList<TypeCours> getTypeCours () throws SQLException{
		ArrayList<TypeCours> lstTypeCours = new ArrayList<>();
		ResultSet rs = this.psSelectTypeCours.executeQuery();
		while (rs.next()) {
			lstTypeCours.add(new TypeCours(rs.getInt("idTypeCours"), rs.getString("nomCours"), rs.getDouble("coefficient")));
		}
		return lstTypeCours;
	}
	  
	
}
