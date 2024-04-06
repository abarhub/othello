package org.othello.test_neurone;

import org.othello.neuronespack.FonctionTransfert;
import org.othello.neuronespack.ReseauBP;
import org.othello.utils.CheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by IntelliJ IDEA.
 * User: Alain
 * Date: 10 janv. 2010
 * Time: 17:57:28
 * To change this template use File | Settings | File Templates.
 */
public class SimpleTest3 extends ReseauBP {

    public static Logger log = LoggerFactory.getLogger(SimpleTest3.class);

    public static final int NON_RECONNU = -1;
    // Paramètres par défaut
    public static int NB_COUCHES_CACHEES = 1;
    //public static int[] TAILLE_COUCHES_CACHEES = {10};
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

    private int entree, sortie;

    public SimpleTest3(int entree, int tailleCouches, int sortie) {
        this(NB_COUCHES_CACHEES, new int[]{tailleCouches}, ETA, 0.5, entree, sortie);
    }

    public SimpleTest3(/*BPRecChiffreProcess p,*/ int nbcc, int[] tailleCouches
            , double eta, double moment, int entree, int sortie) {
        super(nbcc + 2);
        CheckUtils.checkArgument(nbcc >= 0);
        CheckUtils.checkArgument(entree > 0);
        CheckUtils.checkArgument(sortie > 0);
        //processBP = p;
        this.entree = entree;
        this.sortie = sortie;
        setDonneesCourantes(entree, sortie);
        setResultat(sortie);
        int paramReseau[] = new int[getNbCouches()];
        paramReseau[0] = entree;
        for (int i = 0; i < nbcc; i++) {
            paramReseau[i + 1] = tailleCouches[i];
        }
        paramReseau[nbcc + 1] = sortie;
        double paramFT[] = new double[1];
        construitReseau(paramReseau, FonctionTransfert.F_LOGISTIQUE, paramFT);
        setEta(eta);//0.9D);
        setMomentum(moment);//0.7D);//0.75D);
    }

    /**
     * Apprentissage
     */
    public void apprentissage(double[][] donneesApprentissage, double[][] donneesSortie) {
        double er, err_old = 0.0;
        boolean premier_passage = true;
        CheckUtils.checkArgument(donneesApprentissage != null);
        CheckUtils.checkArgument(donneesSortie != null);
        CheckUtils.checkArgument(donneesSortie.length == donneesApprentissage.length);
        log.info("apprentissage par iterration...");
        while ((er = iteration(donneesApprentissage, donneesSortie)) > 0.01 && it < 10000) {
            log.info("erreur=" + er);
            if (!premier_passage) {
                if (Math.abs(err_old) < Math.abs(er) && Math.signum(err_old) > 0) {
                    CheckUtils.checkArgument(false, "err=" + er + ",err_old=" + err_old);
                }
            }
            err_old = er;
            premier_passage = false;
        }
        log.info("fin d'apprentissage par iterration");
    }

    /**
     * Reconnaissance
     */
    public int reconnaissance(double val[]) {
        CheckUtils.checkArgument(val != null);
        CheckUtils.checkArgument(val.length == entree);
        double[] entree = new double[this.entree];
        for (int j = 0; j < this.entree; j++) {
            entree[j] = val[j];
        }
        getDonneesCourantes().setVecteurEntree(val);
        propagation();
        for (int j = 0; j < sortie; j++) {
            if (getResultats().getValeurSortie(j) > 0.95) {
                return j;
            }
        }
        return NON_RECONNU;
    }

    /**
     * Itération. Renvoie valeur d'erreur.
     */
    public double iteration(double[][] donneesApprentissage, double[][] donneesSortie) {
        CheckUtils.checkArgument(donneesApprentissage != null);
        CheckUtils.checkArgument(donneesApprentissage.length > 0);
        CheckUtils.checkArgument(donneesSortie != null);
        CheckUtils.checkArgument(donneesSortie.length > 0);
        CheckUtils.checkArgument(donneesSortie.length == donneesApprentissage.length);
        double err = 0.0D;
        double[] entree = new double[this.entree];
        double[] sortieDes = new double[this.sortie];
        it++;
        for (int i = 0; i < donneesApprentissage.length; i++) {
            log.info("AAA0(" + i + "," + donneesApprentissage.length + ")");
            CheckUtils.checkArgument(donneesApprentissage[i].length == this.entree, "i=" + i + "," + donneesApprentissage[i].length + "," + this.entree);
            for (int j = 0; j < this.entree; j++) {
                entree[j] = donneesApprentissage[i][j];
            }
            log.info("AAA1");
            getDonneesCourantes().setVecteurEntree(entree);
            CheckUtils.checkArgument(donneesSortie[i].length == this.sortie);
            log.info("AAA2");
            for (int j = 0; j < this.sortie; j++) {
                //sortieDes[j] = (j == i ? 1.0D : 0.0D);
                sortieDes[j] = donneesSortie[i][j];
            }
            getDonneesCourantes().setVecteurSortieDesire(sortieDes);
            log.info("AAA");
            err += apprend();
            log.info("AAA3");
        }
        return err;
    }
}