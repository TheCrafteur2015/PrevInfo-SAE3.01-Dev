package modele;


public class Module implements Comparable<Module> {

	public static int NB_MODULE = 0;

	private int id;
	private String nom;
	private int nbSemaines;
	private int idAnnee;
	private int idSemestre;

	/**
	 * @param id
	 * @param nom
	 * @param nbSemaines
	 * @param idAnnee
	 * @param idSemestre
	 */
	public Module(int id, String nom, int nbSemaines, int idAnnee, int idSemestre) {
		this(nom, nbSemaines, idAnnee, idSemestre);
	}

	public Module(String nom, int nbSemaines, int idAnnee, int idSemestre) {
		this.id = ++NB_MODULE;
		this.nom = nom;
		this.nbSemaines = nbSemaines;
		this.idAnnee = idAnnee;
		this.idSemestre = idSemestre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getNbSemaines() {
		return nbSemaines;
	}

	public void setNbSemaines(int nbSemaines) {
		this.nbSemaines = nbSemaines;
	}

	public int getIdSemestre() {
		return idSemestre;
	}

	public void setIdSemestre(int idSemestre) {
		this.idSemestre = idSemestre;
	}

	public int getIdAnnee() {
		return idAnnee;
	}

	public void setIdAnnee(int idAnnee) {
		this.idAnnee = idAnnee;
	}

	@Override
	public String toString() {
		return "Module [id=" + id + ", nom=" + nom + ", nbSemaines=" + nbSemaines + ", idAnnee=" + idAnnee
				+ ", idSemestre=" + idSemestre + "]";
	}

	@Override
	public int compareTo(Module autre)
	{
		return this.nom.compareTo(autre.nom);
	}

}
