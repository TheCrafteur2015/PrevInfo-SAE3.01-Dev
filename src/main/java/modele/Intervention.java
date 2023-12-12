package modele;

public class Intervention {

	private int idIntervenant;
	private int idModule;
	private int idTypeCours;
	private int nbSemaines;
	private int nbGroupe;
	private int idAnnee;

	public Intervention(int idIntervenant, int idModule, int idTypeCours, int nbSemaines, int nbGroupe, int idAnnee) {
		this.idIntervenant = idIntervenant;
		this.idModule = idModule;
		this.idTypeCours = idTypeCours;
		this.nbSemaines = nbSemaines;
		this.nbGroupe = nbGroupe;
		this.idAnnee = idAnnee;
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

	public int getNbSemaines() {
		return nbSemaines;
	}

	public void setNbSemaines(int nbSemaines) {
		this.nbSemaines = nbSemaines;
	}

	public int getNbGroupe() {
		return nbGroupe;
	}

	public void setNbGroupe(int nbGroupe) {
		this.nbGroupe = nbGroupe;
	}

	public int getIdAnnee() {
		return idAnnee;
	}

	public void setIdAnnee(int idAnnee) {
		this.idAnnee = idAnnee;
	}

	@Override
	public String toString() {
		return "Intervention [idIntervenant=" + idIntervenant + ", idModule=" + idModule + ", idTypeCours="
				+ idTypeCours + ", nbSemaines=" + nbSemaines + ", nbGroupe=" + nbGroupe + ", idAnnee=" + idAnnee + "]";
	}

	
	
}