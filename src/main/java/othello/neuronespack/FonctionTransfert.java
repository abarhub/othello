package othello.neuronespack;

/**
 * Package Neurones
 * Fonctionalités de base des réseaux neuronaux.
 * Réseaux Neauronaux, Vuibert 2006.
 * Jean-Philippe Rennard
 * version 1.0, 17/3/2006
 */

/**
 * <p>Title: Fonctions de transfert</p>
 * <p>Description: Gestion des fonctions de transfert.</p>
 */
public class FonctionTransfert {
    public static final int F_LINEAIRE = 0;
    public static final int F_SIGNE = 1;
    public static final int F_BIPOLAIRE = 2;
    public static final int F_LOGISTIQUE = 3;
    public static final int F_TANH = 4;

    /**
     * Calcul de la fonction de transfert
     *
     * @param type int : Type de fonction
     * @param x    double : Valeur (potentiel)
     * @param val  double[] : Tableau des paramètres de la fonction
     * @return double : Valeur de retour
     */
    public static double calcTransfert(int type, double x, double val[]) {
        switch (type) {
            case F_SIGNE:
                return fonctionSigne(x);
            case F_BIPOLAIRE:
                return fonctionBipolaire(x);
            case F_LOGISTIQUE:
                return fonctionLogistique(x);
            case F_TANH:
                return fonctionTangenteHyperbolique(x);
            case F_LINEAIRE:
            default:
                return fonctionLineaire(x, val);
        }
    }

    /**
     * Calcul de la dérivée de la fonction de transition
     *
     * @param type int : Type de fonction
     * @param x    double : Valeur (potentiel)
     * @param val  double[] : Tableau des paramètres de la fonction
     * @return double : Valeur de retour
     */
    public static double calcDerivee(int type, double x, double val[]) {
        double dummy;
        switch (type) {
            case F_LINEAIRE:
                return val[0];
            case F_LOGISTIQUE:
                dummy = fonctionLogistique(x);
                return dummy * (1.0D - dummy);
            case F_TANH:
                return 4.0D / Math.pow(Math.exp(x) + Math.exp(-1.0D * x), 2);
            default:
                return 1.0D;
        }
    }

    /**
     * Calcul transfert linéaire : y=a.x
     *
     * @param x   double : Valeur d'entrée
     * @param val double[] : 1 seul paramètre, coefficient multiplicateur (a)
     * @return double : Valeur de sortie
     */
    private static double fonctionLineaire(double x, double val[]) {
        return val[0] * x;
    }

    /**
     * Calcul fonction signe (0 si <0, 1 sinon)
     */
    private static double fonctionSigne(double x) {
        return (x < 0 ? 0.0D : 1.0D);
    }

    /**
     * Calcul fonction bipolaire (-1 si <0, +1 sinon)
     */
    private static double fonctionBipolaire(double x) {
        return (x < 0 ? -1.0D : 1.0D);
    }

    /**
     * Calcul transfert logistique : y=1/(1+exp(-x))
     */
    private static double fonctionLogistique(double x) {
        return 1.0D / (1.0D + Math.exp(-1.0D * x));
    }

    /**
     * Calcul transfert tangente hyperbolique (exp(x)-exp(-x))/(exp(x)+exp(-x))
     */
    private static double fonctionTangenteHyperbolique(double x) {
        return (Math.exp(x) - Math.exp(-1.0D * x))
                / (Math.exp(x) + Math.exp(-1.0D * x));
    }

    /**
     * Retourne nombre de paramètres selon fonction
     */
    public static int getNbParametre(int typeFonction) {
        switch (typeFonction) {
            case F_LINEAIRE:
                return 1;
            case F_SIGNE:
            case F_BIPOLAIRE:
            case F_LOGISTIQUE:
            case F_TANH:
            default:
                return 0;
        }
    }

    /**
     * Retourne un nombre aléatoire entre 2 bornes
     */
    public static double getRandom(double min, double max) {
        if (min == 0.0D && max == 1.0D)
            return Math.random();
        if (min == -1.0D && max == 1.0D)
            return (Math.random() > 0.5 ? -1.0D : 1.0D) * Math.random();
        if (min >= 0 && max >= 0)
            return min + (max - min) * Math.random();
        if (min <= 0 && max < 0)
            return max - (Math.abs(min - max)) * Math.random();
        return min + (max - min) * Math.random();
    }
}

