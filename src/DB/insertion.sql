salmono
salmono
horny slut

salmono
 — 
06/12/2023 15:58
mais la relation permet de les liées
Marrouche
 — 
06/12/2023 15:58
oui dsl j'avais pas tout lu
salmono
 — 
06/12/2023 15:58
(je viens de le modifier)
Atlonos
 — 
06/12/2023 15:59
bah donne moi un exemple dans lequel ce serait utile
Nocyu
 — 
06/12/2023 16:00
Oui c'est une idée aussi, sinon la relation contiendrait juste 2 années
salmono
 — 
06/12/2023 16:02
c'est juste plus compréhensible je pense
Atlonos
 — 
06/12/2023 16:03
?
SELECT    *
FROM    Intervenant i NATURAL JOIN Annee a
WHERE    a.annee = 2024;

vs

SELECT    *
FROM    Intervenant
WHERE    annee = 2024;
salmono
 — 
06/12/2023 16:19
non, justement, le premier tu join sur une année, t'as pas besoin de vérifié l'année

Select *
From Intervenant i join Annee a on i.idAnnee = a.idAnnee


chaque instance d'intervenant est lié a une instance d'annee
Lovis
 — 
07/12/2023 14:24
LovisCoding
salmono
 a épinglé 
un message
 dans ce salon. Voir tous les 
messages épinglés
.
 — 
07/12/2023 14:25
salmono
 — 
07/12/2023 19:34
TomGoureau
salmono
 — 
09/12/2023 19:52
lundi, après le DS, on va en 715, qui n'est pas une de nos salles de SAE mais une salle de SAE des 3eme années, ça nous permettra de parler plus librement, sans que les autres équipes nous entendent

ça vous va ?
Marrouche
 — 
09/12/2023 19:58
ouais
salmono
 — 
09/12/2023 19:58
validez avec l'émoji, svp
salmono
 — 
09/12/2023 21:03
je rappel le plan, si vous le validez vous pouvez aussi réagir avec l'émoji, sinon, dire ce qui ne vous de convient pas ou ce que vous ne comprenez pas
Nocyu
 — 
09/12/2023 21:29
Bah faudrait faire l'accueil avant tout car les multiplicateurs que l'on paramètre dans l'accueil sont utilisés ensuite. Et il faut faire le java avant l'ihm à chaque fois puisque ça sera utile pour afficher dans l'ihm
Marrouche
 — 
09/12/2023 21:36
oui mais on pourra voir ensemble, je pense que ça sera possible de faire les deux en même temps
Lovis
 — 
09/12/2023 21:43
pour moi faut faire les deux en mm temps
Nocyu
 — 
09/12/2023 21:44
Oui je pense aussi mais ducoup le planning est faux
Mais on verra toute façon
Marrouche
 — 
09/12/2023 23:32
yep
Lovis
 — 
Hier à 10:02
https://prod.liveshare.vsengsaas.visualstudio.com/join?64F12BD3221A9CBF7E3775CDE79329CB714B
Visual Studio Code for the Web
Build with Visual Studio Code, anywhere, anytime, entirely in your browser.
Marrouche
 — 
Hier à 10:12
https://prod.liveshare.vsengsaas.visualstudio.com/join?0B70FB9DA8FE57F9BF7682897EDEEAD77343
Visual Studio Code for the Web
Build with Visual Studio Code, anywhere, anytime, entirely in your browser.
Marrouche
 — 
Hier à 10:25
https://prod.liveshare.vsengsaas.visualstudio.com/join?D540162748B21210EE85B32EDB038063C8C3
Visual Studio Code for the Web
Build with Visual Studio Code, anywhere, anytime, entirely in your browser.
Nocyu
 — 
Hier à 11:11
https://prod.liveshare.vsengsaas.visualstudio.com/join?A40AB93FAB2C9F87F4E8583A715D6E0AEC4E
Visual Studio Code for the Web
Build with Visual Studio Code, anywhere, anytime, entirely in your browser.
Nocyu
 — 
Hier à 12:40
https://prod.liveshare.vsengsaas.visualstudio.com/join?5B74C0AADFB7035E17B4FEBD0DA45EF7D9D0
Visual Studio Code for the Web
Build with Visual Studio Code, anywhere, anytime, entirely in your browser.
Marrouche
 — 
Hier à 12:53
https://prod.liveshare.vsengsaas.visualstudio.com/join?D540162748B21210EE85B32EDB038063C8C3
Visual Studio Code for the Web
Build with Visual Studio Code, anywhere, anytime, entirely in your browser.
https://prod.liveshare.vsengsaas.visualstudio.com/join?82A760878CBC305F744E4A4C4D4DF14A6840
Visual Studio Code for the Web
Build with Visual Studio Code, anywhere, anytime, entirely in your browser.
Nocyu
 — 
Hier à 13:59
https://prod.liveshare.vsengsaas.visualstudio.com/join?D8EC8DFFE592E404655C9133FD6CF200DF03
Visual Studio Code for the Web
Build with Visual Studio Code, anywhere, anytime, entirely in your browser.
Marrouche
 — 
Hier à 14:02
https://prod.liveshare.vsengsaas.visualstudio.com/join?965974C3E0178C3B49C885A10E747477F86C
Visual Studio Code for the Web
Build with Visual Studio Code, anywhere, anytime, entirely in your browser.
salmono
 — 
Hier à 14:40
avant de partir on fait un point de ce qu'on a fait et ce qu'il nous reste a faire
Marrouche
 — 
Hier à 14:47
https://prod.liveshare.vsengsaas.visualstudio.com/join?75B0032F648E8F1C6FCC17371710D6D938F2
Visual Studio Code for the Web
Build with Visual Studio Code, anywhere, anytime, entirely in your browser.
Lovis
 — 
Hier à 15:25

ALTER TABLE HeureCours ADD CONSTRAINT fk_HeureCours_TypeCours FOREIGN KEY (idTypeCours) REFERENCES TypeCours(idTypeCours);
-- ALTER TABLE HeureCours ADD CONSTRAINT fk_HeureCours_Module FOREIGN KEY (idModule) REFERENCES Module(idModule);
-- ALTER TABLE Module ADD CONSTRAINT fk_Module_Semestre FOREIGN KEY (idSemestres) REFERENCES Semestre(idSemestre);
-- ALTER TABLE Intervention ADD CONSTRAINT fk_Intervention_Intervenant FOREIGN KEY (idIntervenant) REFERENCES Intervenant(idIntervenant);

-- Ajout des contraintes PRIMARY KEY manquantes
-- ALTER TABLE Intervention ADD PRIMARY KEY (idIntervention, idModule, idTypeCours);

salmono
 — 
Hier à 15:45

/*
+ RCategorie    ( [idCategorie], nomCategorie, hMaxCategorie, hMinCategorie, idAnnee#)
+ RTypeCours    ( [idTypeCours], nomCours, coefficient, idAnnee#)
- RHeureCours   ( [idModule#, idTypeCours#], heure, idAnnee#)
/ RModule       ( [idModule], nomModule, nbSemainesModule, idSemestre#, idAnnee#)
/ RIntervenant  ( [idIntervenant], prenom, nomIntervenant, email, hMinIntervenant, hMaxIntervenant, idCategorie#, idAnnee#)

Afficher plus
insertion.sql10 Ko
Marrouche
 — 
Hier à 16:04
https://drive.google.com/drive/folders/1VC5kqJGHnGCjWWk0S_3IXSCfnXoOY79G?usp=sharing
Marrouche
 — 
Hier à 16:12

@echo off
set FX_PATH=.\lib\javafx-sdk-17.0.9\lib

:: Compile
javac --module-path "%FX_PATH%" --add-modules javafx.controls,javafx.fxml -encoding utf8 "@compile.list" -d .\bin

:: Change directory
cd bin

:: Set FX_PATH again for the Java execution
set FX_PATH=..\lib\javafx-sdk-17.0.9\lib

:: Run Java
java --module-path "%FX_PATH%" --add-modules javafx.controls,javafx.fxml Essai

:: Wait for user input
set /p dummyVar="Tapez enter pour continuer..."

:: Exit
exit /b 0

salmono
 — 
Hier à 17:00
ducoup, demain :
momo, Gabriel : IHM
Donovan, Arthur : tests insertion en Java
Tom : insertion sql

ça vous va ?
on se retrouve à l'iut ou un autre endroit ?
Lovis
 — 
Hier à 17:26
Avec dono on va finir d’insérer la bdd en java
Demain IUT et on en reparlera
Lovis
 — 
Hier à 18:26
8h30 demain ?
Nocyu
 — 
Aujourd’hui à 08:36
https://prod.liveshare.vsengsaas.visualstudio.com/join?E1867F6648623A53D4D76DA254E5D8FFE69F
Visual Studio Code for the Web
Build with Visual Studio Code, anywhere, anytime, entirely in your browser.
salmono
 — 
Aujourd’hui à 10:44

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
... (157 lignes restantes)

Réduire
message.txt14 Ko
Atlonos
 — 
Aujourd’hui à 10:49
https://drive.google.com/file/d/11nF-qNmS1b91jSMD-BJOsIoQnvNIPQ3n/view?usp=drive_link
Atlonos
 — 
Aujourd’hui à 11:00

@echo off

:: Création de la compile 
call :genererCompileList ".\src"

SET "localFX=.\lib\javafx-sdk-17.0.9\lib"

Afficher plus
run.bat4 Ko
Nocyu
 — 
Aujourd’hui à 11:20
https://drive.google.com/drive/folders/15VnCL48tXaLD_WAi7J1h4FzB0W49rjNA?usp=drive_link
Marrouche
 — 
Aujourd’hui à 11:35
ducoup @Nocyu t'as push?
Nocyu
 — 
Aujourd’hui à 11:41
Pas encore
Lovis
 — 
Aujourd’hui à 13:46

# Compiled source #
###################
*.com
*.class
*.dll
*.exe
*.o
*.so

# Packages #
############
# it's better to unpack these files and commit the raw source
# git has its own built in compression methods
*.7z
*.dmg
*.gz
*.iso
*.jar
*.rar
*.tar
*.zip

# Logs and databases #
######################
*.log
*.sql
*.sqlite

# OS generated files #
######################
.DS_Store
.DS_Store?
._*
.Spotlight-V100
.Trashes
ehthumbs.db
Thumbs.db
lib

﻿

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

INSERT INTO HeureCours (idModule,idTypeCours,heure, idAnnee) VALUES
	( 1, 3,  6, 1),
	( 1, 1, 65, 1),
	( 1, 2, 28, 1),
	( 2, 1, 30, 1),
	( 3, 1, 15, 1),
	( 3, 2,  7, 1),
	( 4, 1, 15, 1),
	( 4, 2,  7, 1),
	( 5, 1, 31, 1),
	( 5, 2, 14, 1),
	( 6, 1, 38, 1),
	( 7, 1, 24, 1),
	( 8, 3,  6, 1),
	( 8, 1, 23, 1),
	( 9, 1, 22, 1),
	(10, 1, 16, 1),
	(10, 2, 14, 1),
	(11, 1, 17, 1),
	(11, 2, 15, 1),
	(12, 1,  7, 1),
	(12, 2,  7, 1),
	(13, 3,  4, 1),
	(13, 1, 62, 1),
	(14, 1, 51, 1),
	(15, 1, 25, 1),
	(16, 1, 10, 1),
	(16, 2, 12, 1),
	(17, 1,  2, 1),
	(17, 2,  6, 1),
	(18, 1, 34, 1),
	(19, 1, 31, 1),
	(20, 1, 15, 1),
	(21, 1, 42, 1),
	(22, 1, 38, 1),
	(23, 1, 15, 1),
	(24, 1, 14, 1),
	(24, 2, 14, 1),
	(25, 1, 21, 1),
	(25, 2, 13, 1),
	(26, 1,  8, 1),
	(26, 2,  8, 1),
	(27, 1, 34, 1),
	(28, 1, 41, 1),
	(29, 3,  2, 1),
	(29, 1, 10, 1),
	(30, 1, 42, 1),
	(31, 3,  7, 1),
	(31, 1, 21, 1),
	(32, 1, 19, 1),
	(33, 3,  3, 1),
	(33, 1, 32, 1),
	(34, 1, 34, 1),
	(35, 1, 22, 1),
	(36, 1, 32, 1),
	(37, 3,  7, 1),
	(37, 1, 17, 1),
	(38, 1, 20, 1),
	(38, 2, 13, 1),
	(39, 1, 20, 1),
	(39, 2, 13, 1),
	(40, 1, 16, 1),
	(42, 3,  7, 1),
	(42, 1, 29, 1),
	(43, 1, 18, 1),
	(44, 1, 20, 1),
	(45, 1, 15, 1),
	(46, 1,  9, 1),
	(46, 2,  9, 1),
	(47, 1, 11, 1),
	(47, 2,  9, 1),
	(48, 1, 10, 1),
	(49, 1, 24, 1),
	(50, 3,  9, 1),
	(50, 1, 11, 1),
	(51, 3,  5, 1),
	(51, 1, 20, 1),
	(52, 3,  8, 1),
	(52, 1, 22, 1),
	(53, 1, 15, 1),
	(56, 1, 13, 1),
	(57, 1, 10, 1),
	(58, 1, 15, 1),
	(58, 2, 15, 1),
	(59, 1, 15, 1),
	(60, 1, 36, 1),
	(61, 1, 15, 1),
	(62, 1, 15, 1),
	(63, 1, 22, 1),
	(64, 1, 20, 1),
	(65, 1, 35, 1),
	(66, 1, 12, 1),
	(67, 1, 35, 1),
	(68, 1, 15, 1),
	(69, 1, 15, 1),
	(69, 2, 10, 1),
	(70, 1, 12, 1),
	(71, 1, 12, 1),
	(72, 1, 10, 1),
	(73, 1, 10, 1),
	(74, 1, 24, 1),
	(75, 1, 18, 1)
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
