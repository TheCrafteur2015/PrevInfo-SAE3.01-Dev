package modele;

/**
 * Semestre
 */
public class Semestre {

public static int NB_SEMESTRE = 0;

	private int id;
	private int nbGTD;
	private int nbGTP;
	private int nbGCM;
	private int nbGAutre;
	private int idAnnee;

	public Semestre(int id, int nbGTD, int nbGTP, int nbGCM, int nbGAutre, int idAnnee) {
		this(nbGTD, nbGTP, nbGCM, nbGAutre, idAnnee);
	}

	public Semestre(int nbGTD, int nbGTP, int nbGCM, int nbGAutre, int idAnnee) {
		this.id = ++NB_SEMESTRE;
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

	@Override
	public String toString() {
		return "Semestre [id=" + id + ", nbGTD=" + nbGTD + ", nbGTP=" + nbGTP + ", nbGCM=" + nbGCM + ", nbGAutre="
				+ nbGAutre + ", idAnnee=" + idAnnee + "]";
	}

	

}