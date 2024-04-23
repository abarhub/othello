package org.othello.gui;

import org.othello.joueurs.ListeAlgos;

public record NouveauResultat(boolean joueur1Humain, ListeAlgos algoJoueur1,
                              boolean joueur2Humain, ListeAlgos algoJoueur2) {
}
