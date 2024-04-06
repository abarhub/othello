package org.othello.joueurs;

import org.othello.model.Couleurs;
import org.othello.model.CouleursJoueurs;
import org.othello.model.ModelOthello;
import org.othello.utils.CheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Barret
 * Date: 10 déc. 2009
 * Time: 22:00:47
 */
public class AlgoMinMax implements AlgoRecherche {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlgoMinMax.class);

    private ModelOthello model;
    private Couleurs couleur;
    private int niveau_profondeur;

    public AlgoMinMax(ModelOthello model, Couleurs couleur, int niveau_profondeur) {
        this.model = model;
        this.couleur = couleur;
        this.niveau_profondeur = niveau_profondeur;
    }


    public Point2D cherche_case() {
        Point2D res = null;
        Point2D meilleur = null, tmp;
        final int arbre = niveau_profondeur;
        Position pos;
        int profondeur = arbre * 2;
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
//        log.setLevel(Level.ALL);
//        System.out.println("Message avec println");
//        log.trace("Message avec log");
//        System.out.println("Message 2 avec println");
        LOGGER.info("Recherche case..." + model + "profondeur=" + profondeur);
        pos = meilleur_score(model, couleur, profondeur);
        LOGGER.info("Fin de recherche case..." + model + "profondeur=" + profondeur);
        //if(pos!=null)
        {
            meilleur = pos.getPoint();
            CheckUtils.checkArgument (meilleur != null);
        }
        //log.info("meilleur socre="+ score_meilleur);
        return meilleur;
    }

    private int calcul_score(ModelOthello model, int no_ligne, int no_colonne) {
        //return model.score_si(couleur,no_ligne,no_colonne);
        return model.getScore(couleur);
    }

    private Position meilleur_score(ModelOthello model, Couleurs couleur, int niveau) {
        Point2D tmp;
        int score_meilleur, score;
        List<Position> liste;
        Position tmp2;
        ModelOthello m;
        Couleurs c;//AlgoMinMax
        Position pos, meilleur = null;
        LOGGER.debug("enter AlgoMinMax meilleur_score couleur={} niveau={}", couleur, niveau);
        CheckUtils.checkArgument (niveau > -1);
        liste = new ArrayList<Position>();
        for (int no_ligne = 0; no_ligne < model.getNbLignes(); no_ligne++) {
            for (int no_colonne = 0; no_colonne < model.getNbColonnes(); no_colonne++) {
                if (model.isCaseValide(couleur, no_ligne, no_colonne)) {
                    LOGGER.trace("model={} case({},{}) couleur={}, niveau={}", model, no_ligne, no_colonne, couleur, niveau);
                    tmp = new Point(no_ligne, no_colonne);
                    m = new ModelOthello(model);
                    //m.setCouleur(couleur,no_ligne,no_colonne);
                    m.SetVerifCouleur(couleur, no_ligne, no_colonne);
                    c = donneAutreJoueur(couleur);
                    if (m.peut_jouer(c)) {
                        if (niveau <= 0) {
                            //score = calcul_score(model, no_ligne, no_colonne);
                            score = m.getScore(this.couleur);
                        } else {
                            pos = meilleur_score(m, c, niveau - 1);
                            score = pos.getScore();
                        }
                    } else {
                        //score = calcul_score(model, no_ligne, no_colonne);
                        score = m.getScore(this.couleur);
                    }
                    m.undo();
                    m = null;
                    //tmp2 = new Position(tmp, m);
                    tmp2 = new Position(tmp, null);
                    tmp2.setScore(score);
                    tmp2.setPoint(tmp);
                    liste.add(tmp2);
					/*if (score > 0) {
						m=new ModelOthello(model);
						m.setCouleur(couleur,no_ligne,no_colonne);
						tmp2=new Position(tmp,m);
						tmp2.setScore(score);
						liste.add(tmp2);
					}*/

                }
            }
        }
        LOGGER.trace("liste={}", liste.size());
        if (!liste.isEmpty()) {// il y a au moins un coup à jouer
            if (niveau % 2 == 0) {
                //score_meilleur = 0;
                score_meilleur = Integer.MIN_VALUE;
            } else {
                score_meilleur = Integer.MAX_VALUE;
            }
            meilleur = null;
            for (Position p : liste) {
                if (niveau % 2 == 0) {
                    if (p.getScore() > score_meilleur) {
                        score_meilleur = p.getScore();
                        meilleur = p;
                    }
                } else {
                    if (p.getScore() < score_meilleur) {
                        score_meilleur = p.getScore();
                        meilleur = p;
                    }
                }
            }
        } else {// il n'y a pas de coup a jouer
            meilleur = new Position(null, model);
            //meilleur.setScore(0);
            meilleur.setScore(model.getScore(this.couleur));
        }
        CheckUtils.checkArgument (meilleur != null);
        LOGGER.debug("exit AlgoMinMax meilleur_score {}", meilleur);
        return meilleur;
    }

    private Couleurs donneAutreJoueur(Couleurs couleur) {
        if (couleur == CouleursJoueurs.Blanc)
            return CouleursJoueurs.Noir;
        else
            return CouleursJoueurs.Blanc;
    }
}