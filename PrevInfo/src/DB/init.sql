DROP TABLE IF EXISTS Categorie;
DROP TABLE IF EXISTS TypeCours;
DROP TABLE IF EXISTS Module;
DROP TABLE IF EXISTS Intervenant;
DROP TABLE IF EXISTS Intervention;
DROP TABLE IF EXISTS Semestre;

CREATE TABLE Categorie(
	idCategorie serial PRIMARY KEY,
	nomCategorie  varchar(255),
	hMaxCategorie int,
	hMinCategorie int
);

CREATE TABLE TypeCours(
	idTypeCours serial PRIMARY KEY,
	nomCours    int,
	coefficient int
);

CREATE TABLE HeureCours(
	idTypeCours int references TypeCours(idTypeCours),
	idModule    int references Module(idModule),
	heure       int
);

CREATE TABLE Module(
	idModule serial PRIMARY KEY,
	nomModule        varchar(255),
	nbSemainesModule int,
	idSemestres      int references Semestre(idSemestres)
);

CREATE TABLE Intervenant(
	idIntervenant serial PRIMARY KEY, 
	prenom          varchar(255),
	nomIntervenant  varchar(255),
	email           varchar(255),
	hMinIntervenant int,
	hMaxIntervenant int,
	idCategorie     int references Categorie(idCategorie)
);

CREATE TABLE Intervention(
	idIntervenant          int references Intervenant(idIntervenant),
	idModule               int references Module(idModule),
	idTypeCours            int references TypeCours(idTypeCours),
	nbSemainesIntervention int,
	nbGroupe
	
	PRIMARY KEY ((idIntervenant,idModule),idTypeCours)
);

CREATE TABLE Semestre(
	idSemestres serial PRIMARY KEY, 
	nbGTD       int,
	nbGTP       int,
	nbGCM       int,
	nbGAutre    int
);

CREATE TABLE Annee(
	idAnnee serial PRIMARY Key,
	annee varchar(9)
);

/*RCategorie    ( [idCategorie], nomCategorie, hMaxCategorie, hMinCategorie, idAnnee#)
RTypeCours    ( [idTypeCours], nomCours, coefficient, idAnnee#)
RHeureCours   ( [idModule#, idTypeCours#], heure, idAnnee#)
RModule       ( [idModule], nomModule, nbSemainesModule, idSemestre#, idAnnee#)
RIntervenant  ( [idIntervenant], prenom, nomIntervenant, email, hMinIntervenant, hMaxIntervenant, idCategorie#, idAnnee#)
RIntervention ( [(idIntervenant#, idModule#), idTypeCours#], nbSemainesIntervention, nbGroupe, idAnnee#)
RSemestre     ( [idSemestre], nbGTD, nbGTP, nbGCM, nbGAutre, idAnnee#)
RAnnee        ( [idAnnee], annee)*/