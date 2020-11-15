package org.othello.joueurs;

import org.othello.model.Couleurs;
import org.othello.model.CouleursJoueurs;
import org.othello.model.ModelOthello;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * User: Barret
 * Date: 10 déc. 2009
 * Time: 22:00:47
 */
public class Algo3 implements AlgoRecherche {

    public static Logger log = Logger.getLogger("org.othello.joueurs.Algo3");

    private ModelOthello model;
    private Couleurs couleur;

    public Algo3(ModelOthello model, Couleurs couleur) {
        this.model = model;
        this.couleur = couleur;
    }


    public Point2D cherche_case() {
        Point2D res = null;
        Point2D meilleur = null, tmp;
		/*int score_meilleur,score;
		score_meilleur=0;
		for (int no_ligne = 0; no_ligne < model.getNbLignes(); no_ligne++) {
			for (int no_colonne = 0; no_colonne < model.getNbLignes(); no_colonne++) {
				if (model.isCaseValide(couleur, no_ligne, no_colonne)) {
					tmp = new Point(no_ligne, no_colonne);
					score=calcul_score(model,no_ligne,no_colonne);
					if(score> score_meilleur)
					{
						score_meilleur=score;
						meilleur=tmp;
					}
				}
			}
		}*/
        meilleur = meilleur_score(model, couleur);
        assert (meilleur != null);
        //log.info("meilleur socre="+ score_meilleur);
        return meilleur;
    }

    private int calcul_score(ModelOthello model, int no_ligne, int no_colonne) {
        return model.score_si(couleur, no_ligne, no_colonne);
    }

    private Point2D meilleur_score(ModelOthello model, Couleurs couleur) {
        Point2D meilleur = null, tmp;
        int score_meilleur, score;
        List<Position> liste;
        Position tmp2;
        ModelOthello m;
        liste = new ArrayList<Position>();
        for (int no_ligne = 0; no_ligne < model.getNbLignes(); no_ligne++) {
            for (int no_colonne = 0; no_colonne < model.getNbLignes(); no_colonne++) {
                if (model.isCaseValide(couleur, no_ligne, no_colonne)) {
                    tmp = new Point(no_ligne, no_colonne);
                    score = calcul_score(model, no_ligne, no_colonne);
                    if (score > 0) {
                        m = new ModelOthello(model);
                        m.setCouleur(couleur, no_ligne, no_colonne);
                        tmp2 = new Position(tmp, m);
                        tmp2.setScore(score);
                        liste.add(tmp2);
                    }
                }
            }
        }
        score_meilleur = 0;
        meilleur = null;
        for (Position p : liste) {
            if (p.getScore() > score_meilleur) {
                score_meilleur = p.getScore();
                meilleur = p.getPoint();
            }
        }
        assert (meilleur != null);
        return meilleur;
    }

    private Couleurs donneAutreJoueur(Couleurs couleur) {
        if (couleur == CouleursJoueurs.Blanc)
            return CouleursJoueurs.Noir;
        else
            return CouleursJoueurs.Blanc;
    }
}

