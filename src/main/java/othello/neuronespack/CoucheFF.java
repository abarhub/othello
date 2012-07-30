package othello.neuronespack;

/**
 * Package Neurones
 * Fonctionalités de base des réseaux neuronaux.
 * Réseaux Neauronaux, Vuibert 2006.
 * Jean-Philippe Rennard
 * version 1.0, 17/3/2006
 */

/**
 * <p>Title: Couche FeedForward</p>
 * <p>Description: Gestion des couches FeedForward.</p>
 */
public class CoucheFF extends Couche {

  /**
   * Constructeur de la couche
   * @param nbN int : Nombre de neurones
   * @param ft int : Fonction de transfert
   * @param pFT double[] : Paramètres de la fonction de transfert
   */
  public CoucheFF(int nbN, int ft, double pFT[]) {
    super(nbN, ft, pFT);
    creeNeurones();
  }

  /** Création des neurones */
  public void creeNeurones() {
    for(int i=0; i<getNbUnites(); i++) {
      addNeurone(new NeuroneFF(this, i));
    }
  }

  /** Traitement de la couche */
  public void traiteCouche(Reseau leReseau) {
    for(int i=0; i<getNbUnites(); i++) {
      ((Neurone) getUnite(i)).run();
    }
  }
}
