


RCategorie    ( [idCategorie], nomCategorie, hMaxCategorie, hMinCategorie)
RTypeCours    ( [idTypeCours], nomCours, coefficient)
RModule       ( [idModule], nomModule, nbSemainesModule, hTD, hTP, hCM, idSemaine#)
RIntervenant  ( [idIntervenant], prenom, nomIntervenant, email, hMinIntervenant, hMaxIntervenant, idCategorie#)
RIntervention ( [(idIntervenant#, idModule#), idTypeCours#], nbSemainesIntervention, nbGroupe)
RSemestre     ( [idSemestres], nbGTD, nbGTP, nbGCM, nbGAutre)