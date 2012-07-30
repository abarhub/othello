package othello.neuronespack;

/**
 * Package Neurones
 * Fonctionalit�s de base des r�seaux neuronaux.
 * R�seaux Neauronaux, Vuibert 2006.
 * Jean-Philippe Rennard
 * version 1.0, 17/3/2006
 */

/**
 * <p>Title: Synapse</p>
 * <p>Description: Gestion des synapses (liens entre unit�s).</p>
 */
public class Synapse {

  /** Connexions */
  private Connexions maConnexion;
  /** Unit� d'origine */
  private Unite uniteOrigine;
  /** Unit� de destination */
  private Unite uniteDestination;

  /** Constructeur d'une synapse dans une connexion */
  public Synapse(Connexions c) {
    maConnexion = c;
  }

  /** Setter du poids de la synapse */
  public void setPoids(double p) {
    maConnexion.matricePoids[uniteOrigine.getID()]
        [uniteDestination.getID()] = p;
  }

  /** Getter du poids de la synapse */
  public double getPoids() {
    return maConnexion.matricePoids[uniteOrigine.getID()]
        [uniteDestination.getID()];
  }

  /** Setter du delta poids de la synapse */
  public void setDeltaPoids(double p) {
    maConnexion.matriceDeltaPoids[uniteOrigine.getID()]
        [uniteDestination.getID()] = p;
  }

  /** Getter du delta poids de la synapse */
  public double getDeltaPoids() {
    return maConnexion.matriceDeltaPoids[uniteOrigine.getID()]
        [uniteDestination.getID()];
  }

  /** Getter de la connexion */
  public Connexions getMaConnexion() {
    return maConnexion;
  }

  /** Getter de l'unit� d'origine */
  public Unite getUniteOrigine() {
    return uniteOrigine;
  }

  /** Getter de l'unit� de destination */
  public Unite getUniteDestination() {
    return uniteDestination;
  }

  /** Renvoie la valeur de la synapse (signal * poids) */
  public double getValeurSynapse() {
    return uniteOrigine.getSignalBrut()*getPoids();
  }

  /**
   * Connexion des deux Unit�s
   * @param uOrig EntreeSortie : Unit� d'origine
   * @param uDest EntreeSortie : Unit� de destination
   */
  public void connecteUniteDeA(Unite uOrig, Unite uDest) {
    setUniteOrigine(uOrig);
    setUniteDestination(uDest);
  }

  /** Fixe l'unit� d'origine */
  private void setUniteOrigine(Unite l) {
      uniteOrigine = l;
      l.addSynapsesOut(this);
  }

  /** Fixe l'unit� de destination */
  private void setUniteDestination(Unite l) {
      uniteDestination = l;
      l.addSynapsesIn(this);
  }
}
