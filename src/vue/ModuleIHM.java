package vue;

import modele.HeureCours;
import modele.Module;

public class ModuleIHM {


	private Module     module;
	private HeureCours td;
	private HeureCours tp;
	private HeureCours cm;
	private HeureCours tut;
	private HeureCours reh;
	private HeureCours sae;
	private HeureCours hp;

	public ModuleIHM() {}

	public ModuleIHM(Module module, HeureCours td, HeureCours tp, HeureCours cm, HeureCours tut, HeureCours reh,HeureCours sae, HeureCours hp) {
		this.module = module;
		this.td     = td;
		this.tp     = tp;
		this.cm     = cm;
		this.tut    = tut;
		this.reh    = reh;
		this.sae    = sae;
		this.hp     = hp;
	}

	/*---------*/
	/* getters */
	/*---------*/


	public void setModule(Module module) {
		this.module = module;
	}

	public void setTd(HeureCours td) {
		this.td = td;
	}

	public void setTp(HeureCours tp) {
		this.tp = tp;
	}

	public void setCm(HeureCours cm) {
		this.cm = cm;
	}

	public void setTut(HeureCours tut) {
		this.tut = tut;
	}

	public void setReh(HeureCours reh) {
		this.reh = reh;
	}

	public void setSae(HeureCours sae) {
		this.sae = sae;
	}

	public void setHp(HeureCours hp) {
		this.hp = hp;
	}

	public String getModuleNom()       {return this.module.getNom();}

	public double getTdHeure()         {return this.td.getHeure();}
	public int    getTdSemaine()       {return this.td.getNbSemaine();}
	public double getTdHeureSemaine()  {return this.td.gethParSemaine();}

	public double getTpHeure()         {return this.tp.getHeure();}
	public int    getTpSemaine()       {return this.tp.getNbSemaine();}
	public double getTpHeureSemaine()  {return this.tp.gethParSemaine();}

	public double getCmHeure()         {return this.cm.getHeure();}
	public int    getCmSemaine()       {return this.cm.getNbSemaine();}
	public double getCmHeureSemaine()  {return this.cm.gethParSemaine();}

	public double getTutHeure()        {return this.tut.getHeure();}
	public int    getTutSemaine()      {return this.tut.getNbSemaine();}
	public double getTutHeureSemaine() {return this.tut.gethParSemaine();}

	public double getRehHeure()        {return this.reh.getHeure();}
	public int    getRehSemaine()      {return this.reh.getNbSemaine();}
	public double getRehHeureSemaine() {return this.reh.gethParSemaine();}

	public double getSaeHeure()        {return this.sae.getHeure();}
	public int    getSaeSemaine()      {return this.sae.getNbSemaine();}
	public double getSaeHeureSemaine() {return this.sae.gethParSemaine();}

	public double getHpHeure()         {return this.hp.getHeure();}
	public int    getHpSemaine()       {return this.hp.getNbSemaine();}
	public double getHpHeureSemaine()  {return this.hp.gethParSemaine();}


	/*---------*/
	/* setters */
	/*---------*/

	public void setModuleNom(String nom)               {this.module.setNom(nom);}

	public void setTdHeure(double heure)               {this.td.setHeure(heure);}
	public void setTdSemaine(int nbSemaine)            {this.td.setNbSemaine(nbSemaine);}
	public void setTdHeureSemaine(double hParSemaine)  {this.td.sethParSemaine(hParSemaine);}

	public void setTpHeure(double heure)               {this.tp.setHeure(heure);}
	public void setTpSemaine(int nbSemaine)            {this.tp.setNbSemaine(nbSemaine);}
	public void setTpHeureSemaine(double hParSemaine)  {this.tp.sethParSemaine(hParSemaine);}

	public void setCmHeure(double heure)               {this.cm.setHeure(heure);}
	public void setCmSemaine(int nbSemaine)            {this.cm.setNbSemaine(nbSemaine);}
	public void setCmHeureSemaine(double hParSemaine)  {this.cm.sethParSemaine(hParSemaine);}

	public void setTutHeure(double heure)              {this.tut.setHeure(heure);}
	public void setTutSemaine(int nbSemaine)           {this.tut.setNbSemaine(nbSemaine);}
	public void setTutHeureSemaine(double hParSemaine) {this.tut.sethParSemaine(hParSemaine);}

	public void setRehHeure(double heure)              {this.reh.setHeure(heure);}
	public void setRehSemaine(int nbSemaine)           {this.reh.setNbSemaine(nbSemaine);}
	public void setRehHeureSemaine(double hParSemaine) {this.reh.sethParSemaine(hParSemaine);}

	public void setSaeHeure(double heure)              {this.sae.setHeure(heure);}
	public void setSaeSemaine(int nbSemaine)           {this.sae.setNbSemaine(nbSemaine);}
	public void setSaeHeureSemaine(double hParSemaine) {this.sae.sethParSemaine(hParSemaine);}

	public void setHpHeure(double heure)               {this.hp.setHeure(heure);}
	public void setHpSemaine(int nbSemaine)            {this.hp.setNbSemaine(nbSemaine);}
	public void setHpHeureSemaine(double hParSemaine)  {this.hp.sethParSemaine(hParSemaine);}

}