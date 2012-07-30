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
 * <p>Title: Neurone</p>
 * <p>Description: Gestion des fonctions de base d'un neurone.</p>
 */
public abstract class Neurone implements Unite {
  public static final int VALEUR_SIGNAL = 0;
  public static final int VALEUR_POTENTIEL = 1;

  /** Dimension du tableau des param�tres */
  private int nbParametresNeurone;
  /** Param�tres du neurone */
  private double parametreNeurone[];
  /** Nombre de synapses entrantes */
  private int nbSynapsesIn;
  /** Nombre de synapses sortantes */
  private int nbSynapsesOut;
  /** Vecteur des synapses entrantes */
  private Vector synapsesIn;
  /** Vecteur des synapses sortantes */
  private Vector synapsesOut;
  /** Identifiant */
  private int idNeurone;
  /** Couche contenant le neurone */
  private Couche maCouche;

  /**
   * Constructeur du neurone
   * @param c Couche : Couche contenant le neurone
   * @param Id int : Identifiant du neurone
   * @param nbp int : Nombre de param�tres du neurone
   */
  public Neurone(Couche c, int Id, int nbp) {
    maCouche=c;
    idNeurone = Id;
    nbParametresNeurone = nbp;
    parametreNeurone = new double[nbParametresNeurone];
    nbSynapsesIn = 0;
    nbSynapsesOut = 0;
    synapsesIn = new Vector(nbSynapsesIn);
    synapsesOut = new Vector(nbSynapsesOut);
  }

  /** Ajoute une synapse entrant dans le neurone */
  public void addSynapsesIn(Synapse s) {
    nbSynapsesIn++;
    synapsesIn.addElement(s);
  }

  /** Ajoute une synapse sortant du neurone */
  public void addSynapsesOut(Synapse s) {
    nbSynapsesOut++;
    synapsesOut.addElement(s);
  }

  /** Getter d'une synapse entrante */
  public Synapse getSynapseIn(int n) {
    return (Synapse) synapsesIn.elementAt(n);
  }

  /** Getter d'une synapse sortante */
  public Synapse getSynapseOut(int n) {
    return (Synapse) synapsesOut.elementAt(n);
  }

  /** Getter du nombre de synapses entrantes */
  public int getNbSynapsesIn() {
    return nbSynapsesIn;
  }

  /** Getter du nombre de synapses sortantes */
  public int getNbSynapsesOut() {
    return nbSynapsesOut;
  }

  /** Setter du potentiel */
  public void setPotentiel(double p) {
    parametreNeurone[VALEUR_POTENTIEL] = p;
  }

  /** Renvoie le potentiel */
  public double getPotentiel() {
    return parametreNeurone[VALEUR_POTENTIEL];
  }

  /** Calcul du potentiel */
  public double calcPotentiel() {
    double p=0.0D;
    for(int i=0;i<nbSynapsesIn;i++) {
      p += ((Synapse) synapsesIn.elementAt(i)).getValeurSynapse();
    }
    return p;
  }

  /** Setter du signal */
  public void setSignal(double s) {
    parametreNeurone[VALEUR_SIGNAL] = s;
  }

  /** Renvoie le signal du neurone */
  public double getSignalBrut() {
    return parametreNeurone[VALEUR_SIGNAL];
  }

  /** Calcul du signal */
  public double calcSignal() {
    return (FonctionTransfert.calcTransfert(maCouche.getTypeFT(),
        parametreNeurone[VALEUR_POTENTIEL], maCouche.getParamFT()));//  .paramFT));
  }

  /** Getter de l'identifiant */
  public int getID() {
    return idNeurone;
  }

  /** Getter d'un param�tre */
  public double getParametreNeurone(int n) {
    return parametreNeurone[n];
  }

  /** Setter d'un param�tre */
  public void setParametreNeurone(int n, double v) {
    parametreNeurone[n] = v;
  }

  /** Getter de la couche du neurone */
  public Couche getMaCouche() {
    return maCouche;
  }
// *********************************************************************
  public abstract void run();

}
