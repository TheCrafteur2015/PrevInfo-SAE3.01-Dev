package modele;

public class Categorie {

	private int id;
	private String nom;
	private double hMax;
	private double hMin;

	public Categorie(int id, String nom, double hMin, double hMax) {
		this.id = id;
		this.nom = nom;
		this.hMin = hMin;
		this.hMax = hMax;
	}
}