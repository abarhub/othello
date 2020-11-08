package othello.neuronespack;

/**
 * Package Neurones
 * Fonctionalités de base des réseaux neuronaux.
 * Réseaux Neauronaux, Vuibert 2006.
 * Jean-Philippe Rennard
 * version 1.0, 17/3/2006
 */


/**
 * <p>Title: Neurone Feed-Forward</p>
 * <p>Description: Gestion des neurones Feed-Forward.</p>
 */
public class NeuroneFF extends Neurone {
  public static final int VALEUR_SIGNAL_ERREUR = 2;

  /**
   * Constructeur
   * @param c Couche : Couche contenant le neurone
   * @param Id int : Identifiant du neurone
   */
  public NeuroneFF(Couche c, int Id) {
    super(c, Id, 3);
  }

  /** Renvoie le signal d'erreur du neurone */
  public double getSignalErreur() {
    return getParametreNeurone(VALEUR_SIGNAL_ERREUR);
  }

  /**
   * Calcul du signal d'erreur sur la couche de sortie
   * @param valDes double : Valeur de sortie désirée
   */
  public void calcSignalErreurSortie(double valDes) {
    setParametreNeurone(NeuroneFF.VALEUR_SIGNAL_ERREUR,
        (valDes - getSignalBrut())
        * FonctionTransfert.calcDerivee(getMaCouche().getTypeFT(),
                                        getParametreNeurone(VALEUR_POTENTIEL),
                                        getMaCouche().getParamFT()));
  }

  /** Calcul des signaux d'erreur d'un neurone caché */
  public void calcSignalErreurCache() {
    Synapse sy;
    double p=0.0D;
    for(int i=0; i<getNbSynapsesOut(); i++) {
      sy = getSynapseOut(i);
      p += ((NeuroneFF) sy.getUniteDestination()).getSignalErreur() * sy.getPoids();
    }
    setParametreNeurone(NeuroneFF.VALEUR_SIGNAL_ERREUR, p
        * FonctionTransfert.calcDerivee(getMaCouche().getTypeFT(),
                                        getParametreNeurone(VALEUR_POTENTIEL),
                                        getMaCouche().getParamFT()));
  }

  /** Traitement du neurone */
  public void run() {
    setPotentiel(calcPotentiel());
    setSignal(calcSignal());
  }

}
