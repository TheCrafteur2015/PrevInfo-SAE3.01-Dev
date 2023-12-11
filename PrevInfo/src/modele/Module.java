package modele;

public class Module {

	private int id;
	private String nom;
	private int nbSemaines;
	private double hTD;
	private double hTP;
	private double hCM;
	private int idSemestre;

	public Module(int id, String nom, int nbSemaines, double hTD, double hTP, double hCM, int idSemestre) {
		this.id = id;
		this.nom = nom;
		this.nbSemaines = nbSemaines;
		this.hTD = hTD;
		this.hTP = hTP;
		this.hCM = hCM;
		this.idSemestre = idSemestre;
	}


	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public String getNom() { return nom; }
	public void setNom(String nom) { this.nom = nom; }

	public int getNbSemaines() { return nbSemaines; }
	public void setNbSemaines(int nbSemaines) { this.nbSemaines = nbSemaines; }

	public double gethTD() { return hTD; }
	public void sethTD(double hTD) { this.hTD = hTD; }

	public double gethTP() { return hTP; }
	public void sethTP(double hTP) { this.hTP = hTP; }

	public double gethCM() { return hCM; }
	public void sethCM(double hCM) { this.hCM = hCM; }

	public int getIdSemestre() { return idSemestre; }
	public void setIdSemestre(int idSemestre) { this.idSemestre = idSemestre; }

	
	
}
