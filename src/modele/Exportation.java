package modele;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;

import vue.App;

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
	
	/*
	private static double round(double d) {
		double rounded = Math.ceil(d * 10.0) / 10.0;
		double decimal = rounded - ((int) rounded);
		if (decimal == 0.0)
			return rounded;
		decimal = Math.ceil(decimal * 10.0) / 10.0;
		if (decimal <= 0.5)
			rounded -= 0.1;
		return Math.ceil(rounded * 10.0) / 10.0;
	}
	*/
	
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
		body += "		<header>\n";
		body += "			<h1>" + nomFichier + "</h1>\n";
		body += "		</header>\n";
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
		
		// obtenir tout les modules au quel l'intervenant est lié
		Map<Module, List<Intervention>> modulesInter = this.model.getModulesIntervenant(idIntervenant);
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
		if (!ensModule.isEmpty())
			body += sSemestreHTML(ensModule.subList(debut, ensModule.size()), modulesInter, intervenant);
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
		body += "						<th> Total </th>\n";
		body += "						<td> " + Math.round(cmReel*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(cmTheorique*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tdReel*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tdTheorique*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tpReel*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tpTheorique*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(rehReel*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(rehTheorique*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(saeReel*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(saeTheorique*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tutoratReel*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tutoratTheorique*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(hPonctuelReel*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(hPonctuelTheorique*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(totalReel*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(totalTheorique*100.0)/100.0 + " </td>\n";
		body += "					</tr>\n";
		body += "				</tfoot>\n";
		body += "			</table>\n";
		this.cssGenerator(chemin);
		return this.ecrireFichier(chemin + nomFichier + ".html", this.head(nomFichier) + body + this.foot());
	}
	
	private String sSemestreHTML(List<Module> ensModule, Map<Module, List<Intervention>> modulesInter, Intervenant intervenant) {
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
		for (Module mod : ensModule)
			ret += sModuleHTML(mod, modulesInter.get(mod), intervenant);
		
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
		ret += "					<th> Total Semestre " + (((ensModule.get(0).getIdSemestre() + 5) % 6) + 1) + " </th>\n";
		ret += "					<td> " + Math.round(cmReel*100.0)/100.0 + " </td>\n";
		ret += "					<td> " + Math.round(cmTheorique*100.0)/100.0 + " </td>\n";
		ret += "					<td> " + Math.round(tdReel*100.0)/100.0 + " </td>\n";
		ret += "					<td> " + Math.round(tdTheorique*100.0)/100.0 + " </td>\n";
		ret += "					<td> " + Math.round(tpReel*100.0)/100.0 + " </td>\n";
		ret += "					<td> " + Math.round(tpTheorique*100.0)/100.0 + " </td>\n";
		ret += "					<td> " + Math.round(rehReel*100.0)/100.0 + " </td>\n";
		ret += "					<td> " + Math.round(rehTheorique*100.0)/100.0 + " </td>\n";
		ret += "					<td> " + Math.round(saeReel*100.0)/100.0 + " </td>\n";
		ret += "					<td> " + Math.round(saeTheorique*100.0)/100.0 + " </td>\n";
		ret += "					<td> " + Math.round(tutoratReel*100.0)/100.0 + " </td>\n";
		ret += "					<td> " + Math.round(tutoratTheorique*100.0)/100.0 + " </td>\n";
		ret += "					<td> " + Math.round(hPonctuelReel*100.0)/100.0 + " </td>\n";
		ret += "					<td> " + Math.round(hPonctuelTheorique*100.0)/100.0 + " </td>\n";
		ret += "					<td> " + Math.round(totalReel*100.0)/100.0 + " </td>\n";
		ret += "					<td> " + Math.round(totalTheorique*100.0)/100.0 + " </td>\n";
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
		totalTheorique = cmTheorique + tdTheorique + tpTheorique + rehTheorique + saeTheorique + tutoratTheorique + hPonctuelTheorique;
		String ret = "", tab = "\t".repeat(5);
		ret += "				<tr class=\"semestre" + mod.getIdSemestre() + " \">\n";
		ret += "					<th> " + mod.getCode()+ " " + mod.getNom() + " </th>\n";
		switch (mod.getIdTypeModule()) {
			case 1:
				ret += tab + "<td> " + Math.round(cmReel*100.0)/100.0 + " </td>\n";
				ret += tab + "<td> " + Math.round(cmTheorique*100.0)/100.0 + " </td>\n";
				ret += tab + "<td> " + Math.round(tdReel*100.0)/100.0 + " </td>\n";
				ret += tab + "<td> " + Math.round(tdTheorique*100.0)/100.0 + " </td>\n";
				ret += tab + "<td> " + Math.round(tpReel*100.0)/100.0 + " </td>\n";
				ret += tab + "<td> " + Math.round(tpTheorique*100.0)/100.0 + " </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td> " + Math.round(tutoratReel*100.0)/100.0 + " </td>\n";
				ret += tab + "<td> " + Math.round(tutoratTheorique*100.0)/100.0 + " </td>\n";
				ret += tab + "<td> " + Math.round(hPonctuelReel*100.0)/100.0 + " </td>\n";
				ret += tab + "<td> " + Math.round(hPonctuelTheorique*100.0)/100.0 + " </td>\n";
				ret += tab + "<td> " + Math.round(totalReel*100.0)/100.0 + " </td>\n";
				ret += tab + "<td> " + Math.round(totalTheorique*100.0)/100.0 + " </td>\n";
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
				ret += tab + "<td> " + Math.round(saeReel*100.0)/100.0 + " </td>\n";
				ret += tab + "<td> " + Math.round(saeTheorique*100.0)/100.0 + " </td>\n";
				ret += tab + "<td> " + Math.round(tutoratReel*100.0)/100.0 + " </td>\n";
				ret += tab + "<td> " + Math.round(tutoratTheorique*100.0)/100.0 + " </td>\n";
				ret += tab + "<td> " + Math.round(hPonctuelReel*100.0)/100.0 + " </td>\n";
				ret += tab + "<td> " + Math.round(hPonctuelTheorique*100.0)/100.0 + " </td>\n";
				ret += tab + "<td> " + Math.round(totalReel*100.0)/100.0 + " </td>\n";
				ret += tab + "<td> " + Math.round(totalTheorique*100.0)/100.0 + " </td>\n";
				break;
			case 3:
				ret += tab + "<td> " + Math.round(cmReel*100.0)/100.0 + " </td>\n";
				ret += tab + "<td> " + Math.round(cmTheorique*100.0)/100.0 + " </td>\n";
				ret += tab + "<td> " + Math.round(tdReel*100.0)/100.0 + " </td>\n";
				ret += tab + "<td> " + Math.round(tdTheorique*100.0)/100.0 + " </td>\n";
				ret += tab + "<td> " + Math.round(tpReel*100.0)/100.0 + " </td>\n";
				ret += tab + "<td> " + Math.round(tpTheorique*100.0)/100.0 + " </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td> " + Math.round(hPonctuelReel*100.0)/100.0 + " </td>\n";
				ret += tab + "<td> " + Math.round(hPonctuelTheorique*100.0)/100.0 + " </td>\n";
				ret += tab + "<td> " + Math.round(totalReel*100.0)/100.0 + " </td>\n";
				ret += tab + "<td> " + Math.round(totalTheorique*100.0)/100.0 + " </td>\n";
				break;
			case 4:
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td> " + Math.round(rehReel*100.0)/100.0 + " </td>\n";
				ret += tab + "<td> " + Math.round(rehTheorique*100.0)/100.0 + " </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td>  </td>\n";
				ret += tab + "<td> " + Math.round(tutoratReel*100.0)/100.0 + " </td>\n";
				ret += tab + "<td> " + Math.round(tutoratTheorique*100.0)/100.0 + " </td>\n";
				ret += tab + "<td> " + Math.round(hPonctuelReel*100.0)/100.0 + " </td>\n";
				ret += tab + "<td> " + Math.round(hPonctuelTheorique*100.0)/100.0 + " </td>\n";
				ret += tab + "<td> " + Math.round(totalReel*100.0)/100.0 + " </td>\n";
				ret += tab + "<td> " + Math.round(totalTheorique*100.0)/100.0 + " </td>\n";
				break;
		}
		return ret + "\t".repeat(4) + "</tr>\n";
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
			case 1  -> this.exportPPPHTML(mod, nomFichier, chemin);
			case 2  -> this.exportSAEHTML(mod, nomFichier, chemin);
			case 3  -> this.exportNormalHTML(mod, nomFichier, chemin);
			case 4  -> this.exportStageHTML(mod, nomFichier, chemin);
			default -> null;
		};
	}
	
	private URL exportPPPHTML(Module mod, String nomFichier, String chemin) {
		String body = "";
		body += "	<body>\n";
		body += "		<header class=\"semestre"+ mod.getIdSemestre() +"\" >\n";
		body += "			<h1>" + nomFichier + "</h1>\n";
		body += "		</header>\n";
		body += "		<main>\n";
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
			body += "						<td> " + Math.round(cmReel*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(cmTheorique*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(tdReel*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(tdTheorique*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(tpReel*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(tpTheorique*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(tutoratReel*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(tutoratTheorique*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(hPonctuelReel*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(hPonctuelTheorique*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(totalReel*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(totalTheorique*100.0)/100.0 + " </td>\n";
			body += "					</tr>\n";
		}
		body += "				</tbody>\n";
		
		body += "				<tfoot>\n";
		body += "					<tr>\n";
		body += "						<th> Total </th>\n";
		body += "						<td> " + Math.round(totcmReel*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(totcmTheorique*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tottdReel*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tottdTheorique*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tottpReel*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tottpTheorique*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tottutoratReel*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tottutoratTheorique*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tothPonctuelReel*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tothPonctuelTheorique*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tottotalReel*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tottotalTheorique*100.0)/100.0 + " </td>\n";
		body += "					</tr>\n";
		body += "				</tfoot>\n";
		body += "			</table>\n";
		this.cssGenerator(chemin);
		return this.ecrireFichier(chemin + nomFichier + ".html", this.head(nomFichier) + body + this.foot());
	}
	
	private URL exportSAEHTML(Module mod, String nomFichier, String chemin) {
		String body = "";
		body += "	<body>\n";
		body += "		<header class=\"semestre"+ mod.getIdSemestre() +"\" >\n";
		body += "			<h1>" + nomFichier + "</h1>\n";
		body += "		</header>\n";
		body += "		<main>\n";
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
			body += "						<td> " + Math.round(tutoratReel*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(tutoratTheorique*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(saeReel*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(saeTheorique*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(hPonctuelReel*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(hPonctuelTheorique*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(totalReel*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(totalTheorique*100.0)/100.0 + " </td>\n";
			body += "					</tr>\n";
		}
		body += "				</tbody>\n";
		
		body += "				<tfoot>\n";
		body += "					<tr>\n";
		body += "						<th> Total </th>\n";
		body += "						<td> " + Math.round(tottutoratReel*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tottutoratTheorique*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(totsaeReel*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(totsaeTheorique*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tothPonctuelReel*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tothPonctuelTheorique*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tottotalReel*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tottotalTheorique*100.0)/100.0 + " </td>\n";
		body += "					</tr>\n";
		body += "				</tfoot>\n";
		body += "			</table>\n";
		this.cssGenerator(chemin);
		return this.ecrireFichier(chemin + nomFichier + ".html", this.head(nomFichier) + body + this.foot());
	}
	
	private URL exportNormalHTML(Module mod, String nomFichier, String chemin) {
		String body = "";
		body += "	<body>\n";
		body += "		<header class=\"semestre"+ mod.getIdSemestre() +"\" >\n";
		body += "			<h1>" + nomFichier + "</h1>\n";
		body += "		</header>\n";
		body += "		<main>\n";
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
			body += "						<td> " + Math.round(cmReel*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(cmTheorique*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(tdReel*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(tdTheorique*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(tpReel*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(tpTheorique*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(hPonctuelReel*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(hPonctuelTheorique*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(totalReel*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(totalTheorique*100.0)/100.0 + " </td>\n";
			body += "					</tr>\n";
		}
		body += "				</tbody>\n";
		
		body += "				<tfoot>\n";
		body += "					<tr>\n";
		body += "						<th> Total </th>\n";
		body += "						<td> " + Math.round(totcmReel*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(totcmTheorique*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tottdReel*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tottdTheorique*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tottpReel*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tottpTheorique*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tothPonctuelReel*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tothPonctuelTheorique*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tottotalReel*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tottotalTheorique*100.0)/100.0 + " </td>\n";
		body += "					</tr>\n";
		body += "				</tfoot>\n";
		body += "			</table>\n";
		this.cssGenerator(chemin);
		return this.ecrireFichier(chemin + nomFichier + ".html", this.head(nomFichier) + body + this.foot());
	}
	
	private URL exportStageHTML(Module mod, String nomFichier, String chemin) {
		String body = "";
		body += "	<body>\n";
		body += "		<header class=\"semestre"+ mod.getIdSemestre() +"\" >\n";
		body += "			<h1>" + nomFichier + "</h1>\n";
		body += "		</header>\n";
		body += "		<main>\n";
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
					case 5 -> {
						rehReel += addReel;
						rehTheorique += addTheorique;
					}
					case 4 -> {
						tutoratReel += addReel;
						tutoratTheorique += addTheorique;
					}
					case 7 -> {
						hPonctuelReel += addReel;
						hPonctuelTheorique += addTheorique;
					}
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
			body += "						<td> " + Math.round(tutoratReel*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(tutoratTheorique*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(rehReel*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(rehTheorique*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(hPonctuelReel*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(hPonctuelTheorique*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(totalReel*100.0)/100.0 + " </td>\n";
			body += "						<td> " + Math.round(totalTheorique*100.0)/100.0 + " </td>\n";
			body += "					</tr>\n";
		}
		body += "				</tbody>\n";
		
		body += "				<tfoot>\n";
		body += "					<tr>\n";
		body += "						<th> Total </th>\n";
		body += "						<td> " + Math.round(tottutoratReel*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tottutoratTheorique*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(totrehReel*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(totrehTheorique*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tothPonctuelReel*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tothPonctuelTheorique*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tottotalReel*100.0)/100.0 + " </td>\n";
		body += "						<td> " + Math.round(tottotalTheorique*100.0)/100.0 + " </td>\n";
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
		String ret = "";
		ret += "thead {background-color: #4D61CC;color: #ffffff;}\n";
		ret += "tbody {background-color: #e4f0f5;}\n";
		ret += "tfoot {background-color: #758AFF;color: #ffffff;}\n";
		ret += "caption {padding: 10px;caption-side: bottom;}\n";
		ret += "table {margin:auto;border-collapse: collapse;border: 2px solid rgb(255, 255, 255);letter-spacing: 1px;font-family: sans-serif;font-size: 0.8rem;}\n";
		ret += "td,th {border: 1px solid rgb(190, 190, 190);padding: 5px 10px;}\n";
		ret += "td {text-align: center;}\n";
		ret += "tbody th{text-align: left;}\n";
		ret += "header {background: #bcb7e7;color: #000000;padding: 20px;text-align: center;}";
		for (Semestre semestre : model.getHmSemestres().values()) {
			ret += ".semestre"+ semestre.getId() + "{background-color: rgba(" + Integer.parseInt(semestre.getCouleur().substring(1,3),16) + "," + Integer.parseInt(semestre.getCouleur().substring(3,5),16) + "," + Integer.parseInt(semestre.getCouleur().substring(5,7),16)+ ", 0.5);}\n";
		}
		return this.ecrireFichier(chemin + "tab.css", ret);
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
		Map<Intervenant, List<Intervention>> hmIntervenants = model.getIntervenantInterventions();
		String body = "";
		body += "nom,catégorie,s1 théorique,s1 reel,s3 théorique,s3 reel,s5 théorique,s5 reel,sTotal théorique,sTotal reel,s2 théorique,s2 reel,s4 théorique,s4 reel,s6 théorique,s6 reel,sTotal théorique,sTotal reel,minimum d'heures,total théorique,total reel, maximum d'heure\n";
		
		for (Intervenant interv : hmIntervenants.keySet())
			body += this.sSemestreCsv(interv, hmIntervenants.get(interv));
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
		ret += Math.round(s1theorique*100.0)/100.0 + ","; // s1 theorique
		ret += Math.round(s1reel*100.0)/100.0 + ","; // s1 reel
		ret += Math.round(s3theorique*100.0)/100.0 + ","; // s3 theorique
		ret += Math.round(s3reel*100.0)/100.0 + ","; // s3 reel
		ret += Math.round(s5reel*100.0)/100.0 + ","; // s5 theorique
		ret += Math.round(s5theorique*100.0)/100.0 + ","; // s5 reel
		ret += Math.round((s1theorique + s3theorique + s5theorique)*100.0)/100.0 + ","; // total theorique partie 1
		ret += Math.round((s1reel + s3reel + s5reel)*100.0)/100.0 + ","; // total reel partie 1
		ret += Math.round(s2reel*100.0)/100.0 + ","; // s2 theorique
		ret += Math.round(s2theorique*100.0)/100.0 + ","; // s2 reel
		ret += Math.round(s4reel*100.0)/100.0 + ","; // s4 theorique
		ret += Math.round(s4theorique*100.0)/100.0 + ","; // s4 reel
		ret += Math.round(s6reel*100.0)/100.0 + ","; // s6 theorique
		ret += Math.round(s6theorique*100.0)/100.0 + ","; // s6 reel
		ret += Math.round((s2theorique + s4theorique + s6theorique)*100.0)/100.0 + ","; // total theorique partie 2
		ret += Math.round((s2reel + s4reel + s6reel)*100.0)/100.0 + ","; // total reel partie 2
		ret += hMin(intervenant) + ","; // minimum d'heure
		ret += Math.round((s1theorique + s2theorique + s3theorique + s4theorique + s5theorique + s6theorique)*100.0)/100.0 + ","; // total theorique
		ret += Math.round((s1reel + s2reel + s3reel + s4reel + s5reel + s6reel)*100.0)/100.0 + ","; // total reel
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
		Categorie categ = model.getHmCategories().get(intervenant.getIdCategorie());            // categorie de l'intervenant
		double ret =  intervention.getNbGroupe();                                               // nombre de groupe
		ret = ret * categ.getRatioTp();                                                         // ratio TP
		ret = ret * model.getHmTypeCours().get(intervention.getIdTypeCours()).getCoefficient(); // coef de typecours
		if (intervention.getIdTypeCours()<4){
			ret = ret*intervention.getNbSemaines();                                             // nombre semaines
			ret = ret*model.getHeureCour(intervention).gethParSemaine();                        // nombre d'heure par semaine
		}
		return ret;
	}

	private double theoriqueIntervention(Intervenant intervenant, Intervention intervention) {
		double ret = intervention.getNbGroupe();                                                // nombre de groupe
		ret = ret * model.getHmTypeCours().get(intervention.getIdTypeCours()).getCoefficient(); // coef de typecours
		if (intervention.getIdTypeCours()<4){
			ret = ret*intervention.getNbSemaines();                                             // nombre semaines
			ret = ret*model.getHeureCour(intervention).gethParSemaine();                        // nombre d'heure par semaine
		}
		return ret;
	}
	
	private double hMin(Intervenant interv) {
		if (interv.getIdCategorie() == 3)
			return interv.gethMin();
		return model.getHmCategories().get(interv.getIdCategorie()).gethMin();
	}
	
	private double hMax(Intervenant interv) {
		if (interv.getIdCategorie() == 3)
			return interv.gethMax();
		return model.getHmCategories().get(interv.getIdCategorie()).gethMax();
	}
	
	/**
	 * Écrit des données dans un fichier spécifié en utilisant un PrintWriter.
	 * 
	 * @param nomFichierDestination Le nom du fichier dans lequel écrire les
	 *                              données.
	 * @param body contenu du Fichier
	 */
	 @SuppressWarnings("deprecation")
	private URL ecrireFichier(String nomFichierDestination, String body) {
		try (PrintWriter pw = new PrintWriter(new FileOutputStream(nomFichierDestination))) {
			pw.println(body);
			return new URL("file:" + nomFichierDestination);
		} catch (Exception e) {
			System.out.println(nomFichierDestination);
			App.log(Level.SEVERE, e);
			return null;
		}
	}
}