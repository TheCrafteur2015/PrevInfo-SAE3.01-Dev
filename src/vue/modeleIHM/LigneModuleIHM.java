package vue.modeleIHM;

import javafx.scene.control.Button;

public class LigneModuleIHM {
	
	private Integer id;
	private Button info;
	private String validation;
	private String code;
	private String nom;
	private String cm;
	private String td;
	private String tp;
	private String reh;
	private String tut;
	private String sae;
	private String hp;
	private Button supprimer;


	public LigneModuleIHM(Integer id, Button info, String validation, String code, String nom, String cm, String td, String tp, String reh, String tut,
			String sae, String hp, Button supprimer) {
		this.id = id;
		this.info = info;
		this.validation = validation;
		this.code = code;
		this.nom = nom;
		this.cm = cm;
		this.td = td;
		this.tp = tp;
		this.reh = reh;
		this.tut = tut;
		this.sae = sae;
		this.hp = hp;
		this.supprimer = supprimer;
	}


	public Integer getId() {
		return this.id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Button getInfo() {
		return this.info;
	}


	public void setInfo(Button info) {
		this.info = info;
	}


	

	public String getNom() {
		return this.nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getCm() {
		return this.cm;
	}


	public void setCm(String cm) {
		this.cm = cm;
	}


	public String getTd() {
		return this.td;
	}


	public void setTd(String td) {
		this.td = td;
	}


	public String getTp() {
		return this.tp;
	}


	public void setTp(String tp) {
		this.tp = tp;
	}


	public String getReh() {
		return this.reh;
	}


	public void setReh(String reh) {
		this.reh = reh;
	}


	public String getTut() {
		return this.tut;
	}


	public void setTut(String tut) {
		this.tut = tut;
	}


	public String getSae() {
		return this.sae;
	}


	public void setSae(String sae) {
		this.sae = sae;
	}


	public String getHp() {
		return this.hp;
	}


	public void setHp(String hp) {
		this.hp = hp;
	}


	public Button getSupprimer() {
		return this.supprimer;
	}


	public void setSupprimer(Button supprimer) {
		this.supprimer = supprimer;
	}


	public String getCode() {
		return this.code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getValidation() {
		return validation;
	}


	public void setValidation(String validation) {
		this.validation = validation;
	}
	
}
