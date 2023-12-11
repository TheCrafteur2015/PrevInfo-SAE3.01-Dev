package modele;
import java.sql.*;
import java.util.ArrayList;

public class DB {
	private Connection connec;
	private static DB dbInstance;
	private PreparedStatement psSelectP;
	private PreparedStatement psInsertP;
	private PreparedStatement psUpdateP;
	private PreparedStatement psDeleteP;
	/* A COMPLETER ! */

	private DB() {
		try {
			Class.forName("org.postgresql.Driver");
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			connec = DriverManager.getConnection("jdbc:postgresql://woody/ld220835","ld220835","USERA262");
			psSelectP = connec.prepareStatement("SELECT * FROM Produit WHERE np = ?");
			psInsertP = connec.prepareStatement("INSERT INTO Produit Values(?, ?, ?, ?)");
			psUpdateP = connec.prepareStatement("UPDATE Produit SET coul = ? WHERE np = ?");
			psDeleteP = connec.prepareStatement("DELETE FROM Produit WHERE np = ?");
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static DB getInstance() {
		if(dbInstance==null){dbInstance=new DB();}
		return dbInstance;
	}

	public Produit getProduit(int np) throws SQLException{
		Produit p = null;  
		psSelectP.setInt(1,np);
		ResultSet rsProd=psSelectP.executeQuery();
		if(rsProd.next()) 
			p = new Produit(rsProd.getInt("np"),rsProd.getString("lib"),rsProd.getString("coul"),rsProd.getInt("qs"));  
		rsProd.close();
		return p;
	  }
	  
	//Methode a n'utiliser que dans la classe DB
	//Le parametre req doit correspondre a une requete de la forme "SELECT * FROM produit WHERE ..."
	private ArrayList<Produit> getProduits(String req) throws SQLException {
		Statement selectNP=connec.createStatement();
		ArrayList<Produit> listeP=new ArrayList<Produit>();
			
		ResultSet rsP=selectNP.executeQuery(req);
		while(rsP.next()){			
			Produit p = new Produit(rsP.getInt("np"),rsP.getString("lib"),rsP.getString("coul"),rsP.getInt("qs"));
			listeP.add(p);
		  }
		rsP.close(); 
		return listeP;
	}

	public ArrayList<Produit> getProduits() throws SQLException {
		return getProduits("SELECT * FROM Produit");
	}

	public void insertProduit(Produit p) throws SQLException {
		psInsertP.setInt(1, p.getNp());
		psInsertP.setString(2, p.getLib());
		psInsertP.setString(3, p.getCoul());
		psInsertP.setInt(4, p.getQs());
		psInsertP.executeUpdate();
	}

	public void updateProduit(int np, String coul) throws SQLException {
		psUpdateP.setString(1, coul);
		psUpdateP.setInt(2, np);
		psUpdateP.executeUpdate();
	}

	public void deleteProduit(int np) throws SQLException {
		psDeleteP.setInt(1, np);
		psDeleteP.executeUpdate();
	}

	public int getNbProduits() throws SQLException {
		return getProduits().size();
	}
	
} //fin classe DB

