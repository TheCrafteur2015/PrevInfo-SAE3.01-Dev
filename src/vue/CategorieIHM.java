package vue;/**
 * CategorieIHM
 */
import javafx.scene.control.Button;
public class CategorieIHM {

	private String nomC;
	private Button btnSup;
	public CategorieIHM(String nomC,Button btnSup)
	{
		this.nomC = nomC;
		this.btnSup = btnSup;
	}
	public String getNomC() {
		return nomC;
	}
	public void setNomC(String nomC) {
		this.nomC = nomC;
	}
	public Button getBtnSup() {
		return btnSup;
	}
	public void setBtnSup(Button btnSup) {
		this.btnSup = btnSup;
	}

	
}