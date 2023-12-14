package modele;

public class Categorie {

	public static int NB_CATEGORIE = 0;
	
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
		NB_CATEGORIE++;
	}	
	
	public Categorie(String nom, double hMin, double hMax, double ratioTp, int idAnnee) {
		this.id = ++NB_CATEGORIE;
		this.nom = nom;
		this.hMin = hMin;
		this.hMax = hMax;
		this.ratioTp = ratioTp;
		this.idAnnee = idAnnee;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public double gethMax() {
		return hMax;
	}

	public void sethMax(double hMax) {
		this.hMax = hMax;
	}

	public double gethMin() {
		return hMin;
	}

	public void sethMin(double hMin) {
		this.hMin = hMin;
	}

	public double getRatioTp() {
		return ratioTp;
	}

	public void setRatioTp(double ratioTp) {
		this.ratioTp = ratioTp;
	}

	public int getIdAnnee() {
		return idAnnee;
	}

	public void setIdAnnee(int idAnnee) {
		this.idAnnee = idAnnee;
	}

	

	@Override
	public String toString() {
		return nom;
	}

}