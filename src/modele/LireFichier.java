package modele;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

import vue.App;

public class LireFichier {

	private String nomFichier;
	private String identifiant;
	private String motDePasse;
	
	public LireFichier(String nomFichier) {
		this.nomFichier = nomFichier;
		this.identifiant = "";
		this.motDePasse = "";
		this.lireFichier();
	}
	
	private void lireFichier() {
		InputStream ips = this.getClass().getResourceAsStream(this.nomFichier);
		String ligne = null;
		// ouverture de la ressource vue comme flux de données
		try (BufferedReader file = new BufferedReader(new InputStreamReader(ips))) {
			// traitement
			while ((ligne = file.readLine()) != null)
				this.traiterLigne(ligne);
		} catch (Exception exc) {
			App.log(Level.WARNING, "Erreur lors de l'ouverture du ficher des identifiants");
			App.log(Level.SEVERE, exc);
		}
		
	}
	public void traiterLigne(String ligne) {
		if (ligne.startsWith("#"))
			return; // commentaire
		String[] tab = ligne.split(":");
		this.identifiant = tab[0];
		this.motDePasse = tab[1];
	}

	public String getNomFichier() {
		return this.nomFichier;
	}

	public void setNomFichier(String nomFichier) {
		this.nomFichier = nomFichier;
	}

	public String getIdentifiant() {
		return this.identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getMotDePasse() {
		return this.motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	
}
