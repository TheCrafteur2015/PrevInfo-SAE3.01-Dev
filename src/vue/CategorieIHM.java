package vue;/**
 * CategorieIHM
 */
import javafx.scene.control.Button;
public class CategorieIHM {

	private String nom;
	private Button supprimer;
	public CategorieIHM(String nom,Button supprimer)
	{
		this.nom = nom;
		this.supprimer = supprimer;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Button getSupprimer() {
		return supprimer;
	}
	public void setSupprimer(Button supprimer) {
		this.supprimer = supprimer;
	}

	
}