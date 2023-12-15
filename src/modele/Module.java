package modele;


public class Module implements Comparable<Module> {

	public static int NB_MODULE = 0;

	private int id;
	private String nom;
	private String code;
	private int idTypeModule;
	private int idAnnee;
	private int idSemestre;

	/**
	 * @param id
	 * @param nom
	 * @param code
	 * @param idAnnee
	 * @param idSemestre
	 */
	public Module(int id, String nom, String code, int idTypeModule, int idAnnee, int idSemestre) {
		this.id = id;
		this.nom = nom;
		this.code = code;
		this.idTypeModule = idTypeModule;
		this.idAnnee = idAnnee;
		this.idSemestre = idSemestre;
		NB_MODULE++;
	}

	public Module(String nom, String code, int idTypeModule, int idAnnee, int idSemestre) {
		this.id = ++NB_MODULE;
		this.nom = nom;
		this.code = code;
		this.idTypeModule = idTypeModule;
		this.idAnnee = idAnnee;
		this.idSemestre = idSemestre;
	}


	public int getIdTypeModule() {
		return idTypeModule;
	}

	public void setIdTypeModule(int idTypeModule) {
		this.idTypeModule = idTypeModule;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
		return code+"_"+nom;
	}

	@Override
	public int compareTo(Module autre)
	{
		return this.nom.compareTo(autre.nom);
	}

}
