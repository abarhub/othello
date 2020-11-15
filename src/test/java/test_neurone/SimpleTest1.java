package test_neurone;

import othello.neuronespack.FonctionTransfert;
import othello.neuronespack.ReseauBP;

/**
 * Created by IntelliJ IDEA.
 * User: Alain
 * Date: 10 janv. 2010
 * Time: 14:04:04
 * To change this template use File | Settings | File Templates.
 */
public class SimpleTest1 extends ReseauBP {
    /**
     * <p>Title: Réseau BP reconnaissance chiffres</p>
     * <p>Description: Réseau BP pour la reconnaissance de chiffres.</p>
     */
    //public class ReseauBPRecChiffre extends ReseauBP {
    public static final int NON_RECONNU = -1;
    // Paramètres par défaut
    public static int NB_COUCHES_CACHEES = 1;
    public static int[] TAILLE_COUCHES_CACHEES = {10};
    public static double ETA = 0.9D;
    public static double MOMENT = 0.7D;
    public static int MAX_NEURONES = 25;
    /**
     * Itération
     */
    static int it;

    /** Pointeur sur processus */
    //BPRecChiffreProcess processBP;
    /**
     * Nb maximum d'itérations autorisées
     */
    int maxIteration = 10000;
    /**
     * Seuil de convergence (erreur totale acceptable)
     */
    double seuilConvergence = 0.01D;

    /**
     * Tableau des données d'apprentissage
     */
    double[][] donneesApprentissage = {
            {
                    1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 0.0D}  //0
            , {
            0.0D, 1.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D}  //1
            , {
            1.0D, 1.0D, 0.0D, 1.0D, 1.0D, 0.0D, 1.0D}  //2
            , {
            1.0D, 1.0D, 1.0D, 1.0D, 0.0D, 0.0D, 1.0D}  //3
            , {
            0.0D, 1.0D, 1.0D, 0.0D, 0.0D, 1.0D, 1.0D}  //4
            , {
            1.0D, 0.0D, 1.0D, 1.0D, 0.0D, 1.0D, 1.0D}  //5
            , {
            1.0D, 0.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D}  //6
            , {
            1.0D, 1.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D}  //7
            , {
            1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D}  //8
            , {
            1.0D, 1.0D, 1.0D, 1.0D, 0.0D, 1.0D, 1.0D}  //9
            , {
            1.0D, 1.0D, 1.0D, 0.0D, 1.0D, 1.0D, 1.0D}  //A
            , {
            0.0D, 0.0D, 1.0D, 1.0D, 1.0D, 1.0D, 1.0D}  //b
            , {
            1.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D, 0.0D}  //C
            , {
            0.0D, 1.0D, 1.0D, 1.0D, 1.0D, 0.0D, 1.0D}  //d
            , {
            1.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D, 1.0D}  //E
            , {
            1.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D}  //F
    };

    /**
     * Construit le réseau au sein d'un processus
     */
    public SimpleTest1(/*BPRecChiffreProcess p,*/ int nbcc, int[] tailleCouches
            , double eta, double moment) {
        super(nbcc + 2);
        //processBP = p;
        setDonneesCourantes(7, 16);
        setResultat(16);
        int paramReseau[] = new int[getNbCouches()];
        paramReseau[0] = 7;
        for (int i = 0; i < nbcc; i++) {
            paramReseau[i + 1] = tailleCouches[i];
        }
        paramReseau[nbcc + 1] = 16;
        double paramFT[] = new double[1];
        construitReseau(paramReseau, FonctionTransfert.F_LOGISTIQUE, paramFT);
        setEta(eta);//0.9D);
        setMomentum(moment);//0.7D);//0.75D);
    }

    /**
     * Apprentissage
     */
    public void apprentissage() {
        double er;
        while ((er = iteration()) > 0.01 && it < 10000) ;
    }

    /**
     * Reconnaissance
     */
    public int reconnaissance(double val[]) {
        double[] entree = new double[7];
        for (int j = 0; j < 7; j++) {
            entree[j] = val[j];
        }
        getDonneesCourantes().setVecteurEntree(val);
        propagation();
        for (int j = 0; j < 16; j++) {
            if (getResultats().getValeurSortie(j) > 0.95) {
                return j;
            }
        }
        return NON_RECONNU;
    }

    /**
     * Itération. Renvoie valeur d'erreur.
     */
    public double iteration() {
        double err = 0.0D;
        double[] entree = new double[7];
        double[] sortieDes = new double[16];
        it++;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 7; j++) {
                entree[j] = donneesApprentissage[i][j];
            }
            getDonneesCourantes().setVecteurEntree(entree);
            for (int j = 0; j < 16; j++) {
                sortieDes[j] = (j == i ? 1.0D : 0.0D);
            }
            getDonneesCourantes().setVecteurSortieDesire(sortieDes);
            err += apprend();
        }
        return err;
    }


}
