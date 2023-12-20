package vue;

public class RecapInterventionIHM {
	
	private String info;
	private String cm;
	private String td;
	private String tp;
	private String reh;
	private String sae;
	private String tut;
	private String hp;
	private String somme;
	
	public RecapInterventionIHM() {}

	public RecapInterventionIHM(String info, String cm, String td, String tp, String reh, String sae, String tut,
			String hp, String somme) {
		this.info = info;
		this.cm = cm;
		this.td = td;
		this.tp = tp;
		this.reh = reh;
		this.sae = sae;
		this.tut = tut;
		this.hp = hp;
		this.somme = somme;
	}

	public String getReh() {
		return reh;
	}



	public void setReh(String reh) {
		this.reh = reh;
	}



	public String getSae() {
		return sae;
	}



	public void setSae(String sae) {
		this.sae = sae;
	}



	public String getTut() {
		return tut;
	}



	public void setTut(String tut) {
		this.tut = tut;
	}



	public String getInfo() {
		return info;
	}


	public void setInfo(String info) {
		this.info = info;
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


	public String getHp() {
		return hp;
	}


	public void setHp(String hp) {
		this.hp = hp;
	}


	public String getSomme() {
		return somme;
	}


	public void setSomme(String somme) {
		this.somme = somme;
	}
	
	
}
