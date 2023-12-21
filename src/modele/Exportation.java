package modele;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import controleur.Controleur;
import vue.ResourceManager;

import java.util.Comparator;
import java.util.HashMap;

public class Exportation {
	private Modele model;

	/**
	 * constructeur de l'exportation
	 * 
	 * @throws SQLException
	 */
	public Exportation(Modele model) {
		this.model = model;
	}

	/*----------------------------------------------------------------------------------------------------------------*/
	/*                                                                                                                */
	/*                                                                                                                */
	/* HTML */
	/*                                                                                                                */
	/*                                                                                                                */
	/*----------------------------------------------------------------------------------------------------------------*/

	/*--------------*/
	/* Intervenants */
	/*--------------*/

	/**
	 * méthode pour obtenir le fichier d'exportation
	 * <a href="https://fr.wikipedia.org/wiki/Hypertext_Markup_Language"> html </a>
	 * d'un {@link Intervenant}
	 * 
	 * @param idModule   identifiant du {@link Intervenant} à exporter
	 * @param nomFichier nom du fichier de sortie
	 */
	public URL exportIntervenantHtml(int idIntervenant, String nomFichier, String chemin) {
		String body = "";
		body += "			<table border=\"1\">\n";
		body += "				<thead>\n";
		body += "					<tr>\n";
		body += "						<th> Module           </th>\n";
		body += "						<th colspan=\"2\"> CM               </th>\n";
		body += "						<th colspan=\"2\"> TD               </th>\n";
		body += "						<th colspan=\"2\"> TP               </th>\n";
		body += "						<th colspan=\"2\"> REH              </th>\n";
		body += "						<th colspan=\"2\"> SAE              </th>\n";
		body += "						<th colspan=\"2\"> tutorat          </th>\n";
		body += "						<th colspan=\"2\"> heure ponctuelle </th>\n";
		body += "						<th colspan=\"2\"> total            </th>\n";
		body += "					</tr>\n";
		body += "					<tr>\n";
		body += "						<th></th>\n";
		body += "						<th> reel               </th>\n";
		body += "						<th> théorique               </th>\n";
		body += "						<th> reel               </th>\n";
		body += "						<th> théorique               </th>\n";
		body += "						<th> reel               </th>\n";
		body += "						<th> théorique               </th>\n";
		body += "						<th> reel               </th>\n";
		body += "						<th> théorique               </th>\n";
		body += "						<th> reel               </th>\n";
		body += "						<th> théorique               </th>\n";
		body += "						<th> reel               </th>\n";
		body += "						<th> théorique               </th>\n";
		body += "						<th> reel               </th>\n";
		body += "						<th> théorique               </th>\n";
		body += "						<th> reel               </th>\n";
		body += "						<th> théorique               </th>\n";
		body += "					</tr>\n";
		body += "				</thead>\n";
		body += "				<tbody>\n";

		Map<Module, List<Intervention>> modulesInter = this.model.getModulesIntervenant(idIntervenant);// obtenir tout
																										// les modules
																										// au quel
																										// l'intervenant
																										// est lié
		List<Module> ensModule = new ArrayList<>(modulesInter.keySet());

		Collections.sort(ensModule, new Module(0,"","",false,0,0,0).new ModuleComparator()); // Module qui n'existe pas
																							// car id à 0

		Intervenant intervenant = this.model.getHmIntervenants().get(idIntervenant);
		int debut = 0;
		for (int cpt = 0; cpt < ensModule.size(); cpt++) {
			if (ensModule.get(cpt).getIdSemestre() > ensModule.get(debut).getIdSemestre()) {
				body += sSemestreHTML(ensModule.subList(debut, cpt), modulesInter, intervenant);
				debut = cpt;
			}
		}
		if (!ensModule.isEmpty()) {
			body += sSemestreHTML(ensModule.subList(debut, ensModule.size()), modulesInter, intervenant);
		}
		body += "				</tbody>\n";

		double cmReel = 0;
		double cmTheorique = 0;
		double tdReel = 0;
		double tdTheorique = 0;
		double tpReel = 0;
		double tpTheorique = 0;
		double rehReel = 0;
		double rehTheorique = 0;
		double saeReel = 0;
		double saeTheorique = 0;
		double tutoratReel = 0;
		double tutoratTheorique = 0;
		double hPonctuelReel = 0;
		double hPonctuelTheorique = 0;
		double totalReel = 0;
		double totalTheorique = 0;

		for (Module module : ensModule) {
			for (Intervention intervention : modulesInter.get(module)) {
				double addReel = reelIntervention(intervenant, intervention);
				double addTheorique = theoriqueIntervention(intervenant, intervention);
				switch (intervention.getIdTypeCours()) {
					case 3:
						cmReel += addReel;
						cmTheorique += addTheorique;
						break;
					case 1:
						tdReel += addReel;
						tdTheorique += addTheorique;
						break;
					case 2:
						tpReel += addReel;
						tpTheorique += addTheorique;
						break;
					case 5:
						rehReel += addReel;
						rehTheorique += addTheorique;
						break;
					case 6:
						saeReel += addReel;
						saeTheorique += addTheorique;
						break;
					case 4:
						tutoratReel += addReel;
						tutoratTheorique += addTheorique;
						break;
					case 7:
						hPonctuelReel += addReel;
						hPonctuelTheorique += addTheorique;
						break;
				}
			}
			totalReel = cmReel + tdReel + tpReel + rehReel + saeReel + tutoratReel + hPonctuelReel;
			totalTheorique = cmTheorique + tdTheorique + tpTheorique + rehTheorique + saeTheorique + tutoratTheorique
					+ hPonctuelTheorique;
		}

		body += "				<tfoot>\n";
		body += "					<tr>\n";
		body += "						<td> Total </td>\n";
		body += "						<td> " + cmReel + " </td>\n";
		body += "						<td> " + cmTheorique + " </td>\n";
		body += "						<td> " + tdReel + " </td>\n";
		body += "						<td> " + tdTheorique + " </td>\n";
		body += "						<td> " + tpReel + " </td>\n";
		body += "						<td> " + tpTheorique + " </td>\n";
		body += "						<td> " + rehReel + " </td>\n";
		body += "						<td> " + rehTheorique + " </td>\n";
		body += "						<td> " + saeReel + " </td>\n";
		body += "						<td> " + saeTheorique + " </td>\n";
		body += "						<td> " + tutoratReel + " </td>\n";
		body += "						<td> " + tutoratTheorique + " </td>\n";
		body += "						<td> " + hPonctuelReel + " </td>\n";
		body += "						<td> " + hPonctuelTheorique + " </td>\n";
		body += "						<td> " + totalReel + " </td>\n";
		body += "						<td> " + totalTheorique + " </td>\n";
		body += "					</tr>\n";
		body += "				</tfoot>\n";
		body += "			</table>\n";
		this.cssGenerator(chemin);
		return this.ecrireFichier(chemin + nomFichier + ".html", this.head(nomFichier) + body + this.foot());
	}

	private String sSemestreHTML(List<Module> ensModule, Map<Module, List<Intervention>> modulesInter,
			Intervenant intervenant) {
		double cmReel = 0;
		double cmTheorique = 0;
		double tdReel = 0;
		double tdTheorique = 0;
		double tpReel = 0;
		double tpTheorique = 0;
		double rehReel = 0;
		double rehTheorique = 0;
		double saeReel = 0;
		double saeTheorique = 0;
		double tutoratReel = 0;
		double tutoratTheorique = 0;
		double hPonctuelReel = 0;
		double hPonctuelTheorique = 0;
		double totalReel = 0;
		double totalTheorique = 0;

		String ret = "";
		for (Module mod : ensModule) {
			ret += sModuleHTML(mod, modulesInter.get(mod), intervenant);
		}

		for (Module module : ensModule) {
			for (Intervention intervention : modulesInter.get(module)) {
				double addReel = reelIntervention(intervenant, intervention);
				double addTheorique = theoriqueIntervention(intervenant, intervention);
				switch (intervention.getIdTypeCours()) {
					case 3:
						cmReel += addReel;
						cmTheorique += addTheorique;
						break;
					case 1:
						tdReel += addReel;
						tdTheorique += addTheorique;
						break;
					case 2:
						tpReel += addReel;
						tpTheorique += addTheorique;
						break;
					case 5:
						rehReel += addReel;
						rehTheorique += addTheorique;
						break;
					case 6:
						saeReel += addReel;
						saeTheorique += addTheorique;
						break;
					case 4:
						tutoratReel += addReel;
						tutoratTheorique += addTheorique;
						break;
					case 7:
						hPonctuelReel += addReel;
						hPonctuelTheorique += addTheorique;
						break;
				}
			}
			totalReel = cmReel + tdReel + tpReel + rehReel + saeReel + tutoratReel + hPonctuelReel;
			totalTheorique = cmTheorique + tdTheorique + tpTheorique + rehTheorique + saeTheorique + tutoratTheorique
					+ hPonctuelTheorique;
		}

		ret += "				<tr class=\"semestre" + ensModule.get(0).getIdSemestre() + "\">\n";
		ret += "					<td> Total Semestre " + (((ensModule.get(0).getIdSemestre() + 5) % 6) + 1)
				+ " </td>\n";
		ret += "					<td> " + cmReel + " </td>\n";
		ret += "					<td> " + cmTheorique + " </td>\n";
		ret += "					<td> " + tdReel + " </td>\n";
		ret += "					<td> " + tdTheorique + " </td>\n";
		ret += "					<td> " + tpReel + " </td>\n";
		ret += "					<td> " + tpTheorique + " </td>\n";
		ret += "					<td> " + rehReel + " </td>\n";
		ret += "					<td> " + rehTheorique + " </td>\n";
		ret += "					<td> " + saeReel + " </td>\n";
		ret += "					<td> " + saeTheorique + " </td>\n";
		ret += "					<td> " + tutoratReel + " </td>\n";
		ret += "					<td> " + tutoratTheorique + " </td>\n";
		ret += "					<td> " + hPonctuelReel + " </td>\n";
		ret += "					<td> " + hPonctuelTheorique + " </td>\n";
		ret += "					<td> " + totalReel + " </td>\n";
		ret += "					<td> " + totalTheorique + " </td>\n";
		ret += "				</tr>\n";

		return ret;
	}

	/**
	 * méthode pour avoir un {@link Module} sous forme de {@link String}
	 * 
	 * @param mod {@link Module} a transformer
	 * @return un {@link String} contenant le nom du {@link Module} et ???
	 */
	private String sModuleHTML(Module mod, List<Intervention> lstIntervention, Intervenant intervenant) {
		double cmReel = 0;
		double cmTheorique = 0;
		double tdReel = 0;
		double tdTheorique = 0;
		double tpReel = 0;
		double tpTheorique = 0;
		double rehReel = 0;
		double rehTheorique = 0;
		double saeReel = 0;
		double saeTheorique = 0;
		double tutoratReel = 0;
		double tutoratTheorique = 0;
		double hPonctuelReel = 0;
		double hPonctuelTheorique = 0;
		double totalReel = 0;
		double totalTheorique = 0;

		for (Intervention intervention : lstIntervention) {
			double addReel = reelIntervention(intervenant, intervention);
			double addTheorique = theoriqueIntervention(intervenant, intervention);
			switch (intervention.getIdTypeCours()) {
				case 3:
					cmReel += addReel;
					cmTheorique += addTheorique;
					break;
				case 1:
					tdReel += addReel;
					tdTheorique += addTheorique;
					break;
				case 2:
					tpReel += addReel;
					tpTheorique += addTheorique;
					break;
				case 5:
					rehReel += addReel;
					rehTheorique += addTheorique;
					break;
				case 6:
					saeReel += addReel;
					saeTheorique += addTheorique;
					break;
				case 4:
					tutoratReel += addReel;
					tutoratTheorique += addTheorique;
					break;
				case 7:
					hPonctuelReel += addReel;
					hPonctuelTheorique += addTheorique;
					break;
			}
		}
		totalReel = cmReel + tdReel + tpReel + rehReel + saeReel + tutoratReel + hPonctuelReel;
		totalTheorique = cmTheorique + tdTheorique + tpTheorique + rehTheorique + saeTheorique + tutoratTheorique
				+ hPonctuelTheorique;
		String ret = "";
		String tab = "					";
		ret += "				<tr class=\"semestre" + mod.getIdSemestre() + " \\\">\n";
		ret += "					<th> " + mod.getCode() + " " + mod.getNom() + " </th>\n";
		switch (mod.getIdTypeModule()) {
			case 1:
				ret += tab + "<td> " + cmReel + " </td>\n";
				ret += tab + "<td> " + cmTheorique + " </td>\n";
				ret += tab + "<td> " + tdReel + " </td>\n";
				ret += tab + "<td> " + tdTheorique + " </td>\n";
				ret += tab + "<td> " + tpReel + " </td>\n";
				ret += tab + "<td> " + tpTheorique + " </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td> " + tutoratReel + " </td>\n";
				ret += tab + "<td> " + tutoratTheorique + " </td>\n";
				ret += tab + "<td> " + hPonctuelReel + " </td>\n";
				ret += tab + "<td> " + hPonctuelTheorique + " </td>\n";
				ret += tab + "<td> " + totalReel + " </td>\n";
				ret += tab + "<td> " + totalTheorique + " </td>\n";
				break;

			case 2:
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td> " + saeReel + " </td>\n";
				ret += tab + "<td> " + saeTheorique + " </td>\n";
				ret += tab + "<td> " + tutoratReel + " </td>\n";
				ret += tab + "<td> " + tutoratTheorique + " </td>\n";
				ret += tab + "<td> " + hPonctuelReel + " </td>\n";
				ret += tab + "<td> " + hPonctuelTheorique + " </td>\n";
				ret += tab + "<td> " + totalReel + " </td>\n";
				ret += tab + "<td> " + totalTheorique + " </td>\n";
				break;

			case 3:
				ret += tab + "<td> " + cmReel + " </td>\n";
				ret += tab + "<td> " + cmTheorique + " </td>\n";
				ret += tab + "<td> " + tdReel + " </td>\n";
				ret += tab + "<td> " + tdTheorique + " </td>\n";
				ret += tab + "<td> " + tpReel + " </td>\n";
				ret += tab + "<td> " + tpTheorique + " </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td> " + hPonctuelReel + " </td>\n";
				ret += tab + "<td> " + hPonctuelTheorique + " </td>\n";
				ret += tab + "<td> " + totalReel + " </td>\n";
				ret += tab + "<td> " + totalTheorique + " </td>\n";
				break;

			case 4:
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td> " + rehReel + " </td>\n";
				ret += tab + "<td> " + rehTheorique + " </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td> " + tutoratReel + " </td>\n";
				ret += tab + "<td> " + tutoratTheorique + " </td>\n";
				ret += tab + "<td> " + hPonctuelReel + " </td>\n";
				ret += tab + "<td> " + hPonctuelTheorique + " </td>\n";
				ret += tab + "<td> " + totalReel + " </td>\n";
				ret += tab + "<td> " + totalTheorique + " </td>\n";
				break;
		}
		ret += "				</tr>\n";
		return ret;
	}

	/*---------*/
	/* modules */
	/*---------*/

	/**
	 * méthode pour obtenir le fichier d'exportation
	 * <a href="https://fr.wikipedia.org/wiki/Hypertext_Markup_Language"> html </a>
	 * d'un {@link Module}
	 * 
	 * @param idModule   identifiant du {@link Module} à exporter
	 * @param nomFichier nom du fichier de sortie
	 */
	public URL exportModuleHTML(int idModule, String nomFichier, String chemin) {
		Module mod = this.model.getHmModules().get(idModule);
		return switch (mod.getIdTypeModule()) {
			case 1  -> exportPPPHTML(mod, nomFichier, chemin);
			case 2  -> exportSAEHTML(mod, nomFichier, chemin);
			case 3  -> exportNormalHTML(mod, nomFichier, chemin);
			case 4  -> exportStageHTML(mod, nomFichier, chemin);
			default -> null;
		};
	}

	private URL exportPPPHTML(Module mod, String nomFichier, String chemin) {
		String body = "";
		body += "			<table border=\"1\">\n";
		body += "				<thead>\n";
		body += "					<tr>\n";
		body += "						<th> Intervenant           </th>\n";
		body += "						<th colspan=\"2\"> CM               </th>\n";
		body += "						<th colspan=\"2\"> TD               </th>\n";
		body += "						<th colspan=\"2\"> TP               </th>\n";
		body += "						<th colspan=\"2\"> tutorat          </th>\n";
		body += "						<th colspan=\"2\"> heure ponctuelle </th>\n";
		body += "						<th colspan=\"2\"> total            </th>\n";
		body += "					</tr>\n";
		body += "					<tr>\n";
		body += "						<th></th>\n";
		body += "						<th> reel               </th>\n";
		body += "						<th> théorique               </th>\n";
		body += "						<th> reel               </th>\n";
		body += "						<th> théorique               </th>\n";
		body += "						<th> reel               </th>\n";
		body += "						<th> théorique               </th>\n";
		body += "						<th> reel               </th>\n";
		body += "						<th> théorique               </th>\n";
		body += "						<th> reel               </th>\n";
		body += "						<th> théorique               </th>\n";
		body += "						<th> reel               </th>\n";
		body += "						<th> théorique               </th>\n";
		body += "					</tr>\n";
		body += "				</thead>\n";
		body += "				<tbody>\n";

		Map<Intervenant, List<Intervention>> hmModuleIntervenant = model.getIntervenantsModule(mod.getId());

		double totcmReel = 0;
		double totcmTheorique = 0;
		double tottdReel = 0;
		double tottdTheorique = 0;
		double tottpReel = 0;
		double tottpTheorique = 0;
		double tottutoratReel = 0;
		double tottutoratTheorique = 0;
		double tothPonctuelReel = 0;
		double tothPonctuelTheorique = 0;
		double tottotalReel = 0;
		double tottotalTheorique = 0;

		for (Intervenant intervenant : hmModuleIntervenant.keySet()) {
			double cmReel = 0;
			double cmTheorique = 0;
			double tdReel = 0;
			double tdTheorique = 0;
			double tpReel = 0;
			double tpTheorique = 0;
			double tutoratReel = 0;
			double tutoratTheorique = 0;
			double hPonctuelReel = 0;
			double hPonctuelTheorique = 0;
			double totalReel = 0;
			double totalTheorique = 0;

			for (Intervention intervention : hmModuleIntervenant.get(intervenant)) {
				double addReel = reelIntervention(intervenant, intervention);
				double addTheorique = theoriqueIntervention(intervenant, intervention);
				switch (intervention.getIdTypeCours()) {
					case 3:
						cmReel += addReel;
						cmTheorique += addTheorique;
						break;
					case 1:
						tdReel += addReel;
						tdTheorique += addTheorique;
						break;
					case 2:
						tpReel += addReel;
						tpTheorique += addTheorique;
						break;
					case 4:
						tutoratReel += addReel;
						tutoratTheorique += addTheorique;
						break;
					case 7:
						hPonctuelReel += addReel;
						hPonctuelTheorique += addTheorique;
						break;
				}
			}
			totalReel = cmReel + tdReel + tpReel + tutoratReel + hPonctuelReel;
			totalTheorique = cmTheorique + tdTheorique + tpTheorique + tutoratTheorique + hPonctuelTheorique;

			totcmReel += cmReel;
			totcmTheorique += cmTheorique;
			tottdReel += tdReel;
			tottdTheorique += tdTheorique;
			tottpReel += tpReel;
			tottpTheorique += tpTheorique;
			tottutoratReel += tutoratReel;
			tottutoratTheorique += tutoratTheorique;
			tothPonctuelReel += hPonctuelReel;
			tothPonctuelTheorique += hPonctuelTheorique;
			tottotalReel += totalReel;
			tottotalTheorique += totalTheorique;

			body += "					<tr>\n";
			body += "						<th> " + intervenant.getNom() + " " + intervenant.getPrenom() + " </th>\n";
			body += "						<td> " + cmReel + " </td>\n";
			body += "						<td> " + cmTheorique + " </td>\n";
			body += "						<td> " + tdReel + " </td>\n";
			body += "						<td> " + tdTheorique + " </td>\n";
			body += "						<td> " + tpReel + " </td>\n";
			body += "						<td> " + tpTheorique + " </td>\n";
			body += "						<td> " + tutoratReel + " </td>\n";
			body += "						<td> " + tutoratTheorique + " </td>\n";
			body += "						<td> " + hPonctuelReel + " </td>\n";
			body += "						<td> " + hPonctuelTheorique + " </td>\n";
			body += "						<td> " + totalReel + " </td>\n";
			body += "						<td> " + totalTheorique + " </td>\n";
			body += "					</tr>\n";

		}
		body += "				</tbody>\n";

		body += "				<tfoot>\n";
		body += "					<tr>\n";
		body += "						<td> Total </td>\n";
		body += "						<td> " + totcmReel + " </td>\n";
		body += "						<td> " + totcmTheorique + " </td>\n";
		body += "						<td> " + tottdReel + " </td>\n";
		body += "						<td> " + tottdTheorique + " </td>\n";
		body += "						<td> " + tottpReel + " </td>\n";
		body += "						<td> " + tottpTheorique + " </td>\n";
		body += "						<td> " + tottutoratReel + " </td>\n";
		body += "						<td> " + tottutoratTheorique + " </td>\n";
		body += "						<td> " + tothPonctuelReel + " </td>\n";
		body += "						<td> " + tothPonctuelTheorique + " </td>\n";
		body += "						<td> " + tottotalReel + " </td>\n";
		body += "						<td> " + tottotalTheorique + " </td>\n";
		body += "					</tr>\n";
		body += "				</tfoot>\n";
		body += "			</table>\n";
		this.cssGenerator(chemin);
		return this.ecrireFichier(chemin + nomFichier + ".html", this.head(nomFichier) + body + this.foot());
	}

	private URL exportSAEHTML(Module mod, String nomFichier, String chemin) {
		String body = "";
		body += "			<table border=\"1\">\n";
		body += "				<thead>\n";
		body += "					<tr>\n";
		body += "					<tr>\n";
		body += "						<th> Intervenant           </th>\n";
		body += "						<th colspan=\"2\"> SAE              </th>\n";
		body += "						<th colspan=\"2\"> tutorat          </th>\n";
		body += "						<th colspan=\"2\"> heure ponctuelle </th>\n";
		body += "						<th colspan=\"2\"> total            </th>\n";
		body += "					</tr>\n";
		body += "					<tr>\n";
		body += "						<th></th>\n";
		body += "						<th> reel               </th>\n";
		body += "						<th> théorique               </th>\n";
		body += "						<th> reel               </th>\n";
		body += "						<th> théorique               </th>\n";
		body += "						<th> reel               </th>\n";
		body += "						<th> théorique               </th>\n";
		body += "						<th> reel               </th>\n";
		body += "						<th> théorique               </th>\n";
		body += "					</tr>\n";
		body += "				</thead>\n";
		body += "				<tbody>\n";

		Map<Intervenant, List<Intervention>> hmModuleIntervenant = model.getIntervenantsModule(mod.getId());

		double totsaeReel = 0;
		double totsaeTheorique = 0;
		double tottutoratReel = 0;
		double tottutoratTheorique = 0;
		double tothPonctuelReel = 0;
		double tothPonctuelTheorique = 0;
		double tottotalReel = 0;
		double tottotalTheorique = 0;

		for (Intervenant intervenant : hmModuleIntervenant.keySet()) {
			double saeReel = 0;
			double saeTheorique = 0;
			double tutoratReel = 0;
			double tutoratTheorique = 0;
			double hPonctuelReel = 0;
			double hPonctuelTheorique = 0;
			double totalReel = 0;
			double totalTheorique = 0;

			for (Intervention intervention : hmModuleIntervenant.get(intervenant)) {
				double addReel = reelIntervention(intervenant, intervention);
				double addTheorique = theoriqueIntervention(intervenant, intervention);
				switch (intervention.getIdTypeCours()) {
					case 4:
						tutoratReel += addReel;
						tutoratTheorique += addTheorique;
						break;
					case 6:
						saeReel += addReel;
						saeTheorique += addTheorique;
						break;
					case 7:
						hPonctuelReel += addReel;
						hPonctuelTheorique += addTheorique;
						break;
				}
			}
			totalReel = tutoratReel + hPonctuelReel + saeReel;
			totalTheorique = tutoratTheorique + hPonctuelTheorique + saeTheorique;

			tottutoratReel += tutoratReel;
			tottutoratTheorique += tutoratTheorique;
			totsaeReel += saeReel;
			totsaeTheorique += saeTheorique;
			tothPonctuelReel += tothPonctuelReel;
			tothPonctuelTheorique += tothPonctuelTheorique;
			tottotalReel += totalReel;
			tottotalTheorique += totalTheorique;

			body += "					<tr>\n";
			body += "						<th> " + intervenant.getNom() + " " + intervenant.getPrenom() + " </th>\n";
			body += "						<td> " + tutoratReel + " </td>\n";
			body += "						<td> " + tutoratTheorique + " </td>\n";
			body += "						<td> " + saeReel + " </td>\n";
			body += "						<td> " + saeTheorique + " </td>\n";
			body += "						<td> " + hPonctuelReel + " </td>\n";
			body += "						<td> " + hPonctuelTheorique + " </td>\n";
			body += "						<td> " + totalReel + " </td>\n";
			body += "						<td> " + totalTheorique + " </td>\n";
			body += "					</tr>\n";

		}
		body += "				</tbody>\n";

		body += "				<tfoot>\n";
		body += "					<tr>\n";
		body += "						<td> Total </td>\n";
		body += "						<td> " + tottutoratReel + " </td>\n";
		body += "						<td> " + tottutoratTheorique + " </td>\n";
		body += "						<td> " + totsaeReel + " </td>\n";
		body += "						<td> " + totsaeTheorique + " </td>\n";
		body += "						<td> " + tothPonctuelReel + " </td>\n";
		body += "						<td> " + tothPonctuelTheorique + " </td>\n";
		body += "						<td> " + tottotalReel + " </td>\n";
		body += "						<td> " + tottotalTheorique + " </td>\n";
		body += "					</tr>\n";
		body += "				</tfoot>\n";
		body += "			</table>\n";
		this.cssGenerator(chemin);
		return this.ecrireFichier(chemin + nomFichier + ".html", this.head(nomFichier) + body + this.foot());
	}

	private URL exportNormalHTML(Module mod, String nomFichier, String chemin) {
		String body = "";
		body += "			<table border=\"1\">\n";
		body += "				<thead>\n";
		body += "					<tr>\n";
		body += "					<tr>\n";
		body += "						<th> Module           </th>\n";
		body += "						<th colspan=\"2\"> CM               </th>\n";
		body += "						<th colspan=\"2\"> TD               </th>\n";
		body += "						<th colspan=\"2\"> TP               </th>\n";
		body += "						<th colspan=\"2\"> heure ponctuelle </th>\n";
		body += "						<th colspan=\"2\"> total            </th>\n";
		body += "					</tr>\n";
		body += "					<tr>\n";
		body += "						<th></th>\n";
		body += "						<th> reel               </th>\n";
		body += "						<th> théorique               </th>\n";
		body += "						<th> reel               </th>\n";
		body += "						<th> théorique               </th>\n";
		body += "						<th> reel               </th>\n";
		body += "						<th> théorique               </th>\n";
		body += "						<th> reel               </th>\n";
		body += "						<th> théorique               </th>\n";
		body += "						<th> reel               </th>\n";
		body += "						<th> théorique               </th>\n";
		body += "					</tr>\n";
		body += "				</thead>\n";
		body += "				<tbody>\n";

		Map<Intervenant, List<Intervention>> hmModuleIntervenant = model.getIntervenantsModule(mod.getId());

		double totcmReel = 0;
		double totcmTheorique = 0;
		double tottdReel = 0;
		double tottdTheorique = 0;
		double tottpReel = 0;
		double tottpTheorique = 0;
		double tothPonctuelReel = 0;
		double tothPonctuelTheorique = 0;
		double tottotalReel = 0;
		double tottotalTheorique = 0;

		for (Intervenant intervenant : hmModuleIntervenant.keySet()) {
			double cmReel = 0;
			double cmTheorique = 0;
			double tdReel = 0;
			double tdTheorique = 0;
			double tpReel = 0;
			double tpTheorique = 0;
			double hPonctuelReel = 0;
			double hPonctuelTheorique = 0;
			double totalReel = 0;
			double totalTheorique = 0;

			for (Intervention intervention : hmModuleIntervenant.get(intervenant)) {
				double addReel = reelIntervention(intervenant, intervention);
				double addTheorique = theoriqueIntervention(intervenant, intervention);
				switch (intervention.getIdTypeCours()) {
					case 3:
						cmReel += addReel;
						cmTheorique += addTheorique;
						break;
					case 1:
						tdReel += addReel;
						tdTheorique += addTheorique;
						break;
					case 2:
						tpReel += addReel;
						tpTheorique += addTheorique;
						break;
					case 7:
						hPonctuelReel += addReel;
						hPonctuelTheorique += addTheorique;
						break;
				}
			}
			totalReel = cmReel + tdReel + tpReel + hPonctuelReel;
			totalTheorique = cmTheorique + tdTheorique + tpTheorique + hPonctuelTheorique;

			totcmReel += cmReel;
			totcmTheorique += cmTheorique;
			tottdReel += tdReel;
			tottdTheorique += tdTheorique;
			tottpReel += tpReel;
			tottpTheorique += tpTheorique;
			tothPonctuelReel += hPonctuelReel;
			tothPonctuelTheorique += hPonctuelTheorique;
			tottotalReel += totalReel;
			tottotalTheorique += totalTheorique;

			body += "					<tr>\n";
			body += "						<th> " + intervenant.getNom() + " " + intervenant.getPrenom() + " </th>\n";
			body += "						<td> " + cmReel + " </td>\n";
			body += "						<td> " + cmTheorique + " </td>\n";
			body += "						<td> " + tdReel + " </td>\n";
			body += "						<td> " + tdTheorique + " </td>\n";
			body += "						<td> " + tpReel + " </td>\n";
			body += "						<td> " + tpTheorique + " </td>\n";
			body += "						<td> " + hPonctuelReel + " </td>\n";
			body += "						<td> " + hPonctuelTheorique + " </td>\n";
			body += "						<td> " + totalReel + " </td>\n";
			body += "						<td> " + totalTheorique + " </td>\n";
			body += "					</tr>\n";

		}
		body += "				</tbody>\n";

		body += "				<tfoot>\n";
		body += "					<tr>\n";
		body += "						<td> Total </td>\n";
		body += "						<td> " + totcmReel + " </td>\n";
		body += "						<td> " + totcmTheorique + " </td>\n";
		body += "						<td> " + tottdReel + " </td>\n";
		body += "						<td> " + tottdTheorique + " </td>\n";
		body += "						<td> " + tottpReel + " </td>\n";
		body += "						<td> " + tottpTheorique + " </td>\n";
		body += "						<td> " + tothPonctuelReel + " </td>\n";
		body += "						<td> " + tothPonctuelTheorique + " </td>\n";
		body += "						<td> " + tottotalReel + " </td>\n";
		body += "						<td> " + tottotalTheorique + " </td>\n";
		body += "					</tr>\n";
		body += "				</tfoot>\n";
		body += "			</table>\n";
		this.cssGenerator(chemin);
		return this.ecrireFichier(chemin + nomFichier + ".html", this.head(nomFichier) + body + this.foot());
	}

	private URL exportStageHTML(Module mod, String nomFichier, String chemin) {
		String body = "";
		body += "			<table border=\"1\">\n";
		body += "				<thead>\n";
		body += "					<tr>\n";
		body += "						<th> Intervenant           </th>\n";
		body += "						<th colspan=\"2\"> REH              </th>\n";
		body += "						<th colspan=\"2\"> tutorat          </th>\n";
		body += "						<th colspan=\"2\"> heure ponctuelle </th>\n";
		body += "						<th colspan=\"2\"> total            </th>\n";
		body += "					</tr>\n";
		body += "					<tr>\n";
		body += "						<th></th>\n";
		body += "						<th> reel               </th>\n";
		body += "						<th> théorique               </th>\n";
		body += "						<th> reel               </th>\n";
		body += "						<th> théorique               </th>\n";
		body += "						<th> reel               </th>\n";
		body += "						<th> théorique               </th>\n";
		body += "						<th> reel               </th>\n";
		body += "						<th> théorique               </th>\n";
		body += "					</tr>\n";
		body += "				</thead>\n";
		body += "				<tbody>\n";

		Map<Intervenant, List<Intervention>> hmModuleIntervenant = model.getIntervenantsModule(mod.getId());

		double totrehReel = 0;
		double totrehTheorique = 0;
		double tottutoratReel = 0;
		double tottutoratTheorique = 0;
		double tothPonctuelReel = 0;
		double tothPonctuelTheorique = 0;
		double tottotalReel = 0;
		double tottotalTheorique = 0;

		for (Intervenant intervenant : hmModuleIntervenant.keySet()) {
			double rehReel = 0;
			double rehTheorique = 0;
			double tutoratReel = 0;
			double tutoratTheorique = 0;
			double hPonctuelReel = 0;
			double hPonctuelTheorique = 0;
			double totalReel = 0;
			double totalTheorique = 0;

			for (Intervention intervention : hmModuleIntervenant.get(intervenant)) {
				double addReel = reelIntervention(intervenant, intervention);
				double addTheorique = theoriqueIntervention(intervenant, intervention);
				switch (intervention.getIdTypeCours()) {
					case 5:
						rehReel += addReel;
						rehTheorique += addTheorique;
						break;
					case 4:
						tutoratReel += addReel;
						tutoratTheorique += addTheorique;
						break;
					case 7:
						hPonctuelReel += addReel;
						hPonctuelTheorique += addTheorique;
						break;
				}
			}
			totalReel = tutoratReel + hPonctuelReel + rehReel;
			totalTheorique = tutoratTheorique + hPonctuelTheorique + rehTheorique;

			tottutoratReel += tutoratReel;
			tottutoratTheorique += tutoratTheorique;
			totrehReel += rehReel;
			totrehTheorique += rehTheorique;
			tothPonctuelReel += tothPonctuelReel;
			tothPonctuelTheorique += tothPonctuelTheorique;
			tottotalReel += totalReel;
			tottotalTheorique += totalTheorique;

			body += "					<tr>\n";
			body += "						<th> " + intervenant.getNom() + " " + intervenant.getPrenom() + " </th>\n";
			body += "						<td> " + tutoratReel + " </td>\n";
			body += "						<td> " + tutoratTheorique + " </td>\n";
			body += "						<td> " + rehReel + " </td>\n";
			body += "						<td> " + rehTheorique + " </td>\n";
			body += "						<td> " + hPonctuelReel + " </td>\n";
			body += "						<td> " + hPonctuelTheorique + " </td>\n";
			body += "						<td> " + totalReel + " </td>\n";
			body += "						<td> " + totalTheorique + " </td>\n";
			body += "					</tr>\n";

		}
		body += "				</tbody>\n";

		body += "				<tfoot>\n";
		body += "					<tr>\n";
		body += "						<td> Total </td>\n";
		body += "						<td> " + tottutoratReel + " </td>\n";
		body += "						<td> " + tottutoratTheorique + " </td>\n";
		body += "						<td> " + totrehReel + " </td>\n";
		body += "						<td> " + totrehTheorique + " </td>\n";
		body += "						<td> " + tothPonctuelReel + " </td>\n";
		body += "						<td> " + tothPonctuelTheorique + " </td>\n";
		body += "						<td> " + tottotalReel + " </td>\n";
		body += "						<td> " + tottotalTheorique + " </td>\n";
		body += "					</tr>\n";
		body += "				</tfoot>\n";
		body += "			</table>\n";
		this.cssGenerator(chemin);
		return this.ecrireFichier(chemin + nomFichier + ".html", this.head(nomFichier) + body + this.foot());
	}

	/*-------*/
	/* autre */
	/*-------*/

	private URL cssGenerator(String chemin) {
		String css = ":root {\n";
		int cpt=0;
		for (Semestre semestre : model.getHmSemestres().values())
			css += "\t--semestre-" + ++cpt + ": " + semestre.getCouleur() + ";\n";
		return this.ecrireFichier(chemin + "tab.css", css + "}\n\n" + ResourceManager.TAB_TEMPLATE_CONTENT);
		/*s
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
		return this.ecrireFichier(chemin + "tab.css", ret);
		*/
	}

	private String head(String titre) {
		String ret = "";
		ret += "<!DOCTYPE html>\n";
		ret += "<html lang=\"en\">\n";
		ret += "	<head>\n";
		ret += "		<meta charset=\"UTF-8\">\n";
		ret += "		<title>" + titre + "</title>\n";
		ret += "		<link href=\"tab.css\" rel=\"stylesheet\">\n";
		ret += "	</head>\n";
		ret += "	<body>\n";
		ret += "		<header>\n";
		ret += "			<h1>" + titre + "</h1>\n";
		ret += "		</header>\n";
		ret += "		<main>\n";
		return ret;
	}

	private String foot() {
		String ret = "";
		ret += "		</main>\n";
		ret += "	</body>\n";
		ret += "</html>\n";
		return ret;
	}

	/*----------------------------------------------------------------------------------------------------------------*/
	/*                                                                                                                */
	/*                                                                                                                */
	/* CSV */
	/*                                                                                                                */
	/*                                                                                                                */
	/*----------------------------------------------------------------------------------------------------------------*/

	/*--------------*/
	/* Intervenants */
	/*--------------*/

	/**
	 * méthode pour obtenir le fichier d'exportation
	 * <a href="https://fr.wikipedia.org/wiki/Hypertext_Markup_Language"> html </a>
	 * d'un {@link Intervenant}
	 * 
	 * @param idModule   identifiant du {@link Intervenant} à exporter
	 * @param nomFichier nom du fichier de sortie
	 */
	public URL exportIntervenantCsv(String nomFichier, String chemin) {
		String body = "";
		Map<Intervenant, List<Intervention>> hmIntervenants = model.getIntervenantInterventions();

		body += "	nom,catégorie,s1 théorique,s1 reel,s3 théorique,s3 reel,s5 théorique,s5 reel,sTotal théorique,sTotal reel,s2 théorique,s2 reel,s4 théorique,s4 reel,s6 théorique,s6 reel,sTotal théorique,sTotal reel,minimum d'heures,total théorique,total reel, maximum d'heure\n";

		for (Intervenant interv : hmIntervenants.keySet()) {
			body += sSemestreCsv(interv, hmIntervenants.get(interv));
		}
		return this.ecrireFichier(chemin + nomFichier + ".csv", body);
	}

	private String sSemestreCsv(Intervenant intervenant, List<Intervention> ensIntervention) {
		String ret = "";
		ret += intervenant.getNom() + " " + intervenant.getPrenom() + ",";
		ret += model.getHmCategories().get(intervenant.getIdCategorie()) + ",";

		double s1theorique = 0;
		double s1reel = 0;
		double s2theorique = 0;
		double s2reel = 0;
		double s3theorique = 0;
		double s3reel = 0;
		double s4theorique = 0;
		double s4reel = 0;
		double s5theorique = 0;
		double s5reel = 0;
		double s6theorique = 0;
		double s6reel = 0;

		for (Intervention intervention : ensIntervention) {
			double addReel = reelIntervention(intervenant, intervention);
			double addTheorique = theoriqueIntervention(intervenant, intervention);
			switch (((model.getHmModules().get(intervention.getIdModule()).getIdSemestre() + 5) % 6) + 1) { // on s'assure que le semestre est bien compris entre 1 et 6
				case 1:
					s1reel += addReel;
					s1theorique += addTheorique;
					break;
				case 2:
					s2reel += addReel;
					s2theorique += addTheorique;
					break;
				case 3:
					s3reel += addReel;
					s3theorique += addTheorique;
					break;
				case 4:
					s4reel += addReel;
					s4theorique += addTheorique;
					break;
				case 5:
					s5reel += addReel;
					s5theorique += addTheorique;
					break;
				case 6:
					s6reel += addReel;
					s6theorique += addTheorique;
					break;
			}
		}
		ret += s1theorique + ","; // s1 theorique
		ret += s1reel + ","; // s1 reel
		ret += s3theorique + ","; // s3 theorique
		ret += s3reel + ","; // s3 reel
		ret += s5reel + ","; // s5 theorique
		ret += s5theorique + ","; // s5 reel
		ret += s1theorique + s3theorique + s5theorique + ","; // total theorique partie 1
		ret += s1reel + s3reel + s5reel + ","; // total reel partie 1
		ret += s2reel + ","; // s2 theorique
		ret += s2theorique + ","; // s2 reel
		ret += s4reel + ","; // s4 theorique
		ret += s4theorique + ","; // s4 reel
		ret += s6reel + ","; // s6 theorique
		ret += s6theorique + ","; // s6 reel
		ret += s2theorique + s4theorique + s6theorique + ","; // total theorique partie 2
		ret += s2reel + s4reel + s6reel + ","; // total reel partie 2
		ret += hMin(intervenant) + ","; // minimum d'heure
		ret += s1theorique + s2theorique + s3theorique + s4theorique + s5theorique + s6theorique + ","; // total theorique
		ret += s1reel + s2reel + s3reel + s4reel + s5reel + s6reel + ","; // total reel
		ret += hMax(intervenant) + ","; // maximum d'heure
		return ret + "\n";
	}

	/*----------------------------------------------------------------------------------------------------------------*/
	/*                                                                                                                */
	/*                                                                                                                */
	/* autre */
	/*                                                                                                                */
	/*                                                                                                                */
	/*----------------------------------------------------------------------------------------------------------------*/

	private double reelIntervention(Intervenant intervenant, Intervention intervention) {
		Categorie categ = model.getHmCategories().get(intervenant.getIdCategorie());
		double ret = categ.getRatioTp() *  intervention.getNbGroupe();
		if (intervention.getIdTypeCours()<4) ret = ret*intervention.getNbSemaines();
		return ret;
	}

	private double theoriqueIntervention(Intervenant intervenant, Intervention intervention) {
		double ret = intervention.getNbGroupe();
		if (intervention.getIdTypeCours()<4) ret = ret*intervention.getNbSemaines();
		return ret;
	}

	private double hMin(Intervenant interv) {
		if (interv.getIdCategorie() == 3) {
			return interv.gethMin();
		} else {
			return model.getHmCategories().get(interv.getIdCategorie()).gethMin();
		}
	}

	private double hMax(Intervenant interv) {
		if (interv.getIdCategorie() == 3) {
			return interv.gethMax();
		} else {
			return model.getHmCategories().get(interv.getIdCategorie()).gethMax();
		}
	}
	/**
	 * Écrit des données dans un fichier spécifié en utilisant un PrintWriter.
	 * 
	 * @param nomFichierDestination Le nom du fichier dans lequel écrire les
	 *                              données.
	 * @param body                  contenue du Fichier
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
