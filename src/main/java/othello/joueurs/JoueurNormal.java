package othello.joueurs;

import othello.model.Controleur;
import othello.model.Couleurs;
import othello.model.ModelOthello;

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

    public Point2D getChoixCase() {
        Point2D res = null;
        return res;
    }


    public Couleurs getCouleur() {
        return couleur;
    }

    protected boolean setChoixCase(int no_ligne, int no_colonne) {
        assert (joue);
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
