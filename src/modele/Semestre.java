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
	private String couleur;

	private int idAnnee;

	public Semestre(int id, int nbGTD, int nbGTP, int nbGCM, int nbSemaine, String couleur, int idAnnee) {
		this.id = id;
		this.nbGTD = nbGTD;
		this.nbGTP = nbGTP;
		this.nbGCM = nbGCM;
		this.nbSemaine = nbSemaine;
		this.couleur = couleur;
		this.idAnnee = idAnnee;
		Semestre.nbSemestre++;
	}

	public Semestre(int nbGTD, int nbGTP, int nbGCM, int nbSemaine, String couleur, int idAnnee) {
		this.id = ++Semestre.nbSemestre;
		this.nbGTD = nbGTD;
		this.nbGTP = nbGTP;
		this.nbGCM = nbGCM;
		this.nbSemaine = nbSemaine;
		this.couleur = couleur;
		this.idAnnee = idAnnee;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNbGTD() {
		return this.nbGTD;
	}

	public void setNbGTD(int nbGTD) {
		this.nbGTD = nbGTD;
	}

	public int getNbGTP() {
		return this.nbGTP;
	}

	public void setNbGTP(int nbGTP) {
		this.nbGTP = nbGTP;
	}

	public int getNbGCM() {
		return this.nbGCM;
	}

	public void setNbGCM(int nbGCM) {
		this.nbGCM = nbGCM;
	}

	public int getNbSemaine() {
		return this.nbSemaine;
	}

	public void setNbSemaine(int nbSemaine) {
		this.nbSemaine = nbSemaine;
	}

	public int getIdAnnee() {
		return this.idAnnee;
	}

	public void setIdAnnee(int idAnnee) {
		this.idAnnee = idAnnee;
	}
	
	@Override
	public String toString() {
		String format = "Semestre [id=%1$d, nbGTD=%2$d, nbGTP=%3$d, nbGCM=%4$d, nbSemaine=%5$d, idAnnee=%6$d]";
		return String.format(format, this.id, this.nbGTD, this.nbGTP, this.nbGCM, this.nbSemaine, this.idAnnee);
		// TODO return "Semestre [id=" + id + ", nbGTD=" + nbGTD + ", nbGTP=" + nbGTP + ", nbGCM=" + nbGCM + ", nbSemaine=" + nbSemaine + ", idAnnee=" + idAnnee + "]";
	}
	
	public String getCouleur() {
		return this.couleur;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}
	
}