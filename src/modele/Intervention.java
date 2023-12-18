package modele;

public class Intervention {

	public static int nbIntervention = 0;
	
	private int    idIntervention;
	private int    idIntervenant;
	private int    idModule;
	private int    idTypeCours;
	private int    nbSemaines;
	private int    nbGroupes;
	private String commentaire;
	private int    idAnnee;

	public Intervention(int idIntervention, int idIntervenant, int idModule, int idTypeCours, int nbSemaines, int nbGroupes, String commentaire, int idAnnee) {
		this.idIntervention = idIntervention;
		this.idIntervenant = idIntervenant;
		this.idModule = idModule;
		this.idTypeCours = idTypeCours;
		this.nbSemaines = nbSemaines;
		this.nbGroupes = nbGroupes;
		this.commentaire = commentaire;
		this.idAnnee = idAnnee;
		Intervention.nbIntervention++;
	}

	public Intervention(int idIntervenant, int idModule, int idTypeCours, int nbSemaines, int nbGroupes, String commentaire, int idAnnee) {
		this.idIntervention = ++Intervention.nbIntervention;
		this.idIntervenant = idIntervenant;
		this.idModule = idModule;
		this.idTypeCours = idTypeCours;
		this.nbSemaines = nbSemaines;
		this.nbGroupes = nbGroupes;
		this.commentaire = commentaire;
		this.idAnnee = idAnnee;
	}

	public int    getIdIntervention() {return idIntervention;}
	public int    getIdIntervenant()  {return idIntervenant;}
	public int    getIdModule()       {return idModule;}
	public int    getIdTypeCours()    {return idTypeCours;}
	public int    getNbSemaines()     {return nbSemaines;}
	public int    getNbGroupe()       {return nbGroupes;}
	public String getCommentaire()    {return commentaire;}
	public int    getIdAnnee()        {return idAnnee;}

	public void setIdIntervention(int idIntervention) {this.idIntervention = idIntervention;}
	public void setIdIntervenant (int idIntervenant)  {this.idIntervenant = idIntervenant;}
	public void setIdModule      (int idModule)       {this.idModule = idModule;}
	public void setIdTypeCours   (int idTypeCours)    {this.idTypeCours = idTypeCours;}
	public void setNbSemaines    (int nbSemaines)     {this.nbSemaines = nbSemaines;}
	public void setNbGroupe      (int nbGroupe)       {this.nbGroupes = nbGroupe;}
	public void setCommentaire   (String commentaire) {this.commentaire = commentaire;}
	public void setIdAnnee       (int idAnnee)        {this.idAnnee = idAnnee;}

	@Override
	public String toString() {
		return "Intervention [idIntervenant=" + idIntervenant + ", idModule=" + idModule + ", idTypeCours="
				+ idTypeCours + ", nbSemaines=" + nbSemaines + ", nbGroupe=" + nbGroupes + ", idAnnee=" + idAnnee + "]";
	}

	
	
}