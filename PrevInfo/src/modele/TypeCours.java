package modele;

/**
 * TypeCours
 */
public class TypeCours {

	private int id;
	private String nom;
	private double coefficient;

	public TypeCours(int id, String nom, double coefficient) {
		this.nom = nom;
		this.coefficient = coefficient;
	}

	public String getNom() { return this.nom; }
	public double getCoefficient() { return this.coefficient; }
	
	public void setNom(String nom)	 {this.nom = nom;}
	public void setCoefficient(float coefficient){this.coefficient = coefficient;}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCoefficient(double coefficient) {
		this.coefficient = coefficient;
	}

	
	
}