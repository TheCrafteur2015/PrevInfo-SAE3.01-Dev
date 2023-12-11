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
INSERT INTO Module (nomModule,nbSemainesModule,idSemestre) VALUES
	( "R1.01_Initiation au développement"                                 , 14, 0),
	( "R1.02_Développement interfaces Web"                                , 13, 0),
	( "R1.03_Introduction Architecture"                                   ,  7, 0),
	( "R1.05_Introduction Base de données"                                ,  7, 0),
	( "R1.06_Mathématiques discrètes"                                     , 14, 0),
	( "R1.07_Outils mathématiques fondamentaux"                           , 14, 0),
	( "R1.08_Gestion de projet et des organisations"                      , 14, 0),
	( "R1.09_Économie durable et numérique"                               , 14, 0),
	( "R1.10_Anglais Technique"                                           , 14, 0),
	( "R1.11_Bases de la communication"                                   , 15, 0),
	( "R1.12_Projet Professionnel et Personnel"                           ,  7, 0),

	( "R2.01_Développement orienté objets"                                , 4, 1),
	( "R2.02_Développement d'applications avec IHM"                       , 15, 1),
	( "R2.03_Qualité de développement"                                    , 15, 1),
	( "R2.04_Communication et fonctionnement bas niveau"                  , 15, 1),
	( "R2.05_Introduction aux services réseaux"                           , 15, 1),
	( "R2.06_Exploitation d'une base de données"                          , 15, 1),
	( "R2.07_Graphes"                                                     , 15, 1),
	( "R2.08_Outils numériques pour les statistiques"                     , 15, 1),
	( "R2.09_Méthodes Numériques"                                         , 15, 1),
	( "R2.10_Gestion de projet et des organisations"                      , 15, 1),
	( "R2.11_Droit"                                                       , 15, 1),
	( "R2.12_Anglais d'entreprise"                                        , 15, 1),
	( "R2.13_Communication Technique"                                     , 15, 1),
	( "R2.14_Projet Professionnel et Personnel"                           , 15, 1),

	( "R3.01_Développement WEB"                                           , 15, 2),
	( "R3.02_Développement Efficace"                                      , 15, 2),
	( "R3.03_Analyse"                                                     , 15, 2),
	( "R3.04_Qualité de développement 3"                                  , 15, 2),
	( "R3.05_Programmation Système"                                       , 15, 2),
	( "R3.06_Architecture des réseaux"                                    , 15, 2),
	( "R3.07_SQL dans un langage de programmation"                        , 15, 2),
	( "R3.08_Probabilités"                                                , 15, 2),
	( "R3.09_Cryptographie et sécurité"                                   , 15, 2),
	( "R3.10_Management des systèmes d'information"                       , 15, 2),
	( "R3.11_Droits des contrats et du numérique"                         , 15, 2),
	( "R3.12_Anglais 3"                                                   , 15, 2),
	( "R3.13_Communication professionnelle"                               , 15, 2),
	( "R3.14_PPP 3"                                                       , 15, 2),

	( "R4.01_Architecture logicielle"                                     , 15, 3),
	( "R4.02_Qualité de développement 4"                                  , 15, 3),
	( "R4.03_Qualité et au delà du relationnel"                           , 15, 3),
	( "R4.04_Méthodes d'optimisation"                                     , 15, 3),
	( "R4.05_Anglais 4"                                                   , 15, 3),
	( "R4.06_Communication interne"                                       , 15, 3),
	( "R4.07_PPP 4"                                                       , 15, 3),
	( "R4.08_Virtualisation"                                              , 15, 3),
	( "R4.09_Management avancé des Systèmesd'information"                 , 15, 3),
	( "R4.10_Complément web"                                              , 15, 3),
	( "R4.11_Développement mobile"                                        , 15, 3),
	( "R4.12_Automates"                                                   , 15, 3),
	( "S4.ST_Stages"                                                      , 15, 3),

	( "R5.01_Initiation au management d’une équipe de projet informatique", 15, 4),
	( "R5.02_Projet Personnel et Professionnel"                           , 15, 4),
	( "R5.03_Politique de communication"                                  , 15, 4),
	( "R5.04_Qualité algorithmique"                                       , 15, 4),
	( "R5.05_Programmation avancée"                                       , 15, 4),
	( "R5.06_Programmation multimédia"                                    , 15, 4),
	( "R5.07_Automatisation de la chaîne de production"                   , 15, 4),
	( "R5.08_Qualité de développement"                                    , 15, 4),
	( "R5.09_Virtualisation avancée"                                      , 15, 4),
	( "R5.10_Nouveaux paradigmes de base de données"                      , 15, 4),
	( "R5.11_Méthodes d’optimisation pour l’aide à la décision"           , 15, 4),
	( "R5.12_Modélisations mathématiques"                                 , 15, 4),
	( "R5.13_Économie durable et numérique"                               , 15, 4),
	( "R5.14_Anglais"                                                     , 15, 4),

	( "R6.01_Initiation à l’entrepreneuriat"                              , 15, 5),
	( "R6.02_Droit du numérique et de la propriété intellectuelle"        , 15, 5),
	( "R6.03_Communication : organisation et diffusion de l’information"  , 15, 5),
	( "R6.04_Projet Personnel et Professionnel"                           , 15, 5),
	( "R6.05_Développement avancé"                                        , 15, 5),
	( "R6.06_Maintenance applicative"                                     , 15, 5),
	( "R6.01_Stage"                                                       , 15, 5)
	;

INSERT INTO HeureCours (idModule,idTypeCours,heure) VALUES
	(  0, 2,  12), (  0, 1,  65), (  0, 3, 168),
	(  1, 2,   0), (  1, 1,  90), (  1, 3,   0),
	(  2, 2,   0), (  2, 1,  45), (  2, 3,  42),
	(  3, 2,   0), (  3, 1,  45), (  3, 3,  42),
	(  4, 2,   0), (  4, 1,  93), (  4, 3,  84),
	(  5, 2,   0), (  5, 1, 114), (  5, 3,   0),
	(  6, 2,   0), (  6, 1,  72), (  6, 3,   0),
	(  7, 2,   9), (  7, 1,  69), (  7, 3,   0),
	(  8, 2,   0), (  8, 1,  66), (  8, 3,   0),
	(  9, 2,   0), (  9, 1,  48), (  9, 3,  84),
	( 10, 2,   0), ( 10, 1,  51), ( 10, 3,  90),
	( 11, 2,   0), ( 11, 1,  21), ( 11, 3,  42)
	;

INSERT INTO TypeCours (nomCours, coefficient) VALUES
	( "Travaux dirigés"  , 1),
	( "Travaux pratiques", 1),
	( "Cours magistrales", 1),
	( "Autres"           , 1)
	;

INSERT INTO Semestre (nbGTD, nbGTP, nbGCM, nbGAutre) VALUES
	( 4, 8, 1, 1),
	( 4, 8, 1, 1),
	( 2, 4, 1, 0),
	( 2, 4, 1, 0),
	( 2, 4, 1, 0),
	( 2, 4, 1, 0)
	;
INSERT INTO Intervenant (prenom, nomIntervenant, email, hMinIntervenant, hMaxIntervenant, idCategorie#) VALUES
	( "Philippe", "Lepivert"  , "philippe.le-pivert@univ-lehavre.fr", 0, 0, 0),
	( "Hadhoum" , "boukachour", "hadhoum.boukachour@univ-lehavre.fr", 0, 0, 0),
	( "Frédéric", "Serin"     , "frederic.serin@univ-lehavre.fr"    , 0, 0, 0),
	( "Hugues"  , "Duflo"     , "hugues.duflo@univ-lehavre.fr"      , 0, 0, 0),
	( "Jaouad"  , "boukachour", "jaouad.boukachour@univ-lehavre.fr" , 0, 0, 0),
	( "Laurence", "Nivet"     , "laurence.nivet@univ-lehavre.fr"    , 0, 0, 0),
	( "Quentin" , "Griette"   , "quentin.griette@univ-lehavre.fr"   , 0, 0, 0),
	( "Quentin" , "Laffeach"  , "quentin.laffeach@univ-lehavre.fr"  , 0, 0, 0),
	( "Rodolphe", "Charrier"  , "Rodolphe.Charrier@univ-lehavre.fr" , 0, 0, 0),
	( "Bruno"   , "Legrix"    , "bruno.legrix@univ-lehavre.fr"      , 0, 0, 0)
	;

INSERT INTO Categorie (nomCategorie, hMaxCategorie, hMinCategorie) VALUES
	( "Titulaire"   , 384, 192),
	( "Contractuel" , 384, 192),
	( "Vacataire"   ,   0,   0),
	( "Chercheur"   , 182, 172)
	;