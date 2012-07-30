package othello.neuronespack;

/**
 * Package Neurones
 * Fonctionalit�s de base des r�seaux neuronaux.
 * R�seaux Neauronaux, Vuibert 2006.
 * Jean-Philippe Rennard
 * version 1.0, 17/3/2006
 */

import java.util.*;

/**
 *
 * <p>Title: Groupe d'unit�s</p>
 * <p>Description: Interface de gestion des groupes d'unit�s.</p>
 */
interface GroupeUnites {
  public Vector getLesUnites();
  public Unite getUnite(int u);
  public int getNbUnites();
}
