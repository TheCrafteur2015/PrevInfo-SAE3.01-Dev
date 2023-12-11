package modele;

/**
 * Semestre
 */
public class Semestre {

	private int id;
	private int nbGTD;
	private int nbGTP;
	private int nbGCM;
	private int nbGAutre;
	private int idAnnee;

	public Semestre(int id, int nbGTD, int nbGTP, int nbGCM, int nbGAutre, int idAnnee) {
		this.id = id;
		this.nbGTD = nbGTD;
		this.nbGTP = nbGTP;
		this.nbGCM = nbGCM;
		this.nbGAutre = nbGAutre;
		this.idAnnee = idAnnee;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNbGTD() {
		return nbGTD;
	}

	public void setNbGTD(int nbGTD) {
		this.nbGTD = nbGTD;
	}

	public int getNbGTP() {
		return nbGTP;
	}

	public void setNbGTP(int nbGTP) {
		this.nbGTP = nbGTP;
	}

	public int getNbGCM() {
		return nbGCM;
	}

	public void setNbGCM(int nbGCM) {
		this.nbGCM = nbGCM;
	}

	public int getNbGAutre() {
		return nbGAutre;
	}

	public void setNbGAutre(int nbGAutre) {
		this.nbGAutre = nbGAutre;
	}

	public int getIdAnnee() {
		return idAnnee;
	}

	public void setIdAnnee(int idAnnee) {
		this.idAnnee = idAnnee;
	}

	

}