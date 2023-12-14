package vue;

import javafx.scene.control.Button;

public class LigneModuleIHM {
	
	private Integer id;
	private Button info;
	private String nom;
	private String cm;
	private String td;
	private String tp;
	private String reh;
	private String htut;
	private String sae;
	private String hp;
	private Button supprimer;


	public LigneModuleIHM(Integer id, Button info, String nom, String cm, String td, String tp, String reh, String htut,
			String sae, String hp, Button supprimer) {
		this.id = id;
		this.info = info;
		this.nom = nom;
		this.cm = cm;
		this.td = td;
		this.tp = tp;
		this.reh = reh;
		this.htut = htut;
		this.sae = sae;
		this.hp = hp;
		this.supprimer = supprimer;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Button getInfo() {
		return info;
	}


	public void setInfo(Button info) {
		this.info = info;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getCm() {
		return cm;
	}


	public void setCm(String cm) {
		this.cm = cm;
	}


	public String getTd() {
		return td;
	}


	public void setTd(String td) {
		this.td = td;
	}


	public String getTp() {
		return tp;
	}


	public void setTp(String tp) {
		this.tp = tp;
	}


	public String getReh() {
		return reh;
	}


	public void setReh(String reh) {
		this.reh = reh;
	}


	public String getHtut() {
		return htut;
	}


	public void setHtut(String htut) {
		this.htut = htut;
	}


	public String getSae() {
		return sae;
	}


	public void setSae(String sae) {
		this.sae = sae;
	}


	public String getHp() {
		return hp;
	}


	public void setHp(String hp) {
		this.hp = hp;
	}


	public Button getSupprimer() {
		return supprimer;
	}


	public void setSupprimer(Button supprimer) {
		this.supprimer = supprimer;
	}

	

	
}
