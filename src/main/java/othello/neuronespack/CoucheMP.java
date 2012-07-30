package othello.neuronespack;

/**
 * Package Neurones
 * Fonctionalités de base des réseaux neuronaux.
 * Réseaux Neauronaux, Vuibert 2006.
 * Jean-Philippe Rennard
 * version 1.0, 17/3/2006
 */

/**
 * <p>Title: Couche McCulloch-Pitts</p>
 * <p>Description: Gestion des couches McCulloch-Pitts.</p>
 */
public class CoucheMP extends Couche {

  /**
   * Constructeur de la couche
   * @param nbN int : Nombre de neurones
   * @param ft int : Fonction de transfert
   * @param pFT double[] : Paramètres de la fonction de transfert
   */
  public CoucheMP(int nbN, int ft, double pFT[]) {
    super(nbN, ft, pFT);
    creeNeurones();
  }

  /** Crée les neurones */
  public void creeNeurones() {
    for(int i=0; i<getNbUnites(); i++) {
      addNeurone(new NeuroneMP(this, i));
    }
  }

  /** Traitement de la couche */
  public void traiteCouche(Reseau leReseau) {
    Neurone neurone;
    for(int i=0; i<getNbUnites(); i++) {
      neurone = (Neurone) getUnite(i);
      neurone.setSignal(neurone.calcSignal());
    }
    for(int i=0; i<getNbUnites(); i++) {
      neurone = (Neurone) getUnite(i);
      neurone.setPotentiel(neurone.calcPotentiel());
    }
  }

}
