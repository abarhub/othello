package othello.neuronespack;

/**
 * Package Neurones
 * Fonctionalités de base des réseaux neuronaux.
 * Réseaux Neauronaux, Vuibert 2006.
 * Jean-Philippe Rennard
 * version 1.0, 17/3/2006
 */

/**
 * <p>Title: Neurone de Kohonen</p>
 * <p>Description: Gestion des neurones de Kohonen.</p>
 */
public class NeuroneKoho extends Neurone {
  public static final int POS_X = 2;
  public static final int POS_Y = 3;

  /**
   * Constructeur
   * @param c Couche : Couche contenant le neurone
   * @param Id int : Identifiant du neurone
   * @param x int : Position du neurone en x
   * @param y int : Position du neurone en y
   */
  public NeuroneKoho(Couche c, int Id, int x, int y) {
    super(c, Id, 4);
    setParametreNeurone(POS_X, x);
    setParametreNeurone(POS_Y, y);
    setPotentiel(-1.0D);
  }

  /** Calcul du potentiel :
   * soit distance euclidienne entre les vecteurs d'entrée et de poids */
  public double calcPotentiel() {
    Synapse sy;
    double p=0.0D;
    for(int i=0;i<getNbSynapsesIn();i++) {
      sy = getSynapseIn(i);
         p += (sy.getUniteOrigine().getSignalBrut() - sy.getPoids())
          * (sy.getUniteOrigine().getSignalBrut() - sy.getPoids());
    }
    return Math.sqrt(p);
  }

  /** Renvoie position en x */
  public double getPosX() {
    return getParametreNeurone(POS_X);
  }

  /** Renvoie position en y */
  public double getPosY() {
    return getParametreNeurone(POS_Y);
  }

  /** Traitement du neurone */
  public void run() {
    setSignal(-1.0D);
    setPotentiel(calcPotentiel());
  }
}
