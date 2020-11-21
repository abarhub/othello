package org.othello.test_neurone;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.othello.joueurs.Joueur;
import org.othello.joueurs.ListeAlgos;
import org.othello.model.Couleurs;
import org.othello.model.Coup;
import org.othello.model.JeuxAuto;
import org.othello.model.ModelOthello;
import org.othello.neuronespack.Resultats;
import org.othello.utils.CheckUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


/**
 * Created by IntelliJ IDEA.
 * User: Alain
 * Date: 10 janv. 2010
 * Time: 13:52:58
 * To change this template use File | Settings | File Templates.
 */
public class TestNeuronne {

    public static Logger log = Logger.getLogger("org.othello.test_neurone.TestNeuronne");

    public static void main(String arg[]) {
        //test1();
        //test2();
        test3();
    }

    private static void test1() {
        SimpleTest1 tmp;
        int nbcc, r;
        int[] tailleCouches;
        double eta;
        double moment;
        double tab[];
        nbcc = SimpleTest1.NB_COUCHES_CACHEES;
        tailleCouches = SimpleTest1.TAILLE_COUCHES_CACHEES;
        eta = 0.9;
        moment = 0.75D;
        tmp = new SimpleTest1(nbcc, tailleCouches, eta, moment);
        /*for(int i=0;i<100;i++)
        {
            tmp.iteration();
        }*/
        tmp.apprentissage();
        for (int j = tmp.donneesApprentissage.length - 1; j >= 0; j--) {
            tab = tmp.donneesApprentissage[j];
            r = tmp.reconnaissance(tab);
            System.out.println("Résultat :" + r);
        }
        //affiche2(tmp);
    }

    private static void affiche(SimpleTest1 tmp) {
        double tab[], tab2[];
        tab = new double[]{};
        tab = tmp.donneesApprentissage[2];
        tmp.getDonneesCourantes().setVecteurEntree(tab);
        tmp.propagation();
        Resultats res = tmp.getResultats();
        System.out.println("Résultat :");
        for (int i = 0; i < res.getTailleVecteurSortie(); i++) {
            System.out.println(res.getValeurSortie(i));
        }
    }

    private static void affiche2(SimpleTest1 tmp) {
        double tab[], tab2[];
        int i;
        tab = new double[]{};
        tab = tmp.donneesApprentissage[2];
        i = tmp.reconnaissance(tab);
        //tmp.getDonneesCourantes().setVecteurEntree(tab);
        //tmp.propagation();
        //Resultats res= tmp.getResultats();
        System.out.println("Résultat :" + i);
        /*for(int i=0;i<res.getTailleVecteurSortie();i++)
        {
            System.out.println(res.getValeurSortie(i));
        }*/
    }

    private static void test2() {
        SimpleTest2 tmp;
        int nb_lignes = 8, nb_colonnes = 8;
        int nb_cases = nb_lignes * nb_colonnes, nb_exemple = 10, nb_exemple2, res;
        List<List<Double>> entree0, sortie0, entree1, entree2, sortie1, sortie2;
        double tmp2[][], tmp3[][], tmp4[];
        List<Double> liste;
        tmp = new SimpleTest2(nb_cases * 2, nb_cases, nb_cases);
        entree0 = new ArrayList<List<Double>>();
        sortie0 = new ArrayList<List<Double>>();
        entree1 = new ArrayList<List<Double>>();
        entree2 = new ArrayList<List<Double>>();
        sortie1 = new ArrayList<List<Double>>();
        sortie2 = new ArrayList<List<Double>>();
        log.info("calcul des exemples...");
        calcul(entree0, sortie0, nb_exemple, nb_lignes, nb_colonnes);
        log.info("fin de calcul des exemples");
        //calcul(entree2,sortie2,nb_exemple,nb_lignes,nb_colonnes);
        CheckUtils.checkArgument(entree0.size() == sortie0.size());
        nb_exemple2 = entree0.size() / 2;
        for (int i = 0; i < entree0.size(); i++) {
            if (i <= entree0.size() / 2) {
                entree1.add(entree0.get(i));
                sortie1.add(sortie0.get(i));
            } else {
                entree2.add(entree0.get(i));
                sortie2.add(sortie0.get(i));
            }
        }
        tmp2 = conv(entree1);
        tmp3 = conv(sortie1);
        log.info("apprentissage...");
        tmp.apprentissage(tmp2, tmp3);
        log.info("fin de l'apprentissage");
        log.info("test du résultat...");
        for (int i = 0; i < entree2.size(); i++) {
            liste = entree2.get(i);
            tmp4 = new double[liste.size()];
            for (int j = 0; j < liste.size(); j++) {
                tmp4[j] = liste.get(j);
            }
            res = tmp.reconnaissance(tmp4);
            log.info("res=" + res);
        }
        log.info("fin du test du résultat");
    }

    private static double[][] conv(List<List<Double>> liste) {
        double tab[][], d;
        int nb_colonnes;
        nb_colonnes = liste.get(0).size();
        tab = new double[liste.size()][nb_colonnes];
        for (int i = 0; i < liste.size(); i++) {
            CheckUtils.checkArgument(liste.get(i).size() == nb_colonnes);
            for (int j = 0; j < liste.get(i).size(); j++) {
                d = liste.get(i).get(j);
                tab[i][j] = d;
            }
        }
        return tab;
    }

    private static void calcul(List<List<Double>> entree1, List<List<Double>> sortie1,
                               int nb_exemple, int nb_lignes, int nb_colonnes) {
        int nb_coups;
        JeuxAuto jeux;
        List<Coup> liste_coups;
        Joueur joueur;
        ModelOthello model, model2;
        int no_ligne, no_colonne;
        boolean b;
        List<Double> tmp1, tmp2;
        nb_coups = 0;
        while (nb_coups < nb_exemple) {
            jeux = new JeuxAuto(ListeAlgos.Simple1, ListeAlgos.Simple1);
            jeux.setEnregistre(true);
            jeux.demarrage();
            //fin=System.currentTimeMillis();
            //log.info("Durée : "+(fin-debut)+"("+conv(fin-debut)+")");
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            joueur = jeux.getJoueur()[0];
            liste_coups = jeux.getListe_coups();
            for (Coup c : liste_coups) {
                if (c.getCouleur() == joueur.getCouleur()) {
                    model = c.getModel();
                    no_ligne = c.getNo_ligne();
                    no_colonne = c.getNo_colonne();
                    model2 = new ModelOthello(model);
                    b = model2.SetVerifCouleur(c.getCouleur(), no_ligne, no_colonne);
                    CheckUtils.checkArgument(b);
                    tmp1 = conv2(model, c.getCouleur());
                    //tmp2=conv2(model2);
                    tmp2 = new ArrayList<Double>();
                    for (int i = 0; i < model.getNbLignes(); i++) {
                        for (int j = 0; j < model.getNbColonnes(); j++) {
                            if (i == no_ligne && j == no_colonne) {
                                tmp2.add(1.0);
                            } else {
                                tmp2.add(0.0);
                            }
                        }
                    }
                    entree1.add(tmp1);
                    sortie1.add(tmp2);
                }
            }
            nb_coups++;
        }
        /*for(int i=0;i<nb_exemple;i++)
        {
            
        }*/
    }

    private static List<Double> conv2(ModelOthello model, Couleurs couleur) {
        List<Double> res;
        double d;
        Couleurs c;
        res = new ArrayList<Double>();
        for (int i = 0; i < model.getNbLignes(); i++) {
            for (int j = 0; j < model.getNbColonnes(); j++) {
                c = model.get(i, j);
                if (c == null) {
                    res.add(0.0);
                } else {
                    res.add(1.0);
                }
                if (c == couleur) {
                    res.add(1.0);
                } else {
                    res.add(0.0);
                }
            }
        }
        return res;
    }

    private static void test3() {
        SimpleTest3 tmp;
        int nbcc, r;
        int[] tailleCouches;
        double eta;
        double moment;
        double tab[];
        double tab2[][], tab3[][];
        int entree, sortie;
        nbcc = SimpleTest1.NB_COUCHES_CACHEES;
        tailleCouches = SimpleTest1.TAILLE_COUCHES_CACHEES;
        eta = 0.9;
        moment = 0.75D;
        entree = 8 * 8;
        sortie = 8 * 8;
        tmp = new SimpleTest3(nbcc, tailleCouches, eta, moment, entree, sortie);
        /*for(int i=0;i<100;i++)
        {
            tmp.iteration();
        }*/
        tab = new double[]{
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 1.0, 0.5, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.5, 1.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0
        };
        CheckUtils.checkArgument(tab.length == entree);
        tab2 = new double[][]{tab};
        tab3 = new double[][]{{
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0
        }};
        tmp.apprentissage(tab2, tab3);
        /*for(int j=tmp.donneesApprentissage.length-1;j>=0;j--)
        {
            tab=tmp.donneesApprentissage[j];
            r=tmp.reconnaissance(tab);
            System.out.println("Résultat :"+r);
        }*/
        r = tmp.reconnaissance(tab);
        System.out.println("Résultat :" + r);
        //affiche2(tmp);
    }

    // TODO: voir s'il faut le réactiver
    @Disabled("trop long a executer")
    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    public void un_test_junit1() {
        test1();
    }

    // TODO: voir s'il faut le réactiver
    @Disabled("trop long a executer")
    @Test
    @Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
    public void un_test_junit2() {
        test2();
    }
}
