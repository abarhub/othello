package othello.neuronespack;

/**
 * Package Neurones
 * Fonctionalit�s de base des r�seaux neuronaux.
 * R�seaux Neauronaux, Vuibert 2006.
 * Jean-Philippe Rennard
 * version 1.0, 17/3/2006
 */

/**
 * <p>Title: R�seau Feed-Forward</p>
 * <p>Description: Gestion des r�seaux Feed-Forward</p>
 */
public abstract class ReseauFF extends Reseau {

  /** Pas d'apprentissage */
  private double eta;

  /**
   * Constructeur du r�seau
   * @param nbC int : nombre de couches
   */
  public ReseauFF(int nbC) {
    super(nbC);
  }

  /**
   * Cr�ation d'une couche
   * @param nbN int : nombre de neurones
   * @param ft int : type de fonction de transfert
   * @param pFT int[] : param�tres de la fonction de transfert
   */
  public void ajouteCouche(int nbN, int ft, double pFT[]) {
    addCouche(new CoucheFF(nbN, ft, pFT));
  }

  /** Propagation avant */
  public void propagation() {
    for(int i=0; i<getNbCouches(); i++) {
      getCouche(i).traiteCouche(this);
    }
    getResultats().setVecteurResultat(getLesConnexions());
  }

  /** Setter du pas d'apprentissage */
  public void setEta(double e) {
    eta = e;
  }

  /** Getter du pas d'apprentissage */
  public double getEta() {
    return eta;
  }

// *********************************************************************
  public abstract double apprend();
}
