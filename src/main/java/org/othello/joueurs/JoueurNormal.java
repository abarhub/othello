package org.othello.joueurs;

import org.othello.model.Controleur;
import org.othello.model.Couleurs;
import org.othello.model.ModelOthello;
import org.othello.utils.CheckUtils;

import java.awt.geom.Point2D;

/**
 * User: Barret
 * Date: 6 déc. 2009
 * Time: 10:05:29
 */
public abstract class JoueurNormal implements Joueur {

    protected ModelOthello model;
    protected Couleurs couleur;
    private Controleur controleur;
    protected boolean joue;

    public JoueurNormal(ModelOthello model, Couleurs couleur, Controleur controleur) {
        CheckUtils.checkArgument(model != null);
        CheckUtils.checkArgument(couleur != null);
        CheckUtils.checkArgument(controleur != null);
        this.model = model;
        this.couleur = couleur;
        this.controleur = controleur;
        joue = false;
    }

    public void setJoue(boolean joue) {
        this.joue = joue;
    }

    public void clique(int no_ligne, int no_colonne) {

    }

    public Couleurs getCouleur() {
        return couleur;
    }

    protected boolean setChoixCase(int no_ligne, int no_colonne) {
        CheckUtils.checkArgument (joue);
        if (controleur.setChoixCase(couleur, no_ligne, no_colonne)) {
            //joue=false;
            return true;
        } else {
            return false;
        }
    }

    public void reflechie() {

    }
}
