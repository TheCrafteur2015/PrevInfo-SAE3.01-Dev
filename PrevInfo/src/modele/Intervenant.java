package modele;

public class Intervenant {
	private int id;
	private String prenom;
	private String nom; 
	private String email;
	private int idIntervenant;

	public Intervenant(int id,String prenom, String nom, String email, int idIntervenant) {

		this.id = id;
		this.prenom = prenom;
		this.nom = nom;
		this.email = email;
		this.idIntervenant = idIntervenant;
	}

	public int getId(){return this.id;}
	public void setId(int id){ this.id = id;}

	public String getPrenom() {return prenom;}
	public void setPrenom(String prenom) {this.prenom = prenom;}

	public String getNom() {return nom;}
	public void setNom(String nom) {this.nom = nom;}

	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}

	public int getIdIntervenant(){return this.idIntervenant;}
	public void setIdIntervenant(int idIntervenant){this.idIntervenant = idIntervenant;}


	@Override
	public String toString() {return "Intervenant [prenom=" + prenom + ", nom=" + nom + ", email=" + email + "]";}
	
}
