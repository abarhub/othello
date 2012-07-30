package othello.neuronespack;

/**
 * Package Neurones
 * Fonctionalités de base des réseaux neuronaux.
 * Réseaux Neauronaux, Vuibert 2006.
 * Jean-Philippe Rennard
 * version 1.0, 17/3/2006
 */

/**
 * <p>Title: Couche Kohonen</p>
 * <p>Description: Gestion des couches de type Kohonen.</p>
 */
public class CoucheKoho extends Couche {

  /** Taille de la couche en X */
  private int nbNeuronesX;
  /** Taille de la couche en Y */
  private int nbNeuronesY;

  /**
   * Constructeur de la couche
   * @param nbNx int : Taille de la couche en x
   * @param nbNy int : Taille de la couche en y
   * @param ft int : Fonction de transfert
   * @param pFT double[] : Paramètres de la fonction de transfert
   */
  public CoucheKoho(int nbNx, int nbNy, int ft, double pFT[]) {
    super(nbNx*nbNy, ft, pFT);
    nbNeuronesX = nbNx;
    nbNeuronesY = nbNy;
    creeNeurones();
  }

  /** Crée les neurones */
  public void creeNeurones() {
    for(int x=0; x<nbNeuronesX; x++) {
      for(int y=0; y<nbNeuronesX; y++) {
        addNeurone(new NeuroneKoho(this, x * nbNeuronesX + y, x, y));
      }
    }
  }

  /** Traitement de la couche */
  public void traiteCouche(Reseau leReseau) {
    NeuroneKoho neurone;
    NeuroneKoho BestMU = null;
    double maxDist = Double.MAX_VALUE;
    for(int i=0; i<getNbUnites(); i++) {
      neurone = ((NeuroneKoho) getUnite(i));
      neurone.run();
      if (neurone.getPotentiel() < maxDist) {
        maxDist = neurone.getPotentiel();
        BestMU = neurone;
      }
    }
    BestMU.setSignal(1.0D);
    ((ReseauKoho) leReseau).setBMU(BestMU);
  }

  /** Renvoie la distance entre 2 neurones */
  public double getDistanceNeurones(NeuroneKoho neurone1, NeuroneKoho neurone2) {
    return Math.sqrt(((neurone1.getPosX() - neurone2.getPosX())
                   * (neurone1.getPosX() - neurone2.getPosX()))
                  + ((neurone1.getPosY() - neurone2.getPosY())
                   * (neurone1.getPosY() - neurone2.getPosY())));
  }

  /** Getter nombre de neurones en x */
  public int getNbNeuronesX() {
    return nbNeuronesX;
  }

  /** Getter nombre de neurones en y */
  public int getNbNeuronesY() {
    return nbNeuronesY;
  }
}
