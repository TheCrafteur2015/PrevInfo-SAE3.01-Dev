package controleur;

import modele.*;
import vue.*;

public class Controleur {

	private static Controleur controleurInstance;

	private ControleurIHM controleurIHM;
	private Modele modele;

	public Controleur(ControleurIHM controleurIHM) {
		this.controleurIHM = controleurIHM;
		this.modele = new Modele(this);
	}

	public static Controleur getInstance(ControleurIHM controleurIHM) {
		if (controleurInstance == null) {
			controleurInstance = new Controleur(controleurIHM);
		}
		return controleurInstance;
	}

	public Modele getModele() {
		return this.modele;
	}

	public ControleurIHM getVue() {
		return this.controleurIHM;
	}

}
