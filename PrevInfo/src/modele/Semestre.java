package modele;

/**
 * Semestre
 */
public class Semestre {
	private int idIntervenant;
	private int idModule;
	private int idTypeCours;
	private int nbSemainesIntervention;
	private int nbGroupe;
	public Semestre(int idIntervenant, int idModule, int idTypeCours, int nbSemainesIntervention, int nbGroupe) {
		this.idIntervenant = idIntervenant;
		this.idModule = idModule;
		this.idTypeCours = idTypeCours;
		this.nbSemainesIntervention = nbSemainesIntervention;
		this.nbGroupe = nbGroupe;
	}
	public int getIdIntervenant() {
		return idIntervenant;
	}
	public void setIdIntervenant(int idIntervenant) {
		this.idIntervenant = idIntervenant;
	}
	public int getIdModule() {
		return idModule;
	}
	public void setIdModule(int idModule) {
		this.idModule = idModule;
	}
	public int getIdTypeCours() {
		return idTypeCours;
	}
	public void setIdTypeCours(int idTypeCours) {
		this.idTypeCours = idTypeCours;
	}
	public int getNbSemainesIntervention() {
		return nbSemainesIntervention;
	}
	public void setNbSemainesIntervention(int nbSemainesIntervention) {
		this.nbSemainesIntervention = nbSemainesIntervention;
	}
	public int getNbGroupe() {
		return nbGroupe;
	}
	public void setNbGroupe(int nbGroupe) {
		this.nbGroupe = nbGroupe;
	}
	@Override
	public String toString() {
		return "Semestre [idIntervenant=" + idIntervenant + ", idModule=" + idModule + ", idTypeCours=" + idTypeCours
				+ ", nbSemainesIntervention=" + nbSemainesIntervention + ", nbGroupe=" + nbGroupe + "]";
	}
	
}