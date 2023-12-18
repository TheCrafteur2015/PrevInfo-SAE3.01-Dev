package modele;

/**
 * Semestre
 */
public class Semestre {

	public static int nbSemestre = 0;

	private int id;
	private int nbGTD;
	private int nbGTP;
	private int nbGCM;
	private int nbSemaine;
	private int idAnnee;

	public Semestre(int id, int nbGTD, int nbGTP, int nbGCM, int nbSemaine, int idAnnee) {
		this.id = id;
		this.nbGTD = nbGTD;
		this.nbGTP = nbGTP;
		this.nbGCM = nbGCM;
		this.nbSemaine = nbSemaine;
		this.idAnnee = idAnnee;
		Semestre.nbSemestre++;
	}

	public Semestre(int nbGTD, int nbGTP, int nbGCM, int nbSemaine, int idAnnee) {
		this.id = ++Semestre.nbSemestre;
		this.nbGTD = nbGTD;
		this.nbGTP = nbGTP;
		this.nbGCM = nbGCM;
		this.nbSemaine = nbSemaine;
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

	public int getNbSemaine() {
		return nbSemaine;
	}

	public void setNbSemaine(int nbSemaine) {
		this.nbSemaine = nbSemaine;
	}

	public int getIdAnnee() {
		return idAnnee;
	}

	public void setIdAnnee(int idAnnee) {
		this.idAnnee = idAnnee;
	}

	@Override
	public String toString() {
		return "Semestre [id=" + id + ", nbGTD=" + nbGTD + ", nbGTP=" + nbGTP + ", nbGCM=" + nbGCM + ", nbSemaine="
				+ nbSemaine + ", idAnnee=" + idAnnee + "]";
	}

	

}