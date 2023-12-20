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

	public int    getIdIntervention() {return this.idIntervention;}
	public int    getIdIntervenant()  {return this.idIntervenant;}
	public int    getIdModule()       {return this.idModule;}
	public int    getIdTypeCours()    {return this.idTypeCours;}
	public int    getNbSemaines()     {return this.nbSemaines;}
	public int    getNbGroupe()       {return this.nbGroupes;}
	public String getCommentaire()    {return this.commentaire;}
	public int    getIdAnnee()        {return this.idAnnee;}

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
		String format = "Intervention [idIntervenant=%1$d, idModule=%2$d, idTypeCours=%3$d, nbSemaines=%4$d, nbGroupe=%5$d, idAnnee=%6$d]";
		return String.format(format, this.idIntervenant, this.idModule, this.idTypeCours, this.nbSemaines, this.nbGroupes, this.idAnnee);
	}
	
}