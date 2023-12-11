package modele;

public class Intervention {

	private int idIntervenant;
	private int idModule;
	private int idTypeCours;
	private int nbSemaines;
	private int nbGroupe;

	public Intervention(int idIntervenant, int idModule, int idTypeCours, int nbSemaines, int nbGroupe) {
		this.idIntervenant = idIntervenant;
		this.idModule = idModule;
		this.idTypeCours = idTypeCours;
		this.nbSemaines = nbSemaines;
		this.nbGroupe = nbGroupe;
	}

}