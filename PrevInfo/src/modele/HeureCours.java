package modele;

public class HeureCours {

	private int idTypeCours;
	private int idModule;
	private double heure;
	private int idAnnee;

	
	public HeureCours(int idTypeCours, int idModule, double heure, int idAnnee) {
		this.idTypeCours = idTypeCours;
		this.idModule = idModule;
		this.heure = heure;
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
	
}
