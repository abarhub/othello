package org.othello.neuronespack;

/**
 * Package Neurones
 * Fonctionalités de base des réseaux neuronaux.
 * Réseaux Neauronaux, Vuibert 2006.
 * Jean-Philippe Rennard
 * version 1.0, 17/3/2006
 */

import java.util.Vector;

/**
 * <p>Title: Couche</p>
 * <p>Description: Gestion des fonctions de base des groupes de neurones.</p>
 */
public abstract class Couche implements GroupeUnites {

    /**
     * nombre de neurones
     */
    private int nbNeurones;
    /**
     * Liste de pointeurs sur les neurones
     */
    private Vector lesUnites;
    /**
     * type fonction transfert
     */
    private int typeFT;
    /**
     * paramètres de la fonction de transfert
     */
    private double paramFT[];

    /**
     * Constructeur de la couche
     *
     * @param nbN int : Nombre de neurones
     * @param ft  int : Fonction de transfert
     * @param pFT double[] : Paramètres de la fonction de transfert
     */
    public Couche(int nbN, int ft, double pFT[]) {
        int nbParamFT = FonctionTransfert.getNbParametre(typeFT);
        nbNeurones = nbN;
        lesUnites = new Vector(nbNeurones);
        typeFT = ft;
        paramFT = new double[nbParamFT];
        for (int i = 0; i < nbParamFT; i++) {
            paramFT[i] = pFT[i];
        }
    }

    /**
     * Ajout d'un neurone
     */
    public void addNeurone(Neurone n) {
        lesUnites.addElement(n);
    }

    /**
     * Getter d'un neurone
     */
    public Unite getUnite(int u) {
        return (Unite) lesUnites.elementAt(u);
    }

    /**
     * Getter des neurones
     */
    public Vector getLesUnites() {
        return lesUnites;
    }

    /**
     * Getter du nombre de neurones
     */
    public int getNbUnites() {
        return nbNeurones;
    }

    /**
     * Getter type fonction transfert
     */
    public int getTypeFT() {
        return typeFT;
    }

    /**
     * Getter des paramètres de la fonction de transfert
     */
    public double[] getParamFT() {
        return paramFT;
    }

    // *********************************************************************
    public abstract void traiteCouche(Reseau leReseau);

    protected abstract void creeNeurones();
}
