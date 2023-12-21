package modele;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;

import vue.ResourceManager;


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
	public URL exportIntervenantHtml(int idIntervenant, String nomFichier, String chemin)
	{
		String body ="";
		body += "			<table border=\"1\">\n";
		body += "				<thead>\n";
		body += "					<tr>\n";
		body += "						<th> Module           </th>\n";
		body += "						<th> CM               </th>\n";
		body += "						<th> TD               </th>\n";
		body += "						<th> TP               </th>\n";
		body += "						<th> REH              </th>\n";
		body += "						<th> SAE              </th>\n";
		body += "						<th> tutorat          </th>\n";
		body += "						<th> heure ponctuelle </th>\n";
		body += "						<th> total            </th>\n";
		body += "					</tr>\n";
		body += "				</thead>\n";
		body += "				<tbody>\n";

		Map<Module, List<Intervention>> modulesInter = this.model.getModulesIntervenant(idIntervenant);// obtenir tout les modules au quel l'intervenant est lié
		List<Module> ensModule = new ArrayList<>(modulesInter.keySet());

		Collections.sort(ensModule, new Module(0,"","",false,0,0,0).new ModuleComparator()); // Module qui n'existe pas car id à 0

		int debut = 0;
		for (int cpt=0; cpt<ensModule.size();cpt++)
		{
			if (ensModule.get(cpt).getIdSemestre()>ensModule.get(debut).getIdSemestre())
			{
				body += sSemestreHTML(ensModule.subList(debut, cpt), modulesInter);
				debut = cpt;
			}
		}
		if (!ensModule.isEmpty()) {
			body += sSemestreHTML(ensModule.subList(debut, ensModule.size()), modulesInter);
		}
		body += "				</tbody>\n";

		int cm        = 0;
		int td        = 0;
		int tp        = 0;
		int reh       = 0;
		int sae       = 0;
		int tutorat   = 0;
		int hPonctuel = 0;
		int total     = 0;
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

		body += "				<tfoot>\n";
		body += "					<tr>\n";
		body += "						<td> Total </td>\n";
		body += "						<td> "+ cm +" </td>\n";
		body += "						<td> "+ td +" </td>\n";
		body += "						<td> "+ tp +" </td>\n";
		body += "						<td> "+ reh +" </td>\n";
		body += "						<td> "+ sae +" </td>\n";
		body += "						<td> "+ tutorat +" </td>\n";
		body += "						<td> "+ hPonctuel +" </td>\n";
		body += "						<td> "+ total +" </td>\n";
		body += "					</tr>\n";
		body += "				</tfoot>\n";
		body += "			</table>\n";
		body += "		</body>\n";
		this.cssGenerator(chemin);
		return this.ecrireFichier(chemin +nomFichier + ".html", this.head(nomFichier) + body + this.foot());
	}

	private String sSemestreHTML(List<Module> ensModule, Map<Module,List<Intervention>> modulesInter)
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
			ret += sModuleHTML(mod,modulesInter.get(mod));
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
		ret += "					<td> Total Semestre " + (((ensModule.get(0).getIdSemestre()+5) %6) +1) + " </td>\n";
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
	private String sModuleHTML(Module mod, List<Intervention> lstIntervention)
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
	public URL exportModuleHTML(int idModule, String nomFichier, String chemin) {
		Module mod = this.model.getHmModules().get(idModule);
		return switch (mod.getIdTypeModule()) {
			case 1  -> this.exportPPPHTML(mod, nomFichier, chemin);
			case 2  -> this.exportSAEHTML(mod, nomFichier, chemin);
			case 3  -> this.exportNormalHTML(mod, nomFichier, chemin);
			case 4  -> this.exportStageHTML(mod, nomFichier, chemin);
			default -> null;
		};
	}

	public URL exportPPPHTML(Module mod, String nomFichier, String chemin) {
		String body ="";
		body += "			<table border=\"1\">\n";
		body += "				<thead>\n";
		body += "					<tr>\n";
		body += "						<th> Intervenant </th>\n";
		body += "						<td> cm          </td>\n";
		body += "						<td> td          </td>\n";
		body += "						<td> tp          </td>\n";
		body += "						<td> tutorat     </td>\n";
		body += "						<td> hPonctuel   </td>\n";
		body += "						<td> total       </td>\n";
		body += "					</tr>\n";
		body += "				</thead>\n";
		body += "				<tbody>\n";

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

				body += "					<tr>\n";
				body += "						<th> " + intervenant.getNom() + " " + intervenant.getPrenom() + " </th>\n";
				body += "						<td> " + cm + " </td>\n";
				body += "						<td> " + td + " </td>\n";
				body += "						<td> " + tp + " </td>\n";
				body += "						<td> " + tutorat + " </td>\n";
				body += "						<td> " + hPonctuel + " </td>\n";
				body += "						<td> " + total + " </td>\n";
				body += "					</tr>\n";
			
		}
		body += "				</tbody>\n";

		body += "				<tfoot>\n";
		body += "					<tr>\n";
		body += "						<td> Total </td>\n";
		body += "						<td> "+ totcm +" </td>\n";
		body += "						<td> "+ tottd +" </td>\n";
		body += "						<td> "+ tottp +" </td>\n";
		body += "						<td> "+ tottutorat +" </td>\n";
		body += "						<td> "+ tothPonctuel +" </td>\n";
		body += "						<td> "+ tottotal +" </td>\n";
		body += "					</tr>\n";
		body += "				</tfoot>\n";
		body += "			</table>\n";
		body += "		</body>\n";
		this.cssGenerator(chemin);
		return this.ecrireFichier(chemin + nomFichier + ".html", this.head(nomFichier) + body + this.foot());
	}

	public URL exportSAEHTML(Module mod, String nomFichier, String chemin)
	{
		String body ="";
		body += "			<table border=\"1\">\n";
		body += "				<thead>\n";
		body += "					<tr>\n";
		body += "						<th> Intervenant </th>\n";
		body += "						<td> tutorat     </td>\n";
		body += "						<td> sae         </td>\n";
		body += "						<td> hPonctuel   </td>\n";
		body += "						<td> total       </td>\n";
		body += "					</tr>\n";
		body += "				</thead>\n";
		body += "				<tbody>\n";

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

				body += "					<tr>\n";
				body += "						<th> " + intervenant.getNom() + " " + intervenant.getPrenom() + " </th>\n";
				body += "						<td> " + tutorat + " </td>\n";
				body += "						<td> " + sae + " </td>\n";
				body += "						<td> " + hPonctuel + " </td>\n";
				body += "						<td> " + total + " </td>\n";
				body += "					</tr>\n";
			
		}
		body += "				</tbody>\n";

		body += "				<tfoot>\n";
		body += "					<tr>\n";
		body += "						<td> Total </td>\n";
		body += "						<td> "+ tottutorat +" </td>\n";
		body += "						<td> "+ totsae +" </td>\n";
		body += "						<td> "+ tothPonctuel +" </td>\n";
		body += "						<td> "+ tottotal +" </td>\n";
		body += "					</tr>\n";
		body += "				</tfoot>\n";
		body += "			</table>\n";
		body += "		</body>\n";
		this.cssGenerator(chemin);
		return this.ecrireFichier(chemin + nomFichier + ".html", this.head(nomFichier) + body + this.foot());
	}

	public URL exportNormalHTML(Module mod, String nomFichier, String chemin)
	{
		String body ="";
		body += "			<table border=\"1\">\n";
		body += "				<thead>\n";
		body += "					<tr>\n";
		body += "						<th> Intervenant </th>\n";
		body += "						<td> cm          </td>\n";
		body += "						<td> td          </td>\n";
		body += "						<td> tp          </td>\n";
		body += "						<td> hPonctuel   </td>\n";
		body += "						<td> total       </td>\n";
		body += "					</tr>\n";
		body += "				</thead>\n";
		body += "				<tbody>\n";

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

			body += "					<tr>\n";
			body += "						<th> " + intervenant.getNom() + " " + intervenant.getPrenom() + " </th>\n";
			body += "						<td> " + cm + " </td>\n";
			body += "						<td> " + td + " </td>\n";
			body += "						<td> " + tp + " </td>\n";
			body += "						<td> " + hPonctuel + " </td>\n";
			body += "						<td> " + total + " </td>\n";
			body += "					</tr>\n";
			
		}
		body += "				</tbody>\n";

		body += "				<tfoot>\n";
		body += "					<tr>\n";
		body += "						<td> Total </td>\n";
		body += "						<td> "+ totcm +" </td>\n";
		body += "						<td> "+ tottd +" </td>\n";
		body += "						<td> "+ tottp +" </td>\n";
		body += "						<td> "+ tothPonctuel +" </td>\n";
		body += "						<td> "+ tottotal +" </td>\n";
		body += "					</tr>\n";
		body += "				</tfoot>\n";
		body += "			</table>\n";
		body += "		</body>\n";
		this.cssGenerator(chemin);
		return this.ecrireFichier(chemin + nomFichier + ".html", this.head(nomFichier) + body + this.foot());
	}

	public URL exportStageHTML(Module mod, String nomFichier, String chemin)
	{
		String body ="";
		body += "			<table border=\"1\">\n";
		body += "				<thead>\n";
		body += "					<tr>\n";
		body += "						<th> Intervenant </th>\n";
		body += "						<td> tutorat     </td>\n";
		body += "						<td> reh         </td>\n";
		body += "						<td> hPonctuel   </td>\n";
		body += "						<td> total       </td>\n";
		body += "					</tr>\n";
		body += "				</thead>\n";
		body += "				<tbody>\n";

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

					body += "					<tr>\n";
					body += "						<th> " + intervenant.getNom() + " " + intervenant.getPrenom() + " </th>\n";
					body += "						<td> " + tutorat + " </td>\n";
					body += "						<td> " + reh + " </td>\n";
					body += "						<td> " + hPonctuel + " </td>\n";
					body += "						<td> " + total + " </td>\n";
					body += "					</tr>\n";
			
		}
		body += "				</tbody>\n";

		body += "				<tfoot>\n";
		body += "					<tr>\n";
		body += "						<td> Total </td>\n";
		body += "						<td> "+ tottutorat +" </td>\n";
		body += "						<td> "+ totreh +" </td>\n";
		body += "						<td> "+ tothPonctuel +" </td>\n";
		body += "						<td> "+ tottotal +" </td>\n";
		body += "					</tr>\n";
		body += "				</tfoot>\n";
		body += "			</table>\n";
		body += "		</body>\n";
		this.cssGenerator(chemin);
		return this.ecrireFichier(chemin + nomFichier + ".html", this.head(nomFichier) + body + this.foot());
	}

	/*-------*/
	/* autre */
	/*-------*/

	private URL cssGenerator(String chemin) {
		String css = ":root {\n";
		for (Semestre semestre : model.getHmSemestres().values())
			css += "\t--semestre-" + semestre.getId() + ": " + semestre.getCouleur() + ";\n";
		return this.ecrireFichier(chemin + "tab.css", css + "}\n\n" + ResourceManager.TAB_TEMPLATE_CONTENT);
		// String ret = "";
		// ret += "thead {background-color: #222222;color: #ffffff;}\n";
		// ret += "tbody {background-color: #e4f0f5;}\n";
		// ret += "tfoot {background-color: #777777;color: #ffffff;}\n";
		// ret += "caption {padding: 10px;caption-side: bottom;}\n";
		// ret += "table {border-collapse: collapse;border: 2px solid rgb(255, 255, 255);letter-spacing: 1px;font-family: sans-serif;font-size: 0.8rem;}\n";
		// ret += "td,th {border: 1px solid rgb(190, 190, 190);padding: 5px 10px;}\n";
		// ret += "td {text-align: center;}\n";
		
		// for (Semestre semestre : model.getHmSemestres().values()) {
		// 	ret += ".semestre"+ semestre.getId() + "{background-color: " + semestre.getCouleur() + ";}\n";
		// }
		// return this.ecrireFichier(chemin + "tab.css", ret);
	}

	private String head(String titre) {
		String ret = "";
		ret += "<!DOCTYPE html>\n";
		ret += "<html lang=\"en\">\n";
		ret += "	<head>\n";
		ret += "		<meta charset=\"UTF-8\">\n";
		ret += "		<title>"+ titre +"</title>\n";
		ret += "		<link href=\"tab.css\" rel=\"stylesheet\">\n";
		ret += "	</head>\n";
		ret += "	<body>\n";
		ret += "		<header>";
		ret += "			<h1>init dev</h1>";
		ret += "		</header>\n";
		ret += "		<main>\n";
		return ret;
	}

	private String foot() {
		String ret ="";
		ret += "		</main>\n";
		ret += "	</body>\n";
		ret += "</html>\n";
		return ret;
	}

	/*----------------------------------------------------------------------------------------------------------------*/
	/*                                                                                                                */
	/*                                                                                                                */
	/*                                                      CSV                                                       */
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
	public URL exportIntervenantCsv( String nomFichier, String chemin)
	{
		String body ="";
		Map<Intervenant,List<Intervention>> hmIntervenants = model.getIntervenantInterventions();
		
		body += "	nom,catégorie,s1 théorique,s1 reel,s3 théorique,s3 reel,s5 théorique,s5 reel,sTotal théorique,sTotal reel,s2 théorique,s2 reel,s4 théorique,s4 reel,s6 théorique,s6 reel,sTotal théorique,sTotal reel,minimum d'heures,total théorique,total reel, maximum d'heure\n";
		
		for (Intervenant interv : hmIntervenants.keySet()) {
			body+=sSemestreCsv(interv, hmIntervenants.get(interv));
		}
		return this.ecrireFichier(chemin + nomFichier + ".csv", body);
	}

	private String sSemestreCsv(Intervenant intervenant,List<Intervention> ensIntervention)
	{
		String ret ="";
		ret += intervenant.getNom() + " " + intervenant.getPrenom() + ",";
		ret += model.getHmCategories().get(intervenant.getIdCategorie()) + ",";
		
		double s1theorique = 0;
		double s1reel      = 0;
		double s2theorique = 0;
		double s2reel      = 0;
		double s3theorique = 0;
		double s3reel      = 0;
		double s4theorique = 0;
		double s4reel      = 0;
		double s5theorique = 0;
		double s5reel      = 0;
		double s6theorique = 0;
		double s6reel      = 0;
		
		for (Intervention intervention : ensIntervention) {
			double addReel      = reelIntervention(intervenant,intervention);
			double addTheorique = theoriqueIntervention(intervenant,intervention);
			switch (((model.getHmModules().get(intervention.getIdModule()).getIdSemestre()+5) %6) +1) { // on s'assure que le semestre est bien compris entre 1 et 6
				case 1 : s1reel += addReel; s1theorique += addTheorique; break;
				case 2 : s2reel += addReel; s2theorique += addTheorique; break;
				case 3 : s3reel += addReel; s3theorique += addTheorique; break;
				case 4 : s4reel += addReel; s4theorique += addTheorique; break;
				case 5 : s5reel += addReel; s5theorique += addTheorique; break;
				case 6 : s6reel += addReel; s6theorique += addTheorique; break;
			}
		}
		ret += s1theorique +                                                                       ","; // s1 theorique
		ret += s1reel      +                                                                       ","; // s1 reel
		ret += s3theorique +                                                                       ","; // s3 theorique
		ret += s3reel      +                                                                       ","; // s3 reel
		ret += s5reel      +                                                                       ","; // s5 theorique
		ret += s5theorique +                                                                       ","; // s5 reel
		ret += s1theorique + s3theorique + s5theorique +                                           ","; // total theorique partie 1
		ret += s1reel      + s3reel      + s5reel      +                                           ","; // total reel      partie 1
		ret += s2reel      +                                                                       ","; // s2 theorique
		ret += s2theorique +                                                                       ","; // s2 reel
		ret += s4reel      +                                                                       ","; // s4 theorique
		ret += s4theorique +                                                                       ","; // s4 reel
		ret += s6reel      +                                                                       ","; // s6 theorique
		ret += s6theorique +                                                                       ","; // s6 reel
		ret += s2theorique + s4theorique + s6theorique +                                           ","; // total theorique partie 2
		ret += s2reel      + s4reel      + s6reel      +                                           ","; // total reel      partie 2
		ret += hMin(intervenant) +                                                                 ","; // minimum d'heure
		ret += s1theorique + s2theorique + s3theorique + s4theorique + s5theorique + s6theorique + ","; // total theorique
		ret += s1reel      + s2reel      + s3reel      + s4reel      + s5reel      + s6reel      + ","; // total reel
		ret += hMax(intervenant) +                                                                 ","; // maximum d'heure
		return ret + "\n";
	}
	
	private double reelIntervention(Intervenant intervenant, Intervention intervention)
	{
		Categorie categ = model.getHmCategories().get(intervenant.getIdCategorie());
		return intervention.getNbSemaines()*intervention.getNbGroupe()*categ.getRatioTp();
	}

	private double theoriqueIntervention(Intervenant intervenant, Intervention intervention)
	{
		return intervention.getNbSemaines()*intervention.getNbGroupe();
	}
	
	private double hMin(Intervenant interv)
	{
		if (interv.getIdCategorie()==3)
		{
			return interv.gethMin();
		}
		else
		{
			return model.getHmCategories().get(interv.getIdCategorie()).gethMin();
		}
	}
	
	private double hMax(Intervenant interv)
	{
		if (interv.getIdCategorie()==3)
		{
			return interv.gethMax();
		}
		else
		{
			return model.getHmCategories().get(interv.getIdCategorie()).gethMax();
		}
	}
	
	/*----------------------------------------------------------------------------------------------------------------*/
	/*                                                                                                                */
	/*                                                                                                                */
	/*                                                     autre                                                      */
	/*                                                                                                                */
	/*                                                                                                                */
	/*----------------------------------------------------------------------------------------------------------------*/
	
	/**
	 * Écrit des données dans un fichier spécifié en utilisant un PrintWriter.
	 * 
	 * @param nomFichierDestination Le nom du fichier dans lequel écrire les données.
	 * @param body contenue du Fichier
	 */
	private URL ecrireFichier(String nomFichierDestination, String body) {
		try (PrintWriter pw = new PrintWriter(new FileOutputStream(nomFichierDestination))) {
			pw.println(body);
			return new URL("file:" + nomFichierDestination);
		} catch (Exception e) {
			System.out.println(nomFichierDestination);
			e.printStackTrace();
			return null;
		}
	}

}
