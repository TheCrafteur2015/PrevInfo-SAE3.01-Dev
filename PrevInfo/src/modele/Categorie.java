package modele;

public class Categorie {

	private int id;
	private String nom;
	private double hMax;
	private double hMin;
	private int idAnnee;

	public Categorie(int id, String nom, double hMin, double hMax, int idAnnee) {
		this.id = id;
		this.nom = nom;
		this.hMin = hMin;
		this.hMax = hMax;
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

	public int getIdAnnee() {
		return idAnnee;
	}

	public void setIdAnnee(int idAnnee) {
		this.idAnnee = idAnnee;
	}

	
}