package othello.neuronespack;

/**
 * Package Neurones
 * Fonctionalités de base des réseaux neuronaux.
 * Réseaux Neauronaux, Vuibert 2006.
 * Jean-Philippe Rennard
 * version 1.0, 17/3/2006
 */

/**
 * <p>Title: Réseau Feed-Forward</p>
 * <p>Description: Gestion des réseaux Feed-Forward</p>
 */
public abstract class ReseauFF extends Reseau {

    /**
     * Pas d'apprentissage
     */
    private double eta;

    /**
     * Constructeur du réseau
     *
     * @param nbC int : nombre de couches
     */
    public ReseauFF(int nbC) {
        super(nbC);
    }

    /**
     * Création d'une couche
     *
     * @param nbN int : nombre de neurones
     * @param ft  int : type de fonction de transfert
     * @param pFT int[] : paramètres de la fonction de transfert
     */
    public void ajouteCouche(int nbN, int ft, double pFT[]) {
        addCouche(new CoucheFF(nbN, ft, pFT));
    }

    /**
     * Propagation avant
     */
    public void propagation() {
        for (int i = 0; i < getNbCouches(); i++) {
            getCouche(i).traiteCouche(this);
        }
        getResultats().setVecteurResultat(getLesConnexions());
    }

    /**
     * Setter du pas d'apprentissage
     */
    public void setEta(double e) {
        eta = e;
    }

    /**
     * Getter du pas d'apprentissage
     */
    public double getEta() {
        return eta;
    }

    // *********************************************************************
    public abstract double apprend();
}
