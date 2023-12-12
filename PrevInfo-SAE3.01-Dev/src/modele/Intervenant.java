package modele;

public class Intervenant {
	private int id;
	private String prenom;
	private String nom; 
	private String email;
	private double hMin;
	private double hMax;
	private int idAnnee;
	private int idCategorie;

	public Intervenant(int id,String prenom, String nom, String email, double hMin, double hMax, int idAnnee, int idCategorie) {

		this.id = id;
		this.prenom = prenom;
		this.nom = nom;
		this.email = email;
		this.hMin = hMin;
		this.hMax = hMax;
		this.idAnnee = idAnnee;
		this.idCategorie = idCategorie;
	}

	public int getId(){return this.id;}
	public void setId(int id){ this.id = id;}

	public String getPrenom() {return prenom;}
	public void setPrenom(String prenom) {this.prenom = prenom;}

	public String getNom() {return nom;}
	public void setNom(String nom) {this.nom = nom;}

	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}

	public int getIdCategorie(){return this.idCategorie;}
	public void setIdCategorie(int idCategorie){this.idCategorie = idCategorie;}

	public double gethMin() {
		return hMin;
	}

	public void sethMin(double hMin) {
		this.hMin = hMin;
	}

	public double gethMax() {
		return hMax;
	}

	public void sethMax(double hMax) {
		this.hMax = hMax;
	}

	public int getIdAnnee() {
		return idAnnee;
	}

	public void setIdAnnee(int idAnnee) {
		this.idAnnee = idAnnee;
	}

	@Override
	public String toString() {return "Intervenant [prenom=" + prenom + ", nom=" + nom + ", email=" + email + "]";}
	
}
