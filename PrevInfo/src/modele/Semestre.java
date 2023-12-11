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

	public Semestre(int id, int nbGTD, int nbGTP, int nbGCM, int nbGAutre) {
		this.id = id;
		this.nbGTD = nbGTD;
		this.nbGTP = nbGTP;
		this.nbGCM = nbGCM;
		this.nbGAutre = nbGAutre;

	}

}