DROP TABLE IF EXISTS Annee CASCADE;
DROP TABLE IF EXISTS Categorie CASCADE;
DROP TABLE IF EXISTS HeureCours CASCADE;
DROP TABLE IF EXISTS TypeCours CASCADE;
DROP TABLE IF EXISTS TypeModule CASCADE;
DROP TABLE IF EXISTS Module CASCADE;
DROP TABLE IF EXISTS Intervenant CASCADE;
DROP TABLE IF EXISTS Intervention CASCADE;
DROP TABLE IF EXISTS Semestre CASCADE;

CREATE TABLE Annee (
	idAnnee SERIAL PRIMARY KEY,
	annee   VARCHAR(9)
);

CREATE TABLE TypeCours (
	idTypeCours SERIAL PRIMARY KEY,
	nomCours    VARCHAR(255),
	coefficient FLOAT
);

CREATE TABLE Categorie (
	idCategorie   SERIAL PRIMARY KEY,
	nomCategorie  VARCHAR(255),
	hMinCategorie FLOAT,
	hMaxCategorie FLOAT,
	ratioTp       FLOAT,
	idAnnee       INT REFERENCES Annee(idAnnee)
);

CREATE TABLE Semestre (
	idSemestre SERIAL PRIMARY KEY, 
	nbGTD      INT,
	nbGTP      INT,
	nbGCM      INT,
	nbSemaine  INT,
	couleur    VARCHAR(20),
	idAnnee    INT REFERENCES Annee(idAnnee)
);

CREATE TABLE TypeModule (
	idTypeModule INT PRIMARY KEY,
	nomTypeModule VARCHAR(15)
);

CREATE TABLE Module (
	idModule   SERIAL PRIMARY KEY,
	nomModule  VARCHAR(255),
	code       VARCHAR(10),
	valid      BOOLEAN,
	idTypeModule INT REFERENCES TypeModule(idTypeModule),
	idAnnee    INT REFERENCES Annee(idAnnee),
	idSemestre INT REFERENCES Semestre(idSemestre)
);

CREATE TABLE HeureCours (
	idTypeCours INT REFERENCES TypeCours(idTypeCours),
	idModule    INT REFERENCES Module(idModule),
	heure       FLOAT,
	nbSemaine   INT,
	hParSemaine FLOAT,
	idAnnee     INT REFERENCES Annee(idAnnee),
	PRIMARY KEY (idTypeCours, idModule, idAnnee)
);

CREATE TABLE Intervenant(
	idIntervenant   SERIAL PRIMARY KEY, 
	prenom          VARCHAR(255),
	nom             VARCHAR(255),
	email           VARCHAR(255),
	hMinIntervenant INT,
	hMaxIntervenant INT,
	idAnnee         INT REFERENCES Annee(idAnnee),
	idCategorie     INT REFERENCES Categorie(idCategorie)
);

CREATE TABLE Intervention(
	idIntervention         SERIAL PRIMARY KEY,
	idIntervenant          INT REFERENCES Intervenant(idIntervenant),
	idModule               INT REFERENCES Module(idModule),
	idTypeCours            INT REFERENCES TypeCours(idTypeCours),
	nbSemainesIntervention INT,
	nbGroupe               INT,
	commentaire            TEXT,
	idAnnee                INT REFERENCES Annee(idAnnee)
);

-- ALTER TABLE HeureCours ADD CONSTRAINT fk_HeureCours_TypeCours FOREIGN KEY (idTypeCours) REFERENCES TypeCours(idTypeCours);
-- ALTER TABLE HeureCours ADD CONSTRAINT fk_HeureCours_Module FOREIGN KEY (idModule) REFERENCES Module(idModule);
-- ALTER TABLE Module ADD CONSTRAINT fk_Module_Semestre FOREIGN KEY (idSemestres) REFERENCES Semestre(idSemestre);
-- ALTER TABLE Intervention ADD CONSTRAINT fk_Intervention_Intervenant FOREIGN KEY (idIntervenant) REFERENCES Intervenant(idIntervenant);

-- Ajout des contraintes PRIMARY KEY manquantes
-- ALTER TABLE Intervention ADD PRIMARY KEY (idIntervention, idModule, idTypeCours);


/*
RCategorie    ( [idCategorie], nomCategorie, hMaxCategorie, hMinCategorie, idAnnee#)
RTypeCours    ( [idTypeCours], nomCours, coefficient, idAnnee#)
RHeureCours   ( [idModule#, idTypeCours#], heure, idAnnee#)
RModule       ( [idModule], nomModule, nbSemainesModule, idSemestre#, idAnnee#)
RIntervenant  ( [idIntervenant], prenom, nomIntervenant, email, hMinIntervenant, hMaxIntervenant, idCategorie#, idAnnee#)
RIntervention ( [(idIntervenant#, idModule#), idTypeCours#], nbSemainesIntervention, nbGroupe, idAnnee#)
RSemestre     ( [idSemestre], nbGTD, nbGTP, nbGCM, nbGAutre, idAnnee#)
RAnnee        ( [idAnnee], annee)
*/