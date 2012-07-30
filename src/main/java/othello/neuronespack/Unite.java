package othello.neuronespack;

/**
 * Package Neurones
 * Fonctionalités de base des réseaux neuronaux.
 * Réseaux Neauronaux, Vuibert 2006.
 * Jean-Philippe Rennard
 * version 1.0, 17/3/2006
 */

/**
 *
 * <p>Title: Unité</p>
 * <p>Description: Interface de gestion des objets reliés par les synapses.</p>
 */
interface Unite {
  public int getID();
  public double getSignalBrut();
  public void addSynapsesIn(Synapse s);
  public void addSynapsesOut(Synapse s);
}
