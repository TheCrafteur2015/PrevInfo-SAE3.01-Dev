package controleur;

import modele.*;
import vue.*;

public class Controleur {
	
	private ControleurIHM controleurIHM;
	private Modele modele;

	public Controleur(ControleurIHM controleurIHM) {
		this.controleurIHM = controleurIHM;
		this.modele = new Modele();
	}

	public Modele getModele() {
		return this.modele;
	}

	public ControleurIHM getVue() {
		return this.controleurIHM;
	}

	
}
