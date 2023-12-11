package controleur;

import modele.Modele;

public class Controleur {

	private Modele modele;
	public Controleur() {
		this.modele = new Modele(this);
		
	}
	public Modele getModele()
	{
		return this.modele;
	}
}