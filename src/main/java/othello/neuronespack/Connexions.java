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
 * <p>Title: Connexions</p>
 * <p>Description: Gestion des connexions entre unit�s.</p>
 */
public class Connexions {

  /** Matrice des poids */
  protected double matricePoids[][];
  /** Matrice des des delta poids */
  protected double matriceDeltaPoids[][];
  /** Liste des synapses */
  private Vector lesSynapses;
  /** Nombre de synapses */
  private int nbSynapses;
  /** Nombre d'unit�s �mettrices */
  private int nbUnitesEmettrices;
  /** Nombre d'unit�s r�ceptrices */
  private int nbUnitesReceptrices;
  /** Groupe d'origine */
  private GroupeUnites groupeOrigineConnexions;
  /** Groupe de destination */
  private GroupeUnites groupeDestinationConnexions;
  /** Type de connexion */
  private int typeConnexions;

  /**
   * Constructeur
   * @param t int : Type de connexion
   * @param c1 GroupeUnites : Groupe d'origine
   * @param c2 GroupeUnites : Groupe de destination
   * @param mc boolean[][] : Matrice des connexions Emission x R�ception.
   */
  public Connexions(int t, GroupeUnites c1, GroupeUnites c2, boolean mc[][]) {
    setTypeConnexions(t);
    nbUnitesEmettrices = c1.getNbUnites();
    nbUnitesReceptrices = c2.getNbUnites();
    nbSynapses = getNbConnexions(mc);
    lesSynapses = new Vector(nbSynapses);
    setGroupeOrigineConnexions(c1);
    setGroupeDestinationConnexions(c2);
    matricePoids = new double[nbUnitesEmettrices]
        [nbUnitesReceptrices];
    matriceDeltaPoids = new double[nbUnitesEmettrices]
        [nbUnitesReceptrices];

    connecte(mc);
  }

  /**
   * Connecte des unit�s
   * @param mc boolean[][] : matrice des connexions.
   */
  public void connecte(boolean mc[][]) {
    for (int i=0; i<getNbUnitesEmettrices(); i++) {
      for (int j=0; j<getNbUnitesReceptrices(); j++) {
        if (sontConnecte(mc, i, j)) {
          Synapse sy = new Synapse(this);
          sy.connecteUniteDeA(getGroupeOrigineConnexions().getUnite(i),
                             getGroupeDestinationConnexions().getUnite(j));
          addSynapse(sy);
        }
      }
    }
  }


  /** MAJ de la matrice des poids */
  public void setMatricePoids(double m[][]) {
    matricePoids = m;
  }

  /**
   * Fixe le poids d'une connexion
   * @param x int : Position x de la matrice de connexions (origine)
   * @param y int : Position y de la matrice de connexions (destination)
   * @param valeur double : Valeur de la connexion
   */
  public void setPoids(int x, int y, double valeur) {
    matricePoids[x][y] = valeur;
  }

  /**
   * Initialisation al�atoire des poids des connexions
   * @param min double : Valeur minimum
   * @param max double : Valeur maximum
   */
  public void setPoidsAleat(double min, double max) {
    for (int i = 0; i < nbSynapses; i++) {
      ((Synapse)lesSynapses.elementAt(i))
          .setPoids(FonctionTransfert.getRandom(min, max));
    }
  }

  /** Fixe tout les poids � une valeur donn�e */
  public void setPoidsFixes(double p) {
    for (int i = 0; i < nbSynapses; i++) {
      ((Synapse)lesSynapses.elementAt(i)).setPoids(p);
    }
  }

  /** Ajout d'une synapse */
  public void addSynapse(Synapse s) {
    lesSynapses.addElement(s);
  }

  /** Getter d'une synapse */
  public Synapse getSynapse(int n) {
    return (Synapse) lesSynapses.elementAt(n);
  }

  /** Getter du nombre de synapses */
  public int getNbSynapses() {
    return nbSynapses;
  }

  /** Getter du nombre de connexions */
  private int getNbConnexions(boolean mc[][]) {
    int n = 0;
    for (int i=0; i<nbUnitesEmettrices; i++) {
      for (int j=0; j<nbUnitesReceptrices; j++) {
        if (sontConnecte(mc, i, j) == true) n++;
      }
    }
    return n;
  }

  /** Setter du type */
  public void setTypeConnexions(int t) {
    typeConnexions = t;
  }

  /** Getter du type */
  public int getTypeConnexions() {
    return typeConnexions;
  }

  /** Getter du nombre d'unites emettrices */
  public int getNbUnitesEmettrices() {
    return nbUnitesEmettrices;
  }

  /** Getter du nombre d'unites r�ceptrices */
  public int getNbUnitesReceptrices() {
    return nbUnitesReceptrices;
  }

  /** Setter du groupe d'origine */
  public void setGroupeOrigineConnexions(GroupeUnites c) {
    groupeOrigineConnexions = c;
  }

  /** Getter du groupe d'origine */
  public GroupeUnites getGroupeOrigineConnexions() {
    return groupeOrigineConnexions;
  }

  /** Setter du groupe de destination */
  public void setGroupeDestinationConnexions(GroupeUnites c) {
    groupeDestinationConnexions = c;
  }

  /** Getter du groupe de destination */
  public GroupeUnites getGroupeDestinationConnexions() {
    return groupeDestinationConnexions;
  }

  /** Getter d'un poid */
  public double getPoids(int x, int y) {
    return matricePoids[x][y];
  }

  /** Getter d'un delta poid */
  public double getDeltaPoids(int x, int y) {
    return matriceDeltaPoids[x][y];
  }

  /**
   * Renvoie flag de connexion entre 2 unit�s
   * @param mc boolean[][] : matrice des connexions
   * @param x int : position en x dans la matrice
   * @param y int : position en y dans la matrice
   * @return boolean : existence d'une connexion
   */
  public boolean sontConnecte(boolean mc[][], int x, int y) {
    return mc[x][y];
  }

  /**
   * Construit une matrice de connexions compl�tes
   * @param nbe int : Nombre d'unit�s �mettrices
   * @param nbr int : Nombre d'unit�s r�ceptrices
   * @param mc boolean[][] : Matrice de connexions
   */
  static public void construitConnexionsCompletes(int nbe, int nbr, boolean mc[][]) {
    for (int i=0; i<nbe; i++) {
      for (int j=0; j<nbr; j++) {
        mc[i][j] = true;
      }
    }
  }

  /**
   * Construit une matrice de connexions directes (diagonale)
   * @param nbe int : Nombre d'unit�s �mettrices
   * @param nbr int : Nombre d'unit�s r�ceptrices
   * @param mc boolean[][] : Matrice de connexions
   */
  static public void construitConnexionsDirectes(int nbe, boolean mc[][]) {
    for (int i=0; i<nbe; i++) {
        mc[i][i] = true;
    }
  }
}
