package org.othello.joueurs;

import org.othello.model.Couleurs;
import org.othello.model.CouleursJoueurs;
import org.othello.model.ModelOthello;
import org.othello.utils.CheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.*;

/**
 * User: Barret
 * Date: 10 déc. 2009
 * Time: 22:00:47
 */
public class AlgoAlphaBeta implements AlgoRecherche {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlgoAlphaBeta.class);

    private final ModelOthello model;
    private final Couleurs couleur;
    private final int niveau_profondeur;

    public AlgoAlphaBeta(ModelOthello model, Couleurs couleur, int niveau_profondeur) {
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
//        log.setLevel(Level.ALL);
//        System.out.println("Message avec println");
//        log.trace("Message avec log");
//        System.out.println("Message 2 avec println");
        LOGGER.info("Recherche case...{} profondeur={}", model, profondeur);
        pos = meilleur_score(model, couleur, profondeur, true,
                Integer.MIN_VALUE, Integer.MAX_VALUE);
        LOGGER.info("Fin de recherche case...{} profondeur={}", model, profondeur);
        meilleur = pos.getPoint();
        CheckUtils.checkArgument(meilleur != null);
        return meilleur;
    }

    private static final int tab_score[][] = {
            {200, -200, 100, 0, 0, 100, -200, 200},
            {-200, -200, 100, 0, 0, 100, -200, -200},
            {100, 100, 100, 0, 0, 100, 100, 100},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {100, 100, 100, 0, 0, 100, 100, 100},
            {-200, -200, 100, 0, 0, 100, -200, -200},
            {200, -200, 100, 0, 0, 100, -200, 200}
    };

    private int calcul_score(ModelOthello model, int no_ligne, int no_colonne, Couleurs couleur) {
        CheckUtils.checkArgument(model != null);
        CheckUtils.checkArgument(couleur != null);
        CheckUtils.checkArgument(no_ligne >= 0);
        CheckUtils.checkArgument(no_ligne < model.getNbLignes());
        CheckUtils.checkArgument(no_colonne >= 0);
        CheckUtils.checkArgument(no_colonne < model.getNbColonnes());
        if (true) {
            //return model.getScore(this.couleur);
            return model.getScore(couleur);
        } else {
            return model.getScore(couleur) + tab_score[no_ligne][no_colonne];
        }
    }

    private Position meilleur_score(ModelOthello model, Couleurs couleur, int niveau, boolean max,
                                    int min_score0, int max_score0) {
        Point2D tmp;
        int score_meilleur, score;
        int max_score, min_score;
        List<Position> liste;
        Position tmp2;
        ModelOthello m;
        Couleurs c;
        Position pos, meilleur = null;
        List<Couple> liste_coups;
        LOGGER.debug("enter AlgoAlphaBeta meilleur_score couleur={}, niveau={}", couleur, niveau);
        CheckUtils.checkArgument(niveau > -1);
        liste_coups = new ArrayList<>();
        for (int no_ligne = 0; no_ligne < model.getNbLignes(); no_ligne++) {
            for (int no_colonne = 0; no_colonne < model.getNbColonnes(); no_colonne++) {
                if (model.isCaseValide(couleur, no_ligne, no_colonne)) {
                    liste_coups.add(new Couple(no_ligne, no_colonne));
                }
            }
        }
        trie(liste_coups, couleur, model, max);
        liste = new ArrayList<Position>();
        max_score = Integer.MAX_VALUE;
        min_score = Integer.MIN_VALUE;
        for (Couple c2 : liste_coups) {
            //for (int no_ligne = 0; no_ligne < model.getNbLignes(); no_ligne++) {
            //for (int no_colonne = 0; no_colonne < model.getNbColonnes(); no_colonne++) {
            int no_ligne, no_colonne;
            no_ligne = c2.no_ligne();
            no_colonne = c2.no_colonne();
            CheckUtils.checkArgument(model.isCaseValide(couleur, no_ligne, no_colonne));
            //log.finest("model="+model+"case(" + no_ligne+","+no_colonne + ")couleur="+couleur+",niveau=" + niveau);
            tmp = new Point(no_ligne, no_colonne);
            m = new ModelOthello(model);
            m.SetVerifCouleur(couleur, no_ligne, no_colonne);
            c = donneAutreJoueur(couleur);
            if (m.peut_jouer(c)) {
                if (niveau <= 0) {
                    boolean methode1 = false;
							/*if(methode1)
							{
								score=m.getScore(this.couleur);
							}
							else*/
                    {
                        score = calcul_score(model, no_ligne, no_colonne, this.couleur);
                    }
                } else {
                    pos = meilleur_score(m, c, niveau - 1, !max, min_score, max_score);
                    score = pos.getScore();
                }
            } else {// l'autre joueur ne peut pas jouer => on continue avec le meme joueur
                if (niveau <= 0) {
                    //score = m.getScore(this.couleur);
                    score = calcul_score(model, no_ligne, no_colonne, this.couleur);
                } else {
                    pos = meilleur_score(m, couleur, niveau - 1, max, min_score, max_score);
                    score = pos.getScore();
                }
            }
            if (max) {
                if (max_score < score) {
                    max_score = score;
                }
            } else {
                if (min_score > score) {
                    min_score = score;
                }
            }
            m.undo();
            m = null;
            //tmp2 = new Position(tmp, m);
            tmp2 = new Position(tmp, null);
            tmp2.setScore(score);
            tmp2.setPoint(tmp);
            liste.add(tmp2);
            if (max) {
                if (max_score < min_score0) {
                    LOGGER.info("elagage max");
                    break;
                }
            } else {
                if (min_score > max_score0) {
                    LOGGER.info("elagage min");
                    break;
                }
            }
            //}
            //}
        }
        //log.fine("liste="+liste.size());
        //log.finest("liste=" + liste.size());
        if (!liste.isEmpty()) {// il y a au moins un coup à jouer
            if (max) {
                //score_meilleur = 0;
                score_meilleur = Integer.MIN_VALUE;
            } else {
                score_meilleur = Integer.MAX_VALUE;
            }
            meilleur = null;
            for (Position p : liste) {
                if (max) {
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
            //meilleur.setScore(model.getScore(this.couleur));
            meilleur.setScore(calcul_score(model, 0, 0, this.couleur));
        }
        CheckUtils.checkArgument(meilleur != null);
        LOGGER.debug("exit AlgoAlphaBeta meilleur_score {}", meilleur);
        return meilleur;
    }

    private void trie(List<Couple> liste_coups, Couleurs couleur, ModelOthello model, boolean max) {
        CheckUtils.checkArgument(liste_coups != null);
        CheckUtils.checkArgument(couleur != null);
        CheckUtils.checkArgument(model != null);
        if (!liste_coups.isEmpty()) {
            Map<Integer, List<Couple>> map;
            List<Couple> tmp;
            ModelOthello m;
            int no_ligne, no_colonne, score;
            map = new TreeMap<>();
            for (Couple c : liste_coups) {
                no_ligne = c.no_ligne();
                no_colonne = c.no_colonne();
                m = new ModelOthello(model);
                m.SetVerifCouleur(couleur, no_ligne, no_colonne);
                //score = m.getScore(this.couleur);
                score = calcul_score(model, no_ligne, no_colonne, this.couleur);
                if (map.containsKey(score)) {
                    tmp = map.get(score);
                } else {
                    tmp = new ArrayList<Couple>();
                    map.put(score, tmp);
                }
                tmp.add(c);
            }
            liste_coups.clear();
            //log.info("Ordre:");
            for (int n : map.keySet()) {
                //log.info("n="+n);
                tmp = map.get(n);
                for (Couple c : tmp) {
                    liste_coups.add(0, c);
                }
            }
            if (!max) {
                Collections.reverse(liste_coups);
            }
        }
    }

    private Couleurs donneAutreJoueur(Couleurs couleur) {
        if (couleur == CouleursJoueurs.Blanc)
            return CouleursJoueurs.Noir;
        else
            return CouleursJoueurs.Blanc;
    }
}