package org.othello.model;

import org.othello.joueurs.*;
import org.othello.utils.CheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alain
 * Date: 10 janv. 2010
 * Time: 15:41:45
 * To change this template use File | Settings | File Templates.
 */
public class JeuxAuto implements EtatJeuxListener {

    public static Logger log = LoggerFactory.getLogger(JeuxAuto.class);

    private ModelOthello model;

    private Joueur joueur[];

    private Controleur controleur;
    private boolean pause_entre_tours = false;
    private int nb_tours;
    private long debut, fin;
    private boolean enregistre;
    private List<Coup> liste_coups;

    public JeuxAuto(ListeAlgos algo1, ListeAlgos algo2) {
        CheckUtils.checkArgument (algo1 != null);
        CheckUtils.checkArgument (algo2 != null);
        model = new ModelOthello();
        liste_coups = new ArrayList<Coup>();

        init(model, false, false, algo1, algo2);
    }

    /*private void init(ModelOthello model) {
		init(model,true,true,null,null);
	}*/

    private void init(ModelOthello model, boolean joueur1_humain, boolean joueur2_humain,
                      ListeAlgos algo1, ListeAlgos algo2) {
        int pos;
        AlgoRecherche algo;
        if (true) {
            pos = 3;
            model.setCouleur(CouleursJoueurs.Blanc, pos, pos);
            model.setCouleur(CouleursJoueurs.Noir, pos, pos + 1);
            model.setCouleur(CouleursJoueurs.Noir, pos + 1, pos);
            model.setCouleur(CouleursJoueurs.Blanc, pos + 1, pos + 1);
        } else if (false) {
            pos = 0;
            model.setCouleur(CouleursJoueurs.Blanc, pos, pos);
            model.setCouleur(CouleursJoueurs.Noir, pos, pos + 1);
            model.setCouleur(CouleursJoueurs.Blanc, pos, pos + 2);
            model.setCouleur(CouleursJoueurs.Blanc, pos, pos + 4);
        } else if (false) {
            for (int no_ligne = 0; no_ligne < 8; no_ligne++) {
                for (int no_colonne = 0; no_colonne < 8; no_colonne++) {
                    if ((no_ligne == 2 && no_colonne == 6) || (no_ligne == 3 && no_colonne == 7)) {
                        model.setCouleur(CouleursJoueurs.Blanc, no_ligne, no_colonne);
                    } else if (no_ligne > 3 && no_colonne == 7) {
                        //model.setCouleur(CouleursJoueurs.Blanc, no_ligne, no_colonne);
                    } else {
                        model.setCouleur(CouleursJoueurs.Noir, no_ligne, no_colonne);
                    }
                }
            }
        } else if (false) {
            model.setCouleur(CouleursJoueurs.Blanc, 0, 0);
            model.setCouleur(CouleursJoueurs.Blanc, 0, 1);
            model.setCouleur(CouleursJoueurs.Blanc, 0, 2);
            model.setCouleur(CouleursJoueurs.Blanc, 0, 3);
            model.setCouleur(CouleursJoueurs.Blanc, 0, 4);

            model.setCouleur(CouleursJoueurs.Blanc, 1, 0);
            model.setCouleur(CouleursJoueurs.Blanc, 1, 1);
            model.setCouleur(CouleursJoueurs.Blanc, 1, 2);
            model.setCouleur(CouleursJoueurs.Noir, 1, 3);

            model.setCouleur(CouleursJoueurs.Blanc, 2, 0);
            model.setCouleur(CouleursJoueurs.Blanc, 2, 1);
            model.setCouleur(CouleursJoueurs.Noir, 2, 2);
            model.setCouleur(CouleursJoueurs.Noir, 2, 3);

            model.setCouleur(CouleursJoueurs.Blanc, 3, 0);
            model.setCouleur(CouleursJoueurs.Noir, 3, 2);
            model.setCouleur(CouleursJoueurs.Noir, 3, 3);
            model.setCouleur(CouleursJoueurs.Noir, 3, 4);

            model.setCouleur(CouleursJoueurs.Noir, 4, 3);
            model.setCouleur(CouleursJoueurs.Noir, 4, 4);
            model.setCouleur(CouleursJoueurs.Noir, 4, 5);

        }
        //joueur_courant= CouleursJoueurs.Noir;
        controleur = new Controleur(model, this, null);
        controleur.setMultithread(false);
        controleur.setGui(false);
        //cadrillage.setControleur(controleur);
        joueur = new Joueur[2];
        if (joueur1_humain) {
            joueur[0] = new JoueurHumain(model, CouleursJoueurs.Noir, controleur);
        } else {
            CheckUtils.checkArgument (algo1 != null);
            //algo = new Algo1(model, CouleursJoueurs.Noir);
            algo = ListeAlgos.getAlgo(algo1, model, CouleursJoueurs.Noir);
            joueur[0] = new JoueurOrdiSimple(model, CouleursJoueurs.Noir, controleur, algo);
        }
        //cadrillage.addListener(joueur[0]);
        //joueur[1] = new JoueurHumain(model, CouleursJoueurs.Blanc, controleur);
        if (joueur2_humain) {
            joueur[1] = new JoueurHumain(model, CouleursJoueurs.Blanc, controleur);
        } else {
            CheckUtils.checkArgument (algo2 != null);
            //algo=new Algo1(model, CouleursJoueurs.Blanc);
            algo = ListeAlgos.getAlgo(algo2, model, CouleursJoueurs.Blanc);
            joueur[1] = new JoueurOrdiSimple(model, CouleursJoueurs.Blanc, controleur, algo);
        }
        if (!pause_entre_tours) {
            for (Joueur j : joueur) {
                if (j instanceof JoueurOrdiSimple) {
                    JoueurOrdiSimple j0 = (JoueurOrdiSimple) j;
                    j0.setDesactive_attente(true);
                }
            }
        }
        //cadrillage.addListener(joueur[1]);
        //joueur_courant=0;
        //controleur.demarrage(joueur,0);
    }


    public void demarrage() {
        debut = System.currentTimeMillis();
        nb_tours = 0;
        System.out.println("model=" + model.toString());
        controleur.demarrage(joueur, 0);
        System.out.println("suite");
        System.err.println("suite");
        while (controleur.change_joueur()) {
            System.out.println("suite0");
        }
        System.out.println("Fin du jeu:\n" + model.toString());
    }

    public void changement_joueur(Joueur joueur) {
        log.info("Changement de joueur");
        affiche_tableau();
        affiche_duree();
        nb_tours++;
    }

    private void affiche_duree() {
        log.info("durée:\n" + controleur.affiche_temps());
    }

    public void joueur_bloque(Joueur joueur) {
        log.info("Joueur " + joueur + " bloqué");
    }

    public void fin_partie() {
        log.info("Fin de partie");
        fin = System.currentTimeMillis();
        affiche_tableau();
    }

    public void case_incorrecte(Couleurs couleur, int no_ligne, int no_colonne) {
        log.info("Case incorrecte");
    }

    public void joueur_joue(Couleurs couleur, int no_ligne, int no_colonne) {
        if (enregistre) {
            Coup c;
            ModelOthello model2;
            model2 = new ModelOthello(model);
            c = new Coup(couleur, no_ligne, no_colonne, model2);
            liste_coups.add(c);
        }
    }

    public void affiche_tableau() {
        String s, debut;
        Couleurs c;
        Joueur[] tab;
        Joueur joueur;
        int max, score;
        if (!controleur.fini()) {
            tab = controleur.getListe_joueurs();
            joueur = tab[controleur.getJoueur_courant()];
            c = joueur.getCouleur();
            debut = "Joueur qui doit jouer:" + c + "\n";
        } else {
            tab = controleur.getListe_joueurs();
            debut = "";
            max = -1;
            joueur = null;
            for (Joueur j : tab) {
                c = j.getCouleur();
                score = model.getScore(c);
                debut += "Joueur " + c + " : " + score + "\n";
                if (score > max) {
                    max = score;
                    joueur = j;
                }
            }
            c = joueur.getCouleur();
            debut += "Gagnant : " + c + "\n";
            debut += "Nb tours : " + nb_tours + "\n";
            debut += "Durée : " + conv(this.fin - this.debut) + "\n";
        }
        debut += "Tableau:\n";
        s = "";
        for (int i = 0; i < model.getNbLignes(); i++) {
            for (int j = 0; j < model.getNbColonnes(); j++) {
                c = model.get(i, j);
                if (c == null) {
                    s += " ";
                } else if (c instanceof CouleursJoueurs) {
                    CouleursJoueurs c2 = (CouleursJoueurs) c;
                    switch (c2) {
                        case Noir:
                            s += "N";
                            break;
                        case Blanc:
                            s += "B";
                            break;
                        default:
                            CheckUtils.checkArgument (false);
                    }
                } else {
                    CheckUtils.checkArgument (false);
                }
                s += "|";
            }
            s += "\n";
        }
        log.info(debut + s);
    }

    private String conv(long duree) {
        String s;
        long tmp, tmp2, tmp3, tmp4;
        tmp = duree / 1000L;
        tmp2 = duree - tmp * 1000L;
        tmp3 = tmp / 60L;
        tmp4 = tmp - tmp3 * 60L;
        s = tmp3 + "m" + tmp4 + "s" + tmp2 + "ms";
        return s;
    }

    public boolean isEnregistre() {
        return enregistre;
    }

    public void setEnregistre(boolean enregistre) {
        this.enregistre = enregistre;
    }

    public List<Coup> getListe_coups() {
        return liste_coups;
    }

    public Joueur[] getJoueur() {
        return joueur;
    }
}
