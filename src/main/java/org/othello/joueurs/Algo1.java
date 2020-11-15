package org.othello.joueurs;

import org.othello.model.Couleurs;
import org.othello.model.ModelOthello;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * User: Barret
 * Date: 6 déc. 2009
 * Time: 13:45:05
 */
public class Algo1 implements AlgoRecherche {

    private ModelOthello model;
    private Couleurs couleur;

    public Algo1(ModelOthello model, Couleurs couleur) {
        this.model = model;
        this.couleur = couleur;
    }


    public Point2D cherche_case() {
        Point2D res = null;
        for (int no_ligne = 0; no_ligne < model.getNbLignes(); no_ligne++) {
            for (int no_colonne = 0; no_colonne < model.getNbLignes(); no_colonne++) {
                if (model.isCaseValide(couleur, no_ligne, no_colonne)) {
                    res = new Point(no_ligne, no_colonne);
                    return res;
                }
            }
        }
        return res;
    }
}
