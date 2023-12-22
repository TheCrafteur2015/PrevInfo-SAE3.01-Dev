/*
+ RCategorie    ( [idCategorie], nomCategorie, hMaxCategorie, hMinCategorie, idAnnee#)
+ RTypeCours    ( [idTypeCours], nomCours, coefficient, idAnnee#)
+ RHeureCours   ( [idModule#, idTypeCours#], heure, idAnnee#)
+ RModule       ( [idModule], nomModule, nbSemainesModule, idSemestre#, idAnnee#)
+ RIntervenant  ( [idIntervenant], prenom, nomIntervenant, email, hMinIntervenant, hMaxIntervenant, idCategorie#, idAnnee#)
- RIntervention ( [(idIntervenant#, idModule#), idTypeCours#], nbSemainesIntervention, nbGroupe, idAnnee#)
+ RSemestre     ( [idSemestre], nbGTD, nbGTP, nbGCM, nbGAutre, idAnnee#)
+ RAnnee        ( [idAnnee], annee)
*/

INSERT INTO Annee (idAnnee, annee) VALUES
	(1, '2023-2024');

INSERT INTO Semestre (idSemestre, nbGTD, nbGTP, nbGCM, nbSemaine, couleur, idAnnee) VALUES
	(1, 4, 8, 1, 1, '#ffffff', 1),
	(2, 4, 8, 1, 1, '#ffffff', 1),
	(3, 2, 4, 1, 0, '#ffffff', 1),
	(4, 2, 4, 1, 0, '#ffffff', 1),
	(5, 2, 4, 1, 0, '#ffffff', 1),
	(6, 2, 4, 1, 0, '#ffffff', 1);


INSERT INTO Categorie (idCategorie ,nomCategorie, hMinCategorie, hMaxCategorie, ratioTp, idAnnee) VALUES
	(1, 'Enseignant',           384,  576,    1,    1),
	(2, 'Contractuel',          192,  384,    1,    1),
	(3, 'Vacataire',              0,   90, 0.66,    1),
	(4, 'Enseignant-Chercheur', 192,  364,    1,    1);

INSERT INTO TypeModule (idTypeModule, nomTypeModule) VALUES
	(1, 'PPP'   ),
	(2, 'SAE'   ),
	(3, 'normal'),
	(4, 'stage' );

INSERT INTO Module (idModule, code ,nomModule, valid, idSemestre,idAnnee, idTypeModule ) VALUES
	( 1, 'R1.01', 'Initiation au développement',                                   FALSE, 1, 1, 3),
	( 2, 'R1.02', 'Développement interfaces Web',                                  FALSE, 1, 1, 3),
	( 3, 'R1.03', 'Introduction Architecture',                                     FALSE, 1, 1, 3),
	( 4, 'R1.04', 'Introduction Système',                                          FALSE, 1, 1, 3),
	( 5, 'R1.05', 'Introduction Base de données',                                  FALSE, 1, 1, 3),
	( 6, 'R1.06', 'Mathématiques discrètes',                                       FALSE, 1, 1, 3),
	( 7, 'R1.07', 'Outils mathématiques fondamentaux',                             FALSE, 1, 1, 3),
	( 8, 'R1.08', 'Gestion de projet et des organisations',                        FALSE, 1, 1, 3),
	( 9, 'R1.09', 'Économie durable et numérique',                                 FALSE, 1, 1, 3),
	(10, 'R1.10', 'Anglais Technique',                                             FALSE, 1, 1, 3),
	(11, 'R1.11', 'Bases de la communication',                                     FALSE, 1, 1, 3),
	(12, 'R1.12', 'Projet Professionnel et Personnel',                             FALSE, 1, 1, 1),
	(13, 'S1.01', 'Implémentation d''un besoin client',                            FALSE, 1, 1, 2),
	(14, 'S1.02', 'Installation d''un poste pour le développement',                FALSE, 1, 1, 2),
	(15, 'S1.03', 'Comparaison d''approches algorithmiques',                       FALSE, 1, 1, 2),
	(16, 'S1.04', 'Création d''une base de données',                               FALSE, 1, 1, 2),
	(17, 'S1.05', 'Recueil de besoins',                                            FALSE, 1, 1, 2),
	(18, 'S1.06', 'Découverte de l''environnement économique et écologique',       FALSE, 1, 1, 2),
	(19, 'R2.01', 'Développement orienté objets',                                  FALSE, 2, 1, 3),
	(20, 'R2.02', 'Développement d''applications avec IHM',                        FALSE, 2, 1, 3),
	(21, 'R2.03', 'Qualité de développement',                                      FALSE, 2, 1, 3),
	(22, 'R2.04', 'Communication et fonctionnement bas niveau',                    FALSE, 2, 1, 3),
	(23, 'R2.05', 'Introduction aux services réseaux',                             FALSE, 2, 1, 3),
	(24, 'R2.06', 'Exploitation d''une base de données',                           FALSE, 2, 1, 3),
	(25, 'R2.07', 'Graphes',                                                       FALSE, 2, 1, 3),
	(26, 'R2.08', 'Outils numériques pour les statistiques',                       FALSE, 2, 1, 3),
	(27, 'R2.09', 'Méthodes Numériques',                                           FALSE, 2, 1, 3),
	(28, 'R2.10', 'Gestion de projet et des organisations',                        FALSE, 2, 1, 3),
	(29, 'R2.11', 'Droit',                                                         FALSE, 2, 1, 3),
	(30, 'R2.12', 'Anglais d''entreprise',                                         FALSE, 2, 1, 3),
	(31, 'R2.13', 'Communication Technique',                                       FALSE, 2, 1, 3),
	(32, 'R2.14', 'Projet Professionnel et Personnel',                             FALSE, 2, 1, 1),
	(33, 'S2.01', 'Développement d''une application',                              FALSE, 2, 1, 2),
	(34, 'S2.02', 'Exploration algorithmique d''un problème',                      FALSE, 2, 1, 2),
	(35, 'S2.03', 'Installation de services réseau',                               FALSE, 2, 1, 2),
	(36, 'S2.04', 'Exploitation d''une base de données',                           FALSE, 2, 1, 2),
	(37, 'S2.05', 'Gestion d''un projet',                                          FALSE, 2, 1, 2),
	(38, 'S2.06', 'Organisation d''un travail d équipe',                           FALSE, 2, 1, 2),
	(39, 'R3.01', 'Développement WEB',                                             FALSE, 3, 1, 3),
	(40, 'R3.02', 'Développement Efficace',                                        FALSE, 3, 1, 3),
	(41, 'R3.03', 'Analyse',                                                       FALSE, 3, 1, 3),
	(42, 'R3.04', 'Qualité de développement 3',                                    FALSE, 3, 1, 3),
	(43, 'R3.05', 'Programmation Système',                                         FALSE, 3, 1, 3),
	(44, 'R3.06', 'Architecture des réseaux',                                      FALSE, 3, 1, 3),
	(45, 'R3.07', 'SQL dans un langage de programmation',                          FALSE, 3, 1, 3),
	(46, 'R3.08', 'Probabilités',                                                  FALSE, 3, 1, 3),
	(47, 'R3.09', 'Cryptographie et sécurité',                                     FALSE, 3, 1, 3),
	(48, 'R3.10', 'Management des systèmes d''information',                        FALSE, 3, 1, 3),
	(49, 'R3.11', 'Droits des contrats et du numérique',                           FALSE, 3, 1, 3),
	(50, 'R3.12', 'Anglais 3',                                                     FALSE, 3, 1, 3),
	(51, 'R3.13', 'Communication professionnelle',                                 FALSE, 3, 1, 3),
	(52, 'R3.14', 'PPP 3',                                                         FALSE, 3, 1, 1),
	(53, 'S3.01', 'Développement d''une application',                              FALSE, 3, 1, 2),
	(54, 'R4.01', 'Architecture logicielle',                                       FALSE, 4, 1, 3),
	(55, 'R4.02', 'Qualité de développement 4',                                    FALSE, 4, 1, 3),
	(56, 'R4.03', 'Qualité et au delà du relationnel',                             FALSE, 4, 1, 3),
	(57, 'R4.04', 'Méthodes d optimisation',                                       FALSE, 4, 1, 3),
	(58, 'R4.05', 'Anglais 4',                                                     FALSE, 4, 1, 3),
	(59, 'R4.06', 'Communication interne',                                         FALSE, 4, 1, 3),
	(60, 'R4.07', 'PPP 4',                                                         FALSE, 4, 1, 1),
	(61, 'R4.08', 'Virtualisation',                                                FALSE, 4, 1, 3),
	(62, 'R4.09', 'Management avancé des Systèmesd information',                   FALSE, 4, 1, 3),
	(63, 'R4.10', 'Complément web',                                                FALSE, 4, 1, 3),
	(64, 'R4.11', 'Développement mobile',                                          FALSE, 4, 1, 3),
	(65, 'R4.12', 'Automates',                                                     FALSE, 4, 1, 3),
	(66, 'S4.ST', 'Stages',                                                        FALSE, 4, 1, 4),
	(67, 'S4.01', 'Développement d''une application',                              FALSE, 4, 1, 2),
	(68, 'R5.01', 'Initiation au management d''une équipe de projet informatique', FALSE, 5, 1, 3),
	(69, 'R5.02', 'Projet Personnel et Professionnel',                             FALSE, 5, 1, 1),
	(70, 'R5.03', 'Politique de communication',                                    FALSE, 5, 1, 3),
	(71, 'R5.04', 'Qualité algorithmique',                                         FALSE, 5, 1, 3),
	(72, 'R5.05', 'Programmation avancée',                                         FALSE, 5, 1, 3),
	(73, 'R5.06', 'Programmation multimédia',                                      FALSE, 5, 1, 3),
	(74, 'R5.07', 'Automatisation de la chaîne de production',                     FALSE, 5, 1, 3),
	(75, 'R5.08', 'Qualité de développement',                                      FALSE, 5, 1, 3),
	(76, 'R5.09', 'Virtualisation avancée',                                        FALSE, 5, 1, 3),
	(77, 'R5.10', 'Nouveaux paradigmes de base de données',                        FALSE, 5, 1, 3),
	(78, 'R5.11', 'Méthodes d optimisation pour l aide à la décision',             FALSE, 5, 1, 3),
	(79, 'R5.12', 'Modélisations mathématiques',                                   FALSE, 5, 1, 3),
	(80, 'R5.13', 'Économie durable et numérique',                                 FALSE, 5, 1, 3),
	(81, 'R5.14', 'Anglais',                                                       FALSE, 5, 1, 3),
	(82, 'R6.01', 'Initiation à l''entrepreneuriat',                               FALSE, 6, 1, 3),
	(83, 'R6.02', 'Droit du numérique et de la propriété intellectuelle',          FALSE, 6, 1, 3),
	(84, 'R6.03', 'Communication : organisation et diffusion de l''information',   FALSE, 6, 1, 3),
	(85, 'R6.04', 'Projet Personnel et Professionnel',                             FALSE, 6, 1, 1),
	(86, 'R6.05', 'Développement avancé',                                          FALSE, 6, 1, 3),
	(87, 'R6.06', 'Maintenance applicative',                                       FALSE, 6, 1, 3),
	(88, 'R6.01', 'Stage',                                                         FALSE, 6, 1, 4),
	(89, 'S6.01', 'Évolution d’une application existante',                         FALSE, 6, 1, 2);

INSERT INTO TypeCours (idTypeCours ,nomCours, coefficient) VALUES
	(1, 'TD',  1),
	(2, 'TP',  1),
	(3, 'CM',  1),
	(4, 'Tut', 1),
	(5, 'REH', 1),
	(6, 'SAE', 1),
	(7, 'HP',  1);

INSERT INTO HeureCours (idTypeCours,idModule,heure,nbSemaine,hParSemaine, idAnnee) VALUES
	(3,  1, 6, 0, 0, 1), (1,  1, 65, 0, 0, 1), (2,  1, 28, 0, 0, 1), (7,  1, 0, 0, 0, 1),
	(3,  2, 0, 0, 0, 1), (1,  2, 30, 0, 0, 1), (2,  2,  0, 0, 0, 1), (7,  2, 0, 0, 0, 1),
	(3,  3, 0, 0, 0, 1), (1,  3, 15, 0, 0, 1), (2,  3,  7, 0, 0, 1), (7,  3, 0, 0, 0, 1),
	(3,  4, 0, 0, 0, 1), (1,  4, 15, 0, 0, 1), (2,  4,  7, 0, 0, 1), (7,  4, 0, 0, 0, 1),
	(3,  5, 0, 0, 0, 1), (1,  5, 31, 0, 0, 1), (2,  5, 14, 0, 0, 1), (7,  5, 0, 0, 0, 1),
	(3,  6, 0, 0, 0, 1), (1,  6, 38, 0, 0, 1), (2,  6,  0, 0, 0, 1), (7,  6, 0, 0, 0, 1),
	(3,  7, 0, 0, 0, 1), (1,  7, 24, 0, 0, 1), (2,  7,  0, 0, 0, 1), (7,  7, 0, 0, 0, 1),
	(3,  8, 6, 0, 0, 1), (1,  8, 23, 0, 0, 1), (2,  8,  0, 0, 0, 1), (7,  8, 0, 0, 0, 1),
	(3,  9, 0, 0, 0, 1), (1,  9, 22, 0, 0, 1), (2,  9,  0, 0, 0, 1), (7,  9, 0, 0, 0, 1),
	(3, 10, 0, 0, 0, 1), (1, 10, 16, 0, 0, 1), (2, 10, 14, 0, 0, 1), (7, 10, 0, 0, 0, 1),
	(3, 11, 0, 0, 0, 1), (1, 11, 17, 0, 0, 1), (2, 11, 15, 0, 0, 1), (7, 11, 0, 0, 0, 1),
	(3, 12, 0, 0, 0, 1), (1, 12,  7, 0, 0, 1), (2, 12,  7, 0, 0, 1), (4, 12, 0, 0, 0, 1), (7, 12, 0, 0, 0, 1),
	(4, 13, 0, 0, 0, 1), (6, 13,  0, 0, 0, 1),
	(4, 14, 0, 0, 0, 1), (6, 14,  0, 0, 0, 1),
	(4, 15, 0, 0, 0, 1), (6, 15,  0, 0, 0, 1),
	(4, 16, 0, 0, 0, 1), (6, 16,  0, 0, 0, 1),
	(4, 17, 0, 0, 0, 1), (6, 17,  0, 0, 0, 1),
	(4, 18, 0, 0, 0, 1), (6, 18,  0, 0, 0, 1),
	(3, 19, 4, 0, 0, 1), (1, 19, 62, 0, 0, 1), (2, 19,  0, 0, 0, 1), (7, 19, 0, 0, 0, 1),
	(3, 20, 0, 0, 0, 1), (1, 20, 51, 0, 0, 1), (2, 20,  0, 0, 0, 1), (7, 20, 0, 0, 0, 1),
	(3, 21, 0, 0, 0, 1), (1, 21, 25, 0, 0, 1), (2, 21,  0, 0, 0, 1), (7, 21, 0, 0, 0, 1),
	(3, 22, 0, 0, 0, 1), (1, 22, 10, 0, 0, 1), (2, 22, 12, 0, 0, 1), (7, 22, 0, 0, 0, 1),
	(3, 23, 0, 0, 0, 1), (1, 23,  2, 0, 0, 1), (2, 23,  6, 0, 0, 1), (7, 23, 0, 0, 0, 1),
	(3, 24, 0, 0, 0, 1), (1, 24, 34, 0, 0, 1), (2, 24,  0, 0, 0, 1), (7, 24, 0, 0, 0, 1),
	(3, 25, 0, 0, 0, 1), (1, 25, 31, 0, 0, 1), (2, 25,  0, 0, 0, 1), (7, 25, 0, 0, 0, 1),
	(3, 26, 0, 0, 0, 1), (1, 26, 15, 0, 0, 1), (2, 26,  0, 0, 0, 1), (7, 26, 0, 0, 0, 1),
	(3, 27, 0, 0, 0, 1), (1, 27, 42, 0, 0, 1), (2, 27,  0, 0, 0, 1), (7, 27, 0, 0, 0, 1),
	(3, 28, 0, 0, 0, 1), (1, 28, 38, 0, 0, 1), (2, 28,  0, 0, 0, 1), (7, 28, 0, 0, 0, 1),
	(3, 29, 0, 0, 0, 1), (1, 29, 15, 0, 0, 1), (2, 29,  0, 0, 0, 1), (7, 29, 0, 0, 0, 1),
	(3, 30, 0, 0, 0, 1), (1, 30, 14, 0, 0, 1), (2, 30, 14, 0, 0, 1), (7, 30, 0, 0, 0, 1),
	(3, 31, 0, 0, 0, 1), (1, 31, 21, 0, 0, 1), (2, 31, 13, 0, 0, 1), (7, 31, 0, 0, 0, 1),
	(3, 32, 0, 0, 0, 1), (1, 32,  8, 0, 0, 1), (2, 32,  8, 0, 0, 1), (4, 32, 0, 0, 0, 1), (7, 32, 0, 0, 0, 1),
	(4, 33, 0, 0, 0, 1), (6, 33,  0, 0, 0, 1),
	(4, 34, 0, 0, 0, 1), (6, 34,  0, 0, 0, 1),
	(4, 35, 0, 0, 0, 1), (6, 35,  0, 0, 0, 1),
	(4, 36, 0, 0, 0, 1), (6, 36,  0, 0, 0, 1),
	(4, 37, 0, 0, 0, 1), (6, 37,  0, 0, 0, 1),
	(4, 38, 0, 0, 0, 1), (6, 38,  0, 0, 0, 1),
	(3, 39, 0, 0, 0, 1), (1, 39, 34, 0, 0, 1), (2, 39,  0, 0, 0, 1), (7, 39, 0, 0, 0, 1),
	(3, 40, 0, 0, 0, 1), (1, 40, 41, 0, 0, 1), (2, 40,  0, 0, 0, 1), (7, 40, 0, 0, 0, 1),
	(3, 41, 2, 0, 0, 1), (1, 41, 10, 0, 0, 1), (2, 41,  0, 0, 0, 1), (7, 41, 0, 0, 0, 1),
	(3, 42, 0, 0, 0, 1), (1, 42, 42, 0, 0, 1), (2, 42,  0, 0, 0, 1), (7, 42, 0, 0, 0, 1),
	(3, 43, 7, 0, 0, 1), (1, 43, 21, 0, 0, 1), (2, 43,  0, 0, 0, 1), (7, 43, 0, 0, 0, 1),
	(3, 44, 0, 0, 0, 1), (1, 44, 19, 0, 0, 1), (2, 44,  0, 0, 0, 1), (7, 44, 0, 0, 0, 1),
	(3, 45, 3, 0, 0, 1), (1, 45, 32, 0, 0, 1), (2, 45,  0, 0, 0, 1), (7, 45, 0, 0, 0, 1),
	(3, 46, 0, 0, 0, 1), (1, 46, 34, 0, 0, 1), (2, 46,  0, 0, 0, 1), (7, 46, 0, 0, 0, 1),
	(3, 47, 0, 0, 0, 1), (1, 47, 22, 0, 0, 1), (2, 47,  0, 0, 0, 1), (7, 47, 0, 0, 0, 1),
	(3, 48, 0, 0, 0, 1), (1, 48, 32, 0, 0, 1), (2, 48,  0, 0, 0, 1), (7, 48, 0, 0, 0, 1),
	(3, 49, 7, 0, 0, 1), (1, 49, 17, 0, 0, 1), (2, 49,  0, 0, 0, 1), (7, 49, 0, 0, 0, 1),
	(3, 50, 0, 0, 0, 1), (1, 50, 20, 0, 0, 1), (2, 50, 13, 0, 0, 1), (7, 50, 0, 0, 0, 1),
	(3, 51, 0, 0, 0, 1), (1, 51, 20, 0, 0, 1), (2, 51, 13, 0, 0, 1), (7, 51, 0, 0, 0, 1),
	(3, 52, 0, 0, 0, 1), (1, 52, 16, 0, 0, 1), (2, 52,  0, 0, 0, 1), (4, 52, 0, 0, 0, 1), (7, 52, 0, 0, 0, 1),
	(4, 53, 0, 0, 0, 1), (6, 53,  0, 0, 0, 1),
	(3, 54, 7, 0, 0, 1), (1, 54, 29, 0, 0, 1), (2, 54,  0, 0, 0, 1), (7, 54, 0, 0, 0, 1),
	(3, 55, 0, 0, 0, 1), (1, 55, 18, 0, 0, 1), (2, 55,  0, 0, 0, 1), (7, 55, 0, 0, 0, 1),
	(3, 56, 0, 0, 0, 1), (1, 56, 20, 0, 0, 1), (2, 56,  0, 0, 0, 1), (7, 56, 0, 0, 0, 1),
	(3, 57, 0, 0, 0, 1), (1, 57, 15, 0, 0, 1), (2, 57,  0, 0, 0, 1), (7, 57, 0, 0, 0, 1),
	(3, 58, 0, 0, 0, 1), (1, 58,  9, 0, 0, 1), (2, 58,  9, 0, 0, 1), (7, 58, 0, 0, 0, 1),
	(3, 59, 0, 0, 0, 1), (1, 59, 11, 0, 0, 1), (2, 59,  9, 0, 0, 1), (7, 59, 0, 0, 0, 1),
	(3, 60, 0, 0, 0, 1), (1, 60, 10, 0, 0, 1), (2, 60,  0, 0, 0, 1), (4, 60, 0, 0, 0, 1), (7, 60, 0, 0, 0, 1),
	(3, 61, 0, 0, 0, 1), (1, 61, 24, 0, 0, 1), (2, 61,  0, 0, 0, 1), (7, 61, 0, 0, 0, 1),
	(3, 62, 0, 0, 0, 1), (1, 62, 11, 0, 0, 1), (2, 62,  0, 0, 0, 1), (7, 62, 0, 0, 0, 1),
	(3, 63, 5, 0, 0, 1), (1, 63, 20, 0, 0, 1), (2, 63,  0, 0, 0, 1), (7, 63, 0, 0, 0, 1),
	(3, 64, 8, 0, 0, 1), (1, 64, 22, 0, 0, 1), (2, 64,  0, 0, 0, 1), (7, 64, 0, 0, 0, 1),
	(3, 65, 0, 0, 0, 1), (1, 65, 15, 0, 0, 1), (2, 65,  0, 0, 0, 1), (7, 65, 0, 0, 0, 1),
	(4, 66, 0, 0, 0, 1), (5, 66,  0, 0, 0, 1), 
	(4, 67, 0, 0, 0, 1), (6, 67,  0, 0, 0, 1),
	(3, 68, 0, 0, 0, 1), (1, 68, 13, 0, 0, 1), (2, 68,  0, 0, 0, 1), (7, 68, 0, 0, 0, 1),
	(3, 69, 0, 0, 0, 1), (1, 69, 10, 0, 0, 1), (2, 69,  0, 0, 0, 1), (4, 69, 0, 0, 0, 1), (7, 69, 0, 0, 0, 1),
	(3, 70, 0, 0, 0, 1), (1, 70, 15, 0, 0, 1), (2, 70, 15, 0, 0, 1), (7, 70, 0, 0, 0, 1),
	(3, 71, 0, 0, 0, 1), (1, 71, 15, 0, 0, 1), (2, 71,  0, 0, 0, 1), (7, 71, 0, 0, 0, 1),
	(3, 72, 0, 0, 0, 1), (1, 72, 36, 0, 0, 1), (2, 72,  0, 0, 0, 1), (7, 72, 0, 0, 0, 1),
	(3, 73, 0, 0, 0, 1), (1, 73, 15, 0, 0, 1), (2, 73,  0, 0, 0, 1), (7, 73, 0, 0, 0, 1),
	(3, 74, 0, 0, 0, 1), (1, 74, 15, 0, 0, 1), (2, 74,  0, 0, 0, 1), (7, 74, 0, 0, 0, 1),
	(3, 75, 0, 0, 0, 1), (1, 75, 22, 0, 0, 1), (2, 75,  0, 0, 0, 1), (7, 75, 0, 0, 0, 1),
	(3, 76, 0, 0, 0, 1), (1, 76, 20, 0, 0, 1), (2, 76,  0, 0, 0, 1), (7, 76, 0, 0, 0, 1),
	(3, 77, 0, 0, 0, 1), (1, 77, 35, 0, 0, 1), (2, 77,  0, 0, 0, 1), (7, 77, 0, 0, 0, 1),
	(3, 78, 0, 0, 0, 1), (1, 78, 12, 0, 0, 1), (2, 78,  0, 0, 0, 1), (7, 78, 0, 0, 0, 1),
	(3, 79, 0, 0, 0, 1), (1, 79, 35, 0, 0, 1), (2, 79,  0, 0, 0, 1), (7, 79, 0, 0, 0, 1),
	(3, 80, 0, 0, 0, 1), (1, 80, 15, 0, 0, 1), (2, 80,  0, 0, 0, 1), (7, 80, 0, 0, 0, 1),
	(3, 81, 0, 0, 0, 1), (1, 81, 15, 0, 0, 1), (2, 81, 10, 0, 0, 1), (7, 81, 0, 0, 0, 1),
	(3, 82, 0, 0, 0, 1), (1, 82, 12, 0, 0, 1), (2, 82,  0, 0, 0, 1), (7, 82, 0, 0, 0, 1),
	(3, 83, 0, 0, 0, 1), (1, 83, 12, 0, 0, 1), (2, 83,  0, 0, 0, 1), (7, 83, 0, 0, 0, 1),
	(3, 84, 0, 0, 0, 1), (1, 84, 10, 0, 0, 1), (2, 84,  0, 0, 0, 1), (7, 84, 0, 0, 0, 1),
	(3, 85, 0, 0, 0, 1), (1, 85, 10, 0, 0, 1), (2, 85,  0, 0, 0, 1), (4, 85, 0, 0, 0, 1), (7, 85, 0, 0, 0, 1),
	(3, 86, 0, 0, 0, 1), (1, 86, 24, 0, 0, 1), (2, 86,  0, 0, 0, 1), (7, 86, 0, 0, 0, 1),
	(3, 87, 0, 0, 0, 1), (1, 87, 18, 0, 0, 1), (2, 87,  0, 0, 0, 1), (7, 87, 0, 0, 0, 1),
	(4, 88, 0, 0, 0, 1), (5, 88,  0, 0, 0, 1), 
	(4, 89, 0, 0, 0, 1), (6, 89,  0, 0, 0, 1);

INSERT INTO Intervenant (idIntervenant ,prenom, nom, email, hMinIntervenant, hMaxIntervenant, idAnnee,  idCategorie) VALUES
	( 1, 'Philippe',  'Le Pivert',  'philippe.le-pivert@univ-lehavre.fr',      0, 0, 1, 1),
	( 2, 'Hadhoum',   'Boukachour', 'hadhoum.boukachour@univ-lehavre.fr',      0, 0, 1, 1),
	( 3, 'Frédéric',  'Serin',      'frederic.serin@univ-lehavre.fr',          0, 0, 1, 1),
	( 4, 'Hugues',    'Duflo',      'hugues.duflo@univ-lehavre.fr',            0, 0, 1, 1),
	( 5, 'Jaouad',    'Boukachour', 'jaouad.boukachour@univ-lehavre.fr',       0, 0, 1, 1),
	( 6, 'Laurence',  'Nivet',      'laurence.nivet@univ-lehavre.fr',          0, 0, 1, 1),
	( 7, 'Quentin',   'Griette',    'quentin.griette@univ-lehavre.fr',         0, 0, 1, 1),
	( 8, 'Quentin',   'Laffeach',   'quentin.laffeach@univ-lehavre.fr',        0, 0, 1, 1),
	( 9, 'Rodolphe',  'Charrier',   'rodolphe.charrier@univ-lehavre.fr',       0, 0, 1, 1),
	(10, 'Bruno',     'Legrix',     'bruno.legrix@univ-lehavre.fr',            0, 0, 1, 1),
	(11, 'Alabboud',  'Hassan',     'alabboudhassan@gmail.com',                0, 0, 1, 1),
	(12, 'Zahour',    'Abderrazak', 'zahoura@univ-lehavre.fr',                 0, 0, 1, 1),
	(13, 'Jean',      'Foubert',    'jean.foubert@univ-lehavre.fr',            0, 0, 1, 1),
	(14, 'Frederic',  'Guinand',    'frederic.guinand@univ-lehavre.fr',        0, 0, 1, 1),
	(15, 'Tiphaine',  'Dubocage',   'dubocage.iut@gmail.com',                  0, 0, 1, 1),
	(16, 'Thomas',    'Colignon',   'colignon.iut@gmail.com',                  0, 0, 1, 1),
	(17, 'Steeve',    'Pytel',      'steeve.pytel@ac-normandie.fr',            0, 0, 1, 1),
	(18, 'Dalila',    'Boudebous',  'dalila.boudebous@univ-lehavre.fr',        0, 0, 1, 1),
	(19, 'Bruno',     'Sadeg',      'bruno.sadeg@univ-lehavre.fr',             0, 0, 1, 1),
	(20, 'Pascal',    'Rembert',    'rembertp@univ-lehavre.fr',                0, 0, 1, 1),
	(21, 'Sébastien', 'Bertin',     'sebastien.d.bertin@gmail.com',            0, 0, 1, 1),
	(22, 'Isabelle',  'Delarue',    'isabelle.delarue2@orange.fr',             0, 0, 1, 1),
	(23, 'Benjamin',  'Boquet',     'boquet.iut@gmail.com',                    0, 0, 1, 1),
	(24, 'Jan-Luis',  'Jiménéz',    'juan-luis.jimenez@univ-lehavre.fr',       0, 0, 1, 1),
	(25, 'Jean-Yves', 'Colin',      'jean-yves.colin@univ-lehavre.fr',         0, 0, 1, 1),
	(26, 'Emmanuel',  'Keith',      'emmanuel.keith@ac-rouen.fr',              0, 0, 1, 1),
	(27, 'Mouhaned',  'Gaied',      'mouhaned.gaied@univ-lehavre.fr',          0, 0, 1, 1),
	(28, 'Salim',     'Khraimeche', 'salimkhr@gmail.com',                      0, 0, 1, 1),
	(29, 'Florian',   'Perroud',    'perroud.iut@gmail.com',                   0, 0, 1, 1),
	(30, 'Claude',    'Duvallet',   'claude.duvallet@guinand@univ-lehavre.fr', 0, 0, 1, 1);

INSERT INTO Intervention (idIntervenant,idTypeCours, idModule,  nbSemainesIntervention, nbGroupe, commentaire, idAnnee) VALUES
	( 1, 3,  1,  6,  1, '3 cm d 1h30', 1),
	( 1, 1,  1, 14,  2, '',            1),
	( 1, 2,  1, 14,  2, '',            1),
	( 1, 7,  1,  0,  6, 'TP0',         1),
	( 1, 7,  1,  0,  5, 'DS papier',   1),
	( 1, 7,  1,  0,  5, 'DS machine',  1),
	(10, 1,  1, 14,  2, '',            1),
	(10, 7,  1,  0,  6, 'TP0',         1),
	(10, 7,  1,  0,  3, 'DS papier',   1),
	(16, 2,  1, 14,  2, '',            1),
	(16, 7,  1,  0,  3, 'DS Machine',  1),
	(15, 2,  1, 14,  2, '',            1),
	(15, 7,  1,  0,  3, 'DSMachine',   1),
	( 2, 7,  1,  0,  5, 'DS papier',   1),
	( 1, 4, 53,  0,  8, '',            1),
	( 1, 6, 53,  0, 10, '',            1),
	( 2, 4, 53,  0,  8, '',            1),
	( 2, 6, 53,  0, 10, '',            1),
	( 8, 4, 53,  0,  7, '',            1),
	( 8, 6, 53,  0,  5, '',            1),
	(10, 4, 53,  0,  8, '',            1),
	(10, 6, 53,  0, 10, '',            1),
	( 6, 4, 53,  0,  7, '',            1),
	( 6, 6, 53,  0,  5, '',            1),
	( 8, 4, 66,  0,  3, '',            1);