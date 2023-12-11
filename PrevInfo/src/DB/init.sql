DROP TABLE IF EXISTS Annee CASCADE;
DROP TABLE IF EXISTS Categorie CASCADE;
DROP TABLE IF EXISTS HeureCours CASCADE;
DROP TABLE IF EXISTS TypeCours CASCADE;
DROP TABLE IF EXISTS Module CASCADE;
DROP TABLE IF EXISTS Intervenant CASCADE;
DROP TABLE IF EXISTS Intervention CASCADE;
DROP TABLE IF EXISTS Semestre CASCADE;

CREATE TABLE Annee(
	idAnnee serial PRIMARY Key,
	annee varchar(9)
);

CREATE TABLE TypeCours(
	idTypeCours serial PRIMARY KEY,
	nomCours    int,
	coefficient int
);

CREATE TABLE Categorie(
	idCategorie serial PRIMARY KEY,
	nomCategorie  varchar(255),
	hMaxCategorie int,
	hMinCategorie int,
	idAnnee       int references Annee(idAnnee)
);

CREATE TABLE Semestre(
	idSemestre  serial PRIMARY KEY, 
	nbGTD       int,
	nbGTP       int,
	nbGCM       int,
	nbGAutre    int,
	idAnnee     int references Annee(idAnnee)
);

CREATE TABLE Module(
	idModule serial PRIMARY KEY,
	nomModule        varchar(255),
	nbSemainesModule int,
	idAnnee          int references Annee(idAnnee),
	idSemestre       int references Semestre(idSemestre)
);

CREATE TABLE HeureCours(
	idTypeCours int references TypeCours(idTypeCours),
	idModule    int references Module(idModule),
	heure       int
);

CREATE TABLE Intervenant(
	idIntervenant serial PRIMARY KEY, 
	prenom          varchar(255),
	nom             varchar(255),
	email           varchar(255),
	hMinIntervenant int,
	hMaxIntervenant int,
	idAnnee         int references Annee(idAnnee),
	idCategorie     int references Categorie(idCategorie)
);

CREATE TABLE Intervention(
	idIntervention         int references Intervenant(idIntervenant),
	idModule               int references Module(idModule),
	idTypeCours            int references TypeCours(idTypeCours),
	nbSemainesIntervention int,
	nbGroupe               int,
	idAnnee                int references Annee(idAnnee),
	
	PRIMARY KEY (idIntervention,idModule,idTypeCours)
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