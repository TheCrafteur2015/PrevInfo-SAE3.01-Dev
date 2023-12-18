package modele;

/**
 * TypeCours
 */
public class TypeCours {

	private int id;
	private String nom;
	private double coefficient;

	public TypeCours(int id, String nom, double coefficient) {
		this.id = id;
		this.nom = nom;
		this.coefficient = coefficient;
	}

	public String getNom() { return this.nom; }
	public double getCoefficient() { return this.coefficient; }
	
	public void setNom(String nom)	 {this.nom = nom;}
	// TODO: public void setCoefficient(float coefficient){this.coefficient = coefficient;}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCoefficient(double coefficient) {
		this.coefficient = coefficient;
	}

	@Override
	public String toString() {
		return "TypeCours [id=" + id + ", nom=" + nom + ", coefficient=" + coefficient + "]";
	}

	
	
}