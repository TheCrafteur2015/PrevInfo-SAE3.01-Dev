package modele;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Exportation
{
	private DB baseDonnees;

	/**
	 * constructeur de l'exportation
	 * @throws SQLException
	 */
	public Exportation() throws SQLException
	{
		this.baseDonnees = DB.getInstance();
	}

	/**
	 * méthode pour obtenir le fichier d'exportation <a href="https://fr.wikipedia.org/wiki/Hypertext_Markup_Language"> html </a> d'un {@link Intervenant}
	 * @param idModule   identifiant du {@link Intervenant} à exporter
	 * @param nomFichier nom du fichier de sortie
	 */
	public void exportIntervenant(int idIntervenant, String nomFichier)
	{
		String body ="";
		body += "	<body>\n";

		Intervenant  interv       = null;// obtenir l'intervenant qui correspond a l'intervenant
		List<Module> modulesInter = new ArrayList<>();// obtenir tout les modules au quel l'intervenant est lié

		for (Module mod : modulesInter)
		{
			body += sModule(mod);
		}

		body += "	</body>\n";
		ecrireFichier(nomFichier, body);
	}

	/**
	 * méthode pour obtenir le fichier d'exportation <a href="https://fr.wikipedia.org/wiki/Hypertext_Markup_Language"> html </a> d'un {@link Module}
	 * @param idModule   identifiant du {@link Module} à exporter
	 * @param nomFichier nom du fichier de sortie
	 */
	public void exportModule(int idModule, String nomFichier)
	{
		String body ="";
		body += "	<body>\n";

		Module            mod        = null;// obtenir l'intervenant qui correspond a l'intervenant
		List<Intervenant> intervsMod = new ArrayList<>();// obtenir tout les modules au quel l'intervenant est lié

		for (Intervenant interv : intervsMod)
		{
			body += sIntervenant(interv);
		}

		body += "	</body>\n";
		ecrireFichier(nomFichier, body);
	}

	/**
	 * méthode pour avoir un {@link Module} sous forme de {@link String}
	 * @param mod {@link Module} a transformer
	 * @return un {@link String} contenant le nom du {@link Module} et ???
	 */
	private String sModule(Module mod)
	{
		// je sais pas quoi mettre dans le tableau
		String ret ="";
		ret += "		<tr>\n";
		ret += "			<th> " + mod.getNom() + " </th>\n";
		ret += "			<td> 2 </td>\n";
		ret += "			<td> 2 </td>\n";
		ret += "			<td> 2 </td>\n";
		ret += "			<td> 2 </td>\n";
		ret += "			<td> 2 </td>\n";
		ret += "			<td> 2 </td>\n";
		ret += "		</tr>\n";

		return ret;
	}

	/**
	 * méthode pour avoir un {@link Intervenant} sous forme de {@link String}
	 * @param mod {@link Intervenant} a transformer
	 * @return un {@link String} contenant le nom du {@link Intervenant} et ???
	 */
	private String sIntervenant(Intervenant interv)
	{
		// je sais pas quoi mettre dans le tableau
		String ret ="";
		ret += "		<tr>\n";
		ret += "			<th> " + interv.getNom() + " </th>\n";
		ret += "			<td> 2 </td>\n";
		ret += "			<td> 2 </td>\n";
		ret += "			<td> 2 </td>\n";
		ret += "			<td> 2 </td>\n";
		ret += "			<td> 2 </td>\n";
		ret += "			<td> 2 </td>\n";
		ret += "		</tr>\n";

		return ret;
	}

	/**
	 * Écrit des données dans un fichier spécifié en utilisant un PrintWriter.
	 * 
	 * @param nomFichierDestination Le nom du fichier dans lequel écrire les données.
	 * @param body contenue du Fichier
	 */
	private void ecrireFichier(String nomFichierDestination, String body)
	{
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(nomFichierDestination));
	
			pw.println(this.head());
			pw.println(body);
			pw.println(this.foot());
	
			pw.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private String head()
	{
		String ret = "";
		ret += "<!DOCTYPE html>";
		ret += "<html lang=\"en\">";
		ret += "	<head>";
		ret += "		<meta charset=\"UTF-8\">";
		ret += "		<title>Tableau HTML</title>";
		ret += "		<link href=\"tab.css\" rel=\"stylesheet\">";
		ret += "	</head>";
		return ret;
	}

	private String foot()
	{
		String ret ="</html>";

		return ret;
	}

}
