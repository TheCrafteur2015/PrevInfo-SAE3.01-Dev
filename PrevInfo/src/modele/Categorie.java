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
}