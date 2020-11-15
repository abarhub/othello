package othello.neuronespack;

/**
 * Package Neurones
 * Fonctionalités de base des réseaux neuronaux.
 * Réseaux Neauronaux, Vuibert 2006.
 * Jean-Philippe Rennard
 * version 1.0, 17/3/2006
 */

import java.util.Vector;

/**
 * <p>Title: Groupe d'unités</p>
 * <p>Description: Interface de gestion des groupes d'unités.</p>
 */
interface GroupeUnites {
    public Vector getLesUnites();

    public Unite getUnite(int u);

    public int getNbUnites();
}
