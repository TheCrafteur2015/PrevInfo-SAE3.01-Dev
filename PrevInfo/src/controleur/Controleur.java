package controleur;

import modele.Modele;
import vue.ControlleurIHM;

public class Controleur {
	
	private Modele modele;
	
	public Controleur() {
		this.modele = new Modele(this);
	}
	
	public Modele getModele() {
		return this.modele;
	}
	
	public static void main(String[] args) {
		new ControlleurIHM(args);
		new Controleur();
	}
	
}