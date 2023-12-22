package modele;

public class Categorie {
	
	public static int nbCategorie = 0;
	
	private int id;
	private String nom;
	private double hMax;
	private double hMin;
	private double ratioTp;
	private int idAnnee;
	
	public Categorie(int id, String nom, double hMin, double hMax, double ratioTp, int idAnnee) {
		this.id = id;
		this.nom = nom;
		this.hMin = hMin;
		this.hMax = hMax;
		this.ratioTp = ratioTp;
		this.idAnnee = idAnnee;
		Categorie.nbCategorie++;
	}
	
	public Categorie(String nom, double hMin, double hMax, double ratioTp, int idAnnee) {
		this.id = ++Categorie.nbCategorie;
		this.nom = nom;
		this.hMin = hMin;
		this.hMax = hMax;
		this.ratioTp = ratioTp;
		this.idAnnee = idAnnee;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public double gethMax() {
		return this.hMax;
	}
	
	public void sethMax(double hMax) {
		this.hMax = hMax;
	}
	
	public double gethMin() {
		return this.hMin;
	}
	
	public void sethMin(double hMin) {
		this.hMin = hMin;
	}
	
	public double getRatioTp() {
		return this.ratioTp;
	}
	
	public void setRatioTp(double ratioTp) {
		this.ratioTp = ratioTp;
	}
	
	public int getIdAnnee() {
		return this.idAnnee;
	}
	
	public void setIdAnnee(int idAnnee) {
		this.idAnnee = idAnnee;
	}
	
	@Override
	public String toString() {
		return this.nom;
	}
	
}