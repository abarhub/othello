package othello.neuronespack;

/**
 * Package Neurones
 * Fonctionalit�s de base des r�seaux neuronaux.
 * R�seaux Neauronaux, Vuibert 2006.
 * Jean-Philippe Rennard
 * version 1.0, 17/3/2006
 */

/**
 *
 * <p>Title: Unit�</p>
 * <p>Description: Interface de gestion des objets reli�s par les synapses.</p>
 */
interface Unite {
  public int getID();
  public double getSignalBrut();
  public void addSynapsesIn(Synapse s);
  public void addSynapsesOut(Synapse s);
}
