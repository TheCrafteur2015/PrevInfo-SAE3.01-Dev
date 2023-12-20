package modele;

import java.util.Comparator;

public class Module implements Comparable<Module> {

	public static int nbModule = 0;

	private int id;
	private String nom;
	private String code;
	private boolean valid;
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
	public Module(int id, String nom, String code, boolean valid, int idTypeModule, int idAnnee, int idSemestre) {
		this.id = id;
		this.nom = nom;
		this.code = code;
		this.valid = valid;
		this.idTypeModule = idTypeModule;
		this.idAnnee = idAnnee;
		this.idSemestre = idSemestre;
		Module.nbModule++;
	}

	public Module(String nom, String code, boolean valid, int idTypeModule, int idAnnee, int idSemestre) {
		this.id = ++Module.nbModule;
		this.nom = nom;
		this.code = code;
		this.valid = valid;
		this.idTypeModule = idTypeModule;
		this.idAnnee = idAnnee;
		this.idSemestre = idSemestre;
	}


	public int getIdTypeModule() {
		return this.idTypeModule;
	}

	public void setIdTypeModule(int idTypeModule) {
		this.idTypeModule = idTypeModule;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getIdSemestre() {
		return this.idSemestre;
	}

	public void setIdSemestre(int idSemestre) {
		this.idSemestre = idSemestre;
	}

	public int getIdAnnee() {
		return this.idAnnee;
	}

	public void setIdAnnee(int idAnnee) {
		this.idAnnee = idAnnee;
	}

	

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	@Override
	public String toString() {
		return this.code + "_" + this.nom;
	}


	@Override
	public int compareTo(Module autre) {
		return this.code.compareTo(autre.code);
	}
	
	public class ModuleComparator implements Comparator<Module>{
		@Override
		public int compare(Module o1, Module o2) {
			return o1.idSemestre - o2.idSemestre;
		}
	}

}
