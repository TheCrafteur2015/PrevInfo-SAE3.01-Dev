/*
+ RCategorie    ( [idCategorie], nomCategorie, hMaxCategorie, hMinCategorie, idAnnee#)
+ RTypeCours    ( [idTypeCours], nomCours, coefficient, idAnnee#)
- RHeureCours   ( [idModule#, idTypeCours#], heure, idAnnee#)
/ RModule       ( [idModule], nomModule, nbSemainesModule, idSemestre#, idAnnee#)
/ RIntervenant  ( [idIntervenant], prenom, nomIntervenant, email, hMinIntervenant, hMaxIntervenant, idCategorie#, idAnnee#)
- RIntervention ( [(idIntervenant#, idModule#), idTypeCours#], nbSemainesIntervention, nbGroupe, idAnnee#)
+ RSemestre     ( [idSemestre], nbGTD, nbGTP, nbGCM, nbGAutre, idAnnee#)
* RAnnee        ( [idAnnee], annee)
*/
INSERT INTO Annee (idAnnee, annee) VALUES (1, '2023-2024');

INSERT INTO Semestre (nbGTD, nbGTP, nbGCM, nbGAutre, idAnnee) VALUES
	( 4, 8, 1, 1, 1),
	( 4, 8, 1, 1, 1),
	( 2, 4, 1, 0, 1),
	( 2, 4, 1, 0, 1),
	( 2, 4, 1, 0, 1),
	( 2, 4, 1, 0, 1);


INSERT INTO Categorie (nomCategorie, hMaxCategorie, hMinCategorie, idAnnee) VALUES
	( 'Titulaire'   , 384, 192, 1),
	( 'Contractuel' , 384, 192, 1),
	( 'Vacataire'   ,   0,   0, 1),
	( 'Chercheur'   , 182, 172, 1)
	;

INSERT INTO Module (idModule, nomModule,nbSemainesModule,idAnnee,idSemestre) VALUES
	(  1, 'R1.01_Initiation au développement'                                 ,   14, 1, 1),
	(  2, 'R1.02_Développement interfaces Web'                                ,   13, 1, 1),
	(  3, 'R1.03_Introduction Architecture'                                   ,    7, 1, 1),
	(  4, 'R1.04_Introduction Système'                                        ,    7, 1, 1),
	(  5, 'R1.05_Introduction Base de données'                                ,    7, 1, 1),
	(  6, 'R1.06_Mathématiques discrètes'                                     ,   14, 1, 1),
	(  7, 'R1.07_Outils mathématiques fondamentaux'                           ,   14, 1, 1),
	(  8, 'R1.08_Gestion de projet et des organisations'                      ,   14, 1, 1),
	(  9, 'R1.09_Économie durable et numérique'                               ,   14, 1, 1),
	( 10, 'R1.10_Anglais Technique'                                           ,   14, 1, 1),
	( 11, 'R1.11_Bases de la communication'                                   ,   15, 1, 1),
	( 12, 'R1.12_Projet Professionnel et Personnel'                           ,   15, 1, 1)
	;

INSERT INTO TypeCours (nomCours, coefficient) VALUES
	( 'Travaux dirigés'  , 1),
	( 'Travaux pratiques', 1),
	( 'Cours magistrales', 1),
	( 'Autres'           , 1)
	;

INSERT INTO HeureCours (idModule,idTypeCours,heure,idAnnee) VALUES
	(  1, 2,  12, 1), (  1, 1,  65, 1), (  1, 3, 168, 1),
	(  2, 2,   0, 1), (  2, 1,  90, 1), (  2, 3,   0, 1),
	(  3, 2,   0, 1), (  3, 1,  45, 1), (  3, 3,  42, 1),
	(  4, 2,   0, 1), (  4, 1,  45, 1), (  4, 3,  42, 1),
	(  5, 2,   0, 1), (  5, 1,  93, 1), (  5, 3,  84, 1),
	(  6, 2,   0, 1), (  6, 1, 114, 1), (  6, 3,   0, 1),
	(  7, 2,   0, 1),                   (  7, 3,   0, 1),
	(  8, 2,   9, 1), (  7, 1,  69, 1), (  8, 3,   0, 1),
	(  9, 2,   0, 1), (  8, 1,  66, 1), (  9, 3,   0, 1),
	( 10, 2,   0, 1), (  9, 1,  48, 1), ( 10, 3,  84, 1),
	( 11, 2,   0, 1), ( 10, 1,  51, 1), ( 11, 3,  90, 1)
	;


INSERT INTO Intervenant (prenom, nom, email, hMinIntervenant, hMaxIntervenant, idAnnee,  idCategorie) VALUES
	( 'Philippe', 'Lepivert'  , 'philippe.le-pivert@univ-lehavre.fr', 0, 0, 1, 1),
	( 'Hadhoum' , 'boukachour', 'hadhoum.boukachour@univ-lehavre.fr', 0, 0, 1, 1),
	( 'Frédéric', 'Serin'     , 'frederic.serin@univ-lehavre.fr'    , 0, 0, 1, 1),
	( 'Hugues'  , 'Duflo'     , 'hugues.duflo@univ-lehavre.fr'      , 0, 0, 1, 1),
	( 'Jaouad'  , 'boukachour', 'jaouad.boukachour@univ-lehavre.fr' , 0, 0, 1, 1),
	( 'Laurence', 'Nivet'     , 'laurence.nivet@univ-lehavre.fr'    , 0, 0, 1, 1),
	( 'Quentin' , 'Griette'   , 'quentin.griette@univ-lehavre.fr'   , 0, 0, 1, 1),
	( 'Quentin' , 'Laffeach'  , 'quentin.laffeach@univ-lehavre.fr'  , 0, 0, 1, 1),
	( 'Rodolphe', 'Charrier'  , 'Rodolphe.Charrier@univ-lehavre.fr' , 0, 0, 1, 1),
	( 'Bruno'   , 'Legrix'    , 'bruno.legrix@univ-lehavre.fr'      , 0, 0, 1, 1)
	;



/*
 0, 'R1.01_Initiation au développement'                                 ,   14, 0
 1, 'R1.02_Développement interfaces Web'                                ,   13, 0
 2, 'R1.03_Introduction Architecture'                                   ,    7, 0
 3, 'R1.05_Introduction Base de données'                                ,    7, 0
 4, 'R1.06_Mathématiques discrètes'                                     ,   14, 0
 5, 'R1.07_Outils mathématiques fondamentaux'                           ,   14, 0
 6, 'R1.08_Gestion de projet et des organisations'                      ,   14, 0
 7, 'R1.09_Économie durable et numérique'                               ,   14, 0
 8, 'R1.10_Anglais Technique'                                           ,   14, 0
 9, 'R1.11_Bases de la communication'                                   ,   15, 0
10, 'R1.12_Projet Professionnel et Personnel'                           ,   15, 0
11, 'R2.01_Développement orienté objets'                                ,   10, 1
12, 'R2.02_Développement d'applications avec IHM'                       ,   13, 1
13, 'R2.03_Qualité de développement'                                    ,    4, 1
14, 'R2.04_Communication et fonctionnement bas niveau'                  ,    6, 1
15, 'R2.05_Introduction aux services réseaux'                           ,    3, 1
16, 'R2.06_Exploitation d'une base de données'                          ,   13, 1
17, 'R2.07_Graphes'                                                     ,    9, 1
18, 'R2.08_Outils numériques pour les statistiques'                     ,    9, 1
19, 'R2.09_Méthodes Numériques'                                         ,    8, 1
20, 'R2.10_Gestion de projet et des organisations'                      ,   13, 1
21, 'R2.11_Droit'                                                       ,    9, 1
22, 'R2.12_Anglais d'entreprise'                                        ,   14, 1
23, 'R2.13_Communication Technique'                                     ,   13, 1
24, 'R2.14_Projet Professionnel et Personnel'                           , Null, 1
25, 'R3.01_Développement WEB'                                           ,   13, 2
26, 'R3.02_Développement Efficace'                                      ,   13, 2
27, 'R3.03_Analyse'                                                     ,    8, 2
28, 'R3.04_Qualité de développement 3'                                  ,   13, 2
29, 'R3.05_Programmation Système'                                       ,   13, 2
30, 'R3.06_Architecture des réseaux'                                    ,   12, 2
31, 'R3.07_SQL dans un langage de programmation'                        ,   12, 2
32, 'R3.08_Probabilités'                                                ,   13, 2
33, 'R3.09_Cryptographie et sécurité'                                   ,   13, 2
34, 'R3.10_Management des systèmes d'information'                       ,   12, 2
35, 'R3.11_Droits des contrats et du numérique'                         ,   13, 2
36, 'R3.12_Anglais 3'                                                   ,   13, 2
37, 'R3.13_Communication professionnelle'                               ,   13, 2
39, 'R3.14_PPP 3'                                                       ,    5, 2
40, 'R4.01_Architecture logicielle'                                     ,    8, 3
41, 'R4.02_Qualité de développement 4'                                  ,    8, 3
 1, 'R4.03_Qualité et au delà du relationnel'                           ,    8, 3
 1, 'R4.04_Méthodes d'optimisation'                                     ,    8, 3
 1, 'R4.05_Anglais 4'                                                   ,    8, 3
 1, 'R4.06_Communication interne'                                       ,    9, 3
 1, 'R4.07_PPP 4'                                                       ,   15, 3
 1, 'R4.08_Virtualisation'                                              ,   15, 3
 1, 'R4.09_Management avancé des Systèmesd'information'                 ,   15, 3
 1, 'R4.10_Complément web'                                              ,   15, 3
 1, 'R4.11_Développement mobile'                                        ,   15, 3
 1, 'R4.12_Automates'                                                   ,   15, 3
 1, 'S4.ST_Stages'                                                      ,   15, 3
 1, 'R5.01_Initiation au management d’une équipe de projet informatique',   15, 4
 1, 'R5.02_Projet Personnel et Professionnel'                           ,   15, 4
 1, 'R5.03_Politique de communication'                                  ,   15, 4
 1, 'R5.04_Qualité algorithmique'                                       ,   15, 4
 1, 'R5.05_Programmation avancée'                                       ,   15, 4
 1, 'R5.06_Programmation multimédia'                                    ,   15, 4
 1, 'R5.07_Automatisation de la chaîne de production'                   ,   15, 4
 1, 'R5.08_Qualité de développement'                                    ,   15, 4
 1, 'R5.09_Virtualisation avancée'                                      ,   15, 4
 1, 'R5.10_Nouveaux paradigmes de base de données'                      ,   15, 4
 1, 'R5.11_Méthodes d’optimisation pour l’aide à la décision'           ,   15, 4
 1, 'R5.12_Modélisations mathématiques'                                 ,   15, 4
 1, 'R5.13_Économie durable et numérique'                               ,   15, 4
 1, 'R5.14_Anglais'                                                     ,   15, 4
 1, 'R6.01_Initiation à l’entrepreneuriat'                              ,   15, 5
 1, 'R6.02_Droit du numérique et de la propriété intellectuelle'        ,   15, 5
 1, 'R6.03_Communication : organisation et diffusion de l’information'  ,   15, 5
 1, 'R6.04_Projet Personnel et Professionnel'                           ,   15, 5
 1, 'R6.05_Développement avancé'                                        ,   15, 5
 1, 'R6.06_Maintenance applicative'                                     ,   15, 5
 1, 'R6.01_Stage'                                                       ,   15, 5
*/