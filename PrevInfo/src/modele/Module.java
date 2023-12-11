package modele;

public class Module {

	private int id;
	private String nom;
	private int nbSemaines;
	private int idAnnee;
	private int idSemestre;

	public Module(int id, String nom, int nbSemaines, int idAnnee, int idSemestre) {
		this.id = id;
		this.nom = nom;
		this.nbSemaines = nbSemaines;
		this.idAnnee = idAnnee;
		this.idSemestre = idSemestre;
	}


	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public String getNom() { return nom; }
	public void setNom(String nom) { this.nom = nom; }

	public int getNbSemaines() { return nbSemaines; }
	public void setNbSemaines(int nbSemaines) { this.nbSemaines = nbSemaines; }

	public int getIdSemestre() { return idSemestre; }
	public void setIdSemestre(int idSemestre) { this.idSemestre = idSemestre; }


	public int getIdAnnee() {
		return idAnnee;
	}


	public void setIdAnnee(int idAnnee) {
		this.idAnnee = idAnnee;
	}

	

	
	
}
