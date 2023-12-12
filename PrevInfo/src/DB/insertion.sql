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
INSERT INTO Annee (idAnnee, annee) VALUES (1, '2023/2024');

INSERT INTO Semestre (idSemestre, nbGTD, nbGTP, nbGCM, nbGAutre, idAnnee) VALUES
	( 1, 4, 8, 1, 1, 1),
	( 2, 4, 8, 1, 1, 1),
	( 3, 2, 4, 1, 0, 1),
	( 4, 2, 4, 1, 0, 1),
	( 5, 2, 4, 1, 0, 1),
	( 6, 2, 4, 1, 0, 1);


INSERT INTO Categorie (idCategorie ,nomCategorie, hMaxCategorie, hMinCategorie, idAnnee) VALUES
	( 1, 'Titulaire'   , 384, 192, 1),
	( 2, 'Contractuel' , 384, 192, 1),
	( 3, 'Vacataire'   ,   0,   0, 1),
	( 4, 'Chercheur'   , 182, 172, 1)
	;

INSERT INTO Module (idModule, nomModule,nbSemainesModule,idAnnee,idSemestre) VALUES
	(  1, 'R1.01_Initiation au développement'                                 ,   14, 1, 1),
	(  2, 'R1.02_Développement interfaces Web'                                ,   13, 1, 1),
	(  3, 'R1.03_Introduction Architecture'                                   ,    7, 1, 1),
	(  4, 'R1.04_Introduction Système'                                        ,    7, 1, 1),
	(  5, 'R1.05_Introduction Base de données'                                ,   14, 1, 1),
	(  6, 'R1.06_Mathématiques discrètes'                                     ,   14, 1, 1),
	(  7, 'R1.07_Outils mathématiques fondamentaux'                           ,   14, 1, 1),
	(  8, 'R1.08_Gestion de projet et des organisations'                      ,   14, 1, 1),
	(  9, 'R1.09_Économie durable et numérique'                               ,   14, 1, 1),
	( 10, 'R1.10_Anglais Technique'                                           ,   14, 1, 1),
	( 11, 'R1.11_Bases de la communication'                                   ,   15, 1, 1),
	( 12, 'R1.12_Projet Professionnel et Personnel'                           , null, 1, 1),
	( 13, 'R2.01_Développement orienté objets'                                ,   10, 1, 2),
	( 14, 'R2.02_Développement d applications avec IHM'                       ,   13, 1, 2),
	( 15, 'R2.03_Qualité de développement'                                    ,    4, 1, 2),
	( 16, 'R2.04_Communication et fonctionnement bas niveau'                  ,    6, 1, 2),
	( 17, 'R2.05_Introduction aux services réseaux'                           ,    3, 1, 2),
	( 18, 'R2.06_Exploitation d une base de données'                          ,   13, 1, 2),
	( 19, 'R2.07_Graphes'                                                     ,    9, 1, 2),
	( 20, 'R2.08_Outils numériques pour les statistiques'                     ,    9, 1, 2),
	( 21, 'R2.09_Méthodes Numériques'                                         ,    8, 1, 2),
	( 22, 'R2.10_Gestion de projet et des organisations'                      ,   13, 1, 2),
	( 23, 'R2.11_Droit'                                                       ,    9, 1, 2),
	( 24, 'R2.12_Anglais d entreprise'                                        ,   14, 1, 2),
	( 25, 'R2.13_Communication Technique'                                     ,   13, 1, 2),
	( 26, 'R2.14_Projet Professionnel et Personnel'                           , null, 1, 2),
	( 27, 'R3.01_Développement WEB'                                           ,   13, 1, 3),
	( 28, 'R3.02_Développement Efficace'                                      ,   13, 1, 3),
	( 29, 'R3.03_Analyse'                                                     ,    8, 1, 3),
	( 30, 'R3.04_Qualité de développement 3'                                  ,   13, 1, 3),
	( 31, 'R3.05_Programmation Système'                                       ,   13, 1, 3),
	( 32, 'R3.06_Architecture des réseaux'                                    ,   12, 1, 3),
	( 33, 'R3.07_SQL dans un langage de programmation'                        ,   12, 1, 3),
	( 34, 'R3.08_Probabilités'                                                ,   13, 1, 3),
	( 35, 'R3.09_Cryptographie et sécurité'                                   ,   13, 1, 3),
	( 36, 'R3.10_Management des systèmes d information'                       ,   12, 1, 3),
	( 37, 'R3.11_Droits des contrats et du numérique'                         ,   13, 1, 3),
	( 38, 'R3.12_Anglais 3'                                                   ,   13, 1, 3),
	( 39, 'R3.13_Communication professionnelle'                               ,   13, 1, 3),
	( 40, 'R3.14_PPP 3'                                                       ,    5, 1, 3),
	( 41, 'P3.01_Portfolio'                                                   , null, 1, 3),
	( 42, 'R4.01_Architecture logicielle'                                     ,    8, 1, 4),
	( 43, 'R4.02_Qualité de développement 4'                                  ,    8, 1, 4),
	( 44, 'R4.03_Qualité et au delà du relationnel'                           ,    8, 1, 4),
	( 45, 'R4.04_Méthodes d optimisation'                                     ,    8, 1, 4),
	( 46, 'R4.05_Anglais 4'                                                   ,    8, 1, 4),
	( 47, 'R4.06_Communication interne'                                       ,    9, 1, 4),
	( 48, 'R4.07_PPP 4'                                                       , null, 1, 4),
	( 49, 'R4.08_Virtualisation'                                              ,    8, 1, 4),
	( 50, 'R4.09_Management avancé des Systèmesd information'                 ,    7, 1, 4),
	( 51, 'R4.10_Complément web'                                              ,    8, 1, 4),
	( 52, 'R4.11_Développement mobile'                                        ,    8, 1, 4),
	( 53, 'R4.12_Automates'                                                   ,    7, 1, 4),
	( 54, 'S4.ST_Stages'                                                      , null, 1, 4),
	( 55, 'P1.02_Portfolio'                                                   , null, 1, 4),
	( 56, 'R5.01_Initiation au management d une équipe de projet informatique',    8, 1, 5),
	( 57, 'R5.02_Projet Personnel et Professionnel'                           , null, 1, 5),
	( 58, 'R5.03_Politique de communication'                                  ,   10, 1, 5),
	( 59, 'R5.04_Qualité algorithmique'                                       ,    9, 1, 5),
	( 60, 'R5.05_Programmation avancée'                                       ,   10, 1, 5),
	( 61, 'R5.06_Programmation multimédia'                                    ,    7, 1, 5),
	( 62, 'R5.07_Automatisation de la chaîne de production'                   ,    9, 1, 5),
	( 63, 'R5.08_Qualité de développement'                                    ,   10, 1, 5),
	( 64, 'R5.09_Virtualisation avancée'                                      ,   10, 1, 5),
	( 65, 'R5.10_Nouveaux paradigmes de base de données'                      ,   10, 1, 5),
	( 66, 'R5.11_Méthodes d optimisation pour l aide à la décision'           ,   10, 1, 5),
	( 67, 'R5.12_Modélisations mathématiques'                                 ,   10, 1, 5),
	( 68, 'R5.13_Économie durable et numérique'                               ,   10, 1, 5),
	( 69, 'R5.14_Anglais'                                                     ,   10, 1, 5),
	( 70, 'R6.01_Initiation à l entrepreneuriat'                              ,    4, 1, 6),
	( 71, 'R6.02_Droit du numérique et de la propriété intellectuelle'        ,    4, 1, 6),
	( 72, 'R6.03_Communication : organisation et diffusion de l information'  ,    4, 1, 6),
	( 73, 'R6.04_Projet Personnel et Professionnel'                           , null, 1, 6),
	( 74, 'R6.05_Développement avancé'                                        ,    4, 1, 6),
	( 75, 'R6.06_Maintenance applicative'                                     ,    4, 1, 6),
	( 76, 'R6.01_Stage'                                                       , null, 1, 6)
	;

INSERT INTO TypeCours (idTypeCours ,nomCours, coefficient) VALUES
	( 1, 'Travaux dirigés'  , 1),
	( 2, 'Travaux pratiques', 1),
	( 3, 'Cours magistrales', 1),
	( 4, 'Autres'           , 1)
	;

INSERT INTO HeureCours (idTypeCours,idModule,heure, idAnnee) VALUES
	(3,  1,  6, 1), 
	(1,  1, 65, 1),
	(2,  1, 28, 1),
	(1,  2, 30, 1),
	(1,  3, 15, 1),
	(2,  3,  7, 1),
	(1,  4, 15, 1),
	(2,  4,  7, 1),
	(1,  5, 31, 1),
	(2,  5, 14, 1),
	(1,  6, 38, 1),
	(1,  7, 24, 1),
	(3,  8,  6, 1),
	(1,  8, 23, 1),
	(1,  9, 22, 1),
	(1, 10, 16, 1),
	(2, 10, 14, 1),
	(1, 11, 17, 1),
	(2, 11, 15, 1),
	(1, 12,  7, 1),
	(2, 12,  7, 1),
	(3, 13,  4, 1),
	(1, 13, 62, 1),
	(1, 14, 51, 1),
	(1, 15, 25, 1),
	(1, 16, 10, 1),
	(2, 16, 12, 1),
	(1, 17,  2, 1),
	(2, 17,  6, 1),
	(1, 18, 34, 1),
	(1, 19, 31, 1),
	(1, 20, 15, 1),
	(1, 21, 42, 1),
	(1, 22, 38, 1),
	(1, 23, 15, 1),
	(1, 24, 14, 1),
	(2, 24, 14, 1),
	(1, 25, 21, 1),
	(2, 25, 13, 1),
	(1, 26,  8, 1),
	(2, 26,  8, 1),
	(1, 27, 34, 1),
	(1, 28, 41, 1),
	(3, 29,  2, 1),
	(1, 29, 10, 1),
	(1, 30, 42, 1),
	(3, 31,  7, 1),
	(1, 31, 21, 1),
	(1, 32, 19, 1),
	(3, 33,  3, 1),
	(1, 33, 32, 1),
	(1, 34, 34, 1),
	(1, 35, 22, 1),
	(1, 36, 32, 1),
	(3, 37,  7, 1),
	(1, 37, 17, 1),
	(1, 38, 20, 1),
	(2, 38, 13, 1),
	(1, 39, 20, 1),
	(2, 39, 13, 1),
	(1, 40, 16, 1),
	(3, 42,  7, 1),
	(1, 42, 29, 1),
	(1, 43, 18, 1),
	(1, 44, 20, 1),
	(1, 45, 15, 1),
	(1, 46,  9, 1),
	(2, 46,  9, 1),
	(1, 47, 11, 1),
	(2, 47,  9, 1),
	(1, 48, 10, 1),
	(1, 49, 24, 1),
	(3, 50,  9, 1),
	(1, 50, 11, 1),
	(3, 51,  5, 1),
	(1, 51, 20, 1),
	(3, 52,  8, 1),
	(1, 52, 22, 1),
	(1, 53, 15, 1),
	(1, 56, 13, 1),
	(1, 57, 10, 1),
	(1, 58, 15, 1),
	(2, 58, 15, 1),
	(1, 59, 15, 1),
	(1, 60, 36, 1),
	(1, 61, 15, 1),
	(1, 62, 15, 1),
	(1, 63, 22, 1),
	(1, 64, 20, 1),
	(1, 65, 35, 1),
	(1, 66, 12, 1),
	(1, 67, 35, 1),
	(1, 68, 15, 1),
	(1, 69, 15, 1),
	(2, 69, 10, 1),
	(1, 70, 12, 1),
	(1, 71, 12, 1),
	(1, 72, 10, 1),
	(1, 73, 10, 1),
	(1, 74, 24, 1),
	(1, 75, 18, 1)
	;


INSERT INTO Intervenant (idIntervenant ,prenom, nom, email, hMinIntervenant, hMaxIntervenant, idAnnee,  idCategorie) VALUES
	( 1, 'Philippe' , 'Lepivert'  , 'philippe.le-pivert@univ-lehavre.fr'     , 0, 0, 1, 1),
	( 2, 'Hadhoum'  , 'Boukachour', 'hadhoum.boukachour@univ-lehavre.fr'     , 0, 0, 1, 1),
	( 3, 'Frédéric' , 'Serin'     , 'frederic.serin@univ-lehavre.fr'         , 0, 0, 1, 1),
	( 4, 'Hugues'   , 'Duflo'     , 'hugues.duflo@univ-lehavre.fr'           , 0, 0, 1, 1),
	( 5, 'Jaouad'   , 'Boukachour', 'jaouad.boukachour@univ-lehavre.fr'      , 0, 0, 1, 1),
	( 6, 'Laurence' , 'Nivet'     , 'laurence.nivet@univ-lehavre.fr'         , 0, 0, 1, 1),
	( 7, 'Quentin'  , 'Griette'   , 'quentin.griette@univ-lehavre.fr'        , 0, 0, 1, 1),
	( 8, 'Quentin'  , 'Laffeach'  , 'quentin.laffeach@univ-lehavre.fr'       , 0, 0, 1, 1),
	( 9, 'Rodolphe' , 'Charrier'  , 'Rodolphe.Charrier@univ-lehavre.fr'      , 0, 0, 1, 1),
	(10, 'Bruno'    , 'Legrix'    , 'bruno.legrix@univ-lehavre.fr'           , 0, 0, 1, 1),
	(11, 'Alabboud' , 'Hassan'    , 'alabboudhassan@gmail.com'               , 0, 0, 1, 1),
	(12, 'Zahour'   , 'Abderrazak', 'zahoura@univ-lehavre.fr'                , 0, 0, 1, 1),
	(13, 'Jean'     , 'Foubert'   , 'jean.foubert@univ-lehavre.fr'           , 0, 0, 1, 1),
	(14, 'Frederic' , 'Guinand'   , 'frederic.guinand@univ-lehavre.fr'       , 0, 0, 1, 1),
	(15, 'Tiphaine' , 'Dubocage'  , 'dubocage.iut@gmail.com'                 , 0, 0, 1, 1),
	(16, 'Thomas'   , 'Colignon'  , 'colignon.iut@gmail.com'                 , 0, 0, 1, 1),
	(17, 'Steeve'   , 'Pytel'     , 'steeve.pytel@ac-normandie.fr'           , 0, 0, 1, 1),
	(18, 'Dalila'   , 'Boudebous' , 'dalila.boudebous@univ-lehavre.fr'       , 0, 0, 1, 1),
	(19, 'Bruno'    , 'Sadeg'     , 'bruno.sadeg@univ-lehavre.fr'            , 0, 0, 1, 1),
	(20, 'Pascal'   , 'Rembert'   , 'rembertp@univ-lehavre.fr'               , 0, 0, 1, 1),
	(21, 'Sébastien', 'Bertin'    , 'sebastien.d.bertin@gmail.com'           , 0, 0, 1, 1),
	(22, 'Isabelle' , 'Delarue'   , 'isabelle.delarue2@orange.fr'            , 0, 0, 1, 1),
	(23, 'Benjamin' , 'Boquet'    , 'boquet.iut@gmail.com'                   , 0, 0, 1, 1),
	(24, 'Jan-Luis' , 'Jiménéz'   , 'juan-luis.jimenez@univ-lehavre.fr'      , 0, 0, 1, 1),
	(25, 'Jean-Yves', 'Colin'     , 'jean-yves.colin@univ-lehavre.fr'        , 0, 0, 1, 1),
	(26, 'Emmanuel' , 'Keith'     , 'emmanuel.keith@ac-rouen.fr'             , 0, 0, 1, 1),
	(27, 'Mouhaned' , 'Gaied'     , 'mouhaned.gaied@univ-lehavre.fr'         , 0, 0, 1, 1),
	(28, 'Salim'    , 'Khraimeche', 'salimkhr@gmail.com'                     , 0, 0, 1, 1),
	(29, 'Florian'  , 'Perroud'   , 'perroud.iut@gmail.com'                  , 0, 0, 1, 1),
	(30, 'Claude'   , 'Duvallet'  , 'claude.duvallet@guinand@univ-lehavre.fr', 0, 0, 1, 1)
	;

INSERT INTO Intervention (idIntervenant, idModule, idTypeCours, nbSemainesIntervention, nbGroupe, idAnnee) VALUES
	(1, 1, 1, 13, 2, 1),
	(1, 1, 2, 13, 4, 1),
	(1, 1, 3,  4, 1, 1)
	;