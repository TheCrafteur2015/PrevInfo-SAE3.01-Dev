RCategorie    ( [idCategorie], nomCategorie, hMaxCategorie, hMinCategorie, idAnnee#)
RTypeCours    ( [idTypeCours], nomCours, coefficient, idAnnee#)
RHeureCours   ( [idModule#, idTypeCours#], heure, idAnnee#)
RModule       ( [idModule], nomModule, nbSemainesModule, idSemestre#, idAnnee#)
RIntervenant  ( [idIntervenant], prenom, nomIntervenant, email, hMinIntervenant, hMaxIntervenant, idCategorie#, idAnnee#)
RIntervention ( [(idIntervenant#, idModule#), idTypeCours#], nbSemainesIntervention, nbGroupe, idAnnee#)
RSemestre     ( [idSemestre], nbGTD, nbGTP, nbGCM, nbGAutre, idAnnee#)
RAnnee        ( [idAnnee], annee)

INSERT INTO Module(  0, "R1.01_Initiation au développement"           , 14, 0);
INSERT INTO Module(  1, "R1.02_Développement interfaces Web"          , 14, 0);
INSERT INTO Module(  2, "R1.03_Introduction Architecture"             , 14, 0);
INSERT INTO Module(  3, "R1.05_Introduction Base de données"          , 14, 0);
INSERT INTO Module(  4, "R1.06_Mathématiques discrètes"               , 14, 0);
INSERT INTO Module(  5, "R1.07_Outils mathématiques fondamentaux"     , 14, 0);
INSERT INTO Module(  6, "R1.08_Gestion de projet et des organisations", 14, 0);
INSERT INTO Module(  7, "R1.09_Économie durable et numérique"         , 14, 0);
INSERT INTO Module(  8, "R1.10_Anglais Technique"                     , 14, 0);
INSERT INTO Module(  9, "R1.11_Bases de la communication"             , 14, 0);
INSERT INTO Module( 10, "R1.12_Projet Professionnel et Personnel"     , 14, 0);

INSERT INTO Semestre( 0, 4, 8, 1, 1);
INSERT INTO Semestre( 1, 4, 8, 1, 1);
INSERT INTO Semestre( 2, 2, 4, 1, 0);
INSERT INTO Semestre( 3, 2, 4, 1, 0);
INSERT INTO Semestre( 4, 2, 4, 1, 0);
INSERT INTO Semestre( 5, 2, 4, 1, 0);

INSERT INTO Intervenant[idIntervenant], prenom, nomIntervenant, email, hMinIntervenant, hMaxIntervenant, idCategorie#)
INSERT INTO Intervenant (0, "Philippe", "Lepivert", "philippe.le-pivert@univ-lehavre.fr" )