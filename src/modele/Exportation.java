package modele;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controleur.Controleur;
import java.util.Comparator;


public class Exportation
{
	private Modele model;

	/**
	 * constructeur de l'exportation
	 * @throws SQLException
	 */
	public Exportation(Modele model)
	{
		this.model = model;
	}


	/*----------------------------------------------------------------------------------------------------------------*/
	/*                                                                                                                */
	/*                                                                                                                */
	/*                                                      HTML                                                      */
	/*                                                                                                                */
	/*                                                                                                                */
	/*----------------------------------------------------------------------------------------------------------------*/

	/*--------------*/
	/* Intervenants */
	/*--------------*/

	/**
	 * méthode pour obtenir le fichier d'exportation <a href="https://fr.wikipedia.org/wiki/Hypertext_Markup_Language"> html </a> d'un {@link Intervenant}
	 * @param idModule   identifiant du {@link Intervenant} à exporter
	 * @param nomFichier nom du fichier de sortie
	 */
	public void exportIntervenant(int idIntervenant, String nomFichier)
	{
		String body ="";
		body += "	<body>\n";
		body += "		<table border=\"1\">\n";
		body += "			<thead>\n";
		body += "				<tr>\n";
		body += "					<th> Module           </th>\n";
		body += "					<th> CM               </th>\n";
		body += "					<th> TD               </th>\n";
		body += "					<th> TP               </th>\n";
		body += "					<th> REH              </th>\n";
		body += "					<th> SAE              </th>\n";
		body += "					<th> tutorat          </th>\n";
		body += "					<th> heure ponctuelle </th>\n";
		body += "					<th> total            </th>\n";
		body += "				</tr>\n";
		body += "			</thead>\n";
		body += "			<tbody>\n";

		Map<Module,List<Intervention>> modulesInter = this.model.getModulesIntervenant(idIntervenant);// obtenir tout les modules au quel l'intervenant est lié
		List<Module> ensmodule = new ArrayList<Module>(modulesInter.keySet());

		Collections.sort(ensmodule, new Module(0, null, null, 0, 0, 0).new ModuleComparator());

		int debut = 0;
		for (int cpt=0; cpt<ensmodule.size();cpt++)
		{
			if (ensmodule.get(cpt).getIdSemestre()>ensmodule.get(debut).getIdSemestre())
			{
				body += sSemestre(ensmodule.subList(debut, cpt), modulesInter);
				debut = cpt;
			}
		}
		if (!ensmodule.isEmpty()) {
			body += sSemestre(ensmodule.subList(debut, ensmodule.size()), modulesInter);
		}
		body += "			</tbody>\n";

		int cm        = 0;
		int td        = 0;
		int tp        = 0;
		int reh       = 0;
		int sae       = 0;
		int tutorat   = 0;
		int hPonctuel = 0;
		int total     = 0;
		for (Module module : ensmodule)
		{
			for (Intervention intervention : modulesInter.get(module))
			{
				int add = intervention.getNbSemaines()*intervention.getNbGroupe();
				switch (intervention.getIdTypeCours()) {
				case 3 -> cm        += add;
				case 1 -> td        += add;
				case 2 -> tp        += add;
				case 5 -> reh       += add;
				case 6 -> sae       += intervention.getNbGroupe();
				case 4 -> tutorat   += intervention.getNbGroupe();
				case 7 -> hPonctuel += intervention.getNbGroupe();
				}
			}
			total = cm+td+tp+reh+sae+tutorat+hPonctuel;
		}

		body += "			<tfoot>\n";
		body += "				<tr>\n";
		body += "					<td> Total </td>\n";
		body += "					<td> "+ cm +" </td>\n";
		body += "					<td> "+ td +" </td>\n";
		body += "					<td> "+ tp +" </td>\n";
		body += "					<td> "+ reh +" </td>\n";
		body += "					<td> "+ sae +" </td>\n";
		body += "					<td> "+ tutorat +" </td>\n";
		body += "					<td> "+ hPonctuel +" </td>\n";
		body += "					<td> "+ total +" </td>\n";
		body += "				</tr>\n";
		body += "			</tfoot>\n";
		body += "		</table>\n";
		body += "	</body>\n";
		this.cssGenerator();
		ecrireFichierhtml(nomFichier + ".html", this.head(nomFichier) + body + this.foot());
	}

	private String sSemestre(List<Module> ensModule, Map<Module,List<Intervention>> modulesInter)
	{
		int cm        = 0;
		int td        = 0;
		int tp        = 0;
		int reh       = 0;
		int sae       = 0;
		int tutorat   = 0;
		int hPonctuel = 0;
		int total     = 0;

		String ret = "";
		for (Module mod : ensModule)
		{
			ret += sModule(mod,modulesInter.get(mod));
		}

		for (Module module : ensModule)
		{
			for (Intervention intervention : modulesInter.get(module))
			{
				int add = intervention.getNbSemaines()*intervention.getNbGroupe();
				switch (intervention.getIdTypeCours()) {
				case 3 -> cm        += add;
				case 1 -> td        += add;
				case 2 -> tp        += add;
				case 5 -> reh       += add;
				case 6 -> sae       += intervention.getNbGroupe();
				case 4 -> tutorat   += intervention.getNbGroupe();
				case 7 -> hPonctuel += intervention.getNbGroupe();
				}
			}
			total = cm+td+tp+reh+sae+tutorat+hPonctuel;
		}
		
		ret += "				<tr class=\"semestre" + ensModule.get(0).getIdSemestre() + "\">\n";
		ret += "					<td> Total Semestre " + ensModule.get(0).getIdSemestre() + " </td>\n";
		ret += "					<td> "+ cm +" </td>\n";
		ret += "					<td> "+ td +" </td>\n";
		ret += "					<td> "+ tp +" </td>\n";
		ret += "					<td> "+ reh +" </td>\n";
		ret += "					<td> "+ sae +" </td>\n";
		ret += "					<td> "+ tutorat +" </td>\n";
		ret += "					<td> "+ hPonctuel +" </td>\n";
		ret += "					<td> "+ total +" </td>\n";
		ret += "				</tr>\n";

		return ret;
	}


	/**
	 * méthode pour avoir un {@link Module} sous forme de {@link String}
	 * @param mod {@link Module} a transformer
	 * @return un {@link String} contenant le nom du {@link Module} et ???
	 */
	private String sModule(Module mod, List<Intervention> lstIntervention)
	{
		int cm        = 0;
		int td        = 0;
		int tp        = 0;
		int reh       = 0;
		int sae       = 0;
		int tutorat   = 0;
		int hPonctuel = 0;
		int total     = 0;

		for (Intervention intervention : lstIntervention) {
			int add = intervention.getNbSemaines()*intervention.getNbGroupe();
			switch (intervention.getIdTypeCours()) {
				case 3 -> cm        += add;
				case 1 -> td        += add;
				case 2 -> tp        += add;
				case 5 -> reh       += add;
				case 6 -> sae       += intervention.getNbGroupe();
				case 4 -> tutorat   += intervention.getNbGroupe();
				case 7 -> hPonctuel += intervention.getNbGroupe();
			}
		}
		total = cm + td + tp + reh + sae + tutorat + hPonctuel;
		String ret ="";
		String tab = "					";
		ret += "				<tr class=\"semestre" + mod.getIdSemestre() + " \\\">\n";
		ret += "					<th> " + mod.getCode() + " " + mod.getNom() + " </th>\n";
		switch (mod.getIdTypeModule()) {
			case 1:
				ret += tab + "<td> "+ cm +" </td>\n";
				ret += tab + "<td> "+ td +" </td>\n";
				ret += tab + "<td> "+ tp +" </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td> "+ tutorat +" </td>\n";
				ret += tab + "<td> "+ hPonctuel +" </td>\n";
				ret += tab + "<td> "+ total +" </td>\n";
				break;

			case 2:
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td> "+ sae +" </td>\n";
				ret += tab + "<td> "+ tutorat +" </td>\n";
				ret += tab + "<td> "+ hPonctuel +" </td>\n";
				ret += tab + "<td> "+ total +" </td>\n";
				break;

			case 3:
				ret += tab + "<td> "+ cm +" </td>\n";
				ret += tab + "<td> "+ td +" </td>\n";
				ret += tab + "<td> "+ tp +" </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td> "+ hPonctuel +" </td>\n";
				ret += tab + "<td> "+ total +" </td>\n";
				break;

			case 4:
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td> "+ reh +" </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td> "+ tutorat +" </td>\n";
				ret += tab + "<td> "+ hPonctuel +" </td>\n";
				ret += tab + "<td> "+ total +" </td>\n";
				break;
		}
		ret += "				</tr>\n";
		return ret;
	}

	/*---------*/
	/* modules */
	/*---------*/

	/**
	 * méthode pour obtenir le fichier d'exportation <a href="https://fr.wikipedia.org/wiki/Hypertext_Markup_Language"> html </a> d'un {@link Module}
	 * @param idModule   identifiant du {@link Module} à exporter
	 * @param nomFichier nom du fichier de sortie
	 */
	public void exportModule(int idModule, String nomFichier)
	{
		Module mod = this.model.getHmModules().get(idModule);
		switch (mod.getIdTypeModule()) {
			case 1 -> exportPPP(mod, nomFichier);
			case 2 -> exportSAE(mod, nomFichier);
			case 3 -> exportNormal(mod, nomFichier);
			case 4 -> exportStage(mod, nomFichier);
		}
	}

	public void exportPPP(Module mod, String nomFichier)
	{
		String body ="";
		body += "	<body>\n";
		body += "		<table border=\"1\">\n";
		body += "			<thead>\n";
		body += "				<tr>\n";
		body += "					<th> Intervenant </th>\n";
		body += "					<td> cm          </td>\n";
		body += "					<td> td          </td>\n";
		body += "					<td> tp          </td>\n";
		body += "					<td> tutorat     </td>\n";
		body += "					<td> hPonctuel   </td>\n";
		body += "					<td> total       </td>\n";
		body += "				</tr>\n";
		body += "			</thead>\n";
		body += "			<tbody>\n";

		Map<Intervenant,List<Intervention>> hmModuleIntervenant = model.getIntervenantsModule(mod.getId());

		int totcm        = 0;
		int tottd        = 0;
		int tottp        = 0;
		int tottutorat   = 0;
		int tothPonctuel = 0;
		int tottotal     = 0;


		for (Intervenant intervenant : hmModuleIntervenant.keySet()) {
			int cm        = 0;
			int td        = 0;
			int tp        = 0;
			int tutorat   = 0;
			int hPonctuel = 0;
			int total     = 0;

			for (Intervention intervention : hmModuleIntervenant.get(intervenant)) {
				int add = intervention.getNbSemaines()*intervention.getNbGroupe();
				switch (intervention.getIdTypeCours()) {
					case 3 -> cm        += add;
					case 1 -> td        += add;
					case 2 -> tp        += add;
					case 4 -> tutorat   += intervention.getNbGroupe();
					case 7 -> hPonctuel += intervention.getNbGroupe();
					}
				}
				total = cm + td + tp + tutorat + hPonctuel;

				totcm        += cm;
				tottd        += td;
				tottp        += tp;
				tottutorat   += tutorat;
				tothPonctuel += tothPonctuel;
				tottotal     += total;

				body += "				<tr>\n";
				body += "					<th> " + intervenant.getNom() + " " + intervenant.getPrenom() + " </th>\n";
				body += "					<td> " + cm + " </td>\n";
				body += "					<td> " + td + " </td>\n";
				body += "					<td> " + tp + " </td>\n";
				body += "					<td> " + tutorat + " </td>\n";
				body += "					<td> " + hPonctuel + " </td>\n";
				body += "					<td> " + total + " </td>\n";
				body += "				</tr>\n";
			
		}
		body += "			</tbody>\n";

		body += "			<tfoot>\n";
		body += "				<tr>\n";
		body += "					<td> Total </td>\n";
		body += "					<td> "+ totcm +" </td>\n";
		body += "					<td> "+ tottd +" </td>\n";
		body += "					<td> "+ tottp +" </td>\n";
		body += "					<td> "+ tottutorat +" </td>\n";
		body += "					<td> "+ tothPonctuel +" </td>\n";
		body += "					<td> "+ tottotal +" </td>\n";
		body += "				</tr>\n";
		body += "			</tfoot>\n";
		body += "		</table>\n";
		body += "	</body>\n";
		this.cssGenerator();
		ecrireFichierhtml(nomFichier + ".html", this.head(nomFichier) + body + this.foot());
	}

	public void exportSAE(Module mod, String nomFichier)
	{
		String body ="";
		body += "	<body>\n";
		body += "		<table border=\"1\">\n";
		body += "			<thead>\n";
		body += "				<tr>\n";
		body += "					<th> Intervenant </th>\n";
		body += "					<td> tutorat     </td>\n";
		body += "					<td> sae         </td>\n";
		body += "					<td> hPonctuel   </td>\n";
		body += "					<td> total       </td>\n";
		body += "				</tr>\n";
		body += "			</thead>\n";
		body += "			<tbody>\n";

		Map<Intervenant,List<Intervention>> hmModuleIntervenant = model.getIntervenantsModule(mod.getId());

		int tottutorat   = 0;
		int totsae       = 0;
		int tothPonctuel = 0;
		int tottotal     = 0;


		for (Intervenant intervenant : hmModuleIntervenant.keySet()) {
			int tutorat   = 0;
			int sae       = 0;
			int hPonctuel = 0;
			int total     = 0;

			for (Intervention intervention : hmModuleIntervenant.get(intervenant)) {
				switch (intervention.getIdTypeCours()) {
					case 4 -> tutorat   += intervention.getNbGroupe();
					case 6 -> sae       += intervention.getNbGroupe();//un truc
					case 7 -> hPonctuel += intervention.getNbGroupe();
					}
				}
				total = tutorat + hPonctuel;

				tottutorat   += tutorat;
				totsae       += sae;
				tothPonctuel += tothPonctuel;
				tottotal     += total;

				body += "				<tr>\n";
				body += "					<th> " + intervenant.getNom() + " " + intervenant.getPrenom() + " </th>\n";
				body += "					<td> " + tutorat + " </td>\n";
				body += "					<td> " + sae + " </td>\n";
				body += "					<td> " + hPonctuel + " </td>\n";
				body += "					<td> " + total + " </td>\n";
				body += "				</tr>\n";
			
		}
		body += "			</tbody>\n";

		body += "			<tfoot>\n";
		body += "				<tr>\n";
		body += "					<td> Total </td>\n";
		body += "					<td> "+ tottutorat +" </td>\n";
		body += "					<td> "+ totsae +" </td>\n";
		body += "					<td> "+ tothPonctuel +" </td>\n";
		body += "					<td> "+ tottotal +" </td>\n";
		body += "				</tr>\n";
		body += "			</tfoot>\n";
		body += "		</table>\n";
		body += "	</body>\n";
		this.cssGenerator();
		ecrireFichierhtml(nomFichier + ".html", this.head(nomFichier) + body + this.foot());
	}

	public void exportNormal(Module mod, String nomFichier)
	{
		String body ="";
		body += "	<body>\n";
		body += "		<table border=\"1\">\n";
		body += "			<thead>\n";
		body += "				<tr>\n";
		body += "					<th> Intervenant </th>\n";
		body += "					<td> cm          </td>\n";
		body += "					<td> td          </td>\n";
		body += "					<td> tp          </td>\n";
		body += "					<td> hPonctuel   </td>\n";
		body += "					<td> total       </td>\n";
		body += "				</tr>\n";
		body += "			</thead>\n";
		body += "			<tbody>\n";

		Map<Intervenant,List<Intervention>> hmModuleIntervenant = model.getIntervenantsModule(mod.getId());

		int totcm        = 0;
		int tottd        = 0;
		int tottp        = 0;
		int tothPonctuel = 0;
		int tottotal     = 0;


		for (Intervenant intervenant : hmModuleIntervenant.keySet()) {
			int cm        = 0;
			int td        = 0;
			int tp        = 0;
			int hPonctuel = 0;
			int total     = 0;

			for (Intervention intervention : hmModuleIntervenant.get(intervenant)) {
				int add = intervention.getNbSemaines()*intervention.getNbGroupe();
				switch (intervention.getIdTypeCours()) {
					case 3 -> cm        += add;
					case 1 -> td        += add;
					case 2 -> tp        += add;
					case 7 -> hPonctuel += intervention.getNbGroupe();
					}
			}
					total = cm + td + tp + hPonctuel;

			totcm        += cm;
			tottd        += td;
			tottp        += tp;
			tothPonctuel += tothPonctuel;
			tottotal     += total;

			body += "				<tr>\n";
			body += "					<th> " + intervenant.getNom() + " " + intervenant.getPrenom() + " </th>\n";
			body += "					<td> " + cm + " </td>\n";
			body += "					<td> " + td + " </td>\n";
			body += "					<td> " + tp + " </td>\n";
			body += "					<td> " + hPonctuel + " </td>\n";
			body += "					<td> " + total + " </td>\n";
			body += "				</tr>\n";
			
		}
		body += "			</tbody>\n";

		body += "			<tfoot>\n";
		body += "				<tr>\n";
		body += "					<td> Total </td>\n";
		body += "					<td> "+ totcm +" </td>\n";
		body += "					<td> "+ tottd +" </td>\n";
		body += "					<td> "+ tottp +" </td>\n";
		body += "					<td> "+ tothPonctuel +" </td>\n";
		body += "					<td> "+ tottotal +" </td>\n";
		body += "				</tr>\n";
		body += "			</tfoot>\n";
		body += "		</table>\n";
		body += "	</body>\n";
		this.cssGenerator();
		ecrireFichierhtml(nomFichier + ".html", this.head(nomFichier) + body + this.foot());
	}

	public void exportStage(Module mod, String nomFichier)
	{
		String body ="";
		body += "	<body>\n";
		body += "		<table border=\"1\">\n";
		body += "			<thead>\n";
		body += "				<tr>\n";
		body += "					<th> Intervenant </th>\n";
		body += "					<td> tutorat     </td>\n";
		body += "					<td> reh        </td>\n";
		body += "					<td> hPonctuel   </td>\n";
		body += "					<td> total       </td>\n";
		body += "				</tr>\n";
		body += "			</thead>\n";
		body += "			<tbody>\n";

		Map<Intervenant,List<Intervention>> hmModuleIntervenant = model.getIntervenantsModule(mod.getId());

		int tottutorat   = 0;
		int totreh       = 0;
		int tothPonctuel = 0;
		int tottotal     = 0;


		for (Intervenant intervenant : hmModuleIntervenant.keySet()) {
			int tutorat   = 0;
			int reh       = 0;
			int hPonctuel = 0;
			int total     = 0;

			for (Intervention intervention : hmModuleIntervenant.get(intervenant)) {
				int add = intervention.getNbSemaines()*intervention.getNbGroupe();
				switch (intervention.getIdTypeCours()) {
					case 5 -> reh       += add;
					case 4 -> tutorat   += intervention.getNbGroupe();
					case 7 -> hPonctuel += intervention.getNbGroupe();
					}
			}
					total = tutorat + hPonctuel;

					totreh       += reh;
					tottutorat   += tutorat;
					tothPonctuel += tothPonctuel;
					tottotal     += total;

					body += "				<tr>\n";
					body += "					<th> " + intervenant.getNom() + " " + intervenant.getPrenom() + " </th>\n";
					body += "					<td> " + tutorat + " </td>\n";
					body += "					<td> " + reh + " </td>\n";
					body += "					<td> " + hPonctuel + " </td>\n";
					body += "					<td> " + total + " </td>\n";
					body += "				</tr>\n";
			
		}
		body += "			</tbody>\n";

		body += "			<tfoot>\n";
		body += "				<tr>\n";
		body += "					<td> Total </td>\n";
		body += "					<td> "+ tottutorat +" </td>\n";
		body += "					<td> "+ totreh +" </td>\n";
		body += "					<td> "+ tothPonctuel +" </td>\n";
		body += "					<td> "+ tottotal +" </td>\n";
		body += "				</tr>\n";
		body += "			</tfoot>\n";
		body += "		</table>\n";
		body += "	</body>\n";
		this.cssGenerator();
		ecrireFichierhtml(nomFichier + ".html", this.head(nomFichier) + body + this.foot());
	}

	/*-------*/
	/* autre */
	/*-------*/

	/**
	 * Écrit des données dans un fichier spécifié en utilisant un PrintWriter.
	 * 
	 * @param nomFichierDestination Le nom du fichier dans lequel écrire les données.
	 * @param body contenue du Fichier
	 */
	private void ecrireFichierhtml(String nomFichierDestination, String body)
	{
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream("src/exportation-test/"+nomFichierDestination));
			pw.println(body);
			pw.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void cssGenerator()
	{
		String ret = "";
		ret += "thead {background-color: #222222;color: #ffffff;}\n";
		ret += "tbody {background-color: #e4f0f5;}\n";
		ret += "tfoot {background-color: #777777;color: #ffffff;}\n";
		ret += "caption {padding: 10px;caption-side: bottom;}\n";
		ret += "table {border-collapse: collapse;border: 2px solid rgb(255, 255, 255);letter-spacing: 1px;font-family: sans-serif;font-size: 0.8rem;}\n";
		ret += "td,th {border: 1px solid rgb(190, 190, 190);padding: 5px 10px;}\n";
		ret += "td {text-align: center;}\n";
		
		for (Semestre semestre : model.getHmSemestres().values()) {
			ret += ".semestre"+ semestre.getId() + "{background-color: " + semestre.getCouleur() + ";}\n";
		}
		ecrireFichierhtml("tab.css", ret);
	}

	private String head(String titre)
	{
		String ret = "";
		ret += "<!DOCTYPE html>\n";
		ret += "<html lang=\"en\">\n";
		ret += "	<head>";
		ret += "		<meta charset=\"UTF-8\">\n";
		ret += "		<title>"+ titre +"</title>\n";
		ret += "		<link href=\"tab.css\" rel=\"stylesheet\">\n";
		ret += "	</head>\n";
		return ret;
	}

	private String foot()
	{
		String ret ="</html>";

		return ret;
	}


	/*----------------------------------------------------------------------------------------------------------------*/
	/*                                                                                                                */
	/*                                                                                                                */
	/*                                                      CSV                                                       */
	/*                                                                                                                */
	/*                                                                                                                */
	/*----------------------------------------------------------------------------------------------------------------*/

	private void ecrireFichiercsv(String nomFichierDestination, String body)
	{
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream("src/exportation-test/"+nomFichierDestination));
			pw.println(body);
			pw.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
