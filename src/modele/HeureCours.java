package modele;

public class HeureCours {

	private int idTypeCours;
	private int idModule;
	private double heure;
	private int nbSemaine;
	private double hParSemaine;
	private int idAnnee;

	public HeureCours(int idTypeCours, int idModule, double heure, int nbSemaine, double hParSemaine, int idAnnee) {
		this.idTypeCours = idTypeCours;
		this.idModule = idModule;
		this.heure = heure;
		this.nbSemaine = nbSemaine;
		this.hParSemaine = hParSemaine;
		this.idAnnee = idAnnee;
	}

	public int getIdTypeCours() {
		return idTypeCours;
	}

	public void setIdTypeCours(int idTypeCours) {
		this.idTypeCours = idTypeCours;
	}

	public int getIdModule() {
		return idModule;
	}

	public void setIdModule(int idModule) {
		this.idModule = idModule;
	}

	public double getHeure() {
		return heure;
	}

	public void setHeure(double heure) {
		this.heure = heure;
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
		return "HeureCours [idTypeCours=" + idTypeCours + ", idModule=" + idModule + ", heure=" + heure + "]";
	}

	public double gethParSemaine() {
		return hParSemaine;
	}

	public void sethParSemaine(double hParSemaine) {
		this.hParSemaine = hParSemaine;
	}

}
