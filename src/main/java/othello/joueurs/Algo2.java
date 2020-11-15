package othello.joueurs;

import othello.model.Couleurs;
import othello.model.ModelOthello;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.logging.Logger;

/**
 * User: Barret
 * Date: 10 déc. 2009
 * Time: 22:00:47
 */
public class Algo2 implements AlgoRecherche {

    public static Logger log = Logger.getLogger("othello.joueurs.Algo2");

    private ModelOthello model;
    private Couleurs couleur;

    public Algo2(ModelOthello model, Couleurs couleur) {
        this.model = model;
        this.couleur = couleur;
    }


    public Point2D cherche_case() {
        Point2D res = null;
        Point2D meilleur = null, tmp;
        int score_meilleur, score;
        score_meilleur = 0;
        for (int no_ligne = 0; no_ligne < model.getNbLignes(); no_ligne++) {
            for (int no_colonne = 0; no_colonne < model.getNbLignes(); no_colonne++) {
                if (model.isCaseValide(couleur, no_ligne, no_colonne)) {
                    tmp = new Point(no_ligne, no_colonne);
                    score = calcul_score(model, no_ligne, no_colonne);
                    if (score > score_meilleur) {
                        score_meilleur = score;
                        meilleur = tmp;
                    }
                }
            }
        }
        assert (meilleur != null);
        log.info("meilleur socre=" + score_meilleur);
        return meilleur;
    }

    private int calcul_score(ModelOthello model, int no_ligne, int no_colonne) {
        return model.score_si(couleur, no_ligne, no_colonne);
    }
}
