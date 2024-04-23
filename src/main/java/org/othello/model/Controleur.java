package org.othello.model;

import org.othello.gui.CadrillageListener;
import org.othello.gui.CliqueListener;
import org.othello.joueurs.Joueur;
import org.othello.utils.CheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Barret
 * Date: 6 déc. 2009
 * Time: 09:45:13
 */
public class Controleur implements CliqueListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(Controleur.class);

    private ModelOthello model;
    //private FenetreSimple fenetre;
//    private Cadrillage cadrillage;
    private Joueur[] liste_joueurs;
    private int joueur_courant;
    private boolean traite_clique;
    private EtatJeuxListener etat_jeux;
    private boolean multithread = true;
    private long debut_temps;
    private Map<Integer, List<Long>> temps_joueurs;
    private boolean gui = true;

    public Controleur(ModelOthello model, EtatJeuxListener etat_jeux, CadrillageListener cadrillage) {
        this.model = model;
        if (cadrillage != null) {
            cadrillage.addListener(this);
        }
        joueur_courant = -1;
        this.etat_jeux = etat_jeux;
        temps_joueurs = new HashMap<Integer, List<Long>>();
    }

    public boolean setChoixCase(Couleurs couleur, int no_ligne, int no_colonne) {
        CheckUtils.checkArgument(isDemarrer());
        LOGGER.info("Joueur " + couleur + " a joue " + no_ligne + "," + no_colonne);
        if (model.VerifCouleur(couleur, no_ligne, no_colonne))
        //if(model.SetVerifCouleur(couleur,no_ligne,no_colonne))
        {
            boolean b;
            etat_jeux.joueur_joue(couleur, no_ligne, no_colonne);
            b = model.SetVerifCouleur(couleur, no_ligne, no_colonne);
            CheckUtils.checkArgument(b = true);
            //cadrillage.repaint();
            if (gui) {
                //cadrillage.repaint();
                change_joueur();
            }
            return true;
        } else {
            LOGGER.info("Case incorrecte pour le joueur " + couleur + " (" + no_ligne + "," + no_colonne + ")");
            etat_jeux.case_incorrecte(couleur, no_ligne, no_colonne);
            return false;
        }
    }

    public boolean change_joueur() {
        joueur_courant = (joueur_courant + 1) % liste_joueurs.length;
        if (peut_jouer(joueur_courant)) {
            activation(joueur_courant);
            //liste_joueurs[joueur_courant].reflechie();
            //fenetre.mise_a_jour();
            etat_jeux.changement_joueur(liste_joueurs[joueur_courant]);
            return true;
        } else if (!fini() && !peut_jouer(joueur_courant)) {
            LOGGER.info("Le joueur " + liste_joueurs[joueur_courant].getCouleur() + " saute son tour !");
            etat_jeux.joueur_bloque(liste_joueurs[joueur_courant]);
            joueur_courant = (joueur_courant + 1) % liste_joueurs.length;
            activation(joueur_courant);
            //fenetre.mise_a_jour();
            etat_jeux.changement_joueur(liste_joueurs[joueur_courant]);
            return true;
        } else {
            LOGGER.info("Fin de la partie");
            //fenetre.mise_a_jour();
            etat_jeux.fin_partie();
            return false;
        }
    }

    public boolean fini() {
        for (int n = 0; n < liste_joueurs.length; n++) {
            if (peut_jouer(n)) {
                return false;
            }
        }
        return true;
    }

    private boolean peut_jouer(int joueur_courant) {
        Couleurs couleur;
        CheckUtils.checkArgument(joueur_courant >= 0);
        CheckUtils.checkArgument(joueur_courant < liste_joueurs.length);
        couleur = liste_joueurs[joueur_courant].getCouleur();
        return model.peut_jouer(couleur);
    }

    public void demarrage(Joueur liste_joueurs[], int joueur_depart) {
        CheckUtils.checkArgument(!isDemarrer());
        CheckUtils.checkArgument(liste_joueurs != null);
        CheckUtils.checkArgument(liste_joueurs.length > 0);
        CheckUtils.checkArgument(joueur_depart >= 0);
        CheckUtils.checkArgument(joueur_depart < liste_joueurs.length);
        this.liste_joueurs = liste_joueurs;
        if (!model.peut_jouer(liste_joueurs[joueur_depart].getCouleur()) && !fini()) {
            etat_jeux.joueur_bloque(liste_joueurs[joueur_depart]);
            joueur_depart = (joueur_depart + 1) % liste_joueurs.length;
        }
        traite_clique = true;
        debut_temps = System.currentTimeMillis();
        temps_joueurs.clear();
        activation(joueur_depart);
        //liste_joueurs[joueur_courant].reflechie();
    }

    public void arrete() {
        CheckUtils.checkArgument(isDemarrer());
        liste_joueurs = null;
        joueur_courant = -1;
    }

    private void activation(int joueur_joue) {
        long temps, duree;
        //CheckUtils.checkArgument (isDemarrer());
        //CheckUtils.checkArgument(joueur_joue!= joueur_courant);
        traite_clique = false;
        LOGGER.info("Joueur n°" + joueur_joue + " de jouer (" + liste_joueurs[joueur_joue].getCouleur() + ")");
        //cadrillage.removeListener(liste_joueurs[joueur_courant]);
        temps = System.currentTimeMillis();
        duree = temps - debut_temps;
        debut_temps = temps;
        if (temps_joueurs.containsKey(joueur_courant)) {
            temps_joueurs.get(joueur_courant).add(duree);
        } else {
            temps_joueurs.put(joueur_courant, new ArrayList<Long>());
            temps_joueurs.get(joueur_courant).add(duree);
        }
        joueur_courant = joueur_joue;
        for (int i = 0; i < liste_joueurs.length; i++) {
            if (i == joueur_courant) {
                liste_joueurs[i].setJoue(true);
                //cadrillage.addListener(liste_joueurs[i]);
            } else {
                liste_joueurs[i].setJoue(false);
            }
        }
        traite_clique = true;
        {
            Joueur tmp;
            tmp = liste_joueurs[joueur_courant];
            if (tmp instanceof Runnable) {
                if (multithread) {
                    Thread t;
                    Runnable tmp2 = (Runnable) tmp;
                    t = new Thread(tmp2);
                    t.start();
	                /*if(!gui)
	                {
						try {
							t.join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
	                }*/
                } else {
                    Runnable tmp2 = (Runnable) tmp;
                    tmp2.run();
                }
            }
        }

    }

    public void clique(int no_ligne, int no_colonne) {
        if (isDemarrer()) {
            LOGGER.info("Clique ?");
            if (traite_clique) {
                liste_joueurs[joueur_courant].clique(no_ligne, no_colonne);
            } else {
                LOGGER.info("Pas de traitement");
            }
        }
    }

    public Couleurs getCouleurCourrante() {
        if (isDemarrer())
            return liste_joueurs[joueur_courant].getCouleur();
        else
            return null;
    }

    public boolean isDemarrer() {
        return joueur_courant != -1;
    }

    public Joueur[] getListe_joueurs() {
        return liste_joueurs;
    }

    public int getJoueur_courant() {
        return joueur_courant;
    }

    public boolean isMultithread() {
        return multithread;
    }

    public void setMultithread(boolean multithread) {
        this.multithread = multithread;
    }

    public Map<Integer, List<Long>> getTemps_joueurs() {
        return temps_joueurs;
    }

    public String affiche_temps() {
        String s, s2;
        List<Long> liste;
        long tmp1, tmp2, tmp3, tmp4, total;
        s = "";
        for (int n : temps_joueurs.keySet()) {
            liste = temps_joueurs.get(n);
            if (liste_joueurs != null && n >= 0 && n < liste_joueurs.length) {
                Joueur j;
                j = liste_joueurs[n];
                s += "Joueur " + j.getCouleur() + ":";
            } else {
                s += "Joueur n°" + n + ":";
            }
            total = 0L;
            for (long tmp : liste) {
                total += tmp;
                s2 = conv_duree(tmp);
                s += s2 + ",";
            }
            s += ", total=" + conv_duree(total);
            s += "\n";
        }
        return s;
    }

    private String conv_duree(long tmp) {
        String s2;
        long tmp1;
        long tmp2;
        long tmp3;
        long tmp4;
        s2 = "";
        tmp1 = tmp % 1000L;
        tmp2 = (tmp - tmp1) / 1000L;
        s2 = tmp1 + "ms";
        if (tmp2 > 0) {
            tmp3 = tmp2 % 60L;
            tmp4 = (tmp2 - tmp3) / 60L;
            s2 = tmp3 + "s" + s2;
            if (tmp4 > 0) {
                s2 = tmp4 + "min" + s2;
            }
        }
        return s2;
    }

    public boolean isGui() {
        return gui;
    }

    public void setGui(boolean gui) {
        this.gui = gui;
    }
}
