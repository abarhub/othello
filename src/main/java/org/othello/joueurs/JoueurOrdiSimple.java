package org.othello.joueurs;

import org.othello.model.Controleur;
import org.othello.model.Couleurs;
import org.othello.model.ModelOthello;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.logging.Logger;

/**
 * User: Barret
 * Date: 6 déc. 2009
 * Time: 11:01:25
 */
public class JoueurOrdiSimple extends JoueurNormal implements Runnable {

    public static Logger log = Logger.getLogger("org.othello.joueurs.JoueurOrdiSimple");

    private AlgoRecherche algo;
    // attente avant de commencer a chercher une solution
    public static final int ATTENTE = 1000;
    private boolean desactive_attente = false;

    public JoueurOrdiSimple(ModelOthello model, Couleurs couleur, Controleur controleur, AlgoRecherche algo) {
        super(model, couleur, controleur);
        this.algo = algo;
    }

    public void reflechie() {
        if (joue) {
            Point2D point;
            int no_ligne, no_colonne;
            if (algo != null) {
                point = algo.cherche_case();
            } else {
                point = cherche_case();
            }
            if (point != null) {
                no_ligne = (int) point.getX();
                no_colonne = (int) point.getY();
                log.info("Trouvé : " + no_ligne + "," + no_colonne);
                setChoixCase(no_ligne, no_colonne);
            } else {
                log.info("Pas trouvé de solution");
            }
        }
    }

    private Point2D cherche_case() {
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

    public void run() {
        if (!isDesactive_attente()) {
            try {
                Thread.sleep(ATTENTE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        reflechie();
    }

    public boolean isDesactive_attente() {
        return desactive_attente;
    }

    public void setDesactive_attente(boolean desactive_attente) {
        this.desactive_attente = desactive_attente;
    }

}
