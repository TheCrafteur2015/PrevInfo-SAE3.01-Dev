package modele;

public class HeureCours {

	private int idTypeCours;
	private int idModule;
	private double heure;

	
	public HeureCours(int idTypeCours, int idModule, double heure) {
		this.idTypeCours = idTypeCours;
		this.idModule = idModule;
		this.heure = heure;
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

	
	
}
